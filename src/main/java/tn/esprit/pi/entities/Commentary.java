package tn.esprit.pi.entities;

import java.io.Serializable;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class Commentary implements Serializable {

	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private long commentaryId;
	private String description;
	private int rating;
	private int nbLikes;
	private int nbDislikes;
	@ManyToOne
	private Post post;


	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public Commentary() {
		super();
	}

	public Commentary(String description, int rating, int nbLikes, int nbDislikes) {
		super();
		this.description = description;
		this.rating = rating;
		this.nbLikes = nbLikes;
		this.nbDislikes = nbDislikes;
	}

	public long getCommentaryId() {
		return commentaryId;
	}

	public void setCommentaryId(long commentaryId) {
		this.commentaryId = commentaryId;
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


}
