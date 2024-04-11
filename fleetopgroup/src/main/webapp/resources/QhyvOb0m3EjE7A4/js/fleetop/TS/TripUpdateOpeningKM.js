var closingkm 	= 0;
var openingkm 	= 0;
var expectedkm 	= 0;
var diffBetClosingAndOpening = 0;
var diffBetExpectedAndOpening_Closing =0;
var startingRange = 0;



function setDataToUpdateOpeningKM(){

	 closingkm 		= Number($("#tripCloseKM").val());
	 expectedkm 	= Number($("#vExpectedOdometer").val());
	 openingkm		= Number($('#tripOpenKM').val());

	 diffBetClosingAndOpening 			= Number(closingkm-openingkm);
	 diffBetExpectedAndOpening_Closing	= Number(expectedkm-diffBetClosingAndOpening);
	 startingRange 						= Number(openingkm-expectedkm);

	/* if(Number(openingkm-diffBetExpectedAndOpening_Closing) < 0 ){
		startingRange = 0;
	}else{
		 startingRange = Number(openingkm-diffBetExpectedAndOpening_Closing);
	}
	 */
		$("#startingKm").html(startingRange);
		$("#endingKm").text(closingkm + expectedkm);
		

}

function updateTripOpeningKM(vid,tripSheetID,tripOpeningKM, tripClosingKM){
	$('#tripOpenKM').val(tripOpeningKM)
	$('#tripCloseKM').val(tripClosingKM)
	$('#vehicleId').val(vid)
	$('#TRIPID').val(tripSheetID)
	setDataToUpdateOpeningKM();
	$('#updateOpenKm').modal('show');
}

function updateTripSheetOpeningKM(){
	
	var tripUpdateOpenKM = Number($('#tripOpenKM').val());
	
	if ($('#validateDirectOdometerUpdate').val() == 'true' || $('#validateDirectOdometerUpdate').val() == true) {
		if (tripUpdateOpenKM >= closingkm) {
			showMessage('errors', 'Trip Opening KM can not be Greater than And Equal To Trip Closing KM! [' + $("#tripClosingKM").val() + ']');
			return false;
		}

		if (tripUpdateOpenKM < startingRange) {
			showMessage('errors', 'Difference Between Opening and Closing Km Can Not Be Greater Than Vehicle Expected Odometer');
			return false;
		}
	}
	var jsonObject			= new Object();
	jsonObject["vid"] 	  			=  $('#vehicleId').val();
	jsonObject["tripSheetID"] 	  	=  $('#TRIPID').val();
	jsonObject["tripOpeningKM"] 	=  tripUpdateOpenKM;
	jsonObject["tripClosingKM"] 	=  $('#tripCloseKM').val();
	console.log('jsonObject ', jsonObject);
	showLayer();
	$.ajax({
             url: "updateTripSheetOpeningKM",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 showMessage('success', 'TripSheet Opening KM Updated Successfully !');
            	 $('#updateOpenKm').modal('hide');
            	 location.reload();
            	 hideLayer();
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});

}