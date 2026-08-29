package com.deportido.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.deportido.exception.ConflictException;
import com.deportido.exception.NotFoundException;
import com.deportido.model.MetodoPago;
import com.deportido.repository.MetodoPagoRepository;

@Service
public class MetodoPagoServiceImpl implements MetodoPagoService {
    private final MetodoPagoRepository repository;

    public MetodoPagoServiceImpl(MetodoPagoRepository repository) { this.repository = repository; }

    public List<MetodoPago> listar() { return repository.findAll(); }
    public List<MetodoPago> listarActivos() { return repository.findByEstadoTrue(); }
    public Optional<MetodoPago> buscarPorId(Long id) { return repository.findById(id); }
    public Optional<MetodoPago> buscarPorNombre(String nombre) { return repository.findByNombre(nombre); }

    public MetodoPago guardar(MetodoPago metodoPago) {
        if (repository.existsByNombreIgnoreCase(metodoPago.getNombre())) {
            throw new ConflictException("El método de pago ya se encuentra registrado");
        }
        return repository.save(metodoPago);
    }

    public MetodoPago actualizar(Long id, MetodoPago metodoPago) {
        MetodoPago existente = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Método de pago no encontrado"));
        repository.findByNombre(metodoPago.getNombre()).ifPresent(otro -> {
            if (!otro.getIdMetodoPago().equals(id)) {
                throw new ConflictException("Ya existe otro método de pago con ese nombre");
            }
        });
        existente.setNombre(metodoPago.getNombre());
        existente.setEstado(metodoPago.getEstado());
        return repository.save(existente);
    }

    public void eliminar(Long id) {
        if (!repository.existsById(id)) throw new NotFoundException("Método de pago no encontrado");
        repository.deleteById(id);
    }
}
