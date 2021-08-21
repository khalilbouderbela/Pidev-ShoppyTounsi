package tn.esprit.pi.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Tokens  implements Serializable{


	private static final long serialVersionUID = 1L;
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private long TokenId;
	private long userId;
	private String name;
	public long getTokenId() {
		return TokenId;
	}
	public void setTokenId(long tokenId) {
		TokenId = tokenId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Tokens() {
		super();
	}
	
	
	
	
	
}
