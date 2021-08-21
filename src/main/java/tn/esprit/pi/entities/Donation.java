package tn.esprit.pi.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
public class Donation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2208393063814081066L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long donationId;
	private int amount ;
	@Temporal(TemporalType.DATE)
	private Date donationDate;
	@JsonIgnore
	@ManyToOne
	private User user;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public long getDonationId() {
		return donationId;
	}
	public void setDonationId(long donationId) {
		this.donationId = donationId;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Date getDonationDate() {
		return donationDate;
	}
	public void setDonationDate(Date donationDate) {
		this.donationDate = donationDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

