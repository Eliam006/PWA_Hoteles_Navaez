package utez.edu.mx.pwa_narvaez_hoteles.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.pwa_narvaez_hoteles.entity.Camarera;
import utez.edu.mx.pwa_narvaez_hoteles.repository.CamareraRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/camareras")
public class CamareraController {

    private final CamareraRepository camareraRepository;

    public CamareraController(CamareraRepository camareraRepository) {
        this.camareraRepository = camareraRepository;
    }

    // ================== LISTAR ==================
    @GetMapping
    public ResponseEntity<List<Camarera>> listar() {
        // Si quisieras ordenar por nombre:
        // return ResponseEntity.ok(camareraRepository.findAll(Sort.by("nombre")));
        return ResponseEntity.ok(camareraRepository.findAll());
    }

    // ================== CREAR ==================
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Camarera body) {

        // Validaciones básicas
        if (body.getNombre() == null || body.getNombre().isBlank()
                || body.getApellidoPaterno() == null || body.getApellidoPaterno().isBlank()
                || body.getUsuario() == null || body.getUsuario().isBlank()
                || body.getPassword() == null || body.getPassword().isBlank()) {

            return ResponseEntity
                    .badRequest()
                    .body("Nombre, apellido paterno, usuario y contraseña son obligatorios.");
        }


        if (camareraRepository.existsByUsuario(body.getUsuario())) {
             return ResponseEntity.status(409).body("Ya existe una camarera con ese usuario.");
         }

        Camarera c = new Camarera();
        c.setNombre(body.getNombre().trim());
        c.setApellidoPaterno(body.getApellidoPaterno() != null ? body.getApellidoPaterno().trim() : null);
        c.setApellidoMaterno(body.getApellidoMaterno() != null ? body.getApellidoMaterno().trim() : null);
        c.setTelefono(body.getTelefono() != null ? body.getTelefono().trim() : null);
        c.setUsuario(body.getUsuario().trim());
        c.setPassword(body.getPassword()); // para proyecto escolar, está bien plano

        Camarera guardada = camareraRepository.save(c);
        return ResponseEntity.ok(guardada);
    }

    // ================== ACTUALIZAR ==================
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id,
                                        @RequestBody Camarera body) {

        Optional<Camarera> opt = camareraRepository.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (body.getNombre() == null || body.getNombre().isBlank()
                || body.getApellidoPaterno() == null || body.getApellidoPaterno().isBlank()
                || body.getUsuario() == null || body.getUsuario().isBlank()
                || body.getPassword() == null || body.getPassword().isBlank()) {

            return ResponseEntity
                    .badRequest()
                    .body("Nombre, apellido paterno, usuario y contraseña son obligatorios.");
        }

        Camarera existente = opt.get();


        if (camareraRepository.existsByUsuarioAndIdNot(body.getUsuario(), id)) {
            return ResponseEntity.status(409).body("Ya existe otra camarera con ese usuario.");
         }

        existente.setNombre(body.getNombre().trim());
        existente.setApellidoPaterno(body.getApellidoPaterno() != null ? body.getApellidoPaterno().trim() : null);
        existente.setApellidoMaterno(body.getApellidoMaterno() != null ? body.getApellidoMaterno().trim() : null);
        existente.setTelefono(body.getTelefono() != null ? body.getTelefono().trim() : null);
        existente.setUsuario(body.getUsuario().trim());
        existente.setPassword(body.getPassword());

        Camarera actualizada = camareraRepository.save(existente);
        return ResponseEntity.ok(actualizada);
    }
}
