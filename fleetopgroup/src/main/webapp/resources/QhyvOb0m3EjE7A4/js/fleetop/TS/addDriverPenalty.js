$(document).ready(function() {
	getTripDetails();
	getDriverPanelty();
});

function getTripDetails(){
	
	var jsonObject			= new Object();
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
	
	$("#tripNumber").text(tripsheet.tripSheetNumber);
	$("#showTripNumber").text("TS-"+tripsheet.tripSheetNumber);
	$("#vehicleRegistration").text(tripsheet.vehicle_registration);
	$("#route").text(tripsheet.routeName);
	$("#tripOpenDate").text(tripsheet.created);
	$("#vehicleGroup").text(tripsheet.dispatchedLocation);
	$("#driver").text(tripsheet.tripFristDriverName+" "+tripsheet.tripFristDriverLastName+" - "+tripsheet.tripFristDriverFatherName);
	$("#conductor").text(tripsheet.tripSecDriverName+" "+tripsheet.tripSecDriverLastName+" - "+tripsheet.tripSecDriverFatherName);
	$("#cleaner").text(tripsheet.tripCleanerName);
	$("#tripOpenKm").text(tripsheet.tripOpeningKM);
	$("#tripCloseDate").text(tripsheet.tripClosingKM);
	$("#tripUsage").text(tripsheet.tripTotalUsageKM);
	$("#createdDate").text(tripsheet.dispatchedByTime);
	
	
	if(tripsheet.tripFristDriverID > 0 ){
		$("#driverId").append($("<option>").text(tripsheet.tripFristDriverName +" "+tripsheet.tripFristDriverFatherName+" - "+tripsheet.tripFristDriverLastName).attr("value",tripsheet.tripFristDriverID));
	}
	if(tripsheet.tripSecDriverID > 0 ){
		$("#driverId").append($("<option>").text(tripsheet.tripSecDriverName+" "+tripsheet.tripSecDriverLastName+" - "+tripsheet.tripSecDriverFatherName).attr("value",tripsheet.tripSecDriverID));
	}
	if(tripsheet.tripCleanerID > 0 ){
		$("#driverId").append($("<option>").text(tripsheet.tripCleanerName).attr("value",tripsheet.tripCleanerID));
	}
	
}

function saveDriverPenalty(){
	
	if(Number($('#driverId').val()) <= 0){
		showMessage('info','Please select driver !');
		return false;
	}
	if(Number($('#advanceAmount').val()) <= 0){
		showMessage('info','Please enter penalty amount !');
		return false;
	}
	if($('#penaltyDate').val() == null || $('#penaltyDate').val().trim() == ''){
		showMessage('info','Please select penalty date !');
		return false;
	}
	
	var jsonObject			= new Object();
	jsonObject["tripNumber"] 	  			=  $('#tripNumber').text();
	jsonObject["tripsheetId"] 	  			=  $('#tripsheetId').val();
	jsonObject["driverId"] 	  				=  $('#driverId').val();
	jsonObject["penaltyDate"] 	  			=  $('#penaltyDate').val();
	jsonObject["advanceAmount"] 	  		=  $('#advanceAmount').val();
	jsonObject["remark"] 	  				=  $('#remark').val();
	showLayer();
	
	$.ajax({
		url: "saveDriverPenalty",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			showMessage('info','Penalty Added Successfully');
			location.reload();
			hideLayer();
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			hideLayer();
		}
	});
}

function getDriverPanelty(){

	var jsonObject							= new Object();
	jsonObject["tripsheetId"] 	  			=  $('#tripsheetId').val();
	
	$.ajax({
		url: "getDriverPenaltyByTripsheetId",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data != undefined){
				setDriverPanelty(data);
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

function setDriverPanelty(data){
	$("#penaltyTable").empty();
	var driverAdvanvce = data.DriverAdvanvce ;
	
	for(var index = 0 ; index < driverAdvanvce.length; index++){
	
		 var columnArray = new Array();
			 columnArray.push("<td class='fit ar' style='vertical-align: middle;'>"+(index+1)+"</td>");
			 columnArray.push("<td class='fit ar' style='vertical-align: middle;'>" + driverAdvanvce[index].driver_empnumber + "-"+driverAdvanvce[index].driver_firstname+" "+driverAdvanvce[index].driver_Lastname+" - "+driverAdvanvce[index].driverFatherName+ "</td>");
			 columnArray.push("<td class='fit ar' style='vertical-align: middle;'>" + driverAdvanvce[index].advance_DATE + "</td>");
			 columnArray.push("<td class='fit ar' style='vertical-align: middle;'>" + driverAdvanvce[index].advance_AMOUNT + "</td>");
			 columnArray.push("<td class='fit ar' style='vertical-align: middle;'>" + driverAdvanvce[index].advance_PAID_BY + "</td>");
			 columnArray.push("<td class='fit ar' style='vertical-align: middle;' ><a href='#' class='confirmation' onclick='removeDriverPenalty("+driverAdvanvce[index].dsaid+");'><span class='fa fa-trash'></span> Delete</a></td>");
		 $('#penaltyTable').append("<tr  class='ng-scope' id='penaltyID"+driverAdvanvce[index].dsaid+"' >" + columnArray.join(' ') + "</tr>");
		 }
		 columnArray = [];
		 
}


function removeDriverPenalty(dsaId){

	var jsonObject							= new Object();
	jsonObject["dsaId"] 	  				=  dsaId;
	
	$.ajax({
		url: "deleteDriverPenalty",
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