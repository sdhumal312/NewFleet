var expectedkm = 0;
var openingkm = 0;
var diffBetClosingAndOpening = 0;
var diffBetExpectedAndOpening_Closing = 0;
var diffBetExpectedAndOpening_Closing = 0;


function setDataToUpdateClosingKM(){
	
	 expectedkm 	= Number($("#vExpectedOdometer").val());
	 openingkm		= Number($('#tripOpenKM').val());
	 
	 diffBetClosingAndOpening 			= Number(closingkm-openingkm);
	 diffBetExpectedAndOpening_Closing	= Number(expectedkm-diffBetClosingAndOpening);
	 endingRange						= Number(closingkm+diffBetExpectedAndOpening_Closing);

	 
		$("#startingClosingKm").html(openingkm+1);
		$("#endingClosingKm").text(endingRange);
		
}

function updateTripClosingKM(vid,tripSheetID,tripClosingKM){
	 $('#vid').val(vid);
	 $('#TRIPSHEETID').val(tripSheetID);
	 $('#tripClosingKM').val(tripClosingKM);
	 setDataToUpdateClosingKM();
	$('#updateCloseKm').modal('show');
}

function updateTripSheetClosingKM(){
	
	if ($('#validateDirectOdometerUpdate').val() == 'true' || $('#validateDirectOdometerUpdate').val() == true) {

		if (Number($('#tripOpeningKM').html()) >= Number($('#tripClosingKM').val())) {
			showMessage('errors', 'Trip Closing KM can not be less or equals than Trip Opening KM !');
			return false;
		}
		if (Number($('#tripClosingKM').val()) > endingRange) {
			showMessage('errors', 'Difference Between Opening And Closing Km is can not be greater than Vehicle Expected Odometer !');
			return false;
		}
	}
	
	var jsonObject			= new Object();
	jsonObject["vid"] 	  			=  $('#vid').val();
	jsonObject["tripSheetID"] 	  	=  $('#TRIPSHEETID').val();
	jsonObject["tripClosingKM"] 	=  $('#tripClosingKM').val();
	console.log('jsonObject ', jsonObject);
	showLayer();
	$.ajax({
             url: "updateTripSheetClosingKM",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 $('#closingKM').html($('#tripClosingKM').val());
            	 $('#tripUsaseKm').html(data.tripUsaseKm);
            	 showMessage('success', 'TripSheet Closing KM Uppdated Successfully !');
            	 $('#updateCloseKm').modal('hide');
            	 location.reload();
            	 hideLayer();
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});

}