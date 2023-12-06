package br.com.schedulebarber.scheduleBarber.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Barber extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "barber")
    @JsonIgnoreProperties({"barber", "schedules"})
    private List<Servico> servicos;

    public Barber(Long id, String name, Sex sex, String phone, String address, LocalDate birthday, Access access, Long id1, List<Servico> servicos) {
        super(id, name, sex, phone, address, birthday, access);
        this.id = id1;
        this.servicos = servicos;
    }

    public Barber(Long id, List<Servico> servicos) {
        this.id = id;
        this.servicos = servicos;
    }

    public Barber() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos;
    }
}
