package com.ccsw.tutorial.cliente;

import com.ccsw.tutorial.cliente.model.Cliente;
import com.ccsw.tutorial.cliente.model.ClienteDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ccsw
 *
 */
@Tag(name = "Cliente", description = "API of Cliente")
@RequestMapping(value = "/cliente")
@RestController
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @Autowired
    ModelMapper mapper;

    /**
     * Método para recuperar todas las {@link Cliente}
     *
     * @return {@link List} de {@link ClienteDto}
     */
    @Operation(summary = "Find", description = "Method that return a list of Categories")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<ClienteDto> findAll() {

        List<Cliente> categories = this.clienteService.findAll();

        return categories.stream().map(e -> mapper.map(e, ClienteDto.class)).collect(Collectors.toList());
    }

    /**
     * Método para crear o actualizar una {@link Cliente}
     *
     * @param id PK de la entidad
     * @param dto datos de la entidad
     */
    @Operation(summary = "Save or Update", description = "Method that saves or updates a Category")
    @RequestMapping(path = { "", "/{id}" }, method = RequestMethod.PUT)
    public void save(@PathVariable(name = "id", required = false) Long id, @RequestBody ClienteDto dto) {

        this.clienteService.save(id, dto);
    }

    /**
     * Método para borrar una {@link Cliente}
     *
     * @param id PK de la entidad
     */
    @Operation(summary = "Delete", description = "Method that deletes a Category")
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) throws Exception {

        this.clienteService.delete(id);
    }

}