package tn.esprit.pi.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
public class Ad implements Serializable {

    private static final long serialVersionUID = 1L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    private String name;
    
    private int touchedPeople;
    private int views;
    private float price;
    private float discount;
    private String image;
    //private int age;
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @OneToOne
    private Product product;
	public Ad() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Ad(String name, int touchedPeople, int views, float price, float discount, String image, Date startDate,
			Date endDate, Product product) {
		super();
		this.name = name;
		this.touchedPeople = touchedPeople;
		this.views = views;
		this.price = price;
		this.discount = discount;
		this.image = image;
		this.startDate = startDate;
		this.endDate = endDate;
		this.product = product;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTouchedPeople() {
		return touchedPeople;
	}
	public void setTouchedPeople(int touchedPeople) {
		this.touchedPeople = touchedPeople;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public float getDiscount() {
		return discount;
	}
	public void setDiscount(float discount) {
		this.discount = discount;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
    
    


}
