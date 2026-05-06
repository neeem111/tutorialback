package com.ccsw.tutorial.prestamo.model;

import com.ccsw.tutorial.cliente.model.ClienteDto;
import com.ccsw.tutorial.game.model.GameDto;
import java.time.LocalDate;

public class PrestamoDto {

    private Long id;

    // Objetos anidados igual que GameDto tiene AuthorDto y CategoryDto
    private ClienteDto client;
    private GameDto game;

    private LocalDate startDate;
    private LocalDate endDate;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public ClienteDto getClient() { return client; }
    public void setClient(ClienteDto client) { this.client = client; }

    public GameDto getGame() { return game; }
    public void setGame(GameDto game) { this.game = game; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
}
