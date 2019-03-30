package com.felipebatista.multitenancia.resources;

import com.felipebatista.multitenancia.model.Cliente;
import com.felipebatista.multitenancia.service.ClienteService;
import java.util.List;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

@RestController
@RequestMapping("/clientes")
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class ClienteResource {
    
    @Autowired
    private ClienteService service;

    @GetMapping
    public List<Cliente> findAll() {
        return service.findAll();
    }
    
    @GetMapping("/{id}")
    public Cliente findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }
    
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente insert(@RequestBody @Valid Cliente cliente) {
        return service.insert(cliente);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> update(@PathVariable("id") Long id,
            @RequestBody @Valid Cliente cliente) {
        if (!id.equals(cliente.getId()))
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(service.update(cliente));
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        service.remove(id);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<String> 
        handleValidationException(ConstraintViolationException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
    
}
