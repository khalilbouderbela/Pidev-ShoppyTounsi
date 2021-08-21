package tn.esprit.pi.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.pi.entities.ShoppingCart;

@Repository
public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {

	

}
