package tn.esprit.pi.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.pi.entities.OrderLine;
import tn.esprit.pi.entities.Orders;

@Repository
public interface OrdersRepository extends CrudRepository<Orders, Long> {
	
	
	@Query(value="select order_line_order_line_id from orders_order_line where orders_order_id= :Id ",nativeQuery = true)
	List<Long> selectOrderLigneIds(@Param("Id") Long Id);
	
	
	/*
	@Query(value="DELETE FROM orders_order_line WHERE orders_order_id= :orderId",nativeQuery = true)
	void deleteLastOrders(@Param("orderId") Long orderId);*/
	
	List<Orders> findAll();
	

}
