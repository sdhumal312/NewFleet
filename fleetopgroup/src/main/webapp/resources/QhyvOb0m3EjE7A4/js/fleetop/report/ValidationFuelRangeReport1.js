function validateFuelRangeReport1()
{
	if(Number($('#ReportVehicleFuelRange').val()) <= 0){
		showMessage('info','Please Select Vehicle ');
		return false;
	}	
}
