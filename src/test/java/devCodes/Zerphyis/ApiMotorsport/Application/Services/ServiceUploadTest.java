package devCodes.Zerphyis.ApiMotorsport.Application.Services;

import devCodes.Zerphyis.ApiMotorsport.Infra.Exceptions.BadRequestException;
import devCodes.Zerphyis.ApiMotorsport.Infra.Exceptions.FileUploadException;
import devCodes.Zerphyis.ApiMotorsport.Model.Entity.Upload.Upload;
import devCodes.Zerphyis.ApiMotorsport.Model.Repositorys.UploadRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ServiceUploadTest {
    @Mock
    private UploadRepository repository;

    @InjectMocks
    private ServiceUpload service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveFile_Success() throws IOException {
        MultipartFile file = new MockMultipartFile(
                "file", "image.png", "image/png", "dummy".getBytes()
        );

        Upload upload = new Upload();
        upload.setId(1L);
        upload.setNomeArquivo("123_image.png");
        upload.setUrl("/uploads/123_image.png");
        upload.setConteudo("image/png");
        upload.setTamanho(file.getSize());

        when(repository.save(any(Upload.class))).thenReturn(upload);

        Upload saved = service.saveFile(file);

        assertNotNull(saved);
        assertEquals("123_image.png", saved.getNomeArquivo());
        assertEquals("image/png", saved.getConteudo());
        verify(repository, times(1)).save(any(Upload.class));
    }

    @Test
    void testSaveFile_EmptyFile() {
        MultipartFile file = new MockMultipartFile("file", new byte[0]);

        assertThrows(BadRequestException.class, () -> service.saveFile(file));
        verify(repository, never()).save(any());
    }

    @Test
    void testSaveFile_InvalidFormat() {
        MultipartFile file = new MockMultipartFile(
                "file", "document.pdf", "application/pdf", "dummy".getBytes()
        );

        assertThrows(BadRequestException.class, () -> service.saveFile(file));
        verify(repository, never()).save(any());
    }

    @Test
    void testSaveFile_FailToSaveFile() throws IOException {
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(false);
        when(file.getOriginalFilename()).thenReturn("image.png");
        when(file.getInputStream()).thenThrow(new IOException("Erro de I/O"));
        when(file.getContentType()).thenReturn("image/png");
        when(file.getSize()).thenReturn(100L);

        assertThrows(FileUploadException.class, () -> service.saveFile(file));
        verify(repository, never()).save(any());
    }
}