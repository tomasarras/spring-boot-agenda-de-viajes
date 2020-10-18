package edu.isistan.controller;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
/**
 * Controlador de clientes
 * @author Tomas
 *
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("clientes")
public class ClienteController extends Controller {
	@Qualifier("clienteRepository")
	@Autowired
	private final ClienteRepository repository;

	public ClienteController(@Qualifier("clienteRepository") ClienteRepository repository) {
		this.repository = repository;
	}
	
	/**
	 * 
	 * @return 200 y los clientes
	 */
	@GetMapping("/")
    public Iterable<Cliente> getClientes() {
        return repository.findAll();
    }

	/**
	 * obtiene uno o mas clientes con un nombre
	 * @param nombre del/los clientes con ese nombre
	 * @return 200 y Iterable<Cliente> con los clientes con ese nombre
	 * @return 404 si no existe ningun cliente con ese nombre 
	 */
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Object> getClientesByNombre(@PathVariable String nombre) {
		Iterable<Cliente> clientes = repository.findAllByNombre(nombre);
		if (clientes.iterator().hasNext())
			return ResponseEntity.status(Response.SC_OK).body(clientes);
		else
			return clienteNoEncontrado(nombre);
    }

    /**
     * crea un cliente
     * @param cliente que se va a crear
     * @return cliente que se creo
     */
    @PostMapping("/")
    ResponseEntity<Cliente> nuevoCliente(@RequestBody Cliente cliente) {
    	Cliente c = new Cliente(cliente.getNombre());
		c = repository.save(c);
		return ResponseEntity.status(Response.SC_CREATED).body(c);
    }

    /**
     * obtiene un cliente por id
     * @param id del cliente
     * @return 200 y el cliente
     * @return 404 si no existe el cliente
     */
    @GetMapping("/{id}")
    ResponseEntity<Object> getById(@PathVariable Integer id) {
		Cliente cliente = getCliente(id);
		
		if (cliente == null)
			return clienteNoEncontrado(id);
		else
			return ResponseEntity.status(Response.SC_OK).body(cliente);
			
    }

    
    /**
     * Modifica un cliente
     * @param nuevoCliente los datos que se van a cambiar al cliente
     * @param id del cliente que se va a cambiar
     * @return 200 y el cliente modificado
     * @return 404 si no existe el cliente
     */
    @PutMapping("/{id}")
    ResponseEntity<Object> replaceCliente(@RequestBody Cliente nuevoCliente, @PathVariable Integer id) {
    	Cliente cliente = getCliente(id);
    	if (cliente == null)
    		return clienteNoEncontrado(id);
    	
    	cliente.setNombre(nuevoCliente.getNombre());
    	repository.flush();
    	return ResponseEntity.status(Response.SC_OK).body(cliente);
    }

    /**
     * 
     * @param id del cliente que se va a borrar
     * @return 204 si se borro el cliente
     * @return 404 si no existe el cliente
     */
    @DeleteMapping("/{id}")
    ResponseEntity<Object> borrarCliente(@PathVariable Integer id) {
    	try {
    		repository.deleteById(id);
    		return ResponseEntity.status(Response.SC_NO_CONTENT).build();
    	} catch (Exception NoSuchElementException) {
    		return clienteNoEncontrado(id);
    	}
    }
}
