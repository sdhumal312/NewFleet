var tripsheetVehicleId;
let TRIP_STATUS_CLOSED =3;
let TRIP_STATUS_ACCOUNT_CLOSED=4;

function saveTripSheetComment(){
	if($('#comment').val() == null && $('#comment').val().trim() == '' ){
		showMessage('info', 'Please Enter TripSheet Comment !');
		return false;
	}
	var jsonObject			= new Object();
	
	jsonObject["tripsheetId"] 	  	=  $('#tripSheetId').val();
	jsonObject["companyId"] 	  	=  $('#companyId').val();         
	jsonObject["userId"] 	  		=  $('#userId').val();
	jsonObject["comment"] 	  		=  $('#comment').val();
	
	showLayer();
	$.ajax({
             url: "saveTripComment",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 showMessage('success', 'Data added successfully !');
            	 location.replace('showTripSheet?tripSheetID='+$('#tripSheetId').val());
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});
}

function removeTripComment(commentId){
	if($('#comment').val() == null && $('#comment').val().trim() == '' ){
		showMessage('info', 'Please Enter TripSheet Comment !');
		return false;
	}
	var jsonObject			= new Object();
	
	jsonObject["tripsheetId"] 	  	=  $('#tripSheetId').val();
	jsonObject["companyId"] 	  	=  $('#companyId').val();
	jsonObject["commentId"] 	  	=  commentId;
	
	showLayer();
	$.ajax({
             url: "removeTripComment",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 showMessage('success', 'Data Deleted successfully !');
            	 location.replace('showTripSheet?tripSheetID='+$('#tripSheetId').val());
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});

}
function deleteTripSheetData(){
	
		var jsonObject					= new Object();
		jsonObject["tripsheetId"] 	  	=  $('#tripSheetId').val();
		jsonObject["companyId"] 	  	=  $('#companyId').val();
		jsonObject["userId"] 	  		=  $('#userId').val();
		
		deleteTripSheetDetails(jsonObject);
}

function deleteTripSheet(tripSheetId){
		var jsonObject					= new Object();
		jsonObject["tripsheetId"] 	  	=  tripSheetId;
		jsonObject["companyId"] 	  	=  $('#companyId').val();
		jsonObject["userId"] 	  		=  $('#userId').val();
		deleteTripSheetDetails(jsonObject);
}

function deleteTripSheetDetails(jsonObject){
	
	if(confirm("Are You Sure To Delete ?")) {
		showLayer();
		$.ajax({
	             url: "deleteTripSheetDetails",
	             type: "POST",
	             dataType: 'json',
	             data: jsonObject,
	             success: function (data) {
	            	 if(data.deleteDH != undefined && data.deleteDH){
	            		 showMessage('info', 'Please delete all driver halt entry first !');
	            		 hideLayer();
	            	 }else if(data.deleteFuel != undefined && data.deleteFuel){
	            		 showMessage('info', 'Please delete all Fuel entry first !');
	            		 hideLayer();
	            	 }else if(data.deleteFuel != undefined && data.deletePenalty){
	            		 showMessage('info', 'Please delete all driver penalty entry first !');
	            		 hideLayer();
	            	 }else if(data.Closed != undefined && data.Closed){
	            		 showMessage('info', 'Cannot delete tripsheet already closed !');
	            		 hideLayer();
	            	 }else if(data.accountClosed != undefined && data.accountClosed){
	            		 showMessage('info', 'Cannot delete tripsheet already account closed !');
	            		 hideLayer();
	            	 }else if(data.deleteExpense != undefined && data.deleteExpense){
	            		 showMessage('info', 'Please delete all TripSheet expenses and penalty first !');
	            		 hideLayer();
	            	 }else if(data.deleteIncome != undefined && data.deleteIncome){
	            		 showMessage('info', 'Please delete All TripSheet income first !');
	            		 hideLayer();
	            	 }else if(data.deleteAdvance != undefined && data.deleteAdvance){
	            		 showMessage('info', 'Please delete all TripSheet Advance First !');
	            		 hideLayer();
	            	 }else if(data.accidentId != undefined && data.accidentId > 0){
	            		 showMessage('info', 'Cannot Delete , Accident Details are created on this tripSheet !');
	            		 hideLayer();
	            	 }else{
	            		 showMessage('success', 'Data Deleted successfully !');
	            		 location.replace('newTripSheetEntries.in');
	            	 }
	             },
	             error: function (e) {
	            	 showMessage('errors', 'Some error occured!')
	            	 hideLayer();
	             }
		});
	} 
}

function searchTripSheetShow(e){

	var code = (e.keyCode ? e.keyCode : e.which);
	if(code == 13) { //Enter keycode
		searchTripSheet();
	}

}
function searchTripSheet(){
	
	if($('#searchTripSheet').val() == null || $('#searchTripSheet').val().trim() == ''){
		showMessage('info','Please enter tripsheet number !');
		return false;
	}

	var jsonObject					= new Object();
	jsonObject["tripSheetNumber"] 	=  $('#searchTripSheet').val();
	jsonObject["companyId"] 		=  $('#companyId').val();
	jsonObject["userId"] 			=  $('#userId').val();
	
	showLayer();
	
	$.ajax({
             url: "searchTripSheetByNumber",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 $('#searchStatus').val('');
            	 $('#searchTripSheet').val('');
            	if(data.tripSheetId != undefined){
            		location.replace('showTripSheet.in?tripSheetID='+data.tripSheetId);
            	}else{
            		showMessage('info','No record found !');
            		hideLayer();
            	}
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});
}

function searchTripSheetStatus(e){
	var code = (e.keyCode ? e.keyCode : e.which);
	if(code == 13) { //Enter keycode
		searchTripSheetDiffStatus();
	}
}
function searchTripSheetDiffStatus(){
	
	if($('#searchStatus').val() == null || $('#searchStatus').val().trim() == ''){
		showMessage('info','Please enter search term !');
		return false;
	}

	var jsonObject					= new Object();
	jsonObject["search"] 			=  $('#searchStatus').val();
	jsonObject["companyId"] 		=  $('#companyId').val();
	jsonObject["userId"] 			=  $('#userId').val();
	jsonObject["loadTypeId"] 		=  $('#loadTypeId').val();
	jsonObject["status"] 			=  getTripSheetStatusToSearch(Number($('#loadTypeId').val()));
	
	showLayer();
	
	$.ajax({
             url: "searchTripSheetDiffStatus",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 $('#searchStatus').val('');
            	 $('#searchTripSheet').val('');
            	if(data.TripSheet != undefined){
            		if(data.loadTypeId == 1 || data.loadTypeId == 2){
            			setDispatchTripSheetTableData(data);
            		}else if(data.loadTypeId == 3 || data.loadTypeId == 4){
            			setManageTripSheetTableData(data);
            		}else if(data.loadTypeId == 5){
            			setTripSheetAccountList(data);
            		}else if(data.loadTypeId == 6){
            			setTripSheetAccounClosetList(data);
            		}
            		hideLayer();
            	}else{
            		$("#TripSheetTableBody tr").remove();
            		$("#tableHeader tr").remove();
            		$("#navigationBar").empty();
            		showMessage('info','No record found !');
            		hideLayer();
            	}
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});
}

function getTripSheetStatusToSearch(loadTypeId){
	if(loadTypeId == 1 || loadTypeId == 2){
		return 1;
	}else if(loadTypeId == 3 || loadTypeId == 4){
		return 2;
	}else if(loadTypeId == 5){
		return 3;
	}else if(loadTypeId == 6){
		return 4;
	}else{
		return 0;
	}
}
function searchVehCurTSShowEvt(e){
	var code = (e.keyCode ? e.keyCode : e.which);
	if(code == 13) { //Enter keycode
		searchVehCurTSShow();
	}
}
function searchVehCurTSShow(){
	var jsonObject			= new Object();
	jsonObject["vid"] 		=  $('#TripSelectVehicle_ID').val();
	jsonObject["companyId"] =  $('#companyId').val();
	jsonObject["userId"] 	=  $('#userId').val();
	
	if(Number($('#TripSelectVehicle_ID').val()) <= 0){
		showMessage('info','Please select vehicle !');
		return false;
	}
	
	submitVehicleSearch(jsonObject);
}

function submitVehicleSearch(jsonObject){
	console.log('submitVehicleSearch ...........');
	showLayer();
	$.ajax({
             url: "searchVehCurTSShow",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	setTripSheetTableData(data);
            	 hideLayer();
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});
}



$(document).ready(function() {
	 if($('#tripSheetId').val() != undefined && $('#tripSheetId').val() != null && Number($('#tripSheetId').val()) > 0){
		 
		 var jsonObject			= new Object();
		 jsonObject["companyId"] 	=  $('#companyId').val();
		 jsonObject["userId"] 		=  $('#userId').val();
		 jsonObject["tripsheetId"] 	=  $('#tripSheetId').val();
		 showLayer();
		 $.ajax({
			 url: "showTripSheetDetails",
			 type: "POST",
			 dataType: 'json',
			 data: jsonObject,
			 success: function (data) {
				 setTripSheetDetails(data);
				 hideLayer();
			 },
			 error: function (e) {
				 showMessage('errors', 'Some error occured!')
				 hideLayer();
			 }
		 });
	 }
	 $("#tallyCompanyId").select2({
	        minimumInputLength: 3,
	        minimumResultsForSearch: 10,
	        ajax: {
	            url: "getTallyCompanySearchList.in?Action=FuncionarioSelect2",
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
	                            text: a.companyName ,
	                            slug: a.slug,
	                            id: a.tallyCompanyId
	                        }
	                    })
	                }
	            }
	        }
	    });
});
function setTripSheetDetails(data){
	data = JSON.parse(JSON.stringify(data).replace(/\:null/gi, "\:\"\"")); 
	

	if(data.TripSheet != undefined){
		setTripSheetSideMenu(data.TripSheet.tripStutesId, data.configuration);
		setTripSheetTable(data);
		getPendingDueAmountData(data);
		setConfigurationParts(data);
		
		if(data.TripSheetAdvance != undefined && data.TripSheetAdvance.length > 0){
			setTripSheetAdvanceTable(data.TripSheetAdvance, data.configuration);
		}
			setTripSheetExpenseTable(data);
			setTripSheetROuteWiseWeight(data);
			setFastTagTollExpenses(data);
			setDriverPenalty(data);
			setTripsheetDueAmount(data);
			setFuelEntryData(data);
			setTripSheetUreaData(data);
			setTripSheetDriverHaltData(data);
			setTripSheetRefreshment(data);
			setTripSheetIncome(data);
			setTripAdvanceExpense(data);
			//setTripBalanceData(data);
			setTripSheetAccountDetails(data);
			setTripSheetUsageData(data);
			setTripSheetComment(data);
			if(data.driverWalleteBalance != undefined){
				$('#driverWalletBalanceSpan').text("Total Driver balance:  "+data.driverWalleteBalance);
				$('#driverWalletBalance').removeClass('hide');
			}
			if(data.lorryHireList != undefined && data.lorryHireList.length > 0){
				setLorrryHireDetails(data.lorryHireList);
			}
			
		
		$('#tripSheetDetails').show();
	}else{
		if($('#tripSheetId').val() != undefined)
		showMessage('errors','No TripSheet Found !');
	}
}
function setTripSheetTable(data){
	var tripSheet = data.TripSheet;
	var vehicle = data.vehicle
	var driverReminder = data.DriverReminder;
	tripsheetVehicleId = tripSheet.vid;
	$('#tripSheetDocumentId').val(tripSheet.tripSheetDocumentId);
	$('#tripSheetNumber').html(tripSheet.tripSheetNumber);
	$('#created').html(tripSheet.created);
	$('#vehicle_registration').html('<a target="_blank" href="showVehicle?vid='+tripSheet.vid+'">'+tripSheet.vehicle_registration+'</a>');
	$('#routeName').html(tripSheet.routeName);
	$('#allowTollApiIntegration').val(data.allowTollApiIntegration);
	$("#addLhpvAfterTripSheet").attr("href", "addLhpvDataToTripSheet.in?ID="+tripSheet.tripSheetID+"&vid="+tripSheet.vid+"");
	$('#createdBy').html(tripSheet.createdBy);
	$('#createdOn').html(tripSheet.created);
	$('#updatedBy').html(tripSheet.lastModifiedBy);
	$('#updatedOn').html(tripSheet.created);
	$('#tripTotalexpense').val(tripSheet.tripTotalexpense);
	$('#tvid').val(tripSheet.vid);
	
	
	$('#tripOpenKM').val(tripSheet.tripOpeningKM);
	if(data.vehicle != undefined)
	$('#vExpectedOdometer').val(data.vehicle.vehicle_ExpectedOdameter);
	$('#tripCloseKM').val(tripSheet.tripClosingKM);
	
	$("#allowIVLoadingSheetEntry").click(function(){ 
		addIVCargoLOadingSheetDetails(tripSheet.tripSheetID, tripSheet.vid, tripSheet.vehicle_registration, tripSheet.dispatchedOn, tripSheet.closetripDate); 
	});
	$("#tollApi").click(function(){ 
		addTollExpensesDetails(tripSheet.tripSheetID, tripSheet.vid, tripSheet.vehicle_registration, tripSheet.dispatchedOn, tripSheet.closedOn); 
	});
	$("#gpsApi").click(function(){ 
		updateGpsUsage(tripSheet.tripSheetID, tripSheet.vid, tripSheet.dispatchedOn, tripSheet.closedOn); 
	});
	$("#mantisIncome").click(function(){ 
		addMantisIncome(tripSheet.tripSheetID, tripSheet.vid, tripSheet.vehicle_registration, tripSheet.dispatchedOn, tripSheet.closedOn); 
	});
	$("#updateClosingKM").click(function(){ 
		updateTripClosingKM(tripSheet.vid, tripSheet.tripSheetID, tripSheet.tripClosingKM); 
	});
	
	$("#itsIncome").click(function(){ 
		addMantisIncome(tripSheet.tripSheetID, tripSheet.vid, tripSheet.vehicle_registration, tripSheet.dispatchedOn, tripSheet.closedOn); 
	});
	
	$("#bitlaIncome").click(function(){ 
		addMantisIncome(tripSheet.tripSheetID, tripSheet.vid, tripSheet.vehicle_registration, tripSheet.dispatchedOn, tripSheet.closedOn); 
	});
	
	
	$("#updateOPeningKM").click(function(){ 
		updateTripOpeningKM(tripSheet.vid, tripSheet.tripSheetID, tripSheet.tripOpeningKM, tripSheet.tripClosingKM); 
	});
	
	$("#editRoutePoint").click(function(){ 
		$('#driver1Point').val($('#driver1RoutePoint').text());
		$('#driver2Point').val($('#driver2RoutePoint').text());
		$('#cleanerPoint').val($('#cleanerRoutePoint').text());
		
		$('#editRoutePointModal').modal('show');
	});
	
	 var openDateRow = '<tr>'
		+'<td class="fit">Date of Journey : <a data-toggle="tip" data-original-title="Trip Open Date">'+tripSheet.tripOpenDate+' TO </a> <a data-toggle="tip" data-original-title="Trip Close Date">'+tripSheet.closetripDate+'</a> </td>'
		+'<td class="fit">Group Service : <a data-toggle="tip" data-original-title="Group Service">'+tripSheet.vehicle_Group+'</a> </td>'
		+'<td class="fit">Booking No : <a data-toggle="tip" data-original-title="Booking No">'+tripSheet.tripBookref+'</a> </td>'
		+'</tr>';
	//if(($("#showDriverFullNameConfig").val() == true) || ($("#showDriverFullNameConfig").val() == 'true')){
		 var routePointRow = '<tr>'
				+'<td class="fit">Trip Route Point : <a data-toggle="tip" data-original-title="Fixed Point">'+tripSheet.routeAttendancePoint+'</a></td>'
				+'<td class="fit">Trip Route Volume :<a data-toggle="tip" data-original-title="Volume Point">'+tripSheet.routeTotalLiter+'</a> </td>'
				if(tripSheet.tripFristDriverName != ""){
					routePointRow += '<td class="fit">Driver 1 : <a data-toggle="tip" data-original-title="Driver 1" id="driver1Name">'+tripSheet.tripFristDriverName+' '+tripSheet.tripFristDriverLastName+'-'+tripSheet.tripFristDriverFatherName+ '</a> / '+tripSheet.tripFristDriverMobile+'</td>'	
				}
				+'</tr>';
//	}else{
//		var routePointRow = '<tr>'
//			+'<td class="fit">Trip Route Point : <a data-toggle="tip" data-original-title="Fixed Point">'+tripSheet.routeAttendancePoint+'</a></td>'
//			+'<td class="fit">Trip Route Volume :<a data-toggle="tip" data-original-title="Volume Point">'+tripSheet.routeTotalLiter+'</a> </td>'
//			+'<td class="fit">Driver 1 : <a data-toggle="tip" data-original-title="Driver 1" id="driver1Name">'+tripSheet.tripFristDriverName+'</a> / '+tripSheet.tripFristDriverMobile+'</td>'
//			+'</tr>';
//	}
//	if(($("#showDriverFullNameConfig").val() == true) || ($("#showDriverFullNameConfig").val() == 'true')){
		var driver2Row = '<tr>'
			if(tripSheet.tripSecDriverName != ""){
				driver2Row +='<td class="fit">Driver 2 : <a data-toggle="tip" data-original-title="Driver 2 " id="driver2Name">'+tripSheet.tripSecDriverName+ ' '+tripSheet.tripSecDriverLastName+'-'+tripSheet.tripSecDriverFatherName+ '</a>/ '+tripSheet.tripSecDriverMobile+'</td>'
			}
			if(tripSheet.tripCleanerName != ""){
				driver2Row += '<td class="fit">Cleaner :<a data-toggle="tip" data-original-title="Cleaner" id="cleanerName">'+tripSheet.tripCleanerName+'  '+tripSheet.tripCleanerMidleName+'  '+tripSheet.tripCleanerLastName + '</a>/ '+tripSheet.tripCleanerMobile+' </td>'
			}
			+'<td class="fit"></td>'
			+'</tr>';
//	}else{
//		var driver2Row = '<tr>'
//			+'<td class="fit">Driver 2 : <a data-toggle="tip" data-original-title="Driver 2 " id="driver2Name">'+tripSheet.tripSecDriverName+'</a>/ '+tripSheet.tripSecDriverMobile+'</td>'
//			+'<td class="fit">Cleaner :<a data-toggle="tip" data-original-title="Cleaner" id="cleanerName">'+tripSheet.tripCleanerName+'</a>/ '+tripSheet.tripCleanerMobile+' </td>'
//			+'<td class="fit"></td>'
//			+'</tr>';
//	}
	 var pointRowOnlyDriver1 = '<tr>'
			+'<td class="fit">Driver 1 Route Point : <a data-toggle="tip" data-original-title="Driver 1 " id="driver1RoutePoint">'+tripSheet.tripFristDriverRoutePoint+'</a></td>'
			+'<td class="fit"></td>'
			+'<td class="fit"></td>'
			+'</tr>';
	 var pointRow = '<tr>'
			+'<td class="fit">Driver 1 Route Point : <a data-toggle="tip" data-original-title="Driver 1 " id="driver1RoutePoint">'+tripSheet.tripFristDriverRoutePoint+'</a></td>'
			+'<td class="fit">Driver 2 Route Point : <a data-toggle="tip" data-original-title="Driver 2" id="driver2RoutePoint">'+tripSheet.tripSecDriverRoutePoint+'</a></td>'
			+'<td class="fit">Cleaner Route Point : <a data-toggle="tip" data-original-title="Cleaner" id="cleanerRoutePoint">'+tripSheet.tripCleanerRoutePoint+'</a></td>'
			+'</tr>';
	 var kMRow = '<tr>'
			+'<td class="fit">Opening KM : <a data-toggle="tip" data-original-title="Opening KM">'+tripSheet.tripOpeningKM+'</a></td>'
			+'<td class="fit">Closing KM : <a data-toggle="tip" data-original-title="Closing KM">'+tripSheet.tripClosingKM+'</a></td>';
	 		if(tripSheet.tripClosingKMStatusId == 0){
	 			kMRow+= '<td class="fit">Usage KM: <a data-toggle="tip" data-original-title="Usage KM">'+tripSheet.tripUsageKM+'</a></td>'
	 		}else{
	 			kMRow+= '<td class="fit">Meter : <a data-toggle="tip" data-original-title="Usage KM">Meter Not Working</a></td>'
	 		}
			
	 		kMRow+= '</tr>';
	 var gpskMRow = '<tr>'
			+'<td class="fit">GPS Opening KM: <a data-toggle="tip" data-original-title="GPS Opening KM">'+tripSheet.tripGpsOpeningKM+'</a></td>'
			+'<td class="fit">GPS Closing KM: <a data-toggle="tip" data-original-title="GPS Closing KM">'+tripSheet.tripGpsClosingKM+'</a></td>'
			+'<td class="fit">GPS Usage KM: <a data-toggle="tip" data-original-title="GPS Usage KM">'+tripSheet.tripGpsUsageKM+'</a></td>'
			+'</tr>';
	 var gpskMRowOnlyGPSUsage = '<tr>'
		 +'<td class="fit">GPS Usage KM: <a data-toggle="tip" data-original-title="GPS Usage KM">'+tripSheet.tripGpsUsageKM+'</a></td>';
		 if(vehicle != undefined){
			 gpskMRowOnlyGPSUsage += '<td class="fit">Expected Mileage '+vehicle.vehicle_ExpectedMileage+' to '+vehicle.vehicle_ExpectedMileage_to+' </td>';
		 }else{
			 gpskMRowOnlyGPSUsage += '<td class="fit"></td>';
		 }
		 gpskMRowOnlyGPSUsage += '<td class="fit"></td>'
			+'</tr>';
	 var dispatchRow = '<tr>'
			+'<td class="fit">Dispatch By: <a data-toggle="tip" data-original-title="Dispatch By">'+tripSheet.dispatchedBy+'</a></td>'
			+'<td class="fit">Dispatch Location: <a data-toggle="tip" data-original-title="Dispatch Location">'+tripSheet.dispatchedLocation+'</a></td>'
			+'<td class="fit">Dispatch Time: <a data-toggle="tip" data-original-title="Dispatch Time">'+tripSheet.dispatchedByTime+'</a></td>'
			+'</tr>';
	 var closedRow = '<tr>'
			+'<td class="fit">Closed By: <a data-toggle="tip" data-original-title="Closed By">'+tripSheet.closedBy+'</a></td>'
			+'<td class="fit">Closed Location: <a data-toggle="tip" data-original-title="Closed Location">'+tripSheet.cloesdLocation+'</a></td>'
			+'<td class="fit">Closed Time: <a data-toggle="tip" data-original-title="Closed Time">'+tripSheet.closedByTime+'</a></td>'
			+'</tr>';
	 var routeRemarkRow = '<tr>'
			+'<td colspan="3" class="fit">Route Remark: <a data-toggle="tip" data-original-title="routeRemark">'+tripSheet.routeRemark+'</a></td>'
			+'</tr>';
	 
	 
	$('#tripSheetDetailsBody').append(openDateRow);
	$('#tripSheetDetailsBody').append(routePointRow);
	
	if(data.configuration.driver2){
		$('#tripSheetDetailsBody').append(driver2Row);
	}
	
	$('#tripSheetDetailsBody').append(kMRow);
	if(data.gpsConfig.allowGPSIntegration){
		if(tripSheet.tripGpsOpeningKM != null && tripSheet.tripGpsOpeningKM > 0 && tripSheet.tripGpsClosingKM != null ){
			$('#tripSheetDetailsBody').append(gpskMRow);
		} else {
			$('#tripSheetDetailsBody').append(gpskMRowOnlyGPSUsage);
		}
	}
	if(data.TripSheet.tripStutesId > 1){
		$('#tripSheetDetailsBody').append(dispatchRow);
	}
	if(data.TripSheet.tripStutesId > 2){
		$('#tripSheetDetailsBody').append(closedRow);
	}
	if(tripSheet.routeRemark != undefined && tripSheet.routeRemark.trim() != '')
		$('#tripSheetDetailsBody').append(routeRemarkRow);
	
	if(data.configuration.driver2){
		$('#tripSheetDetailsBody').append(pointRow);
	}else {
		$('#tripSheetDetailsBody').append(pointRowOnlyDriver1);
	}
	//driverValidation

	var dateFrom = tripSheet.tripOpenDate;
        var dateTo = tripSheet.closetripDate;
        
        var d1 = dateFrom.split("-");
        var d2 = dateTo.split("-");

        var from = new Date(d1[2], parseInt(d1[1])-1, d1[0]); // -1 because months are from 0 to 11
        var to = new Date(d2[2], parseInt(d2[1])-1, d2[0]);
        
        var newDate;
        var splitDate;
        var demoDate;
        
        var objectArray = new Array();
        
        if(driverReminder != null && driverReminder != undefined){
		for (let i = 0; i < driverReminder.length; i++) {
			      var obj = new Object();
			      newDate = driverReminder[i].driver_dlto_show;
			      splitDate = newDate.split("-");
		 	      demoDate = new Date(splitDate[2], parseInt(splitDate[1])-1, splitDate[0]);
			      obj.date = demoDate;
			      obj.type = driverReminder[i].driver_remindertype
			
			      objectArray.push(obj);
	     	}
     	
     	let messageShownOnTripPage = "";
     	 
	for(let i =0; i<objectArray.length; i++){
	            if(objectArray[i].date <from){
	                 messageShownOnTripPage += objectArray[i].type +" Expire Before Trip Journey ! ";
	            }
	            else if (objectArray[i].date >= from && objectArray[i].date <=to) {
	    		  messageShownOnTripPage += objectArray[i].type +" will Expire in between Trip Journey! ";
    		    }
	            else if(objectArray[i].date <=to){
	                  messageShownOnTripPage += objectArray[i].type +" will Expire in between Trip Journey! ";
	            }
		}
		    $("#DriverWarningMsg").html(messageShownOnTripPage);
	}
}

function setConfigurationParts(data){
	
	var subrouteRow = '<tr>'; 
		if(data.configuration.showSubroute){
			subrouteRow += '<td class="fit">Sub Route: <a data-toggle="tip" data-original-title="subRouteName">'+data.TripSheet.subRouteName+'</a></td>';
		}
		if(data.configuration.showLoadType){
			subrouteRow += '<td class="fit">Load Type: <a data-toggle="tip" data-original-title="Load Type">'+data.TripSheet.loadTypeName+'</a></td>';
		}
		/*if(data.configuration.allowTallyIntegration && data.TripSheet.voucherDateStr != '' ){
			$('#tripVoucherDate').val(data.TripSheet.voucherDateStr);
			subrouteRow += '<td class="fit">Voucher Date: <a data-toggle="tip" data-original-title="Voucher Date">'+data.TripSheet.voucherDateStr+'</a></td>';
		}*/
		subrouteRow += '</tr>';
		
		if(data.configuration.showPODdetails){
			var podRow = '<tr>'; 
			if(data.configuration.showSubroute){
				podRow += '<td class="fit">Number of POD <a data-toggle="tip" data-original-title="Number of POD">'+data.TripSheet.noOfPOD+'</a></td>';
			}
			if(data.configuration.showLoadType){
				podRow += '<td class="fit">Received POD: <a data-toggle="tip" data-original-title="Receive Number of POD">'+data.TripSheet.receivedPOD+'</a></td><td></td></tr>';
			}
			
			$('#tripSheetDetailsBody').append(podRow);
		}
		
		if(data.configuration.tripOpenCloseFuelRequired){
			 var fuelRow = '<tr>'
					+'<td class="fit">Last Fuel: <a data-toggle="tip" data-original-title="Last Fuel">'+data.TripSheet.tripStartDiesel+'</a></td>'
					+'<td class="fit">Purchased Fuel: <a data-toggle="tip" data-original-title="Purchased Fuel">'+data.fTDiesel+'</a></td>'
					+'<td class="fit">Total : <a data-toggle="tip" data-original-title="Total Fuel">'+(data.TripSheet.tripStartDiesel + data.fTDiesel)+'</a></td>'
					+'</tr>';
			 $('#tripSheetDetailsBody').append(fuelRow);
		}
		if(data.configuration.tripOpenCloseFuelRequired && data.TripSheet.tripEndDiesel != null){
			var usedFuel = (data.TripSheet.tripStartDiesel + data.fTDiesel) - data.TripSheet.tripEndDiesel;
			if(usedFuel < 0){
				usedFuel = 0;
			}
			 var fuelLastRow = '<tr>'
					+'<td class="fit">Balance Fuel: <a data-toggle="tip" data-original-title="Balance Fuel">'+data.TripSheet.tripEndDiesel+'</a></td>';
			 		if(usedFuel > 0){
			 			fuelLastRow += '<td class="fit">Used Fuel: <a data-toggle="tip" data-original-title="Used Fuel">'+usedFuel+'</a></td>'
			 			+'<td>Trip KMPL : <a data-toggle="tip" data-original-title="Trip KMPL">'+(data.TripSheet.tripUsageKM/usedFuel).toFixed(2)+'</a></td>';
			 		}else{
			 			fuelLastRow += '<td class="fit">Used Fuel: <a data-toggle="tip" data-original-title="Used Fuel">'+usedFuel+'</a></td>'
			 						+'<td>Trip KMPL : <a data-toggle="tip" data-original-title="Trip KMPL">0</a></td>';
			 		}
			 		fuelLastRow+= '</tr>';
			 $('#tripSheetDetailsBody').append(fuelLastRow);
		}
		$('#tripSheetDetailsBody').append(subrouteRow);
		if(data.configuration.allowTallyIntegration){
			 var tallyCompanyRow = '<tr>'
				 if(data.TripSheet.tallyCompanyId != null && data.TripSheet.tallyCompanyId > 0){
						$('#tripVoucherDate').val(data.TripSheet.voucherDateStr);
						tallyCompanyRow += '<td class="fit">Tally Company: <a data-toggle="tip" data-original-title="Tally Company">'+data.TripSheet.tallyCompanyName+'</a></td>';
					}else{
						tallyCompanyRow += '<td class="fit"></td>';
					}	
				 
					if(data.configuration.allowTallyIntegration && data.TripSheet.voucherDateStr != '' ){
						$('#tripVoucherDate').val(data.TripSheet.voucherDateStr);
						tallyCompanyRow += '<td class="fit">Voucher Date: <a data-toggle="tip" data-original-title="Voucher Date">'+data.TripSheet.voucherDateStr+'</a></td>';
					}else{
						tallyCompanyRow += '<td class="fit"></td>';
					}
					+'<td class="fit"></td>'
					+'</tr>';
			 $('#tripSheetDetailsBody').append(tallyCompanyRow);
			$("#tallyCompanyId").select2("data", {
				id : data.TripSheet.tallyCompanyId,
				text : data.TripSheet.tallyCompanyName
			});
		}
}

function setTripSheetSideMenu(tripStutesId, configuration){
	if(tripStutesId == 1){
		$('.statusTwo').hide();
		$('.statusThree').hide();
		$('.statusFour').hide();
		$('.statusOne').show();
		$('.ivCargoLSData').hide();
	}else if(tripStutesId == 2){
		$('.statusOne').hide();
		$('.statusThree').hide();
		$('.statusFour').hide();
		$('.statusTwo').show();
		$('.ivCargoLSData').hide();
	}else if(tripStutesId == TRIP_STATUS_CLOSED){
		$('.statusOne').hide();
		$('.statusTwo').hide();
		$('.statusFour').hide();
		$('.statusThree').show();
		$('.ivCargoLSData').show();
		if(configuration.hideAdvanceAndExpenseAddIfAccClose){
		$('#expenseLi').hide();	
		$('#advanceLi').hide();	
		}
	}else{
		$('.ivCargoLSData').hide();
		$('.statusOne').hide();
		$('.statusTwo').hide();
		$('.statusThree').hide();
		$('.statusFour').show();
	}
	
	if(configuration.deleteTripSheetAfterClose){
		$('#deleteTripSheet').show();
	}
}

function setTripSheetAdvanceTable(TripSheetAdvance, configuration){
	var srNo = 1;
	var totalAdvance = 0;
	var advanceTable = '<tr><td><table class="table table-bordered table-striped"><thead>'
						+'<tr class="breadcrumb">'
						   +'<th class="fit">No</th>'
						   +'<th class="fit ar">Advance Place</th>'
						   +'<th class="fit ar">Advance PaidBy</th>';
						if(configuration.showAdvanceDriver){
							advanceTable += '<th class="fit ar">Advance Paid To</th>';
						}
						if(configuration.exchangeTypeWithAmountRefWithAmount)
						{
							advanceTable +=    '<th class="fit ar">Amount</th>'
						   +'<th class="fit ar">Reference</th>'
						   +'<th class="fit ar">Advance Date</th></tr></thead><tbody>';
						}
						else
						{
		advanceTable +=    '<th class="fit ar">Reference</th>'
						   +'<th class="fit ar">Amount</th>'
						   +'<th class="fit ar">Advance Date</th></tr></thead><tbody>';
						   }
						for(var i = 0; i < TripSheetAdvance.length; i++ ){
							totalAdvance += TripSheetAdvance[i].advanceAmount;
						  advanceTable += '<tr data-object-id="" class="ng-scope">'	
							               +'<td class="fit">'+srNo+'</td>'
							               +'<td class="fit">'+TripSheetAdvance[i].advancePlace+'</td>'
							               +'<td class="fit">'+TripSheetAdvance[i].advancePaidby+'</td>';
										  if(configuration.showAdvanceDriver){
												advanceTable += '<td class="fit">'+TripSheetAdvance[i].driverName+'</td>';
										  }
							              if(configuration.exchangeTypeWithAmountRefWithAmount)
							              {
											  advanceTable +=       '<td class="fit">'+TripSheetAdvance[i].advanceAmount+'</td>'
							               +'<td class="fit">'+TripSheetAdvance[i].advanceRefence+'</td>'
							               +'<td class="fit">'+TripSheetAdvance[i].createdStr+'</td></tr>';
										  }
										  else
										  {
					 advanceTable +=       '<td class="fit">'+TripSheetAdvance[i].advanceRefence+'</td>'
							               +'<td class="fit">'+TripSheetAdvance[i].advanceAmount+'</td>'
							               +'<td class="fit">'+TripSheetAdvance[i].createdStr+'</td></tr>';
							               }
						  srNo ++;
						}
						if(configuration.roundFigureAmount){
							advanceTable += '<tr><td class="key" colspan="4"><b>Total Advance :</b> </td><td class="value" colspan="2"><b><i class="fa fa-inr"></i> '+ Math.round(totalAdvance)+'</b></td></tr></tbody></table></td></tr>';
						}else{
							advanceTable += '<tr><td class="key" colspan="4"><b>Total Advance :</b> </td><td class="value" colspan="2"><b><i class="fa fa-inr"></i> '+totalAdvance.toFixed(2)+'</b></td></tr></tbody></table></td></tr>'; 
						}
						
		$('#tripSheetSubDetailsBody').append(advanceTable);		
}
function setTripSheetExpenseTable(data){
	
	var configuration = data.configuration;
	
	var srNo = 1;
	var totalExpense = 0, totalPF = 0, totalESI = 0, totalBalance = 0;
	if(!configuration.showCombineTripDetails){
		
		if(data.TripSheetExpense != undefined && data.TripSheetExpense.length > 0){
			var TripSheetExpense = data.TripSheetExpense;

			
			var expenseTable = '<tr><td><table class="table table-bordered table-striped"><thead>'
				+'<tr class="breadcrumb">'
				   +'<th class="fit">No</th>'
				   +'<th class="fit ar">Expense Name</th>';
					if(configuration.showCreditAndVendorAtExpense){
						expenseTable += '<th class="fit ar">Vendor</th>';
					}else{
						if(configuration.showTypePlaceRefInIncomeExpense){
							if(configuration.exchangeTypeWithAmountRefWithAmount)
							{
								expenseTable +=     '<th class="fit ar">Amount</th>'
								   +'<th class="fit ar">Place</th>'
								   +'<th class="fit ar">Ref</th>';
							}
							else
							{
							expenseTable +=     '<th class="fit ar">Type</th>'
								   +'<th class="fit ar">Place</th>'
								   +'<th class="fit ar">Ref</th>';
							}
						}
					}
					if(configuration.exchangeTypeWithAmountRefWithAmount)
					{
						expenseTable +=     '<th class="fit ar">Expense Date</th>'
				                   +'<th class="fit ar">Type</th>';
					}
					else
					{
				expenseTable +=     '<th class="fit ar">Expense Date</th>'
				                   +'<th class="fit ar">Amount</th>';
					}
				
				if(configuration.showPFAmount){
					expenseTable +=     '<th class="fit ar">PF</th>'
										+'<th class="fit ar">ESI</th>'
										+'<th class="fit ar">Balance</th>';
				}
				if(($("#downloadTripExpenseDocument").val() == true || $("#downloadTripExpenseDocument").val() == 'true')){
					expenseTable +=   '<th class="fit ar" style="width: 10%;" >Doc</th>'
				}
				expenseTable += '</tr></thead><tbody>';
				for(var i = 0; i < TripSheetExpense.length; i++ ){
					totalExpense += TripSheetExpense[i].expenseAmount;
					totalPF		 += TripSheetExpense[i].pfAmount;
					totalESI	 += TripSheetExpense[i].esiAmount;
					totalBalance += TripSheetExpense[i].balanceAmount;
					
					expenseTable += '<tr data-object-id="" class="ng-scope">'	
					               +'<td class="fit">'+srNo+'</td>'
					               +'<td class="fit">'+TripSheetExpense[i].expenseName+'</td>';
								  if(configuration.showCreditAndVendorAtExpense){
									  expenseTable += '<td class="fit">'+TripSheetExpense[i].vendorName+'</td>';
								  }else{
									  
									  
									  if(configuration.showTypePlaceRefInIncomeExpense){
										  if(configuration.exchangeTypeWithAmountRefWithAmount)
										  {
											  expenseTable +='<td style="text-align: right;" class="fit">'+(TripSheetExpense[i].expenseAmount).toFixed(2)+'</td>';
											  
											   expenseTable += '<td class="fit">'+TripSheetExpense[i].expensePlace+'</td>'
								 			
								 			  	             +'<td class="fit">'+TripSheetExpense[i].expenseRefence+'</td>'; 
										  }
										  else
										  {
											  if(TripSheetExpense[i].expenseFixed == 'FIXED'){
												  expenseTable += '<td class="fit"><small class="label label-success">'+TripSheetExpense[i].expenseFixed+'</small></td>';
											    }else{
											      expenseTable += '<td class="fit"><small class="label label-warning">'+TripSheetExpense[i].expenseFixed+'</small></td>';
											  }
										  
											  expenseTable += '<td class="fit">'+TripSheetExpense[i].expensePlace+'</td>'
								 			
								 			  	             +'<td class="fit">'+TripSheetExpense[i].expenseRefence+'</td>'; 
							 			  }
									 }
									  
								  }
					              if(configuration.exchangeTypeWithAmountRefWithAmount)
					              {
									  expenseTable +=       '<td class="fit">'+TripSheetExpense[i].createdStr+'</td>'
									  
					                     if(TripSheetExpense[i].expenseFixed == 'FIXED'){
											  expenseTable += '<td class="fit"><small class="label label-success">'+TripSheetExpense[i].expenseFixed+'</small></td>';
										    }else{
										      expenseTable += '<td class="fit"><small class="label label-warning">'+TripSheetExpense[i].expenseFixed+'</small></td>';
										  }
								  }
								  else
								  {
								  
			  expenseTable +=       '<td class="fit">'+TripSheetExpense[i].createdStr+'</td>'
					               +'<td style="text-align: right;" class="fit">'+(TripSheetExpense[i].expenseAmount).toFixed(2)+'</td>';
					              }
					               
			  if(configuration.showPFAmount){
					expenseTable +=     '<td style="text-align: right;" class="fit ar">'+(TripSheetExpense[i].pfAmount).toFixed(2)+'</td>'
										+'<td style="text-align: right;" class="fit ar">'+(TripSheetExpense[i].esiAmount).toFixed(2)+'</td>'
										+'<td style="text-align: right;" class="fit ar">'+(TripSheetExpense[i].balanceAmount).toFixed(2)+'</td>';
				}
			  if(($("#downloadTripExpenseDocument").val() == true || $("#downloadTripExpenseDocument").val() == 'true') && (TripSheetExpense[i].tripSheetExpense_document_id != undefined && TripSheetExpense[i].tripSheetExpense_document_id != "" )){
				  expenseTable +=  '<td class="fit" ><a href="download/TripsheetExpenseDocument/'+TripSheetExpense[i].tripSheetExpense_document_id+'"> <span class="fa fa-download"></span></a></td>';
			  }
			  expenseTable += '</tr>';
				  srNo ++;
				}
				if(configuration.showCreditAndVendorAtExpense){
					expenseTable += '<tr><td class="key" colspan="4"><b>Total Expense :</b> </td><td class="value" style="width : 15%;text-align: right;"><b><i class="fa fa-inr"></i> '+totalExpense.toFixed(2)+'</b></td>';
					if(configuration.showPFAmount){
						expenseTable += '<td class="value" style="width : 10%;text-align: right;"><b><i class="fa fa-inr"></i> '+totalPF.toFixed(2)+'</b></td><td style="width : 10%;text-align: right;" class="value"><b><i class="fa fa-inr"></i> '+totalESI.toFixed(2)+'</b></td><td style="width : 12%;text-align: right;" class="value"><b><i class="fa fa-inr"></i> '+totalBalance.toFixed(2)+'</b></td>';
					}
					expenseTable +=	'</tr></tbody></table></td></tr>';
				}else{
					if(configuration.showTypePlaceRefInIncomeExpense ){
						if(configuration.roundFigureAmount){
							expenseTable += '<tr><td class="key" colspan="5"><b>Total Expense :</b> </td><td class="value" colspan="2" style="width : 15%;text-align: right;"><b><i class="fa fa-inr"></i> '+Math.round(totalExpense)+'</b></td>';
							if(configuration.showPFAmount){
								expenseTable += '<td class="value" style="width : 10%;text-align: right;"><b><i class="fa fa-inr"></i> '+totalPF.toFixed(2)+'</b></td><td style="width : 10%;text-align: right;" class="value"><b><i class="fa fa-inr"></i> '+totalESI.toFixed(2)+'</b></td><td style="width : 12%;text-align: right;" class="value"><b><i class="fa fa-inr"></i> '+totalBalance.toFixed(2)+'</b></td>';
								}
							expenseTable +=	'</tr></tbody></table></td></tr>';
						}else{
							expenseTable += '<tr><td class="key" colspan="5"><b>Total Expense :</b> </td><td class="value" colspan="2" style="width : 15%;text-align: right;"><b><i class="fa fa-inr"></i> '+totalExpense.toFixed(2)+'</b></td>'; 
							if(configuration.showPFAmount){
								expenseTable += '<td class="value" style="width : 10%;text-align: right;"><b><i class="fa fa-inr"></i> '+totalPF.toFixed(2)+'</b></td><td style="width : 10%;text-align: right;" class="value"><b><i class="fa fa-inr"></i> '+totalESI.toFixed(2)+'</b></td><td style="width : 12%;text-align: right;" class="value"><b><i class="fa fa-inr"></i> '+totalBalance.toFixed(2)+'</b></td>';
								}
							expenseTable +=	'</tr></tbody></table></td></tr>';
						}
					} else {
						expenseTable += '<tr><td class="key" colspan="2"><b>Total Expense :</b> </td><td class="value" colspan="2" style="width : 15%;text-align: right;"><b><i class="fa fa-inr"></i> '+totalExpense.toFixed(2)+'</b></td>'; 
						if(configuration.showPFAmount){
							expenseTable += '<td class="value" style="width : 10%;text-align: right;"><b><i class="fa fa-inr"></i> '+totalPF.toFixed(2)+'</b></td><td style="width : 10%;text-align: right;" class="value"><b><i class="fa fa-inr"></i> '+totalESI.toFixed(2)+'</b></td><td style="width : 12%;text-align: right;" class="value"><b><i class="fa fa-inr"></i> '+totalBalance.toFixed(2)+'</b></td>';
							}
						expenseTable +=	'</tr></tbody></table></td></tr>';
					}
				}
		}
		
	}else{
		if(data.ExpenseCombineList != undefined && data.ExpenseCombineList.length > 0){
			var TripSheetExpense = data.ExpenseCombineList;
			var expenseTable = '<tr><td><table class="table table-bordered table-striped"><thead>'
				+'<tr class="breadcrumb">'
				   +'<th class="fit">No</th>'
				   +'<th class="fit ar">Expense Name</th>'
				    +'<th class="fit ar">Amount</th></tr></thead><tbody>';
				for(var i = 0; i < TripSheetExpense.length; i++ ){
					totalExpense += TripSheetExpense[i].expenseAmount;
					expenseTable += '<tr data-object-id="" class="ng-scope">'	
					               +'<td class="fit">'+srNo+'</td>'
					               +'<td class="fit">'+TripSheetExpense[i].expenseName+'</td>'
					               +'<td class="fit"><a href="#" onclick="expensePopUp('+data.tripsheetId+','+TripSheetExpense[i].expenseId+')";>'+(TripSheetExpense[i].expenseAmount).toFixed(2)+'</a></td></tr>';
				  srNo ++;
				}
					expenseTable += '<tr><td class="key" colspan="2"><b>Total Expense :</b> </td><td class="value" ><b><i class="fa fa-inr"></i> '+(totalExpense).toFixed(2)+'</b></td></tr></tbody></table></td></tr>';
		 }
		}
	$('#tripSheetSubDetailsBody').append(expenseTable);		
}
function setFastTagTollExpenses(data){
	if(data.TripSheetTollExpenseTotalAmount != undefined && data.TripSheetTollExpenseTotalAmount > 0){
		var expenseTable = '<tr><td><table class="table table-bordered table-striped"><thead>'
			+'<tr class="breadcrumb">'
			   +'<th class="fit">No</th>'
			   +'<th class="fit ar">Expense Name</th>'
			    +'<th class="fit ar">Amount</th></tr></thead><tbody>';
				expenseTable += '<tr data-object-id="" class="ng-scope">'	
				               +'<td class="fit">1</td>'
				               +'<td class="fit"><a href="#" onclick="PopUp('+data.tripsheetId+')";>'+data.TripSheetTollExpenseName+'</a></td>'
				               +'<td class="fit"><a href="#" onclick="PopUp('+data.tripsheetId+')";>'+data.TripSheetTollExpenseTotalAmount+'</a></td></tr>';

	}
							
		$('#tripSheetSubDetailsBody').append(expenseTable);		
}
function setTripSheetIncome(data){
	if(data.TripSheetIncome != undefined && data.TripSheetIncome.length > 0){
		var TripSheetIncome 	= data.TripSheetIncome;
		var configuration		= data.configuration;
		var wayBillTypeWiseHM 	= data.wayBillTypeWiseHM;
		var withBillTotal       = 0;
		var withOutBillTotal    = 0;
		var srNo = 1, totalIncome = 0;
		var B_Income   = false;
		var  incomeTable = '<tr><td><table class="table table-bordered table-striped"><thead>'
				+'<tr class="breadcrumb">'
				   +'<th class="fit">No</th>'
				   +'<th class="fit ar" style="width:13%">Income Name</th>'
				   
				   if(configuration.showWithOrWithoutBill){
					   incomeTable +="<th style='width:12%'>Bill Type</th>"
				   }
				   
				    incomeTable +='<th class="fit ar">LS CreatedDate</th>'
				   
				   if(configuration.showLsDestination){
					   incomeTable +='<th class="fit ar" style ="width:5%">LsDestinationBranch</th>'
					  /*  +'<th class="fit ar">LS Details</th>*/
				   }
	
				   incomeTable +='<th class="fit ar" style="width:15%;">Ref</th>';
				  if(!configuration.showToPayPaidTotalOfLS){
					  incomeTable +='<th class="fit ar">Type</th>'
						           +'<th class="fit ar"  style="width:10%" >Place</th>';
				  }else{
					  incomeTable +='<th class="fit ar" style ="width:5%">ToPay</th>'
				           			+'<th class="fit ar" style ="width:5%">Paid</th>';
				  }
				  incomeTable +='<th class="fit ar" style ="width:7%"> Amount </th>'
					  			+'<th class="fit ar" style ="width:10%"> Income Date </th>';
				  
				  if(configuration.showDriverExcludeAtIncome){
					  incomeTable +='<th class="fit ar">Driver Exclude</th>'; 
				  }
				  incomeTable+= '</tr></thead><tbody>';
				  
			for(var i = 0; i < TripSheetIncome.length; i++ ){
				
				 var nextObject = i < TripSheetIncome.length - 1 ? TripSheetIncome[i + 1] : null;
				
				totalIncome  += TripSheetIncome[i].incomeAmount;
				
				if(TripSheetIncome[i].billSelectionId == 1){
					withBillTotal += TripSheetIncome[i].incomeAmount;
					lastBillSelectionId1Index = i;
				}
				if(TripSheetIncome[i].billSelectionId == 2){
					withOutBillTotal += TripSheetIncome[i].incomeAmount;
				}
				incomeTable += '<tr data-object-id="" class="ng-scope">'	
				               +'<td class="fit">'+srNo+'</td>'
				               +'<td class="fit">'+TripSheetIncome[i].incomeName+'</td>'
				               
				                if(configuration.showWithOrWithoutBill){
									incomeTable+='<th class="fit ar">'+ TripSheetIncome[i].billType +'</th>'
								}
				               
				                incomeTable +='<td class="fit">'+TripSheetIncome[i].tripDateTime+'</td>'
				               
				               	if(configuration.showLsDestination){
								  incomeTable +='<th class="fit ar">'+TripSheetIncome[i].lsDestinationBranch+'</th>'
										  /*  +'<th class="fit ar">LS Details</th>*/
								}
				 			   incomeTable +='<td class="fit">'+TripSheetIncome[i].incomeRefence+'</td>';
							if(!configuration.showToPayPaidTotalOfLS){
								if(TripSheetIncome[i].incomeFixed == 'FIXED'){
									incomeTable += '<td class="fit" style="width:15%"><small class="label label-success">'+TripSheetIncome[i].incomeFixed+'</small></td>';
								}else{
									incomeTable += '<td class="fit" style="width:15%"><small class="label label-warning">'+TripSheetIncome[i].incomeFixed+'</small></td>';
								}
								incomeTable += '<td class="fit">'+TripSheetIncome[i].incomePlace+'</td>';
							}else{
								incomeTable += '<td class="fit" style="width:15%">'+getWayBillTypeWiseData(2, TripSheetIncome[i].dispatchLedgerId, wayBillTypeWiseHM)+'</td>'
											  +'<td class="fit" style="width:15%">'+getWayBillTypeWiseData(1, TripSheetIncome[i].dispatchLedgerId, wayBillTypeWiseHM)+'</td>';
							}
							  
							    if(TripSheetIncome[i].dispatchLedgerId != null && TripSheetIncome[i].dispatchLedgerId > 0){
							    	incomeTable += '<td class="fit" style="width:15%"><a href="#" onclick="getLoadingSheetIncomeDetails('+TripSheetIncome[i].dispatchLedgerId+', '+TripSheetIncome[i].tripSheetId+');">'+(TripSheetIncome[i].incomeAmount).toFixed(2)+'</td>';
							    }else{
							    	incomeTable += '<td class="fit" style="width:15%"><a href="#" onclick="TcktIncmApi('+TripSheetIncome[i].ticketIncomeApiId+');">'+(TripSheetIncome[i].incomeAmount).toFixed(2)+'</td>';
							    }
							    incomeTable += '<td class="fit" style="width:15%">'+TripSheetIncome[i].createdStr+'</td>';
							    if(configuration.showDriverExcludeAtIncome){
									  incomeTable +='<td class="fit ar">'+TripSheetIncome[i].driverExcludedStr+'</td>'; 
								 }
							    incomeTable += '</tr>';
					
					
					if(configuration.showWithOrWithoutBill){		    
						if(nextObject){	
							 if (nextObject.billSelectionId ==2 && B_Income == false && TripSheetIncome[i].billSelectionId == 1) {
								incomeTable += '<tr><td class="key" colspan="7"><b>B Income Total :</b> </td><td class="value" style="margin-right:3%;text-align:center;" colspan="3"><b><i class="fa fa-inr"></i> '+Math.round(withBillTotal)+'</b></td></tr>';
								B_Income = true;
							 }			
						 }
						 if (i === TripSheetIncome.length - 1 && TripSheetIncome[i].billSelectionId == 1) {
							 incomeTable += '<tr><td class="key" colspan="7"><b>B Income Total :</b> </td><td class="value" style="margin-right:3%;text-align:center;" colspan="3"><b><i class="fa fa-inr"></i> '+Math.round(withBillTotal)+'</b></td></tr>';
						 }
						 if (i === TripSheetIncome.length - 1 && TripSheetIncome[i].billSelectionId == 2) {
							 incomeTable += '<tr><td class="key" colspan="7"><b>E Income Total :</b> </td><td class="value" style="margin-right:3%;text-align:center;" colspan="3"><b><i class="fa fa-inr"></i> '+Math.round(withOutBillTotal)+'</b></td></tr>';
						 }
					 }
					 
			  srNo ++;
			  if(TripSheetIncome[i].incomeFixed == '--'){
					$('#incomeFixed_39768').addClass('label label-success');
			  }else{
				  $('#incomeFixed_39768').addClass('label label-success');
			  }
			}
			if(configuration.roundFigureAmount){
				incomeTable += '<tr><td class="key" colspan="5"><b>Total Income :</b> </td><td class="value" colspan="2"><b><i class="fa fa-inr"></i> '+Math.round(totalIncome)+'</b></td></tr></tbody></table></td></tr>';
			}else{
				incomeTable += '<tr><td class="key" colspan="5"><b>Total Income :</b> </td><td class="value" colspan="2"><b><i class="fa fa-inr"></i> '+(totalIncome).toFixed(2)+'</b></td></tr></tbody></table></td></tr>'; 
			}
			
			$('#tripsheetTotalIncome').val(totalIncome);	// just to check trip has income or not (while closing tripAccount)	
			$('#tripSheetSubDetailsBody').append(incomeTable);		
	}
}
function setDriverPenalty(data){

	if(data.DriverAdvanvce != undefined && data.DriverAdvanvce.length > 0){
		var DriverAdvanvce = data.DriverAdvanvce;
		var srNo = 1, totalPenalty = 0;
		var  incomeTable = '<tr><td><table class="table table-bordered table-striped"><thead>'
				+'<tr class="breadcrumb">'
				   +'<th class="fit">No</th>'
				   +'<th class="fit ar">Driver Name</th>'
				   +'<th class="fit ar">Penalty Date</th>'
				   +'<th class="fit ar">Penatly Amount</th>'
				   +'<th class="fit ar">Paid By</th>'
			for(var i = 0; i < DriverAdvanvce.length; i++ ){
				totalPenalty  += DriverAdvanvce[i].advance_AMOUNT;
				incomeTable += '<tr data-object-id="" class="ng-scope">'	
				               +'<td class="fit">'+srNo+'</td>'
				               +'<td class="fit">'+DriverAdvanvce[i].driver_empnumber+'-'+DriverAdvanvce[i].driver_firstname+' '+DriverAdvanvce[i].driver_Lastname +' - '+DriverAdvanvce[i].driverFatherName+'</td>'
							   +'<td class="fit">'+DriverAdvanvce[i].advance_DATE+'</td>'
					 		   +'<td class="fit">'+DriverAdvanvce[i].advance_AMOUNT+'</td>'
							   +'<td class="fit">'+DriverAdvanvce[i].advance_PAID_BY+'</td></tr>';
			  srNo ++;
			}
			incomeTable += '<tr><td class="key" colspan="3"><b>Total Penalty :</b> </td><td class="value" colspan="2"><b><i class="fa fa-inr"></i> '+totalPenalty.toFixed(2)+'</b></td></tr></tbody></table></td></tr>'; 
			
			$('#tripSheetSubDetailsBody').append(incomeTable);		
	}

}
function setTripsheetDueAmount(data){

	if(data.configuration.showTripsheetDueAmount && data.TripsheetDueAmount != undefined && data.TripsheetDueAmount.length > 0){
		var TripsheetDueAmount = data.TripsheetDueAmount;
		var srNo = 1;
		var  incomeTable = '<tr><td><table class="table table-bordered table-striped"><thead>'
				+'<tr class="breadcrumb">'
				   +'<th class="fit">No</th>'
				   +'<th class="fit ar">Name</th>'
				   +'<th class="fit ar">Job Type</th>'
				   +'<th class="fit ar">Approx Date</th>'
				   +'<th class="fit ar">Due Date</th>';
				   
				 if(data.configuration.showDueIncomeType){ 
				 	incomeTable  += '<th class="fit ar" >Due Income Type</th>';
				 }
				    incomeTable  += '<th class="fit ar">Due Amount</th>'
				   				   +'<th class="fit ar">Balance Amount</th></tr></thead><tbody>';
				   				
			for(var i = 0; i < TripsheetDueAmount.length; i++ ){
				incomeTable += '<tr data-object-id="" class="ng-scope">'	
				               +'<td class="fit">'+srNo+'</td>'
				               +'<td class="fit">'+TripsheetDueAmount[i].driver_firstname+'-'+TripsheetDueAmount[i].driver_Lastname+'</td>'
							   +'<td class="fit">'+TripsheetDueAmount[i].driJobType+'</td>'
					 		   +'<td class="fit">'+TripsheetDueAmount[i].approximateDateStr+'</td>'
							   +'<td class="fit">'+TripsheetDueAmount[i].dueDateStr+'</td>';
							   
							   if(data.configuration.showDueIncomeType){
							   	    incomeTable += '<td class="fit">'+TripsheetDueAmount[i].billType+'</td>';
							   }
							   
				               incomeTable +='<td class="fit">'+TripsheetDueAmount[i].dueAmount+'</td>'
							   				+'<td class="fit">'+(TripsheetDueAmount[i].balanceAmount).toFixed(2)+'</td></tr>';
			  				   srNo ++;
			}
			incomeTable += '<tr><td class="key" colspan="5"><b>Total Income :</b> </td><td class="value" colspan="3"><b><i class="fa fa-inr"></i> '+data.TotalDueAmount.toFixed(2)+'</b></td></tr></tbody></table></td></tr>'; 
			
			$('#tripSheetSubDetailsBody').append(incomeTable);		
	}

}
function setFuelEntryData(data){
	if(data.fuel != undefined && data.fuel.length > 0){
		var fuel = data.fuel;
		var vehicle = data.vehicle;
		
		
		if($("#showDiffAvgCost").val() == true || $("#showDiffAvgCost").val() == 'true'){
			var  incomeTable = '<tr><td><table class="table table-bordered table-striped"><thead>'
				+'<tr class="breadcrumb">'
				   +'<th class="fit">ID</th>'
				   +'<th class="fit ar">Vehicle</th>'
				   +'<th class="fit ar">Date</th>'
				   +'<th class="fit ar">Close(Km)</th>'
				   +'<th class="fit ar">Open(Km)</th>'
				   +'<th class="fit ar">Usage</th>'
				   +'<th class="fit ar">Volume</th>'
				   +'<th class="fit ar">Amount</th>'
				   +'<th class="fit ar">FE</th>'
				   +'<th class="fit ar">Cost</th>'
				   +'<th class="fit ar">Diff Avg Cost</th></tr></thead><tbody>';
		}else{
			var  incomeTable = '<tr><td><table class="table table-bordered table-striped"><thead>'
				+'<tr class="breadcrumb">'
				   +'<th class="fit">ID</th>'
				   +'<th class="fit ar">Vehicle</th>'
				   +'<th class="fit ar">Date</th>'
				   +'<th class="fit ar">Close(Km)</th>'
				   +'<th class="fit ar">Open(Km)</th>'
				   +'<th class="fit ar">Usage</th>'
				   +'<th class="fit ar">Volume</th>'
				   +'<th class="fit ar">Amount</th>'
				   +'<th class="fit ar">FE</th>'
				   +'<th class="fit ar">Cost</th></tr></thead><tbody>';
		}
		var diffAvgCost = 0;
		var kmSign = '';
			for(var i = 0; i < fuel.length; i++ ){
				if(vehicle.vehicle_ExpectedMileage <= fuel[i].fuel_kml){
					if(vehicle.vehicle_ExpectedMileage_to >= fuel[i].fuel_kml){
						kmSign ='<i class="fa fa-stop-circle" style="color: #1FB725; font-size: 19px;"></i>';
					}else{
						kmSign ='<i class="fa fa-chevron-circle-up" style="color:blue; font-size: 19px;"></i>';
					}
				}else{
					kmSign = '<i class="fa fa-chevron-circle-down" style="color: red; font-size: 19px;"></i>';
				}
				incomeTable += '<tr data-object-id="" class="ng-scope">'	
				               +'<td class="fit"><a data-toggle="tip" data-original-title="Click Fuel details" href="showFuel.in?FID='+fuel[i].fuel_id+'" target="_blank">F-'+fuel[i].fuel_Number+'</a></td>'
							   +'<td class="fit"><a data-toggle="tip" data-original-title="Click vehicle details" href="VehicleFuelDetails/1.in?vid='+fuel[i].vid+'" target="_blank">'+fuel[i].vehicle_registration+'</a></td>'
					 		   +'<td class="fit">'+fuel[i].fuel_date+'<br><a data-toggle="tip" data-original-title="Vendor Name">'+fuel[i].vendor_name+'-('+fuel[i].vendor_location+')</a></td>'
							   +'<td class="fit">'+fuel[i].fuel_meter+'</td>'
							   +'<td class="fit">'+fuel[i].fuel_meter_old+'</td>'
				               +'<td class="fit">'+fuel[i].fuel_usage+' KM</td>';
							if(fuel[i].fuel_tank_partial != 1){
								incomeTable += '<td class="fit"><abbr data-toggle="tip" data-original-title="Liters"></abbr> '+fuel[i].fuel_liters+'<br>'+fuel[i].fuel_type+'</td>';
							}else{
								incomeTable += '<td class="fit"><abbr data-toggle="tip" data-original-title="Partial fuel-up"><i class="fa fa-adjust"></i></abbr> '+fuel[i].fuel_liters+'<br>'+fuel[i].fuel_type+'</td>';
							}
						incomeTable +='<td class="fit"><i class="fa fa-inr"></i> '+fuel[i].fuel_amount+'<br>'+fuel[i].fuel_price+' /liters</td>'
							   +'<td class="fit"><abbr data-toggle="tip" data-original-title="Expected Mileage '+vehicle.vehicle_ExpectedMileage+' to '+vehicle.vehicle_ExpectedMileage_to+'">'+fuel[i].fuel_kml+'<abbr data-toggle="tip" data-original-title="Partial fuel-up"> '+kmSign+'</abbr></td>'
							   +'<td class="fit">'+fuel[i].fuel_cost+' /KM</td>'
						if($("#showDiffAvgCost").val() == true || $("#showDiffAvgCost").val() == 'true'){
							incomeTable +='<td class="fit">'+(fuel[i].fuelPriceDiff).toFixed(2)+' </td></tr>';
							diffAvgCost += Number(fuel[i].fuelPriceDiff);
						}
			}
			var fuelKmpl = 0.0, costPerKM = 0.0;
			if(data.fTUsage > 0){
				fuelKmpl = data.fTUsage/data.fTDiesel;
			}
			if(data.fTUsage > 0){
				costPerKM = data.fTAmount/data.fTUsage;
				diffAvgCost = diffAvgCost/fuel.length;
			}
		if($("#showDiffAvgCost").val() == true || $("#showDiffAvgCost").val() == 'true'){
			
			incomeTable += '<tr><td class="key" colspan="5"><b>Total :</b></td><td><b>'+data.fTUsage.toFixed(2)+'</b></td><td><b>'+data.fTDiesel.toFixed(2)+'</b></td><td><b>'+data.fTAmount.toFixed(2)+'</b></td><td><b>'+fuelKmpl.toFixed(2)+'</b></td><td><b>'+costPerKM.toFixed(2)+'</b></td><td><b>'+diffAvgCost.toFixed(2)+'</b></td></tr></tbody></table></td></tr>';
		}else{
			incomeTable += '<tr><td class="key" colspan="5"><b>Total :</b></td><td><b>'+data.fTUsage.toFixed(2)+'</b></td><td><b>'+data.fTDiesel.toFixed(2)+'</b></td><td><b>'+data.fTAmount.toFixed(2)+'</b></td><td><b>'+fuelKmpl.toFixed(2)+'</b></td><td><b>'+costPerKM.toFixed(2)+'</b></td></tr></tbody></table></td></tr>';
		}
			
			$('#tripSheetSubDetailsBody').append(incomeTable);		
	}
}
function setTripSheetUreaData(data){

	if(data.urea != undefined && data.urea.length > 0){
		var urea = data.urea;
		var totalAmount = 0, totalVolume = 0;
		var  incomeTable = '<tr><td><table class="table table-bordered table-striped"><thead>'
				+'<tr class="breadcrumb">'
				   +'<th class="fit">No</th>'
				   +'<th class="fit ar">Vehicle</th>'
				   +'<th class="fit ar">Urea Manufacturer</th>'
				   +'<th class="fit ar">Date</th>'
				   +'<th class="fit ar">Close(Km)</th>'
				   +'<th class="fit ar">Volume</th>'
				   +'<th class="fit ar">Amount</th></tr></thead><tbody>';
			for(var i = 0; i < urea.length; i++ ){
				totalVolume  += urea[i].ureaLiters;
				totalAmount  += urea[i].ureaAmount;
				incomeTable += '<tr data-object-id="" class="ng-scope">'	
				               +'<td class="fit"><a target="blank" href="showUreaDetails.in?Id='+urea[i].ureaEntriesId+'">U-'+urea[i].ureaEntriesNumber+'</a></td>'
				               +'<td class="fit"><a target="blank" href="showVehicle.in?vid='+urea[i].vid+'">'+urea[i].vehicle_registration+'</a></td>'
							   +'<td class="fit">'+urea[i].manufacturerName+'</td>'
					 		   +'<td class="fit">'+urea[i].ureaDateStr+'</td>'
							   +'<td class="fit">'+urea[i].ureaOdometer+'</td>'
				               +'<td class="fit">'+urea[i].ureaLiters+'</td>'
							   +'<td class="fit"><i class="fa fa-inr"></i> '+urea[i].ureaAmount+'<br>'+urea[i].ureaRate+' /liters</td></tr>';
			}
			incomeTable += '<tr><td class="key" colspan="5"><b>Total :</b> </td><td ><b>'+totalVolume+'</b></td><td><b>'+totalAmount+'</b></td></tr></tbody></table></td></tr>'; 
			
			$('#tripSheetSubDetailsBody').append(incomeTable);		
	}
}
function setTripSheetDriverHaltData(data){
	if(data.TripSheetHalt != undefined && data.TripSheetHalt.length > 0){
		var TripSheetHalt = data.TripSheetHalt;
		var totalAmount = 0, totalVolume = 0, srNo = 1;
		var  incomeTable = '<tr><td><table class="table table-bordered table-striped"><thead>'
				+'<tr class="breadcrumb">'
				   +'<th class="fit">No</th>'
				   +'<th class="fit ar">Name</th>'
				   +'<th colspan="2" class="fit ar">Halt date</th>'
				   +'<th class="fit ar">Amount</th>'
				   +'<th class="fit ar">Place</th>'
				   +'<th class="fit ar">Paid By</th></tr></thead><tbody>';
			for(var i = 0; i < TripSheetHalt.length; i++ ){
				totalAmount  += TripSheetHalt[i].halt_AMOUNT;
				incomeTable += '<tr data-object-id="" class="ng-scope">'	
				               +'<td class="fit">'+srNo+'</td>'
				               +'<td class="fit">'+TripSheetHalt[i].driver_NAME+'</td>'
							   +'<td colspan="2" class="fit">'+TripSheetHalt[i].halt_DATE_FROM+ ' to '+TripSheetHalt[i].halt_DATE_TO+'</td>'
					 		   +'<td class="fit">'+(TripSheetHalt[i].halt_AMOUNT).toFixed(2)+'</td>'
							   +'<td class="fit">'+TripSheetHalt[i].halt_PLACE+'</td>'
				               +'<td class="fit">'+TripSheetHalt[i].halt_PAIDBY+'</td>'
							   +'</tr>';
				srNo ++;
			}
			incomeTable += '</tbody></table></td></tr>'; 
			
			$('#tripSheetSubDetailsBody').append(incomeTable);		
	}
}
function setTripSheetRefreshment(data){

	if(data.refreshment != undefined && data.refreshment.length > 0){
		$('#refreshmentDate').val(data.TripSheet.closetripDate);
		var refreshment = data.refreshment;
		var returnedAmount = 0, totalVolume = 0, balanceAmt = 0, totalReturnAmt = 0, totalBalance = 0;
		var  incomeTable = '<tr><td><table class="table table-bordered table-striped"><thead>'
				+'<tr class="breadcrumb">'
				   +'<th class="fit">R.NO</th>'
				   +'<th class="fit ar">Date</th>'
				   +'<th class="fit ar">Quantity</th>'
				   +'<th class="fit ar">Returned Qty</th>'
				   +'<th class="fit ar">Consumption</th>'
				   +'<th class="fit ar">Amount</th>'
				   +'<th class="fit ar">Return Amt</th>'
				   +'<th class="fit ar">Balance</th>'
				   +'<th class="fit ar">Action</th></tr></thead><tbody>';
			for(var i = 0; i < refreshment.length; i++ ){
				 totalVolume  	 = refreshment[i].quantity - refreshment[i].returnQuantity ;
				 returnedAmount  = refreshment[i].returnQuantity * refreshment[i].unitprice;
				 balanceAmt		 = refreshment[i].totalAmount -returnedAmount;
				 totalReturnAmt +=  returnedAmount;
				 totalBalance +=  balanceAmt;
				incomeTable += '<tr data-object-id="" class="ng-scope">'	
				               +'<td class="fit"><a href="#">R-'+refreshment[i].refreshmentEntryNumber+'</a></td>'
				               +'<td class="fit">'+refreshment[i].asignmentDateStr+'</td>'
							   +'<td class="fit">'+(refreshment[i].quantity).toFixed(2)+'</td>'
					 		   +'<td class="fit">'+(refreshment[i].returnQuantity).toFixed(2)+'</td>'
							   +'<td class="fit">'+totalVolume.toFixed(2)+'</td>'
				               +'<td class="fit">'+refreshment[i].totalAmount.toFixed(2)+'</td>'
				               +'<td class="fit">'+returnedAmount.toFixed(2)+'</td>'
				               +'<td class="fit"><i class="fa fa-inr"></i> '+balanceAmt.toFixed(2)+'</td>'
							   +'<td class="fit"><a href="#" onclick="returnRefreshmentToTripSheet('+refreshment[i].refreshmentEntryId+','+totalVolume+', '+refreshment[i].returnQuantity+')"; class="btn btn-info btn-sm fa fa-undo"> Return</a></td></tr>';
			}
			data.tripTotalincome = data.tripTotalincome - totalBalance;
			incomeTable += '<tr><td colspan="2" class="fit ar"><h4>Total :</h4></td> <td class="fit ar"><h4>'+(data.totalQty).toFixed(2)+'</h4></td><td class="fit ar"><h4>'+(data.totalRQty).toFixed(2)+'</h4></td><td class="fit ar"><h4>'+(data.totalConsumption).toFixed(2)+'</h4></td><td class="fit ar"><h4> '+data.grandTotal.toFixed(2)+'</h4></td><td class="fit ar"><h4>'+totalReturnAmt.toFixed(2)+'</h4></td><td class="fit ar"><h4><i class="fa fa-inr">'+totalBalance.toFixed(2)+'</h4></td></tr></tbody></table></td></tr>'; 
			
			$('#tripSheetSubDetailsBody').append(incomeTable);		
	}
}
function setTripSheetComment(data){
	if(data.TripComment != undefined && data.TripComment.length > 0){
		$('#commentCount').html(data.TripComment.length);
		var TripComment = data.TripComment;
		var  incomeTable = '<tr><td><table class="table table-bordered table-striped"><tbody>';
			for(var i = 0; i < TripComment.length; i++ ){
				incomeTable += '<tr data-object-id="" class="ng-scope">'	
							   +'<td class="fit"><div class="timeline-item"><strong><a data-toggle="tip"><i class="fa fa-user"></i>'
							   +''+TripComment[i].createdby+' commented from <b>'+TripComment[i].created_PLACE+' '+TripComment[i].created_DATE+'</strong></b></a>'
							   +'<div class="pull-right"><span class="time"><i class="fa fa-clock-o"></i>'+TripComment[i].created_DATE_DIFFERENT+'</span>'
							   +'<a class="btn btn-info btn-flat btn-xs" href="#" onclick="removeTripComment('+TripComment[i].tripcid+');"><i class="fa fa-trash"></i></a></div>'
							   +'<div class="timeline-body">'+TripComment[i].trip_COMMENT+'</div>'
							   +'</div></td></tr>'
			}
			incomeTable += '</tbody></table></td></tr>'; 
			$('#tripSheetSubDetailsBody').append(incomeTable);		
	}

}
function setTripAdvanceExpense(data){
	var TripSheet = data.TripSheet;
	var configuration = data.configuration;
	var  incomeTable = '<tr><td><table class="table table-bordered table-striped"><thead>'
			+'<tr class="breadcrumb">'
			   +'<th class="fit">Paid To</th>'
			   +'<th class="fit ar">Paid By</th>';
			  if(configuration.removeAdvanceFromDriverBalance){
				  incomeTable += '<th class="fit ar">(Income - Expense)</th>';
			  }	else{
				  
				  incomeTable += '<th class="fit ar">(Advance - Expense)</th>';
			  }
			  incomeTable += '<th class="fit ar">Reference</th>'
			   +'<th class="fit ar">TS-Close Date</th>'
			   +'<th class="fit ar">Status</th>'
			   +'</tr></thead><tbody>';
	var  balance = 0;
	  if(configuration.removeAdvanceFromDriverBalance){
		  balance = (TripSheet.tripTotalincome - TripSheet.tripTotalexpense).toFixed(2);
		  tripBalance = data.tripTotalincome
		  driverBalance =data.driverBalance;
	  }else if(configuration.roundFigureAmount){
		  balance = Math.round(TripSheet.tripTotalAdvance - TripSheet.tripTotalexpense);
		  driverBalance = Math.round(data.driverBalance);
		  tripBalance = Math.round(data.tripTotalincome);
	  }else{
		  tripBalance = data.tripTotalincome;
		  driverBalance =data.driverBalance;
		  balance = (TripSheet.tripTotalAdvance - TripSheet.tripTotalexpense).toFixed(2);
	  }
	  
	  
			incomeTable += '<tr data-object-id="" class="ng-scope">'	
			               +'<td class="fit">'+TripSheet.closeTripStatus+'</td>'
						   +'<td class="fit">'+TripSheet.closeTripNameBy+'</td>'
				 		   +'<td class="fit">'+balance+'</td>'
						   +'<td class="fit">'+TripSheet.closeTripReference+'</td>'
			               +'<td class="fit">'+TripSheet.closetripDate+'</td>'
			               +'<td class="fit"><span class="label label-pill label-warning">'+TripSheet.tripStutes+'</span></td></tr></tbody></table></td></tr>';
		$('#tripSheetSubDetailsBody').append(incomeTable);
	
		/*if(data.configuration.changedTripBalance){
			var  tripBalanceRow = '<tr><td><table class="table table-bordered table-striped"><thead></thead><tbody>';
				 tripBalanceRow += '<tr data-object-id="" class="ng-scope">'	
					               +'<td colspan="0" class="key"><h4>"B" income + "E" income - All expenses = Trip Balance</h4></td>'
								   +'<td colspan="0" class="value"><h4>'+ ' <i class="fa fa-inr"></i> ' + data.B_Income + '+ <i class="fa fa-inr"></i> '+ data.E_Income + ' - <i class="fa fa-inr"></i> '+data.TotalExpense + "= "+ (data.tripbalance).toFixed(2) +'</h4></td></tr></tbody></table></td></tr>';
		}
		else{	
			var  tripBalanceRow = '<tr><td><table class="table table-bordered table-striped"><thead></thead><tbody>';
				 tripBalanceRow += '<tr data-object-id="" class="ng-scope">'	
					               +'<td colspan="0" class="key"><h4>Total Income - Total Expenses = Trip Balance</h4></td>'
								   +'<td colspan="0" class="value"><h4><i class="fa fa-inr"></i>'+(Number(TripSheet.tripTotalincome)+' -  <i class="fa fa-inr"></i>'+Number(TripSheet.tripTotalexpense))+'  =   <i class="fa fa-inr"></i>'+(Number(TripSheet.tripTotalincome)-Number(TripSheet.tripTotalexpense)).toFixed(2)+'</h4></td></tr></tbody></table></td></tr>';
		}

	
		if(data.configuration.changedTripBalance){
			var  tripBalanceRow = '<tr><td><table class="table table-bordered table-striped"><thead></thead><tbody>';
				 tripBalanceRow += '<tr data-object-id="" class="ng-scope">'	
					               +'<td colspan="0" class="key"><h4>"B" income + "E" income - All expenses = Trip Balance</h4></td>'
								   +'<td colspan="0" class="value"><h4>'+ ' <i class="fa fa-inr"></i> ' + data.B_Income + '+ <i class="fa fa-inr"></i> '+ data.E_Income + ' - <i class="fa fa-inr"></i> '+data.TotalExpense + "= "+ (data.tripbalance).toFixed(2) +'</h4></td></tr></tbody></table></td></tr>';
		}
		else{	
			var  tripBalanceRow = '<tr><td><table class="table table-bordered table-striped"><thead></thead><tbody>';
				 tripBalanceRow += '<tr data-object-id="" class="ng-scope">'	
					               +'<td colspan="0" class="key"><h4>Total Income - Total Expenses = Trip Balance</h4></td>'
								   +'<td colspan="0" class="value"><h4><i class="fa fa-inr"></i>'+(Number(TripSheet.tripTotalincome)+' -  <i class="fa fa-inr"></i>'+Number(TripSheet.tripTotalexpense))+'  =   <i class="fa fa-inr"></i>'+(Number(TripSheet.tripTotalincome)-Number(TripSheet.tripTotalexpense)).toFixed(2)+'</h4></td></tr></tbody></table></td></tr>';
		}
		*/
	/*var  tripBalanceRow = '<tr><td><table class="table table-bordered table-striped"><thead></thead><tbody>';
		 tripBalanceRow += '<tr data-object-id="" class="ng-scope">'
		
	var  tripBalanceRow = '<tr><td><table class="table table-bordered table-striped"><thead></thead><tbody>';
		 tripBalanceRow += '<tr data-object-id="" class="ng-scope">'	
			               +'<td colspan="0" class="key"><h4>Total Income - Total Expenses = Trip Balance</h4></td>'
						   +'<td colspan="0" class="value"><h4><i class="fa fa-inr"></i>'+(Number(TripSheet.tripTotalincome)+' -  <i class="fa fa-inr"></i>'+Number(TripSheet.tripTotalexpense))+'  =   <i class="fa fa-inr"></i>'+(Number(TripSheet.tripTotalincome)-Number(TripSheet.tripTotalexpense)).toFixed(2)+'</h4></td></tr></tbody></table></td></tr>';
		
					        +'<td colspan="0" class="key"><h4>Total Income - Total Expenses = Trip Balance</h4></td>'
						    +'<td colspan="0" class="value"><h4><i class="fa fa-inr"></i>'+(Number(TripSheet.tripTotalincome)+' -  <i class="fa fa-inr"></i>'+Number(TripSheet.tripTotalexpense))+'  =   <i class="fa fa-inr"></i>'+(Number(TripSheet.tripTotalincome)-Number(TripSheet.tripTotalexpense)).toFixed(2)+'</h4></td></tr></tbody></table></td></tr>';
		$('#tripSheetSubDetailsBody').append(tripBalanceRow);
		$('#tripSheetSubDetailsBody').append(tripBalanceRow);*/
		
		
		if(data.configuration.driverBalanceWithNarration){
				var  tripBalanceRow2 = '<tr><td><table class="table table-bordered table-striped"><thead></thead><tbody>';
				tripBalanceRow2 += '<tr data-object-id="" class="ng-scope">'	
					+'<td colspan="0" class="key"><h4><b>'+data.driverBalanceKey+':</b></h4></td>'
					+'<td colspan="0" class="value"><h4><i class="fa fa-inr"></i><b> '+(data.balanceAmount).toFixed(2)+'</b></h4></td></tr></tbody></table></td></tr>';
			
			$('#tripSheetSubDetailsBody').append(tripBalanceRow2);
		}
		if(data.configuration.showDriverBalance){
			var  tripBalanceRow3 = '<tr><td><table class="table table-bordered table-striped"><thead></thead><tbody>';
			if(data.configuration.addInDriverbalanceAfterTripClose && TripSheet.tripStutesId > 2){
				if(TripSheet.closeTripStatusId == 1){
					tripBalanceRow3 += '<tr data-object-id="" class="ng-scope"><td><h4><b>Driver TripSheet Balance <span>(Advance - Expense)</span> </td><td>'+ (Number(TripSheet.closeTripAmount+TripSheet.driverBalance)).toFixed(2)+' </b></h4></td></tr>';
					tripBalanceRow3 += '<tr data-object-id="" class="ng-scope"><td><h4><b>Cash Statement(Credit)  </td><td>'+ TripSheet.closeTripAmount  +' </b></h4></td></tr>';
					tripBalanceRow3 += '<tr data-object-id="" class="ng-scope"><td><h4><b>Push To Tally </td><td>'+ TripSheet.driverBalance  +' </b></h4></td></tr>';
				}
				else{
					tripBalanceRow3 += '<tr data-object-id="" class="ng-scope"><td><h4><b>Driver TripSheet Balance <span>(Advance - Expense)</span> </td><td>'+ (Number(TripSheet.closeTripAmount+TripSheet.driverBalance)).toFixed(2)+'</b></h4> </td></tr>';
					tripBalanceRow3 += '<tr data-object-id="" class="ng-scope"><td><h4><b>Cash Statement(Debit)  </td><td>'+ TripSheet.closeTripAmount  +' </b></h4></td></tr>';
					tripBalanceRow3 += '<tr data-object-id="" class="ng-scope"><td><h4><b>Push To Tally </td><td>'+ TripSheet.driverBalance  +'</b></h4> </td></tr>';
				}
			}
				
			tripBalanceRow3 += '<tr data-object-id="" class="ng-scope">';
			
				if(!data.configuration.addInDriverbalanceAfterTripClose){
					if(data.configuration.showDriverBalanceBifurcation)
						tripBalanceRow3 +='<td colspan="0" class="key"><h4> Driver advance +"B" income - "B" income due - All expenses <b>= Trip Balance</b></h4></td>';
					else
						tripBalanceRow3 +='<td colspan="0" class="key"><h4><b>Driver Balance :</b></h4></td>';
				}
				if(!data.configuration.addInDriverbalanceAfterTripClose){
					if(data.configuration.showDriverBalanceBifurcation)
						tripBalanceRow3 +=	'<td colspan="0" class="value"><h4><i class="fa fa-inr"></i> '+ data.TotalAdvance + ' + <i class="fa fa-inr"></i> ' + data.B_Income + ' - <i class="fa fa-inr"></i> ' + data.B_IncomeDue + ' - <i class="fa fa-inr"></i> ' +data.TotalExpense + '<b>= '+ driverBalance.toFixed(2) +'</b></h4></td></tr></tbody></table></td></tr>';
					else
						tripBalanceRow3 +=	'<td colspan="0" class="value"><h4><i class="fa fa-inr"></i><b> '+driverBalance.toFixed(2)+'</b></h4></td></tr></tbody></table></td></tr>';
				}
				
			$('#tripSheetSubDetailsBody').append(tripBalanceRow3);
		}
		
		if(data.configuration.changedTripBalance){
			if($("#View_BE_TripBalance").val() == "true" || $("#View_BE_TripBalance").val() == true){
				var  tripBalanceRow = '<tr><td><table class="table table-bordered table-striped"><thead></thead><tbody>';
					 tripBalanceRow += '<tr data-object-id="" class="ng-scope">'	
						               +'<td colspan="0" class="key"><h4 style="font-size:17px;" >"B" income + "E" income - All expenses <b> = Trip Balance</b></h4></td>'
									   +'<td colspan="0" class="value"><h4>'+ ' <i class="fa fa-inr"></i> ' + data.B_Income + '+ <i class="fa fa-inr"></i> '+ data.E_Income + ' - <i class="fa fa-inr"></i> '+data.TotalExpense + "<b> = "+ (data.tripbalance).toFixed(2) +'</b></h4></td></tr></tbody></table></td></tr>';
			}
		}
		else{	
			var  tripBalanceRow = '<tr><td><table class="table table-bordered table-striped"><thead></thead><tbody>';
				 tripBalanceRow += '<tr data-object-id="" class="ng-scope">'	
					               +'<td colspan="0" class="key"><h4>Total Income - Total Expenses = Trip Balance</h4></td>'
								   +'<td colspan="0" class="value"><h4><i class="fa fa-inr"></i>'+(Number(TripSheet.tripTotalincome)+' -  <i class="fa fa-inr"></i>'+Number(TripSheet.tripTotalexpense))+'  =   <i class="fa fa-inr"></i>'+(Number(TripSheet.tripTotalincome)-Number(TripSheet.tripTotalexpense)).toFixed(2)+'</h4></td></tr></tbody></table></td></tr>';
		}
		
		$('#tripSheetSubDetailsBody').append(tripBalanceRow);
		
		if(data.configuration.showTripsheetDueAmount){
			var  tripBalanceRow4 = '<tr><td><table class="table table-bordered table-striped"><thead></thead><tbody>';
			tripBalanceRow3 += '<tr data-object-id="" class="ng-scope">'	
				+'<td colspan="0" class="key"><h4><b>Due Amount:</b></h4></td>'
				+'<td colspan="0" class="value"><h4><i class="fa fa-inr"></i><b> '+data.TotalDueAmount.toFixed(2)+'</b></h4></td></tr></tbody></table></td></tr>';
			
			$('#tripSheetSubDetailsBody').append(tripBalanceRow4);
		}

}


function setTripSheetAccountDetails(data){
	var TripSheet = data.TripSheet;
	if(TripSheet.tripStutesId == 4){
		
		var formulaRow = '<tr>';
		         if(data.urea != undefined && data.urea.length > 0){
		        	 formulaRow += '<td class="fit ar">Balance : Total Income - (Driver expenses + fuel expenses paid in credit or card + urea expense + fasttag toll expenses)</td>';
		         }else{
		        	 formulaRow += '<td class="fit ar">Balance : Total Income - (Driver expenses + fuel expenses paid in credit or card + fasttag toll expenses)</td>';
		         }
		 $('#tripSheetSubDetailsBody').append(formulaRow);	            
		
		var  incomeTable = '<tr><td><table class="table table-bordered table-striped"><thead>'
			+'<tr class="breadcrumb">'
			+'<th class="fit">A/C Closed By</th>'
			+'<th class="fit ar">Balance</th>'
			+'<th class="fit ar">A/C Reference</th>'
			+'<th class="fit ar">A/C Closed Date</th>'
			+'<th class="fit ar">Status</th>'
			+'</tr></thead><tbody>';
		
		incomeTable += '<tr data-object-id="" class="ng-scope">'	
			+'<td class="fit">'+TripSheet.closeACCTripNameBy+'</td>'
			+'<td class="fit">'+data.tripTotalincome+'</td>'
			+'<td class="fit">'+TripSheet.closeACCTripReference+'</td>'
			+'<td class="fit">'+TripSheet.closeACCtripDateStr+'</td>'
			+'<td class="fit"><span class="label label-pill label-warning">'+TripSheet.tripStutes+'</span></td></tr></tbody></table></td></tr>';
			
					
		if(TripSheet.tripStutesId==TRIP_STATUS_ACCOUNT_CLOSED)
		{
			$("#showChangeStatus").show();
		}
		
		
		$('#tripSheetSubDetailsBody').append(incomeTable);
	}
		
}
function setTripSheetUsageData(data){
	var TripSheet = data.TripSheet;
	if(TripSheet.tripStutesId == 3 || TripSheet.tripStutesId == 4){
		var  incomeTable = '<tr><td><table class="table table-bordered table-striped"><thead>'
			+'<tr class="breadcrumb">'
			+'<th class="fit"></th>'
			+'<th class="fit ar">Actual</th>'
			+'<th class="fit ar">Fixed</th>'
			+'<th class="fit ar">Differences</th></tr></thead><tbody>';
		var diffvolume = data.fTDiesel - TripSheet.routeTotalLiter;
		var diffUsageKM = TripSheet.tripUsageKM - TripSheet.routeApproximateKM;
		incomeTable += '<tr data-object-id="" class="ng-scope">'	
			+'<td class="text-right">Differences of Trip Volume :</td>'
			+'<td class="fit">'+(data.fTDiesel).toFixed(2)+'</td>'
			+'<td class="fit">'+TripSheet.routeTotalLiter+'</td>'
			+'<td class="fit">'+(diffvolume).toFixed(2)+'</td></tr>';
			
			incomeTable += '<tr class="breadcrumb">'
							+'<td class="text-right">Differences of Trip KM Usage :</td>'
							+'<td class="fit">'+TripSheet.tripUsageKM+'</td>'
							+'<td class="fit">'+TripSheet.routeApproximateKM+'</td>';
							if(TripSheet.tripClosingKMStatusId == 1){
							    incomeTable += '<td class="fit">Meter : <a data-toggle="tip" data-original-title="Meter Not Working">Meter Not Working </a></td>';
							}else{
								incomeTable += '<td class="fit">'+diffUsageKM+'</td></tr></tbody></table></td></tr>';
							}
		
		$('#tripSheetSubDetailsBody').append(incomeTable);
	}
}
$(document).ready(function() {
	$("#tripCompanyId1").select2( {
		ajax: {
			url:"searchCompanyList.in", dataType:"json", type:"GET", contentType:"application/json", data:function(a) {
				return {
					term: a
				}
			}
	, results:function(a) {
		return {
			results:$.map(a, function(a) {
				return {
					text: a.company_name, slug: a.slug, id: a.company_id
				}
			}
			)
		}
	}
		}
	}),$("#tripCompanyId").select2( {
        ajax: {
            url:"searchCompanyList.in", dataType:"json", type:"GET", contentType:"application/json", data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.company_name, slug: a.slug, id: a.company_id
                        }
                    }
                    )
                }
            }
        }
    }
    ),$("#selectTripSheetNumber").select2( {
        minimumInputLength:1, minimumResultsForSearch:10, ajax: {
            url:"searchTripSheetNumberList.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a,
                    tripCompanyId : $('#tripCompanyId').val()
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.tripSheetNumberStr, slug: a.slug, id: a.tripSheetID
                        }
                    }
                    )
                }
            }
        }
    }), $("#tripCompanyId").change(function() {
    	$("#selectTripSheetNumber").select2("val", "");
    	$("#reason").val('');
    })
});
function superUserTripSheetDelete(){
	if(confirm("Are You Sure To Delete ?")) {
		if(Number($('#tripCompanyId').val()) <= 0){
			showMessage('info', 'Please Select Company !');
			return false;
		}
		if(Number($('#selectTripSheetNumber').val()) <= 0){
			showMessage('info', 'Please Select TripSheet Number !');
			return false;
		}
		if($('#reason').val() == undefined ||  $('#reason').val() == null || $('#reason').val().trim() == ''){
			showMessage('info', 'Please Enter Reason !');
			return false;
		}
		var jsonObject			= new Object();
		
		jsonObject["tripsheetId"] 	  	=  $('#selectTripSheetNumber').val();
		jsonObject["companyId"] 	  	=  $('#companyId').val();
		jsonObject["userId"] 	  		=  $('#userId').val();
		jsonObject["tripCompanyId"] 	=  $('#tripCompanyId').val();
		jsonObject["reason"] 			=  $('#reason').val();
		
		showLayer();
		$.ajax({
	             url: "superUserTripSheetDelete",
	             type: "POST",
	             dataType: 'json',
	             data: jsonObject,
	             success: function (data) {
	            	 if(data.accountClosed != undefined){
	            		 showMessage('info', 'TripSheet A/C Closed , We can not delete it !');
	            		 hideLayer();
	            		 return false;
	            	 }
	            	 if(data.notFoundToDelete != undefined){
	            		 showMessage('info', 'TripSheet Not Found To Delete/Already Deleted !');
	            		 hideLayer();
	            		 return false;
	            	 }
	            	 showMessage('success', 'Data Deleted Successfully !');
	            	 location.replace('newTripSheetEntries.in');
	             },
	             error: function (e) {
	            	 showMessage('errors', 'Some error occured!')
	            	 hideLayer();
	             }
		});
	}
	
}
function showTallyCompanyModal(){
	$('#tallyCompanyModal').modal('show');
}
function saveTallyCompany(){

	if(Number($('#tallyCompanyId').val()) <= 0 ){
		showMessage('info', 'Please Select Tally Company !');
		return false;
	}
	var jsonObject			= new Object();
	
	jsonObject["tripSheetId"] 		=  $('#tripSheetId').val();
	jsonObject["tallyCompanyId"] 	=  $('#tallyCompanyId').val();
	
	
	showLayer();
	$.ajax({
             url: "updateTallyCompanyToTripSheet",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 showMessage('success', 'Data added successfully !');
            	 showLayer();
            	 window.location.replace("showTripSheet.in?tripSheetID="+data.tripSheetId);
            	
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});

}

function updateGpsUsage(tripsheetId , vid , dispatchOn, closeOn){
	
	var jsonObject			= new Object();
	
	jsonObject["tripSheetID"] 		=  tripsheetId;
	jsonObject["vid"] 				=  vid;
	jsonObject["dispatchOn"] 		=  dispatchOn;
	jsonObject["closeOn"] 			=  closeOn;
	
	
	showLayer();
	$.ajax({
             url: "updateTripSheetUsageKM",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 if(data.updated == true || data.updated == 'true'){
            		 showMessage('success','GPS Usage Odometer Updated Successfully')
            		 hideLayer();
            	 }else if(data.notFound == true){
            		 showMessage('success','GPS Data Not Found')
            		 hideLayer();
            	 }
            	 location.reload();
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});
	
}
function getWayBillTypeWiseData(wayBillTypeId, dispatchLedgerId, wayBillTypeWiseHM){
	if(wayBillTypeWiseHM != undefined && dispatchLedgerId != undefined){
		var key = dispatchLedgerId+'_'+wayBillTypeId;
		if(wayBillTypeWiseHM[key] != undefined){
			return wayBillTypeWiseHM[key].bookingTotal;
		}else{
			return 0;
		}
	}else{
		return '--';
	}
}

function updateTripRoutePoint(){

	
	var jsonObject			= new Object();
	
	jsonObject["tripSheetId"] 			=  $('#tripSheetId').val();
	jsonObject["driver1RoutePoint"] 	=  $('#driver1Point').val();
	jsonObject["driver2RoutePoint"] 	=  $('#driver2Point').val();
	jsonObject["cleanerRoutePoint"] 	=  $('#cleanerPoint').val();
	
	$('#editRoutePointModal').modal('hide');
	
	showLayer();
	$.ajax({
             url: "updateTripRoutePoint",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            		 showMessage('success','Data Updated Successfully')
            		 	$('#driver1RoutePoint').text($('#driver1Point').val())
            		 	$('#driver2RoutePoint').text($('#driver2Point').val());
            			$('#cleanerRoutePoint').text($('#cleanerPoint').val());
            			hideLayer();
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});
	

}

//$(document).ready(function() {
//	
//	 if($('#tripSheetId').val() != undefined && $('#tripSheetId').val() != null && Number($('#tripSheetId').val()) > 0){
//		 
//		 var jsonObject				= new Object();
//		 jsonObject["companyId"] 	=  $('#companyId').val();
//		 jsonObject["userId"] 		=  $('#userId').val();
//		 jsonObject["tripsheetId"] 	=  $('#tripSheetId').val();
//		 jsonObject["vehId"] 		=  $('#vehId').val();
//		 
//		 console.log("jsonObject..",jsonObject);
//		 
////		 showLayer();
////		 $.ajax({
////			 url: "showTripSheetDetails",
////			 type: "POST",
////			 dataType: 'json',
////			 data: jsonObject,
////			 success: function (data) {
////				 setTripSheetDetails(data);
////				 hideLayer();
////			 },
////			 error: function (e) {
////				 showMessage('errors', 'Some error occured!')
////				 hideLayer();
////			 }
////		 });
//	 }
//	 
//});

function getPendingDueAmountData(data){
	
	if($('#showTripsheetDueAmount').val()){
		
		var jsonObject				= new Object();
		 jsonObject["companyId"] 	=  $('#companyId').val();
		 jsonObject["userId"] 		=  $('#userId').val();
		 jsonObject["tripsheetId"] 	=  $('#tripSheetId').val();
		 jsonObject["vehId"] 		=  data.TripSheet.vid;
		 
		 showLayer();
		 $.ajax({
			 url: "getPreviousDueAmountData",
			 type: "POST",
			 dataType: 'json',
			 data: jsonObject,
			 success: function (data) {
				 
				 if(data.previousDueAmount > 0){
					 $("#pendingDue").text("Pending Due Amount - Rs "+data.previousDueAmount.toFixed(2)); 
				 } else {
					 $("#pendingDue").text("Pending Due Amount - Rs 0");
				 }
				 
				 hideLayer();
			 },
			 error: function (e) {
				 showMessage('errors', 'Some error occured!')
				 hideLayer();
			 }
		 });
		
	}
	
}

$("#balanceDueAmount").click(function(){ 
	
	 var jsonObject				= new Object();
	 jsonObject["companyId"] 	=  $('#companyId').val();
	 jsonObject["userId"] 		=  $('#userId').val();
	 jsonObject["tripsheetId"] 	=  $('#tripSheetId').val();
	 jsonObject["vehId"] 		=  tripsheetVehicleId;
	 
	 console.log("jsonObjectbalanceDueAmount..",jsonObject);
	 
	 showLayer();
	 $.ajax({
		 url: "getPreviousDueAmountDataList",
		 type: "POST",
		 dataType: 'json',
		 data: jsonObject,
		 success: function (data) {
			 
			 setDueAmountReport(data);
			 $('#previousDuePayment').modal('show');
			 hideLayer();
		 },
		 error: function (e) {
			 showMessage('errors', 'Some error occured!')
			 hideLayer();
		 }
	 });
	
});

function setDueAmountReport(data) {
	console.log("due...",data);
	$("#firstTableHeader").html("Tripsheet Due Amount");
	$("#dueAmountTable").empty();
	
	var thead = $('<thead style="background-color: aqua;">');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th>');
	var th5		= $('<th>');
	var th6		= $('<th>');

	tr1.append(th1.append("No"));
	tr1.append(th2.append("Tripsheet No."));
	tr1.append(th3.append("Driver Name"));
	tr1.append(th4.append("Due Date"));
	tr1.append(th5.append("Due Amount"));
	tr1.append(th6.append("Balance Amount"));

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
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append('<a href="showTripSheet?tripSheetID='+dueAmountList[i].tripSheetID+'" target="_blank">TS-'+dueAmountList[i].tripSheetNumber+'</a>'));
			tr1.append(td3.append(dueAmountList[i].driver_firstname + "-"+dueAmountList[i].driver_Lastname));
			tr1.append(td4.append(dueAmountList[i].dueDateStr)); 
			tr1.append(td5.append(dueAmountList[i].dueAmount));
			tr1.append(td6.append(dueAmountList[i].balanceAmount));

			tbody.append(tr1);
		}
		
		$("#dueAmountTable").append(thead);
		$("#dueAmountTable").append(tbody);
		
	} else {
		showMessage('info', 'No Record Found!')
	}

}

function editDocModal(){
	$("#editTripSheetDoc").modal('show');
}
function updateTripSheetDocument(){
	var jsonObject							= new Object();
	jsonObject["tripSheetId"] 				=  $('#tripSheetId').val();
	jsonObject["input-file-preview"] 		=  $('#tripDocument').val();
	
	var form = $('#fileUploadForm')[0];
    var data = new FormData(form);

    data.append("updateDocument", JSON.stringify(jsonObject));



    showLayer();
    $.ajax({
		type: "POST",
		enctype: 'multipart/form-data',
		url: "updateTripSheetDocument",
		data: data,
		processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
		success: function (data) {
			hideLayer();
		}
    });
}

function downloadTripSheetDocument(){
	if($("#tripSheetDocumentId").val() != undefined && $("#tripSheetDocumentId").val() != ""){
		window.location.replace('download/tripSheetDocument/'+$("#tripSheetDocumentId").val()+'.in');
	}
}
function setLorrryHireDetails(loryHireList){
	console.log('inside setLorrryHireDetails.....')
		var tableBody = '<tr><td><table class="table table-bordered table-striped"><thead>'
			+'<tr class="breadcrumb">'
			   +'<th class="fit">Hire Number</th>'
			   +'<th class="fit">Hire Date</th>'
			   +'<th class="fit ar">Vendor Name</th>'
			   +'<th class="fit ar">Hire Amount</th>'
			   +'<th class="fit ar">Advance</th>'
			   +'<th class="fit ar">Paid Amount</th>'
			   +'<th class="fit ar">Balance</th>'
			    +'<th class="fit ar">Status</th></tr></thead><tbody>';
			  for(var i=0;i<loryHireList.length; i++){
				tableBody += '<tr data-object-id="" class="ng-scope">'	
				               +'<td class="fit"><a target="_blank" href="ShowLorryHireDetails.in?ID='+loryHireList[i].lorryHireDetailsId+'">LH-'+loryHireList[i].lorryHireDetailsNumber+'</a></td>'
				               +'<td class="fit">'+loryHireList[i].hireDateStr+'</td>'
				               +'<td class="fit"><a target="_blank" href="ShowVendor.in?vendorId='+loryHireList[i].vendorId+'">'+loryHireList[i].vendorName+'</a></td>'
				               +'<td class="fit">'+loryHireList[i].lorryHire+'</td>'
				               +'<td class="fit">'+loryHireList[i].advanceAmount+'</td>'
				               +'<td class="fit">'+loryHireList[i].paidAmount+'</td>'
				               +'<td class="fit">'+loryHireList[i].balanceAmount+'</td>'
				               +'<td class="fit">'+loryHireList[i].paymentStatus+'</td>'
				               +'</tr>';
			  }
			  tableBody += '</tbody></table></td></tr>'
			    
		$('#tripSheetSubDetailsBody').append(tableBody);		

}
function setTripSheetROuteWiseWeight(data)
{
	var srNo = 1;
	var diff=0;
	
			if(data.TripSheetRouteWiseWeight != undefined && data.TripSheetRouteWiseWeight.length > 0){
			
			var  TripSheetWeight = data.TripSheetRouteWiseWeight;
			var weightTable = '<tr><td><table class="table table-bordered table-striped"><thead>'
							  + '<tr class="breadcrumb">'
							  + '<th class="fit">No</th>'
							  + '<th class="fit">RouteName</th>'
							  + '<th class="fit">Actual Weight</th>'
							  + '<th class="fit">Scale Weight</th>'
							  + '<th class="fit">Weight Difference</th></tr></thead><tbody>';
					
					for(var i = 0; i<TripSheetWeight.length;i++)
					{
						diff = TripSheetWeight[i].actualWeight - TripSheetWeight[i].scaleWeight;
						weightTable  += '<tr data-object-id="" class="ng-scope">'
									    +'<td class="fit">'+srNo+'</td>'
									    +'<td class="fit">'+TripSheetWeight[i].routeName+'</td>'
									    +'<td class="fit">'+TripSheetWeight[i].actualWeight+'</td>'
									    +'<td class="fit">'+TripSheetWeight[i].scaleWeight+'</td>'
									    +'<td class="fit">'+diff+'</td></tr>'
						srNo ++;
	  					diff= 0; 
					}
					
					weightTable += '</tbody></table></td></tr>'; 
					$('#tripSheetSubDetailsBody').append(weightTable);	
		}
}


