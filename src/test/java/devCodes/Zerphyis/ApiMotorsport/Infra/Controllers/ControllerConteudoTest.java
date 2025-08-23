package devCodes.Zerphyis.ApiMotorsport.Infra.Controllers;

import devCodes.Zerphyis.ApiMotorsport.Application.Records.Conteudo.DataConteudoRequest;
import devCodes.Zerphyis.ApiMotorsport.Application.Records.Conteudo.DataConteudoResponse;
import devCodes.Zerphyis.ApiMotorsport.Application.Services.ServiceConteudo;
import devCodes.Zerphyis.ApiMotorsport.Infra.Exceptions.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;


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
    void findById() {
        when(service.findById(1L)).thenReturn(response);

        ResponseEntity<DataConteudoResponse> result = controller.findById(1L);

        assertEquals(response, result.getBody());
        verify(service, times(1)).findById(1L);
    }


    @Test
    void findByIdSad() {
         when(service.findById(99L)).thenThrow(new EntityNotFoundException("Conteúdo não encontrado"));
        assertThrows(EntityNotFoundException.class, () -> controller.findById(99L));
    }

}