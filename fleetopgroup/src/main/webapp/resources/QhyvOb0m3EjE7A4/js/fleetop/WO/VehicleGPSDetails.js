function getVehicleGPSDataAtTime(){
	var allowGPSIntegration	= $('#allowGPSIntegration').val();
	if(allowGPSIntegration == 'true' || allowGPSIntegration == true){
		if($('#woStartDate').val() != '' && $('#woStartTime').val() != undefined && $('#woStartTime').val() != ''){
			if(($('#select3').val() != undefined && $('#select3').val() != '') ||  ($('#vid').val() != undefined && $('#vid').val() != '')){
				
				showLayer();
				var jsonObject			= new Object();
				if($('#select3').val() != undefined && $('#select3').val() != ''){
					jsonObject["vehicleId"] 					=  $('#select3').val();
				}
				if($('#vid').val() != undefined && $('#vid').val() != ''){
					jsonObject["vehicleId"] 					=  $('#vid').val();
				}
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
							$('#gpsOdometer').val(data.VEHICLE_TOTAL_KM);
							$('#vehicle_Odometer').val(parseInt(data.VEHICLE_TOTAL_KM));
							$('#gpsOdometerRow').show();
							//$('#grpwoOdometer').hide();
						}else{
							$('#grpwoOdometer').show();
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
		
}
	setStartDateOfWOEndDate();
}

function setStartDateOfWOEndDate(){
	$("#woEndDate").datepicker({
		 autoclose: !0,
		startDate:$('#woStartDate').val()
	});
}