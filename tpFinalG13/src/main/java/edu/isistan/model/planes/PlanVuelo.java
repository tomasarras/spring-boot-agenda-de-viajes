package edu.isistan.model.planes;

import javax.persistence.Column;
import javax.persistence.Entity;
import org.springframework.util.StringUtils;
import edu.isistan.model.Plan;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
public class PlanVuelo extends Plan {
	@Column(name = "numero_vuelo", nullable = false)
	private Integer numeroVuelo;
	@Column(name = "aeropuerto_salida", nullable = false)
	private String aeropuertoSalida;
	@Column(name = "aeropuerto_llegada", nullable = false)
	private String aeropuertoLlegada;
	@Column(name = "codigo_reserva", nullable = false)
	private int codigoReserva;
	@Column(name = "tiempo_escalas")
	private Long tiempoEscalas;
	@Column(nullable = false)
	private String aeronave;
	
	@Override
	public boolean esValido() {
		return !(!super.esValido() ||
			numeroVuelo == null ||
			StringUtils.isEmpty(aeropuertoSalida) ||
			StringUtils.isEmpty(aeropuertoLlegada) ||
			StringUtils.isEmpty(aeronave));
		
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
