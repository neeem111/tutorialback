package com.ccsw.tutorial.prestamo.model;

import java.time.LocalDate;

public class PrestamoDto {

    private Long id;

    private Long clienteId;
    private String clienteName;

    private Long gameId;
    private String gameTitle;

    private LocalDate startDate;
    private LocalDate endDate;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return this.clienteId;
    }

    public void setClientId(Long clientId) {
        this.clienteId = clientId;
    }

    public String getClientName() {
        return this.clienteName;
    }

    public void setClientName(String clientName) {
        this.clienteName = clientName;
    }

    public Long getGameId() {
        return this.gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getGameTitle() {
        return this.gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }
 
    public void setStartDate(LocalDate startDate) {

        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}