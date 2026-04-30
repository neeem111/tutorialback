package com.ccsw.tutorial.cliente;

import com.ccsw.tutorial.cliente.model.Cliente;
import org.springframework.data.repository.CrudRepository;

/**
 * @author ccsw
 *
 */
public interface ClienteRepository extends CrudRepository<Cliente, Long> {

    boolean existsByName(String name);

}