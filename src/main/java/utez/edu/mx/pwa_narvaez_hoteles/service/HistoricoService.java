package utez.edu.mx.pwa_narvaez_hoteles.service;

import org.springframework.stereotype.Service;
import utez.edu.mx.pwa_narvaez_hoteles.entity.Historico;
import utez.edu.mx.pwa_narvaez_hoteles.repository.HistoricoRepository;

import java.time.LocalDateTime;

@Service
public class HistoricoService {

    private final HistoricoRepository historicoRepository;

    public HistoricoService(HistoricoRepository historicoRepository) {
        this.historicoRepository = historicoRepository;
    }

    public void registrar(String nombreHab, String rol, String movimiento) {
        Historico h = new Historico();
        h.setNombreHabitacion(nombreHab);
        h.setRol(rol);
        h.setMovimiento(movimiento);
        h.setFecha(LocalDateTime.now().toString());

        historicoRepository.save(h);
    }
}
