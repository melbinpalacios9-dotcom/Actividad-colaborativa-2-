package com.veterinaria.controller;

import com.veterinaria.model.Mascota;
import com.veterinaria.repository.MascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {

    @Autowired
    private MascotaRepository mascotaRepository;

    // 1. Consultar todos los registros (GET)
    @GetMapping
    public List<Mascota> obtenerTodas() {
        return mascotaRepository.findAll();
    }

    // 2. Consultar un registro por identificador (GET por ID)
    @GetMapping("/{id}")
    public ResponseEntity<Mascota> obtenerPorId(@PathVariable Long id) {
        Optional<Mascota> mascota = mascotaRepository.findById(id);
        if (mascota.isPresent()) {
            return ResponseEntity.ok(mascota.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 3. Crear un nuevo registro (POST)
    @PostMapping
    public Mascota crearMascota(@RequestBody Mascota mascota) {
        return mascotaRepository.save(mascota);
    }

    // 4. Actualizar un registro existente (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Mascota> actualizarMascota(@PathVariable Long id, @RequestBody Mascota detallesMascota) {
        Optional<Mascota> mascotaExistente = mascotaRepository.findById(id);
        
        if (mascotaExistente.isPresent()) {
            Mascota mascota = mascotaExistente.get();
            mascota.setNombre(detallesMascota.getNombre());
            mascota.setEspecie(detallesMascota.getEspecie());
            mascota.setEdad(detallesMascota.getEdad());
            
            Mascota mascotaActualizada = mascotaRepository.save(mascota);
            return ResponseEntity.ok(mascotaActualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. Eliminar un registro (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMascota(@PathVariable Long id) {
        if (mascotaRepository.existsById(id)) {
            mascotaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}