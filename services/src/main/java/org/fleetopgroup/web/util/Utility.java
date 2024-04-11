package org.fleetopgroup.web.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.constant.MyTrustManager;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.VehicleGPSData;
import org.json.JSONObject;
import org.springframework.security.core.context.SecurityContextHolder;

public class Utility {
	
	private static final String HIBERNATE_ESCAPE_CHAR = "\\";
	
	public static String removeLastComma(String	string) throws Exception{
		try {
			if(string != null) {
				if (string.endsWith(",")) {
					string = string.substring(0, string.length() - 1);
					}
			}
			return string;
		} catch (Exception e) {
			throw e;
		}
	}
	
	 public static String convertHexToString(String hex){

		  StringBuilder sb = new StringBuilder();
		  StringBuilder temp = new StringBuilder();
		  
		  for( int i=0; i<hex.length()-1; i+=2 ){
			  
		      //grab the hex in pairs
		      String output = hex.substring(i, (i + 2));
		      //convert hex to decimal
		      int decimal = Integer.parseInt(output, 16);
		      //convert the decimal to character
		      sb.append((char)decimal);
			  
		      temp.append(decimal);
		  }
		  
		  return sb.toString();
	  }
	 

	 public static String getLongArrayToString(Long[] arr) throws Exception {

		 String arrStr = null;

		 try {

			 arrStr = Arrays.toString(arr).replace(", ", ",");
			 arrStr = arrStr.substring(1, arrStr.length() - 1);
			 return arrStr;

		 } catch (Exception e) {
			 throw e;
		 } finally {
			 arrStr = null;
		 }
	 }

	 
	 public static String getClientIpAddr(HttpServletRequest request) {  
	        String ip = request.getHeader("X-Forwarded-For");  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getHeader("Proxy-Client-IP");  
	        }  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getHeader("WL-Proxy-Client-IP");  
	        }  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getHeader("HTTP_CLIENT_IP");  
	        }  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
	        }  
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	            ip = request.getRemoteAddr();  
	        }  
	        return ip;  
	    }  
	 
	 
	  public static String replaceAll(String value) {
	        return value
	                .replace("\\",  HIBERNATE_ESCAPE_CHAR + "\\")
	                .replace("_",   HIBERNATE_ESCAPE_CHAR + "_")
	                .replace("%",   HIBERNATE_ESCAPE_CHAR + "%");
	 
	    }
	  
	  
	  public static Integer calculateRunKM(double lat1, double lat2, double lon1,
		        double lon2, double el1, double el2) {

		    final int R = 6371; // Radius of the earth

		    double latDistance = Math.toRadians(lat2 - lat1);
		    double lonDistance = Math.toRadians(lon2 - lon1);
		    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
		            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
		            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		    Double distance = R * c * 1000; // convert to meters

		    double height = el1 - el2;

		    distance = Math.pow(distance, 2) + Math.pow(height, 2);
		    distance =  Math.sqrt(distance);
		    return distance.intValue();
		}
	  
	  public static Integer calculateRunKM(List<VehicleGPSData> vehicleGPSDataList) throws Exception{
		  Integer	runKM	= 0;
		  int		count	= 0;
		  double    lat1    = 0.0;
		  double    lat2    = 0.0;
		  double 	lon1    = 0.0;
		  double 	lon2    = 0.0;
		  try {
			 for(VehicleGPSData vehicleGPSData : vehicleGPSDataList) {
				 if(count == 0) {
					 lat1    = Double.parseDouble(vehicleGPSData.getLatitude());
					 lat2    = Double.parseDouble(vehicleGPSData.getLatitude());
					 lon1    = Double.parseDouble(vehicleGPSData.getLongitude());
					 lon2    = Double.parseDouble(vehicleGPSData.getLongitude());
					 runKM += calculateRunKM(lat1, lon1, lat2, lon2);
				 }else {
					 lat1    = lat2;
					 lat2    = Double.parseDouble(vehicleGPSData.getLatitude());
					 lon1    = lon2;
					 lon2    = Double.parseDouble(vehicleGPSData.getLongitude());
					 
					 runKM += calculateRunKM(lat1, lon1, lat2, lon2);
				 
				 }
				 count ++;
			 }
			 return runKM;
		} catch (Exception e) {
			throw e;
		}
		  
	  }
	  
	  public static Integer calculateRunKM(double lat1, double lon1, double lat2, double lon2) {
		
		  double theta = lon1 - lon2;
		  Double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		  dist = Math.acos(dist);
		  dist = rad2deg(dist);
		  dist = dist * 60 * 1.1515;
		   
		  dist = dist * 1.609344;
		 
		  return (dist.intValue());
		}
		/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
		/*::  This function converts decimal degrees to radians             :*/
		/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
		private static double deg2rad(double deg) {
		  return (deg * Math.PI / 180.0);
		}
		/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
		/*::  This function converts radians to decimal degrees             :*/
		/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
		private static double rad2deg(double rad) {
		  return (rad * 180.0 / Math.PI);
		}

	  
	  
	  public static String getStringArrayToString(String[] arr) throws Exception {
			 
			 String arrStr = null;
			 
			 try {
				 
				 arrStr = Arrays.toString(arr).replace(", ", ",");
				 arrStr = arrStr.substring(1, arrStr.length() - 1);
				 return arrStr;
				 
			 } catch (Exception e) {
				 throw e;
			 } finally {
				 arrStr = null;
			 }
		 }

	  public static double round(Double value, int places) {
		  	if(value == null) {
		  		value = 0.0;
		  	}
			long factor = (long) Math.pow(10, places);
			value = value * factor;
			long tmp = Math.round(value);
			return (double) tmp / factor;
		}
	  
	  public static boolean getBoolean(String value) {
		  try {
			  if(value != null && value.equalsIgnoreCase("true")) {
				  return true;
			  }
			  return false;
		} catch (Exception e) {
			return false;
		}
	  }
	  
	  public static ValueObject setUniqueTokenInSession(ValueObject	valueObject, HttpServletRequest request) throws Exception {
		  
		  	valueObject.put("accessToken",  getUniqueToken(request));
			
			return valueObject;
	  }
	  
	  public static HashMap<String, Object> setUniqueTokenInSession(HashMap<String, Object>	valueObject, HttpServletRequest request) throws Exception {
		  
		  	valueObject.put("accessToken",  getUniqueToken(request));
			
			return valueObject;
	  }
	  
	  
	  public static String getUniqueToken(HttpServletRequest request) {
		  
		  String uniqueID = UUID.randomUUID().toString();
		
		  request.getSession().setAttribute(uniqueID, uniqueID);
			
		  return uniqueID;
	  }
	  public static CustomUserDetails getObject(ValueObject object){
		  CustomUserDetails 	userDetails 				= null;
		  try{
			  if(object!=null && object.getBoolean("isFromMob",false)){
				  userDetails = new CustomUserDetails();
				  userDetails.setCompany_id(object.getInt("companyId",0));
				  userDetails.setId(object.getLong("userId",0));
			  }else{
				  userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			  }
			  return userDetails;
		  }catch(Exception e){
			  return null;
		  }
	  }
	  
	 public static double calculateTotalCost(double quantity, double unitCost, double gst, double discount) throws Exception {
			double totalUnitCost	 	= 0.0;
			double discountCost 		= 0.0;
			double totalCost 			= 0.0;
			try {
				
				totalUnitCost 	= quantity * unitCost;
				discountCost	= totalUnitCost -(totalUnitCost * discount/100);
				totalCost		= discountCost + (discountCost * gst/100);
				
				return totalCost;
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			
		}
	  
	  public static String HmacSHA256(String message, String secret) {
		  try {
		  Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		  SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
		  sha256_HMAC.init(secret_key);
		  byte raw[] = sha256_HMAC.doFinal(message.getBytes());
		  // System.out.println(raw.toString());
		  StringBuffer ls_sb = new StringBuffer();
		  for (int i = 0; i < raw.length; i++) {
		  ls_sb.append(char2hex(raw[i]));
		  }
		  return ls_sb.toString(); // step 6
		  } catch (Exception e) {
		  e.printStackTrace();
		  return null;
		  }
		  }
		  private static String char2hex(byte x) {
		  char arr[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		  char c[] = { arr[(x & 0xF0) >> 4], arr[x & 0x0F] };
		  return (new String(c));
		  }
		  
		  public static JSONObject createJson()throws Exception
		  {
			  try{
				  JSONObject requestDetails=new JSONObject();
				  requestDetails.put("requestTime", "20210215125830");
				  requestDetails.put("fromDate", "20210428");
				  requestDetails.put("walletId", "W0121040116565530053");
				  requestDetails.put("merchantID", "C210910066");
				  requestDetails.put("requestID", "001");
				  requestDetails.put("toDate", "20210429");
				  requestDetails.put("contactNumber", "");
				  requestDetails.put("vehicleNumber", "");
				  requestDetails.put("requestSource", "BD");

				  return requestDetails;
			  }catch(Exception e){
				  e.printStackTrace();
				  return null;
			  }
		  }
		  
		  public static double calculateTotalCostByGstDisAmount(double quantity, double unitCost, double gstCost, double discountCost) throws Exception {
				double totalUnitCost	 	= 0.0;
				double totalCost	 	= 0.0;
				try {
					
					totalUnitCost 	= quantity * unitCost;
					discountCost	= totalUnitCost -(discountCost);
					totalCost		= discountCost + (gstCost);
					
					return totalCost;
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
				
			}
		  
		  public static void disableSSL() {
		        try {
		           TrustManager[] trustAllCerts = new TrustManager[] { new   MyTrustManager() };

		      // Install the all-trusting trust manager
		      SSLContext sc = SSLContext.getInstance("SSL");
		      sc.init(null, trustAllCerts, new java.security.SecureRandom());
		      HostnameVerifier allHostsValid = new HostnameVerifier() {
		          public boolean verify(String hostname, SSLSession session) {
		              return true;
		          }
		      };
		      HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		      HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		  } catch (Exception e) {
		      e.printStackTrace();
		  }}
		
		  public static void print(String str){
				System.out.println(str);
			}
		
}
