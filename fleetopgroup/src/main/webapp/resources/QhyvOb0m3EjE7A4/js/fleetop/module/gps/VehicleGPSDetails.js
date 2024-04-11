$(document).ready(function() {
	var allowGPSIntegration	= $('#allowGPSIntegration').val();
		if(allowGPSIntegration == 'true' || allowGPSIntegration == true){
			showLayer();
			var jsonObject			= new Object();
			jsonObject["vehicle_Id"] 					=  $('#vehicleGPSId').val();
			jsonObject["vehicleNumber"] 				=  $('#vehicleNumber').val();
			jsonObject["vehicleId"] 					=  $('#vid').val();
			jsonObject["companyId"] 					=  $('#companyId').val();
			
			$.ajax({
		             url: "getVehicleGPSDetails",
		             type: "POST",
		             dataType: 'json',
		             data: jsonObject,
		             success: function (data) {
		            	 if(data.VEHICLE_TOTAL_KM != null){
		            		 $('#gpsOddometer').html(data.VEHICLE_TOTAL_KM);
		            		 $('#gpsOddometerRow').show();
		            		 $('#gpsLocationRow').show();
		            		 var mapUrl = 'https://www.google.com/maps/search/?api=1&query='+data.VEHICLE_LATITUDE+','+data.VEHICLE_LONGITUDE+'';
		            		 $('#gpsLocationTag').append('<a target="_blank" href='+mapUrl+'>'+data.GPS_LOCATION+'</a>');
		            	 }
		            	 
		            	 hideLayer();
		               
		             },
		             error: function (e) {
		            	// showMessage('errors', 'Some error occured!');
		            	 hideLayer();
		             }
			});
			
		}
		
});

