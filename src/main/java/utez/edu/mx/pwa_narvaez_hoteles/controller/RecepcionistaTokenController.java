package utez.edu.mx.pwa_narvaez_hoteles.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.pwa_narvaez_hoteles.entity.RecepcionistaToken;
import utez.edu.mx.pwa_narvaez_hoteles.repository.RecepcionistaTokenRepository;

import java.util.Map;

@RestController
@RequestMapping("/api/tokens")
public class RecepcionistaTokenController {

    private final RecepcionistaTokenRepository repo;

    public RecepcionistaTokenController(RecepcionistaTokenRepository repo) {
        this.repo = repo;
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveToken(@RequestBody Map<String, String> body) {

        Long recepId = Long.parseLong(body.get("recepcionistaId"));
        String token = body.get("token");

        System.out.println("📥 Token recibido desde frontend:");
        System.out.println("   Recepcionista ID = " + recepId);
        System.out.println("   Token = " + token);

        // Evitar duplicados
        repo.findByToken(token).ifPresentOrElse(
                existing -> System.out.println("⚠️ Token ya existía, no se inserta de nuevo."),
                () -> {
                    repo.save(new RecepcionistaToken(recepId, token));
                    System.out.println("✔️ Token guardado en base de datos.");
                }
        );

        return ResponseEntity.ok("Token guardado");
    }
}
