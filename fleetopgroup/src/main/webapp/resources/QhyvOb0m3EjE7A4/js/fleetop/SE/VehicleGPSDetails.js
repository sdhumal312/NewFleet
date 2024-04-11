function getVehicleGPSDataAtTime(){
	var allowGPSIntegration	= $('#allowGPSIntegration').val();
	if(allowGPSIntegration == 'true' || allowGPSIntegration == true){
			showLayer();
			var jsonObject			= new Object();
			jsonObject["vehicleId"] 					=  $('#vehicle_vid').val();
			jsonObject["companyId"] 					=  $('#companyId').val();
		
			$.ajax({
		             url: "getVehicleGPSDetails",
		             type: "POST",
		             dataType: 'json',
		             data: jsonObject,
		             success: function (data) {
		            	 if(data.VEHICLE_TOTAL_KM != null){
		            		 $('#gpsOdometer').val(data.VEHICLE_TOTAL_KM);
		            		 $('#fuel_meter').val(parseInt(data.VEHICLE_TOTAL_KM));
		            		 $('#gpsWorking').val('true');
		            		//$('#grpfuelOdometer').hide();
		            	 }else{
		            		 $('#grpfuelOdometer').show();
		            		 $('#gpsOdometerRow').hide();
		            		 $('#gpsOdometer').val('');
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