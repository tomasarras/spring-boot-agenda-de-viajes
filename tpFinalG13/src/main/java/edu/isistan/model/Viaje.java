package edu.isistan.model;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.springframework.util.StringUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Entity
@Data
public class Viaje {
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@Column(name = "id_viaje", nullable = false)
	private Integer id;
	@Column(name = "nombre_viaje", nullable = false)
	@ApiModelProperty(notes = "Nombre del viaje",name = "nombre",required = true,value  = "nombre_viaje")
	private String nombre;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "id_usuario",nullable = false)
	private Usuario usuario;
	@JsonIgnore
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "viaje")
	private List<Plan> planes;
	@Column(name = "ciudad_destino", nullable = false)
	@ApiModelProperty(required = true)
	private String ciudadDestino;
	@Column(name = "fecha_inicio", nullable = false)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
	@ApiModelProperty(notes = "Fecha de inicio del viaje",name = "fechaInicio",required = true,value  = "yyyy-MM-dd HH:mm")
	private LocalDateTime fechaInicio;
	@Column(name = "fecha_fin", nullable = false)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
	@ApiModelProperty(notes = "Fecha de fin del viaje",name = "fechaFin",required = true,value  = "yyyy-MM-dd HH:mm")
	private LocalDateTime fechaFin;
	@Column(nullable = false)
	@ApiModelProperty(required = true)
	private String descripcion;
	
	public boolean esValido() {
		if (this.fechaInicio != null && this.fechaFin != null) {
			String inicio = this.fechaInicio.toString();
			String fin = this.fechaFin.toString();
			
			boolean valido = (inicio.compareTo(fin)) < 0;
			if (!valido) {
				return false;
			}
			
		} else {
			return false;
		}
		return !StringUtils.isEmpty(this.getNombre())
				&& !StringUtils.isEmpty(this.getCiudadDestino())
				&& !StringUtils.isEmpty(this.getDescripcion());
	}
	
	public boolean puedeGuardarPlan(Plan plan) {
		return plan.getFechaInicio().compareTo(this.fechaInicio) >= 0
				&& this.fechaFin.compareTo(plan.getFechaFin()) >= 0;
	}
	
}
