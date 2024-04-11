function validateVehicleWiseFuelMileage(){
	
	if(Number($('#vehicleGrpId7').val()) <= 0){
		showMessage('info','Please Select Depot!');
		return false;
	}
	/*else if(Number($('#Vehicleofdepot7').val()) <= 0){
		showMessage('info','Please Select Vehicle Name!');
		return false;
	}*/
	return true;
	
}

function validateDriverConductorWisePenaltyReport(){
	
	if(Number($('#vehicleGrpId10').val()) <= 0){
		showMessage('info','Please Select Depot!');
		return false;
	}
	else if(Number($('#Conductorofdepot').val()) <= 0){
		showMessage('info','Please Select Driver/Conductor!');
		return false;
	}
	return true;
	
}


function validateVehicleWiseFuelMileageReport(){
	
	if(Number($('#vehicleGrpId11').val()) <= 0){
		showMessage('info','Please Select Depot!');
		return false;
	}
	else if(Number($('#driverofdepot').val()) <= 0){
		showMessage('info','Please Select Vehicle Name!');
		return false;
	}
	return true;
	
}

function validateDriverWiseFuelMilageReport(){
	
	if(Number($('#vehicleGrpId18').val()) <= 0){
		showMessage('info','Please Select Depot!');
		return false;
	}
	else if(Number($('#driverofdepot18').val()) <= 0){
		showMessage('info','Please Select Driver!');
		return false;
	}
	return true;
	
}

function validateConductorWiseTripCollectionReport(){
	
	if(Number($('#vehicleGrpId9').val()) <= 0){
		showMessage('info','Please Select Depot!');
		return false;
	}
	else if(Number($('#Conductorofdepot').val()) <= 0){
		showMessage('info','Please Select Conductor!');
		return false;
	}
	return true;
	
}