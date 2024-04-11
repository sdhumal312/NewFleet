$(document).ready(function() {
	getTripSheetAdvanceList();
});
function getTripSheetAdvanceList(){

	showLayer();
	var jsonObject				= new Object();
	jsonObject["tripSheetId"] 	= $('#tripSheetId').val();
	
	$.ajax({
		url: "getTripSheetAdvanceList",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setTripSheetAdvanceList(data);
			hideLayer();
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!');
			hideLayer();
		}
	});
}
function setTripSheetAdvanceList(data){
	if(data.TripSheetAdvance != undefined && data.TripSheetAdvance.length > 0){
		$('#advanceTotalTab').show();
		$('#tripAdvanceTable').show();
		$("#tripAdvanceBody tr").remove();
		
		var srNo = 1;
		var advanceTotal = 0;
		 for(var i = 0; i< data.TripSheetAdvance.length; i++){
			var TripSheetAdvance = data.TripSheetAdvance[i];
			advanceTotal += TripSheetAdvance.advanceAmount;
			var tr =' <tr data-object-id="">'
				+'<td class="fit" value="'+srNo+'">'+srNo+'</td>'
				+'<td>'+TripSheetAdvance.advancePlace+'</td>'
				+'<td>'+TripSheetAdvance.advancePaidby+'</td>'
				+'<td>'+TripSheetAdvance.advanceRefence+'</td>'
				+'<td>'+TripSheetAdvance.advanceAmount+'</td>'
				+'<td><a href="#"><font color="red" onclick="removeTripAdvance('+TripSheetAdvance.tripAdvanceID+');"><i class="fa fa-times"> Remove</i></font> </a></td>'
				+'</tr>';
			$('#tripAdvanceBody').append(tr);
			srNo++;
		}
		 $('#advanceTotal').html(advanceTotal);
	}else{
		$('#advanceTotalTab').hide();
		$('#tripAdvanceTable').hide();
	}
}
function removeTripAdvance(tripAdvanceId){
	showLayer();
	var jsonObject				= new Object();
	jsonObject["tripsheetId"] 	= $('#tripSheetId').val();
	jsonObject["companyId"] 	= $('#companyId').val();
	jsonObject["userId"] 		= $('#userId').val();
	jsonObject["tripAdvanceId"] = tripAdvanceId;
	
	$.ajax({
		url: "removeTripAdvance",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.closed != undefined && data.closed){
				showMessage('info', 'cannot remove advance. tripsheet already closed !');
			}else if(data.accountClosed != undefined && data.accountClosed){
				showMessage('info', 'cannot remove advance. tripsheet already account closed !');
			}else{
				getTripSheetAdvanceList();
			}
			hideLayer();
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!');
			hideLayer();
		}
	});

}

function saveTripSheetAdvance(){
	if(!validateAdvance()){
		return false;
	}
	if (validateBankPaymentDetails && $('#paidDateMaxTodate').val() == '') {
		showMessage('info', 'Enter paid Date to Process');
		hideLayer();
		return false;
	}
	if (validateBankPaymentDetails && !validateBankPayment($('#bankPaymentTypeId').val())) {
		hideLayer();
		return false;
	}
	
	showLayer();
	var jsonObject					= new Object();
	jsonObject["tripsheetId"] 		= $('#tripSheetId').val();
	jsonObject["companyId"] 		= $('#companyId').val();
	jsonObject["userId"] 			= $('#userId').val();
	jsonObject["advanceAmnt"] 		= $('#AdvanceAmount').val();
	jsonObject["driverAdvanceId"]	= $('#advanceDriverId').val();
	jsonObject["place"] 			= $('#advancePlaceId').val();
	jsonObject["advancePaidBy"] 	= $('#advancePaidbyId').val();
	jsonObject["advanceRef"] 		= $('#advanceRefence').val();
	jsonObject["remarks"] 			= $('#advanceRemarks').val();
	jsonObject["paymetTypeId"]		= $('#renPT_option').val();
	jsonObject["paidDate"]			= $('#paidDateMaxTodate').val();
	
	if (allowBankPaymentDetails) {
		prepareObject(jsonObject)
	}
	$.ajax({
		url: "saveTripSheetAdvanceDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
				showMessage('success', 'Data Saved Successfully !');
				location.replace('showTripSheet?tripSheetID='+$('#tripSheetId').val());
				//resetAdvanceFeilds()
				//getTripSheetAdvanceList();
				//hideLayer();
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!');
			hideLayer();
		}
	});
}

function resetAdvanceFeilds(){
	$('#AdvanceAmount').val('');
	$('#advanceRefence').val('');
	$('#advanceRemarks').val('');
	$("#advanceDriverId").val(0);
}

function validateAdvance(){
	var AdvanceAmount	= Number($('#AdvanceAmount').val());
	if(AdvanceAmount <=0 ){
		$('#AdvanceAmount').focus();
		showMessage('info', 'Please enter advance amount !');
		return false;
	}
	return true;
}