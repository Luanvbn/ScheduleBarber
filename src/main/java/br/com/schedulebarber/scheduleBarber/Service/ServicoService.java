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
        Optional<Barber> optionalBarber = barberRepository.findById(id);
        if (optionalBarber.isPresent()) {
            Barber barber = optionalBarber.get();
            for (Servico existingServico : barber.getServicos()) {
                if (existingServico.getNomeServico().equals(servico.getNomeServico())) {
                    throw new ServicoAlreadyExistsException();
                }
            }
            Servico servicoSaved = new Servico();
            servicoSaved.setNomeServico(removerAcento(servico.getNomeServico()));
            servicoSaved.setValorServico(servico.getValorServico());
            servicoSaved.setBarber(barber);
            return servicoRepository.save(servicoSaved);
        } else {
            throw new ServicoAlreadyExistsException();
        }
    }

    public Servico updateServico(Long id, Servico servico) throws ServicoNotExistsException {
        Optional<Servico> servicoOptional = servicoRepository.findById(id);
        Servico existingServico = servicoOptional.orElseThrow(ServicoNotExistsException::new);

        if (servicoRepository.existsByNomeServico(servicoOptional.get().getNomeServico())) {
            if (servico.getNomeServico() != null) {
                existingServico.setNomeServico(removerAcento(servico.getNomeServico()));
            }
            if (servico.getValorServico() != null) {
                existingServico.setValorServico(servico.getValorServico());
            }
            if (servico.getBarber() != null) {
                existingServico.setBarber(servico.getBarber());
            }
            if(servico.getSchedules() != null) {
                existingServico.setSchedules(servico.getSchedules());
            }

            Servico servicoSave = servicoRepository.save(existingServico);
            return servicoRepository.save(servicoSave);
        } else {
            throw new ServicoNotExistsException();
        }
    }

    public Servico deleteServico(Long id) throws ServicoNotExistsException {
        Optional<Servico> servicoOptional = servicoRepository.findById(id);
        if(servicoOptional.isPresent()) {
            Servico servicoExisting = servicoOptional.get();
            servicoRepository.deleteById(id);
            return servicoExisting;
        } else {
            throw new ServicoNotExistsException();
        }
    }
}
