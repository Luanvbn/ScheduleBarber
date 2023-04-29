package br.com.schedulebarber.scheduleBarber.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.schedulebarber.scheduleBarber.model.Client;
import br.com.schedulebarber.scheduleBarber.service.ClientService;
import br.com.schedulebarber.scheduleBarber.util.PaginationParams;

import java.util.Optional;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping ("/name/{name}")
    public ResponseEntity<Client> findClientByName(@PathVariable("name") String name) {
        Client cliente = clientService.findClientByName(name);
        return ResponseEntity.ok(cliente);
    }

    @PostMapping ("/findAllClient")
    public ResponseEntity<Page<Client>> findAllClients(@RequestBody() PaginationParams params) {
        Page<Client> clients = clientService.findAllClients(params);
        return ResponseEntity.ok(clients);

    }
    @GetMapping("/id/{id}")
    public ResponseEntity<Optional<Client>> findById(@PathVariable("id") Long id){
        Optional<Client> cliente = clientService.findClientById(id);
        return ResponseEntity.ok(cliente);
    }


}
