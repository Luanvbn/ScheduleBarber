package br.com.schedulebarber.scheduleBarber.Service;

import br.com.schedulebarber.scheduleBarber.Exception.AccessAlreadyExistsException;
import br.com.schedulebarber.scheduleBarber.Exception.ClientNotExistsException;
import br.com.schedulebarber.scheduleBarber.Model.Access;
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
        if (client != null) {
            return client;
        } else {
            throw new ClientNotExistsException();
        }
    }

    public Page<Client> findAllClients(PaginationParams params) {
        Sort sort = Sort.by(params.getSortOrder().equalsIgnoreCase("asc") ?
                Sort.Order.asc(params.getSortProperty()) : Sort.Order.desc(params.getSortProperty()));
        Pageable pageable = PageRequest.of(params.getPage(), params.getSize(), sort);
        return clientRepository.findAll(pageable);
    }

    public Optional<Client> findClientById(Long id) throws ClientNotExistsException {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (clientOptional.isPresent()) {
            return clientOptional;
        } else {
            throw new ClientNotExistsException();
        }
    }

    public Client updateClient(Long id, Client updatedClient) throws ClientNotExistsException {
        Optional<Client> clientOptional = clientRepository.findById(id);
        Client existingClient = clientOptional.orElseThrow(ClientNotExistsException::new);

        if (accessRepository.existsByEmail(existingClient.getAccess().getEmail())) {
            updateClientFields(existingClient, updatedClient);
            return clientRepository.save(existingClient);
        } else {
            throw new ClientNotExistsException();
        }
    }

    private void updateClientFields(Client existingClient, Client updatedClient) {
        existingClient.setName(updatedClient.getName() != null ? removerAcento(updatedClient.getName()) : existingClient.getName());
        existingClient.setBirthday(updatedClient.getBirthday() != null ? updatedClient.getBirthday() : existingClient.getBirthday());
        existingClient.setSex(updatedClient.getSex() != null ? updatedClient.getSex() : existingClient.getSex());

        Access updatedAccess = updatedClient.getAccess();
        Access existingAccess = existingClient.getAccess();

        if (updatedAccess != null) {
            existingAccess.setEmail(updatedAccess.getEmail() != null ? updatedAccess.getEmail() : existingAccess.getEmail());
            existingAccess.setPassword(updatedAccess.getPassword() != null ? BcryptUtils.encode(updatedAccess.getPassword()) : existingAccess.getPassword());
        }
    }

    public Client deleteClient(Long id) throws ClientNotExistsException {
        Optional<Client> clienteOptional = clientRepository.findById(id);
        if (clienteOptional.isPresent()) {
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
