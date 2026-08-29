package com.deportido.controller;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.deportido.model.Mantenimiento;
import com.deportido.services.MantenimientoService;

@RestController
@RequestMapping("/api/mantenimientos")
@CrossOrigin(origins = "*")
public class MantenimientoController {

    private final MantenimientoService mantenimientoService;

    public MantenimientoController(
            MantenimientoService mantenimientoService) {

        this.mantenimientoService = mantenimientoService;
    }

    @GetMapping
    public List<Mantenimiento> listar() {
        return mantenimientoService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mantenimiento> buscarPorId(
            @PathVariable Long id) {

        return mantenimientoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/espacio/{idEspacio}")
    public List<Mantenimiento> listarPorEspacio(
            @PathVariable Long idEspacio) {

        return mantenimientoService
                .listarPorEspacio(idEspacio);
    }

    @PostMapping
    public ResponseEntity<Mantenimiento> guardar(
            @RequestBody Mantenimiento mantenimiento) {

        return ResponseEntity.ok(
                mantenimientoService.guardar(mantenimiento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mantenimiento> actualizar(
            @PathVariable Long id,
            @RequestBody Mantenimiento mantenimiento) {

        return ResponseEntity.ok(
                mantenimientoService
                        .actualizar(id, mantenimiento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        mantenimientoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
