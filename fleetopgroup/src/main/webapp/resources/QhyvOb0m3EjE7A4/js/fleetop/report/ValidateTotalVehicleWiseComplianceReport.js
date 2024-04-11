function validateTotalVehicleWiseComplianceReport()
{
	if(Number($('#ReportRRCRange').val()) <= 0){
		showMessage('info','Please Vehicle Name !');
		return false;
	}	
	if(Number($('#fromRRV').val()) <= 0){
		showMessage('info','Please Renewal Type  !');
		return false;
	}
	if(Number($('#toRRV').val()) <= 0){
		showMessage('info','Please Renewal Sub Type  !');
		return false;
	}
}
