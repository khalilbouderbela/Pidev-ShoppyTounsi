package tn.esprit.pi.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.pi.entities.Category;
import tn.esprit.pi.entities.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
	@Query("Select " + "DISTINCT prod from Product prod " + "where prod.quantity < 10")
	public List<Product> listMissingProduct();

	
	
	

	Optional<Product> findByName(String name);
	
	
	Page<Product> findAll(Pageable pageable);
	List<Product> findAll();

    Page<Product> getByCategory(Category category, Pageable pageable);
    
   
    
	
	@Transactional
	@Modifying
	@Query("update Product p set p.image = :image where p.productId = :productId")
	public void affectImageToProduct(@Param("image") String image,@Param("productId") Long productId);
	
	
	

}
