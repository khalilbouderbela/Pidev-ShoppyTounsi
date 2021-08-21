package tn.esprit.pi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.pi.entities.Provider;

@Repository
public interface ProviderRepository extends CrudRepository<Provider, Long>{

 Provider findByName(String name);


}
