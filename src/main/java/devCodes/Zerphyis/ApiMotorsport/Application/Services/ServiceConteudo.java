package devCodes.Zerphyis.ApiMotorsport.Application.Services;

import devCodes.Zerphyis.ApiMotorsport.Application.Records.Conteudo.DataConteudoRequest;
import devCodes.Zerphyis.ApiMotorsport.Application.Records.Conteudo.DataConteudoResponse;
import devCodes.Zerphyis.ApiMotorsport.Infra.Exceptions.NotFoundException;
import devCodes.Zerphyis.ApiMotorsport.Model.Entity.Conteudo.Conteudo;
import devCodes.Zerphyis.ApiMotorsport.Model.Repositorys.ConteudoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ServiceConteudo {


    private final ConteudoRepository repository;

    @Transactional
    public DataConteudoResponse create(DataConteudoRequest dto) {
        Conteudo conteudo = new Conteudo();
        conteudo.setTitulo(dto.titulo());
        conteudo.setConteudo(dto.conteudo());
        conteudo.setImagemUrl(dto.imagemUrl());
        return toResponse(repository.save(conteudo));
    }

    @Transactional
    public DataConteudoResponse update(Long id, DataConteudoRequest dto) {
        Conteudo conteudo = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Conteúdo não encontrado com ID " + id));
        conteudo.setTitulo(dto.titulo());
        conteudo.setConteudo(dto.conteudo());
        conteudo.setImagemUrl(dto.imagemUrl());
        return toResponse(repository.save(conteudo));
    }

    public DataConteudoResponse findById(Long id) {
        Conteudo conteudo = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Conteúdo não encontrado com ID " + id));
        return toResponse(conteudo);
    }

    @Transactional
    public void delete(Long id) {
        if(!repository.existsById(id)) {
            throw new NotFoundException("Conteúdo não encontrado com ID " + id);
        }
        repository.deleteById(id);
    }

    private DataConteudoResponse toResponse(Conteudo conteudo) {
        return new DataConteudoResponse(
                conteudo.getConteudo(),
                conteudo.getTitulo(),
                conteudo.getImagemUrl()
        );
    }
}
