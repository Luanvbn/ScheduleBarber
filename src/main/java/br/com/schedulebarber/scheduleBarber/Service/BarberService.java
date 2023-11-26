package br.com.schedulebarber.scheduleBarber.Service;

import br.com.schedulebarber.scheduleBarber.Exception.AccessAlreadyExistsException;
import br.com.schedulebarber.scheduleBarber.Exception.BarberNotExistsException;
import br.com.schedulebarber.scheduleBarber.Model.*;
import br.com.schedulebarber.scheduleBarber.Repository.AccessRepository;
import br.com.schedulebarber.scheduleBarber.Repository.BarberRepository;
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

import java.util.List;
import java.util.Optional;


import static br.com.schedulebarber.scheduleBarber.Util.RemovedAcent.removerAcento;

@Service
public class BarberService {

    @Autowired
    public BarberRepository barberRepository;

    @Autowired
    public AccessRepository accessRepository;

    @Autowired
    public RoleRepository roleRepository;

    @Autowired
    public SchedulingRepository schedulingRepository;

    public Barber findClientByName(String name) throws BarberNotExistsException {
        Barber barber = barberRepository.findByNameContainingIgnoreCase(name);
        if (barber != null) {
            return barber;
        } else {
            throw new BarberNotExistsException();
        }
    }

    public Page<Barber> findAllBarber(PaginationParams params) {
        Sort sort = Sort.by(params.getSortOrder().equalsIgnoreCase("asc") ?
                Sort.Order.asc(params.getSortProperty()) : Sort.Order.desc(params.getSortProperty()));
        Pageable pageable = PageRequest.of(params.getPage(), params.getSize(), sort);
        return barberRepository.findAll(pageable);
    }

    public Optional<Barber> findClientById(Long id) throws BarberNotExistsException {
        Optional<Barber> barberOptional = barberRepository.findById(id);
        if (barberOptional.isPresent()) {
            return barberOptional;
        } else {
            throw new BarberNotExistsException();
        }
    }

    public Barber updateBarber(Long id, Barber updatedBarber) throws BarberNotExistsException {
        Optional<Barber> barberOptional = barberRepository.findById(id);
        Barber existingBarber = barberOptional.orElseThrow(BarberNotExistsException::new);

        if (accessRepository.existsByEmail(existingBarber.getAccess().getEmail())) {
            updateBarberFields(existingBarber, updatedBarber);
            return barberRepository.save(existingBarber);
        } else {
            throw new BarberNotExistsException();
        }
    }


    private void updateBarberFields(Barber existingBarber, Barber updatedBarber) {
        existingBarber.setName(updatedBarber.getName() != null ? removerAcento(updatedBarber.getName()) : existingBarber.getName());
        existingBarber.setBirthday(updatedBarber.getBirthday() != null ? updatedBarber.getBirthday() : existingBarber.getBirthday());
        existingBarber.setSex(updatedBarber.getSex() != null ? updatedBarber.getSex() : existingBarber.getSex());
        existingBarber.setAddress(updatedBarber.getAddress() != null ? updatedBarber.getAddress() : existingBarber.getAddress());
        existingBarber.setPhone(updatedBarber.getPhone() != null ? updatedBarber.getPhone() : existingBarber.getPhone());
        existingBarber.setServicos(updatedBarber.getServicos() != null ? updatedBarber.getServicos() : existingBarber.getServicos());

        Access updatedAccess = updatedBarber.getAccess();
        Access existingAccess = existingBarber.getAccess();

        if (updatedAccess != null) {
            existingAccess.setEmail(updatedAccess.getEmail() != null ? updatedAccess.getEmail() : existingAccess.getEmail());
            existingAccess.setPassword(updatedAccess.getPassword() != null ? BcryptUtils.encode(updatedAccess.getPassword()) : existingAccess.getPassword());
        }
    }

    public Barber deleteBarber(Long id) throws BarberNotExistsException {
        Optional<Barber> barberOptional = barberRepository.findById(id);
        if (barberOptional.isPresent()) {
            Barber existingBarber = barberOptional.get();
            barberRepository.deleteById(id);
            return existingBarber;
        } else {
            throw new BarberNotExistsException();
        }
    }

    public List<Scheduling> getAgendamentosDoBarbeiro(Long barberId) {
        Barber barber = new Barber();
        barber.setId(barberId);

        return schedulingRepository.findByBarber(barber);
    }


}
