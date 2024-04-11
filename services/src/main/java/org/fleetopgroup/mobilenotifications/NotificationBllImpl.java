package org.fleetopgroup.mobilenotifications;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;


public class NotificationBllImpl {
	public static void MobileNotification(final String finalNotificationString, final String tokenNumber,String title) throws Exception {
		if(tokenNumber!= null && tokenNumber.length() > 0  &&finalNotificationString!= null && finalNotificationString.length() > 0)
		{
			Thread t = new Thread() {
				public void run(){ 
					try {
						URL url = new URL(NotificationConstant.APP_FIREBASE_URL);
						HttpURLConnection conn = (HttpURLConnection) url.openConnection();
						conn.setUseCaches(false);
						conn.setDoInput(true);
						conn.setDoOutput(true);
						conn.setRequestMethod(NotificationConstant.REQUEST_METHOD);
						conn.setRequestProperty(NotificationConstant.REQUEST_AUTH, NotificationConstant.KEY+ NotificationConstant.SERVER_KEY_FOR_NOTIFICATION_NEW);
						conn.setRequestProperty(NotificationConstant.CONTENT_TYPE, NotificationConstant.RETURN_TYPE);
						JSONObject data = new JSONObject();
						data.put(NotificationConstant.TO, tokenNumber.trim());
						JSONObject info = new JSONObject();
						if(title!=null && title.length()>0) {
							info.put(NotificationConstant.TITLE, title);
						}
						else {
							info.put(NotificationConstant.TITLE, NotificationConstant.APP_NAME);
						}
						// Notification title
						info.put(NotificationConstant.TEXT, finalNotificationString); // Notification body
						data.put(NotificationConstant.NOTIFICATION, info);
						OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
						wr.write(data.toString());
						wr.flush();
						wr.close();
						int responseCode = conn.getResponseCode();
						BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
						String inputLine;
						StringBuffer response = new StringBuffer();

						while ((inputLine = in.readLine()) != null) {
							response.append(inputLine);
						}
						in.close();
						if(responseCode == 200){
							//LogWriter.writeLog(TRACE_ID, LogWriter.LOG_LEVEL_INFO, "------Notification send on IVCargo User Mobile-----");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			t.start();
		}
	}
}
