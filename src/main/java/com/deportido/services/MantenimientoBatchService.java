
package com.deportido.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deportido.model.Mantenimiento;
import com.deportido.repository.MantenimientoRepository;

@Service
public class MantenimientoBatchService {

    private final MantenimientoRepository mantenimientoRepository;

    public MantenimientoBatchService(
            MantenimientoRepository mantenimientoRepository) {
        this.mantenimientoRepository = mantenimientoRepository;
    }

    @Scheduled(fixedDelay = 10000)
    @Transactional
    public void actualizarEstadosMantenimiento() {

        // Hora de Perú
        LocalDateTime ahora = LocalDateTime.now(
                ZoneId.of("America/Lima")
        );

        List<Mantenimiento> mantenimientos =
                mantenimientoRepository.findAll();

        for (Mantenimiento mantenimiento : mantenimientos) {

            // No modificar mantenimientos cancelados
            if ("CANCELADO".equalsIgnoreCase(
                    mantenimiento.getEstado())) {
                continue;
            }

            // Combinar fecha y hora de inicio
            LocalDateTime inicio = LocalDateTime.of(
                    mantenimiento.getFechaInicio(),
                    mantenimiento.getHoraInicio()
            );

            // Combinar fecha y hora de finalización
            LocalDateTime fin = LocalDateTime.of(
                    mantenimiento.getFechaFin(),
                    mantenimiento.getHoraFin()
            );

            String nuevoEstado;

            if (ahora.isBefore(inicio)) {

                nuevoEstado = "PROGRAMADO";

            } else if (ahora.isBefore(fin)) {

                nuevoEstado = "EN_CURSO";

            } else {

                nuevoEstado = "FINALIZADO";
            }

            // Actualizar solamente si cambió el estado
            if (!nuevoEstado.equalsIgnoreCase(
                    mantenimiento.getEstado())) {

                mantenimiento.setEstado(nuevoEstado);

                mantenimientoRepository.save(mantenimiento);
            }
        }
    }
}
