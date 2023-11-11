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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "access_id", referencedColumnName = "id")
    private Access access;

    public Barber(Long id, String name, Sex sex, String phone, String address, LocalDate birthday, Long id1, List<Servico> servicos, Access access) {
        super(id, name, sex, phone, address, birthday);
        this.id = id1;
        this.servicos = servicos;
        this.access = access;
    }

    public Barber(Long id, List<Servico> servicos, Access access) {
        this.id = id;
        this.servicos = servicos;
        this.access = access;
    }

    public Barber() {

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

    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos;
    }
}
