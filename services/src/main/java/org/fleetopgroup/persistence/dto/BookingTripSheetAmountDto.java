/**
 * 
 */
package org.fleetopgroup.persistence.dto;

import org.fleetopgroup.web.util.Utility;

/**
 * @author fleetop
 *
 */
public class BookingTripSheetAmountDto {

	private Long BOOKAMOUNTID;

	/** The value party name */
	private Long BOOKID;

	/** The value NAMEOFDBATA name */
	private String NAMEOFBATA;

	/** The value AMOUNT OF no */
	private Double BTS_AMOUNT;

	/** The value DAYS no */
	private Double BTS_DAYS;

	/** The value AMOUNT TOTAL no */
	private Double BTS_AMOUNT_TOTAL;

	public BookingTripSheetAmountDto() {
		super();
	}

	public BookingTripSheetAmountDto(Long bOOKAMOUNTID, Long bOOKID, String nAMEOFBATA, Double bTS_AMOUNT,
			Double bTS_DAYS, Double bTS_AMOUNT_TOTAL) {
		super();
		BOOKAMOUNTID = bOOKAMOUNTID;
		BOOKID = bOOKID;
		NAMEOFBATA = nAMEOFBATA;
		BTS_AMOUNT = bTS_AMOUNT;
		BTS_DAYS = bTS_DAYS;
		BTS_AMOUNT_TOTAL = bTS_AMOUNT_TOTAL;
	}

	/**
	 * @return the bOOKAMOUNTID
	 */
	public Long getBOOKAMOUNTID() {
		return BOOKAMOUNTID;
	}

	/**
	 * @param bOOKAMOUNTID
	 *            the bOOKAMOUNTID to set
	 */
	public void setBOOKAMOUNTID(Long bOOKAMOUNTID) {
		BOOKAMOUNTID = bOOKAMOUNTID;
	}

	/**
	 * @return the bOOKID
	 */
	public Long getBOOKID() {
		return BOOKID;
	}

	/**
	 * @param bOOKID
	 *            the bOOKID to set
	 */
	public void setBOOKID(Long bOOKID) {
		BOOKID = bOOKID;
	}

	/**
	 * @return the nAMEOFBATA
	 */
	public String getNAMEOFBATA() {
		return NAMEOFBATA;
	}

	/**
	 * @param nAMEOFBATA
	 *            the nAMEOFBATA to set
	 */
	public void setNAMEOFBATA(String nAMEOFBATA) {
		NAMEOFBATA = nAMEOFBATA;
	}

	/**
	 * @return the bTS_AMOUNT
	 */
	public Double getBTS_AMOUNT() {
		return BTS_AMOUNT;
	}

	/**
	 * @param bTS_AMOUNT
	 *            the bTS_AMOUNT to set
	 */
	public void setBTS_AMOUNT(Double bTS_AMOUNT) {
		BTS_AMOUNT = Utility.round(bTS_AMOUNT, 2);
	}

	/**
	 * @return the bTS_DAYS
	 */
	public Double getBTS_DAYS() {
		return BTS_DAYS;
	}

	/**
	 * @param bTS_DAYS
	 *            the bTS_DAYS to set
	 */
	public void setBTS_DAYS(Double bTS_DAYS) {
		BTS_DAYS = Utility.round(bTS_DAYS, 2);
	}

	/**
	 * @return the bTS_AMOUNT_TOTAL
	 */
	public Double getBTS_AMOUNT_TOTAL() {
		return BTS_AMOUNT_TOTAL;
	}

	/**
	 * @param bTS_AMOUNT_TOTAL
	 *            the bTS_AMOUNT_TOTAL to set
	 */
	public void setBTS_AMOUNT_TOTAL(Double bTS_AMOUNT_TOTAL) {
		BTS_AMOUNT_TOTAL = Utility.round(bTS_AMOUNT_TOTAL, 2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((BOOKAMOUNTID == null) ? 0 : BOOKAMOUNTID.hashCode());
		result = prime * result + ((BOOKID == null) ? 0 : BOOKID.hashCode());
		result = prime * result + ((BTS_AMOUNT_TOTAL == null) ? 0 : BTS_AMOUNT_TOTAL.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookingTripSheetAmountDto other = (BookingTripSheetAmountDto) obj;
		if (BOOKAMOUNTID == null) {
			if (other.BOOKAMOUNTID != null)
				return false;
		} else if (!BOOKAMOUNTID.equals(other.BOOKAMOUNTID))
			return false;
		if (BOOKID == null) {
			if (other.BOOKID != null)
				return false;
		} else if (!BOOKID.equals(other.BOOKID))
			return false;
		if (BTS_AMOUNT_TOTAL == null) {
			if (other.BTS_AMOUNT_TOTAL != null)
				return false;
		} else if (!BTS_AMOUNT_TOTAL.equals(other.BTS_AMOUNT_TOTAL))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BookingTripSheetAmount [BOOKAMOUNTID=");
		builder.append(BOOKAMOUNTID);
		builder.append(", BOOKID=");
		builder.append(BOOKID);
		builder.append(", NAMEOFBATA=");
		builder.append(NAMEOFBATA);
		builder.append(", BTS_AMOUNT=");
		builder.append(BTS_AMOUNT);
		builder.append(", BTS_DAYS=");
		builder.append(BTS_DAYS);
		builder.append(", BTS_AMOUNT_TOTAL=");
		builder.append(BTS_AMOUNT_TOTAL);
		builder.append("]");
		return builder.toString();
	}
}
