package edu.isistan.model;

public class FacturaCliente {
	float montoTotal;
	Cliente cliente;
	
	public FacturaCliente(Cliente c, float montoTotal) {
		this.montoTotal = montoTotal;
		this.cliente = c;
	}

	public FacturaCliente() {
		super();
	}

	public float getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(float montoTotal) {
		this.montoTotal = montoTotal;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}
