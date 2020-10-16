package edu.isistan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import edu.isistan.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

	@Query("SELECT p FROM Producto p WHERE p.nombre LIKE :nombre%")
	Iterable<Producto> findAllByNombre(String nombre);

	@Query("SELECT COUNT(*) FROM Compra c "
		 + "WHERE c.cliente.id = :idCliente AND c.producto.id = :idProducto AND "
		 + "EXTRACT(YEAR FROM c.fecha) = :anio AND "
		 + "EXTRACT(MONTH FROM c.fecha) = :mes AND "
		 + "EXTRACT(DAY FROM c.fecha) = :dia")
	int getCantidad(int idProducto,int idCliente,int anio,int mes, int dia);

	/**
	 * retorna la cantidad de ese producto que compro el cliente en un dia
	 * 
	 * @param producto por el cual se va a buscar
	 * @param cliente busca los productos de este cliente
	 * @return la cantidad de productos que tiene el cliente en ese dia
	 */
	//int getCantidad(Producto producto, Cliente cliente);

}
