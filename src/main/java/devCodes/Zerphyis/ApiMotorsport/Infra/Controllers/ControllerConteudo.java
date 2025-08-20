package devCodes.Zerphyis.ApiMotorsport.Infra.Controllers;

import devCodes.Zerphyis.ApiMotorsport.Application.Records.Conteudo.DataConteudoRequest;
import devCodes.Zerphyis.ApiMotorsport.Application.Records.Conteudo.DataConteudoResponse;
import devCodes.Zerphyis.ApiMotorsport.Application.Services.ServiceConteudo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sobre")
@RequiredArgsConstructor

public class ControllerConteudo {
    private final ServiceConteudo service;

    @GetMapping("/{id}")
    public ResponseEntity<DataConteudoResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<DataConteudoResponse> create(@Valid @RequestBody DataConteudoRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataConteudoResponse> update(@PathVariable Long id, @Valid @RequestBody DataConteudoRequest dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}

