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
public class MailHistory implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long MailHistoryId;
	private String distination ;
	private String body ;
	private String type ;
	@Temporal(TemporalType.DATE)
	private Date sendDate;
	
	public long getMailHistoryId() {
		return MailHistoryId;
	}
	public void setMailHistoryId(long mailHistoryId) {
		MailHistoryId = mailHistoryId;
	}
	public String getDistination() {
		return distination;
	}
	public void setDistination(String distination) {
		this.distination = distination;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	
	

}
