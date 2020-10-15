package edu.isistan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.isistan.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

	@Query("SELECT p FROM Producto p WHERE p.nombre = :nombre")
	Iterable<Producto> findAllByNombre(String nombre);

}
