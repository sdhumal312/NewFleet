$(document).ready(function() {
	getRenewalDetailsByRenewalId();
});

function getRenewalDetailsByRenewalId() {
	
	showLayer();
	var jsonObject					= new Object();
	jsonObject["renewal_id"]		= $('#renewal_id').val();
	
	
	$.ajax({
		url: "RenewalReminderWS/getRenewalDetailsByRenewalId",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			setRenewalDetailsByRenewalId(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setRenewalDetailsByRenewalId(data) {
	
	var VID = data.renewalDetails.vid;
	var VText = data.renewalDetails.vehicle_registration;
	$('#RenewalSelectVehicle').select2('data', {
	id : VID,
	text : VText
	});
		
	var rtID = data.renewalDetails.renewalTypeId;
	var rtText = data.renewalDetails.renewal_type;
	$('#from').select2('data', {
	id : rtID,
	text : rtText
	});
	
	$('#to').select2('data', {
		id : data.renewalDetails.renewal_Subid,
		text : data.renewalDetails.renewal_subtype
	});
	
	getRenewalSubTypeListById(rtID, true, data.renewalDetails.renewal_Subid);
	
}

function validateRenewalDetails(){
	
	if(Number($('#RenewalSelectVehicle').val()) <= 0){
		showMessage('errors', 'Please select vehicle !');
		return false;
	}
	
	if(Number($('#from').val()) <= 0){
		showMessage('errors', 'Please select Renewal Type !');
		return false;
	}
	
	if(Number($('#to').val()) <= 0){
		showMessage('errors', 'Please select Renewal Sub Type !');
		return false;
	}
	
	if($('#reservation').val() == null || $('#reservation').val().trim() == ''){
		showMessage('errors','Please Enter Validity From & To !');
		$('#reservation').focus();
		return false;
	}
	
	if(Number($('#renewal_timethreshold').val()) <= 0){
		showMessage('errors', 'Please select Due Threshold !');
		return false;
	}
	
	if($('#validateReceiptNo').val() == 'true' || $('#validateReceiptNo').val() == true){
		if($('#renewalReceipt').val() == null || $('#renewalReceipt').val().trim() == ''){
			showMessage('errors','Please Enter Receipt/Challan Number !');
			$('#reservation').focus();
			return false;
		}
	}
	
	if($('#renewalAmount').val() <= 0 && $('#renewalAmount').val().trim() == ''){					
		showMessage('errors', 'Please Enter Amount!');
		hideLayer();
		return false;
	}
	
	if($('#showVendorCol').val() == 'true' || $('#showVendorCol').val() == true){
		if($('#vendorId').val() <= 0){					
			showMessage('errors', 'Please Enter Vendor!');
			hideLayer();
			return false;
		}
	}
	
	if($('#validateModeOfPayment').val() == 'true' || $('#validateModeOfPayment').val() == true){
		if($('#renPT_option').val() < 0){					
			showMessage('errors', 'Please Enter Mode of Payment!');
			hideLayer();
			return false;
		}
	}
	
	if($('#validatePaidDate').val() == 'true' || $('#validatePaidDate').val() == true){
		if($('#renewalpaidDate').val() == null || $('#renewalpaidDate').val().trim() == ''){
			showMessage('errors','Please Enter Paid Date !');
			$('#reservation').focus();
			return false;
		}
	}
	
	if($('#tallyIntegrationRequired').val() == 'true' || $('#tallyIntegrationRequired').val() == true){
		if($('#tallyCompanyId').val() <= 0){					
			showMessage('errors', 'Please Enter Tally Company Name!');
			hideLayer();
			return false;
		}
	}
	
	if($('#saveRenewalWithoutFile').val() == 'false' || $('#saveRenewalWithoutFile').val() == false){
		if($('#renewalFile').val().trim() == ''){
			$('#renewalFile').focus();
			showMessage('errors', 'Please Select A File To Upload !');
			hideLayer();
			return false;
		}
	}
	
	if (validateBankPaymentDetails && !validateBankPayment($('#bankPaymentTypeId').val())) {
		return false;
	}
	
	return true;
	
}

$(document).ready(function () {
	
    $("#btnSubmit").on('keypress click', function (e) {
        e.preventDefault();
        reviseRenewalReminder();
    });

});

if($('#defaultPaiddate').val()==true || $('#defaultPaiddate').val()=='true'){
	
$('#reservation').on('change', function(){
	$('#renewalpaidDate').val($('#reservation').val().split(" to ")[0]);
})

}

function reviseRenewalReminder(){
	
	if(!validateRenewalDetails()){
		return false;
	}
	
	if(!dateRangeValidation()){
		return false;
	}
	
	var jsonObject								= new Object();
	
	jsonObject["renewal_id"]					= $('#renewal_id').val();
	jsonObject["vehicleId"]						= $('#RenewalSelectVehicle').val();
	jsonObject["renewalTypeId"]					= $('#from').val();
	jsonObject["renewalSubTypeId"]				= $('#to').val();
	jsonObject["dateRange"]						= $('#reservation').val();
	jsonObject["timeThreshold"]					= $('#renewal_timethreshold').val();
	jsonObject["renewalPeriodThreshold"]		= $('#renewal_periedthreshold').val();
	jsonObject["renewalReceiptNo"]				= $('#renewalReceipt').val();
	jsonObject["renewalAmount"]					= $('#renewalAmount').val();
	jsonObject["vendorId"]						= $('#vendorId').val();
	jsonObject["paymentTypeId"]					= $('#renPT_option').val();
	jsonObject["renewalPayNo"]					= $('#renewal_PayNumber').val();
	jsonObject["paidDate"]						= $('#renewalpaidDate').val();
	jsonObject["tallyCompanyId"]				= $('#tallyCompanyId').val();
	jsonObject["renewalPaidById"]				= $('#renewal_paidby').val();
	jsonObject["renewalFile"]					= $('#renewalFile').val();
	jsonObject["renewal_authorization"]			= $('#renewal_authorization').val();
	jsonObject["remark"]						= $('#renewal_number').val();
	jsonObject["validateDoublePost"] 	 		=  true;
	jsonObject["unique-one-time-token"] 	 	=  $('#accessToken').val();
	
	
	var form = $('#fileUploadForm')[0];
    var data = new FormData(form);
    
	if (allowBankPaymentDetails) {
		prepareObject(jsonObject)
	}

    data.append("RenewalData", JSON.stringify(jsonObject));
    
    var fileInput = document.getElementById('renewalFile');
    
    for (i = 0; i < fileInput.files.length; i++) {  
        data.append("file", fileInput.files[i]);  
    } 
    
    showLayer();
    $.ajax({
		type: "POST",
		enctype: 'multipart/form-data',
		url: "RenewalReminderWS/reviseRenewalReminderDetails",
		data: data,
		processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
		success: function (data) {
			
			if(data.sequenceNotFound == true){
				hideLayer();
				showMessage('errors', "Sequence Not Found. Please contact Admin !");
				
			} else if (data.renewalRemindeAlready == true){
				hideLayer();
				showMessage('info', "Renewal Reminder Already Exists Hence Cannot Create New RR !");
				
			} else if (data.renewalReceiptAlready == true){
				hideLayer();
				showMessage('info', "Renewal Reminder Compliance Issue Hence Cannot Create New RR !");
				
			} else if (data.documentIsCompulsory == true){
				hideLayer();
				showMessage('errors', "Renewal Reminder cannot be created. Upload of Document is Mandatory !");
				
			}  else if (data.saveRenewalReminder == true){
				hideLayer();
				showMessage('success', "Renewal Reminder successfully Revised !");
				window.location.replace("viewRenewalReminder.in");
				
			} else {
				hideLayer();
				showMessage('errors', "Renewal Not Created, Please contact on Support !");
			}
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
}
