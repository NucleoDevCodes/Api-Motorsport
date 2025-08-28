package devCodes.Zerphyis.ApiMotorsport.Infra.Controllers;

import devCodes.Zerphyis.ApiMotorsport.Application.Records.Upload.ResponseUpload;
import devCodes.Zerphyis.ApiMotorsport.Application.Services.ServiceUpload;
import devCodes.Zerphyis.ApiMotorsport.Infra.Exceptions.BadRequestException;
import devCodes.Zerphyis.ApiMotorsport.Infra.Exceptions.FileUploadException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

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
    void uploadFileHappy() {
        when(service.saveFile(file)).thenReturn(CompletableFuture.completedFuture(responseUpload));

        ResponseEntity<ResponseUpload> result = controller.uploadFile(file).join();

        assertEquals(201, result.getStatusCodeValue());
        assertEquals(responseUpload, result.getBody());
        verify(service, times(1)).saveFile(file);
    }

    @Test
    void uploadFileSad() {
        when(service.saveFile(file)).thenReturn(
                CompletableFuture.failedFuture(new FileUploadException("Falha no upload"))
        );

        CompletableFuture<ResponseEntity<ResponseUpload>> future = controller.uploadFile(file);

        assertThrows(FileUploadException.class, future::join);
    }

    @Test
    void getUploadHappy() {
        when(service.findById(1L)).thenReturn(CompletableFuture.completedFuture(responseUpload));

        ResponseEntity<ResponseUpload> result = controller.getUpload(1L).join();

        assertEquals(200, result.getStatusCodeValue());
        assertEquals(responseUpload, result.getBody());
    }

    @Test
    void getUploadSad() {
        when(service.findById(1L)).thenReturn(
                CompletableFuture.failedFuture(new BadRequestException("Arquivo não encontrado"))
        );

        CompletableFuture<ResponseEntity<ResponseUpload>> future = controller.getUpload(1L);

        assertThrows(BadRequestException.class, future::join);
    }

    @Test
    void getAllUploadsHappy() {
        ResponseUpload response2 = new ResponseUpload(2L, "file2.jpg", "/uploads/file2.jpg", "image/jpeg", 200L);
        when(service.findAll()).thenReturn(CompletableFuture.completedFuture(List.of(responseUpload, response2)));

        ResponseEntity<List<ResponseUpload>> result = controller.getAllUploads().join();

        assertEquals(200, result.getStatusCodeValue());
        assertEquals(2, result.getBody().size());
    }

    @Test
    void updateUploadHappy() {
        when(service.updateFile(1L, file)).thenReturn(CompletableFuture.completedFuture(responseUpload));

        ResponseEntity<ResponseUpload> result = controller.updateUpload(1L, file).join();

        assertEquals(200, result.getStatusCodeValue());
        assertEquals(responseUpload, result.getBody());
    }

    @Test
    void updateUploadSad() {
        when(service.updateFile(1L, file)).thenReturn(
                CompletableFuture.failedFuture(new BadRequestException("Arquivo não encontrado"))
        );

        CompletableFuture<ResponseEntity<ResponseUpload>> future = controller.updateUpload(1L, file);

        assertThrows(BadRequestException.class, future::join);
    }

    @Test
    void deleteUploadHappy() {
        when(service.delete(1L)).thenReturn(CompletableFuture.completedFuture(null));

        ResponseEntity<Void> result = controller.deleteUpload(1L).join();

        assertEquals(204, result.getStatusCodeValue());
        verify(service, times(1)).delete(1L);
    }

    @Test
    void deleteUploadSad() {
        when(service.delete(1L)).thenReturn(
                CompletableFuture.failedFuture(new BadRequestException("Arquivo não encontrado"))
        );

        CompletableFuture<ResponseEntity<Void>> future = controller.deleteUpload(1L);

        assertThrows(BadRequestException.class, future::join);
    }
}
