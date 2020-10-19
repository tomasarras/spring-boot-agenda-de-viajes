package edu.isistan.controller;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
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
/**
 * Controlador de productos
 * @author Tomas
 *
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("productos")
public class ProductoController extends Controller {
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

	/**
	 * obtiene los productos que tengan ese nombre
	 * @param nombre de los productos
	 * @return 200 y Iterable<Producto> con los productos
	 * @return 404 si no existen productos con ese nombre
	 */
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Object> getProductosByNombre(@PathVariable String nombre) {
    	Iterable<Producto> productos = repository.findAllByNombre(nombre);
		if (productos.iterator().hasNext())
			return ResponseEntity.status(Response.SC_OK).body(productos);
		else
			return productoNoEncontrado(nombre);
    }

    /**
     * Crea un producto
     * @param producto que se va a crear
     * @return 201 y el producto
     * @return 400 si ingreso algun dato mal
     */
    @PostMapping("/")
    public ResponseEntity<Object> nuevoProducto(@RequestBody Producto producto) {
    	if (!esProductoValido(producto))
    		return productoNoEsValido();
    	Producto p = new Producto(producto.getNombre(),producto.getPrecio(),producto.getStock());
    	p = repository.save(p);
		return ResponseEntity.status(Response.SC_CREATED).body(p);
    }

    /**
     * Obtiene un producto por el id
     * @param id del producto
     * @return 200 y el producto
     * @return 404 si no existe el producto
     */
    @GetMapping("/{id}")
    ResponseEntity<Object> getById(@PathVariable Integer id) {
    	Producto producto = getProducto(id);
		
		if (producto == null)
			return productoNoEncontrado(id);
		else
			return ResponseEntity.status(Response.SC_OK).body(producto);
    }

    /**
     * Modifica un producto
     * @param nuevoProducto con los datos que se van a modificar
     * @param id del producto que se va a modificar
     * @return 200 y el producto modificado
     * @return 404 si el producto no existe
     * @return 400 si algun dato esta mal
     */
    @PutMapping("/{id}")
    ResponseEntity<Object> replaceProducto(@RequestBody Producto nuevoProducto, @PathVariable Integer id) {
    	if (!esProductoValido(nuevoProducto))
    		return productoNoEsValido();
    	
    	Producto producto = getProducto(id);
    	if (producto == null)
    		return productoNoEncontrado(id);
    	
    	producto.setNombre(nuevoProducto.getNombre());
    	producto.setPrecio(nuevoProducto.getPrecio());
    	producto.setStock(nuevoProducto.getStock());
    	repository.flush();
    	return ResponseEntity.status(Response.SC_OK).body(producto);
    }

    /**
     * 
     * @param id del producto que se va a borrar
     * @return 204 si el producto se borro
     * @return 404 si no existe el producto
     * @return 502 si no se borro el producto porque un cliente realizo una compra
     */
	@DeleteMapping("/{id}")
    ResponseEntity<Object> borrarProducto(@PathVariable Integer id) {
		try {
			if (getProducto(id) == null)
				return productoNoEncontrado(id);
    		repository.deleteById(id);
    		return ResponseEntity.status(Response.SC_NO_CONTENT).build();
    	} catch (Exception DataIntegrityViolationException) {
    		return ResponseEntity.status(Response.SC_BAD_GATEWAY).build();
    	}
    }
	
	private boolean esProductoValido(Producto nuevoProducto) {
		return !StringUtils.isEmpty(nuevoProducto.getNombre()) &&
				nuevoProducto.getPrecio() > 0 &&
				nuevoProducto.getStock() >= 0;
	}
	
	private ResponseEntity<Object> productoNoEsValido() {
		return ResponseEntity.status(Response.SC_BAD_REQUEST).build();
	}
}