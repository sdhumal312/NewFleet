$(document).ready(function() {
	showLayer();
	var jsonObject					= new Object();
	$.ajax({
		url: "getDriverAllBasicDetailsType",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			setDriverBasicDetailsTypeList(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
});
function setDriverBasicDetailsTypeList (data){
	
	$("#driverBasicDetailsTypeTable").empty();
	var DriverBasicDetailsTypeList = data.DriverBasicDetailsTypeList;
	
	if(DriverBasicDetailsTypeList != undefined || DriverBasicDetailsTypeList != null){
		for(var index = 0 ; index < DriverBasicDetailsTypeList.length; index++){
			
			var columnArray = new Array();
			columnArray.push("<td class='fit'>"+(index+1)+"</td>");
			columnArray.push("<td class='fit'ar> <h4> "+ DriverBasicDetailsTypeList[index].driverBasicDetailsTypeName  +"</td>");
			columnArray.push("<td class='fit ar'>" + DriverBasicDetailsTypeList[index].description +"</td>");
			columnArray.push("<td class='fit ar' style='vertical-align: middle;' ><a href='#' class='confirmation' onclick='editDriverBasicDetailType("+DriverBasicDetailsTypeList[index].driverBasicDetailsTypeId+");'><span class='fa fa-edit'></span> Edit</a>&nbsp;&nbsp;&nbsp<a href='#' class='confirmation' onclick='deleteDriverBasicDetailsType("+DriverBasicDetailsTypeList[index].driverBasicDetailsTypeId+");'><span class='fa fa-trash'></span> Delete</a></td>");
			
			$('#driverBasicDetailsTypeTable').append("<tr id='driverBasicDetailsId"+DriverBasicDetailsTypeList[index].driverBasicDetailsTypeId+"' >" + columnArray.join(' ') + "</tr>");
		}
		columnArray = [];
		}else{
			showMessage('info','No Record Found!')
		}
	}

$(document).ready(
		function($) {
			
			$('button[id=saveDriverBasicDetailsType]').click(function(e) {
				
				showLayer();
				var jsonObject								= new Object();
			
				jsonObject["driverBasicDetailsTypeName"]					= $('#addDriverBasicDetailsTypeName').val();
				jsonObject["description"]						= $('#addDescription').val();
				
				if($('#addDriverBasicDetailsTypeName').val().trim() == "" ||  $('#addDriverBasicDetailsTypeName').val() == undefined){
					$('#addDriverBasicDetailsTypeName').focus();
					showMessage('errors', 'Please Select Details Type Name !');
					hideLayer();
					return false;
				}
				if($('#addDescription').val() == "" ||  $('#addDescription').val() == undefined){
					$('#addDescription').focus();
					showMessage('errors', 'Please Enter Description !');
					hideLayer();
					return false;
				}
				
				$.ajax({
					url: "addDriverBasicDetailsType",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						$('#addBasicDetails').modal('hide');
						if(data.alreadyExist != undefined && data.alreadyExist == true){
							showMessage('info', 'Already Exist!');
							hideLayer();
							return false;
						}else{
							showMessage('success', 'Details Saved Successfully!');
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

function editDriverBasicDetailType(driverBasicDetailsTypeId){
	var jsonObject							= new Object();
	jsonObject["driverBasicDetailsTypeId"]		= driverBasicDetailsTypeId;
	console.log("driverBasicDetailsTypeId",driverBasicDetailsTypeId)
	
	$.ajax({
		url: "getDriverBasicDetailsType",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			console.log("dddd",data)
			setDriverBasicDetailsType(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setDriverBasicDetailsType(data){
	var driverBasicDetailsType 	= data.driverBasicDetailsType;
	$('#driverBasicDetailsTypeId').val(driverBasicDetailsType.driverBasicDetailsTypeId)
	$('#editDriverBasicDetailsTypeName').val(driverBasicDetailsType.driverBasicDetailsTypeName);
	$('#editDescription').val(driverBasicDetailsType.description);
	
	$('#editDriverBasicDetailsTypeModal').modal('show');
}


$(document).ready(
		function($) {
			$('button[id=updateDriverBasicDetailsType]').click(function(e) {
	
				showLayer();
				var jsonObject					= new Object();
				
				jsonObject["driverBasicDetailsTypeId"]		= $('#driverBasicDetailsTypeId').val();
				jsonObject["driverBasicDetailsTypeName"]	= $('#editDriverBasicDetailsTypeName').val();
				jsonObject["description"]						= $('#editDescription').val();
				
				
				if($('#editDriverBasicDetailsTypeName').val().trim() == "" ||  $('#editDriverBasicDetailsTypeName').val() == undefined){
					$('#editDriverBasicDetailsTypeName').focus();
					showMessage('errors', 'Please Enter Details Type !');
					hideLayer();
					return false;
				}
			
				
				$.ajax({
					url: "updateDriverBasicDetailsType",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						$('#editBasicDetails').modal('hide');
						if(data.alreadyExist != undefined && data.alreadyExist == true){
							showMessage('info', 'Already Exist!');
							hideLayer();
							return false;
						}else{
							showMessage('success', 'Update  Successfully!');
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

function deleteDriverBasicDetailsType(driverBasicDetailsTypeId){
	
	if (confirm('Are you sure, you want to Delete?')) {
	
		if(driverBasicDetailsTypeId != undefined && driverBasicDetailsTypeId > 0){
			
			var jsonObject							= new Object();
			
			jsonObject["driverBasicDetailsTypeId"]		= driverBasicDetailsTypeId;
			
			$.ajax({
				url: "deleteDriverBasicDetailsType",
				type: "POST",
				dataType: 'json',
				data: jsonObject,
				success: function (data) {
					if(data.alreadyExistInDriver == true || data.alreadyExistInDriver == "true"){
						showMessage('info','This Type Has Been Used In Driver ')
						return false;
						hideLayer();
					}else{
						showMessage('success', 'Deleted Successfully!');
						hideLayer();
						
					}
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



