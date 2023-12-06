package br.com.schedulebarber.scheduleBarber.Model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Client extends Person  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    public Client(Long id, String name, Sex sex, String phone, String address, LocalDate birthday, Long id1, Access access) {
        super(id, name, sex, phone, address, birthday, access);
        this.id = id1;
    }

    public Client(Long id) {
        this.id = id;
    }

    public Client() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                '}';
    }
}
