package com.ejemplo.demo.api.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductoDTO(
    Long id,
    
    @NotBlank(message = "El SKU es obligatorio")
    String sku,
    
    @NotBlank(message = "El nombre es obligatorio")
    String nombre,
    
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    BigDecimal precio,
    
    @NotNull(message = "El ID de categoría es obligatorio")
    Long categoriaId
) {}