package org.fleetopgroup.persistence.dto;

public class CountryStatelistDto {

	private Integer countrystate_id;

	private String country_name;

	private String countrystate_name;

	public CountryStatelistDto() {
		super();
	}

	public CountryStatelistDto(String country_name, String countrystate_name) {
		super();
		this.country_name = country_name;
		this.countrystate_name = countrystate_name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((countrystate_id == null) ? 0 : countrystate_id.hashCode());
		result = prime * result + ((countrystate_name == null) ? 0 : countrystate_name.hashCode());
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
		CountryStatelistDto other = (CountryStatelistDto) obj;
		if (countrystate_id == null) {
			if (other.countrystate_id != null)
				return false;
		} else if (!countrystate_id.equals(other.countrystate_id))
			return false;
		if (countrystate_name == null) {
			if (other.countrystate_name != null)
				return false;
		} else if (!countrystate_name.equals(other.countrystate_name))
			return false;
		return true;
	}

	public Integer getCountrystate_id() {
		return countrystate_id;
	}

	public void setCountrystate_id(Integer countrystate_id) {
		this.countrystate_id = countrystate_id;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CountryStatelist [countrystate_id=");
		builder.append(countrystate_id);
		builder.append(", country_name=");
		builder.append(country_name);
		builder.append(", countrystate_name=");
		builder.append(countrystate_name);
		builder.append("]");
		return builder.toString();
	}

}
