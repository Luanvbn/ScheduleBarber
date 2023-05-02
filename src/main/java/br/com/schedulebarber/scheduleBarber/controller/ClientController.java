package br.com.schedulebarber.scheduleBarber.controller;


import br.com.schedulebarber.scheduleBarber.Exception.AccessAlreadyExistsException;
import br.com.schedulebarber.scheduleBarber.Exception.AccessNotExistsException;
import br.com.schedulebarber.scheduleBarber.Exception.ClientNotExistsException;
import br.com.schedulebarber.scheduleBarber.model.Client;
import br.com.schedulebarber.scheduleBarber.service.ClientService;
import br.com.schedulebarber.scheduleBarber.util.PaginationParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/save")
    public ResponseEntity<?> saveUser (@RequestBody Client client) {
        try {
            Client savedClient = clientService.SaveClient(client);
            return ResponseEntity.ok(savedClient);
        } catch (AccessAlreadyExistsException e) {
            return ResponseEntity.badRequest().body("O Email já existe!");
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao salvar o cliente: " + e.getMessage());
        }
    }

    @PutMapping ("/update/{id}")
    public ResponseEntity<Client> updateUser(@PathVariable Long id, @RequestBody Client cliente) throws AccessNotExistsException {
        Client client = clientService.updateClient(id, cliente);
        return ResponseEntity.ok().body(client);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Client> deleteUser (@PathVariable Long id) throws ClientNotExistsException {
        Client client = clientService.deleteClient(id);
        return ResponseEntity.ok().body(client);
    }




}
