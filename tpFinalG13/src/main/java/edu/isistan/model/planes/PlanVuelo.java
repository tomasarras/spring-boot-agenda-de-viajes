package edu.isistan.model.planes;

import javax.persistence.Column;
import javax.persistence.Entity;
import org.springframework.util.StringUtils;
import edu.isistan.model.Plan;

@Entity
public class PlanVuelo extends Plan {
	@Column(name = "numero_vuelo")
	private Integer numeroVuelo;
	@Column(name = "aeropuerto_salida")
	private String aeropuertoSalida;
	@Column(name = "aeropuerto_llegada")
	private String aeropuertoLlegada;
	@Column(name = "codigo_reserva")
	private int codigoReserva;
	@Column(name = "tiempo_escalas")
	private Long tiempoEscalas;
	@Column
	private String aeronave;
	
	@Override
	public boolean esValido() {
		return !(!super.esValido() ||
			numeroVuelo == null ||
			tiempoEscalas == null ||
			StringUtils.isEmpty(aeropuertoSalida) ||
			StringUtils.isEmpty(aeropuertoLlegada) ||
			StringUtils.isEmpty(aeronave));
		
	}
	
	public PlanVuelo() {}
	
	public int getNumeroVuelo() {
		return numeroVuelo;
	}
	public void setNumeroVuelo(int numeroVuelo) {
		this.numeroVuelo = numeroVuelo;
	}
	public String getAeropuertoSalida() {
		return aeropuertoSalida;
	}
	public void setAeropuertoSalida(String aeropuertoSalida) {
		this.aeropuertoSalida = aeropuertoSalida;
	}
	public String getAeropuertoLlegada() {
		return aeropuertoLlegada;
	}
	public void setAeropuertoLlegada(String aeropuertoLlegada) {
		this.aeropuertoLlegada = aeropuertoLlegada;
	}
	public int getCodigoReserva() {
		return codigoReserva;
	}
	public void setCodigoReserva(int codigoReserva) {
		this.codigoReserva = codigoReserva;
	}
	public Long getTiempoEscalas() {
		return tiempoEscalas;
	}
	public void setTiempoEscalas(Long tiempoEscalas) {
		this.tiempoEscalas = tiempoEscalas;
	}
	public String getAeronave() {
		return aeronave;
	}
	public void setAeronave(String aeronave) {
		this.aeronave = aeronave;
	}
	
	@Override
	public void modificarse(Plan plan) {
		PlanVuelo p = (PlanVuelo) plan;
		super.modificarse(plan);
		this.numeroVuelo = p.getNumeroVuelo();
		this.aeropuertoSalida = p.getAeropuertoSalida();
		this.aeropuertoLlegada = p.getAeropuertoLlegada();
		this.codigoReserva = p.getCodigoReserva();
		this.tiempoEscalas = p.getTiempoEscalas();
		this.aeronave = p.getAeronave();
	}
	
}
