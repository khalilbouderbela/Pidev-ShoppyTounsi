package tn.esprit.pi.service;

import java.util.List;



import tn.esprit.pi.entities.Logg;

public interface ILoggService {
	 Logg addLogg(Logg Logg);

	 void DeleteLoggById(long LoggId);

	 List<Logg> getAllLoggs();
	
	 Logg updateLogg(Logg Logg);
	
	 Logg getLoggById(Long LoggId);
	  List<Logg> getAllLogg();
	  List<Logg> getLoggByUser(long id);
	
}
