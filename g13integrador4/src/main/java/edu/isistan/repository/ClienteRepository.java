package edu.isistan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.isistan.model.Cliente;
/**
 * Repositorio de clientes
 * @author Tomas
 *
 */
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	/**
	 * 
	 * @param nombre de los clientes a buscar
	 * @return todos los clientes que tengan ese nombre
	 */
	@Query("SELECT c FROM Cliente c WHERE c.nombre LIKE :nombre%")
	Iterable<Cliente> findAllByNombre(String nombre);
	
}
