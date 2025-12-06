package utez.edu.mx.pwa_narvaez_hoteles.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MessagingErrorCode;
import org.springframework.stereotype.Service;
import utez.edu.mx.pwa_narvaez_hoteles.repository.RecepcionistaTokenRepository;

@Service
public class NotificationService {

    private final RecepcionistaTokenRepository recepcionistaTokenRepository;

    public NotificationService(RecepcionistaTokenRepository recepcionistaTokenRepository) {
        this.recepcionistaTokenRepository = recepcionistaTokenRepository;
    }

    public void enviarNotificacion(String token, String titulo, String cuerpo) {
        try {
            System.out.println("🔥 Enviando a token: " + token);

            Message msg = Message.builder()
                    .setToken(token)
                    .putData("title", titulo)
                    .putData("body", cuerpo)
                    .build();

            String response = FirebaseMessaging.getInstance().send(msg);
            System.out.println("✔️ Enviado correctamente: " + response);

        } catch (FirebaseMessagingException e) {
            System.out.println("❌ Error enviando a token: " + token);
            System.out.println("Código: " + e.getMessagingErrorCode());
            System.out.println("Mensaje: " + e.getMessage());

            // 🔥🔥🔥 TOKEN INVALIDO → BORRARLO
            if (e.getMessagingErrorCode() == MessagingErrorCode.UNREGISTERED) {
                System.out.println("🗑️ Eliminando token inválido: " + token);
                recepcionistaTokenRepository.deleteByToken(token);
            }
        }
    }

}
