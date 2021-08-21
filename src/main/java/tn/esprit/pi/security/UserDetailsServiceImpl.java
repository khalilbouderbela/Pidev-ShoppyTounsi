package tn.esprit.pi.security;

import org.springframework.transaction.annotation.Transactional;

import javax.naming.NameNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import tn.esprit.pi.entities.User;
import tn.esprit.pi.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByName(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		return UserDetailsImpl.build(user);
	}

}
