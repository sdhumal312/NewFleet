var configurationObject = null;
var backDateTripSheet = false;
var previousTrip	  = null;
$(document).ready(function() {
    $.getJSON("getVehicleTripList.in", function(e) {
        $("#TripSelectVehicle").append($("<option>").text("Please Select Vehicle").attr("value", 0)), $.each(e, function(e, t) {
            $("#TripSelectVehicle").append($("<option>").text(t.vehicle_registration).attr("value", t.vid))
        })
    }), $.getJSON("getDriverTripList.in", function(e) {
        $("#driverList").append($("<option>").text("Please Select Driver").attr("value", 0)), $.each(e, function(e, t) {
            $("#driverList").append($("<option>").text(t.driver_empnumber + " - " + t.driver_firstname).attr("value", t.driver_id))
        }), $("#driverList2").append($("<option>").text("Please  Select Driver").attr("value", 0)), $.each(e, function(e, t) {
            $("#driverList2").append($("<option>").text(t.driver_empnumber + " - " + t.driver_firstname).attr("value", t.driver_id))
        }), $("#Cleaner").append($("<option>").text("Please  Select Driver").attr("value", 0)), $.each(e, function(e, t) {
            $("#Cleaner").append($("<option>").text(t.driver_empnumber + " - " + t.driver_firstname).attr("value", t.driver_id))
        })
    }), $.getJSON("getTripRouteList.in", function(e) {
        $("#TripRouteList").append($("<option>").text("Please  Select Route").attr("value", 0)), $.each(e, function(e, t) {
            $("#TripRouteList").append($("<option>").text(t.routeNo + " - " + t.routeName).attr("value", t.routeID))
        })
    })
    /*
    var gpsConfiguration = $('#gpsConfiguration').val();
    var jsonObject = JSON.parse(gpsConfiguration);

    console.log('jsonObject : ', jsonObject);*/
});

function setGpsReletedFeilds(gpsConfiguration){
	var jsonObject = JSON.parse(gpsConfiguration);
    if(jsonObject.allowGPSIntegration == true){
    	/*if(jsonObject.gpsFlavor != 1){
    		$('#gpsKMRow').show();
    	}*/
    	/*if(jsonObject.gpsFlavor == 2){
    		$('#manualKM').hide();
    	}*/
    }
}


$(document).ready(function() {
    $("#TripRouteSubList1").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getTripRouteSubList",
            dataType: "json",
            type: "POST",
            contentType: "application/json",
            quietMillis: 50,
            data: function(e) {
                return {
                    term: e
                }
            },
            results: function(e) {
                return {
                    results: $.map(e, function(e) {
                        return {
                            text: e.routeID + " " + e.routeName,
                            slug: e.slug,
                            id: e.routeID
                        }
                    })
                }
            }
        }
    })
    
    if($("#validateDriverOnTripsheet").val() == "true" || $("#validateDriverOnTripsheet").val() == true){
	    $("#driverList").change(function() {
	   		if($("#driverList").val()!=null && $("#driverList").val() >0){
				getDriverCurrentStatus($("#driverList").val(),1);
			}
	     })
	     $("#driverList2").change(function() {
	   		if($("#driverList2").val()!=null && $("#driverList2").val() >0){
				getDriverCurrentStatus($("#driverList2").val(),2);
			}
	     })
     }
});

function getDriverCurrentStatus(driverId, driverNo)
{
	var jsonObject1			 		= new Object();
	jsonObject1["driverId"]      	= driverId;
	jsonObject1["tripsheetId"]		= $('#tripsheetId').val();
	$.ajax({
			url  : "getDriverStatus",
			type : "POST",
			datatype : 'json',
			data  : jsonObject1,
			success :function(data) {
				if((data.driverIntripRoute == "true" || data.driverIntripRoute == true) && data.driver.driverStatusId ==  3){
					showMessage('info', "Select Another Driver Selected Driver In "+ data.driver.driverStatusStr + " Status");
				}
			},
			error :function(e){
				showMessage('errors', 'Some error occured!');
	            hideLayer();
			}
		})
	if($('#dispatchTime').val() == ''){
		showMessage("select Dispatch Time");
	}
		
	if($('#dispatchedByTime').val() != null && $('#dispatchedByTime').val().trim() != '' && $('#dispatchTime').val() != ''){
		showLayer();
		var jsonObject			= new Object();
		jsonObject["driverId"] 					    =  driverId;
		jsonObject["companyId"] 					=  $('#companyId').val();
		jsonObject["driverNo"]						=  driverNo;
		jsonObject["dispatchedByTime"] 				=  $('#dispatchedByTime').val();
		jsonObject["dispatchedToByTime"] 			=  $('#reservationToTripSheet').val().split("to")[1].replace(/ /g,'');
		jsonObject["dispatchTime"] 					=  $('#dispatchTime').val();
		jsonObject["tripsheetId"]					=  $('#tripsheetId').val();
		
		$.ajax({
			url:"getLastTripSheetDetailsForDriver",
			type:"POST",
			datatype:"json",
			data: jsonObject,
			success: function(data){
				if(data.inBetweenTripSheet != undefined){
					var dispatchDateTime 		= $('#dispatchedByTime').val().split("-").reverse().join("-") +" "+ $('#dispatchTime').val()+":00";
					var dispatchedToByTime 		= ($('#reservationToTripSheet').val().split("to")[1].replace(/ /g,'')).split("-").reverse().join("-") +" "+ $('#dispatchTime').val()+":00";
					var nextDispatchDateTime	= data.inBetweenTripSheet.dispatchedByTime;
					var nextClosedByTime		= data.inBetweenTripSheet.closedByTime;
					
					if((nextDispatchDateTime <= dispatchDateTime && dispatchDateTime <= nextClosedByTime) || (nextDispatchDateTime <= dispatchedToByTime && dispatchedToByTime <= nextClosedByTime) || ( dispatchDateTime <= nextDispatchDateTime && nextClosedByTime <= dispatchedToByTime  )){ 
						showMessage('info','You already Have Tripsheet TS-'+data.inBetweenTripSheet.tripSheetNumber+' For Selected Driver On Same Date And Time Please Select Another Driver')
						
						if(data.inBetweenTripSheet.driverId ==$('#driverList').val()){
							$('#driverList').select2('data', {
	            	    			id : '',
	            	    			text : ''
	            	    		});
						}
						if(data.inBetweenTripSheet.driverId ==$('#driverList2').val()){
							$('#driverList2').select2('data', {
	            	    			id : '',
	            	    			text : ''
	            	    		});
						}
						hideLayer();
						return false;
					}
				}
				else{
					hideLayer();
				}
			},
			error:function(e){
				showMessage('errors', 'Some error occured!');
	            hideLayer();
			}
		})
	}
}
function getVehicleGPSDataAtTime(){
	var allowGPSIntegration	= $('#allowGPSIntegration').val();
	if((allowGPSIntegration == 'true' || allowGPSIntegration == true) && $('#dispatchedByTime').val() != '' && $('#dispatchTime').val() != ''){
		var vid = $('#TripSelectVehicle').val();
		if(Number(vid) <=0){
			showMessage('errors', 'Please select vehicle !');
			$('#dispatchTime').val('');
			return false;
		}
		
		showLayer();
		var jsonObject			= new Object();
		jsonObject["vehicleId"] 					=  $('#TripSelectVehicle').val();
		jsonObject["companyId"] 					=  $('#companyId').val();
		jsonObject["dispatchedByTime"] 				=  $('#dispatchedByTime').val();
		
		if($("#twelveHourClock").val() == "true")
		{
			var NewTime = ChangeDispatchTime();
			jsonObject["dispatchTime"] 					=  NewTime;
		}
		else
		{
			jsonObject["dispatchTime"] 					=  $('#dispatchTime').val();
		}
		jsonObject["fromTripOpening"] 				=  true;
		
		$.ajax({
	             url: "getVehicleGPSDataAtTime",
	             type: "POST",
	             dataType: 'json',
	             data: jsonObject,
	             success: function (data) {
	            	 if(data.VEHICLE_TOTAL_KM != null && data.isOdometerReading){
	            		 $('#tripGpsOpeningKM').val(data.VEHICLE_TOTAL_KM);
	            		 $('#gpsKMRow').show();
	            		 if($('#showGPSodoInOpeningKm').val() == 'true')
	            		 $('#tripOpeningKM').val(data.VEHICLE_TOTAL_KM);
	            		/* $('#gpsLocationRow').show();
	            		 var mapUrl = 'https://www.google.com/maps/search/?api=1&query='+data.VEHICLE_LATITUDE+','+data.VEHICLE_LONGITUDE+'';
	            		 $('#gpsLocationTag').append('<a target="_blank" href='+mapUrl+'>'+data.GPS_LOCATION+'</a>');*/
	            	 }else{
	            		 
	            		 $('#tripGpsOpeningKM').val(0);
	            	 }
	            	 
	            	 hideLayer();
	               
	             },
	             error: function (e) {
	            	 showMessage('errors', 'Some error occured!');
	            	 hideLayer();
	             }
		});
	}
	
}
function getVehicleDetailsOnTime(){

	if($('#dispatchedByTime').val() != '' && $('#dispatchTime').val() != ''){
		var vid = $('#TripSelectVehicle').val();
		if(Number(vid) <=0){
			showMessage('errors', 'Please select vehicle !');
			$('#dispatchTime').val('');
			return false;
		}
		getLastTripSheetDetailsToCopy($('#dispatchedByTime').val(), $('#dispatchTime').val(), true);
	}
}

function getDriverDetails(){
	$('#showDriverDetailsFromItsGatewayApi').addClass('hide');
	
	var allowITSGatewayDriverDetails = $('#allowITSGatewayDriverDetails').val();
	
	if((allowITSGatewayDriverDetails == 'true' || allowITSGatewayDriverDetails == true)){
		
		$('#dispatchTime').val('');
		
		var vid = $('#TripSelectVehicle').val();
		if(Number(vid) <=0){
			showMessage('errors', 'Please select vehicle !');
			return false;
		}
		
		showLayer();
		var jsonObject								= new Object();
		jsonObject["vehicleId"] 					=  $('#TripSelectVehicle').val();
		jsonObject["companyId"] 					=  $('#companyId').val();
		jsonObject["journeyDate"] 					=  $('#reservationToTripSheet').val();
		
		$.ajax({
	             url: "getDriverDetailsFromItsGatewayApi",
	             type: "POST",
	             dataType: 'json',
	             data: jsonObject,
	             success: function (data) {
	            	 
	            	 if(data.noBusIdConfiguredForThisVehicle != undefined && data.noBusIdConfiguredForThisVehicle == true){
	            		 showMessage('info', 'Please Configure Bus Id for this vehicle. Driver details not found.');
	            		 
	     			} else if (data.noRecordFound != undefined && data.noRecordFound == true){
	     				showMessage('info', 'Driver details not found.');
	     			
	     			} else if (data.diverDetailsNotFound != undefined && data.diverDetailsNotFound == true){
	     				showMessage('info', 'Driver Mobile Details Not Found');
	     			
	     			}else {
	     				
	     				if(data.diverDetails != undefined && data.dispatchTime != undefined) {
	     				   
	     				    $('#dispatchTime').val(''+data.dispatchTime+'');
	     				    $('#driverList').select2('data', {
	     				        id : data.diverDetails.driver_id,
	     				        text : data.driverName
	     				        });
	     				    
	     				   getLastTripsheetFuelMileageById(data.diverDetails.driver_id);
	     				   
	     				    $('#buttonMessageDriverDetails').html('Trip Details Found. '
	     				            +'</br> Route Time : '+data.routeTime+''
	     				            +'</br> Driver Name : '+data.driverName+''
	     				            +'</br> Route Name : '+data.routeName+'');
	     				    $('#showDriverDetailsFromItsGatewayApi').removeClass('hide');
	     				   
	     				    } else {
	     				    console.log("Driver Details not found !!")
	     				    }
	     				
	     			}
	            	 
	            	 hideLayer();
	               
	             },
	             error: function (e) {
	            	 showMessage('errors', 'Some error occured!');
	            	 hideLayer();
	             }
		});
	}
}
function setConfigurationFeilds(configuration){
	configurationObject = JSON.parse(configuration);
	
	
   if(configurationObject.showAlwaysDispatchTime){
	   $('#dispatchDateTime').show();
   }
   if(configurationObject.driver2){
	   $('#driver2Row').show();
   }
   if(configurationObject.cleaner){
	   $('#cleanerRow').show();
   }
   if(configurationObject.showSubroute){
	   $('#subRouteRow').show();
   }
   if(configurationObject.tripOpenCloseFuelRequired){
	   $('#lastFuelRow').show();
   }
   if(configurationObject.showLoadType){
	   $('#loadTypeRow').show();
   }
   if(configurationObject.showPODdetails){
	   $('#noOfOwnerRow').show();
   }
   if(configurationObject.tripOpenCloseFuelRequired){
	   $('#lastFuelRow').show();
   }
}

function saveTripSheet(status){
	
	$('#saveTripSheet').hide();
	$('#dispatchTripSheet').hide();
	
	if(!validateTripSheetSave()){
		$('#saveTripSheet').show();
		$('#dispatchTripSheet').show();
		return false;
	}
	if(!validateOdometer()){
		$('#saveTripSheet').show();
		$('#dispatchTripSheet').show();
		return false;
	}
	
	if (validateBankPaymentDetails && !validateBankPayment($('#bankPaymentTypeId').val())) {
		hideLayer();
		return false;
	}
	
	var jsonObject			= new Object();
	var dateRange	= $('#reservationToTripSheet').val();
	  var array 	= dateRange.split('to');
	
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
	jsonObject["tripstatusId"] 	  		=  status;
	jsonObject["bookingReference"] 	  	=  $('#tripBookref').val();
	jsonObject["gpsOpenOdoMeter"] 	  	=  $('#tripGpsOpeningKM').val();
	jsonObject["gpsLocation"] 	  		=  '';
	
	if($("#twelveHourClock").val() == "true")
	{
		var NewTime = ChangeDispatchTime();
		jsonObject["dispatchByTime"] 				=  NewTime;
		jsonObject["backDateDispatchTime"] 	    =  NewTime; 
	}
	else
	{
		jsonObject["dispatchByTime"] 	  	=  $('#dispatchTime').val();
		jsonObject["backDateDispatchTime"] 	=  $('#dispatchTime').val(); 
	}
	jsonObject["numOfPod"] 	  			=  $('#noOfPOD').val();
	jsonObject["backDateDispatchDate"] 	=  array[0].trim();
	jsonObject["tripStartDiesel"] 	  	=  $('#tripStartDiesel').val();
	jsonObject["companyId"] 	  		=  $('#companyId').val();
	jsonObject["userId"] 	  			=  $('#userId').val();
	jsonObject["advanceAmnt"] 	  		=  $('#AdvanceAmount').val();
	jsonObject["advanceRef"] 	  		=  $('#advanceRefence').val();
	jsonObject["advancePaidBy"] 	  	=  $('#userId').val();
	jsonObject["place"] 	  				=  $('#branchId').val();
	jsonObject["ipAddress"] 	  			=  $('#ipAddress').val();
	jsonObject["hexLhpvIds"] 	  			=  $('#hexLhpvIds').val();
	jsonObject["tripsheetId"] 	  			=  $('#tripsheetId').val();
	jsonObject["validateDoublePost"] 	 	=  true;
	jsonObject["unique-one-time-token"] 	=  $('#accessToken').val();
	jsonObject["input-file-preview"] 		=  $('#tripDocument').val();
	jsonObject["meterNotWorking"] 		=  $('#meterNotWorking').is(':checked');
	jsonObject["advancePaymentType"] 	  	=  $('#renPT_option').val();
	
	if (allowBankPaymentDetails) {
		prepareObject(jsonObject)
	}
	
	var form = $('#fileUploadForm')[0];
    var data = new FormData(form);

    data.append("tripDetails", JSON.stringify(jsonObject));
	
	/*showLayer();
	$.ajax({
             url: "saveTripSheetData",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {*/
	
	showLayer();
    $.ajax({
		type: "POST",
		enctype: 'multipart/form-data',
		url: "saveTripSheetData",
		data: data,
		processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
		success: function (data) {
            	 	if(data.accessToken == undefined){
            	 		 if(data.tripsheetId != undefined){
                    		 window.location.replace("showTripSheet.in?tripSheetID="+data.tripsheetId);
                    		 showMessage('success', 'Data Saved Successfully!')
                    	 }else{
                        	 showMessage('errors', 'Some error occured!')
                        	 hideLayer();
                    	 }
            	 	}else{
            	 		 if(data.vidNotFound != undefined && data.vidNotFound){
                   		 showMessage('info', 'Vehicle Not Found')
                   		 $('#dispatchTripSheet').show();
                   		 $('#saveTripSheet').show();
                   	 }else if(data.inActiveStatus != undefined && data.inActiveStatus){
                		 showMessage('errors', 'Cannot Save TripSheet, Vehicle Status Is '+data.vehicleStatus)
                	 }else if(data.odometerNotFound != undefined && data.odometerNotFound){
                   		 showMessage('info', 'Odometer Not Found')
                   		 $('#dispatchTripSheet').show();
                   		 $('#saveTripSheet').show();
                   	 }else if(data.dateNotFound != undefined && data.dateNotFound){
                   		 showMessage('info', 'Date Not Found!')
                   		 $('#dispatchTripSheet').show();
                   		 $('#saveTripSheet').show();
                   	 }else if(data.inBetweenTrip != undefined){
                   		 showMessage('info', 'TripSheet Already Exists For Selcted Dispatch Time : <a href="showTripSheet.in?tripSheetID='+data.inBetweenTrip.tripSheetID+'">TS-'+data.inBetweenTrip.tripSheetNumber+'</a>')
                   		 $('#dispatchTripSheet').show();
                   		 $('#saveTripSheet').show();
                   	 }else if(data.activeTripSheet != undefined){
                   		 showMessage('info', 'TripSheet Already Exists For Selected Vehicle : <a href="showTripSheet.in?tripSheetID='+data.activeTripSheet.tripSheetID+'">TS-'+data.activeTripSheet.tripSheetNumber+'</a>')
                   		 $('#dispatchTripSheet').show();
                   		 $('#saveTripSheet').show();
                   	 }else if(data.driverOneNotActive != undefined && data.driverOneNotActive){
                   		 showMessage('info', 'Cannot Save TripSheet, Driver One Is Not in Active Status !');
                   		 $('#dispatchTripSheet').show();
                   		 $('#saveTripSheet').show();
                   	 }else if(data.driverTwoNotActive != undefined && data.driverTwoNotActive){
                   		 showMessage('info', 'Cannot Save TripSheet, Driver Two Is Not in Active Status !');
                   		 $('#dispatchTripSheet').show();
                   		 $('#saveTripSheet').show();
                   	 }else if(data.cleanerNotActive != undefined && data.cleanerNotActive){
                   		 showMessage('info', 'Cannot Save TripSheet, Cleaner Is Not in Active Status !');
                   		 $('#dispatchTripSheet').show();
                   		 $('#saveTripSheet').show();
                   	 }
            	 	 $('#accessToken').val(data.accessToken);
                   	 hideLayer();
                    
            	 	}
            	 
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});

}
function validateTripSheetSave(){
	if(Number($('#TripSelectVehicle').val()) <= 0){
		showMessage('errors', 'Please select vehicle !');
		return false;
	}
	

	if($('#reservationToTripSheet').val() == null || $('#reservationToTripSheet').val() == ''){
		showMessage('errors', 'Please select Journey Date !');
		return false;
	}

	if(configurationObject.showAlwaysDispatchTime == true || backDateTripSheet || ($('#tripEdit').val() != undefined || $('#tripEdit').val() == 'true' || $('#tripEdit').val() == true)){
		if($('#dispatchTime').val() == null || $('#dispatchTime').val().trim() == ''){
			showMessage('errors','Please Enter Dispatch Time !');
			$('#dispatchTime').focus();
			return false;
		}
	}
	
	if(Number($('#driverList').val()) <= 0){
		showMessage('errors','Please Select Driver 1!');
		return false;
	}
	
	if(Number($('#driverList2').val()) > 0){
		if(Number($('#driverList2').val()) == Number($('#driverList').val())){ 
			showMessage('errors','Driver 1 and Driver 2 cannot be same !');
			return false;
		}
	}
	
	if(Number($('#TripRouteList').val()) <= 0 && ($('#routeName').val() == null || $('#routeName').val().trim() == '')){
		showMessage('errors','Please Select Route!');
		return false;
	}
	
	
	if(configurationObject.tripOpenCloseFuelRequired == true){
		if(Number($('#tripStartDiesel').val()) <= 0){
			$('#tripStartDiesel').focus();
			showMessage('errors','Please Enter Last Diesel !');
			return false;
		}
	}
	if(configurationObject.showLoadType == true){
		if(Number($('#loadListId').val()) <= 0){
			showMessage('errors','Please Select Load Type !');
			return false;
		}
	}
	return true;
}
function getLastTripSheetDetailsToCopy(dispatchedByTime, dispatchTime, onDispatchSelect){
		showLayer();
		var jsonObject			= new Object();
		jsonObject["vehicleId"] 					=  $('#TripSelectVehicle').val();
		jsonObject["companyId"] 					=  $('#companyId').val();
		jsonObject["dispatchedByTime"] 				=  dispatchedByTime;
		
		if($("#twelveHourClock").val() == "true" && $('#dispatchTime').val() != '')
		{
			var NewTime = ChangeDispatchTime();
			jsonObject["dispatchTime"] 				  =  NewTime;
		}
		else
		{
			jsonObject["dispatchTime"] 				=  dispatchTime;
		}
		
		jsonObject["onDispatchSelect"] 				=  onDispatchSelect;
			
		
		//important to set from removed method 
		$.ajax({
	             url: "getVehicleDetailsOnTime",
	             type: "POST",
	             dataType: 'json',
	             data: jsonObject,
	             success: function (data) {
	            	 
	            	 if(data.serviceOverDue){
	             		showMessage('errors','There is mandetory service reminder is overdue, you can not create tripsheet Service Reminders is : '+data.overDueReminder);
	             		$("#TripSelectVehicle").select2("val", "");
	             		return false;
	             	}
	            	 
	            	 if(data.savedTripSheet != undefined){
	            		 $("#TripSelectVehicle").select2("val", "");
	            		 showAlert('error', 'You have already one tripsheet saved for this vehicle : <a href="showTripSheet.in?tripSheetID='+data.savedTripSheet.tripSheetID+'">TS-'+data.savedTripSheet.tripSheetNumber+'</a> ');
	            	     hideLayer();
	            		 return false;
	            	 }
	            	 
	            	 	if(data.tripStatus != undefined){
	            	 		if(data.vehicle.vStatusId == 5){
	            	 			$('#last_occurred').html('Cannot Create TripSheet Vehicle Status is '+data.vehicle.vehicle_Status+' Close :  <a href="addCloseTripsheet.in?tripSheetID='+data.tripStatus.tripSheetID+'">TS-'+data.tripStatus.tripSheetNumber+'</a> ');
	            	 			$('#last_occurred').show();
	            	 		}
	            	 		if(data.vehicle.vStatusId == 6){
	            	 			$('#last_occurred').html('Cannot Create TripSheet Vehicle Status is '+data.vehicle.vehicle_Status+' Close : <a href="showWorkOrder?woId='+data.tripStatus.workorders_id+'">WO-'+data.tripStatus.workorders_Number+'</a> ');
	            	 			$('#last_occurred').show();
	            	 		}
	            	 	}
	            	 	if(data.vehicle != undefined && data.vehicle.vStatusId != 1){
	            	 		showMessage('info','Cannot Create TripSheet Vehicle Status is '+data.vehicle.vehicle_Status);
	            	 	if($("#tripEdit").val() == true || $("#tripEdit").val()  == 'true'){
	            	 		$('#TripSelectVehicle').select2('data', {
	            				id : $("#vid").val(),
	            				text : $("#vregist").val()
	            			});
	            	 		
	            	 		hideLayer();
	            	 	}else{
	            	 		resetTripSheetFeilds();
	            	 	}
	            	 		hideLayer();
	            	 		return false;
	            	 	}else{
	            	 		$('#last_occurred').hide();
	            	 	}
	            	 if(data.gpsObject != undefined && data.gpsObject.VEHICLE_TOTAL_KM != null){
	            		 $('#tripGpsOpeningKM').val(data.gpsObject.VEHICLE_TOTAL_KM);
	            		 $('#gpsKMRow').show();
	            	 }else{
	            		 $('#tripGpsOpeningKM').val(0);
	            	 }
	            	 
	            	 $('#vehicle_ExpectedOdameter').val(data.vehicle.vehicle_ExpectedOdameter);
	            	 $('#vehicle_Odometer').val(data.vehicle.vehicle_Odometer);
	            	 $('#vehicle_ExpectedMileage').val(data.vehicle.vehicle_ExpectedMileage);
	            	 
	            	 if(data.tripSheet != undefined){
	            		
	            		 if(data.tripSheet.tripClosingKM >= data.vehicle.vehicle_Odometer){
	            			 $('#tripOpeningKM').val(data.tripSheet.tripClosingKM);
	            		 }else{
	            			 $('#tripOpeningKM').val(data.vehicle.vehicle_Odometer);
	            		 }
	            		 //setting last trip end diesel entry for srisai
	            		 if(data.tripEndDiesel != undefined && data.tripEndDiesel > 0){
	            	         	$('#tripStartDiesel').val(data.tripEndDiesel);
	            	     }
	            		 setOdometerRange(data.tripSheet.tripClosingKM, Number($('#tripOpeningKM').val())+ data.vehicle.vehicle_ExpectedOdameter);
	            	 }else{
	            		 $('#tripOpeningKM').val(data.vehicle.vehicle_Odometer);
	            		 setOdometerRange(Number($('#tripOpeningKM').val()), Number($('#tripOpeningKM').val())+ data.vehicle.vehicle_ExpectedOdameter);
	            	 }
	            	 setPreviousTripSheet(data.tripSheet);
	            	 //setting various details related to vehicle
	            	 
	            	 if(data.vehicle != undefined){
	            		 $("#vehicle_Group").val(data.vehicle.vehicle_Group);
	            		 //setting driver name if inside driver master vehicle number saved
	            		 if($("#tripEdit").val() == undefined || $("#tripEdit").val()  == false ||$("#tripEdit").val()  == 'false'){
	            			 if(data.vehicle.driverFirstName != undefined && data.vehicle.driverFirstName != null){
	            	    		$('#driverList').select2('data', {
	            	    			id : data.vehicle.partlocation_id,
	            	    			text : data.vehicle.driverEmpName+" "+data.vehicle.driverFirstName+" "+data.vehicle.driverLastName+" - "+data.vehicle.driverFatherName
	            	    		});
	            	    		getLastTripsheetFuelMileageById(data.vehicle.partlocation_id);
		            	    }else{
		            	    	$("#driverList").select2("val", "");
		            	    }
	            		 }
	            		// setting default route name if route saved inside vehicle master
	            		 if($("#tripEdit").val() == undefined || $("#tripEdit").val()  == false || $("#tripEdit").val()  == 'false'){
		            		 if(data.vehicle.vehicle_RouteName != undefined && data.vehicle.vehicle_RouteName != null){
		            	    		$('#TripRouteList').select2('data', {
		            	    			id : data.vehicle.routeID,
		            	    			text : data.vehicle.vehicle_RouteName
		            	    		});
		            	    }else{
		            	    	$("#TripRouteList").select2("val", "");
		            	    }
	            		 }
	            	 }
	            	 
	            	 hideLayer();
	               
	             },
	             error: function (e) {
	            	 showMessage('errors', 'Some error occured!');
	            	 hideLayer();
	             }
		});

}
function resetTripSheetFeilds(){
	$("#TripSelectVehicle").select2("val", "");
	$("#vehicle_Group").val('');
	$('#reservationToTripSheet').val('');
	$('#dispatchTime').val('');
	$('#dispatchedByTime').val('');
	$("#driverList").select2("val", "");
	$("#driverList2").select2("val", "");
	$("#Cleaner").select2("val", "");
	$("#TripRouteList").select2("val", "");
	$('#subRouteName').val('');
	$('#tripGpsOpeningKM').val('');
	$('#tripBookref').val('');
	$("#tripOpeningKM").val(0);
	$('#tripStartDiesel').val();
	$("#loadListId").select2("val", "");
	$('#noOfPOD').val('');
	$("#advanceDriverId").select2("val", "");
	$('#AdvanceAmount').val('');
	$('#advanceRefence').val('');
	$('#fuel_comments').val('');
}
function getLastNextTripSheetDetails(){
	
	if($('#dispatchedByTime').val() != null && $('#dispatchedByTime').val().trim() != '' && $('#TripSelectVehicle').val() != ''){
		showLayer();
		var jsonObject			= new Object();
		jsonObject["vehicleId"] 					=  $('#TripSelectVehicle').val();
		jsonObject["companyId"] 					=  $('#companyId').val();
		jsonObject["dispatchedByTime"] 				=  $('#dispatchedByTime').val();
		jsonObject["dispatchedToByTime"] 			=  $('#reservationToTripSheet').val().split("to")[1].replace(/ /g,'');
		
		if($("#twelveHourClock").val() == "true")
		{
			var NewTime = ChangeDispatchTime();
			jsonObject["dispatchTime"] 				=  NewTime;
		}
		else
		{
			jsonObject["dispatchTime"] 	  			=  $('#dispatchTime').val();
		}
		
		
		$.ajax({
			url: "getLastNextTripSheetDetails",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				if(data.inBetweenTripSheet != undefined){
					
					if($("#twelveHourClock").val() == "true" && $('#dispatchTime').val() != '')
					{
						var NewTime = ChangeDispatchTime();
						var dispatchDateTime 		= $('#dispatchedByTime').val().split("-").reverse().join("-") +" "+ NewTime +":00";
						var dispatchedToByTime 		= ($('#reservationToTripSheet').val().split("to")[1].replace(/ /g,'')).split("-").reverse().join("-") +" "+ NewTime +":00";
					}
					else
					{
						var dispatchDateTime 		= $('#dispatchedByTime').val().split("-").reverse().join("-") +" "+ $('#dispatchTime').val()+":00";
						var dispatchedToByTime 		= ($('#reservationToTripSheet').val().split("to")[1].replace(/ /g,'')).split("-").reverse().join("-") +" "+ $('#dispatchTime').val()+":00";
					}
					
					var nextDispatchDateTime	= data.inBetweenTripSheet.dispatchedByTime;
					var nextClosedByTime		= data.inBetweenTripSheet.closedByTime;
					
					
					if((nextDispatchDateTime <= dispatchDateTime && dispatchDateTime <= nextClosedByTime) || (nextDispatchDateTime <= dispatchedToByTime && dispatchedToByTime <= nextClosedByTime) || ( dispatchDateTime <= nextDispatchDateTime && nextClosedByTime <= dispatchedToByTime  )){
						showMessage('info','You already Have Tripsheet TS-'+data.inBetweenTripSheet.tripSheetNumber+' On Same Date And Time Please Select Another Date Or Time')
						
						if($("#preDispatchDate").val() != undefined){
							
							var preDispatchDate 	= $("#preDispatchDate").val();
							var preDispatchToDate 	= $("#preDispatchToDate").val();
							var preDispatchTime 	= $("#preDispatchTime").val();
							
							$('#reservationToTripSheet').val(''+preDispatchDate+' to '+preDispatchToDate+'');
							$('#dispatchTime').val(preDispatchTime);
						}else{
							$("#dispatchTime").val('');
						}
						hideLayer();
						return false;
					}
					
				}
				
				
				if(data.tripSheet != undefined && data.nextTripSheet != undefined ){
					
					$('#tripOpeningKM').val(data.tripSheet.tripClosingKM);
					if(data.nextTripSheet.tripOpeningKM > data.tripSheet.tripClosingKM){
						setOdometerRange(data.tripSheet.tripClosingKM, data.nextTripSheet.tripOpeningKM);
					}else{
						setOdometerRange(data.tripSheet.tripClosingKM, data.tripSheet.tripClosingKM + Number($('#vehicle_ExpectedOdameter').val()));
					}
					
				}else if(data.tripSheet != undefined && data.nextTripSheet == undefined){
					$('#tripOpeningKM').val(data.tripSheet.tripClosingKM);
					setOdometerRange(data.tripSheet.tripClosingKM, data.tripSheet.tripClosingKM + Number($('#vehicle_ExpectedOdameter').val()));
				}else if(data.nextTripSheet != undefined && data.tripSheet == undefined){
					$('#tripOpeningKM').val(data.nextTripSheet.tripOpeningKM - Number($('#vehicle_ExpectedOdameter').val()));
				//		$('#tripOpeningKM').val(data.nextTripSheet.tripOpeningKM);
					if(data.nextTripSheet.tripOpeningKM - Number($('#vehicle_ExpectedOdameter').val()) > 0){
				//	if(data.nextTripSheet.tripOpeningKM > 0){
						setOdometerRange(data.nextTripSheet.tripOpeningKM - Number($('#vehicle_ExpectedOdameter').val()) , data.nextTripSheet.tripOpeningKM);
					//	setOdometerRange(data.nextTripSheet.tripOpeningKM ,$('#vehicle_ExpectedOdameter').val())
					}else{
						console.log("4")
						setOdometerRange(0  , data.nextTripSheet.tripOpeningKM);
					//	setOdometerRange(0  , $('#vehicle_ExpectedOdameter').val());
						
					}
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
				
				if($('#showGPSodoInOpeningKm').val() == 'true')
				getVehicleGPSDataAtTime();
				hideLayer();
			},
			error: function (e) {
				showMessage('errors', 'Some error occured!');
				hideLayer();
			}
		});
	}
}
function setOdometerRange(startOdometer, endOdometer){
	$('#errorOpening').html('Odometer Range is : '+startOdometer+' to '+endOdometer);
	$('#minOdometer').val(startOdometer);
	$('#maxOdometer').val(endOdometer);
	$('#errorOpening').show();
}

function validateOdometer(){
	
	var validateOdometerInTripSheet 	= $('#validateOdometerInTripSheet').val();
	var tripOpeningKM					=  Number($('#tripOpeningKM').val());
	var tripClosingKM					=  Number($('#closetripKm').val());
	var maxOdometer						=  Number($('#maxOdometer').val());
	var minOdometer						=  Number($('#minOdometer').val());
	
	if($('#tripOpeningKM').val() == ""){
		showMessage('errors', 'Please Enter Trip Odometer');
		return false;
	}
	
	if(validateOdometerInTripSheet == 'true' || validateOdometerInTripSheet == true){
		if(tripOpeningKM > maxOdometer || tripOpeningKM < minOdometer){
			if($('#meterNotWorking').is(':checked')){
				return true;
			}
			$('#tripOpeningKM').focus();
			showMessage('errors', 'Trip Opening KM Should Be Between '+ minOdometer + ' to '+maxOdometer);
			return false;
		}
		
		if(tripClosingKM != null && tripClosingKM != undefined && tripClosingKM != ''){
			if(tripClosingKM > maxOdometer || tripClosingKM < minOdometer){
				$('#closetripKm').focus();
				showMessage('errors', 'Trip Closing KM Should Be Between '+ minOdometer + ' to '+maxOdometer);
				return false;
			}
		}
		
	}
		return true;		
}

function setPreviousTripSheet(tripSheet){
	if(tripSheet != undefined){
		previousTrip	= tripSheet;
		$('#copyTrip').html('<b><a href="#" onclick="setPreviousTripSheetDataToCurrent();">Copy Previous TripSheet Data</a></b>');
		$('#copyTrip').show();
	}else{
		previousTrip	= null;
	}
}
function setPreviousTripSheetDataToCurrent(){
	if(previousTrip.tripFristDriverID != undefined && previousTrip.tripFristDriverID != null && previousTrip.tripFristDriverID > 0){
		$('#driverList').select2('data', {
			id : previousTrip.tripFristDriverID,
			text : previousTrip.tripFristDriverName
		});
		getLastTripsheetFuelMileageById(previousTrip.tripFristDriverID);
	}
	if(previousTrip.tripSecDriverID != undefined && previousTrip.tripSecDriverID != null && previousTrip.tripSecDriverID > 0){
		$('#driverList2').select2('data', {
			id : previousTrip.tripSecDriverID,
			text : previousTrip.tripSecDriverName
		});
	}
	if(previousTrip.tripCleanerID != undefined && previousTrip.tripCleanerID != null && previousTrip.tripCleanerID > 0){
		$('#Cleaner').select2('data', {
			id : previousTrip.tripCleanerID,
			text : previousTrip.tripCleanerName
		});
	}
	if(previousTrip.routeID != undefined && previousTrip.routeID != null && previousTrip.routeID > 0){
		$('#TripRouteList').select2('data', {
			id : previousTrip.routeID,
			text : previousTrip.routeName
		});
	}

}
function getLastTripsheetFuelMileage(evt){
	
	if($('#driverFuelMileageAlert').val() == 'true' || $('#driverFuelMileageAlert').val() == true){
		showLayer();
		var jsonObject			= new Object();
		jsonObject["driverId"] 					=  $('#'+evt.id+'').val();
		
		findLastTripsheetFuelMileage(jsonObject);
	}
}

function getLastTripsheetFuelMileageById(driverId){
	
	if($('#driverFuelMileageAlert').val() == 'true' || $('#driverFuelMileageAlert').val() == true){
		showLayer();
		var jsonObject			= new Object();
		jsonObject["driverId"] 					=  driverId;
		
		findLastTripsheetFuelMileage(jsonObject);
	}
}

function findLastTripsheetFuelMileage(jsonObject){
	$.ajax({
		url: "getLastTripsheetFuelMileage",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			console.log('data ', data);
			hideLayer();  
			if(data.Fuel != undefined && data.Fuel.fuel_kml != undefined && data.Fuel.fuel_kml > 0 && data.Fuel.fuel_cost != null){
					if(data.Fuel.fuel_cost > data.Fuel.fuel_kml){
						showMessage('errors', 'Driver Having Below Mileage in his last tripsheet '+data.Fuel.tripSheetNumber);
					}
			}
			
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!');
			hideLayer();
		}
	});
}
function ChangeDispatchTime()
{
	var dispatchTime = $('#dispatchTime').val()
	var NewTime;		
	if(dispatchTime.includes('PM'))
	{
		let time12Hour = dispatchTime.substring(0,5);
		let [hours, minutes] = time12Hour.split(':');
		if (hours !== '12') {
		  hours = String(Number(hours) + 12);
		}
		let time24Hour = hours.padStart(2, '0') + ':' + minutes;
		NewTime = time24Hour;
		return NewTime
	}
	else
	{
		let time12Hour = dispatchTime.substring(0,5)
		NewTime = time12Hour;
		return NewTime;
	}
}
