package com.ccsw.tutorial.prestamo;

import com.ccsw.tutorial.prestamo.model.PrestamoDto;
import com.ccsw.tutorial.prestamo.model.PrestamoSearchDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author ccsw
 *
 */
@Tag(name = "prestamo", description = "API of prestamo")
@RequestMapping(value = "/prestamo")
@RestController
@CrossOrigin(origins = "*")
public class PrestamoController {

    @Autowired
    PrestamoService prestamoService;

    @Autowired
    ModelMapper mapper;

    /**
     * Método para recuperar préstamos con filtros
     */
    @Operation(summary = "Find")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public Page<PrestamoDto> find(@RequestBody PrestamoSearchDto dto) {

        return prestamoService.find(dto).map(e -> mapper.map(e, PrestamoDto.class));
    }

    /**
     * Guardar / actualizar
     */
    @RequestMapping(path = { "", "/{id}" }, method = RequestMethod.PUT)
    public void save(@PathVariable(required = false) Long id, @RequestBody PrestamoDto dto) {

        prestamoService.save(id, dto);
    }

    /**
     * Borrar
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {

        prestamoService.delete(id);
    }
}