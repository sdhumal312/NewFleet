$(document).ready(function() {
	showLayer();
	var jsonObject					= new Object();
	$.ajax({
		url: "getAllPartyMaster",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			setAllPartyMaster(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
});
function setAllPartyMaster (data){
	$("#partyMasterTable").empty();
	$('#searchData').show();
	$('#countDiv').show();
	
	var thead 	= $('<thead>');
	var tbody 	= $('<tbody>');
	var tr1 	= $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th>');
	var th5		= $('<th>');
	var th6		= $('<th>');
	var th7		= $('<th>');
	var th8		= $('<th>');

	tr1.append(th1.append("No"));
	tr1.append(th2.append("Corporate Name"));
	tr1.append(th3.append("Address"));
	tr1.append(th4.append("Mobile Number"));
	tr1.append(th5.append("Alternate Mobile Number"));
	tr1.append(th6.append("Gst Number"));
	tr1.append(th7.append("Per KM Rate"));
	tr1.append(th8.append("Action"));

	thead.append(tr1);
	
	if(data.partMasterList != undefined && data.partMasterList.length > 0) {
		
		var partMasterList = data.partMasterList;
		$('#countId').text(partMasterList.length);
	
		for(var i = 0; i < partMasterList.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td>');
			var td5		= $('<td>');
			var td6		= $('<td>');
			var td7		= $('<td>');
			var td8		= $('<td>');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append(partMasterList[i].corporateName));
			tr1.append(td3.append(partMasterList[i].address));
			tr1.append(td4.append(partMasterList[i].mobileNumber));
			tr1.append(td5.append(partMasterList[i].alternateMobileNumber));
			tr1.append(td6.append(partMasterList[i].gstNumber));
			tr1.append(td7.append(partMasterList[i].perKMRate));
			tr1.append(td8.append(
			'<div class="btn-group">'
			+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
			+'<ul class="dropdown-menu pull-right">'
			+'<li><a href="#" class="confirmation" onclick="editPartyMaster('+partMasterList[i].corporateAccountId+')"><i class="fa fa-edit"></i> Edit</a></li>'
			+'<li><a href="#" class="confirmation" onclick="deletePartyMaster('+partMasterList[i].corporateAccountId+')"><span class="fa fa-trash"></span> Delete</a></li>'
			+'</ul>'
			+'</div>'
			));

			tbody.append(tr1);
			
		}
		
		$("#partyMasterTable").append(thead);
		$("#partyMasterTable").append(tbody);
	
	}else{
		showMessage('info','No record found !')
		return false;
	}
	
}

function showAddPartyMaster(){
	$("#corporateName").val('')
	$("#address").val('')
	$("#mobileNumber").val('')
	$("#alternateMobileNumber").val('')
	$("#gstNumber").val('')
	$("#perKMRate").val(0)
	$("#balanceAmount").val(0)
	$("#addPartyMaster").modal('show');
	
}

function savePartyMaster(){
	showLayer();
	var jsonObject							= new Object();

	jsonObject["corporateName"]				= $('#corporateName').val();
	jsonObject["address"]					= $('#address').val();
	jsonObject["mobileNumber"]				= $('#mobileNumber').val();
	jsonObject["alternateMobileNumber"]		= $('#alternateMobileNumber').val();
	jsonObject["gstNumber"]				= $('#gstNumber').val();
	jsonObject["perKMRate"]					= $('#perKMRate').val();
	
	if($('#corporateName').val().trim() == ''){
		$('#corporateName').focus();
		showMessage('info', 'Please Select Tyre Corporate Name !');
		hideLayer();
		return false;
	}
	if($('#mobileNumber').val().trim() == ''){
		$('#mobileNumber').focus();
		showMessage('info', 'Please Select Mobile Number !');
		hideLayer();
		return false;
	}
	
	$.ajax({
		url: "addPartyMaster",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			$('#addPartymaster').modal('hide');
			$('#corporateName').val('');
			$('#address').val('');
			$('#mobileNumber').val('');
			$('#alternateMobileNumber').val('');
			if(data.alreadyExist != undefined && data.alreadyExist == true){
				showMessage('info', 'Already Exist!');
				hideLayer();
				return false;
			}else{
				showMessage('success', 'New Tally Master Saved Successfully!');
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

function editPartyMaster(id){
	var jsonObject							= new Object();
	jsonObject["corporateAccountId"]		= id;
	
	$.ajax({
		url: "getPartyMasterByCorporateAccountId",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setPartyMasterByCorporateAccountId(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setPartyMasterByCorporateAccountId(data){
	var partyMaster 	= data.partyMaster;
	$('#editCorporateAccountId').val(partyMaster.corporateAccountId);
	$('#editCorporateName').val(partyMaster.corporateName);
	$('#editAddress').val(partyMaster.address);
	$('#editMobileNumber').val(partyMaster.mobileNumber);
	$('#editAlternateMobileNumber').val(partyMaster.alternateMobileNumber);
	$('#editGstNumber').val(partyMaster.gstNumber);
	$('#editPerKMRate').val(partyMaster.perKMRate);
	
	$('#editPartyMaster').modal('show');
}

function updatePartyMaster(){
	if($('#editCorporateAccountId').val() != undefined &&  $('#editCorporateAccountId').val() > 0){
		showLayer();
		var jsonObject								= new Object();
		
		jsonObject["editCorporateAccountId"]		= $('#editCorporateAccountId').val();
		jsonObject["editCorporateName"]				= $('#editCorporateName').val();
		jsonObject["editAddress"]					= $('#editAddress').val();
		jsonObject["editMobileNumber"]				= $('#editMobileNumber').val();
		jsonObject["editAlternateMobileNumber"]		= $('#editAlternateMobileNumber').val();
		jsonObject["editGstNumber"]					= $('#editGstNumber').val();
		jsonObject["editPerKMRate"]					= $('#editPerKMRate').val();
		
		if($('#editCorporateName').val().trim() == ''){
			$('#editCorporateName').focus();
			showMessage('info', 'Please Select Tyre Corporate Name !');
			hideLayer();
			return false;
		}
		if($('#editMobileNumber').val().trim() == ''){
			$('#editMobileNumber').focus();
			showMessage('info', 'Please Select Mobile Number !');
			hideLayer();
			return false;
		}
		if($('#editPerKMRate').val() == "" || $('#editPerKMRate').val() < 0){
			$('#editPerKMRate').focus();
			showMessage('info', 'Please KM Rate !');
			hideLayer();
			return false;
		}
		
		$.ajax({
			url: "updatePartyMaster",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				$('#editPartyMaster').modal('hide');
				$('#editCorporateName').val('');
				$('#editAddress').val('');
				$('#editMobileNumber').val('');
				$('#editAlternateMobileNumber').val('');
				$('#editGstNumber').val('');
				$('#editPerKMRate').val(0);
				
				if(data.alreadyExist != undefined && data.alreadyExist == true){
					showMessage('info', 'Already Exist!');
					hideLayer();
					return false;
				}else{
					showMessage('success', 'Update Party Master Successfully!');
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

function deletePartyMaster(id){
	
	if (confirm('Are you sure, you want to Delete this Party Master?')) {
	
		if(id != undefined && id > 0){
			
			var jsonObject					= new Object();
			
			jsonObject["corporateAccountId"]	= id;
			
			$.ajax({
				url: "deletePartyMaster",
				type: "POST",
				dataType: 'json',
				data: jsonObject,
				success: function (data) {
					showMessage('success', 'Delete Party Master!');
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

