package br.com.schedulebarber.scheduleBarber.Controller;

import br.com.schedulebarber.scheduleBarber.Exception.AccessAlreadyExistsException;
import br.com.schedulebarber.scheduleBarber.Exception.BarberNotExistsException;
import br.com.schedulebarber.scheduleBarber.Model.Barber;
import br.com.schedulebarber.scheduleBarber.Service.BarberService;
import br.com.schedulebarber.scheduleBarber.Util.PaginationParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    @PostMapping("/findAllBarber")
    public ResponseEntity<Page<Barber>> findAllBarber(@RequestBody() PaginationParams params) {
        Page<Barber> barbers = barberService.findAllBarber(params);
        return ResponseEntity.ok(barbers);

    }
    @GetMapping("/id/{id}")
    public ResponseEntity<Optional<Barber>> findById(@PathVariable("id") Long id) throws BarberNotExistsException {
        Optional<Barber> barber = barberService.findClientById(id);
        return ResponseEntity.ok(barber);
    }
    @PostMapping("/save")
    public ResponseEntity<?> saveUser (@RequestBody Barber barber) throws AccessAlreadyExistsException {
        Barber savedBarber = barberService.SaveBarber(barber);
        return ResponseEntity.ok(savedBarber);
    }

    @PutMapping ("/update/{id}")
    public ResponseEntity<Barber> updateUser(@PathVariable Long id, @RequestBody Barber body) throws BarberNotExistsException {
        Barber barber = barberService.updateBarber(id, body);
        return ResponseEntity.ok().body(barber);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Barber> deleteUser (@PathVariable Long id) throws BarberNotExistsException {
        Barber barber = barberService.deleteBarber(id);
        return ResponseEntity.ok().body(barber);
    }

}
