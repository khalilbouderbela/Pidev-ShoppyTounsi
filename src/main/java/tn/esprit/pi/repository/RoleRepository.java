package tn.esprit.pi.repository;
import tn.esprit.pi.entities.Role;
import tn.esprit.pi.entities.Roles;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {
	Optional<Roles> findByName(Role name);
}
