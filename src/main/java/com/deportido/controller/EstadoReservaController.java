package com.deportido.controller;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.deportido.model.EstadoReserva;
import com.deportido.services.EstadoReservaService;

@RestController
@RequestMapping("/api/estados-reserva")
@CrossOrigin(origins = "*")
public class EstadoReservaController {

    private final EstadoReservaService estadoReservaService;

    public EstadoReservaController(
            EstadoReservaService estadoReservaService) {

        this.estadoReservaService = estadoReservaService;
    }

    @GetMapping
    public List<EstadoReserva> listar() {
        return estadoReservaService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoReserva> buscarPorId(
            @PathVariable Long id) {

        return estadoReservaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EstadoReserva> guardar(
            @RequestBody EstadoReserva estado) {

        return ResponseEntity.ok(
                estadoReservaService.guardar(estado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoReserva> actualizar(
            @PathVariable Long id,
            @RequestBody EstadoReserva estado) {

        return ResponseEntity.ok(
                estadoReservaService.actualizar(id, estado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        estadoReservaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
