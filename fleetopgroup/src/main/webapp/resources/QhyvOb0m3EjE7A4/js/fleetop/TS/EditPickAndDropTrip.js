var partyRate = 1;
var locationId = 0;

$(document).ready(function() {
	var editPickAndDropId = $('#editPickAndDropId').val();
	getTripsheetPickDropDispatchDetails(editPickAndDropId);
});

function getTripsheetPickDropDispatchDetails(editPickAndDropId) {
	
	showLayer();
	var jsonObject							= new Object();
	jsonObject["tripsheetPickAndDropId"]	= editPickAndDropId;

	$.ajax({
		url: "getTripsheetPickDropDispatchDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setTripsheetPickDropDispatchDetails(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setTripsheetPickDropDispatchDetails(data){
	
	console.log("dataedit...",data);
	
	var VID = data.pickOrDropDetails.vid;
	var VText = data.pickOrDropDetails.vehicleRegistration;
	$('#vid').select2('data', {
	id : VID,
	text : VText
	});
	
	var DID = data.pickOrDropDetails.tripFristDriverID;
	var DText = data.pickOrDropDetails.driverName;
	$('#tripFristDriverID').select2('data', {
	id : DID,
	text : DText
	});
	
	var VENID = data.pickOrDropDetails.vendorId;
	var VENText = data.pickOrDropDetails.vendorName;
	$('#vendorId').select2('data', {
	id : VENID,
	text : VENText
	});
	
	$('#newParty').val(data.pickOrDropDetails.newVendorName);
	
	if(data.pickOrDropDetails.vendorId > 0){
		$('.oldParty').removeClass('hide');
	} else {
		$('.newParty').removeClass('hide');
	}
	
	
	if(data.pickOrDropDetails.pickOrDropStatus == 1){
		console.log("pick..")
		$('.pk').addClass('active');
		$('.pick').removeClass('hide');
		
		var PID = data.pickOrDropDetails.pickOrDropId;
		var PText = data.pickOrDropDetails.locationName;
		$('#pickId').select2('data', {
		id : PID,
		text : PText
		});
		
		var DText = "";
		$('#dropId').select2('data', {
		id : 0,
		text : DText
		});
	}
	
	if(data.pickOrDropDetails.pickOrDropStatus == 2){
		console.log("drop..")
		$('.dp').addClass('active');
		$('.drop').removeClass('hide');
		
		var PID = data.pickOrDropDetails.pickOrDropId;
		var PText = data.pickOrDropDetails.locationName;
		$('#dropId').select2('data', {
		id : PID,
		text : PText
		});
		
		var DText = "";
		$('#pickId').select2('data', {
		id : 0,
		text : DText
		});
	}
	
	$('#newRouteName').val(data.pickOrDropDetails.newPickOrDropLocationName);
	locationId = data.pickOrDropDetails.pickOrDropId;
	
	if(data.pickOrDropDetails.pickOrDropId > 0){
		
	} else {
		$('.newRoute').removeClass('hide');
		
		if(data.pickOrDropDetails.pickOrDropStatus == 1){
			$('.pick').addClass('hide');
		} else {
			$('.drop').addClass('hide');
		}
	}
	
	
	$('#journeyDate').val(data.pickOrDropDetails.journeyDateStr2);
	$('#journeyTime').val(data.pickOrDropDetails.journeyTimeStr);
	$('#rate').val(data.pickOrDropDetails.rate);
	$('#tripUsageKM').val(data.pickOrDropDetails.tripUsageKM);
	$('#amount').val(data.pickOrDropDetails.amount);
	$('#tripTotalAdvance').val(data.pickOrDropDetails.tripTotalAdvance);
	$('#remark').val(data.pickOrDropDetails.remark);
	
}


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
                    	console.log('data ', e);
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
    }),$("#vendorId").change(function() {
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

function pickOrDrop(obj){
	
	var pickAndDropId = obj.value;
	console.log("id....",pickAndDropId);
	
	if(locationId > 0){
		if(pickAndDropId == 1){
			$('.pick').removeClass('hide');
			$('.drop').addClass('hide');
			$('#dropId').val(0);
		} else {
			$('.pick').addClass('hide');
			$('.drop').removeClass('hide');
			
			//$('.dp').addClass('active');
			$('#pickId').val(0);
		}
	}
	
}

/*function getRate(){
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
	
	if(Number($("#vendorId").val()) > 0){
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
	
	if(locationId > 0){
		
		var status = document.getElementsByName("pickOrDropStatus");
		
		/*if( (status[0].checked == false) && (status[1].checked == false) ){
			showMessage('errors','Please select Pick Or DropStatus !');
			return false;
		}*/
		
		if( ($(".pk").hasClass("active")) && (Number($("#pickId").val()) == 0) ){
			$("#pickId").select2('focus');
			showMessage('errors','Please select PickUp Point !');
			return false;
		}
		
		if( ($(".dp").hasClass("active")) && (Number($("#dropId").val()) == 0) ){
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
	if( $(".pk").hasClass("active") ){
		pickDropStatus = 1;
	} 
	
	if( $(".dp").hasClass("active") ){
		pickDropStatus = 2;
	}
	
	
	var jsonObject							= new Object();
	
	jsonObject["tripsheetPickAndDropId"]	= $('#editPickAndDropId').val();
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
	
	console.log("jsonObject...",jsonObject);
	
	showLayer();
	$.ajax({
		url: "updatePickAndDropTrip",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			if(data.InvoiceMade != undefined && data.InvoiceMade == true){
				showMessage('info', 'Invoice already created hence cannot edit Tripsheet!');
				hideLayer();
			}
			
			if(data.updateDone != undefined && data.updateDone == true){
				window.location.replace("showDispatchedPickAndDropTrip?dispatchPickAndDropId="+data.TripsheetPickAndDropId+"");
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
