package edu.isistan.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
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
import edu.isistan.model.Viaje;
import edu.isistan.repository.ViajeRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql("/viajes.sql")
public class ViajeControllerTest {
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private ViajeRepository repository;
	
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
	public void getViajesDeUsuarioTest() {
		int cantidadViajes = 2;
		HttpEntity<String> request = new HttpEntity<String>(headerToken);
		ResponseEntity<List> response = testRestTemplate.exchange("/usuarios/viajes",HttpMethod.GET,request, List.class);
		List<HashMap<String, Object>> viajes = response.getBody();
		
		assertEquals(Response.SC_OK,response.getStatusCodeValue());
		assertEquals(cantidadViajes, viajes.size());
		assertEquals(4,viajes.get(0).get("id"));
		assertEquals("nombre1",viajes.get(0).get("nombre"));
		assertEquals("ciudad1",viajes.get(0).get("ciudadDestino"));
		assertEquals("2020-01-01",viajes.get(0).get("fechaInicio"));
		assertEquals("2020-01-01",viajes.get(0).get("fechaFin"));
		assertEquals("descripcion1",viajes.get(0).get("descripcion"));
	}
	
	@Test
	public void getViajeTest() {
		HttpEntity<String> request = new HttpEntity<String>(headerToken);
		ResponseEntity<Viaje> response = testRestTemplate.exchange("/usuarios/viajes/4",HttpMethod.GET,request, Viaje.class);
		Viaje viaje = response.getBody();
		
		assertEquals(Response.SC_OK,response.getStatusCodeValue());
		assertEquals(4,viaje.getId());
		assertEquals("nombre1",viaje.getNombre());
		assertEquals("ciudad1",viaje.getCiudadDestino());
		assertEquals("2020-01-01",viaje.getFechaInicio().toString());
		assertEquals("2020-01-01",viaje.getFechaFin().toString());
		assertEquals("descripcion1",viaje.getDescripcion());
	}
	
	@Test
	public void getViajeDeOtroUsuarioTest() {
		HttpEntity<String> request = new HttpEntity<String>(headerToken);
		ResponseEntity<String> response = testRestTemplate.exchange("/usuarios/viajes/6",HttpMethod.GET,request, String.class);
		
		assertEquals(Response.SC_FORBIDDEN,response.getStatusCodeValue());
		assertNull(response.getBody());
	}
	
	@Test
	public void getViajeNoEncontradoTest() {
		HttpEntity<String> request = new HttpEntity<String>(headerToken);
		ResponseEntity<String> response = testRestTemplate.exchange("/usuarios/viajes/99",HttpMethod.GET,request, String.class);
		
		assertEquals(Response.SC_NOT_FOUND,response.getStatusCodeValue());
	}
	
	@Test
	public void crearViajeTest() {
		String viajePost = "{"
				+ "\"nombre\" : \"nombreViaje\","
				+ "\"ciudadDestino\" : \"ciudadDestino\","
				+ "\"fechaInicio\" : \"2020-02-02\","
				+ "\"fechaFin\" : \"2020-02-02\","
				+ "\"descripcion\" : \"descripcion\""
				+ "}";
		
		headerToken.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(viajePost,headerToken);
		ResponseEntity<Viaje> response = testRestTemplate.exchange("/usuarios/viajes",HttpMethod.POST,request, Viaje.class);
		Viaje viaje = response.getBody();
		Viaje vRepository = repository.findById(1).get();
		
		assertEquals(Response.SC_CREATED,response.getStatusCodeValue());
		assertEquals(1,viaje.getId());
		assertEquals("nombreViaje",viaje.getNombre());
		assertEquals("nombreViaje",vRepository.getNombre());
		assertEquals("ciudadDestino",viaje.getCiudadDestino());
		assertEquals("ciudadDestino",vRepository.getCiudadDestino());
		assertEquals("2020-02-02",viaje.getFechaInicio().toString());
		assertEquals("2020-02-02",vRepository.getFechaInicio().toString());
		assertEquals("2020-02-02",viaje.getFechaFin().toString());
		assertEquals("2020-02-02",vRepository.getFechaFin().toString());
		assertEquals("descripcion",viaje.getDescripcion());
		assertEquals("descripcion",vRepository.getDescripcion());
	}
	
	@Test
	public void borrarViajeTest() {
		HttpEntity<String> request = new HttpEntity<String>(headerToken);
		ResponseEntity<String> response = testRestTemplate.exchange("/usuarios/viajes/4",HttpMethod.DELETE,request, String.class);
		
		assertEquals(Response.SC_NO_CONTENT,response.getStatusCodeValue());
		Assertions.assertThrows(NoSuchElementException.class, () -> {
			repository.findById(4).get();
		});
	}
	
	 
	@Test
	public void borrarViajeDeOtroUsuarioTest() {
		HttpEntity<String> request = new HttpEntity<String>(headerToken);
		ResponseEntity<String> response = testRestTemplate.exchange("/usuarios/viajes/6",HttpMethod.DELETE,request, String.class);
		
		assertEquals(Response.SC_FORBIDDEN,response.getStatusCodeValue());
		assertNull(response.getBody());
	}
	
	@Test
	public void borrarViajeNoEncontradoTest() {
		HttpEntity<String> request = new HttpEntity<String>(headerToken);
		ResponseEntity<String> response = testRestTemplate.exchange("/usuarios/viajes/99",HttpMethod.DELETE,request, String.class);
		
		assertEquals(Response.SC_NOT_FOUND,response.getStatusCodeValue());
	}
	
	@Test
	public void modificarViajeTest() {
		String viajePut = "{"
				+ "\"nombre\" : \"nombre modificado\","
				+ "\"ciudadDestino\" : \"ciudad modificada\","
				+ "\"fechaInicio\" : \"2021-01-01\","
				+ "\"fechaFin\" : \"2021-01-01\","
				+ "\"descripcion\" : \"descripcion modificada\""
				+ "}";
		
		headerToken.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(viajePut,headerToken);
		ResponseEntity<Viaje> response = testRestTemplate.exchange("/usuarios/viajes/4",HttpMethod.PUT,request, Viaje.class);
		Viaje viaje = response.getBody();
		Viaje vRepository = repository.findById(4).get();
		
		assertEquals(Response.SC_OK,response.getStatusCodeValue());
		assertEquals(4,viaje.getId());
		assertEquals("nombre modificado",viaje.getNombre());
		assertEquals("nombre modificado",vRepository.getNombre());
		assertEquals("ciudad modificada",viaje.getCiudadDestino());
		assertEquals("ciudad modificada",vRepository.getCiudadDestino());
		assertEquals("2021-01-01",viaje.getFechaInicio().toString());
		assertEquals("2021-01-01",vRepository.getFechaInicio().toString());
		assertEquals("2021-01-01",viaje.getFechaFin().toString());
		assertEquals("2021-01-01",vRepository.getFechaFin().toString());
		assertEquals("descripcion modificada",viaje.getDescripcion());
		assertEquals("descripcion modificada",vRepository.getDescripcion());
	}
	
	
	@Test
	public void modificarViajeDeOtroUsuarioTest() {
		String viajePut = "{"
				+ "\"nombre\" : \"nombre modificado\","
				+ "\"ciudadDestino\" : \"ciudad modificada\","
				+ "\"fechaInicio\" : \"2021-01-01\","
				+ "\"fechaFin\" : \"2021-01-01\","
				+ "\"descripcion\" : \"descripcion modificada\""
				+ "}";
		
		headerToken.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(viajePut,headerToken);
		ResponseEntity<String> response = testRestTemplate.exchange("/usuarios/viajes/6",HttpMethod.PUT,request, String.class);
		
		assertEquals(Response.SC_FORBIDDEN,response.getStatusCodeValue());
		assertNull(response.getBody());
	}
	
	@Test
	public void modificarViajeNoEncontradoTest() {
		String viajePut = "{"
				+ "\"nombre\" : \"nombre modificado\","
				+ "\"ciudadDestino\" : \"ciudad modificada\","
				+ "\"fechaInicio\" : \"2021-01-01\","
				+ "\"fechaFin\" : \"2021-01-01\","
				+ "\"descripcion\" : \"descripcion modificada\""
				+ "}";
		
		headerToken.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(viajePut,headerToken);
		ResponseEntity<String> response = testRestTemplate.exchange("/usuarios/viajes/99",HttpMethod.PUT,request, String.class);
		
		assertEquals(Response.SC_NOT_FOUND,response.getStatusCodeValue());
	}
	
	@Test
	public void modificarViajeSinDatosTest() {
		String sinNombre = "{"
				+ "\"nombre\" : \"\","
				+ "\"ciudadDestino\" : \"ciudad modificada\","
				+ "\"fechaInicio\" : \"2021-01-01\","
				+ "\"fechaFin\" : \"2021-01-01\","
				+ "\"descripcion\" : \"descripcion modificada\""
				+ "}";
		
		String sinCiudad = "{"
				+ "\"nombre\" : \"nombre modificado\","
				+ "\"ciudadDestino\" : \"\","
				+ "\"fechaInicio\" : \"2021-01-01\","
				+ "\"fechaFin\" : \"2021-01-01\","
				+ "\"descripcion\" : \"descripcion modificada\""
				+ "}";
		
		String sinFechaInicio = "{"
				+ "\"nombre\" : \"nombre modificado\","
				+ "\"ciudadDestino\" : \"ciudad modificada\","
				+ "\"fechaInicio\" : \"\","
				+ "\"fechaFin\" : \"2021-01-01\","
				+ "\"descripcion\" : \"descripcion modificada\""
				+ "}";
		
		String sinFechaFin = "{"
				+ "\"nombre\" : \"nombre modificado\","
				+ "\"ciudadDestino\" : \"ciudad modificada\","
				+ "\"fechaInicio\" : \"2021-01-01\","
				+ "\"fechaFin\" : \"\","
				+ "\"descripcion\" : \"descripcion modificada\""
				+ "}";
		
		String sinDescripcion = "{"
				+ "\"nombre\" : \"nombre modificado\","
				+ "\"ciudadDestino\" : \"ciudad modificada\","
				+ "\"fechaInicio\" : \"2021-01-01\","
				+ "\"fechaFin\" : \"2021-01-01\","
				+ "\"descripcion\" : \"\""
				+ "}";
		
		headerToken.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> request1 = new HttpEntity<String>(sinNombre,headerToken);
		HttpEntity<String> request2 = new HttpEntity<String>(sinCiudad,headerToken);
		HttpEntity<String> request3 = new HttpEntity<String>(sinFechaInicio,headerToken);
		HttpEntity<String> request4 = new HttpEntity<String>(sinFechaFin,headerToken);
		HttpEntity<String> request5 = new HttpEntity<String>(sinDescripcion,headerToken);
		
		ResponseEntity<String> response1 = testRestTemplate.exchange("/usuarios/viajes/4",HttpMethod.PUT,request1, String.class);
		ResponseEntity<String> response2 = testRestTemplate.exchange("/usuarios/viajes/4",HttpMethod.PUT,request2, String.class);
		ResponseEntity<String> response3 = testRestTemplate.exchange("/usuarios/viajes/4",HttpMethod.PUT,request3, String.class);
		ResponseEntity<String> response4 = testRestTemplate.exchange("/usuarios/viajes/4",HttpMethod.PUT,request4, String.class);
		ResponseEntity<String> response5 = testRestTemplate.exchange("/usuarios/viajes/4",HttpMethod.PUT,request5, String.class);
		
		assertEquals(Response.SC_BAD_REQUEST,response1.getStatusCodeValue());
		assertEquals(Response.SC_BAD_REQUEST,response2.getStatusCodeValue());
		assertEquals(Response.SC_BAD_REQUEST,response3.getStatusCodeValue());
		assertEquals(Response.SC_BAD_REQUEST,response4.getStatusCodeValue());
		assertEquals(Response.SC_BAD_REQUEST,response5.getStatusCodeValue());
	}
	

}
