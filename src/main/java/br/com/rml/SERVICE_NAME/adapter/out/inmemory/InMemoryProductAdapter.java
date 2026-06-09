package br.com.rml.SERVICE_NAME.adapter.out.inmemory;

import br.com.rml.SERVICE_NAME.domain.model.Product;
import br.com.rml.SERVICE_NAME.domain.port.out.ProductRepositoryPort;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Adapter OUT — implementação in-memory do ProductRepositoryPort.
 *
 * Este é o ponto-chave da arquitetura hexagonal: O ProductService não sabe que
 * existe esta classe. Ele só conhece a interface ProductRepositoryPort.
 *
 * Se amanhã você quiser trocar por PostgreSQL, MongoDB ou qualquer outro banco:
 * 1. Crie um novo adapter (ex: JpaProductAdapter implements
 * ProductRepositoryPort) 2. Remove o @Component daqui 3. O ProductService não
 * muda uma linha.
 */
@Component
public class InMemoryProductAdapter implements ProductRepositoryPort {

	private final Map<Long, Product> store = new ConcurrentHashMap<>();
	private final AtomicLong sequence = new AtomicLong(1);

	@Override
	public Optional<Product> findById(Long id) {
		return Optional.ofNullable(store.get(id));
	}

	@Override
	public List<Product> findAll() {
		return List.copyOf(store.values());
	}

	@Override
	public Product save(Product product) {
		Long id = product.id() != null ? product.id() : sequence.getAndIncrement();
		Product saved = product.withId(id);
		store.put(id, saved);
		return saved;
	}

	@Override
	public void deleteById(Long id) {
		store.remove(id);
	}

	@Override
	public boolean existsById(Long id) {
		return store.containsKey(id);
	}
}
