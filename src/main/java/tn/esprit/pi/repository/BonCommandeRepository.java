package tn.esprit.pi.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.pi.entities.BonCommande;
@Repository
public interface BonCommandeRepository extends CrudRepository< BonCommande, Long>{

	@Modifying
	@Transactional
	@Query("delete from BonCommande b where b.productName=:productName and b.providerName=:providerName and b.quantity=:quantity ")
	public int deleteBon(@Param("productName")String productName,@Param("providerName")String providerName,@Param("quantity")int quantity);
	
}
