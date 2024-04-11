
$(document).ready(function(){
$("input[name=transferName]").each(function(){
	var id = this.value;
	if($('#transferStatusID_'+id).val() == 1 ){
		$('#rejectRequisition_'+id).removeClass('hide');
	}
});

});

function setRejectRequisitionDetails(TransferID, partNumber, partName, quantity, receivedBy){
	$('#TransferID').val(TransferID);
	$('#partNumber').val(partNumber);
	$('#rejectedPartName').val(partName);
	$('#rejectQuantity').val(quantity);
	$('#receivedBy').val(receivedBy);
	$('#rejectModel').modal('show')
}
function rejectPartRequisition(){
	var jsonObject			= new Object();

	jsonObject["TransferID"] 			=  $('#TransferID').val();
	jsonObject["partNumber"] 			=  $('#partNumber').val();
	jsonObject["rejectedPartName"] 		=  $('#rejectedPartName').val();
	jsonObject["rejectQuantity"] 		=  $('#rejectQuantity').val();
	jsonObject["rejectRemark"] 			=  $('#rejectRemark').val();
	jsonObject["receivedBy"] 			=  $('#receivedBy').val();
	
	if($('#rejectRemark').val() == ""  || $('#rejectRemark').val() == null  ){
		showMessage('info','Please Enter Remark');
		return false;
	}
	
	var confirmMessage= confirm("Are You Sure , Do You Want To Reject The Requisition");
  
	if (confirmMessage == true){

    	showLayer();
    	$.ajax({
    		
    		url: "/rejectParRequisition",
    		type: "POST",
    		dataType: 'json',
    		data: jsonObject,
    		success: function (data) {
    			hideLayer();
    			location.reload(); 
    		},
    		error: function (e) {
    			showMessage('errors', 'Some error occured!')
    		}
    	});
    	
    }else{
    	console.log("inside elsse")
    	location.reload(); 
    	return false;
    }
	
	
}

function setNotificationAlertMsg(){
	if(document.getElementById('isNotifyYes').checked){
		$('#notificationRow').show();
		
	}else{
		$('#notificationRow').hide();
	}

}
function validateNotification(event){

	if($('#showNotifyUser').val() == 'true' || $('#showNotifyUser').val() == true){
		if(document.getElementById('isNotifyYes').checked){
			if(Number($('#userId').val()) <= 0){
				showMessage('info','Please select User to notify !');
				return false;
			}
			if($('#"alertMsg"').val() == ''){
				$('#"alertMsg"').focus();
				showMessage('info','Please Enter Notification Msg !');
				return false;
			}
		}
	}
	return true;
}