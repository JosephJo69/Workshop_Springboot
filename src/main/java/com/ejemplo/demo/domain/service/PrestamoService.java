package com.ejemplo.demo.domain.service; // Ojo: lo ideal es moverlo a .domain.service después

import com.ejemplo.demo.api.dto.PrestamoRequest;
import com.ejemplo.demo.api.dto.PrestamoResponse;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class PrestamoService {
    public PrestamoResponse calcularSimulacion(PrestamoRequest request) {
        double P = request.monto().doubleValue();
        double r = request.tasaAnual().doubleValue() / 12 / 100;
        int n = request.meses();

        // Fórmula: cuota = P * (r * (1 + r)^n) / ((1 + r)^n - 1)
        double cuota = P * (r * Math.pow(1 + r, n)) / (Math.pow(1 + r, n) - 1);
        
        BigDecimal totalPagar = BigDecimal.valueOf(cuota * n);
        BigDecimal interesTotal = totalPagar.subtract(request.monto());

        return new PrestamoResponse(
            BigDecimal.valueOf(cuota).setScale(2, RoundingMode.HALF_UP),
            interesTotal.setScale(2, RoundingMode.HALF_UP),
            totalPagar.setScale(2, RoundingMode.HALF_UP)
        );
    }
}