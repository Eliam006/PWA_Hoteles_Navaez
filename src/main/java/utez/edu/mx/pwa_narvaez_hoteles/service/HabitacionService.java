package utez.edu.mx.pwa_narvaez_hoteles.service;

import org.springframework.stereotype.Service;
import utez.edu.mx.pwa_narvaez_hoteles.entity.Camarera;
import utez.edu.mx.pwa_narvaez_hoteles.entity.Habitacion;
import utez.edu.mx.pwa_narvaez_hoteles.repository.CamareraRepository;
import utez.edu.mx.pwa_narvaez_hoteles.repository.HabitacionRepository;
import utez.edu.mx.pwa_narvaez_hoteles.repository.HistoricoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HabitacionService {

    private final HabitacionRepository habitacionRepository;
    private final CamareraRepository camareraRepository;
    private final HistoricoService historicoService;
    private final RecepcionistaNotifyService notifyService;

    public HabitacionService(HabitacionRepository habitacionRepository,
                             CamareraRepository camareraRepository,
                             RecepcionistaNotifyService notifyService,
                             HistoricoService historicoService) {
        this.habitacionRepository = habitacionRepository;
        this.camareraRepository = camareraRepository;
        this.notifyService = notifyService;
        this.historicoService = historicoService;
    }

    // ==========================================================
    // CREAR HABITACIÓN
    // ==========================================================
    public Habitacion crearHabitacion(String nombre) {
        if (habitacionRepository.existsByNombre(nombre)) {
            return null;
        }

        Habitacion h = new Habitacion();
        h.setNombre(nombre);
        h.setDisponibilidad("Disponible");
        h.setStatus("En Revisión");
        return habitacionRepository.save(h);
    }

    // ==========================================================
    // ACTUALIZAR NOMBRE
    // ==========================================================
    public Habitacion actualizarNombre(Long id, String nuevoNombre) {
        if (habitacionRepository.findByNombre(nuevoNombre).isPresent()) {
            return null;
        }

        return habitacionRepository.findById(id).map(h -> {
            h.setNombre(nuevoNombre);
            return habitacionRepository.save(h);
        }).orElse(null);
    }

    // ==========================================================
    // ACTUALIZAR DISPONIBILIDAD
    // ==========================================================
    public Habitacion actualizarDisponibilidad(Long id, String nuevaDisp) {

        return habitacionRepository.findById(id).map(h -> {

            h.setDisponibilidad(nuevaDisp);

            // 🔥 Nuevas reglas de negocio
            if ("Disponible".equalsIgnoreCase(nuevaDisp)) {
                // Recepción habilita → Limpia
                h.setStatus("Limpia");

                // Registrar en histórico
                historicoService.registrar(
                        h.getNombre(),
                        "Recepción",
                        "Habilitar"
                );
            }
            else if ("No disponible".equalsIgnoreCase(nuevaDisp)) {
                // Recepción deshabilita → En Revisión
                h.setStatus("En Revisión");

                // Registrar en histórico
                historicoService.registrar(
                        h.getNombre(),
                        "Recepción",
                        "Deshabilitar"
                );
            }

            return habitacionRepository.save(h);
        }).orElse(null);
    }


    // ==========================================================
    // LISTAR HABITACIONES
    // ==========================================================
    public List<Habitacion> listarHabitaciones() {
        return habitacionRepository.findAll();
    }

    // ==========================================================
    // ASIGNAR CAMARERAS (max 4)
    // ==========================================================
    public Habitacion asignarCamareras(Long habitacionId, List<Long> idsCamareras) { Optional<Habitacion> optHab = habitacionRepository.findById(habitacionId); if (optHab.isEmpty()) { return null; } Habitacion hab = optHab.get(); if (idsCamareras.size() > 4) { idsCamareras = idsCamareras.subList(0, 4); } List<Camarera> camareras = new ArrayList<>(); for (Long id : idsCamareras) { camareraRepository.findById(id).ifPresent(camareras::add); } hab.setCamareras(camareras); return habitacionRepository.save(hab); }

    // ==========================================================
    // ACTUALIZAR STATUS + NOTIFICACIÓN
    // ==========================================================
    public Habitacion actualizarStatus(Long habitacionId, String status, Long camareraId) {

        Optional<Habitacion> optHab = habitacionRepository.findById(habitacionId);
        if (optHab.isEmpty()) {
            return null;
        }

        Habitacion hab = optHab.get();
        hab.setStatus(status);

        // Reglas de disponibilidad
        if ("Incidente".equalsIgnoreCase(status)) {
            hab.setDisponibilidad("No disponible");
        } else if (!"Limpia".equalsIgnoreCase(status)) {
            hab.setDisponibilidad("No disponible");
        }

        habitacionRepository.save(hab);

        // CAMARERA QUE HIZO EL CAMBIO
        Camarera camarera = camareraRepository.findById(camareraId).orElse(null);

        if (camarera != null) {

            // 🔥 REGISTRAR EN HISTORICO 🔥
            String movimiento = status.equalsIgnoreCase("Limpia")
                    ? "Marcar Limpia"
                    : "Marcar Incidencia";

            historicoService.registrar(
                    hab.getNombre(),
                    "Camarera",
                    movimiento
            );

            // 🔔 Notificación a recepción
            String titulo = "Actualización de habitación";
            String cuerpo =
                    "La camarera " + camarera.getNombre() +
                            " marcó la habitación " + hab.getNombre() +
                            " como " + status;

            notifyService.notificarATodos(titulo, cuerpo);
        }

        return hab;
    }


}
