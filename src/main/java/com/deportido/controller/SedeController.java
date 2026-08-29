package com.deportido.controller;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.deportido.model.Sede;
import com.deportido.services.SedeService;

@RestController
@RequestMapping("/api/sedes")
@CrossOrigin(origins = "*")
public class SedeController {

    private final SedeService sedeService;

    public SedeController(SedeService sedeService) {
        this.sedeService = sedeService;
    }

    @GetMapping
    public List<Sede> listar() {
        return sedeService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sede> buscarPorId(@PathVariable Long id) {
        return sedeService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

  

    @GetMapping("/activas")
    public List<Sede> listarActivas() {
        return sedeService.listarActivas();
    }

    @PostMapping
    public ResponseEntity<Sede> guardar(@RequestBody Sede sede) {
        return ResponseEntity.ok(sedeService.guardar(sede));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sede> actualizar(
            @PathVariable Long id,
            @RequestBody Sede sede) {

        return ResponseEntity.ok(
                sedeService.actualizar(id, sede));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        sedeService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
