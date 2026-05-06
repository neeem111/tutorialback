package com.ccsw.tutorial.prestamo.model;

import com.ccsw.tutorial.cliente.model.Cliente;
import com.ccsw.tutorial.game.model.Game;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "prestamo")
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    // Campo renombrado a 'client' para que ModelMapper case con PrestamoDto.client
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente client;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Cliente getClient() { return client; }
    public void setClient(Cliente client) { this.client = client; }

    public Game getGame() { return game; }
    public void setGame(Game game) { this.game = game; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
}
