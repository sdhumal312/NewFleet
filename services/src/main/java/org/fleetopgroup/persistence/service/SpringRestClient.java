package org.fleetopgroup.persistence.service;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.fleetopgroup.constant.MyTrustManager;
import org.fleetopgroup.persistence.dto.AuthTokenInfo;
import org.fleetopgroup.persistence.dto.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

 
public class SpringRestClient {
	
 
    public static final String REST_SERVICE_URI = "http://localhost:8080/SpringSecurityOAuth2Example";
    
    public static final String AUTH_SERVER_URI = "https://14.141.77.61:5443/TRANSPORTAPI/oauth/token";
    
    public static final String QPM_PASSWORD_GRANT = "?grant_type=password&username=bill&password=abc123";
    
    public static final String QPM_ACCESS_TOKEN = "?access_token=";

    /*
     * Prepare HTTP Headers.
     */
    private static HttpHeaders getHeaders(){
    	HttpHeaders headers = new HttpHeaders();
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	return headers;
    }
    
    /*
     * Add HTTP Authorization header, using Basic-Authentication to send client-credentials.
     */
    private static HttpHeaders getHeadersWithClientCredentials(){
    	
    	String base64ClientCredentials = "Nzg1OGZlM2UtZWI0OS00ZDAyLTk2MDktMjlhMjgyNmIzZ2doOmExMmdhOGQyLTQ0NzAtNGFhZS05ZDZkLWIzNGU0ZDgxMzIwYQ==";
    	
    	HttpHeaders headers = getHeaders();
    	headers.add("Authorization", "Basic " + base64ClientCredentials);
    	return headers;
    }    
    
    /*
     * Send a POST request [on /oauth/token] to get an access-token, which will then be send with each request.
     */
    @SuppressWarnings({ "unchecked"})
	private static AuthTokenInfo sendTokenRequest(){
        RestTemplate restTemplate = new RestTemplate(); 
        
        HttpEntity<String> request = new HttpEntity<String>(getHeadersWithClientCredentials());
        ResponseEntity<Object> response = restTemplate.exchange(AUTH_SERVER_URI, HttpMethod.POST, request, Object.class);
        LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>)response.getBody();
        AuthTokenInfo tokenInfo = null;
        
        if(map!=null){
        	tokenInfo = new AuthTokenInfo();
        	tokenInfo.setAccess_token((String)map.get("access_token"));
        	tokenInfo.setToken_type((String)map.get("token_type"));
        	tokenInfo.setRefresh_token((String)map.get("refresh_token"));
        	//tokenInfo.setExpires_in((int)map.get("expires_in"));
        	tokenInfo.setScope((String)map.get("scope"));
        	System.out.println(tokenInfo);
        	//System.out.println("access_token ="+map.get("access_token")+", token_type="+map.get("token_type")+", refresh_token="+map.get("refresh_token")
        	//+", expires_in="+map.get("expires_in")+", scope="+map.get("scope"));;
        }else{
            System.out.println("No user exist----------");
            
        }
        return tokenInfo;
    }
    
    /*
     * Send a GET request to get list of all users.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static void listAllUsers(AuthTokenInfo tokenInfo){
    	Assert.notNull(tokenInfo, "Authenticate first please......");

    	System.out.println("\nTesting listAllUsers API-----------");
        RestTemplate restTemplate = new RestTemplate(); 
        
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        ResponseEntity<List> response = restTemplate.exchange(REST_SERVICE_URI+"/user/"+QPM_ACCESS_TOKEN+tokenInfo.getAccess_token(),
        		HttpMethod.GET, request, List.class);
        List<LinkedHashMap<String, Object>> usersMap = (List<LinkedHashMap<String, Object>>)response.getBody();
        
        if(usersMap!=null){
            for(LinkedHashMap<String, Object> map : usersMap){
                System.out.println("User : id="+map.get("id")+", Name="+map.get("name")+", Age="+map.get("age")+", Salary="+map.get("salary"));;
            }
        }else{
            System.out.println("No user exist----------");
        }
    }
     
    /*
     * Send a GET request to get a specific user.
     */
    private static void getUser(AuthTokenInfo tokenInfo){
    	Assert.notNull(tokenInfo, "Authenticate first please......");
        System.out.println("\nTesting getUser API----------");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        ResponseEntity<User> response = restTemplate.exchange(REST_SERVICE_URI+"/user/1"+QPM_ACCESS_TOKEN+tokenInfo.getAccess_token(),
        		HttpMethod.GET, request, User.class);
        User user = response.getBody();
        System.out.println(user);
    }
     
    /*
     * Send a POST request to create a new user.
     */
    private static void createUser(AuthTokenInfo tokenInfo) {
    	Assert.notNull(tokenInfo, "Authenticate first please......");
        System.out.println("\nTesting create User API----------");
        RestTemplate restTemplate = new RestTemplate();
        User user = new User(0,"Sarah",51,134);
        HttpEntity<Object> request = new HttpEntity<Object>(user, getHeaders());
        URI uri = restTemplate.postForLocation(REST_SERVICE_URI+"/user/"+QPM_ACCESS_TOKEN+tokenInfo.getAccess_token(),
        		request, User.class);
        System.out.println("Location : "+uri.toASCIIString());
    }
 
    /*
     * Send a PUT request to update an existing user.
     */
    private static void updateUser(AuthTokenInfo tokenInfo) {
    	Assert.notNull(tokenInfo, "Authenticate first please......");
        System.out.println("\nTesting update User API----------");
        RestTemplate restTemplate = new RestTemplate();
        User user  = new User(1,"Tomy",33, 70000);
        HttpEntity<Object> request = new HttpEntity<Object>(user, getHeaders());
        ResponseEntity<User> response = restTemplate.exchange(REST_SERVICE_URI+"/user/1"+QPM_ACCESS_TOKEN+tokenInfo.getAccess_token(),
        		HttpMethod.PUT, request, User.class);
        System.out.println(response.getBody());
    }
 
    /*
     * Send a DELETE request to delete a specific user.
     */
    private static void deleteUser(AuthTokenInfo tokenInfo) {
    	Assert.notNull(tokenInfo, "Authenticate first please......");
        System.out.println("\nTesting delete User API----------");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        restTemplate.exchange(REST_SERVICE_URI+"/user/3"+QPM_ACCESS_TOKEN+tokenInfo.getAccess_token(),
        		HttpMethod.DELETE, request, User.class);
    }
 
 
    /*
     * Send a DELETE request to delete all users.
     */
    private static void deleteAllUsers(AuthTokenInfo tokenInfo) {
    	Assert.notNull(tokenInfo, "Authenticate first please......");
        System.out.println("\nTesting all delete Users API----------");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        restTemplate.exchange(REST_SERVICE_URI+"/user/"+QPM_ACCESS_TOKEN+tokenInfo.getAccess_token(),
        		HttpMethod.DELETE, request, User.class);
    }
 
    public static void main(String args[]) throws Exception{
    	disableSSL();

    	AuthTokenInfo tokenInfo = sendTokenRequest();
    	/*listAllUsers(tokenInfo);
        
    	getUser(tokenInfo);
        
    	createUser(tokenInfo);
        listAllUsers(tokenInfo);
        
        updateUser(tokenInfo);
        listAllUsers(tokenInfo);
        
        deleteUser(tokenInfo);
        listAllUsers(tokenInfo);
        
        deleteAllUsers(tokenInfo);
        listAllUsers(tokenInfo);*/
    }
    
    private static void callFastTagApi() throws UnirestException {
    	HttpResponse<String> response = Unirest.post("http://14.141.77.61:5443/TRANSPORTAPI/oauth/token")
				  .header("Authorization", "Basic Nzg1OGZlM2UtZWI0OS00ZDAyLTk2MDktMjlhMjgyNmIzZ2doOmExMmdhOGQyLTQ0NzAtNGFhZS05ZDZkLWIzNGU0ZDgxMzIwYQ==")
				  .header("cache-control", "no-cache")
				  .header("Postman-Token", "c3465a5f-944d-4b01-b069-0e67362b2043")
				  .asString();
    }
    
    
    public static CloseableHttpClient getCloseableHttpClient()
    {
        CloseableHttpClient httpClient = null;
        try {
            httpClient = HttpClients.custom().
                    setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).
                    setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy()
                    {
                        public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException
                        {
                            return true;
                        }
                    }).build()).build();
        } catch (KeyManagementException e) {
        	System.err.println("KeyManagementException in creating http client instance"+ e);
        } catch (NoSuchAlgorithmException e) {
        	System.err.println("NoSuchAlgorithmException in creating http client instance"+ e);
        } catch (KeyStoreException e) {
        	System.err.println("KeyStoreException in creating http client instance"+ e);
        }
        return httpClient;
    }
    
    private static void disableSSL() {
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
}