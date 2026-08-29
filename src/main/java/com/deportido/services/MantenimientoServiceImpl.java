package com.deportido.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.deportido.exception.BadRequestException;
import com.deportido.exception.NotFoundException;
import com.deportido.model.EspacioDeportivo;
import com.deportido.model.Mantenimiento;
import com.deportido.repository.EspacioDeportivoRepository;
import com.deportido.repository.MantenimientoRepository;

@Service
public class MantenimientoServiceImpl implements MantenimientoService {

    private final MantenimientoRepository repository;
    private final EspacioDeportivoRepository espacioRepository;

    public MantenimientoServiceImpl(
            MantenimientoRepository repository,
            EspacioDeportivoRepository espacioRepository) {
        this.repository = repository;
        this.espacioRepository = espacioRepository;
    }

    @Override
    public List<Mantenimiento> listar() {
        return repository.findAll();
    }

    @Override
    public Optional<Mantenimiento> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Mantenimiento> listarPorEspacio(Long idEspacio) {
        return repository.findByEspacioIdEspacio(idEspacio);
    }

    @Override
    public boolean existeMantenimiento(
            Long idEspacio,
            LocalDate fecha,
            LocalTime horaInicio,
            LocalTime horaFin) {

        List<Mantenimiento> mantenimientos =
                repository.findByEspacioIdEspacioAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(
                        idEspacio, fecha, fecha);

        for (Mantenimiento m : mantenimientos) {
            if ("CANCELADO".equalsIgnoreCase(m.getEstado())) {
                continue;
            }

            // Si la fecha está totalmente entre inicio y fin, se considera bloqueada.
            if (fecha.isAfter(m.getFechaInicio()) && fecha.isBefore(m.getFechaFin())) {
                return true;
            }

            // Mantenimiento de un solo día.
            if (m.getFechaInicio().equals(m.getFechaFin())) {
                if (horaInicio.isBefore(m.getHoraFin()) && horaFin.isAfter(m.getHoraInicio())) {
                    return true;
                }
                continue;
            }

            // Primer día de un mantenimiento de varios días.
            if (fecha.equals(m.getFechaInicio()) && horaFin.isAfter(m.getHoraInicio())) {
                return true;
            }

            // Último día de un mantenimiento de varios días.
            if (fecha.equals(m.getFechaFin()) && horaInicio.isBefore(m.getHoraFin())) {
                return true;
            }
        }

        return false;
    }

    private void validar(Mantenimiento mantenimiento) {
        if (mantenimiento.getEspacio() == null || mantenimiento.getEspacio().getIdEspacio() == null) {
            throw new BadRequestException("Debe indicar el espacio deportivo");
        }

        EspacioDeportivo espacio = espacioRepository.findById(mantenimiento.getEspacio().getIdEspacio())
                .orElseThrow(() -> new NotFoundException("Espacio deportivo no encontrado"));
        mantenimiento.setEspacio(espacio);

        if (mantenimiento.getFechaInicio() == null || mantenimiento.getFechaFin() == null) {
            throw new BadRequestException("Debe indicar fecha de inicio y fecha de fin");
        }
        if (mantenimiento.getFechaFin().isBefore(mantenimiento.getFechaInicio())) {
            throw new BadRequestException("La fecha final no puede ser anterior a la fecha inicial");
        }
        if (mantenimiento.getHoraInicio() == null || mantenimiento.getHoraFin() == null) {
            throw new BadRequestException("Debe indicar hora de inicio y hora de fin");
        }
        if (!mantenimiento.getHoraFin().isAfter(mantenimiento.getHoraInicio())) {
            throw new BadRequestException("La hora final debe ser mayor a la hora inicial");
        }

        if (mantenimiento.getEstado() == null || mantenimiento.getEstado().isBlank()) {
            mantenimiento.setEstado("PROGRAMADO");
        } else {
            mantenimiento.setEstado(mantenimiento.getEstado().trim().toUpperCase());
        }
    }

    @Override
    public Mantenimiento guardar(Mantenimiento mantenimiento) {
        validar(mantenimiento);
        return repository.save(mantenimiento);
    }

    @Override
    public Mantenimiento actualizar(Long id, Mantenimiento mantenimiento) {
        Mantenimiento existente = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Mantenimiento no encontrado"));

        validar(mantenimiento);

        existente.setFechaInicio(mantenimiento.getFechaInicio());
        existente.setFechaFin(mantenimiento.getFechaFin());
        existente.setHoraInicio(mantenimiento.getHoraInicio());
        existente.setHoraFin(mantenimiento.getHoraFin());
        existente.setMotivo(mantenimiento.getMotivo());
        existente.setEstado(mantenimiento.getEstado());
        existente.setEspacio(mantenimiento.getEspacio());

        return repository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Mantenimiento no encontrado");
        }
        repository.deleteById(id);
    }
}
