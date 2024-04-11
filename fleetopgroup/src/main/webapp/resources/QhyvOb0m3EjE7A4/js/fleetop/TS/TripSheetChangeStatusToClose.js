
var flag=false;

function ChangeStausToClose()
{
	if(confirm('Are you sure? Go back to Tripsheet Closed status')){
		flag = true;
	}
	if(flag)
	{
		tripsheetChangeStatusToClose();
	}
}

function tripsheetChangeStatusToClose(){

  var jsonObject			= new Object();
	
	jsonObject["tripsheetId"] 	  		=  $('#tripSheetId').val();
	jsonObject["companyId"] 	  		=  $('#companyId').val();
	jsonObject["userId"] 	  			=  $('#userId').val();
	
	showLayer();
	$.ajax({
             url: "saveChangeStatusTripSheet",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 	if(data.StatusChanged){
            	 		showMessage('success', 'TripSheet Status Changed To TripSheet Closed Successfully!');
            	 		showUpperSignLayer('TripSheet Status Changes To TripSheet Closed , Redirecting To TripSheet View Page...');
            	 		 window.location.replace("showTripSheet.in?tripSheetID="+$('#tripSheetId').val());
            	 	}
            		
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 $('#saveClose').show();
            	 hideLayer();
             }
	});
}