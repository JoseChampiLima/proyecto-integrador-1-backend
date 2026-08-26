package com.deportido.model;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "espacio_deportivo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EspacioDeportivo {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long idEspacio;

	    @ManyToOne
	    @JoinColumn(name = "id_sede", nullable = false)
	    private Sede sede;

	    @ManyToOne
	    @JoinColumn(name = "id_tipo_espacio", nullable = false)
	    private TipoEspacio tipoEspacio;

	    @Column(length = 1000)
	    private String foto;
	    
	    @Column(nullable = false, length = 100)
	    private String nombre;

	    @Column(length = 250)
	    private String descripcion;

	    private Integer capacidad;

	    @Column(nullable = false, precision = 10, scale = 2)
	    private BigDecimal precioHora;

	    @Column(length = 20)
	    private String estado = "DISPONIBLE";

	    @OneToMany(mappedBy = "espacio")
	    private List<Horario> horarios;

	    @OneToMany(mappedBy = "espacio")
	    private List<Reserva> reservas;

	    @OneToMany(mappedBy = "espacio")
	    private List<Mantenimiento> mantenimientos;

		public Long getIdEspacio() {
			return idEspacio;
		}

		public void setIdEspacio(Long idEspacio) {
			this.idEspacio = idEspacio;
		}

		public Sede getSede() {
			return sede;
		}

		public void setSede(Sede sede) {
			this.sede = sede;
		}

		public TipoEspacio getTipoEspacio() {
			return tipoEspacio;
		}

		public void setTipoEspacio(TipoEspacio tipoEspacio) {
			this.tipoEspacio = tipoEspacio;
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

		public Integer getCapacidad() {
			return capacidad;
		}

		public void setCapacidad(Integer capacidad) {
			this.capacidad = capacidad;
		}

		public BigDecimal getPrecioHora() {
			return precioHora;
		}

		public void setPrecioHora(BigDecimal precioHora) {
			this.precioHora = precioHora;
		}

		public String getEstado() {
			return estado;
		}

		public void setEstado(String estado) {
			this.estado = estado;
		}

		public List<Horario> getHorarios() {
			return horarios;
		}

		public void setHorarios(List<Horario> horarios) {
			this.horarios = horarios;
		}

		public List<Reserva> getReservas() {
			return reservas;
		}

		public void setReservas(List<Reserva> reservas) {
			this.reservas = reservas;
		}

		public List<Mantenimiento> getMantenimientos() {
			return mantenimientos;
		}

		public void setMantenimientos(List<Mantenimiento> mantenimientos) {
			this.mantenimientos = mantenimientos;
		}
	    
		public String getFoto() {
		    return foto;
		}

		public void setFoto(String foto) {
		    this.foto = foto;
		}
}
