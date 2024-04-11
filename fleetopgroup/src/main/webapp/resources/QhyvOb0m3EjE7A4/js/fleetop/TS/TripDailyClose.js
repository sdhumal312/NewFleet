function validateCloseOdometer(){
	 var noOfRoundTrip	= Number($('#noOfRoundTrip').val());
	    if(noOfRoundTrip <= 0){
	    	showMessage('info', 'please enter no of round trip !');
	    	return false;
	    }
	var routeApproxKM	= Number($('#routeApproxKM').val());
	var tripOpenKM		= Number($('#tripOpenKM').val());
	var allwedRange		= (routeApproxKM + (routeApproxKM * 10/100)) * Number($('#noOfRoundTrip').val()) + tripOpenKM;
	var tripCloseKM		= Number($('#TRIP_CLOSE_KM').val());
	if(tripCloseKM > allwedRange || tripCloseKM < tripOpenKM){
		showMessage('errors','You can enter Closing KM Only Between '+tripOpenKM+ ' and '+allwedRange);
		return false;
	}
	$('#submit').hide();
	return true;
}
$(document).ready(function() {
	    var routeApproxKM	= Number($('#routeApproxKM').val());
	    var tripOpenKM		= Number($('#tripOpenKM').val());
	    var noOfRoundTrip	= Number($('#noOfRoundTrip').val());
	    if(noOfRoundTrip > 0){
	    	var allwedRange		= (routeApproxKM + (routeApproxKM * 10/100)) * noOfRoundTrip + tripOpenKM;
		    var text = 'Allowed Range '+tripOpenKM+' - '+allwedRange+'';
		    $('#range').val(text);
		    $('#range').show();
	    }else{
	    	 $("#noOfRoundTrip").attr("readonly", false); 
	    	$('#range').hide();
	    }
});