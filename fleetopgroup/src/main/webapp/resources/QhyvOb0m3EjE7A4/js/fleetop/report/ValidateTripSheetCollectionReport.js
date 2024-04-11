function validateTripSheetCollectionReport()
{
	if(Number($('#workOrderGroup').val()) <= 0){
		showMessage('info','Please Select Vehicle  Group!');
		return false;
	}	
}