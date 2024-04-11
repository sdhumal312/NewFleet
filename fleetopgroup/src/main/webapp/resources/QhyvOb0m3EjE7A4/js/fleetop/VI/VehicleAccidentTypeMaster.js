$(document).ready(function() {
	showLayer();
	var jsonObject					= new Object();
	jsonObject["companyId"]			= $('#companyId').val();
	$.ajax({
		url: "getAllVehicleAccidentTypeMaster",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			setAllVehicleAccidentType(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
});
function setAllVehicleAccidentType (data){
	
	$("#vehicleAccidentTypeTable").empty();
	
	var vehicleAccidentTypeList = data.vehicleAccidentTypeList;
	
	if(vehicleAccidentTypeList != undefined || vehicleAccidentTypeList != null){
		for(var index = 0 ; index < vehicleAccidentTypeList.length; index++){
			
			var columnArray = new Array();
			columnArray.push("<td class='fit'>"+(index+1)+"</td>");
			columnArray.push("<td class='fit'ar'>"+ vehicleAccidentTypeList[index].vehicleAccidentTypeMasterName  +"</td>");
			columnArray.push("<td class='fit ar'>" + vehicleAccidentTypeList[index].description +"</td>");
			columnArray.push("<td class='fit ar' style='vertical-align: middle;' ><a href='#' class='confirmation' onclick='editVehicleAccidentType("+vehicleAccidentTypeList[index].vehicleAccidentTypeMasterId+");'><span class='fa fa-edit'></span> Edit</a>&nbsp;&nbsp;&nbsp<a href='#' onclick='deleteVehicleAccidentType("+vehicleAccidentTypeList[index].vehicleAccidentTypeMasterId+");'><span class='fa fa-trash'></span> Delete</a></td>");
			
			$('#vehicleAccidentTypeTable').append("<tr>" + columnArray.join(' ') + "</tr>");
			
		}
		
		$("#vehicleAccidentTypeCount").html(vehicleAccidentTypeList.length);
		columnArray = [];
		}else{
			showMessage('info','No Record Found!')
		}
	}

$(document).ready(
		function($) {
			$('button[id=saveVehicleAccidentType]').click(function(e) {
	
				showLayer();
				var jsonObject								= new Object();
			
				jsonObject["vehicleAccidentTypeMasterName"]		= $('#addVehicleAccidentTypeMasterName').val().trim();
				jsonObject["description"]					= $('#addDescription').val().trim();
				jsonObject["userId"]						= $('#userId').val();
				jsonObject["companyId"]						= $('#companyId').val();
				
				
				if($('#addVehicleAccidentTypeMasterName').val().trim() == ''){
					$('#addVehicleAccidentTypeMasterName').focus();
					showMessage('errors', 'Please Enter Vehicle Accident Type Name !');
					hideLayer();
					return false;
				}
				
				$.ajax({
					url: "saveVehicleAccidentTypeMaster",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						if(data.alreadyExist != undefined && data.alreadyExist == true){
							showMessage('info', 'Already Exist!');
							hideLayer();
							return false;
						}else{
							showMessage('success', 'Vehicle Accident Type Saved Successfully!');
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

function editVehicleAccidentType(vehicleAccidentTypeMasterId){
	var jsonObject							= new Object();
	jsonObject["vehicleAccidentTypeMasterId"]		= vehicleAccidentTypeMasterId;
	jsonObject["companyId"]						= $('#companyId').val();	
	$.ajax({
		url: "getVehicleAccidentTypeMasterById",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setVehicleAccidentType(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setVehicleAccidentType(data){
	console.log("data",data)
	var vehicleAccidentTypeMaster 	= data.VehicleAccidentTypeMaster;
	
	$('#editVehicleAccidentTypeMasterId').val(vehicleAccidentTypeMaster.vehicleAccidentTypeMasterId);
	$('#editVehicleAccidentTypeMasterName').val(vehicleAccidentTypeMaster.vehicleAccidentTypeMasterName);
	$('#editDescription').val(vehicleAccidentTypeMaster.description);
	
	$('#editVehicleAccidentTypeModal').modal('show');
}

$(document).ready(
		function($) {
			$('button[id=updateVehicleAccidentTypeMaster]').click(function(e) {
	
			if($('#editVehicleAccidentTypeMasterName').val().trim() != undefined ){
				showLayer();
				var jsonObject					= new Object();
				
				jsonObject["vehicleAccidentTypeMasterId"]			= $('#editVehicleAccidentTypeMasterId').val();
				jsonObject["vehicleAccidentTypeMasterName"]		= $('#editVehicleAccidentTypeMasterName').val();
				jsonObject["description"]					= $('#editDescription').val();
				jsonObject["companyId"]						= $('#companyId').val();
				
			
				
				if($('#editVehicleAccidentTypeMasterName').val().trim() == ''){
					$('#editVehicleAccidentTypeMasterName').focus();
					showMessage('errors', 'Please Enter Vehicle Accident Type Name !');
					hideLayer();
					return false;
				}
				
				$.ajax({
					url: "updateVehicleAccidentTypeMaster",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						if(data.alreadyExist != undefined && data.alreadyExist == true){
							showMessage('info', 'Already Exist!');
							hideLayer();
							return false;
						}else{
							showMessage('success', 'Update Vehicle Accident Type Successfully!');
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

function deleteVehicleAccidentType(vehicleAccidentTypeMasterId){
	
	if (confirm('Are you sure, you want to Delete this Accident Type?')) {
	
		if(vehicleAccidentTypeMasterId != undefined && vehicleAccidentTypeMasterId > 0){
			
			var jsonObject							= new Object();
			
			jsonObject["vehicleAccidentTypeMasterId"]		= vehicleAccidentTypeMasterId;
			jsonObject["companyId"]					= $('#companyId').val();			
			$.ajax({
				url: "deleteVehicleAccidentTypeMaster",
				type: "POST",
				dataType: 'json',
				data: jsonObject,
				success: function (data) {
					showMessage('success', 'Deleted Vehicle Accident Type Successfully!');
					location.reload();
					hideLayer();
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

