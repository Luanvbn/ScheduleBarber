package br.com.schedulebarber.scheduleBarber.service;

import br.com.schedulebarber.scheduleBarber.Exception.AccessAlreadyExistsException;
import br.com.schedulebarber.scheduleBarber.Repository.AccessRepository;
import br.com.schedulebarber.scheduleBarber.Repository.ClientRepository;
import br.com.schedulebarber.scheduleBarber.model.Client;
import br.com.schedulebarber.scheduleBarber.util.PaginationParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccessRepository accessRepository;

    public Client findClientByName(String name) {
        return clientRepository.findByName(name);
    }

    public Page<Client> findAllClients(PaginationParams params){
        Sort sort = params.getSortOrder().equalsIgnoreCase("asc") ?
                Sort.by(params.getSortProperty()).ascending() : Sort.by(params.getSortProperty()).descending();
        Pageable pageable = PageRequest.of(params.getPage(), params.getSize(), sort);
        Page<Client> clients = clientRepository.findAll((Pageable) pageable);
        return clients;
    }

    public Optional<Client> findClientById(Long id){
        return clientRepository.findById(id);
    }

    public Client SaveClient(Client client) throws AccessAlreadyExistsException{
        if (accessRepository.existsByEmail(client.getAccess().getEmail())) {
            throw new AccessAlreadyExistsException(("O Email já existe!"));

        } else {
            Client savedClient = clientRepository.save(client);
            return savedClient;
        }
    }



}
