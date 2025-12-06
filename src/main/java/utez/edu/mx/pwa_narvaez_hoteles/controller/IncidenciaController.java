package utez.edu.mx.pwa_narvaez_hoteles.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.pwa_narvaez_hoteles.entity.Incidencia;
import utez.edu.mx.pwa_narvaez_hoteles.service.IncidenciaService;


import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/incidencias")
public class IncidenciaController {

    private final IncidenciaService incidenciaService;

    public IncidenciaController(IncidenciaService incidenciaService) {
        this.incidenciaService = incidenciaService;
    }

    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody Map<String, Object> body) {

        Long habitacionId = Long.valueOf(body.get("habitacionId").toString());
        Long camareraId = Long.valueOf(body.get("camareraId").toString());
        String descripcion = body.get("descripcion").toString();

        List<String> fotos = (List<String>) body.get("fotos");

        Incidencia inc = incidenciaService.registrarIncidencia(
                habitacionId,
                camareraId,
                descripcion,
                fotos
        );

        return inc == null ?
                ResponseEntity.badRequest().body("No se pudo registrar la incidencia") :
                ResponseEntity.ok(inc);
    }

    @GetMapping
    public ResponseEntity<?> obtenerTodas() {
        List<Incidencia> lista = incidenciaService.obtenerTodas();
        return ResponseEntity.ok(lista);
    }

}
