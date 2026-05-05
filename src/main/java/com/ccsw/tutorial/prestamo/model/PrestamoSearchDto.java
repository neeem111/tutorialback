package com.ccsw.tutorial.prestamo.model;

import com.ccsw.tutorial.common.pagination.PageableRequest;

import java.util.Date;

public class PrestamoSearchDto {

    private Long gameId;
    private Long clienteId;
    private Date date;
    private PageableRequest pageable;

    public Long getGameId() {
        return gameId;
    }

    /**
     * @param gameId new value of {@link #getGameId}.
     */
    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    /**
     * @return clientId
     */
    public Long getClientId() {
        return clienteId;
    }

    /**
     * @param clientId new value of {@link #getClientId}.
     */
    public void setClientId(Long clientId) {
        this.clienteId = clientId;
    }

    /**
     * @return date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date new value of {@link #getDate}.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return pageable
     */
    public PageableRequest getPageable() {
        return pageable;
    }

    /**
     * @param pageable new value of {@link #getPageable}.
     */
    public void setPageable(PageableRequest pageable) {
        this.pageable = pageable;
    }
}