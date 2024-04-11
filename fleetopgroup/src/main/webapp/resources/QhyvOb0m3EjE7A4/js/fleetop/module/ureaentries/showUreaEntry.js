$(document).ready(function() {
		showLayer();
		var jsonObject			= new Object();

		jsonObject["ureaEntriesId"] 				=  $('#ureaEntriesId').val();
		
		$.ajax({
	             url: "getShowUreaEntryDetails",
	             type: "POST",
	             dataType: 'json',
	             data: jsonObject,
	             success: function (data) {
	                setData(data);
	                hideLayer();
	             },
	             error: function (e) {
	            	 showMessage('errors', 'Some error occured!')
	            	 hideLayer();
	             }
		});
		
});

function setData(data){
	if(data.ureaEntriesDto != undefined && data.ureaEntriesDto != null){
		var ureaEntriesDto = data.ureaEntriesDto;
		$('#ureaOdometer').html(ureaEntriesDto.ureaOdometer);
		$('#reference').html(ureaEntriesDto.reference);
		$('#ureaEntriesNumber').html('UE-'+ureaEntriesDto.ureaEntriesNumber);
		
		$('#vehicle_registration').html('<a href="showVehicle?vid='+ureaEntriesDto.vid+'" target="_blank">'+ureaEntriesDto.vehicle_registration+'</a>');
		$('#ureaDate').html(ureaEntriesDto.ureaDateStr);
		$('#openingOdometer').html(ureaEntriesDto.ureaOdometerOld);
		$('#closingOdometer').html(ureaEntriesDto.ureaOdometer);
		$('#routeName').html(ureaEntriesDto.routeName);
		
		$('#liters').html(ureaEntriesDto.ureaLiters);
		$('#unitRate').html(ureaEntriesDto.ureaRate);
		$('#discount').html(ureaEntriesDto.discount);
		$('#gst').html(ureaEntriesDto.gst);
		if(ureaEntriesDto.tripSheetNumber != undefined && ureaEntriesDto.tripSheetNumber != null)
			$('#tripSheetId').html('<a href="showTripSheet.in?tripSheetID='+ureaEntriesDto.tripSheetId+'" target="_blank">'+ureaEntriesDto.tripSheetNumber+'</a>');
		
		$('#amount').html(ureaEntriesDto.ureaAmount);
		$('#costPerKM').html(((100 * ureaEntriesDto.ureaLiters) / ureaEntriesDto.ureaAmount).toFixed(2));
		$('#UreaReference').html(ureaEntriesDto.reference);
		$('#comment').html(ureaEntriesDto.comments);
		
		$('#firstDriver').html(ureaEntriesDto.firsDriverName+' '+ureaEntriesDto.firsDriverLastName);
		$('#secondDriver').html(ureaEntriesDto.secDriverName+' '+ureaEntriesDto.secDriverLastName);
		$('#cleaner').html(ureaEntriesDto.cleanerName);
		
		if($('#showFilledLocation').val() == true ||$('#showFilledLocation').val() == 'true' ){
			$('#filledLocation').html(ureaEntriesDto.locationName);
			$('#filledBy').html(ureaEntriesDto.filledBy);
		}
		
		$('#createdOnStr').html(ureaEntriesDto.createdOnStr);
		$('#createdBy').html(ureaEntriesDto.createdBy);
		$('#lastModifiedBy').html(ureaEntriesDto.lastModifiedBy);
		$('#lastModifiedStr').html(ureaEntriesDto.lastModifiedStr);
		if(ureaEntriesDto.meterWorkingStatus == true || ureaEntriesDto.meterWorkingStatus == 'true'){
			$('#meterWorkingStatus').html('Meter Not Woking');
			$("#meterWorkingStatus").addClass('label-danger');
			
		}else{
			$('#meterWorkingStatus').html('Meter Is Woking');
			$("#meterWorkingStatus").addClass('label-success');
		}
		
	}
}
