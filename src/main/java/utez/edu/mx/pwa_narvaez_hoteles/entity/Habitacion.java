package utez.edu.mx.pwa_narvaez_hoteles.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Habitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;         // 4 caracteres
    private String disponibilidad; // Disponible / No Disponible
    private String status;         // Limpia / Incidente / En Revision

    @ManyToMany
    @JoinTable(
            name = "habitacion_camarera",
            joinColumns = @JoinColumn(name = "habitacion_id"),
            inverseJoinColumns = @JoinColumn(name = "camarera_id")
    )
    private List<Camarera> camareras = new ArrayList<>();

    public Habitacion() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDisponibilidad() { return disponibilidad; }
    public void setDisponibilidad(String disponibilidad) { this.disponibilidad = disponibilidad; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<Camarera> getCamareras() { return camareras; }
    public void setCamareras(List<Camarera> camareras) { this.camareras = camareras; }
}
