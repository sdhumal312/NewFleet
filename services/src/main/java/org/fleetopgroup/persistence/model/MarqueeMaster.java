package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "MarqueeMaster")
public class MarqueeMaster implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "marquee_id")
	private Long marquee_id;
	
	@Column(name = "marquee_message", length = 150)
	private String marquee_message;

	@Column(name = "companyId", nullable = false)
	private Integer companyId;

	@Column(name = "createdById", updatable = false)
	private Long createdById;
	
	@Column(name = "lastModifiedById")
	private Long lastModifiedById;

	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;
	
	

	/** The value for the created DATE field */
	@Basic(optional = false)
	@Column(name = "createdDate", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	/** The value for the lastUpdated DATE field */
	@Basic(optional = false)
	@Column(name = "lastupdated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastupdated;

	@Override
	public String toString() {
		return "MarqueeMaster [marquee_id=" + marquee_id + ", marquee_message=" + marquee_message + ", companyId="
				+ companyId + ", createdById=" + createdById + ", lastModifiedById=" + lastModifiedById
				+ ", markForDelete=" + markForDelete + ", createdDate=" + createdDate + ", lastupdated=" + lastupdated
				+ "]";
	}

	public MarqueeMaster() {
		super();
	}

	public MarqueeMaster(Long marquee_id,String marquee_message,Integer companyId,Long createdById,Date createdDate,Long lastModifiedById, Date lastupdated, boolean markForDelete ) {
		super();
		this.marquee_id = marquee_id;
		this.marquee_message = marquee_message;
		this.companyId = companyId;
		this.createdById = createdById;
		this.createdDate = createdDate;
		this.lastModifiedById = lastModifiedById;
		this.lastupdated = lastupdated;
		this.markForDelete = markForDelete;
		
	}
	
	
	

	public Long getMarquee_id() {
		return marquee_id;
	}

	public void setMarquee_id(Long marquee_id) {
		this.marquee_id = marquee_id;
	}

	public String getMarquee_message() {
		return marquee_message;
	}

	public void setMarquee_message(String marquee_message) {
		this.marquee_message = marquee_message;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastupdated() {
		return lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/*@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((inventory_all_id == null) ? 0 : inventory_all_id.hashCode());
		result = prime * result + ((inventory_id == null) ? 0 : inventory_id.hashCode());
		result = prime * result + ((invoice_date == null) ? 0 : invoice_date.hashCode());
		result = prime * result + ((invoice_number == null) ? 0 : invoice_number.hashCode());
		return result;
	}*/

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MarqueeMaster other = (MarqueeMaster) obj;
		if (marquee_id == null) {
			if (other.marquee_id != null)
				return false;
		} else if (!marquee_id.equals(other.marquee_id))
			return false;
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!lastupdated.equals(other.lastupdated))
			return false;
		
		return true;
	}

	

}