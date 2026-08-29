package com.deportido.model;
import jakarta.persistence.*; 
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

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
    private LocalDate fechaInicio;

    @Column(nullable = false)
    private LocalDate fechaFin;

    @Column(nullable = false)
    private LocalTime horaInicio;

    @Column(nullable = false)
    private LocalTime horaFin;

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

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}

	public LocalTime getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(LocalTime horaInicio) {
		this.horaInicio = horaInicio;
	}

	public LocalTime getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(LocalTime horaFin) {
		this.horaFin = horaFin;
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
