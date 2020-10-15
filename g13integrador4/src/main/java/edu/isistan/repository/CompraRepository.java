package edu.isistan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.isistan.model.Compra;

public interface CompraRepository extends JpaRepository<Compra, Integer> {

	/*@Query("SELECT p FROM Producto p WHERE p.nombre = :nombre")
	Iterable<Producto> findAllByNombre(String nombre);*/

}
