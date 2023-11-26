package br.com.schedulebarber.scheduleBarber.Service;

import br.com.schedulebarber.scheduleBarber.Exception.ServicoAlreadyExistsException;
import br.com.schedulebarber.scheduleBarber.Exception.ServicoNotExistsException;
import br.com.schedulebarber.scheduleBarber.Model.Barber;
import br.com.schedulebarber.scheduleBarber.Model.Servico;
import br.com.schedulebarber.scheduleBarber.Repository.BarberRepository;
import br.com.schedulebarber.scheduleBarber.Repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static br.com.schedulebarber.scheduleBarber.Util.RemovedAcent.removerAcento;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private BarberRepository barberRepository;

    public Optional<Servico> findServicetById(Long id) throws ServicoNotExistsException {
        Optional<Servico> servicoOptional = servicoRepository.findById(id);
        if (servicoOptional.isPresent()) {
            return servicoRepository.findById(id);
        } else {
            throw new ServicoNotExistsException();
        }
    }

    public Servico createServico(Long id, Servico servico) throws ServicoAlreadyExistsException {
        Barber barber = barberRepository.findById(id).orElseThrow(ServicoAlreadyExistsException::new);

        barber.getServicos().stream()
                .filter(existingServico -> existingServico.getNomeServico().equals(servico.getNomeServico()))
                .findAny()
                .ifPresent(existing -> {
                    try {
                        throw new ServicoAlreadyExistsException();
                    } catch (ServicoAlreadyExistsException e) {
                        throw new RuntimeException(e);
                    }
                });

        Servico servicoSaved = new Servico();
        servicoSaved.setNomeServico(removerAcento(servico.getNomeServico()));
        servicoSaved.setValorServico(servico.getValorServico());
        servicoSaved.setBarber(barber);
        return servicoRepository.save(servicoSaved);
    }

    public Servico updateServico(Long id, Servico servico) throws ServicoNotExistsException {
        Servico existingServico = servicoRepository.findById(id).orElseThrow(ServicoNotExistsException::new);

        if (servico.getNomeServico() != null) {
            existingServico.setNomeServico(removerAcento(servico.getNomeServico()));
        }
        if (servico.getValorServico() != null) {
            existingServico.setValorServico(servico.getValorServico());
        }
        if (servico.getBarber() != null) {
            existingServico.setBarber(servico.getBarber());
        }
        if (servico.getSchedules() != null) {
            existingServico.setSchedules(servico.getSchedules());
        }

        return servicoRepository.save(existingServico);
    }

    public Servico deleteServico(Long id) throws ServicoNotExistsException {
        return servicoRepository.findById(id)
                .map(servico -> {
                    servicoRepository.deleteById(id);
                    return servico;
                })
                .orElseThrow(ServicoNotExistsException::new);
    }
}
