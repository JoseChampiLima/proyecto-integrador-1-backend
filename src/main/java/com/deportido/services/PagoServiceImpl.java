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

    public Pago guardar(Pago pago) {
        validar(pago, null);
        return repository.save(pago);
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
}
