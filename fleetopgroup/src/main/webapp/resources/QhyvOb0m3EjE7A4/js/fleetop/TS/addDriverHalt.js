$(document).ready(function() {
	  $("#tallyCompanyId").select2({
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
	});
	
function validateHaltDate(){
	
	var haltdate = $("#reservation").val().split("  to  ");
	var haltDateFrom = haltdate[0];
	var haltDateTo   = haltdate[1];
	
	var date1 = haltDateFrom.split("-");
	var date2 = haltDateTo.split("-");
	var date3 = $("#trpiOpendate").val().split("-");
	var date4 = $("#trpiClosedate").val().split("-");
	
	var haltfrom  = new Date(date1[2], date1[1] - 1, date1[0]);
	var haltto    = new Date(date2[2], date2[1] - 1, date2[0]);
	var from      = new Date(date3[2], date3[1] - 1, date3[0]);
	var to        = new Date(date4[2], date4[1] - 1, date4[0]); 
	
	var dateTimeString = $("#closeTime").val();
    var closeDate = new Date(dateTimeString.replace(' ', 'T'));
	
	if(Number($("#tripStatusId").val()) < 3){
		if(haltfrom >to || haltto > to){
			showMessage('info'," Halt Date Cannot be greater than TripCloseDate");
			$("#reservation").val("");
		}else if(haltfrom <from){
			showMessage('info'," Halt Date Cannot be Before TripOpen Date");
			$("#reservation").val("");
		}
	}
	if(Number($("#tripStatusId").val()) >= 3){
		console.log("inside 2nd if ")
		if(haltfrom>closeDate || haltto>closeDate){
			console.log("inside cnd")
			showMessage('info'," Halt Date Cannot be greater than TripCloseDate");
			$("#reservation").val("");
		}
	}
	
}	
function saveDriverHalt(){
	if(!validate()){
		return false;
	}
	$('#haltSave').hide();
	
	var dateRange	= $('#reservation').val();
	var array 	= dateRange.split('to');
	var jsonObject			= new Object();
	jsonObject["tripsheetId"] 	  	=  $('#tripSheetId').val();
	jsonObject["companyId"] 	  	=  $('#companyId').val();
	jsonObject["userId"] 	  		=  $('#userId').val();
	jsonObject["haltPaidById"] 	  	=  $('#userId').val();
	jsonObject["haltReason"] 	  	=  $('#haltReason').val();
	jsonObject["driverHaltId"] 	  	=  $('#DRIVERID').val();
	jsonObject["haltAmount"] 	  	=  $('#haltAmount').val();
	jsonObject["fromDate"] 	  		=  array[0].trim();
	jsonObject["toDate"] 	  		=  array[1].trim();
	jsonObject["tallyCompanyId"] 	=  $('#tallyCompanyId').val();
	
	showLayer();
	$.ajax({
             url: "saveTripSheetDriverHaltDetails",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 if(data.AlreadyHalt == true || data.AlreadyHalt == 'true'){
            		 showMessage('info','Driver Halt Already Exists !');
            		 $('#haltSave').show();
            		 hideLayer();
            	 }else{
            		 location.replace('showTripSheet?tripSheetID='+$('#tripSheetId').val());
            	 }
             },
             error: function (e) {
            	 $('#haltSave').show();
            	 showMessage('errors', 'Some error occured!');
            	 hideLayer();
             }
	});

}

function removeDriverHalt(driverHaltId,driverHaltAmt){
	var jsonObject			= new Object();
	jsonObject["tripsheetId"] 	  	= $('#tripSheetId').val();
	jsonObject["companyId"] 	  	= $('#companyId').val();
	jsonObject["driverHaltId"] 		= driverHaltId;
	jsonObject["expenseId"] 		= 1;//monthwise/DatewiseVehicleExpense(ExpenseId for driverHalt) 
	jsonObject["vid"] 	  			= $('#vid').val();
	jsonObject["expenseAmount"] 	= driverHaltAmt;
	jsonObject["tripStatusId"] 		= $('#tripStatusId').val();
	
	showLayer();
	$.ajax({
             url: "removeTripSheetDriverHaltDetails",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            		 showMessage('success','Driver Halt Removed Successfully !');
            		 location.replace('addHaltAmount.in?ID='+$('#tripSheetId').val());
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!');
            	 hideLayer();
             }
	});
}


	function validate(){
		
		if($('#DRIVERID').val() == null || $('#DRIVERID').val().trim() == ''){
			showMessage('info','Please Select Driver !');
			return false;
		}
		
		if($('#reservation').val() == null || $('#reservation').val().trim() == ''){
			$('#reservation').focus();
			showMessage('info','Please Select Date !');
			return false;
		}
		
		if($('#haltReason').val() == null || $('#haltReason').val().trim() == ''){
			$('#haltAmount').focus();
			showMessage('info','Please Enter Halt Reason !');
			return false;
		}
		
		if(Number($('#haltAmount').val()) <= 0){
			$('#haltAmount').focus();
			showMessage('info','Please Enter Amount !');
			return false;
		}
		
		
		if($('#TallyCompanyMasterInDriverHalt').val() == 'true' || $('#TallyCompanyMasterInDriverHalt').val() == true){
			var tallyCompanyId	=	$("#tallyCompanyId").val();
			if(tallyCompanyId == "" || Number(tallyCompanyId) <= 0){
				showMessage('info','Please Select Tally Company Name');
				return false;
			}
		}
		return true;
	}