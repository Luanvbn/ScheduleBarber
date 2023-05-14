package br.com.schedulebarber.scheduleBarber.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Scheduling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("access")
    private Client client;

    @ManyToOne
    @JsonIgnoreProperties({"servicos", "access"})
    private Barber barber;

    private LocalDateTime dataInicio;

    private LocalDateTime datafim;

    @ManyToMany
    @JoinTable(
            name = "schedulling_service",
            joinColumns = @JoinColumn(name = "scheduling_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    @JsonIgnoreProperties("schedules")
    private List<Servico> servicos;

    public Scheduling() {
    }

    public Scheduling(Long id, Client client, Barber barber, LocalDateTime dataInicio, LocalDateTime datafim, List<Servico> servicos) {
        this.id = id;
        this.client = client;
        this.barber = barber;
        this.dataInicio = dataInicio;
        this.datafim = datafim;
        this.servicos = servicos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Barber getBarber() {
        return barber;
    }

    public void setBarber(Barber barber) {
        this.barber = barber;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDatafim() {
        return datafim;
    }

    public void setDatafim(LocalDateTime datafim) {
        this.datafim = datafim;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos;
    }
}
