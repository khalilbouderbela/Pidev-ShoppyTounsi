package tn.esprit.pi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import tn.esprit.pi.entities.Tokens;

public interface TokenReopsitory extends CrudRepository<Tokens, Long>{

	@Query("Select "
			+ "DISTINCT t.name from Tokens t "
			+ "where t.userId=:id ")
    public List<String> getTokenByUser(@Param("id") long Shelfid);
	
	public Tokens findByName(String name);
}
