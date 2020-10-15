package edu.isistan.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Compra {
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_producto", nullable = false)
	private Integer id;
	@ManyToOne
	private Cliente cliente;
	@ManyToOne
	private Producto producto;
	@Column
	private Timestamp fecha;
	
	
	public Compra() {
		super();
	}


	public Compra(Cliente cliente, Producto producto,Timestamp fecha) {
		super();
		this.cliente = cliente;
		this.producto = producto;
		this.fecha = fecha;
	}


	public Cliente getCliente() {
		return cliente;
	}


	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}


	public Producto getProducto() {
		return producto;
	}


	public void setProducto(Producto producto) {
		this.producto = producto;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}
	
	public Timestamp getFecha() {
		return this.fecha;
	}
}
