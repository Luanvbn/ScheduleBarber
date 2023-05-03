package br.com.schedulebarber.scheduleBarber.Repository;

import br.com.schedulebarber.scheduleBarber.Model.Barber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarberRepository extends JpaRepository<Barber, Long> {

    Barber findByName(String name);
}
