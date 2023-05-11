package br.com.schedulebarber.scheduleBarber.Service;

import br.com.schedulebarber.scheduleBarber.Exception.AccessAlreadyExistsException;
import br.com.schedulebarber.scheduleBarber.Exception.BarberNotExistsException;
import br.com.schedulebarber.scheduleBarber.Model.Barber;
import br.com.schedulebarber.scheduleBarber.Repository.AccessRepository;
import br.com.schedulebarber.scheduleBarber.Repository.BarberRepository;
import br.com.schedulebarber.scheduleBarber.Util.PaginationParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BarberService {

    @Autowired
    public BarberRepository barberRepository;

    @Autowired
    public AccessRepository accessRepository;

    public Barber findClientByName(String name) throws BarberNotExistsException {
        Barber barber = barberRepository.findByName(name);
        if(barber != null){
            return barberRepository.findByName(name);
        } else {
            throw new BarberNotExistsException();
        }
    }

    public Page<Barber> findAllBarber(PaginationParams params) {
        Sort sort = params.getSortOrder().equalsIgnoreCase("asc") ?
                Sort.by(params.getSortProperty()).ascending() : Sort.by(params.getSortProperty()).descending();
        Pageable pageable = PageRequest.of(params.getPage(), params.getSize(), sort);
        Page<Barber> barbers = barberRepository.findAll((Pageable) pageable);
        return barbers;
    }

    public Optional<Barber> findClientById(Long id) throws BarberNotExistsException {
        Optional<Barber> barberOptional = barberRepository.findById(id);
        if(barberOptional.isPresent()){
            return barberRepository.findById(id);
        } else {
            throw new BarberNotExistsException();
        }
    }

    public Barber SaveBarber(Barber barber) throws AccessAlreadyExistsException {
        if (accessRepository.existsByEmail(barber.getAccess().getEmail())) {
            throw new AccessAlreadyExistsException();
        } else {
            Barber roleBarber = barber;
            roleBarber.getAccess().setRole("BARBER");
            Barber savedBarber = barberRepository.save(roleBarber);
            return savedBarber;
        }
    }



    public Barber updateBarber(Long id, Barber barber) throws BarberNotExistsException {
        Optional<Barber> barberOptional = barberRepository.findById(id);
        Barber existingBarber = barberOptional.orElseThrow(BarberNotExistsException::new);
        if(barberOptional.isPresent()){

            if(accessRepository.existsByEmail(barberOptional.get().getAccess().getEmail())) {
                if(barber.getName() != null) {
                    existingBarber.setName(barber.getName());
                }
                if(barber.getBirthday() != null ) {
                    existingBarber.setBirthday(barber.getBirthday());
                }
                if (barber.getSex() != null) {
                    existingBarber.setSex(barber.getSex());
                }
                if (barber.getAddress() != null) {
                    existingBarber.setAddress(barber.getAddress());
                }
                if (barber.getPhone() != null) {
                    existingBarber.setPhone(barber.getPhone());
                }

                if(barber.getServicos() != null) {
                    existingBarber.setServicos(barber.getServicos());
                }
                 if(barber.getAccess() != null) {
                    if (barber.getAccess().getEmail() != null) {
                        existingBarber.getAccess().setEmail(barber.getAccess().getEmail());
                    }
                    if (barber.getAccess().getPassword() != null) {
                        existingBarber.getAccess().setPassword(barber.getAccess().getPassword());
                    }
                }

                Barber barberSave = barberRepository.save(existingBarber);
                return barberRepository.save(barberSave);
            }
        } else {
            throw new BarberNotExistsException();
        }
        throw new BarberNotExistsException();
    }

    public Barber deleteBarber(Long id) throws BarberNotExistsException {
        Optional<Barber> barberOptional = barberRepository.findById(id);
        if(barberOptional.isPresent()) {
            Barber existingBarber = barberOptional.get();
            barberRepository.deleteById(id);
            return existingBarber;
        } else {
            throw new BarberNotExistsException();
        }
    }


}
