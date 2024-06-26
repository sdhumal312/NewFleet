var invoiceId 		= null;
var vendorID		= $("#vendorId").val();
var dateRangeFrom	= $("#dateRangeFrom").val();
var dateRangeTo		= $("#dateRangeTo").val();
var SelectServiceID	= true;


$(document).ready(function() {
	$('input[name*=datepicker]').each(function(obj){
		$(this).datepicker({
	        autoclose: !0,
	        todayHighlight: !0,
	        format: "yyyy-mm-dd"
	    });
		
	});
});

function changeFuelPaymentType(obj){
	if(obj.id != undefined){
		var invoiceId				= obj.id.split("_")[1];
		var paymentTypeID 			= $("#"+obj.id).val();
		var balAmount				= Number($("#balAmt_"+invoiceId).val());
	}
	if(paymentTypeID == 0){ //Please Select
		$("#receivedAmt_"+invoiceId).prop('readonly',true);
		$("#receivedAmt_"+invoiceId).val(0);
		$("#balanceAmt_"+invoiceId).val(balAmount);
	}
	else if(paymentTypeID == 1){ //clear
		$("#receivedAmt_"+invoiceId).prop('readonly',true);
		$("#receivedAmt_"+invoiceId).val(balAmount)
		$("#balanceAmt_"+invoiceId).val(0)
	} 
	else if(paymentTypeID == 2){ //partial
		$("#receivedAmt_"+invoiceId).removeAttr('readonly');
		$("#receivedAmt_"+invoiceId).val(0)
		$("#balanceAmt_"+invoiceId).val(balAmount)
	}
	else if(paymentTypeID == 3){ //Negotiate
		$("#receivedAmt_"+invoiceId).removeAttr('readonly');
		$("#receivedAmt_"+invoiceId).val(0)
		$("#balanceAmt_"+invoiceId).val(balAmount)
	}
}

function calculateFuelBalance(obj){
	if(obj != null && obj.id != undefined){
		var invoiceId				= obj.id.split("_")[1];
		var recievedAmount 			= Number(obj.value);
		var balanceAmount 			= Number($("#balanceAmt_"+invoiceId).val());
		var balAmount				= Number($("#balAmt_"+invoiceId).val());
		var paymentType 			= $("#PaymentType_"+invoiceId).val();
		if(paymentType == 0){ //Please Select
			$("#paidAmt_"+invoiceId).val(0)
			$("#balanceAmt_"+invoiceId).val(0);
		}
		else if(paymentType == 1){ //clear
			$("#receivedAmt"+invoiceId).val(balAmount)
			$("#balanceAmt_"+invoiceId).val(0);
		}
		else if(paymentType == 2){ //partial
			if(recievedAmount > balAmount ){
				$("#receivedAmt_"+invoiceId).val(0);
				$("#balanceAmt_"+invoiceId).val(balAmount);
				showMessage('info','The Recieved Amount ShouldNot Be Greater Than ' + balAmount +' !');
				return false;
			}
		} 
		else if(paymentType == 3){ //Negotiate
			if(recievedAmount > balAmount){
				showMessage('info','The Recieved Amount ShouldNot Be Greater Than ' + balAmount +' !');
				$("#receivedAmt_"+invoiceId).val(0);
				$("#balanceAmt_"+invoiceId).val(balAmount);
				return false;
			}
		}

		var FinalbalanceAmt				= Number(balAmount) - Number(recievedAmount);
		$("#balanceAmt_"+invoiceId).val(FinalbalanceAmt.toFixed(2))

	}

}

function createFuelVendorPaymentApproval(){

	var 	jsonArr					= new Array();
	var 	checkBoxArray			= new Array();
	var		jsonObject				= new Object();
	var 	recievedAmt				= 0;
	var 	balanceAmount 			= 0;
	var 	balAmount				= 0;
	var 	paymentType				= 0;
	var		jsonObject		        = null;
	var 	invoiceAmount 	        = null;
	var 	paidAmount		        = null;
	var 	PaymentMode				= null;
	var 	paymentModeNum			= null;
	var 	paymentDate				= null;
	

	$.each($("input[name*=SelectService_id]:checked"), function(){ 
		checkBoxArray.push(this.id.split("_")[1]);
	});
	if(checkBoxArray.length == 0 ){
		showMessage('info','please Select Atleast one Record Approval');
		return false;
	}

	for(var i =0 ; i < checkBoxArray.length;i++){

		
		paymentType 			= $("#PaymentType_"+checkBoxArray[i]).val();	
		jsonObject				= new Object();
		invoiceAmount 			= Number($("#invoiceAmount_"+checkBoxArray[i]).text()); 
		paidAmount				= Number($("#paidAmt_"+checkBoxArray[i]).val());
		recievedAmt				= Number($("#receivedAmt_"+checkBoxArray[i]).val());
		balanceAmount 			= Number($("#balanceAmt_"+checkBoxArray[i]).val());
		balAmount				= Number($("#balAmt_"+checkBoxArray[i]).val());
		PaymentMode				= Number($("#PaymentMode_"+checkBoxArray[i]).val());
		paymentModeNum			= $("#paymentModeNum_"+checkBoxArray[i]).val();
		paymentDate				= $("#datepicker_"+checkBoxArray[i]).val();
		
		if(paymentDate == undefined || paymentDate == "" ){
			showMessage('info','Please Select Date' );
			return false;
		} 
		if(paymentType == undefined || paymentType == 0){
			showMessage('info','Please Select Payment Type' );
			return false;
		}
		
		if(paymentType == 2 || paymentType == 3){
			if(recievedAmt <= 0){
				showMessage('info','Receive Amount should Be Greater Than 0')
				return false;
			}
			if(recievedAmt == balAmount){
				paymentType = $("#PaymentType_"+checkBoxArray[i]).val(1);
				showMessage('info','You are Entering Full Amount then the payment type Must be Clear')
				hideLayer();
				return false;
			}
		} 
		
		if(PaymentMode == undefined || PaymentMode == "" || PaymentMode == 0 ){
			showMessage('info','Please Select Payment Mode' );
			return false;
		}
		if(paymentModeNum == undefined || paymentModeNum == "" ){
			showMessage('info','Please Select Transaction No' );
			return false;
		}

		if(checkBoxArray[i] > 0 && checkBoxArray[i] != undefined){
			jsonObject.invoiceId 				= checkBoxArray[i];
			jsonObject.paymentType 				= paymentType;
			jsonObject.dateRangeFrom 			= dateRangeFrom;
			jsonObject.dateRangeTo 				= dateRangeTo;
			jsonObject.invoiceAmount 			= invoiceAmount; 
			jsonObject.PaymentMode 				= PaymentMode; 
			jsonObject.paymentModeNum 			= paymentModeNum; 
			jsonObject.paymentDate 				= paymentDate; 

			if(paymentType == 3){

				jsonObject.approvalAmount  		= recievedAmt;
				jsonObject.recievedAmount  		= paidAmount + recievedAmt;
				jsonObject.balanceAmt			= 0;
				jsonObject.discountAmt			= balanceAmount ;
			}else if(paymentType==2){
				jsonObject.approvalAmount  		= recievedAmt;
				jsonObject.recievedAmount  		= paidAmount + recievedAmt;
				jsonObject.balanceAmt			= balanceAmount ;
				jsonObject.discountAmt			= 0;
			}else{
				jsonObject.approvalAmount  		= recievedAmt;
				jsonObject.recievedAmount  		= invoiceAmount; 
				jsonObject.balanceAmt			= 0;
				jsonObject.discountAmt			= 0;

			}
		}
		jsonArr.push(jsonObject);
		SelectServiceID = false;
	}



	jsonObject.partInvoicePayment = JSON.stringify(jsonArr);
	jsonObject.vendorID			  = vendorID;
	
	if(paymentType != 0 && (recievedAmt <= balAmount)){

		showLayer();
		$.ajax({
			url: "vendorPaymentWS/createFuelVendorPaymentApproval",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				showMessage('success', 'Payment Successfully Done..')
				setTimeout(function(){
					window.location.replace("1/ShowVendor.in?vendorId="+vendorID+"&page=1#!");
					hideLayer();
				},150);
			},
			error: function (e) {
				showMessage('info', 'Some error occured!')
				hideLayer();
			}
		});
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


function popUp(obj){
	var id 	= obj.id
	var val	= $("#"+id).val();
	if(val == 'PARTIALLY'){
	$('#popupPaidInvoice').modal('show');
	getPaymentList(id);
	}
}
function getPaymentList(id){
	//$('input[name*=paymentStatus]').each(function(){
		var paymentStatusVal 		= $("#"+id).val();
		
		
		var invoiceId				= id.split("_")[1];
		var	jsonObject				= new Object();

		jsonObject["invoiceId"] 		=  invoiceId;

		if(invoiceId!= null ||invoiceId!= undefined){
			$.ajax({
				url: "vendorPaymentWS/getPartiallyPaidApproval",
				type: "POST",
				dataType: 'json',
				data: jsonObject,
				success: function (data) {
					getApprovalPaymentList(data);
					hideLayer();
				},
				error: function (e) {
					showMessage('info', 'Some error occured!')
					hideLayer();
				}
			});
		}
}


function getApprovalPaymentList(data) {
	var paidList			= null;
	var table=$('<table border="1" width="100%">');
	var thead=$('<thead>');
	var tbody = $('<tbody>');
	var tr1 = $('<tr >');
	var td1	= $('<td>');

	$("#approval").empty();

	tbody.append(tr1);
	if(data.approvalList != undefined) {

		paidList	= data.approvalList;
		
		var tr1		=$('<tr>')

		var th1		=$('<th align="right">')
		var th2		=$('<th align="right">')
		var th3		=$('<th align="right">')
		var th4		=$('<th align="right">')
		var th5		=$('<th align="right">')
		var th6		=$('<th align="right">')
		var th7		=$('<th align="right">')
		var th8		=$('<th align="right">')
		var th9		=$('<th align="right">')

		th1.append("Sr No")	
		th2.append("Approval ID")	
		th3.append("Vendor Name")	
		th4.append("Vendor Type")	
		th5.append("Date")	
		th6.append("Created By")	
		th7.append("Place")	
		th8.append("Amount")		
		th9.append("Paid Status")	


		tr1.append(th1);
		tr1.append(th2);
		tr1.append(th3);
		tr1.append(th4);
		tr1.append(th5);
		tr1.append(th6);
		tr1.append(th7);
		tr1.append(th8);
		tr1.append(th9);

		thead.append(tr1);

		for(var i = 0; i < paidList.length; i++ ) {

			var tr2 = $('<tr width="100%">');

			var td1 	= $('<td align="left">');
			var td2		= $('<td align="left">');
			var td3		= $('<td align="left">');
			var td4		= $('<td align="left">');
			var td5		= $('<td align="left">');
			var td6		= $('<td align="left">');
			var td7		= $('<td align="left">');
			var td8		= $('<td align="left">');
			var td9		= $('<td align="left">');

			td1.append(i+1);
			td2.append(paidList[i].approvalId);
			td3.append(paidList[i].approvalvendorName);  
			td4.append(paidList[i].approvalvendorType);  
			td5.append(paidList[i].created);             
			td6.append(paidList[i].approvalCreateBy);    
			td7.append(paidList[i].approvalPlace);       
			td8.append(paidList[i].approvalTotal);       
			td9.append(paidList[i].approvalStatus);      


			tr2.append(td1);
			tr2.append(td2);
			tr2.append(td3);
			tr2.append(td4);
			tr2.append(td5);
			tr2.append(td6);
			tr2.append(td7);
			tr2.append(td8);
			tr2.append(td9);

			tbody.append(tr2);

		}
		table.append(thead);
		table.append(tbody);

		$("#approval").append(table);

	}
}
function changePaymentModeType(obj){//dirct payment
	var paymentTypeId 			= obj.id;
	var invoiceId				= paymentTypeId.split("_")[1];
	//paymentModeNum_
	if($("#"+paymentTypeId).val() != 0){
		$(".paymentMode").removeClass("hide");
		$("#paymentModeNum_"+invoiceId).removeClass("hide");
		$("#paymentModeNum_"+invoiceId).val("");
	} else {
		$("#paymentModeNum_"+invoiceId).addClass("hide");
		var flag = true;
		$('input[name*=modeOfPaymentId]').each(function(obj){
			var paymentMode = $(this).val();
			if(paymentMode == 0){
				flag = true;
			} else {
				flag = false;
			}
		});
		if(flag){
			$("#paymentModeNum_"+invoiceId).addClass("hide");
		} else {
			$("#paymentModeNum_"+invoiceId).removeClass("hide");
		}
		
	}
}
function addNegotiableAmount(obj){//srs fuel payment
	var paymentTypeId 			= obj.id;
	var paymentTypeValue 		= $("#"+paymentTypeId).val();
	var draffAmount 			= Number($("#renewal_Amount").val().replace(",",""));
	
	if(paymentTypeValue == 5){//negotiable
		$(".paidAmnt").removeClass("hide");
		//$("#paymentModeNum_"+invoiceId).removeClass("hide");
		$("#paidAmount").val(0);
		$("#negotiableAmount").val(draffAmount);
	}else if(paymentTypeValue == 1){//clear
		$(".paidAmnt").addClass("hide");
		//$("#paymentModeNum_"+invoiceId).addClass("hide");
		$("#paidAmount").val(draffAmount);
		$("#negotiableAmount").val(0);
	}
}

