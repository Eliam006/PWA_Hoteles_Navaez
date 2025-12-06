package utez.edu.mx.pwa_narvaez_hoteles.service;

import org.springframework.stereotype.Service;
import utez.edu.mx.pwa_narvaez_hoteles.repository.RecepcionistaTokenRepository;

@Service
public class RecepcionistaNotifyService {

    private final RecepcionistaTokenRepository tokenRepo;
    private final NotificationService notificationService;

    public RecepcionistaNotifyService(RecepcionistaTokenRepository tokenRepo,
                                      NotificationService notificationService) {
        this.tokenRepo = tokenRepo;
        this.notificationService = notificationService;
    }

    public void notificarATodos(String titulo, String cuerpo) {
        tokenRepo.findAll().forEach(t -> {
            notificationService.enviarNotificacion(t.getToken(), titulo, cuerpo);
        });
    }
}
