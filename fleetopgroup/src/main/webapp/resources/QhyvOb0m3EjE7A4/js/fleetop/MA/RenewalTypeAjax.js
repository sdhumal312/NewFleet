$(document).ready(function() {
	getRenewalTypeList();
});

function getRenewalTypeList() {
	
	showLayer();
	var jsonObject	= new Object();

	$.ajax({
		url: "RenewalReminderWS/getRenewalTypeList",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.renewalType != undefined && data.renewalType != null){
				setRenewalTypeList(data);
			}
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setRenewalTypeList(data) {
	$("#VendorPaymentTable").empty();
	
	var tally = data.configuration.tallyIntegrationRequired;
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	if(tally)
	var th3		= $('<th>');
	var th4		= $('<th>');

	tr1.append(th1.append("No"));
	tr1.append(th2.append("Renewal Type"));
	if(tally)
	tr1.append(th3.append("Tally Expense Head"));
	tr1.append(th4.append("Action"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.renewalType != undefined && data.renewalType.length > 0) {
		
		var renewalType = data.renewalType;
	
		for(var i = 0; i < renewalType.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td>');
			var td2		= $('<td>');
			if(tally)
			var td3		= $('<td>');
			var td4		= $('<td>');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append(renewalType[i].renewal_Type));
			if(tally)
			tr1.append(td3.append(renewalType[i].tallyExpenseName));
			
			if(renewalType[i].companyId != 0){
			tr1.append(td4.append(
			'<div class="btn-group">'
				+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
				+'<ul class="dropdown-menu pull-right">'
				+'<li><a href="#" class="confirmation" onclick="getRenewalTypeById('+renewalType[i].renewal_id+')"><i class="fa fa-edit"></i> Edit</a></li>'
				+'<li><a href="#" class="confirmation" onclick="deleteRenewalTypeById('+renewalType[i].renewal_id+')"><span class="fa fa-trash"></span> Delete</a></li>'
				+'</ul>'
			+'</div>'
			));
			}else{
				tr1.append(td4.append(''));
			}

			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#VendorPaymentTable").append(thead);
	$("#VendorPaymentTable").append(tbody);

}

$(document).ready(function() {
	$("#tallyExpenseId").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getSearchExpenseName.in?Action=FuncionarioSelect2",
            dataType: "json",
            type: "POST",
            contentType: "application/json",
            quietMillis: 50,
            data: function(a) {
                return {
                    term: a
                }
            },
            results: function(a) {
                return {
                    results: $.map(a, function(a) {
                        return {
                            text: a.expenseName ,
                            slug: a.slug,
                            id: a.expenseID
                        }
                    })
                }
            }
        }
    })
})

function saveRenewalType(){
	
	var jsonObject							= new Object();

	jsonObject["ReType"]					= $('#ReType').val();
	jsonObject["tallyExpenseId"]			= $('#tallyExpenseId').val();
	jsonObject["avoidAllow"]			= $('#avoidAllow').prop('checked');
	
	if($('#ReType').val().trim() == ''){
		$('#ReType').focus();
		showMessage('errors', 'Please Select Renewal Type !');
		hideLayer();
		return false;
	}
	
	if($('#tallyExpenseId').val() <= 0){
		$('#tallyExpenseId').focus();
		showMessage('errors', 'Please Select Tally Expense Head !');
		hideLayer();
		return false;
	}
	
	showLayer();
	$.ajax({
		url: "RenewalReminderWS/saveRenewalTypeList",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			if(data.saveRenewalType != undefined && data.saveRenewalType == true){
				showMessage('success', 'Renewal Type Saved Successfully!');
				location.reload();
				hideLayer();
			}else if(data.alreadyRenewal != undefined && data.alreadyRenewal == true){
				showMessage('errors', 'Renewal Type Already Present Hence Cannot Create Duplicate!');
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
	$("#editTallyExpenseId").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getSearchExpenseName.in?Action=FuncionarioSelect2",
            dataType: "json",
            type: "POST",
            contentType: "application/json",
            quietMillis: 50,
            data: function(a) {
                return {
                    term: a
                }
            },
            results: function(a) {
                return {
                    results: $.map(a, function(a) {
                        return {
                            text: a.expenseName ,
                            slug: a.slug,
                            id: a.expenseID
                        }
                    })
                }
            }
        }
    })
})

function getRenewalTypeById(renewalId){
	
	var jsonObject					= new Object();
	jsonObject["renewalId"]			= renewalId;
	
	showLayer();
	$.ajax({
		url: "RenewalReminderWS/getRenewalTypeById.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			$('#renewalId').val(renewalId);
			$('#editReType').val(data.renewalType.renewal_Type);
			if(data.renewalType != undefined || data.renewalType != null){
				if(data.renewalType.allowToAvoid){
					$('#editAvoidAllow').prop('checked',true);
				}
			}
			
			var rtID = data.renewalType.expenseId;
			var rtText = data.renewalType.tallyExpenseName;
			$('#editTallyExpenseId').select2('data', {
			id : rtID,
			text : rtText
			});
			
			$('#editRenewaltypes').modal('show');
			hideLayer();
		},
		error: function (e) {
			showMessage('errors', 'Some Error Occurred!');
			hideLayer();
		}
	});

}

function updateRenewalType(){
	
	var jsonObject								= new Object();
	
	jsonObject["renewalId"]						= $('#renewalId').val();
	jsonObject["editReType"]					= $('#editReType').val();
	jsonObject["editTallyExpenseId"]			= $('#editTallyExpenseId').val();
	jsonObject["editAvoidAllow"]				= $('#editAvoidAllow').prop('checked');
	
	if($('#editReType').val().trim() == ''){
		$('#editReType').focus();
		showMessage('errors', 'Please Select Renewal Type !');
		hideLayer();
		return false;
	}
	
	if($('#editTallyExpenseId').val() <= 0){
		$('#editTallyExpenseId').focus();
		showMessage('errors', 'Please Select Tally Expense Head !');
		hideLayer();
		return false;
	}
	
	
	showLayer();
	$.ajax({
		url: "RenewalReminderWS/updateRenewalType.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			$('#editRenewaltypes').modal('hide');
			
			if(data.renewalTypeAlready != undefined && data.renewalTypeAlready == true){
				showMessage('errors', 'Already Exists!');
				hideLayer();
			}
			
			if(data.renewalTypeUpdated != undefined && data.renewalTypeUpdated == true){
				showMessage('success', 'Renewal Type Updated Successfully !');
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

function deleteRenewalTypeById(renewalId) {
	
	if (confirm('are you sure to delete ?')) {
		
		var jsonObject				= new Object();
		jsonObject["renewalId"]		= renewalId;
		
		showLayer();
		$.ajax({
			url: "RenewalReminderWS/deleteRenewalTypeById.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				
				if(data.renewalTypeDeleted != undefined && (data.renewalTypeDeleted == 'true' || data.renewalTypeDeleted == true )){
					showMessage('success', 'Renewal Type Deleted Sucessfully!');
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

