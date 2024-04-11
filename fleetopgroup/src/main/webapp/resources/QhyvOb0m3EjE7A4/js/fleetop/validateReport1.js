function validationVehicleWiseDateRangeWeeklyMonthlyYearlyTripCollectionReport(){

	if(Number($('#vehicleGroupId6').val()) <= 0){
	showMessage('info','Please Select Depot!');
	return false;
	}
	if(Number($('#Vehicleofdepot6').val()) <= 0){
	showMessage('info','Please Select Vehicle!');
	return false;
	}
	return true;

	}

function validationRouteWiseDateRangeWeeklyMonthlyYearlyTripCollectionReport(){

	if(Number($('#vehicleGroupId4').val()) <= 0){
	showMessage('info','Please Select Depot!');
	return false;
	}
	 if(Number($('#Routeofdepot4').val()) <= 0){
	showMessage('info','Please Select Route!');
	return false;
	}
	return true;

	}

function validationDriverConductorWiseAdvanceReport(){

	if(Number($('#vehicleGroupId8').val()) <= 0){
	showMessage('info','Please Select Depot!');
	return false;
	}
	
	return true;

	}