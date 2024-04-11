function validateDepotWisePenaltyReport()
{
	if(Number($('#SelectFuelGroup3').val()) <= 0){
		showMessage('info','Please Select Depot!');
		return false;
	}	
}