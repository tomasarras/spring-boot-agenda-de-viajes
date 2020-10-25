package edu.isistan.controller;

import java.util.List;
import java.util.NoSuchElementException;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
import edu.isistan.model.Viaje;
import edu.isistan.repository.ViajeRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("viajes")
public class ViajeController {
	
	@Autowired
	private ViajeRepository repository;

	/**
	 * 
	 * @return 200 y todos los viajes
	 */
	@GetMapping
    public ResponseEntity<List<Viaje>> getViajes() {
		return ResponseEntity.
				status(Response.SC_OK).
				body(repository.findAll());
    }
	
	/**
	 * Obtiene un viaje por id
	 * @param id del viaje
	 * @return 200 y el viaje
	 * @return 404 si el viaje no existe
	 */
	@GetMapping("/{id}")
    public ResponseEntity<Viaje> getViaje(@PathVariable int id) {
		try {
			return ResponseEntity
					.status(Response.SC_OK)
					.body(repository.findById(id).get());
		} catch (NoSuchElementException e) {
			return ResponseEntity
				.status(Response.SC_NOT_FOUND)
				.build();
		}
    }
	
	/**
	 * 
	 * @param viaje que se va a crear
	 * @return 201 y el viaje si se creo
	 */
	@PostMapping
    public ResponseEntity<Viaje> crearViaje(@RequestBody Viaje viaje) {
		Viaje v = new Viaje(viaje.getNombre());
		v = repository.save(v);
		return ResponseEntity.status(Response.SC_CREATED).body(v);
    }
	
	/**
	 * 
	 * @param id del viaje que se va a borrar
	 * @return 204 si se borro el viaje
	 * @return 404 si el viaje no existe
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> borrarViaje(@PathVariable int id) {
		try {
			repository.deleteById(id);
			return ResponseEntity.status(Response.SC_NO_CONTENT).build();
		} catch (EmptyResultDataAccessException e) {
			return ResponseEntity.status(Response.SC_NOT_FOUND).build();
		}
	}
	
	/**
	 * 
	 * @param id del viaje que se va a modificar
	 * @param viaje con los datos que se van a modificar
	 * @return 200 y el viaje si se modifico
	 * @return 404 si el viaje no existe
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Viaje> modificarViaje(@PathVariable int id,@RequestBody Viaje viaje) {
		try {
			Viaje v = repository.findById(id).get();
			v.setNombre(viaje.getNombre());
			repository.flush();
			return ResponseEntity.status(Response.SC_OK).body(v);
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(Response.SC_NOT_FOUND).build();
		}
	}

}
