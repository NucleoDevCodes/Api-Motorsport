package devCodes.Zerphyis.ApiMotorsport.Infra.Controllers;

import devCodes.Zerphyis.ApiMotorsport.Application.Records.Especificacao.DataEspecificacaoTecnicaRequest;
import devCodes.Zerphyis.ApiMotorsport.Application.Records.Especificacao.DataEspecificacaoTecnicaResponse;
import devCodes.Zerphyis.ApiMotorsport.Application.Services.ServiceEspecificacaoTecnica;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/especificacao")
@RequiredArgsConstructor
public class ControllerEspecificacaoController {


    private final ServiceEspecificacaoTecnica service;

    @PostMapping
    public CompletableFuture<ResponseEntity<DataEspecificacaoTecnicaResponse>> create(
            @Valid @RequestBody DataEspecificacaoTecnicaRequest dto) {
        return service.create(dto)
                .thenApply(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));
    }

    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<DataEspecificacaoTecnicaResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody DataEspecificacaoTecnicaRequest dto) {
        return service.update(id, dto)
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<DataEspecificacaoTecnicaResponse>> findById(@PathVariable Long id) {
        return service.findById(id)
                .thenApply(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Void>> delete(@PathVariable Long id) {
        return service.delete(id)
                .thenApply(v -> ResponseEntity.noContent().build());
    }
}
