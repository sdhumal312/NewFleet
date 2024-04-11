package org.fleetopgroup.constant;

import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class RestPApiClient {
  public static void main(String[] args) throws UnirestException {
	  
	  /*	String url =  "https://fastaglogin.icicibank.com/ISRCUSTAPI/Customer/GetTransactionDetails";
		RestTemplate	restTemplate	= new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("APIClient_ID", "10006159");
		headers.set("API_KEY", "AE901B4070AEC51F9E62470E72097A18763A62033DE667273395DD4598DBBF3D1C3389");
		headers.setContentType(MediaType.APPLICATION_JSON);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("CustomerId", "10006159");
		map.add("VehicleNumber", "KA51AA6117");
		map.add("StartTransactionDate", "2018-10-01");
		map.add("EndTransactionDate", "2018-10-16");
		
		final HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(map ,
		        headers);
	     
		 ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

		 System.err.println("result "+result);*/
	  
	  try {
		 
		  JSONObject obj=new JSONObject();    
		  obj.put("CustomerId",10091598);    
		  obj.put("StartTransactionDate","2018-10-16");    
		  obj.put("EndTransactionDate","2018-10-23"); 
		  obj.put("VehicleNumber","GJ15AT5758"); 
		  
		  HttpResponse<JsonNode> response = Unirest.post("https://fastaglogin.icicibank.com/ISRCUSTAPI/Customer/GetTransactionDetails")
				  .header("API_KEY", "AE901B4070AEC51F9E62470E72097A18763A62033DE667273395DD4598DBBF3D1C3389")
				  .header("Content-Type", "application/json")
				  .header("APIClient_ID", "10006159")
				  .body(obj)
				  .asJson();
		  
		  System.out.println("response : "+response.getBody().getObject());
		  
		  Unirest.shutdown();

	} catch (Exception e) {
		System.err.println("Exception "+e);
	}
	  
	
		
  }
}
