package utez.edu.mx.pwa_narvaez_hoteles.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utez.edu.mx.pwa_narvaez_hoteles.entity.Habitacion;

import java.util.Optional;

public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
    boolean existsByNombre(String nombre);
    Optional<Habitacion> findByNombre(String nombre);

}
