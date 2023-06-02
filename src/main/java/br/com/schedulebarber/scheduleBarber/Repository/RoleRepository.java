package br.com.schedulebarber.scheduleBarber.Repository;

import br.com.schedulebarber.scheduleBarber.Model.Role;
import br.com.schedulebarber.scheduleBarber.Model.Scheduling;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByAuthority (String role);

    boolean existsByAuthority (String role);

    Page<Role> findAll(Pageable pageable);
}
