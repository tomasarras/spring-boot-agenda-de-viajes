package edu.isistan.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import edu.isistan.model.Usuario;
import edu.isistan.reportes.ReporteUsuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

	@Query("SELECT u FROM Usuario u WHERE u.username = :username")
	Usuario buscarPorUsername(String username);
	
	@Query("SELECT u FROM Usuario u WHERE u.email = :email")
	Usuario buscarPorEmail(String email);

	@Query("SELECT new edu.isistan.reportes.ReporteUsuario(u.email,u.username, COUNT(u.id)) "
			+ " FROM Viaje v JOIN v.usuario u "
			+ "GROUP BY u.id "
			+ "ORDER BY COUNT(u.id) DESC")
			List<ReporteUsuario> getUsuariosConMasViajes();
}