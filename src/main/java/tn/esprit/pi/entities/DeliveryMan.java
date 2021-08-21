package tn.esprit.pi.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class DeliveryMan implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long deliveryManId;
	private int position;
	private boolean isAvailable;
	@OneToMany(mappedBy = "deliveryMan")
	private List<Delivery> delivery;

	public DeliveryMan(int position, boolean isAvailable) {
		super();
		this.position = position;
		this.isAvailable = isAvailable;
	}

	public DeliveryMan() {
		super();
	}

	public long getDeliveryManId() {
		return deliveryManId;
	}

	public void setDeliveryManId(long deliveryManId) {
		this.deliveryManId = deliveryManId;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public List<Delivery> getDelivery() {
		return delivery;
	}

	public void setDelivery(List<Delivery> delivery) {
		this.delivery = delivery;
	}

}
