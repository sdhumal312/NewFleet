package org.fleetopgroup.constant;

public class VehicleAgentConstant {

	public static final short TXN_TYPE_INCOME			= 1;
	public static final short TXN_TYPE_EXPENSE			= 2;
	public static final short TXN_TYPE_OPENING_ENTRY	= 3;
	public static final short TXN_TYPE_CLOSING_ENTRY	= 4;
	public static final short TXN_TYPE_AGENT_PAYMENT	= 5;
	
	public static final short TXN_IDENTIFIER_FUEL_ENTRY				= 1;
	public static final short TXN_IDENTIFIER_SERVICE_ENTRY			= 2;
	public static final short TXN_IDENTIFIER_WORKORDER				= 3;
	public static final short TXN_IDENTIFIER_TRIPSHEET_EXPENSE		= 4;
	public static final short TXN_IDENTIFIER_RENEWAL				= 5;
	public static final short TXN_IDENTIFIER_VEHICLE_EMI			= 6;
	public static final short TXN_IDENTIFIER_VEHICLE_TYRE			= 7;
	public static final short TXN_IDENTIFIER_VEHICLE_BATTERY		= 8;
	public static final short TXN_IDENTIFIER_VEHICLE_UREA			= 9;
	public static final short TXN_IDENTIFIER_LORRY_HIRE				= 10;
	public static final short TXN_IDENTIFIER_DEALER_SERVICE_ENTRY	= 11;
	
	public static final String TXN_IDENTIFIER_FUEL_ENTRY_NAME			= "Fuel Entry";
	public static final String TXN_IDENTIFIER_SERVICE_ENTRY_NAME		= "Service Entry";
	public static final String TXN_IDENTIFIER_WORKORDER_NAME			= "WorkOrder";
	public static final String TXN_IDENTIFIER_TRIPSHEET_EXPENSE_NAME	= "Trip Expense";
	public static final String TXN_IDENTIFIER_RENEWAL_NAME				= "Renewal Entry";
	public static final String TXN_IDENTIFIER_VEHICLE_EMI_NAME			= "Vehicle EMI";
	public static final String TXN_IDENTIFIER_VEHICLE_TYRE_NAME			= "Tyre";
	public static final String TXN_IDENTIFIER_VEHICLE_BATTERY_NAME		= "Battery";
	public static final String TXN_IDENTIFIER_VEHICLE_UREA_NAME			= "Urea Entry";
	public static final String TXN_IDENTIFIER_LORRY_HIRE_NAME			= "Lorry Hire";
	public static final String TXN_IDENTIFIER_DEALER_SERVICE_ENTRY_NAME	= "Dealer Service Entry";
	
	public static final String TXN_TYPE_INCOME_NAME					= "Income";
	public static final String TXN_TYPE_EXPENSE_NAME				= "Expense";
	public static final String TXN_TYPE_OPENING_ENTRY_NAME			= "Opening Entry";
	public static final String TXN_TYPE_CLOSING_ENTRY_NAME			= "Closing Entry";
	public static final String TXN_TYPE_AGENT_PAYMENT_NAME			= "Payment";
	
	
	public static final String 	TRANSACTION_TYPE					= "transactionType";
	public static final String 	TRANSACTION_ID						= "transactionId";
	public static final String 	VEHICLE_ID							= "vehicleId";
	public static final String 	TRANSACTION_AMOUNT					= "transactionAmount";
	public static final String 	IDENTIFIER							= "identifier";
	public static final String 	COMPANY_ID							= "companyId";
	public static final String 	TRANSACTION_DATE					= "transactionDate";
	public static final String 	TRANSACTION_CHECKER					= "transactionChecker";
	public static final String 	TRANSACTION_CHECKER_ID				= "transactionCheckerId";
	public static final String 	TRANSACTION_ACCOUNT					= "accountName";
	public static final String 	TRANSACTION_REMARK					= "remark";
	public static final String 	NUMBER_WITH_TYPE					= "numberWithType";
	public static final String 	CREATED_BY_ID						= "createdById";
	public static final String 	CREDIT_AMOUNT						= "creditAmount";
	public static final String 	DEBIT_AMOUNT						= "deditAmount";
	public static final String 	CLOSING_AMOUNT						= "closingAmount";
	
	
	public static String getTxnIdentifierName(short status) throws Exception{

		String statusString		=	null;
		switch (status) {
		  case TXN_IDENTIFIER_FUEL_ENTRY:
			  statusString	= TXN_IDENTIFIER_FUEL_ENTRY_NAME;
		        break;
		  case TXN_IDENTIFIER_SERVICE_ENTRY: 
			  statusString	= TXN_IDENTIFIER_SERVICE_ENTRY_NAME;
		        break;
		  case TXN_IDENTIFIER_WORKORDER: 
			  statusString	= TXN_IDENTIFIER_WORKORDER_NAME;
		        break;
		  case TXN_IDENTIFIER_TRIPSHEET_EXPENSE: 
			  statusString	= TXN_IDENTIFIER_TRIPSHEET_EXPENSE_NAME;
		        break;
		  case TXN_IDENTIFIER_RENEWAL: 
			  statusString	= TXN_IDENTIFIER_RENEWAL_NAME;
		        break;
		  case TXN_IDENTIFIER_VEHICLE_EMI: 
			  statusString	= TXN_IDENTIFIER_VEHICLE_EMI_NAME;
		        break;
		  case TXN_IDENTIFIER_VEHICLE_TYRE: 
			  statusString	= TXN_IDENTIFIER_VEHICLE_TYRE_NAME;
		        break;
		  case TXN_IDENTIFIER_VEHICLE_BATTERY: 
			  statusString	= TXN_IDENTIFIER_VEHICLE_BATTERY_NAME;
		        break;
		  case TXN_IDENTIFIER_VEHICLE_UREA: 
			  statusString	= TXN_IDENTIFIER_VEHICLE_UREA_NAME;
		        break;
		  case TXN_IDENTIFIER_LORRY_HIRE: 
			  statusString	= TXN_IDENTIFIER_LORRY_HIRE_NAME;
		        break;
		  case TXN_IDENTIFIER_DEALER_SERVICE_ENTRY: 
			  statusString	= TXN_IDENTIFIER_DEALER_SERVICE_ENTRY_NAME;
			  break;
		 
		  default:
			  statusString = "--";
		        break;
		}
		return statusString;
	}
	
	public static String getTxnTypeName(short status) throws Exception{

		String statusString		=	null;
		switch (status) {
		  case TXN_TYPE_INCOME:
			  statusString	= TXN_TYPE_INCOME_NAME;
		        break;
		  case TXN_TYPE_EXPENSE: 
			  statusString	= TXN_TYPE_EXPENSE_NAME;
		        break;
		  case TXN_TYPE_OPENING_ENTRY: 
			  statusString	= TXN_TYPE_OPENING_ENTRY_NAME;
		        break;
		  case TXN_TYPE_CLOSING_ENTRY: 
			  statusString	= TXN_TYPE_CLOSING_ENTRY_NAME;
		        break;
		  case TXN_TYPE_AGENT_PAYMENT: 
			  statusString	= TXN_TYPE_AGENT_PAYMENT_NAME;
		        break;      
		 
		  default:
			  statusString = "--";
		        break;
		}
		return statusString;
	
	}
}
