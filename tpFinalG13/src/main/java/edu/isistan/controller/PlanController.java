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
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller de planes que contienen los viajes, para cada llamado a este
 * controlador, se requiere un token el cual contiene el id del usuario 
 * con el que se buscan/crean/borran planes de el usuario con ese id
 * 
 * @author Tomas Arras
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
	 * busca los planes de un viaje que sean del usuario
	 * @param idViaje id del viaje que se quiere obtener los planes
	 * @param @param token con el id del usuario que quiere obtener los planes
	 * @return 404 si el viaje no existe
	 * @return 200 y los planes del viaje
	 * @return 403 si se intenta acceder a los planes de otro usuario
	 */
	@ApiOperation(value = "Obtener los planes de un viaje")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 403, message = "No se puede acceder a los planes de otro usuario")
	})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
		@ApiImplicitParam(name = "idViaje", value = "id del viaje que se quiere obtener los planes")
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
	 * Obtiene todos los planes de un usuario, opcionalmente puede recibir un criterio de busqueda
	 * para los planes
	 * @param fechaInicio y fechaFin, es el rango en el que se van a buscar los planes
	 * @param estado es el estado en que se van a buscar los planes, pueden ser pendientes o realizados
	 * @param zona es la ciudad del viaje en el que esta el plan
	 * @param token con el id del usuario que quiere obtener sus planes
	 * @return 200 y los planes por algun criterio
	 * @return 400 si es por algun criterio fechas, las fechas no son validas
	 */
	@ApiOperation(value = "Obtener todos los planes del usuario")
	@GetMapping("/planes")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "estado", value = "puede ser 'realizados' o 'pendientes'", required = false, dataType = "string", paramType = "query", example = "pendientes"),
		@ApiImplicitParam(name = "fechaInicio", value = "fecha de inicio a partir del cual se buscan los planes, si hay fecha inicio, debe ir una fecha de fin tambien, formato yyyy-MM-dd HH:mm", required = false, dataType = "date", paramType = "query"),
		@ApiImplicitParam(name = "fechaFin", value = "fecha de fin a partir del cual se buscan los planes, si hay fecha fin, debe ir una fecha de inicio tambien, formato yyyy-MM-dd HH:mm", required = false, dataType = "date", paramType = "query"),
		@ApiImplicitParam(name = "zona", value = "los planes que esten en un viaje y que su ciudad de destino sea esa zona o ciudad", required = false, dataType = "string", paramType = "query"),
		@ApiImplicitParam(name = "Authorization", value = "Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
	})
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
	 * @param token con el id del usuario que quiere obtener el plan
	 * @return 200 y el plan
	 * @return 404 si el plan no existe
	 * @return 403 si se intenta acceder al plan de otro usuario
	 */
	@ApiOperation(value = "Obtener un plan por id")
	@GetMapping("/planes/{id}")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 403, message = "No se puede acceder al plan de otro usuario")
	})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
		@ApiImplicitParam(name = "id", value = "id del plan")
	})
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
	 * Crea un plan a un viaje
	 * @param idViaje el viaje al cual se le va a crear un plan
	 * @param plan el plan que se va a crear
	 * @param token con el id del usuario que quiere crear un plan
	 * @return 201 y el plan si se creo
	 * @return 404 si el viaje no existe
	 * @return 403 si se intenta crear un plan a un viaje que pertenece a otro usuario
	 * @return 400 si la fecha de inicio y fin del plan no estan en el rango de fechas del viaje
	 */
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created"),
			@ApiResponse(code = 404, message = "El viaje al cual se le quiere agregar un plan no existe"),
			@ApiResponse(code = 403, message = "Si se intenta crear un plan a un viaje que pertenece a otro usuario"),
			@ApiResponse(code = 400, message = "Si la fecha de inicio y fin del plan no estan en el rango de las fechas del viaje")
	})
	@ApiOperation(value = "Crear un plan a un viaje")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
		@ApiImplicitParam(name = "idViaje", value = "id del viaje que se quiere agregar el plan"),
		@ApiImplicitParam(name = "plan", value = "json con los datos del plan comun o un plan especifico, "
				+ "usar el parametro 'type' seguido del tipo de plan que se quiere agregar, ver en la"
				+ " documentacion para mas detalles")
		})
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
	 * Borrar un plan
	 * @param id del plan que se quiere borrar
	 * @param token con el id del usuario que quiere borrar el plan
	 * @return 204 si el plan fue borrado
	 * @return 404 si el plan no existe
	 * @return 403 si se intenta borrar un plan de otro usuario
	 */
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "No content"),
			@ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 403, message = "Si se intenta borrar un plan a un viaje que pertenece a otro usuario")
	})
	@DeleteMapping("/planes/{id}")
	@ApiOperation(value = "Borrar plan")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
		@ApiImplicitParam(name = "id", value = "id del plan")
	})
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
	 * @param token con el id del usuario que quiere modificar el plan
	 * @return 200 y el plan si fue modificado
	 * @return 404 si el plan no existe
	 * @return 403 si se intenta modificar el plan de otro usuario
	 */
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 404, message = "El plan que se intenta modificar no existe"),
			@ApiResponse(code = 403, message = "Se intenta modificar un plan que pertenece a otro usuario")
	})
	@PutMapping("/planes/{idPlan}")
	@ApiOperation(value = "Modificar plan")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
		@ApiImplicitParam(name = "idPlan", value = "id del plan", required = true),
		@ApiImplicitParam(name = "plan", value = "plan con los datos que se van a modificar", required = true)
	})
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
