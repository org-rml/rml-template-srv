package com.rml.SERVICE_NAME.domain.port.in;

import com.rml.SERVICE_NAME.domain.model.Sample;

import java.util.List;

/**
 * TODO: Renomear para o caso de uso real. Ex: ManageUserUseCase, CreateOrderUseCase
 *
 * Port IN (driving port) — define o que o domínio expõe para o mundo externo.
 * Implementado pelo domain service. Chamado pelos adapters de entrada (controllers).
 */
public interface ManageSampleUseCase {

    Sample findById(Long id);

    List<Sample> findAll();

    Sample create(Sample sample);

    Sample update(Long id, Sample sample);

    void delete(Long id);
}
