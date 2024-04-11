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
	                            text: e.driver_empnumber+" "+e.driver_firstname+" "+e.driver_Lastname+" - "+e.driver_fathername, slug: e.slug, id: e.driver_id
	                        	
	                        }
	                    }
	                    )
	                }
	            }
	        }
	    }
	    ),$("#FuelRouteList").select2({
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
	    });
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
	            		 
	            		 $('#tripSheetId').html(opt);
	            		 
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

function saveAccidentDetails(){
		$('#saveAccidentDetails').hide();
		if(!validateVehicleAccidentDetails()){
			$('#saveAccidentDetails').show();
			return false;
		}
		showLayer();
		var jsonObject			= new Object();
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
		
		jsonObject["routeId"] 				=  $('#FuelRouteList').val();
		jsonObject["firNumber"] 			=  $('#firNumber').val();
		jsonObject["firPoliceStation"] 		=  $('#firPoliceStation').val();
		jsonObject["firBy"] 				=  $('#firBy').val();
		jsonObject["firRemark"] 			=  $('#firRemark').val();
		jsonObject["firDocument"] 			=  $('#firDocument').val();
		jsonObject["unique-one-time-token"] =  $('#accessToken').val();
		jsonObject["validateDoublePost"] 	=  true;
		
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
		jsonObject["accidentClaimConfig"] 			=  $('#accidentClaimConfig').val();
		console.log("abcccc",jsonObject)
		
		$.ajax({
	             url: "saveVehicleAccidentDetails",
	             type: "POST",
	             dataType: 'json',
	             data: jsonObject,
	             success: function (data) {
	            	if(data.success != undefined && data.success){
	            		showMessage('success', 'Data Save Successfully !');
	            		location.replace('showVehicleAccidentDetails?id='+data.encreptedId);
	            	}else if(data.error != undefined){
	            		$('#accessToken').val(data.accessToken);
	            		showMessage('info', data.error);
	            		 $('#saveAccidentDetails').show();
	            	}else if(data.sequenceNotFound != undefined && data.sequenceNotFound){
	            		showMessage('info', 'Sequence Counter not found, Please contact to system adminitrator !');
	            		 $('#saveAccidentDetails').show();
	            	}else if(data.hasException != undefined && data.hasException){
	            		 $('#saveAccidentDetails').show();
		            	 showMessage('errors', 'Some error occured!');
		            	 hideLayer();
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
