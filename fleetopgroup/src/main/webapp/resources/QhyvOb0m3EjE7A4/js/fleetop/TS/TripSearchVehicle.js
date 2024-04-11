$(document).ready(function() {
	
	$("#TripSelectVehicle_ID").select2({
		minimumInputLength : 2,
		minimumResultsForSearch : 10,
		ajax : {
			url : "getVehicleSearchServiceEntrie.in?Action=FuncionarioSelect2",
			dataType : "json",
			type : "POST",
			contentType : "application/json",
			quietMillis : 50,
			data : function(e) {
				return {
					term : e
				}
			},
			results : function(e) {
				return {
					results : $.map(e, function(e) {
						return {
							text : e.vehicle_registration,
							slug : e.slug,
							id : e.vid
						}
					})
				}
			}
		}
	});
});
$(document).ready(function() {
	$("#TripSelectVehicle").select2({
		minimumInputLength : 2,
		minimumResultsForSearch : 10,
		ajax : {
			url : "../getVehicleSearchServiceEntrie.in?Action=FuncionarioSelect2",
			dataType : "json",
			type : "POST",
			contentType : "application/json",
			quietMillis : 50,
			data : function(e) {
				return {
					term : e
				}
			},
			results : function(e) {
				return {
					results : $.map(e, function(e) {
						return {
							text : e.vehicle_registration,
							slug : e.slug,
							id : e.vid
						}
					})
				}
			}
		}
	})
});

function checkUrlParameterForVid(vid){
	var jsonObject			= new Object();
	jsonObject["vid"] 		=  vid;
	jsonObject["companyId"] =  $('#companyId').val();
	jsonObject["userId"] 	=  $('#userId').val();
	console.log('jsonObject : ', jsonObject);
	
	submitVehicleSearch(jsonObject);
}

function updateTripClosingKM(vid,tripSheetID,tripClosingKM){
	$('#updateCloseKm').modal('show');
}

function updateTripSheetClosingKM(){
	var jsonObject			= new Object();
	jsonObject["vid"] 	  			=  $('#vid').val();
	jsonObject["tripSheetID"] 	  	=  $('#TRIPSHEETID').val();
	jsonObject["tripClosingKM"] 	=  $('#tripClosingKM').val();
	
	showLayer();
	$.ajax({
             url: "updateTripSheetClosingKM",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 $('#closingKM').val($('#tripClosingKM').val());
            	 $('#tripUsaseKm').val(data.tripUsaseKm);
            	 showMessage('success', 'TripSheet Closing KM Uppdated Successfully !');
            	 $('#updateCloseKm').modal('hide');
            	 hideLayer();
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});

}

function showDispatchTripPrint(tripsheetId){
	childwin = window.open('printDispatchTripsheet?id='+tripsheetId,'newwindow', config='height=300,width=425, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, directories=no, status=no');
}