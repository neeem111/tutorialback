package com.ccsw.tutorial.cliente;

import com.ccsw.tutorial.cliente.model.Cliente;
import com.ccsw.tutorial.cliente.model.ClienteDto;

import java.util.List;

/**
 * @author ccsw
 *
 */
public interface ClienteService {

    /**
     * Recupera una {@link Cliente} a partir de su ID
     *
     * @param id PK de la entidad
     * @return {@link Cliente}
     */
    Cliente get(Long id);

    /**
     * Método para recuperar todas las {@link Cliente}
     *
     * @return {@link List} de {@link Cliente}
     */
    List<Cliente> findAll();

    /**
     * Método para crear o actualizar una {@link Cliente}
     *
     * @param id PK de la entidad
     * @param dto datos de la entidad
     */
    void save(Long id, ClienteDto dto);

    /**
     * Método para borrar una {@link Cliente}
     *
     * @param id PK de la entidad
     */
    void delete(Long id) throws Exception;

}