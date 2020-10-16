package edu.isistan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.isistan.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

	@Query("SELECT c FROM Cliente c WHERE c.nombre LIKE :nombre%")
	Iterable<Cliente> findAllByNombre(String nombre);
	
}
