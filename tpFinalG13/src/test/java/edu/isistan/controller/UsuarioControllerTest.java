package edu.isistan.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import edu.isistan.model.Usuario;
import edu.isistan.repository.UsuarioRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioControllerTest {
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private UsuarioRepository repository;
	
	@Test
	@Order(1)
	public void crearUsuarioTest() {
		String usuarioPost = "{"
				+ "\"username\" : \"user\","
				+ "\"password\" : \"password\","
				+ "\"email\" : \"user@gmail.com\""
				+ "}";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> request = new HttpEntity<String>(usuarioPost ,headers);
		ResponseEntity<Usuario> response = testRestTemplate.postForEntity("/usuarios/registrar", request, Usuario.class);
		
		Usuario usuarioResponse = response.getBody();
		
		Usuario uRepository = repository.findById(usuarioResponse.getId()).get();
		
		assertEquals(Response.SC_CREATED,response.getStatusCodeValue());
		assertEquals("user@gmail.com",usuarioResponse.getEmail());
		assertEquals("user@gmail.com",uRepository.getEmail());
		assertEquals("user",usuarioResponse.getUsername());
		assertEquals("user",uRepository.getUsername());
		assertNotNull(usuarioResponse.getToken());
		assertNull(usuarioResponse.getPassword());
	}
	
	@Test
	@Order(2)
	public void crearUsuarioConMismoEmailTest() {
		String usuarioPost = "{"
				+ "\"username\" : \"user2\","
				+ "\"password\" : \"password\","
				+ "\"email\" : \"user@gmail.com\""
				+ "}";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> request = new HttpEntity<String>(usuarioPost ,headers);
		ResponseEntity<String> response = testRestTemplate.postForEntity("/usuarios/registrar", request, String.class);
		
		assertEquals(Response.SC_CONFLICT,response.getStatusCodeValue());
	}
	
	@Test
	@Order(3)
	public void crearUsuarioConMismoUsernameTest() {
		String usuarioPost = "{"
				+ "\"username\" : \"user\","
				+ "\"password\" : \"password\","
				+ "\"email\" : \"user2@gmail.com\""
				+ "}";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> request = new HttpEntity<String>(usuarioPost ,headers);
		ResponseEntity<String> response = testRestTemplate.postForEntity("/usuarios/registrar", request, String.class);
		
		assertEquals(Response.SC_CONFLICT,response.getStatusCodeValue());
	}
	
	@Test
	@Order(4)
	public void crearUsuarioSinValoresTest() {
		String usuarioSinUsername = "{"
				+ "\"username\" : \"\","
				+ "\"password\" : \"password\","
				+ "\"email\" : \"user2@gmail.com\""
				+ "}";
		String usuarioSinEmail = "{"
				+ "\"username\" : \"user2\","
				+ "\"password\" : \"password\","
				+ "\"email\" : \"\""
				+ "}";
		
		String usuarioSinPassword = "{"
				+ "\"username\" : \"user2\","
				+ "\"password\" : \"\","
				+ "\"email\" : \"user2@gmail.com\""
				+ "}";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> request1 = new HttpEntity<String>(usuarioSinEmail ,headers);
		ResponseEntity<String> response1 = testRestTemplate.postForEntity("/usuarios/registrar", request1, String.class);
		
		HttpEntity<String> request2 = new HttpEntity<String>(usuarioSinUsername ,headers);
		ResponseEntity<String> response2 = testRestTemplate.postForEntity("/usuarios/registrar", request2, String.class);
		
		HttpEntity<String> request3 = new HttpEntity<String>(usuarioSinPassword ,headers);
		ResponseEntity<String> response3 = testRestTemplate.postForEntity("/usuarios/registrar", request3, String.class);
		
		assertEquals(Response.SC_BAD_REQUEST,response1.getStatusCodeValue());
		assertEquals(Response.SC_BAD_REQUEST,response2.getStatusCodeValue());
		assertEquals(Response.SC_BAD_REQUEST,response3.getStatusCodeValue());
	}
	
	@Test
	@Order(5)
	public void loginTest() {
		String usuarioPost = "{"
				+ "\"username\" : \"user\","
				+ "\"password\" : \"password\""
				+ "}";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> request = new HttpEntity<String>(usuarioPost ,headers);
		ResponseEntity<Usuario> response = testRestTemplate.postForEntity("/usuarios/login", request, Usuario.class);
		
		Usuario usuarioResponse = response.getBody();
		
		assertEquals(Response.SC_OK,response.getStatusCodeValue());
		assertEquals("user@gmail.com",usuarioResponse.getEmail());
		assertEquals("user",usuarioResponse.getUsername());
		assertNotNull(usuarioResponse.getToken());
		assertNull(usuarioResponse.getPassword());
	}
	
	@Test
	@Order(6)
	public void loginUsuarioIncorrectoTest() {
		String usuarioPost = "{"
				+ "\"username\" : \"userIncorrecto\","
				+ "\"password\" : \"password\""
				+ "}";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> request = new HttpEntity<String>(usuarioPost ,headers);
		ResponseEntity<String> response = testRestTemplate.postForEntity("/usuarios/login", request, String.class);
		
		assertEquals(Response.SC_NOT_FOUND,response.getStatusCodeValue());
	}
	
	@Test
	@Order(7)
	public void loginPasswordIncorrectoTest() {
		String usuarioPost = "{"
				+ "\"username\" : \"user\","
				+ "\"password\" : \"password2\""
				+ "}";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> request = new HttpEntity<String>(usuarioPost ,headers);
		ResponseEntity<String> response = testRestTemplate.postForEntity("/usuarios/login", request, String.class);
		assertEquals(Response.SC_UNAUTHORIZED,response.getStatusCodeValue());
	}
	
	@Test
	@Order(8)
	public void loginSinValores() {
		String usuarioSinPassword = "{"
				+ "\"username\" : \"user\","
				+ "\"password\" : \"\""
				+ "}";
		
		String usuarioSinUsername = "{"
				+ "\"username\" : \"\","
				+ "\"password\" : \"password\""
				+ "}";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> request1 = new HttpEntity<String>(usuarioSinPassword ,headers);
		ResponseEntity<String> response1 = testRestTemplate.postForEntity("/usuarios/login", request1, String.class);
		
		HttpEntity<String> request2 = new HttpEntity<String>(usuarioSinUsername ,headers);
		ResponseEntity<String> response2 = testRestTemplate.postForEntity("/usuarios/login", request2, String.class);
		
		assertEquals(Response.SC_BAD_REQUEST,response1.getStatusCodeValue());
		assertEquals(Response.SC_BAD_REQUEST,response2.getStatusCodeValue());
	}
	
	
	
}
