package edu.isistan.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
public class Viaje {
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(notes = "Identificador del viaje",name = "id",required = false,value  = "5")
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@Column(name = "id_viaje", nullable = false)
	private Integer id;
	@Column(name = "nombre_viaje")
	@ApiModelProperty(notes = "Nombre del viaje",name = "nombre",required = true,value  = "nombre_viaje")
	private String nombre;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "id_usuario",nullable = false)
	private Usuario usuario;
	@JsonIgnore
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "viaje")
	private List<Plan> planes;
	@Column(name = "ciudad_destino")
	private String ciudadDestino;
	@Column(name = "fecha_inicio")
	private LocalDate fechaInicio;
	@Column(name = "fecha_fin")
	private LocalDate fechaFin;
	@Column
	private String descripcion;
	
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getCiudadDestino() {
		return ciudadDestino;
	}

	public void setCiudadDestino(String ciudadDestino) {
		this.ciudadDestino = ciudadDestino;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
}
