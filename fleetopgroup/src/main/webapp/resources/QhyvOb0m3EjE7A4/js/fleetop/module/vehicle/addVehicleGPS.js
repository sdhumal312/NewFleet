$(document).ready(function() {
	getVehicleGPSCredentialList();
});


function getVehicleGPSCredentialList() {
	
	showLayer();
	var jsonObject					= new Object();

	$.ajax({
		url: "getVehicleGPSCredentialList",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			setVehicleGPSCredentialList(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}


function setVehicleGPSCredentialList(data) {
	$("#VendorPaymentTable").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th>');
	var th5		= $('<th>');
	var th6		= $('<th>');

	tr1.append(th1.append("No"));
	tr1.append(th2.append("UserName"));
	tr1.append(th3.append("Password"));
	tr1.append(th4.append("Asigned Vehicle"));
	tr1.append(th5.append("Description"));
	tr1.append(th6.append("Action"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.credentialList != undefined && data.credentialList.length > 0) {
		$("#totalToolBox").html(data.credentialList.length);
		var credentialList = data.credentialList;
		for(var i = 0; i < credentialList.length; i++) {
			var count = 0;
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td>');
			var td5		= $('<td>');
			var td6		= $('<td>');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append(credentialList[i].userName));
			tr1.append(td3.append(credentialList[i].password));
			if(data.gpsCount != undefined){
				if(data.gpsCount[credentialList[i].vehicleGPSCredentialId] != undefined){
					count	= data.gpsCount[credentialList[i].vehicleGPSCredentialId];
				}
				tr1.append(td4.append('<a href="#">'+count+'</a>'));
			}else{
				tr1.append(count);
			}
			tr1.append(td5.append(credentialList[i].description));
					
			
			tr1.append(td6.append(
			'<div class="btn-group">'
			+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
			+'<ul class="dropdown-menu pull-right">'
			+'<li><a href="#" class="confirmation" onclick="getVehicleGPSCredentialById('+credentialList[i].vehicleGPSCredentialId+')"><i class="fa fa-edit"></i> Edit</a></li>'
			+'<li><a href="#" class="confirmation" onclick="deleteVehicleGPSCredential('+credentialList[i].vehicleGPSCredentialId+', '+count+')"><span class="fa fa-trash"></span> Delete</a></li>'
			+'</ul>'
			+'</div>'
			));

			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#VendorPaymentTable").append(thead);
	$("#VendorPaymentTable").append(tbody);

}

function saveVehicleGPSCredential(){
	
	if($('#userName').val().trim() == ''){
		$('#userName').focus();
		showMessage('errors', 'Please Enter UserName !');
		return false;
	}
	
	if($('#password').val().trim() == ''){
		$('#password').focus();
		showMessage('errors', 'Please Enter Password !');
		return false;
	}
	

	showLayer();
	var jsonObject					= new Object();

	jsonObject["userName"]						= $('#userName').val().trim();
	jsonObject["password"]						= $('#password').val().trim();
	jsonObject["description"]					= $('#description').val().trim();
	
	
	$.ajax({
		url: "saveVehicleGPSCredential",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.alreadyExist != undefined && data.alreadyExist == true){
				showMessage('info', 'Already Exist!');
				hideLayer();
				return false;
			}else{
				$('#userName').val('');
				 $('#password').val('');
				$('#description').val('');
				getVehicleGPSCredentialList();
				$('#addToolBox').modal('hide');
				showMessage('success', 'Data saved successfuelly !')
			}
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function getVehicleGPSCredentialById(vehicleGPSCredentialId){
	var jsonObject						= new Object();
	
	showLayer();
	
	jsonObject["vehicleGPSCredentialId"]		= vehicleGPSCredentialId;
	
	$.ajax({
		url: "getVehicleGPSCredentialById.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			$('#editVehicleTollDetailsId').val(data.vehicleGPSCredential.vehicleGPSCredentialId);
			$('#editUserName').val(data.vehicleGPSCredential.userName);
			$('#editPassword').val(data.vehicleGPSCredential.password);
			$('#editDescription').val(data.vehicleGPSCredential.description);
			$('#editVehicleTollDetails').modal('show');
			hideLayer();
		},
		error: function (e) {
			console.log("Error : " , e);
			hideLayer();
		}
	});

}

function updateVehicleGPSCredential(){
	showLayer();
	var jsonObject							= new Object();

	jsonObject["vehicleGPSCredentialId"]	= $('#editVehicleTollDetailsId').val();
	jsonObject["userName"]					= $('#editUserName').val();
	jsonObject["password"]					= $('#editPassword').val();
	jsonObject["description"]				= $('#editDescription').val();
	
	if($('#editUserName').val().trim() == ''){
		$('#editUserName').focus();
		showMessage('errors', 'Please Enter UserName !');
		hideLayer();
		return false;
	}
	
	if($('#editPassword').val().trim() == ''){
		$('#editPassword').focus();
		showMessage('errors', 'Please Enter Password !');
		hideLayer();
		return false;
	}
	
	$.ajax({
		url: "editVehicleGPSCredential.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.alreadyExist != undefined && data.alreadyExist == true){
				showMessage('info', 'Already Exist!');
				hideLayer();
				return false;
			}else{
				getVehicleGPSCredentialList();
				$('#editVehicleTollDetails').modal('hide');
				showMessage('success', 'Data Updated Successfully!');
			}
		},
		error: function (e) {
			console.log("Error : " , e);
			hideLayer();
		}
	});

}


function deleteVehicleGPSCredential(vehicleGPSCredentialId, count) {
	
	if(count > 0){
		showMessage('info', 'You Can Not Delete ! Please Remove From All Vehicle First !');
		return false;
	}
	
	if (confirm('Aru You Sure, Do You Want to delete?')) {
		showLayer();
		var jsonObject					= new Object();

		jsonObject["vehicleGPSCredentialId"]	= vehicleGPSCredentialId;
		
		$.ajax({
			url: "deleteVehicleGPSCredentialById.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
			if(data.deleted != undefined && (data.deleted == 'true' || data.deleted == true )){
				showMessage('success', 'Deleted Sucessfully!');
				window.location.replace("addVehicleGps");
				hideLayer();
			}
				
			},
			error: function (e) {
				console.log("Error : " , e);
				hideLayer();
			}
		});
	} 
	
}
