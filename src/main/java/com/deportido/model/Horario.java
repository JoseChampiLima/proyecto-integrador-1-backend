package com.deportido.model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "horario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Horario {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHorario;

	@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_espacio", nullable = false)
    private EspacioDeportivo espacio;

    @Column(nullable = false, length = 15)
    private String diaSemana;

    @Column(nullable = false)
    private LocalTime horaInicio;

    @Column(nullable = false)
    private LocalTime horaFin;

    private Boolean estado = true;

	public Long getIdHorario() {
		return idHorario;
	}

	public void setIdHorario(Long idHorario) {
		this.idHorario = idHorario;
	}

	public EspacioDeportivo getEspacio() {
		return espacio;
	}

	public void setEspacio(EspacioDeportivo espacio) {
		this.espacio = espacio;
	}

	public String getDiaSemana() {
		return diaSemana;
	}

	public void setDiaSemana(String diaSemana) {
		this.diaSemana = diaSemana;
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

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
    
    
}
