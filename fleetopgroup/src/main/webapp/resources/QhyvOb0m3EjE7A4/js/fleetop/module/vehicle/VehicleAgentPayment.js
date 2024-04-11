$(document).ready(function() {
	
	$(function() {
		function a(a, b) {
			$("#ReportDailydate").val(a.format("YYYY-MM-DD")+" to "+b.format("YYYY-MM-DD"))
		}
		a(moment().subtract(1, "days"), moment()), $("#ReportDailydate").daterangepicker( {
			ranges: {
	            Today: [moment(), moment()],
	            Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")],
	            "Last 7 Days": [moment().subtract(6, "days"), moment()],
	            "This Month": [moment().startOf("month"), moment().endOf("month")],
	            "Last Month": [moment().subtract(1, "months").startOf("month"), moment().subtract(1, "months").endOf("month")]
	        }
		}
		, a)
	});
	
});

function setVehicleAgentPaymentData(paymentList, response){
	$("#creditTable tr").remove();
	$("#deditTable tr").remove();
	$('#dateRange').html(response.dateRange);
	
	var crsrNo = 1;
	var dbsrNo = 1;
	var creditTotal	= 0;
	var debitTotal	= 0;
	 for(var i = 0; i< paymentList.length; i++){
		var payment = paymentList[i];
		if(payment.txnTypeId == 1){
			creditTotal	+= payment.creditAmount;
			var tr =' <tr data-object-id="">'
				+'<td class="fit">'+crsrNo+'</td>'
				+'<td>'+payment.numberWithtype+'</td>'
				+'<td>'+payment.transactionDateStr+'</td>'
				+'<td>'+payment.creditAmount+'</td>'
				+'</tr>';
			$('#creditTable').append(tr);
			crsrNo ++;
		}
		if(payment.txnTypeId == 2 || payment.txnTypeId == 5){
			debitTotal	+= payment.debitAmount;
			var tr ="";
			if(payment.txnTypeId == 5){
				tr =' <tr style=" background-color: #b0b317;">'
				+'<td class="fit" value="'+dbsrNo+'">'+dbsrNo+'</td>'
				+'<td>'+payment.numberWithtype+'</td>'
				+'<td>'+payment.transactionDateStr+'</td>'
				+'<td>'+payment.debitAmount+'</td>'
				+'</tr>';
			}else{
				tr =' <tr id="'+payment.transactionId+'">'
				+'<td class="fit" value="'+dbsrNo+'">'+dbsrNo+'</td>'
				+'<td>'+payment.numberWithtype+'</td>'
				+'<td>'+payment.transactionDateStr+'</td>'
				+'<td>'+payment.debitAmount+'</td>'
				+'</tr>';
			}
			$('#deditTable').append(tr);
			dbsrNo++;
		}
		
	}
	 var balance = response.openingBalance+creditTotal-debitTotal;
	 $('#debitTotal').html(debitTotal);
	 $('#creditTotal').html(creditTotal);
	 $('#balance').html(response.closingBalance);
	 $('#openingBalance').html(response.openingBalance);
	 if(balance > 0 && !response.isPaymentDone){
		 $('#createApproval').show();
	 }else{
		 $('#createApproval').hide();
	 }
	 	$('#advanceTable').show();
		$('#print').show();
		$('#toDate').val(response.toDate);
		
		setDataForPaymentModal(response, balance);
}
function getVehicleAgentPaymentDetails(){
	var jsonObject			= new Object();
	jsonObject["vid"]				= $('#vid').val();
	jsonObject["dateRange"]			= $('#ReportDailydate').val();
	
	showLayer();
	$.ajax({
		url: "getVehicleAgentPaymentDetails.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		
		success: function (response) {
			if(response.paymentList != undefined && response.paymentList.length > 0){
				setVehicleAgentPaymentData(response.paymentList, response);
			}else{
				showMessage('info', 'No record found !');
				$('#advanceTable').hide();
				$('#print').hide();
				$('#createApproval').hide();
			}
			$('#searchServiceEntriesByDate').modal('hide');
			hideLayer();
		},
		error: function (e) {
			console.log("Error");
			hideLayer();
		}
	});
}
function creteVehicleAgentApproval(){
	$('#makePaymentModal').modal('show');

}

function setDataForPaymentModal(data, balance){
	$('#vehicleId').val(data.vid);
	$('#toDate').val(data.toDate);
	$('#totalAmount').val(balance);
	$('#paidAmount').val(balance);
}

function onPaymentModeSelect(){
	if($('#paymentMode').val() == 1){
		$('#paidAmount').val($('#totalAmount').val());
	}else{
		$('#paidAmount').val(0);
		$('#paidAmount').attr('readonly', false);
	}
}
function validatePaidAmount(){
	var totalAmount = Number($('#totalAmount').val());
	var paidAmount  = Number($('#paidAmount').val());
	
	if(paidAmount > totalAmount){
		if(Number($('#paymentMode').val()) == 1){
			$('#paidAmount').val($('#totalAmount').val());
		}else{
			$('#paidAmount').val(0);
		}
		showMessage('errors', 'Paid Amount Cannot be greater than Total Amount');
		return false;
	}else if(paidAmount <= 0){
		showMessage('errors', 'Paid Amount will be greater than zero !');
		return false;
	}else if(paidAmount == totalAmount && Number($('#paymentMode').val()) != 1){
		showMessage('errors', 'Paid Amount and Total Amount are equal, Please change payment type to clear !');
		return false;
	}
	return true;
}

function makeVehicleAgentPayment(){
	var jsonObject					= new Object();
	
	jsonObject["vid"]				= $('#vehicleId').val();
	jsonObject["totalAmount"]		= $('#totalAmount').val();
	jsonObject["paymentMode"]		= $('#paymentMode').val();
	jsonObject["paymentType"]		= $('#paymentType').val();
	jsonObject["paidAmount"]		= $('#paidAmount').val();
	jsonObject["paymentDate"]		= $('#paymentDate').val();
	jsonObject["remark"]			= $('#remark').val();
	jsonObject["companyId"]			= $('#companyId').val();
	jsonObject["userId"]			= $('#userId').val();
	jsonObject["toDate"]			= $('#toDate').val();
	
	showLayer();
	$.ajax({
		url: "saveVehicleAgentPayment.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		
		success: function (response) {
			$('#makePaymentModal').modal('hide');
			showMessage('success', 'Payment Done Successfully !');
			location.reload();
		},
		error: function (e) {
			console.log("Error");
			hideLayer();
		}
	});

}