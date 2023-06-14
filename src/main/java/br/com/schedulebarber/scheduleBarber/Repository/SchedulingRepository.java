package br.com.schedulebarber.scheduleBarber.Repository;

import br.com.schedulebarber.scheduleBarber.Model.Barber;
import br.com.schedulebarber.scheduleBarber.Model.Client;
import br.com.schedulebarber.scheduleBarber.Model.Scheduling;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchedulingRepository extends JpaRepository<Scheduling, Long> {

    List<Scheduling> findByBarber(Barber barber);

    Page<Scheduling> findAll(Pageable pageable);

    List<Scheduling> findByClient(Client client);

    List<Scheduling> findByBarber(Client client);
}
