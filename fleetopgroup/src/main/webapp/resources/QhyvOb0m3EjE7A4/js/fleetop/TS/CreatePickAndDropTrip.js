var partyRate = 1;

$(document).ready(function() {
	$("#vid").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getVehicleListFuel.in?Action=FuncionarioSelect2",
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
                            text: e.vehicle_registration,
                            slug: e.slug,
                            id: e.vid
                        }
                    })
                }
            }
        }
    }),
    
   $("#tripFristDriverID").select2({
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
                            text: e.driver_empnumber + " - " + e.driver_firstname,
                            slug: e.slug,
                            id: e.driver_id
                        }
                    })
                }
            }
        }
    }), 
    
    $("#vendorId").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getPartyListByName.in?Action=FuncionarioSelect2",
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
                    	console.log('dataparty ', e);
                        return {
                            text: e.corporateName,
                            slug: e.slug,
                            id: e.corporateAccountId,
                            mobileNumber : e.mobileNumber,
                            gstNumber 	 : e.gstNumber,
                            perKMRate    : e.perKMRate,
                            partyAddress : e.address
                        }
                    })
                }
            }
        }
    }), $("#vendorId").change(function() {
    	var partyData	= $('#vendorId').select2('data');
    	$('#rate').val(partyData.perKMRate);
    });
    
    $("#pickId").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "pickAndDropLocationAutoComplete.in?Action=FuncionarioSelect2",
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
                            text: e.locationName,
                            slug: e.slug,
                            id: e.pickAndDropLocationId
                        }
                    })
                }
            }
        }
    }),
    
    $("#dropId").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "pickAndDropLocationAutoComplete.in?Action=FuncionarioSelect2",
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
                            text: e.locationName,
                            slug: e.slug,
                            id: e.pickAndDropLocationId
                        }
                    })
                }
            }
        }
    })
})

$(document).ready(function() {
    $("#vid").change(function() {
    	showLayer();
        0 != $(this).val() && $.getJSON("getTripVehicleOdoMerete.in", {
            FuelvehicleID: $(this).val(),
            ajax: "true"
        }, function(e) {
        	
        	if(e.vehicleGroupId == 106){         // When vehicle group Company Car that time party name should come from party master only
        		$('.party').addClass('hide');
        	} else {
        		$('.party').removeClass('hide');
        	}
        	
        	if(e.vStatusId != 1 && e.vStatusId != 5){
        		hideLayer();
        		showMessage('errors','You Can not create TripSheet , Vehicle Status is : '+e.vehicle_Status);
        		$("#vid").select2("val", "");
        		return false;
        	}
        })
        hideLayer();
    })
})

function newParty(){
	
	var newLoc = $("#newPartyId").val();
	
	if(newLoc == 0){
		$("#newPartyId").val(1);
		$('.newParty').removeClass('hide');
		$('.oldParty').addClass('hide');
	} else {
		$("#newPartyId").val(0);
		$('.newParty').addClass('hide');
		$('.oldParty').removeClass('hide');
	}
}

function pickOrDrop(obj){
	
	var pickAndDropId = obj.value;
	
	if(pickAndDropId == 1){
		$('.pick').removeClass('hide');
		$('.drop').addClass('hide');
		
		var DText = "";
		$('#dropId').select2('data', {
		id : 0,
		text : DText
		});
		
	} else {
		$('.pick').addClass('hide');
		$('.drop').removeClass('hide');
		
		var DText = "";
		$('#pickId').select2('data', {
		id : 0,
		text : DText
		});
	}
	
}

function newLocation(){
	
	var newLoc = $("#newLocationId").val();
	var status = document.getElementsByName("pickOrDropStatus");
	
	if(newLoc == 0){
		
		if((status[0].checked == true)){
			$('.pick').addClass('hide');
		}
		
		if((status[1].checked == true)){
			$('.drop').addClass('hide');
		}
		
		$("#newLocationId").val(1);
		$('.newRoute').removeClass('hide');
		
	} else {
		
		if((status[0].checked == true)){
			$('.pick').removeClass('hide');
		}
		
		if((status[1].checked == true)){
			$('.drop').removeClass('hide');
		}

		$("#newLocationId").val(0);
		$('.newRoute').addClass('hide');
	}
}

/*function getRate(){
	console.log("mobileNumber...",mobileNumber);
	$("#rate").val(partyRate);
}*/

function calAmnt(){
	
	if($("#tripUsageKM").val() != null && $("#tripUsageKM").val().trim() != ''){
		var amount = $("#tripUsageKM").val() * $("#rate").val();
		$("#amount").val(amount.toFixed(2));
	}
}


function validateDispatch(){
	
	if(Number($("#vid").val()) == 0){
		$("#vid").select2('focus');
		showMessage('errors','Please select Vehicle !');
		return false;
	}
	
	if($("#journeyDate").val() == ""){
		$("#journeyDate").focus();
		showMessage('errors','Please select Date !');
		return false;
	}
	
	if($("#journeyTime").val()  != null && $("#journeyTime").val().trim() == ''){
			$("#journeyTime").focus();
			showMessage('errors','Please select Time !');
			return false;
	}
	
	if(Number($("#tripFristDriverID").val()) == 0){
		$("#tripFristDriverID").select2('focus');
		showMessage('errors','Please select Driver !');
		return false;
	}
	
	if ($('.newParty').hasClass('hide')) {
	  if(Number($("#vendorId").val()) == 0){
		$("#vendorId").select2('focus');
		showMessage('errors','Please select Vendor !');
		return false;
	  }
	} else {
		if($("#newParty").val() == ""){
			$("#newParty").focus();
			showMessage('errors','Please Select New Party Name !');
			return false;
		}
	}
	
	if( Number($("#rate").val()) <= 0) {
		$("#rate").select2('focus');
		showMessage('errors','Please select Rate!');
		return false;
	}
	
	if ($('.newRoute').hasClass('hide')) {
		
		var status = document.getElementsByName("pickOrDropStatus");
		
		if( (status[0].checked == false) && (status[1].checked == false) ){
			showMessage('errors','Please select Pick Or DropStatus !');
			return false;
		}
		
		if( (status[0].checked == true) && (Number($("#pickId").val()) == 0) ){
			$("#pickId").select2('focus');
			showMessage('errors','Please select PickUp Point !');
			return false;
		}
		
		if( (status[1].checked == true) && (Number($("#dropId").val()) == 0) ){
			$("#dropId").select2('focus');
			showMessage('errors','Please select Drop Point !');
			return false;
		}
		
	} else {
		
		if($("#newRouteName").val() == ""){
			$("#newRouteName").focus();
			showMessage('errors','Please Select New Pick/Drop Location !');
			return false;
		}
		
	}
	
	
	if( Number($("#tripUsageKM").val()) <= 0) {
		$("#tripUsageKM").select2('focus');
		showMessage('errors','Please select Usage KM!');
		return false;
	}
	
	return true;
}	

function dispatchPickAndDropTrip() {
	
	if(!validateDispatch()){
		return false;
	}
	
	var pickDropStatus = 1;
	var status = document.getElementsByName("pickOrDropStatus");
	if((status[0].checked == true)){
		pickDropStatus = 1;
	} else {
		pickDropStatus = 2;
	}
	
	
	var jsonObject							= new Object();
	
	jsonObject["vid"]						= $('#vid').val();
	jsonObject["journeyDate"]				= $('#journeyDate').val();
	jsonObject["journeyTime"]				= $('#journeyTime').val();
	jsonObject["tripFristDriverID"]			= $('#tripFristDriverID').val();
	jsonObject["vendorId"]					= $('#vendorId').val();
	jsonObject["newParty"]					= $('#newParty').val();
	jsonObject["rate"]						= $('#rate').val();
	jsonObject["pickDropStatus"]			= pickDropStatus;
	jsonObject["pickId"]					= $('#pickId').val();
	jsonObject["dropId"]					= $('#dropId').val();
	jsonObject["newRouteName"]				= $('#newRouteName').val();
	jsonObject["tripUsageKM"]				= $('#tripUsageKM').val();
	jsonObject["amount"]					= $('#amount').val();
	jsonObject["tripTotalAdvance"]			= $('#tripTotalAdvance').val();
	jsonObject["remark"]					= $('#remark').val();
	
	console.log("jsonObject..",jsonObject);
	
	showLayer();
	$.ajax({
		url: "dispatchPickAndDropTrip",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			if(data.sequenceNotFound != undefined && data.sequenceNotFound == true){
				showMessage('errors', 'Sequence not found please contact to system Administrator !');
				hideLayer();
			} else {
				window.location.replace("showDispatchedPickAndDropTrip?dispatchPickAndDropId="+data.dispatchPickAndDropId+"");
				hideLayer();
			}
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function searchTripsheet(){
	
	if($("#tripStutes").val()  != null && $("#tripStutes").val().trim() != ''){
		showLayer();
		var jsonObject						= new Object();
		jsonObject["tripsheetNumber"]		= $("#tripStutes").val();

		$.ajax({
			url: "searchTripsheetNumber",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				console.log('data : ', data);
				
				if(data.tripsheetNoFound != undefined && data.tripsheetNoFound == true){
					window.location.replace("showDispatchedPickAndDropTrip?dispatchPickAndDropId="+data.tripsheetPickAndDropId+"");
				} else {
					hideLayer();
					showMessage('info', 'Tripsheet Number Not Found. Please Enter correct Tripsheet Number');
				}
				
				hideLayer();
			},
			error: function (e) {
				hideLayer();
				showMessage('errors', 'Some Error Occurred!');
			}
		});
		
	} else {
		showMessage('info', 'Please Enter correct Tripsheet Number');
	}
	
}