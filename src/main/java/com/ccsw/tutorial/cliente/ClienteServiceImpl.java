package com.ccsw.tutorial.cliente;

import com.ccsw.tutorial.cliente.model.Cliente;
import com.ccsw.tutorial.cliente.model.ClienteDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ccsw
 *
 */
@Service
@Transactional
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Cliente get(Long id) {

        return this.clienteRepository.findById(id).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Cliente> findAll() {

        return (List<Cliente>) this.clienteRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, ClienteDto dto) {
        
        if (id == null && clienteRepository.existsByName(dto.getName())) {
            throw new RuntimeException("Client already exists");
        }

        Cliente cliente;

        if (id == null) {
            cliente = new Cliente();
        } else {
            cliente = clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Not exists"));
        }

        cliente.setName(dto.getName());
        clienteRepository.save(cliente);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws Exception {

        if (this.get(id) == null) {
            throw new Exception("Not exists");
        }

        this.clienteRepository.deleteById(id);
    }

}