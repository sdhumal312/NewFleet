function validateDriverCommentReport()
{
	if(Number($('#driverId').val()) <= 0){
		showMessage('info','Please Select Driver!');
		return false;
	}	
}
