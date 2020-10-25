package edu.isistan.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Plan {
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_plan", nullable = false)
	private Integer id;
	@Column(name = "nombre_plan")
	private String nombre;
	@ManyToOne
	@JoinColumn(name = "id_viaje")
	private Viaje viaje;
	
	public Plan() {
		super();
	}
	
	public Plan(String nombre) {
		super();
		this.nombre = nombre;
	}
	
	public Plan(Integer id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}
	
	public Plan(String nombre,Viaje viaje) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.viaje = viaje;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Plan [id=" + id + ", nombre=" + nombre + "]";
	}
	
	
}
