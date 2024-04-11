package org.fleetopgroup.service;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.fleetopgroup.service.impl.TallyIntegrationServiceImpl;
import org.fleetopgroup.utility.Utility;
import org.jdesktop.swingx.JXDatePicker;
import org.json.JSONArray;
import org.json.JSONObject;

public class JAVASwingFormExample {

	private JFrame frame;

	private JXDatePicker	picker;
	private JXDatePicker	picker2;
	public static List<String>  	companyList	= null;
	static String[] companyStrArr	=	null;
	JComboBox<Object> cb= null;
	/**
	 * Launch the application.
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		JAVASwingFormExample window = new JAVASwingFormExample();
		companyList = window.getCompanyList();
		window.initialize();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public  List<String> getCompanyList() throws IOException {
		
		Properties props = new Properties();
        InputStream  inputStream = this.getClass().getClassLoader().getResourceAsStream("configuration.properties");
        props.load(inputStream);

        
		String host = props.getProperty("host");
		boolean permissionRequired = Boolean.parseBoolean(props.getProperty("permissionRequired"));
		JSONObject reqObj = prepareReqJsonObj(Integer.parseInt(props.getProperty("companyId")), props.getProperty("tallyCompanyIds"), permissionRequired);
		
		String response = 	Utility.excutePost(host, reqObj.toString());

		JSONObject resObj	= new JSONObject(response);
		
		if(resObj.has("companyList")) {
			JSONArray	expenseListArr	=	resObj.getJSONArray("companyList");	
			
			companyStrArr	= toStringArray(expenseListArr);
			
		}
		
		return null;
	}
	public static String[] toStringArray(JSONArray array) {
	    if(array==null)
	        return null;

	    String[] arr=new String[array.length()];
	    for(int i=0; i<arr.length; i++) {
	        arr[i]=array.optString(i);
	    }
	    
	    return arr;
	}
	
	public static JSONObject prepareReqJsonObj(Integer companyId, String tallyCompanyIds, boolean permissionRequired){
		 JSONObject jsonobj = new JSONObject();

		    jsonobj.put("companyId",companyId);
		    jsonobj.put("tallyCompanyIds",tallyCompanyIds);
		    jsonobj.put("permissionRequired",permissionRequired);
		    return jsonobj; 
		}

	/**
	 * Create the application.
	 */
	public JAVASwingFormExample() {
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Tally Integration With Fleetop");
		frame.setBounds(100, 100, 730, 489);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		if(companyStrArr != null) {
			cb=new JComboBox<Object>(companyStrArr);
		}else {
			 String[] arr=new String[1];
			    for(int i=0; i<arr.length; i++) {
			        arr[i]="Please Select";
			 }
			    
			cb=new JComboBox<Object>(arr);
		}
		    
	    cb.setBounds(158, 105,200,20);    
	    frame.add(cb);        
	    frame.setLayout(null);    
	    frame.setSize(500,500);    
	
		/*
		 * textField = new JTextField(); textField.setBounds(128, 28, 86, 20);
		 * frame.getContentPane().add(textField); textField.setColumns(10);
		 */
			picker = new JXDatePicker();
			picker.setBounds(158, 28, 120, 20);
	        picker.setDate(Calendar.getInstance().getTime());
	        picker.setFormats(new SimpleDateFormat("dd-MM-yyyy"));
	        frame.getContentPane().add(picker);
	        
	        
			JLabel lblName = new JLabel("From Date : ");
			lblName.setBounds(65, 31, 180, 14);
			frame.getContentPane().add(lblName);
			
			JLabel lblPhone = new JLabel("To Date : ");
			lblPhone.setBounds(65, 68, 180, 14);
			frame.getContentPane().add(lblPhone);
		
		
		 	JLabel lblCompanyName = new JLabel("Company : ");    
		    lblCompanyName.setBounds(65, 100, 180, 14);
			frame.getContentPane().add(lblCompanyName); 
		
		
			picker2 = new JXDatePicker();
	        picker2.setBounds(158, 65, 120, 20);
	        picker2.setDate(Calendar.getInstance().getTime());
	        picker2.setFormats(new SimpleDateFormat("dd-MM-yyyy"));
	        frame.getContentPane().add(picker2);
	        
	        
	
		JButton btnSubmit = new JButton("Search");
		
		btnSubmit.setBackground(Color.BLUE);
		btnSubmit.setForeground(Color.MAGENTA);
		btnSubmit.setBounds(85, 140, 150, 23);
		frame.getContentPane().add(btnSubmit);
		
		
		btnSubmit.addActionListener(new ActionListener() {
			
			
			
			public void actionPerformed(ActionEvent arg0) {
				String selectedStr = String.valueOf(cb.getSelectedItem());
				if((picker.getDate() == null)||(picker2.getDate() == null)) {
					
					JOptionPane.showOptionDialog(null,null, "Create", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);
				}else if(selectedStr.equalsIgnoreCase("Please Select")){
					JOptionPane.showMessageDialog(null, "Please Select Company Name !");

				}else {
					TallyIntegrationServiceImpl integrationServiceImpl = new TallyIntegrationServiceImpl();
					btnSubmit.setEnabled(false);
					//JOptionPane.showMessageDialog(null, "Data Submitted");
					try {
						integrationServiceImpl.getExpenseVoucherDataWthinRange(picker.getDate(), picker2.getDate(), selectedStr);
						//JOptionPane.showMessageDialog(null, "Data Synchronized to Tally Successfully !");
						btnSubmit.setEnabled(true);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Some Error Occured , Please contact to system administrator !");
						e.printStackTrace();
					}
				}		
			}
		});
		
	
		
	}
}
