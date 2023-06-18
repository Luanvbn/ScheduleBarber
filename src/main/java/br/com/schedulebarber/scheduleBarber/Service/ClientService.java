package br.com.schedulebarber.scheduleBarber.Service;

import br.com.schedulebarber.scheduleBarber.Exception.AccessAlreadyExistsException;
import br.com.schedulebarber.scheduleBarber.Exception.ClientNotExistsException;
import br.com.schedulebarber.scheduleBarber.Model.Role;
import br.com.schedulebarber.scheduleBarber.Model.Scheduling;
import br.com.schedulebarber.scheduleBarber.Repository.AccessRepository;
import br.com.schedulebarber.scheduleBarber.Repository.ClientRepository;
import br.com.schedulebarber.scheduleBarber.Model.Client;
import br.com.schedulebarber.scheduleBarber.Repository.RoleRepository;
import br.com.schedulebarber.scheduleBarber.Repository.SchedulingRepository;
import br.com.schedulebarber.scheduleBarber.Util.BcryptUtils;
import br.com.schedulebarber.scheduleBarber.Util.PaginationParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

import static br.com.schedulebarber.scheduleBarber.Util.RemovedAcent.removerAcento;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccessRepository accessRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SchedulingRepository schedulingRepository;

    public Client findClientByName(String name) throws ClientNotExistsException {
        Client client = clientRepository.findByNameContainingIgnoreCase(name);
        if(client != null){
            return clientRepository.findByNameContainingIgnoreCase(name);
        } else {
            throw new ClientNotExistsException();
        }
    }

    public Page<Client> findAllClients(PaginationParams params) {
        Sort sort = params.getSortOrder().equalsIgnoreCase("asc") ?
                Sort.by(params.getSortProperty()).ascending() : Sort.by(params.getSortProperty()).descending();
        Pageable pageable = PageRequest.of(params.getPage(), params.getSize(), sort);
        Page<Client> clients = clientRepository.findAll((Pageable) pageable);
        return clients;
    }

    public Optional<Client> findClientById(Long id) throws ClientNotExistsException {
        Optional<Client> client = clientRepository.findById(id);
        if(client.isPresent()){
            return clientRepository.findById(id);
        } else {
            throw new ClientNotExistsException();
        }
    }


    public Client updateClient(Long id, Client client) throws ClientNotExistsException {
            Optional<Client> clientOptional = clientRepository.findById(id);
            Client existingClient = clientOptional.orElseThrow(ClientNotExistsException::new);
        if(clientOptional.isPresent()){

            if(accessRepository.existsByEmail(clientOptional.get().getAccess().getEmail())) {
                if(client.getName() != null) {
                    existingClient.setName(removerAcento(client.getName()));
                }
                if(client.getBirthday() != null ) {
                    existingClient.setBirthday(client.getBirthday());
                }
                if (client.getSex() != null) {
                    existingClient.setSex(client.getSex());
                }

                if(client.getAccess() != null) {
                    if (client.getAccess().getEmail() != null) {
                        existingClient.getAccess().setEmail(client.getAccess().getEmail());
                    }
                    if (client.getAccess().getPassword() != null) {
                        existingClient.getAccess().setPassword(BcryptUtils.encode(client.getAccess().getPassword()));
                    }
                }

                Client clienteSave = clientRepository.save(existingClient);
                return clientRepository.save(clienteSave);
            }
        } else {
            throw new ClientNotExistsException();
        }
        throw new ClientNotExistsException();
    }

    public Client deleteClient(Long id) throws ClientNotExistsException {
        Optional<Client> clienteOptional = clientRepository.findById(id);
        if(clienteOptional.isPresent()) {
            Client existingClient = clienteOptional.get();
            clientRepository.deleteById(id);
            return existingClient;
        } else {
            throw new ClientNotExistsException();
        }
    }

    public List<Scheduling> getAgendamentosDoCliente(Long clientId) {
        Client client = new Client();
        client.setId(clientId);

        return schedulingRepository.findByClient(client);
    }




}
