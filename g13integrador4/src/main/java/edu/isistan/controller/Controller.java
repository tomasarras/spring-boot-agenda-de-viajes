package edu.isistan.controller;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import edu.isistan.model.Cliente;
import edu.isistan.model.Compra;
import edu.isistan.model.Producto;
import edu.isistan.repository.ClienteRepository;
import edu.isistan.repository.CompraRepository;
import edu.isistan.repository.ProductoRepository;
/**
 * Controlador con los metodos que tinen en comun los controladores,
 * como los codigos de respuesta al no encontrar una entidad o los metodos
 * para obtener una entidad
 * @author Tomas
 *
 */
public abstract class Controller {
	
	
	@Autowired
	private CompraRepository repositoryCompra;
	
	@Autowired
	private ClienteRepository repositoryCliente;
	
	@Autowired
	private ProductoRepository repositoryProducto;
	
	protected ResponseEntity<Object> compraNoEncontrada(int id) {
    	String error = "La compra con el id=" + id + " no existe.";
    	return ResponseEntity.status(Response.SC_NOT_FOUND).body(error);
    }
    
	protected ResponseEntity<Object> productoNoEncontrado(int id) {
    	String error = "El producto con el id=" + id + " no existe.";
    	return ResponseEntity.status(Response.SC_NOT_FOUND).body(error);
    }
	
	protected ResponseEntity<Object> productoNoEncontrado(String nombre) {
    	String error = "No existen productos de nombre=" + nombre;
    	return ResponseEntity.status(Response.SC_NOT_FOUND).body(error);
    }
    
    protected ResponseEntity<Object> clienteNoEncontrado(int id) {
    	String error = "El cliente con el id=" + id + " no existe.";
    	return ResponseEntity.status(Response.SC_NOT_FOUND).body(error);
    }
    
    protected ResponseEntity<Object> clienteNoEncontrado(String nombre) {
    	String error = "No existen clientes con el nombre=" + nombre;
    	return ResponseEntity.status(Response.SC_NOT_FOUND).body(error);
    }
    
    protected Compra getCompra(int idCompra) {
		try {
			return repositoryCompra.findById(idCompra).get();
		} catch (Exception NoSuchElementException) {
			return null;
		}
	}
    
    protected Producto getProducto(int id) {
    	try {
    		return repositoryProducto.findById(id).get();
    	} catch (Exception NoSuchElementException) {
    		return null;
    	}
    }
    
    protected Cliente getCliente(int id) {
    	try {
    		return repositoryCliente.findById(id).get();
    	} catch (Exception NoSuchElementException) {
    		return null;
    	}
    }
}
