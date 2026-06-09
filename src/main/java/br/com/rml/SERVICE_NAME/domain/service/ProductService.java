package br.com.rml.SERVICE_NAME.domain.service;

import br.com.rml.SERVICE_NAME.domain.model.Product;
import br.com.rml.SERVICE_NAME.domain.port.in.ManageProductUseCase;
import br.com.rml.SERVICE_NAME.domain.port.out.ProductRepositoryPort;
import br.com.rml.common.exception.NotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Domain Service — implementa os casos de uso (Port IN).
 *
 * REGRAS DE NEGÓCIO vivem aqui: - Preço não pode ser negativo no cadastro -
 * Produto inexistente lança NotFoundException
 *
 * O que este service NÃO sabe: - Que existe HTTP, JSON, REST - Que existe
 * PostgreSQL, MongoDB ou qualquer banco - Que existe Spring Data JPA
 *
 * Ele só conhece: Product (domínio) e ProductRepositoryPort (interface que ele
 * mesmo definiu).
 */
@Service
@RequiredArgsConstructor
public class ProductService implements ManageProductUseCase {

	// Depende da INTERFACE (port OUT), nunca da implementação concreta
	private final ProductRepositoryPort repository;

	@Override
	public Product findById(Long id) {
		return repository.findById(id).orElseThrow(() -> new NotFoundException("Product", id));
	}

	@Override
	public List<Product> findAll() {
		return repository.findAll();
	}

	@Override
	public Product create(Product product) {
		// Regra de negócio: preço não pode ser negativo
		if (product.price().compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("Preço não pode ser negativo");
		}
		return repository.save(product);
	}

	@Override
	public Product update(Long id, Product product) {
		if (!repository.existsById(id)) {
			throw new NotFoundException("Product", id);
		}
		// Product é um record (imutável) — withId() cria nova instância com o id
		// correto
		return repository.save(product.withId(id));
	}

	@Override
	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new NotFoundException("Product", id);
		}
		repository.deleteById(id);
	}
}
