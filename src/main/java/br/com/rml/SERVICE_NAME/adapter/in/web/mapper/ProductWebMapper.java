package br.com.rml.SERVICE_NAME.adapter.in.web.mapper;

import br.com.rml.SERVICE_NAME.adapter.in.web.dto.ProductRequestDto;
import br.com.rml.SERVICE_NAME.adapter.in.web.dto.ProductResponseDto;
import br.com.rml.SERVICE_NAME.domain.model.Product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Converte entre DTO web e modelo de domínio. MapStruct gera a implementação em
 * tempo de compilação. Como Product e os DTOs são records, MapStruct usa o
 * construtor canônico.
 */
@Mapper(componentModel = "spring")
public interface ProductWebMapper {

	@Mapping(target = "id", ignore = true)
	Product toDomain(ProductRequestDto request);

	ProductResponseDto toResponse(Product domain);

	List<ProductResponseDto> toResponse(List<Product> domains);
}
