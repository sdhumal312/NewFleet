function validateVehicleAccidentDetails(){
	if(Number($('#TripSelectVehicle').val()) <= 0){
		$('#TripSelectVehicle').select2('focus');
		 showMessage('info', 'Please select Vehicle !');
		 return false;
	 }
	 if($('#accidentDate').val().trim() == ''){
		 $('#accidentDate').focus();
		 showMessage('info', 'Please select accident date !');
		 return false;
	 }
	 if($('#accidentTime').val().trim() == ''){
		 $('#accidentTime').focus();
		 showMessage('info', 'Please select accident time !');
		 return false;
	 }
	if(Number($('#driverList').val()) <= 0){
		$('#driverList').select2('focus');
		 showMessage('info', 'Please select Driver !');
		 return false;
	 }
	if($('#incidentlocation').val().trim() == ''){
		 $('#incidentlocation').focus();
		 showMessage('info', 'Please enter accident location !');
		 return false;
	 }
	if($('#description').val().trim() == ''){
		 $('#description').focus();
		 showMessage('info', 'Please enter accident description !');
		 return false;
	 }
	return true;
}

function validateServeyerDetails(){
	 if($('#serveyorName').val().trim() == ''){
		 $('#serveyorName').focus();
		 showMessage('info', 'Please Enter serveyor name !');
		 return false;
	 }
	 if($('#serveyorMobile').val().trim() == ''){
		 $('#serveyorMobile').focus();
		 showMessage('info', 'Please Enter serveyor mobile !');
		 return false;
	 }
	 if($('#serveyorCompany').val().trim() == ''){
		 $('#serveyorCompany').focus();
		 showMessage('info', 'Please Enter serveyor company !');
		 return false;
	 }
	 
	 if($('#accidentAdditionalFields').val() == "true" && $('#surveyorDate').val().trim() == ''){
		 showMessage('info', 'Please select Surveyor Inform Date !');
		 return false; 
	 }
	return true;
}
function validateSpotSurveyorDetails(){
	 if($('#spotSurveyorName').val().trim() == ''){
		 $('#spotSurveyorName').focus();
		 showMessage('info', 'Please Enter Spot surveyor name !');
		 return false;
	 }
	 if($('#spotSurveyorMobile').val().trim() == ''){
		 $('#spotSurveyorMobile').focus();
		 showMessage('info', 'Please Enter Spot surveyor mobile !');
		 return false;
	 }
	 if($('#spotSurveyorCompany').val().trim() == ''){
		 $('#spotSurveyorCompany').focus();
		 showMessage('info', 'Please Enter Spot surveyor company !');
		 return false;
	 }
	 if($('#spotSurveyorDate').val().trim() == ''){
		 $('#spotSurveyorDate').focus();
		 showMessage('info', 'Please Enter Spot surveyor Date !');
		 return false;
	 }
	 if($('#spotSurveyorCompany').val().trim() == ''){
		 $('#spotSurveyorCompany').focus();
		 showMessage('info', 'Please Enter Spot surveyor Time !');
		 return false;
	 }
	return true;
}
function validateServeyCompletionDetails(){
	if($('#completionDate').val().trim() == ''){
		 $('#completionDate').focus();
		 showMessage('info', 'Please select completion date !');
		 return false;
	 }
	 if($('#completionRemark').val().trim() == ''){
		 $('#completionRemark').focus();
		 showMessage('info', 'Please Enter completion remark !');
		 return false;
	 }
	return true;
}
function validateFinalServeyForDamage(){
	
	if(($("#accidentClaimConfig").val() == true || $("#accidentClaimConfig").val() == "true" ) && $("#finalServeyorName").val().trim() == ""){
		 $('#finalServeyorName').focus();
		 showMessage('info', 'Please select final servey Name !');
		 return false;
	 }
	if(($("#accidentClaimConfig").val() == true || $("#accidentClaimConfig").val() == "true" ) && $("#finalServeyorMobile").val().trim() == ""){
		$('#finalServeyorMobile').focus();
		showMessage('info', 'Please Enter final servey Mobile Number !');
		return false;
	}
	if($('#finalSDate').val().trim() == ''){
		 $('#finalSDate').focus();
		 showMessage('info', 'Please select final servey date !');
		 return false;
	 }
	 if($('#finalSRemark').val().trim() == ''){
		 $('#finalSRemark').focus();
		 showMessage('info', 'Please Enter final servey remark !');
		 return false;
	 }
	return true;
}
function validateQuotationApprovalDetails(){
	if($('#multipleQuotation').val() == true || $('#multipleQuotation').val() == 'true'){
		if($('#serviceNumber').text().trim() == ''){
			showMessage('info', 'Please Create Quotation/Service Entries/WorkOrder first to approve it !');
			return false;
		}
	}else{
		if(Number($('#serviceId').val()) <= 0){
			showMessage('info', 'Please Create Quotation/Service Entries/WorkOrder first to approve it !');
			return false;
		}
	}
	if($('#approvalDate').val().trim() == ''){
		 $('#approvalDate').focus();
		 showMessage('info', 'Please select approval date !');
		 return false;
	 }
	 if($('#approvalRemark').val().trim() == ''){
		 $('#approvalRemark').focus();
		 showMessage('info', 'Please Enter approval remark !');
		 return false;
	 }
	 
	 
	return true;
}
function validateAdvanceDetails(){
	if($('#advanceDate').val().trim() == ''){
		 $('#advanceDate').focus();
		 showMessage('info', 'Please select advance date !');
		 return false;
	 }
	if(Number($('#advanceAmount').val()) <= 0){
		$('#advanceAmount').focus();
		 showMessage('info', 'Please enter advance amount !');
		 return false;
	 }
	 if($('#advanceRemark').val().trim() == ''){
		 $('#advanceRemark').focus();
		 showMessage('info', 'Please Enter advance remark !');
		 return false;
	 }
	 
	return true;
}

function validateExpenseDetails(){
	if($('#expenseType').val().trim() == ''){
		 $('#expenseType').focus();
		 showMessage('info', 'Please select expense type !');
		 return false;
	 }
	if($('#expenseDate').val().trim() == ''){
		 $('#expenseDate').focus();
		 showMessage('info', 'Please select expense date !');
		 return false;
	 }

	 if(Number($('#expenseAmount').val()) <= 0){
		 $('#expenseAmount').focus();
		 showMessage('info', 'Please enter expense amount !');
		 return false;
	 }
	 if($('#expenseRemark').val().trim() == ''){
		 $('#expenseRemark').focus();
		 showMessage('info', 'Please Enter expense remark !');
		 return false;
	 }
	
	return true;

}
function validateFinalInspectionDetails(){

	if($('#inspectionDate').val().trim() == ''){
		 $('#inspectionDate').focus();
		 showMessage('info', 'Please select final inspection date !');
		 return false;
	 }
	 if($('#inspectionRemark').val().trim() == ''){
		 $('#inspectionRemark').focus();
		 showMessage('info', 'Please Enter final inspection remark !');
		 return false;
	 }
	 
	return true;

}
function validatePaymentDetails(){

	if($('#paymentDateStr').val().trim() == ''){
		 $('#paymentDateStr').focus();
		 showMessage('info', 'Please select payment date !');
		 return false;
	 }
	if(Number($('#paymentAmount').val()) <= 0){
		$('#paymentAmount').focus();
		 showMessage('info', 'Please enter payment amount !');
		 return false;
	 }
	 if($('#paymentRemark').val().trim() == ''){
		 $('#paymentRemark').focus();
		 showMessage('info', 'Please Enter payment remark !');
		 return false;
	 }
	 if(($('#accidentAdditionalFields').val() == true || $('#accidentAdditionalFields').val() == 'true') && $('#queryCheck').is(":checked") && $('#queryRemark').val().trim() == ''){
		 showMessage('info', 'Please Enter Query remark !');
		 return false;
	 }
	return true;
}


function amtReceivedClick(){
	console.log("dsfdsfds")
	if($("#isAmtReceived").prop('checked')){
		if($("#isAmtPaid").prop('checked')){
			showMessage('info','You Have Already Checked Paid Amount');
			$("#isAmtReceived").prop('checked',false);
			$("#receivedAmountDiv").hide();
			$("#receivedDamageAmount").val(0);
		}else{
			$("#receivedAmountDiv").show();
		}
	}else{
		$("#receivedAmountDiv").hide();
		$("#receivedDamageAmount").val(0);
	}
	
}

function amtPaidClick(){
	
	if($("#isAmtPaid").prop('checked')){
		if($("#isAmtReceived").prop('checked')){
			showMessage('info','You Have Already Checked Received Amount');
			$("#isAmtPaid").prop('checked',false);
			$("#paidAmountDiv").hide();
			$("#paidDamageAmount").val(0);
			
		}else{
			$("#paidAmountDiv").show();
		}
	}else{
		$("#paidAmountDiv").hide();
		$("#paidDamageAmount").val(0);
	}
	
}
function showClaimRemark(){
	
	if($("#isClaim").prop('checked')){
		$("#claimRemarkDiv").show();
	}else{
		$("#claimRemarkDiv").hide();
	}
	
}	

function selectClaim(typeId){
	if(typeId == 1){																	
		$('#yes').addClass('active');
		$('#no').removeClass('active');
		$('#yes').addClass('btn-success');
		$('#no').removeClass('btn-success');
		$('#isClaim').val(true);
	} else {
		$('#yes').removeClass('active');
		$('#no').addClass('active');
		$('#yes').removeClass('btn-success');
		$('#no').addClass('btn-success');
		$('#isClaim').val(false);
	}
}