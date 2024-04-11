function validateDepotTripWiseReport()
{
	if(Number($('#TCGroupWise2').val()) <= 0){
		showMessage('info','Please Select Depot Name !');
		return false;
	}	
	if(Number($('#RouteList').val()) <= 0){
		showMessage('info','Please Select Route Service!');
		return false;
	}	
	if(Number($('#RouteSubList').val()) <= 0){
		showMessage('info','Please Select Sub Route Service !');
		return false;
	}	
}