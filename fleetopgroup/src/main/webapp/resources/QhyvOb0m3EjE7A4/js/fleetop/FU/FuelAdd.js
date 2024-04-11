$(document).ready(function() {
    $("#driverEnter").hide(), $("#JoBEnter").hide(), $("#Branch_Depart_hide").hide(), $("#to").select2(), $("#tagPicker").select2({
        closeOnSelect: !1
    }),$("#grpvehicleNumber").change(function(){
    	showLayer();
        $.getJSON("getFuelVehicleOdoMerete.in", {
            FuelvehicleID: $(this).val(),
            ajax: "true"
        }, function(e) {
            var t = "";
            t = e.vehicle_Odometer, document.getElementById("grpfuelOdometer").placeholder = t
            $('#vehicle_ExpectedOdameter').val(e.vehicle_ExpectedOdameter);
            $('#vehicle_Odameter').val(e.vehicle_Odometer);
            
            if(e.gpsOdameter != undefined && e.gpsOdameter > 0){
            	$('#gpsOdometer').val(e.gpsOdameter);
            }
            if(e.vehicle_Odometer != undefined && e.vehicle_Odometer > 0){	
            	$('#fuel_meter').val(e.vehicle_Odometer);
            }	
            
			$('#fuel_type').select2('data', {
				id : 1,
				text : 'DIESEL'
			});
            
            hideLayer();
        })
    
})

var tripSheetId		= $('#tripSheetId').val();
var tripSheetNumber = $('#tripSheetNumber').val();
    
if(tripSheetId != undefined && tripSheetId != '' && tripSheetId > 0){
	
setTimeout(function(){ 
	
	$('#FuelSelectVehicle').select2('data', {
		id : $('#vid').val(),
		text : $('#vehicle_registration').val()
	});
	
	if(dropDownLength == 0){
		 $('#tripsheetNumberSelect')
		 .append($("<option></option>")
				 .attr("value",tripSheetId)
				 .text(tripSheetNumber));
	 }
	
	$('#tripsheetNumberSelect').attr("readonly","readonly") 

	 
	 $("#tripsheetNumberSelect").val(tripSheetId);
	
	if($('#DriverFuel').val() != undefined &&  Number($('#tripFristDriverID').val()) > 0){	
//		if($('#showDriverFullName').val() == true || $('#showDriverFullName').val() == 'true'){
			$('#DriverFuel').select2('data', {
				id : $('#tripFristDriverID').val(),
				text : $('#tripFristDriverName').val()+" "+$('#tripFristDriverLastName').val()+" - "+$('#tripFristDriverFatherName').val()
			});
//		}else{
//			$('#DriverFuel').select2('data', {
//				id : $('#tripFristDriverID').val(),
//				text : $('#tripFristDriverName').val()
//			});
//		}
			$("#DriverFuel").select2("readonly", true);
	}
	if($('#Driver2Fuel').val() != undefined &&  Number($('#tripSecDriverID').val()) > 0){
		$('#Driver2Fuel').select2('data', {
			id : $('#tripSecDriverID').val(),
			text : $('#tripSecDriverName').val()
		});
		$("#Driver2Fuel").select2("readonly", true);
	}
	
	if($('#VehicleTOCleanerFuel').val() != undefined && Number($('#tripCleanerID').val()) > 0){
		$('#VehicleTOCleanerFuel').select2('data', {
			id : $('#tripCleanerID').val(),
			text : $('#tripCleanerName').val()
		});
		$("#VehicleTOCleanerFuel").select2("readonly", true);
	}
	if($('#FuelRouteList').val() != undefined &&  Number($('#routeID').val()) > 0){
		$('#FuelRouteList').select2('data', {
			id : $('#routeID').val(),
			text : $('#routeName').val()
		});
		$("#FuelRouteList").select2("readonly", true);
	}
	
	$("#FuelSelectVehicle").select2("readonly", true);
	$('#TripSheetRow').hide();
	$('#fuel_TripsheetNumber').val($('#tripSheetNumber').val());
	setVehicleOnChangeDate($('#vid').val());
	}, 500);
	
	
}

});
$(document).ready(function() {
   $("#DriverFuel").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getDriverSearchAllListFuel.in?Action=FuncionarioSelect2",
            dataType: "json",
            type: "POST",
            contentType: "application/json",
            quietMillis: 50,
            data: function(e) {
                return {
                    term: e
                }
            },
            results: function(e) {
                return {
                    results: $.map(e, function(e) {
                        return {
                        	text: e.driver_empnumber + " - " + e.driver_firstname+" "+e.driver_Lastname +" - "+e.driver_fathername,
                            slug: e.slug,
                            id: e.driver_id
                        }
                    })
                }
            }
        }
    }),$("#Driver2Fuel").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getDriverSearchAllListFuel.in?Action=FuncionarioSelect2",
            dataType: "json",
            type: "POST",
            contentType: "application/json",
            quietMillis: 50,
            data: function(e) {
                return {
                    term: e
                }
            },
            results: function(e) {
                return {
                    results: $.map(e, function(e) {
                        return {
                        	text: e.driver_empnumber + " - " + e.driver_firstname+" "+e.driver_fathername+" "+e.driver_Lastname,
                            slug: e.slug,
                            id: e.driver_id
                        }
                    })
                }
            }
        }
    }),$("#VehicleTOCleanerFuel").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "SearchOnlyAllCleanerNAME.in?Action=FuncionarioSelect2",
            dataType: "json",
            type: "POST",
            contentType: "application/json",
            quietMillis: 50,
            data: function(e) {
                return {
                    term: e
                }
            },
            results: function(e) {
                return {
                    results: $.map(e, function(e) {
                        return {
                            text: e.driver_empnumber + " - " + e.driver_firstname,
                            slug: e.slug,
                            id: e.driver_id
                        }
                    })
                }
            }
        }
    }),$("#FuelRouteList").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getTripRouteSerachList.in?Action=FuncionarioSelect2",
            dataType: "json",
            type: "GET",
            contentType: "application/json",
            quietMillis: 50,
            data: function(e) {
                return {
                    term: e
                }
            },
            results: function(e) {
                return {
                    results: $.map(e, function(e) {
                        return {
                            text: e.routeName,
                            slug: e.slug,
                            id: e.routeID
                        }
                    })
                }
            }
        }
    }),$("#tallyCompanyId").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getTallyCompanySearchList.in?Action=FuncionarioSelect2",
            dataType: "json",
            type: "POST",
            contentType: "application/json",
            quietMillis: 50,
            data: function(a) {
                return {
                    term: a
                }
            },
            results: function(a) {
                return {
                    results: $.map(a, function(a) {
                        return {
                            text: a.companyName ,
                            slug: a.slug,
                            id: a.tallyCompanyId
                        }
                    })
                }
            }
        }
    });
})

function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
}


function saveKMPL(){
	showLayer();
	var jsonObject					= new Object();
	
	if( Number($('#vehicleKmplId').val()) == 0){
		$('#vehicleKmplId').focus();
		showMessage('errors', 'Please Select Vehicle !');
		hideLayer();
		return false;
	}
	
	if( Number($('#expectedMileageFrom').val()) == 0){
		$('#expectedMileageFrom').focus();
		showMessage('errors', 'Please Select Expected Mileage From !');
		hideLayer();
		return false;
	}
	
	if( Number($('#expectedMileageTo').val()) == 0){
		$('#expectedMileageTo').focus();
		showMessage('errors', 'Please Select Expected Mileage To !');
		hideLayer();
		return false;
	}
	
	if( Number($('#expectedMileageTo').val()) <  Number($('#expectedMileageFrom').val()) ){
		$('#expectedMileageTo').focus();
		showMessage('info', 'Expected Mileage_From Can not be greater Expected Mileage_To');
		hideLayer();
		return false;
	}
	

	jsonObject["vehicleKmplId"]					= $('#vehicleKmplId').val();
	jsonObject["expectedMileageFrom"]			= $('#expectedMileageFrom').val();
	jsonObject["expectedMileageTo"]				= $('#expectedMileageTo').val();
	
	$.ajax({
		url: "saveVehicleKmplDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			 
			if(data.vehicle_ExpectedMileage != undefined && data.vehicle_ExpectedMileage_to != undefined){
				$('#expectedMileageFrom').val(data.vehicle_ExpectedMileage);
				$('#expectedMileageTo').val(data.vehicle_ExpectedMileage_to);
			}
			
			$('#addKMPL').modal('hide');
			
			showMessage('success', 'Vehicle KM/L Details Updated Successfully!');
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
}
$(document).ready( function($) {
	
	$('#expectedMileageFrom').keyup(function(e) {
		if($('#expectedMileageFrom').val() > 100){
			$('#expectedMileageFrom').val(0);
			showMessage('info', 'Expected Mileage_From Can not be greater than 100');
			return false;
		}
	}),$('#expectedMileageTo').keyup(function(e) {
		if($('#expectedMileageTo').val() > 100){
			$('#expectedMileageTo').val(0);
			showMessage('info', 'Expected Mileage_To Can not be greater than 100');
			return false;
		}
	})
	
});

