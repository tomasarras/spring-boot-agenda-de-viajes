package edu.isistan.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import edu.isistan.model.Cliente;
import edu.isistan.repository.ClienteRepository;

@RestController
@RequestMapping("clientes")
public class ClienteController {
	@Qualifier("clienteRepository")
	@Autowired
	private final ClienteRepository repository;

	public ClienteController(@Qualifier("clienteRepository") ClienteRepository repository) {
		this.repository = repository;
	}
	
	@GetMapping("/")
    public Iterable<Cliente> getClientes() {
        return repository.findAll();
    }

    @GetMapping("/nombre/{nombre}")
    public Iterable<Cliente> getClienteByNombre(@PathVariable String nombre) {
        return repository.findAllByNombre(nombre);
    }

    @PostMapping("/")
    public Cliente nuevoCliente(@RequestBody Cliente cliente) {
    	Cliente c = new Cliente(cliente.getNombre());
		return repository.save(c);
    }

    @GetMapping("/{id}")
    Optional<Cliente> getById(@PathVariable Integer id) {
        return repository.findById(id);
    }

    @PutMapping("/{id}")
    Cliente replaceCliente(@RequestBody Cliente nuevoCliente, @PathVariable Integer id) {

        return repository.findById(id)
                .map(cliente -> {
                	cliente.setNombre(nuevoCliente.getNombre());
                    return repository.save(cliente);
                })
                .orElseGet(() -> {
                	nuevoCliente.setId(id);
                    return repository.save(nuevoCliente);
                });
    }

    @DeleteMapping("/{id}")
    void borrarCliente(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
