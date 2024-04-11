function validateFuelRangeReport()
{
	if(Number($('#SelectVehicleGroup').val()) <= 0){
		showMessage('info','Please Select  Group!');
		return false;
	}	
}
