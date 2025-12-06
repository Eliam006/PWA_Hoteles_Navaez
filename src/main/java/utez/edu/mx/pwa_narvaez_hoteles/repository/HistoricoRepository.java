package utez.edu.mx.pwa_narvaez_hoteles.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.pwa_narvaez_hoteles.entity.Historico;

public interface HistoricoRepository extends JpaRepository<Historico, Long> {
    List<Historico> findAllByOrderByFechaDesc();
}
