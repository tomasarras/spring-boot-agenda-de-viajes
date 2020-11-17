package edu.isistan.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import edu.isistan.config.TokenConfig;
import edu.isistan.model.Usuario;
import edu.isistan.reportes.ReporteUsuario;
import edu.isistan.repository.UsuarioRepository;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UsuarioRepository repository;
	
	@CrossOrigin
	@PostMapping("/registrar")
	public ResponseEntity<Object> crearUsuario(@RequestBody Usuario u) {
		if (!checkUsuarioValido(u)) {
			return ResponseEntity
			.status(Response.SC_BAD_REQUEST)
			.build();
		}
		
		if (repository.buscarPorEmail(u.getEmail()) != null) {
			return ResponseEntity
					.status(Response.SC_CONFLICT)
					.body("El email ya esta registrado");
		}
		
		if (repository.buscarPorUsername(u.getUsername()) != null) {
			return ResponseEntity
					.status(Response.SC_CONFLICT)
					.body("El username ya esta registrado");
		}
		
		String hash = bCryptPasswordEncoder.encode(u.getPassword());
		
		Usuario usuario = new Usuario();
		usuario.setUsername(u.getUsername());
		usuario.setEmail(u.getEmail());
		usuario.setPassword(hash);
		repository.save(usuario);
		
		String data = Integer.toString(usuario.getId());
		boolean admin = false;
		String token = getJWTToken(data,admin);
		usuario.setToken(token);
		
		return ResponseEntity
				.status(Response.SC_CREATED)
				.body(usuario);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody Usuario u) {
		if (StringUtils.isEmpty(u.getUsername()) || StringUtils.isEmpty(u.getPassword())) {
			return ResponseEntity
					.status(Response.SC_BAD_REQUEST)
					.build();
		}
		
		Usuario usuario = repository.buscarPorUsername(u.getUsername());		
		
		if (usuario == null) {
			return ResponseEntity
					.status(Response.SC_NOT_FOUND)
					.body("El username no existe");
		}
		
		if (bCryptPasswordEncoder.matches(u.getPassword(), usuario.getPassword())) {
			String data = Integer.toString(usuario.getId());
			
			usuario.setToken(getJWTToken(data,usuario.isAdmin()));
			return ResponseEntity
					.status(Response.SC_OK)
					.body(usuario);
		} else {
			return ResponseEntity
					.status(Response.SC_UNAUTHORIZED)
					.body("La contraseña es incorrecta");
		}
		
		
	}
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping
	public ResponseEntity<List<ReporteUsuario>> getUsuariosPorMasViajesRealizados() {
		return ResponseEntity
				.status(Response.SC_OK)
				.body(repository.getUsuariosConMasViajes());
	}

	
	private boolean checkUsuarioValido(Usuario usuario) {
		return !StringUtils.isEmpty(usuario.getEmail())
				&& !StringUtils.isEmpty(usuario.getPassword())
				&& !StringUtils.isEmpty(usuario.getUsername());
	}

	private String getJWTToken(String data,boolean admin) {
		String secretKey = TokenConfig.SECRET;
		String roles = "ROLE_USER";
		if (admin) {
			roles += ",ROLE_ADMIN";
		}
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList(roles);
		
		String token = Jwts
				.builder()
				.setId("softtekJWT")
				.setSubject(data)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TokenConfig.EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();

		return "Bearer " + token;
	}
}
