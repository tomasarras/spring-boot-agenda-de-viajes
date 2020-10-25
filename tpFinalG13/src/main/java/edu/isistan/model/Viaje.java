package edu.isistan.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Viaje {
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_viaje", nullable = false)
	private Integer id;
	@Column(name = "nombre_viaje")
	private String nombre;
	@JsonIgnore
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "viaje")
	private List<Plan> planes;
	
	public Viaje() {
		super();
	}
	
	public Viaje(String nombre) {
		super();
		this.nombre = nombre;
		this.planes = new ArrayList<Plan>();
	}

	public Viaje(Integer id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.planes = new ArrayList<Plan>();
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

	public List<Plan> getPlanes() {
		return planes;
	}

	public void setPlanes(List<Plan> planes) {
		this.planes = planes;
	}
	
	public void addPlan(Plan plan) {
		this.planes.add(plan);
	}
}
