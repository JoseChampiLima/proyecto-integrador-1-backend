package com.deportido.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.deportido.repository.EspacioDeportivoRepository;
import com.deportido.repository.PagoRepository;
import com.deportido.repository.ReservaRepository;
import com.deportivo.DTO.DashboardDTO;
import com.deportivo.DTO.ReservaEspacioDTO;
import com.deportivo.DTO.ReservaEstadoDTO;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final ReservaRepository reservaRepository;
    private final PagoRepository pagoRepository;
    private final EspacioDeportivoRepository espacioRepository;

    public DashboardServiceImpl(
            ReservaRepository reservaRepository,
            PagoRepository pagoRepository,
            EspacioDeportivoRepository espacioRepository) {

        this.reservaRepository = reservaRepository;
        this.pagoRepository = pagoRepository;
        this.espacioRepository = espacioRepository;
    }

    @Override
    public DashboardDTO obtenerDashboard(
            LocalDate fechaDesde,
            LocalDate fechaHasta) {

        if (fechaDesde == null || fechaHasta == null) {
            throw new IllegalArgumentException(
                    "Debe ingresar fechaDesde y fechaHasta"
            );
        }

        if (fechaDesde.isAfter(fechaHasta)) {
            throw new IllegalArgumentException(
                    "La fecha inicial no puede ser mayor a la fecha final"
            );
        }

        // =====================================
        // TOTAL RESERVAS
        // =====================================

        long totalReservas =
                reservaRepository.contarReservasPorRango(
                        fechaDesde,
                        fechaHasta
                );

        // =====================================
        // CLIENTES DEL PERIODO
        // =====================================

        long totalClientes =
                reservaRepository.contarClientesPorRango(
                        fechaDesde,
                        fechaHasta
                );

        // =====================================
        // TOTAL ESPACIOS
        // =====================================

        long totalEspacios =
                espacioRepository.count();

        // =====================================
        // INGRESOS
        // =====================================

        LocalDateTime inicio =
                fechaDesde.atStartOfDay();

        LocalDateTime fin =
                fechaHasta.plusDays(1).atStartOfDay();

        BigDecimal totalIngresos =
                pagoRepository.obtenerIngresosPorRango(
                        inicio,
                        fin
                );

        // =====================================
        // RESERVAS POR ESTADO
        // =====================================

        List<ReservaEstadoDTO> reservasPorEstado =
                reservaRepository
                    .contarReservasPorEstado(
                            fechaDesde,
                            fechaHasta
                    )
                    .stream()
                    .map(fila ->
                        new ReservaEstadoDTO(
                                (String) fila[0],
                                (Long) fila[1]
                        )
                    )
                    .toList();

        // =====================================
        // RESERVAS POR ESPACIO
        // =====================================

        List<ReservaEspacioDTO> reservasPorEspacio =
                reservaRepository
                    .contarReservasPorEspacio(
                            fechaDesde,
                            fechaHasta
                    )
                    .stream()
                    .map(fila ->
                        new ReservaEspacioDTO(
                                (String) fila[0],
                                (Long) fila[1]
                        )
                    )
                    .toList();

        return new DashboardDTO(
                totalReservas,
                totalIngresos,
                totalClientes,
                totalEspacios,
                reservasPorEstado,
                reservasPorEspacio
        );
    }
}