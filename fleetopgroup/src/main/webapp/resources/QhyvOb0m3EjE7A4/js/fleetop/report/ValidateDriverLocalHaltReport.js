function validateDriverLocalHaltReport()
{
	if(Number($('#TCGroupWise').val()) <= 0){
		showMessage('info','Please Select Group!');
		return false;
	}	
}