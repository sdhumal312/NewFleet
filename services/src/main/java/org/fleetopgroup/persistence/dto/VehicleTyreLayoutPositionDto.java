package org.fleetopgroup.persistence.dto;

import org.fleetopgroup.web.util.Utility;

public class VehicleTyreLayoutPositionDto {

	private Long LP_ID;
	
	private Integer VEHICLE_ID;

	private Integer AXLE;

	private String POSITION;
	
	private String TYRE_SIZE;
	
	private Integer TYRE_SIZE_ID;

	private String TYRE_PRESSURE;

	private Long TYRE_ID;

	private String TYRE_SERIAL_NO;

	private boolean TYRE_ASSIGNED = false;
	
	private Integer COMPANY_ID;
	
	private short	TYRE_ASSIGN_STATUS_ID;
	
	private Integer OPEN_ODOMETER;

	private Integer CLOSE_ODOMETER;

	private Integer TYRE_USEAGE;
	
	private Integer VEHICLE_ODOMETER;

	private Double	costPerKM;
	
	private boolean markForDelete;
	
	private String tyreModel;
	
	private short tyreTypeId;
	
	private String tyreType;
	
	private Long oldTyreId;
	
	private String oldTyre;
	
	private short oldTyreMoveId;
	
	private String oldTyreMove;
	
	private short alignment;
	
	private String alignmentStr;
	
	private short kinpin;
	
	private String kinpinStr;
	
	private short assignFromId;
	
	private String assignFrom;
	
	private short tyreModelTypeId;
	
	private String tyreModelType;
	
	private Integer tyreGauge;
	
	private Integer gauageMeasurementLine;
	
	private short tyreTubeTypeId;
	
	private String tyreTubeType;
	
	private Integer ply;
	
	private Integer psi;
	
	private Integer tyreModelSizeId;
	
	private String tyreModelSize;

	private Integer tyreModelId;
	
	private String mountDateStr;
	
	private String dismountDateStr;

	private short transactionTypeId;
	
	private Long transactionId;
	
	private Long transactionSubId;
	
	private String vehicleRegistration;

	private Long vehicleModelId;

	/**
	 * @return the vEHICLE_ID
	 */
	public Integer getVEHICLE_ID() {
		return VEHICLE_ID;
	}

	/**
	 * @param vEHICLE_ID the vEHICLE_ID to set
	 */
	public void setVEHICLE_ID(Integer vEHICLE_ID) {
		VEHICLE_ID = vEHICLE_ID;
	}

	/**
	 * @return the lP_ID
	 */
	public Long getLP_ID() {
		return LP_ID;
	}

	/**
	 * @param lP_ID
	 *            the lP_ID to set
	 */
	public void setLP_ID(Long lP_ID) {
		LP_ID = lP_ID;
	}

	/**
	 * @return the aXLE
	 */
	public Integer getAXLE() {
		return AXLE;
	}

	/**
	 * @param aXLE
	 *            the aXLE to set
	 */
	public void setAXLE(Integer aXLE) {
		AXLE = aXLE;
	}

	/**
	 * @return the pOSITION
	 */
	public String getPOSITION() {
		return POSITION;
	}

	/**
	 * @param pOSITION
	 *            the pOSITION to set
	 */
	public void setPOSITION(String pOSITION) {
		POSITION = pOSITION;
	}

	/**
	 * @return the tYRE_ID
	 */
	public Long getTYRE_ID() {
		return TYRE_ID;
	}

	/**
	 * @param tYRE_ID
	 *            the tYRE_ID to set
	 */
	public void setTYRE_ID(Long tYRE_ID) {
		TYRE_ID = tYRE_ID;
	}

	/**
	 * @return the tYRE_SERIAL_NO
	 */
	public String getTYRE_SERIAL_NO() {
		return TYRE_SERIAL_NO;
	}

	/**
	 * @param tYRE_SERIAL_NO
	 *            the tYRE_SERIAL_NO to set
	 */
	public void setTYRE_SERIAL_NO(String tYRE_SERIAL_NO) {
		TYRE_SERIAL_NO = tYRE_SERIAL_NO;
	}

	/**
	 * @return the tYRE_ASSIGNED
	 */
	public boolean isTYRE_ASSIGNED() {
		return TYRE_ASSIGNED;
	}

	/**
	 * @param tYRE_ASSIGNED
	 *            the tYRE_ASSIGNED to set
	 */
	public void setTYRE_ASSIGNED(boolean tYRE_ASSIGNED) {
		TYRE_ASSIGNED = tYRE_ASSIGNED;
	}

	
	/**
	 * @return the tYRE_SIZE
	 */
	public String getTYRE_SIZE() {
		return TYRE_SIZE;
	}

	/**
	 * @param tYRE_SIZE the tYRE_SIZE to set
	 */
	public void setTYRE_SIZE(String tYRE_SIZE) {
		TYRE_SIZE = tYRE_SIZE;
	}

	/**
	 * @return the tYRE_PRESSURE
	 */
	public String getTYRE_PRESSURE() {
		return TYRE_PRESSURE;
	}

	/**
	 * @param tYRE_PRESSURE the tYRE_PRESSURE to set
	 */
	public void setTYRE_PRESSURE(String tYRE_PRESSURE) {
		TYRE_PRESSURE = tYRE_PRESSURE;
	}

	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}


	/**
	 * @return the tYRE_SIZE_ID
	 */
	public Integer getTYRE_SIZE_ID() {
		return TYRE_SIZE_ID;
	}

	/**
	 * @param tYRE_SIZE_ID the tYRE_SIZE_ID to set
	 */
	public void setTYRE_SIZE_ID(Integer tYRE_SIZE_ID) {
		TYRE_SIZE_ID = tYRE_SIZE_ID;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	
	public short getTYRE_ASSIGN_STATUS_ID() {
		return TYRE_ASSIGN_STATUS_ID;
	}

	public void setTYRE_ASSIGN_STATUS_ID(short tYRE_ASSIGN_STATUS_ID) {
		TYRE_ASSIGN_STATUS_ID = tYRE_ASSIGN_STATUS_ID;
	}

	public Integer getOPEN_ODOMETER() {
		return OPEN_ODOMETER;
	}

	public void setOPEN_ODOMETER(Integer oPEN_ODOMETER) {
		OPEN_ODOMETER = oPEN_ODOMETER;
	}

	public Integer getCLOSE_ODOMETER() {
		return CLOSE_ODOMETER;
	}

	public void setCLOSE_ODOMETER(Integer cLOSE_ODOMETER) {
		CLOSE_ODOMETER = cLOSE_ODOMETER;
	}

	public Integer getTYRE_USEAGE() {
		return TYRE_USEAGE;
	}

	public void setTYRE_USEAGE(Integer tYRE_USEAGE) {
		TYRE_USEAGE = tYRE_USEAGE;
	}

	public Integer getVEHICLE_ODOMETER() {
		return VEHICLE_ODOMETER;
	}

	public void setVEHICLE_ODOMETER(Integer vEHICLE_ODOMETER) {
		VEHICLE_ODOMETER = vEHICLE_ODOMETER;
	}

	public Double getCostPerKM() {
		return costPerKM;
	}

	public void setCostPerKM(Double costPerKM) {
		this.costPerKM = Utility.round(costPerKM, 2);
	}
	

	public short getTyreTypeId() {
		return tyreTypeId;
	}

	public void setTyreTypeId(short tyreTypeId) {
		this.tyreTypeId = tyreTypeId;
	}

	public String getTyreType() {
		return tyreType;
	}

	public void setTyreType(String tyreType) {
		this.tyreType = tyreType;
	}

	public Long getOldTyreId() {
		return oldTyreId;
	}

	public void setOldTyreId(Long oldTyreId) {
		this.oldTyreId = oldTyreId;
	}

	public String getOldTyre() {
		return oldTyre;
	}

	public void setOldTyre(String oldTyre) {
		this.oldTyre = oldTyre;
	}

	public short getOldTyreMoveId() {
		return oldTyreMoveId;
	}

	public void setOldTyreMoveId(short oldTyreMoveId) {
		this.oldTyreMoveId = oldTyreMoveId;
	}

	public String getOldTyreMove() {
		return oldTyreMove;
	}

	public void setOldTyreMove(String oldTyreMove) {
		this.oldTyreMove = oldTyreMove;
	}

	public short getAlignment() {
		return alignment;
	}

	public void setAlignment(short alignment) {
		this.alignment = alignment;
	}

	public String getAlignmentStr() {
		return alignmentStr;
	}

	public void setAlignmentStr(String alignmentStr) {
		this.alignmentStr = alignmentStr;
	}

	public short getKinpin() {
		return kinpin;
	}

	public void setKinpin(short kinpin) {
		this.kinpin = kinpin;
	}

	public String getKinpinStr() {
		return kinpinStr;
	}

	public void setKinpinStr(String kinpinStr) {
		this.kinpinStr = kinpinStr;
	}

	public short getAssignFromId() {
		return assignFromId;
	}

	public void setAssignFromId(short assignFromId) {
		this.assignFromId = assignFromId;
	}

	public String getAssignFrom() {
		return assignFrom;
	}

	public void setAssignFrom(String assignFrom) {
		this.assignFrom = assignFrom;
	}

	public short getTyreModelTypeId() {
		return tyreModelTypeId;
	}

	public void setTyreModelTypeId(short tyreModelTypeId) {
		this.tyreModelTypeId = tyreModelTypeId;
	}

	public String getTyreModelType() {
		return tyreModelType;
	}

	public void setTyreModelType(String tyreModelType) {
		this.tyreModelType = tyreModelType;
	}

	public Integer getTyreGauge() {
		return tyreGauge;
	}

	public void setTyreGauge(Integer tyreGauge) {
		this.tyreGauge = tyreGauge;
	}

	public Integer getGauageMeasurementLine() {
		return gauageMeasurementLine;
	}

	public void setGauageMeasurementLine(Integer gauageMeasurementLine) {
		this.gauageMeasurementLine = gauageMeasurementLine;
	}

	public short getTyreTubeTypeId() {
		return tyreTubeTypeId;
	}

	public void setTyreTubeTypeId(short tyreTubeTypeId) {
		this.tyreTubeTypeId = tyreTubeTypeId;
	}

	public String getTyreTubeType() {
		return tyreTubeType;
	}

	public void setTyreTubeType(String tyreTubeType) {
		this.tyreTubeType = tyreTubeType;
	}

	public Integer getPly() {
		return ply;
	}

	public void setPly(Integer ply) {
		this.ply = ply;
	}

	public Integer getPsi() {
		return psi;
	}

	public void setPsi(Integer psi) {
		this.psi = psi;
	}

	public Integer getTyreModelSizeId() {
		return tyreModelSizeId;
	}

	public void setTyreModelSizeId(Integer tyreModelSizeId) {
		this.tyreModelSizeId = tyreModelSizeId;
	}

	public String getTyreModelSize() {
		return tyreModelSize;
	}

	public void setTyreModelSize(String tyreModelSize) {
		this.tyreModelSize = tyreModelSize;
	}
	

	public Integer getTyreModelId() {
		return tyreModelId;
	}

	public void setTyreModelId(Integer tyreModelId) {
		this.tyreModelId = tyreModelId;
	}

	public String getTyreModel() {
		return tyreModel;
	}

	public void setTyreModel(String tyreModel) {
		this.tyreModel = tyreModel;
	}
	
	public String getMountDateStr() {
		return mountDateStr;
	}

	public void setMountDateStr(String mountDateStr) {
		this.mountDateStr = mountDateStr;
	}

	public String getDismountDateStr() {
		return dismountDateStr;
	}

	public void setDismountDateStr(String dismountDateStr) {
		this.dismountDateStr = dismountDateStr;
	}
	
	public short getTransactionTypeId() {
		return transactionTypeId;
	}

	public void setTransactionTypeId(short transactionTypeId) {
		this.transactionTypeId = transactionTypeId;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}
	
	public Long getTransactionSubId() {
		return transactionSubId;
	}

	public void setTransactionSubId(Long transactionSubId) {
		this.transactionSubId = transactionSubId;
	}

	public String getVehicleRegistration() {
		return vehicleRegistration;
	}

	public void setVehicleRegistration(String vehicleRegistration) {
		this.vehicleRegistration = vehicleRegistration;
	}

	@Override
	public String toString() {
		return "VehicleTyreLayoutPositionDto [LP_ID=" + LP_ID + ", VEHICLE_ID=" + VEHICLE_ID + ", AXLE=" + AXLE
				+ ", POSITION=" + POSITION + ", TYRE_SIZE=" + TYRE_SIZE + ", TYRE_SIZE_ID=" + TYRE_SIZE_ID
				+ ", TYRE_PRESSURE=" + TYRE_PRESSURE + ", TYRE_ID=" + TYRE_ID + ", TYRE_SERIAL_NO=" + TYRE_SERIAL_NO
				+ ", TYRE_ASSIGNED=" + TYRE_ASSIGNED + ", COMPANY_ID=" + COMPANY_ID + ", TYRE_ASSIGN_STATUS_ID="
				+ TYRE_ASSIGN_STATUS_ID + ", OPEN_ODOMETER=" + OPEN_ODOMETER + ", CLOSE_ODOMETER=" + CLOSE_ODOMETER
				+ ", TYRE_USEAGE=" + TYRE_USEAGE + ", VEHICLE_ODOMETER=" + VEHICLE_ODOMETER + ", costPerKM=" + costPerKM
				+ ", markForDelete=" + markForDelete + ", tyreModel=" + tyreModel + ", tyreTypeId=" + tyreTypeId
				+ ", tyreType=" + tyreType + ", oldTyreId=" + oldTyreId + ", oldTyre=" + oldTyre + ", oldTyreMoveId="
				+ oldTyreMoveId + ", oldTyreMove=" + oldTyreMove + ", alignment=" + alignment + ", alignmentStr="
				+ alignmentStr + ", kinpin=" + kinpin + ", kinpinStr=" + kinpinStr + ", assignFromId=" + assignFromId
				+ ", assignFrom=" + assignFrom + ", tyreModelTypeId=" + tyreModelTypeId + ", tyreModelType="
				+ tyreModelType + ", tyreGauge=" + tyreGauge + ", gauageMeasurementLine=" + gauageMeasurementLine
				+ ", tyreTubeTypeId=" + tyreTubeTypeId + ", tyreTubeType=" + tyreTubeType + ", ply=" + ply + ", psi="
				+ psi + ", tyreModelSizeId=" + tyreModelSizeId + ", tyreModelSize=" + tyreModelSize + ", tyreModelId="
				+ tyreModelId + ", mountDateStr=" + mountDateStr + ", dismountDateStr=" + dismountDateStr
				+ ", transactionTypeId=" + transactionTypeId + ", transactionId=" + transactionId
				+ ", transactionSubId=" + transactionSubId + ", vehicleRegistration=" + vehicleRegistration + "]";
	}

	public Long getVehicleModelId() {
		return vehicleModelId;
	}

	public void setVehicleModelId(Long vehicleModelId) {
		this.vehicleModelId = vehicleModelId;
	}




	

	
	

}
