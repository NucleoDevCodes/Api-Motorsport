package devCodes.Zerphyis.ApiMotorsport.Infra.Controllers;

import devCodes.Zerphyis.ApiMotorsport.Application.Services.ServiceUpload;
import lombok.RequiredArgsConstructor;
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

        private final ServiceUpload uploadService;

        @PostMapping
        public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
            String url = uploadService.uploadFile(file);
            return ResponseEntity.ok(Map.of("url", url));
        }
}
