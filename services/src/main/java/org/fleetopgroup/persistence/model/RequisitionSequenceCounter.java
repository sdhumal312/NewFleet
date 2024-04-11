package org.fleetopgroup.persistence.model;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RequisitionSequenceCounter")
public class RequisitionSequenceCounter  implements Serializable{
		private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "sequenceId")
		private long sequenceId;
		
		@Column(name = "companyId", nullable = false)
		private Integer companyId;
		
		@Column(name = "nextVal", nullable = false)
		private long	nextVal;
		
		@Column(name = "markForDelete")
		private boolean markForDelete;
		

		public long getSequenceId() {
			return sequenceId;
		}

		public Integer getCompanyId() {
			return companyId;
		}

		public long getNextVal() {
			return nextVal;
		}

		public boolean isMarkForDelete() {
			return markForDelete;
		}

		public void setSequence_Id(long sequenceId) {
			this.sequenceId = sequenceId;
		}

		public void setCompanyId(Integer companyId) {
			this.companyId = companyId;
		}

		public void setNextVal(long nextVal) {
			this.nextVal = nextVal;
		}

		public void setMarkForDelete(boolean markForDelete) {
			this.markForDelete = markForDelete;
		}
}
