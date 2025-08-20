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


@Service
@RequiredArgsConstructor
public class ServiceEspecificacaoTecnica {

    private final EspecificacaoRepository especificacaoTecnicaRepository;
    private final CarroRepository carroRepository;

    public DataEspecificacaoTecnicaResponse criar(DataEspecificacaoTecnicaRequest dto) {
        Carro carro = carroRepository.findById(dto.carroId()).orElseThrow(() -> new NotFoundException("Carro não encontrado com id: " + dto.carroId()));

        EspecificacaoTecnica especificacao = new EspecificacaoTecnica();

        especificacao.setCarro(carro);
        especificacao.setTitulo(dto.titulo());
        especificacao.setValor(dto.valor());

        especificacao = especificacaoTecnicaRepository.save(especificacao);

        return toResponseDTO(especificacao);
    }

    public DataEspecificacaoTecnicaResponse atualizar(Long id,DataEspecificacaoTecnicaRequest dto){
        EspecificacaoTecnica especificacao = especificacaoTecnicaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Especificação não encontrada com id: " + id));

        Carro carro = carroRepository.findById(dto.carroId())
                .orElseThrow(() -> new NotFoundException("Carro não encontrado com id: " + dto.carroId()));

        especificacao.setCarro(carro);
        especificacao.setTitulo(dto.titulo());
        especificacao.setValor(dto.valor());

        especificacao = especificacaoTecnicaRepository.save(especificacao);

        return toResponseDTO(especificacao);
    }


    public DataEspecificacaoTecnicaResponse buscarPorId(Long id) {
        EspecificacaoTecnica especificacao = especificacaoTecnicaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Especificação não encontrada com id: " + id));

        return toResponseDTO(especificacao);
    }

    public void deletar(Long id){
        EspecificacaoTecnica especificacao=especificacaoTecnicaRepository.findById(id).orElseThrow(() -> new NotFoundException("Especificação não encontrada com id: " + id));

    especificacaoTecnicaRepository.delete(especificacao);
    }



    private DataEspecificacaoTecnicaResponse toResponseDTO(EspecificacaoTecnica especificacao) {
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