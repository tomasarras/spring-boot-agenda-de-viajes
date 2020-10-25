package edu.isistan.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Viaje {
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_viaje", nullable = false)
	private Integer id;
	@Column(name = "nombre_viaje")
	private String nombre;
	
	public Viaje() {
		super();
	}
	
	public Viaje(String nombre) {
		super();
		this.nombre = nombre;
	}

	public Viaje(Integer id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
