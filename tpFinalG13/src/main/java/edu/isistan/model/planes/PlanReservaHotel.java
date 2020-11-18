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
@ApiModel(value="PlanReservaHotel", description="Plan que hereda de plan comun")
public class PlanReservaHotel extends Plan {
	@Column(nullable = false)
	@ApiModelProperty(required = true)
	private String habitacion;
	@Column(nullable = false)
	@ApiModelProperty(required = true)
	private String direccion;
	public PlanReservaHotel() {
		super();
	}
	public String getHabitacion() {
		return habitacion;
	}
	public void setHabitacion(String habitacion) {
		this.habitacion = habitacion;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	@Override
	public boolean esValido() {
		return !(!super.esValido() ||
				StringUtils.isEmpty(habitacion) || 
				StringUtils.isEmpty(direccion));
	}
	
	@Override
	public void modificarse(Plan plan) {
		PlanReservaHotel p = (PlanReservaHotel) plan;
		super.modificarse(plan);
		this.habitacion = p.getHabitacion();
		this.direccion = p.getDireccion();
	}
	
}
