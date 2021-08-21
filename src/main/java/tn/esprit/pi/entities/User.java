package tn.esprit.pi.entities;

import javax.persistence.*;



import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private long userId;
	private long cin;
	private String name;
	private String address;
	private String email;
	private String numTel;
	private String sex;

	@Column(columnDefinition = "integer default 0 not null")
	private int counterLogin;
	@Column(columnDefinition="tinyint(1) default 0 not null")
	private boolean desactivate;
	@Temporal(TemporalType.DATE)
	private Date lastLoginDate;
	@Temporal(TemporalType.DATE)
	private Date dateCreate;
	@Column(columnDefinition = "integer default 0 not null")
	private int point;
	private int lastyearaddpoint;
	private String password;
	private int age;
	@JsonIgnore
	private boolean isConnected;
	@JsonIgnore
	private boolean viewAd;
	@JsonIgnore
    private String resetToken;
	 private int codeVerif;
	 private Boolean verified;
	
	@ManyToMany(fetch = FetchType.LAZY,cascade= CascadeType.ALL)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Roles> roles = new HashSet<>();
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Claim> claim;
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Post> post;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Donation> donation;
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<DiscountToken> discountToken;
	
	@ManyToMany(mappedBy="user",cascade=CascadeType.ALL)
	private Set<Event> event;

	@OneToOne(mappedBy = "user")
	private ShoppingCart shoppingcart;

	public User(long cin, String name, String address, String email, String numTel, int age) {
		super();
		this.cin = cin;
		this.name = name;
		this.address = address;
		this.email = email;
		this.numTel = numTel;
		this.age = age;
		

	}

	public User() {
		super();
	}

	public User(String name2, String email2, String encode, String address2, int age2, long cin2, String numTel2,String sex ) {
		this.name=name2;
		this.address=address2;
		this.age=age2;
		this.email=email2;
		this.numTel=numTel2;
		this.password=encode;
		this.cin=cin2;
		this.sex=sex;
	}
	
	
	public User(String name2, String email2, String encode, String address2, int age2, long cin2, String numTel2 ) {
		this.name=name2;
		this.address=address2;
		this.age=age2;
		this.email=email2;
		this.numTel=numTel2;
		this.password=encode;
		this.cin=cin2;
		
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getCin() {
		return cin;
	}

	public void setCin(long cin) {
		this.cin = cin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNumTel() {
		return numTel;
	}

	public void setNumTel(String numTel) {
		this.numTel = numTel;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isConnected() {
		return isConnected;
	}

	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}

	public boolean isViewAd() {
		return viewAd;
	}

	public void setViewAd(boolean viewAd) {
		this.viewAd = viewAd;
	}

	public Set<Roles> getRoles() {
		return roles;
	}

	public void setRoles(Set<Roles> roles) {
		this.roles = roles;
	}

	public List<Claim> getClaim() {
		return claim;
	}

	public void setClaim(List<Claim> claim) {
		this.claim = claim;
	}

	public List<Post> getPost() {
		return post;
	}

	public void setPost(List<Post> post) {
		this.post = post;
	}

	public List<Donation> getDonation() {
		return donation;
	}

	public void setDonation(List<Donation> donation) {
		this.donation = donation;
	}

	public List<DiscountToken> getDiscountToken() {
		return discountToken;
	}

	public void setDiscountToken(List<DiscountToken> discountToken) {
		this.discountToken = discountToken;
	}



	public Set<Event> getEvent() {
		return event;
	}

	public void setEvent(Set<Event> event) {
		this.event = event;
	}

	public ShoppingCart getShoppingcart() {
		return shoppingcart;
	}

	public void setShoppingcart(ShoppingCart shoppingcart) {
		this.shoppingcart = shoppingcart;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getResetToken() {
		return resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}



	public int getCounterLogin() {
		return counterLogin;
	}

	public void setCounterLogin(int counterLogin) {
		this.counterLogin = counterLogin;
	}

	public boolean isDesactivate() {
		return desactivate;
	}

	public void setDesactivate(boolean desactivate) {
		this.desactivate = desactivate;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Date getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public int getLastyearaddpoint() {
		return lastyearaddpoint;
	}

	public void setLastyearaddpoint(int lastyearaddpoint) {
		this.lastyearaddpoint = lastyearaddpoint;
	}

	public int getCodeVerif() {
		return codeVerif;
	}

	public void setCodeVerif(int codeVerif) {
		this.codeVerif = codeVerif;
	}

	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

	
	
}
