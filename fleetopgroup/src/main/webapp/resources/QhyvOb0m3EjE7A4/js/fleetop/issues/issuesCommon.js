var intangleTripDetailsList = new Array();
$(document).ready(function() {
	getVehicleHealthData();
});

function getVehicleHealthData() {
	showLayer();
	var jsonObject					= new Object();

	$.ajax({
		url: "getVehicleHealthData",
		dataType: 'json',
		type: "POST",
		contentType: "application/json",
		data: jsonObject,
		success: function (data) {
			hideLayer();
			setVehicleHealthCount(data);
			if(data.intangleTripDetailsList != undefined && data.intangleTripDetailsList != null ){
				intangleTripDetailsList = data.intangleTripDetailsList;
			}
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setVehicleHealthCount (data){
	var tr = '<tr>'
		+'<td class="fit"><button type="button" class="badge bg-red" onclick="setVehicleHealthData()" href="#" style="padding-top: 9px;">Health Count ('+data.tripDetailsCount+')</button></td>'
		+'</tr>';
	$('#vehicleCount').append(tr);
}
function setVehicleHealthData (){
	if(intangleTripDetailsList != undefined && intangleTripDetailsList != null ){
		$('#vehicleHealthDetals').empty();
		for(var i=0; i < intangleTripDetailsList.length; i++){
			var tr = '<tr>'
				+'<td class="fit">'+intangleTripDetailsList[i].plate+'</td>'
				+'<td class="fit">'+intangleTripDetailsList[i].last_update_Str+'</td>'
				+'<td class="fit">'+intangleTripDetailsList[i].health+'</td>';
				if($('#createIssueFromHealth').val()== true || $('#createIssueFromHealth').val()== 'true'){
					tr +='<td class="fit">'+'<a href="addIssuesDetails?ISSUES_VID='+intangleTripDetailsList[i].vid+'&healthStatus='+intangleTripDetailsList[i].health+'&ISSUES_VEHICLE_REGISTRATION='+intangleTripDetailsList[i].plate+'"">Create Issue </a>'+'</td>';
				}
				tr += '</tr>';
			$('#vehicleHealthDetals').append(tr);
		}
	}
	$('#vehicleHealthId').modal('show');
}

