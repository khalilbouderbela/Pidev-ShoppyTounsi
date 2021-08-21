package tn.esprit.pi.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class ClaimPK implements Serializable {

	private static final long serialVersionUID = 1L;
	private long productId;
	private long userId;
	@Temporal(TemporalType.DATE)
	private Date claimDate;

	public ClaimPK() {
		super();
	}

	public ClaimPK(long productId, long userId, Date claimDate) {
		super();
		this.productId = productId;
		this.userId = userId;
		this.claimDate = claimDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((claimDate == null) ? 0 : claimDate.hashCode());

		result = (int) (prime * result + productId);
		result = (int) (prime * result + userId);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClaimPK other = (ClaimPK) obj;
		if (claimDate == null) {
			if (other.claimDate != null)
				return false;
		} else if (!claimDate.equals(other.claimDate))
			return false;
		if (productId != other.productId)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Date getClaimDate() {
		return claimDate;
	}

	public void setClaimDate(Date claimDate) {
		this.claimDate = claimDate;
	}

	

}
