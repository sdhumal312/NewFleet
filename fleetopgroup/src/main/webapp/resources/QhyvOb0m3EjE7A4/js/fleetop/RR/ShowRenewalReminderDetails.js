function serachRenewalReminderByNumber(){
	
	var jsonObject					= new Object();
	jsonObject["renewalNumber"]		= $("#searchByNumber").val();
	
	if( $("#searchByNumber").val() == ""){
		$('#searchByNumber').focus();
		showMessage('errors', 'Please Enter Valid Renewal Number !');
		return false;
	}
	
	showLayer();
	$.ajax({
		url: "RenewalReminderWS/serachRenewalReminderByNumber",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			if(data.RenewalReminder != undefined && data.RenewalReminder.renewal_id != null){
				hideLayer();
				window.location.replace("showRenewalReminderDetails.in?renewalId="+data.RenewalReminder.renewal_id+"");
			} else {
				hideLayer();
				showMessage('info', 'Please Enter Valid Renewal Number !');
			}
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function updateRenewalPeriod(){
	
	if(!dateRangeValidation()){
		return false;
	}
	
	var jsonObject								= new Object();
	jsonObject["renewal_id"]					= $("#renewal_id").val();
	jsonObject["dateRange"]						= $("#reservation").val();
	jsonObject["timeThreshold"]					= $("#renewal_timethreshold").val();
	jsonObject["renewalPeriodThreshold"]		= $("#renewal_periedthreshold").val();
	
	if($('#reservation').val() == null || $('#reservation').val().trim() == ''){
		showMessage('errors','Please Enter Validity From & To !');
		$('#reservation').focus();
		return false;
	}
	
	if(Number($('#renewal_timethreshold').val()) <= 0){
		showMessage('errors', 'Please select Due Threshold !');
		return false;
	}
	
	showLayer();
	$.ajax({
		url: "RenewalReminderWS/updateRenewalPeriodDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			if(data.renewalRemindeAlready != undefined && data.renewalRemindeAlready == true){
				hideLayer();
				showMessage('info', 'Renewal Period Already Exists Hence Cannot Updated Renewal Period ! ');
			} else if(data.renewalPeriodUpdated != undefined && data.renewalPeriodUpdated == true) {
				hideLayer();
				showMessage('success', 'Renewal Period Updated !');
				window.location.replace("showRenewalReminderDetails.in?renewalId="+$("#renewal_id").val()+"");
			}
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

$(document).ready(function () {
	thresholdperiod();
	switch(Number($("#renewalStatusId").val())) {
	  case 1:
		  $("#renewalStatusLabel").addClass('label-danger');
	    break;
	  default:
		  $("#renewalStatusLabel").addClass('label-success');
	} 
	
    $("#btnSubmit").on('keypress click', function (e) {
        e.preventDefault(); 	//stop submit the form, we will post it manually.
        uploadEditDocument();
    });

});

function uploadEditDocument(){
	
	var jsonObject					= new Object();
	jsonObject["renewal_id"]		= $('#renewal_id').val();
	jsonObject["renewal_Number"]	= $('#renewal_Number').val();
	jsonObject["renewalFile"]		= $('#renewalFile').val();
	
	if($('#renewalFile').val().trim() == ''){
		$('#renewalFile').focus();
		showMessage('errors', 'Please Select A File To Upload !');
		hideLayer();
		return false;
	}
	
	var form = $('#fileUploadForm')[0];
    var data = new FormData(form);

    data.append("RenewalData", JSON.stringify(jsonObject)); 
    
    var fileInput = document.getElementById('renewalFile');
    
    for (i = 0; i < fileInput.files.length; i++) {  
        var sfilename = fileInput.files[i].name;  
        data.append("file", fileInput.files[i]);  
    }
	
    showLayer();
    $.ajax({
		type: "POST",
		enctype: 'multipart/form-data',
		url: "RenewalReminderWS/updateRenewalDocumentsDetails",
		data: data,
		processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
		success: function (data) {
			
			if(data.renewalDocumentUpdated == true){
				hideLayer();
				showMessage('success', 'Renewal Document Updated Successfully !');
				window.location.replace("showRenewalReminderDetails.in?renewalId="+$("#renewal_id").val()+"");
				
			} else {
				hideLayer();
				showMessage('errors', "Document Not Uploaded. Please Contact On Support !");
			} 
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}



$("#createApproval").click(function(){
	showLayer();
	var jsonObject					= new Object();
	jsonObject["renewal_id"]		= $('#renewal_id').val();
	jsonObject["approvalRemark"]	= $('#approvalRemark').val();
	$.ajax({
		url: "RenewalReminderWS/createRRApproval",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			showMessage('success','Renewal Approved')
			location.reload();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}); 
function removeRenewalDocument(documentId){

	if(Number($('#docSize').val()) <= 1){
	 showMessage('info', 'cannot delete. First Upload the new document than try to delete !');
	 return false;
	}

	showLayer();
	var jsonObject				= new Object();
	jsonObject["renewal_id"]	= $('#renewal_id').val();
	jsonObject["documentId"]	= documentId;
	$.ajax({
		url: "RenewalReminderWS/removeRenewalDocument",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
				showMessage('success', 'Date Deleted Successfully !');
				location.reload();
				hideLayer();
		},
		error: function (e) {
			showMessage('success', 'Date Deleted Successfully !');
				location.reload();
		}
	});
}