package devCodes.Zerphyis.ApiMotorsport.Infra.Controllers;

import devCodes.Zerphyis.ApiMotorsport.Application.Records.Conteudo.DataConteudoRequest;
import devCodes.Zerphyis.ApiMotorsport.Application.Records.Conteudo.DataConteudoResponse;
import devCodes.Zerphyis.ApiMotorsport.Application.Services.ServiceConteudo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/sobre")
@RequiredArgsConstructor

public class ControllerConteudo {
    private final ServiceConteudo service;

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<DataConteudoResponse>> findById(@PathVariable Long id) {
        return service.findById(id)
                .thenApply(ResponseEntity::ok);
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<DataConteudoResponse>> create(@Valid @RequestBody DataConteudoRequest dto) {
        return service.create(dto)
                .thenApply(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));
    }

    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<DataConteudoResponse>> update(@PathVariable Long id,
                                                                          @Valid @RequestBody DataConteudoRequest dto) {
        return service.update(id, dto)
                .thenApply(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Void>> delete(@PathVariable Long id) {
        return service.delete(id)
                .thenApply(v -> ResponseEntity.noContent().build());
    }

}

