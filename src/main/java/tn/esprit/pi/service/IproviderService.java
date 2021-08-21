package tn.esprit.pi.service;

import java.util.List;

import tn.esprit.pi.entities.Provider;

public interface IproviderService {
	
	public long addProvider(Provider provider);

	public void DeleteProviderById(long providerId);

	public List<Provider> getAllProviders();
	
	public 	Provider getProviderById(long  providerId) ;
	
	public Provider updateProvider(Provider provider );
	

}
