package edu.isistan.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import edu.isistan.model.Compra;
import edu.isistan.model.Producto;
/**
 * Repositorio de compras
 * @author Tomas
 *
 */
public interface CompraRepository extends JpaRepository<Compra, Integer> {
	
	/**
	 * 
	 * @return List<FacturaCliente> Lista de clientes con el 
	 * gasto que lleva gastado hasta el momento
	 */
	@Query(value=
			 "SELECT com.id_cliente, cli.nombre_cliente , SUM(precio) AS monto_total "
			+ "FROM Compra com JOIN Producto p ON (com.id_producto = p.id_producto) "
			+ "JOIN Cliente cli ON (com.id_cliente = cli.id_cliente) "
			+ "GROUP BY com.id_cliente"
			,nativeQuery=true)
	Iterable<Object> getReporteCompras();

	
	/**
	 * 
	 * @return List<FacturadoEnUnDia> una lista con las ganancias 
	 * generadas por cada dia
	 */
	@Query(value = 
			"SELECT fecha, SUM(precio) AS facturado FROM Compra c "
		  + "JOIN Producto p ON (c.id_producto = p.id_producto) "
		  + "GROUP BY fecha"
			,nativeQuery = true)
	Iterable<Object> getReporteComprasFechas();
	
	
	/**
	 * retorna los productos mas vendidos
	 * @param pageable la cantidad de productos
	 * @return List<Producto> con los productos mas vendidos
	 */
	@Query("SELECT p from Compra c "
		 + "JOIN c.producto p "
		 + "GROUP BY p "
		 + "ORDER BY COUNT(p) DESC")
	List<Producto> getProductosMasVendidos(Pageable pageable);
	
	/**
	 * busca la cantidad de compras que realizo un cliente sobre un mismo producto
	 * en ese dia
	 * @param idProducto id del producto que se quiere saber la cantidad
	 * @param idCliente id del cliente que lo compra
	 * @param fecha dia en el que se van a contar los productos
	 * @return la cantidad de un mismo producto que compro un cliente en ese dia
	 */
	@Query("SELECT COUNT(c) FROM Compra c "
			 + "WHERE c.cliente.id = :idCliente AND c.producto.id = :idProducto AND "
			 + "fecha = :fecha")
	int getCantidadProductoxCliente(int idProducto,int idCliente,LocalDate fecha);
}
