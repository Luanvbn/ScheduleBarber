package br.com.schedulebarber.scheduleBarber.Controller;


import br.com.schedulebarber.scheduleBarber.Exception.AccessAlreadyExistsException;
import br.com.schedulebarber.scheduleBarber.Exception.AccessNotExistsException;
import br.com.schedulebarber.scheduleBarber.Exception.BarberNotExistsException;
import br.com.schedulebarber.scheduleBarber.Model.Access;
import br.com.schedulebarber.scheduleBarber.Model.Barber;
import br.com.schedulebarber.scheduleBarber.Service.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/access")
@CrossOrigin
public class AccessController {

    @Autowired
    private AccessService accessService;

    @GetMapping("/id/{id}")
    public ResponseEntity<?>  findById(@PathVariable("id") Long id) throws AccessNotExistsException {
        Optional<Access> access = accessService.findAccessById(id);
        return ResponseEntity.ok(access);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?>  updateAccess(@PathVariable Long id, @RequestBody Access body) throws AccessNotExistsException {
        Access access = accessService.updateAccess(id, body);
        return ResponseEntity.ok().body(access);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?>  deleteAccess(@PathVariable Long id) throws AccessNotExistsException {
        Access access = accessService.deleteAccess(id);
        return ResponseEntity.ok().body(access);
    }
}
