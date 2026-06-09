package br.com.rml.SERVICE_NAME.adapter.in.web;

import br.com.rml.SERVICE_NAME.adapter.in.web.dto.ProductRequestDto;
import br.com.rml.SERVICE_NAME.adapter.in.web.dto.ProductResponseDto;
import br.com.rml.SERVICE_NAME.adapter.in.web.mapper.ProductWebMapper;
import br.com.rml.SERVICE_NAME.domain.port.in.ManageProductUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Adapter IN — Web (REST Controller).
 *
 * Responsabilidade ÚNICA: traduzir HTTP ↔ domínio. 1. Recebe o request HTTP 2.
 * Converte para modelo de domínio via mapper 3. Chama o use case (port IN) —
 * nunca o service diretamente 4. Converte o resultado para DTO de response 5.
 * Retorna o HTTP response
 *
 * NÃO contém regras de negócio. Nunca.
 */
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

	// Depende da INTERFACE (port IN), nunca do ProductService diretamente
	private final ManageProductUseCase useCase;
	private final ProductWebMapper mapper;

	@GetMapping("/{id}")
	public ResponseEntity<ProductResponseDto> findById(@PathVariable Long id) {
		return ResponseEntity.ok(mapper.toResponse(useCase.findById(id)));
	}

	@GetMapping
	public ResponseEntity<List<ProductResponseDto>> findAll() {
		return ResponseEntity.ok(mapper.toResponse(useCase.findAll()));
	}

	@PostMapping
	public ResponseEntity<ProductResponseDto> create(@Valid @RequestBody ProductRequestDto request) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(mapper.toResponse(useCase.create(mapper.toDomain(request))));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProductResponseDto> update(@PathVariable Long id,
			@Valid @RequestBody ProductRequestDto request) {
		return ResponseEntity.ok(mapper.toResponse(useCase.update(id, mapper.toDomain(request))));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		useCase.delete(id);
		return ResponseEntity.noContent().build();
	}
}
