function validateDailyFuelConsumptionReport()
{
	if(Number($('#SelectFuelGroup').val()) <= 0){
		showMessage('info','Please Select Group!');
		return false;
	}	
}