package utez.edu.mx.pwa_narvaez_hoteles.entity;

import jakarta.persistence.*;

@Entity
public class RecepcionistaToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long recepcionistaId;
    private String token;

    public RecepcionistaToken() {}

    public RecepcionistaToken(Long recepcionistaId, String token) {
        this.recepcionistaId = recepcionistaId;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public Long getRecepcionistaId() {
        return recepcionistaId;
    }

}
