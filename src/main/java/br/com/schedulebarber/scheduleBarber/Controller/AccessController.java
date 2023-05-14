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
public class AccessController {

    @Autowired
    private AccessService accessService;

    @GetMapping("/id/{id}")
    public ResponseEntity<Optional<Access>> findById(@PathVariable("id") Long id) throws AccessNotExistsException {
        Optional<Access> access = accessService.findAccessById(id);
        return ResponseEntity.ok(access);
    }

    @PostMapping("/createAccess")
    public ResponseEntity<?> saveAccess(@RequestBody Access access) throws AccessAlreadyExistsException {
        Access savedAccess = accessService.createAdmin(access);
        return ResponseEntity.ok(savedAccess);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Access> updateAccess(@PathVariable Long id, @RequestBody Access body) throws AccessNotExistsException {
        Access access = accessService.updateAccess(id, body);
        return ResponseEntity.ok().body(access);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Access> deleteAccess(@PathVariable Long id) throws AccessNotExistsException {
        Access access = accessService.deleteAccess(id);
        return ResponseEntity.ok().body(access);
    }
}
