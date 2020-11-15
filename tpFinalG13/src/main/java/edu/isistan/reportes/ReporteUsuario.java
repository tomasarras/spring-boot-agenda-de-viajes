package edu.isistan.reportes;

import javax.persistence.MappedSuperclass;

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
