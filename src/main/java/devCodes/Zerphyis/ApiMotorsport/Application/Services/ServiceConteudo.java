package devCodes.Zerphyis.ApiMotorsport.Application.Services;

import devCodes.Zerphyis.ApiMotorsport.Application.Records.DataConteudoRequest;
import devCodes.Zerphyis.ApiMotorsport.Application.Records.DataConteudoResponse;
import devCodes.Zerphyis.ApiMotorsport.Infra.Exceptions.ResourceNotFoundException;
import devCodes.Zerphyis.ApiMotorsport.Model.Entity.Conteudo.Conteudo;
import devCodes.Zerphyis.ApiMotorsport.Model.Repositorys.ConteudoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceConteudo {

    private final ConteudoRepository repository;

    private static final Long SOBRE_ID = 1L;

    public DataConteudoResponse getConteudo() {
        Conteudo conteudo = repository.findById(SOBRE_ID)
                .orElseThrow(() -> new ResourceNotFoundException("Conteúdo da página 'Sobre Nós' não encontrado"));
        return toResponse(conteudo);
    }

    public DataConteudoResponse atualizarConteudo(DataConteudoRequest dto) {
        Conteudo conteudo = repository.findById(SOBRE_ID)
                .orElse(new Conteudo());
        conteudo.setId(SOBRE_ID);
        conteudo.setTitulo(dto.titulo());
        conteudo.setConteudo(dto.conteudo());
        conteudo.setImagemUrl(dto.imagemUrl());
        return toResponse(repository.save(conteudo));
    }

    private DataConteudoResponse toResponse(Conteudo conteudo) {
        return new DataConteudoResponse(
                conteudo.getConteudo(),
                conteudo.getTitulo(),
                conteudo.getImagemUrl()
        );
    }
}
