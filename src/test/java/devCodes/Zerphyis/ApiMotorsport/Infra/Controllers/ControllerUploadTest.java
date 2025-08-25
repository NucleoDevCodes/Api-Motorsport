package devCodes.Zerphyis.ApiMotorsport.Infra.Controllers;

import devCodes.Zerphyis.ApiMotorsport.Application.Records.Upload.ResponseUpload;
import devCodes.Zerphyis.ApiMotorsport.Application.Services.ServiceUpload;
import devCodes.Zerphyis.ApiMotorsport.Infra.Exceptions.BadRequestException;
import devCodes.Zerphyis.ApiMotorsport.Infra.Exceptions.FileUploadException;
import devCodes.Zerphyis.ApiMotorsport.Model.Entity.Upload.Upload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ControllerUploadTest {
    @Mock
    private ServiceUpload service;

    @Mock
    private MultipartFile file;

    @InjectMocks
    private ControllerUpload controller;

    private ResponseUpload responseUpload;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        responseUpload = new ResponseUpload(
                1L,
                "file.png",
                "/uploads/file.png",
                "image/png",
                123L
        );
    }


    @Test
    void uploadFile() {
        when(service.saveFile(file)).thenReturn(responseUpload);

        ResponseEntity<ResponseUpload> result = controller.uploadFile(file);

        assertEquals(201, result.getStatusCodeValue());
        assertEquals(responseUpload, result.getBody());
        verify(service, times(1)).saveFile(file);
    }

    @Test
    void uploadFileFailure() {
        when(service.saveFile(file)).thenThrow(new FileUploadException("Falha no upload"));

        assertThrows(FileUploadException.class, () -> controller.uploadFile(file));
    }


    @Test
    void getUpload() {
        when(service.findById(1L)).thenReturn(responseUpload);

        ResponseEntity<ResponseUpload> result = controller.getUpload(1L);

        assertEquals(200, result.getStatusCodeValue());
        assertEquals(responseUpload, result.getBody());
    }

    @Test
    void getUploadSad() {
        when(service.findById(1L)).thenThrow(new BadRequestException("Arquivo não encontrado"));

        assertThrows(BadRequestException.class, () -> controller.getUpload(1L));
    }


    @Test
    void getAllUploads() {
        ResponseUpload response2 = new ResponseUpload(2L, "file2.jpg", "/uploads/file2.jpg", "image/jpeg", 200L);
        when(service.findAll()).thenReturn(List.of(responseUpload, response2));

        ResponseEntity<List<ResponseUpload>> result = controller.getAllUploads();

        assertEquals(200, result.getStatusCodeValue());
        assertEquals(2, result.getBody().size());
    }

}