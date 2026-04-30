package com.ccsw.tutorial.cliente;

import com.ccsw.tutorial.cliente.model.Cliente;
import com.ccsw.tutorial.cliente.model.ClienteDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteTest {

    @Mock
    private ClienteRepository ClienteRepository;

    @InjectMocks
    private ClienteServiceImpl ClienteService;

    @Test
    public void findAllShouldReturnAllCategories() {

        List<Cliente> list = new ArrayList<>();
        list.add(mock(Cliente.class));

        when(ClienteRepository.findAll()).thenReturn(list);

        List<Cliente> categories = ClienteService.findAll();

        assertNotNull(categories);
        assertEquals(1, categories.size());
    }

    public static final String Cliente_NAME = "CAT1";

    @Test
    public void saveNotExistsClienteIdShouldInsert() {

        ClienteDto ClienteDto = new ClienteDto();
        ClienteDto.setName(Cliente_NAME);

        ArgumentCaptor<Cliente> Cliente = ArgumentCaptor.forClass(Cliente.class);

        ClienteService.save(null, ClienteDto);

        verify(ClienteRepository).save(Cliente.capture());

        assertEquals(Cliente_NAME, Cliente.getValue().getName());
    }

    public static final Long EXISTS_Cliente_ID = 1L;

    @Test
    public void saveExistsClienteIdShouldUpdate() {

        ClienteDto ClienteDto = new ClienteDto();
        ClienteDto.setName(Cliente_NAME);

        Cliente Cliente = mock(Cliente.class);
        when(ClienteRepository.findById(EXISTS_Cliente_ID)).thenReturn(Optional.of(Cliente));

        ClienteService.save(EXISTS_Cliente_ID, ClienteDto);

        verify(ClienteRepository).save(Cliente);
    }

    @Test
    public void saveDuplicatedNameShouldThrowException() {

        ClienteDto dto = new ClienteDto();
        dto.setName("ANA");

        when(ClienteRepository.existsByName("ANA")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> ClienteService.save(null, dto));
    }

    @Test
    public void deleteExistsClienteIdShouldDelete() throws Exception {

        Cliente Cliente = mock(Cliente.class);
        when(ClienteRepository.findById(EXISTS_Cliente_ID)).thenReturn(Optional.of(Cliente));

        ClienteService.delete(EXISTS_Cliente_ID);

        verify(ClienteRepository).deleteById(EXISTS_Cliente_ID);
    }

    public static final Long NOT_EXISTS_Cliente_ID = 0L;

    @Test
    public void getExistsClienteIdShouldReturnCliente() {

        Cliente Cliente = mock(Cliente.class);
        when(Cliente.getId()).thenReturn(EXISTS_Cliente_ID);
        when(ClienteRepository.findById(EXISTS_Cliente_ID)).thenReturn(Optional.of(Cliente));

        Cliente ClienteResponse = ClienteService.get(EXISTS_Cliente_ID);

        assertNotNull(ClienteResponse);
        assertEquals(EXISTS_Cliente_ID, Cliente.getId());
    }

    @Test
    public void getNotExistsClienteIdShouldReturnNull() {

        when(ClienteRepository.findById(NOT_EXISTS_Cliente_ID)).thenReturn(Optional.empty());

        Cliente Cliente = ClienteService.get(NOT_EXISTS_Cliente_ID);

        assertNull(Cliente);
    }
}