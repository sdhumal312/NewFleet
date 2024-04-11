$(document).ready(function() {
	getRenewalSubTypeList();
});

function getRenewalSubTypeList() {
	
	showLayer();
	var jsonObject					= new Object();

	$.ajax({
		url: "RenewalReminderWS/getRenewalSubTypeList",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			if(data.ManadatorySubRenewal != undefined && data.ManadatorySubRenewal != null){
				setMandatoryList(data);
			}
			
			if(data.NonManadatorySubRenewal != undefined && data.NonManadatorySubRenewal != null){
				setNonMandatoryList(data);
			}
			
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setMandatoryList(data) {
	$("#VendorPaymentTable").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th>');
	var th4		= $('<th>');

	tr1.append(th1.append("No"));
	tr1.append(th2.append("Renewal Type"));
	tr1.append(th3.append("Renewal Sub Type"));
	//tr1.append(th4.append("Action"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.ManadatorySubRenewal != undefined && data.ManadatorySubRenewal.length > 0) {
		
		var ManadatorySubRenewal = data.ManadatorySubRenewal;
	
		for(var i = 0; i < ManadatorySubRenewal.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td>');
			var td4		= $('<td>');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append(ManadatorySubRenewal[i].renewal_Type));
			tr1.append(td3.append(ManadatorySubRenewal[i].renewal_SubType));
		/*	var action='<div class="btn-group">'
			+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
			+'<ul class="dropdown-menu pull-right">';
			if($('#editMandatorySubRenewal').val() == true || $('#editMandatorySubRenewal').val() == 'true' ){
				action+='<li><a href="#" class="confirmation" onclick="getRenewalSubTypeById('+ManadatorySubRenewal[i].renewal_Subid+')"><i class="fa fa-edit"></i> Edit</a></li>';
			}
			action+='<li><a href="#" class="confirmation" onclick="deleteRenewalSubTypeById('+ManadatorySubRenewal[i].renewal_Subid+')"><span class="fa fa-trash"></span> Delete</a></li>'
			+'</ul>'
			+'</div>';
			if(ManadatorySubRenewal[i].companyId != 0){
				tr1.append(td4.append(action));
			}else{
				tr1.append(td4.append(''));
			}  */
			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#VendorPaymentTable").append(thead);
	$("#VendorPaymentTable").append(tbody);

}

function setNonMandatoryList(data) {
	$("#VendorPaymentTable1").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th>');
	var th4		= $('<th>');

	tr1.append(th1.append("No"));
	tr1.append(th2.append("Renewal Type"));
	tr1.append(th3.append("Renewal Sub Type"));
	tr1.append(th4.append("Action"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.NonManadatorySubRenewal != undefined && data.NonManadatorySubRenewal.length > 0) {
		
		var NonManadatorySubRenewal = data.NonManadatorySubRenewal;
	
		for(var i = 0; i < NonManadatorySubRenewal.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td>');
			var td4		= $('<td>');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append(NonManadatorySubRenewal[i].renewal_Type));
			tr1.append(td3.append(NonManadatorySubRenewal[i].renewal_SubType));
			
			tr1.append(td4.append(
			'<div class="btn-group">'
			+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
			+'<ul class="dropdown-menu pull-right">'
			+'<li><a href="#" class="confirmation" onclick="getRenewalSubTypeById('+NonManadatorySubRenewal[i].renewal_Subid+')"><i class="fa fa-edit"></i> Edit</a></li>'
			+'<li><a href="#" class="confirmation" onclick="deleteRenewalSubTypeById('+NonManadatorySubRenewal[i].renewal_Subid+')"><span class="fa fa-trash"></span> Delete</a></li>'
			+'</ul>'
			+'</div>'
			));

			tbody.append(tr1);
		}
	}else{
		showMessage('info','Non Mandatory Renewals Not found !')
	}
	
	$("#VendorPaymentTable1").append(thead);
	$("#VendorPaymentTable1").append(tbody);

}


function saveRenewalSubType(){
	
	var jsonObject							= new Object();

	jsonObject["renewalId"]					= $('#selectReType').val();
	jsonObject["renewalSubType"]			= $('#SubReType').val();
	
	if($("#isMandatory").prop("checked")){
		jsonObject["isMandatory"]			= true;
	} else {
		jsonObject["isMandatory"]			= false;
	}
	
	if($('#selectReType').val() < 0){
		$('#selectReType').focus();
		showMessage('errors', 'Please Select Renewal Type !');
		hideLayer();
		return false;
	}
	
	if($('#SubReType').val().trim() == ''){
		$('#SubReType').focus();
		showMessage('errors', 'Please Select Renewal Sub Type !');
		hideLayer();
		return false;
	}
	
	showLayer();
	$.ajax({
		url: "RenewalReminderWS/saveRenewalSubTypeDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			if(data.SaveRenewalSubType != undefined && data.SaveRenewalSubType == true){
				showMessage('success', 'Renewal Subtype Saved Successfully!');
				location.reload();
				hideLayer();
			}else if(data.RenewalSubTypeAlreadyExists != undefined && data.RenewalSubTypeAlreadyExists == true){
				showMessage('errors', 'Renewal Subtype Already Present Hence Cannot Create Duplicate!');
				hideLayer();
			}
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

$(document).ready(function() {
     $("#editSelectReType").select2({
        ajax: {
            url: "getRenewalType.in?Action=FuncionarioSelect2",
            dataType: "json",
            type: "GET",
            contentType: "application/json",
            data: function(e) {
                return {
                    term: e
                }
            },
            results: function(e) {
                return {
                    results: $.map(e, function(e) {
                        return {
                            text: e.renewal_Type,
                            slug: e.slug,
                            id: e.renewal_id
                        }
                    })
                }
            }
        }
    })
})

function getRenewalSubTypeById(subTypeId){
	
	var jsonObject					= new Object();
	jsonObject["subTypeId"]			= subTypeId;
	
	showLayer();
	$.ajax({
		url: "RenewalReminderWS/getRenewalSubTypeById.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			var rtID = data.RenewalSubType.renewal_id;
			var rtText = data.RenewalSubType.renewal_Type;
			$('#editSelectReType').select2('data', {
			id : rtID,
			text : rtText
			});
			
			$('#editSubReType').val(data.RenewalSubType.renewal_SubType);
			$('#editSubTypeId').val(data.RenewalSubType.renewal_Subid);
			
			if(data.RenewalSubType.mandatory == true){
				$('#editIsMandatory').prop('checked', true);
			} else {
				$('#editIsMandatory').prop('checked', false);
			}
			
			$('#editRenewalSubtypes').modal('show');
			hideLayer();
		},
		error: function (e) {
			showMessage('errors', 'Some Error Occurred!');
			hideLayer();
		}
	});

}

function updateRenewalSubType(){
	
	var jsonObject							= new Object();
	
	jsonObject["renewalId"]					= $('#editSelectReType').val();
	jsonObject["renewalSubTypeId"]			= $('#editSubTypeId').val();
	jsonObject["renewalSubType"]			= $('#editSubReType').val();
	
	if($("#editIsMandatory").prop("checked")){
		jsonObject["isMandatory"]			= true;
	} else {
		jsonObject["isMandatory"]			= false;
	}
	
	if($('#editSelectReType').val() < 0){
		$('#editSelectReType').focus();
		showMessage('errors', 'Please Select Renewal Type !');
		hideLayer();
		return false;
	}
	
	if($('#editSubReType').val().trim() == ''){
		$('#editSubReType').focus();
		showMessage('errors', 'Please Select Renewal Sub Type !');
		hideLayer();
		return false;
	}
	
	showLayer();
	$.ajax({
		url: "RenewalReminderWS/updateRenewalSubTypeDetails.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			$('#editRenewalSubtypes').modal('hide');
			
			if(data.alreadyRenewalSubType != undefined && data.alreadyRenewalSubType == true){
				showMessage('info', 'Already Exists!');
				hideLayer();
			}
			
			if(data.renewalSubTypeUpdated != undefined && data.renewalSubTypeUpdated == true){
				showMessage('success', 'Renewal Sub Type Updated Successfully !');
				hideLayer();
				location.reload();
			}
			
		},
		error: function (e) {
			showMessage('errors', 'Some Error Occurred!');
			hideLayer();
		}
	});

}

function deleteRenewalSubTypeById(subTypeId) {
	
	if (confirm('are you sure to delete ?')) {
		
		var jsonObject					= new Object();
		jsonObject["renewalSubTypeId"]	= subTypeId;
		
		showLayer();
		$.ajax({
			url: "RenewalReminderWS/deleteRenewalSubTypeById.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				
				if(data.renewalSubTypeDeleted != undefined && (data.renewalSubTypeDeleted == 'true' || data.renewalSubTypeDeleted == true )){
					showMessage('success', 'Renewal SubType Deleted Sucessfully!');
				}
				
				hideLayer();
				location.reload();
			},
			error: function (e) {
				console.log("Error : " , e);
				hideLayer();
			}
		});
	} 
	
}