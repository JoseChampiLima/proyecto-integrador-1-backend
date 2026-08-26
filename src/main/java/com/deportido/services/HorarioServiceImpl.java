package com.deportido.services;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.deportido.model.Horario;
import com.deportido.repository.HorarioRepository;

@Service
public class HorarioServiceImpl implements HorarioService{
	private final HorarioRepository repository;

    public HorarioServiceImpl(HorarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Horario> listar() {
        return repository.findAll();
    }

    @Override
    public Optional<Horario> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Horario> listarPorEspacio(Long idEspacio) {
        return repository.findByEspacioIdEspacio(idEspacio);
    }

    @Override
    public List<Horario> listarPorEspacioYDia(
            Long idEspacio,
            String diaSemana) {

        return repository
                .findByEspacioIdEspacioAndDiaSemana(
                        idEspacio,
                        diaSemana);
    }

    @Override
    public Horario guardar(Horario horario) {
        return repository.save(horario);
    }

    @Override
    public Horario actualizar(Long id, Horario horario) {

        Horario existente = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Horario no encontrado"));

        existente.setDiaSemana(horario.getDiaSemana());
        existente.setHoraInicio(horario.getHoraInicio());
        existente.setHoraFin(horario.getHoraFin());
        existente.setEstado(horario.getEstado());
        existente.setEspacio(horario.getEspacio());

        return repository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
