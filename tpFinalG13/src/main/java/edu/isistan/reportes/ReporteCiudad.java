package edu.isistan.reportes;

public class ReporteCiudad {
	private String ciudad;
	private long cantidadViajes;
	
	public ReporteCiudad(String ciudad, long cantidadViajes) {
		this.ciudad = ciudad;
		this.cantidadViajes = cantidadViajes;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public long getCantidadViajes() {
		return cantidadViajes;
	}

	public void setCantidadViajes(long cantidadViajes) {
		this.cantidadViajes = cantidadViajes;
	}
	
	
}
