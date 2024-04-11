
function setTripClosingDataforGps(gpsConfiguration, gpsObject){
	showLayer();
		var gpsData;
		var configuration = JSON.parse(gpsConfiguration);
		if(gpsObject != undefined && gpsObject != null && gpsObject != ''){
			
			gpsData = JSON.parse(gpsObject);
			
			if((configuration.allowGPSIntegration == true || allowGPSIntegration == 'true') && configuration.gpsFlavor != 1){
				if(gpsData.VEHICLE_TOTAL_KM != undefined && gpsData.VEHICLE_GPS_WORKING && gpsData.isOdometerReading){
					$('#gpsClosingKMRow').show();
					$('#tripGpsClosingKM').val(gpsData.VEHICLE_TOTAL_KM);
					if(gpsData.GPS_LOCATION != null && gpsData.GPS_LOCATION.trim() != ''){
						$('#gpsClosingLocationRow').show();
						$('#gpsCloseLocation').val(gpsData.GPS_LOCATION);
					}
					if(configuration.gpsFlavor == 2){
						$('#manualClosingKm').hide().prop('required',false);
					}
				}else{
					if(gpsData.isOdometerReading){
						$('#tripGpsClosingKM').val(0);
						$('#gpsNotWorking').show();
					}
				}
				
			}
		}
			
	hideLayer();
}

function getGpsVehicleOdometerDetails(fromdate , toDate, vehicleGPSId){
	
	var gPSIntegration = $('#allowGPSIntegration').val();
	var allowGPSIntegration = false;
	if(gPSIntegration == 'true' || gPSIntegration == true){
		allowGPSIntegration = true;
	}
	if(allowGPSIntegration){
		showLayer();
		var jsonObject			= new Object();
	    var closeDate = $('#closetripDate').val();
	    
	    var closeTime = $('#tripEndTime').val();
	    var closeTime2 = $('#tripEndTime').val();
	    
	    closeDate =  closeDate.split("-").reverse().join("-")+' '+closeTime+':00';
	    
		jsonObject["fromdate"] 					=  fromdate;
		jsonObject["toDate"] 					=  closeDate;
		jsonObject["vehicleGPSId"] 				=  vehicleGPSId;
		
		$.ajax({
	             url: "getVehicleGPSDetailsAtSpecifiedTime",
	             type: "POST",
	             dataType: 'json',
	             data: jsonObject,
	             success: function (data) {
	            	 if(data.VEHICLE_ODOMETER != undefined){
	            		 $('#tripGpsClosingKM').val(data.VEHICLE_ODOMETER);
	            		 $('#tripCloseKM').val(Math.round(data.VEHICLE_ODOMETER));
	            	 }else{
	            		 $('#tripGpsClosingKM').val(0);
	 					$('#gpsNotWorking').show();
	 				}
	            	 
	            	 hideLayer();
	               // setData(data);
	             },
	             error: function (e) {
	            	 showMessage('errors', 'Some error occured!');
	            	 hideLayer();
	             }
		});
	}
	
}

function getVehicleGPSDataAtTime(){
	var allowGPSIntegration	= $('#allowGPSIntegration').val();
	if(allowGPSIntegration == 'true' || allowGPSIntegration == true){
		if(($('#tripEndTime').val() != null && $('#tripEndTime').val() != '') && $('#closetripDate').val() != null && $('#closetripDate').val() != ''){
			showLayer();
			var jsonObject			= new Object();
			jsonObject["vehicleId"] 					=  $('#vehicleId').val();
			jsonObject["companyId"] 					=  $('#companyId').val();
			jsonObject["dispatchedByTime"] 				=  $('#closetripDate').val();
			jsonObject["dispatchTime"] 					=  $('#tripEndTime').val();
			jsonObject["fromTripSheetClose"] 			=  true;
			jsonObject["tripSheetId"] 					=  $('#tripSheetId').val();
			
			$.ajax({
		             url: "getVehicleGPSDataAtTime",
		             type: "POST",
		             dataType: 'json',
		             data: jsonObject,
		             success: function (data) {
		            	 if(data.VEHICLE_TOTAL_KM != undefined && data.VEHICLE_GPS_WORKING && data.VEHICLE_TOTAL_KM != null && data.isOdometerReading){
		            		 $('#tripGpsClosingKM').val(data.VEHICLE_TOTAL_KM);
		            		 	$('#gpsClosingKMRow').show();
		            	 }else{
		            		 if(data.isOdometerReading){
		            			 $('#gpsNotWorking').show();
		            		 }
		            		$('#tripGpsClosingKM').val(0);
		 					$('#manualClosingKm').show();
		 					$('#gpsClosingKMRow').hide();
		 					$('#tripGpsClosingKM').val('');
		 				}
		            	 hideLayer();
		             },
		             error: function (e) {
		            	 showMessage('errors', 'Some error occured!');
		            	
		             }
			});
		}
		
}
	
	if(!getNextTripDetails()){
		hideLayer();
		return false;
	}
}

