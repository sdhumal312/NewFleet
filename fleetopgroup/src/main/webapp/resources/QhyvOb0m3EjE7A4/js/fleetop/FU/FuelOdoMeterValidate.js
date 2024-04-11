function validateOdometerInTripDaily(){

	var validateOdometerInTripDailySheet	= $('#validateOdometerInTripDailySheet').val();
	if(validateOdometerInTripDailySheet == 'false' || validateOdometerInTripDailySheet == false){
		return true;
	}
	
	var allowedPerDiffInOdometer			= Number($('#allowedPerDiffInOdometer').val());
	var tripRouteApproxKm					= Number($('#tripRouteApproxKm').val());
	var noOfRoundTrip						= Number($('#noOfRoundTrip').val());
	var fuel_meter							= Number($('#fuel_meter').val());
	var maxAllowedOdoMeter					= ((tripRouteApproxKm * noOfRoundTrip) + (tripRouteApproxKm * noOfRoundTrip) * allowedPerDiffInOdometer/100) + Number($('#tripOpenKm').val());
	if(fuel_meter < Number($('#tripOpenKm').val()) || fuel_meter > maxAllowedOdoMeter){
		showMessage('info','Please enter Odometer Between '+ Number($('#tripOpenKm').val()) + ' - '+maxAllowedOdoMeter);
		$('#fuel_meter').focus();
		return false;
	}
	return true;
}

$(document).ready(function() {
	var validateOdometerInTripDailySheet	= $('#validateOdometerInTripDailySheet').val();
	if(validateOdometerInTripDailySheet == 'false' || validateOdometerInTripDailySheet == false){
		return true;
	}
	var allowedPerDiffInOdometer			= Number($('#allowedPerDiffInOdometer').val());
	var tripRouteApproxKm					= Number($('#tripRouteApproxKm').val());
	var noOfRoundTrip						= Number($('#noOfRoundTrip').val());
	var maxAllowedOdoMeter					= ((tripRouteApproxKm * noOfRoundTrip) + (tripRouteApproxKm * noOfRoundTrip) * allowedPerDiffInOdometer/100) + Number($('#tripOpenKm').val());
	$('#allowedRange').show();
	$('#allowedRange').val('Odometer Allowed Range '+Number($('#tripOpenKm').val())+' - '+ maxAllowedOdoMeter );
	
});