package org.fleetopgroup.persistence.dto;

public class RoleCountDto {

	private Long id;

	private String name;

	private Long count_user;

	public RoleCountDto() {
		super();
	}

	public RoleCountDto(Long id, String name, Long count_user) {
		super();
		this.id = id;
		this.name = name;
		this.count_user = count_user;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the count_user
	 */
	public Long getCount_user() {
		return count_user;
	}

	/**
	 * @param count_user
	 *            the count_user to set
	 */
	public void setCount_user(Long count_user) {
		this.count_user = count_user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RoleDto [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", count_user=");
		builder.append(count_user);
		builder.append("]");
		return builder.toString();
	}

}