package com.deportivo.DTO;

import java.math.BigDecimal;
import java.util.List;

public class DashboardDTO {

    private Long totalReservas;
    private BigDecimal totalIngresos;
    private Long totalClientes;
    private Long totalEspacios;

    private List<ReservaEstadoDTO> reservasPorEstado;
    private List<ReservaEspacioDTO> reservasPorEspacio;

    public DashboardDTO() {
    }

    public DashboardDTO(
            Long totalReservas,
            BigDecimal totalIngresos,
            Long totalClientes,
            Long totalEspacios,
            List<ReservaEstadoDTO> reservasPorEstado,
            List<ReservaEspacioDTO> reservasPorEspacio) {

        this.totalReservas = totalReservas;
        this.totalIngresos = totalIngresos;
        this.totalClientes = totalClientes;
        this.totalEspacios = totalEspacios;
        this.reservasPorEstado = reservasPorEstado;
        this.reservasPorEspacio = reservasPorEspacio;
    }

    public Long getTotalReservas() {
        return totalReservas;
    }

    public void setTotalReservas(Long totalReservas) {
        this.totalReservas = totalReservas;
    }

    public BigDecimal getTotalIngresos() {
        return totalIngresos;
    }

    public void setTotalIngresos(BigDecimal totalIngresos) {
        this.totalIngresos = totalIngresos;
    }

    public Long getTotalClientes() {
        return totalClientes;
    }

    public void setTotalClientes(Long totalClientes) {
        this.totalClientes = totalClientes;
    }

    public Long getTotalEspacios() {
        return totalEspacios;
    }

    public void setTotalEspacios(Long totalEspacios) {
        this.totalEspacios = totalEspacios;
    }

    public List<ReservaEstadoDTO> getReservasPorEstado() {
        return reservasPorEstado;
    }

    public void setReservasPorEstado(List<ReservaEstadoDTO> reservasPorEstado) {
        this.reservasPorEstado = reservasPorEstado;
    }

    public List<ReservaEspacioDTO> getReservasPorEspacio() {
        return reservasPorEspacio;
    }

    public void setReservasPorEspacio(List<ReservaEspacioDTO> reservasPorEspacio) {
        this.reservasPorEspacio = reservasPorEspacio;
    }
}