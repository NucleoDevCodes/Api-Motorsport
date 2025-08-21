package devCodes.Zerphyis.ApiMotorsport.Infra.Controllers;

import devCodes.Zerphyis.ApiMotorsport.Application.Services.ServiceUpload;
import devCodes.Zerphyis.ApiMotorsport.Model.Entity.Upload.Upload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class ControllerUpload {

    private final ServiceUpload service;

    @PostMapping
    public ResponseEntity<Upload> uploadFile(@RequestParam("file") MultipartFile file) {
        Upload upload = service.saveFile(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(upload);
    }
}
