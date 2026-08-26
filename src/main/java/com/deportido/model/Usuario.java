package com.deportido.model;
import jakarta.persistence.*; 
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long idUsuario;

	    @ManyToOne
	    @JoinColumn(name = "id_rol", nullable = false)
	    private Rol rol;

	    @Column(nullable = false, length = 100)
	    private String nombres;

	    @Column(nullable = false, length = 100)
	    private String apellidos;

	    @Column(nullable = false, unique = true, length = 8)
	    private String dni;

	    @Column(length = 20)
	    private String telefono;

	    @Column(nullable = false, unique = true, length = 150)
	    private String correo;

	    @Column(nullable = false, length = 255)
	    private String clave;

	    private LocalDateTime fechaRegistro = LocalDateTime.now();

	    private Boolean estado = true;

	    @JsonIgnore
	    @OneToMany(mappedBy = "usuario")
	    private List<Reserva> reservas;

	    public Usuario() {
	    }

	    public Long getIdUsuario() {
	        return idUsuario;
	    }

	    public void setIdUsuario(Long idUsuario) {
	        this.idUsuario = idUsuario;
	    }

	    public Rol getRol() {
	        return rol;
	    }

	    public void setRol(Rol rol) {
	        this.rol = rol;
	    }

	    public String getNombres() {
	        return nombres;
	    }

	    public void setNombres(String nombres) {
	        this.nombres = nombres;
	    }

	    public String getApellidos() {
	        return apellidos;
	    }

	    public void setApellidos(String apellidos) {
	        this.apellidos = apellidos;
	    }

	    public String getDni() {
	        return dni;
	    }

	    public void setDni(String dni) {
	        this.dni = dni;
	    }

	    public String getTelefono() {
	        return telefono;
	    }

	    public void setTelefono(String telefono) {
	        this.telefono = telefono;
	    }

	    public String getCorreo() {
	        return correo;
	    }

	    public void setCorreo(String correo) {
	        this.correo = correo;
	    }

	    public String getClave() {
	        return clave;
	    }

	    public void setClave(String clave) {
	        this.clave = clave;
	    }

	    public LocalDateTime getFechaRegistro() {
	        return fechaRegistro;
	    }

	    public void setFechaRegistro(LocalDateTime fechaRegistro) {
	        this.fechaRegistro = fechaRegistro;
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
