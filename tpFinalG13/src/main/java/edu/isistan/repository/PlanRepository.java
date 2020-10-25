package edu.isistan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.isistan.model.Plan;


public interface PlanRepository extends JpaRepository<Plan, Integer> {
	
	/*@Query("SELECT c FROM Cliente c WHERE c.nombre LIKE :nombre%")
	Iterable<Plan> findAllByNombre(String nombre);*/
}
