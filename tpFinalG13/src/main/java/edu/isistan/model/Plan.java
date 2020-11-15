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
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import edu.isistan.model.planes.PlanReservaHotel;
import edu.isistan.model.planes.PlanViajeColectivo;
import edu.isistan.model.planes.PlanViajeTren;
import edu.isistan.model.planes.PlanVuelo;


@Entity
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
	@Column(name = "nombre_plan")
	private String nombre;
	@Column
	private String compania;
	@Column(name = "fecha_inicio")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
	private LocalDateTime fechaInicio;
	@Column(name = "fecha_fin")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
	private LocalDateTime fechaFin;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "id_viaje",nullable = false)
	private Viaje viaje;
	
	public boolean esValido() {
		return !(id == null ||
				StringUtils.isEmpty(nombre) || 
				StringUtils.isEmpty(compania) ||
				fechaInicio == null ||
				fechaFin == null);
	}
	
	public void modificarse(Plan plan) {
		this.nombre = plan.getNombre();
		this.compania = plan.getCompania();
		this.fechaInicio = plan.getFechaInicio();
		this.fechaFin = plan.getFechaFin();
	}
	
	
	public Plan() {
		super();
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Viaje getViaje() {
		return viaje;
	}

	public void setViaje(Viaje viaje) {
		this.viaje = viaje;
	}


	public String getCompania() {
		return compania;
	}


	public void setCompania(String compania) {
		this.compania = compania;
	}


	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}


	public void setFechaInicio(LocalDateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}


	public LocalDateTime getFechaFin() {
		return fechaFin;
	}


	public void setFechaFin(LocalDateTime fechaFin) {
		this.fechaFin = fechaFin;
	}


	
	
	
}
