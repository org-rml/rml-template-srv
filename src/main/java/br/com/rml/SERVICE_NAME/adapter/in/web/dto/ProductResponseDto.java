package br.com.rml.SERVICE_NAME.adapter.in.web.dto;

import java.math.BigDecimal;

/**
 * DTO de saída — record Java 21. Contrato de resposta para o cliente HTTP. O
 * domínio nunca enxerga esta classe.
 */
public record ProductResponseDto(Long id, String name, BigDecimal price, boolean active) {
}
