package com.deportido.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deportido.exception.BadRequestException;
import com.deportido.exception.ConflictException;
import com.deportido.exception.NotFoundException;
import com.deportido.model.EspacioDeportivo;
import com.deportido.model.EstadoReserva;
import com.deportido.model.Horario;
import com.deportido.model.Reserva;
import com.deportido.model.Usuario;
import com.deportido.repository.EspacioDeportivoRepository;
import com.deportido.repository.EstadoReservaRepository;
import com.deportido.repository.HorarioRepository;
import com.deportido.repository.ReservaRepository;
import com.deportido.repository.UsuarioRepository;

@Service
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final EstadoReservaRepository estadoReservaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EspacioDeportivoRepository espacioRepository;
    private final HorarioRepository horarioRepository;
    private final MantenimientoService mantenimientoService;

    public ReservaServiceImpl(
            ReservaRepository reservaRepository,
            EstadoReservaRepository estadoReservaRepository,
            UsuarioRepository usuarioRepository,
            EspacioDeportivoRepository espacioRepository,
            HorarioRepository horarioRepository,
            MantenimientoService mantenimientoService) {
        this.reservaRepository = reservaRepository;
        this.estadoReservaRepository = estadoReservaRepository;
        this.usuarioRepository = usuarioRepository;
        this.espacioRepository = espacioRepository;
        this.horarioRepository = horarioRepository;
        this.mantenimientoService = mantenimientoService;
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
    public List<Reserva> listarPorUsuario(Long idUsuario) {
        return reservaRepository.findByUsuarioIdUsuario(idUsuario);
    }

    @Override
    public List<Reserva> listarPorEspacio(Long idEspacio) {
        return reservaRepository.findByEspacioIdEspacio(idEspacio);
    }

    @Override
    public List<Reserva> listarPorFecha(LocalDate fecha) {
        return reservaRepository.findByFechaReserva(fecha);
    }

    @Override
    public boolean estaDisponible(
            Long idEspacio,
            LocalDate fecha,
            LocalTime horaInicio,
            LocalTime horaFin) {

        validarDatosBasicos(fecha, horaInicio, horaFin);

        EspacioDeportivo espacio = espacioRepository.findById(idEspacio)
                .orElseThrow(() -> new NotFoundException("Espacio deportivo no encontrado"));

        if (!"DISPONIBLE".equalsIgnoreCase(espacio.getEstado())) {
            return false;
        }

        if (!estaDentroDelHorario(idEspacio, fecha, horaInicio, horaFin)) {
            return false;
        }

        if (mantenimientoService.existeMantenimiento(idEspacio, fecha, horaInicio, horaFin)) {
            return false;
        }

        return reservaRepository.contarReservasSolapadas(
                idEspacio, fecha, horaInicio, horaFin) == 0;
    }

    private void validarDatosBasicos(LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
        if (fecha == null) {
            throw new BadRequestException("Debe indicar la fecha de reserva");
        }
        if (horaInicio == null || horaFin == null) {
            throw new BadRequestException("Debe indicar hora de inicio y hora de fin");
        }
        if (!horaFin.isAfter(horaInicio)) {
            throw new BadRequestException("La hora final debe ser mayor a la hora inicial");
        }
        if (fecha.isBefore(LocalDate.now())) {
            throw new BadRequestException("No se puede registrar una reserva en una fecha pasada");
        }
    }

    private String diaSemana(LocalDate fecha) {
        DayOfWeek dia = fecha.getDayOfWeek();
        return switch (dia) {
            case MONDAY -> "LUNES";
            case TUESDAY -> "MARTES";
            case WEDNESDAY -> "MIERCOLES";
            case THURSDAY -> "JUEVES";
            case FRIDAY -> "VIERNES";
            case SATURDAY -> "SABADO";
            case SUNDAY -> "DOMINGO";
        };
    }

    private boolean estaDentroDelHorario(
            Long idEspacio,
            LocalDate fecha,
            LocalTime horaInicio,
            LocalTime horaFin) {

        String dia = diaSemana(fecha);
        List<Horario> horarios = horarioRepository
                .findByEspacioIdEspacioAndDiaSemana(idEspacio, dia);

        return horarios.stream()
                .filter(h -> Boolean.TRUE.equals(h.getEstado()))
                .anyMatch(h ->
                        !horaInicio.isBefore(h.getHoraInicio())
                        && !horaFin.isAfter(h.getHoraFin()));
    }

    private BigDecimal calcularPrecio(
            BigDecimal precioHora,
            LocalTime horaInicio,
            LocalTime horaFin) {

        long minutos = Duration.between(horaInicio, horaFin).toMinutes();
        BigDecimal horas = BigDecimal.valueOf(minutos)
                .divide(BigDecimal.valueOf(60), 4, RoundingMode.HALF_UP);

        return precioHora.multiply(horas).setScale(2, RoundingMode.HALF_UP);
    }

    private DatosReserva validarYResolver(Reserva reserva) {
        if (reserva.getUsuario() == null || reserva.getUsuario().getIdUsuario() == null) {
            throw new BadRequestException("Debe indicar el usuario");
        }
        if (reserva.getEspacio() == null || reserva.getEspacio().getIdEspacio() == null) {
            throw new BadRequestException("Debe indicar el espacio deportivo");
        }

        validarDatosBasicos(
                reserva.getFechaReserva(),
                reserva.getHoraInicio(),
                reserva.getHoraFin());

        Usuario usuario = usuarioRepository.findById(reserva.getUsuario().getIdUsuario())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        if (!Boolean.TRUE.equals(usuario.getEstado())) {
            throw new BadRequestException("El usuario se encuentra inactivo");
        }

        EspacioDeportivo espacio = espacioRepository.findById(reserva.getEspacio().getIdEspacio())
                .orElseThrow(() -> new NotFoundException("Espacio deportivo no encontrado"));

        if (!"DISPONIBLE".equalsIgnoreCase(espacio.getEstado())) {
            throw new ConflictException("El espacio deportivo no se encuentra disponible");
        }

        if (!estaDentroDelHorario(
                espacio.getIdEspacio(),
                reserva.getFechaReserva(),
                reserva.getHoraInicio(),
                reserva.getHoraFin())) {
            throw new BadRequestException(
                    "El horario solicitado se encuentra fuera del horario disponible de la cancha");
        }

        if (mantenimientoService.existeMantenimiento(
                espacio.getIdEspacio(),
                reserva.getFechaReserva(),
                reserva.getHoraInicio(),
                reserva.getHoraFin())) {
            throw new ConflictException(
                    "El espacio deportivo se encuentra en mantenimiento en ese horario");
        }

        return new DatosReserva(usuario, espacio);
    }

    private record DatosReserva(Usuario usuario, EspacioDeportivo espacio) {}

    @Override
    @Transactional
    public Reserva guardar(Reserva reserva) {
        DatosReserva datos = validarYResolver(reserva);

        long solapadas = reservaRepository.contarReservasSolapadas(
                datos.espacio().getIdEspacio(),
                reserva.getFechaReserva(),
                reserva.getHoraInicio(),
                reserva.getHoraFin());

        if (solapadas > 0) {
            throw new ConflictException(
                    "El espacio deportivo ya se encuentra reservado en ese horario");
        }

        EstadoReserva pendiente = estadoReservaRepository.findByNombre("PENDIENTE")
                .orElseThrow(() -> new NotFoundException(
                        "No existe el estado PENDIENTE. Regístrelo antes de crear reservas"));

        reserva.setUsuario(datos.usuario());
        reserva.setEspacio(datos.espacio());
        reserva.setEstadoReserva(pendiente);
        reserva.setPrecioTotal(calcularPrecio(
                datos.espacio().getPrecioHora(),
                reserva.getHoraInicio(),
                reserva.getHoraFin()));

        return reservaRepository.save(reserva);
    }

    @Override
    @Transactional
    public Reserva actualizar(Long id, Reserva reserva) {
        Reserva existente = reservaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reserva no encontrada"));

        if ("CANCELADA".equalsIgnoreCase(existente.getEstadoReserva().getNombre())
                || "FINALIZADA".equalsIgnoreCase(existente.getEstadoReserva().getNombre())) {
            throw new BadRequestException(
                    "No se puede modificar una reserva cancelada o finalizada");
        }

        DatosReserva datos = validarYResolver(reserva);

        long solapadas = reservaRepository.contarReservasSolapadasExcluyendo(
                id,
                datos.espacio().getIdEspacio(),
                reserva.getFechaReserva(),
                reserva.getHoraInicio(),
                reserva.getHoraFin());

        if (solapadas > 0) {
            throw new ConflictException(
                    "El espacio deportivo ya se encuentra reservado en ese horario");
        }

        existente.setUsuario(datos.usuario());
        existente.setEspacio(datos.espacio());
        existente.setFechaReserva(reserva.getFechaReserva());
        existente.setHoraInicio(reserva.getHoraInicio());
        existente.setHoraFin(reserva.getHoraFin());
        existente.setPrecioTotal(calcularPrecio(
                datos.espacio().getPrecioHora(),
                reserva.getHoraInicio(),
                reserva.getHoraFin()));
        existente.setObservacion(reserva.getObservacion());

        return reservaRepository.save(existente);
    }

    @Override
    @Transactional
    public Reserva cancelar(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reserva no encontrada"));

        if ("CANCELADA".equalsIgnoreCase(reserva.getEstadoReserva().getNombre())) {
            throw new ConflictException("La reserva ya se encuentra cancelada");
        }

        EstadoReserva cancelada = estadoReservaRepository.findByNombre("CANCELADA")
                .orElseThrow(() -> new NotFoundException(
                        "No existe el estado CANCELADA"));

        reserva.setEstadoReserva(cancelada);
        return reservaRepository.save(reserva);
    }

    @Override
    public void eliminar(Long id) {
        if (!reservaRepository.existsById(id)) {
            throw new NotFoundException("Reserva no encontrada");
        }
        reservaRepository.deleteById(id);
    }
}
