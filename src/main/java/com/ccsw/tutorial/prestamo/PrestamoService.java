package com.ccsw.tutorial.prestamo;

import com.ccsw.tutorial.prestamo.model.Prestamo;
import com.ccsw.tutorial.prestamo.model.PrestamoDto;
import com.ccsw.tutorial.prestamo.model.PrestamoSearchDto;
import org.springframework.data.domain.Page;

/**
 * @author ccsw
 *
 */
public interface PrestamoService {

    /**
     * Recupera préstamos con filtros
     *
     * @param dto filtros
     * @return {@link Page} de {@link Prestamo}
     */
    Page<Prestamo> find(PrestamoSearchDto dto);

    /**
     * Guarda o modifica un préstamo
     *
     * @param id PK de la entidad
     * @param dto datos
     */
    void save(Long id, PrestamoDto dto);

    /**
     * Elimina un préstamo
     *
     * @param id PK
     */
    void delete(Long id);
}