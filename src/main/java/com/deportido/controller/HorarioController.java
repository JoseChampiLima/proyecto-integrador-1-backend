package com.deportido.controller;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.deportido.model.Horario;
import com.deportido.services.HorarioService;

@RestController
@RequestMapping("/api/horarios")
@CrossOrigin(origins = "*")
public class HorarioController {
	 private final HorarioService horarioService;

	    public HorarioController(HorarioService horarioService) {
	        this.horarioService = horarioService;
	    }

	    @GetMapping
	    public List<Horario> listar() {
	        return horarioService.listar();
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Horario> buscarPorId(
	            @PathVariable Long id) {

	        return horarioService.buscarPorId(id)
	                .map(ResponseEntity::ok)
	                .orElse(ResponseEntity.notFound().build());
	    }

	    @GetMapping("/espacio/{idEspacio}")
	    public List<Horario> listarPorEspacio(
	            @PathVariable Long idEspacio) {

	        return horarioService.listarPorEspacio(idEspacio);
	    }

	    @GetMapping("/espacio/{idEspacio}/dia/{dia}")
	    public List<Horario> listarPorEspacioYDia(
	            @PathVariable Long idEspacio,
	            @PathVariable String dia) {

	        return horarioService
	                .listarPorEspacioYDia(idEspacio, dia);
	    }

	    @PostMapping
	    public ResponseEntity<Horario> guardar(
	            @RequestBody Horario horario) {

	        return ResponseEntity.ok(
	                horarioService.guardar(horario));
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<Horario> actualizar(
	            @PathVariable Long id,
	            @RequestBody Horario horario) {

	        return ResponseEntity.ok(
	                horarioService.actualizar(id, horario));
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
	        horarioService.eliminar(id);
	        return ResponseEntity.noContent().build();
	    }
}
