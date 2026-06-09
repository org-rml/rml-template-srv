package br.com.rml.SERVICE_NAME.adapter.in.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * DTO de entrada — record Java 21. Representa o corpo do JSON que chega via
 * HTTP. O domínio nunca enxerga esta classe.
 */
public record ProductRequestDto(@NotBlank(message = "name é obrigatório") String name,
		@NotNull(message = "price é obrigatório") @DecimalMin(value = "0.0", message = "price deve ser >= 0") BigDecimal price,
		boolean active) {
}
