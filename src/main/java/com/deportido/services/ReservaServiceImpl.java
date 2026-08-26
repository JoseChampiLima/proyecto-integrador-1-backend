package com.deportido.services;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deportido.model.EstadoReserva;
import com.deportido.model.Reserva;
import com.deportido.repository.EstadoReservaRepository;
import com.deportido.repository.ReservaRepository;


@Service
public class ReservaServiceImpl implements ReservaService{
	private final ReservaRepository reservaRepository;

    private final EstadoReservaRepository
            estadoReservaRepository;

    public ReservaServiceImpl(
            ReservaRepository reservaRepository,
            EstadoReservaRepository estadoReservaRepository) {

        this.reservaRepository = reservaRepository;
        this.estadoReservaRepository =
                estadoReservaRepository;
    }

    @Override
    public List<Reserva> listar() {
        return reservaRepository.findAll();
    }

    @Override
    public Optional<Reserva> buscarPorId(Long id) {
        return reservaRepository.findById(id);
    }

    @Override
    public List<Reserva> listarPorUsuario(
            Long idUsuario) {

        return reservaRepository
                .findByUsuarioIdUsuario(idUsuario);
    }

    @Override
    public List<Reserva> listarPorEspacio(
            Long idEspacio) {

        return reservaRepository
                .findByEspacioIdEspacio(idEspacio);
    }

    @Override
    public List<Reserva> listarPorFecha(
            LocalDate fecha) {

        return reservaRepository.findByFechaReserva(fecha);
    }

    @Override
    public boolean estaDisponible(
            Long idEspacio,
            LocalDate fecha,
            LocalTime horaInicio,
            LocalTime horaFin) {

        long cantidad =
                reservaRepository.contarReservasSolapadas(
                        idEspacio,
                        fecha,
                        horaInicio,
                        horaFin);

        return cantidad == 0;
    }

    @Override
    @Transactional
    public Reserva guardar(Reserva reserva) {

        if (reserva.getHoraFin()
                .isBefore(reserva.getHoraInicio())
            ||
            reserva.getHoraFin()
                .equals(reserva.getHoraInicio())) {

            throw new RuntimeException(
                    "La hora final debe ser mayor a la hora inicial");
        }

        boolean disponible = estaDisponible(
                reserva.getEspacio().getIdEspacio(),
                reserva.getFechaReserva(),
                reserva.getHoraInicio(),
                reserva.getHoraFin());

        if (!disponible) {
            throw new RuntimeException(
                    "El espacio ya se encuentra reservado en ese horario");
        }

        EstadoReserva pendiente =
                estadoReservaRepository
                        .findByNombre("PENDIENTE")
                        .orElseThrow(() ->
                                new RuntimeException(
                                    "No existe el estado PENDIENTE"));

        reserva.setEstadoReserva(pendiente);

        return reservaRepository.save(reserva);
    }

    @Override
    public Reserva actualizar(
            Long id,
            Reserva reserva) {

        Reserva existente =
                reservaRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Reserva no encontrada"));

        existente.setFechaReserva(
                reserva.getFechaReserva());
        existente.setHoraInicio(
                reserva.getHoraInicio());
        existente.setHoraFin(
                reserva.getHoraFin());
        existente.setPrecioTotal(
                reserva.getPrecioTotal());
        existente.setObservacion(
                reserva.getObservacion());

        return reservaRepository.save(existente);
    }

    @Override
    @Transactional
    public Reserva cancelar(Long id) {

        Reserva reserva =
                reservaRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Reserva no encontrada"));

        EstadoReserva cancelada =
                estadoReservaRepository
                        .findByNombre("CANCELADA")
                        .orElseThrow(() ->
                                new RuntimeException(
                                    "No existe el estado CANCELADA"));

        reserva.setEstadoReserva(cancelada);

        return reservaRepository.save(reserva);
    }

    @Override
    public void eliminar(Long id) {
        reservaRepository.deleteById(id);
    }
}
