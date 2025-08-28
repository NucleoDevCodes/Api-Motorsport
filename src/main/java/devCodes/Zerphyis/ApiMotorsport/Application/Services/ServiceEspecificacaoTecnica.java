package devCodes.Zerphyis.ApiMotorsport.Application.Services;

import devCodes.Zerphyis.ApiMotorsport.Application.Records.Especificacao.DataEspecificacaoTecnicaRequest;
import devCodes.Zerphyis.ApiMotorsport.Application.Records.Especificacao.DataEspecificacaoTecnicaResponse;
import devCodes.Zerphyis.ApiMotorsport.Infra.Exceptions.NotFoundException;
import devCodes.Zerphyis.ApiMotorsport.Model.Entity.Carro.Carro;
import devCodes.Zerphyis.ApiMotorsport.Model.Entity.Especificao.EspecificacaoTecnica;
import devCodes.Zerphyis.ApiMotorsport.Model.Repositorys.CarroRepository;
import devCodes.Zerphyis.ApiMotorsport.Model.Repositorys.EspecificacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;


@Service
@RequiredArgsConstructor
public class ServiceEspecificacaoTecnica {


    private final EspecificacaoRepository especificacaoRepository;
    private final CarroRepository carroRepository;

    @Transactional
    @Async
    public CompletableFuture<DataEspecificacaoTecnicaResponse> create(DataEspecificacaoTecnicaRequest dto) {
        return CompletableFuture.supplyAsync(() -> {
            Carro carro = carroRepository.findById(dto.carroId())
                    .orElseThrow(() -> new NotFoundException("Carro não encontrado com ID " + dto.carroId()));

            EspecificacaoTecnica especificacao = new EspecificacaoTecnica();
            especificacao.setCarro(carro);
            especificacao.setTitulo(dto.titulo());
            especificacao.setValor(dto.valor());

            return toResponse(especificacaoRepository.save(especificacao));
        });
    }

    @Transactional
    @Async
    public CompletableFuture<DataEspecificacaoTecnicaResponse> update(Long id, DataEspecificacaoTecnicaRequest dto) {
        return CompletableFuture.supplyAsync(() -> {
            EspecificacaoTecnica especificacao = especificacaoRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Especificação não encontrada com ID " + id));

            Carro carro = carroRepository.findById(dto.carroId())
                    .orElseThrow(() -> new NotFoundException("Carro não encontrado com ID " + dto.carroId()));

            especificacao.setCarro(carro);
            especificacao.setTitulo(dto.titulo());
            especificacao.setValor(dto.valor());

            return toResponse(especificacaoRepository.save(especificacao));
        });
    }

    @Async
    public CompletableFuture<DataEspecificacaoTecnicaResponse> findById(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            EspecificacaoTecnica especificacao = especificacaoRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Especificação não encontrada com ID " + id));

            return toResponse(especificacao);
        });
    }

    @Transactional
    @Async
    public CompletableFuture<Void> delete(Long id) {
        return CompletableFuture.runAsync(() -> {
            EspecificacaoTecnica especificacao = especificacaoRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Especificação não encontrada com ID " + id));
            especificacaoRepository.delete(especificacao);
        });
    }

    private DataEspecificacaoTecnicaResponse toResponse(EspecificacaoTecnica especificacao) {
        Carro carro = especificacao.getCarro();
        return new DataEspecificacaoTecnicaResponse(
                especificacao.getTitulo(),
                especificacao.getValor(),
                carro.getModelo(),
                carro.getNome(),
                carro.getDescricao(),
                carro.getImagemUrl()
        );
    }
}
