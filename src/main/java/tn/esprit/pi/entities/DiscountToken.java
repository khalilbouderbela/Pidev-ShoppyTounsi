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

import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
public class DiscountToken implements Serializable {

	private static final long serialVersionUID = 6074898355976345209L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long discountTokenId ;
	private int value;
	@Temporal(TemporalType.DATE)
	private Date validity;
	private boolean isUsed; 
	
	@ManyToOne
	private User user;
	public long getDiscountTokenId() {
		return discountTokenId;
	}
	public void setDiscountTokenId(long discountTokenId) {
		this.discountTokenId = discountTokenId;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public Date getValidity() {
		return validity;
	}
	public void setValidity(Date validity) {
		this.validity = validity;
	}
	public boolean isUsed() {
		return isUsed;
	}
	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	

}
