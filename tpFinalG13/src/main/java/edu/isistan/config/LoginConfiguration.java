package edu.isistan.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import edu.isistan.filter.JWTAuthorizationFilter;
/**
 * Configuracion para las rutas o paths que esten permitidos
 * @author Tomas Arras
 *
 */
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
			.antMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
			.antMatchers(HttpMethod.GET,"/v3/api-docs").permitAll()
			.antMatchers(HttpMethod.GET,"/swagger-ui.html").permitAll()
			.antMatchers(HttpMethod.GET,"/index.html").permitAll()
			.antMatchers(HttpMethod.GET,"/html/**").permitAll()
			.antMatchers(HttpMethod.GET,"/css/**").permitAll()
			.antMatchers(HttpMethod.GET,"/js/**").permitAll()
			.antMatchers(HttpMethod.GET,"/img/**").permitAll()
			.anyRequest().authenticated();
	}
	
	@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/", "/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
                "/configuration/**", "/swagger-ui.html", "/webjars/**");
    }
}
