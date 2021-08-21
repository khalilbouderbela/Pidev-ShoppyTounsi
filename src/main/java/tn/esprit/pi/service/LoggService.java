package tn.esprit.pi.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pi.entities.Logg;
import tn.esprit.pi.repository.LoggRepository;
@Service
public class LoggService implements ILoggService{
	@Autowired
	LoggRepository LoggRepository;
	
	@Override
	public Logg addLogg(Logg Logg) {
		
		return this.LoggRepository.save(Logg);
		 
	}

	@Override
	public void DeleteLoggById(long LoggId) {
		Logg Logg = this.LoggRepository.findById(LoggId).get();
		this.LoggRepository.delete(Logg);
		
	}

	@Override
	public List<Logg> getAllLoggs() {
		
		return  (List<Logg>) this.LoggRepository.findAll();
	}
	@Override
	public Logg updateLogg(Logg Logg)
	{
		return this.LoggRepository.save(Logg);
		
	}

	@Override
	public Logg getLoggById(Long LoggId) {
		
		return this.LoggRepository.findById(LoggId).get();
	}

	@Override
	public List<Logg> getAllLogg() {
		// TODO Auto-generated method stub
		return this.LoggRepository.getAllLoggDesc();
	}

	@Override
	public List<Logg> getLoggByUser(long id) {
		// TODO Auto-generated method stub
		return this.LoggRepository.getLoggDescByUser(id);
	}
}
	