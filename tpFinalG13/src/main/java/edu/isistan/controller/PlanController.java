package edu.isistan.controller;

import java.util.List;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
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
import edu.isistan.model.Plan;
import edu.isistan.model.Viaje;
import edu.isistan.repository.PlanRepository;
import edu.isistan.repository.ViajeRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("viajes")
public class PlanController {
	@Autowired
	private PlanRepository repository;
	
	@Autowired
	private ViajeRepository repositoryViaje;

	@GetMapping("/planes")
    public ResponseEntity<List<Plan>> getPlanes() {
		return ResponseEntity
				.status(Response.SC_OK)
				.body(repository.findAll());
    }
	
	@GetMapping("/planes/{id}")
    public ResponseEntity<Plan> getPlan(@PathVariable int id) {
		try {
			Plan p = repository.findById(id).get(); 
			return ResponseEntity.status(Response.SC_OK).body(p);
		} catch (Exception e) {
			return ResponseEntity.status(Response.SC_NOT_FOUND).build();
		}
    }
	
	@PostMapping("/{idViaje}/planes")
    public ResponseEntity<Plan> crearPlan(@RequestBody Plan plan,@PathVariable int idViaje) {
		Viaje viaje = repositoryViaje.findById(idViaje).get();
		Plan p = new Plan(plan.getNombre(),viaje);
		p = repository.save(p);
		return ResponseEntity.status(Response.SC_CREATED).body(p);
    }
	
	@DeleteMapping("/planes/{id}")
	public ResponseEntity<Void> borrarPlan(@PathVariable int id) {
		try {
			repository.deleteById(id);
			return ResponseEntity.status(Response.SC_NO_CONTENT).build();
		} catch (EmptyResultDataAccessException e) {
			return ResponseEntity.status(Response.SC_NOT_FOUND).build();
		}
	}
}
