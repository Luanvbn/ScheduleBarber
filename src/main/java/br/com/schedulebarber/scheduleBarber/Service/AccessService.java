package br.com.schedulebarber.scheduleBarber.Service;

import br.com.schedulebarber.scheduleBarber.Exception.AccessAlreadyExistsException;
import br.com.schedulebarber.scheduleBarber.Exception.AccessNotExistsException;
import br.com.schedulebarber.scheduleBarber.Exception.BarberNotExistsException;
import br.com.schedulebarber.scheduleBarber.Model.Access;
import br.com.schedulebarber.scheduleBarber.Model.Barber;
import br.com.schedulebarber.scheduleBarber.Repository.AccessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccessService {

    @Autowired
    public AccessRepository accessRepository;

    public Access createAdmin(Access access) throws AccessAlreadyExistsException {
        if (accessRepository.existsByEmail(access.getEmail())) {
            throw new AccessAlreadyExistsException();
        } else {
            Access accessSaved = new Access();
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
}
