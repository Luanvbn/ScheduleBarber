package br.com.schedulebarber.scheduleBarber.Service;


import br.com.schedulebarber.scheduleBarber.Exception.*;
import br.com.schedulebarber.scheduleBarber.Model.Barber;
import br.com.schedulebarber.scheduleBarber.Model.Client;
import br.com.schedulebarber.scheduleBarber.Model.Scheduling;
import br.com.schedulebarber.scheduleBarber.Model.Servico;
import br.com.schedulebarber.scheduleBarber.Repository.BarberRepository;
import br.com.schedulebarber.scheduleBarber.Repository.ClientRepository;
import br.com.schedulebarber.scheduleBarber.Repository.SchedulingRepository;
import br.com.schedulebarber.scheduleBarber.Repository.ServicoRepository;
import br.com.schedulebarber.scheduleBarber.Util.PaginationParams;
import br.com.schedulebarber.scheduleBarber.Util.SchedulingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SchedulingService {

    @Autowired
    public SchedulingRepository schedulingRepository;

    @Autowired
    public ClientRepository clientRepository;

    @Autowired
    public BarberRepository barberRepository;

    @Autowired
    public ServicoRepository servicoRepository;

    public Optional<Scheduling> findSchedulingById(Long id) throws SchedulingNotExistsException {
        Optional<Scheduling> optionalScheduling = schedulingRepository.findById(id);
        if (optionalScheduling.isPresent()) {
            return schedulingRepository.findById(id);
        } else {
            throw new SchedulingNotExistsException();
        }
    }

    public Page<Scheduling> findAllScheduling(PaginationParams params) {
        Sort sort = params.getSortOrder().equalsIgnoreCase("asc") ?
                Sort.by(params.getSortProperty()).ascending() : Sort.by(params.getSortProperty()).descending();
        Pageable pageable = PageRequest.of(params.getPage(), params.getSize(), sort);
        return schedulingRepository.findAll(pageable);
    }

    public Scheduling createScheduling(SchedulingRequest schedulingRequest) throws ClientNotExistsException, BarberNotExistsException, BarberDoesNotHaveService, SchedulingConflictException {
        Client client = getClientById(schedulingRequest.getClientId());
        Barber barber = getBarberById(schedulingRequest.getBarberId());
        List<Servico> servicos = getServicosByIds(schedulingRequest.getServiceIds(), barber);

        checkServiceBelongsToBarber(servicos, barber);
        checkSchedulingConflicts(schedulingRequest.getScheduling().getDataInicio(), schedulingRequest.getScheduling().getDatafim(), barber);

        Scheduling saveScheduling = createAndSaveScheduling(client, barber, schedulingRequest.getScheduling().getDataInicio(), schedulingRequest.getScheduling().getDatafim(), servicos);

        return saveScheduling;
    }

    private void checkServiceBelongsToBarber(List<Servico> servicos, Barber barber) throws BarberDoesNotHaveService {
        for (Servico servico : servicos) {
            if (!servico.getBarber().equals(barber)) {
                throw new BarberDoesNotHaveService("Serviço " + servico.getNomeServico() + " não pertence ao barbeiro selecionado");
            }
        }
    }

    private void checkSchedulingConflicts(LocalDateTime dataInicio, LocalDateTime dataFim, Barber barber) throws SchedulingConflictException {
        List<Scheduling> existingSchedulings = schedulingRepository.findByBarber(barber);
        for (Scheduling existingScheduling : existingSchedulings) {
            LocalDateTime existingDataInicio = existingScheduling.getDataInicio();
            LocalDateTime existingDataFim = existingScheduling.getDatafim();
            if (dataInicio.isBefore(existingDataFim) && dataFim.isAfter(existingDataInicio)) {
                throw new SchedulingConflictException("Já existe um agendamento no mesmo período");
            }
        }
    }

    private Scheduling createAndSaveScheduling(Client client, Barber barber, LocalDateTime dataInicio, LocalDateTime dataFim, List<Servico> servicos) {
        Scheduling saveScheduling = new Scheduling();
        saveScheduling.setClient(client);
        saveScheduling.setBarber(barber);
        saveScheduling.setDataInicio(dataInicio);
        saveScheduling.setDatafim(dataFim);
        saveScheduling.setServicos(servicos);
        return schedulingRepository.save(saveScheduling);
    }

    private List<Servico> getServicosByIds(List<Long> serviceIds, Barber barber) throws BarberDoesNotHaveService {
        List<Servico> servicos = servicoRepository.findByIdIn(serviceIds);

        if (servicos.isEmpty()) {
            throw new BarberDoesNotHaveService("Nenhum serviço foi selecionado para o barbeiro");
        }

        return servicos;
    }

    private Barber getBarberById(Long barberId) throws BarberNotExistsException {
        return barberRepository.findById(barberId).orElseThrow(BarberNotExistsException::new);
    }

    private Client getClientById(Long clientId) throws ClientNotExistsException {
        return clientRepository.findById(clientId).orElseThrow(ClientNotExistsException::new);
    }

    public Scheduling updateScheduling(Long schedulingId, SchedulingRequest schedulingRequest) throws SchedulingNotExistsException, ClientNotExistsException, BarberNotExistsException, BarberDoesNotHaveService, SchedulingConflictException {
        Scheduling existingScheduling = getSchedulingById(schedulingId);

        updateClientIfPresent(schedulingRequest, existingScheduling);
        updateBarberIfPresent(schedulingRequest, existingScheduling);
        updateSchedulingDatesIfPresent(schedulingRequest, existingScheduling);
        updateServicesIfPresent(schedulingRequest, existingScheduling);

        return schedulingRepository.save(existingScheduling);
    }

    private void updateClientIfPresent(SchedulingRequest schedulingRequest, Scheduling existingScheduling) throws ClientNotExistsException {
        if (schedulingRequest.getClientId() != null) {
            Client client = getClientById(schedulingRequest.getClientId());
            existingScheduling.setClient(client);
        }
    }

    private void updateBarberIfPresent(SchedulingRequest schedulingRequest, Scheduling existingScheduling) throws BarberNotExistsException {
        if (schedulingRequest.getBarberId() != null) {
            Barber barber = getBarberById(schedulingRequest.getBarberId());
            existingScheduling.setBarber(barber);
        }
    }

    private void updateSchedulingDatesIfPresent(SchedulingRequest schedulingRequest, Scheduling existingScheduling) throws SchedulingConflictException {
        if (schedulingRequest.getScheduling() != null) {
            LocalDateTime dataInicio = schedulingRequest.getScheduling().getDataInicio();
            LocalDateTime dataFim = schedulingRequest.getScheduling().getDatafim();
            checkSchedulingConflicts(dataInicio, dataFim, existingScheduling.getBarber());
            existingScheduling.setDataInicio(dataInicio);
            existingScheduling.setDatafim(dataFim);
        }
    }

    private void updateServicesIfPresent(SchedulingRequest schedulingRequest, Scheduling existingScheduling) throws BarberDoesNotHaveService {
        if (schedulingRequest.getServiceIds() != null) {
            List<Servico> servicos = getServicosByIds(schedulingRequest.getServiceIds(), existingScheduling.getBarber());
            checkServiceBelongsToBarber(servicos, existingScheduling.getBarber());
            existingScheduling.setServicos(servicos);
        }
    }

    private Scheduling getSchedulingById(Long schedulingId) throws SchedulingNotExistsException {
        return schedulingRepository.findById(schedulingId).orElseThrow(SchedulingNotExistsException::new);
    }

    public Scheduling schedulingDelete(Long id) throws SchedulingNotExistsException {
        Optional<Scheduling> schedulingOptional = schedulingRepository.findById(id);
        if (schedulingOptional.isPresent()) {
            Scheduling existingScheduling = schedulingOptional.get();
            schedulingRepository.deleteById(id);
            return existingScheduling;
        } else {
            throw new SchedulingNotExistsException();
        }
    }
}
