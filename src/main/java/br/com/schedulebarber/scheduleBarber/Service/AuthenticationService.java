package br.com.schedulebarber.scheduleBarber.Service;

import br.com.schedulebarber.scheduleBarber.DTO.AuthenticationRequest;
import br.com.schedulebarber.scheduleBarber.DTO.AuthenticationResponse;
import br.com.schedulebarber.scheduleBarber.DTO.RegisterRequest;
import br.com.schedulebarber.scheduleBarber.Exception.AccessAlreadyExistsException;
import br.com.schedulebarber.scheduleBarber.Model.Access;
import br.com.schedulebarber.scheduleBarber.Model.Role;
import br.com.schedulebarber.scheduleBarber.Repository.AccessRepository;
import br.com.schedulebarber.scheduleBarber.Repository.RoleRepository;
import br.com.schedulebarber.scheduleBarber.Security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

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


    public AuthenticationResponse register(RegisterRequest request) throws AccessAlreadyExistsException {
        if (accessRepository.existsByEmail(request.getEmail())) {
            throw new AccessAlreadyExistsException();
        } else {
            Access access = new Access();
            access.setEmail(request.getEmail());
            access.setPassword(passwordEncoder.encode(request.getPassword()));
            Role role = roleRepository.findByAuthority("CLIENT");
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            access.setAuthorities(roles);
            accessRepository.save(access);

            var jwtToken = jwtService.generateToken(access);

            AuthenticationResponse authenticationResponse = new AuthenticationResponse();
            authenticationResponse.setToken(jwtToken);

            return authenticationResponse;
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
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
