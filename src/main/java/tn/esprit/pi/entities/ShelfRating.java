package tn.esprit.pi.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class ShelfRating implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ratingId;

    @ManyToOne(cascade =CascadeType.ALL)
    @NotNull(message = "The user id provided is either empty or null")
    private User user;

    @ManyToOne (cascade =CascadeType.ALL)
    @NotNull(message = "The book id provided is either empty or null")
    private Shelf shelf;

  
    @NotNull(message = "Please specify a rating between 1 and 5 inclusive")
    @Min(value = 1, message = "Please enter a rating greater than 0")
    @Max(value = 10, message = "Please enter a rating lesser than 10")
    private int rating;


    
	public ShelfRating() {
		super();
	}


	public long getRatingId() {
		return ratingId;
	}


	public void setRatingId(long ratingId) {
		this.ratingId = ratingId;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Shelf getShelf() {
		return shelf;
	}


	public void setShelf(Shelf shelf) {
		this.shelf = shelf;
	}


	public int getRating() {
		return rating;
	}


	public void setRating(int rating) {
		this.rating = rating;
	}
    
    
    

}
