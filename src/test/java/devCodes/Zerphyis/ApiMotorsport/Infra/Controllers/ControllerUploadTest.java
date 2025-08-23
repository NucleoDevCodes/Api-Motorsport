package devCodes.Zerphyis.ApiMotorsport.Infra.Controllers;

import devCodes.Zerphyis.ApiMotorsport.Application.Services.ServiceUpload;
import devCodes.Zerphyis.ApiMotorsport.Infra.Exceptions.FileUploadException;
import devCodes.Zerphyis.ApiMotorsport.Model.Entity.Upload.Upload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ControllerUploadTest {
    @Mock
    private ServiceUpload service;

    @Mock
    private MultipartFile file;

    @InjectMocks
    private ControllerUpload controller;

    private Upload upload;
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        upload = new Upload();
        upload.setId(1L);
        upload.setNomeArquivo("file.png");
        upload.setUrl("uploads/file.png");
        upload.setConteudo("conteudo do arquivo");
        upload.setTamanho(123L);
    }

    @Test
    void updateFile() {
        when(service.saveFile(file)).thenReturn(upload);

        ResponseEntity<Upload> result = controller.uploadFile(file);

        assertEquals(201, result.getStatusCodeValue());
        assertEquals(upload.getNomeArquivo(), result.getBody().getNomeArquivo());
        assertEquals(upload.getUrl(), result.getBody().getUrl());
        assertEquals(upload.getConteudo(), result.getBody().getConteudo());
        assertEquals(upload.getTamanho(), result.getBody().getTamanho());
    }

    @Test
    void updateFileSad() {
        when(service.saveFile(file)).thenThrow(new FileUploadException("Falha no upload"));

        assertThrows(FileUploadException.class, () -> controller.uploadFile(file));
    }
}