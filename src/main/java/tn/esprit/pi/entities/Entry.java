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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Entry implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long entryId;
	private int quantity;
	private float montant;
	private float ArticalPrice;
	@Temporal(TemporalType.DATE)
	private Date entryDate= new Date(System.currentTimeMillis());
	@ManyToOne
	private Product product;
	@ManyToOne
	private Provider provider;

	public Entry() {
		super();
	}

	
	public float getMontant() {
		return montant;
	}


	public void setMontant(float montant) {
		this.montant = montant;
	}


	public long getEntryId() {
		return entryId;
	}

	public void setEntryId(long entryId) {
		this.entryId = entryId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}


	public float getArticalPrice() {
		return ArticalPrice;
	}


	public void setArticalPrice(float articalPrice) {
		ArticalPrice = articalPrice;
	}

}
