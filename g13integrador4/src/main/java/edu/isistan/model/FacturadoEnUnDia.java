package edu.isistan.model;

import java.sql.Date;

public class FacturadoEnUnDia {
	private Date fecha;
	private float monto;
	
	public FacturadoEnUnDia(Date fecha, float monto) {
		super();
		this.fecha = fecha;
		this.monto = monto;
	}
	
	public FacturadoEnUnDia() {
		super();
	}
	
	public Date getFecha() {
		return fecha;
	}
	
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public float getMonto() {
		return monto;
	}
	
	public void setMonto(float monto) {
		this.monto = monto;
	}
	
}
