package br.com.schedulebarber.scheduleBarber.Service;

import br.com.schedulebarber.scheduleBarber.Exception.AccessNotExistsException;
import br.com.schedulebarber.scheduleBarber.Model.Access;
import br.com.schedulebarber.scheduleBarber.Repository.AccessRepository;
import br.com.schedulebarber.scheduleBarber.Repository.RoleRepository;
import br.com.schedulebarber.scheduleBarber.Util.BcryptUtils;
import br.com.schedulebarber.scheduleBarber.Util.RemovedAcent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static br.com.schedulebarber.scheduleBarber.Util.RemovedAcent.removerAcento;

@Service
public class AccessService {

    @Autowired
    public AccessRepository accessRepository;

    @Autowired
    public RoleRepository roleRepository;


    public Optional<Access> findAccessById(Long id) {
        return accessRepository.findById(id);
    }

    public Access updateAccess(Long id, Access updatedAccess) throws AccessNotExistsException {
        Access existingAccess = accessRepository.findById(id)
                .orElseThrow(AccessNotExistsException::new);

        updateAccessFields(existingAccess, updatedAccess);

        return accessRepository.save(existingAccess);
    }

    public Access deleteAccess(Long id) throws AccessNotExistsException {
        Access existingAccess = accessRepository.findById(id)
                .orElseThrow(AccessNotExistsException::new);

        accessRepository.deleteById(id);
        return existingAccess;
    }


    private void updateAccessFields(Access existingAccess, Access updatedAccess) {
        if (updatedAccess.getEmail() != null) {
            existingAccess.setEmail(removerAcento(updatedAccess.getEmail()));
        }

        if (updatedAccess.getPassword() != null) {
            existingAccess.setPassword(BcryptUtils.encode(updatedAccess.getPassword()));
        }
    }


}
