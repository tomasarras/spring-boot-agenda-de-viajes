package edu.isistan.controller;

import org.junit.jupiter.api.MethodOrderer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.util.HashMap;
import java.util.List;
import org.apache.catalina.connector.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import edu.isistan.model.Usuario;
import edu.isistan.model.planes.PlanReservaHotel;
import edu.isistan.model.planes.PlanViajeColectivo;
import edu.isistan.model.planes.PlanViajeTren;
import edu.isistan.model.planes.PlanVuelo;
import edu.isistan.repository.PlanRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql("/planes.sql")
public class PlanControllerTest {
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private PlanRepository repository;
	
	private static String token;
	private static HttpHeaders headerToken;
	
	
	@Before
	public void obtenerToken() {
		String usuarioPost = "{"
				+ "\"username\" : \"user1\","
				+ "\"password\" : \"password\""
				+ "}";
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> request = new HttpEntity<String>(usuarioPost ,headers);
		ResponseEntity<Usuario> response = testRestTemplate.postForEntity("/usuarios/login", request, Usuario.class);
		
		Usuario usuarioResponse = response.getBody();
		token = usuarioResponse.getToken();
		
		headerToken = new HttpHeaders();
		headerToken.set("Authorization", token);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void getPlanesDeViajeTest() {
		int cantidadPlanes = 2;
		HttpEntity<String> request = new HttpEntity<String>(headerToken);
		ResponseEntity<List> response = testRestTemplate.exchange("/usuarios/viajes/4/planes",HttpMethod.GET,request, List.class);
		List<HashMap<String, Object>> planes = response.getBody();
		
		assertEquals(Response.SC_OK,response.getStatusCodeValue());
		assertEquals(cantidadPlanes, planes.size());
		assertEquals(10,planes.get(0).get("id"));
		assertEquals("nombre1",planes.get(0).get("nombre"));
		assertEquals("compañia1",planes.get(0).get("compania"));
		assertEquals("2020-01-01 01:01",planes.get(0).get("fechaInicio"));
		assertEquals("2020-01-01 01:01",planes.get(0).get("fechaFin"));
		assertEquals("habitacion1",planes.get(0).get("habitacion"));
		assertEquals("direccion1",planes.get(0).get("direccion"));
		assertEquals("reservaHotel",planes.get(0).get("type"));
	}
	
	@Test
	public void getPlanesDeViajeDeOtroUsuarioTest() {
		HttpEntity<String> request = new HttpEntity<String>(headerToken);
		ResponseEntity<String> response = testRestTemplate.exchange("/usuarios/viajes/7/planes",HttpMethod.GET,request, String.class);
		
		assertEquals(Response.SC_FORBIDDEN,response.getStatusCodeValue());
		assertNull(response.getBody());
	}
	
	@Test
	public void getPlanesDeViajeNoEncontradoTest() {
		HttpEntity<String> request = new HttpEntity<String>(headerToken);
		ResponseEntity<String> response = testRestTemplate.exchange("/usuarios/viajes/99/planes",HttpMethod.GET,request, String.class);
		
		assertEquals(Response.SC_NOT_FOUND,response.getStatusCodeValue());
		assertNull(response.getBody());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void getPlanesDelUsuarioTest() {
		int cantidadPlanes = 3;
		HttpEntity<String> request = new HttpEntity<String>(headerToken);
		ResponseEntity<List> response = testRestTemplate.exchange("/usuarios/viajes/planes",HttpMethod.GET,request, List.class);
		List<HashMap<String, Object>> planes = response.getBody();
		
		assertEquals(Response.SC_OK,response.getStatusCodeValue());
		assertEquals(cantidadPlanes, planes.size());
		assertEquals(10,planes.get(0).get("id"));
		assertEquals("nombre1",planes.get(0).get("nombre"));
		assertEquals("compañia1",planes.get(0).get("compania"));
		assertEquals("2020-01-01 01:01",planes.get(0).get("fechaInicio"));
		assertEquals("2020-01-01 01:01",planes.get(0).get("fechaFin"));
		assertEquals("habitacion1",planes.get(0).get("habitacion"));
		assertEquals("direccion1",planes.get(0).get("direccion"));
		assertEquals("reservaHotel",planes.get(0).get("type"));
	}
	
	@Test
	public void getPlanTest() {
		HttpEntity<String> request = new HttpEntity<String>(headerToken);
		ResponseEntity<PlanReservaHotel> response = testRestTemplate.exchange("/usuarios/viajes/planes/10",HttpMethod.GET,request, PlanReservaHotel.class);
		PlanReservaHotel plan = response.getBody();
		
		assertEquals(Response.SC_OK,response.getStatusCodeValue());
		assertEquals(10,plan.getId());
		assertEquals("nombre1",plan.getNombre());
		assertEquals("compañia1",plan.getCompania());
		assertEquals("2020-01-01T01:01",plan.getFechaInicio().toString());
		assertEquals("2020-01-01T01:01",plan.getFechaFin().toString());
		assertEquals("habitacion1",plan.getHabitacion());
		assertEquals("direccion1",plan.getDireccion());
	}
	
	@Test
	public void getPlanDeOtroUsuarioTest() {
		HttpEntity<String> request = new HttpEntity<String>(headerToken);
		ResponseEntity<String> response = testRestTemplate.exchange("/usuarios/viajes/planes/12",HttpMethod.GET,request, String.class);
		
		assertEquals(Response.SC_FORBIDDEN,response.getStatusCodeValue());
	}
	
	@Test
	public void getPlanNoEncontradoTest() {
		HttpEntity<String> request = new HttpEntity<String>(headerToken);
		ResponseEntity<String> response = testRestTemplate.exchange("/usuarios/viajes/planes/99",HttpMethod.GET,request, String.class);
		
		assertEquals(Response.SC_NOT_FOUND,response.getStatusCodeValue());
	}
	
	@Test
	public void crearPlanReservaHotelTest() {
		String planPost = "{"
				+ "\"type\": \"reservaHotel\","
				+ "\"nombre\": \"hotel\","
				+ "\"compania\": \"compañia\","
				+ "\"fechaInicio\": \"2020-01-01 01:01\","
				+ "\"fechaFin\": \"2020-01-01 01:01\","
				+ "\"habitacion\": \"habitacion\","
				+ "\"direccion\": \"direccion\""
				+ "}";
		headerToken.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(planPost,headerToken);
		ResponseEntity<PlanReservaHotel> response = testRestTemplate.exchange("/usuarios/viajes/4/planes",HttpMethod.POST,request, PlanReservaHotel.class);
		PlanReservaHotel responsePlan = response.getBody();
		PlanReservaHotel repositoryPlan = (PlanReservaHotel) repository.findById(1).get();
		
		assertEquals(Response.SC_CREATED,response.getStatusCodeValue());
		assertEquals("hotel",responsePlan.getNombre());
		assertEquals("hotel",repositoryPlan.getNombre());
		assertEquals("compañia",responsePlan.getCompania());
		assertEquals("compañia",repositoryPlan.getCompania());
		assertEquals("2020-01-01T01:01",responsePlan.getFechaInicio().toString());
		assertEquals("2020-01-01T01:01",repositoryPlan.getFechaInicio().toString());
		assertEquals("2020-01-01T01:01",responsePlan.getFechaFin().toString());
		assertEquals("2020-01-01T01:01",repositoryPlan.getFechaFin().toString());
		assertEquals("habitacion",responsePlan.getHabitacion());
		assertEquals("habitacion",repositoryPlan.getHabitacion());
		assertEquals("direccion",responsePlan.getDireccion());
		assertEquals("direccion",repositoryPlan.getDireccion());
	}
	
	@Test
	public void crearPlanViajeColectivoTest() {
		String planPost = "{"
				+ "\"type\": \"viajeColectivo\","
				+ "\"nombre\": \"hotel\","
				+ "\"compania\": \"compañia\","
				+ "\"fechaInicio\": \"2020-01-01 01:01\","
				+ "\"fechaFin\": \"2020-01-01 01:01\","
				+ "\"asiento\": 5"
				+ "}";
		
		headerToken.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(planPost,headerToken);
		ResponseEntity<PlanViajeColectivo> response = testRestTemplate.exchange("/usuarios/viajes/4/planes",HttpMethod.POST,request, PlanViajeColectivo.class);
		PlanViajeColectivo responsePlan = response.getBody();
		PlanViajeColectivo repositoryPlan = (PlanViajeColectivo) repository.findById(1).get();
		
		assertEquals(Response.SC_CREATED,response.getStatusCodeValue());
		assertEquals("hotel",responsePlan.getNombre());
		assertEquals("hotel",repositoryPlan.getNombre());
		assertEquals("compañia",responsePlan.getCompania());
		assertEquals("compañia",repositoryPlan.getCompania());
		assertEquals("2020-01-01T01:01",responsePlan.getFechaInicio().toString());
		assertEquals("2020-01-01T01:01",repositoryPlan.getFechaInicio().toString());
		assertEquals("2020-01-01T01:01",responsePlan.getFechaFin().toString());
		assertEquals("2020-01-01T01:01",repositoryPlan.getFechaFin().toString());
		assertEquals(5,responsePlan.getAsiento());
		assertEquals(5,repositoryPlan.getAsiento());
	}
	
	@Test
	public void crearPlanViajeTrenTest() {
		String planPost = "{"
				+ "\"type\": \"viajeTren\","
				+ "\"nombre\": \"hotel\","
				+ "\"compania\": \"compañia\","
				+ "\"fechaInicio\": \"2020-01-01 01:01\","
				+ "\"fechaFin\": \"2020-01-01 01:01\","
				+ "\"asiento\": 5,"
				+ "\"estacion\" : \"estacion\""
				+ "}";
		
		headerToken.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(planPost,headerToken);
		ResponseEntity<PlanViajeTren> response = testRestTemplate.exchange("/usuarios/viajes/4/planes",HttpMethod.POST,request, PlanViajeTren.class);
		PlanViajeTren responsePlan = response.getBody();
		PlanViajeTren repositoryPlan = (PlanViajeTren) repository.findById(1).get();
		
		assertEquals(Response.SC_CREATED,response.getStatusCodeValue());
		assertEquals("hotel",responsePlan.getNombre());
		assertEquals("hotel",repositoryPlan.getNombre());
		assertEquals("compañia",responsePlan.getCompania());
		assertEquals("compañia",repositoryPlan.getCompania());
		assertEquals("2020-01-01T01:01",responsePlan.getFechaInicio().toString());
		assertEquals("2020-01-01T01:01",repositoryPlan.getFechaInicio().toString());
		assertEquals("2020-01-01T01:01",responsePlan.getFechaFin().toString());
		assertEquals("2020-01-01T01:01",repositoryPlan.getFechaFin().toString());
		assertEquals(5,responsePlan.getAsiento());
		assertEquals(5,repositoryPlan.getAsiento());
		assertEquals("estacion",responsePlan.getEstacion());
		assertEquals("estacion",repositoryPlan.getEstacion());
	}
	
	@Test
	public void crearPlanVueloTest() {
		String planPost = "{"
				+ "\"type\": \"vuelo\","
				+ "\"nombre\": \"hotel\","
				+ "\"compania\": \"compañia\","
				+ "\"fechaInicio\": \"2020-01-01 01:01\","
				+ "\"fechaFin\": \"2020-01-01 01:01\","
				+ "\"numeroVuelo\": 5,"
				+ "\"aeropuertoSalida\" : \"aeropuertoSalida\","
				+ "\"aeropuertoLlegada\" : \"aeropuertoLlegada\","
				+ "\"codigoReserva\" : 5,"
				+ "\"tiempoEscalas\" : 9000,"
				+ "\"aeronave\" : \"aeronave\""
				+ "}";
		
		headerToken.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(planPost,headerToken);
		ResponseEntity<PlanVuelo> response = testRestTemplate.exchange("/usuarios/viajes/4/planes",HttpMethod.POST,request, PlanVuelo.class);
		PlanVuelo responsePlan = response.getBody();
		PlanVuelo repositoryPlan = (PlanVuelo) repository.findById(1).get();
		
		assertEquals(Response.SC_CREATED,response.getStatusCodeValue());
		assertEquals("hotel",responsePlan.getNombre());
		assertEquals("hotel",repositoryPlan.getNombre());
		assertEquals("compañia",responsePlan.getCompania());
		assertEquals("compañia",repositoryPlan.getCompania());
		assertEquals("2020-01-01T01:01",responsePlan.getFechaInicio().toString());
		assertEquals("2020-01-01T01:01",repositoryPlan.getFechaInicio().toString());
		assertEquals("2020-01-01T01:01",responsePlan.getFechaFin().toString());
		assertEquals("2020-01-01T01:01",repositoryPlan.getFechaFin().toString());
		assertEquals(5,responsePlan.getNumeroVuelo());
		assertEquals(5,repositoryPlan.getNumeroVuelo());
		assertEquals("aeropuertoSalida",responsePlan.getAeropuertoSalida());
		assertEquals("aeropuertoSalida",repositoryPlan.getAeropuertoSalida());
		assertEquals("aeropuertoLlegada",repositoryPlan.getAeropuertoLlegada());
		assertEquals("aeropuertoLlegada",responsePlan.getAeropuertoLlegada());
		assertEquals(5,responsePlan.getCodigoReserva());
		assertEquals(5,repositoryPlan.getCodigoReserva());
		assertEquals(9000,repositoryPlan.getTiempoEscalas());
		assertEquals(9000,responsePlan.getTiempoEscalas());
		assertEquals("aeronave",responsePlan.getAeronave());
		assertEquals("aeronave",repositoryPlan.getAeronave());
	}
	
	@Test
	public void crearPlanAOtroUsuarioTest() {
		String planPost = "{"
				+ "\"type\": \"viajeColectivo\","
				+ "\"nombre\": \"hotel\","
				+ "\"compania\": \"compañia\","
				+ "\"fechaInicio\": \"2020-01-01 01:01\","
				+ "\"fechaFin\": \"2020-01-01 01:01\","
				+ "\"asiento\": 5"
				+ "}";
		
		headerToken.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(planPost,headerToken);
		ResponseEntity<String> response = testRestTemplate.exchange("/usuarios/viajes/7/planes",HttpMethod.POST,request, String.class);
		
		assertEquals(Response.SC_FORBIDDEN,response.getStatusCodeValue());
	}
	
	@Test
	public void crearPlanAViajeNoEncontradoTest() {
		String planPost = "{"
				+ "\"type\": \"viajeColectivo\","
				+ "\"nombre\": \"hotel\","
				+ "\"compania\": \"compañia\","
				+ "\"fechaInicio\": \"2020-01-01 01:01\","
				+ "\"fechaFin\": \"2020-01-01 01:01\","
				+ "\"asiento\": 5"
				+ "}";
		
		headerToken.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(planPost,headerToken);
		ResponseEntity<String> response = testRestTemplate.exchange("/usuarios/viajes/99/planes",HttpMethod.POST,request, String.class);
		
		assertEquals(Response.SC_NOT_FOUND,response.getStatusCodeValue());
	}
	
	@Test
	public void crearPlanDatosVaciosTest() {
		String sinTipo = "{"
				+ "\"type\": \"\","
				+ "\"nombre\": \"hotel\","
				+ "\"compania\": \"compañia\","
				+ "\"fechaInicio\": \"2020-01-01 01:01\","
				+ "\"fechaFin\": \"2020-01-01 01:01\","
				+ "\"asiento\": 5"
				+ "}";
		
		String sinNombre = "{"
				+ "\"type\": \"viajeColectivo\","
				+ "\"nombre\": \"\","
				+ "\"compania\": \"compañia\","
				+ "\"fechaInicio\": \"2020-01-01 01:01\","
				+ "\"fechaFin\": \"2020-01-01 01:01\","
				+ "\"asiento\": 5"
				+ "}";
		
		headerToken.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> request1 = new HttpEntity<String>(sinTipo,headerToken);
		HttpEntity<String> request2 = new HttpEntity<String>(sinNombre,headerToken);
		
		ResponseEntity<String> response1 = testRestTemplate.exchange("/usuarios/viajes/4/planes",HttpMethod.POST,request1, String.class);
		ResponseEntity<String> response2 = testRestTemplate.exchange("/usuarios/viajes/4/planes",HttpMethod.POST,request2, String.class);
		
		assertEquals(Response.SC_BAD_REQUEST,response1.getStatusCodeValue());
		assertEquals(Response.SC_BAD_REQUEST,response2.getStatusCodeValue());
	}
	
	@Test
	public void borrarPlanTest() {
		HttpEntity<String> request = new HttpEntity<String>(headerToken);
		
		ResponseEntity<String> response = testRestTemplate.exchange("/usuarios/viajes/planes/10",HttpMethod.DELETE,request, String.class);
		
		assertEquals(Response.SC_NO_CONTENT,response.getStatusCodeValue());
	}
	
	@Test
	public void borrarPlanDeOtroUsuarioTest() {
		HttpEntity<String> request = new HttpEntity<String>(headerToken);
		
		ResponseEntity<String> response = testRestTemplate.exchange("/usuarios/viajes/planes/12",HttpMethod.DELETE,request, String.class);
		
		assertEquals(Response.SC_FORBIDDEN,response.getStatusCodeValue());
	}
	
	@Test
	public void borrarPlanNoEncontradoTest() {
		HttpEntity<String> request = new HttpEntity<String>(headerToken);
		
		ResponseEntity<String> response = testRestTemplate.exchange("/usuarios/viajes/planes/99",HttpMethod.DELETE,request, String.class);
		
		assertEquals(Response.SC_NOT_FOUND,response.getStatusCodeValue());
	}
	
	@Test
	public void modificarPlanTest() {
		String planPost = "{"
				+ "\"type\": \"reservaHotel\","
				+ "\"nombre\": \"hotel editado\","
				+ "\"compania\": \"compañia editada\","
				+ "\"fechaInicio\": \"2020-01-01 01:01\","
				+ "\"fechaFin\": \"2020-01-01 01:01\","
				+ "\"direccion\": \"direccion editada\","
				+ "\"habitacion\": \"habitacion editada\""
				+ "}";
		
		headerToken.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(planPost,headerToken);
		ResponseEntity<PlanReservaHotel> response = testRestTemplate.exchange("/usuarios/viajes/planes/10",HttpMethod.PUT,request, PlanReservaHotel.class);
		PlanReservaHotel responsePlan = response.getBody();
		PlanReservaHotel repositoryPlan = (PlanReservaHotel) repository.findById(10).get();
		
		assertEquals(Response.SC_OK,response.getStatusCodeValue());
		assertEquals("hotel editado",responsePlan.getNombre());
		assertEquals("hotel editado",repositoryPlan.getNombre());
		assertEquals("compañia editada",responsePlan.getCompania());
		assertEquals("compañia editada",repositoryPlan.getCompania());
		assertEquals("2020-01-01T01:01",responsePlan.getFechaInicio().toString());
		assertEquals("2020-01-01T01:01",repositoryPlan.getFechaInicio().toString());
		assertEquals("2020-01-01T01:01",responsePlan.getFechaFin().toString());
		assertEquals("2020-01-01T01:01",repositoryPlan.getFechaFin().toString());
		assertEquals("direccion editada",repositoryPlan.getDireccion());
		assertEquals("direccion editada",responsePlan.getDireccion());
		assertEquals("habitacion editada",responsePlan.getHabitacion());
		assertEquals("habitacion editada",repositoryPlan.getHabitacion());
	}
	
	@Test
	public void modificarPlanDatosVaciosTest() {
		String sinTipo = "{"
				+ "\"type\": \"\","
				+ "\"nombre\": \"hotel\","
				+ "\"compania\": \"compañia\","
				+ "\"fechaInicio\": \"2020-01-01 01:01\","
				+ "\"fechaFin\": \"2020-01-01 01:01\","
				+ "\"direccion\": \"direccion\","
				+ "\"habitacion\": \"habitacion\""
				+ "}";
		
		String sinNombre = "{"
				+ "\"type\": \"reservaHotel\","
				+ "\"nombre\": \"\","
				+ "\"compania\": \"compañia\","
				+ "\"fechaInicio\": \"2020-01-01 01:01\","
				+ "\"fechaFin\": \"2020-01-01 01:01\","
				+ "\"direccion\": \"direccion\","
				+ "\"habitacion\": \"habitacion\""
				+ "}";
		
		headerToken.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> request1 = new HttpEntity<String>(sinTipo,headerToken);
		HttpEntity<String> request2 = new HttpEntity<String>(sinNombre,headerToken);
		
		ResponseEntity<String> response1 = testRestTemplate.exchange("/usuarios/viajes/planes/10",HttpMethod.PUT,request1, String.class);
		ResponseEntity<String> response2 = testRestTemplate.exchange("/usuarios/viajes/planes/10",HttpMethod.PUT,request2, String.class);
		
		assertEquals(Response.SC_BAD_REQUEST,response1.getStatusCodeValue());
		assertEquals(Response.SC_BAD_REQUEST,response2.getStatusCodeValue());
	}
	
	@Test
	public void modificarPlanNoEncontradoTest() {
		String planPut = "{"
				+ "\"type\": \"reservaHotel\","
				+ "\"nombre\": \"hotel\","
				+ "\"compania\": \"compañia\","
				+ "\"fechaInicio\": \"2020-01-01 01:01\","
				+ "\"fechaFin\": \"2020-01-01 01:01\","
				+ "\"direccion\": \"direccion\","
				+ "\"habitacion\": \"habitacion\""
				+ "}";
		
		headerToken.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> request = new HttpEntity<String>(planPut,headerToken);
		
		ResponseEntity<String> response = testRestTemplate.exchange("/usuarios/viajes/planes/99",HttpMethod.PUT,request, String.class);
		
		assertEquals(Response.SC_NOT_FOUND,response.getStatusCodeValue());
	}
	
	@Test
	public void modificarPlanAOtroUsuarioTest() {
		String planPut = "{"
				+ "\"type\": \"reservaHotel\","
				+ "\"nombre\": \"hotel\","
				+ "\"compania\": \"compañia\","
				+ "\"fechaInicio\": \"2020-01-01 01:01\","
				+ "\"fechaFin\": \"2020-01-01 01:01\","
				+ "\"direccion\": \"direccion\","
				+ "\"habitacion\": \"habitacion\""
				+ "}";
		
		headerToken.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> request = new HttpEntity<String>(planPut,headerToken);
		
		ResponseEntity<String> response = testRestTemplate.exchange("/usuarios/viajes/planes/12",HttpMethod.PUT,request, String.class);
		
		assertEquals(Response.SC_FORBIDDEN,response.getStatusCodeValue());
	}
	
	
}
