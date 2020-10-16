package edu.isistan.controller;

import java.time.LocalDate;
import java.util.Optional;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import edu.isistan.model.Producto;
import edu.isistan.repository.ClienteRepository;
import edu.isistan.repository.CompraRepository;
import edu.isistan.repository.ProductoRepository;

@RestController
@RequestMapping("compras")
public class CompraController {
	@Autowired
	private CompraRepository repository;
	
	@Autowired
	private ClienteRepository repositoryCliente;
	
	@Autowired
	private ProductoRepository repositoryProducto;

	@GetMapping("/")
    public Iterable<Compra> getCompras() {
        return repository.findAll();
    }

	@PostMapping("/clientes/{idCliente}/productos/{idProducto}/")
    public ResponseEntity<Object> nuevaCompra(
    		@PathVariable int idCliente,
    		@PathVariable int idProducto) {
    	
		Cliente cliente = null;
		Producto producto;
    	LocalDate fecha = LocalDate.now();

    	int anio = fecha.getYear();
    	int mes = fecha.getMonthValue();
    	int dia = fecha.getDayOfMonth();
    	
    	try {
    		cliente = repositoryCliente.findById(idCliente).get();
    	} catch (Exception e) {
    		String error = "El cliente con el id=" + idCliente + " no existe.";
    		return ResponseEntity.status(Response.SC_NOT_FOUND).body(error);
    	}
    	
    	try {
    		producto = repositoryProducto.findById(idProducto).get();
    	} catch (Exception e) {
    		String error = "El producto con el id=" + idProducto + " no existe.";
    		return ResponseEntity.status(Response.SC_NOT_FOUND).body(error);
    	}
    	
    	int cantidadProductos = repositoryProducto.getCantidad(producto.getId(),cliente.getId(),anio,mes,dia);
    	if (cantidadProductos < 3) {
    		Compra compra = new Compra(cliente,producto,fecha);
    		compra = repository.save(compra);
    		return ResponseEntity.status(Response.SC_CREATED).body(compra);
    	} else {
    		String error = "El cliente alcanzo la cantidad maxima de productos diarios.";
    		assert (cantidadProductos <= 3) : "el producto tiene mas del limite diarios";
    		return ResponseEntity.status(Response.SC_BAD_GATEWAY).body(error);
    	}
    }
	
	@PostMapping("/test/")
    public void test() {
		/*
		Cliente c1 = new Cliente("tom");
		Cliente c2 = new Cliente("juan");
		Cliente c3 = new Cliente("pedro");
		repositoryCliente.save(c1);
		repositoryCliente.save(c2);
		repositoryCliente.save(c3);
		
		Producto p1 = new Producto("tomato",10,1000);
		Producto p2 = new Producto("papa",20,100);
		Producto p3 = new Producto("lechuga",40,500);
		repositoryProducto.save(p1);
		repositoryProducto.save(p2);
		repositoryProducto.save(p3);
		
	    LocalDate cal=	LocalDate.parse("2020-10-14");

	    Compra com1 = new Compra(c1, p1, cal);
	    Compra com2 = new Compra(c1, p1, cal);
	    Compra com3 = new Compra(c2, p1, cal);
	    Compra com4 = new Compra(c2, p2, cal);
	    cal = LocalDate.parse("2020-10-15");
	    Compra com5 = new Compra(c1, p1, cal);
	    Compra com6 = new Compra(c1, p1, cal);
	    Compra com7 = new Compra(c1, p1, cal);
	    repository.save(com1);
	    repository.save(com2);
	    repository.save(com3);
	    repository.save(com4);
	    repository.save(com5);
	    repository.save(com6);
	    repository.save(com7);
	    */
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