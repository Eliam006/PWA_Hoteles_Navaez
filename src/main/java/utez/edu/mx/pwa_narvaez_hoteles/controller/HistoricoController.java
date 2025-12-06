package utez.edu.mx.pwa_narvaez_hoteles.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utez.edu.mx.pwa_narvaez_hoteles.entity.Historico;
import utez.edu.mx.pwa_narvaez_hoteles.repository.HistoricoRepository;

import java.util.List;

@RestController
@RequestMapping("/api/historico")
public class HistoricoController {

    private final HistoricoRepository historicoRepository;

    public HistoricoController(HistoricoRepository historicoRepository) {
        this.historicoRepository = historicoRepository;
    }

    // 🔥 GET usado por el frontend
    @GetMapping
    public ResponseEntity<List<Historico>> obtenerHistorial() {
        List<Historico> lista = historicoRepository.findAllByOrderByFechaDesc();
        return ResponseEntity.ok(lista);
    }
}
