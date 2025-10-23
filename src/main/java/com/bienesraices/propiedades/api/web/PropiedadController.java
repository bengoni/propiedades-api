package com.bienesraices.propiedades.api.web;


import com.bienesraices.propiedades.api.model.Propiedad;
import com.bienesraices.propiedades.api.repository.PropiedadJpaRepository; // <-- nuevo import
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/propiedades")
public class PropiedadController {

    private final PropiedadJpaRepository repo; // <-- cambia el tipo

    public PropiedadController(PropiedadJpaRepository repo) { // <-- cambia el ctor
        this.repo = repo;
    }

    @GetMapping("/health")
    public ResponseEntity<?> health() {
        return ResponseEntity.ok("{\"status\":\"UP\"}");
    }

    // LISTAR
    @GetMapping
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(repo.findAll());
    }

    // OBTENER POR ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // CREAR
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Propiedad p) {
        Propiedad saved = repo.save(p);
        return ResponseEntity
                .created(URI.create("/api/propiedades/" + saved.getId()))
                .body(saved);
    }

    // ACTUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Propiedad p) {
        return repo.findById(id).map(db -> {
            db.setDireccion(p.getDireccion());
            db.setPrecio(p.getPrecio());
            db.setTipo(p.getTipo());
            db.setDisponible(p.getDisponible());
            return ResponseEntity.ok(repo.save(db));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

