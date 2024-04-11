function createServiceReminder(){
	$('#saveReminder').hide();
	if(!validateServiceReminder()){
		$('#saveReminder').show();
		return false;
	}
	
	var jsonObject						= new Object();
	jsonObject["companyId"]				= $('#companyId').val();
	jsonObject["userId"]				= $('#userId').val();
	jsonObject["vids"]					= $('#ServiceSelectVehicle').val();
	jsonObject["serviceTypeId"]			= $('#from').val();
	jsonObject["serviceSubTypeId"]		= $('#to').val();
	jsonObject["meter_interval"]		= $('#meter_interval').val();
	jsonObject["time_interval"]			= $('#time_interval').val();
	jsonObject["timeIntervalType"]		= $('#time_intervalperiod').val();
	jsonObject["serviceType"]			= $('#serviceType').val();
	jsonObject["dueMeterThreshold"]		= $('#dueMeterThreshold').val();
	jsonObject["time_threshold"]		= $('#time_threshold').val();
	jsonObject["time_thresholdType"]	= $('#time_thresholdperiod').val();
	jsonObject["subscribe"]				= $('#subscribe').val();
	jsonObject["validateDoublePost"] 	 	 =  true;
	jsonObject["unique-one-time-token"] 	 =  $('#accessToken').val();
	jsonObject["firstService"]			= $('#firstService').is(":checked");
	if($('#firstService').is(":checked")){
		jsonObject["first_meter_interval"]		= $('#first_meter_interval').val();
		jsonObject["first_time_interval"]		= $('#first_time_interval').val();
		jsonObject["first_time_intervalperiod"]	= $('#first_time_intervalperiod').val();
	}
	
	showLayer();
	$.ajax({
		url: "saveServiceReminderDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.duplicateCount != undefined && data.duplicateCount > 0 && (data.vehicleLength == data.duplicateCount)){
				$('#accessToken').val(data.accessToken);
				showMessage('info', 'No Service Reminder Saved, Already Exits  ! ');
				$('#saveReminder').show();
				hideLayer();
			}else if(data.duplicateCount != undefined && data.duplicateCount > 0 && (data.vehicleLength > data.duplicateCount)){
				showMessage('success', 'Data saved successfully !, Saved Count : '+(data.vehicleLength - data.duplicateCount)+' Duplicate Count : '+data.duplicateCount+' ');
				setTimeout(function(){ 
					location.replace('ViewServiceReminderList.in');
				}, 2000);

			}else{
				showMessage('success', 'All Data saved successfully !');
				setTimeout(function(){ 
					location.replace('ViewServiceReminderList.in');
				}, 2000);

			}
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
			$('#saveReminder').show();
		}
	});
}
	$(document).ready(function() {
	$("[id='firstService']").bootstrapSwitch({
		  onSwitchChange: function(e, state) {
			  setFirstServiceDetails(e, state);
		  }
		});
	});
function setFirstServiceDetails(e, state){
	if(state){
		$('#firstMeter').show();
		$('#firstTime').show();
		$('#firstTimeType').show();
	}else{
		$('#firstMeter').hide();
		$('#firstTime').hide();
		$('#firstTimeType').hide();
	}
}