function validateDriverAttendancePointReport()
{
	if(Number($('#JAMAdriverList2').val()) <= 0){
		showMessage('info','Please Enter Driver Name!');
		return false;
	}	
}