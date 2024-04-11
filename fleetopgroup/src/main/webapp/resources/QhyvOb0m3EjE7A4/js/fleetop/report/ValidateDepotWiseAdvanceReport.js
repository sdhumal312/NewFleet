function validateDepotWiseAdvanceReport()
{
	if(Number($('#SelectFuelGroup4').val()) <= 0){
		showMessage('info','Please Select Depot!');
		return false;
	}	
}