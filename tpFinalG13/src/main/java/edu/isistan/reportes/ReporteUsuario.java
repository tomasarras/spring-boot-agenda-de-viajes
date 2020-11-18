package edu.isistan.reportes;

import javax.persistence.MappedSuperclass;

import io.swagger.annotations.ApiModel;
/**
 * Reporte de los usuarios que mas viajes tienen en total, utilizado por 
 * UsuarioController
 * @author Tomas
 *
 */
@ApiModel(value="ReporteUsuario", description="Es el reporte que muestra la cantidad de viajes"
		+ " que tiene un usuario")
@MappedSuperclass
public class ReporteUsuario {
	private String username;
	private String email;
	private long cantidadViajes;
	public ReporteUsuario() {
		super();
	}
	public long getCantidad_viajes() {
		return cantidadViajes;
	}
	public void setCantidad_viajes(long cantidadViajes) {
		this.cantidadViajes = cantidadViajes;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public ReporteUsuario(String email, String username, long cantidadViajes) {
		super();
		this.username = username;
		this.email = email;
		this.cantidadViajes = cantidadViajes;
	}
	
	public String getEmail( ) {
		return this.email;
	}
	
	

}
