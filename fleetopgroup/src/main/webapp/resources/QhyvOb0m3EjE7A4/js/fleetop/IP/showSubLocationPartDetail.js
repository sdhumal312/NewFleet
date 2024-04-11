function showSubLocation(partName, partId){
	showLayer();
	var jsonObject					= new Object();
	jsonObject["partId"]			= partId;
	jsonObject["partName"]			= partName;
	jsonObject["mainLocationId"]	= $('#locationId').val();
	
	$.ajax({	
		url: "/getsubLocationPartDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			setsubLocationPartDetails(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setsubLocationPartDetails (data){
	
	$("#subLocationModelTable").empty();
	
	var subLocationPartDetails = data.subLocationPartDetails;
	
	if(subLocationPartDetails != undefined || subLocationPartDetails != null){
		for(var index = 0 ; index < subLocationPartDetails.length; index++){
			
			var columnArray = new Array();
			columnArray.push("<td class='fit'>"+(index+1)+"</td>");
			columnArray.push("<td class='fit'ar> <h4> "+ subLocationPartDetails[index].subLocation  +"</td>");
			columnArray.push("<td class='fit ar'>" + subLocationPartDetails[index].quantity +"</td>");
			
			$('#subLocationModelTable').append("<tr id='penaltyID"+subLocationPartDetails[index].inventory_id+"' >" + columnArray.join(' ') + "</tr>");
			
		}
		if($('#statues').val() != ""){
			$("#mainLocationName").html($('#statues').val());
		}else{
			$("#mainLocationName").html("--");
		}
		$("#partName").html(data.partName);
		columnArray = [];
		$("#showSubLocation").modal('show');
		
		}else{
			showMessage('info','No Record Found!')
		}
	}