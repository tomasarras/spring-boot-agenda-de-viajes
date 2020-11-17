package edu.isistan.model.planes;

import javax.persistence.Column;
import javax.persistence.Entity;
import edu.isistan.model.Plan;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
public class PlanViajeColectivo extends Plan {
	@Column(nullable = false)
	private Integer asiento;
	@Override
	public boolean esValido() {
		return !(!super.esValido() || asiento == null);
	}
	
	@Override
	public void modificarse(Plan plan) {
		PlanViajeColectivo p = (PlanViajeColectivo) plan;
		super.modificarse(plan);
		this.asiento = p.getAsiento();
	}

}
