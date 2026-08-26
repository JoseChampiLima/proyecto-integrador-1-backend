package com.deportido.controller;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.deportido.model.MetodoPago;
import com.deportido.services.MetodoPagoService;

@RestController
@RequestMapping("/api/metodos-pago")
@CrossOrigin(origins = "*")
public class MetodoPagoController {
	 private final MetodoPagoService metodoPagoService;

	    public MetodoPagoController(
	            MetodoPagoService metodoPagoService) {

	        this.metodoPagoService = metodoPagoService;
	    }

	    @GetMapping
	    public List<MetodoPago> listar() {
	        return metodoPagoService.listar();
	    }

	    @GetMapping("/activos")
	    public List<MetodoPago> listarActivos() {
	        return metodoPagoService.listarActivos();
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<MetodoPago> buscarPorId(
	            @PathVariable Long id) {

	        return metodoPagoService.buscarPorId(id)
	                .map(ResponseEntity::ok)
	                .orElse(ResponseEntity.notFound().build());
	    }

	    @PostMapping
	    public ResponseEntity<MetodoPago> guardar(
	            @RequestBody MetodoPago metodoPago) {

	        return ResponseEntity.ok(
	                metodoPagoService.guardar(metodoPago));
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<MetodoPago> actualizar(
	            @PathVariable Long id,
	            @RequestBody MetodoPago metodoPago) {

	        return ResponseEntity.ok(
	                metodoPagoService.actualizar(id, metodoPago));
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
	        metodoPagoService.eliminar(id);
	        return ResponseEntity.noContent().build();
	    }
}
