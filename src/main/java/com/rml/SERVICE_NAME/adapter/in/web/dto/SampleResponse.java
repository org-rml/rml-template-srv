package com.rml.SERVICE_NAME.adapter.in.web.dto;

import lombok.Builder;
import lombok.Data;

/**
 * TODO: Renomear e adaptar os campos para o domínio real.
 * DTO de saída do adapter web — contrato de resposta para o cliente.
 */
@Data
@Builder
public class SampleResponse {

    // TODO: Adicionar campos do response
    private Long id;
    private String name;
}
