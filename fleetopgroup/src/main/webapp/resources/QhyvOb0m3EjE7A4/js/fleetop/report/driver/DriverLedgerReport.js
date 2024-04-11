$(document).ready(function() {
   $("#driverId").select2( {
	   minimumInputLength:3, minimumResultsForSearch:10, ajax: {
           url:"getDriverALLListOfCompany.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
               return {
                   term: a
               }
           }
           , results:function(a) {
               return {
                   results:$.map(a, function(a) {
                       return {
                           text: a.driver_empnumber+" "+a.driver_firstname+" "+a.driver_Lastname+" - "+a.driver_fathername, slug: a.slug, id: a.driver_id
                       }
                   }
                   )
               }
           }
       }
    });
});

function validateDriverLedger()
{
	if(Number($('#driverId').val()) <= 0){
		showMessage('info','Please Select Driver Name!');
		return false;
	}
}

$(document).ready(
		function($) {
			$('button[type=submit]').click(function(e) {
				e.preventDefault();
				showLayer();
				var jsonObject							= new Object();				
				jsonObject["driverId"] 					= $('#driverId').val();
				jsonObject["dateRange"] 				= $('#dateRangeReport').val();
			
				$.ajax({					
					url: "/DriverLedgerReport",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {						
						FectchReportData(data);						
						hideLayer();
					},
					error: function (e) {
						showMessage('errors', 'Some error occured!')
						hideLayer();
					}
				});

			});
	});

function FectchReportData(data)
{
	$("#driverLedger").show();
	$('#date').html(data.DATE_RANGE);
	$('#driver').html(data.DriverName);
	$('#openBal').html("Rs." +data.OpeninngBal);
	$("#closeBal").html("Rs."+ data.ClosingBal);

	var driver_Ledger_Report	= null;
	if(data.DriverLedgerList != undefined) {
		$("#reportHeader").html("Driver Ledger Report");
		$("#driverLedgerTable").empty();
		
		driver_Ledger_Report	= data.DriverLedgerList;
		var driver_Ledger_Report_Final =  new Array();
		for(var i =0; i<driver_Ledger_Report.length; i++)
		{
			var driverReportObj = new Object();
			driverReportObj.txnDateStr 			= driver_Ledger_Report[i].txnDateStr;
			driverReportObj.debitAmount 		= driver_Ledger_Report[i].debitAmount;
			driverReportObj.creditAmount	    = driver_Ledger_Report[i].creditAmount;
			driverReportObj.openingBalance		= driver_Ledger_Report[i].openingBalance;
			driverReportObj.closingBalance 		= driver_Ledger_Report[i].closingBalance;
			driverReportObj.transactionNo 		= driver_Ledger_Report[i].transactionNo;
			driverReportObj.txnTypeStr 			= driver_Ledger_Report[i].txnTypeStr;
			driverReportObj.description 		= driver_Ledger_Report[i].description;
			driverReportObj.tripsheetId 		= driver_Ledger_Report[i].tripsheetId;
			driver_Ledger_Report_Final.push(driverReportObj);
		}
		
		var thead = $('<thead>');
		var tbody = $('<tbody>');
		var tr1 = $('<tr style="height:35px;">');
	
		var th1 = $('<th style="font-size:14px;">'); 
		var th2 = $('<th style="font-size:14px;">');
		var th3 = $('<th style="font-size:14px;">');
		var th4 = $('<th style="font-size:14px;">');
		var th5 = $('<th style="font-size:14px;">');
		var th6 = $('<th style="font-size:14px;">');
		var th7 = $('<th style="font-size:14px;">');
		var th8 = $('<th style="font-size:14px;">');
		var th9 = $('<th style="font-size:14px;">');
		
		th1.append("Sr No");
		th2.append("Txn Date");
		th3.append("Debit Amount");
		th4.append("Credit Amount");
		th5.append("Opening Balance");
		th6.append("Closing Balance");
		th7.append("Txn No");
		th8.append("Txn Type");
		th9.append("Description");
		
		tr1.append(th1,th2,th3,th4,th5,th6,th7,th8,th9);		
		thead.append(tr1);
		
		var i=0;
		for(var key in driver_Ledger_Report_Final){
			var tr = $('<tr style="height:30px;">');
			(function(tr) {
			        tr.mouseover(function() {
			            tr.css('background-color', '#aeb6bf');
			            tr.css('color', 'black');
			            tr.find('td').css('font-size', '14px');
			        });
				
					tr.mouseout(function() {
			            tr.css('background-color', '');
			            tr.css('color', '');
		  				tr.css('font-weight', '');
		  				 tr.find('td').css('font-size', '');
			        });
			        var td1 = $('<td>');
			        var td2 = $('<td>');
			        var td3 = $('<td>');
			        var td4 = $('<td>');
			        var td5 = $('<td>');
			        var td6 = $('<td>');
			        var td7 = $('<td>');
			        var td8 = $('<td>');
			        var td9 = $('<td>');
					
					var txnNo;
					if(driver_Ledger_Report_Final[key].transactionNo != ""){ 	
						txnNo ='<a style="color:blue;" href="/showTripSheet.in?tripSheetID='+ driver_Ledger_Report_Final[key].tripsheetId +'" target="_blank" >TS-'+ driver_Ledger_Report_Final[key].transactionNo+'</a>'
					}
				    td1.append(i+1); i++;
					td2.append(driver_Ledger_Report_Final[key].txnDateStr);
					td3.append(driver_Ledger_Report_Final[key].debitAmount);
					td4.append(driver_Ledger_Report_Final[key].creditAmount);
					td5.append(driver_Ledger_Report_Final[key].openingBalance);
					td6.append(driver_Ledger_Report_Final[key].closingBalance);
					td7.append(txnNo);
					td8.append(driver_Ledger_Report_Final[key].txnTypeStr);
					td9.append(driver_Ledger_Report_Final[key].description);
			        tr.append(td1, td2, td3, td4, td5, td6, td7, td8, td9);
			    })(tr);
			tbody.append(tr);
		}
		$("#driverLedgerTable").append(thead);
		$("#driverLedgerTable").append(tbody);
		
		$("#ResultContent").removeClass("hide");
		$("#printBtn").removeClass("hide");
		$("#exportExcelBtn").removeClass("hide");
		
	}else {
		showMessage('info','No record found !');
		$("#ResultContent").addClass("hide");
		$("#printBtn").addClass("hide");
		$("#exportExcelBtn").addClass("hide");
	}
		
}
