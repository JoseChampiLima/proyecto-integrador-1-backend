package com.deportido.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.deportido.exception.ConflictException;
import com.deportido.exception.NotFoundException;
import com.deportido.model.TipoEspacio;
import com.deportido.repository.TipoEspacioRepository;

@Service
public class TipoEspacioServiceImpl implements TipoEspacioService {
    private final TipoEspacioRepository repository;

    public TipoEspacioServiceImpl(TipoEspacioRepository repository) {
        this.repository = repository;
    }

    public List<TipoEspacio> listar() { return repository.findAll(); }
    public List<TipoEspacio> listarActivos() { return repository.findByEstadoTrue(); }
    public Optional<TipoEspacio> buscarPorId(Long id) { return repository.findById(id); }
    public Optional<TipoEspacio> buscarPorNombre(String nombre) { return repository.findByNombre(nombre); }

    public TipoEspacio guardar(TipoEspacio tipoEspacio) {
        if (repository.existsByNombreIgnoreCase(tipoEspacio.getNombre())) {
            throw new ConflictException("El tipo de espacio ya se encuentra registrado");
        }
        return repository.save(tipoEspacio);
    }

    public TipoEspacio actualizar(Long id, TipoEspacio tipoEspacio) {
        TipoEspacio existente = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tipo de espacio no encontrado"));
        repository.findByNombre(tipoEspacio.getNombre()).ifPresent(otro -> {
            if (!otro.getIdTipoEspacio().equals(id)) {
                throw new ConflictException("Ya existe otro tipo de espacio con ese nombre");
            }
        });
        existente.setNombre(tipoEspacio.getNombre());
        existente.setDescripcion(tipoEspacio.getDescripcion());
        existente.setEstado(tipoEspacio.getEstado());
        return repository.save(existente);
    }

    public void eliminar(Long id) {
        if (!repository.existsById(id)) throw new NotFoundException("Tipo de espacio no encontrado");
        repository.deleteById(id);
    }
}
