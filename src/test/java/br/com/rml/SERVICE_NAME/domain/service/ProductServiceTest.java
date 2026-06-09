package br.com.rml.SERVICE_NAME.domain.service;

import br.com.rml.SERVICE_NAME.domain.model.Product;
import br.com.rml.SERVICE_NAME.domain.port.out.ProductRepositoryPort;
import br.com.rml.common.exception.NotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

/**
 * Teste unitário do ProductService.
 *
 * Conceito-chave: o @Mock simula o ProductRepositoryPort. O service NÃO sabe se
 * é banco, in-memory ou qualquer outra coisa. Isso prova que o domínio está
 * isolado — só regras de negócio são testadas aqui.
 */
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

	@Mock
	ProductRepositoryPort repository;

	@InjectMocks
	ProductService service;

	// -------------------------------------------------------------------------
	// create
	// -------------------------------------------------------------------------

	@Test
	void create_deveSalvar_quandoDadosValidos() {
		Product input = new Product(null, "Notebook", new BigDecimal("4500.00"), true);
		Product salvo = new Product(1L, "Notebook", new BigDecimal("4500.00"), true);

		when(repository.save(input)).thenReturn(salvo);

		Product result = service.create(input);

		assertThat(result.id()).isEqualTo(1L);
		assertThat(result.name()).isEqualTo("Notebook");
		verify(repository).save(input);
	}

	@Test
	void create_deveLancarExcecao_quandoPrecoNegativo() {
		Product input = new Product(null, "Notebook", new BigDecimal("-1.00"), true);

		assertThatThrownBy(() -> service.create(input)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Preço não pode ser negativo");

		// Regra: se lançou exceção, nunca deve ter chamado o repositório
		verifyNoInteractions(repository);
	}

	@Test
	void create_deveSalvar_quandoPrecoZero() {
		Product input = new Product(null, "Brinde", BigDecimal.ZERO, true);
		Product salvo = new Product(1L, "Brinde", BigDecimal.ZERO, true);

		when(repository.save(input)).thenReturn(salvo);

		Product result = service.create(input);

		assertThat(result.id()).isEqualTo(1L);
	}

	// -------------------------------------------------------------------------
	// findById
	// -------------------------------------------------------------------------

	@Test
	void findById_deveRetornarProduto_quandoExiste() {
		Product product = new Product(1L, "Monitor", new BigDecimal("1200.00"), true);

		when(repository.findById(1L)).thenReturn(Optional.of(product));

		Product result = service.findById(1L);

		assertThat(result.id()).isEqualTo(1L);
		assertThat(result.name()).isEqualTo("Monitor");
	}

	@Test
	void findById_deveLancarNotFoundException_quandoNaoExiste() {
		when(repository.findById(99L)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> service.findById(99L)).isInstanceOf(NotFoundException.class)
				.hasMessageContaining("99");
	}

	// -------------------------------------------------------------------------
	// findAll
	// -------------------------------------------------------------------------

	@Test
	void findAll_deveRetornarListaVazia_quandoNenhumProduto() {
		when(repository.findAll()).thenReturn(List.of());

		List<Product> result = service.findAll();

		assertThat(result).isEmpty();
	}

	@Test
	void findAll_deveRetornarTodos_quandoExistemProdutos() {
		List<Product> products = List.of(new Product(1L, "Mouse", new BigDecimal("150.00"), true),
				new Product(2L, "Teclado", new BigDecimal("300.00"), true));

		when(repository.findAll()).thenReturn(products);

		List<Product> result = service.findAll();

		assertThat(result).hasSize(2);
	}

	// -------------------------------------------------------------------------
	// update
	// -------------------------------------------------------------------------

	@Test
	void update_deveAtualizarComIdCorreto_quandoExiste() {
		Product input = new Product(null, "Novo Nome", new BigDecimal("999.00"), true);
		Product atualizado = new Product(1L, "Novo Nome", new BigDecimal("999.00"), true);

		when(repository.existsById(1L)).thenReturn(true);
		when(repository.save(atualizado)).thenReturn(atualizado);

		Product result = service.update(1L, input);

		// Garante que o id do path foi aplicado ao produto salvo
		assertThat(result.id()).isEqualTo(1L);
		verify(repository).save(atualizado);
	}

	@Test
	void update_deveLancarNotFoundException_quandoNaoExiste() {
		Product input = new Product(null, "X", BigDecimal.TEN, true);

		when(repository.existsById(99L)).thenReturn(false);

		assertThatThrownBy(() -> service.update(99L, input)).isInstanceOf(NotFoundException.class);

		verify(repository, never()).save(any());
	}

	// -------------------------------------------------------------------------
	// delete
	// -------------------------------------------------------------------------

	@Test
	void delete_deveDeletar_quandoExiste() {
		when(repository.existsById(1L)).thenReturn(true);

		service.delete(1L);

		verify(repository).deleteById(1L);
	}

	@Test
	void delete_deveLancarNotFoundException_quandoNaoExiste() {
		when(repository.existsById(99L)).thenReturn(false);

		assertThatThrownBy(() -> service.delete(99L)).isInstanceOf(NotFoundException.class);

		verify(repository, never()).deleteById(any());
	}
}
