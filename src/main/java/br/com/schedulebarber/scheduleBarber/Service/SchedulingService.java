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
        Client client = clientRepository.findById(schedulingRequest.getClientId()).orElseThrow(ClientNotExistsException::new);
        Barber barber = barberRepository.findById(schedulingRequest.getBarberId()).orElseThrow(BarberNotExistsException::new);
        List<Servico> servicos = servicoRepository.findByIdIn(schedulingRequest.getServiceIds());

        boolean hasService = !servicos.isEmpty();
        for (Servico servico : servicos) {
            if (!servico.getBarber().equals(barber)) {
                throw new BarberDoesNotHaveService("Serviço " + servico.getNomeServico() + "não pertence ao barbeiro selecionado");
            }
        }

        if (hasService) {
            LocalDateTime DataInicio = schedulingRequest.getScheduling().getDataInicio();
            LocalDateTime DataFim = schedulingRequest.getScheduling().getDatafim();

            List<Scheduling> existingSchedulings = schedulingRepository.findByBarber(barber);
            for (Scheduling existingScheduling : existingSchedulings) {
                LocalDateTime existingDataInicio = existingScheduling.getDataInicio();
                LocalDateTime existingDataFim = existingScheduling.getDatafim();
                if (DataInicio.isBefore(existingDataFim) && DataFim.isAfter(existingDataInicio)) {
                    throw new SchedulingConflictException("Já existe um agendamento no mesmo período");
                }
            }


            Scheduling saveScheduling = new Scheduling();
            saveScheduling.setClient(client);
            saveScheduling.setBarber(barber);
            saveScheduling.setDataInicio(schedulingRequest.getScheduling().getDataInicio());
            saveScheduling.setDatafim(schedulingRequest.getScheduling().getDatafim());
            saveScheduling.setServicos(servicos);

            return schedulingRepository.save(saveScheduling);
        } else {
            throw new BarberDoesNotHaveService("Algum(s) dos serviços não pertence ao barbeiro selecionado");
        }
    }
}
