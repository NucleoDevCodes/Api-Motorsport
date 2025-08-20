package devCodes.Zerphyis.ApiMotorsport.Application.Services;

import devCodes.Zerphyis.ApiMotorsport.Application.Records.Carro.DataCarroRequest;
import devCodes.Zerphyis.ApiMotorsport.Application.Records.Carro.DataCarroResponse;
import devCodes.Zerphyis.ApiMotorsport.Infra.Exceptions.NotFoundException;
import devCodes.Zerphyis.ApiMotorsport.Model.Entity.Carro.Carro;
import devCodes.Zerphyis.ApiMotorsport.Model.Repositorys.CarroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceCarro {

    private final CarroRepository repository;

    @Transactional
    public DataCarroResponse create(DataCarroRequest dto){
        Carro carro = new Carro();
        carro.setNome(dto.nome());
        carro.setModelo(dto.modelo());
        carro.setDescricao(dto.descricao());
        carro.setPreco(dto.preco());
        carro.setImagemUrl(dto.imagemUrl());
        carro.setOrdem(dto.ordem());
        return toResponse(repository.save(carro));
    }

    @Transactional
    public DataCarroResponse update(Long id, DataCarroRequest dto){
        Carro carro = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Carro não encontrado com ID " + id));
        carro.setNome(dto.nome());
        carro.setModelo(dto.modelo());
        carro.setDescricao(dto.descricao());
        carro.setPreco(dto.preco());
        carro.setImagemUrl(dto.imagemUrl());
        carro.setOrdem(dto.ordem());
        return toResponse(repository.save(carro));
    }

    public List<DataCarroResponse> findAll(){
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public DataCarroResponse findById(Long id){
        Carro carro = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Carro não encontrado com ID " + id));
        return toResponse(carro);
    }

    @Transactional
    public void delete(Long id){
        if(!repository.existsById(id)) {
            throw new NotFoundException("Carro não encontrado com ID " + id);
        }
        repository.deleteById(id);
    }

    private DataCarroResponse toResponse(Carro carro){
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
