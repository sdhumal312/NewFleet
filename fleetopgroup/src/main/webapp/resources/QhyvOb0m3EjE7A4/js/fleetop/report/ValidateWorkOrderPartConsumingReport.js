function validateWorkOrderPartConsumingReport()
{
	if(Number($('#workOrderLocation2').val()) <= 0){
		showMessage('info','Please Select Work Location!');
		return false;
	}	
}