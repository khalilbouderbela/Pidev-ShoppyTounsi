package tn.esprit.pi.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ShoppingCart implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private long ShoppingCartId;
	
	@Temporal(TemporalType.DATE)
	private Date dateCreation;
	@JsonIgnore
	@OneToOne
	private User user;
	@JsonIgnore
	@OneToMany(mappedBy="shoppingCart")
	private List<OrderLine> orderLines;
	
	

	public ShoppingCart() {
		super();
	}

	public long getShoppingCartId() {
		return ShoppingCartId;
	}

	public void setShoppingCartId(long shoppingCartId) {
		ShoppingCartId = shoppingCartId;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OrderLine> getOrderLines() {
		return orderLines;
	}

	public void setOrderLines(List<OrderLine> orderLines) {
		this.orderLines = orderLines;
	}
	
	
	
	

}
