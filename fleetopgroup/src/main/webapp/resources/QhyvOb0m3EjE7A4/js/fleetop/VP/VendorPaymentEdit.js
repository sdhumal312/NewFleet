
function getVendorPaymentDetailsById(vendorPaymentId) {
	showLayer();
	var jsonObject					= new Object();

	jsonObject["vendorPaymentId"]			= vendorPaymentId;

	$.ajax({
		url: "vendorPaymentWS/getVendorPaymentDetailsById.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setVendorPaymentDetails(data);
		},
		error: function (e) {
			console.log("Error : " , e);
		}
	});
	setTimeout(function(){ hideLayer(); }, 500);	
}

function setVendorPaymentDetails(data) {
	if(data.vendorPayment != undefined) {
		var vendorPayment	= data.vendorPayment;
		
		
		$("#vehicleGroup1").val(vendorPayment.vehicleGroup);
		$("#selectVendor1").val(vendorPayment.vendorName);
		$("#transactionTypeId").val(vendorPayment.transactionTypeId);
		$("#transactionAmount").val(vendorPayment.transactionAmount);
		$("#gstAmount").val(vendorPayment.gstAmount);
		
		
		if(vendorPayment.transactionTypeId == 1) {
			$(".invoiceDetails").removeClass("hide");
			$(".paymentDetails").addClass("hide");
			$("#invoiceNumber").val(vendorPayment.invoiceNumber);
			$("#invoice_Date").datepicker({
		        autoclose: !0,
		        todayHighlight: !0,
		        format: "dd-mm-yyyy"
		    }), $("#invoice_Date").datepicker("setDate", vendorPayment.invoiceDateStr)
		} else {
			$(".paymentDetails").removeClass("hide");
			$(".invoiceDetails").addClass("hide");
			$("#paymentTypeId").val(vendorPayment.paymentTypeId);
			$("#payment_Date").datepicker({
		        autoclose: !0,
		        todayHighlight: !0,
		        format: "dd-mm-yyyy"
		    }), $("#payment_Date").datepicker("setDate", vendorPayment.paymentDateStr)
		    
			if(vendorPayment.paymentTypeId == 1) {
				$(".cashVoucherDetails").removeClass("hide");
				$(".chequeDetails").addClass("hide");
				$("#cashVoucherNumber").val(vendorPayment.cashVoucherNumber);				
			} else {
				$(".chequeDetails").removeClass("hide");
				$(".cashVoucherDetails").addClass("hide");
				$("#chequeNumber").val(vendorPayment.chequeNumber);
				$("#datePicker").datepicker("setDate", vendorPayment.chequeDateStr)				
			}
		}
	}
}

function updateVendorPayment() {

	showLayer();
	var jsonObject					= new Object();
	jsonObject["VehicleGroup"]		= $("#vehicleGroup1").val();
	jsonObject["vendorPaymentId"]	= $("#vendorPaymentId").val();
	jsonObject["transactionAmount"]	= $("#transactionAmount").val();
	jsonObject["gstAmount"]			= $("#gstAmount").val();
	jsonObject["invoiceNumber"]		= $("#invoiceNumber").val();
	jsonObject["invoiceDate"]		= $("#invoiceDate").val();
	jsonObject["paymentTypeId"]		= $("#paymentTypeId").val();
	jsonObject["cashVoucherNumber"]	= $("#cashVoucherNumber").val();
	jsonObject["chequeNumber"]		= $("#chequeNumber").val();
	jsonObject["chequeDate"]		= $("#chequeDate").val();

	$.ajax({
		url: "vendorPaymentWS/updateVendorPayment.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			showMessage("success", "Vendor Payment Updated.")
			showLayer();
		},
		error: function (e) {
			console.log("Error");
		}
	});
	setTimeout(function(){ hideLayer();location.reload(); }, 500);

}

function showHideChequeDetails() {
	if($("#paymentTypeId").val() == 7) {
		$(".chequeDetails").removeClass("hide");
		$(".cashVoucherDetails").addClass("hide");		
	} else {
		$(".cashVoucherDetails").removeClass("hide");
		$(".chequeDetails").addClass("hide");		
	}
}