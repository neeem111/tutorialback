package com.ccsw.tutorial.cliente;

import com.ccsw.tutorial.cliente.model.ClienteDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ClienteIT {

    public static final String LOCALHOST = "http://localhost:";
    public static final String SERVICE_PATH = "/cliente";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    ParameterizedTypeReference<List<ClienteDto>> responseType = new ParameterizedTypeReference<List<ClienteDto>>() {
    };

    @Test
    public void findAllShouldReturnAllClientes() {

        ResponseEntity<List<ClienteDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.GET, null, responseType);

        assertNotNull(response);
        assertEquals(3, response.getBody().size());
    }

    public static final Long NEW_CLIENTE_ID = 4L;
    public static final String NEW_Cliente_NAME = "CAT4";

    @Test
    public void saveWithoutIdShouldCreateNewCliente() {

        ClienteDto dto = new ClienteDto();
        dto.setName(NEW_Cliente_NAME);

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        ResponseEntity<List<ClienteDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.GET, null, responseType);
        assertNotNull(response);
        assertEquals(4, response.getBody().size());

        ClienteDto ClienteSearch = response.getBody().stream().filter(item -> item.getId().equals(NEW_CLIENTE_ID)).findFirst().orElse(null);
        assertNotNull(ClienteSearch);
        assertEquals(NEW_Cliente_NAME, ClienteSearch.getName());
    }

    public static final Long MODIFY_Cliente_ID = 3L;

    @Test
    public void modifyWithExistIdShouldModifyCliente() {

        ClienteDto dto = new ClienteDto();
        dto.setName(NEW_Cliente_NAME);

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + MODIFY_Cliente_ID, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        ResponseEntity<List<ClienteDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.GET, null, responseType);
        assertNotNull(response);
        assertEquals(3, response.getBody().size());

        ClienteDto ClienteSearch = response.getBody().stream().filter(item -> item.getId().equals(MODIFY_Cliente_ID)).findFirst().orElse(null);
        assertNotNull(ClienteSearch);
        assertEquals(NEW_Cliente_NAME, ClienteSearch.getName());
    }

    @Test
    public void modifyWithNotExistIdShouldInternalError() {

        ClienteDto dto = new ClienteDto();
        dto.setName(NEW_Cliente_NAME);

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + NEW_CLIENTE_ID, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    public static final Long DELETE_Cliente_ID = 2L;

    @Test
    public void deleteWithExistsIdShouldDeleteCliente() {

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + DELETE_Cliente_ID, HttpMethod.DELETE, null, Void.class);

        ResponseEntity<List<ClienteDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.GET, null, responseType);
        assertNotNull(response);
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void deleteWithNotExistsIdShouldInternalError() {

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + NEW_CLIENTE_ID, HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void saveDuplicatedClientShouldInternalError() {

        ClienteDto dto = new ClienteDto();
        dto.setName("Luis");

        restTemplate.exchange(LOCALHOST + port + "/cliente", HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + "/cliente", HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}