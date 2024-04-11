package org.fleetopgroup.persistence.dto;


public class CountrylistDto{


	private Integer country_id;

	private String country_name;


	public CountrylistDto() {
		super();
	}


	public CountrylistDto(String country_name) {
		super();
		this.country_name = country_name;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((country_name == null) ? 0 : country_name.hashCode());
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
		CountrylistDto other = (CountrylistDto) obj;
		if (country_name == null) {
			if (other.country_name != null)
				return false;
		} else if (!country_name.equals(other.country_name))
			return false;
		return true;
	}


	public Integer getCountry_id() {
		return country_id;
	}


	public void setCountry_id(Integer country_id) {
		this.country_id = country_id;
	}


	public String getCountry_name() {
		return country_name;
	}


	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Countrylist [country_id=");
		builder.append(country_id);
		builder.append(", country_name=");
		builder.append(country_name);
		builder.append("]");
		return builder.toString();
	}

	
}
