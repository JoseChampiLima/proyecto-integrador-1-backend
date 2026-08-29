package com.deportido.controller;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.deportido.model.TipoEspacio;
import com.deportido.services.TipoEspacioService;

@RestController
@RequestMapping("/api/tipos-espacio")
@CrossOrigin(origins = "*")
public class TipoEspacioController {
	  private final TipoEspacioService tipoEspacioService;

	    public TipoEspacioController(
	            TipoEspacioService tipoEspacioService) {

	        this.tipoEspacioService = tipoEspacioService;
	    }

	    @GetMapping
	    public List<TipoEspacio> listar() {
	        return tipoEspacioService.listar();
	    }

	    @GetMapping("/activos")
	    public List<TipoEspacio> listarActivos() {
	        return tipoEspacioService.listarActivos();
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<TipoEspacio> buscarPorId(
	            @PathVariable Long id) {

	        return tipoEspacioService.buscarPorId(id)
	                .map(ResponseEntity::ok)
	                .orElse(ResponseEntity.notFound().build());
	    }

	    @PostMapping
	    public ResponseEntity<TipoEspacio> guardar(
	            @RequestBody TipoEspacio tipoEspacio) {

	        return ResponseEntity.ok(
	                tipoEspacioService.guardar(tipoEspacio));
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<TipoEspacio> actualizar(
	            @PathVariable Long id,
	            @RequestBody TipoEspacio tipoEspacio) {

	        return ResponseEntity.ok(
	                tipoEspacioService.actualizar(id, tipoEspacio));
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
	        tipoEspacioService.eliminar(id);
	        return ResponseEntity.noContent().build();
	    }
}
