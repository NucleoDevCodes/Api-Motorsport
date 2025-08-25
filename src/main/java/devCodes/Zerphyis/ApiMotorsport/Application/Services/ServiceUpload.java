package devCodes.Zerphyis.ApiMotorsport.Application.Services;


import devCodes.Zerphyis.ApiMotorsport.Application.Records.Upload.ResponseUpload;
import devCodes.Zerphyis.ApiMotorsport.Infra.Exceptions.BadRequestException;
import devCodes.Zerphyis.ApiMotorsport.Infra.Exceptions.FileUploadException;
import devCodes.Zerphyis.ApiMotorsport.Model.Entity.Upload.Upload;
import devCodes.Zerphyis.ApiMotorsport.Model.Repositorys.UploadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceUpload {
    private static final String UPLOAD_DIR = "uploads/";
    private final UploadRepository repository;

    @Transactional
    public ResponseUpload saveFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new BadRequestException("Arquivo vazio");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !originalFilename.matches(".*\\.(png|jpg|jpeg)$")) {
            throw new BadRequestException("Formato inválido. Apenas PNG, JPG e JPEG são permitidos.");
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

        Upload upload = new Upload();
        upload.setNomeArquivo(fileName);
        upload.setUrl("/uploads/" + fileName);
        upload.setConteudo(file.getContentType());
        upload.setTamanho(file.getSize());

        return toResponse(repository.save(upload));
    }

    @Transactional(readOnly = true)
    public ResponseUpload findById(Long id) {
        Upload upload = repository.findById(id)
                .orElseThrow(() -> new BadRequestException("Arquivo não encontrado com ID: " + id));
        return toResponse(upload);
    }

    @Transactional(readOnly = true)
    public List<ResponseUpload> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    private ResponseUpload toResponse(Upload upload) {
        return new ResponseUpload(
                upload.getId(),
                upload.getNomeArquivo(),
                upload.getUrl(),
                upload.getConteudo(),
                upload.getTamanho()
        );
    }
}
