package edu.isistan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import edu.isistan.model.Producto;
/**
 * Repositorio de productos
 * @author Tomas
 *
 */
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
	/**
	 * 
	 * @param nombre el nombre de los productos a buscar
	 * @return todos los productos que tengan ese nombre
	 */
	@Query("SELECT p FROM Producto p WHERE p.nombre LIKE :nombre%")
	Iterable<Producto> findAllByNombre(String nombre);

}
