function validateUserWiseTripSheetAdvancesReport()
{
	if(Number($('#workOrderGroup').val()) <= 0){
		showMessage('info','Please Select Group!');
		return false;
	}
	if(Number($('#subscribe').val()) <= 0){
		showMessage('info','Please Select Advance User Name!');
		return false;
	}

}