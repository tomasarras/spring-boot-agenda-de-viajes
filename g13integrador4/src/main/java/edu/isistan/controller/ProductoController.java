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
import edu.isistan.model.Producto;
import edu.isistan.repository.ProductoRepository;

@RestController
@RequestMapping("productos")
public class ProductoController {
	@Qualifier("productoRepository")
	@Autowired
	private final ProductoRepository repository;

	public ProductoController(@Qualifier("productoRepository") ProductoRepository repository) {
		this.repository = repository;
	}
	
	@GetMapping("/")
    public Iterable<Producto> getProductos() {
        return repository.findAll();
    }

    @GetMapping("/nombre/{nombre}")
    public Iterable<Producto> getProductoByNombre(@PathVariable String nombre) {
        return repository.findAllByNombre(nombre);
    }

    @PostMapping("/")
    public Producto nuevoProducto(@RequestBody Producto producto) {
    	Producto p = new Producto(producto.getNombre(),producto.getPrecio());
		return repository.save(p);
    }

    @GetMapping("/{id}")
    Optional<Producto> getById(@PathVariable Integer id) {
        return repository.findById(id);
    }

    @PutMapping("/{id}")
    Producto replacePerson(@RequestBody Producto nuevoProducto, @PathVariable Integer id) {

        return repository.findById(id)
                .map(producto -> {
                	producto.setNombre(nuevoProducto.getNombre());
                	producto.setPrecio(nuevoProducto.getPrecio());
                    return repository.save(producto);
                })
                .orElseGet(() -> {
                	nuevoProducto.setId(id);
                    return repository.save(nuevoProducto);
                });
    }

    @DeleteMapping("/{id}")
    void borrarProducto(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}