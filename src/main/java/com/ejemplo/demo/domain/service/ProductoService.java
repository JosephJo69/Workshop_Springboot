package com.ejemplo.demo.domain.service;

import com.ejemplo.demo.api.dto.ProductoDTO;
import com.ejemplo.demo.domain.model.Categoria;
import com.ejemplo.demo.domain.model.Producto;
import com.ejemplo.demo.domain.repository.CategoriaRepository;
import com.ejemplo.demo.domain.repository.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional
    public ProductoDTO crear(ProductoDTO dto) {
      
        Categoria cat = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new EntityNotFoundException("No existe la categoría " + dto.categoriaId()));
        
     
        Producto nuevoProducto = new Producto();
        nuevoProducto.setSku(dto.sku());      
        nuevoProducto.setNombre(dto.nombre()); 
        nuevoProducto.setPrecio(dto.precio()); 
        nuevoProducto.setCategoria(cat);
        
       
        Producto guardado = productoRepository.save(nuevoProducto);
        
        return new ProductoDTO(
            guardado.getId(), 
            guardado.getSku(), 
            guardado.getNombre(), 
            guardado.getPrecio(), 
            cat.getId()
        );
    }

    @Transactional(readOnly = true)
    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }
}