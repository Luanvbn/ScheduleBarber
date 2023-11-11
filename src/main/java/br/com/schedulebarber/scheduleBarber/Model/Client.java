package br.com.schedulebarber.scheduleBarber.Model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Client extends Person  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "access_id", referencedColumnName = "id")
    private Access access;

    public Client(Long id, String name, Sex sex, String phone, String address, LocalDate birthday, Long id1, Access access) {
        super(id, name, sex, phone, address, birthday);
        this.id = id1;
        this.access = access;
    }

    public Client(Long id, Access access) {
        this.id = id;
        this.access = access;
    }

    public Client() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Access getAccess() {
        return access;
    }

    public void setAccess(Access access) {
        this.access = access;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", access=" + access +
                '}';
    }
}
