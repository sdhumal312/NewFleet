$(document).ready(function() {
	showLayer();
	var jsonObject					= new Object();
	$.ajax({
		url: "getAllVehicleManufacturer",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			setVehicleManufacturer(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
});
function setVehicleManufacturer (data){
	$('#searchData').show();
	$('#countDiv').show();
	
	$("#vehicleManufacturerTable").empty();
	
	var vehicleManufacturerList = data.allVehicleManufacturer;
	if(vehicleManufacturerList != undefined || vehicleManufacturerList != null){
		for(var index = 0 ; index < vehicleManufacturerList.length; index++){
			
			var columnArray = new Array();
			columnArray.push("<td class='fit'>"+(index+1)+"</td>");
			columnArray.push("<td class='fit'ar> <h4> "+ vehicleManufacturerList[index].vehicleManufacturerName  +"</td>");
			columnArray.push("<td class='fit ar'>" + vehicleManufacturerList[index].description +"</td>");
			columnArray.push("<td class='fit ar' style='vertical-align: middle;' ><a href='#' class='confirmation' onclick='editVehicleManufacturer("+vehicleManufacturerList[index].vehicleManufacturerId+");'><span class='fa fa-edit'></span> Edit</a>&nbsp;&nbsp;&nbsp<a href='#' class='confirmation' onclick='deleteVehicleManufacturer("+vehicleManufacturerList[index].vehicleManufacturerId+");'><span class='fa fa-trash'></span> Delete</a></td>");
			
			$('#vehicleManufacturerTable').append("<tr id='penaltyID"+vehicleManufacturerList[index].vehicleManufacturerId+"' >" + columnArray.join(' ') + "</tr>");
			
		}
		
		$("#vehicleManufacturerCount").html(vehicleManufacturerList.length);
		columnArray = [];
		}else{
			showMessage('info','No Record Found!')
		}
	}

$(document).ready(
		function($) {
			$('button[id=saveVehicleManufacturer]').click(function(e) {
			
				showLayer();
				var jsonObject								= new Object();
			
				jsonObject["vehicleManufacturerName"]		= $('#addVehicleManufacturerName').val();
				jsonObject["description"]					= $('#addDescription').val();
				
				if($('#addVehicleManufacturerName').val().trim() == ''){
					$('#addVehicleManufacturerName').focus();
					showMessage('errors', 'Please Enter Vehicle Manufacturer Name !');
					hideLayer();
					return false;
				}
				
				$.ajax({
					url: "addVehicleManufacturer",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						$('#addVehicleManufacturer').modal('hide');
						$('#addVehicleManufacturerName').val('');
						$('#addDescription').val('');
						if(data.alreadyExist != undefined && data.alreadyExist == true){
							showMessage('info', 'Already Exist!');
							hideLayer();
							return false;
						}else{
							showMessage('success', 'New Vehicle Manufacturer Saved Successfully!');
							location.reload();
						}
						hideLayer();
					},
					error: function (e) {
						hideLayer();
						showMessage('errors', 'Some Error Occurred!');
					}
				});
			})
		});	

function editVehicleManufacturer(vehicleManufacturerId){
	var jsonObject							= new Object();
	jsonObject["vehicleManufacturerId"]		= vehicleManufacturerId;
	
	$.ajax({
		url: "getVehicleManufacturerById",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setVehicleManufacturerById(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setVehicleManufacturerById(data){
	var vehicleManufacturer 	= data.vehicleManufacturer;
	
	$('#editVehicleManufacturerId').val(vehicleManufacturer.vehicleManufacturerId);
	$('#editVehicleManufacturerName').val(vehicleManufacturer.vehicleManufacturerName);
	$('#editDescription').val(vehicleManufacturer.description);
	
	$('#editVehicleManufacturer').modal('show');
}

$(document).ready(
		function($) {
			$('button[id=updateVehicleManufacturer]').click(function(e) {
	
				if($('#editVehicleManufacturerId').val() != undefined &&  $('#editVehicleManufacturerId').val() > 0){
					showLayer();
					var jsonObject					= new Object();
					
					jsonObject["vehicleManufacturerId"]			= $('#editVehicleManufacturerId').val();
					jsonObject["vehicleManufacturerName"]		= $('#editVehicleManufacturerName').val();
					jsonObject["description"]					= $('#editDescription').val();
					
					if($('#editVehicleManufacturerName').val().trim() == ''){
						$('#editVehicleManufacturerName').focus();
						showMessage('errors', 'Please Enter Vehicle Manufacturer Name !');
						hideLayer();
						return false;
					}
					
					
					$.ajax({
						url: "updateVehicleManufacturer",
						type: "POST",
						dataType: 'json',
						data: jsonObject,
						success: function (data) {
							$('#editVehicleManufacturer').modal('hide');
							$('#editVehicleManufacturerId').val('');
							$('#editVehicleManufacturerName').val('');
							$('#editDescription').val('');
							if(data.alreadyExist != undefined && data.alreadyExist == true){
								showMessage('info', 'Already Exist!');
								hideLayer();
								return false;
							}else{
								showMessage('success', 'Update Vehicle Manufacturer Successfully!');
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
			})
		});	

function deleteVehicleManufacturer(vehicleManufacturerId){
	
	if (confirm('Are you sure, you want to Delete this Manufacturer?')) {
	
		if(vehicleManufacturerId != undefined && vehicleManufacturerId > 0){
			
			var jsonObject							= new Object();
			
			jsonObject["vehicleManufacturerId"]		= vehicleManufacturerId;
			
			$.ajax({
				url: "deleteVehicleManufacturer",
				type: "POST",
				dataType: 'json',
				data: jsonObject,
				success: function (data) {
					showMessage('success', 'Deleted Vehicle Manufacturer Successfully!');
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

