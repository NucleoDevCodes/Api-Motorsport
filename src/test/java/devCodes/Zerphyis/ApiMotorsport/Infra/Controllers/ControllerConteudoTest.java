package devCodes.Zerphyis.ApiMotorsport.Infra.Controllers;

import devCodes.Zerphyis.ApiMotorsport.Application.Records.Conteudo.DataConteudoRequest;
import devCodes.Zerphyis.ApiMotorsport.Application.Records.Conteudo.DataConteudoResponse;
import devCodes.Zerphyis.ApiMotorsport.Application.Services.ServiceConteudo;
import devCodes.Zerphyis.ApiMotorsport.Infra.Exceptions.EntityNotFoundException;
import devCodes.Zerphyis.ApiMotorsport.Infra.Exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ControllerConteudoTest {

    @Mock
    private ServiceConteudo service;

    @InjectMocks
    private ControllerConteudo controller;

    private DataConteudoRequest request;
    private DataConteudoResponse response;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        request = new DataConteudoRequest("Título", "Conteúdo", "img.png");
        response = new DataConteudoResponse("Título", "Conteúdo", "img.png");
    }

    @Test
    void findByIdHappy() {
        when(service.findById(1L)).thenReturn(CompletableFuture.completedFuture(response));

        ResponseEntity<DataConteudoResponse> result = controller.findById(1L).join();

        assertEquals(200, result.getStatusCodeValue());
        assertEquals(response, result.getBody());
        verify(service, times(1)).findById(1L);
    }

    @Test
    void findByIdSad() {
        when(service.findById(99L)).thenReturn(
                CompletableFuture.failedFuture(new EntityNotFoundException("Conteúdo não encontrado"))
        );

        CompletableFuture<ResponseEntity<DataConteudoResponse>> future = controller.findById(99L);

        CompletionException exception = assertThrows(CompletionException.class, future::join);
        assertTrue(exception.getCause() instanceof EntityNotFoundException);
        verify(service, times(1)).findById(99L);
    }

    @Test
    void createHappy() {
        when(service.create(request)).thenReturn(CompletableFuture.completedFuture(response));

        ResponseEntity<DataConteudoResponse> result = controller.create(request).join();

        assertEquals(201, result.getStatusCodeValue());
        assertEquals(response, result.getBody());
        verify(service, times(1)).create(request);
    }

    @Test
    void createSad() {
        when(service.create(request)).thenReturn(
                CompletableFuture.failedFuture(new NotFoundException("Erro ao criar Conteúdo"))
        );

        CompletableFuture<ResponseEntity<DataConteudoResponse>> future = controller.create(request);

        CompletionException exception = assertThrows(CompletionException.class, future::join);
        assertTrue(exception.getCause() instanceof NotFoundException);
        verify(service, times(1)).create(request);
    }

    @Test
    void updateHappy() {
        when(service.update(1L, request)).thenReturn(CompletableFuture.completedFuture(response));

        ResponseEntity<DataConteudoResponse> result = controller.update(1L, request).join();

        assertEquals(200, result.getStatusCodeValue());
        assertEquals(response, result.getBody());
        verify(service, times(1)).update(1L, request);
    }

    @Test
    void updateSad() {
        when(service.update(99L, request)).thenReturn(
                CompletableFuture.failedFuture(new EntityNotFoundException("Conteúdo não encontrado"))
        );

        CompletableFuture<ResponseEntity<DataConteudoResponse>> future = controller.update(99L, request);

        CompletionException exception = assertThrows(CompletionException.class, future::join);
        assertTrue(exception.getCause() instanceof EntityNotFoundException);
        verify(service, times(1)).update(99L, request);
    }

    @Test
    void deleteHappy() {
        when(service.delete(1L)).thenReturn(CompletableFuture.completedFuture(null));

        ResponseEntity<Void> result = controller.delete(1L).join();

        assertEquals(204, result.getStatusCodeValue());
        assertNull(result.getBody());
        verify(service).delete(1L);
    }

    @Test
    void deleteSad() {
        when(service.delete(99L)).thenReturn(
                CompletableFuture.failedFuture(new EntityNotFoundException("Conteúdo não encontrado"))
        );

        CompletableFuture<ResponseEntity<Void>> future = controller.delete(99L);

        CompletionException exception = assertThrows(CompletionException.class, future::join);
        assertTrue(exception.getCause() instanceof EntityNotFoundException);
        verify(service, times(1)).delete(99L);
    }
}
