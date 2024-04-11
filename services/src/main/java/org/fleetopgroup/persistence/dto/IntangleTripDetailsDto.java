package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;

public class IntangleTripDetailsDto {

	private Integer vid;

	private String plate;
	
	private String health;
	
	private Timestamp last_update;
	
	private String last_update_Str;

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public String getHealth() {
		return health;
	}

	public void setHealth(String health) {
		this.health = health;
	}

	public Timestamp getLast_update() {
		return last_update;
	}

	public void setLast_update(Timestamp last_update) {
		this.last_update = last_update;
	}

	public String getLast_update_Str() {
		return last_update_Str;
	}

	public void setLast_update_Str(String last_update_Str) {
		this.last_update_Str = last_update_Str;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

}
