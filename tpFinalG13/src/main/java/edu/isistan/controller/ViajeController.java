package edu.isistan.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import edu.isistan.model.Usuario;
import edu.isistan.model.Viaje;
import edu.isistan.reportes.ReporteCiudad;
import edu.isistan.repository.UsuarioRepository;
import edu.isistan.repository.ViajeRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("usuarios/viajes")
public class ViajeController extends AbsController {
	
	@Autowired
	private ViajeRepository repository;
	
	@Autowired
	private UsuarioRepository repositoryUsuarios;

	/**
	 * Obtener todos los viajes de un usuario y opcionalmente algun criterio
	 * @param criterio opcional, puede ser 'realizados' o 'pendientes'
	 * @param token con el id del usuario que se quiere obtener los viajes
	 * @return 200 y los viajes
	 */
	
	@GetMapping
	@ApiOperation(value = "Obtener los viajes del usuario")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
		@ApiImplicitParam(name = "criterio", value = "un criterio por el cual obtener los viajes, puede ser 'realizados' o 'pendientes'", example = "realizados")
	})
    public ResponseEntity<List<Viaje>> getViajesDeUsuario(@RequestParam(required = false) String criterio) {
		int idUsuario = getIdUsuarioDelToken();
		List<Viaje> viajes;
		if (criterio != null) {
			LocalDateTime now = LocalDateTime.now();
			if (criterio.equals("realizados")) {
				viajes = repository.buscarViajesDeUsuarioRealizados(idUsuario,now);
			} else if (criterio.equals("pendientes")) {
				viajes = repository.buscarViajesDeUsuarioPendientes(idUsuario,now);
			} else {
				viajes = repository.buscarViajesDeUsuario(idUsuario);
			}
		} else {
			viajes = repository.buscarViajesDeUsuario(idUsuario);
		}
		
		return ResponseEntity
				.status(Response.SC_OK)
				.body(viajes);
	}
	
	/**
	 * Obtener las ciudades mas visitadas con la cantidad de viajes que tengan y ordenadas, 
	 * solo disponible si es admin
	 * @param token con el rol ADMIN
	 * @return 200
	 */
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping("/ciudades")
	@ApiOperation(value = "Obtener las ciudades mas visitadas con la cantidad de viajes que tengan"
			+ " y ordenadas, solo disponible si es admin")
	@ApiImplicitParam(name = "Authorization", value = "Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    public ResponseEntity<List<ReporteCiudad>> getCiudadesMasVisitadas() {
		return ResponseEntity
				.status(Response.SC_OK)
				.body(repository.reporteCiudadesMasVisitadas());
	}
	
	/**
	 * Obtiene un viaje por id
	 * @param id del viaje
	 * @param token con el id del usuario que quiere obtener el viaje
	 * @return 200 y el viaje
	 * @return 404 si el viaje no existe
	 * @return 403 si se intenta acceder al viaje de otro usuario
	 */
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 403, message = "Se intenta acceder al viaje de otro usuario")
	})
	@GetMapping("/{id}")
	@ApiOperation(value = "Obtener un viaje por id")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
		@ApiImplicitParam(name = "id", value = "id del viaje", required = true)
	})
    public ResponseEntity<Viaje> getViaje(@PathVariable int id) {
		try {
			Viaje viaje = repository.findById(id).get();
			if (lePerteneceAlUsuario(viaje)) {
				return ResponseEntity
						.status(Response.SC_OK)
						.body(viaje);
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
	 * Crear un viaje
	 * @param viaje con los datos
	 * @param token con el id del usuario que quiere crear el viaje
	 * @return 201 si se creo 
	 * @return 400 si algun dato esta vacio
	 */
	@PostMapping
	@ApiOperation(value = "Crear un viaje")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
		@ApiImplicitParam(name = "viaje", value = "json con los datos del viaje", required = true)
	})
    public ResponseEntity<Viaje> crearViaje(@RequestBody Viaje viaje) {
		if (!viaje.esValido()) {
			return ResponseEntity
					.status(Response.SC_BAD_REQUEST)
					.build();
		}
		
		Viaje v = new Viaje();
		v.setNombre(viaje.getNombre());
		v.setCiudadDestino(viaje.getCiudadDestino());
		v.setDescripcion(viaje.getDescripcion());
		v.setFechaFin(viaje.getFechaFin());
		v.setFechaInicio(viaje.getFechaInicio());
		int idUsuario = getIdUsuarioDelToken();
		Usuario usuario = repositoryUsuarios.findById(idUsuario).get();
		v.setUsuario(usuario);
		v = repository.save(v);
		System.out.println(v);

		return ResponseEntity
				.status(Response.SC_CREATED)
				.body(v);
    }
	
	/**
	 * Borrar un viaje
	 * @param id del viaje que se va a borrar
	 * @param token con el id del usuario que quiere borrar el viaje
	 * @return 204 si se borro el viaje
	 * @return 404 si el viaje no existe
	 * @return 403 si se intenta borrar el viaje de otro usuario
	 */
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "No content"),
			@ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 403, message = "Se intenta borrar el viaje de otro usuario")
	})
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Borrar viaje")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
		@ApiImplicitParam(name = "id", value = "id del viaje que se quiere borrar", required = true)
	})
	public ResponseEntity<Void> borrarViaje(@PathVariable int id) {
		try {
			Viaje viaje = repository.findById(id).get();
			if (lePerteneceAlUsuario(viaje)) {
				repository.delete(viaje);
				
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
	 * @param id del viaje que se va a modificar
	 * @param viaje con los datos que se van a modificar
	 * @param token con el id del usuario que quiere modificar el viaje
	 * @return 200 y el viaje si se modifico
	 * @return 404 si el viaje no existe
	 * @return 403 si el viaje que se quiere modificar pertenece a otro usuario
	 */
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 403, message = "Se intenta modificar el viaje de otro usuario")
	})
	@PutMapping("/{id}")
	@ApiOperation(value = "Modificar un viaje")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "id del viaje que se modificar", required = true),
		@ApiImplicitParam(name = "viaje", value = "json con los datos del viaje que se quieren modificar", required = true),
		@ApiImplicitParam(name = "Authorization", value = "Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
	})
	public ResponseEntity<Viaje> modificarViaje(@PathVariable int id,@RequestBody Viaje viaje) {
		if (!viaje.esValido()) {
			return ResponseEntity
					.status(Response.SC_BAD_REQUEST)
					.build();
		}
		
		try {
			Viaje v = repository.findById(id).get();
			if (lePerteneceAlUsuario(v)) {
				v.setNombre(viaje.getNombre());
				v.setCiudadDestino(viaje.getCiudadDestino());
				v.setFechaInicio(viaje.getFechaInicio());
				v.setFechaFin(viaje.getFechaFin());
				v.setDescripcion(viaje.getDescripcion());
				repository.flush();
				
				return ResponseEntity
						.status(Response.SC_OK)
						.body(v);
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

	
}
