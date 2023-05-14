package br.com.schedulebarber.scheduleBarber.Service;

import br.com.schedulebarber.scheduleBarber.Exception.AccessAlreadyExistsException;
import br.com.schedulebarber.scheduleBarber.Exception.AccessNotExistsException;
import br.com.schedulebarber.scheduleBarber.Exception.BarberNotExistsException;
import br.com.schedulebarber.scheduleBarber.Exception.ClientNotExistsException;
import br.com.schedulebarber.scheduleBarber.Model.Access;
import br.com.schedulebarber.scheduleBarber.Model.Barber;
import br.com.schedulebarber.scheduleBarber.Model.Client;
import br.com.schedulebarber.scheduleBarber.Repository.AccessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static br.com.schedulebarber.scheduleBarber.Util.RemovedAcent.removerAcento;

@Service
public class AccessService {

    @Autowired
    public AccessRepository accessRepository;

    public Access createAdmin(Access access) throws AccessAlreadyExistsException {
        if (accessRepository.existsByEmail(access.getEmail())) {
            throw new AccessAlreadyExistsException();
        } else {
            Access accessSaved = access;
            accessSaved.setRole("ADMIN");
            return accessRepository.save(accessSaved);
        }
    }

    public Optional<Access> findAccessById(Long id) throws AccessNotExistsException {
        Optional<Access> accessOptional = accessRepository.findById(id);
        if (accessOptional.isPresent()) {
            return accessRepository.findById(id);
        } else {
            throw new AccessNotExistsException();
        }
    }

    public Access updateAccess(Long id, Access access) throws AccessNotExistsException {
        Optional<Access> accessOptional = accessRepository.findById(id);
        Access existingAccess = accessOptional.orElseThrow(AccessNotExistsException::new);

        if (access.getEmail() != null) {
            existingAccess.setEmail(removerAcento(access.getEmail()));
        }
        if (access.getPassword() != null) {
            existingAccess.setPassword(access.getPassword());
        }

        Access accessSave = accessRepository.save(existingAccess);
        return accessRepository.save(accessSave);
    }

    public Access deleteAccess (Long id) throws AccessNotExistsException {
        Optional<Access> accessOptional = accessRepository.findById(id);
        if(accessOptional.isPresent()) {
            Access existingAccess = accessOptional.get();
            accessRepository.deleteById(id);
            return existingAccess;
        } else {
            throw new AccessNotExistsException();
        }
    }


}