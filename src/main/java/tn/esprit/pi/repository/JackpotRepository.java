package tn.esprit.pi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.pi.entities.Jackpot;


@Repository
public interface JackpotRepository extends CrudRepository<Jackpot,Long>{

}
