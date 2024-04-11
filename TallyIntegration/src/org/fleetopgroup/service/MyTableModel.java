package org.fleetopgroup.service;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import org.fleetopgroup.service.impl.TallyIntegrationServiceImpl;
import org.json.JSONArray;
import org.json.JSONObject;


public class MyTableModel  extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JSONArray	expenseListArr;
	JSONArray	vendorPaymentArr;
	JSONArray	invoiceListArr;
	JSONArray	vehicleExpListArr;
	JSONArray	driverBalanceListArr;
	String 	selectedCompany;
	JFrame frame = null;
	Object[][] fianldata = {};
    public MyTableModel() {
        super();
    }
    
    public MyTableModel(JSONArray	expenseListArr, JSONArray	vendorPaymentArr, JSONArray	invoiceListArr, String selectedCompany
    		, JSONArray	vehicleExpListArr, JSONArray	driverBalanceListArr) {
    	this.expenseListArr 	= expenseListArr;
    	this.vendorPaymentArr 	= vendorPaymentArr;
    	this.invoiceListArr		= invoiceListArr;
    	this.selectedCompany    = selectedCompany;
    	this.vehicleExpListArr	= vehicleExpListArr;
    	this.driverBalanceListArr = driverBalanceListArr;
    	
    	initializeUI();
    }

    private void initializeUI() {
    	int rowSize = 0;
    	int addedCount = 0;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(960, 600));
        
       if(expenseListArr != null && expenseListArr.length() > 0) {
    	   rowSize += expenseListArr.length();
       }
       if(vendorPaymentArr != null && vendorPaymentArr.length() > 0) {
    	   rowSize += vendorPaymentArr.length();
       }
       if(invoiceListArr != null && invoiceListArr.length() > 0) {
    	   rowSize += invoiceListArr.length();
       }
       if(vehicleExpListArr != null && vehicleExpListArr.length() > 0) {
    	   rowSize += vehicleExpListArr.length();
       }
       if(driverBalanceListArr != null && driverBalanceListArr.length() > 0) {
    	   rowSize += driverBalanceListArr.length();
       }
       
       fianldata = new Object[rowSize][10];
       if(expenseListArr != null && expenseListArr.length() > 0) {
    	   for (int i = 0; i < expenseListArr.length(); i++) {
    		   JSONObject	jsonObject	=	expenseListArr.getJSONObject(i);
    		   for (int j = 0; j < fianldata[i].length; j++) {
    			   if(j == 0) {
    				   fianldata[i][j] = Boolean.FALSE;
    			   }else if(j == 1){
    				   fianldata[i][j] = jsonObject.get("tripSheetNumberStr");
    			   }else if(j == 2){
    				   fianldata[i][j] = jsonObject.get("expenseFixed");
    			   }else if(j == 3){
    				   fianldata[i][j] = jsonObject.get("vendorName");
    			   }else if(j == 4){
    				   fianldata[i][j] = jsonObject.get("expenseName");
    			   }else if(j == 5){
    				   fianldata[i][j] = jsonObject.get("createdStr");
    			   }else if(j == 6){
    				   fianldata[i][j] = jsonObject.getDouble("expenseAmount");
    			   }else if(j == 7){
    				   fianldata[i][j] = jsonObject.get("expenseType");
    			   }else if(j == 8){
    				   fianldata[i][j] = jsonObject.get("vehicle_registration");
    			   }else if(j == 9){
    				   if(jsonObject.getBoolean("pendingForTally")) {
    					   fianldata[i][j] = "Already Added";
    				   }else {
    					   fianldata[i][j] = "Pending";
    				   }
    				   
    			   }
    		   }
    		   addedCount ++;
    	   }
       }
	   	if(invoiceListArr != null && invoiceListArr.length() > 0) {
	   		int invoiceLength = addedCount + invoiceListArr.length();
	   		int count = 0;
	   		for (int i = addedCount; i < invoiceLength; i++) {
	   			JSONObject	jsonObject	=	invoiceListArr.getJSONObject(count);
	   			for (int j = 0; j < fianldata[i].length; j++) {
	   				if(j == 0) {
	   					fianldata[i][j] = Boolean.FALSE;
	   				}else if(j == 1){
	   					fianldata[i][j] = jsonObject.get("tripSheetNumberStr");
	   				}else if(j == 2){
	   					fianldata[i][j] = jsonObject.get("expenseFixed");
	   				}else if(j == 3){
	   					fianldata[i][j] = jsonObject.get("vendorName");
	   				}else if(j == 4){
	   					fianldata[i][j] = jsonObject.get("expenseName");
	   				}else if(j == 5){
	   					fianldata[i][j] = jsonObject.get("createdStr");
	   				}else if(j == 6){
	   					fianldata[i][j] = jsonObject.get("expenseAmount");
	   				}else if(j == 7){
	   					fianldata[i][j] = jsonObject.get("expenseType");
	   				}else if(j == 8){
	    				   fianldata[i][j] = "";
	    			}else if(j == 9){
	    				   if(jsonObject.getBoolean("pendingForTally")) {
	    					   fianldata[i][j] = "Already Added";
	    				   }else {
	    					   fianldata[i][j] = "Pending";
	    				   }
	    			}
	   			}
	   			count ++;
	   			addedCount ++;
	   		}
	   	}
	   	if(vendorPaymentArr != null && vendorPaymentArr.length() > 0) {
	   		
	   		int paymentLength = addedCount + vendorPaymentArr.length();
	   		
	   		int count = 0;
	   		for (int i = addedCount; i < paymentLength; i++) {
	   			JSONObject	jsonObject	=	vendorPaymentArr.getJSONObject(count);
	   			for (int j = 0; j < fianldata[i].length; j++) {
	   				if(j == 0) {
	   					fianldata[i][j] = Boolean.FALSE;
	   				}else if(j == 1){
	   					fianldata[i][j] = jsonObject.get("tripSheetNumberStr");
	   				}else if(j == 2){
	   					fianldata[i][j] = jsonObject.get("expenseFixed");
	   				}else if(j == 3){
	   					fianldata[i][j] = jsonObject.get("vendorName");
	   				}else if(j == 4){
	   					fianldata[i][j] = jsonObject.get("ledgerName");
	   				}else if(j == 5){
	   					fianldata[i][j] = jsonObject.get("createdStr");
	   				}else if(j == 6){
	   					fianldata[i][j] = jsonObject.get("expenseAmount");
	   				}else if(j == 7){
	   					fianldata[i][j] = jsonObject.get("expenseType");
	   				}else if(j == 8){
	    				   fianldata[i][j] = "";
	    			}else if(j == 9){
	    				   if(jsonObject.getBoolean("pendingForTally")) {
	    					   fianldata[i][j] = "Added";
	    				   }else {
	    					   fianldata[i][j] = "Pending";
	    				   }
	    			}
	   			}
	   			count ++;
	   			addedCount ++;
	   		}
	   	}
	   		if(vehicleExpListArr != null && vehicleExpListArr.length() > 0) {
		   		int invoiceLength = addedCount + vehicleExpListArr.length();
		   		int count = 0;
		   		for (int i = addedCount; i < invoiceLength; i++) {
		   			JSONObject	jsonObject	=	vehicleExpListArr.getJSONObject(count);
		   			for (int j = 0; j < fianldata[i].length; j++) {
		   				if(j == 0) {
		   					fianldata[i][j] = Boolean.FALSE;
		   				}else if(j == 1){
		   					fianldata[i][j] = jsonObject.get("tripSheetNumberStr");
		   				}else if(j == 2){
		   					fianldata[i][j] = jsonObject.get("expenseFixed");
		   				}else if(j == 3){
		   					fianldata[i][j] = jsonObject.get("vendorName");
		   				}else if(j == 4){
		   					fianldata[i][j] = jsonObject.get("expenseName");
		   				}else if(j == 5){
		   					fianldata[i][j] = jsonObject.get("createdStr");
		   				}else if(j == 6){
		   					fianldata[i][j] = jsonObject.get("expenseAmount");
		   				}else if(j == 7){
		   					fianldata[i][j] = jsonObject.get("expenseType");
		   				}else if(j == 8){
		   				 fianldata[i][j] = jsonObject.get("vehicle_registration");
		    			}else if(j == 9){
		    				   if(jsonObject.getBoolean("pendingForTally")) {
		    					   fianldata[i][j] = "Already Added";
		    				   }else {
		    					   fianldata[i][j] = "Pending";
		    				   }
		    			}
		   			}
		   			count ++;
		   			addedCount ++;
		   		}
		   	}
	   		if(driverBalanceListArr != null && driverBalanceListArr.length() > 0) {
		   		int driverBalLength = addedCount + driverBalanceListArr.length();
		   		int count = 0;
		   		for (int i = addedCount; i < driverBalLength; i++) {
		   			JSONObject	jsonObject	=	driverBalanceListArr.getJSONObject(count);
		   			for (int j = 0; j < fianldata[i].length; j++) {
		   				if(j == 0) {
		   					fianldata[i][j] = Boolean.FALSE;
		   				}else if(j == 1){
		   					fianldata[i][j] = jsonObject.get("tripSheetNumberStr");
		   				}else if(j == 2){
		   					fianldata[i][j] = jsonObject.get("expenseFixed");
		   				}else if(j == 3){
		   					fianldata[i][j] = jsonObject.get("vendorName");
		   				}else if(j == 4){
		   					fianldata[i][j] = jsonObject.get("expenseName");
		   				}else if(j == 5){
		   					fianldata[i][j] = jsonObject.get("createdStr");
		   				}else if(j == 6){
		   					fianldata[i][j] = jsonObject.get("expenseAmount");
		   				}else if(j == 7){
		   					fianldata[i][j] = jsonObject.get("expenseType");
		   				}else if(j == 8){
		   				 fianldata[i][j] = jsonObject.get("vehicle_registration");
		    			}else if(j == 9){
		    				   if(jsonObject.getBoolean("pendingForTally")) {
		    					   fianldata[i][j] = "Already Added";
		    				   }else {
		    					   fianldata[i][j] = "Pending";
		    				   }
		    			}
		   			}
		   			count ++;
		   			addedCount ++;
		   		}
		   	}
	   	
	   	
   	
        JTable table = new JTable(new BooleanTableModel());
        table.setFillsViewportHeight(true);
        JScrollPane pane = new JScrollPane(table);
        
        JButton btnSubmit = new JButton("Click Here To Sync Selected");
		
		btnSubmit.setBackground(Color.BLUE);
		btnSubmit.setForeground(Color.MAGENTA);
		btnSubmit.setBounds(85, 140, 150, 23);
		
		btnSubmit.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				boolean isAnyVoucherSelected = false;
				String selectedItems	= "";
				String expenseListIdsToSync	= "";
				String invoiceListIdsToSync	= "";
				String paymentListIdsToSync	= "";
				String vehicleListIdsToSync	= "";
				String tripDBalanceToSync	= "";
				HashMap<String, String> syncListHM = new HashMap<String, String>();
				btnSubmit.setEnabled(false);
				
				TableModel model =	table.getModel();
				for(int i=0; i < model.getRowCount(); i++) {
					if((boolean) model.getValueAt(i, 0)) {
						isAnyVoucherSelected = true;
						selectedItems += model.getValueAt(i, 1)+",";
					}
				}
				if(!isAnyVoucherSelected) {
					JOptionPane.showMessageDialog(null, "Please Select Any Voucher To Sync !");
				}else {
					String [] selectedArr = selectedItems.split(",");
					for(int j = 0 ; j< selectedArr.length ; j++) {
						if(selectedArr[j].contains("TS")) {
							expenseListIdsToSync += selectedArr[j].split("_")[1]+",";
						}else if(selectedArr[j].contains("VA")) {
							paymentListIdsToSync += selectedArr[j]+",";
						}else if(selectedArr[j].contains("F") || selectedArr[j].contains("SE") || selectedArr[j].contains("RR")) {
							expenseListIdsToSync += selectedArr[j].split("_")[1]+",";
						}else if(selectedArr[j].contains("WO") || selectedArr[j].contains("LI")) {
							vehicleListIdsToSync += selectedArr[j];
						}else {
							invoiceListIdsToSync += selectedArr[j]+",";
						}
					}
					syncListHM.put("expenseIds", expenseListIdsToSync);
					syncListHM.put("invoiceIds", invoiceListIdsToSync);
					syncListHM.put("paymentIds", paymentListIdsToSync);
					syncListHM.put("vehicleListIdsToSync", vehicleListIdsToSync);
					syncListHM.put("selectedCompany", selectedCompany);
					TallyIntegrationServiceImpl impl = new TallyIntegrationServiceImpl();
					try {
						impl.getFinalListToSync(expenseListArr, vendorPaymentArr, invoiceListArr, syncListHM, vehicleExpListArr, driverBalanceListArr);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				btnSubmit.setEnabled(true);
			}
		});
		
		
		
        add(pane, BorderLayout.CENTER);
        add(btnSubmit, BorderLayout.PAGE_END);
    }

    public  void showFrame(JSONArray	expenseListArr, JSONArray	vendorPaymentArr, JSONArray	invoiceListArr, String selectedCompany, 
    		JSONArray	vehicleExpListArr, JSONArray	driverBalanceListArr) {
    	
    	JPanel panel = new MyTableModel(expenseListArr, vendorPaymentArr, invoiceListArr, selectedCompany, vehicleExpListArr, driverBalanceListArr);
        panel.setOpaque(true);

        frame = new JFrame("Voucher List to sync");
      //  frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(panel);
        frame.pack();
        frame.setVisible(true);
    }

  /*  public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	MyTableModel.showFrame();
            }
        });
    }
*/
    class BooleanTableModel extends AbstractTableModel {
    	
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		String[] columns = {"Select","Transaction No", "Payment Type", "Vendor", "Tally Head", "Date", "Amount", "Type", "Vehicle", "Status"};
        String trnsactionNumber = "";
        String type = "";
       
        Object[][] data = fianldata;
        
        public int getRowCount() {
            return data.length;
        }

        public int getColumnCount() {
            return columns.length;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }

        @Override
        public String getColumnName(int column) {
            return columns[column];
        }

        // This method is used by the JTable to define the default
        // renderer or editor for each cell. For example if you have
        // a boolean data it will be rendered as a check box. A
        // number value is right aligned.
		/*
		 * @Override public Class<?> getColumnClass(int columnIndex) { return
		 * data[0][columnIndex].getClass(); }
		 */
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return columnIndex == 0 ? Boolean.class : super.getColumnClass(columnIndex);
        }
        
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col == 0 ||col == 4) {
                return true;
            } else {
                return false;
            }
        }
        
        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        public void setValueAt(Object value, int row, int col) {
            data[row][col] = value;
            fireTableCellUpdated(row, col);
        }
        
    }
}
