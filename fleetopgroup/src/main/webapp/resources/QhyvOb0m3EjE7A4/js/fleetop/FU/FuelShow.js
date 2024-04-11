function addGPSUsageData(){
	showLayer();
	var jsonObject				= new Object();
	jsonObject["fuelId"]		= $('#fuelId').val();
	
	$.ajax({
		url: "updateFuelGPSUsage",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			location.reload();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}