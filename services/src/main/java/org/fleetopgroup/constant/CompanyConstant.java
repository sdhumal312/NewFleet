package org.fleetopgroup.constant;

import java.util.ArrayList;
import java.util.List;

public class CompanyConstant {
	
	public static final Integer	COMPANY_CODE_DURGAMBA				= 1;
	public static final Integer	COMPANY_CODE_DWD					= 2;
	//public static final Integer	COMPANY_CODE_SRE					= 3;
	public static final Integer	COMPANY_CODE_FLEETOP				= 4;
	public static final Integer	COMPANY_CODE_IVCARGO				= 5;
	public static final Integer	COMPANY_CODE_SRS					= 6;
	public static final Integer	COMPANY_CODE_GTPL					= 7;
	public static final Integer	COMPANY_CODE_CONTI					= 8;
	public static final Integer	COMPANY_CODE_NEW_SRIDURGAMBA		= 9;
	public static final Integer	COMPANY_CODE_NEW_CHINTAMANI_TRAVELS	= 10;
	public static final Integer	COMPANY_CODE_NEW_KATIRA				= 11;
	public static final Integer	COMPANY_CODE_SGMT					= 12;
	public static final Integer COMPANY_CODE_SAINI					= 13;
	public static final Integer COMPANY_CODE_PPTPL					= 14;
	public static final Integer COMPANY_CODE_GREENLINE				= 15;
	public static final Integer COMPANY_CODE_STA					= 16;
	public static final Integer COMPANY_CODE_KTS					= 17;
	public static final Integer COMPANY_CODE_SKT					= 18;
	public static final Integer COMPANY_CODE_PTA					= 19;
	public static final Integer COMPANY_CODE_CCI					= 20;
	public static final Integer COMPANY_CODE_LAXMI_xxx				= 21;
	public static final Integer COMPANY_CODE_CSPL					= 22;
	public static final Integer COMPANY_CODE_NETC					= 23;
	public static final Integer COMPANY_CODE_FALCON					= 24;
	public static final Integer COMPANY_CODE_SVT					= 25;
	public static final Integer COMPANY_CODE_SRISAI					= 26;
	public static final Integer COMPANY_CODE_SRT					= 27;
	public static final Integer COMPANY_CODE_GGS					= 28;
	public static final Integer COMPANY_CODE_SRE					= 29;
	public static final Integer COMPANY_CODE_MTL					= 30;
	public static final Integer COMPANY_CODE_LMT					= 31;
	public static final Integer COMPANY_CODE_LAXMI					= 32;
	public static final Integer COMPANY_CODE_PATEL					= 33;
	public static final Integer COMPANY_CODE_DLT					= 34;
	public static final Integer COMPANY_CODE_SEABIRD				= 38;
	public static final Integer COMPANY_CODE_AADINATH				= 40;
	public static final Integer COMPANY_CODE_MANTRA					= 41;
	public static final Integer COMPANY_CODE_BABA					= 42;
	
	
	public static List<String> getAuthorizedPagesNames(){
		List<String> authorizedPages = new ArrayList<>();
		authorizedPages.add("login");
		authorizedPages.add("logout");
		authorizedPages.add("open");
		authorizedPages.add("otpvalidate");
		authorizedPages.add("validateOtp");
		authorizedPages.add("invalidSession");
		authorizedPages.add("console");
		return authorizedPages;
	}
}
