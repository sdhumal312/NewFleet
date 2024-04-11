/**
 * 
 */
package org.fleetopgroup.persistence.dto;

public class DriverSalaryReportDto {
	
	private String driverName;
	
	private  String driverLastName;
	
	private String driverFatherName;
	
	private String driver_empnumber;
	
	private Integer countSingleTrip;
	
	private Integer countRoundTrip;
	
	private Integer totalTripCount;
	
	private Double singleTripSalary;
	
	private Double roundTripSalary;
	
	private Double totalSalary;
	
	private Double netSalary;
	
	private Double da;
	
	private Double advance;
	
	private Double penalty;
	
	public DriverSalaryReportDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DriverSalaryReportDto(String driverName, String driverLastName, String driverFatherName,
			String driver_empnumber, Integer countSingleTrip, Integer countRoundTrip, Integer totalTripCount,
			Double singleTripSalary, Double roundTripSalary, Double totalSalary, Double netSalary, Double da,
			Double advance, Double penalty) {
		super();
		this.driverName = driverName;
		this.driverLastName = driverLastName;
		this.driverFatherName = driverFatherName;
		this.driver_empnumber = driver_empnumber;
		this.countSingleTrip = countSingleTrip;
		this.countRoundTrip = countRoundTrip;
		this.totalTripCount = totalTripCount;
		this.singleTripSalary = singleTripSalary;
		this.roundTripSalary = roundTripSalary;
		this.totalSalary = totalSalary;
		this.netSalary = netSalary;
		this.da = da;
		this.advance = advance;
		this.penalty = penalty;
	}


	public String getDriverName() {
		return driverName;
	}

	public String getDriverLastName() {
		return driverLastName;
	}

	public void setDriverLastName(String driverLastName) {
		this.driverLastName = driverLastName;
	}

	public String getDriverFatherName() {
		return driverFatherName;
	}

	public void setDriverFatherName(String driverFatherName) {
		this.driverFatherName = driverFatherName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriver_empnumber() {
		return driver_empnumber;
	}

	public void setDriver_empnumber(String driver_empnumber) {
		this.driver_empnumber = driver_empnumber;
	}

	public Integer getCountSingleTrip() {
		return countSingleTrip;
	}

	public void setCountSingleTrip(Integer countSingleTrip) {
		this.countSingleTrip = countSingleTrip;
	}

	public Integer getCountRoundTrip() {
		return countRoundTrip;
	}

	public void setCountRoundTrip(Integer countRoundTrip) {
		this.countRoundTrip = countRoundTrip;
	}

	public Integer getTotalTripCount() {
		return totalTripCount;
	}

	public void setTotalTripCount(Integer totalTripCount) {
		this.totalTripCount = totalTripCount;
	}

	public Double getSingleTripSalary() {
		return singleTripSalary;
	}

	public void setSingleTripSalary(Double singleTripSalary) {
		this.singleTripSalary = singleTripSalary;
	}

	public Double getRoundTripSalary() {
		return roundTripSalary;
	}

	public void setRoundTripSalary(Double roundTripSalary) {
		this.roundTripSalary = roundTripSalary;
	}

	public Double getTotalSalary() {
		return totalSalary;
	}

	public void setTotalSalary(Double totalSalary) {
		this.totalSalary = totalSalary;
	}

	public Double getNetSalary() {
		return netSalary;
	}

	public void setNetSalary(Double netSalary) {
		this.netSalary = netSalary;
	}

	public Double getDa() {
		return da;
	}

	public void setDa(Double da) {
		this.da = da;
	}

	public Double getAdvance() {
		return advance;
	}

	public void setAdvance(Double advance) {
		this.advance = advance;
	}

	public Double getPenalty() {
		return penalty;
	}

	public void setPenalty(Double penalty) {
		this.penalty = penalty;
	}

	@Override
	public String toString() {
		return "DriverSalaryReportDto [driverName=" + driverName + ", driverLastName=" + driverLastName
				+ ", driverFatherName=" + driverFatherName + ", driver_empnumber=" + driver_empnumber
				+ ", countSingleTrip=" + countSingleTrip + ", countRoundTrip=" + countRoundTrip + ", totalTripCount="
				+ totalTripCount + ", singleTripSalary=" + singleTripSalary + ", roundTripSalary=" + roundTripSalary
				+ ", totalSalary=" + totalSalary + ", netSalary=" + netSalary + ", da=" + da + ", advance=" + advance
				+ ", penalty=" + penalty + "]";
	}
	
}
