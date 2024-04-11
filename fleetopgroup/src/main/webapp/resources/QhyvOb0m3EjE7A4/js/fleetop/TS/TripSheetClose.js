function saveCloseTripSheet(tripOpen, expected){
	$('#saveClose').hide();
	if(!validateExpectedOdameter(tripOpen, expected)){
		$('#saveClose').show();
		return false;
	}
		
	if($('#addInDriverbalanceAfterTripClose').val()  == 'true' || $('#addInDriverbalanceAfterTripClose').val() == true){
			
		if(!validateCloseAmount()){
			$('#saveClose').show();
			return false;
		}
	}
	
	if($('#validateTripRoutePoint').val() == 'true' || $('#validateTripRoutePoint').val() == true){
	  if($('#RoutePoint'+$('#driver1Id').val()).val() == "" ){
			showMessage('errors', "Please enter TripRoute Point");
			$('#saveClose').show();
			hideLayer();
			return false;
		}		
	}

	if($('#tripEndTime').val() != undefined){
		
		const format 			= "YYYY-MM-DD HH:mm:ss";
		var date 				= new Date();
		var currentDateTime 	= moment(date).format(format);
		
		var dispatchTime  	 		= $('#dispatchedByTimeOn').val();
		var closeDateTime    		= $('#closetripDate').val().split("-").reverse().join("-")+' '+$('#tripEndTime').val();
		var isBeforeDispatchDate	= moment(closeDateTime).isBefore(dispatchTime);
		var isAfterCurrnetDate		= moment(closeDateTime).isAfter(currentDateTime);
		
		if(isBeforeDispatchDate){
			showMessage('info','Can Not Enter Close Date Before Dispatch Date '+dispatchTime+' ');
			$('#saveClose').show();
			hideLayer();
			return false;
		}
		
		if(($("#validateTripCloseTimeAsCurrentTime").val() == true || $("#validateTripCloseTimeAsCurrentTime").val() == 'true') && isAfterCurrnetDate){
			showMessage('info','Can Not Enter Close Date Time After Current Date Time ');
			$('#saveClose').show();
			hideLayer();
			return false;
		}
	}
	showLayer();
	var jsonObject			= new Object();
	
	jsonObject["tripsheetId"] 	  		=  $('#tripSheetId').val();
	jsonObject["companyId"] 	  		=  $('#companyId').val();
	jsonObject["userId"] 	  			=  $('#userId').val();
	jsonObject["tripClosingDate"] 	  	=  $('#closetripDate').val();
	if(document.getElementById('tripClosingKMStatusId').checked){
		jsonObject["tripClosingKMStatusId"] 	 =  1;
	}else{
		jsonObject["tripClosingKMStatusId"] 	 =  0;
	}
	jsonObject["tripCloseTime"] 	  			=  $('#tripEndTime').val();
	jsonObject["tripGPSClosingKM"] 	  			=  $('#tripGpsClosingKM').val();
	jsonObject["tripClosingKM"] 	  			=  $('#tripCloseKM').val();
	jsonObject["closeTripStatusId"] 	  		=  $('#closeTripStatusId').val();
	jsonObject["closeTripRefNo"] 	  			=  $('#closeTripReference').val();
	jsonObject["closeTripAmount"] 	  			=  $('#closeTripAmount').val();
	jsonObject["closeTripNameById"] 	  		=  $('#closeTripNameByIdselect').val();
	jsonObject["gpsCloseLocation"] 	  			=  $('#gpsCloseLocation').val();
	jsonObject["tripEndDiesel"] 	  			=  $('#tripEndDiesel').val();
	jsonObject["meterNotWorking"] 				= $('#meterNotWorkingDontValidate').val();
	jsonObject["isWebRequest"] 	  				= true;
	jsonObject["isTallyPushYes"] 	  			= $('#isTallyPushYes').prop('checked');
	
	if(Number($('#driver1Id').val()) > 0){
		jsonObject["driver1RoutePoint"] 	  	=  $('#RoutePoint'+$('#driver1Id').val()).val();
	}
	if(Number($('#driver2Id').val()) > 0){
		jsonObject["driver2RoutePoint"] 	  	=  $('#RoutePoint'+$('#driver2Id').val()).val();
	}
	if(Number($('#cleanerId').val()) > 0){
		jsonObject["cleanerRoutePoint"] 	  	=  $('#RoutePoint'+$('#cleanerId').val()).val();
	}
	jsonObject["dispatchDateTime"] 	  					= dispatchTime;
	jsonObject["closeDateTime"] 	  					= closeDateTime+":00";
	jsonObject["validateTripCloseTimeAsCurrentTime"] 	= $('#validateTripCloseTimeAsCurrentTime').val();
	
	jsonObject["driverAdvance"] 	  			= Number($('#advanceForReverseBalance').val()).toFixed(2);	
	jsonObject["driverExpense"] 	  			= Number($('#expenseForReverseBalance').val()).toFixed(2);
	jsonObject["driverBalance"] 	  			= calculateDriverBalance();	
	
	console.log('jsonObject : ', jsonObject);
	
	showLayer();
	$.ajax({
             url: "saveTripSheetCloseDetails",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	    if(data.closeDateNotFound != undefined && data.closeDateNotFound){
            	    	showMessage('info', 'Please select proper close date and time');
            			$('#saveClose').show();
            			hideLayer();
            			return false;
            	    }else if(data.closeDateNotMatching != undefined && data.closeDateNotMatching){
            	   	 	showMessage('info', 'TripSheet Close Date Not Matching Please check Close Date !');
            	   	 	hideLayer();
            	    }else if(data.checkOdometer != undefined && data.checkOdometer){
            	    		showMessage('info', 'Cannot Close Tripheet Please Check Close Odometer !');
            	   	 		hideLayer();
            	    }else if(!data.alreadyClosed){
            	 		showMessage('success', 'TripSheet Closed  Successfully!');
            	 		showUpperSignLayer('TripSheet Closed , Redirecting To TripSheet View Page...');
            	 		showLayer();
            	 		 window.location.replace("showTripSheet.in?tripSheetID="+$('#tripSheetId').val());
            	 	}else if(data.closeDateTimeAfterCurrentDateTime != undefined && data.closeDateTimeAfterCurrentDateTime){
            	 		showMessage('info', 'Can Not Enter Close Date Time After Current Date Time ');
            			$('#saveClose').show();
            			hideLayer();
            			return false;
            	 	}else{
                		 showMessage('errors', 'TripSheet Already Closed !');
                		 hideLayer();
            	 	}
            		
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 $('#saveClose').show();
            	 hideLayer();
             }
	});
	
}

function calculateDriverBalance(){
	var driverBalance 	= 0;
	var driverAdvance	= 	0.0;
	var driverExpense	= 	0.0;
	var closeAmount		=   0.0;
	
	driverAdvance	= 	Number($('#advanceForReverseBalance').val()).toFixed(2);
	driverExpense	= 	Number($('#expenseForReverseBalance').val()).toFixed(2);
	closeAmount		=   Number($('#closeTripAmount').val()).toFixed(2);
	
	if(Number($('#closeTripStatusId').val()) == 1){
		driverBalance 	  					=  Math.round(driverAdvance) - Math.round(driverExpense) - Math.round(closeAmount);
														
	}else if(Number($('#closeTripStatusId').val()) == 2){
		driverBalance 	  					= Math.round(driverAdvance) - Math.round(driverExpense) + Math.round(closeAmount);
		
	}
	return driverBalance;
}
function validateExpectedOdameter(tripOpen, expected){
	var  showExtraReceived	= $('#showExtraReceived').val();
	var validateClosingOdometerOnRoute = false;
	if($('#validateClosingOdometerOnRoute').val() == 'true' || $('#validateClosingOdometerOnRoute').val() == true){
		validateClosingOdometerOnRoute = true;
	}
	var validate = false;
	if(showExtraReceived == 'true' || showExtraReceived == true){
  
		$('input[name*=TripSheetExtraQuantityReceived]' ).each(function(){
			var extraOption = Number($("#"+$( this ).attr( "id" )).val());
			if(extraOption <= 0){
				 $("#"+$( this ).attr( "id" )).focus();
				 validate = true;
				//showMessage('errors', 'Please Select Quantity!');
				//return false;
			}
		});
	}
	
	if(validate){
		showMessage('errors', 'Please Select Quantity!');
		return false;
	}
	
	var gPSIntegration = $('#allowGPSIntegration').val();
	var allowGPSIntegration = false;
	if(gPSIntegration == 'true' || gPSIntegration == true){
		allowGPSIntegration = true;
	}
	var minAllowedKM = Number($('#minAllowedKM').val());
	var maxAllowedKM = Number($('#maxAllowedKM').val());
	
	if(Number($('#gpsFlavor').val()) == 3){
		allowGPSIntegration = false;
	}
	var validateOdometer = true;
	  if(document.getElementById('tripClosingKMStatusId').checked && ($('#meterNotWorkingDontValidate').val() == 'true' || $('#meterNotWorkingDontValidate').val() == true)){
		  validateOdometer = false;
	  }
	  
	  if($('#validateOdometerInTripSheet').val() == 'false' || $('#validateOdometerInTripSheet').val() == false){
		  validateOdometer = false;
	  }
	  
	   if(validateOdometer){
		   if((Number($('#tripCloseKM').val()) < minAllowedKM || Number($('#tripCloseKM').val()) > maxAllowedKM)  && !allowGPSIntegration){
			   showMessage('errors', 'Please Enter Closing KM Between '+minAllowedKM+' and '+maxAllowedKM);
			   return false;
		   }
	   }
	   if($('#closetripDate').val() == undefined || $('#closetripDate').val() == null || $('#closetripDate').val().trim() == ''){
			showMessage('info', 'Please select proper close date and time');
			$('#saveClose').show();
			return false;
		}
		
	var showTime = $('#showTime').val()
	if(showTime == 'true'){
		var tripEndTime = $('#tripEndTime').val();
		if(tripEndTime == ''){
			$('#tripEndTime').focus();
			showMessage('info', 'Please Select Trip Closing Time !');
			return false;
		} 
	}
	if($('#tripOpenCloseFuelRequired').val() == 'true' || $('#tripOpenCloseFuelRequired').val() == true){
		if(Number($('#tripEndDiesel').val()) <= 0){
			$('#tripEndDiesel').focus();
			showMessage('info', 'Please Enter Balance Fuel !');
			return false;
		}
	}
	if (confirm('Are You sure to close TripSheet?')) {
		showLayer();
		$("#tripSheetClose").submit();
		return true;
	} else {
		hideLayer();
		return false;
	}
		showLayer();
	
	return true;
}


function tripExtra(id,tripExtraOptions){
	var closetripExtra = Number($('#tripSheetExtraQuantity'+id).val());
	
	if(closetripExtra == undefined || closetripExtra == ""){
		showMessage('errors', 'Please Enter Quantity');
		return false;
	} else{
			if(closetripExtra > tripExtraOptions){
				$('#tripSheetExtraQuantity'+id).val('');
				showMessage('errors', 'Please Enter Quantity less than '+tripExtraOptions);
				return false;
	}
	return true;
	}	  
}

function closeKM(openingKM, approximateKM){
	alert(openingKM+approximateKM);
}

function getNextTripDetails(){
	
	if(($('#closetripDate').val() != null && $('#closetripDate').val().trim() != '') && ( $('#tripEndTime').val() != null && $('#tripEndTime').val() != "" && $('#vehicleId').val() != '' )){
		showLayer();
		var jsonObject			= new Object();
		jsonObject["vehicleId"] 					=  $('#vehicleId').val();
		jsonObject["companyId"] 					=  $('#companyId').val();
		jsonObject["dispatchedByTime"] 				=  $('#dispatchedByTime').val();
		jsonObject["dispatchedToByTime"] 			=  $('#closetripDate').val();
		jsonObject["dispatchTime"] 					=  $('#tripEndTime').val();
		jsonObject["validateOnTripClose"] 			= true;
		var dispatchedByTimeOn						= $('#dispatchedByTimeOn').val();
		
		
		$.ajax({
			url: "getLastNextTripSheetDetails",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				
				if(data.inBetweenTripSheet != undefined  ){
					showMessage('info','You already Have Tripsheet TS-'+data.inBetweenTripSheet.tripSheetNumber+' On Same Date And Time Please Select Another Date Or Time')
					$('#saveClose').show();
					$('#tripEndTime').val('');
					hideLayer();
					return false;
				}
				
				if(data.tripSheet != undefined && data.nextTripSheet == undefined ){
					
					var isafter = moment(data.tripSheet.dispatchedByTime).isAfter(dispatchedByTimeOn);
				
					if(isafter){
						showMessage('info','You already Have Tripsheet TS-'+data.tripSheet.tripSheetNumber+' On Same Date And Time Please Select Another Date Or Time')
						$('#saveClose').show();
						$('#tripEndTime').val('');
						hideLayer();
						return false;
					}
				
				}
				hideLayer();
				return true;
			}
				
		});
	}
}
document.getElementById('tripCloseKM').addEventListener('change', function() {
	 
  if($('#newLogicForTripSheetDriverBalance').val() == true || $('#newLogicForTripSheetDriverBalance').val() == "true"){
	  newDriverBalanceLogic();
  }
	    
});
	 
function newDriverBalanceLogic(){
			
			
		var fuel_openingKm            =  $('#tripOpeningKM').val();  
		var fuel_closingKm	          =  $('#tripCloseKM').val();    
		var fuel_volume               =  $('#fuelVolume').val();   
		var fuelCostPerLiter          =  $('#fuelCostPerLiter').val(); 
		var fuelMileage               =  $('#fuelMileage').val();       
		
		var actualKm                  =  fuel_closingKm - fuel_openingKm;   
		var expectedkm                =  (actualKm/fuelMileage);         
		var diff                      =  Math.abs(fuel_volume - expectedkm).toFixed(2);
		var costGiveOrTakeByDriver    =  fuelCostPerLiter * diff;
		var totalExpenseAmount        =  $('#totalExpenseAmount').val();
		var totalAdvanceAmount        =  $('#totalAdvanceAmount').val();
		
		var finalAmountCal = 0;
		
		if (fuel_volume >= expectedkm) {
		    var amountText = "DriverBalance =(Amount - Expense ) + Diesel = ";
		    var finalAmount = "("+'<i class="fa fa-inr"></i>' + $('#totalAdvanceAmount').val() + " - " +
		                      '<i class="fa fa-inr"></i>' + $('#totalExpenseAmount').val() +")"+ " + " +
		                      ' <i class="fa fa-inr"></i>' + fuelCostPerLiter * diff +
		                      ' = <i class="fa fa-inr"></i>' +
		                      ($('#totalAdvanceAmount').val() - $('#totalExpenseAmount').val() + costGiveOrTakeByDriver).toFixed(2) + " ";
		    
		    finalAmountCal  =  ($('#totalAdvanceAmount').val() - $('#totalExpenseAmount').val() + costGiveOrTakeByDriver).toFixed(2);
		
		} else {
		    var amountText = "DriverBalance = (Amount - Expense) - Diesel = ";
		    var finalAmount = "("+'<i class="fa fa-inr"></i>' + $('#totalAdvanceAmount').val() + " - " +
		                      '<i class="fa fa-inr"></i>' + $('#totalExpenseAmount').val() +")"+ " - " +
		                      ' <i class="fa fa-inr"></i>' + fuelCostPerLiter * diff +
		                      ' = <i class="fa fa-inr"></i>' +
		                      ($('#totalAdvanceAmount').val() - $('#totalExpenseAmount').val() - costGiveOrTakeByDriver).toFixed(2) + " ";
		    
		   finalAmountCal  =  ($('#totalAdvanceAmount').val() - $('#totalExpenseAmount').val() - costGiveOrTakeByDriver).toFixed(2);
		
		}
		
		var finalAmount3 = amountText+ "     " + " "+ finalAmount; 
		
		 $('#advanceID').html(totalAdvanceAmount);
		 $('#expenseID').html(totalExpenseAmount);
		 $('#perLitreRate').html(fuelCostPerLiter);
		 $('#expectedLitre').html(expectedkm);
		 $('#actualLitre').html(fuel_volume);
		 $('#dieselExpense').html(costGiveOrTakeByDriver);
		 $('#amountText').html(finalAmount3);
		 $('#showDriverData').show();
		 
			setPayToField(finalAmountCal);
}
