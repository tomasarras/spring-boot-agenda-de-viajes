package edu.isistan.controller;

import java.sql.Timestamp;
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
import edu.isistan.model.Compra;
import edu.isistan.repository.CompraRepository;

@RestController
@RequestMapping("compras")
public class CompraController {
	@Qualifier("compraRepository")
	@Autowired
	private final CompraRepository repository;

	public CompraController(@Qualifier("compraRepository") CompraRepository repository) {
		this.repository = repository;
	}
	
	@GetMapping("/")
    public Iterable<Compra> getCompras() {
        return repository.findAll();
    }

    @PostMapping("/")
    public Compra nuevaCompra(@RequestBody Compra compra) {
    	/*Timestamp fecha = new Timestamp(System.currentTimeMillis());
    	Cliente cliente = //TODO fetch("clientes/{compra.getCliente().getId()} ?? o hacer static repo de cliente o crear el metodo para obtener cliente o instanciar al repocitory aca y pedir el cliente
    	Compra c = new Compra(compra.getCliente(),compra.getProducto(),fecha);
		return repository.save(c);*/
    }

    @GetMapping("/{id}")
    Optional<Compra> getById(@PathVariable Integer id) {
        return repository.findById(id);
    }

    @PutMapping("/{id}")
    Compra replaceCompra(@RequestBody Compra nuevaCompra, @PathVariable Integer id) {

        return repository.findById(id)
                .map(compra -> {
                	compra.setCliente(nuevaCompra.getCliente());
                	compra.setProducto(nuevaCompra.getProducto());
                    return repository.save(compra);
                })
                .orElseGet(() -> {
                	nuevaCompra.setId(id);
                    return repository.save(nuevaCompra);
                });
    }

    @DeleteMapping("/{id}")
    void borrarCompra(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}