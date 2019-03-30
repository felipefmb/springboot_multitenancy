package com.felipebatista.multitenancia.service;

import com.felipebatista.multitenancia.model.Cliente;
import com.felipebatista.multitenancia.repository.ClienteRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository repository;
    
    public List<Cliente> findAll() {
        return repository.findAll();
    }
    
    public Cliente insert(Cliente cliente) {
        repository.save(cliente);
        return cliente;
    }
    
    public Cliente findById(Long id) {
        return repository.findById(id).get();
    }
    
    public Cliente update(Cliente cliente) {
        return repository.save(cliente);
    }
    
    public void remove(Long id) {
        repository.deleteById(id);
    }

}
