package devCodes.Zerphyis.ApiMotorsport.Infra.Controllers;

import devCodes.Zerphyis.ApiMotorsport.Application.Records.Carro.DataCarroRequest;
import devCodes.Zerphyis.ApiMotorsport.Application.Records.Carro.DataCarroResponse;
import devCodes.Zerphyis.ApiMotorsport.Application.Services.ServiceCarro;
import devCodes.Zerphyis.ApiMotorsport.Infra.Exceptions.EntityNotFoundException;
import devCodes.Zerphyis.ApiMotorsport.Infra.Exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ControllerCarroTest {

    @Mock
    private ServiceCarro service;

    @InjectMocks
    private ControllerCarro controller;

    private DataCarroRequest request;
    private DataCarroResponse response;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        request = new DataCarroRequest(
                "Fusca",
                "Volkswagen",
                "Clássico 1970",
                BigDecimal.valueOf(50000),
                "http://imagem.com/fusca.jpg",
                1
        );
        response = new DataCarroResponse(
                "Fusca",
                "Volkswagen",
                "Clássico 1970",
                BigDecimal.valueOf(50000),
                "http://imagem.com/fusca.jpg",
                1
        );
    }

    @Test
    void findAllHappy() {
        when(service.findAll()).thenReturn(CompletableFuture.completedFuture(List.of(response)));

        ResponseEntity<List<DataCarroResponse>> resultado = controller.findAll().join();
        assertEquals(1, resultado.getBody().size());
        verify(service, times(1)).findAll();
    }

    @Test
    void findByIdHappy() {
        when(service.findById(1L)).thenReturn(CompletableFuture.completedFuture(response));

        ResponseEntity<DataCarroResponse> result = controller.findById(1L).join();

        assertEquals(response, result.getBody());
        verify(service, times(1)).findById(1L);
    }

    @Test
    void findByIdNotFound() {
        when(service.findById(99L)).thenReturn(
                CompletableFuture.failedFuture(new EntityNotFoundException("Carro não encontrado"))
        );

        CompletableFuture<ResponseEntity<DataCarroResponse>> future = controller.findById(99L);

        assertThrows(EntityNotFoundException.class, future::join);
        verify(service, times(1)).findById(99L);
    }

    @Test
    void createHappy() {
        when(service.create(request)).thenReturn(CompletableFuture.completedFuture(response));

        ResponseEntity<DataCarroResponse> result = controller.create(request).join();

        assertEquals(201, result.getStatusCodeValue());
        assertEquals(response, result.getBody());
        verify(service, times(1)).create(request);
    }

    @Test
    void createSad() {
        when(service.create(request)).thenReturn(
                CompletableFuture.failedFuture(new NotFoundException("Erro ao salvar carro"))
        );

        CompletableFuture<ResponseEntity<DataCarroResponse>> future = controller.create(request);

        assertThrows(NotFoundException.class, future::join);
        verify(service, times(1)).create(request);
    }

    @Test
    void updateHappy() {
        when(service.update(1L, request)).thenReturn(CompletableFuture.completedFuture(response));

        ResponseEntity<DataCarroResponse> result = controller.update(1L, request).join();

        assertEquals(response, result.getBody());
        verify(service, times(1)).update(1L, request);
    }

    @Test
    void updateSad() {
        when(service.update(99L, request)).thenReturn(
                CompletableFuture.failedFuture(new EntityNotFoundException("Carro não encontrado"))
        );

        CompletableFuture<ResponseEntity<DataCarroResponse>> future = controller.update(99L, request);

        assertThrows(EntityNotFoundException.class, future::join);
        verify(service, times(1)).update(99L, request);
    }

    @Test
    void deleteHappy() {
        when(service.delete(1L)).thenReturn(CompletableFuture.completedFuture(null));

        ResponseEntity<Void> result = controller.delete(1L).join();

        assertEquals(204, result.getStatusCodeValue());
        verify(service).delete(1L);
    }

    @Test
    void deleteSad() {
        when(service.delete(99L)).thenReturn(
                CompletableFuture.failedFuture(new NotFoundException("Carro não encontrado"))
        );

        CompletableFuture<ResponseEntity<Void>> future = controller.delete(99L);

        assertThrows(NotFoundException.class, future::join);
        verify(service, times(1)).delete(99L);
    }
}
