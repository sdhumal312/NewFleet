var pageNumber = 1;
function showAllVehicleFuelReminderList (pageNumber) {
	showLayer();
	var jsonObject					= new Object();
	jsonObject["pageNumber"]			= pageNumber;

	$.ajax({
		url: "/FuelEntryAlertOfAllActiveVehicle",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setFuelEntryAlertOfAllActiveVehicle(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setFuelEntryAlertOfAllActiveVehicle(data) {
	$('#fuelAlertTable').empty();
	$('#noFuelTable').empty();
	
	var vehicleFinalList = data.vehicleFinalList;
	var noFuelList = data.noFuelList;
	
	if(vehicleFinalList != undefined || vehicleFinalList != null){
		for(var index = 0 ; index < vehicleFinalList.length; index++){
			
			var columnArray = new Array();
			columnArray.push("<td class='fit'>"+(index+1)+"</td>");
			columnArray.push("<td class='fit'ar'><a target='_blank' href='/showVehicle.in?vid="+vehicleFinalList[index].vid+"'>"+ vehicleFinalList[index].vehicle_registration +" </a></td>");
			columnArray.push("<td class='fit ar'><a target='_blank' href='/showFuel.in?FID="+vehicleFinalList[index].fuel_id+"'> F-" + vehicleFinalList[index].fuel_Number +"</a></td>");
			columnArray.push("<td class='fit ar'>" + vehicleFinalList[index].fuelDateStr +"</td>");
			columnArray.push("<td class='fit ar'>" + vehicleFinalList[index].fuelMeter +"</td>");
			columnArray.push("<td class='fit ar'>" + vehicleFinalList[index].vehicle_Odometer +"</td>");
			columnArray.push("<td class='fit ar'>" + vehicleFinalList[index].vehicle_ExpectedOdameter +"</td>");
			
			$('#fuelAlertTable').append("<tr id='penaltyID"+vehicleFinalList[index].vid+"' >" + columnArray.join(' ') + "</tr>");
		}
	columnArray = [];
	}else{
	showMessage('info','No record found !')
}
	
	if(noFuelList != undefined || noFuelList != null){
		console.log("length",noFuelList.length)
		$('#noFuelEntry').removeClass('hide');
		for(var index = 0 ; index < noFuelList.length; index++){
			
			var columnArray = new Array();
			columnArray.push("<td class='fit'>"+(index+1)+"</td>");
			columnArray.push("<td class='fit'ar'><a target='_blank' href='/showVehicle.in?vid="+noFuelList[index].vid+"'>"+ noFuelList[index].vehicle_registration +" </a></td>");
			columnArray.push("<td class='fit ar'>" + noFuelList[index].vehicle_Odometer +"</td>");
			columnArray.push("<td class='fit ar'>" + noFuelList[index].vehicle_ExpectedOdameter +"</td>");
			
			$('#noFuelTable').append("<tr id='penaltyID"+noFuelList[index].vid+"' >" + columnArray.join(' ') + "</tr>");
		}
		columnArray = [];
	}
	
	
	$("#fuelAlertModal").modal('show');
	$("#navigationBar").empty();

	
}