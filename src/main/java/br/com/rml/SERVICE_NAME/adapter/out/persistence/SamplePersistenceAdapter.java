package br.com.rml.SERVICE_NAME.adapter.out.persistence;

import br.com.rml.SERVICE_NAME.domain.model.Sample;
import br.com.rml.SERVICE_NAME.domain.port.out.SampleRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * TODO: Renomear para o adapter real. Ex: UserPersistenceAdapter
 *
 * Adapter OUT — Persistência.
 * Implementa o port OUT do domínio usando JPA.
 * Converte entre entidade JPA e modelo de domínio via mapper.
 * O domínio nunca conhece este adapter — só conhece o port (interface).
 */
@Component
@RequiredArgsConstructor
public class SamplePersistenceAdapter implements SampleRepositoryPort {

    private final SampleJpaRepository jpaRepository;
    private final SamplePersistenceMapper mapper;

    @Override
    public Optional<Sample> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Sample> findAll() {
        return mapper.toDomainList(jpaRepository.findAll());
    }

    @Override
    public Sample save(Sample sample) {
        SampleEntity entity = mapper.toEntity(sample);
        return mapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }
}
