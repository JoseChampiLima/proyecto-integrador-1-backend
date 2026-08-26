package com.deportido.model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "mantenimiento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mantenimiento {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMantenimiento;

    @ManyToOne
    @JoinColumn(name = "id_espacio", nullable = false)
    private EspacioDeportivo espacio;

    @Column(nullable = false)
    private LocalDateTime fechaInicio;

    @Column(nullable = false)
    private LocalDateTime fechaFin;

    @Column(length = 250)
    private String motivo;

    @Column(length = 20)
    private String estado = "PROGRAMADO";

	public Long getIdMantenimiento() {
		return idMantenimiento;
	}

	public void setIdMantenimiento(Long idMantenimiento) {
		this.idMantenimiento = idMantenimiento;
	}

	public EspacioDeportivo getEspacio() {
		return espacio;
	}

	public void setEspacio(EspacioDeportivo espacio) {
		this.espacio = espacio;
	}

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDateTime getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDateTime fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
    
    
}
