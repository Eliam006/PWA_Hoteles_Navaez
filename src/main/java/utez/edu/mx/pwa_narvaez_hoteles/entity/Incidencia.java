package utez.edu.mx.pwa_narvaez_hoteles.entity;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Incidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;

    @ElementCollection
    private List<String> fotos = new ArrayList<>();

    private String fecha; // formato ISO 2025-02-18T10:22:00

    @ManyToOne
    private Habitacion habitacion;

    @ManyToOne
    private Camarera camarera;

    // getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    public Camarera getCamarera() {
        return camarera;
    }

    public void setCamarera(Camarera camarera) {
        this.camarera = camarera;
    }
}
