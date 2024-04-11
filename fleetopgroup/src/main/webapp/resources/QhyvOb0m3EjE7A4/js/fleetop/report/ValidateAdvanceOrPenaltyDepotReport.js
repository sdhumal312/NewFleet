function validateAdvanceOrPenaltyDepotReport()
{
	if(Number($('#SelectFuelGroup6').val()) <= 0){
		showMessage('info','Please Select Depot!');
		return false;
	}	
}
