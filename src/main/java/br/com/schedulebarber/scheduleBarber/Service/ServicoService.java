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
            servicoSaved.setNomeServico(servico.getNomeServico());
            servicoSaved.setValorServico(servico.getValorServico());
            servicoSaved.setBarber(barber);
            return servicoRepository.save(servicoSaved);
        } else {
            throw new ServicoAlreadyExistsException();
        }
    }
}
