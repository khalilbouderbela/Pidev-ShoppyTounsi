package tn.esprit.pi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.pi.entities.Ad;
import tn.esprit.pi.entities.Product;

import java.util.Date;
import java.util.List;

@Repository
public interface AdRepository extends CrudRepository<Ad, Long> {
    List<Ad> findAll();
    Ad findByProduct(Product product);
    List<Ad> findByEndDate(Date endDate);
    void deleteById(Long aLong);
}
