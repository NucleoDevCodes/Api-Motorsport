package devCodes.Zerphyis.ApiMotorsport.Infra.Controllers;

import devCodes.Zerphyis.ApiMotorsport.Application.Records.Carro.DataCarroRequest;
import devCodes.Zerphyis.ApiMotorsport.Application.Records.Carro.DataCarroResponse;
import devCodes.Zerphyis.ApiMotorsport.Application.Services.ServiceCarro;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/carros")
@RequiredArgsConstructor
public class ControllerCarro {
    private final ServiceCarro service;

    @GetMapping
    public CompletableFuture<ResponseEntity<List<DataCarroResponse>>> findAll() {
        return service.findAll()
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<DataCarroResponse>> findById(@PathVariable Long id) {
        return service.findById(id)
                .thenApply(ResponseEntity::ok);
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<DataCarroResponse>> create(@Valid @RequestBody DataCarroRequest dto) {
        return service.create(dto)
                .thenApply(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));
    }

    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<DataCarroResponse>> update(@PathVariable Long id,
                                                                       @Valid @RequestBody DataCarroRequest dto) {
        return service.update(id, dto)
                .thenApply(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Void>> delete(@PathVariable Long id) {
        return service.delete(id)
                .thenApply(v -> ResponseEntity.noContent().build());
    }
}
