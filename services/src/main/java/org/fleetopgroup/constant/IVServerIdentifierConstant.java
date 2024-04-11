package org.fleetopgroup.constant;

public class IVServerIdentifierConstant {

		
		public static final short CARGO_SERVER				= 1;
		public static final short TRANSCARGO_SERVER			= 2;
		public static final short LMT_SERVER				= 3;
		public static final short IV_TRANSCARGO_SERVER		= 4;
		
		public static final String	CARGO_URL			= "https://www.ivcargo.com/ivcargo/";
		public static final String	TRANS_CARGO_URL		= "https://www.ivcargo.net/ivcargo/";
		public static final String	IV_TRANS_CARGO_URL	= "https://www.ivcargo.in/ivcargo/";
		public static final String	LMT_URL				= "https://www.lmterp.com/ivcargo/";

		
		public static String getServerName(short serverId){
			String returnValue = null;

			switch (serverId) {
			case CARGO_SERVER:
				returnValue = CARGO_URL;
				return returnValue;

			case TRANSCARGO_SERVER:
				returnValue = TRANS_CARGO_URL;
				return returnValue;

			case LMT_SERVER:
				returnValue = LMT_URL;
				return returnValue;

			case IV_TRANSCARGO_SERVER:
				returnValue = IV_TRANS_CARGO_URL;
				return returnValue;


			default:
				returnValue = "--";
				return returnValue;
			}
		}
}
