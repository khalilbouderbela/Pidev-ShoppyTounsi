package tn.esprit.pi.repository;

import org.springframework.data.repository.CrudRepository;

import tn.esprit.pi.entities.MailHistory;

public interface MailHistoryRepository  extends CrudRepository<MailHistory, Long>{

}
