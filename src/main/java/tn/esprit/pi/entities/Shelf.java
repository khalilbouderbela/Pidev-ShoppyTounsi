package tn.esprit.pi.entities;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Shelf implements Serializable {

	private static final long serialVersionUID = 1L;
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private long ShelfId;
	private String Shelfname;
	@JsonIgnore
	@Temporal(TemporalType.DATE)
	private Date dateCreation = new Date(System.currentTimeMillis());

	@Temporal(TemporalType.DATE)
	private Date dateExpiration;
	@Enumerated(EnumType.STRING)
	private ShelfType type;
	private String image;
	@OneToMany(mappedBy = "shelf")
	private List<Category> category;
	@NotNull(message = "Please specify a rating between 1 and 5 inclusive")
	@Min(value = 1, message = "Please enter a rating greater than 0")
	@Max(value = 5, message = "Please enter a rating lesser than 5")
	private float rating;
	@Column(columnDefinition = "boolean default false")
	private Boolean promo = false;
	@Column(columnDefinition = "integer default 0")
	private int reductionPercantage;

	
	public Shelf(String shelfname, Date dateCreation, ShelfType type, String image, List<Category> category) {
		super();
		Shelfname = shelfname;
		this.dateCreation = dateCreation;
		this.type = type;
		this.category = category;
		this.image = image;
	}

	public Shelf() {
		super();
	}

	public long getShelfId() {
		return ShelfId;
	}

	public void setShelfId(long shelfId) {
		ShelfId = shelfId;
	}

	public String getShelfname() {
		return Shelfname;
	}

	public void setShelfname(String shelfname) {
		Shelfname = shelfname;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public ShelfType getType() {
		return type;
	}

	public void setType(ShelfType type) {
		this.type = type;
	}

	public List<Category> getCategory() {
		return category;
	}

	public void setCategory(List<Category> category) {
		this.category = category;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public Boolean getPromo() {
		return promo;
	}

	public void setPromo(Boolean promo) {
		this.promo = promo;
	}

	public int getReductionPercantage() {
		return reductionPercantage;
	}

	public void setReductionPercantage(int reductionPercantage) {
		this.reductionPercantage = reductionPercantage;
	}

	
	public Date getDateExpiration() {
		return dateExpiration;
	}

	public void setDateExpiration(Date dateExpiration) {
		this.dateExpiration = dateExpiration;
	}

	public Shelf calculateNewPrice() {
		List<Category> categories = new ArrayList<Category>();
		categories = this.getCategory();
		if (this.getType().equals(ShelfType.PROMO)) {
			for (Category c : categories) {
				for (Product p : c.getProduct()) {
					p.setPriceV(p.getPriceV() - (p.getPriceV() * this.getReductionPercantage()) / 100);
					p.setInPromo(true);
					System.out.println(p.getPriceV());
				}
			}
			this.setCategory(categories);
		
			return this;
		}
		return this;
	}

}
