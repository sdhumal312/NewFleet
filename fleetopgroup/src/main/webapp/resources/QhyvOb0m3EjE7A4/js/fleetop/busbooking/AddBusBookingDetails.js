$(document).ready(function() {
	
    $("#partyId").select2({
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
    }),$("#partyId").change(function() {
    	var partyData	= $('#partyId').select2('data');
    	$('#partyGSTNo').val(partyData.gstNumber);
    	$('#partyMobileNumber').val(partyData.mobileNumber);
    	$('#partyAddress').val(partyData.partyAddress);
    	$('#rate').val(partyData.perKMRate);
    });
	
    $("#vehicleTypeId").select2( {
		minimumInputLength:3,
		minimumResultsForSearch:10,
		ajax:{
		url:"getVehicleTypeByName.in?Action=FuncionarioSelect2",
		dataType:"json",
		type:"POST",
		contentType:"application/json",
		quietMillis:50,
		data:function(e){
			return{term:e}
			},
		results:function(e){
			return{
				results:$.map(e,function(e){
					return {
						text:e.vtype,slug:e.slug,id:e.tid
						}
					})
				}
			}
		}
	});
	
	$("#selectVendor").select2( {
		minimumInputLength:3,
		minimumResultsForSearch:10,
		ajax:{
		url:"getVendorSearchListInventory.in?Action=FuncionarioSelect2",
		dataType:"json",
		type:"POST",
		contentType:"application/json",
		quietMillis:50,
		data:function(e){
			return{term:e}
			},
		results:function(e){
			return{
				results:$.map(e,function(e){
					return {
						text:e.vendorName+" - "+e.vendorLocation,slug:e.slug,id:e.vendorId
						}
					})
				}
			}
		}
	});
});


function saveBusBookingDetails() {
	if(validateBusBookingDetails()) {
		showLayer();
		var jsonObject					= new Object();
		
		jsonObject["bookingRefNumber"]		= $("#bookingRefNumber").val(); 
		jsonObject["busBookingDate"]		= $("#busBookingDate").val();		
		jsonObject["partyId"]				= $("#partyId").val();
		jsonObject["partyGSTNo"]			= $("#partyGSTNo").val();
		jsonObject["partyMobileNumber"]		= $("#partyMobileNumber").val();
		jsonObject["partyAddress"]			= $("#partyAddress").val();
		jsonObject["reportToName"]			= $("#reportToName").val();
		jsonObject["reportToMobileNumber"]	= $("#reportToMobileNumber").val();
		jsonObject["billingAddress"]		= $("#billingAddress").val();
		jsonObject["vehicleTypeId"]			= $("#vehicleTypeId").val();
		jsonObject["tripStartDate"]			= $("#tripStartDate").val();
		jsonObject["startTime"]				= $("#startTime").val();
		jsonObject["tripEndDate"]			= $("#tripEndDate").val();
		jsonObject["endTime"]				= $("#endTime").val();
		jsonObject["placeOfVisit"]			= $("#placeOfVisit").val();
		jsonObject["pickUpAddress"]			= $("#pickUpAddress").val();
		jsonObject["dropAddress"]			= $("#dropAddress").val();
		jsonObject["rate"]					= $("#rate").val();
		jsonObject["hireAmount"]			= $("#hireAmount").val();
		jsonObject["remark"]				= $("#remark").val();
		jsonObject["bookedBy"]				= $("#bookedBy").val();
		
		jsonObject["companyId"]				= $("#companyId").val();
		jsonObject["userId"]				= $("#userId").val();
		
		$.ajax({
			url: "saveBusBookingDetails.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			
			success: function (data) {
				if(data.sequenceNotFound){
					showMessage("errors", "Sequence not found Please contact to system administrator !");
					hideLayer();
				}else{
					showMessage("success", "Bus Booking Details Saved Successfully.");
					showLayer();
					setTimeout(function(){ hideLayer();location.replace('viewBusBookingDetails.in'); }, 500);
				}
			},
			error: function (e) {
			}
		});
		
	}
}

function validateBusBookingDetails() {
	if($('#validateBookingRef').val() == true || $('#validateBookingRef').val() == 'true'){
	if($('#bookingRefNumber').val() == undefined || $('#bookingRefNumber').val().trim() == ''){
		showMessage('errors','Please enter Booking Ref !');
		$('#bookingRefNumber').focus();
		return false
		}
	
	}
	if($('#partyId').val() == undefined || Number($('#partyId').val()) <= 0){
		showMessage('errors','Please Select party name !');
		$('#partyId').select2('open');
		return false
	}
	
	if($('#tripStartDate').val() == undefined || $('#tripStartDate').val().trim() == ''){
		showMessage('errors','Please Select Trip Start Date !');
		$('#tripStartDate').focus();
		return false
	}
	if($('#tripEndDate').val() == undefined || $('#tripEndDate').val().trim() == ''){
		showMessage('errors','Please Select Trip End Date !');
		$('#tripEndDate').focus();
		return false
	}
	if($('#startTime').val() == undefined || $('#startTime').val().trim() == ''){
		showMessage('errors','Please Select Trip End Time !');
		$('#startTime').focus();
		return false
	}
	if($('#endTime').val() == undefined || $('#endTime').val().trim() == ''){
		showMessage('errors','Please Select Trip End Time !');
		$('#endTime').focus();
		return false
	}
	/*if($('#tripStartDate').val()+" "+$('#startTime').val() >= $('#tripEndDate').val()+" "+$('#endTime').val()){
		showMessage('errors','Trip start date time should be before Trip end date time !');
		return false
	}*/
	if($('#pickUpAddress').val() == undefined || $('#pickUpAddress').val().trim() == ''){
		showMessage('errors','Please enter pick up address !');
		$('#pickUpAddress').focus();
		return false
	}
	
	if($('#validateRate').val() == 'true' || $('#validateRate').val() == true){
		if(Number($('#rate').val()) <= 0 ){
			showMessage('errors','Please enter Rate !');
			$('#rate').focus();
			return false
		}
	}
	
	return true;
}
