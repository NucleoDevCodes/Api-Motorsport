package devCodes.Zerphyis.ApiMotorsport.Infra.Controllers;

import devCodes.Zerphyis.ApiMotorsport.Application.Records.Especificacao.DataEspecificacaoTecnicaRequest;
import devCodes.Zerphyis.ApiMotorsport.Application.Records.Especificacao.DataEspecificacaoTecnicaResponse;
import devCodes.Zerphyis.ApiMotorsport.Application.Services.ServiceEspecificacaoTecnica;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/especificacao")
@RequiredArgsConstructor
public class ControllerEspecificacaoController {

    private final ServiceEspecificacaoTecnica service;

    @PostMapping
    public ResponseEntity<DataEspecificacaoTecnicaResponse> criar(@Valid @RequestBody DataEspecificacaoTecnicaRequest dto) {
        return ResponseEntity.ok(service.criar(dto));

    }

    @PutMapping("/{id}")
    public ResponseEntity<DataEspecificacaoTecnicaResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody DataEspecificacaoTecnicaRequest dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataEspecificacaoTecnicaResponse> buscarId(@PathVariable Long id){
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
