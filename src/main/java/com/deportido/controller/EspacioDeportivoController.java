package com.deportido.controller;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import com.deportido.model.EspacioDeportivo;
import com.deportido.services.EspacioDeportivoService;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;
@RestController
@RequestMapping("/api/espacios")
@CrossOrigin(origins = "*")
public class EspacioDeportivoController {

    private final EspacioDeportivoService espacioService;

    public EspacioDeportivoController(
            EspacioDeportivoService espacioService) {

        this.espacioService = espacioService;
    }

    @GetMapping
    public List<EspacioDeportivo> listar() {
        return espacioService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EspacioDeportivo> buscarPorId(
            @PathVariable Long id) {

        return espacioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/sede/{idSede}")
    public List<EspacioDeportivo> listarPorSede(
            @PathVariable Long idSede) {

        return espacioService.listarPorSede(idSede);
    }

    @GetMapping("/tipo/{idTipo}")
    public List<EspacioDeportivo> listarPorTipo(
            @PathVariable Long idTipo) {

        return espacioService.listarPorTipo(idTipo);
    }

    @GetMapping("/estado/{estado}")
    public List<EspacioDeportivo> listarPorEstado(
            @PathVariable String estado) {

        return espacioService.listarPorEstado(estado);
    }

    @GetMapping("/disponibles/sede/{idSede}")
    public List<EspacioDeportivo> listarDisponiblesPorSede(
            @PathVariable Long idSede) {

        return espacioService.listarDisponiblesPorSede(idSede);
    }

    @PostMapping
    public ResponseEntity<EspacioDeportivo> guardar(
            @RequestBody EspacioDeportivo espacio) {

        return ResponseEntity.ok(
                espacioService.guardar(espacio));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EspacioDeportivo> actualizar(
            @PathVariable Long id,
            @RequestBody EspacioDeportivo espacio) {

        return ResponseEntity.ok(
                espacioService.actualizar(id, espacio));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        espacioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{id}/foto")
    public ResponseEntity<?> subirFoto(
            @PathVariable Long id,
            @RequestParam("archivo") MultipartFile archivo) {

        try {

            EspacioDeportivo espacio = espacioService.buscarPorId(id)
                    .orElseThrow(() ->
                        new RuntimeException("Espacio deportivo no encontrado"));

            // Eliminar foto anterior
            if (espacio.getFoto() != null &&
                !espacio.getFoto().isEmpty()) {

                String rutaAnterior =
                        espacio.getFoto()
                               .replace("/uploads/", "uploads/");

                Files.deleteIfExists(
                        Paths.get(rutaAnterior)
                );
            }

            String nombreArchivo =
                    UUID.randomUUID() +
                    "_" +
                    archivo.getOriginalFilename();

            Path carpeta = Paths.get("uploads/espacios");

            Files.createDirectories(carpeta);

            Path rutaArchivo =
                    carpeta.resolve(nombreArchivo);

            Files.copy(
                    archivo.getInputStream(),
                    rutaArchivo,
                    StandardCopyOption.REPLACE_EXISTING
            );

            espacio.setFoto(
                    "/uploads/espacios/" + nombreArchivo
            );

            espacioService.guardar(espacio);

            return ResponseEntity.ok(espacio);

        } catch (Exception e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}/foto")
    public ResponseEntity<?> eliminarFoto(
            @PathVariable Long id) {

        try {

            EspacioDeportivo espacio =
                    espacioService.buscarPorId(id)
                    .orElseThrow(() ->
                        new RuntimeException(
                            "Espacio deportivo no encontrado"));

            if (espacio.getFoto() != null &&
                !espacio.getFoto().isEmpty()) {

                String ruta =
                        espacio.getFoto()
                               .replace("/uploads/", "uploads/");

                Files.deleteIfExists(
                        Paths.get(ruta)
                );
            }

            espacio.setFoto(null);

            espacioService.guardar(espacio);

            Map<String, String> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Foto eliminada correctamente");

            return ResponseEntity.ok(respuesta);

        } catch (Exception e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}
