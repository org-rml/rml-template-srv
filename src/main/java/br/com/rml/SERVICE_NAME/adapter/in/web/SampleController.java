package br.com.rml.SERVICE_NAME.adapter.in.web;

import br.com.rml.SERVICE_NAME.adapter.in.web.dto.SampleRequest;
import br.com.rml.SERVICE_NAME.adapter.in.web.dto.SampleResponse;
import br.com.rml.SERVICE_NAME.adapter.in.web.mapper.SampleWebMapper;
import br.com.rml.SERVICE_NAME.domain.port.in.ManageSampleUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TODO: Renomear para o controller real. Ex: UserController, OrderController
 *
 * Adapter IN — Web (REST Controller)
 * Recebe requisições HTTP, converte para modelo de domínio via mapper,
 * chama o use case e retorna o response.
 * Não contém regras de negócio.
 */
@RestController
@RequestMapping("/api/samples")
@RequiredArgsConstructor
public class SampleController {

    private final ManageSampleUseCase useCase;
    private final SampleWebMapper mapper;

    @GetMapping("/{id}")
    public ResponseEntity<SampleResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(useCase.findById(id)));
    }

    @GetMapping
    public ResponseEntity<List<SampleResponse>> findAll() {
        return ResponseEntity.ok(mapper.toResponseList(useCase.findAll()));
    }

    @PostMapping
    public ResponseEntity<SampleResponse> create(@Valid @RequestBody SampleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toResponse(useCase.create(mapper.toDomain(request))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SampleResponse> update(@PathVariable Long id,
                                                  @Valid @RequestBody SampleRequest request) {
        return ResponseEntity.ok(mapper.toResponse(useCase.update(id, mapper.toDomain(request))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        useCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
