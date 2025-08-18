package devCodes.Zerphyis.ApiMotorsport.Infra.Controllers;

import devCodes.Zerphyis.ApiMotorsport.Application.Records.DataCarroRequest;
import devCodes.Zerphyis.ApiMotorsport.Application.Records.DataCarroResponse;
import devCodes.Zerphyis.ApiMotorsport.Application.Services.ServiceCarro;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carros")
@RequiredArgsConstructor
public class ControllerCarro {
    private final ServiceCarro carroService;

    @GetMapping
    public ResponseEntity<List<DataCarroResponse>> getAll() {
        return ResponseEntity.ok(carroService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataCarroResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(carroService.findByid(id));
    }

    @PostMapping
    public ResponseEntity<DataCarroResponse> create(@Valid @RequestBody DataCarroRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carroService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataCarroResponse> update(@PathVariable Long id, @Valid @RequestBody DataCarroRequest dto) {
        return ResponseEntity.ok(carroService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        carroService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
