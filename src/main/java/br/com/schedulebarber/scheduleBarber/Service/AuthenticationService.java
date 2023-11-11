package br.com.schedulebarber.scheduleBarber.Service;

import br.com.schedulebarber.scheduleBarber.DTO.AuthenticationRequest;
import br.com.schedulebarber.scheduleBarber.DTO.AuthenticationResponse;
import br.com.schedulebarber.scheduleBarber.DTO.RegisterRequest;
import br.com.schedulebarber.scheduleBarber.Exception.AccessAlreadyExistsException;
import br.com.schedulebarber.scheduleBarber.Exception.AccessNotExistsException;
import br.com.schedulebarber.scheduleBarber.Exception.AccessNotFoundException;
import br.com.schedulebarber.scheduleBarber.Model.Access;
import br.com.schedulebarber.scheduleBarber.Model.Barber;
import br.com.schedulebarber.scheduleBarber.Model.Client;
import br.com.schedulebarber.scheduleBarber.Model.Role;
import br.com.schedulebarber.scheduleBarber.Repository.AccessRepository;
import br.com.schedulebarber.scheduleBarber.Repository.BarberRepository;
import br.com.schedulebarber.scheduleBarber.Repository.ClientRepository;
import br.com.schedulebarber.scheduleBarber.Repository.RoleRepository;
import br.com.schedulebarber.scheduleBarber.Security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

import static br.com.schedulebarber.scheduleBarber.Util.RemovedAcent.removerAcento;

@Service
public class AuthenticationService {

    @Autowired
    public AccessRepository accessRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    public RoleRepository roleRepository;

    @Autowired
    public JwtService jwtService;

    @Autowired
    public AuthenticationManager authenticationManager;

    @Autowired
    public BarberRepository barberRepository;

    @Autowired
    public ClientRepository clientRepository;

    public final String ROLE_CLIENT = "CLIENT";
    public final String ROLE_BARBER = "BARBER";

    private void validateEmailNotExist(String email) throws AccessAlreadyExistsException {
        if (accessRepository.existsByEmail(email)) {
            throw new AccessAlreadyExistsException();
        }
    }

    private AuthenticationResponse createAuthenticationResponse(Access access) {
        var jwtToken = jwtService.generateToken(access);
        return new AuthenticationResponse(jwtToken);
    }

    private Access createAccess(String email, String password, String roleAuthority) {
        Access access = new Access();
        access.setEmail(email);
        access.setPassword(passwordEncoder.encode(password));
        Role role = roleRepository.findByAuthority(roleAuthority);
        access.setAuthorities(Set.of(role));
        return access;
    }

    public AuthenticationResponse register(RegisterRequest request) throws AccessAlreadyExistsException {
        validateEmailNotExist(request.getEmail());

        Access access = createAccess(request.getEmail(), request.getPassword(), ROLE_CLIENT);
        accessRepository.save(access);

        return createAuthenticationResponse(access);
    }

    public AuthenticationResponse registerBarber(Barber barber) throws AccessAlreadyExistsException {
        validateEmailNotExist(barber.getAccess().getEmail());

        Access access = createAccess(barber.getAccess().getEmail(), barber.getAccess().getPassword(), ROLE_BARBER);
        barber.setAccess(access);
        barber.setName(removerAcento(barber.getName()));
        barberRepository.save(barber);

        return createAuthenticationResponse(access);
    }

    public AuthenticationResponse registerClient(Client client) throws AccessAlreadyExistsException {
        validateEmailNotExist(client.getAccess().getEmail());

        Access access = createAccess(client.getAccess().getEmail(), client.getAccess().getPassword(), ROLE_CLIENT);
        client.setAccess(access);
        client.setName(removerAcento(client.getName()));
        clientRepository.save(client);

        return createAuthenticationResponse(access);
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) throws AccessNotExistsException, AccessNotFoundException {
        if (!accessRepository.existsByEmail(request.getEmail())) {
            throw new AccessNotExistsException();
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        Access access = accessRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(access);

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setToken(jwtToken);

        return authenticationResponse;
    }
}
