package br.com.schedulebarber.scheduleBarber.Service;

import br.com.schedulebarber.scheduleBarber.Exception.AccessAlreadyExistsException;
import br.com.schedulebarber.scheduleBarber.Exception.BarberNotExistsException;
import br.com.schedulebarber.scheduleBarber.Model.Barber;
import br.com.schedulebarber.scheduleBarber.Model.Client;
import br.com.schedulebarber.scheduleBarber.Model.Role;
import br.com.schedulebarber.scheduleBarber.Model.Scheduling;
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

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
            return barberRepository.findByNameContainingIgnoreCase(name);
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
        if (barberOptional.isPresent()) {
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
            Role role = roleRepository.findByAuthority("BARBER");
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            roleBarber.getAccess().setAuthorities(roles);
            roleBarber.getAccess().setPassword(BcryptUtils.encode(barber.getAccess().getPassword()));
            roleBarber.setName(removerAcento(barber.getName()));
            Barber savedBarber = barberRepository.save(roleBarber);
            return savedBarber;
        }
    }


    public Barber updateBarber(Long id, Barber barber) throws BarberNotExistsException {
        Optional<Barber> barberOptional = barberRepository.findById(id);
        Barber existingBarber = barberOptional.orElseThrow(BarberNotExistsException::new);
        if (barberOptional.isPresent()) {

            if (accessRepository.existsByEmail(barberOptional.get().getAccess().getEmail())) {
                if (barber.getName() != null) {
                    existingBarber.setName(removerAcento(barber.getName()));
                }
                if (barber.getBirthday() != null) {
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

                if (barber.getServicos() != null) {
                    existingBarber.setServicos(barber.getServicos());
                }
                if (barber.getAccess() != null) {
                    if (barber.getAccess().getEmail() != null) {
                        existingBarber.getAccess().setEmail(barber.getAccess().getEmail());
                    }
                    if (barber.getAccess().getPassword() != null) {
                        existingBarber.getAccess().setPassword(BcryptUtils.encode(barber.getAccess().getPassword()));
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
