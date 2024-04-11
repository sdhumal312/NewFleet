function validateGroupWiseDriverPointReport()
{
	if(Number($('#workOrderGroup').val()) <= 0){
		showMessage('info','Please Select Group!');
		return false;
	}	
}