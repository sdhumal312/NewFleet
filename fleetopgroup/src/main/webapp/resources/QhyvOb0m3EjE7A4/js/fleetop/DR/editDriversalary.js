function validateEditInput(){
	if($('#vehicleGroupId').val() == 0){
		showMessage('errors', 'Please select vehicle group !');
		return false;
	}
	if($('#driJobId').val() == 0){
		showMessage('errors', 'Please select Driver Job Type !');
		return false;
	}
	return true;
}

