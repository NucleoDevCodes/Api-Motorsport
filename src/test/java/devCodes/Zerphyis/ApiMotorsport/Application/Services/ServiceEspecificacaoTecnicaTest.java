package devCodes.Zerphyis.ApiMotorsport.Application.Services;

import devCodes.Zerphyis.ApiMotorsport.Application.Records.Especificacao.DataEspecificacaoTecnicaRequest;
import devCodes.Zerphyis.ApiMotorsport.Application.Records.Especificacao.DataEspecificacaoTecnicaResponse;
import devCodes.Zerphyis.ApiMotorsport.Infra.Exceptions.NotFoundException;
import devCodes.Zerphyis.ApiMotorsport.Model.Entity.Carro.Carro;
import devCodes.Zerphyis.ApiMotorsport.Model.Entity.Especificao.EspecificacaoTecnica;
import devCodes.Zerphyis.ApiMotorsport.Model.Repositorys.CarroRepository;
import devCodes.Zerphyis.ApiMotorsport.Model.Repositorys.EspecificacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.concurrent.CompletionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ServiceEspecificacaoTecnicaTest {
    @Mock
    private EspecificacaoRepository especificacaoRepository;

    @Mock
    private CarroRepository carroRepository;

    @InjectMocks
    private ServiceEspecificacaoTecnica service;

    private DataEspecificacaoTecnicaRequest request;
    private Carro carro;
    private EspecificacaoTecnica especificacao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        carro = new Carro();
        carro.setId(1L);
        carro.setNome("Carro Teste");
        carro.setModelo("Modelo X");
        carro.setDescricao("Descrição teste");
        carro.setImagemUrl("carro.png");

        request = new DataEspecificacaoTecnicaRequest(1L, "Potência", "300cv");

        especificacao = new EspecificacaoTecnica();
        especificacao.setId(1L);
        especificacao.setTitulo(request.titulo());
        especificacao.setValor(request.valor());
        especificacao.setCarro(carro);
    }

    @Test
    void testCreate_Success() {
        when(carroRepository.findById(1L)).thenReturn(Optional.of(carro));
        when(especificacaoRepository.save(any(EspecificacaoTecnica.class))).thenReturn(especificacao);

        DataEspecificacaoTecnicaResponse response = service.create(request).join();

        assertNotNull(response);
        assertEquals("Potência", response.titulo());
        assertEquals("300cv", response.valor());
        assertEquals("Modelo X", response.modelo());
        verify(carroRepository, times(1)).findById(1L);
        verify(especificacaoRepository, times(1)).save(any(EspecificacaoTecnica.class));
    }

    @Test
    void testCreate_CarroNotFound() {
        when(carroRepository.findById(1L)).thenReturn(Optional.empty());

        CompletionException exception = assertThrows(
                CompletionException.class,
                () -> service.create(request).join()
        );

        assertTrue(exception.getCause() instanceof NotFoundException);
        verify(especificacaoRepository, never()).save(any());
    }

    @Test
    void testUpdate_Success() {
        when(especificacaoRepository.findById(1L)).thenReturn(Optional.of(especificacao));
        when(carroRepository.findById(1L)).thenReturn(Optional.of(carro));
        when(especificacaoRepository.save(any(EspecificacaoTecnica.class))).thenReturn(especificacao);

        DataEspecificacaoTecnicaResponse response = service.update(1L, request).join();

        assertEquals("Potência", response.titulo());
        assertEquals("300cv", response.valor());
        verify(especificacaoRepository, times(1)).findById(1L);
        verify(carroRepository, times(1)).findById(1L);
        verify(especificacaoRepository, times(1)).save(especificacao);
    }

    @Test
    void testUpdate_EspecificacaoNotFound() {
        when(especificacaoRepository.findById(1L)).thenReturn(Optional.empty());

        CompletionException exception = assertThrows(
                CompletionException.class,
                () -> service.update(1L, request).join()
        );

        assertTrue(exception.getCause() instanceof NotFoundException);
        verify(carroRepository, never()).findById(anyLong());
        verify(especificacaoRepository, never()).save(any());
    }

    @Test
    void testUpdate_CarroNotFound() {
        when(especificacaoRepository.findById(1L)).thenReturn(Optional.of(especificacao));
        when(carroRepository.findById(1L)).thenReturn(Optional.empty());

        CompletionException exception = assertThrows(
                CompletionException.class,
                () -> service.update(1L, request).join()
        );

        assertTrue(exception.getCause() instanceof NotFoundException);
        verify(especificacaoRepository, never()).save(any());
    }

    @Test
    void testFindById_Success() {
        when(especificacaoRepository.findById(1L)).thenReturn(Optional.of(especificacao));

        DataEspecificacaoTecnicaResponse response = service.findById(1L).join();

        assertEquals("Potência", response.titulo());
        assertEquals("Modelo X", response.modelo());
        verify(especificacaoRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_NotFound() {
        when(especificacaoRepository.findById(1L)).thenReturn(Optional.empty());

        CompletionException exception = assertThrows(
                CompletionException.class,
                () -> service.findById(1L).join()
        );

        assertTrue(exception.getCause() instanceof NotFoundException);
        verify(especificacaoRepository, times(1)).findById(1L);
    }

    @Test
    void testDelete_Success() {
        when(especificacaoRepository.findById(1L)).thenReturn(Optional.of(especificacao));

        service.delete(1L).join();

        verify(especificacaoRepository, times(1)).findById(1L);
        verify(especificacaoRepository, times(1)).delete(especificacao);
    }

    @Test
    void testDelete_NotFound() {
        when(especificacaoRepository.findById(1L)).thenReturn(Optional.empty());

        CompletionException exception = assertThrows(
                CompletionException.class,
                () -> service.delete(1L).join()
        );

        assertTrue(exception.getCause() instanceof NotFoundException);
        verify(especificacaoRepository, never()).delete(any());
    }
}
