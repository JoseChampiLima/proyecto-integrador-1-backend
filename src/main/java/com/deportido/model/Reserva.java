package com.deportido.model;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "reserva")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reserva {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long idReserva;

	    @ManyToOne
	    @JoinColumn(name = "id_usuario", nullable = false)
	    private Usuario usuario;

	    @ManyToOne
	    @JoinColumn(name = "id_espacio", nullable = false)
	    private EspacioDeportivo espacio;

	    @ManyToOne
	    @JoinColumn(name = "id_estado_reserva", nullable = false)
	    private EstadoReserva estadoReserva;

	    @Column(nullable = false)
	    private LocalDate fechaReserva;

	    @Column(nullable = false)
	    private LocalTime horaInicio;

	    @Column(nullable = false)
	    private LocalTime horaFin;

	    @Column(nullable = false, precision = 10, scale = 2)
	    private BigDecimal precioTotal;

	    private LocalDateTime fechaRegistro = LocalDateTime.now();

	    @Column(length = 250)
	    private String observacion;

	    @JsonIgnore
	    @OneToMany(mappedBy = "reserva")
	    private List<Pago> pagos;

		public Long getIdReserva() {
			return idReserva;
		}

		public void setIdReserva(Long idReserva) {
			this.idReserva = idReserva;
		}

		public Usuario getUsuario() {
			return usuario;
		}

		public void setUsuario(Usuario usuario) {
			this.usuario = usuario;
		}

		public EspacioDeportivo getEspacio() {
			return espacio;
		}

		public void setEspacio(EspacioDeportivo espacio) {
			this.espacio = espacio;
		}

		public EstadoReserva getEstadoReserva() {
			return estadoReserva;
		}

		public void setEstadoReserva(EstadoReserva estadoReserva) {
			this.estadoReserva = estadoReserva;
		}

		public LocalDate getFechaReserva() {
			return fechaReserva;
		}

		public void setFechaReserva(LocalDate fechaReserva) {
			this.fechaReserva = fechaReserva;
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

		public BigDecimal getPrecioTotal() {
			return precioTotal;
		}

		public void setPrecioTotal(BigDecimal precioTotal) {
			this.precioTotal = precioTotal;
		}

		public LocalDateTime getFechaRegistro() {
			return fechaRegistro;
		}

		public void setFechaRegistro(LocalDateTime fechaRegistro) {
			this.fechaRegistro = fechaRegistro;
		}

		public String getObservacion() {
			return observacion;
		}

		public void setObservacion(String observacion) {
			this.observacion = observacion;
		}

		public List<Pago> getPagos() {
			return pagos;
		}

		public void setPagos(List<Pago> pagos) {
			this.pagos = pagos;
		}
	    
	    
}
