var nextTripCloseDetails;
var nextTripNumber;

$(document).ready(function() {
	setOdometerRange(Number($('#minOdometer').val()), Number($('#maxOdometer').val()));
	//setTimeout(function(){ getLastNextTripSheetDetails(); }, 200);	
	
	if($("#twelveHourClock").val() == "true"){
		var Time = convertTime($('#dispatchTime').val());
		$('#dispatchTime').val(Time);
	}
	
});
function convertTime(time){
	const parsedTime = time.split(":");
	const hours = parseInt(parsedTime[0], 10);
	const minutes = parsedTime[1];
	let period;
	if (hours >= 12) {
	    period = "PM";
	} else {
	    period = "AM";
	}
	const hours12 = ((hours + 11) % 12 + 1).toString().padStart(2, "0");
	const formattedTime = `${hours12}:${minutes}${period}`;
	return formattedTime;
}
function emptyTime(){
	$("#dispatchTime").val('');
}

function updateTripSheetDetails(){
	
	var dateRange	= $('#reservationToTripSheet').val();
	var array 	    = dateRange.split('to');
	
	if($('#driverList').val != undefined || $('#driverList').val != null || $('#driverList2').val != undefined || $('#driverList').val != undefined ){
		getDriverRemiderDetails("driverList","select2-chosen-2");
	}else{
		getDriverRemiderDetails("driverList2","select2-chosen-3");
	}
	
	if($('#tripEndTime').val() != undefined){
		const format 			= "YYYY-MM-DD HH:mm:ss";
		var date 				= new Date();
		var currentDateTime 	= moment(date).format(format);
		
		if($("#twelveHourClock").val() == "true"){
			var NewTime = ChangeDispatchTime();
			var dispatchTime  	 		= array[0].trim().split("-").reverse().join("-")+' '+NewTime+":00";
		}else{
			var dispatchTime  	 		= array[0].trim().split("-").reverse().join("-")+' '+$('#dispatchTime').val()+":00";
		}
		
		var closeDateTime    		= array[1].trim().split("-").reverse().join("-")+' '+$('#tripEndTime').val()+":00";
		var isBeforeDispatchDate	= moment(closeDateTime).isBefore(dispatchTime);
		var isAfterCurrnetDate		= moment(closeDateTime).isAfter(currentDateTime);
		
		if(isBeforeDispatchDate){
			showMessage('info','Can Not Enter Close Date Before Dispatch Date '+dispatchTime+' ');
			$('#saveClose').show();
			hideLayer();
			return false;
		}
		
		if(($("#validateTripCloseTimeAsCurrentTime").val() == true || $("#validateTripCloseTimeAsCurrentTime").val() == 'true') && isAfterCurrnetDate){
			showMessage('info','Can Not Enter Close Date Time After Current Date Time ');
			$('#saveClose').show();
			hideLayer();
			return false;
		}
	}

	if(!validateTripSheetSave()){
		return false;
	}
	
	if($('#tripStatusId').val() == 3){
		if(!validateTripSheetEdit()){
			return false;
		}
	}
	
	if(!validateOdometer()){
		return false;
	}
	
	var jsonObject	= new Object();
	
	jsonObject["vid"] 	  				=  $('#TripSelectVehicle').val();
	jsonObject["openOdoMeter"] 	  		=  $('#tripOpeningKM').val();
	jsonObject["loadTypeId"] 	  		=  $('#loadListId').val();
	jsonObject["fromDate"] 	  			=  array[0].trim();
	jsonObject["toDate"] 	  			=  array[1].trim();
	if(Number($('#isNewRoute').val()) == 0){
		jsonObject["routeServiceId"] 	  	=  $('#TripRouteList').val();
	}else{
		jsonObject["routeServiceId"] 	  	=  0;
	}
	jsonObject["newRouteId"] 	  		=  0;
	jsonObject["newRouteName"] 	  		=  $('#routeName').val();
	jsonObject["subRoute"] 	  			=  $('#subRouteName').val();
	jsonObject["driverId"] 	  			=  $('#driverList').val();
	jsonObject["driver2Id"] 	  		=  $('#driverList2').val();
	jsonObject["cleanerId"] 	  		=  $('#Cleaner').val();
	if($('#dispatchTrip').val() != undefined && ($('#dispatchTrip').val() == 'true' || $('#dispatchTrip').val() == true)){
		jsonObject["tripstatusId"] 	  		=  2;
	}else{
		jsonObject["tripstatusId"] 	  		= $('#tripStatusId').val();
	}
	jsonObject["bookingReference"] 	  	=  $('#tripBookref').val();
	jsonObject["gpsOpenOdoMeter"] 	  	=  $('#tripGpsOpeningKM').val();
	jsonObject["gpsLocation"] 	  		=  '';
	
	if($("#twelveHourClock").val() == "true"){
		var NewTime = ChangeDispatchTime();
		jsonObject["dispatchByTime"] 	  	=  NewTime;
		jsonObject["backDateDispatchTime"] 	=  NewTime;
	}else{	
		jsonObject["dispatchByTime"] 	  	=  $('#dispatchTime').val();
		jsonObject["backDateDispatchTime"] 	=  $('#dispatchTime').val(); 
	}
	
	jsonObject["numOfPod"] 	  			=  $('#noOfPOD').val();
	jsonObject["backDateDispatchDate"] 	=  array[0].trim();
	jsonObject["tripStartDiesel"] 	  	=  $('#tripStartDiesel').val();
	jsonObject["companyId"] 	  		=  $('#companyId').val();
	jsonObject["userId"] 	  			=  $('#userId').val();
	jsonObject["tripsheetId"] 	  		=  $('#tripsheetId').val();
	jsonObject["closetripKm"] 	  		=  $('#closetripKm').val();
	jsonObject["tripEndTime"] 	  		=  $('#tripEndTime').val();
	jsonObject["meterNotWorking"] 	  	=  $('#meterNotWorking').is(':checked');
	
	showLayer();
	$.ajax({
             url: "updateTripSheetData",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 if(data.inActiveStatus != undefined && data.inActiveStatus){
            		 showMessage('errors', 'Cannot Save TripSheet, Vehicle Status Is '+data.vehicleStatus)
            		 hideLayer();
            	 }else if(data.odometerNotFound != undefined && data.odometerNotFound){
               		 showMessage('info', 'Odometer Not Found')
               		 $('#dispatchTripSheet').show();
               		 $('#saveTripSheet').show();
               		 hideLayer();
               	 }else if(data.dateNotFound != undefined && data.dateNotFound){
               		 showMessage('info', 'Date Not Found!')
               		 $('#dispatchTripSheet').show();
               		 $('#saveTripSheet').show();
               		 hideLayer();
               	 }else if(data.inBetweenTrip != undefined){
               		 showMessage('info', 'TripSheet Already Exists For Selcted Dispatch Time : <a href="showTripSheet.in?tripSheetID='+data.inBetweenTrip.tripSheetID+'">TS-'+data.inBetweenTrip.tripSheetNumber+'</a>')
               		 $('#dispatchTripSheet').show();
               		 $('#saveTripSheet').show();
               		 hideLayer();
               	 }else if(data.activeTripSheet != undefined){
               		 showMessage('info', 'TripSheet Already Exists For Selected Vehicle : <a href="showTripSheet.in?tripSheetID='+data.activeTripSheet.tripSheetID+'">TS-'+data.activeTripSheet.tripSheetNumber+'</a>')
               		 $('#dispatchTripSheet').show();
               		 $('#saveTripSheet').show();
               	 }else if(data.driverOneNotActive != undefined && data.driverOneNotActive){
               		 showMessage('info', 'Cannot Save TripSheet, Driver One Is Not in Active Status !');
               		 $('#dispatchTripSheet').show();
               		 $('#saveTripSheet').show();
               		 hideLayer();
               	 }else if(data.driverTwoNotActive != undefined && data.driverTwoNotActive){
               		 showMessage('info', 'Cannot Save TripSheet, Driver Two Is Not in Active Status !');
               		 $('#dispatchTripSheet').show();
               		 $('#saveTripSheet').show();
               		 hideLayer();
               	 }else if(data.cleanerNotActive != undefined && data.cleanerNotActive){
               		 showMessage('info', 'Cannot Save TripSheet, Cleaner Is Not in Active Status !');
               		 $('#dispatchTripSheet').show();
               		 $('#saveTripSheet').show();
               		 hideLayer();
               	 }else if(data.tripsheetId != undefined){
            		 window.location.replace("showTripSheet.in?tripSheetID="+data.tripsheetId);
            		 showMessage('success', 'Data Saved Successfully!')
            	 }else{
                	 showMessage('errors', 'Some error occured!')
                	 hideLayer();
            	 }
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});


}


function validateEditDetails(){
	
	if($('#dispatchedByTime').val() != null && $('#dispatchedByTime').val().trim() != '' && $('#dispatchTime').val().trim() != ''){
		showLayer();
		var jsonObject								= new Object();
		jsonObject["vehicleId"] 					=  $('#TripSelectVehicle').val();
		jsonObject["companyId"] 					=  $('#companyId').val();
		jsonObject["dispatchedByTime"] 				=  $('#dispatchedByTime').val();
		jsonObject["dispatchedToByTime"] 			=  $('#reservationToTripSheet').val().split("to")[1].replace(/ /g,'');
		
		if($("#twelveHourClock").val() == "true"){
			var NewTime = ChangeDispatchTime();
			jsonObject["dispatchTime"] 					=  NewTime;
		}else{
			jsonObject["dispatchTime"] 					=  $('#dispatchTime').val();
		}
		jsonObject["tripSheetId"] 					=  $('#tripsheetId').val();
		
		$.ajax({
			url: "getLastNextTripSheetDetailsForEdit",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				
				if(data.inBetweenTripSheet != undefined){
					
					if($("#twelveHourClock").val() == "true"){
						var NewTime = ChangeDispatchTime();
						var dispatchDateTime 		= $('#dispatchedByTime').val().split("-").reverse().join("-") +" "+ NewTime +":00";
						var dispatchedToByTime 		= ($('#reservationToTripSheet').val().split("to")[1].replace(/ /g,'')).split("-").reverse().join("-") +" "+ NewTime +":00";
					}else{	
						var dispatchDateTime 		= $('#dispatchedByTime').val().split("-").reverse().join("-") +" "+ $('#dispatchTime').val()+":00";
					    var dispatchedToByTime 		= ($('#reservationToTripSheet').val().split("to")[1].replace(/ /g,'')).split("-").reverse().join("-") +" "+ $('#dispatchTime').val()+":00";
					}
					
					var nextDispatchDateTime	= data.inBetweenTripSheet.dispatchedByTime;
					var nextClosedByTime		= data.inBetweenTripSheet.closedByTime;
					
					var a = Number($('#tripsheetId').val());
					var b = Number(data.inBetweenTripSheet.tripSheetID);
					var value = true;
					
					if(a == b){
						value = false;
					}
					
					if( (nextDispatchDateTime <= dispatchDateTime && dispatchDateTime <= nextClosedByTime) || (nextDispatchDateTime <= dispatchedToByTime && dispatchedToByTime <= nextClosedByTime) || ( dispatchDateTime <= nextDispatchDateTime && nextClosedByTime <= dispatchedToByTime  )){
						
						if(value){
							showMessage('info','You already Have Tripsheet TS-'+data.inBetweenTripSheet.tripSheetNumber+' On Same Date And Time Please Select Another Date Or Time')
							$("#dispatchTime").val('');
							hideLayer();
							return false;
						}
						
					}
					
				}
				
				if(data.tripSheet != undefined && data.nextTripSheet != undefined ){
					
					$('#tripOpeningKM').val(data.tripSheet.tripClosingKM);
					if(data.nextTripSheet.tripOpeningKM > data.tripSheet.tripClosingKM){
						setOdometerRange(data.tripSheet.tripClosingKM, data.nextTripSheet.tripOpeningKM);
					}else{
						setOdometerRange(data.tripSheet.tripClosingKM, data.tripSheet.tripClosingKM + Number($('#vehicle_ExpectedOdameter').val()));
					}
					
					nextTripCloseDetails = data.nextTripSheet.dispatchedByTime;
					nextTripNumber 		 = data.nextTripSheet.tripSheetNumber;
					
				}else if(data.tripSheet != undefined && data.nextTripSheet == undefined){
					$('#tripOpeningKM').val(data.tripSheet.tripClosingKM);
					setOdometerRange(data.tripSheet.tripClosingKM, data.tripSheet.tripClosingKM + Number($('#vehicle_ExpectedOdameter').val()));
					
				}else if(data.tripSheet == undefined && data.nextTripSheet != undefined){
					$('#tripOpeningKM').val($('#tripOpeningKM').val());
		
					setOdometerRange($('#tripOpeningKM').val() , data.nextTripSheet.tripOpeningKM);
					
//					$('#tripOpeningKM').val(data.nextTripSheet.tripOpeningKM - Number($('#vehicle_ExpectedOdameter').val()));
//					if(data.nextTripSheet.tripOpeningKM - Number($('#vehicle_ExpectedOdameter').val()) > 0){
//						setOdometerRange(data.nextTripSheet.tripOpeningKM - Number($('#vehicle_ExpectedOdameter').val()) , data.nextTripSheet.tripOpeningKM);
//					}else{
//						setOdometerRange(0  , data.nextTripSheet.tripOpeningKM);
//					}
					
				}else if(data.nextTripSheet == undefined && data.tripSheet == undefined && !backDateTripSheet){
					$('#tripOpeningKM').val($('#vehicle_Odometer').val());
					setOdometerRange(0 , Number($('#vehicle_Odometer').val()) + Number($('#vehicle_ExpectedOdameter').val()) );
					
				}else if(data.nextTripSheet == undefined && data.tripSheet == undefined && backDateTripSheet){
					$('#tripOpeningKM').val($('#vehicle_Odometer').val());
					setOdometerRange(0 , Number($('#vehicle_Odometer').val()) + Number($('#vehicle_ExpectedOdameter').val()));
				}
					setPreviousTripSheet(data.tripSheet);
					
					getVehicleGPSDataAtTime();
				if(data.tripEndDiesel != undefined && data.tripEndDiesel > 0){
					$('#tripStartDiesel').val(data.tripEndDiesel);
				}
				
				hideLayer();
			},
			error: function (e) {
				showMessage('errors', 'Some error occured!');
				hideLayer();
			}
		});
	}else{
		showMessage('info', 'Please select tripsheet date and time first !');
	}
}

function validateTripSheetEdit(){
	
	if($('#tripEndTime').val() == null || $('#tripEndTime').val().trim() == ''){
		showMessage('errors','Please Enter Closing Time !');
		$('#tripEndTime').focus();
		return false;
	}
	
	if( Number($('#tripOpeningKM').val()) >= Number($('#closetripKm').val()) ){
		showMessage('errors','Opening KM cannot be greater than Closing KM');
		$('#tripOpeningKM').focus();
		return false;
	}
	if($("#twelveHourClock").val() == "true"){
		var NewTime = ChangeDispatchTime();
		var openDateTime 		= $('#dispatchedByTime').val().split("-").reverse().join("-") +" "+ NewTime +":00";
	}else{					
		var openDateTime 		= $('#dispatchedByTime').val().split("-").reverse().join("-") +" "+ $('#dispatchTime').val()+":00";
	}
	var closedDateTime 		= ($('#reservationToTripSheet').val().split("to")[1].replace(/ /g,'')).split("-").reverse().join("-") +" "+ $('#tripEndTime').val()+":00";
	
	if(openDateTime > closedDateTime){
		showMessage('errors', 'Trip Dispatch Time Cannot Be Greater Than Trip Close Time ! ');
		return false;
	}
	
	if(nextTripCloseDetails != undefined){
		if(closedDateTime > nextTripCloseDetails){
			showMessage('errors', 'Trip Close Time Cannot Be Greater Than NextTrip TS-'+nextTripNumber+' Dispatch Time ! ');
			return false;
		}
	}
	
	return true;
}
			

function validateEditAfterCloseDetails(){
	
if($('#tripEndTime').val() != undefined){
		
		const format 			= "YYYY-MM-DD HH:mm:ss";
		var date 				= new Date();
		var currentDateTime 	= moment(date).format(format);
		
		if($("#twelveHourClock").val() == "true"){
			var NewTime = ChangeDispatchTime();
			var dispatchTime  	 		= (($('#reservationToTripSheet').val().split("to")[0].trim()).split("-").reverse().join("-")+' '+NewTime).trim();
		}else{
			var dispatchTime  	 		= (($('#reservationToTripSheet').val().split("to")[0].trim()).split("-").reverse().join("-")+' '+$('#dispatchTime').val()).trim();
		}
		var closeDateTime    		= (($('#reservationToTripSheet').val().split("to")[1].trim()).split("-").reverse().join("-")+' '+$('#tripEndTime').val()).trim();
		var isBeforeDispatchDate	= moment(closeDateTime).isBefore(dispatchTime);
		var isAfterCurrnetDate		= moment(closeDateTime).isAfter(currentDateTime);
		
		if(isBeforeDispatchDate){
			showMessage('info','Can Not Enter Close Date Before Dispatch Date '+dispatchTime+' ');
			$("#tripEndTime").val('');
			hideLayer();
			return false;
		}
		
		if(($("#validateTripCloseTimeAsCurrentTime").val() == true || $("#validateTripCloseTimeAsCurrentTime").val() == 'true') && isAfterCurrnetDate){
			showMessage('info','Can Not Enter Close Date Time After Current Date Time ');
			$("#tripEndTime").val('');
			hideLayer();
			return false;
		}
	}
	
	
	if($('#dispatchedByTime').val() != null && $('#dispatchedByTime').val().trim() != '' && $('#dispatchTime').val().trim() != '' && $('#tripEndTime').val().trim() != '' ){
		showLayer();
		var jsonObject								= new Object();
		jsonObject["vehicleId"] 					=  $('#TripSelectVehicle').val();
		jsonObject["companyId"] 					=  $('#companyId').val();
		jsonObject["dispatchedByTime"] 				=  $('#dispatchedByTime').val();
		jsonObject["dispatchedToByTime"] 			=  $('#reservationToTripSheet').val().split("to")[1].replace(/ /g,'');
		jsonObject["dispatchTime"] 					=  $('#tripEndTime').val();
		if($("#twelveHourClock").val() == "true"){
			var NewTime = ChangeDispatchTime();
			jsonObject["realDispatchTime"] 				=  NewTime;
		}else{
			jsonObject["realDispatchTime"] 				=  $('#dispatchTime').val();
		}	
		
		jsonObject["tripSheetId"] 					=  $('#tripsheetId').val();
		
		$.ajax({
			url: "getLastNextTripSheetDetailsForEdit",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				
				if(data.inBetweenTripSheet != undefined){
					var dispatchDateTime 		= $('#dispatchedByTime').val().split("-").reverse().join("-") +" "+ $('#tripEndTime').val()+":00";
					var dispatchedToByTime 		= ($('#reservationToTripSheet').val().split("to")[1].replace(/ /g,'')).split("-").reverse().join("-") +" "+ $('#tripEndTime').val()+":00";
					var nextDispatchDateTime	= data.inBetweenTripSheet.dispatchedByTime;
					var nextClosedByTime		= data.inBetweenTripSheet.closedByTime;
					
					var a = Number($('#tripsheetId').val());
					var b = Number(data.inBetweenTripSheet.tripSheetID);
					var value = true;
					
					if(a == b){
						value = false;
					}
					
					if( (nextDispatchDateTime <= dispatchDateTime && dispatchDateTime <= nextClosedByTime) || (nextDispatchDateTime <= dispatchedToByTime && dispatchedToByTime <= nextClosedByTime) || ( dispatchDateTime <= nextDispatchDateTime && nextClosedByTime <= dispatchedToByTime  )){
						
						if(value){
							showMessage('info','You already Have Tripsheet TS-'+data.inBetweenTripSheet.tripSheetNumber+' On Same Date And Time Please Select Another Date Or Time')
							$("#tripEndTime").val('');
							hideLayer();
							return false;
						}
						
					}
					
				}
				
				if(data.tripSheet != undefined && data.nextTripSheet != undefined ){
					
					$('#tripOpeningKM').val(data.tripSheet.tripClosingKM);
					if(data.nextTripSheet.tripOpeningKM > data.tripSheet.tripClosingKM){
						setOdometerRange(data.tripSheet.tripClosingKM, data.nextTripSheet.tripOpeningKM);
					}else{
						setOdometerRange(data.tripSheet.tripClosingKM, data.tripSheet.tripClosingKM + Number($('#vehicle_ExpectedOdameter').val()));
					}
					
					nextTripCloseDetails = data.nextTripSheet.dispatchedByTime;
					nextTripNumber 		 = data.nextTripSheet.tripSheetNumber;
					
				}else if(data.tripSheet != undefined && data.nextTripSheet == undefined){
					$('#tripOpeningKM').val(data.tripSheet.tripClosingKM);
					setOdometerRange(data.tripSheet.tripClosingKM, data.tripSheet.tripClosingKM + Number($('#vehicle_ExpectedOdameter').val()));
					
				}else if(data.tripSheet == undefined && data.nextTripSheet != undefined){
					$('#tripOpeningKM').val($('#tripOpeningKM').val());
		
					setOdometerRange($('#tripOpeningKM').val() , data.nextTripSheet.tripOpeningKM);
					
//					$('#tripOpeningKM').val(data.nextTripSheet.tripOpeningKM - Number($('#vehicle_ExpectedOdameter').val()));
//					if(data.nextTripSheet.tripOpeningKM - Number($('#vehicle_ExpectedOdameter').val()) > 0){
//						setOdometerRange(data.nextTripSheet.tripOpeningKM - Number($('#vehicle_ExpectedOdameter').val()) , data.nextTripSheet.tripOpeningKM);
//					}else{
//						setOdometerRange(0  , data.nextTripSheet.tripOpeningKM);
//					}
					
				}else if(data.nextTripSheet == undefined && data.tripSheet == undefined && !backDateTripSheet){
					$('#tripOpeningKM').val($('#vehicle_Odometer').val());
					setOdometerRange(0 , Number($('#vehicle_Odometer').val()) + Number($('#vehicle_ExpectedOdameter').val()) );
					
				}else if(data.nextTripSheet == undefined && data.tripSheet == undefined && backDateTripSheet){
					$('#tripOpeningKM').val($('#vehicle_Odometer').val());
					setOdometerRange(0 , Number($('#vehicle_Odometer').val()) + Number($('#vehicle_ExpectedOdameter').val()));
				}
				
					setPreviousTripSheet(data.tripSheet);
				
				if(data.tripEndDiesel != undefined && data.tripEndDiesel > 0){
					$('#tripStartDiesel').val(data.tripEndDiesel);
				}
				
				hideLayer();
			},
			error: function (e) {
				showMessage('errors', 'Some error occured!');
				hideLayer();
			}
		});
	}else{
		showMessage('info', 'Please select tripsheet date and time first !');
	}
}