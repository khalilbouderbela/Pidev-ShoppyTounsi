package tn.esprit.pi.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Provider implements Serializable , Comparable<Provider>{

	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private long providerId;
	private String name;
	private String email;
	private int note;
	private float deleviryFees;
	private float seuilMontant;
	private int reductionPercentage;
	private Boolean Disponibility=true;
	@JsonIgnore
	@OneToMany(mappedBy = "provider",cascade=CascadeType.ALL)
	private List<Entry> entry;


	public Provider() {
		super();
	}
	public Provider(long id) {
		super();
		this.providerId=id;
	}

	public Provider(String name, String email, int note) {
		super();
		this.name = name;
		this.email = email;
		this.note = note;
	}
	
	

	public float getDeleviryFees() {
		return deleviryFees;
	}
	public void setDeleviryFees(float deleviryFees) {
		this.deleviryFees = deleviryFees;
	}
	public float getSeuilMontant() {
		return seuilMontant;
	}
	public void setSeuilMontant(float seuilMontant) {
		this.seuilMontant = seuilMontant;
	}
	public int getReductionPercentage() {
		return reductionPercentage;
	}
	public void setReductionPercentage(int reductionPercentage) {
		this.reductionPercentage = reductionPercentage;
	}
	public long getProviderId() {
		return providerId;
	}

	public void setProviderId(long providerId) {
		this.providerId = providerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getNote() {
		return note;
	}

	public void setNote(int note) {
		this.note = note;
	}

	public List<Entry> getEntry() {
		return entry;
	}

	public void setEntry(List<Entry> entry) {
		this.entry = entry;
	}
	public Boolean getDisponibility() {
		return Disponibility;
	}
	public void setDisponibility(Boolean disponibility) {
		Disponibility = disponibility;
	}
	@Override
	public int compareTo(Provider o) {
		if(this.getSeuilMontant()==o.getSeuilMontant())
		{
			return -(this.getReductionPercentage() - o.getReductionPercentage());
		}
		return (int) (this.getSeuilMontant()-o.getSeuilMontant());
	}


}
