package edu.isistan.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.springframework.util.StringUtils;
import javax.persistence.InheritanceType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import edu.isistan.model.planes.PlanReservaHotel;
import edu.isistan.model.planes.PlanViajeColectivo;
import edu.isistan.model.planes.PlanViajeTren;
import edu.isistan.model.planes.PlanVuelo;

/**
 * Plan comun del cual heredan otros tipos de planes que estan en el 
 * package edu.isiatan.model.planes
 * @author Tomas Arras
 *
 */

@ApiModel(value="Plan", description="Plan comun que pertenece a un viaje",subTypes = {
		PlanReservaHotel.class,
		PlanViajeColectivo.class,
		PlanViajeTren.class,
		PlanVuelo.class
})
@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
	@JsonSubTypes.Type(value = PlanReservaHotel.class, name = "reservaHotel"),
	@JsonSubTypes.Type(value = PlanViajeColectivo.class, name = "viajeColectivo"),
	@JsonSubTypes.Type(value = PlanViajeTren.class, name = "viajeTren"),
	@JsonSubTypes.Type(value = PlanVuelo.class, name = "vuelo")
})
public class Plan {
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@Column(name = "id_plan", nullable = false)
	private Integer id;
	@Column(name = "nombre_plan", nullable = false)
	@ApiModelProperty(notes = "Nombre del plan",name = "nombre",required = true)
	private String nombre;
	@Column(nullable = false)
	@ApiModelProperty(notes = "Compañia del plan",name = "compania",required = true)
	private String compania;
	@Column(name = "fecha_inicio", nullable = false)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
	@ApiModelProperty(notes = "Fecha de inicio del plan, debe estar en el rango de la fecha del viaje que lo contiene",name = "fechaInicio",required = true,value = "yyyy-MM-dd HH:mm")
	private LocalDateTime fechaInicio;
	@Column(name = "fecha_fin", nullable = false)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
	@ApiModelProperty(notes = "Fecha de fin del plan, debe estar en el rango de la fecha del viaje que lo contiene",name = "fechaFin",required = true,value = "yyyy-MM-dd HH:mm")
	private LocalDateTime fechaFin;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "id_viaje",nullable = false)
	private Viaje viaje;
	
	public boolean esValido() {
		if (fechaInicio != null && fechaFin != null) {
			if (fechaFin.compareTo(fechaInicio) <= 0) {
				return false;
			}
		}
		
		return !(id == null ||
				StringUtils.isEmpty(nombre) || 
				StringUtils.isEmpty(compania));
	}
	
	public void modificarse(Plan plan) {
		this.nombre = plan.getNombre();
		this.compania = plan.getCompania();
		this.fechaInicio = plan.getFechaInicio();
		this.fechaFin = plan.getFechaFin();
	}

}
