function validateVehicleWiseServiceEntriesReport()
{
	if(Number($('#SEVehicle').val()) <= 0){
		showMessage('info','Please Select Vehicle Name !');
		return false;
	}	
}

