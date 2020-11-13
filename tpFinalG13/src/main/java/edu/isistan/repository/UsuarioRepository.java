package edu.isistan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.isistan.model.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

	@Query("SELECT u FROM Usuario u WHERE u.username = :username")
	Usuario buscarPorUsername(String username);
	
	@Query("SELECT u FROM Usuario u WHERE u.email = :email")
	Usuario buscarPorEmail(String email);
	
}
