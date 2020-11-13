package edu.isistan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import edu.isistan.model.Usuario;
import edu.isistan.model.Viaje;

public interface ViajeRepository extends JpaRepository<Viaje, Integer> {
	@Query("SELECT v FROM Viaje v WHERE v.usuario.id = :idUsuario")
	List<Viaje> buscarViajesDeUsuario(int idUsuario);

}
