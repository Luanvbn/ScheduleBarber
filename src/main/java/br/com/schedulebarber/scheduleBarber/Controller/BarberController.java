package br.com.schedulebarber.scheduleBarber.Controller;

import br.com.schedulebarber.scheduleBarber.Exception.AccessAlreadyExistsException;
import br.com.schedulebarber.scheduleBarber.Exception.BarberNotExistsException;
import br.com.schedulebarber.scheduleBarber.Model.Barber;
import br.com.schedulebarber.scheduleBarber.Model.Scheduling;
import br.com.schedulebarber.scheduleBarber.Service.BarberService;
import br.com.schedulebarber.scheduleBarber.Util.PaginationParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/barber")
public class BarberController {

    @Autowired
    private BarberService barberService;

    @GetMapping("/name/{name}")
    public ResponseEntity<?> findClientByName(@PathVariable("name") String name) throws BarberNotExistsException {
        Barber barber = barberService.findClientByName(name);
        return ResponseEntity.ok(barber);
    }

    @PostMapping("/findAllBarber")
    public ResponseEntity<?> findAllBarber(@RequestBody() PaginationParams params) {
        Page<Barber> barbers = barberService.findAllBarber(params);
        return ResponseEntity.ok(barbers);

    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) throws BarberNotExistsException {
        Optional<Barber> barber = barberService.findClientById(id);
        return ResponseEntity.ok(barber);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody Barber body) throws BarberNotExistsException {
        Barber barber = barberService.updateBarber(id, body);
        return ResponseEntity.ok().body(barber);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) throws BarberNotExistsException {
        Barber barber = barberService.deleteBarber(id);
        return ResponseEntity.ok().body(barber);
    }

    @GetMapping("/getscheduling/{id}")
    public ResponseEntity<?> getAgendamentoBarber(@PathVariable Long id) {
        List<Scheduling> scheduling = barberService.getAgendamentosDoBarbeiro(id);
        return ResponseEntity.ok().body(scheduling);
    }

}
