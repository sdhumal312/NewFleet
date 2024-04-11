package org.fleetopgroup.persistence.document;

import java.io.Serializable;
import java.util.Arrays;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "VehicleExpenseDocument")
public class VehicleExpenseDocument implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		@Id
		private Long _id;

		private Long	vehicleExpensesId;
		
		private Long	accidentId;
		
		private String filename;

		private byte[] content;
		
		private String contentType;
		
		@Indexed(name = "companyId")
		private Integer companyId;

		public Long get_id() {
			return _id;
		}

		public void set_id(Long _id) {
			this._id = _id;
		}

		public Long getVehicleExpensesId() {
			return vehicleExpensesId;
		}

		public void setVehicleExpensesId(Long vehicleExpensesId) {
			this.vehicleExpensesId = vehicleExpensesId;
		}

		public Long getAccidentId() {
			return accidentId;
		}

		public void setAccidentId(Long accidentId) {
			this.accidentId = accidentId;
		}

		public String getFilename() {
			return filename;
		}

		public void setFilename(String filename) {
			this.filename = filename;
		}

		public byte[] getContent() {
			return content;
		}

		public void setContent(byte[] content) {
			this.content = content;
		}

		public String getContentType() {
			return contentType;
		}

		public void setContentType(String contentType) {
			this.contentType = contentType;
		}

		public Integer getCompanyId() {
			return companyId;
		}

		public void setCompanyId(Integer companyId) {
			this.companyId = companyId;
		}

		@Override
		public String toString() {
			return "VehicleExpenseDocument [_id=" + _id + ", vehicleExpensesId=" + vehicleExpensesId + ", accidentId="
					+ accidentId + ", filename=" + filename + ", content=" + Arrays.toString(content) + ", contentType="
					+ contentType + ", companyId=" + companyId + "]";
		}
		
		
}
