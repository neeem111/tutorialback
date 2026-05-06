package com.ccsw.tutorial.loan.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class LoanDto {

    private Long id;

    private Long clientId;
    private String clientName;

    private Long gameId;
    private String gameTitle;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    public Long getId() {
        return id;
    }

    public Long getClientId() {
        return clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public Long getGameId() {
        return gameId;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}