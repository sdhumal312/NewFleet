$(document).ready(function() {
	getTripDetails();
	getDueAmount();
});

$(document).ready(function() {
	
	$("#driverId").select2( {
		 minimumInputLength: 2,
	        minimumResultsForSearch: 10,
	        ajax: {
	            url: "getDriverALLListOfCompany.in",
	            dataType: "json",
	            type: "POST",
	            contentType: "application/json",
	            quietMillis: 50,
	            data: function(a) {
	                return {
	                    term: a
	                }
	            },
	            results: function(a) {
	                return {
	                    results: $.map(a, function(a) {
	                        return {
	                        	text: a.driver_empnumber+" "+a.driver_firstname+" "+a.driver_Lastname+" - "+a.driver_fathername , slug: a.slug, id: a.driver_id
	                        }
	                    })
	                }
	            }
	        }
    });
});

function getTripDetails(){
	
	var jsonObject							= new Object();
	jsonObject["tripsheetId"] 	  			=  $('#tripsheetId').val();
	
	$.ajax({
        url: "getTripSheetByTripsheetId",
        type: "POST",
        dataType: 'json',
        data: jsonObject,
        success: function (data) {
        	setTripDetails(data);
        },
        error: function (e) {
       	 showMessage('errors', 'Some error occured!')
       	 hideLayer();
        }
	});
}

function setTripDetails(data){
	var tripsheet 	= data.tripsheet;
	console.log("tripsheet...",tripsheet);
	
	$("#tripNumber").text(tripsheet.tripSheetNumber);
	$("#showTripNumber").text("TS-"+tripsheet.tripSheetNumber);
	$("#vehicleRegistration").text(tripsheet.vehicle_registration);
	$("#route").text(tripsheet.routeName);
	$("#tripOpenDate").text(tripsheet.created);
	$("#vehicleGroup").text(tripsheet.dispatchedLocation);
	$("#driver").text(tripsheet.tripFristDriverName+' '+tripsheet.tripFristDriverLastName+' - '+tripsheet.tripFristDriverFatherName);
	$("#conductor").text(tripsheet.tripSecDriverName+' '+tripsheet.tripSecDriverLastName+' - '+tripsheet.tripSecDriverFatherName);
	$("#cleaner").text(tripsheet.tripCleanerName);
	$("#tripOpenKm").text(tripsheet.tripOpeningKM);
	$("#tripCloseDate").text(tripsheet.tripClosingKM);
	$("#tripUsage").text(tripsheet.tripTotalUsageKM);
	$("#createdDate").text(tripsheet.dispatchedByTime);
	$("#vid").val(tripsheet.vid);
	$("#B_Income").val(data.B_IncomeTotal);
	$("#E_Income").val(data.E_IncomeTotal);
}

function saveDueAmount(){
	if($('#driverId').val() <= 0){
		showMessage('info','Please Select Driver to Process');
		return false;
	}
	if($('#dueAmount').val() == ''){
		showMessage('info','Please Enter Due amount');
		return false;
	}
	
	if($('#dueDate').val() == '' || $('#approxDate').val() == ''){
		showMessage('info','Please Select Date to Process');
		return false;
	}
	if($("#showbilltypeDropdown").val() == true || $("#showbilltypeDropdown").val() == "true"){
		if($("#billselectionId").val() == 1 && Number($('#dueAmount').val()) > Number($("#B_Income").val())){
			showMessage('info','Due Amount cannot be Greater than B Income');
			return false;
		}
		if($("#billselectionId").val() == 2 && Number($('#dueAmount').val()) > Number($("#E_Income").val()) ){
			showMessage('info','Due Amount cannot be Greater than E Income');
			return false;
		}
		
	}
	var jsonObject							= new Object();
	jsonObject["vid"] 	  					=  $('#vid').val();
	jsonObject["tripNumber"] 	  			=  $('#tripNumber').text();
	jsonObject["tripsheetId"] 	  			=  $('#tripsheetId').val();
	jsonObject["driverId"] 	  				=  $('#driverId').val();
	jsonObject["dueAmount"] 	  			=  $('#dueAmount').val();
	jsonObject["approxDate"] 	  			=  $('#approxDate').val();
	jsonObject["dueDate"] 	  				=  $('#dueDate').val();
	jsonObject["remark"] 	  				=  $('#remark').val();
	
	if($("#billselectionId").val() !=null){
		jsonObject["billselectionId"]			=  $("#billselectionId").val();
	}else{
		jsonObject["billselectionId"]			=  0;
	}

	showLayer();
	
	$.ajax({
		url: "saveDueAmount",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			location.reload();
			showMessage('Success','Due Amount Added Successfully');
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			hideLayer();
		}
	});
}

function getDueAmount(){

	var jsonObject							= new Object();
	jsonObject["tripsheetId"] 	  			=  $('#tripsheetId').val();
	
	$.ajax({
		url: "getDueAmountList",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			console.log("data...",data);
			if(data != undefined){
				setDueAmount(data);
			}else{
				showMessage('info','No Record Found')
			}
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			hideLayer();
		}
	});

}

function setDueAmount(data) {
	$("#dueAmountTable").empty();
	//$("#totalTallyCompany").html(data.count);
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th>');
	var th5		= $('<th>');
	var th6		= $('<th>');
	var th7     = $('<th>');

	tr1.append(th1.append("No"));
	tr1.append(th2.append("Name"));
	tr1.append(th3.append("Due Amount"));
	if($("#showDueIncomeType").val() == true || $("#showDueIncomeType").val() == "true"){
		tr1.append(th7.append("Due Income Type"));
	}
	tr1.append(th4.append("Approx Date"));
	tr1.append(th5.append("Due Date"));
	tr1.append(th6.append("Actions"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.dueAmountList != undefined && data.dueAmountList.length > 0) {
		
		var dueAmountList = data.dueAmountList;
	
		for(var i = 0; i < dueAmountList.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td>');
			var td5		= $('<td>');
			var td6		= $('<td>');
			var td7     = $('<td>');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append(dueAmountList[i].driver_firstname + " "+dueAmountList[i].driver_Lastname));
			tr1.append(td3.append(dueAmountList[i].dueAmount));
			if($("#showDueIncomeType").val() == true || $("#showDueIncomeType").val() == "true"){
				tr1.append(td7.append(dueAmountList[i].billType));
			}
			tr1.append(td4.append(dueAmountList[i].approximateDateStr));
			tr1.append(td5.append(dueAmountList[i].dueDateStr));
			tr1.append(td6.append("<a href='#' class='confirmation' onclick='removeDueAmount("+dueAmountList[i].tripsheetDueAmountId+");'><span class='fa fa-trash'></span> Delete</a>"));

			tbody.append(tr1);
		}
	}
	
	$("#dueAmountTable").append(thead);
	$("#dueAmountTable").append(tbody);

}

function removeDueAmount(tripsheetDueAmountId){

	var jsonObject							= new Object();
	jsonObject["tripsheetDueAmountId"] 	  	=  tripsheetDueAmountId;
	
	$.ajax({
		url: "removeDueAmount",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			showMessage('info','scuccessfully Removed')
			location.reload();
			},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			hideLayer();
		}
	});

}



