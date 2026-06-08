package com.rml.SERVICE_NAME.domain.port.out;

import com.rml.SERVICE_NAME.domain.model.Sample;

import java.util.List;
import java.util.Optional;

/**
 * TODO: Renomear para o port de saída real. Ex: UserRepositoryPort, SendEmailPort
 *
 * Port OUT (driven port) — define o que o domínio precisa do mundo externo.
 * Implementado pelos adapters de saída (persistence, messaging, etc).
 * O domínio depende desta interface, nunca da implementação concreta.
 */
public interface SampleRepositoryPort {

    Optional<Sample> findById(Long id);

    List<Sample> findAll();

    Sample save(Sample sample);

    void deleteById(Long id);

    boolean existsById(Long id);
}
