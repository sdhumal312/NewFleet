$(document).ready(function() {
	showLayer();
	var jsonObject					= new Object();
	$.ajax({
		url: "getAllVehicleModel",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			setAllVehicleModel(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
});
function setAllVehicleModel (data){
	
	$("#vehicleModelTable").empty();
	$('#searchData').show();
	$('#countDiv').show();
	
	var vehicleModelList = data.allVehicleModel;
	
	if(vehicleModelList != undefined || vehicleModelList != null){
		for(var index = 0 ; index < vehicleModelList.length; index++){
			
			var columnArray = new Array();
			columnArray.push("<td class='fit'>"+(index+1)+"</td>");
			columnArray.push("<td class='fit'ar> <h4> "+ vehicleModelList[index].vehicleManufacturer  +"</td>");
			columnArray.push("<td class='fit'ar> <h4> "+ vehicleModelList[index].vehicleModelName  +"</td>");
			columnArray.push("<td class='fit ar'>" + vehicleModelList[index].description +"</td>");
			if($("#vehicleModelTyreLayoutConfig").val() != undefined && ($("#vehicleModelTyreLayoutConfig").val() == true || $("#vehicleModelTyreLayoutConfig").val() == 'true')){
				if(vehicleModelList[index].vehicleModelTyreLayoutId != undefined){
					columnArray.push("<td class='fit ar' style='vertical-align: middle;' ><a href='#' class='confirmation' onclick='editVehicleModel("+vehicleModelList[index].vehicleModelId+");'><span class='fa fa-edit'></span> Edit</a>&nbsp;&nbsp;&nbsp<a href='showVehicleModelTyreLayout.in?id="+vehicleModelList[index].vehicleModelId+"' class='confirmation'><span class='fa fa-car'></span> Show Layout</a></td>");
				}else{
					columnArray.push("<td class='fit ar' style='vertical-align: middle;' ><a href='#' class='confirmation' onclick='editVehicleModel("+vehicleModelList[index].vehicleModelId+");'><span class='fa fa-edit'></span> Edit</a>&nbsp;&nbsp;&nbsp<a href='#' onclick='deleteVehicleModel("+vehicleModelList[index].vehicleModelId+");'><span class='fa fa-trash'></span> Delete</a>&nbsp;&nbsp;&nbsp<a href='vehicleModelTyreLayout.in?id="+vehicleModelList[index].vehicleModelId+"' class='confirmation'><span class='fa fa-car'></span> Create Layout</a></td>");
				}
			}else{
				columnArray.push("<td class='fit ar' style='vertical-align: middle;' ><a href='#' class='confirmation' onclick='editVehicleModel("+vehicleModelList[index].vehicleModelId+");'><span class='fa fa-edit'></span> Edit</a>&nbsp;&nbsp;&nbsp<a href='#' onclick='deleteVehicleModel("+vehicleModelList[index].vehicleModelId+");'><span class='fa fa-trash'></span> Delete</a></td>");
			}
			
			
			$('#vehicleModelTable').append("<tr id='penaltyID"+vehicleModelList[index].vehicleModelId+"' >" + columnArray.join(' ') + "</tr>");
			
		}
		
		$("#vehicleModelCount").html(vehicleModelList.length);
		columnArray = [];
		}else{
			showMessage('info','No Record Found!')
		}
	}

$(document).ready(
		function($) {
			$('button[id=saveVehicleModel]').click(function(e) {
	
				showLayer();
				var jsonObject								= new Object();
			
				jsonObject["vehicleModelName"]				= $('#addVehicleModelName').val();
				jsonObject["description"]					= $('#addDescription').val();
				jsonObject["manufacturer"]					= $('#manufacturer').val();
				
				if(Number($('#manufacturer').val()) <= 0){
					$('#manufacturer').focus();
					showMessage('errors', 'Please Vehicle Manufacturer !');
					hideLayer();
					return false;
				}
				
				
				if($('#addVehicleModelName').val().trim() == ''){
					$('#addVehicleModelName').focus();
					showMessage('errors', 'Please Enter Vehicle Model Name !');
					hideLayer();
					return false;
				}
				
				$.ajax({
					url: "addVehicleModel",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						$('#vehicleModelTable').modal('hide');
						$('#addVehicleModelName').val('');
						$('#addDescription').val('');
						if(data.alreadyExist != undefined && data.alreadyExist == true){
							showMessage('info', 'Already Exist!');
							hideLayer();
							return false;
						}else{
							showMessage('success', 'New Vehicle Model Saved Successfully!');
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

function editVehicleModel(vehicleModelId){
	var jsonObject							= new Object();
	jsonObject["vehicleModelId"]		= vehicleModelId;
	
	$.ajax({
		url: "getVehicleModelById",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setVehicleModelById(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setVehicleModelById(data){
	var vehicleModel 	= data.vehicleModel;
	
	$('#editVehicleModelId').val(vehicleModel.vehicleModelId);
	$('#editVehicleModelName').val(vehicleModel.vehicleModelName);
	$('#editDescription').val(vehicleModel.description);
	$('#editManufacturer').val(vehicleModel.vehicleManufacturerId);
	
	$('#editVehicleModel').modal('show');
}

$(document).ready(
		function($) {
			$('button[id=updateVehicleModel]').click(function(e) {
	
			if($('#editVehicleModelId').val().trim() != undefined &&  $('#editVehicleModelId').val() > 0){
				showLayer();
				var jsonObject					= new Object();
				
				jsonObject["vehicleModelId"]			= $('#editVehicleModelId').val();
				jsonObject["vehicleModelName"]			= $('#editVehicleModelName').val();
				jsonObject["description"]				= $('#editDescription').val();
				jsonObject["manufacturer"]				= $('#editManufacturer').val();
				
				if(Number($('#editManufacturer').val()) <= 0){
					$('#manufacturer').focus();
					showMessage('errors', 'Please Vehicle Manufacturer !');
					hideLayer();
					return false;
				}
				
				
				if($('#editVehicleModelId').val().trim() == ''){
					$('#editVehicleModelId').focus();
					showMessage('errors', 'Please Enter Vehicle Model Name !');
					hideLayer();
					return false;editVehicleModelName
				}
				
				if($("#editVehicleModelName").val() == undefined || ($("#editVehicleModelName").val()).trim() == "" ){
					showMessage('info','Please Enter Vehicle Model Name')
					hideLayer();
					return false;
				}
				
				
				$.ajax({
					url: "updateVehicleModel",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						$('#editVehicleModel').modal('hide');
						$('#vehicleModelId').val('');
						$('#vehicleModelName').val('');
						$('#description').val('');
						if(data.alreadyExist != undefined && data.alreadyExist == true){
							showMessage('info', 'Already Exist!');
							hideLayer();
							return false;
						}else{
							showMessage('success', 'Update Vehicle Model Successfully!');
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

function deleteVehicleModel(vehicleModelId){
	
	if (confirm('Are you sure, you want to Delete this Model?')) {
	
		if(vehicleModelId != undefined && vehicleModelId > 0){
			
			var jsonObject							= new Object();
			
			jsonObject["vehicleModelId"]		= vehicleModelId;
			jsonObject["companyId"]		= $("#companyId").val();
			
			$.ajax({
				url: "deleteVehicleModel",
				type: "POST",
				dataType: 'json',
				data: jsonObject,
				success: function (data) {
					if(data.vehicleExist != undefined && (data.vehicleExist == true || data.vehicleExist == 'true')){
						showMessage('info', 'Found Vehicle With Same Model');
						return false;
					}else{
						showMessage('success', 'Deleted Vehicle Model Successfully!');
						location.reload();
					}
					
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

function validateVechicleModelEdit(){
	
	if($("#editVehicleModelName").val() == undefined || ($("#editVehicleModelName").val()).trim() == "" ){
		showMessage('info','Please Enter Vehicle Model Name')
		return false;
	}
}