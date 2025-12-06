package utez.edu.mx.pwa_narvaez_hoteles.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.pwa_narvaez_hoteles.entity.Camarera;

import java.util.Optional;

public interface CamareraRepository extends JpaRepository<Camarera, Long> {
    boolean existsByUsuario(String usuario);
    boolean existsByUsuarioAndIdNot(String usuario, Long id);
    Optional<Camarera> findByUsuarioAndPassword(String usuario, String password);
}
