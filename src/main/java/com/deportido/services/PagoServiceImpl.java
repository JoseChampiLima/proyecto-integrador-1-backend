package com.deportido.services;

import java.math.BigDecimal; 
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.deportido.exception.BadRequestException;
import com.deportido.exception.ConflictException;
import com.deportido.exception.NotFoundException;
import com.deportido.model.MetodoPago;
import com.deportido.model.Pago;
import com.deportido.model.Reserva;
import com.deportido.repository.MetodoPagoRepository;
import com.deportido.repository.PagoRepository;
import com.deportido.repository.ReservaRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PagoServiceImpl implements PagoService {

    private final PagoRepository repository;
    private final ReservaRepository reservaRepository;
    private final MetodoPagoRepository metodoPagoRepository;

    public PagoServiceImpl(
            PagoRepository repository,
            ReservaRepository reservaRepository,
            MetodoPagoRepository metodoPagoRepository) {
        this.repository = repository;
        this.reservaRepository = reservaRepository;
        this.metodoPagoRepository = metodoPagoRepository;
    }

    public List<Pago> listar() { return repository.findAll(); }
    public Optional<Pago> buscarPorId(Long id) { return repository.findById(id); }
    public List<Pago> listarPorReserva(Long idReserva) { return repository.findByReservaIdReserva(idReserva); }
    public Optional<Pago> buscarPorNumeroOperacion(String nroOperacion) { return repository.findByNroOperacion(nroOperacion); }

    private void validar(Pago pago, Long idActual) {
        if (pago.getReserva() == null || pago.getReserva().getIdReserva() == null) {
            throw new BadRequestException("Debe indicar la reserva");
        }
        if (pago.getMetodoPago() == null || pago.getMetodoPago().getIdMetodoPago() == null) {
            throw new BadRequestException("Debe indicar el método de pago");
        }
        if (pago.getMonto() == null || pago.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("El monto debe ser mayor a cero");
        }

        Reserva reserva = reservaRepository.findById(pago.getReserva().getIdReserva())
                .orElseThrow(() -> new NotFoundException("Reserva no encontrada"));

        if ("CANCELADA".equalsIgnoreCase(reserva.getEstadoReserva().getNombre())) {
            throw new BadRequestException("No se puede registrar un pago para una reserva cancelada");
        }

        MetodoPago metodo = metodoPagoRepository.findById(pago.getMetodoPago().getIdMetodoPago())
                .orElseThrow(() -> new NotFoundException("Método de pago no encontrado"));

        if (!Boolean.TRUE.equals(metodo.getEstado())) {
            throw new BadRequestException("El método de pago se encuentra inactivo");
        }

        if (pago.getNroOperacion() != null && !pago.getNroOperacion().isBlank()) {
            repository.findByNroOperacion(pago.getNroOperacion()).ifPresent(otro -> {
                if (idActual == null || !otro.getIdPago().equals(idActual)) {
                    throw new ConflictException("El número de operación ya se encuentra registrado");
                }
            });
        }

        pago.setReserva(reserva);
        pago.setMetodoPago(metodo);

        if (pago.getEstado() == null || pago.getEstado().isBlank()) {
            pago.setEstado("PENDIENTE");
        } else {
            pago.setEstado(pago.getEstado().trim().toUpperCase());
        }
    }

    @Override
    @Transactional
    public Pago guardar(Pago pago) {

        // Validar los datos recibidos
        validar(pago, null);

        // Establecer la fecha desde el servidor
        pago.setFechaPago(LocalDateTime.now());

        // El número de operación no se recibe del cliente
        pago.setNroOperacion(null);

        // Guardar para obtener el ID autogenerado
        Pago pagoGuardado = repository.saveAndFlush(pago);

        // Generar el número de operación
        String numeroOperacion = generarNumeroOperacion(pagoGuardado);

        // Asignar el número generado
        pagoGuardado.setNroOperacion(numeroOperacion);

        // Guardar el número de operación
        return repository.save(pagoGuardado);
    }

    public Pago actualizar(Long id, Pago pago) {
        Pago existente = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pago no encontrado"));

        validar(pago, id);

        existente.setMonto(pago.getMonto());
        existente.setNroOperacion(pago.getNroOperacion());
        existente.setEstado(pago.getEstado());
        existente.setMetodoPago(pago.getMetodoPago());
        existente.setReserva(pago.getReserva());

        return repository.save(existente);
    }

    public void eliminar(Long id) {
        if (!repository.existsById(id)) throw new NotFoundException("Pago no encontrado");
        repository.deleteById(id);
    }
    
    private String generarNumeroOperacion(Pago pago) {

        String nombreMetodo = pago.getMetodoPago()
                                .getNombre()
                                .trim()
                                .toUpperCase(Locale.ROOT);

        String prefijo;

        if (nombreMetodo.contains("YAPE")) {
            prefijo = "YAPE";
        } else if (nombreMetodo.contains("PLIN")) {
            prefijo = "PLIN";
        } else if (nombreMetodo.contains("TRANSFERENCIA")) {
            prefijo = "TRANS";
        } else if (nombreMetodo.contains("TARJETA")) {
            prefijo = "TAR";
        } else {
            prefijo = nombreMetodo.replaceAll("[^A-Z0-9]", "");
            prefijo = prefijo.substring(0, Math.min(4, prefijo.length()));
        }

        String fecha = pago.getFechaPago()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        String idFormateado = String.format("%03d", pago.getIdPago());

        return prefijo + "-" + fecha + "-" + idFormateado;
    }
    

	@Override
	@Transactional(readOnly = true)
	public List<Pago> listarPorUsuario(Long idUsuario) {
	    return repository.findByReservaUsuarioIdUsuario(idUsuario);
	}

}
