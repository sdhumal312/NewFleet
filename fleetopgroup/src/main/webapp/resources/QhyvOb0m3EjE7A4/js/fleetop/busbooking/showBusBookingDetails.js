$(document).ready(function() {
	getShowBookingDetails();
});

function getShowBookingDetails() {
	
	var jsonObject					= new Object();
	jsonObject["busBookingDetailsId"]		= $("#busBookingDetailsId").val(); 
	  showLayer();
	$.ajax({
		url: "getShowBusBookingDetails.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		
		success: function (data) {
			setBusBookingDetails(data);
			hideLayer();
		},
		error: function (e) {
			console.log("Error : " , e);
		}
	});
}

function setBusBookingDetails(data) {
	if(data.busBooking != undefined){
		
		$('#busBookingNumber').html(data.busBooking.busBookingNumber);
		if(data.configuration.showBookingDate){
			$('#bookingDate').html(data.busBooking.bookingDateStr);
		}else{
			$('#busBookingRow').hide();
		}
		
		$('#bookingRefNumber').html(data.busBooking.bookingRefNumber);
		
		if(data.configuration.showVehicleType){
			$('#vehicleType').html(data.busBooking.vehicleType);
		}else{
			$('#vehicleTypeRow').hide();
		}
		
		
		$('#corporateName').html(data.busBooking.corporateName);
		if(data.configuration.showMobileNumber){
			$('#partyMobileNumber').html(data.busBooking.partyMobileNumber);
		}else{
			$('#partyMobileNumberRow').hide();
		}
		
		if(data.configuration.showGstNumber){
			$('#partyGSTNo').html(data.busBooking.partyGSTNo);
		}else{
			$('#partyGSTNoRow').hide();
		}
		if(data.configuration.showPartyAddress){
			$('#partyAddress').html(data.busBooking.partyAddress);
		}else{
			$('#partyAddressRow').hide();
		}
		if(data.configuration.showReportTo){
			$('#reportToName').html(data.busBooking.reportToName);
		}else{
			$('#reportToNameRow').hide();
		}
		if(data.configuration.showReportToMob){
			$('#reportToMobileNumber').html(data.busBooking.reportToMobileNumber);
		}else{
			$('#reportToMobileNumberRow').hide();
		}
		if(data.configuration.showReportToAddress){
			$('#billingAddress').html(data.busBooking.billingAddress);
		}else{
			$('#billingAddressRow').hide();
		}
		
		
		$('#tripStartDateTime').html(data.busBooking.tripStartDateStr);
		$('#tripEndDateTime').html(data.busBooking.tripEndDateStr);
		if(data.configuration.showPlaceOfVisit){
			$('#placeOfVisit').html(data.busBooking.placeOfVisit);
		}else{
			$('#placeOfVisitRow').hide();
		}
		
		$('#pickUpAddress').html(data.busBooking.pickUpAddress);
		$('#dropAddress').html(data.busBooking.dropAddress);
		if(data.configuration.showPerKMRate){
			$('#rate').html(data.busBooking.rate);
		}else{
			$('#rateRow').hide();
		}
		
		if(data.configuration.showHireAmount){
			$('#hireAmount').html(data.busBooking.hireAmount);
		}else{
			$('#hireAmountRow').hide();
		}
		
		
		$('#createdBy').html(data.busBooking.createdBy);
		$('#createdOn').html(data.busBooking.createdOnStr);
		$('#lastupdatedBy').html(data.busBooking.lastModifiedBy);
		$('#lastUpdated').html(data.busBooking.lastModifiedOnStr);
		
		$('#tripSheetNumber').html(data.busBooking.tripSheetNumber);
		$('#vehicleNumber').html(data.busBooking.vehicle_registration);
		$('#remark').html(data.busBooking.remark);
		
	}else{
		showMessage('errors', 'No Record Found !');
	}
}
