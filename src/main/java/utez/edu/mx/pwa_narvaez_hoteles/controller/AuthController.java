package utez.edu.mx.pwa_narvaez_hoteles.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import utez.edu.mx.pwa_narvaez_hoteles.entity.Recepcionista;
import utez.edu.mx.pwa_narvaez_hoteles.service.AuthService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {

        String usuario = body.get("usuario");
        String password = body.get("password");

        Object result = service.login(usuario, password);

        if (result == null) {
            return ResponseEntity.status(401).body("Credenciales incorrectas");
        }

        return ResponseEntity.ok(result);
    }
}
