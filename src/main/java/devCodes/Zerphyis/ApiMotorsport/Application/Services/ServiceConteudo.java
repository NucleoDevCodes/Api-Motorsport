package devCodes.Zerphyis.ApiMotorsport.Application.Services;

import devCodes.Zerphyis.ApiMotorsport.Application.Records.DataConteudoRequest;
import devCodes.Zerphyis.ApiMotorsport.Application.Records.DataConteudoResponse;
import devCodes.Zerphyis.ApiMotorsport.Infra.Exceptions.NotFoundException;
import devCodes.Zerphyis.ApiMotorsport.Infra.Exceptions.ResourceNotFoundException;
import devCodes.Zerphyis.ApiMotorsport.Model.Entity.Conteudo.Conteudo;
import devCodes.Zerphyis.ApiMotorsport.Model.Repositorys.ConteudoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceConteudo {

    private final ConteudoRepository repository;

    public DataConteudoResponse getConteudo(Long id) {
        Conteudo conteudo = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Conteúdo com ID " + id + " não encontrado"));
        return toResponse(conteudo);
    }

    public DataConteudoResponse atualizarConteudo(Long id, DataConteudoRequest dto) {
        Conteudo conteudo = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Conteúdo com ID " + id + " não encontrado"));

        conteudo.setTitulo(dto.titulo());
        conteudo.setConteudo(dto.conteudo());
        conteudo.setImagemUrl(dto.imagemUrl());

        return toResponse(repository.save(conteudo));
    }

    public DataConteudoResponse adicionarConteudo(DataConteudoRequest dto) {
        Conteudo conteudo = new Conteudo();
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
