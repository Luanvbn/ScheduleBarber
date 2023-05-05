package br.com.schedulebarber.scheduleBarber.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeServico;

    private Double valorServico;

    @ManyToOne
    private Barber barber;

    @ManyToMany(mappedBy = "servicos")
    private List<Scheduling> schedules;


    public Servico(Long id, String nomeServico, Double valorServico, List<Scheduling> schedules) {
        this.id = id;
        this.nomeServico = nomeServico;
        this.valorServico = valorServico;
        this.schedules = schedules;
    }

    public Servico() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeServico() {
        return nomeServico;
    }

    public void setNomeServico(String nomeServico) {
        this.nomeServico = nomeServico;
    }

    public Double getValorServico() {
        return valorServico;
    }

    public void setValorServico(Double valorServico) {
        this.valorServico = valorServico;
    }

    public List<Scheduling> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Scheduling> schedules) {
        this.schedules = schedules;
    }
}
