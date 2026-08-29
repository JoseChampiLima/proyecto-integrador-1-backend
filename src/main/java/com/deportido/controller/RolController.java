package com.deportido.controller;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; 

import com.deportido.model.Rol;
import com.deportido.services.RolService;	

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*")
public class RolController {
	 private final RolService rolService;

	    public RolController(RolService rolService) {
	        this.rolService = rolService;
	    }

	    @GetMapping
	    public List<Rol> listar() {
	        return rolService.listar();
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Rol> buscarPorId(@PathVariable Long id) {
	        return rolService.buscarPorId(id)
	                .map(ResponseEntity::ok)
	                .orElse(ResponseEntity.notFound().build());
	    }

	    @PostMapping
	    public ResponseEntity<Rol> guardar(@RequestBody Rol rol) {
	        return ResponseEntity.ok(rolService.guardar(rol));
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<Rol> actualizar(
	            @PathVariable Long id,
	            @RequestBody Rol rol) {

	        return ResponseEntity.ok(
	                rolService.actualizar(id, rol));
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
	        rolService.eliminar(id);
	        return ResponseEntity.noContent().build();
	    }
}
