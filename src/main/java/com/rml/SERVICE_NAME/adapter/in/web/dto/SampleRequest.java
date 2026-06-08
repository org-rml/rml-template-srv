package com.rml.SERVICE_NAME.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * TODO: Renomear e adaptar os campos para o domínio real.
 * DTO de entrada do adapter web — nunca expõe o modelo de domínio diretamente.
 */
@Data
public class SampleRequest {

    // TODO: Adicionar campos e validações
    @NotBlank(message = "name é obrigatório")
    private String name;
}
