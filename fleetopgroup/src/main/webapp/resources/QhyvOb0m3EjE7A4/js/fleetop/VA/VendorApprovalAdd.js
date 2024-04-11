

$(document).ready(function() {
	$('input[name*=multipleApproval]').each(function(obj){
		
		var invoiceId =	this.id.split("_")[1];
		console.log("invoiceId",invoiceId);

		var balAmount				= Number($("#balAmt_"+invoiceId).val());
		console.log("balAmount",balAmount)
		$("#receivedAmt_"+invoiceId).prop('readonly',true);
		$("#receivedAmt_"+invoiceId).val(balAmount)
		$("#balanceAmt_"+invoiceId).val(0)
	});
	
});



function changePaymentType(obj){
	if(obj.id != undefined){
		var invoiceId				= obj.id.split("_")[1];
		var paymentTypeID 			= $("#"+obj.id).val();
		var balAmount				= Number($("#balAmt_"+invoiceId).val());
	}
	
	 if(paymentTypeID == 1){ //clear
		$("#receivedAmt_"+invoiceId).prop('readonly',true);
		$("#receivedAmt_"+invoiceId).val(balAmount)
		$("#balanceAmt_"+invoiceId).val(0)
	}else{
		$("#receivedAmt_"+invoiceId).removeAttr('readonly');
		$("#receivedAmt_"+invoiceId).val(0)
		$("#balanceAmt_"+invoiceId).val(balAmount)
	} 
	
}

function calculateBalance(obj){
	if(obj != null && obj.id != undefined){
		var invoiceId				= obj.id.split("_")[1];
		var recievedAmount 			= Number(obj.value);
		var balanceAmount 			= Number($("#balanceAmt_"+invoiceId).val());
		var balAmount				= Number($("#balAmt_"+invoiceId).val());
		var paymentType 			= $("#PaymentType_"+invoiceId).val();
		
		if(paymentType == 1){ //clear
			$("#receivedAmt"+invoiceId).val(balAmount)
			$("#balanceAmt_"+invoiceId).val(0);
		}else{
			 //Negotiate
			if(recievedAmount > balAmount){
				showMessage('info','The Recieved Amount ShouldNot Be Greater Than ' + balAmount +' !');
				$("#receivedAmt_"+invoiceId).val(0);
				$("#balanceAmt_"+invoiceId).val(balAmount);
				return false;
			}else if(recievedAmount == balAmount){
				showMessage('info','The Recieved Amount Should  Be Less Than ' + balAmount +' !');
			}
		}
		
		
		
		var FinalbalanceAmt				= Number(balAmount) - Number(recievedAmount);
		$("#balanceAmt_"+invoiceId).val(FinalbalanceAmt.toFixed(2))
	}

}

function isNumber(evt) {
	evt = (evt) ? evt : window.event;
	var charCode = (evt.which) ? evt.which : evt.keyCode;
	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	}
	return true;
}

function removeInvoiceFromApproval(invoiceId, approvalId, txnType, amount, subApprovalId){

	var jsonObject			= new Object();
	jsonObject["invoiceId"]		= invoiceId;
	jsonObject["approvalId"]	= approvalId;
	jsonObject["txnType"]		= txnType;
	jsonObject["amount"]		= amount;
	jsonObject["subApprovalId"]	= subApprovalId;
	
	showLayer();
	$.ajax({
		url: "removeInvoiceFromApproval",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.deleteFailed != undefined && data.deleteFailed){
				showMessage('errors','Cannot Remove , Already Deleted !');
			}else if(data.deleteSuccess != undefined && data.deleteSuccess){
				showMessage('success','Data Removed Successfully !');
			}
			setTimeout(function(){ location.reload(); }, 2000);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function approveVendorApprovalEntry(approvalId){

	
	var jsonObject			= new Object();
	jsonObject["approvalId"] = approvalId;
	
	var invoiceArr 			   = new Array();
	var txnTypeArr 			   = new Array();
	var paymentStatusArr 	   = new Array();
	var expectedPaymentDateArr = new Array();
	var receivedAmountArr	   = new Array();
	

	$("input[name=invoiceId]").each(function(){
		invoiceArr.push($(this).val().replace(/"/g, ""));
	});
	$("input[name=txnTypeId]").each(function(){
		txnTypeArr.push($(this).val());
	});
	$("select[name=approvedPaymentStatusId]").each(function(){
		paymentStatusArr.push($(this).val().replace(/"/g, ""));
	});
	$("input[name=expectedPaymentDate]").each(function(){
		expectedPaymentDateArr.push($(this).val());
	});
	$("input[name=paidAmount]").each(function(){
		receivedAmountArr.push($(this).val());
	});
	
	var array	 = new Array();
	
	var gradTotalAmount	= 0;
	for(var i =0 ; i< invoiceArr.length; i++){
		var invoiceDetails	= new Object();
		
		invoiceDetails.invoiceId			= invoiceArr[i];
		invoiceDetails.txnType				= txnTypeArr[i];
		invoiceDetails.paymentStatus		= paymentStatusArr[i];
		invoiceDetails.expectedPaymentDate	= expectedPaymentDateArr[i];
		invoiceDetails.receivedAmount		= receivedAmountArr[i];
		
		gradTotalAmount += Number(receivedAmountArr[i]);
		
		array.push(invoiceDetails);
	}
	jsonObject.invoiceDetails = JSON.stringify(array);
	
	jsonObject["totalApprovedAmount"] 		= gradTotalAmount;
	
    showLayer();
	$.ajax({
		url: "approveVendorApprovalEntry",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.approveSuccess != undefined && data.approveSuccess){
				showMessage('success','Data Approved Successfully !');
			}
			setTimeout(function(){ location.replace("approvedPayment.in?approvalId="+approvalId); }, 2000);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}