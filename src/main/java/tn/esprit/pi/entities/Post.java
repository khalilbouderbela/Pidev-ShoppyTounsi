package tn.esprit.pi.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class Post implements Serializable {

	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private long postId;
	private String subject;
	private String description;
	private int rating;
	private int nbLikes;
	private int nbDislikes;
	@ManyToOne
	private User user;
	@OneToMany(mappedBy = "post")
	private List<Commentary> commentary;
	public List<Commentary> getCommentary() {
		return commentary;
	}
	public void setCommentary(List<Commentary> commentary) {
		this.commentary = commentary;
	}




	public Post() {
		super();
	}

	public Post(String subject, String description, int rating, int nbLikes, int nbDislikes) {
		super();
		this.subject = subject;
		this.description = description;
		this.rating = rating;
		this.nbLikes = nbLikes;
		this.nbDislikes = nbDislikes;
	}

	public long getPostId() {
		return postId;
	}

	public void setPostId(long postId) {
		this.postId = postId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public int getNbLikes() {
		return nbLikes;
	}

	public void setNbLikes(int nbLikes) {
		this.nbLikes = nbLikes;
	}

	public int getNbDislikes() {
		return nbDislikes;
	}

	public void setNbDislikes(int nbDislikes) {
		this.nbDislikes = nbDislikes;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	

}
