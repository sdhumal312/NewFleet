package org.fleetopgroup.constant;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

  
public class DateTimeTest { 
  
	 public static void main(String[] args) {
	        try {
	            String folderPath = "/home/manish/IVFleet/51217/55269/services/src/main/resources/configuration"; // Replace with the actual folder path
	            String targetFileName = "16.yml"; // Replace with the target file name
	            String newFileName = "126.yml"; // Replace with the new file name

	            // Search, copy, and create files logic
	            searchCopyAndCreateFiles(new File(folderPath), targetFileName, newFileName);

	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    public static void searchCopyAndCreateFiles(File folder, String targetFileName, String newFileName) throws IOException {
	        File[] files = folder.listFiles();
	        if (files != null) {
	            for (File file : files) {
	                if (file.isFile() && file.getName().equals(targetFileName)) {
	                    System.out.println("Found: " + file.getAbsolutePath());
	                    String parentFolderPath = file.getParent();
	                    File newFile = new File(parentFolderPath, newFileName);
	                    Files.copy(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
	                    System.out.println("New file created: " + newFile.getAbsolutePath());
	                } else if (file.isDirectory()) {
	                    searchCopyAndCreateFiles(file, targetFileName, newFileName); // Recursively search subdirectories
	                }
	            }
	        }
	    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public String CreateRequest()
    {             
        String TXML = null;

        TXML = "<ENVELOPE>"
        		+"<HEADER>"
        		+"<TALLYREQUEST>Import Data</TALLYREQUEST>"
        		+"</HEADER>"
        		+"<BODY>"
        		+"<IMPORTDATA>"
        		+"<REQUESTDESC>"
        		+"<REPORTNAME>All Masters</REPORTNAME>"
        		+"<STATICVARIABLES>"
        		+"<SVCURRENTCOMPANY>SCPL Ahmedabad HO 2019</SVCURRENTCOMPANY>"
        		+"</STATICVARIABLES>"
        		+"</REQUESTDESC>"
        		+"<REQUESTDATA>"
        		+"<TALLYMESSAGE xmlns:UDF=\"TallyUDF\">"
        		+"<VOUCHER VCHTYPE=\"Payment\" REMOTEID=\"9256_2\">"
        		+"<VOUCHERTYPENAME>Payment</VOUCHERTYPENAME> "
        		+"<DATE>20200201</DATE>"
        		+"<VOUCHERNUMBER>9256</VOUCHERNUMBER>"
        		+"<PARTYLEDGERNAME>Cash</PARTYLEDGERNAME>"
        		+"<NARRATION>Service Entries On Vehicle DD-03-L-9474 Date: 01-02-2020 From: SHREE AMBICA AUTO SALES SERVICE</NARRATION>"
        		+"<EFFECTIVEDATE>20200201</EFFECTIVEDATE> "
        		+"<ALLLEDGERENTRIES.LIST>  "
        		+"<LEDGERNAME>Cash</LEDGERNAME> "
        		+"<REMOVEZEROENTRIES>NO</REMOVEZEROENTRIES>"
        		+"<LEDGERFROMITEM>NO</LEDGERFROMITEM> "
        		+"<ISDEEMEDPOSITIVE>NO</ISDEEMEDPOSITIVE>"
        		+"<AMOUNT>2025.0</AMOUNT>"
        		+"</ALLLEDGERENTRIES.LIST>"
        		+"<ALLLEDGERENTRIES.LIST> "
        		+"<LEDGERNAME>VEHICLE REPAIRING EXP</LEDGERNAME>" 
        		+"<REMOVEZEROENTRIES>NO</REMOVEZEROENTRIES> "
        		+"<LEDGERFROMITEM>NO</LEDGERFROMITEM>"
        		+"<ISDEEMEDPOSITIVE>YES</ISDEEMEDPOSITIVE> "
        		+"<AMOUNT>-2025.0</AMOUNT>"
        		+"</ALLLEDGERENTRIES.LIST>"
        		+"</VOUCHER>"
        		+"</TALLYMESSAGE>"
        		+"</REQUESTDATA>"
        		+"</IMPORTDATA>"
        		+"</BODY>"
        		+"</ENVELOPE>";

        return TXML;
    }
    public void SendToTally() throws Exception{
        String Url = "http://127.0.0.1:9000/";      

        String SOAPAction = "";
       
        String Voucher = this.CreateRequest();

// Create the connection where we're going to send the file.
        URL url = new URL(Url);
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

        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            System.out.println(inputLine);
        }

        in.close();
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
    
    public static void sendPlainTextEmail(String host, String port,
            final String userName, final String password, String toAddress,
            String subject, String message) throws AddressException,
            MessagingException {

        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
// *** BEGIN CHANGE
        properties.put("mail.smtp.user", userName);

        // creates a new session, no Authenticator (will connect() later)
        Session session = Session.getDefaultInstance(properties);
// *** END CHANGE

        // creates a new e-mail message
        Message msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        // set plain text message
        msg.setText(message);

// *** BEGIN CHANGE
        // sends the e-mail
        Transport t = session.getTransport("smtp");
        t.connect(userName, password);
        t.sendMessage(msg, msg.getAllRecipients());
        t.close();
// *** END CHANGE

    }
} 