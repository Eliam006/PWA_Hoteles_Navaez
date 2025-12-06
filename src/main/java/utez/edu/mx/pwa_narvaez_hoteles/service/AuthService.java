package utez.edu.mx.pwa_narvaez_hoteles.service;

import org.springframework.stereotype.Service;
import utez.edu.mx.pwa_narvaez_hoteles.entity.Camarera;
import utez.edu.mx.pwa_narvaez_hoteles.entity.Recepcionista;
import utez.edu.mx.pwa_narvaez_hoteles.repository.CamareraRepository;
import utez.edu.mx.pwa_narvaez_hoteles.repository.RecepcionistaRepository;

import java.util.Map;

@Service
public class AuthService {

    private final RecepcionistaRepository recepRepo;
    private final CamareraRepository camRepo;

    public AuthService(RecepcionistaRepository recepRepo, CamareraRepository camRepo) {
        this.recepRepo = recepRepo;
        this.camRepo = camRepo;
    }

    public Object login(String usuario, String password) {

        // Buscar primero en recepcionistas
        Recepcionista r = recepRepo.findByUsuarioAndPassword(usuario, password)
                .orElse(null);

        if (r != null) {
            return Map.of(
                    "tipo", "recepcionista",
                    "data", r
            );
        }

        // Buscar camarera
        Camarera c = camRepo.findByUsuarioAndPassword(usuario, password)
                .orElse(null);

        if (c != null) {
            return Map.of(
                    "tipo", "camarera",
                    "data", c
            );
        }

        return null;
    }
}
