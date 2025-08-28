package devCodes.Zerphyis.ApiMotorsport.Application.Services;

import devCodes.Zerphyis.ApiMotorsport.Application.Records.Carro.DataCarroRequest;
import devCodes.Zerphyis.ApiMotorsport.Application.Records.Carro.DataCarroResponse;
import devCodes.Zerphyis.ApiMotorsport.Infra.Exceptions.NotFoundException;
import devCodes.Zerphyis.ApiMotorsport.Model.Entity.Carro.Carro;
import devCodes.Zerphyis.ApiMotorsport.Model.Repositorys.CarroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ServiceCarro {

    private final CarroRepository repository;

    @Transactional
    @Async
    public CompletableFuture<DataCarroResponse> create(DataCarroRequest dto) {
        return CompletableFuture.supplyAsync(() -> {
            Carro carro = new Carro();
            carro.setNome(dto.nome());
            carro.setModelo(dto.modelo());
            carro.setDescricao(dto.descricao());
            carro.setPreco(dto.preco());
            carro.setImagemUrl(dto.imagemUrl());
            carro.setOrdem(dto.ordem());
            return toResponse(repository.save(carro));
        });
    }

    @Transactional
    @Async
    public CompletableFuture<DataCarroResponse> update(Long id, DataCarroRequest dto) {
        return CompletableFuture.supplyAsync(() -> {
            Carro carro = repository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Carro não encontrado com ID " + id));
            carro.setNome(dto.nome());
            carro.setModelo(dto.modelo());
            carro.setDescricao(dto.descricao());
            carro.setPreco(dto.preco());
            carro.setImagemUrl(dto.imagemUrl());
            carro.setOrdem(dto.ordem());
            return toResponse(repository.save(carro));
        });
    }

    @Async
    public CompletableFuture<List<DataCarroResponse>> findAll() {
        return CompletableFuture.supplyAsync(() ->
                repository.findAll().stream().map(this::toResponse).toList()
        );
    }


    @Async
    public CompletableFuture<DataCarroResponse> findById(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            Carro carro = repository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Carro não encontrado com ID " + id));
            return toResponse(carro);
        });
    }

    @Transactional
    public CompletableFuture<Void> delete(Long id) {
        return CompletableFuture.runAsync(() -> {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Carro não encontrado com ID " + id);
        }
        repository.deleteById(id);
    });
    }


    private DataCarroResponse toResponse(Carro carro) {
        return new DataCarroResponse(
                carro.getNome(),
                carro.getModelo(),
                carro.getDescricao(),
                carro.getPreco(),
                carro.getImagemUrl(),
                carro.getOrdem()
        );
    }
}
