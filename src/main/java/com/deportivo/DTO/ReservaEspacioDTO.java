package com.deportivo.DTO;

public class ReservaEspacioDTO {

    private String espacio;
    private Long cantidad;

    public ReservaEspacioDTO(String espacio, Long cantidad) {
        this.espacio = espacio;
        this.cantidad = cantidad;
    }

    public String getEspacio() {
        return espacio;
    }

    public void setEspacio(String espacio) {
        this.espacio = espacio;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }
}