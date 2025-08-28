package devCodes.Zerphyis.ApiMotorsport.Application.Services;

import devCodes.Zerphyis.ApiMotorsport.Application.Records.Conteudo.DataConteudoRequest;
import devCodes.Zerphyis.ApiMotorsport.Application.Records.Conteudo.DataConteudoResponse;
import devCodes.Zerphyis.ApiMotorsport.Infra.Exceptions.NotFoundException;
import devCodes.Zerphyis.ApiMotorsport.Model.Entity.Conteudo.Conteudo;
import devCodes.Zerphyis.ApiMotorsport.Model.Repositorys.ConteudoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ServiceConteudoTest {

    @InjectMocks
    private ServiceConteudo serviceConteudo;

    @Mock
    private ConteudoRepository conteudoRepository;

    private Conteudo conteudo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        conteudo = new Conteudo();
        conteudo.setId(1L);
        conteudo.setTitulo("titulo teste");
        conteudo.setConteudo("conteudo Teste");
        conteudo.setImagemUrl("url_teste");
    }

    @Test
    void testCreate_Success() {
        DataConteudoRequest request = new DataConteudoRequest(
                "titulo teste", "conteudo Teste", "url_teste"
        );

        when(conteudoRepository.save(any(Conteudo.class))).thenReturn(conteudo);

        DataConteudoResponse response = serviceConteudo.create(request).join();

        assertEquals("titulo teste", response.titulo());
        assertEquals("conteudo Teste", response.conteudo());
        assertEquals("url_teste", response.imagemUrl());
        verify(conteudoRepository, times(1)).save(any(Conteudo.class));
    }

    @Test
    void testFindById_Success() {
        when(conteudoRepository.findById(1L)).thenReturn(Optional.of(conteudo));

        DataConteudoResponse response = serviceConteudo.findById(1L).join();

        assertEquals("titulo teste", response.titulo());
        assertEquals("conteudo Teste", response.conteudo());
        assertEquals("url_teste", response.imagemUrl());
        verify(conteudoRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdate_Success() {
        DataConteudoRequest request = new DataConteudoRequest(
                "titulo teste", "conteudo Teste", "url_teste"
        );

        when(conteudoRepository.findById(1L)).thenReturn(Optional.of(conteudo));
        when(conteudoRepository.save(any(Conteudo.class))).thenReturn(conteudo);

        DataConteudoResponse response = serviceConteudo.update(1L, request).join();

        assertEquals("titulo teste", response.titulo());
        assertEquals("conteudo Teste", response.conteudo());
        assertEquals("url_teste", response.imagemUrl());
        verify(conteudoRepository, times(1)).findById(1L);
        verify(conteudoRepository, times(1)).save(any(Conteudo.class));
    }

    @Test
    void testDelete_Success() {
        when(conteudoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(conteudoRepository).deleteById(1L);

        serviceConteudo.delete(1L).join();

        verify(conteudoRepository, times(1)).existsById(1L);
        verify(conteudoRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindById_NotFound() {
        when(conteudoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> serviceConteudo.findById(1L).join());
        verify(conteudoRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdate_NotFound() {
        when(conteudoRepository.findById(1L)).thenReturn(Optional.empty());

        DataConteudoRequest request = new DataConteudoRequest(
                "titulo teste", "conteudo Teste", "url_teste"
        );

        assertThrows(NotFoundException.class, () -> serviceConteudo.update(1L, request).join());
        verify(conteudoRepository, times(1)).findById(1L);
        verify(conteudoRepository, never()).save(any());
    }

    @Test
    void testDelete_NotFound() {
        when(conteudoRepository.existsById(1L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> serviceConteudo.delete(1L).join());
        verify(conteudoRepository, times(1)).existsById(1L);
        verify(conteudoRepository, never()).deleteById(anyLong());
    }
}
