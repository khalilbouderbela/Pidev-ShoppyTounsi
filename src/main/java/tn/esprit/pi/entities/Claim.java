package tn.esprit.pi.entities;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Claim implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ClaimPK claimPK;

	@ManyToOne
	@JoinColumn(name = "productId", referencedColumnName = "productId", insertable = false, updatable = false)
	private Product product;

	@ManyToOne
	@JoinColumn(name = "userId", referencedColumnName = "userId", insertable = false, updatable = false)
	private User user;

	public String description;

	public ClaimPK getClaimPK() {
		return claimPK;
	}

	public void setClaimPK(ClaimPK claimPK) {
		this.claimPK = claimPK;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
