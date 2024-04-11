package org.fleetopgroup.persistence.dto;


public class CountryStateCitylistDto{

	private Integer countrystatecity_id;
	
	private String country_name;

	private String countrystate_name;

	private String countrystatecity_name;
	
	

	public CountryStateCitylistDto() {
		super();
	}



	public CountryStateCitylistDto(String country_name, String countrystate_name, String countrystatecity_name) {
		super();
		this.country_name = country_name;
		this.countrystate_name = countrystate_name;
		this.countrystatecity_name = countrystatecity_name;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((country_name == null) ? 0 : country_name.hashCode());
		result = prime * result + ((countrystate_name == null) ? 0 : countrystate_name.hashCode());
		result = prime * result + ((countrystatecity_name == null) ? 0 : countrystatecity_name.hashCode());
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
		CountryStateCitylistDto other = (CountryStateCitylistDto) obj;
		if (country_name == null) {
			if (other.country_name != null)
				return false;
		} else if (!country_name.equals(other.country_name))
			return false;
		if (countrystate_name == null) {
			if (other.countrystate_name != null)
				return false;
		} else if (!countrystate_name.equals(other.countrystate_name))
			return false;
		if (countrystatecity_name == null) {
			if (other.countrystatecity_name != null)
				return false;
		} else if (!countrystatecity_name.equals(other.countrystatecity_name))
			return false;
		return true;
	}



	public Integer getCountrystatecity_id() {
		return countrystatecity_id;
	}



	public void setCountrystatecity_id(Integer countrystatecity_id) {
		this.countrystatecity_id = countrystatecity_id;
	}



	public String getCountry_name() {
		return country_name;
	}



	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}



	public String getCountrystate_name() {
		return countrystate_name;
	}



	public void setCountrystate_name(String countrystate_name) {
		this.countrystate_name = countrystate_name;
	}



	public String getCountrystatecity_name() {
		return countrystatecity_name;
	}



	public void setCountrystatecity_name(String countrystatecity_name) {
		this.countrystatecity_name = countrystatecity_name;
	}



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CountryStateCitylist [countrystatecity_id=");
		builder.append(countrystatecity_id);
		builder.append(", country_name=");
		builder.append(country_name);
		builder.append(", countrystate_name=");
		builder.append(countrystate_name);
		builder.append(", countrystatecity_name=");
		builder.append(countrystatecity_name);
		builder.append("]");
		return builder.toString();
	}

}
