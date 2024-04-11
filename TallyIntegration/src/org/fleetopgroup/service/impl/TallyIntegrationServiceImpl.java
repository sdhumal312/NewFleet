package org.fleetopgroup.service.impl;

import java.util.Map;
import org.fleetopgroup.service.MyTableModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import javax.xml.bind.Unmarshaller;
import java.net.URLConnection;
import javax.xml.bind.JAXBContext;
import org.fleetopgroup.dto.RESPONSE;
import java.io.StringReader;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStream;
import org.json.JSONArray;
import java.io.IOException;
import java.net.ConnectException;
import java.awt.Component;
import javax.swing.JOptionPane;
import org.json.JSONObject;
import org.fleetopgroup.utility.Utility;
import java.util.Properties;
import java.util.Date;
import org.fleetopgroup.dto.TripSheetExpenseDto;
import java.util.List;
import java.util.HashMap;
import org.fleetopgroup.service.ITallyIntegrationService;

public class TallyIntegrationServiceImpl implements ITallyIntegrationService
{
    public String hostUrl;
    public String tallyHost;
    public Integer companyId;
    public String dataUrl;
    String voucher;
    public boolean tallyPushForVehicle;
    HashMap<String, List<TripSheetExpenseDto>> vehicleHM;
    
    public TallyIntegrationServiceImpl() {
        this.hostUrl = "";
        this.tallyHost = "";
        this.companyId = 0;
        this.dataUrl = "";
        this.voucher = null;
        this.tallyPushForVehicle = false;
        this.vehicleHM = null;
    }
    
    public HashMap<String, Object> getExpenseVoucherDataWthinRange(final Date fromDate, final Date toDate, final String selectedCompany) throws Exception {
        JSONArray vendorPaymentArr = null;
        JSONArray expenseListArr = null;
        JSONArray invoiceListArr = null;
        JSONArray vehicleExpListArr = null;
        JSONArray driverBalanceListArr = null;
        try {
            final Properties props = new Properties();
            final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("configuration.properties");
            props.load(inputStream);
            this.dataUrl = props.getProperty("dataUrl");
            this.companyId = Integer.parseInt(props.getProperty("companyId"));
            this.tallyPushForVehicle = Boolean.getBoolean(props.getProperty("tallyPushForVehicle", "true"));
            final JSONObject reqObj = this.prepareReqJsonObj(fromDate, toDate, this.companyId, selectedCompany);
            final String response = Utility.excutePost(this.dataUrl, reqObj.toString());
            final JSONObject resObj = new JSONObject(response);
            if (resObj != null && resObj.has("vendorPaymentList")) {
                vendorPaymentArr = resObj.getJSONArray("vendorPaymentList");
            }
            if (resObj != null && resObj.has("invoiceList")) {
                invoiceListArr = resObj.getJSONArray("invoiceList");
            }
            if (resObj != null && resObj.has("vehicleExpList")) {
                vehicleExpListArr = resObj.getJSONArray("vehicleExpList");
            }
            if (resObj != null && resObj.has("driverBalanceList")) {
                driverBalanceListArr = resObj.getJSONArray("driverBalanceList");
            }
            if ((resObj != null && resObj.has("expenseList")) || resObj.has("vendorPaymentList") || resObj.has("invoiceList") || resObj.has("vehicleExpList") || resObj.has("driverBalanceList")) {
                this.vehicleHM = new HashMap<String, List<TripSheetExpenseDto>>();
                try {
                    expenseListArr = resObj.getJSONArray("expenseList");
                }
                catch (Exception ex) {}
                if ((expenseListArr != null && expenseListArr.length() > 0) || (vendorPaymentArr != null && vendorPaymentArr.length() > 0) || (invoiceListArr != null && invoiceListArr.length() > 0) || (vehicleExpListArr != null && vehicleExpListArr.length() > 0) || (driverBalanceListArr != null && driverBalanceListArr.length() > 0)) {
                    openVoucherListPopUp(expenseListArr, vendorPaymentArr, invoiceListArr, selectedCompany, vehicleExpListArr, driverBalanceListArr);
                }
                else {
                    JOptionPane.showMessageDialog(null, "No Record Found !");
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "No Record Found !");
            }
        }
        catch (ConnectException e2) {
            JOptionPane.showMessageDialog(null, "Unable To Connect With Tally, Please Make Sure Tally Is Running!");
        }
        catch (IOException e3) {
            JOptionPane.showMessageDialog(null, "Some Error Occured , Please contact to system administrator !");
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Exception : " + e);
        }
        return null;
    }
    
    public boolean SendToTally(final String Voucher) throws Exception {
        final String SOAPAction = "";
        boolean isSuccess = false;
        final URL url = new URL("http://127.0.0.1:9000/");
        final URLConnection connection = url.openConnection();
        final HttpURLConnection httpConn = (HttpURLConnection)connection;
        final ByteArrayInputStream bin = new ByteArrayInputStream(Voucher.getBytes());
        final ByteArrayOutputStream bout = new ByteArrayOutputStream();
        copy(bin, bout);
        final byte[] b = bout.toByteArray();
        httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
        httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        httpConn.setRequestProperty("SOAPAction", "");
        httpConn.setRequestMethod("POST");
        httpConn.setDoOutput(true);
        httpConn.setDoInput(true);
        final OutputStream out = httpConn.getOutputStream();
        out.write(b);
        out.close();
        final InputStreamReader isr = new InputStreamReader(httpConn.getInputStream());
        final BufferedReader in = new BufferedReader(isr);
        final String str = in.lines().collect(Collectors.joining());
        final StringReader sr = new StringReader(str);
        final JAXBContext jaxbContext = JAXBContext.newInstance(new Class[] { RESPONSE.class });
        final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        final RESPONSE response = (RESPONSE)unmarshaller.unmarshal((Reader)sr);
        if (response.getLINEERROR() != null) {
            JOptionPane.showMessageDialog(null, response.getLINEERROR());
        }
        else if (response.getERRORS() != null && response.getERRORS() > 0) {
            JOptionPane.showMessageDialog(null, "ERRORS : " + response.getERRORS());
            JOptionPane.showMessageDialog(null, "ERRORS : Please check voucher and tally date !");
        }
        else {
            JOptionPane.showMessageDialog(null, "All Record Synchronized Successfully !");
            isSuccess = true;
        }
        in.close();
        return isSuccess;
    }
    
    public static void copy(final InputStream in, final OutputStream out) throws IOException {
        synchronized (in) {
            final byte[] buffer = new byte[256];
            while (true) {
                final int bytesRead = in.read(buffer);
                if (bytesRead == -1) {
                    break;
                }
                out.write(buffer, 0, bytesRead);
            }
        }
    }
    
    public JSONObject prepareReqJsonObj(final Date fromDate, final Date toDate, final Integer companyId, final String selectedStr) {
        final JSONObject jsonobj = new JSONObject();
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        jsonobj.put("fromDate", (Object)dateFormat.format(fromDate));
        jsonobj.put("toDate", (Object)dateFormat.format(toDate));
        jsonobj.put("companyId", (Object)companyId);
        jsonobj.put("selectedStr", (Object)selectedStr);
        return jsonobj;
    }
    
    public String createRequest(final JSONArray expenseListArr, final String selectedCompany, final HashMap<String, List<TripSheetExpenseDto>> vehicleHM, final JSONArray vendorPaymentArr, final JSONArray invoiceListArr, final JSONArray vehicleListArr, final JSONArray driverBalanceListArr) {
        final HashMap<String, TripSheetExpenseDto> trHashMap = new HashMap<String, TripSheetExpenseDto>();
        String TXML = null;
        final DecimalFormat format = new DecimalFormat("##.00");
        TXML = "<ENVELOPE><HEADER><TALLYREQUEST>Import Data</TALLYREQUEST></HEADER><BODY><IMPORTDATA><REQUESTDESC><REPORTNAME>All Masters</REPORTNAME><STATICVARIABLES><SVCURRENTCOMPANY>" + selectedCompany + "</SVCURRENTCOMPANY>" + "</STATICVARIABLES>" + "</REQUESTDESC>" + "<REQUESTDATA>";
        if (expenseListArr != null && expenseListArr.length() > 0) {
            for (int i = 0; i < expenseListArr.length(); ++i) {
                final JSONObject jsonObject = expenseListArr.getJSONObject(i);
                if (jsonObject.getString("ledgerName").equalsIgnoreCase("--") || jsonObject.getString("ledgerName").equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(null, "Please Configure Ledger Name for Vehicle " + jsonObject.getString("vehicle_registration"));
                    return null;
                }
                TripSheetExpenseDto expenseDto = trHashMap.get(String.valueOf(String.valueOf(jsonObject.getInt("vid"))) + "_" + jsonObject.getLong("tripSheetId") + "_" + jsonObject.getString("voucherDate") + "_" + jsonObject.getLong("tripExpenseID"));
                if (expenseDto == null) {
                    expenseDto = new TripSheetExpenseDto();
                    expenseDto.setLedgerName(jsonObject.getString("ledgerName"));
                    expenseDto.setRemark(jsonObject.getString("remark"));
                    expenseDto.setExpenseAmount(Double.valueOf(jsonObject.getDouble("expenseAmount")));
                    expenseDto.setVoucherDate(jsonObject.getString("voucherDate"));
                    expenseDto.setVid(Integer.valueOf(jsonObject.getInt("vid")));
                    expenseDto.setTripSheetId(Long.valueOf(jsonObject.getLong("tripSheetId")));
                }
                else {
                    expenseDto.setExpenseAmount(Double.valueOf(jsonObject.getDouble("expenseAmount") + expenseDto.getExpenseAmount()));
                }
                trHashMap.put(String.valueOf(String.valueOf(jsonObject.getInt("vid"))) + "_" + jsonObject.getLong("tripSheetId") + "_" + jsonObject.getString("voucherDate") + "_" + jsonObject.getLong("tripExpenseID"), expenseDto);
                TXML = String.valueOf(String.valueOf(TXML)) + "<TALLYMESSAGE xmlns:UDF=\"TallyUDF\">";
                if (jsonObject.getBoolean("credit")) {
                    TXML = String.valueOf(String.valueOf(TXML)) + "<VOUCHER VCHTYPE=\"Journal\" REMOTEID=\"" + jsonObject.getLong("tripExpenseID") + "_" + jsonObject.getLong("expenseTypeId") + " \">";
                    TXML = String.valueOf(String.valueOf(TXML)) + "<VOUCHERTYPENAME>Journal</VOUCHERTYPENAME> ";
                }
                else {
                    TXML = String.valueOf(String.valueOf(TXML)) + "<VOUCHER VCHTYPE=\"Payment\" REMOTEID=\"" + jsonObject.getLong("tripExpenseID") + "_" + jsonObject.getLong("expenseTypeId") + "\">";
                    TXML = String.valueOf(String.valueOf(TXML)) + "<VOUCHERTYPENAME>Payment</VOUCHERTYPENAME> ";
                }
                TXML = String.valueOf(String.valueOf(TXML)) + "<DATE>" + jsonObject.getString("voucherDate") + "</DATE>" + "<VOUCHERNUMBER>" + jsonObject.getLong("tripExpenseID") + "</VOUCHERNUMBER>";
                if (jsonObject.getBoolean("credit")) {
                    TXML = String.valueOf(String.valueOf(TXML)) + "  <PARTYLEDGERNAME>" + jsonObject.getString("vendorName") + "</PARTYLEDGERNAME> ";
                }
                else {
                    TXML = String.valueOf(String.valueOf(TXML)) + "  <PARTYLEDGERNAME>Cash</PARTYLEDGERNAME>";
                }
                TXML = String.valueOf(String.valueOf(TXML)) + "<NARRATION>" + jsonObject.getString("remark") + "</NARRATION>" + "<EFFECTIVEDATE>" + jsonObject.getString("voucherDate") + "</EFFECTIVEDATE> <ALLLEDGERENTRIES.LIST>";
                if (jsonObject.getBoolean("credit")) {
                    TXML = String.valueOf(String.valueOf(TXML)) + "  <LEDGERNAME>" + jsonObject.getString("vendorName") + "</LEDGERNAME> ";
                }
                else {
                    TXML = String.valueOf(String.valueOf(TXML)) + "  <LEDGERNAME>Cash</LEDGERNAME>";
                }
                TXML = String.valueOf(String.valueOf(TXML)) + " <REMOVEZEROENTRIES>NO</REMOVEZEROENTRIES> <LEDGERFROMITEM>NO</LEDGERFROMITEM> <ISDEEMEDPOSITIVE>NO</ISDEEMEDPOSITIVE> <AMOUNT>" + format.format(jsonObject.getDouble("expenseAmount")) + "</AMOUNT></ALLLEDGERENTRIES.LIST><ALLLEDGERENTRIES.LIST>";
                if (jsonObject.getString("expenseName").equalsIgnoreCase("Diesel EXP") || jsonObject.getString("expenseName").equalsIgnoreCase("Diesel")) {
                    TXML = String.valueOf(String.valueOf(TXML)) + " <LEDGERNAME>Diesel Expense</LEDGERNAME>";
                }
                else {
                    TXML = String.valueOf(String.valueOf(TXML)) + " <LEDGERNAME>" + jsonObject.getString("expenseName") + "</LEDGERNAME>";
                }
                TXML = String.valueOf(String.valueOf(TXML)) + "<REMOVEZEROENTRIES>NO</REMOVEZEROENTRIES> <LEDGERFROMITEM>NO</LEDGERFROMITEM><ISDEEMEDPOSITIVE>YES</ISDEEMEDPOSITIVE> <AMOUNT>-" + format.format(jsonObject.getDouble("expenseAmount")) + "</AMOUNT></ALLLEDGERENTRIES.LIST>" + "</VOUCHER>" + "</TALLYMESSAGE>";
            }
        }
        if (vehicleHM != null && vehicleHM.size() > 0) {
            try {
                for (final String key : vehicleHM.keySet()) {
                    final TripSheetExpenseDto expenseDto = trHashMap.get(key);
                    final List<TripSheetExpenseDto> tripList = vehicleHM.get(key);
                    TXML = String.valueOf(String.valueOf(TXML)) + "<TALLYMESSAGE xmlns:UDF=\"TallyUDF\"><VOUCHER VCHTYPE=\"Journal\" REMOTEID=\"" + key + "  \">" + "<VOUCHERTYPENAME>Journal</VOUCHERTYPENAME> " + "<DATE>" + expenseDto.getVoucherDate() + "</DATE>" + "<VOUCHERNUMBER>" + expenseDto.getTripSheetId() + "_" + expenseDto.getVoucherDate() + "</VOUCHERNUMBER>" + "<PARTYLEDGERNAME>" + expenseDto.getLedgerName() + "</PARTYLEDGERNAME> " + "<NARRATION>" + expenseDto.getRemark() + "</NARRATION>" + "<EFFECTIVEDATE>" + expenseDto.getVoucherDate() + "</EFFECTIVEDATE> <ALLLEDGERENTRIES.LIST>" + "<LEDGERNAME>" + expenseDto.getLedgerName() + "</LEDGERNAME>" + " <REMOVEZEROENTRIES>NO</REMOVEZEROENTRIES>" + " <LEDGERFROMITEM>NO</LEDGERFROMITEM>" + " <ISDEEMEDPOSITIVE>YES</ISDEEMEDPOSITIVE>" + " <AMOUNT>-" + format.format(expenseDto.getExpenseAmount()) + "</AMOUNT></ALLLEDGERENTRIES.LIST>";
                    for (int j = 0; j < tripList.size(); ++j) {
                        TXML = String.valueOf(String.valueOf(TXML)) + "<ALLLEDGERENTRIES.LIST> <LEDGERNAME>" + tripList.get(j).getExpenseName() + "</LEDGERNAME>" + " <REMOVEZEROENTRIES>NO</REMOVEZEROENTRIES>" + " <LEDGERFROMITEM>NO</LEDGERFROMITEM>" + "<ISDEEMEDPOSITIVE>NO</ISDEEMEDPOSITIVE>" + " <AMOUNT>" + format.format(tripList.get(j).getExpenseAmount()) + "</AMOUNT>" + "</ALLLEDGERENTRIES.LIST>";
                    }
                    TXML = String.valueOf(String.valueOf(TXML)) + "</VOUCHER></TALLYMESSAGE>";
                }
            }
            catch (Exception e) {
                System.err.println("inside vehicle exce  " + e);
            }
        }
        if (vendorPaymentArr != null && vendorPaymentArr.length() > 0) {
            for (int i = 0; i < vendorPaymentArr.length(); ++i) {
                final JSONObject jsonObject = vendorPaymentArr.getJSONObject(i);
                TXML = String.valueOf(String.valueOf(TXML)) + "<TALLYMESSAGE xmlns:UDF=\"TallyUDF\">";
                TXML = String.valueOf(String.valueOf(TXML)) + "<VOUCHER VCHTYPE=\"Payment\" REMOTEID=\"" + jsonObject.getLong("tripExpenseID") + "_" + jsonObject.getLong("expenseTypeId") + "\">";
                TXML = String.valueOf(String.valueOf(TXML)) + "<VOUCHERTYPENAME>Payment</VOUCHERTYPENAME> ";
                TXML = String.valueOf(String.valueOf(TXML)) + "<DATE>" + jsonObject.getString("voucherDate") + "</DATE>" + "<VOUCHERNUMBER>" + jsonObject.getLong("tripSheetNumber") + "</VOUCHERNUMBER>";
                TXML = String.valueOf(String.valueOf(TXML)) + "<PARTYLEDGERNAME>" + jsonObject.getString("ledgerName") + "</PARTYLEDGERNAME>";
                TXML = String.valueOf(String.valueOf(TXML)) + "<NARRATION>" + jsonObject.getString("remark") + "</NARRATION>" + "<EFFECTIVEDATE>" + jsonObject.getString("voucherDate") + "</EFFECTIVEDATE> <ALLLEDGERENTRIES.LIST>";
                TXML = String.valueOf(String.valueOf(TXML)) + " <LEDGERNAME>" + jsonObject.getString("ledgerName") + "</LEDGERNAME>";
                TXML = String.valueOf(String.valueOf(TXML)) + " <REMOVEZEROENTRIES>NO</REMOVEZEROENTRIES> <LEDGERFROMITEM>NO</LEDGERFROMITEM> <ISDEEMEDPOSITIVE>NO</ISDEEMEDPOSITIVE> <AMOUNT>" + format.format(jsonObject.getDouble("expenseAmount")) + "</AMOUNT></ALLLEDGERENTRIES.LIST><ALLLEDGERENTRIES.LIST>";
                TXML = String.valueOf(String.valueOf(TXML)) + " <LEDGERNAME>" + jsonObject.getString("vendorName") + "</LEDGERNAME>";
                TXML = String.valueOf(String.valueOf(TXML)) + "<REMOVEZEROENTRIES>NO</REMOVEZEROENTRIES> <LEDGERFROMITEM>NO</LEDGERFROMITEM><ISDEEMEDPOSITIVE>YES</ISDEEMEDPOSITIVE> <AMOUNT>-" + format.format(jsonObject.getDouble("expenseAmount")) + "</AMOUNT></ALLLEDGERENTRIES.LIST>" + "</VOUCHER>" + "</TALLYMESSAGE>";
            }
        }
        if (invoiceListArr != null && invoiceListArr.length() > 0) {
            for (int i = 0; i < invoiceListArr.length(); ++i) {
                final JSONObject jsonObject = invoiceListArr.getJSONObject(i);
                TXML = String.valueOf(String.valueOf(TXML)) + "<TALLYMESSAGE xmlns:UDF=\"TallyUDF\">";
                if (jsonObject.getBoolean("credit")) {
                    TXML = String.valueOf(String.valueOf(TXML)) + "<VOUCHER VCHTYPE=\"Journal\" REMOTEID=\"" + jsonObject.getLong("tripExpenseID") + "_" + jsonObject.getLong("expenseTypeId") + " \">";
                    TXML = String.valueOf(String.valueOf(TXML)) + "<VOUCHERTYPENAME>Journal</VOUCHERTYPENAME> ";
                }
                else {
                    TXML = String.valueOf(String.valueOf(TXML)) + "<VOUCHER VCHTYPE=\"Payment\" REMOTEID=\"" + jsonObject.getLong("tripExpenseID") + "_" + jsonObject.getLong("expenseTypeId") + "\">";
                    TXML = String.valueOf(String.valueOf(TXML)) + "<VOUCHERTYPENAME>Payment</VOUCHERTYPENAME> ";
                }
                TXML = String.valueOf(String.valueOf(TXML)) + "<DATE>" + jsonObject.getString("voucherDate") + "</DATE>" + "<VOUCHERNUMBER>" + jsonObject.getLong("tripExpenseID") + "</VOUCHERNUMBER>";
                if (jsonObject.getBoolean("credit")) {
                    TXML = String.valueOf(String.valueOf(TXML)) + "  <PARTYLEDGERNAME>" + jsonObject.getString("vendorName") + "</PARTYLEDGERNAME> ";
                }
                else {
                    TXML = String.valueOf(String.valueOf(TXML)) + "  <PARTYLEDGERNAME>Cash</PARTYLEDGERNAME>";
                }
                TXML = String.valueOf(String.valueOf(TXML)) + "<NARRATION>" + jsonObject.getString("remark") + "</NARRATION>" + "<EFFECTIVEDATE>" + jsonObject.getString("voucherDate") + "</EFFECTIVEDATE> <ALLLEDGERENTRIES.LIST>";
                if (jsonObject.getBoolean("credit")) {
                    TXML = String.valueOf(String.valueOf(TXML)) + "  <LEDGERNAME>" + jsonObject.getString("vendorName") + "</LEDGERNAME> ";
                }
                else {
                    TXML = String.valueOf(String.valueOf(TXML)) + "  <LEDGERNAME>Cash</LEDGERNAME>";
                }
                TXML = String.valueOf(String.valueOf(TXML)) + " <REMOVEZEROENTRIES>NO</REMOVEZEROENTRIES> <LEDGERFROMITEM>NO</LEDGERFROMITEM> <ISDEEMEDPOSITIVE>NO</ISDEEMEDPOSITIVE> <AMOUNT>" + format.format(jsonObject.getDouble("expenseAmount")) + "</AMOUNT></ALLLEDGERENTRIES.LIST><ALLLEDGERENTRIES.LIST>";
                if (jsonObject.getString("expenseName").equalsIgnoreCase("Diesel EXP") || jsonObject.getString("expenseName").equalsIgnoreCase("Diesel")) {
                    TXML = String.valueOf(String.valueOf(TXML)) + " <LEDGERNAME>Diesel Expense</LEDGERNAME>";
                }
                else {
                    TXML = String.valueOf(String.valueOf(TXML)) + " <LEDGERNAME>" + jsonObject.getString("expenseName") + "</LEDGERNAME>";
                }
                TXML = String.valueOf(String.valueOf(TXML)) + "<REMOVEZEROENTRIES>NO</REMOVEZEROENTRIES> <LEDGERFROMITEM>NO</LEDGERFROMITEM><ISDEEMEDPOSITIVE>YES</ISDEEMEDPOSITIVE> <AMOUNT>-" + format.format(jsonObject.getDouble("expenseAmount")) + "</AMOUNT></ALLLEDGERENTRIES.LIST>" + "</VOUCHER>" + "</TALLYMESSAGE>";
            }
        }
        if (driverBalanceListArr != null && driverBalanceListArr.length() > 0) {
            for (int i = 0; i < driverBalanceListArr.length(); ++i) {
                final JSONObject jsonObject = driverBalanceListArr.getJSONObject(i);
                TXML = String.valueOf(String.valueOf(TXML)) + "<TALLYMESSAGE xmlns:UDF=\"TallyUDF\"><VOUCHER VCHTYPE=\"Journal\" REMOTEID=\"" + jsonObject.getLong("tripExpenseID") + "_" + jsonObject.getLong("expenseTypeId") + "_" + jsonObject.get("expenseType") + " \">" + "<VOUCHERTYPENAME>Journal</VOUCHERTYPENAME> " + "<DATE>" + jsonObject.getString("voucherDate") + "</DATE>" + "<VOUCHERNUMBER>" + jsonObject.getLong("tripSheetNumber") + "_" + jsonObject.getLong("expenseTypeId") + "</VOUCHERNUMBER>" + "<PARTYLEDGERNAME>" + jsonObject.getString("vendorName") + "</PARTYLEDGERNAME> " + "<NARRATION>" + jsonObject.getString("remark") + "</NARRATION>" + "<EFFECTIVEDATE>" + jsonObject.getString("voucherDate") + "</EFFECTIVEDATE> <ALLLEDGERENTRIES.LIST>" + "<LEDGERNAME>" + jsonObject.getString("vendorName") + "</LEDGERNAME>" + " <REMOVEZEROENTRIES>NO</REMOVEZEROENTRIES>" + " <LEDGERFROMITEM>NO</LEDGERFROMITEM>";
                if (jsonObject.getDouble("expenseAmount") < 0.0) {
                    TXML = String.valueOf(String.valueOf(TXML)) + " <ISDEEMEDPOSITIVE>NO</ISDEEMEDPOSITIVE> <AMOUNT>" + format.format(Math.abs(jsonObject.getDouble("expenseAmount"))) + "</AMOUNT></ALLLEDGERENTRIES.LIST>";
                }
                else {
                    TXML = String.valueOf(String.valueOf(TXML)) + " <ISDEEMEDPOSITIVE>YES</ISDEEMEDPOSITIVE> <AMOUNT>-" + format.format(Math.abs(jsonObject.getDouble("expenseAmount"))) + "</AMOUNT></ALLLEDGERENTRIES.LIST>";
                }
                TXML = String.valueOf(String.valueOf(TXML)) + "<ALLLEDGERENTRIES.LIST> <LEDGERNAME>" + jsonObject.getString("expenseName") + "</LEDGERNAME>" + " <REMOVEZEROENTRIES>NO</REMOVEZEROENTRIES>" + " <LEDGERFROMITEM>NO</LEDGERFROMITEM>";
                if (jsonObject.getDouble("expenseAmount") < 0.0) {
                    TXML = String.valueOf(String.valueOf(TXML)) + "<ISDEEMEDPOSITIVE>YES</ISDEEMEDPOSITIVE> <AMOUNT>-" + format.format(Math.abs(jsonObject.getDouble("expenseAmount"))) + "</AMOUNT>";
                }
                else {
                    TXML = String.valueOf(String.valueOf(TXML)) + "<ISDEEMEDPOSITIVE>NO</ISDEEMEDPOSITIVE> <AMOUNT>" + format.format(Math.abs(jsonObject.getDouble("expenseAmount"))) + "</AMOUNT>";
                }
                TXML = String.valueOf(String.valueOf(TXML)) + "</ALLLEDGERENTRIES.LIST></VOUCHER></TALLYMESSAGE>";
            }
        }
        TXML = String.valueOf(String.valueOf(TXML)) + "</REQUESTDATA></IMPORTDATA></BODY></ENVELOPE>";
        return TXML;
    }
    
    private HashMap<String, List<TripSheetExpenseDto>> getVehicleTripWiseList(final JSONArray expenseListArr) throws Exception {
        HashMap<String, List<TripSheetExpenseDto>> vehicleHM = null;
        List<TripSheetExpenseDto> vehicleLIst = null;
        try {
            if (expenseListArr != null && expenseListArr.length() > 0) {
                vehicleHM = new HashMap<String, List<TripSheetExpenseDto>>();
                TripSheetExpenseDto expenseDto = null;
                for (int i = 0; i < expenseListArr.length(); ++i) {
                    final JSONObject jsonObject = expenseListArr.getJSONObject(i);
                    if (jsonObject.has("vid") && jsonObject.get("vid") != null && jsonObject.getInt("vid") > 0) {
                        expenseDto = new TripSheetExpenseDto();
                        expenseDto.setVid(Integer.valueOf(jsonObject.getInt("vid")));
                        expenseDto.setTripSheetId(Long.valueOf(jsonObject.getLong("tripSheetId")));
                        expenseDto.setCredit(jsonObject.getBoolean("credit"));
                        expenseDto.setVendorName(jsonObject.getString("vendorName"));
                        expenseDto.setTripExpenseID(Long.valueOf(jsonObject.getLong("tripExpenseID")));
                        if (jsonObject.getString("expenseName").equalsIgnoreCase("Diesel EXP") || jsonObject.getString("expenseName").equalsIgnoreCase("Diesel")) {
                            expenseDto.setExpenseName("Diesel Expense");
                        }
                        else {
                            expenseDto.setExpenseName("Vehicle Fuel Expenses");
                        }
                        if (jsonObject.has("expenseId") && !jsonObject.isNull("expenseId")) {
                            expenseDto.setExpenseId(Integer.valueOf(jsonObject.getInt("expenseId")));
                        }
                        expenseDto.setExpenseAmount(Double.valueOf(jsonObject.getDouble("expenseAmount")));
                        expenseDto.setLedgerName(jsonObject.getString("ledgerName"));
                        expenseDto.setRemark(jsonObject.getString("remark"));
                        expenseDto.setVoucherDate(jsonObject.getString("voucherDate"));
                        expenseDto.setExpenseTypeId(Short.parseShort(new StringBuilder().append(jsonObject.get("expenseTypeId")).toString()));
                        vehicleLIst = vehicleHM.get(expenseDto.getVid() + "_" + expenseDto.getTripSheetId() + "_" + expenseDto.getVoucherDate() + "_" + expenseDto.getTripExpenseID());
                        if (vehicleLIst == null || vehicleLIst.isEmpty()) {
                            vehicleLIst = new ArrayList<TripSheetExpenseDto>();
                        }
                        vehicleLIst.add(expenseDto);
                        vehicleHM.put(expenseDto.getVid() + "_" + expenseDto.getTripSheetId() + "_" + expenseDto.getVoucherDate() + "_" + expenseDto.getTripExpenseID(), vehicleLIst);
                    }
                }
            }
            return vehicleHM;
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            vehicleHM = null;
            vehicleLIst = null;
        }
    }
    
    public static void openVoucherListPopUp(final JSONArray expenseListArr, final JSONArray vendorPaymentArr, final JSONArray invoiceListArr, final String selectedCompany, final JSONArray vehicleExpListArr, final JSONArray driverBalanceListArr) throws Exception {
        final MyTableModel model = new MyTableModel();
        model.showFrame(expenseListArr, vendorPaymentArr, invoiceListArr, selectedCompany, vehicleExpListArr, driverBalanceListArr);
    }
    
    public void getFinalListToSync(final JSONArray expenseListArr, final JSONArray vendorPaymentArr, final JSONArray invoiceListArr, final HashMap<String, String> syncListHM, final JSONArray vehicleExpListArr, final JSONArray driverBalanceListArr) throws Exception {
        try {
            final JSONArray fianalExpenseArr = new JSONArray();
            final JSONArray fianalInvoiceArr = new JSONArray();
            final JSONArray fianalPAymentArr = new JSONArray();
            final JSONArray fianalVehicleArr = new JSONArray();
            final JSONArray fianalDriverBalanceArr = new JSONArray();
            final String selectedCompany = syncListHM.get("selectedCompany");
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
            String vehicleListIds = "";
            String tripIdsForDBal = "";
            String dseIds = "";
            String tripSheetAdvIds = "";
            final HashMap<String, String> insertedHM = new HashMap<String, String>();
            if (expenseListArr != null && expenseListArr.length() > 0) {
                for (int i = 0; i < expenseListArr.length(); ++i) {
                    final JSONObject jsonObject = expenseListArr.getJSONObject(i);
                    final String ids = syncListHM.get("expenseIds");
                    if (ids != null && !ids.trim().equalsIgnoreCase("")) {
                        final String[] idsArr = ids.split(",");
                        for (int j = 0; j < idsArr.length; ++j) {
                            if (jsonObject.getLong("tripExpenseID") == Long.parseLong(idsArr[j])) {
                                fianalExpenseArr.put(expenseListArr.get(i));
                                if (jsonObject.getString("tripSheetNumberStr").contains("TS") && jsonObject.getString("expenseName").contains("Fasttag")) {
                                    tripSheetIds = String.valueOf(String.valueOf(tripSheetIds)) + jsonObject.getLong("tripExpenseID") + ",";
                                }
                                else if (jsonObject.getString("tripSheetNumberStr").contains("F")) {
                                    fuelIds = String.valueOf(String.valueOf(fuelIds)) + jsonObject.getLong("tripExpenseID") + ",";
                                }
                                else if (jsonObject.getString("tripSheetNumberStr").contains("SE") && !jsonObject.getString("tripSheetNumberStr").contains("DSE")) {
                                    serviceEntriesIds = String.valueOf(String.valueOf(serviceEntriesIds)) + jsonObject.getLong("tripExpenseID") + ",";
                                }
                                else if (jsonObject.getString("tripSheetNumberStr").contains("RR")) {
                                    renewalIds = String.valueOf(String.valueOf(renewalIds)) + jsonObject.getLong("tripExpenseID") + ",";
                                }
                                else if (jsonObject.getString("tripSheetNumberStr").split("_")[0].contains("DSE")) {
                                    dseIds = String.valueOf(String.valueOf(dseIds)) + jsonObject.getLong("tripExpenseID") + ",";
                                }else if (jsonObject.getString("tripSheetNumberStr").contains("TS") && jsonObject.getString("expenseName").contains("Trip Advance")) {
                                	tripSheetAdvIds = String.valueOf(String.valueOf(tripSheetIds)) + jsonObject.getLong("tripExpenseID") + ",";
                                }
                                else {
                                    tripExpenseIds = String.valueOf(String.valueOf(tripExpenseIds)) + jsonObject.getLong("tripExpenseID") + ",";
                                }
                            }
                        }
                    }
                }
            }
            if (invoiceListArr != null && invoiceListArr.length() > 0) {
                for (int i = 0; i < invoiceListArr.length(); ++i) {
                    final JSONObject jsonObject = invoiceListArr.getJSONObject(i);
                    final String ids = syncListHM.get("invoiceIds");
                    if (ids != null && !ids.trim().equalsIgnoreCase("")) {
                        final String[] idsArr = ids.split(",");
                        for (int j = 0; j < idsArr.length; ++j) {
                            if (jsonObject.getString("tripSheetNumberStr").equalsIgnoreCase(idsArr[j])) {
                                fianalInvoiceArr.put(invoiceListArr.get(i));
                                if (jsonObject.getString("tripSheetNumberStr").contains("TI")) {
                                    tyreInvoiceIds = String.valueOf(String.valueOf(tyreInvoiceIds)) + jsonObject.getLong("tripExpenseID") + ",";
                                }
                                else if (jsonObject.getString("tripSheetNumberStr").contains("PI")) {
                                    partInvoiceIds = String.valueOf(String.valueOf(partInvoiceIds)) + jsonObject.getLong("tripExpenseID") + ",";
                                }
                                else if (jsonObject.getString("tripSheetNumberStr").contains("BI")) {
                                    batteryInvoiceIds = String.valueOf(String.valueOf(batteryInvoiceIds)) + jsonObject.getLong("tripExpenseID") + ",";
                                }
                                else if (jsonObject.getString("tripSheetNumberStr").contains("CI")) {
                                    clothInoviceIds = String.valueOf(String.valueOf(clothInoviceIds)) + jsonObject.getLong("tripExpenseID") + ",";
                                }
                                else if (jsonObject.getString("tripSheetNumberStr").contains("UI")) {
                                    ureaInvoiceIds = String.valueOf(String.valueOf(ureaInvoiceIds)) + jsonObject.getLong("tripExpenseID") + ",";
                                }
                            }
                        }
                    }
                }
            }
            if (vendorPaymentArr != null && vendorPaymentArr.length() > 0) {
                for (int i = 0; i < vendorPaymentArr.length(); ++i) {
                    final JSONObject jsonObject = vendorPaymentArr.getJSONObject(i);
                    final String ids = syncListHM.get("paymentIds");
                    if (ids != null && !ids.trim().equalsIgnoreCase("")) {
                        final String[] idsArr = ids.split(",");
                        for (int j = 0; j < idsArr.length; ++j) {
                            if (jsonObject.getString("tripSheetNumberStr").equalsIgnoreCase(idsArr[j])) {
                                fianalPAymentArr.put(vendorPaymentArr.get(i));
                                vendorPaymentIds = String.valueOf(String.valueOf(vendorPaymentIds)) + jsonObject.getLong("tripExpenseID") + ",";
                            }
                        }
                    }
                }
            }
            if (vehicleExpListArr != null && vehicleExpListArr.length() > 0) {
                for (int i = 0; i < vehicleExpListArr.length(); ++i) {
                    final JSONObject jsonObject = vehicleExpListArr.getJSONObject(i);
                    final String ids = syncListHM.get("vehicleListIdsToSync");
                    if (ids != null && !ids.trim().equalsIgnoreCase("")) {
                        final String[] idsArr = ids.split(",");
                        for (int j = 0; j < idsArr.length; ++j) {
                            if (jsonObject.getString("tripSheetNumberStr").equalsIgnoreCase(idsArr[j])) {
                                fianalVehicleArr.put(vehicleExpListArr.get(i));
                                vehicleListIds = String.valueOf(String.valueOf(vehicleListIds)) + jsonObject.getLong("tripExpenseID") + ",";
                            }
                        }
                    }
                }
            }
            if (driverBalanceListArr != null && driverBalanceListArr.length() > 0) {
                for (int i = 0; i < driverBalanceListArr.length(); ++i) {
                    final JSONObject jsonObject = driverBalanceListArr.getJSONObject(i);
                    final String ids = syncListHM.get("expenseIds");
                    if (ids != null && !ids.trim().equalsIgnoreCase("")) {
                        final String[] idsArr = ids.split(",");
                        for (int j = 0; j < idsArr.length; ++j) {
                            if (jsonObject.getString("tripSheetNumberStr").split("_")[1].equalsIgnoreCase(idsArr[j])) {
                                fianalDriverBalanceArr.put((Object)jsonObject);
                                tripIdsForDBal = String.valueOf(String.valueOf(tripIdsForDBal)) + jsonObject.getLong("tripExpenseID") + ",";
                            }
                        }
                    }
                }
            }
            try {
                this.voucher = this.createRequest(fianalExpenseArr, selectedCompany, null, fianalPAymentArr, fianalInvoiceArr, fianalVehicleArr, fianalDriverBalanceArr);
            }
            catch (Exception e) {
                System.err.println("Excep " + e);
            }
            System.err.println("insdsaasd 6.. " + this.voucher);
            if (this.voucher != null) {
                final boolean isSuccess = this.SendToTally(this.voucher.replace("&", "AND"));
                if (isSuccess) {
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
                    insertedHM.put("dseIds", dseIds);
                    insertedHM.put("tripSheetAdvIds", tripSheetAdvIds);
                    this.updateTallyStatusToDB(insertedHM);
                }
            }
        }
        catch (ConnectException e2) {
            JOptionPane.showMessageDialog(null, "Unable To Connect With Tally, Please Make Sure Tally Is Running!");
        }
        catch (Exception ex) {}
    }
    
    public void updateTallyStatusToDB(final HashMap<String, String> insertedHM) throws Exception {
        try {
            final Properties props = new Properties();
            final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("configuration.properties");
            props.load(inputStream);
            this.companyId = Integer.parseInt(props.getProperty("companyId"));
            final String updateUrl = props.getProperty("updateUrl");
            final JSONObject jsonobj = new JSONObject();
            jsonobj.put("insertedHM", (Map)insertedHM);
            jsonobj.put("companyId", (Object)this.companyId);
            Utility.excutePost(updateUrl, jsonobj.toString());
        }
        catch (Exception e) {
            throw e;
        }
    }
}