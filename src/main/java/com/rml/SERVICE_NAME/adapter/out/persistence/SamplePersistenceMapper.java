package com.rml.SERVICE_NAME.adapter.out.persistence;

import com.rml.SERVICE_NAME.domain.model.Sample;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * TODO: Renomear para o mapper real. Ex: UserPersistenceMapper
 * Converte entre entidade JPA e modelo de domínio.
 */
@Mapper(componentModel = "spring")
public interface SamplePersistenceMapper {

    Sample toDomain(SampleEntity entity);

    SampleEntity toEntity(Sample domain);

    List<Sample> toDomainList(List<SampleEntity> entities);
}
