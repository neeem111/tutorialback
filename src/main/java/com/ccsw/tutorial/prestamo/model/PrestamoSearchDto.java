package com.ccsw.tutorial.prestamo.model;

import com.ccsw.tutorial.common.pagination.PageableRequest;
import java.time.LocalDate;

public class PrestamoSearchDto {

    private Long gameId;
    private Long clientId;       // renombrado: quita la 'e' para consistencia
    private LocalDate date;      // cambiado de Date a LocalDate
    private PageableRequest pageable;

    public Long getGameId() { return gameId; }
    public void setGameId(Long gameId) { this.gameId = gameId; }

    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public PageableRequest getPageable() { return pageable; }
    public void setPageable(PageableRequest pageable) { this.pageable = pageable; }
}
