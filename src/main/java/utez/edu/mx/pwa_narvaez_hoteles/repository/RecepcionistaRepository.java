package utez.edu.mx.pwa_narvaez_hoteles.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.pwa_narvaez_hoteles.entity.Recepcionista;

import java.util.Optional;

public interface RecepcionistaRepository extends JpaRepository<Recepcionista, Long> {

    Optional<Recepcionista> findByUsuarioAndPassword(String usuario, String password);

}