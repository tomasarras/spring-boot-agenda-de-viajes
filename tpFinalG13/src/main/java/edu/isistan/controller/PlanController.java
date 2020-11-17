package edu.isistan.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("/usuarios/viajes")
public class PlanController extends AbsController {
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
			if (lePerteneceAlUsuario(viaje)) {
				return ResponseEntity
						.status(Response.SC_OK)
						.body(viaje.getPlanes());
			} else {
				return ResponseEntity
						.status(Response.SC_FORBIDDEN)
						.build();
			}
		} catch (NoSuchElementException e) {
			return ResponseEntity
					.status(Response.SC_NOT_FOUND)
					.build();
		}
	}

	/**
	 * Obtiene todos los planes
	 * @return 200 y los planes
	 */
	@GetMapping("/planes")
    public ResponseEntity<List<Plan>> getPlanesDelUsuario(
    		@RequestParam(required = false) String zona,
    		@RequestParam(required = false, name = "fechaInicio") String fechaInicioString,
    		@RequestParam(required = false, name ="fechaFin") String fechaFinString,
    		@RequestParam(required = false) String estado
    		) {
		
		List<Plan> planes = null;
		int idUsuario = getIdUsuarioDelToken();
		
		boolean porZona = zona != null;
		boolean porFecha = fechaInicioString != null && fechaFinString != null;
		boolean porEstado = estado != null;
		
		if (porFecha) {
			LocalDateTime fechaInicio = getFechaString(fechaInicioString);
			LocalDateTime fechaFin = getFechaString(fechaFinString);
			
			if (fechaInicio == null || fechaFin == null) {
				return ResponseEntity
						.status(Response.SC_BAD_REQUEST)
						.build();
			} else {
				planes = repository.getPlanesDelUsuarioPorFecha(idUsuario,fechaInicio,fechaFin);
			}
		}
		
		if (porZona) {
			planes = repository.getPlanesDelUsuarioPorZona(idUsuario, zona);
		}
		
		if (porEstado) {
			LocalDateTime now = LocalDateTime.now();
			if (estado.equals("pendientes")) {
				planes = repository.getPlanesDelUsuarioPendientes(idUsuario,now);
			} else if (estado.equals("realizados")) {
				planes = repository.getPlanesDelUsuarioRealizados(idUsuario,now);
			}
		}
		
		if (planes == null) {
			planes = repository.getPlanesDelUsuario(idUsuario);
		}
		
		return ResponseEntity
				.status(Response.SC_OK)
				.body(planes);
    }
	
	private LocalDateTime getFechaString(String str) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		try {
			return LocalDateTime.parse(str, formatter);
		} catch (Exception e) {
			return null;
		}
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
			if (lePerteneceAlUsuario(p.getViaje())) {
				return ResponseEntity
						.status(Response.SC_OK)
						.body(p);
			} else {
				return ResponseEntity
						.status(Response.SC_FORBIDDEN)
						.build();
			}
		} catch (NoSuchElementException e) {
			return ResponseEntity
					.status(Response.SC_NOT_FOUND)
					.build();
		}
    }
	
	/**
	 * Crea un plan de un vuelo a un viaje
	 * @param PlanVuelo el que se va a crear
	 * @param idViaje al cual se va a asignar el plan
	 * @return 201 y el plan si se creo
	 * @return 404 si el viaje no existe
	 */
	@PostMapping("/{idViaje}/planes")
	private ResponseEntity<Object> crearPlan(@PathVariable int idViaje,@RequestBody Plan plan) {
		plan.setId(0);
		if (!plan.esValido()) {
			return ResponseEntity
					.status(Response.SC_BAD_REQUEST)
					.build();
		}
		
		try {
			Viaje viaje = repositoryViaje.findById(idViaje).get();
			
			if (!viaje.puedeGuardarPlan(plan)) {
				return ResponseEntity
						.status(Response.SC_BAD_REQUEST)
						.body("La fecha del plan no esta en el rango del viaje");
			}
			
			if (!lePerteneceAlUsuario(viaje)) {
				return ResponseEntity
				.status(Response.SC_FORBIDDEN)
				.build();
			}
			
			plan.setViaje(viaje);
			plan = repository.save(plan);
			return ResponseEntity
					.status(Response.SC_CREATED)
					.body(plan);
		} catch (NoSuchElementException e ) {
			return ResponseEntity
					.status(Response.SC_NOT_FOUND)
					.build();
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
			Plan plan = repository.findById(id).get();
			if (lePerteneceAlUsuario(plan.getViaje())) {
				repository.delete(plan);
				return ResponseEntity
						.status(Response.SC_NO_CONTENT)
						.build();
			} else {
				return ResponseEntity
						.status(Response.SC_FORBIDDEN)
						.build();
			}
		} catch (NoSuchElementException e) {
			return ResponseEntity
					.status(Response.SC_NOT_FOUND)
					.build();
		}
	}
	
	/**
	 * 
	 * @param id del plan que se quiere modificar
	 * @param plan con los datos que se van a cambiar
	 * @return 200 y el plan si fue modificado
	 * @return 404 si el plan no existe
	 */
	@PutMapping("/planes/{idPlan}")
	public ResponseEntity<Plan> modificarPlan(@PathVariable int idPlan,@RequestBody Plan plan) {
		plan.setId(0);
		if (!plan.esValido()) {
			return ResponseEntity
					.status(Response.SC_BAD_REQUEST)
					.build();
		}
		
		
		try {
			Plan p = repository.findById(idPlan).get();
			if(!lePerteneceAlUsuario(p.getViaje())) {
				return ResponseEntity
						.status(Response.SC_FORBIDDEN)
						.build();
			}
			
			p.modificarse(plan);
			repository.flush();
			return ResponseEntity
					.status(Response.SC_OK)
					.body(p);
		} catch (NoSuchElementException e) {
			return ResponseEntity
					.status(Response.SC_NOT_FOUND)
					.build();
		}
	}
}
