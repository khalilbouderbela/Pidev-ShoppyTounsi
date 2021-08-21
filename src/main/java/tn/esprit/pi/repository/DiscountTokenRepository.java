package tn.esprit.pi.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.pi.entities.DiscountToken;
import tn.esprit.pi.entities.User;


@Repository
public interface DiscountTokenRepository extends CrudRepository<DiscountToken,Long> {
 public List<DiscountToken> findDiscountTokenByUser(User u); 
}
