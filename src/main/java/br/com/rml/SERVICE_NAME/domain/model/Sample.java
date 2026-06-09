package br.com.rml.SERVICE_NAME.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO: Renomear para o modelo de domínio real. Ex: User, Product, Client
 *
 * Modelo de domínio PURO — sem anotações de persistência (@Entity, @Table).
 * É o coração do hexágono, isolado de frameworks.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sample {

    // TODO: Adicionar campos do domínio
    private Long id;
    private String name;
}
