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
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import edu.isistan.model.planes.PlanReservaHotel;
import edu.isistan.model.planes.PlanViajeColectivo;
import edu.isistan.model.planes.PlanViajeTren;
import edu.isistan.model.planes.PlanVuelo;


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
	/*@ApiModelProperty(notes = "Identificador del plan",name = "id",required = false,value = "10")*/
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@Column(name = "id_plan", nullable = false)
	private Integer id;
	@ApiModelProperty(notes = "Nombre del plan",name = "nombre",required = true,value = "nombre_plan")
	@Column(name = "nombre_plan", nullable = false)
	private String nombre;
	@Column(nullable = false)
	private String compania;
	@Column(name = "fecha_inicio", nullable = false)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
	private LocalDateTime fechaInicio;
	@Column(name = "fecha_fin", nullable = false)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
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
