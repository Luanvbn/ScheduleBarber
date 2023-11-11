package br.com.schedulebarber.scheduleBarber.Controller;


import br.com.schedulebarber.scheduleBarber.DTO.AuthenticationRequest;
import br.com.schedulebarber.scheduleBarber.DTO.AuthenticationResponse;
import br.com.schedulebarber.scheduleBarber.DTO.RegisterRequest;
import br.com.schedulebarber.scheduleBarber.Exception.AccessAlreadyExistsException;
import br.com.schedulebarber.scheduleBarber.Exception.AccessNotExistsException;
import br.com.schedulebarber.scheduleBarber.Exception.AccessNotFoundException;
import br.com.schedulebarber.scheduleBarber.Model.Barber;
import br.com.schedulebarber.scheduleBarber.Model.Client;
import br.com.schedulebarber.scheduleBarber.Repository.AccessRepository;
import br.com.schedulebarber.scheduleBarber.Service.AuthenticationService;
import br.com.schedulebarber.scheduleBarber.Service.BarberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthenticationController {

    private final AuthenticationService service;

    @Autowired
    public AccessRepository accessRepository;

    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }


    @PostMapping("/registerBarber")
    public ResponseEntity<AuthenticationResponse> registerBarber(@RequestBody Barber barber) throws AccessAlreadyExistsException {
        return ResponseEntity.ok(service.registerBarber(barber));
    }

    @PostMapping("/registerClient")
    public ResponseEntity<AuthenticationResponse> registerClient(@RequestBody Client client) throws AccessAlreadyExistsException {
        return ResponseEntity.ok(service.registerClient(client));
    }


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authentication) {
        try {
            return ResponseEntity.ok(service.authenticate(authentication));
        } catch (AccessNotFoundException e) {
            return ResponseEntity.status(401).build(); // Unauthorized
        } catch (Exception e) {
            // Trate outras exceções aqui, se necessário
            throw new RuntimeException(e);
        }
    }
}
