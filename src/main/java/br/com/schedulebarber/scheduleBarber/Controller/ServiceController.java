package br.com.schedulebarber.scheduleBarber.Controller;

import br.com.schedulebarber.scheduleBarber.Exception.ServicoNotExistsException;
import br.com.schedulebarber.scheduleBarber.Model.Servico;
import br.com.schedulebarber.scheduleBarber.Service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    private ServicoService servicoService;


    @GetMapping("/id/{id}")
    public ResponseEntity<Optional<Servico>> findById(@PathVariable("id") Long id) throws ServicoNotExistsException {
        Optional<Servico> servico = servicoService.findServicetById(id);
        return ResponseEntity.ok(servico);
    }
}
