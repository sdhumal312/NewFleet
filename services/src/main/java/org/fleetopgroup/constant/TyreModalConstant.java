package org.fleetopgroup.constant;

public class TyreModalConstant {
	
	public static final short TYRE_MODEL_RADIAL 					= 1;
	public static final short TYRE_MODEL_NYLON	 					= 2;
	
	public static final String TYRE_MODEL_RADIAL_NAME 				= "RADIAL";
	public static final String TYRE_MODEL_NYLON_NAME 				= "NYLON";
	
	public static final short TYRE_MODAL_TUBE	 					= 1;
	public static final short TYRE_MODAL_TUBELESS	 				= 2;
	
	public static final String TYRE_MODAL_TUBE_NAME 				= "TUBE";
	public static final String TYRE_MODAL_TUBELESS_NAME 			= "TUBELESS";
	
	public static String getTyreModelType (short tyreModelTypeId) throws Exception {
		String tyreModelType = null;
		switch(tyreModelTypeId) {
		case TYRE_MODEL_RADIAL :
			tyreModelType 	= 	 TYRE_MODEL_RADIAL_NAME;
			break;
		case TYRE_MODEL_NYLON :
			tyreModelType 	=	 TYRE_MODEL_NYLON_NAME;
			break;
		default :
			tyreModelType = "--";
			break;
		}
		return tyreModelType;
	}
	
	public static String getTyreTubeType (short tyreModelTubeTypeId) throws Exception {
		String tyreModelTubeType = null;
		switch(tyreModelTubeTypeId) {
		case TYRE_MODAL_TUBE :
			tyreModelTubeType 	= 	 TYRE_MODAL_TUBE_NAME;
			break;
		case TYRE_MODAL_TUBELESS :
			tyreModelTubeType 	=	 TYRE_MODAL_TUBELESS_NAME;
			break;
		default :
			tyreModelTubeType = "--";
			break;
		}
		return tyreModelTubeType;
	}
	
}