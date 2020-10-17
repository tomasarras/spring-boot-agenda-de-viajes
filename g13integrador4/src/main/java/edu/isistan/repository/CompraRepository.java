package edu.isistan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import edu.isistan.model.Compra;
import edu.isistan.model.Producto;

public interface CompraRepository extends JpaRepository<Compra, Integer> {
	@Query(value=""
			+ "SELECT com.id_cliente, cli.nombre_cliente , SUM(precio) AS monto_total "
			+ "FROM Compra com JOIN Producto p ON (com.id_producto = p.id_producto) "
			+ "JOIN Cliente cli ON (com.id_cliente = cli.id_cliente) "
			+ "GROUP BY com.id_cliente",nativeQuery=true)
	Iterable<Object> getReporteCompras();
		 /*+ "JOIN Cliente cli ON (com.id_cliente = cli.id_cliente) "
		 + "GROUP BY id_cliente")*/
}
