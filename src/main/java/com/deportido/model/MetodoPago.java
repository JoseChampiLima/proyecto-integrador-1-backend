package com.deportido.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "metodo_pago")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetodoPago {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMetodoPago;

    @Column(nullable = false, length = 50, unique = true)
    private String nombre;

    private Boolean estado = true;

    @JsonIgnore
    @OneToMany(mappedBy = "metodoPago")
    private List<Pago> pagos;

	public Long getIdMetodoPago() {
		return idMetodoPago;
	}

	public void setIdMetodoPago(Long idMetodoPago) {
		this.idMetodoPago = idMetodoPago;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public List<Pago> getPagos() {
		return pagos;
	}

	public void setPagos(List<Pago> pagos) {
		this.pagos = pagos;
	}
    
    
}
