package org.fleetopgroup.constant;

public class ConsumptionSummaryConstant {

	public static final short	DATE_TYPE_CREATION		= 1;
	public static final short	DATE_TYPE_TRANSACION	= 2;
	
	public static final String	DATE_TYPE_CREATION_NAME		= "Creation date";
	public static final String	DATE_TYPE_TRANSACION_NAME	= "Transaction date";
	
	public static final short	CONSUMPTION_TYPE_FUEL				= 1;
	public static final short	CONSUMPTION_TYPE_UREA				= 2;
	public static final short	CONSUMPTION_TYPE_UPHOLSTERY			= 3;
	public static final short	CONSUMPTION_TYPE_WO					= 4;
	public static final short	CONSUMPTION_TYPE_SE					= 5;
	public static final short	CONSUMPTION_TYPE_TYRE				= 6;
	public static final short	CONSUMPTION_TYPE_BATTERY			= 7;
	public static final short	CONSUMPTION_TYPE_REFRESHMENT		= 8;
	
	public static final String	CONSUMPTION_TYPE_NAME_FUEL				= "FUEL CONSUMPION";
	public static final String	CONSUMPTION_TYPE_NAME_UREA				= "UREA CONSUMPION";
	public static final String	CONSUMPTION_TYPE_NAME_UPHOLSTERY		= "UPHOLSTERY CONSUMPION";
	public static final String	CONSUMPTION_TYPE_NAME_WO				= "WO CONSUMPION";
	public static final String	CONSUMPTION_TYPE_NAME_SE				= "SE CONSUMPION";
	public static final String	CONSUMPTION_TYPE_NAME_TYRE				= "TYRE CONSUMPION";
	public static final String	CONSUMPTION_TYPE_NAME_BATTERY			= "BATTERY CONSUMPION";
	public static final String	CONSUMPTION_TYPE_NAME_REFRESHMENT		= "REFRESHMENT CONSUMPION";
	
	
	
	public static String getDateType(short dateTypeId) throws Exception {
		String dateType = null;
		switch (dateTypeId) {
		case CONSUMPTION_TYPE_FUEL:
			dateType = CONSUMPTION_TYPE_NAME_FUEL;
			break;
		case CONSUMPTION_TYPE_UREA:
			dateType = CONSUMPTION_TYPE_NAME_UREA;
			break;
		case CONSUMPTION_TYPE_UPHOLSTERY:
			dateType = CONSUMPTION_TYPE_NAME_UPHOLSTERY;
			break;
		case CONSUMPTION_TYPE_WO:
			dateType = CONSUMPTION_TYPE_NAME_WO;
			break;
		case CONSUMPTION_TYPE_SE:
			dateType = CONSUMPTION_TYPE_NAME_SE;
			break;
		case CONSUMPTION_TYPE_TYRE:
			dateType = CONSUMPTION_TYPE_NAME_TYRE;
			break;
		case CONSUMPTION_TYPE_BATTERY:
			dateType = CONSUMPTION_TYPE_NAME_BATTERY;
			break;
		case CONSUMPTION_TYPE_REFRESHMENT:
			dateType = CONSUMPTION_TYPE_NAME_REFRESHMENT;
			break;
		default:
			dateType = "--";
			break;
		}
		return dateType;
	}
	
	public static String getConsumptionType(short consumptionTypeId) throws Exception {
		String consumptionType = null;
		switch (consumptionTypeId) {
		case DATE_TYPE_CREATION:
			consumptionType = DATE_TYPE_CREATION_NAME;
			break;
		case DATE_TYPE_TRANSACION:
			consumptionType = DATE_TYPE_TRANSACION_NAME;
			break;
		default:
			consumptionType = "--";
			break;
		}
		return consumptionType;
	}
}
