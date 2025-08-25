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


@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class ControllerUpload {

    private final ServiceUpload service;

    @PostMapping
    public ResponseEntity<ResponseUpload> uploadFile(@RequestParam("file") MultipartFile file) {
        ResponseUpload upload = service.saveFile(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(upload);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseUpload> getUpload(@PathVariable Long id) {
        ResponseUpload upload = service.findById(id);
        return ResponseEntity.ok(upload);
    }

    @GetMapping
    public ResponseEntity<List<ResponseUpload>> getAllUploads() {
        List<ResponseUpload> uploads = service.findAll();
        return ResponseEntity.ok(uploads);
    }

}
