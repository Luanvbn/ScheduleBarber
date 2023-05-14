package br.com.schedulebarber.scheduleBarber.Repository;

import br.com.schedulebarber.scheduleBarber.Model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {

    boolean existsByNomeServico(String nomeServico);

    List<Servico> findByIdAndBarberIdIn(Long id, Collection<Long> barber_id);

    List<Servico> findByIdIn(List<Long> serviceIds);
}
