package br.com.schedulebarber.scheduleBarber.Controller;

import br.com.schedulebarber.scheduleBarber.Exception.BarberNotExistsException;
import br.com.schedulebarber.scheduleBarber.Model.Barber;
import br.com.schedulebarber.scheduleBarber.Service.BarberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/barber")
public class BarberController {

    @Autowired
    private BarberService barberService;

    @GetMapping("/name/{name}")
    public ResponseEntity<Barber> findClientByName(@PathVariable("name") String name) throws BarberNotExistsException {
        Barber barber = barberService.findClientByName(name);
        return ResponseEntity.ok(barber);
    }
}
