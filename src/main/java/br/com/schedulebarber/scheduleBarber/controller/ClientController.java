package br.com.schedulebarber.scheduleBarber.controller;


import br.com.schedulebarber.scheduleBarber.model.Client;
import br.com.schedulebarber.scheduleBarber.service.ClientService;
import br.com.schedulebarber.scheduleBarber.util.PaginationParams;
import com.fasterxml.jackson.annotation.JsonAlias;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping ("/findClientByname")
    public ResponseEntity<Client> findClientByName(@RequestBody String name) {
        Client cliente = clientService.findClientByName(name);
        return ResponseEntity.ok(cliente);
    }

    @PostMapping ("/findAllClient")
    public ResponseEntity<Page<Client>> findAllClients(@RequestBody() PaginationParams params) {
        Page<Client> clients = clientService.findAllClients(params);
        return ResponseEntity.ok(clients);

    }


}
