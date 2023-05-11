package br.com.schedulebarber.scheduleBarber.Repository;

import br.com.schedulebarber.scheduleBarber.Model.Barber;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarberRepository extends JpaRepository<Barber, Long> {

    Barber findByName(String name);

    boolean existsByServicos(String nomeServico);

    Page<Barber> findAll(Pageable pageable);
}
