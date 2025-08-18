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

    @GetMapping
    public ResponseEntity<DataConteudoResponse> getConteudo() {
        return ResponseEntity.ok(sobreService.getConteudo());
    }

    @PutMapping
    public ResponseEntity<DataConteudoResponse> atualizarConteudo(@Valid @RequestBody DataConteudoRequest dto) {
        return ResponseEntity.ok(sobreService.atualizarConteudo(dto));
    }
}
