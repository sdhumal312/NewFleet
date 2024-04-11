$(document).ready(function() {
	getVehicleAndRenewalTypeDetails();
});

function getVehicleAndRenewalTypeDetails() {
	
	showLayer();
	var jsonObject								= new Object();
	jsonObject["vid"]							= $('#renewalVehicleId').val();
	jsonObject["mandatoryRenewalSubTypeId"]		= $('#mandatoryRenewalSubTypeId').val();
	
	$.ajax({
		url: "RenewalReminderWS/getVehicleAndRenewalTypeDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setVehicleAndRenewalTypeDetails(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setVehicleAndRenewalTypeDetails(data) {
	
	if($('#renewalVehicleId').val() > 0){
		var VID = $('#renewalVehicleId').val();
		var VText = data.vehicle.vehicle_registration;
		$('#RenewalSelectVehicle').select2('data', {
			id : VID,
			text : VText
		});
	}
	if(data.typeAndSubType != null){
		
		var rtID = data.typeAndSubType.renewal_id;
		var rtText = data.typeAndSubType.renewal_Type;
		$('#from').select2('data', {
		id : rtID,
		text : rtText
		});
		
		$('#to').select2('data', {
			id : data.typeAndSubType.renewal_Subid,
			text : data.typeAndSubType.renewal_SubType
		});
		
		getRenewalSubTypeListById(rtID, true, data.typeAndSubType.renewal_Subid);
		
	}
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
	
	if(validateBankPaymentDetails && !validateBankPayment($('#bankPaymentTypeId').val())){
		return false;	
	}
	return true;
	
}

$(document).ready(function () {
	
    $("#btnSubmit").on('keypress click', function (e) {
        e.preventDefault(); 	//stop submit the form, we will post it manually.
        saveRenewalReminder();
    });

});

if($('defaultPaiddate').val()==true || $('#defaultPaiddate').val()=='true'){
$('#reservation').on('change', function(){
	var currentDate =moment().toDate();
	var renwalstart =$('#reservation').val().split(" to ")[0];
	var renwalstartFormat=dateFormat(renwalstart);
	if(renwalstartFormat<=currentDate){
		$('#renewalpaidDate').val(renwalstart);
	}else{
		showMessage('info', "You have selected future date kindly select paid date");
	}
});

}
function saveRenewalReminder(){
	
	if(!validateRenewalDetails()){
		return false;
	}
	if(!dateRangeValidation()){
		return false;
	}
	
	var jsonObject								= new Object();

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
	jsonObject["renewalPaidById"]				= $('#renewal_paidbyId').val();
	jsonObject["renewalFile"]					= $('#renewalFile').val();
	jsonObject["renewal_authorization"]			= $('#renewal_authorization').val();
	jsonObject["remark"]						= $('#renewal_number').val();
	jsonObject["validateDoublePost"] 	 		=  true;
	jsonObject["unique-one-time-token"] 	 	=  $('#unique-one-time-token').val();
	
	if($("#stopIvCargoCashBookEntries").val() == "true" || $("#stopIvCargoCashBookEntries").val() == true){
		jsonObject["stopIvCargoCashBookEntries"]    = true
	}
	
	var form = $('#fileUploadForm')[0];
    var data = new FormData(form);

	if (allowBankPaymentDetails) {
		prepareObject(jsonObject);
	}
	
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
		url: "RenewalReminderWS/saveRenewalReminderDetails",
		data: data,
		processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
		success: function (data) {
			if(data.sequenceNotFound == true){
				hideLayer();
				$('#unique-one-time-token').val(data.accessToken);
				showMessage('errors', "Sequence Not Found. Please contact Admin !");
				
			} else if (data.renewalRemindeAlready == true){
				hideLayer();
				$('#unique-one-time-token').val(data.accessToken);
				showMessage('info', "Renewal Reminder Already Exists Hence Cannot Create New RR !");
				
			} else if (data.renewalReceiptAlready == true){
				hideLayer();
				$('#unique-one-time-token').val(data.accessToken);
				showMessage('info', "Duplicate Receipt Number , cannot create RR !");
				
			} else if (data.documentIsCompulsory == true){
				hideLayer();
				$('#unique-one-time-token').val(data.accessToken);
				showMessage('errors', "Renewal Reminder cannot be created. Upload of Document is Mandatory !");
				
			} else if (data.saveRenewalReminder == true){
				showMessage('success', "Renewal Reminder successfully Created !");
				hideLayer();
				window.location.replace("ViewVehicleDocument?vehid="+$('#RenewalSelectVehicle').val());
				
			}else if (data.vehicleStatus != undefined){
				$('#unique-one-time-token').val(data.accessToken);
				showMessage('errors', "Cannot Create Renewal Vehicle Status id "+data.vehicleStatus+" !");
				hideLayer();
				
			} else {
				hideLayer();
				$('#unique-one-time-token').val(data.accessToken);
				showMessage('errors', "Renewal Not Created, Please contact on Support !");
			}
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
}

