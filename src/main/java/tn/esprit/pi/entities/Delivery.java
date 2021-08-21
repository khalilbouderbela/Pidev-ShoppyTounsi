package tn.esprit.pi.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Delivery implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long deliveryId;
	@Temporal(TemporalType.DATE)
	private Date deliveryDate;
	private int fees;
	@ManyToOne
	private DeliveryMan deliveryMan;
	@OneToOne
	private Orders order;

	public Delivery(Date deliveryDate, int fees) {
		super();
		this.deliveryDate = deliveryDate;
		this.fees = fees;
	}

	public Delivery() {
		super();
	}

	public long getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(long deliveryId) {
		this.deliveryId = deliveryId;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public int getFees() {
		return fees;
	}

	public void setFees(int fees) {
		this.fees = fees;
	}

	public DeliveryMan getDeliveryMan() {
		return deliveryMan;
	}

	public void setDeliveryMan(DeliveryMan deliveryMan) {
		this.deliveryMan = deliveryMan;
	}

	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}

}
