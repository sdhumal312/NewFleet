 function CreateTripFromLhpv(vid, vehicle_registration, lHPVNumber, newTripSheet){
	 
	 if(newTripSheet == false || newTripSheet == 'false'){
		 
		 showLayer();
		 setTimeout(function(){ 
			 $("#TripSelectVehicle").select2("data",{
				 id:Number(vid),
				 text:vehicle_registration
			 });
			 $("#TripSelectVehicle").select2("readonly", true);
			 getLastTripSheetDetailsToCopy(getCurrentDate(),getCurrentTime(), false);
			 $('#tripBookref').val('LHPV No : '+lHPVNumber);
			 hideLayer();
		 }, 500);
	 }
	 
	 
 }