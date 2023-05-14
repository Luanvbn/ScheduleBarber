package br.com.schedulebarber.scheduleBarber.Util;

import br.com.schedulebarber.scheduleBarber.Model.Scheduling;

import java.util.List;

public class SchedulingRequest {

    private Long barberId;
    private Long clientId;
    private List<Long> serviceIds;
    private Scheduling scheduling;

    public SchedulingRequest(Long barberId, Long clientId, List<Long> serviceIds, Scheduling scheduling) {
        this.barberId = barberId;
        this.clientId = clientId;
        this.serviceIds = serviceIds;
        this.scheduling = scheduling;
    }

    public SchedulingRequest() {
    }

    public Long getBarberId() {
        return barberId;
    }

    public void setBarberId(Long barberId) {
        this.barberId = barberId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public List<Long> getServiceIds() {
        return serviceIds;
    }

    public void setServiceIds(List<Long> serviceIds) {
        this.serviceIds = serviceIds;
    }

    public Scheduling getScheduling() {
        return scheduling;
    }

    public void setScheduling(Scheduling scheduling) {
        this.scheduling = scheduling;
    }
}
