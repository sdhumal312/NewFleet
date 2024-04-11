function validateDriverAndConductorAllRemarksReport()
{
	if(Number($('#driverAllList').val()) <= 0){
		showMessage('info','Please Select Driver/Conductor!');
		return false;
	}	
}