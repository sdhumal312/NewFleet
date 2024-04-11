package org.fleetopgroup.constant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class LhpvChargeTypeMaster implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final short WARAI							=	1;
	public static final short COMMISSION					=	2;
	public static final short CROSSING						=	3;
	public static final short LORRY_HIRE					=	4;
	public static final short ADVANCE_AMOUNT				=	5;
	public static final short BALANCE_AMOUNT				=	6;
	public static final short REFUND_AMOUNT					=	7;
	public static final short UNLOADING						=	8;
	public static final short RATE_PMT						=	9;
	public static final short DETENTION						=	10;
	public static final short OTHER_ADDITIONAL				=	11;
	public static final short DEDUCTION						=	12;
	public static final short TOPAY_RECEIVED				=	13;
	public static final short CREDIT_HAMALI					=	14;
	public static final short CLAIM							=	15;
	public static final short COLLECTION					=	16;
	public static final short TDS							=	17;
	public static final short DELIVERY_CHARGE				=	18;
	public static final short PENALTY						=	19;
	public static final short ACTUAL_BALANCE				=	20;
	public static final short ACTUAL_REFUND					=	21;
	public static final short LABOUR						=	22;
	public static final short DETENTION_AT_LOADING_POINT	=	23;
	public static final short DETENTION_AT_UNLOADING_POINT	=	24;
	public static final short ADDITIONAL					=	25;
	public static final short HAMALI_DEDUCT					=	26;
	public static final short HAMALI_GIVEN					=	27;
	public static final short EXT_LHC						=	28;
	public static final short EXT_DD						=	29;
	public static final short DIRECT_DELIVERY_AMOUNT		=	30;
	public static final short DELIVERY_COMMISSION			=	31;
	public static final short LATE_DATE						=	32;
	public static final short OH							=	33;
	public static final short OL							=	34;
	public static final short LC							=	35;
	public static final short CROSSING_HAMALI				=	36;
	public static final short CROSSING_LH					=	37;
	public static final short DOOR_DELIVERY					=	38;
	public static final short CARTAGE						=	39;
	public static final short FUEL_CHARGE					=	40;
	public static final short CO_LH							=	41;
	public static final short LOCAL_CHARGE					=	42;
	public static final short OTHER_NEGATIVE				=	43;
	public static final short BORDER_EXPENSES				=	44;
	public static final short POLICE_CHALLANS				=	45;
	public static final short BYPASS						=	46;
	public static final short OVER_LOAD						=	47;
	public static final short TOLL_TAX						=	48;
	public static final short SALES_TAX						=	49;
	public static final short CHECK_POST					=	50;
	public static final short INAM							=	51;
	public static final short OLD_ADVANCE_BALANCE			=	52;
	public static final short OLD_DAMAGE_CHARGES			=	53;
	public static final short THAPI							=	54;
	public static final short PAID							=	55;
	public static final short SERVICE_TAX					=	56;
	public static final short REFUND						=	57;
	public static final short MISC							=	58;
	public static final short PAID_CREDIT					=	59;
	public static final short DRIVER_COLLECTION				=	60;
	public static final short ADDITIONAL_TRUCK_ADVANCE		=	61;
	public static final short OVER_RUN						=	62;
	public static final short DELIVERY_CANCELLATION			=	63;
	
	

	public static final String WARAI_STRING                       	= "WARAI";
	public static final String COMMISSION_STRING                 	= "COMMISSION";
	public static final String CROSSING_STRING                      = "CROSSING";
	public static final String LORRY_HIRE_STRING                	= "LORRY_HIRE";
	public static final String ADVANCE_AMOUNT_STRING				= "ADVANCE_AMOUNT";
	public static final String BALANCE_AMOUNT_STRING				= "BALANCE_AMOUNT";
	public static final String REFUND_AMOUNT_STRING					= "REFUND_AMOUNT";
	public static final String UNLOADING_STRING						= "UNLOADING";
	public static final String RATE_PMT_STRING						= "RATE_PMT";
	public static final String DETENTION_STRING						= "DETENTION";
	public static final String OTHER_ADDITIONAL_STRING				= "OTHER_ADDITIONAL";
	public static final String DEDUCTION_STRING						= "DEDUCTION";
	public static final String TOPAY_RECEIVED_STRING				= "TOPAY_RECEIVED";
	public static final String CREDIT_HAMALI_STRING					= "CREDIT_HAMALI";
	public static final String CLAIM_STRING							= "CLAIM";
	public static final String COLLECTION_STRING					= "COLLECTION";
	public static final String TDS_STRING							= "TDS";
	public static final String DELIVERY_CHARGE_STRING				= "DELIVERY_CHARGE";
	public static final String PENALTY_STRING						= "PENALTY";
	public static final String ACTUAL_BALANCE_STRING				= "ACTUAL_BALANCE";
	public static final String ACTUAL_REFUND_STRING					= "ACTUAL_REFUND";
	public static final String LABOUR_STRING						= "LABOUR";
	public static final String DETENTION_AT_LOADING_POINT_STRING	= "DETENTION_AT_LOADING_POINT";
	public static final String DETENTION_AT_UNLOADING_POINT_STRING	= "DETENTION_AT_UNLOADING_POINT";
	public static final String ADDITIONAL_STRING					= "ADDITIONAL";
	public static final String HAMALI_DEDUCT_STRING					= "HAMALI_DEDUCT";
	public static final String HAMALI_GIVEN_STRING					= "HAMALI_GIVEN";
	public static final String EXT_LHC_STRING						= "EXT_LHC";
	public static final String EXT_DD_STRING						= "EXT_DD";
	public static final String DIRECT_DELIVERY_AMOUNT_STRING		= "DIRECT_DELIVERY_AMOUNT";
	public static final String DELIVERY_COMMISSION_STRING			= "DELIVERY_COMMISSION";
	public static final String LATE_DATE_STRING						= "LATE_DATE";
	public static final String OH_STRING							= "OH";
	public static final String OL_STRING							= "OL";
	public static final String LC_STRING							= "LC";
	public static final String CROSSING_HAMALI_STRING				= "CROSSING_HAMALI";
	public static final String CROSSING_LH_STRING					= "CROSSING_LH";
	public static final String DOOR_DELIVERY_STRING					= "DOOR_DELIVERY";
	public static final String CARTAGE_STRING						= "CARTAGE";
	public static final String FUEL_CHARGE_STRING					= "FUEL_CHARGE";
	public static final String CO_LH_STRING							= "CO_LH";
	public static final String LOCAL_CHARGE_STRING					= "LOCAL_CHARGE";
	public static final String OTHER_NEGATIVE_STRING				= "OTHER_NEGATIVE";
	public static final String BORDER_EXPENSES_STRING				= "BORDER_EXPENSES";
	public static final String POLICE_CHALLANS_STRING				= "POLICE_CHALLANS";
	public static final String BYPASS_STRING						= "BYPASS";
	public static final String OVER_LOAD_STRING						= "OVER_LOAD";
	public static final String TOLL_TAX_STRING						= "TOLL_TAX";
	public static final String SALES_TAX_STRING						= "SALES_TAX";
	public static final String CHECK_POST_STRING					= "CHECK_POST";
	public static final String INAM_STRING							= "INAM";
	public static final String OLD_ADVANCE_BALANCE_STRING			= "OLD_ADVANCE_BALANCE";
	public static final String OLD_DAMAGE_CHARGES_STRING			= "OLD_DAMAGE_CHARGES";
	public static final String THAPI_STRING							= "THAPI";
	public static final String PAID_STRING							= "PAID";
	public static final String SERVICE_TAX_STRING					= "SERVICE_TAX";
	public static final String REFUND_STRING						= "REFUND";
	public static final String MISC_STRING							= "MISC";
	public static final String PAID_CREDIT_STRING					= "PAID_CREDIT";
	public static final String DRIVER_COLLECTION_STRING				= "DRIVER_COLLECTION";
	public static final String ADDITIONAL_TRUCK_ADVANCE_STRING		= "ADDITIONAL_TRUCK_ADVANCE";
	public static final String OVER_RUN_STRING						= "OVER_RUN";
	public static final String DELIVERY_CANCELLATION_STRING			= "DELIVERY_CANCELLATION";
	
	public static final String LORRY_HIRE_STRING_NAME				= "LORRY HIRE";
	public static final String ADVANCE_AMOUNT_STRING_NAME			= "ADVANCE";
	public static final String BALANCE_AMOUNT_STRING_NAME			= "BALANCE";
	public static final String REFUND_AMOUNT_STRING_NAME			= "REFUND";
	public static final String ACTUAL_BALANCE_STRING_NAME			= "ACTUAL BALANCE";
	public static final String ACTUAL_REFUND_STRING_NAME			= "ACTUAL REFUND";
	

	private long 	lhpvChargeTypeMasterId;
	private String 	chargeName;
	private String 	chargeDescription;

	public long getLhpvChargeTypeMasterId() {
		return lhpvChargeTypeMasterId;
	}
	public void setLhpvChargeTypeMasterId(long lhpvChargeTypeMasterId) {
		this.lhpvChargeTypeMasterId = lhpvChargeTypeMasterId;
	}
	public String getChargeName() {
		return chargeName;
	}
	public void setChargeName(String chargeName) {
		this.chargeName = chargeName;
	}
	public String getChargeDescription() {
		return chargeDescription;
	}
	public void setChargeDescription(String chargeDescription) {
		this.chargeDescription = chargeDescription;
	}

	private static ArrayList<Short> 		necessaryCharges 	= new ArrayList<Short>();
	private static HashMap<Short, String> 	chargesHsmp 		= new HashMap<Short, String>();
	
	static {
		necessaryCharges.add(LhpvChargeTypeMaster.LORRY_HIRE);
		necessaryCharges.add(LhpvChargeTypeMaster.ADVANCE_AMOUNT);
		necessaryCharges.add(LhpvChargeTypeMaster.BALANCE_AMOUNT);
		necessaryCharges.add(LhpvChargeTypeMaster.REFUND_AMOUNT);
		necessaryCharges.add(LhpvChargeTypeMaster.ACTUAL_BALANCE);
		necessaryCharges.add(LhpvChargeTypeMaster.ACTUAL_REFUND);

		chargesHsmp.put(LORRY_HIRE, LORRY_HIRE_STRING_NAME);
		chargesHsmp.put(ADVANCE_AMOUNT, ADVANCE_AMOUNT_STRING_NAME);
		chargesHsmp.put(BALANCE_AMOUNT, BALANCE_AMOUNT_STRING_NAME);
		chargesHsmp.put(REFUND_AMOUNT, REFUND_AMOUNT_STRING_NAME);
		chargesHsmp.put(ACTUAL_BALANCE, ACTUAL_BALANCE_STRING_NAME);
		chargesHsmp.put(ACTUAL_REFUND, ACTUAL_REFUND_STRING_NAME);
	}

	public static ArrayList<Short> getNecessaryCharges() {
		return necessaryCharges;
	}

	public static HashMap<Short, String> getLHPVChargesName() {
		return chargesHsmp;
	}
}
