package br.com.rml.SERVICE_NAME.adapter.in.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.rml.SERVICE_NAME.adapter.in.web.dto.ProductRequestDto;
import br.com.rml.SERVICE_NAME.adapter.in.web.dto.ProductResponseDto;
import br.com.rml.SERVICE_NAME.adapter.in.web.mapper.ProductWebMapper;
import br.com.rml.SERVICE_NAME.domain.model.Product;
import br.com.rml.SERVICE_NAME.domain.port.in.ManageProductUseCase;
import br.com.rml.SERVICE_NAME.infrastructure.config.SecurityConfig;
import br.com.rml.common.exception.NotFoundException;

/**
 * Teste de slice do ProductController.
 *
 * @WebMvcTest sobe apenas a camada web (sem banco, sem service real). O
 *             ManageProductUseCase é mockado — o controller não sabe que é um
 *             mock. Testa apenas: parsing HTTP, validações de campo, status
 *             codes e JSON de resposta.
 */
@WebMvcTest(ProductController.class)
@Import(SecurityConfig.class)
@WithMockUser
class ProductControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockitoBean
	ManageProductUseCase useCase;

	@MockitoBean
	ProductWebMapper mapper;

	// -------------------------------------------------------------------------
	// POST /api/products
	// -------------------------------------------------------------------------

	@Test
	void create_deveRetornar201_quandoDadosValidos() throws Exception {
		ProductRequestDto request = new ProductRequestDto("Notebook", new BigDecimal("4500"), true);
		Product domain = new Product(null, "Notebook", new BigDecimal("4500"), true);
		Product saved = new Product(1L, "Notebook", new BigDecimal("4500"), true);
		ProductResponseDto response = new ProductResponseDto(1L, "Notebook", new BigDecimal("4500"), true);

		when(mapper.toDomain(any())).thenReturn(domain);
		when(useCase.create(domain)).thenReturn(saved);
		when(mapper.toResponse(saved)).thenReturn(response);

		mockMvc.perform(post("/api/products").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.name").value("Notebook"));
	}

	@Test
	void create_deveRetornar400_quandoNameEmBranco() throws Exception {
		String body = """
				{ "name": "", "price": 100.00, "active": true }
				""";

		mockMvc.perform(post("/api/products").contentType(MediaType.APPLICATION_JSON).content(body))
				.andExpect(status().isBadRequest());
	}

	@Test
	void create_deveRetornar400_quandoPrecoNulo() throws Exception {
		String body = """
				{ "name": "Produto", "active": true }
				""";

		mockMvc.perform(post("/api/products").contentType(MediaType.APPLICATION_JSON).content(body))
				.andExpect(status().isBadRequest());
	}

	// -------------------------------------------------------------------------
	// GET /api/products/{id}
	// -------------------------------------------------------------------------

	@Test
	void findById_deveRetornar200_quandoExiste() throws Exception {
		Product product = new Product(1L, "Monitor", new BigDecimal("1200"), true);
		ProductResponseDto response = new ProductResponseDto(1L, "Monitor", new BigDecimal("1200"), true);

		when(useCase.findById(1L)).thenReturn(product);
		when(mapper.toResponse(product)).thenReturn(response);

		mockMvc.perform(get("/api/products/1")).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("Monitor"));
	}

	@Test
	void findById_deveRetornar404_quandoNaoExiste() throws Exception {
		when(useCase.findById(99L)).thenThrow(new NotFoundException("Product", 99L));

		mockMvc.perform(get("/api/products/99")).andExpect(status().isNotFound());
	}

	// -------------------------------------------------------------------------
	// GET /api/products
	// -------------------------------------------------------------------------

	@Test
	void findAll_deveRetornar200_comListaVazia() throws Exception {
		when(useCase.findAll()).thenReturn(List.of());
		// mapper.toResponseList retorna null por padrão no mock — suficiente para 200
		doReturn(List.of()).when(mapper).toResponse(ArgumentMatchers.<List<Product>>any());

		mockMvc.perform(get("/api/products")).andExpect(status().isOk()).andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").isEmpty());
	}

	// -------------------------------------------------------------------------
	// PUT /api/products/{id}
	// -------------------------------------------------------------------------

	@Test
	void update_deveRetornar200_quandoDadosValidos() throws Exception {
		ProductRequestDto request = new ProductRequestDto("Notebook Pro", new BigDecimal("5000"), true);
		Product domain = new Product(null, "Notebook Pro", new BigDecimal("5000"), true);
		Product updated = new Product(1L, "Notebook Pro", new BigDecimal("5000"), true);
		ProductResponseDto response = new ProductResponseDto(1L, "Notebook Pro", new BigDecimal("5000"), true);

		when(mapper.toDomain(any())).thenReturn(domain);
		when(useCase.update(1L, domain)).thenReturn(updated);
		when(mapper.toResponse(updated)).thenReturn(response);

		mockMvc.perform(put("/api/products/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("Notebook Pro"));
	}

	// -------------------------------------------------------------------------
	// DELETE /api/products/{id}
	// -------------------------------------------------------------------------

	@Test
	void delete_deveRetornar204_quandoExiste() throws Exception {
		mockMvc.perform(delete("/api/products/1")).andExpect(status().isNoContent());

		verify(useCase).delete(1L);
	}

	@Test
	void delete_deveRetornar404_quandoNaoExiste() throws Exception {
		Mockito.doThrow(new NotFoundException("Product", 99L)).when(useCase).delete(99L);

		mockMvc.perform(delete("/api/products/99")).andExpect(status().isNotFound());
	}
}
