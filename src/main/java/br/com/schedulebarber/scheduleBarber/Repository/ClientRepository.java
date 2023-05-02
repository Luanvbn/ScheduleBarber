package br.com.schedulebarber.scheduleBarber.Repository;

import br.com.schedulebarber.scheduleBarber.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findByName(String name);
    Page<Client> findAll(Pageable pageable);

    void deleteById(Long id);
}
