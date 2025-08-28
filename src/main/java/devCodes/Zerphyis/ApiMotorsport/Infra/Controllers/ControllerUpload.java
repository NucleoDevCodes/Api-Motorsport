package devCodes.Zerphyis.ApiMotorsport.Infra.Controllers;

import devCodes.Zerphyis.ApiMotorsport.Application.Records.Upload.ResponseUpload;
import devCodes.Zerphyis.ApiMotorsport.Application.Services.ServiceUpload;
import devCodes.Zerphyis.ApiMotorsport.Model.Entity.Upload.Upload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class ControllerUpload {

    private final ServiceUpload service;

    @PostMapping
    public CompletableFuture<ResponseEntity<ResponseUpload>> uploadFile(@RequestParam("file") MultipartFile file) {
        return service.saveFile(file)
                .thenApply(upload -> ResponseEntity.status(HttpStatus.CREATED).body(upload));
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<ResponseUpload>> getUpload(@PathVariable Long id) {
        return service.findById(id)
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<List<ResponseUpload>>> getAllUploads() {
        return service.findAll()
                .thenApply(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<ResponseUpload>> updateUpload(@PathVariable Long id,
                                                                          @RequestParam("file") MultipartFile file) {
        return service.updateFile(id, file)
                .thenApply(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteUpload(@PathVariable Long id) {
        return service.delete(id)
                .thenApply(v -> ResponseEntity.noContent().build());
    }
}
