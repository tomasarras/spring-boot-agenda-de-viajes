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
import edu.isistan.model.Plan;
import edu.isistan.model.Viaje;
import edu.isistan.repository.PlanRepository;
import edu.isistan.repository.ViajeRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
/**
 * Controller de planes que contienen los viajes
 * @author Tomas
 *
 */

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("viajes")
public class PlanController {
	@Autowired
	private PlanRepository repository;
	
	@Autowired
	private ViajeRepository repositoryViaje;
	
	/**
	 * busca los planes de un viaje
	 * @param idViaje id del viaje que se quiere obtener los planes
	 * @return 404 si el viaje no existe
	 * @return 200 y los planes del viaje
	 */
	@ApiOperation(value = "Obtener los planes de un viaje")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 404, message = "Not found")
	})
	@GetMapping("/{idViaje}/planes")
	public ResponseEntity<List<Plan>> getPlanesDeViaje(@PathVariable int idViaje) {
		try {
			Viaje viaje = repositoryViaje.findById(idViaje).get();
			return ResponseEntity.
					status(Response.SC_OK).
					body(viaje.getPlanes());
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(Response.SC_NOT_FOUND).build();
		}
	}

	/**
	 * Obtiene todos los planes
	 * @return 200 y los planes
	 */
	@GetMapping("/planes")
    public ResponseEntity<List<Plan>> getPlanes() {
		return ResponseEntity
				.status(Response.SC_OK)
				.body(repository.findAll());
    }
	
	/**
	 * Obtener un plan por id
	 * @param id del plan que se quiere obtener
	 * @return 200 y el plan
	 * @return 404 si el plan no existe
	 */
	@GetMapping("/planes/{id}")
    public ResponseEntity<Plan> getPlan(@PathVariable int id) {
		try {
			Plan p = repository.findById(id).get(); 
			return ResponseEntity.status(Response.SC_OK).body(p);
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(Response.SC_NOT_FOUND).build();
		}
    }
	
	/**
	 * Crea un plan a un viaje
	 * @param plan el que se va a crear
	 * @param idViaje al cual se va a asignar el plan
	 * @return 201 y el plan si se creo
	 * @return 404 si el viaje no existe
	 */
	
	@PostMapping("/{idViaje}/planes")
    public ResponseEntity<Plan> crearPlan(@RequestBody Plan plan,@PathVariable int idViaje) {
		try {
			Viaje viaje = repositoryViaje.findById(idViaje).get();
			Plan p = new Plan(plan.getNombre(),viaje);
			p = repository.save(p);
			return ResponseEntity.status(Response.SC_CREATED).body(p);
		} catch (NoSuchElementException e ) {
			return ResponseEntity.status(Response.SC_NOT_FOUND).build();
		}
    }
	
	/**
	 * 
	 * @param id del plan que se quiere borrar
	 * @return 204 si el plan fue borrado
	 * @return 404 si el plan no existe
	 */
	@DeleteMapping("/planes/{id}")
	public ResponseEntity<Void> borrarPlan(@PathVariable int id) {
		try {
			repository.deleteById(id);
			return ResponseEntity.status(Response.SC_NO_CONTENT).build();
		} catch (EmptyResultDataAccessException e) {
			return ResponseEntity.status(Response.SC_NOT_FOUND).build();
		}
	}
	
	/**
	 * 
	 * @param id del plan que se quiere modificar
	 * @param plan con los datos que se van a cambiar
	 * @return 200 y el plan si fue modificado
	 * @return 404 si el plan no existe
	 */
	@PutMapping("/planes/{id}")
	public ResponseEntity<Plan> modificarPlan(@PathVariable int id,@RequestBody Plan plan) {
		try {
			Plan p = repository.findById(id).get();
			p.setNombre(plan.getNombre());
			repository.flush();
			return ResponseEntity.status(Response.SC_OK).body(p);
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(Response.SC_NOT_FOUND).build();
		}
	}
}
