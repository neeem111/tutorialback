package com.ccsw.tutorial.prestamo;

import com.ccsw.tutorial.common.pagination.PageableRequest;
import com.ccsw.tutorial.config.ResponsePage;
import com.ccsw.tutorial.prestamo.model.PrestamoDto;
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

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PrestamoIT {

    private static final String LOCALHOST = "http://localhost:";
    private static final String SERVICE_PATH = "/prestamo";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private final ParameterizedTypeReference<ResponsePage<PrestamoDto>> responseType = new ParameterizedTypeReference<>() {
    };

    @Test
    public void findWithoutFiltersShouldReturnPagedResults() {

        PageableRequest pageable = new PageableRequest(0, 10);

        ResponseEntity<ResponsePage<PrestamoDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(pageable), responseType);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getTotalElements() >= 0);
    }

    // filtro x juego
    @Test
    public void findByGameShouldReturnOnlyThatGame() {

        PageableRequest pageable = new PageableRequest(0, 10);
        Long gameId = 1L;

        ResponseEntity<ResponsePage<PrestamoDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "?gameId=" + gameId, HttpMethod.POST, new HttpEntity<>(pageable), responseType);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().getContent().stream().allMatch(p -> p.getGameId().equals(gameId)));
    }

    // cliente
    @Test
    public void findByClienteShouldReturnOnlyThatCliente() {

        PageableRequest pageable = new PageableRequest(0, 10);
        Long clienteId = 1L;

        ResponseEntity<ResponsePage<PrestamoDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "?clienteId=" + clienteId, HttpMethod.POST, new HttpEntity<>(pageable), responseType);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().getContent().stream().allMatch(p -> p.getClientId().equals(clienteId)));
    }

    // FECHA INTERMEDIA
    @Test
    public void findByDateShouldReturnPrestamosInDateRange() {

        PageableRequest pageable = new PageableRequest(0, 10);
        String date = "2024-05-05"; // día intermedio del rango

        ResponseEntity<ResponsePage<PrestamoDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "?date=" + date, HttpMethod.POST, new HttpEntity<>(pageable), responseType);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    // Ccrear
    @Test
    public void saveValidPrestamoShouldCreate() {

        PrestamoDto dto = new PrestamoDto();
        dto.setClientId(1L);
        dto.setGameId(2L);
        dto.setStartDate(LocalDate.now());
        dto.setEndDate(LocalDate.now().plusDays(3));

        ResponseEntity<Void> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // fecha fin e incio
    @Test
    public void saveWithEndDateBeforeStartShouldFail() {

        PrestamoDto dto = new PrestamoDto();
        dto.setClientId(1L);
        dto.setGameId(1L);
        dto.setStartDate(LocalDate.now());
        dto.setEndDate(LocalDate.now().minusDays(1));

        ResponseEntity<Void> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    // mas de 14dias
    @Test
    public void saveWithMoreThan14DaysShouldFail() {

        PrestamoDto dto = new PrestamoDto();
        dto.setClientId(1L);
        dto.setGameId(1L);
        dto.setStartDate(LocalDate.now());
        dto.setEndDate(LocalDate.now().plusDays(20));

        ResponseEntity<Void> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    // borrar rpestamo

    @Test
    public void deletePrestamoShouldRemove() {

        Long prestamoId = 1L;

        ResponseEntity<Void> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + prestamoId, HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}