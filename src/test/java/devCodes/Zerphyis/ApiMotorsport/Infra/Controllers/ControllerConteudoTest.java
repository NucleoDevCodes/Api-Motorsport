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

    @Test
    void create() {
        when(service.create(request)).thenReturn(response);
        ResponseEntity<DataConteudoResponse> result = controller.create(request);

        assertEquals(201, result.getStatusCodeValue());
        assertEquals(response, result.getBody());
    }


    @Test
    void createSad(){
        when(service.create(request)).thenThrow(new NotFoundException("Erro ao criar Conteúdo"));
        assertThrows(NotFoundException.class,() -> controller.create(request));
        verify(service,times(1)).create(request);
    }

    @Test
    void update() {
        when(service.update(1L,request)).thenReturn(response);
        ResponseEntity<DataConteudoResponse> result= controller.update(1L,request);
        assertEquals(response,result.getBody());
    }

    @Test
    void updateSad() {
        when(service.update(99L,request)).thenThrow(new EntityNotFoundException("Conteúdo não encontrado"));
        assertThrows(EntityNotFoundException.class, () -> controller.update(99L,request));
        verify(service, times(1)).update(99L,request);
    }

    @Test
    void delete() {
       doNothing().when(service).delete(1L);
       ResponseEntity<Void> result=controller.delete(1L);
       assertEquals(204,result.getStatusCodeValue());
    }

    @Test
    void deleteSad() {
        doThrow(new EntityNotFoundException("Conteúdo não encontrado")).when(service).delete(99L);

        assertThrows(EntityNotFoundException.class, () -> controller.delete(99L));
        verify(service, times(1)).delete(99L);
    }
}