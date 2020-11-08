package edu.isistan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.isistan.model.Plan;

public interface PlanRepository extends JpaRepository<Plan, Integer> {
	
	/*@Query("SELECT p FROM Plan p JOIN p.viaje v WHERE v.id = :idViaje")
	List<Plan> getPlanesDeViaje(int idViaje);*/
}
