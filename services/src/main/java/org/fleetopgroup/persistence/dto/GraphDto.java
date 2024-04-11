/**
 * 
 */
package org.fleetopgroup.persistence.dto;

import org.fleetopgroup.web.util.Utility;

/**
 * @author fleetop
 *
 */
public class GraphDto {

	public GraphDto() {
		super();
	}

	private Long id;
	private String name;
	private Double amount;
	private String amountString;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	
	
	/**
	 * @return the amountString
	 */
	public String getAmountString() {
		return amountString;
	}
	/**
	 * @param amountString the amountString to set
	 */
	public void setAmountString(String amountString) {
		this.amountString = amountString;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = Utility.round(amount, 2) ;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GraphDto [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", amount=");
		builder.append(amount);
		builder.append("]");
		return builder.toString();
	}
	
	
}
