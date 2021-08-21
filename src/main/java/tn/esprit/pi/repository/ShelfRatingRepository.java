package tn.esprit.pi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.pi.entities.Category;
import tn.esprit.pi.entities.Shelf;
import tn.esprit.pi.entities.ShelfRating;
import tn.esprit.pi.entities.User;

public interface ShelfRatingRepository extends CrudRepository<ShelfRating, Long>{
	
	List<ShelfRating> findByRating( int rating);

    List<ShelfRating> findByShelf( Shelf shelf);

    List<ShelfRating> findByUser( User user);
    
	@Query("SELECT count(*) FROM  ShelfRating s where s.shelf.ShelfId=:id ")
    public int countshelfRate(@Param("id") long Shelfid);
	
	@Query("SELECT sum(s.rating) FROM  ShelfRating s where s.shelf.ShelfId=:id ")
    public int sumshelfRate(@Param("id") long Shelfid);
	
	@Query("SELECT s FROM  ShelfRating s where s.shelf.ShelfId=:shelfId and s.user.userId=:userId ")
    public ShelfRating getRatingByUserAndShelf(@Param("userId") long userId ,@Param("shelfId")long Shelfid);

	@Modifying
	@Transactional
	@Query("Delete FROM  ShelfRating s where s.shelf.ShelfId=:shelfId  ")
    public void deleteAllShelfRating( @Param("shelfId")long Shelfid);
}
