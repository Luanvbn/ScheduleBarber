package br.com.schedulebarber.scheduleBarber.service;

import br.com.schedulebarber.scheduleBarber.Repository.ClientRepository;
import br.com.schedulebarber.scheduleBarber.model.Client;
import br.com.schedulebarber.scheduleBarber.util.PaginationParams;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Client findClientByName(String name) {
        JSONObject object = new JSONObject(name);
        Client client = clientRepository.findByName((String) object.get("name"));
        return client;
    }

    public Page<Client> findAllClients(PaginationParams params){
        Sort sort = params.getSortOrder().equalsIgnoreCase("asc") ?
                Sort.by(params.getSortProperty()).ascending() : Sort.by(params.getSortProperty()).descending();
        Pageable pageable = PageRequest.of(params.getPage(), params.getSize(), sort);
        Page<Client> clients = clientRepository.findAll((Pageable) pageable);
        return clients;
    }



}
