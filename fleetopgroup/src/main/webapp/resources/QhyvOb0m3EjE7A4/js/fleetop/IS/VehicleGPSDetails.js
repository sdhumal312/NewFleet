function getVehicleGPSDataAtTime(){
	var allowGPSIntegration	= $('#allowGPSIntegration').val();
	if(allowGPSIntegration == 'true' || allowGPSIntegration == true){
		if($('#woStartDate').val() != '' && $('#woStartTime').val() != '' && $('#IssuesSelectVehicle').val() != ''){
			showLayer();
			var jsonObject			= new Object();
			
			jsonObject["vehicleId"] 					=  $('#IssuesSelectVehicle').val();
			jsonObject["companyId"] 					=  $('#companyId').val();
			jsonObject["dispatchedByTime"] 				=  $('#woStartDate').val();
			jsonObject["dispatchTime"] 					=  $('#woStartTime').val();
			$.ajax({
		             url: "getVehicleGPSDataAtTime",
		             type: "POST",
		             dataType: 'json',
		             data: jsonObject,
		             success: function (data) {
		            	 if(data.VEHICLE_TOTAL_KM != null){
		            		 $('#GPS_ODOMETER').val(data.VEHICLE_TOTAL_KM);
		            		 $('#Issues_Odometer').val(parseInt(data.VEHICLE_TOTAL_KM));
		            		 $('#gpsOdometerRow').show();
		            		// $('#VehicleOdoRow').hide();
		            	 }else{
		            		 $('#VehicleOdoRow').show();
		            		 $('#gpsOdometerRow').hide();
		            		 $('#GPS_ODOMETER').val('');
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

}