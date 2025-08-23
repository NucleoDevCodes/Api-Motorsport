package devCodes.Zerphyis.ApiMotorsport.Infra.Controllers;

import devCodes.Zerphyis.ApiMotorsport.Application.Records.Especificacao.DataEspecificacaoTecnicaRequest;
import devCodes.Zerphyis.ApiMotorsport.Application.Records.Especificacao.DataEspecificacaoTecnicaResponse;
import devCodes.Zerphyis.ApiMotorsport.Application.Services.ServiceEspecificacaoTecnica;
import devCodes.Zerphyis.ApiMotorsport.Infra.Exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

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
    void create(){
        when(service.create(request)).thenReturn(response);
        ResponseEntity<DataEspecificacaoTecnicaResponse> result= controller.create(request);
        assertEquals(201,result.getStatusCodeValue());
        assertEquals(response,result.getBody());
    }

    @Test
    void createSad(){
        when(service.create(request)).thenThrow(new NotFoundException("Erro ao criar especificação"));
        assertThrows(NotFoundException.class, () -> controller.create(request));
    }
}