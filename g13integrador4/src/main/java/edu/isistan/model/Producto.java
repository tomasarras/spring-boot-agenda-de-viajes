package edu.isistan.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Producto {
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_producto", nullable = false)
	private Integer id;
	@Column(name = "nombre_producto", nullable = false)
	private String nombre;
	@Column
	private float precio;
	@Column
	private int stock;
	
	public Producto() {
		super();
	}
	
	public Producto(String nombre, float precio,int stock) {
		super();
		this.nombre = nombre;
		this.precio = precio;
		this.stock = stock;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
}
