function validateDriverAttendanceReport()
{
	if(Number($('#driverAttendance').val()) <= 0){
		showMessage('info','Please Enter Driver Name!');
		return false;
	}	
}