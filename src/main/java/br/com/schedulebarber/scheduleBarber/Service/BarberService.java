package br.com.schedulebarber.scheduleBarber.Service;

import br.com.schedulebarber.scheduleBarber.Exception.BarberNotExistsException;
import br.com.schedulebarber.scheduleBarber.Model.Barber;
import br.com.schedulebarber.scheduleBarber.Repository.AccessRepository;
import br.com.schedulebarber.scheduleBarber.Repository.BarberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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



}
