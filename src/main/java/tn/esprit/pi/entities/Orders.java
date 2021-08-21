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

@Entity
public class Orders implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long orderId;

	@Temporal(TemporalType.DATE)
	private Date orderDate;

	@OneToOne(mappedBy = "order")
	private Delivery delivery;
	@OneToOne(mappedBy = "order")
	private Bill bill;

	@OneToMany
	private List<OrderLine> orderLine;

	public Orders() {
		super();
	}

	public Orders(Date orderDate) {
		super();
		this.orderDate = orderDate;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date eventDate) {
		this.orderDate = eventDate;
	}

	public Delivery getDelivery() {
		return delivery;
	}

	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public List<OrderLine> getOrderLine() {
		return orderLine;
	}

	public void setOrderLine(List<OrderLine> orderLine) {
		this.orderLine = orderLine;
	}

}
