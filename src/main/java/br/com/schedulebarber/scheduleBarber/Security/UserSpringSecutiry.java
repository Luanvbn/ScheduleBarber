package br.com.schedulebarber.scheduleBarber.Security;

import br.com.schedulebarber.scheduleBarber.Model.Access;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class UserSpringSecutiry implements UserDetails {

    private Long id;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserSpringSecutiry(Access access) {
        this.id = access.getId();
        this.email = access.getEmail();
        this.password = access.getPassword();
        this.authorities = List.of(new CustomAuthority(access.getRole()));
    }

    public UserSpringSecutiry() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean hasRole(Access access) {
        return getAuthorities().contains(new CustomAuthority(access.getRole()));
    }
}
