package tn.esprit.pi.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Logg implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String tableName;
	private String actionName;
	private long userId;
	@Temporal(TemporalType.DATE)
	private Date dateAction;
	
	public Date getDateAction() {
		return dateAction;
	}
	public void setDateAction(Date dateAction) {
		this.dateAction = dateAction;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Logg(long id, String tableName, String actionName, long userId) {
		super();
		this.id = id;
		this.tableName = tableName;
		this.actionName = actionName;
		this.userId = userId;
	}
	public Logg() {
		super();
	}
	
	
}
