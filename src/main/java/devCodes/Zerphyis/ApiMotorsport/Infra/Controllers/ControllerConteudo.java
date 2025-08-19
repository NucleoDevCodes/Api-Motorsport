package devCodes.Zerphyis.ApiMotorsport.Infra.Controllers;

import devCodes.Zerphyis.ApiMotorsport.Application.Records.DataConteudoRequest;
import devCodes.Zerphyis.ApiMotorsport.Application.Records.DataConteudoResponse;
import devCodes.Zerphyis.ApiMotorsport.Application.Services.ServiceConteudo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sobre")
@RequiredArgsConstructor

public class ControllerConteudo {
    private final ServiceConteudo sobreService;

    @GetMapping("/{id}")
    public ResponseEntity<DataConteudoResponse> getConteudo(@PathVariable Long id) {
        return ResponseEntity.ok(sobreService.getConteudo(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataConteudoResponse> atualizarConteudo(
            @PathVariable Long id,
            @Valid @RequestBody DataConteudoRequest dto) {
        return ResponseEntity.ok(sobreService.atualizarConteudo(id, dto));
    }

    @PostMapping
    public ResponseEntity<DataConteudoResponse> adicionarConteudo(@Valid @RequestBody DataConteudoRequest dto) {
        return ResponseEntity.ok(sobreService.adicionarConteudo(dto));
    }
}

