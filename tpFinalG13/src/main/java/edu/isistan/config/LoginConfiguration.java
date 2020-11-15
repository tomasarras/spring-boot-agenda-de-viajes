package edu.isistan.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import edu.isistan.filter.JWTAuthorizationFilter;

@EnableWebSecurity
@Configuration
@Order(1)
@EnableGlobalMethodSecurity(prePostEnabled = true) //Necesario para que funcione la anotación en el servicio
public class LoginConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//Desactiva el método por defecto
		http.cors().and().csrf().disable()
		    //Agrega el método de filtrado que codificamos nosotros 
			.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
			.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/usuarios/login").permitAll()
			.antMatchers(HttpMethod.POST, "/usuarios/registrar").permitAll()
			//.antMatchers(HttpMethod.GET, "/oldman").hasAuthority("LINK") // Esta línea es otra manera de agregar requerimientos de logeo.
			.anyRequest().authenticated();
	}
}
