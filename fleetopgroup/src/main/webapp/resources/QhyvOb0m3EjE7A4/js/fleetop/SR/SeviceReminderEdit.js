$(document).ready(function() {
	getServiceReminderData();	
	$("[id='firstService']").bootstrapSwitch({
		  onSwitchChange: function(e, state) {
			  setFirstServiceDetails(e, state);
		  }
		});
});
function getServiceReminderData(){
	
	var jsonObject	= new Object();
	jsonObject["service_id"] 	  	=  $('#service_id').val();
	jsonObject["companyId"] 	  	=  $('#companyId').val();
	jsonObject["userId"] 	  		=  $('#userId').val();
	
	showLayer();
	$.ajax({
             url: "getServiceReminderDetailsById",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 setServiceReminderEditData(data);
            	 hideLayer();
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});
}
function setServiceReminderEditData(data){
	if(data.serviceReminder != undefined){
		var serviceReminder = data.serviceReminder;
		$('#service_Number').val(serviceReminder.service_Number);
		$('#vid').select2('data', {
			id : serviceReminder.vid,
			text : serviceReminder.vehicle_registration
		});
		$("#vid").select2("readonly", true);
		
		$('#serviceType').val(serviceReminder.serviceType);
		$('#meter_interval').val(serviceReminder.meter_interval);
		$('#time_interval').val(serviceReminder.time_interval);
		$('#time_intervalperiod').val(serviceReminder.time_intervalperiodId);
		
		$('#dueMeterThreshold').val(serviceReminder.meter_threshold);
		$('#time_threshold').val(serviceReminder.time_threshold);
		$('#time_thresholdperiod').val(serviceReminder.time_thresholdperiodId);
		
		$('#subscribe').select2('data', {
			id : serviceReminder.service_subScribedUserId,
			text : serviceReminder.service_subscribeduser_name
		});
		
		if(serviceReminder.firstService){
			$('input[id="firstService"]').bootstrapSwitch('state', true, true);
			$('#first_meter_interval').val(serviceReminder.firstMeterInterval);
			$('#first_time_interval').val(serviceReminder.firstTimeInterval);
			$('#first_time_intervalperiod').val(serviceReminder.firstTimeIntervalType);
			$('#firstMeter').show();
			$('#firstTime').show();
			$('#firstTimeType').show();
		}else{
			$('input[id="firstService"]').bootstrapSwitch();
			$('#firstMeter').hide();
			$('#firstTime').hide();
			$('#firstTimeType').hide();
		
		}
		$('#from').select2('data', {
			id : serviceReminder.serviceTypeId,
			text : serviceReminder.service_type
		});
		
		$('#to').select2('data', {
			id : serviceReminder.serviceSubTypeId,
			text : serviceReminder.service_subtype
		});
		
		getJobSubTypeListById(serviceReminder.serviceTypeId, true);

	}
}
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

function updateServiceReminder(){
	
	if(!validateServiceReminder()){
		return false;
	}
	
	Swal.fire({
		  title: 'Are you sure to Update?',
		  text: "",
		  icon: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  customClass:'swal-wide',
		  confirmButtonText: 'Yes, Update it !'
		}).then((result) => {
		  if (result.value) {
			  var jsonObject						= new Object();
				jsonObject["companyId"]				= $('#companyId').val();
				jsonObject["userId"]				= $('#userId').val();
				jsonObject["service_id"]			= $('#service_id').val();
				jsonObject["service_Number"]		= $('#service_Number').val();
				jsonObject["vid"]					= $('#vid').val();
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
				
				jsonObject["firstService"]			= $('#firstService').is(":checked");
				if($('#firstService').is(":checked")){
					jsonObject["first_meter_interval"]		= $('#first_meter_interval').val();
					jsonObject["first_time_interval"]		= $('#first_time_interval').val();
					jsonObject["first_time_intervalperiod"]	= $('#first_time_intervalperiod').val();
				}
				
				showLayer();
				$.ajax({
					url: "updateServiceReminderDetails",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						if(data.duplicateCount != undefined && data.duplicateCount > 0 && (data.vehicleLength == data.duplicateCount)){
							showMessage('info', 'No Service Reminder Saved, Already Exits  ! ');
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
					}
				});
		  }
		})
	
	
}
