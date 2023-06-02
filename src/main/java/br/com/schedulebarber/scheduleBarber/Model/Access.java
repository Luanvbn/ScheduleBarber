package br.com.schedulebarber.scheduleBarber.Model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Access {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="Access_Role",
            joinColumns = {@JoinColumn(name="id")},
            inverseJoinColumns = {@JoinColumn(name="roleId")}
    )
    private Set<Role> authorities;

    public Access(Long id, String email, String password, Set<Role> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public Access() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long access_id) {
        this.id = access_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return "Access{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", authorities=" + authorities +
                '}';
    }
}
