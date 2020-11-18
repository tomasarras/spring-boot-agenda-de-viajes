package edu.isistan.model.planes;

import javax.persistence.Column;
import javax.persistence.Entity;
import org.springframework.util.StringUtils;
import edu.isistan.model.Plan;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(value="PlanVuelo", description="Plan que hereda de plan comun")
public class PlanVuelo extends Plan {
	@Column(name = "numero_vuelo", nullable = false)
	@ApiModelProperty(required = true)
	private Integer numeroVuelo;
	@Column(name = "aeropuerto_salida", nullable = false)
	@ApiModelProperty(required = true)
	private String aeropuertoSalida;
	@Column(name = "aeropuerto_llegada", nullable = false)
	@ApiModelProperty(required = true)
	private String aeropuertoLlegada;
	@Column(name = "codigo_reserva", nullable = false)
	@ApiModelProperty(required = true)
	private int codigoReserva;
	@Column(name = "tiempo_escalas")
	private Long tiempoEscalas;
	@Column(nullable = false)
	@ApiModelProperty(required = true)
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
