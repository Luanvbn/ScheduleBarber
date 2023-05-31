package br.com.schedulebarber.scheduleBarber.Security;

import br.com.schedulebarber.scheduleBarber.Exception.AccessNotExistsException;
import br.com.schedulebarber.scheduleBarber.Model.Access;
import br.com.schedulebarber.scheduleBarber.Repository.AccessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailsServiceImp implements UserDetailsService {


    @Autowired
    private AccessRepository accessRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Access access = accessRepository.findByEmail(email);
        if(Objects.isNull(access)){
            throw new UsernameNotFoundException("Email não encontrado: " + email);
        }
        return new UserSpringSecutiry(access);
    }
}
