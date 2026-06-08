package com.rml.SERVICE_NAME.adapter.in.web.mapper;

import com.rml.SERVICE_NAME.adapter.in.web.dto.SampleRequest;
import com.rml.SERVICE_NAME.adapter.in.web.dto.SampleResponse;
import com.rml.SERVICE_NAME.domain.model.Sample;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * TODO: Renomear para o mapper real. Ex: UserWebMapper
 * Converte entre DTO web (request/response) e modelo de domínio.
 */
@Mapper(componentModel = "spring")
public interface SampleWebMapper {

    Sample toDomain(SampleRequest request);

    SampleResponse toResponse(Sample domain);

    List<SampleResponse> toResponseList(List<Sample> domains);
}
