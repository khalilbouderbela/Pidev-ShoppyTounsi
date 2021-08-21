package tn.esprit.pi.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class BonCommande implements Serializable{


	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long bonCoammandId;
	private int quantity;
	private String productName;
	private String providerName;
	@Temporal(TemporalType.DATE)
	private Date bonCommandeDate= new Date(System.currentTimeMillis());
	
	
	
	public BonCommande() {
		super();
	}
	public long getBonCoammandId() {
		return bonCoammandId;
	}
	public void setBonCoammandId(long bonCoammandId) {
		this.bonCoammandId = bonCoammandId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	public Date getBonCommandeDate() {
		return bonCommandeDate;
	}
	public void setBonCommandeDate(Date bonCommandeDate) {
		this.bonCommandeDate = bonCommandeDate;
	}
	
	
	
	
	

}
