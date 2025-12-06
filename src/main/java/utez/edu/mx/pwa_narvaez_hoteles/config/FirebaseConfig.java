package utez.edu.mx.pwa_narvaez_hoteles.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void init() {
        try {
            InputStream serviceAccount;

            // Intentar primero con variable de entorno (Railway/Producción)
            String firebaseCredentials = System.getenv("FIREBASE_CREDENTIALS");

            if (firebaseCredentials != null && !firebaseCredentials.isEmpty()) {
                // Usar credenciales de variable de entorno
                System.out.println("🔐 Cargando credenciales de Firebase desde variable de entorno");
                serviceAccount = new ByteArrayInputStream(
                        firebaseCredentials.getBytes(StandardCharsets.UTF_8)
                );
            } else {
                // Usar archivo local (desarrollo)
                System.out.println("📁 Cargando credenciales de Firebase desde archivo local");
                serviceAccount = getClass().getResourceAsStream("/firebase/serviceAccountKey.json");

                if (serviceAccount == null) {
                    System.out.println("⚠️ No se encontraron credenciales de Firebase");
                    return;
                }
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                System.out.println("Firebase Admin inicializado correctamente");
            }

        } catch (Exception e) {
            System.err.println("Error inicializando Firebase Admin: " + e.getMessage());
            e.printStackTrace();
        }
    }
}