package devCodes.Zerphyis.ApiMotorsport.Application.Services;

import devCodes.Zerphyis.ApiMotorsport.Application.Records.Upload.ResponseUpload;
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
import java.util.List;
import java.util.Optional;

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
    void testSaveFile() throws IOException {
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

        ResponseUpload saved = service.saveFile(file);

        assertNotNull(saved);
        assertEquals("123_image.png", saved.nomeArquivo());
        assertEquals("image/png", saved.conteudo());
        verify(repository, times(1)).save(any(Upload.class));
    }

    @Test
    void testSaveFileEmptyFile() {
        MultipartFile file = new MockMultipartFile("file", new byte[0]);
        assertThrows(BadRequestException.class, () -> service.saveFile(file));
        verify(repository, never()).save(any());
    }

    @Test
    void testSaveFileInvalidFormat() {
        MultipartFile file = new MockMultipartFile(
                "file", "document.pdf", "application/pdf", "dummy".getBytes()
        );
        assertThrows(BadRequestException.class, () -> service.saveFile(file));
        verify(repository, never()).save(any());
    }

    @Test
    void testSaveFileSad() throws IOException {
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(false);
        when(file.getOriginalFilename()).thenReturn("image.png");
        when(file.getInputStream()).thenThrow(new IOException("Erro de I/O"));
        when(file.getContentType()).thenReturn("image/png");
        when(file.getSize()).thenReturn(100L);

        assertThrows(FileUploadException.class, () -> service.saveFile(file));
        verify(repository, never()).save(any());
    }


    @Test
    void testFindById() {
        Upload upload = new Upload(1L, "file.png", "/uploads/file.png", "image/png", 100L);
        when(repository.findById(1L)).thenReturn(Optional.of(upload));

        ResponseUpload response = service.findById(1L);

        assertNotNull(response);
        assertEquals(1L, response.id());
    }

    @Test
    void testFindByIdSad() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(BadRequestException.class, () -> service.findById(1L));
    }

    @Test
    void testFindAll() {
        Upload upload1 = new Upload(1L, "file1.png", "/uploads/file1.png", "image/png", 100L);
        Upload upload2 = new Upload(2L, "file2.jpg", "/uploads/file2.jpg", "image/jpeg", 200L);

        when(repository.findAll()).thenReturn(List.of(upload1, upload2));

        List<ResponseUpload> uploads = service.findAll();

        assertEquals(2, uploads.size());
    }

    @Test
    void testFindAllSad() {
        when(repository.findAll()).thenReturn(List.of());

        List<ResponseUpload> uploads = service.findAll();

        assertNotNull(uploads);
        assertTrue(uploads.isEmpty());
    }

    @Test
    void testUpdateFile() throws IOException {
        MultipartFile file = new MockMultipartFile(
                "file", "new.png", "image/png", "dummy".getBytes()
        );
        Upload existing = new Upload(1L, "old.png", "/uploads/old.png", "image/png", 50L);
        Upload updated = new Upload(1L, "123_new.png", "/uploads/123_new.png", "image/png", file.getSize());

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any(Upload.class))).thenReturn(updated);

        ResponseUpload response = service.updateFile(1L, file);

        assertNotNull(response);
        assertEquals("123_new.png", response.nomeArquivo());
    }

    @Test
    void testUpdateFileSad() {
        MultipartFile file = new MockMultipartFile(
                "file", "new.png", "image/png", "dummy".getBytes()
        );
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> service.updateFile(1L, file));
    }

    @Test
    void testUpdateFileEmptyFile() {
        MultipartFile file = new MockMultipartFile("file", new byte[0]);
        Upload existing = new Upload(1L, "old.png", "/uploads/old.png", "image/png", 50L);

        when(repository.findById(1L)).thenReturn(Optional.of(existing));

        assertThrows(BadRequestException.class, () -> service.updateFile(1L, file));
    }

    @Test
    void testUpdateFileFailToSaveFile() throws IOException {
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(false);
        when(file.getOriginalFilename()).thenReturn("new.png");
        when(file.getContentType()).thenReturn("image/png");
        when(file.getSize()).thenReturn(200L);
        when(file.getInputStream()).thenThrow(new IOException("Erro de I/O"));

        Upload existing = new Upload(1L, "old.png", "/uploads/old.png", "image/png", 50L);
        when(repository.findById(1L)).thenReturn(Optional.of(existing));

        assertThrows(FileUploadException.class, () -> service.updateFile(1L, file));
        verify(repository, never()).save(any());
    }

    @Test
    void testDelete() throws IOException {
        Upload existing = new Upload(1L, "file.png", "/uploads/file.png", "image/png", 100L);
        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        doNothing().when(repository).delete(existing);

        assertDoesNotThrow(() -> service.delete(1L));
        verify(repository, times(1)).delete(existing);
    }

    @Test
    void testDeleteSad() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(BadRequestException.class, () -> service.delete(1L));
    }
}
