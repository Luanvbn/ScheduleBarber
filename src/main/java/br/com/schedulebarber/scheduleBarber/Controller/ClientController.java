package br.com.schedulebarber.scheduleBarber.Controller;


import br.com.schedulebarber.scheduleBarber.Exception.AccessAlreadyExistsException;
import br.com.schedulebarber.scheduleBarber.Exception.ClientNotExistsException;
import br.com.schedulebarber.scheduleBarber.Model.Client;
import br.com.schedulebarber.scheduleBarber.Model.Scheduling;
import br.com.schedulebarber.scheduleBarber.Service.ClientService;
import br.com.schedulebarber.scheduleBarber.Util.PaginationParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/client")
@CrossOrigin
public class ClientController implements BaseController<Client>{

    @Autowired
    private ClientService clientService;

    @GetMapping ("/name/{name}")
    public ResponseEntity<?>  findByName(@PathVariable("name") String name) throws ClientNotExistsException {
        Client cliente = clientService.findClientByName(name);
        return ResponseEntity.ok(cliente);
    }

    @PostMapping ("/findAllClient")
    public ResponseEntity<?>  findAll(@RequestBody() PaginationParams params) {
        Page<Client> clients = clientService.findAllClients(params);
        return ResponseEntity.ok(clients);

    }
    @GetMapping("/id/{id}")
    public ResponseEntity<?>  findById(@PathVariable("id") Long id) throws ClientNotExistsException {
        Optional<Client> cliente = clientService.findClientById(id);
        return ResponseEntity.ok(cliente);
    }
    @PutMapping ("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Client cliente) throws ClientNotExistsException {
        Client client = clientService.updateClient(id, cliente);
        return ResponseEntity.ok().body(client);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?>  delete (@PathVariable Long id) throws ClientNotExistsException {
        Client client = clientService.deleteClient(id);
        return ResponseEntity.ok().body(client);
    }

    @GetMapping("/getscheduling/{id}")
    public ResponseEntity<?> getAgendamento(@PathVariable Long id) {
        List<Scheduling> scheduling = clientService.getAgendamentosDoCliente(id);
        return ResponseEntity.ok().body(scheduling);
    }






}
