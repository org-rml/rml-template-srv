package br.com.rml.SERVICE_NAME.domain.service;

import br.com.rml.SERVICE_NAME.domain.model.Sample;
import br.com.rml.SERVICE_NAME.domain.port.in.ManageSampleUseCase;
import br.com.rml.SERVICE_NAME.domain.port.out.SampleRepositoryPort;
import br.com.rml.common.exception.NotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * TODO: Renomear para o service de domínio real. Ex: UserService, OrderService
 *
 * Domain Service — implementa os casos de uso (port IN).
 * Depende apenas de ports OUT, nunca de adapters concretos.
 * Contém as regras de negócio do domínio.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SampleService implements ManageSampleUseCase {

    // TODO: Substituir pelo port OUT real
    private final SampleRepositoryPort repository;

    @Override
    public Sample findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Sample", id));
    }

    @Override
    public List<Sample> findAll() {
        return repository.findAll();
    }

    @Override
    public Sample create(Sample sample) {
        // TODO: Adicionar regras de negócio antes de salvar
        return repository.save(sample);
    }

    @Override
    public Sample update(Long id, Sample sample) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Sample", id);
        }
        sample.setId(id);
        return repository.save(sample);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Sample", id);
        }
        repository.deleteById(id);
    }
}
