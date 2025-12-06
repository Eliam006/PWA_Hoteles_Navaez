package utez.edu.mx.pwa_narvaez_hoteles.entity;

import jakarta.persistence.*;

@Entity
public class Historico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreHabitacion;
    private String rol;        // "Camarera" o "Recepción"
    private String movimiento; // "Marcar Limpia", "Marcar Incidencia", "Habilitar", "Deshabilitar"
    private String fecha;      // LocalDateTime.now().toString()

    // GETTERS & SETTERS
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombreHabitacion() { return nombreHabitacion; }
    public void setNombreHabitacion(String nombreHabitacion) { this.nombreHabitacion = nombreHabitacion; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getMovimiento() { return movimiento; }
    public void setMovimiento(String movimiento) { this.movimiento = movimiento; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
}
