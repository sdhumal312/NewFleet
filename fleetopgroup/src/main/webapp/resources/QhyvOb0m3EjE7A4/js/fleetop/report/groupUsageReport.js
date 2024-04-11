$(function() {
    function a(a, b) {
        $("#reportrange").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
    }
    a(moment().subtract(1, "days"), moment()), $("#reportrange").daterangepicker( {
        format:'DD-MM-YYYY',
        ranges: {
            Today: [moment(), moment()], Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")], "Last 7 Days": [moment().subtract(6, "days"), moment()]
        }
    }
    , a)
}

),
$(function() {
    $("#vid").select2()
}

);

$(document).ready(function() {
	$("#vehicleGroupId").select2( {
		ajax: {
			url:"getVehicleGroup.in", dataType:"json", type:"GET", contentType:"application/json", data:function(a) {
				return {
					term: a
				}
			}
	, results:function(a) {
		return {
			results:$.map(a, function(a) {
				return {
					text: a.vGroup, slug: a.slug, id: a.gid
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
			$('button[id=btn-save]').click(function(e) {
				e.preventDefault();

				var jsonObject			= new Object();

				jsonObject["dateRange"] 	  	=  $('#reportrange').val();
				jsonObject["vehicleGroupId"] 	=  $('#vehicleGroupId').val();
				
				
				if(Number($('#vehicleGroupId').val()) <= 0){
					showMessage('errors', 'Please Select Vehicle Group!');
					return false;
				}
				showLayer();
				$.ajax({
			             url: "getGroupUsageReport",
			             type: "POST",
			             dataType: 'json',
			             data: jsonObject,
			             success: function (data) {
			                renderReportData(data);
			                hideLayer();
			             },
			             error: function (e) {
			            	 showMessage('errors', 'Some error occured!')
			             }
				});
			
			});

		});

function renderReportData(data){
	if(data.tripSheetDtoList != null && data.tripSheetDtoList.length > 0){
		//setHeaderData(data.company);
		setReportData(data);
	}else{
		showMessage('errors', 'NO Record Found !');
	}
}

function setHeaderData(company){
	$("#companyTable tr").remove();
	if(company != undefined && company != null){
		$('#companyTable').show();
		if(company.company_id_encode != null){
			$('#tbodyHeader').append('<tr id="imgRow"><td id="companyLogo"> </td><td id="printBy"</td></tr>');
			$('#tbodyHeader').append('<tr><td colspan="2" id="branchInfo"></td></tr>');
			$('#companyLogo').append('<img id="imgSrc" src="downloadlogo/'+company.company_id_encode+'.in" class="img-rounded " alt="Company Logo" width="280" height="40" />');		
		 	$('#printBy').html('Print By : '+company.firstName+'_'+company.lastName); 
		 	$('#branchInfo').html('Branch : '+company.branch_name+' , Department : '+company.department_name);
		

		}
	}
}
function setReportData(data) {
	var tripSheetDtoList	= null;
	if(data.tripSheetDtoList != undefined) {
		$("#reportHeader").html("Group Wise Vehicle Usage And Movement Report");

		$("#batteryTransferDetails").empty();
		$("#selectedReportDetails").empty();
		
		
		var ReportSelectVehicle = $('#vehicleGroupId').select2('data');
		var dateRange 		= $('#reportrange').val().split("to");
		var startDate 		= dateRange[0].split("-").reverse().join("-").replace(/ /g,'');
		var endDate 		= dateRange[1].split("-").reverse().join("-").replace(/ /g,'');
		var newDateRange 	= startDate.split("-").reverse().join("-") +" To "+ endDate.split("-").reverse().join("-");
		
		if(ReportSelectVehicle){
			var tr =' <tr data-object-id="">'
						+'<td class="fit" style="font-size: initial;"> Group : '+ReportSelectVehicle.text+'</td>'
					+'</tr>';
			$('#selectedReportDetails').append(tr);
		}
		if(newDateRange){
			var tr =' <tr data-object-id="">'
						+'<td class="fit" style="font-size: initial;"> DateRange : '+newDateRange+'</td>'
					+'</tr>';
			$('#selectedReportDetails').append(tr);
		}
		
		tripSheetDtoList	= data.tripSheetDtoList;
		
		tripSheetDtoList.sort(function(a, b) {
			
			var keyA = new Date((a.closetripDate).split("-").reverse().join("-")),
			keyB = new Date((b.closetripDate).split("-").reverse().join("-"));
			
			if (keyA < keyB) return -1;
			if (keyA > keyB) return 1;
			return 0;
			
		});
		

	
		
		var thead = $('<thead class="sticky" style="background-color: aqua; width: 100%;" >');
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
		var th11	= $('<th>');
		var th12	= $('<th>');
		var th13        = $('<th>');

		th1.append('Sr No');
		th2.append('TripSheet No');
		th3.append('Open Date');
		th4.append('Close Date');
		th5.append('Vehicle');
		th6.append('Route');
		th7.append('No Of KM');
		th8.append('GPS Usage');
		th9.append('Income');
		th10.append('Expense');
		th11.append('Fuel Expense');
		th12.append('Toll Expense');
		th13.append('Balance');

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
		tr1.append(th11);
		tr1.append(th12);
		tr1.append(th13);

		thead.append(tr1);
		
		var totalKM	= 0;
		var totalGPSKM	= 0;
		var totalIncome=0;
		var totalExpense=0;
		var totalFuelExpense=0;
		var totalTollExpense=0;
		var totalBalance=0;

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
			var td11	= $('<td>');
			var td12	= $('<td>');
			var td13        = $('<td>');
			
			
			
			if(data.tripFlavor == 1){
				var tripSheetUrl	= "showTripSheet.in?tripSheetID="+tripSheetDtoList[i].tripSheetID;
			}else{
				var tripSheetUrl	= "showTripDaily.in?ID="+tripSheetDtoList[i].tripSheetID;
			}
			td1.append(i+1);
			td2.append('<a target="_blank" href="' + tripSheetUrl + '">'+tripSheetDtoList[i].tripSheetNumber+'</a><br>');
			td3.append(tripSheetDtoList[i].tripOpenDate);
			td4.append(tripSheetDtoList[i].closetripDate);
			td5.append(tripSheetDtoList[i].vehicle_registration);
			td6.append(tripSheetDtoList[i].routeName);
			td7.append(tripSheetDtoList[i].tripUsageKM);
			td8.append(tripSheetDtoList[i].tripGpsUsageKM);
			td9.append(tripSheetDtoList[i].tripTotalincome);
			td10.append(tripSheetDtoList[i].expenseAmount);
			td11.append(tripSheetDtoList[i].fuelExpenseAmount.toFixed(2));
			td12.append(tripSheetDtoList[i].tollExpenseAmount);
			td13.append((tripSheetDtoList[i].tripTotalincome - (tripSheetDtoList[i].expenseAmount +tripSheetDtoList[i].fuelExpenseAmount+tripSheetDtoList[i].tollExpenseAmount)).toFixed(2));
			
			totalKM += tripSheetDtoList[i].tripUsageKM;
			totalGPSKM += tripSheetDtoList[i].tripGpsUsageKM;
			
			totalIncome += tripSheetDtoList[i].tripTotalincome;
			totalExpense += tripSheetDtoList[i].expenseAmount;
			
			totalFuelExpense += tripSheetDtoList[i].fuelExpenseAmount;
			
			totalTollExpense += tripSheetDtoList[i].tollExpenseAmount;
			
		//	totalBalance += ((tripSheetDtoList[i].tripTotalincome - (tripSheetDtoList[i].expenseAmount +tripSheetDtoList[i].fuelExpenseAmount+tripSheetDtoList[i].tollExpenseAmount)).toFixed(2));
			
			
			
		

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
			tr.append(td11);
			tr.append(td12);
			tr.append(td13);
			
			
			/*tr.append(td6.append(totalKM));
			tr.append(td7.append(totalIncome));
			tr.append(td8.append(totalExpense));
			tr.append(td9.append(totalBalance));*/
			
			
			

			tbody.append(tr);
		}
		var tr2 = $('<tr>');

		var td1		= $('<td colspan="6">');
		var td2		= $('<td>');
		var td3		= $('<td>');
		var td4		= $('<td>');
		var td5		= $('<td>');
		var td6		= $('<td>');
		var td7		= $('<td>');
		var td8		= $('<td>');
		

		totalBalance = totalIncome-(totalExpense + totalFuelExpense + totalTollExpense);

		td1.append("Total  :");
		td2.append(totalKM);
		td3.append(totalGPSKM.toFixed(2));
		td4.append(totalIncome.toFixed(2));
		td5.append(totalExpense.toFixed(2));
		td6.append(totalFuelExpense.toFixed(2));
		td7.append(totalTollExpense.toFixed(2));
		td8.append(totalBalance.toFixed(2));
		
		tr2.append(td1);
		tr2.append(td2);
		tr2.append(td3);
		tr2.append(td4);
		tr2.append(td5);
		tr2.append(td6);
		tr2.append(td7);
		tr2.append(td8);
		

		tbody.append(tr2);

		
		
		$("#batteryTransferDetails").append(thead);
		$("#batteryTransferDetails").append(tbody);
		
		$("#ResultContent").removeClass("hide");
		$("#printBtn").removeClass("hide");
		$("#exportExcelBtn").removeClass("hide");
		$("#printPdf").removeClass("hide");
		
	} else {
		showMessage('info','No record found !');
		$("#ResultContent").addClass("hide");
		$("#printBtn").addClass("hide");
		$("#exportExcelBtn").addClass("hide");
		$("#printPdf").addClass("hide");
	}
}

