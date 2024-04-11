function validateDriverMonthWiseESIPFDateRangeReport()
{
	if(Number($('#SelectFuelGroup2').val()) <= 0){
		showMessage('info','Please Select Group!');
		return false;
	}	
}
