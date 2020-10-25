package edu.isistan.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.HashMap;
import java.util.List;
import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import edu.isistan.model.Plan;
import edu.isistan.repository.PlanRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class PlanControllerTest {
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private PlanRepository repository;
	
	
	@Test
	@Sql("/test.sql")
	public void getPlanTest() {
		int id = 99;
		ResponseEntity<Plan> response = testRestTemplate.getForEntity("/planes/" + id, Plan.class);
		Plan plan = response.getBody();
		assertEquals("nombre plan1", plan.getNombre());
		assertEquals(id, plan.getId());
		assertEquals(Response.SC_OK,response.getStatusCodeValue());
	}
	
	@Test
	public void getPlanesTest() {
		ResponseEntity<List> response = testRestTemplate.getForEntity("/planes", List.class);
		List<HashMap<String, Object>> planes = response.getBody();
		
		assertEquals(2,planes.size());
		assertEquals(99,planes.get(0).get("id"));
		assertEquals("nombre plan1",planes.get(0).get("nombre"));
		assertEquals(100,planes.get(1).get("id"));
		assertEquals("nombre plan2",planes.get(1).get("nombre"));
		assertEquals(Response.SC_OK,response.getStatusCodeValue());
	}
	
	@Test
	public void crearPlanTest() {
		String expectedNombre = "crear plan test";
		Plan plan = new Plan(expectedNombre);
		HttpEntity<Plan> request = new HttpEntity<>(plan);
		ResponseEntity<Plan> response = testRestTemplate.postForEntity("/planes", request, Plan.class);
		Plan planGuardado = response.getBody();
		assertEquals(expectedNombre,planGuardado.getNombre());
		assertNotNull(planGuardado.getId());
		assertEquals(Response.SC_CREATED,response.getStatusCodeValue());
	}
	
	@Test
	public void planNoEncontradoTest() {
		int id = 8080;
		ResponseEntity<Plan> response = testRestTemplate.getForEntity("/planes/" + id, Plan.class);
		assertEquals(Response.SC_NOT_FOUND,response.getStatusCodeValue());
	}
	
}
