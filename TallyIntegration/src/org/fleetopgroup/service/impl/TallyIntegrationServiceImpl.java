package org.fleetopgroup.service.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.fleetopgroup.dto.RESPONSE;
import org.fleetopgroup.dto.TripSheetExpenseDto;
import org.fleetopgroup.service.ITallyIntegrationService;
import org.fleetopgroup.service.MyTableModel;
import org.fleetopgroup.utility.Utility;
import org.json.JSONArray;
import org.json.JSONObject;

public class TallyIntegrationServiceImpl implements ITallyIntegrationService {
	
	public String hostUrl = "";
	public String tallyHost = "";
	public Integer companyId = 0;
	public String dataUrl = "";
	String voucher	= null;
	public boolean tallyPushForVehicle = false;
	 HashMap<String, List<TripSheetExpenseDto>>  vehicleHM		= null;
	@Override
	public HashMap<String, Object> getExpenseVoucherDataWthinRange(Date fromDate, Date toDate, String selectedCompany) throws Exception {
		
		JSONArray	vendorPaymentArr		=	null;
		JSONArray	expenseListArr			=	null;
		JSONArray	invoiceListArr			=	null;
		JSONArray	vehicleExpListArr		=	null;
		JSONArray	driverBalanceListArr	=	null;
		try {
		
		Properties props = new Properties();
        InputStream  inputStream = this.getClass().getClassLoader().getResourceAsStream("configuration.properties");
        props.load(inputStream);


		dataUrl		= props.getProperty("dataUrl");
		companyId	= Integer.parseInt(props.getProperty("companyId"));
		tallyPushForVehicle	= Boolean.getBoolean(props.getProperty("tallyPushForVehicle", "true"));
		
		
		JSONObject reqObj = prepareReqJsonObj(fromDate, toDate, companyId, selectedCompany);
		
		String response 	= Utility.excutePost(dataUrl, reqObj.toString());
		
		JSONObject resObj	= new JSONObject(response);
		
		if(resObj != null && resObj.has("vendorPaymentList")) {
			vendorPaymentArr	=	resObj.getJSONArray("vendorPaymentList");
		}
		if(resObj != null && resObj.has("invoiceList")) {
			invoiceListArr	=	resObj.getJSONArray("invoiceList");
		}
		if(resObj != null && resObj.has("vehicleExpList")) {
			vehicleExpListArr	=	resObj.getJSONArray("vehicleExpList");
		}
		if(resObj != null && resObj.has("driverBalanceList")) {
			driverBalanceListArr	=	resObj.getJSONArray("driverBalanceList");
		}
		
		
		if((resObj != null && resObj.has("expenseList")) || resObj.has("vendorPaymentList") || resObj.has("invoiceList") 
				|| resObj.has("vehicleExpList") || resObj.has("driverBalanceList")) {
			
			vehicleHM	= new HashMap<String, List<TripSheetExpenseDto>>();
				try {
						expenseListArr	=	resObj.getJSONArray("expenseList");
				} catch (Exception e) {
					
				}
				
				if((expenseListArr != null && expenseListArr.length() > 0) || (vendorPaymentArr != null && vendorPaymentArr.length() > 0) 
						|| (invoiceListArr != null && invoiceListArr.length() > 0) || (vehicleExpListArr != null && vehicleExpListArr.length() > 0)
						|| (driverBalanceListArr != null && driverBalanceListArr.length() > 0)) {
					
					openVoucherListPopUp(expenseListArr, vendorPaymentArr, invoiceListArr, selectedCompany, vehicleExpListArr, driverBalanceListArr);
					
				}else {
					JOptionPane.showMessageDialog(null, "No Record Found !");
				}
				
		}else {
			JOptionPane.showMessageDialog(null, "No Record Found !");
		}
	} catch (ConnectException e) {
		JOptionPane.showMessageDialog(null, "Unable To Connect With Tally, Please Make Sure Tally Is Running!");
	} catch (IOException e) {
		JOptionPane.showMessageDialog(null, "Some Error Occured , Please contact to system administrator !");
	}catch (Exception e) {
		e.printStackTrace();
		System.err.println("Exception : "+ e);
		//JOptionPane.showMessageDialog(null, "Unable To Process Record !");
	}		
	
	return null;
	}
	
    public boolean SendToTally(String Voucher) throws Exception{
        String SOAPAction = "";
        boolean isSuccess = false;

// Create the connection where we're going to send the file.
        URL url = new URL("http://127.0.0.1:9000/");
        URLConnection connection = url.openConnection();
        HttpURLConnection httpConn = (HttpURLConnection) connection;

       
        ByteArrayInputStream bin = new ByteArrayInputStream(Voucher.getBytes());
        ByteArrayOutputStream bout = new ByteArrayOutputStream();

// Copy the SOAP file to the open connection.
       
        copy(bin, bout);     

        byte[] b = bout.toByteArray();

// Set the appropriate HTTP parameters.
        httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
        httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        httpConn.setRequestProperty("SOAPAction", SOAPAction);
        httpConn.setRequestMethod("POST");
        httpConn.setDoOutput(true);
        httpConn.setDoInput(true);

// Everything's set up; send the XML that was read in to b.
        OutputStream out = httpConn.getOutputStream();
        out.write(b);
        out.close();

// Read the response and write it to standard out.

        InputStreamReader isr =
                new InputStreamReader(httpConn.getInputStream());
        BufferedReader in = new BufferedReader(isr);

        String str = in.lines().collect(Collectors.joining());
        StringReader sr = new StringReader(str);
        JAXBContext jaxbContext = JAXBContext.newInstance(RESPONSE.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        RESPONSE	response = (RESPONSE) unmarshaller.unmarshal(sr);
        if(response.getLINEERROR() != null) {
        	JOptionPane.showMessageDialog(null, response.getLINEERROR());
        }else {
        	 if(response.getERRORS() != null && response.getERRORS() > 0) {
        		 JOptionPane.showMessageDialog(null, "ERRORS : "+response.getERRORS());
        		 JOptionPane.showMessageDialog(null, "ERRORS : "+"Please check voucher and tally date !");
        	 }else {
        		 JOptionPane.showMessageDialog(null, "All Record Synchronized Successfully !");
        		 isSuccess = true;
        	 }
        	
        }
        
       
        in.close();
        return isSuccess;
    }
    
    public static void copy(InputStream in, OutputStream out)
            throws IOException {

// do not allow other threads to read from the
// input or write to the output while copying is
// taking place

        synchronized (in) {
            synchronized (out) {

                byte[] buffer = new byte[256];
                while (true) {
                    int bytesRead = in.read(buffer);
                    if (bytesRead == -1) {
                        break;
                    }
                    out.write(buffer, 0, bytesRead);
                }
            }
        }
    }
    

	public JSONObject prepareReqJsonObj(Date fromDate, Date toDate ,Integer companyId, String selectedStr){
	 JSONObject jsonobj = new JSONObject();

	 	SimpleDateFormat	dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	 	
	    jsonobj.put("fromDate", dateFormat.format(fromDate));
	    jsonobj.put("toDate", dateFormat.format(toDate));
	    jsonobj.put("companyId",companyId);
	    jsonobj.put("selectedStr",selectedStr);

	    return jsonobj; 
	}
	
	
	 public String createRequest(JSONArray	expenseListArr, String selectedCompany, HashMap<String, List<TripSheetExpenseDto>>	vehicleHM, 
			 JSONArray	vendorPaymentArr, JSONArray	invoiceListArr, JSONArray vehicleListArr, JSONArray driverBalanceListArr)
	    {             
		 
	    	HashMap<String, TripSheetExpenseDto>	trHashMap	= new HashMap<String, TripSheetExpenseDto>();
	        String TXML = null;
	        DecimalFormat format = new DecimalFormat("##.00");

	        TXML = "<ENVELOPE>"
	        		+ "<HEADER>"
	        		+ "<TALLYREQUEST>Import Data</TALLYREQUEST>"
	        		+ "</HEADER>"
	        		+ "<BODY>"
	        		+ "<IMPORTDATA>"
	        		+ "<REQUESTDESC>"
	        		+ "<REPORTNAME>All Masters</REPORTNAME>"
	        		+ "<STATICVARIABLES>"
	        		+ "<SVCURRENTCOMPANY>"+selectedCompany+"</SVCURRENTCOMPANY>"
	        		+ "</STATICVARIABLES>"
	        		+ "</REQUESTDESC>"
	        		+ "<REQUESTDATA>";
	        		if(expenseListArr != null && expenseListArr.length() > 0) {
	        			for (int i = 0; i < expenseListArr.length(); i++) {
	        				JSONObject	jsonObject	=	expenseListArr.getJSONObject(i);
	        				if(jsonObject.getString("ledgerName").equalsIgnoreCase("--") || jsonObject.getString("ledgerName").equalsIgnoreCase("")) {
	        					JOptionPane.showMessageDialog(null, "Please Configure Ledger Name for Vehicle "+jsonObject.getString("vehicle_registration"));
	        					return null;
	        				}										//expenseDto.getVid()+"_"+expenseDto.getTripSheetId()+"_"+expenseDto.getVoucherDate()+"_"+expenseDto.getTripExpenseID()		
	        				TripSheetExpenseDto	expenseDto = trHashMap.get(jsonObject.getInt("vid")+"_"+jsonObject.getLong("tripSheetId")+"_"+jsonObject.getString("voucherDate")+"_"+jsonObject.getLong("tripExpenseID"));
	        				if(expenseDto == null) {
	        					expenseDto	= new TripSheetExpenseDto();
	        					expenseDto.setLedgerName(jsonObject.getString("ledgerName"));
	        					expenseDto.setRemark(jsonObject.getString("remark"));
	        					expenseDto.setExpenseAmount(jsonObject.getDouble("expenseAmount"));
	        					expenseDto.setVoucherDate(jsonObject.getString("voucherDate"));
	        					expenseDto.setVid(jsonObject.getInt("vid"));
	        					expenseDto.setTripSheetId(jsonObject.getLong("tripSheetId"));
	        				}else {
	        					expenseDto.setExpenseAmount(jsonObject.getDouble("expenseAmount") + expenseDto.getExpenseAmount());
	        				}
	        				trHashMap.put(jsonObject.getInt("vid")+"_"+jsonObject.getLong("tripSheetId")+"_"+jsonObject.getString("voucherDate")+"_"+jsonObject.getLong("tripExpenseID"), expenseDto);
	        				TXML += "<TALLYMESSAGE xmlns:UDF=\"TallyUDF\">";
	        				if(jsonObject.getBoolean("credit")) {
	        					TXML +="<VOUCHER VCHTYPE=\"Journal\" REMOTEID=\""+jsonObject.getLong("tripExpenseID")+"_"+jsonObject.getLong("expenseTypeId")+" \">";
	        					TXML += "<VOUCHERTYPENAME>Journal</VOUCHERTYPENAME> "  ;
	        				}else {
	        					TXML +="<VOUCHER VCHTYPE=\"Payment\" REMOTEID=\""+jsonObject.getLong("tripExpenseID")+"_"+jsonObject.getLong("expenseTypeId")+"\">";
	        					TXML += "<VOUCHERTYPENAME>Payment</VOUCHERTYPENAME> "  ;
	        				}
	        				TXML +=	"<DATE>"+jsonObject.getString("voucherDate")+"</DATE>"
	        						+"<VOUCHERNUMBER>"+jsonObject.getLong("tripExpenseID")+"</VOUCHERNUMBER>";
	        				if(jsonObject.getBoolean("credit")) {
	        					TXML +=	 "  <PARTYLEDGERNAME>"+jsonObject.getString("vendorName")+"</PARTYLEDGERNAME> ";
	        				}else {
	        					TXML += "  <PARTYLEDGERNAME>Cash</PARTYLEDGERNAME>";
	        				}
	        				TXML +="<NARRATION>"+jsonObject.getString("remark")+"</NARRATION>"	  		
	        						+"<EFFECTIVEDATE>"+jsonObject.getString("voucherDate")+"</EFFECTIVEDATE> <ALLLEDGERENTRIES.LIST>";		
	        				if(jsonObject.getBoolean("credit")) {
	        					TXML +=	 "  <LEDGERNAME>"+jsonObject.getString("vendorName")+"</LEDGERNAME> ";
	        				}else {
	        					TXML += "  <LEDGERNAME>Cash</LEDGERNAME>";
	        				}	
	        				TXML +=  " <REMOVEZEROENTRIES>NO</REMOVEZEROENTRIES>"
	        						+" <LEDGERFROMITEM>NO</LEDGERFROMITEM>"
	        						+" <ISDEEMEDPOSITIVE>NO</ISDEEMEDPOSITIVE>"  
	        						+" <AMOUNT>"+format.format(jsonObject.getDouble("expenseAmount"))+"</AMOUNT></ALLLEDGERENTRIES.LIST><ALLLEDGERENTRIES.LIST>"	;
	        				if(jsonObject.getString("expenseName").equalsIgnoreCase("Diesel EXP") || jsonObject.getString("expenseName").equalsIgnoreCase("Diesel")) {
	        					TXML +=" <LEDGERNAME>Diesel Expense</LEDGERNAME>";
	        				}else {
	        					TXML +=" <LEDGERNAME>"+jsonObject.getString("expenseName")+"</LEDGERNAME>";
	        				}
	        				
	        				TXML += "<REMOVEZEROENTRIES>NO</REMOVEZEROENTRIES>"
	        						+" <LEDGERFROMITEM>NO</LEDGERFROMITEM>"
	        						+"<ISDEEMEDPOSITIVE>YES</ISDEEMEDPOSITIVE>"  
	        						+" <AMOUNT>-"+format.format(jsonObject.getDouble("expenseAmount"))+"</AMOUNT></ALLLEDGERENTRIES.LIST>"
	        						+ "</VOUCHER>"
	        						+ "</TALLYMESSAGE>";
	        			}
	        		}
		        	if(vehicleHM != null && vehicleHM.size() > 0) {	
		        		try {
		        			for (String key : vehicleHM.keySet()) {
			        			TripSheetExpenseDto	expenseDto = trHashMap.get(key);
			        			List<TripSheetExpenseDto> tripList	= vehicleHM.get(key);
				        		TXML += "<TALLYMESSAGE xmlns:UDF=\"TallyUDF\">"
				        			+"<VOUCHER VCHTYPE=\"Journal\" REMOTEID=\""+key+"  \">"
				        			+"<VOUCHERTYPENAME>Journal</VOUCHERTYPENAME> "
				        			+"<DATE>"+expenseDto.getVoucherDate()+"</DATE>"
				        			+"<VOUCHERNUMBER>"+expenseDto.getTripSheetId()+"_"+expenseDto.getVoucherDate()+"</VOUCHERNUMBER>"
				        			+"<PARTYLEDGERNAME>"+expenseDto.getLedgerName()+"</PARTYLEDGERNAME> "
				        			+"<NARRATION>"+expenseDto.getRemark()+"</NARRATION>"	  		
				        			+"<EFFECTIVEDATE>"+expenseDto.getVoucherDate()+"</EFFECTIVEDATE> <ALLLEDGERENTRIES.LIST>"
			     					+"<LEDGERNAME>"+expenseDto.getLedgerName()+"</LEDGERNAME>"
				        			+" <REMOVEZEROENTRIES>NO</REMOVEZEROENTRIES>"
				                    +" <LEDGERFROMITEM>NO</LEDGERFROMITEM>"
				                    +" <ISDEEMEDPOSITIVE>YES</ISDEEMEDPOSITIVE>"  
				                    +" <AMOUNT>-"+format.format(expenseDto.getExpenseAmount())+"</AMOUNT></ALLLEDGERENTRIES.LIST>";
				        		for (int i = 0; i < tripList.size(); i++) {
				        			TXML += "<ALLLEDGERENTRIES.LIST>"	
				        			+" <LEDGERNAME>"+tripList.get(i).getExpenseName()+"</LEDGERNAME>"  
				                    +" <REMOVEZEROENTRIES>NO</REMOVEZEROENTRIES>"
				                    +" <LEDGERFROMITEM>NO</LEDGERFROMITEM>"
				                    +"<ISDEEMEDPOSITIVE>NO</ISDEEMEDPOSITIVE>"  
				                    +" <AMOUNT>"+format.format(tripList.get(i).getExpenseAmount())+"</AMOUNT>"
				                    + "</ALLLEDGERENTRIES.LIST>";
				        		}
				        		TXML += "</VOUCHER>"
				                     + "</TALLYMESSAGE>";
				        		
			        		}
						} catch (Exception e) {
							System.err.println("inside vehicle exce  "+e);
						}
		        	
		        	}
	        		if(vendorPaymentArr != null && vendorPaymentArr.length() > 0) {
	        			for (int i = 0; i < vendorPaymentArr.length(); i++) {
	        				JSONObject	jsonObject	=	vendorPaymentArr.getJSONObject(i);
	        				
	        				TXML += "<TALLYMESSAGE xmlns:UDF=\"TallyUDF\">";
	        				TXML +="<VOUCHER VCHTYPE=\"Payment\" REMOTEID=\""+jsonObject.getLong("tripExpenseID")+"_"+jsonObject.getLong("expenseTypeId")+"\">";
	        				TXML += "<VOUCHERTYPENAME>Payment</VOUCHERTYPENAME> "  ;
	        				TXML +=	"<DATE>"+jsonObject.getString("voucherDate")+"</DATE>"
	        					 +"<VOUCHERNUMBER>"+jsonObject.getLong("tripSheetNumber")+"</VOUCHERNUMBER>";
	        				TXML += "<PARTYLEDGERNAME>"+jsonObject.getString("ledgerName")+"</PARTYLEDGERNAME>";
	        				TXML +="<NARRATION>"+jsonObject.getString("remark")+"</NARRATION>"	  		
	        					 +"<EFFECTIVEDATE>"+jsonObject.getString("voucherDate")+"</EFFECTIVEDATE> <ALLLEDGERENTRIES.LIST>";		
	        				TXML += " <LEDGERNAME>"+jsonObject.getString("ledgerName")+"</LEDGERNAME>";
	        				TXML +=  " <REMOVEZEROENTRIES>NO</REMOVEZEROENTRIES>"
	        						+" <LEDGERFROMITEM>NO</LEDGERFROMITEM>"
	        						+" <ISDEEMEDPOSITIVE>NO</ISDEEMEDPOSITIVE>"  
	        						+" <AMOUNT>"+format.format(jsonObject.getDouble("expenseAmount"))+"</AMOUNT></ALLLEDGERENTRIES.LIST><ALLLEDGERENTRIES.LIST>"	;
	        					TXML +=" <LEDGERNAME>"+jsonObject.getString("vendorName")+"</LEDGERNAME>";
	        				
	        				TXML += "<REMOVEZEROENTRIES>NO</REMOVEZEROENTRIES>"
	        						+" <LEDGERFROMITEM>NO</LEDGERFROMITEM>"
	        						+"<ISDEEMEDPOSITIVE>YES</ISDEEMEDPOSITIVE>"  
	        						+" <AMOUNT>-"+format.format(jsonObject.getDouble("expenseAmount"))+"</AMOUNT></ALLLEDGERENTRIES.LIST>"
	        						+ "</VOUCHER>"
	        						+ "</TALLYMESSAGE>";
	        			}
	        		}
	        		if(invoiceListArr != null && invoiceListArr.length() > 0) {
	        			for (int i = 0; i < invoiceListArr.length(); i++) {
	        				JSONObject	jsonObject	=	invoiceListArr.getJSONObject(i);
	        				TXML += "<TALLYMESSAGE xmlns:UDF=\"TallyUDF\">";
	        				if(jsonObject.getBoolean("credit")) {
	        					TXML +="<VOUCHER VCHTYPE=\"Journal\" REMOTEID=\""+jsonObject.getLong("tripExpenseID")+"_"+jsonObject.getLong("expenseTypeId")+" \">";
	        					TXML += "<VOUCHERTYPENAME>Journal</VOUCHERTYPENAME> "  ;
	        				}else {
	        					TXML +="<VOUCHER VCHTYPE=\"Payment\" REMOTEID=\""+jsonObject.getLong("tripExpenseID")+"_"+jsonObject.getLong("expenseTypeId")+"\">";
	        					TXML += "<VOUCHERTYPENAME>Payment</VOUCHERTYPENAME> "  ;
	        				}
	        				TXML +=	"<DATE>"+jsonObject.getString("voucherDate")+"</DATE>"
	        						+"<VOUCHERNUMBER>"+jsonObject.getLong("tripExpenseID")+"</VOUCHERNUMBER>";
	        				if(jsonObject.getBoolean("credit")) {
	        					TXML +=	 "  <PARTYLEDGERNAME>"+jsonObject.getString("vendorName")+"</PARTYLEDGERNAME> ";
	        				}else {
	        					TXML += "  <PARTYLEDGERNAME>Cash</PARTYLEDGERNAME>";
	        				}
	        				TXML +="<NARRATION>"+jsonObject.getString("remark")+"</NARRATION>"	  		
	        						+"<EFFECTIVEDATE>"+jsonObject.getString("voucherDate")+"</EFFECTIVEDATE> <ALLLEDGERENTRIES.LIST>";		
	        				if(jsonObject.getBoolean("credit")) {
	        					TXML +=	 "  <LEDGERNAME>"+jsonObject.getString("vendorName")+"</LEDGERNAME> ";
	        				}else {
	        					TXML += "  <LEDGERNAME>Cash</LEDGERNAME>";
	        				}	
	        				TXML +=  " <REMOVEZEROENTRIES>NO</REMOVEZEROENTRIES>"
	        						+" <LEDGERFROMITEM>NO</LEDGERFROMITEM>"
	        						+" <ISDEEMEDPOSITIVE>NO</ISDEEMEDPOSITIVE>"  
	        						+" <AMOUNT>"+format.format(jsonObject.getDouble("expenseAmount"))+"</AMOUNT></ALLLEDGERENTRIES.LIST><ALLLEDGERENTRIES.LIST>"	;
	        				if(jsonObject.getString("expenseName").equalsIgnoreCase("Diesel EXP") || jsonObject.getString("expenseName").equalsIgnoreCase("Diesel")) {
	        					TXML +=" <LEDGERNAME>Diesel Expense</LEDGERNAME>";
	        				}else {
	        					TXML +=" <LEDGERNAME>"+jsonObject.getString("expenseName")+"</LEDGERNAME>";
	        				}
	        				
	        				TXML += "<REMOVEZEROENTRIES>NO</REMOVEZEROENTRIES>"
	        						+" <LEDGERFROMITEM>NO</LEDGERFROMITEM>"
	        						+"<ISDEEMEDPOSITIVE>YES</ISDEEMEDPOSITIVE>"  
	        						+" <AMOUNT>-"+format.format(jsonObject.getDouble("expenseAmount"))+"</AMOUNT></ALLLEDGERENTRIES.LIST>"
	        						+ "</VOUCHER>"
	        						+ "</TALLYMESSAGE>";
	        			}
	        		}
	        		
	        		if(vehicleListArr != null && vehicleListArr.length() > 0) {
	        			for (int i = 0; i < vehicleListArr.length(); i++) {
	        				JSONObject	jsonObject	=	vehicleListArr.getJSONObject(i);
			        		TXML += "<TALLYMESSAGE xmlns:UDF=\"TallyUDF\">"
			        			+"<VOUCHER VCHTYPE=\"Journal\" REMOTEID=\""+jsonObject.getLong("tripExpenseID")+"_"+jsonObject.getLong("expenseTypeId")+" \">"
			        			+"<VOUCHERTYPENAME>Journal</VOUCHERTYPENAME> "
			        			+"<DATE>"+jsonObject.getString("voucherDate")+"</DATE>"
			        			+"<VOUCHERNUMBER>"+jsonObject.getLong("tripSheetNumber")+"_"+jsonObject.getLong("expenseTypeId")+"</VOUCHERNUMBER>"
			        			+"<PARTYLEDGERNAME>"+jsonObject.getString("ledgerName")+"</PARTYLEDGERNAME> "
			        			+"<NARRATION>"+jsonObject.getString("remark")+"</NARRATION>"	  		
			        			+"<EFFECTIVEDATE>"+jsonObject.getString("voucherDate")+"</EFFECTIVEDATE> <ALLLEDGERENTRIES.LIST>"
		     					+"<LEDGERNAME>"+jsonObject.getString("ledgerName")+"</LEDGERNAME>"
			        			+" <REMOVEZEROENTRIES>NO</REMOVEZEROENTRIES>"
			                    +" <LEDGERFROMITEM>NO</LEDGERFROMITEM>"
			                    +" <ISDEEMEDPOSITIVE>YES</ISDEEMEDPOSITIVE>"  
			                    +" <AMOUNT>-"+format.format(jsonObject.getDouble("expenseAmount"))+"</AMOUNT></ALLLEDGERENTRIES.LIST>"
			        			+"<ALLLEDGERENTRIES.LIST>"	
			        			+" <LEDGERNAME>"+jsonObject.getString("expenseName")+"</LEDGERNAME>"  
			                    +" <REMOVEZEROENTRIES>NO</REMOVEZEROENTRIES>"
			                    +" <LEDGERFROMITEM>NO</LEDGERFROMITEM>"
			                    +"<ISDEEMEDPOSITIVE>NO</ISDEEMEDPOSITIVE>"  
			                    +" <AMOUNT>"+format.format(jsonObject.getDouble("expenseAmount"))+"</AMOUNT>"
			                    + "</ALLLEDGERENTRIES.LIST>";
			        		TXML += "</VOUCHER>"
			                     + "</TALLYMESSAGE>";
			        		
		        		}
		        	}
	        		
	        		if(driverBalanceListArr != null && driverBalanceListArr.length() > 0) {
	        			for (int i = 0; i < driverBalanceListArr.length(); i++) {
	        				JSONObject	jsonObject	=	driverBalanceListArr.getJSONObject(i);
			        		TXML += "<TALLYMESSAGE xmlns:UDF=\"TallyUDF\">"
			        			+"<VOUCHER VCHTYPE=\"Journal\" REMOTEID=\""+jsonObject.getLong("tripExpenseID")+"_"+jsonObject.getLong("expenseTypeId")+"_"+jsonObject.getLong("expenseType")+" \">"
			        			+"<VOUCHERTYPENAME>Journal</VOUCHERTYPENAME> "
			        			+"<DATE>"+jsonObject.getString("voucherDate")+"</DATE>"
			        			+"<VOUCHERNUMBER>"+jsonObject.getLong("tripSheetNumber")+"_"+jsonObject.getLong("expenseTypeId")+"</VOUCHERNUMBER>"
			        		    +"<PARTYLEDGERNAME>"+jsonObject.getString("vendorName")+"</PARTYLEDGERNAME> "
			        		    +"<NARRATION>"+jsonObject.getString("remark")+"</NARRATION>"	  		
			        			+"<EFFECTIVEDATE>"+jsonObject.getString("voucherDate")+"</EFFECTIVEDATE> <ALLLEDGERENTRIES.LIST>"
			        		    +"<LEDGERNAME>"+jsonObject.getString("vendorName")+"</LEDGERNAME>"
			        			+ " <REMOVEZEROENTRIES>NO</REMOVEZEROENTRIES>"
			                    +" <LEDGERFROMITEM>NO</LEDGERFROMITEM>";
			        		if(jsonObject.getDouble("expenseAmount") < 0) {
			        			TXML += " <ISDEEMEDPOSITIVE>NO</ISDEEMEDPOSITIVE>"  
						          +" <AMOUNT>"+format.format(Math.abs(jsonObject.getDouble("expenseAmount")))+"</AMOUNT></ALLLEDGERENTRIES.LIST>";
			        		}else {

			        			TXML += " <ISDEEMEDPOSITIVE>YES</ISDEEMEDPOSITIVE>"  
						          +" <AMOUNT>-"+format.format(Math.abs(jsonObject.getDouble("expenseAmount")))+"</AMOUNT></ALLLEDGERENTRIES.LIST>";
			        		
			        		}
			        	 TXML += "<ALLLEDGERENTRIES.LIST>"	
			        			+" <LEDGERNAME>"+jsonObject.getString("expenseName")+"</LEDGERNAME>"  
			                    +" <REMOVEZEROENTRIES>NO</REMOVEZEROENTRIES>"
			                    +" <LEDGERFROMITEM>NO</LEDGERFROMITEM>";
			        	 if(jsonObject.getDouble("expenseAmount") < 0) {
			        		 TXML +=  "<ISDEEMEDPOSITIVE>YES</ISDEEMEDPOSITIVE>"  
			                    +" <AMOUNT>-"+format.format(Math.abs(jsonObject.getDouble("expenseAmount")))+"</AMOUNT>";
			        	 }else {
			        		 TXML +=  "<ISDEEMEDPOSITIVE>NO</ISDEEMEDPOSITIVE>"  
			                    +" <AMOUNT>"+format.format(Math.abs(jsonObject.getDouble("expenseAmount")))+"</AMOUNT>";
			        	 }
			        	   TXML +=  "</ALLLEDGERENTRIES.LIST>"
			        			 +"</VOUCHER>"
			                     + "</TALLYMESSAGE>";
			        		
		        		}
		        	}
	        		
		        	TXML += "</REQUESTDATA>"
	        		+ "</IMPORTDATA>"
	        		+ "</BODY>"
	        		+ "</ENVELOPE>";
	        	
	        return TXML;
	    }
    private HashMap<String, List<TripSheetExpenseDto>>  getVehicleTripWiseList(JSONArray	expenseListArr) throws Exception{
    	HashMap<String, List<TripSheetExpenseDto>>	vehicleHM		= null;
    	List<TripSheetExpenseDto>					vehicleLIst 	= null;
    	try {
			if(expenseListArr != null && expenseListArr.length() > 0) {
				vehicleHM	= new HashMap<String, List<TripSheetExpenseDto>>();
				TripSheetExpenseDto	expenseDto = null;
				for (int i = 0; i < expenseListArr.length(); i++) {
					JSONObject	jsonObject	=	expenseListArr.getJSONObject(i);
					if(jsonObject.has("vid") && jsonObject.get("vid") != null && jsonObject.getInt("vid") > 0) {
						expenseDto	= new TripSheetExpenseDto();
						expenseDto.setVid(jsonObject.getInt("vid"));
						expenseDto.setTripSheetId(jsonObject.getLong("tripSheetId"));
						expenseDto.setCredit(jsonObject.getBoolean("credit"));
						expenseDto.setVendorName(jsonObject.getString("vendorName"));
						expenseDto.setTripExpenseID(jsonObject.getLong("tripExpenseID"));
						if(jsonObject.getString("expenseName").equalsIgnoreCase("Diesel EXP") || jsonObject.getString("expenseName").equalsIgnoreCase("Diesel")) {
							expenseDto.setExpenseName("Diesel Expense");
						}else {
							expenseDto.setExpenseName("Vehicle Fuel Expenses");
						}
						
						if(jsonObject.has("expenseId") && !jsonObject.isNull("expenseId"))
							expenseDto.setExpenseId(jsonObject.getInt("expenseId"));
						expenseDto.setExpenseAmount(jsonObject.getDouble("expenseAmount"));
						expenseDto.setLedgerName(jsonObject.getString("ledgerName"));
						expenseDto.setRemark(jsonObject.getString("remark"));
						expenseDto.setVoucherDate(jsonObject.getString("voucherDate"));
						expenseDto.setExpenseTypeId(Short.parseShort(jsonObject.get("expenseTypeId")+""));
						
						vehicleLIst	= vehicleHM.get(expenseDto.getVid()+"_"+expenseDto.getTripSheetId()+"_"+expenseDto.getVoucherDate()+"_"+expenseDto.getTripExpenseID());
						if(vehicleLIst == null || vehicleLIst.isEmpty()) {
							vehicleLIst	= new ArrayList<>();
						}
						vehicleLIst.add(expenseDto);
						
						vehicleHM.put(expenseDto.getVid()+"_"+expenseDto.getTripSheetId()+"_"+expenseDto.getVoucherDate()+"_"+expenseDto.getTripExpenseID(), vehicleLIst);
					}
				}
			}
			
    		return vehicleHM;
		} catch (Exception e) {
			throw e;
		}finally {
			vehicleHM	= null;
			vehicleLIst	= null;
		}
    }
    
    public static void openVoucherListPopUp(JSONArray	expenseListArr, JSONArray	vendorPaymentArr, JSONArray	invoiceListArr, String selectedCompany,
    		JSONArray	vehicleExpListArr, JSONArray	driverBalanceListArr) throws Exception{
    	MyTableModel	model = new MyTableModel();
    	model.showFrame(expenseListArr,vendorPaymentArr, invoiceListArr, selectedCompany, vehicleExpListArr, driverBalanceListArr);
    }
    
    public  void getFinalListToSync(JSONArray	expenseListArr, JSONArray	vendorPaymentArr, JSONArray	invoiceListArr, HashMap<String, 
    			String> syncListHM, JSONArray vehicleExpListArr, JSONArray	driverBalanceListArr) throws Exception {
    	try {
			
    		JSONArray fianalExpenseArr = new JSONArray(); 
    		JSONArray fianalInvoiceArr = new JSONArray(); 
    		JSONArray fianalPAymentArr = new JSONArray();
    		JSONArray fianalVehicleArr = new JSONArray();
    		JSONArray fianalDriverBalanceArr = new JSONArray();
    		String selectedCompany = syncListHM.get("selectedCompany");
    		
    		String tripExpenseIds = "";
    		String tripSheetIds = "";
    		String fuelIds = "";
    		String serviceEntriesIds = "";
    		String renewalIds = "";
    		String tyreInvoiceIds = "";
    		String partInvoiceIds = "";
    		String batteryInvoiceIds = "";
    		String ureaInvoiceIds = "";
    		String clothInoviceIds = "";
    		String vendorPaymentIds = "";
    		String vehicleListIds   = "";
    		String tripIdsForDBal   = "";
    		
    		
    		HashMap<String, String>  insertedHM = new HashMap<String, String>();
    		
    		if (expenseListArr != null && expenseListArr.length() > 0) { 
    			for (int i=0;i<expenseListArr.length();i++){ 	
    				JSONObject	jsonObject	=	expenseListArr.getJSONObject(i);
    				String ids = syncListHM.get("expenseIds");
    				if(ids != null && !ids.trim().equalsIgnoreCase("")) {
    					String [] idsArr =  ids.split(",");
    					for(int j = 0; j< idsArr.length ; j++) {
    						if (jsonObject.getLong("tripExpenseID") == Long.parseLong(idsArr[j]) ){
    							fianalExpenseArr.put(expenseListArr.get(i));
    							if(jsonObject.getString("tripSheetNumberStr").contains("TS") && jsonObject.getString("expenseName").contains("Fasttag")) {
    								tripSheetIds += jsonObject.getLong("tripExpenseID")+",";
    							}else if(jsonObject.getString("tripSheetNumberStr").contains("F")) {
    								fuelIds += jsonObject.getLong("tripExpenseID")+",";
    							}else if(jsonObject.getString("tripSheetNumberStr").contains("SE")) {
    								serviceEntriesIds += jsonObject.getLong("tripExpenseID")+",";
    							}else if(jsonObject.getString("tripSheetNumberStr").contains("RR")) {
    								renewalIds += jsonObject.getLong("tripExpenseID")+",";
    							}else {
    								tripExpenseIds += jsonObject.getLong("tripExpenseID")+",";
    							}
    						}
    					}
    				}
    			} 
    		}
    		if (invoiceListArr != null && invoiceListArr.length() > 0) { 
    			for (int i=0;i<invoiceListArr.length();i++){ 	
    				JSONObject	jsonObject	=	invoiceListArr.getJSONObject(i);
    				String ids = syncListHM.get("invoiceIds");
    				if(ids != null && !ids.trim().equalsIgnoreCase("")) {
    					String [] idsArr =  ids.split(",");
    					for(int j = 0; j< idsArr.length ; j++) {
    						if (jsonObject.getString("tripSheetNumberStr").equalsIgnoreCase(idsArr[j]) ){
    							fianalInvoiceArr.put(invoiceListArr.get(i));
    							
    							if(jsonObject.getString("tripSheetNumberStr").contains("TI")) {
    								tyreInvoiceIds += jsonObject.getLong("tripExpenseID")+",";
    							}else if(jsonObject.getString("tripSheetNumberStr").contains("PI")) {
    								partInvoiceIds += jsonObject.getLong("tripExpenseID")+",";
    							}else if(jsonObject.getString("tripSheetNumberStr").contains("BI")) {
    								batteryInvoiceIds += jsonObject.getLong("tripExpenseID")+",";
    							}else if(jsonObject.getString("tripSheetNumberStr").contains("CI")) {
    								clothInoviceIds += jsonObject.getLong("tripExpenseID")+",";
    							}else if(jsonObject.getString("tripSheetNumberStr").contains("UI")) {
    								ureaInvoiceIds += jsonObject.getLong("tripExpenseID")+",";
    							}
    						}
    					}
    				}
    			} 
    		}
    		
    		if (vendorPaymentArr != null && vendorPaymentArr.length() > 0) { 
    			for (int i=0;i<vendorPaymentArr.length();i++){ 	
    				JSONObject	jsonObject	=	vendorPaymentArr.getJSONObject(i);
    				String ids = syncListHM.get("paymentIds");
    				if(ids != null && !ids.trim().equalsIgnoreCase("")) {
    					String [] idsArr =  ids.split(",");
    					for(int j = 0; j< idsArr.length ; j++) {
    						if (jsonObject.getString("tripSheetNumberStr").equalsIgnoreCase(idsArr[j]) ){
    							fianalPAymentArr.put(vendorPaymentArr.get(i));
    							vendorPaymentIds += jsonObject.getLong("tripExpenseID")+",";
    						}
    					}
    				}
    			} 
    		}
    		if (vehicleExpListArr != null && vehicleExpListArr.length() > 0) { 
    			for (int i=0;i<vehicleExpListArr.length();i++){ 	
    				JSONObject	jsonObject	=	vehicleExpListArr.getJSONObject(i);
    				String ids = syncListHM.get("vehicleListIdsToSync");
    				if(ids != null && !ids.trim().equalsIgnoreCase("")) {
    					String [] idsArr =  ids.split(",");
    					for(int j = 0; j< idsArr.length ; j++) {
    						if (jsonObject.getString("tripSheetNumberStr").equalsIgnoreCase(idsArr[j]) ){
    							fianalVehicleArr.put(vehicleExpListArr.get(i));
    							 vehicleListIds += jsonObject.getLong("tripExpenseID")+",";
    						}
    					}
    				}
    			} 
    		}
    		if(!tallyPushForVehicle)
    			vehicleHM = getVehicleTripWiseList(fianalExpenseArr);
    		
    		if (driverBalanceListArr != null && driverBalanceListArr.length() > 0) { 
    			for (int i=0;i<driverBalanceListArr.length();i++){ 	
    				JSONObject	jsonObject	=	driverBalanceListArr.getJSONObject(i);
    				String ids = syncListHM.get("expenseIds");
    				if(ids != null && !ids.trim().equalsIgnoreCase("")) {
    					String [] idsArr =  ids.split(",");
    					for(int j = 0; j< idsArr.length ; j++) {
    						if (jsonObject.getString("tripSheetNumberStr").split("_")[1].equalsIgnoreCase(idsArr[j]) ){
    							fianalDriverBalanceArr.put(jsonObject);
    							tripIdsForDBal += jsonObject.getLong("tripExpenseID")+",";
    						}
    					}
    				}
    			} 
    		}
    		
    		
    		try {
    			voucher	= createRequest(fianalExpenseArr, selectedCompany, vehicleHM, fianalPAymentArr, fianalInvoiceArr, fianalVehicleArr, fianalDriverBalanceArr);
			} catch (Exception e) {
				System.err.println("Excep "+e);
			}
    		
    		System.err.println("insdsaasd 6.. "+voucher);
    		if(voucher != null ) {
    			boolean isSuccess = SendToTally(voucher.replace("&", "AND"));
    			if(isSuccess) {
    				insertedHM.put("tripExpenseIds", tripExpenseIds);
    				insertedHM.put("tripSheetIds", tripSheetIds);
    				insertedHM.put("fuelIds", fuelIds);
    				insertedHM.put("serviceEntriesIds", serviceEntriesIds);
    				insertedHM.put("renewalIds", renewalIds);
    				insertedHM.put("tyreInvoiceIds", tyreInvoiceIds);
    				insertedHM.put("partInvoiceIds", partInvoiceIds);
    				insertedHM.put("batteryInvoiceIds", batteryInvoiceIds);
    				insertedHM.put("ureaInvoiceIds", ureaInvoiceIds);
    				insertedHM.put("clothInoviceIds", clothInoviceIds);
    				insertedHM.put("vendorPaymentIds", vendorPaymentIds);
    				insertedHM.put("vehicleListIds", vehicleListIds);
    				insertedHM.put("tripIdsForDBal", tripIdsForDBal);
    				updateTallyStatusToDB(insertedHM);
    			}
    		}
		} catch (ConnectException e) {
			JOptionPane.showMessageDialog(null, "Unable To Connect With Tally, Please Make Sure Tally Is Running!");
		}catch (Exception e) {
			
		}
    }
    
    public void updateTallyStatusToDB(HashMap<String, String>  insertedHM) throws Exception{
    	try {
    		Properties props = new Properties();
            InputStream  inputStream = this.getClass().getClassLoader().getResourceAsStream("configuration.properties");
            props.load(inputStream);


            companyId		= Integer.parseInt(props.getProperty("companyId"));
    		 String updateUrl 	= props.getProperty("updateUrl");
    			JSONObject jsonobj = new JSONObject();
    		 	
    		    jsonobj.put("insertedHM", insertedHM);
    		    jsonobj.put("companyId",companyId);
    		    Utility.excutePost(updateUrl, jsonobj.toString());
    		    
		} catch (Exception e) {
			throw e;
		}
    }
}
