function validateVehicleWisePartConsumptionAndUsageReport()
{
	if(Number($('#ReportSelectVehicle1').val()) <= 0){
		showMessage('info','Please Select Vehicle Name !');
		return false;
	}	
}
