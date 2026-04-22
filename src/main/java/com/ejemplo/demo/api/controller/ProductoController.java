package com.ejemplo.demo.api.controller;

import com.ejemplo.demo.api.dto.ProductoDTO;
import com.ejemplo.demo.domain.model.Producto;
import com.ejemplo.demo.domain.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProductoDTO> crear(@Valid @RequestBody ProductoDTO dto) {
        Producto p = new Producto();
        p.setSku(dto.sku());
        p.setNombre(dto.nombre());
        p.setPrecio(dto.precio());
        
        return new ResponseEntity<>(service.crear(p, dto.categoriaId()), HttpStatus.CREATED);
    }

    @GetMapping
    public List<ProductoDTO> listar() {
        return service.listarTodos().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private ProductoDTO mapToDTO(Producto p) {
        return new ProductoDTO(p.getId(), p.getSku(), p.getNombre(), p.getPrecio(), p.getCategoria().getId());
    }
}