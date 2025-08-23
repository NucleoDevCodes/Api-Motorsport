package devCodes.Zerphyis.ApiMotorsport.Infra.Controllers;

import devCodes.Zerphyis.ApiMotorsport.Application.Records.Carro.DataCarroRequest;
import devCodes.Zerphyis.ApiMotorsport.Application.Records.Carro.DataCarroResponse;
import devCodes.Zerphyis.ApiMotorsport.Application.Services.ServiceCarro;
import devCodes.Zerphyis.ApiMotorsport.Infra.Exceptions.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

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
        when(service.findAll()).thenReturn(List.of(response));

        ResponseEntity<List<DataCarroResponse>> resultado = controller.findAll();
        assertEquals(1, resultado.getBody().size());
        verify(service, times(1)).findAll();
    }

    @Test
    void findByIdHappy() {
        when(service.findById(1l)).thenReturn(response);
        ResponseEntity<DataCarroResponse> result = controller.findById(1L);

        assertEquals(response, result.getBody());
        verify(service, times(1)).findById(1L);
    }

    @Test
    void findByIdNotFound() {
        when(service.findById(99L)).thenThrow(new EntityNotFoundException("Carro não encontrado"));

        assertThrows(EntityNotFoundException.class, () -> controller.findById(99L));
        verify(service, times(1)).findById(99L);
    }
    @Test
    void createHappy() {
        when(service.create(request)).thenReturn(response);

        ResponseEntity<DataCarroResponse> result = controller.create(request);

        assertEquals(201, result.getStatusCodeValue());
        assertEquals(response, result.getBody());
        verify(service, times(1)).create(request);
    }

    @Test
    void createSad() {
        when(service.create(request)).thenThrow(new RuntimeException("Erro ao salvar carro"));

        assertThrows(RuntimeException.class, () -> controller.create(request));
        verify(service, times(1)).create(request);
    }


}

