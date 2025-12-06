package utez.edu.mx.pwa_narvaez_hoteles.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.pwa_narvaez_hoteles.entity.RecepcionistaToken;
import java.util.List;
import java.util.Optional;

public interface RecepcionistaTokenRepository extends JpaRepository<RecepcionistaToken, Long> {
    List<RecepcionistaToken> findByRecepcionistaId(Long recepcionistaId);
    Optional<RecepcionistaToken> findByToken(String token);

    void deleteByToken(String token);
}
