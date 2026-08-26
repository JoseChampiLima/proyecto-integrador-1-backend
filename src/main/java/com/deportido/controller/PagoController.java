package com.deportido.controller;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.deportido.model.Pago;
import com.deportido.services.PagoService;

@RestController
@RequestMapping("/api/pagos")
@CrossOrigin(origins = "*")
public class PagoController {
	  private final PagoService pagoService;

	    public PagoController(PagoService pagoService) {
	        this.pagoService = pagoService;
	    }

	    @GetMapping
	    public List<Pago> listar() {
	        return pagoService.listar();
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Pago> buscarPorId(
	            @PathVariable Long id) {

	        return pagoService.buscarPorId(id)
	                .map(ResponseEntity::ok)
	                .orElse(ResponseEntity.notFound().build());
	    }

	    @GetMapping("/reserva/{idReserva}")
	    public List<Pago> listarPorReserva(
	            @PathVariable Long idReserva) {

	        return pagoService.listarPorReserva(idReserva);
	    }

	    @PostMapping
	    public ResponseEntity<Pago> guardar(@RequestBody Pago pago) {
	        return ResponseEntity.ok(pagoService.guardar(pago));
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<Pago> actualizar(
	            @PathVariable Long id,
	            @RequestBody Pago pago) {

	        return ResponseEntity.ok(
	                pagoService.actualizar(id, pago));
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
	        pagoService.eliminar(id);
	        return ResponseEntity.noContent().build();
	    }
}
