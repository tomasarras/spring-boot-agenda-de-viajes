package edu.isistan.controller;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.google.common.hash.Hashing;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import edu.isistan.model.Usuario;
import edu.isistan.repository.UsuarioRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UsuarioRepository repository;
	
	@PostMapping("/registrar")
	public ResponseEntity<Object> crearUsuario(@RequestParam String email, @RequestParam String password) {
		if (!checkUsuarioValido(password,email)) {
			return ResponseEntity
			.status(Response.SC_BAD_REQUEST)
			.build();
		}
		
		if (repository.buscarPorEmail(email) != null) {
			return ResponseEntity
					.status(Response.SC_CONFLICT)
					.body("el email ya esta registrado");
		}
		
		String hash = bCryptPasswordEncoder.encode(password);
		
		Usuario usuario = new Usuario();
		usuario.setEmail(email);
		usuario.setPassword(hash);
		repository.save(usuario);
		
		Map<String,Object> data = Map.of(
			    "id", usuario.getId(),
			    "email", email
		);
		
		String token = getJWTToken(data);
		usuario.setToken(token);
		
		return ResponseEntity
				.status(Response.SC_OK)
				.body(usuario);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestParam String email, @RequestParam String password) {
		if (StringUtils.isEmpty(email)) {
			return ResponseEntity
			.status(Response.SC_BAD_REQUEST)
			.build();
		}
		
		Usuario usuario = repository.buscarPorEmail(email);
		if (usuario == null) {
			return ResponseEntity
					.status(Response.SC_NOT_FOUND)
					.body("el email no existe");
		}
		
		if (bCryptPasswordEncoder.matches(password, usuario.getPassword())) {
			Map<String,Object> data = Map.of(
				    "id", usuario.getId(),
				    "email", email
			);
			
			usuario.setToken(getJWTToken(data));
			return ResponseEntity
					.status(Response.SC_OK)
					.body(usuario);
		} else {
			Usuario u = new Usuario();
			u.setEmail("no match");
			return ResponseEntity
					.status(Response.SC_UNAUTHORIZED)
					.body("la contraseña es incorrecta");
		}
		
		
	}
	
	private boolean checkUsuarioValido(String arg1,String arg2) {
		return !StringUtils.isEmpty(arg1)
				&& !StringUtils.isEmpty(arg2);
	}

	private String getJWTToken(Map <String,Object> data) {
		String secretKey = "2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824";

		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");
		
		String token = Jwts
				.builder()
				.setId("softtekJWT")
				.addClaims(data)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 6000000))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();

		return "Bearer " + token;
	}

}
