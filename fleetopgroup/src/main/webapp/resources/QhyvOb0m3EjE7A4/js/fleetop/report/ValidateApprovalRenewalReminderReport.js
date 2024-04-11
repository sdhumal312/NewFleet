function validateApprovalRenewalReminderReport()
{
	if(Number($('#VehicleGroupRRG').val()) <= 0){
		showMessage('info','Please Select Group!');
		return false;
	}	
	if(Number($('#from2').val()) <= 0){
		showMessage('info','Please Select Renewal Type !');
		return false;
	}	
	if(Number($('#to2').val()) <= 0){
		showMessage('info','Please Select Renewal Sub Type !');
		return false;
	}	
}