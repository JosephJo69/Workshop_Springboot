package com.ejemplo.demo.api.controller;

import com.ejemplo.demo.api.dto.CategoriaDTO;
import com.ejemplo.demo.domain.model.Categoria;
import com.ejemplo.demo.domain.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

  
    @PostMapping
    public ResponseEntity<CategoriaDTO> crear(@Valid @RequestBody CategoriaDTO dto) {
        Categoria entity = new Categoria();
        entity.setNombre(dto.nombre());
        entity.setDescripcion(dto.descripcion());
        
        Categoria guardada = service.crear(entity);
        return new ResponseEntity<>(mapToDTO(guardada), HttpStatus.CREATED);
    }

   
    @GetMapping
    public List<CategoriaDTO> listar() {
        return service.listarTodas().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

   
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(mapToDTO(service.buscarPorId(id)));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> actualizar(@PathVariable Long id, @Valid @RequestBody CategoriaDTO dto) {
        Categoria existente = service.buscarPorId(id);
        existente.setNombre(dto.nombre());
        existente.setDescripcion(dto.descripcion());
        
        Categoria actualizada = service.crear(existente); 
        return ResponseEntity.ok(mapToDTO(actualizada));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build(); 
    }
   
    private CategoriaDTO mapToDTO(Categoria entidad) {
        return new CategoriaDTO(entidad.getId(), entidad.getNombre(), entidad.getDescripcion());
    }
}