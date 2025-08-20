package devCodes.Zerphyis.ApiMotorsport.Infra.Controllers;

import devCodes.Zerphyis.ApiMotorsport.Application.Records.Especificacao.DataEspecificacaoTecnicaRequest;
import devCodes.Zerphyis.ApiMotorsport.Application.Records.Especificacao.DataEspecificacaoTecnicaResponse;
import devCodes.Zerphyis.ApiMotorsport.Application.Services.ServiceEspecificacaoTecnica;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/especificacao")
@RequiredArgsConstructor
public class ControllerEspecificacaoController {


    private final ServiceEspecificacaoTecnica service;

    @PostMapping
    public ResponseEntity<DataEspecificacaoTecnicaResponse> create(@Valid @RequestBody DataEspecificacaoTecnicaRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataEspecificacaoTecnicaResponse> update(@PathVariable Long id, @Valid @RequestBody DataEspecificacaoTecnicaRequest dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataEspecificacaoTecnicaResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
