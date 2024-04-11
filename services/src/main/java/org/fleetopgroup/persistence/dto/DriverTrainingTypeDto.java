package org.fleetopgroup.persistence.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author fleetop
 *
 *
 *
 */
public class DriverTrainingTypeDto {
	
	
	private Integer dri_id;
	
	@NotNull
	@Size(min = 1)
	private String dri_TrainingType;

	
	
	
	public DriverTrainingTypeDto() {
		super();
	}
	
	

	public Integer getDri_id() {
		return dri_id;
	}

	public void setDri_id(Integer dri_id) {
		this.dri_id = dri_id;
	}

	public String getDri_TrainingType() {
		return dri_TrainingType;
	}

	public void setDri_TrainingType(String dri_TrainingType) {
		this.dri_TrainingType = dri_TrainingType;
	}



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DriverTrainingTypeDto [dri_TrainingType=");
		builder.append(dri_TrainingType);
		builder.append("]");
		return builder.toString();
	}


}
