package tn.esprit.pi.entities;

import java.io.Serializable;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private long productId;
	private String name;
	private String description;
	private float priceV;
	private float priceA;
	private int quantity;
	private long code;
	@JsonIgnore
	@ManyToOne
	private Category category;
	private boolean inPromo;
	private String image;
	@OneToOne(mappedBy="product",cascade=CascadeType.REMOVE)
	private Ad ad;
	@OneToMany(mappedBy = "product")
	private List<Claim> claim;
@JsonIgnore
	@OneToMany(mappedBy = "product")
	private List<Entry> entry;

	public Product(String name, String description, float priceV, float priceA, int quantity, long code,
			Category category, boolean inPromo, String image, Ad ad) {
		super();
		this.name = name;
		this.description = description;
		this.priceV = priceV;
		this.priceA = priceA;
		this.quantity = quantity;
		this.code = code;
		this.category = category;
		this.inPromo = inPromo;
		this.image = image;
		this.ad = ad;
	}

	public Product() {
		super();
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getPriceV() {
		return priceV;
	}

	public void setPriceV(float priceV) {
		this.priceV = priceV;
	}

	public float getPriceA() {
		return priceA;
	}

	public void setPriceA(float priceA) {
		this.priceA = priceA;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public long getCode() {
		return code;
	}

	public void setCode(long code) {
		this.code = code;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}



	public boolean isInPromo() {
		return inPromo;
	}

	public void setInPromo(boolean inPromo) {
		this.inPromo = inPromo;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Ad getAd() {
		return ad;
	}

	public void setAd(Ad ad) {
		this.ad = ad;
	}


	public List<Claim> getClaim() {
		return claim;
	}

	public void setClaim(List<Claim> claim) {
		this.claim = claim;
	}

	public List<Entry> getEntry() {
		return entry;
	}

	public void setEntry(List<Entry> entry) {
		this.entry = entry;
	}

}
