package edu.isistan.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import edu.isistan.model.Viaje;
import edu.isistan.repository.ViajeRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ViajeControllerTest {
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private ViajeRepository repository;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	@Sql("/viajes.sql")
	@Order(1)
	public void getViajesTest() {
		int cantidadViajes = 2;
		int idViaje1 = 99;
		int idViaje2 = 100;
		
		ResponseEntity<List> response = testRestTemplate.getForEntity("/viajes", List.class);
		List<HashMap<String, Object>> viajes = response.getBody();
		
		assertEquals(Response.SC_OK,response.getStatusCodeValue());
		assertEquals(cantidadViajes, viajes.size());
		assertEquals(idViaje1,viajes.get(0).get("id"));
		assertEquals("nombre viaje1",viajes.get(0).get("nombre"));
		assertEquals(idViaje2,viajes.get(1).get("id"));
		assertEquals("nombre viaje2",viajes.get(1).get("nombre"));
	}
	
	@Test
	public void getViajeTest() {
		int id = 99;
		ResponseEntity<Viaje> response = testRestTemplate.getForEntity("/viajes/" + id, Viaje.class);
		Viaje viaje = response.getBody();
		
		assertEquals(Response.SC_OK,response.getStatusCodeValue());
		assertEquals(id, viaje.getId());
		assertEquals("nombre viaje1", viaje.getNombre());
	}
	
	@Test
	public void getViajeNoEncontradoTest() {
		int id = 8080;
		ResponseEntity<Viaje> response = testRestTemplate.getForEntity("/viajes/" + id, Viaje.class);
		assertEquals(Response.SC_NOT_FOUND,response.getStatusCodeValue());
	}
	
	@Test
	public void crearViajeTest() {
		String expectedNombre = "crear viaje test";
		Viaje viaje = new Viaje(expectedNombre);
		HttpEntity<Viaje> request = new HttpEntity<>(viaje);
		ResponseEntity<Viaje> response = testRestTemplate.postForEntity("/viajes", request, Viaje.class);
		Viaje viajeGuardado = response.getBody();
		assertEquals(Response.SC_CREATED,response.getStatusCodeValue());
		assertEquals(expectedNombre,viajeGuardado.getNombre());
		assertNotNull(viajeGuardado.getId());
		repository.deleteById(viajeGuardado.getId());
	}
	
	@Test
	public void borrarViajeTest() {
		final Viaje viaje = repository.save(new Viaje("viaje test"));
		HttpEntity<String> request = new HttpEntity<>("");
		ResponseEntity<String> response = testRestTemplate.exchange("/viajes/" + viaje.getId(),HttpMethod.DELETE,request, String.class);
		assertEquals(Response.SC_NO_CONTENT,response.getStatusCodeValue());
		Assertions.assertThrows(NoSuchElementException.class, () -> {
			repository.findById(viaje.getId()).get();
		});
	}
	
	@Test
	public void borrarViajeQueNoExisteTest() {
		int id = 9000;
		HttpEntity<String> request = new HttpEntity<>("");
		ResponseEntity<String> response = testRestTemplate.exchange("/viajes/" + id,HttpMethod.DELETE,request, String.class);
		assertEquals(Response.SC_NOT_FOUND,response.getStatusCodeValue());
	}
	
	@Test
	public void modificarViajeTest() {
		Viaje viajeOriginal = repository.save(new Viaje("viaje test"));
		Viaje viajeEditado = new Viaje("viaje editado");
		
		HttpEntity<Viaje> request = new HttpEntity<>(viajeEditado);
		ResponseEntity<Viaje> response = testRestTemplate.exchange("/viajes/" + viajeOriginal.getId(),HttpMethod.PUT,request, Viaje.class);
		Viaje viajeResponse = response.getBody();
		Viaje viajeRepo = repository.findById(viajeOriginal.getId()).get();
		
		assertEquals(Response.SC_OK,response.getStatusCodeValue());
		assertEquals(viajeRepo.getNombre(),viajeEditado.getNombre());
		assertEquals(viajeRepo.getNombre(),viajeResponse.getNombre());
		repository.deleteById(viajeOriginal.getId());
	}
	
	@Test
	public void modificarViajeQueNoExisteTest() {
		int id = 9000;
		
		HttpEntity<Viaje> request = new HttpEntity<>(new Viaje("viaje editado"));
		ResponseEntity<Viaje> response = testRestTemplate.exchange("/viajes/" + id,HttpMethod.PUT,request, Viaje.class);
		assertEquals(Response.SC_NOT_FOUND,response.getStatusCodeValue());
	}
}
