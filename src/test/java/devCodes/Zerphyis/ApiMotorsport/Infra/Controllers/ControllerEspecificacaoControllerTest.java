package devCodes.Zerphyis.ApiMotorsport.Infra.Controllers;

import devCodes.Zerphyis.ApiMotorsport.Application.Records.Especificacao.DataEspecificacaoTecnicaRequest;
import devCodes.Zerphyis.ApiMotorsport.Application.Records.Especificacao.DataEspecificacaoTecnicaResponse;
import devCodes.Zerphyis.ApiMotorsport.Application.Services.ServiceEspecificacaoTecnica;
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

class ControllerEspecificacaoControllerTest {

    @Mock
    private ServiceEspecificacaoTecnica service;

    @InjectMocks
    private ControllerEspecificacaoController controller;

    private DataEspecificacaoTecnicaRequest request;
    private DataEspecificacaoTecnicaResponse response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        request = new DataEspecificacaoTecnicaRequest(1L, "Motor", "2.0 Turbo");

        response = new DataEspecificacaoTecnicaResponse(
                "Motor",
                "2.0 Turbo",
                "Modelo X",
                "Carro Esportivo",
                "Motor turbo potente",
                "http://imagem.com/motor.jpg"
        );
    }

    @Test
    void createHappy() {
        when(service.create(request)).thenReturn(CompletableFuture.completedFuture(response));

        ResponseEntity<DataEspecificacaoTecnicaResponse> result = controller.create(request).join();

        assertEquals(201, result.getStatusCodeValue());
        assertEquals(response, result.getBody());
        verify(service, times(1)).create(request);
    }

    @Test
    void createSad() {
        when(service.create(request)).thenReturn(
                CompletableFuture.failedFuture(new NotFoundException("Erro ao criar especificação"))
        );

        CompletableFuture<ResponseEntity<DataEspecificacaoTecnicaResponse>> future = controller.create(request);

        CompletionException exception = assertThrows(CompletionException.class, future::join);
        assertTrue(exception.getCause() instanceof NotFoundException);
        verify(service, times(1)).create(request);
    }

    @Test
    void updateHappy() {
        when(service.update(1L, request)).thenReturn(CompletableFuture.completedFuture(response));

        ResponseEntity<DataEspecificacaoTecnicaResponse> result = controller.update(1L, request).join();

        assertEquals(200, result.getStatusCodeValue());
        assertEquals(response, result.getBody());
        verify(service, times(1)).update(1L, request);
    }

    @Test
    void updateSad() {
        when(service.update(99L, request)).thenReturn(
                CompletableFuture.failedFuture(new EntityNotFoundException("Não encontrada especificação"))
        );

        CompletableFuture<ResponseEntity<DataEspecificacaoTecnicaResponse>> future = controller.update(99L, request);

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
        verify(service, times(1)).delete(1L);
    }

    @Test
    void deleteSad() {
        when(service.delete(99L)).thenReturn(
                CompletableFuture.failedFuture(new EntityNotFoundException("Não encontrada"))
        );

        CompletableFuture<ResponseEntity<Void>> future = controller.delete(99L);

        CompletionException exception = assertThrows(CompletionException.class, future::join);
        assertTrue(exception.getCause() instanceof EntityNotFoundException);
        verify(service, times(1)).delete(99L);
    }

    @Test
    void findByIdHappy() {
        when(service.findById(1L)).thenReturn(CompletableFuture.completedFuture(response));

        ResponseEntity<DataEspecificacaoTecnicaResponse> result = controller.findById(1L).join();

        assertEquals(200, result.getStatusCodeValue());
        assertEquals(response, result.getBody());
        verify(service, times(1)).findById(1L);
    }

    @Test
    void findByIdSad() {
        when(service.findById(99L)).thenReturn(
                CompletableFuture.failedFuture(new EntityNotFoundException("Especificação não encontrada"))
        );

        CompletableFuture<ResponseEntity<DataEspecificacaoTecnicaResponse>> future = controller.findById(99L);

        CompletionException exception = assertThrows(CompletionException.class, future::join);
        assertTrue(exception.getCause() instanceof EntityNotFoundException);
        verify(service, times(1)).findById(99L);
    }
}
