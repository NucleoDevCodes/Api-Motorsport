package devCodes.Zerphyis.ApiMotorsport.Application.Services;

import devCodes.Zerphyis.ApiMotorsport.Application.Records.Conteudo.DataConteudoRequest;
import devCodes.Zerphyis.ApiMotorsport.Application.Records.Conteudo.DataConteudoResponse;
import devCodes.Zerphyis.ApiMotorsport.Infra.Exceptions.NotFoundException;
import devCodes.Zerphyis.ApiMotorsport.Model.Entity.Conteudo.Conteudo;
import devCodes.Zerphyis.ApiMotorsport.Model.Repositorys.ConteudoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ServiceConteudo {


    private final ConteudoRepository repository;

    @Transactional
    @Async
    public CompletableFuture<DataConteudoResponse> create(DataConteudoRequest dto) {
        return CompletableFuture.supplyAsync(() -> {
            Conteudo conteudo = new Conteudo();
            conteudo.setTitulo(dto.titulo());
            conteudo.setConteudo(dto.conteudo());
            conteudo.setImagemUrl(dto.imagemUrl());
            return toResponse(repository.save(conteudo));
        });
    }

    @Transactional
    @Async
    public CompletableFuture<DataConteudoResponse> update(Long id, DataConteudoRequest dto) {
        return CompletableFuture.supplyAsync(() -> {
            Conteudo conteudo = repository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Conteúdo não encontrado com ID " + id));
            conteudo.setTitulo(dto.titulo());
            conteudo.setConteudo(dto.conteudo());
            conteudo.setImagemUrl(dto.imagemUrl());
            return toResponse(repository.save(conteudo));
        });
    }

    @Async
    public CompletableFuture<DataConteudoResponse> findById(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            Conteudo conteudo = repository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Conteúdo não encontrado com ID " + id));
            return toResponse(conteudo);
        });
    }

    @Transactional
    public CompletableFuture<Void> delete(Long id) {
        return CompletableFuture.runAsync(() -> {
            if (!repository.existsById(id)) {
                throw new NotFoundException("Conteúdo não encontrado com ID " + id);
            }
            repository.deleteById(id);
        });
    }


    private DataConteudoResponse toResponse(Conteudo conteudo) {
        return new DataConteudoResponse(
                conteudo.getTitulo(),
                conteudo.getConteudo(),
                conteudo.getImagemUrl()
        );
    }
}

