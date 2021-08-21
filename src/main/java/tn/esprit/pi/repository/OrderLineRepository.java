package tn.esprit.pi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.pi.entities.OrderLine;
import tn.esprit.pi.entities.Product;

import java.util.List;

@Repository
public interface OrderLineRepository extends CrudRepository<OrderLine, Long> {

    List<OrderLine> findByProduct(Product product);
    List<OrderLine> findAll();

}
