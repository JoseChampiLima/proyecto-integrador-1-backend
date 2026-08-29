package com.deportido.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "estado_reserva")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoReserva {
	   @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long idEstadoReserva;

	    @Column(nullable = false, length = 30, unique = true)
	    private String nombre;

	    @Column(length = 150)
	    private String descripcion;

	    private Boolean estado = true;

	    @JsonIgnore
	    @OneToMany(mappedBy = "estadoReserva")
	    private List<Reserva> reservas;

		public Long getIdEstadoReserva() {
			return idEstadoReserva;
		}

		public void setIdEstadoReserva(Long idEstadoReserva) {
			this.idEstadoReserva = idEstadoReserva;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public String getDescripcion() {
			return descripcion;
		}

		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}

		public Boolean getEstado() {
			return estado;
		}

		public void setEstado(Boolean estado) {
			this.estado = estado;
		}

		public List<Reserva> getReservas() {
			return reservas;
		}

		public void setReservas(List<Reserva> reservas) {
			this.reservas = reservas;
		}
	    
}
