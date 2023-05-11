package br.com.schedulebarber.scheduleBarber.Controller;

import br.com.schedulebarber.scheduleBarber.Exception.ServicoAlreadyExistsException;
import br.com.schedulebarber.scheduleBarber.Exception.ServicoNotExistsException;
import br.com.schedulebarber.scheduleBarber.Model.Servico;
import br.com.schedulebarber.scheduleBarber.Service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/save/{id}")
    public ResponseEntity<?> createService(@PathVariable("id") Long id, @RequestBody Servico servico) throws ServicoAlreadyExistsException {
        Servico servicoSaved = servicoService.createServico(id, servico);
        return ResponseEntity.ok(servicoSaved);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateService(@PathVariable("id") Long id, @RequestBody Servico servico) throws ServicoNotExistsException {
        Servico servicoSaved = servicoService.updateServico(id, servico);
        return ResponseEntity.ok(servicoSaved);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Servico> deleteService (@PathVariable Long id) throws ServicoNotExistsException {
        Servico servico = servicoService.deleteServico(id);
        return ResponseEntity.ok().body(servico);
    }


}
