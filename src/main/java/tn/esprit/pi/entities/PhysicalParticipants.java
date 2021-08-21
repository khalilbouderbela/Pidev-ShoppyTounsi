package tn.esprit.pi.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class PhysicalParticipants implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private long id ;
private String fullname;
private String email;
private int sum;
@ManyToOne
private Event event;
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public String getFullname() {
	return fullname;
}
public void setFullname(String fullname) {
	this.fullname = fullname;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public int getSum() {
	return sum;
}
public void setSum(int sum) {
	this.sum = sum;
}
public Event getEvent() {
	return event;
}
public void setEvent(Event event) {
	this.event = event;
}
public static long getSerialversionuid() {
	return serialVersionUID;
}

}
