package com.ejemplo.demo.api.dto;

import java.time.Instant;
import com.fasterxml.jackson.annotation.JsonFormat;

public record SaludoResponse(
        String mensaje,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "UTC")
        Instant timestamp
) {
}