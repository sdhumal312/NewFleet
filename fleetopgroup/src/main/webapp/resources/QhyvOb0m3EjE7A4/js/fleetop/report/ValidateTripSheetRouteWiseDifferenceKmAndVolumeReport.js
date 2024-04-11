function validateTripSheetRouteWiseDifferenceKmAndVolumeReport()
{
	
	if(Number($('#GTC_daterange').val()) <= 0){
		showMessage('info','Please Select Date Range!');
		return false;
	}	
}