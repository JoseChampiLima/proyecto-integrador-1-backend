
package com.deportido.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.deportido.exception.BadRequestException;
import com.deportido.exception.NotFoundException;
import com.deportido.model.EspacioDeportivo;
import com.deportido.model.Horario;
import com.deportido.repository.EspacioDeportivoRepository;
import com.deportido.repository.HorarioRepository;

@Service
public class HorarioServiceImpl implements HorarioService {

    private final HorarioRepository repository;
    private final EspacioDeportivoRepository espacioRepository;

    public HorarioServiceImpl(
            HorarioRepository repository,
            EspacioDeportivoRepository espacioRepository) {

        this.repository = repository;
        this.espacioRepository = espacioRepository;
    }

    public List<Horario> listar() {
        return repository.findAll();
    }

    public Optional<Horario> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public List<Horario> listarPorEspacio(Long idEspacio) {
        return repository.findByEspacioIdEspacio(idEspacio);
    }

    public List<Horario> listarPorEspacioYDia(
            Long idEspacio, String diaSemana) {

        return repository.findByEspacioIdEspacioAndDiaSemana(
                idEspacio, diaSemana.toUpperCase());
    }

    private void validar(Horario horario) {

        // 1. Validar espacio deportivo
        if (horario.getEspacio() == null ||
            horario.getEspacio().getIdEspacio() == null) {

            throw new BadRequestException(
                "Debe indicar el espacio deportivo");
        }

        EspacioDeportivo espacio = espacioRepository
                .findById(horario.getEspacio().getIdEspacio())
                .orElseThrow(() ->
                    new NotFoundException(
                        "Espacio deportivo no encontrado"));

        // 2. Validar día
        if (horario.getDiaSemana() == null ||
            horario.getDiaSemana().isBlank()) {

            throw new BadRequestException(
                "Debe indicar el día de la semana");
        }

        String dia = horario.getDiaSemana()
                .trim()
                .toUpperCase()
                .replace("Á", "A")
                .replace("É", "E")
                .replace("Í", "I")
                .replace("Ó", "O")
                .replace("Ú", "U");

        if (!List.of(
                "LUNES", "MARTES", "MIERCOLES",
                "JUEVES", "VIERNES", "SABADO",
                "DOMINGO"
            ).contains(dia)) {

            throw new BadRequestException(
                "Día de la semana inválido");
        }

        // 3. Validar horas
        if (horario.getHoraInicio() == null ||
            horario.getHoraFin() == null) {

            throw new BadRequestException(
                "Debe indicar hora de inicio y hora de fin");
        }

        if (!horario.getHoraFin()
                .isAfter(horario.getHoraInicio())) {

            throw new BadRequestException(
                "La hora final debe ser mayor a la hora inicial");
        }

        // 4. Validar duplicados y superposición
        Long idActual = horario.getIdHorario();

        boolean existeConflicto = repository
                .findByEspacioIdEspacio(
                    espacio.getIdEspacio())
                .stream()

                // Excluir el registro actual
                .filter(h ->
                    idActual == null ||
                    !idActual.equals(h.getIdHorario())
                )

                // Comparar el mismo día
                .filter(h ->
                    h.getDiaSemana() != null &&
                    h.getDiaSemana().trim()
                        .equalsIgnoreCase(dia)
                )

                // Detectar horas iguales o superpuestas
                .anyMatch(h ->
                    h.getHoraInicio() != null &&
                    h.getHoraFin() != null &&
                    horario.getHoraInicio()
                        .isBefore(h.getHoraFin()) &&
                    horario.getHoraFin()
                        .isAfter(h.getHoraInicio())
                );

        if (existeConflicto) {
            throw new BadRequestException(
                "Ya existe otro horario registrado " +
                "para esta cancha y día con horas " +
                "iguales o superpuestas"
            );
        }

        // 5. Asignar datos normalizados
        horario.setDiaSemana(dia);
        horario.setEspacio(espacio);
    }

    public Horario guardar(Horario horario) {

        // Un registro nuevo no debe excluir ningún ID
        horario.setIdHorario(null);

        validar(horario);

        return repository.save(horario);
    }

    public Horario actualizar(Long id, Horario horario) {

        // 1. Buscar el registro original
        Horario existente = repository.findById(id)
                .orElseThrow(() ->
                    new NotFoundException(
                        "Horario no encontrado"));

        // 2. Asignar el ID que se está editando
        // Esto permite modificar el mismo registro
        horario.setIdHorario(id);

        // 3. Validar contra los demás registros
        validar(horario);

        // 4. Actualizar los datos
        existente.setDiaSemana(horario.getDiaSemana());
        existente.setHoraInicio(horario.getHoraInicio());
        existente.setHoraFin(horario.getHoraFin());
        existente.setEstado(horario.getEstado());
        existente.setEspacio(horario.getEspacio());

        return repository.save(existente);
    }

    public void eliminar(Long id) {

        if (!repository.existsById(id)) {
            throw new NotFoundException(
                "Horario no encontrado");
        }

        repository.deleteById(id);
    }
}
