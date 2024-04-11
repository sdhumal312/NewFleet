function closeTripSheetAccount(){
	
	if($('#allowTallyIntegration').val() == 'true' || $('#allowTallyIntegration').val() == true){
		if($('#voucherDate').val() == null || $('#voucherDate').val().trim() == ''){
			showMessage('info', 'Please add voucher date first to account close !');
			return false;
		}
		if(Number($('#tallyId').val()) <= 0){
			showMessage('info', 'Please add tally company first to account close !');
			return false;
		}
	}
	if($('#closeACCTripReference').val() == null || $('#closeACCTripReference').val().trim() == ''){
		showMessage('info', 'Please Enter Reference Number !');
		return false;
	}
	if(($('#allowAccountCloseWithoutIncome').val() == 'false' || $('#allowAccountCloseWithoutIncome').val() == false) && (Number($('#tripsheetTotalIncome').val()) <= 0)){
		showMessage('info', 'No Income Found Hence You Can Not Close Account to This Trip!');
		return false;
	}
	
	if(($('#allowCloseWithNoGPSandFastag').val() == true || $('#allowCloseWithNoGPSandFastag').val() == 'true')&&($('#permissionToClose').val() == false ||  $('#permissionToClose').val() == 'false')&&(($('#fastagFound').val() == false || $('#fastagFound').val() == 'false')||($('#gpsUsageFound').val() == false ||$('#gpsUsageFound').val() == 'false' ))){
		showMessage('info', 'Only authorised user have permission to close this acoount !');
		return false;
	}
	
  var jsonObject			= new Object();
	
	jsonObject["tripsheetId"] 	  		=  $('#tripSheetId').val();
	jsonObject["companyId"] 	  		=  $('#companyId').val();
	jsonObject["userId"] 	  			=  $('#userId').val();
	jsonObject["accountClosedAmount"] 	=  $('#closeACCTripAmount').val();
	jsonObject["accountClosedRef"] 	  	=  $('#closeACCTripReference').val();

	closeTripAccount(jsonObject);
}


function closeTripAccount(jsonObject){
		showLayer();
 var showVehicleSearchAfterACCClose =	$('#showVehicleSearchAfterACCClose').val();
	$.ajax({
             url: "savecloseAccountTripSheet",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 	if(data.accountClosed && (showVehicleSearchAfterACCClose == undefined || showVehicleSearchAfterACCClose == 'false' || showVehicleSearchAfterACCClose == false) ){
            	 		showMessage('success', 'TripSheet Account Closed  Successfully!');
            	 		showUpperSignLayer('TripSheet Account Closed , Redirecting To TripSheet View Page...');
            	 		 window.location.replace("showTripSheet.in?tripSheetID="+$('#tripSheetId').val());
            	 	}else{
						showMessage('success', 'TripSheet Account Closed  Successfully!');
						var tvid = $('#tvid').val();
						location.replace('newTripSheetEntries.in?vid='+tvid+'');
					}
            		
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 $('#saveClose').show();
            	 hideLayer();
             }
	});
}
