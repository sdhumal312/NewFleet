$(function() {
    function a(a, b) {
        $("#reportrange").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
    }
    a(moment().subtract(1, "days"), moment()), $("#reportrange").daterangepicker( {
        format:'DD-MM-YYYY',
        ranges: {
            Today: [moment(), moment()],
            Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")],
            "Last 7 Days": [moment().subtract(6, "days"), moment()],
            "This Month": [moment().startOf("month"), moment().endOf("month")],
            "Last Month": [moment().subtract(1, "months").startOf("month"), moment().subtract(1, "months").endOf("month")]
        }
    }
    , a)
}

),

$(document).ready(function() {
	 $("#routeId").select2( {
       ajax: {
           url:"getTripRouteList.in", dataType:"json", type:"POST", contentType:"application/json", data:function(e) {
               return {
                   term: e
               }
           }
           , results:function(e) {
               return {
                   results:$.map(e, function(e) {
                       return {
                           text: e.routeNo+" "+e.routeName, slug: e.slug, id: e.routeID
                       }
                   }
                   )
               }
           }
       }
   }
   )
});

$(document).ready(
		function($) {
			$('button[type=submit]').click(function(e) {
				e.preventDefault();
				showLayer();
				var jsonObject								= new Object();				
				jsonObject["routeId"] 						= $('#routeId').val();
				jsonObject["dateRange"] 					= $('#reportrange').val();
				
				$.ajax({					
					url: "getRouteWiseProfitAndLossReport",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {	
						renderReportData(data);						
						hideLayer();
					},
					error: function (e) {
						showMessage('errors', 'Some error occured!')
						hideLayer();
					}
				});

				
			});

		});

function renderReportData(resultData) {
	var finalList = resultData.tripSheetDtoMap;
	if(finalList != undefined && finalList.length > 0 ){
		setHeaderData(resultData.company);
		var columnConfiguration ;
		var tableProperties;
		$('#ResultContent').show();
		$("#companyTable tr").remove();
		$("#selectedReportDetails tr").remove();
		$('#myGrid').empty();
		
		$("#reportHeader").html("ROUTE WISE PROFIT AND LOSS REPORT");
		
		var data = $('#routeId').select2('data');
		if(data){
			var tr =' <tr data-object-id="">'
				+'<td class="fit"> Route: '+data.text+'</td>'
				+'</tr>';
			$('#selectedReportDetails').append(tr);
		}else{
			var tr =' <tr data-object-id="">'
				+'<td class="fit"> ALL ROUTE</td>'
				+'</tr>';
			$('#selectedReportDetails').append(tr);
		}

		
		if(resultData.tableConfig != undefined) {
			var ColumnConfig = resultData.tableConfig.columnConfiguration;
			var columnKeys	= _.keys(ColumnConfig);
			var bcolConfig		= new Object();
			
			for (var i = 0; i < columnKeys.length; i++) {
				var bObj	= ColumnConfig[columnKeys[i]];
				
				if (bObj.show != undefined && bObj.show == true) {
					bcolConfig[columnKeys[i]] = bObj;
				}
			}
		
			columnConfiguration	= _.values(bcolConfig);
			tableProperties	=  resultData.tableConfig.tableProperties;
			
		}
		setSlickData(finalList, columnConfiguration, tableProperties);
		
		
		$('#gridContainer').show();
		$('#printBtn').removeClass('hide');
	}else{
		//$('#gridContainer').hide();
		$('#ResultContent').hide();
	    $('#printBtn').addClass('hide');
	    showMessage('info', 'No record found !');
	}

}
var companyName;
function setHeaderData(company){
	$("#companyTable tr").remove();
	if(company != undefined && company != null){
		$('#companyTable').show();
		if(company.company_id_encode != null){
			$('#companyHeader').append('<tr id="imgRow"><td id="companyLogo"> </td><td id="printBy"</td></tr>');
			$('#companyHeader').append('<tr><td colspan="2" id="branchInfo"></td></tr>');
			$('#companyLogo').append('<img id="imgSrc" src="downloadlogo/'+company.company_id_encode+'.in" class="img-rounded " alt="Company Logo" width="280" height="40" />');		
		 	$('#printBy').html('Print By : '+company.firstName+'_'+company.lastName); 
		 	$('#branchInfo').html('Branch : '+company.branch_name+' , Department : '+company.department_name);
		}
		$('#companyName').html(company.company_name);
	}
}



function showProfitAndLossDetails(gridObj, dataView, row){
	if(dataView.routeID != null){
		var jsonObject								= new Object();				
		jsonObject["routeId"] 						= dataView.routeID;
		jsonObject["dateRange"] 					= $('#reportrange').val();
		
		$.ajax({					
			url: "getRouteWiseUsageReport",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {	
				setReportData(data);						
				hideLayer();
			},
			error: function (e) {
				showMessage('errors', 'Some error occured!')
				hideLayer();
			}
		});
	}
}

function setReportData(data) {
	$('#getExpensesModel').modal('show');
	var tripSheetDtoList	= null;
	if(data.tripSheetDtoList != undefined && data.tripSheetDtoList != null && data.tripSheetDtoList.length > 0 ) {
		
		$("#reportHeader").html("Route Wise Usage And Movement Report");
		$("#batteryTransferDetails").empty();
		
		tripSheetDtoList	= data.tripSheetDtoList;
		
		var thead = $('<thead style="background-color: aqua;">');
		var tbody = $('<tbody>');
		var tr1 = $('<tr style="font-weight: bold; font-size : 12px;">');

		var th1		= $('<th>');
		var th2		= $('<th>');
		var th3		= $('<th>');
		var th4		= $('<th>');
		var th5		= $('<th>');
		var th6		= $('<th>');
		var th7		= $('<th>');
		var th8		= $('<th>');
		var th9		= $('<th>');
		var th10	= $('<th>');
		/*var th11	= $('<th>');*/

		th1.append('Sr No');
		th2.append('TripSheet No');
		th3.append('Open Date');
		th4.append('Close Date');
		th5.append('Vehicle');
		th6.append('No Of KM');
		th7.append('Income');
		th8.append('Total Expense');
		th9.append('Fuel Expense');
		th10.append('Toll Expense');
		/*th11.append('Balance');*/

		tr1.append(th1);
		tr1.append(th2);
		tr1.append(th3);
		tr1.append(th4);
		tr1.append(th5);
		tr1.append(th6);
		tr1.append(th7);
		tr1.append(th8);
		tr1.append(th9);
		tr1.append(th10);
		/*tr1.append(th11);*/
		
		thead.append(tr1);
		
		var totalKM	= 0;
		var totalIncome=0;
		var totalExpense=0;
		var totalFuelExpense=0;
		var totalTollExpense=0;
		/*var totalBalance=0;*/

		for(var i = 0; i < tripSheetDtoList.length; i++ ) {
			var tr = $('<tr>');

			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td>');
			var td4		= $('<td>');
			var td5		= $('<td>');
			var td6		= $('<td>');
			var td7		= $('<td>');
			var td8		= $('<td>');
			var td9		= $('<td>');
			var td10	= $('<td>');
			/*var td11	= $('<td>');*/
			
			if(data.tripFlavor == 1){
				var tripSheetUrl	= "showTripSheet.in?tripSheetID="+tripSheetDtoList[i].tripSheetID;
			}else{
				var tripSheetUrl	= "showTripDaily.in?ID="+tripSheetDtoList[i].tripSheetID;
			}
			var vehicleUrl	= "showVehicle.in?vid="+tripSheetDtoList[i].vid;
			
			td1.append(i+1);
			//td2.append(tripSheetDtoList[i].tripSheetNumber);
			td2.append('<a target="_blank" href="' + tripSheetUrl + '">'+tripSheetDtoList[i].tripSheetNumber+'</a><br>');
			td3.append(tripSheetDtoList[i].tripOpenDate);
			td4.append(tripSheetDtoList[i].closetripDate);
			td5.append('<a target="_blank" href="' + vehicleUrl + '">'+tripSheetDtoList[i].vehicle_registration+'</a><br>');
			td6.append(tripSheetDtoList[i].tripUsageKM);
			td7.append(tripSheetDtoList[i].tripTotalincome);
			td8.append(tripSheetDtoList[i].expenseAmount);
			td9.append(tripSheetDtoList[i].fuelExpenseAmount);
			td10.append(tripSheetDtoList[i].tollExpenseAmount);
			/*td11.append((tripSheetDtoList[i].tripTotalincome - (tripSheetDtoList[i].expenseAmount +tripSheetDtoList[i].fuelExpenseAmount+tripSheetDtoList[i].tollExpenseAmount)).toFixed(2));*/
			
		/*	bal += tripSheetDtoList[i].tripTotalincome - (tripSheetDtoList[i].expenseAmount +tripSheetDtoList[i].fuelExpenseAmount+tripSheetDtoList[i].tollExpenseAmount);*/
			totalKM += tripSheetDtoList[i].tripUsageKM;
			
			totalIncome += tripSheetDtoList[i].tripTotalincome;
			
			totalExpense += tripSheetDtoList[i].expenseAmount;
			
			totalFuelExpense += tripSheetDtoList[i].fuelExpenseAmount;
			
			totalTollExpense += tripSheetDtoList[i].tollExpenseAmount;
			
		//	totalBalance += totalIncome-(totalExpense + totalFuelExpense + totalTollExpense);
			/*totalBalance += bal;*/

			tr.append(td1);
			tr.append(td2);
			tr.append(td3);
			tr.append(td4);
			tr.append(td5);
			tr.append(td6);
			tr.append(td7);
			tr.append(td8);
			tr.append(td9);
			tr.append(td10);
			/*tr.append(td11);*/
			

			tbody.append(tr);
		}
		var tr2 = $('<tr>');

		var td1		= $('<td colspan="5">');
		var td2		= $('<td>');
		var td3		= $('<td>');
		var td4		= $('<td>');
		var td5		= $('<td>');
		var td6		= $('<td>');
		/*var td7		= $('<td>');*/


		td1.append("Total  :");
		td2.append(totalKM);
		td3.append(totalIncome);
		td4.append(totalExpense);
		td5.append(totalFuelExpense);
		td6.append(totalTollExpense);
		/*td7.append(totalBalance);*/
		
		tr2.append(td1);
		tr2.append(td2);
		tr2.append(td3);
		tr2.append(td4);
		tr2.append(td5);
		tr2.append(td6);
		/*tr2.append(td7);*/

		tbody.append(tr2);

		$("#batteryTransferDetails").append(thead);
		$("#batteryTransferDetails").append(tbody);
		
		$("#ResultContent").removeClass("hide");
		$("#printBtn").removeClass("hide");
		$("#exportExcelBtn").removeClass("hide");
	} else {
		showMessage('info','No record found !');
		$("#ResultContent").addClass("hide");
		$("#printBtn").addClass("hide");
		$("#exportExcelBtn").addClass("hide");
	}
}


