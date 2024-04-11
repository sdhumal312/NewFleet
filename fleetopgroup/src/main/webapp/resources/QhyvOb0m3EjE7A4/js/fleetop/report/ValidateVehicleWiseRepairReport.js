function validateVehicleWiseRepairReport()
{
	if($('#showUserInVehicleRepairReport').val() == 'false' || $('#showUserInVehicleRepairReport').val() == false){
		
		if(Number($('#ReportSelectVehicle').val()) <= 0)
		{
			showMessage('info','Please Enter Vehicle Name !');
			return false;
		}		
	}
}
