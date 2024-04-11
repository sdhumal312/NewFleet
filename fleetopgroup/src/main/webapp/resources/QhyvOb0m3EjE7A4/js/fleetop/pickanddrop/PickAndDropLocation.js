$(document).ready(function() {
	showLayer();
	var jsonObject					= new Object();
	$.ajax({
		url: "getAllPickAndDropLocation",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			console.log("data",data)
			setAllPickAndDropLocation(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
});


function setAllPickAndDropLocation (data){
	
	$("#locationTable").empty();
	var location = data.locationList ;
	console.log("location",location.length)
	if( location != undefined || location.length > 0  ){
	
		for(var index = 0 ; index < location.length; index++){
	
		var columnArray = new Array();
		columnArray.push("<td class='fit ar' style='vertical-align: middle;'>"+(index+1)+"</td>");
		columnArray.push("<td class='fit ar' style='vertical-align: middle;'>" + location[index].locationName +"</td>");
		columnArray.push("<td class='fit ar' style='vertical-align: middle;'>" + location[index].description + "</td>");
		columnArray.push("<td class='fit ar' style='vertical-align: middle;' ><a href='#' class='confirmation' onclick='editPickAndDropLocation("+location[index].pickAndDropLocationId+");'><span class='fa fa-edit'></span> Edit</a>&nbsp;&nbsp;&nbsp<a href='#' class='confirmation' onclick='deletePickAndDropLocation("+location[index].pickAndDropLocationId+");'><span class='fa fa-trash'></span> Delete</a></td>");
		$('#locationTable').append("<tr id='penaltyID"+location[index].pickAndDropLocationId+"' >" + columnArray.join(' ') + "</tr>");
		}
		columnArray = [];
	}else{
		showMessage('info','No Record Found!')
	}

	}

function saveLocation(){
	showLayer();
	var jsonObject							= new Object();

	jsonObject["locationName"]				= $('#addLocationName').val();
	jsonObject["description"]			= $('#addDescription').val();
	
	if($('#addLocationName').val().trim() == ''){
		$('#addLocationName').focus();
		showMessage('errors', 'Please Enter location Name !');
		hideLayer();
		return false;
	}
	
	$.ajax({
		url: "savePickAndDropLocation",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			$('#addLocation').modal('hide');
			$('#addLocationName').val('');
			$('#addDescription').val('');
			if(data.alreadyExist != undefined && data.alreadyExist == true){
				showMessage('info', 'Already Exist!');
				hideLayer();
				return false;
			}else{
				showMessage('success', 'New Location Name Saved Successfully!');
				location.reload();
			}
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function editPickAndDropLocation(pickAndDropLocationId){
	var jsonObject					= new Object();
	jsonObject["pickAndDropLocationId"]		= pickAndDropLocationId;
	
	$.ajax({
		url: "getPickAndDropLocationById",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setPickAndDropLocationById(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setPickAndDropLocationById(data){
	var location 	= data.location;
	
	$('#editLocationName').val(location.locationName);
	$('#editDescription').val(location.description);
	$('#pickAndDropLocationId').val(location.pickAndDropLocationId);
	
	$('#editLocation').modal('show');
}
function updatePickAndDropLocation(){
	if($('#pickAndDropLocationId').val() != undefined &&  $('#pickAndDropLocationId').val() > 0){
		showLayer();
		var jsonObject					= new Object();
		
		jsonObject["pickAndDropLocationId"]				= $('#pickAndDropLocationId').val();
		jsonObject["editLocationName"]					= $('#editLocationName').val();
		jsonObject["editDescription"]					= $('#editDescription').val();
		
		if($('#editLocationName').val().trim() == ''){
			$('#editLocationName').focus();
			showMessage('errors', 'Please Enter Location Name !');
			hideLayer();
			return false;
		}
		
		
		$.ajax({
			url: "updatePickAndDropLocation",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				$('#editLocation').modal('hide');
				$('#editLocationName').val('');
				$('#editDescription').val('');
				if(data.alreadyExist != undefined && data.alreadyExist == true){
					showMessage('info', 'Already Exist!');
					hideLayer();
					return false;
				}else{
					showMessage('success', 'Update Location Name Successfully!');
					location.reload();
				}
				hideLayer();
			},
			error: function (e) {
				hideLayer();
				showMessage('errors', 'Some Error Occurred!');
			}
		});
	}
}

function deletePickAndDropLocation(pickAndDropLocationId){
	
	if (confirm('Are you sure, you want to Delete this ?')) {
	
		if(pickAndDropLocationId != undefined && pickAndDropLocationId > 0){
			
			var jsonObject					= new Object();
			
			jsonObject["pickAndDropLocationId"]					= pickAndDropLocationId;
			
			$.ajax({
				url: "deletePickAndDropLocation",
				type: "POST",
				dataType: 'json',
				data: jsonObject,
				success: function (data) {
					showMessage('success', 'Deleted location Name Successfully!');
					hideLayer();
					location.reload();
					return false;
					
				},
				error: function (e) {
					hideLayer();
					showMessage('errors', 'Some Error Occurred!');
				}
			});
		}
	  
	}else {
		location.reload();
	}
}

