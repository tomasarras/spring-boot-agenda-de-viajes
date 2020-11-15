package edu.isistan.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import edu.isistan.model.Viaje;
import edu.isistan.reportes.ReporteCiudad;

public interface ViajeRepository extends JpaRepository<Viaje, Integer> {
	@Query("SELECT v FROM Viaje v WHERE v.usuario.id = :idUsuario")
	List<Viaje> buscarViajesDeUsuario(int idUsuario);
	
	@Query("SELECT v FROM Viaje v WHERE v.usuario.id = :idUsuario AND v.fechaFin < :now")
	List<Viaje> buscarViajesDeUsuarioRealizados(int idUsuario,LocalDateTime now);
	
	@Query("SELECT v FROM Viaje v WHERE v.usuario.id = :idUsuario AND v.fechaFin >= :now")
	List<Viaje> buscarViajesDeUsuarioPendientes(int idUsuario,LocalDateTime now);
	
	@Query("SELECT new edu.isistan.reportes.ReporteCiudad(v.ciudadDestino, COUNT(v.id)) "
			+ "FROM Viaje v "
			+ "GROUP BY v.ciudadDestino "
			+ "ORDER BY COUNT(v.ciudadDestino) DESC")
	List<ReporteCiudad> reporteCiudadesMasVisitadas();
}
