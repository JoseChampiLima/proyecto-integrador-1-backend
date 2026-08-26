package com.deportido.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tipo_espacio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoEspacio {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long idTipoEspacio;

	    @Column(nullable = false, length = 100)
	    private String nombre;

	    @Column(length = 200)
	    private String descripcion;

	    private Boolean estado = true;

	    @JsonIgnore
	    @OneToMany(mappedBy = "tipoEspacio")
	    private List<EspacioDeportivo> espacios;

		public Long getIdTipoEspacio() {
			return idTipoEspacio;
		}

		public void setIdTipoEspacio(Long idTipoEspacio) {
			this.idTipoEspacio = idTipoEspacio;
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

		public List<EspacioDeportivo> getEspacios() {
			return espacios;
		}

		public void setEspacios(List<EspacioDeportivo> espacios) {
			this.espacios = espacios;
		}
	    
}
