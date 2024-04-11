$(document).ready(function() {
	getToolBoxDetails();
});


function getToolBoxDetails() {
	
	showLayer();
	var jsonObject					= new Object();

	$.ajax({
		url: "getVehicleTollList",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			setVehicleTollList(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}


function setVehicleTollList(data) {
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
	tr1.append(th2.append("Customer Id"));
	tr1.append(th3.append("Wallet Id"));
	tr1.append(th4.append("Description"));
	tr1.append(th5.append("Asigned Vehicle"));
	tr1.append(th6.append("Action"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.tollList != undefined && data.tollList.length > 0) {
		$("#totalToolBox").html(data.tollList.length);
		var tollList = data.tollList;
		
		for(var i = 0; i < tollList.length; i++) {
			var count = 0;
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td>');
			var td5		= $('<td>');
			var td6		= $('<td>');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append('<a href="#">'+tollList[i].customerId+'</a>'));
			if(tollList[i].walletId == null){
				tr1.append(td3.append('<a href="#"></a>'));
			}else{
				tr1.append(td3.append('<a href="#">'+tollList[i].walletId+'</a>'));
			}
			tr1.append(td4.append(tollList[i].description));
			
			
			if(data.countList != undefined){
				if(data.countList[tollList[i].vehicleTollDetailsId] != undefined){
					count	= data.countList[tollList[i].vehicleTollDetailsId];
				}
				tr1.append(td5.append('<a href="#">'+count+'</a>'));
			}else{
				tr1.append(count);
			}
					
			
			tr1.append(td6.append(
			'<div class="btn-group">'
			+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
			+'<ul class="dropdown-menu pull-right">'
			+'<li><a href="#" class="confirmation" onclick="getVehicleTollById('+tollList[i].vehicleTollDetailsId+')"><i class="fa fa-edit"></i> Edit</a></li>'
			+'<li><a href="#" class="confirmation" onclick="return deleteVehicleToll('+tollList[i].vehicleTollDetailsId+', '+count+')"><span class="fa fa-trash"></span> Delete</a></li>'
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

function saveTollDetails(){
	
	showLayer();
	var jsonObject					= new Object();

	jsonObject["customerId"]						= $('#customerId').val();
	jsonObject["walletId"]							= $('#walletId').val();
	jsonObject["description"]						= $('#description').val();
	
	if($('#customerId').val().trim() == ''){
		$('#customerId').focus();
		showMessage('errors', 'Please Enter CustomerId !');
		hideLayer();
		return false;
	}
	if($('#walletId').val().trim() == ''){
		$('#walletId').focus();
		showMessage('errors', 'Please Enter Wallet Id !');
		hideLayer();
		return false;
	}
	

	$.ajax({
		url: "saveVehicleToll",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.alreadyExist != undefined && data.alreadyExist == true){
				showMessage('info', 'Already Exist!');
				hideLayer();
				return false;
			}else{
				 $('#customerId').val('');
				$('#walletId').val('');
				$('#description').val('');
				getToolBoxDetails();
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

function getVehicleTollById(tollId){
	var jsonObject						= new Object();
	
	showLayer();
	
	jsonObject["vehicleTollDetailsId"]		= tollId;
	
	$.ajax({
		url: "getVehicleTollById.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			$('#editVehicleTollDetailsId').val(data.vehicleToll.vehicleTollDetailsId);
			$('#editCustomerId').val(data.vehicleToll.customerId);
			$('#editWalletId').val(data.vehicleToll.walletId);
			$('#editDescription').val(data.vehicleToll.description);
			$('#editVehicleTollDetails').modal('show');
			hideLayer();
		},
		error: function (e) {
			console.log("Error : " , e);
			hideLayer();
		}
	});

}

function updateVehicleToll(){
	showLayer();
	var jsonObject							= new Object();

	jsonObject["vehicleTollDetailsId"]	= $('#editVehicleTollDetailsId').val();
	jsonObject["customerId"]			= $('#editCustomerId').val();
	jsonObject["walletId"]				= $('#editWalletId').val();
	jsonObject["description"]			= $('#editDescription').val();
	
	if($('#editCustomerId').val().trim() == ''){
		$('#editCustomerId').focus();
		showMessage('errors', 'Please Enter CustomerId !');
		hideLayer();
		return false;
	}
	
	$.ajax({
		url: "editVehicleToll.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.alreadyExist != undefined && data.alreadyExist == true){
				showMessage('info', 'Already Exist!');
				hideLayer();
				return false;
			}else{
				getToolBoxDetails();
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


function deleteVehicleToll(vehicleTollDetailsId, count) {
	
	if(count > 0){
		showMessage('info', 'You Can Not Delete ! Please Remove From All Vehicle First !');
		return false;
	}
	
	if (confirm('Aru You Sure, Do You Want to delete Customer Id?')) {
		showLayer();
		var jsonObject					= new Object();

		jsonObject["vehicleTollDetailsId"]	= vehicleTollDetailsId;
		
		$.ajax({
			url: "deleteVehicleToll.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
			if(data.deleted != undefined && (data.deleted == 'true' || data.deleted == true )){
				showMessage('success', 'Deleted Sucessfully!');
				window.location.replace("addVehicleToll");
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
