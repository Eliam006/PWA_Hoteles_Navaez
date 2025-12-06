package utez.edu.mx.pwa_narvaez_hoteles.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.pwa_narvaez_hoteles.entity.Habitacion;
import utez.edu.mx.pwa_narvaez_hoteles.service.HabitacionService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/habitaciones")
public class HabitacionController {

    private final HabitacionService habitacionService;

    public HabitacionController(HabitacionService habitacionService) {
        this.habitacionService = habitacionService;
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Map<String, String> body) {
        String nombre = body.get("nombre");

        if (nombre == null || nombre.length() != 4) {
            return ResponseEntity.badRequest().body("El nombre debe tener 4 caracteres.");
        }

        Habitacion h = habitacionService.crearHabitacion(nombre.toUpperCase());

        if (h == null) {
            return ResponseEntity.status(409).body("Ya existe una habitación con ese nombre.");
        }

        return ResponseEntity.ok(h);
    }


    @GetMapping
    public ResponseEntity<List<Habitacion>> listar() {
        return ResponseEntity.ok(habitacionService.listarHabitaciones());
    }

    @PostMapping("/{id}/asignar-camareras")
    public ResponseEntity<?> asignar(@PathVariable Long id, @RequestBody Map<String, List<Long>> body) {
        List<Long> ids = body.get("idsCamareras");
        if (ids == null || ids.isEmpty()) {
            return ResponseEntity.badRequest().body("Debes enviar al menos una camarera.");
        }

        Habitacion h = habitacionService.asignarCamareras(id, ids);
        if (h == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(h);
    }

    @PutMapping("/{id}/nombre")
    public ResponseEntity<?> actualizarNombre(
            @PathVariable Long id,
            @RequestBody Map<String, String> body
    ) {
        String nuevoNombre = body.get("nombre");
        if (nuevoNombre == null || nuevoNombre.length() != 4) {
            return ResponseEntity.badRequest().body("El nombre debe tener 4 caracteres.");
        }

        Habitacion actualizada = habitacionService.actualizarNombre(id, nuevoNombre.toUpperCase());
        if (actualizada == null) {
            return ResponseEntity.status(409).body("Ya existe una habitación con ese nombre.");
        }

        return ResponseEntity.ok(actualizada);
    }

    @PutMapping("/{id}/disponibilidad")
    public ResponseEntity<?> actualizarDisponibilidad(
            @PathVariable Long id,
            @RequestBody Map<String, String> body
    ) {
        String nuevaDisp = body.get("disponibilidad");
        Habitacion h = habitacionService.actualizarDisponibilidad(id, nuevaDisp);

        return h != null ? ResponseEntity.ok(h) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> actualizarStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {

        String nuevoStatus = body.get("status");
        Long camareraId = Long.parseLong(body.get("camareraId")); // <-- NUEVO

        Habitacion h = habitacionService.actualizarStatus(id, nuevoStatus, camareraId);

        if (h == null) {
            return ResponseEntity.badRequest().body("No se pudo actualizar el estado");
        }

        return ResponseEntity.ok(h);
    }


}
