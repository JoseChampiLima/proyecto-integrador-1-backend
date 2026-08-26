package com.deportido.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "sede")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sede {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long idSede;

	    @Column(nullable = false, length = 100)
	    private String nombre;

	    @Column(length = 250)
	    private String descripcion;

	    @Column(nullable = false, length = 200)
	    private String direccion;

	    @Column(nullable = false, length = 100)
	    private String distrito;

	    @Column(length = 20)
	    private String telefono;

	    private Boolean estado = true;

	    @JsonIgnore
	    @OneToMany(mappedBy = "sede")
	    private List<EspacioDeportivo> espacios;

	    public Sede() {
	    }

	    public Long getIdSede() {
	        return idSede;
	    }
	    

	    public void setIdSede(Long idSede) {
	        this.idSede = idSede;
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

	    public String getDireccion() {
	        return direccion;
	    }

	    public void setDireccion(String direccion) {
	        this.direccion = direccion;
	    }

	    public String getDistrito() {
	        return distrito;
	    }

	    public void setDistrito(String distrito) {
	        this.distrito = distrito;
	    }

	    public String getTelefono() {
	        return telefono;
	    }

	    public void setTelefono(String telefono) {
	        this.telefono = telefono;
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
