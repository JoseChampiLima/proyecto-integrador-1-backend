package com.deportido.controller;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.deportido.model.Reserva;
import com.deportido.services.ReservaService;

@RestController
@RequestMapping("/api/reservas")
@CrossOrigin(origins = "*")
public class ReservaController {
	  private final ReservaService reservaService;

	    public ReservaController(ReservaService reservaService) {
	        this.reservaService = reservaService;
	    }

	    @GetMapping
	    public List<Reserva> listar() {
	        return reservaService.listar();
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Reserva> buscarPorId(
	            @PathVariable Long id) {

	        return reservaService.buscarPorId(id)
	                .map(ResponseEntity::ok)
	                .orElse(ResponseEntity.notFound().build());
	    }

	    @GetMapping("/usuario/{idUsuario}")
	    public List<Reserva> listarPorUsuario(
	            @PathVariable Long idUsuario) {

	        return reservaService.listarPorUsuario(idUsuario);
	    }

	    @GetMapping("/espacio/{idEspacio}")
	    public List<Reserva> listarPorEspacio(
	            @PathVariable Long idEspacio) {

	        return reservaService.listarPorEspacio(idEspacio);
	    }

	    @GetMapping("/fecha/{fecha}")
	    public List<Reserva> listarPorFecha(
	            @PathVariable LocalDate fecha) {

	        return reservaService.listarPorFecha(fecha);
	    }

	    @GetMapping("/disponibilidad")
	    public ResponseEntity<Boolean> disponibilidad(
	            @RequestParam Long idEspacio,
	            @RequestParam LocalDate fecha,
	            @RequestParam LocalTime horaInicio,
	            @RequestParam LocalTime horaFin) {

	        boolean disponible =
	                reservaService.estaDisponible(
	                        idEspacio,
	                        fecha,
	                        horaInicio,
	                        horaFin);

	        return ResponseEntity.ok(disponible);
	    }

	    @PostMapping
	    public ResponseEntity<Reserva> guardar(
	            @RequestBody Reserva reserva) {

	        return ResponseEntity.ok(
	                reservaService.guardar(reserva));
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<Reserva> actualizar(
	            @PathVariable Long id,
	            @RequestBody Reserva reserva) {

	        return ResponseEntity.ok(
	                reservaService.actualizar(id, reserva));
	    }

	    @PutMapping("/{id}/cancelar")
	    public ResponseEntity<Reserva> cancelar(
	            @PathVariable Long id) {

	        return ResponseEntity.ok(
	                reservaService.cancelar(id));
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
	        reservaService.eliminar(id);
	        return ResponseEntity.noContent().build();
	    }
}
