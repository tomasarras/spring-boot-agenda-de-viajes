package edu.isistan.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import edu.isistan.model.Plan;

public interface PlanRepository extends JpaRepository<Plan, Integer> {
	
	@Query("SELECT p FROM Viaje v "
			+ "JOIN v.usuario u JOIN v.planes p "
			+ "WHERE u.id = :idUsuario")
	List<Plan> getPlanesDelUsuario(int idUsuario);

	@Query("SELECT p FROM Viaje v "
			+ "JOIN v.usuario u JOIN v.planes p "
			+ "WHERE u.id = :idUsuario "
			+ "AND p.fechaInicio >= :fechaInicio "
			+ "AND p.fechaFin <= :fechaFin")
	List<Plan> getPlanesDelUsuarioPorFecha(int idUsuario, LocalDateTime fechaInicio, LocalDateTime fechaFin);

	@Query("SELECT p FROM Viaje v "
			+ "JOIN v.usuario u JOIN v.planes p "
			+ "WHERE u.id = :idUsuario AND v.ciudadDestino = :zona")
	List<Plan> getPlanesDelUsuarioPorZona(int idUsuario, String zona);

	@Query("SELECT p FROM Viaje v "
			+ "JOIN v.usuario u JOIN v.planes p "
			+ "WHERE u.id = :idUsuario AND p.fechaInicio >= :now")
	List<Plan> getPlanesDelUsuarioPendientes(int idUsuario, LocalDateTime now);

	@Query("SELECT p FROM Viaje v "
			+ "JOIN v.usuario u JOIN v.planes p "
			+ "WHERE u.id = :idUsuario AND p.fechaFin < :now")
	List<Plan> getPlanesDelUsuarioRealizados(int idUsuario, LocalDateTime now);
}