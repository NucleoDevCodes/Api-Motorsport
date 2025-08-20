package devCodes.Zerphyis.ApiMotorsport.Application.Services;

import devCodes.Zerphyis.ApiMotorsport.Application.Records.Especificacao.DataEspecificacaoTecnicaRequest;
import devCodes.Zerphyis.ApiMotorsport.Application.Records.Especificacao.DataEspecificacaoTecnicaResponse;
import devCodes.Zerphyis.ApiMotorsport.Infra.Exceptions.NotFoundException;
import devCodes.Zerphyis.ApiMotorsport.Model.Entity.Carro.Carro;
import devCodes.Zerphyis.ApiMotorsport.Model.Entity.Especificao.EspecificacaoTecnica;
import devCodes.Zerphyis.ApiMotorsport.Model.Repositorys.CarroRepository;
import devCodes.Zerphyis.ApiMotorsport.Model.Repositorys.EspecificacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ServiceEspecificacaoTecnica {


    private final EspecificacaoRepository especificacaoRepository;
    private final CarroRepository carroRepository;

    @Transactional
    public DataEspecificacaoTecnicaResponse create(DataEspecificacaoTecnicaRequest dto) {
        Carro carro = carroRepository.findById(dto.carroId())
                .orElseThrow(() -> new NotFoundException("Carro não encontrado com ID " + dto.carroId()));

        EspecificacaoTecnica especificacao = new EspecificacaoTecnica();
        especificacao.setCarro(carro);
        especificacao.setTitulo(dto.titulo());
        especificacao.setValor(dto.valor());

        return toResponse(especificacaoRepository.save(especificacao));
    }

    @Transactional
    public DataEspecificacaoTecnicaResponse update(Long id, DataEspecificacaoTecnicaRequest dto) {
        EspecificacaoTecnica especificacao = especificacaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Especificação não encontrada com ID " + id));

        Carro carro = carroRepository.findById(dto.carroId())
                .orElseThrow(() -> new NotFoundException("Carro não encontrado com ID " + dto.carroId()));

        especificacao.setCarro(carro);
        especificacao.setTitulo(dto.titulo());
        especificacao.setValor(dto.valor());

        return toResponse(especificacaoRepository.save(especificacao));
    }

    public DataEspecificacaoTecnicaResponse findById(Long id) {
        EspecificacaoTecnica especificacao = especificacaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Especificação não encontrada com ID " + id));

        return toResponse(especificacao);
    }

    @Transactional
    public void delete(Long id) {
        EspecificacaoTecnica especificacao = especificacaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Especificação não encontrada com ID " + id));
        especificacaoRepository.delete(especificacao);
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
