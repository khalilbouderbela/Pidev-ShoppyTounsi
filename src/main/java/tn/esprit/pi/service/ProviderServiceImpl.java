package tn.esprit.pi.service;

import java.util.List
;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import tn.esprit.pi.entities.Entry;
import tn.esprit.pi.entities.Provider;

import tn.esprit.pi.repository.ProviderRepository;

@Service
public class ProviderServiceImpl implements IproviderService {
	@Autowired
	ProviderRepository providerRepository;

	@Override
	public long addProvider(Provider provider) {
		providerRepository.save(provider);
		return provider.getProviderId();
	}

	@Override
	public void DeleteProviderById(long providerId) {
		Provider provider = providerRepository.findById(providerId).get();
		
		for (Entry ent : provider.getEntry())
			ent.setProvider(null);
		
		
		providerRepository.delete(provider);
	}
	@Override
	public List<Provider> getAllProviders() {
		return (List<Provider>) providerRepository.findAll();
	}

	@Override
	public Provider getProviderById(long providerId) {
		return providerRepository.findById(providerId).get();
	}

	@Override
	public Provider updateProvider(Provider provider) {
	providerRepository.save(provider);
	return provider;
	}

	
}
