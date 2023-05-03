package br.com.schedulebarber.scheduleBarber.Service;

import br.com.schedulebarber.scheduleBarber.Model.Barber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.schedulebarber.scheduleBarber.Repository.BarberRepository;

@Service
public class BarberService {

    @Autowired
    public BarberRepository barberRepository;

    public Barber findClientByName(String name) {
        return barberRepository.findByName(name);
    }
}
