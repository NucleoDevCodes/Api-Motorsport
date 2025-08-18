package devCodes.Zerphyis.ApiMotorsport.Application.Services;


import devCodes.Zerphyis.ApiMotorsport.Infra.Exceptions.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ServiceUpload {
    private static final String UPLOAD_DIR = "uploads/";

    public String uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new FileUploadException("Arquivo vazio");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !originalFilename.matches(".*\\.(png|jpg|jpeg)$")) {
            throw new FileUploadException("Formato inválido. Apenas PNG, JPG e JPEG são permitidos.");
        }

        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR));
        } catch (IOException e) {
            throw new FileUploadException("Erro ao criar diretório de upload.");
        }

        String fileName = System.currentTimeMillis() + "_" + originalFilename;
        Path filePath = Paths.get(UPLOAD_DIR + fileName);

        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new FileUploadException("Erro ao salvar arquivo.");
        }

        return "/uploads/" + fileName;
    }
}
