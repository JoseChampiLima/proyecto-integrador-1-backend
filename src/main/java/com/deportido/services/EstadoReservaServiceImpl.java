package com.deportido.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.deportido.exception.ConflictException;
import com.deportido.exception.NotFoundException;
import com.deportido.model.EstadoReserva;
import com.deportido.repository.EstadoReservaRepository;

@Service
public class EstadoReservaServiceImpl implements EstadoReservaService {
    private final EstadoReservaRepository repository;

    public EstadoReservaServiceImpl(EstadoReservaRepository repository) { this.repository = repository; }

    public List<EstadoReserva> listar() { return repository.findAll(); }
    public Optional<EstadoReserva> buscarPorId(Long id) { return repository.findById(id); }
    public Optional<EstadoReserva> buscarPorNombre(String nombre) { return repository.findByNombre(nombre); }

    public EstadoReserva guardar(EstadoReserva estadoReserva) {
        if (repository.existsByNombreIgnoreCase(estadoReserva.getNombre())) {
            throw new ConflictException("El estado de reserva ya se encuentra registrado");
        }
        return repository.save(estadoReserva);
    }

    public EstadoReserva actualizar(Long id, EstadoReserva estadoReserva) {
        EstadoReserva existente = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Estado de reserva no encontrado"));
        repository.findByNombre(estadoReserva.getNombre()).ifPresent(otro -> {
            if (!otro.getIdEstadoReserva().equals(id)) {
                throw new ConflictException("Ya existe otro estado de reserva con ese nombre");
            }
        });
        existente.setNombre(estadoReserva.getNombre());
        existente.setDescripcion(estadoReserva.getDescripcion());
        existente.setEstado(estadoReserva.getEstado());
        return repository.save(existente);
    }

    public void eliminar(Long id) {
        if (!repository.existsById(id)) throw new NotFoundException("Estado de reserva no encontrado");
        repository.deleteById(id);
    }
}
