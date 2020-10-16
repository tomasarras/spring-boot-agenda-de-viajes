package edu.isistan.controller;

import java.time.LocalDate;
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

	/**
	 * 
	 * @return todas las compras
	 */
	@GetMapping("/")
    public Iterable<Compra> getCompras() {
        return repository.findAll();
    }

	/**
	 * Agrega la compra de producto hecha por un cliente
	 * @param idCliente del cliente que hace la compra
	 * @param idProducto del producto que se esta comprando
	 * @return 201 si se registro la compra
	 * @return 404 si el cliente que hace la compra no existe
	 * @return 404 si el producto que se compra no existe
	 * @return 502 si supero el limite de compra por producto
	 */
	@PostMapping("/clientes/{idCliente}/productos/{idProducto}/")
    public ResponseEntity<Object> nuevaCompra(
    		@PathVariable int idCliente,
    		@PathVariable int idProducto) {
    	
		Cliente cliente = getCliente(idCliente);
		if (cliente == null)
			clienteNoEncontrado(idCliente);
		
		Producto producto = getProducto(idProducto);
		if (producto == null)
			productoNoEncontrado(idProducto);
		
    	LocalDate fecha = LocalDate.now();
    	int anio = fecha.getYear();
    	int mes = fecha.getMonthValue();
    	int dia = fecha.getDayOfMonth();
    	
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

	/**
	 * retorna una compra por id
	 * @param id de la compra
	 * @return 200 y la compra
	 * @return 404 si no existe la compra
	 */
    @GetMapping("/{id}")
    ResponseEntity<Compra> getById(@PathVariable Integer id) {
    	try {
    		Compra compra = repository.findById(id).get();
    		return ResponseEntity.status(Response.SC_OK).body(compra);
    	} catch (Exception e) {
    		return ResponseEntity.status(Response.SC_NOT_FOUND).build();
    	}
    	
    }

    /**
     * Edita una compra
     * @param nuevaCompra la nueva compra con los datos que cambian
     * @param idCompra id de la compra que se quiere cambiar
     * @return 200 y la compra modificada
     * @return 400 si algun dato no es valido
     * @return 404 si el cliente, producto o la compra no existe
     */
    @PutMapping("/{idCompra}/")
    ResponseEntity<Object> replaceCompra(
    		@RequestBody Compra nuevaCompra, 
    		@PathVariable int idCompra) {
    	
    	if (!esValidaCompra(nuevaCompra))
    		return ResponseEntity.status(Response.SC_BAD_REQUEST).build();
    	
    	Cliente cliente = getCliente(nuevaCompra.getCliente().getId());
		if (cliente == null)
			clienteNoEncontrado(nuevaCompra.getCliente().getId());
		
		Producto producto = getProducto(nuevaCompra.getProducto().getId());
		if (producto == null)
			productoNoEncontrado(nuevaCompra.getProducto().getId());
		
		Compra compra = getCompra(idCompra);
		if (compra == null)
			compraNoEncontrada(idCompra);
		
		compra.setCliente(cliente);
		compra.setProducto(producto);
		compra.setFecha(nuevaCompra.getFecha());
		repository.flush();
		
		return ResponseEntity.status(Response.SC_OK).body(compra);
    }

    /**
     * 
     * @param id de la compra que se quiere borrar
     * @return 204 si se borro la compra
     * @return 404 si no existe la compra
     */
	@DeleteMapping("/{id}")
    ResponseEntity<Object> borrarCompra(@PathVariable Integer id) {
		try {
			repository.deleteById(id);
			return ResponseEntity.status(Response.SC_NO_CONTENT).build();
		} catch (Exception e) {
			return compraNoEncontrada(id);
		}
    }
	
	private boolean esValidaCompra(Compra compra) {
		return compra.getCliente() != null && compra.getProducto() != null && compra.getFecha() != null;
	}
	
	private Compra getCompra(int idCompra) {
		try {
			return repository.findById(idCompra).get();
		} catch (Exception e) {
			return null;
		}
	}
    
    private Producto getProducto(int id) {
    	try {
    		return repositoryProducto.findById(id).get();
    	} catch (Exception e) {
    		return null;
    	}
    }
    
    private Cliente getCliente(int id) {
    	try {
    		return repositoryCliente.findById(id).get();
    	} catch (Exception e) {
    		return null;
    	}
    }
    
    private ResponseEntity<Object> compraNoEncontrada(int id) {
    	String error = "La compra con el id=" + id + " no existe.";
    	return ResponseEntity.status(Response.SC_NOT_FOUND).body(error);
    }
    
    private ResponseEntity<Object> productoNoEncontrado(int id) {
    	String error = "El producto con el id=" + id + " no existe.";
    	return ResponseEntity.status(Response.SC_NOT_FOUND).body(error);
    }
    
    private ResponseEntity<Object> clienteNoEncontrado(int id) {
    	String error = "El cliente con el id=" + id + " no existe.";
    	return ResponseEntity.status(Response.SC_NOT_FOUND).body(error);
    }
}