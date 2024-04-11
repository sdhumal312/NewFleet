function validateDriverDetailsReport()
{
	if(Number($('#driverGroup').val()) <= 0){
		showMessage('info','Please Select Group!');
		return false;
	}	
}