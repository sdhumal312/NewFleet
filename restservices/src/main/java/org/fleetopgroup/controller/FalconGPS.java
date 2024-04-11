package org.fleetopgroup.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.ValueObject;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

@RestController
public class FalconGPS {
	
	SimpleDateFormat dateFormat 		= new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
	SimpleDateFormat time 				= new SimpleDateFormat("hh:mm aa");
	SimpleDateFormat displayFormat 		= new SimpleDateFormat("HH:mm");
    SimpleDateFormat parseFormat 		= new SimpleDateFormat("hh:mm a");
    SimpleDateFormat date1 				= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat date2 				= new SimpleDateFormat("dd-MM-yyyy");
	
	@RequestMapping(value = { "/getFalconVehicleBusId" }, produces = { "application/json" }, method = { RequestMethod.POST })
    public HashMap<Object, Object> getFalconVehicleBusId(@RequestBody final HashMap<Object, Object> allRequestParams) throws Exception {
        ValueObject object = null;
        try {
            object = new ValueObject((HashMap)allRequestParams);
            System.err.println("inside getFalconVehicleBusId........");
            
            final JSONObject json = new JSONObject();
            json.put("VerifyCall", (Object)"hQ3A55qg553Y7V24e4e72zT8Aw39f2436kf45rv584524e37hMFT");
            
            final HttpResponse<String> response = (HttpResponse<String>)Unirest.post("http://apicargotrack.itspl.net/ITSGateway/API/BusMasterList").header("Content-Type", "application/json").body(json).asString();
            if (response != null) {
                System.err.println("response " + (String)response.getBody());
                object.put((Object)"response", response.getBody());
            }
            return (HashMap<Object, Object>)object.getHtData();
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            object = null;
        }
    }
	
	@RequestMapping(value = "/getITSGatewayIncome" , produces =  "application/json" , method =  RequestMethod.POST )
    public HashMap<Object, Object> getITSGatewayIncome(@RequestParam final HashMap<Object, Object> allRequestParams) throws Exception {
        ValueObject 		object 								= null;
        ValueObject 		incomeDetails						= null;
        try {
            object = new ValueObject((HashMap)allRequestParams);
            System.err.println("inside getITSGatewayIncome........");
            
            final JSONObject json = new JSONObject();
            
            final HttpResponse<String> response = (HttpResponse<String>)Unirest.post("https://demo.fleetop.in/restservices/getITSGatewayBusIncomeTest").header("Content-Type", "application/json").body(json).asString();
            System.err.println("response income " + response);
            
            if (response != null) {
                System.err.println("response " + (String)response.getBody());
                object.put((Object)"response", response.getBody());
                
                incomeDetails = JsonConvertor.toValueObjectFormSimpleJsonString((String)response.getBody());
                incomeDetails = JsonConvertor.toValueObjectFormSimpleJsonString((String)incomeDetails.get("response"));
                
                if(incomeDetails.get("data") != null) {
                	LinkedHashMap<Object, Object> incomeValue = (LinkedHashMap<Object, Object>) incomeDetails.get("data");
                	
                	if(!incomeValue.isEmpty() && incomeValue.get("Data") != null) {
                		ArrayList<LinkedHashMap<Object, Object>> income	= (ArrayList<LinkedHashMap<Object, Object>>) incomeValue.get("Data");
                		for (Map<Object, Object> entry : income) {
                			
                			String datestr = (String)entry.get("IncomeDateTime");
                			
                			java.util.Date date 		 	= dateFormat.parse(datestr);
                			
            				Timestamp datesql 				= new java.sql.Timestamp(date.getTime());
            				
                		}	 
                	}else {
                		System.err.println("empty hence no record...");
                	}
                }
                
            }
            return (HashMap<Object, Object>)object.getHtData();
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            object = null;
        }
    }
	
	@RequestMapping(value = "/getITSGatewayBusIncome" , produces =  "application/json" , method =  RequestMethod.POST )
    public HashMap<Object, Object> getITSGatewayBusIncome(@RequestBody final HashMap<Object, Object> allRequestParams) throws Exception {
        ValueObject object = null;
        try {
            object = new ValueObject((HashMap)allRequestParams);
            System.err.println("inside getITSGatewayBusIncome........");
            
            final JSONObject json = new JSONObject();
            json.put("FromDate", (Object)"2020-02-10 00:00:00");
            json.put("ToDate", (Object)"2020-02-10 00:00:00");
            json.put("VerifyCall", (Object)"hQ3A55qg553Y7V24e4e72zT8Aw39f2436kf45rv584524e37hMFT");
            json.put("BusID", (Object)15165);
            
            final HttpResponse<String> response = (HttpResponse<String>)Unirest.post("http://apicargotrack.itspl.net/ITSGateway/API/GetBus_Collection").header("Content-Type", "application/json").body(json).asString();
            System.err.println("response income " + response);
            
            if (response != null) {
                System.err.println("response " + (String)response.getBody());
                object.put((Object)"response", response.getBody());
            }
            return (HashMap<Object, Object>)object.getHtData();
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            object = null;
        }
    }
	
	@RequestMapping(value = "/getITSGatewayBusIncomeTest" , produces =  "application/json" , method =  RequestMethod.POST )
    public HashMap<Object, Object> getITSGatewayBusIncomeTest(@RequestBody final HashMap<Object, Object> allRequestParams) throws Exception {
        ValueObject object = null;
        try {
            object = new ValueObject((HashMap)allRequestParams);
            System.err.println("inside getITSGatewayBusIncome........");
            
            final JSONObject json = new JSONObject();
            json.put("FromDate", (Object)"2020-02-25 01:05:00");
            json.put("ToDate", (Object)"2020-02-26 20:45:00");
            json.put("VerifyCall", (Object)"hQ3A55qg553Y7V24e4e72zT8Aw39f2436kf45rv584524e37hMFT");
            json.put("BusID", (Object)15165);
            
            final HttpResponse<String> response = (HttpResponse<String>)Unirest.post("http://apicargotrack.itspl.net/ITSGateway/API/GetBus_Collection").header("Content-Type", "application/json").body(json).asString();
            System.err.println("response income " + response);
            
            if (response != null) {
                System.err.println("response " + (String)response.getBody());
                object.put((Object)"response", response.getBody());
            }
            return (HashMap<Object, Object>)object.getHtData();
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            object = null;
        }
    }
	
	@RequestMapping(value = "/getITSGatewayBusIncomeTest1" , produces =  "application/json" , method =  RequestMethod.POST )
    public HashMap<Object, Object> getITSGatewayBusIncomeTest1(@RequestBody final HashMap<Object, Object> allRequestParams) throws Exception {
        ValueObject object = null;
        try {
            object = new ValueObject((HashMap)allRequestParams);
            System.err.println("inside getITSGatewayBusIncome........");
            
            final JSONObject json = new JSONObject();
            json.put("FromDate", (Object)"2020-02-24 00:00:00");
            json.put("ToDate", (Object)"2020-02-24 23:59:58");
            json.put("VerifyCall", (Object)"hQ3A55qg553Y7V24e4e72zT8Aw39f2436kf45rv584524e37hMFT");
            json.put("BusID", (Object)15165);
            
            final HttpResponse<String> response = (HttpResponse<String>)Unirest.post("http://apicargotrack.itspl.net/ITSGateway/API/GetBus_Collection").header("Content-Type", "application/json").body(json).asString();
            System.err.println("response income " + response);
            
            if (response != null) {
                System.err.println("response " + (String)response.getBody());
                object.put((Object)"response", response.getBody());
            }
            return (HashMap<Object, Object>)object.getHtData();
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            object = null;
        }
    }
	
	@RequestMapping(value = "/getITSGatewayTimeTesting" , produces =  "application/json" , method =  RequestMethod.POST )
    public HashMap<Object, Object> getITSGatewayTimeTesting(@RequestBody final HashMap<Object, Object> allRequestParams) throws Exception {
        ValueObject object = null;
        try {
            object = new ValueObject((HashMap)allRequestParams);
            System.err.println("inside getITSGatewayBusIncome........");
            
            final JSONObject json = new JSONObject();
            json.put("FromDate", (Object)"2020-02-24 16:15:00.0");
            json.put("ToDate", (Object)"2020-02-25 19:40:00.0");
            json.put("VerifyCall", (Object)"hQ3A55qg553Y7V24e4e72zT8Aw39f2436kf45rv584524e37hMFT");
            json.put("BusID", (Object)15165);
            
            final HttpResponse<String> response = (HttpResponse<String>)Unirest.post("http://apicargotrack.itspl.net/ITSGateway/API/GetBus_Collection").header("Content-Type", "application/json").body(json).asString();
            System.err.println("response income " + response);
            
            if (response != null) {
                System.err.println("response " + (String)response.getBody());
                object.put((Object)"response", response.getBody());
            }
            return (HashMap<Object, Object>)object.getHtData();
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            object = null;
        }
    }
	
	@RequestMapping(value = "/getITSGatewayDriverDetails" , produces =  "application/json" , method =  RequestMethod.POST )
    public HashMap<Object, Object> getITSGatewayDriverDetails(@RequestParam final HashMap<Object, Object> allRequestParams) throws Exception {
        ValueObject 		object 								= null;
        ValueObject 		incomeDetails						= null;
        try {
            object = new ValueObject((HashMap)allRequestParams);
            System.err.println("inside getITSGatewayDriverDetails........");
            
            final JSONObject json = new JSONObject();
            
            final HttpResponse<String> response = (HttpResponse<String>)Unirest.post("https://demo.fleetop.in/restservices/getITSGatewayDriver").header("Content-Type", "application/json").body(json).asString();
            System.err.println("response income " + response);
            
            if (response != null) {
                System.err.println("response " + (String)response.getBody());
                object.put((Object)"response", response.getBody());
                
                incomeDetails = JsonConvertor.toValueObjectFormSimpleJsonString((String)response.getBody());
                incomeDetails = JsonConvertor.toValueObjectFormSimpleJsonString((String)incomeDetails.get("response"));
                
                if(incomeDetails.get("data") != null) {
                	LinkedHashMap<Object, Object> incomeValue = (LinkedHashMap<Object, Object>) incomeDetails.get("data");
                	
                	if(!incomeValue.isEmpty() && incomeValue.get("Data") != null) {
                		ArrayList<LinkedHashMap<Object, Object>> income	= (ArrayList<LinkedHashMap<Object, Object>>) incomeValue.get("Data");
                		for (Map<Object, Object> entry : income) {
                			
                			/*String routeTime = (String) entry.get("RouteTime");
                			System.err.println("routeTime..."+routeTime);
                			
                			String finalroute = displayFormat.format((Date)parseFormat.parse("04:00 AM"));
                			System.err.println("finalroute.."+finalroute);*/
                			
                			String closetripdate = date2.format((Date)date1.parse("2020-02-25 00:00:00"));
                			
                			String routeTime = "18:00";
                			
                			java.util.Date ola = DateTimeUtility.getDateTimeFromDateTimeString(closetripdate, routeTime);
                			
                			java.util.Date Reported_Date = new java.util.Date(ola.getTime());
                			
                			Reported_Date.setTime(Reported_Date.getTime() - 1800 * 1000);
                			
                			
                			String one = date1.format(Reported_Date);
            				
                		}	 
                	}else {
                		System.err.println("empty hence no record...");
                	}
                }
                
            }
            return (HashMap<Object, Object>)object.getHtData();
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            object = null;
        }
    }
	
	@RequestMapping(value = "/getITSGatewayDriver" , produces =  "application/json" , method =  RequestMethod.POST )
    public HashMap<Object, Object> getITSGatewayDriver(@RequestBody final HashMap<Object, Object> allRequestParams) throws Exception {
        ValueObject object = null;
        try {
            object = new ValueObject((HashMap)allRequestParams);
            System.err.println("inside getITSGatewayDriver........");
            
            final JSONObject json = new JSONObject();
            json.put("FromDate", (Object)"2020-02-10 00:00:00");
            json.put("ToDate", (Object)"2020-02-10 00:00:00");
            json.put("VerifyCall", (Object)"hQ3A55qg553Y7V24e4e72zT8Aw39f2436kf45rv584524e37hMFT");
            json.put("BusID", (Object)15165);
            
            final HttpResponse<String> response = (HttpResponse<String>)Unirest.post("http://apicargotrack.itspl.net/ITSGateway/API/GetBus_DriverDetail").header("Content-Type", "application/json").body(json).asString();
            System.err.println("response driver " + response);
            
            if (response != null) {
                System.err.println("response " + (String)response.getBody());
                object.put((Object)"response", response.getBody());
            }
            return (HashMap<Object, Object>)object.getHtData();
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            object = null;
        }
    }
	
	@RequestMapping(value = "/getITSGatewayDriverTest" , produces =  "application/json" , method =  RequestMethod.POST )
    public HashMap<Object, Object> getITSGatewayDriverTest(@RequestBody final HashMap<Object, Object> allRequestParams) throws Exception {
        ValueObject object = null;
        try {
            object = new ValueObject((HashMap)allRequestParams);
            System.err.println("inside getITSGatewayDriver........");
            
            final JSONObject json = new JSONObject();
            json.put("FromDate", (Object)"2020-02-10 05:00:00");
            json.put("ToDate", (Object)"2020-02-10 05:00:00");
            json.put("VerifyCall", (Object)"hQ3A55qg553Y7V24e4e72zT8Aw39f2436kf45rv584524e37hMFT");
            json.put("BusID", (Object)15165);
            
            final HttpResponse<String> response = (HttpResponse<String>)Unirest.post("http://apicargotrack.itspl.net/ITSGateway/API/GetBus_DriverDetail").header("Content-Type", "application/json").body(json).asString();
            System.err.println("response driver " + response);
            
            if (response != null) {
                System.err.println("response " + (String)response.getBody());
                object.put((Object)"response", response.getBody());
            }
            return (HashMap<Object, Object>)object.getHtData();
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            object = null;
        }
    }
	
	@RequestMapping(value = "/getITSGatewayDriverTest1" , produces =  "application/json" , method =  RequestMethod.POST )
    public HashMap<Object, Object> getITSGatewayDriverTest1(@RequestBody final HashMap<Object, Object> allRequestParams) throws Exception {
        ValueObject object = null;
        try {
        	object 				= new ValueObject(allRequestParams);
            System.err.println("inside getITSGatewayDriver........"+object);
            
            final JSONObject json = new JSONObject();
            json.put("FromDate", object.getString("FromDate"));
            json.put("ToDate",  object.getString("ToDate"));
            json.put("VerifyCall", object.getString("VerifyCall"));
            json.put("BusID", object.getLong("BusID"));
            
            final HttpResponse<String> response = (HttpResponse<String>)Unirest.post("http://apicargotrack.itspl.net/ITSGateway/API/GetBus_DriverDetail").header("Content-Type", "application/json").body(json).asString();
            System.err.println("response driver " + response);
            
            if (response != null) {
                System.err.println("response " + (String)response.getBody());
                object.put((Object)"response", response.getBody());
            }
            return (HashMap<Object, Object>)object.getHtData();
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            object = null;
        }
    }
	
	@RequestMapping(value = "/getITSGatewayDriverTest2" , produces =  "application/json" , method =  RequestMethod.POST )
    public HashMap<Object, Object> getITSGatewayDriverTest2(@RequestBody final HashMap<Object, Object> allRequestParams) throws Exception {
        ValueObject object = null;
        try {
            object = new ValueObject((HashMap)allRequestParams);
            System.err.println("inside getITSGatewayDriver........");
            
            final JSONObject json = new JSONObject();
            json.put("FromDate", (Object)"2020-02-10 05:00:00");
            json.put("ToDate", (Object)"2020-02-10 05:01:00");
            json.put("VerifyCall", (Object)"hQ3A55qg553Y7V24e4e72zT8Aw39f2436kf45rv584524e37hMFT");
            json.put("BusID", (Object)15165);
            
            final HttpResponse<String> response = (HttpResponse<String>)Unirest.post("http://apicargotrack.itspl.net/ITSGateway/API/GetBus_DriverDetail").header("Content-Type", "application/json").body(json).asString();
            System.err.println("response driver " + response);
            
            if (response != null) {
                System.err.println("response " + (String)response.getBody());
                object.put((Object)"response", response.getBody());
            }
            return (HashMap<Object, Object>)object.getHtData();
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            object = null;
        }
    }
	
	@RequestMapping(value = "/getITSGatewayDriverTest3" , produces =  "application/json" , method =  RequestMethod.POST )
    public HashMap<Object, Object> getITSGatewayDriverTest3(@RequestBody final HashMap<Object, Object> allRequestParams) throws Exception {
        ValueObject object = null;
        try {
            object = new ValueObject((HashMap)allRequestParams);
            System.err.println("inside getITSGatewayDriver........");
            
            final JSONObject json = new JSONObject();
            json.put("FromDate", (Object)"2020-02-10 20:00:00");
            json.put("ToDate", (Object)"2020-02-10 23:58:58");
            json.put("VerifyCall", (Object)"hQ3A55qg553Y7V24e4e72zT8Aw39f2436kf45rv584524e37hMFT");
            json.put("BusID", (Object)15165);
            
            final HttpResponse<String> response = (HttpResponse<String>)Unirest.post("http://apicargotrack.itspl.net/ITSGateway/API/GetBus_DriverDetail").header("Content-Type", "application/json").body(json).asString();
            System.err.println("response driver " + response);
            
            if (response != null) {
                System.err.println("response " + (String)response.getBody());
                object.put((Object)"response", response.getBody());
            }
            return (HashMap<Object, Object>)object.getHtData();
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            object = null;
        }
    }
	
	@RequestMapping(value = "/getITSGatewayDriverTest4" , produces =  "application/json" , method =  RequestMethod.POST )
    public HashMap<Object, Object> getITSGatewayDriverTest4(@RequestBody final HashMap<Object, Object> allRequestParams) throws Exception {
        ValueObject object = null;
        try {
            object = new ValueObject((HashMap)allRequestParams);
            System.err.println("inside getITSGatewayDriver........");
            
            final JSONObject json = new JSONObject();
            json.put("FromDate", (Object)"2020-02-24 05:00:00");
            json.put("ToDate", (Object)"2020-02-26 23:58:58");
            json.put("VerifyCall", (Object)"hQ3A55qg553Y7V24e4e72zT8Aw39f2436kf45rv584524e37hMFT");
            json.put("BusID", (Object)15165);
            
            final HttpResponse<String> response = (HttpResponse<String>)Unirest.post("http://apicargotrack.itspl.net/ITSGateway/API/GetBus_DriverDetail").header("Content-Type", "application/json").body(json).asString();
            System.err.println("response driver " + response);
            
            if (response != null) {
                System.err.println("response " + (String)response.getBody());
                object.put((Object)"response", response.getBody());
            }
            return (HashMap<Object, Object>)object.getHtData();
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            object = null;
        }
    }
	
	@RequestMapping(value = "/getITSGatewayDriverTest5" , produces =  "application/json" , method =  RequestMethod.POST )
    public HashMap<Object, Object> getITSGatewayDriverTest5(@RequestBody final HashMap<Object, Object> allRequestParams) throws Exception {
        ValueObject object = null;
        try {
            object = new ValueObject((HashMap)allRequestParams);
            System.err.println("inside getITSGatewayDriver........");
            
            final JSONObject json = new JSONObject();
            json.put("FromDate", (Object)"2020-02-23 05:00:00");
            json.put("ToDate", (Object)"2020-02-23 23:58:58");
            json.put("VerifyCall", (Object)"hQ3A55qg553Y7V24e4e72zT8Aw39f2436kf45rv584524e37hMFT");
            json.put("BusID", (Object)15165);
            
            final HttpResponse<String> response = (HttpResponse<String>)Unirest.post("http://apicargotrack.itspl.net/ITSGateway/API/GetBus_DriverDetail").header("Content-Type", "application/json").body(json).asString();
            System.err.println("response driver " + response);
            
            if (response != null) {
                System.err.println("response " + (String)response.getBody());
                object.put((Object)"response", response.getBody());
            }
            return (HashMap<Object, Object>)object.getHtData();
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            object = null;
        }
    }
}