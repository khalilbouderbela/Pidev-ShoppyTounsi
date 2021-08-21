
package tn.esprit.pi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.pi.entities.Logg;

@Repository
public interface LoggRepository extends CrudRepository<Logg, Long> {
	@Query("select l from Logg l order by l.dateAction desc ")
	public List<Logg> getAllLoggDesc();

	@Query("select l from Logg l  where l.userId=:us order by l.dateAction desc ")
	public List<Logg> getLoggDescByUser(@Param("us") long id);
	
	@Query("delete  from Logg l  where l.userId=:us  ")
	public List<Logg> deleteByUserId(@Param("us") long id);
}
