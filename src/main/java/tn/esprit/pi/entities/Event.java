package tn.esprit.pi.entities;

import java.io.Serializable;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Event implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long eventId;
	private String name;
	private int estimatedAmount;
	private int collectedAmount;
	private String location;
	private int numberOfVisits;
	private int numberOfparticipants ;
	private int numberOfCurrentParticipants ;
	@Temporal(TemporalType.DATE)
	private Date eventDate;
	
	@ManyToMany(cascade=CascadeType.ALL)
	private Set<User> user;
	
	@OneToMany(mappedBy="event",cascade=CascadeType.ALL)
	private Set<PhysicalParticipants> physicalparticipants;
	public long getEventId() {
		return eventId;
	}
	public void setEventId(long eventId) {
		this.eventId = eventId;
	}
	public int getEstimatedAmount() {
		return estimatedAmount;
	}
	public void setEstimatedAmount(int estimatedAmount) {
		this.estimatedAmount = estimatedAmount;
	}
	public int getCollectedAmount() {
		return collectedAmount;
	}
	public void setCollectedAmount(int collectedAmount) {
		this.collectedAmount = collectedAmount;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getNumberOfVisits() {
		return numberOfVisits;
	}
	public void setNumberOfVisits(int numberOfVisits) {
		this.numberOfVisits = numberOfVisits;
	}
	public int getNumberOfparticipants() {
		return numberOfparticipants;
	}
	public void setNumberOfparticipants(int numberOfparticipants) {
		this.numberOfparticipants = numberOfparticipants;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Date getEventDate() {
		return eventDate;
	}
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	public Set<User> getUser() {
		return user;
	}
	public void setUser(Set<User> user) {
		this.user = user;
	}
	public int getNumberOfCurrentParticipants() {
		return numberOfCurrentParticipants;
	}
	public void setNumberOfCurrentParticipants(int numberOfCurrentParticipants) {
		this.numberOfCurrentParticipants = numberOfCurrentParticipants;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public static Comparator<Event> ComparatorNumberOfVisits = new Comparator<Event>() {
	     
		@Override
		public int compare(Event o1, Event o2) {
			return (int)(o2.getNumberOfVisits()-o1.getNumberOfVisits());
		}};

}
