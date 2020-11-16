package edu.isistan.model.planes;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.springframework.util.StringUtils;

import edu.isistan.model.Plan;

@Entity
public class PlanViajeTren extends Plan {
	@Column(nullable = false)
	private Integer asiento;
	@Column(nullable = false)
	private String estacion;
	public PlanViajeTren() {
		super();
	}
	public int getAsiento() {
		return asiento;
	}
	public void setAsiento(int asiento) {
		this.asiento = asiento;
	}
	public String getEstacion() {
		return estacion;
	}
	public void setEstacion(String estacion) {
		this.estacion = estacion;
	}
	
	@Override
	public boolean esValido() {
		return !(!super.esValido() ||
				asiento == null || 
				StringUtils.isEmpty(estacion));
	}
	
	@Override
	public void modificarse(Plan plan) {
		PlanViajeTren p = (PlanViajeTren) plan;
		super.modificarse(plan);
		this.asiento = p.getAsiento();
		this.estacion = p.getEstacion();
	}

	
}
