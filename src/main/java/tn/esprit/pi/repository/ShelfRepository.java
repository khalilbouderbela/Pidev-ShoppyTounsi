package tn.esprit.pi.repository;
import java.time.LocalDate;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.pi.entities.Category;
import tn.esprit.pi.entities.OrderLine;
import tn.esprit.pi.entities.Orders;
import tn.esprit.pi.entities.Shelf;
import tn.esprit.pi.entities.ShelfType;



@Repository
public interface ShelfRepository extends CrudRepository<Shelf,Long> {
	
    List<Shelf> findAllByType(ShelfType type);
   

	@Query("SELECT count(*) FROM Shelf")
    public int countshelf();
	
	@Query("Select "
			+ "DISTINCT cat from Category cat "
			+ "where cat.shelf.ShelfId=:id ")
    public List<Category> getAllCategoryByShelfJPQL(@Param("id") long Shelfid);
	
	@Query("Select "
			+ "prod.name from Product prod "
			+ "join prod.category cat "
			+ "join cat.shelf s "
			+ "where s.ShelfId=:id ")
    public List<String> getAllProductByShelfJPQL(@Param("id") long Shelfid);
	
	
	@Query("Select "
			+ "DISTINCT o from OrderLine o "
			+ "join o.product p "
			+ "join p.category c "
			+ "join c.shelf s "
			+ "where s.ShelfId=:id ")
    public List<OrderLine> getOrderByShelf(@Param("id") long Shelfid);
	
	
	 @Transactional
	  @Modifying
	@Query("update Shelf s set s.reductionPercantage=:rp where s.ShelfId=:id")
	 public void updateReductionPercentage(@Param("rp") int reduction ,@Param("id") long id);


	 @Query("select DISTINCT s from Shelf s ORDER BY s.rating DESC")
	 public List<Shelf> getShelfdOrderByRating();
	 
	 @Query("select DISTINCT cat from Category cat")
	 public List<Category> getAllCategory();
	 
	 
	 @Query("select DISTINCT o from Orders o")
	 public List<Orders> getAllOrder();
	 
	 @Query("select o from Orders o where orderDate >= :threeDaysAgoDate")
	    List<Orders> getOrdersLastThreeDays(@Param("threeDaysAgoDate") Date threeDaysAgoDate);


	 
	 @Query("select DISTINCT o from OrderLine o")
	 public List<OrderLine> getAllOrderLine();

}






