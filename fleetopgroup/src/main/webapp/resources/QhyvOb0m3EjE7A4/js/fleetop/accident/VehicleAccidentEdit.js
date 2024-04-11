$(document).ready(function() {
	getVehicleAccidentDetails();
});
$(document).ready(function() {
	  $("#TripSelectVehicle").select2( {
	        minimumInputLength:2, minimumResultsForSearch:10, ajax: {
	            url:"getVehicleListTEST.in?Action=FuncionarioSelect2", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(e) {
	                return {
	                    term: e
	                }
	            }
	            , results:function(e) {
	                return {
	                    results:$.map(e, function(e) {
	                    	$('#vehicle_registration').val(e.vehicle_registration);
	                        return {
	                            text: e.vehicle_registration, slug: e.slug, id: e.vid
	                        }
	                    }
	                    )
	                }
	            }
	        }
	    }
	    ), $("#driverList").select2( {
	        minimumInputLength:3, minimumResultsForSearch:10, ajax: {
	            url:"getDriver1ListOfCompany.in?Action=FuncionarioSelect2", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(e) {
	                return {
	                    term: e
	                }
	            }
	            , results:function(e) {
	                return {
	                    results:$.map(e, function(e) {
	                        return {
	                            text: e.driver_empnumber+" "+e.driver_firstname+"_"+e.driver_Lastname, slug: e.slug, id: e.driver_id
	                        	
	                        }
	                    }
	                    )
	                }
	            }
	        }
	    }
	    )
});

function visibility(showId, plusSign) {
	if($('#'+showId).is(":hidden")){
		$('#'+showId).show();
		$('#'+plusSign).addClass('fa fa-minus');
	}else{
		$('#'+showId).hide();
		$('#'+plusSign).removeClass('fa fa-minus');
		$('#'+plusSign).addClass('fa fa-plus');
	}
	
}

function getVehicleTripDetails(){
	if($('#accidentDate').val() != '' && $('#accidentTime').val() != ''){
		var vid = $('#TripSelectVehicle').val();
		if(Number(vid) <=0){
			showMessage('errors', 'Please select vehicle !');
			$('#accidentTime').val('');
			return false;
		}
		
		showLayer();
		var jsonObject			= new Object();
		jsonObject["vid"] 					=  $('#TripSelectVehicle').val();
		jsonObject["companyId"] 			=  $('#companyId').val();
		jsonObject["userId"] 				=  $('#userId').val();
		jsonObject["accidentDate"] 			=  $('#accidentDate').val();
		jsonObject["accidentTime"] 			=  $('#accidentTime').val();
		
		$.ajax({
	             url: "getTripSheetDetailsAtTime",
	             type: "POST",
	             dataType: 'json',
	             data: jsonObject,
	             success: function (data) {
	            	 console.log('data : ', data);
	            	 if(data.tripActive != undefined){
	            		 var opt = '<option value="'+data.tripActive.tripSheetID+'">TS-'+data.tripActive.tripSheetNumber+'</option>';
	            		 
	            		 $('#tripSheetId').select2('data', {
	            				id : data.tripActive.tripSheetID,
	            				text : 'TS-'+data.tripActive.tripSheetNumber
	            		 });
	            		 
	            		 $('#driverList').select2('data', {
	            				id : data.tripActive.tripFristDriverID,
	            				text : data.tripActive.tripFristDriverName
	            		 });
	            	 }
	            	 hideLayer();
	             },
	             error: function (e) {
	            	 showMessage('errors', 'Some error occured!');
	            	 hideLayer();
	             }
		});
	}
	
}

function updateAccidentDetails(){
		$('#saveAccidentDetails').hide();
		if(!validateVehicleAccidentDetails()){
			$('#saveAccidentDetails').show();
			return false;
		}
		
		if(Number($('#status').val()) >= 4){
			$('#saveAccidentDetails').show();
			showMessage('info', 'Cannot Update As Quotation is already created for this entry!');
			return false;
		}
	
		showLayer();
		var jsonObject			= new Object();
		jsonObject["accidentId"] 			=  $('#accidentId').val();
		jsonObject["vid"] 					=  $('#TripSelectVehicle').val();
		jsonObject["companyId"] 			=  $('#companyId').val();
		jsonObject["userId"] 				=  $('#userId').val();
		jsonObject["accidentDate"] 			=  $('#accidentDate').val();
		jsonObject["accidentTime"] 			=  $('#accidentTime').val();
		jsonObject["driverId"] 				=  $('#driverList').val();
		jsonObject["tripSheetId"] 			=  $('#tripSheetId').val();
		jsonObject["incidentlocation"] 		=  $('#incidentlocation').val();
		jsonObject["description"] 			=  $('#description').val();
		
		jsonObject["otherVehicle"] 			=  $('#otherVehicleNumber').val();
		jsonObject["otherDriver"] 			=  $('#otherDriver').val();
		jsonObject["otherDriverMob"] 		=  $('#otherDriverMob').val();
		jsonObject["otherVehicleOwner"] 	=  $('#otherVehicleOwner').val();
		jsonObject["otherVehicleOwnerMob"] 	=  $('#otherVehicleOwnerMob').val();
		jsonObject["otherVehicleDetails"] 	=  $('#otherVehicleDetails').val();
		
		jsonObject["firNumber"] 			=  $('#firNumber').val();
		jsonObject["firPoliceStation"] 		=  $('#firPoliceStation').val();
		jsonObject["firBy"] 				=  $('#firBy').val();
		jsonObject["firRemark"] 			=  $('#firRemark').val();
		jsonObject["firDocument"] 			=  $('#firDocument').val();
		
		jsonObject["approxDamageAmount"] 	=  $('#approxDamageAmount').val();
		if($("#isAmtReceived").prop('checked')){
			jsonObject["damageAmount"] 				=  $('#receivedDamageAmount').val().trim();
			jsonObject["damageAmountStatusId"] 		=  1;
		}else if($("#isAmtPaid").prop('checked')){
			jsonObject["damageAmount"] 				=  $('#paidDamageAmount').val().trim();
			jsonObject["damageAmountStatusId"] 		= 2;
		}
		if($("#isClaim").val() == true || $("#isClaim").val() == 'true'  || $("#isClaim").val() == "" ){
			jsonObject["isClaim"] 				=  true;
		}else{
			jsonObject["isClaim"] 				=  false;
		}
		if($('#claimRemark').val() != undefined && $('#claimRemark').val() != ""){
			jsonObject["claimRemark"] 			=  $('#claimRemark').val().trim();
		}
		
		$.ajax({
	             url: "updateVehicleAccidentDetails",
	             type: "POST",
	             dataType: 'json',
	             data: jsonObject,
	             success: function (data) {
	            	 console.log('data : ', data);
	            	if(data.success != undefined && data.success){
	            		showMessage('success', 'Data Save Successfully !');
	            		location.replace('showVehicleAccidentDetails?id='+data.encreptedId);
	            	}else if(data.sequenceNotFound != undefined && data.sequenceNotFound){
	            		showMessage('info', 'Sequence Counter not found, Please contact to system adminitrator !');
	            	}
	            	 hideLayer();
	             },
	             error: function (e) {
	            	 $('#saveAccidentDetails').show();
	            	 showMessage('errors', 'Some error occured!');
	            	 hideLayer();
	             }
		});

}
function getVehicleAccidentDetails(){
	showLayer();
	var jsonObject			= new Object();
	jsonObject["accidentId"] 			=  $('#accidentId').val();
	
	$.ajax({
             url: "getVehicleAccidentDetails",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 setVehicleAccidentData(data);
            	 hideLayer();
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!');
            	 hideLayer();
             }
	});
}
function setVehicleAccidentData(data){
	if(data.accidentDetails != undefined){
		$('#status').val(data.accidentDetails.status);
		$('#TripSelectVehicle').select2('data', {
    		id : data.accidentDetails.vid,
    		text : data.accidentDetails.vehicleNumber
    	});
		$('#TripSelectVehicle').select2('readonly', true);
		$('#accidentDate').val(data.accidentDetails.accidentDate);
		$('#accidentTime').val(data.accidentDetails.accidentTime);
		$('#driverList').select2('data', {
    		id : data.accidentDetails.driverId,
    		text : data.accidentDetails.driverName
    	});
		if(data.accidentDetails.tripSheetId > 0){
			$('#tripSheetId').select2('data', {
				id : data.accidentDetails.tripSheetId,
				text : data.accidentDetails.tripSheetNumber
			});
			$('#tripSheetId').select2('readonly', true);
		}
		$('#incidentlocation').val(data.accidentDetails.location);
		$('#description').val(data.accidentDetails.description);
		
		$('#otherVehicleNumber').val(data.accidentDetails.accidentWithVehicle);
		$('#otherDriver').val(data.accidentDetails.accidentWithDriver);
		$('#otherDriverMob').val(data.accidentDetails.accidentWithDriverrMobile);
		$('#otherVehicleOwner').val(data.accidentDetails.accidentWithOwner);
		$('#otherVehicleDetails').val(data.accidentDetails.accidentWithOtherDetails);
		
		$('#firNumber').val(data.accidentDetails.firNumber);
		$('#firPoliceStation').val(data.accidentDetails.firPoliceStation);
		$('#firBy').val(data.accidentDetails.firBy);
		$('#firRemark').val(data.accidentDetails.firRemark);
		$("#additionalDetails").show();
		$('#approxDamageAmount').val(data.accidentDetails.approxDamageAmount);
		if(data.accidentDetails.damageAmountStatusId == true){
			$('#receivedDamageAmount').val(data.accidentDetails.damageAmount);
			$("#isAmtReceived").prop('checked',true);
			$("#isAmtPaid").prop('checked',false);
			$("#receivedAmountDiv").show();
			$("#paidAmountDiv").hide();
		}else{
			$('#paidDamageAmount').val(data.accidentDetails.damageAmount);
			$("#isAmtReceived").prop('checked',false);
			$("#isAmtPaid").prop('checked',true);
			$("#paidAmountDiv").show();
			$("#receivedAmountDiv").hide();
		}
		console.log("data.accidentDetails",data.accidentDetails)
		if(data.accidentDetails.claim == true){
			
			$('#yes').addClass('active');
			$('#no').removeClass('active');
			$('#yes').addClass('btn-success');
			$('#no').removeClass('btn-success');
			$('#isClaim').val(true);
		}else{
			$('#yes').removeClass('active');
			$('#no').addClass('active');
			$('#yes').removeClass('btn-success');
			$('#no').addClass('btn-success');
			$('#isClaim').val(false);
		}
		$('#sClaimStatusId').addClass('btn-success');
		$('#sClaimStatusId').html(data.accidentDetails.claimStatus);
		
		
		$('#claimRemark').val(data.accidentDetails.claimRemark);
		
		
		
	}
}