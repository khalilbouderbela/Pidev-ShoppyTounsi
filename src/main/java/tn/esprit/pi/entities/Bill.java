package tn.esprit.pi.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Bill implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private long billId;
	@Temporal(TemporalType.DATE)
	private Date billDate;
	@OneToOne
	private Orders order;
	private int amount ;
	
	
	public Bill() {
		super();
	}
	public long getBillId() {
		return billId;
	}
	public void setBillId(long billId) {
		this.billId = billId;
	}
	public Date getBillDate() {
		return billDate;
	}
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}
	public Orders getOrder() {
		return order;
	}
	public void setOrder(Orders order) {
		this.order = order;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}

}
