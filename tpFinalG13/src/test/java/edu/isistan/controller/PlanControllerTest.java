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
import edu.isistan.model.Plan;
import edu.isistan.model.Viaje;
import edu.isistan.repository.PlanRepository;
import edu.isistan.repository.ViajeRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PlanControllerTest {
	/*@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private PlanRepository repository;
	
	@Autowired
	private ViajeRepository repositoryViaje;
	
	
	@Test
	@Sql({"/viajes.sql","/planes.sql"})
	@Order(1)
	public void getPlanTest() {
		int id = 99;
		ResponseEntity<Plan> response = testRestTemplate.getForEntity("/viajes/planes/" + id, Plan.class);
		Plan plan = response.getBody();
		assertEquals(Response.SC_OK,response.getStatusCodeValue());
		assertEquals("nombre plan1", plan.getNombre());
		assertEquals(id, plan.getId());
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void getPlanesTest() {
		ResponseEntity<List> response = testRestTemplate.getForEntity("/viajes/planes", List.class);
		List<HashMap<String, Object>> planes = response.getBody();
		
		assertEquals(Response.SC_OK,response.getStatusCodeValue());
		assertEquals(3,planes.size());
		assertEquals(99,planes.get(0).get("id"));
		assertEquals("nombre plan1",planes.get(0).get("nombre"));
		assertEquals(100,planes.get(1).get("id"));
		assertEquals("nombre plan2",planes.get(1).get("nombre"));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void getPlanesDeViajeTest() {
		int idViaje = 99;
		ResponseEntity<List> response = testRestTemplate.getForEntity("/viajes/"+idViaje+"/planes", List.class);
		List<HashMap<String, Object>> planes = response.getBody();
		
		
		assertEquals(Response.SC_OK,response.getStatusCodeValue());
		assertEquals(2,planes.size());
		assertEquals(99,planes.get(0).get("id"));
		assertEquals("nombre plan1",planes.get(0).get("nombre"));
		assertEquals(100,planes.get(1).get("id"));
		assertEquals("nombre plan2",planes.get(1).get("nombre"));
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void getPlanesDeViajeNoEncontradoTest() {
		int idViaje = 9000;
		ResponseEntity<List> response = testRestTemplate.getForEntity("/viajes/"+idViaje+"/planes", List.class);
		assertEquals(Response.SC_NOT_FOUND, response.getStatusCodeValue());
	}
	
	@Test
	public void crearPlanTest() {
		int idViaje = 100;
		String expectedNombre = "crear plan test";
		Plan plan = new Plan(expectedNombre);
		HttpEntity<Plan> request = new HttpEntity<>(plan);
		ResponseEntity<Plan> response = testRestTemplate.postForEntity("/viajes/"+idViaje+"/planes", request, Plan.class);
		Plan planGuardado = response.getBody();
		assertEquals(Response.SC_CREATED,response.getStatusCodeValue());
		assertEquals(expectedNombre,planGuardado.getNombre());
		assertNotNull(planGuardado.getId());
		repository.deleteById(planGuardado.getId());
	}
	
	@Test
	public void crearPlanEnViajeQueNoExisteTest() {
		int idViaje = 9000;
		HttpEntity<Plan> request = new HttpEntity<>(new Plan("test"));
		ResponseEntity<Plan> response = testRestTemplate.postForEntity("/viajes/"+idViaje+"/planes", request, Plan.class);
		assertEquals(Response.SC_NOT_FOUND,response.getStatusCodeValue());
	}
	
	@Test
	public void getPlanNoEncontradoTest() {
		int id = 8080;
		ResponseEntity<Plan> response = testRestTemplate.getForEntity("/viajes/planes/" + id, Plan.class);
		assertEquals(Response.SC_NOT_FOUND,response.getStatusCodeValue());
	}
	
	@Test
	public void borrarPlanTest() {
		Viaje viaje = new Viaje("viaje test");
		viaje = repositoryViaje.save(viaje);
		Plan plan = new Plan("plan",viaje);
		final Plan planRepo = repository.save(plan);
		HttpEntity<String> request = new HttpEntity<>("");
		ResponseEntity<String> response = testRestTemplate.exchange("/viajes/planes/" + planRepo.getId(),HttpMethod.DELETE,request, String.class);
		assertEquals(Response.SC_NO_CONTENT,response.getStatusCodeValue());
		Assertions.assertThrows(NoSuchElementException.class, () -> {
			repository.findById(planRepo.getId()).get();
		});
		repositoryViaje.delete(viaje);
	}
	
	@Test
	public void borrarPlanQueNoExisteTest() {
		int id = 9000;
		HttpEntity<String> request = new HttpEntity<>("");
		ResponseEntity<String> response = testRestTemplate.exchange("/viajes/planes/" + id,HttpMethod.DELETE,request, String.class);
		assertEquals(Response.SC_NOT_FOUND,response.getStatusCodeValue());
	}
	
	@Test
	public void modificarPlanTest() {
		Viaje viaje = new Viaje("viaje test");
		viaje = repositoryViaje.save(viaje);
		Plan planOriginal = new Plan("plan original",viaje);
		planOriginal = repository.save(planOriginal);
		
		Plan planEditado = new Plan("plan editado");
		HttpEntity<Plan> request = new HttpEntity<>(planEditado);
		ResponseEntity<Plan> response = testRestTemplate.exchange("/viajes/planes/" + planOriginal.getId(),HttpMethod.PUT,request, Plan.class);
		Plan planResponse = response.getBody();
		Plan planRepo = repository.findById(planOriginal.getId()).get();
		
		assertEquals(Response.SC_OK,response.getStatusCodeValue());
		assertEquals(planRepo.getNombre(),planEditado.getNombre());
		assertEquals(planRepo.getNombre(),planResponse.getNombre());
		repository.deleteById(planOriginal.getId());
		repositoryViaje.delete(viaje);
	}
	
	@Test
	public void modificarPlanQueNoExisteTest() {
		int id = 9000;
		
		HttpEntity<Plan> request = new HttpEntity<>(new Plan("test"));
		ResponseEntity<Plan> response = testRestTemplate.exchange("/viajes/planes/" + id,HttpMethod.PUT,request, Plan.class);
		assertEquals(Response.SC_NOT_FOUND,response.getStatusCodeValue());
	}
	*/
}
