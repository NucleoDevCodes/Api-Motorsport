package devCodes.Zerphyis.ApiMotorsport.Application.Services;

import devCodes.Zerphyis.ApiMotorsport.Application.Records.Carro.DataCarroRequest;
import devCodes.Zerphyis.ApiMotorsport.Application.Records.Carro.DataCarroResponse;
import devCodes.Zerphyis.ApiMotorsport.Infra.Exceptions.NotFoundException;
import devCodes.Zerphyis.ApiMotorsport.Model.Entity.Carro.Carro;
import devCodes.Zerphyis.ApiMotorsport.Model.Repositorys.CarroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ServiceCarroTest {

    @Mock
    private CarroRepository repository;

    @InjectMocks
    private ServiceCarro service;

    private DataCarroRequest request;
    private Carro carro;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        request = new DataCarroRequest(
                "Carro Teste", "Modelo X", "Descrição Teste",
                BigDecimal.valueOf(100000), "img.png", 1
        );

        carro = new Carro();
        carro.setId(1L);
        carro.setNome(request.nome());
        carro.setModelo(request.modelo());
        carro.setDescricao(request.descricao());
        carro.setPreco(request.preco());
        carro.setImagemUrl(request.imagemUrl());
        carro.setOrdem(request.ordem());
    }

    @Test
    void testCreate_Success() {
        when(repository.save(any(Carro.class))).thenReturn(carro);

        DataCarroResponse response = service.create(request).join();

        assertNotNull(response);
        assertEquals("Carro Teste", response.nome());
        verify(repository, times(1)).save(any(Carro.class));
    }

    @Test
    void testUpdate_Success() {
        when(repository.findById(1L)).thenReturn(Optional.of(carro));
        when(repository.save(any(Carro.class))).thenReturn(carro);

        DataCarroResponse response = service.update(1L, request).join();

        assertEquals("Modelo X", response.modelo());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(carro);
    }

    @Test
    void testUpdate_NotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        CompletionException exception = assertThrows(
                CompletionException.class,
                () -> service.update(1L, request).join()
        );

        assertTrue(exception.getCause() instanceof NotFoundException);
        verify(repository, times(1)).findById(1L);
        verify(repository, never()).save(any());
    }

    @Test
    void testFindAll_Success() {
        when(repository.findAll()).thenReturn(List.of(carro));

        List<DataCarroResponse> result = service.findAll().join();

        assertEquals(1, result.size());
        assertEquals("Carro Teste", result.get(0).nome());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testFindById_Success() {
        when(repository.findById(1L)).thenReturn(Optional.of(carro));

        DataCarroResponse response = service.findById(1L).join();

        assertEquals("Carro Teste", response.nome());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testFindById_NotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        CompletionException exception = assertThrows(
                CompletionException.class,
                () -> service.findById(1L).join()
        );

        assertTrue(exception.getCause() instanceof NotFoundException);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testDelete_Success() {
        when(repository.existsById(1L)).thenReturn(true);

        service.delete(1L).join();

        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDelete_NotFound() {
        when(repository.existsById(1L)).thenReturn(false);

        CompletionException exception = assertThrows(
                CompletionException.class,
                () -> service.delete(1L).join()
        );

        assertTrue(exception.getCause() instanceof NotFoundException);
        verify(repository, times(1)).existsById(1L);
        verify(repository, never()).deleteById(anyLong());
    }
}
