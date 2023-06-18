package br.com.schedulebarber.scheduleBarber.Controller;


import br.com.schedulebarber.scheduleBarber.DTO.AuthenticationRequest;
import br.com.schedulebarber.scheduleBarber.DTO.AuthenticationResponse;
import br.com.schedulebarber.scheduleBarber.DTO.RegisterRequest;
import br.com.schedulebarber.scheduleBarber.Exception.AccessAlreadyExistsException;
import br.com.schedulebarber.scheduleBarber.Model.Barber;
import br.com.schedulebarber.scheduleBarber.Model.Client;
import br.com.schedulebarber.scheduleBarber.Service.AuthenticationService;
import br.com.schedulebarber.scheduleBarber.Service.BarberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService service;

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
    public ResponseEntity<AuthenticationResponse> authentication(@RequestBody AuthenticationRequest authentication) {
        return ResponseEntity.ok(service.authenticate(authentication));
    }

}
