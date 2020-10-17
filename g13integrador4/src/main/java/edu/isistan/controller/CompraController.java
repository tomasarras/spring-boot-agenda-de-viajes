package edu.isistan.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
import edu.isistan.model.FacturaCliente;
import edu.isistan.model.FacturadoEnUnDia;
import edu.isistan.model.Producto;
import edu.isistan.repository.CompraRepository;
import edu.isistan.repository.ProductoRepository;
/**
 * 
 * Controlador de compras 
 * @author Tomas
 *
 */
@RestController
@RequestMapping("compras")
public class CompraController extends Controller {
	@Autowired
	private CompraRepository repository;
	
	@Autowired
	private ProductoRepository repositoryProducto;
	
	
	/**
	 * 
	 * @return 200 y el producto mas se ha vendido hasta el momento
	 * @return 404 si no hay ninguno
	 */
	@GetMapping("/productos/mas-vendido")
    public ResponseEntity<Producto> gerProductoMasVendido() {
		try {
			Producto producto = repository.getProductosMasVendidos(PageRequest.of(0,1)).get(0);
			return ResponseEntity.status(Response.SC_OK).body(producto);
		} catch (Exception e) {
			return ResponseEntity.status(Response.SC_NOT_FOUND).build();
		}
		
	}
	
	/**
	 * @return 200 y List<FacturadoEnUnDia> una lista con las ganancias que se 
	 * generaron en cada dia
	 */
	@GetMapping("/reportes/")
    public List<FacturadoEnUnDia> getReportesFechas() {
		Iterator<Object> it = repository.getReporteComprasFechas().iterator();
		List<FacturadoEnUnDia> facturas = new ArrayList<FacturadoEnUnDia>();
		Object[] reporte;
		Date fecha;
		float monto;
		while (it.hasNext()) {
			reporte = (Object[]) it.next();
			fecha = (Date) reporte[0];
			monto = ((Double) reporte[1]).floatValue();
			facturas.add(new FacturadoEnUnDia(fecha, monto));
		}
		return facturas;
	}
	
	
	/**
	 * 
	 * @return 200 y List<FacturaCliente> una lista con los gastos que lleva 
	 * cada cliente hasta el momento
	 */
	@GetMapping("/clientes/reportes/")
    public List<FacturaCliente> getFacturasClientes() {
		Iterator<Object> it = repository.getReporteCompras().iterator();
		List<FacturaCliente> facturas = new ArrayList<FacturaCliente>();
		Object[] reporte;
		Cliente cliente;
		int idCliente;
		String nombreCliente;
		float montoTotal;
		while (it.hasNext()) {
			reporte = (Object[]) it.next();
			idCliente = (int) reporte[0];
			nombreCliente = (String) reporte[1];
			montoTotal = ((Double) reporte[2]).floatValue();
			cliente = new Cliente(idCliente, nombreCliente);
			facturas.add(new FacturaCliente(cliente, montoTotal));
		}
		return facturas;
	}

	/**
	 * 
	 * @return todas las compras
	 */
	@GetMapping("/")
    public Iterable<Compra> getCompras() {
        return repository.findAll();
    }

	/**
	 * Agrega la compra de producto hecha por un cliente y se resta en stock
	 * @param idCliente del cliente que hace la compra
	 * @param idProducto del producto que se esta comprando
	 * @param fechaJson si hay fecha se genera una compra en esa fecha, si la fecha no esta se genera
	 * una fecha actual
	 * @return 201 si se registro la compra
	 * @return 404 si el cliente que hace la compra no existe
	 * @return 404 si el producto que se compra no existe
	 * @return 502 si supero el limite de compra por producto
	 */
	@PostMapping("/clientes/{idCliente}/productos/{idProducto}/")
    public ResponseEntity<Object> nuevaCompra(
    		@RequestBody Compra fechaJson,
    		@PathVariable int idCliente,
    		@PathVariable int idProducto) {
    	
		Cliente cliente = getCliente(idCliente);
		if (cliente == null)
			clienteNoEncontrado(idCliente);
		
		Producto producto = getProducto(idProducto);
		if (producto == null)
			productoNoEncontrado(idProducto);
		
    	LocalDate fecha = fechaJson.getFecha();
    	if (fecha == null) 
    		fecha = LocalDate.now();
    	
    	int cantidadProductos = repository.getCantidadProductoxCliente(producto.getId(),cliente.getId(),fecha);
    	if (cantidadProductos < 3 && producto.getStock() > 0) {
    		producto.setStock(producto.getStock()-1);
    		repositoryProducto.flush();
    		Compra compra = new Compra(cliente,producto,fecha);
    		compra = repository.save(compra);
    		return ResponseEntity.status(Response.SC_CREATED).body(compra);
    	} else {
    		String error;
    		if (producto.getStock() > 0)
	    		error = "El cliente alcanzo la cantidad maxima de productos diarios.";
    		else
    			error = "El pruducto con el id=" + idProducto + " no tiene stock.";
    		
    		return ResponseEntity.status(Response.SC_BAD_GATEWAY).body(error);
    	}
    }
	
	/**
	 * retorna una compra por id
	 * @param id de la compra
	 * @return 200 y la compra
	 * @return 404 si no existe la compra
	 */
    @GetMapping("/{id}")
    ResponseEntity<Object> getById(@PathVariable Integer id) {
    	try {
    		Compra compra = repository.findById(id).get();
    		return ResponseEntity.status(Response.SC_OK).body(compra);
    	} catch (Exception NoSuchElementException) {
    		return compraNoEncontrada(id);
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
		} catch (Exception NoSuchElementException) {
			return compraNoEncontrada(id);
		}
    }
	
	private boolean esValidaCompra(Compra compra) {
		return compra.getCliente() != null && compra.getProducto() != null && compra.getFecha() != null;
	}
	
}