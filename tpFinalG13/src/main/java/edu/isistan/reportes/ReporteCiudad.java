package edu.isistan.reportes;

import io.swagger.annotations.ApiModel;

/**
 * reporte que se utiliza para obtener las ciudades mas visitadas en el controller viajes
 * @author Tomas Arras
 *
 */
@ApiModel(value="ReporteCiudad", description="Es el reporte que muestra la"
		+ " cantidad de viajes que tiene una ciudad")
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
