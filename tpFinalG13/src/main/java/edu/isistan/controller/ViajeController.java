package edu.isistan.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

	@GetMapping
    public List<Viaje> getViajes() {
		return repository.findAll();
    }
	
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
	
	@PostMapping
    public ResponseEntity<Viaje> crearViaje(@RequestBody Viaje viaje) {
		Viaje v = new Viaje(viaje.getNombre());
		v = repository.save(v);
		return ResponseEntity.status(Response.SC_CREATED).body(v);
    }
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> borrarViaje(@PathVariable int id) {
		try {
			repository.deleteById(id);
			return ResponseEntity.status(Response.SC_NO_CONTENT).build();
		} catch (EmptyResultDataAccessException e) {
			return ResponseEntity.status(Response.SC_NOT_FOUND).build();
		} catch (DataIntegrityViolationException e) {
			return borrarViajeConPlanes(id);
		}
	}

	private ResponseEntity<Void> borrarViajeConPlanes(int id) {
		// TODO Auto-generated method stub crear este metodo
		return null;
	}
}
