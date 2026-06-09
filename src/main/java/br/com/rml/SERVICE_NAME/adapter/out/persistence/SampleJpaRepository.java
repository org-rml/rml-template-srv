package br.com.rml.SERVICE_NAME.adapter.out.persistence;

import br.com.rml.common.repository.base.BaseLongRepository;
import org.springframework.stereotype.Repository;

/**
 * TODO: Renomear para o JPA repository real. Ex: UserJpaRepository
 * Estende BaseLongRepository do rml-common.
 */
@Repository
public interface SampleJpaRepository extends BaseLongRepository<SampleEntity> {

    // TODO: Adicionar queries customizadas se necessário
    // Ex: Optional<SampleEntity> findByName(String name);
}
