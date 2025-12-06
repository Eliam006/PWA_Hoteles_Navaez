package utez.edu.mx.pwa_narvaez_hoteles.service;

import org.springframework.stereotype.Service;
import utez.edu.mx.pwa_narvaez_hoteles.entity.Camarera;
import utez.edu.mx.pwa_narvaez_hoteles.entity.Habitacion;
import utez.edu.mx.pwa_narvaez_hoteles.entity.Incidencia;
import utez.edu.mx.pwa_narvaez_hoteles.repository.CamareraRepository;
import utez.edu.mx.pwa_narvaez_hoteles.repository.HabitacionRepository;
import utez.edu.mx.pwa_narvaez_hoteles.repository.IncidenciaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class IncidenciaService {

    private final IncidenciaRepository incidenciaRepository;
    private final HabitacionRepository habitacionRepository;
    private final CamareraRepository camareraRepository;
    private final RecepcionistaNotifyService notifyService;

    public IncidenciaService(
            IncidenciaRepository incidenciaRepository,
            HabitacionRepository habitacionRepository,
            CamareraRepository camareraRepository,
            RecepcionistaNotifyService notifyService
    ) {
        this.incidenciaRepository = incidenciaRepository;
        this.habitacionRepository = habitacionRepository;
        this.camareraRepository = camareraRepository;
        this.notifyService = notifyService;
    }

    public Incidencia registrarIncidencia(Long habitacionId,
                                          Long camareraId,
                                          String descripcion,
                                          List<String> fotos) {

        Habitacion hab = habitacionRepository.findById(habitacionId).orElse(null);
        Camarera cam = camareraRepository.findById(camareraId).orElse(null);

        if (hab == null || cam == null) return null;

        Incidencia inc = new Incidencia();
        inc.setDescripcion(descripcion);
        inc.setFecha(java.time.LocalDateTime.now().toString());
        inc.setFotos(fotos);
        inc.setHabitacion(hab);
        inc.setCamarera(cam);

        // Guardar incidencia
        incidenciaRepository.save(inc);

        // Cambiar el estado y notificar
        hab.setStatus("Incidente");
        hab.setDisponibilidad("No disponible");
        habitacionRepository.save(hab);

        notifyService.notificarATodos(
                "Incidencia en habitación " + hab.getNombre(),
                "La camarera " + cam.getNombre() + " reportó una incidencia."
        );

        return inc;
    }

    public List<Incidencia> obtenerTodas() {
        return incidenciaRepository.findAll();
    }

}
