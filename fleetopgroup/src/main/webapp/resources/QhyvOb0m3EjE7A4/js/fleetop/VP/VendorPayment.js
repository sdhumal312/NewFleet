$(document).ready(function() {
	//Below  Code added added for Group info in Vendor Report By Dev Y Start 
	$("#vehicleGroup").select2( {
        ajax: {
            url:"getVehicleGroup.in", dataType:"json", type:"GET", contentType:"application/json", data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.vGroup, slug: a.slug, id: a.gid
                        }
                    }
                    )
                }
            }
        }
    }
	// Code added for Group info in Vendor Report By Dev Y End 
    
    ),
	
	
	
	
	
	$("#selectVendor").select2( {
		minimumInputLength:3,
		minimumResultsForSearch:10,
		ajax:{
		url:"getVendorSearchListInventory.in?Action=FuncionarioSelect2",
		dataType:"json",
		type:"POST",
		contentType:"application/json",
		quietMillis:50,
		data:function(e){
			return{term:e}
			},
		results:function(e){
			return{
				results:$.map(e,function(e){
					return {
						text:e.vendorName+" - "+e.vendorLocation,slug:e.slug,id:e.vendorId
						}
					})
				}
			}
		}
	});
	showHideChequeDetails();
});

$(document).ready(function() {
	$("#selectVendor").change(function() {
		if($("#selectVendor").val() > 0) {
			showLayer();
			var jsonObject					= new Object();

			jsonObject["vendorId"]			= $("#selectVendor").val();

			$.ajax({
				url: "vendorPaymentWS/getVendorOpeningBalance.do",
				type: "POST",
				dataType: 'json',
				data: jsonObject,
				success: function (data) {
					if(data.vendorPayment != undefined) {						
						$("#openingBalance").val(data.vendorPayment.openingAmount);
					} else {
						$("#openingBalance").val(0);
					}
				},
				error: function (e) {
					console.log("Error");
				}
			});
			setTimeout(function(){ hideLayer(); }, 500);
		}
	})
});

function addVendorPayment() {
	if(validateVendorPayment()) {
		showLayer();
		var jsonObject					= new Object();
		
		jsonObject["VehicleGroup"]			= $("#vehicleGroup").val(); //This line code added for Group Info By Dev Y
		
		jsonObject["vendorId"]			= $("#selectVendor").val();		
		
		jsonObject["openingBalance"]	= $("#openingBalance").val();
		jsonObject["transactionTypeId"]	= $("#transactionTypeId").val();
		jsonObject["transactionAmount"]	= $("#transactionAmount").val();
		jsonObject["gstAmount"]			= $("#gstAmount").val();
		jsonObject["invoiceNumber"]		= $("#invoiceNumber").val();
		jsonObject["invoiceDate"]		= $("#invoiceDate").val();
		jsonObject["paymentTypeId"]		= $("#paymentTypeId").val();
		jsonObject["chequeNumber"]		= $("#chequeNumber").val();
		jsonObject["chequeDate"]		= $("#chequeDate").val();
		jsonObject["cashVoucherNumber"]	= $("#cashVoucherNumber").val();
		jsonObject["paymentDate"]		= $("#payment_Date").val();
		
		console.log("json info",jsonObject)
		

		$.ajax({
			url: "vendorPaymentWS/saveVendorPayment.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			
			success: function (data) {
				showMessage("success", "Vendor Payment Saved.");
				showLayer();
			},
			error: function (e) {
				console.log("Error");
			}
		});
		setTimeout(function(){ hideLayer();location.reload(); }, 500);
	}
}

function validateVendorPayment() {
	
	if($("#selectVendor").val() <= 0) {
		showMessage('info','Select Vendor');
		return false;
	}

	if($("#transactionTypeId").val() <= 0) {
		showMessage('info','Select Transaction Type');
		return false;
	}

	if($("#transactionAmount").val() == "" || $("#transactionAmount").val() <= 0) {
		showMessage('info','Enter Transaction Amount');
		return false;
	}

	if($("#transactionTypeId").val() == 1) {
		if($("#invoiceNumber").val() == "") {
			showMessage('info','Enter Invoice Number');
			return false;
		}
		
		if($("#invoiceDate").val() == "") {
			showMessage('info','Select Invoice Date');
			return false;
		}		
	}

	return true;
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

function showHidePaymentDetails() {
	if($("#transactionTypeId").val() == 1) {
		$(".invoiceDetails").removeClass("hide");
		$(".paymentDetails").addClass("hide");		
	} else if($("#transactionTypeId").val() == 2) {
		$(".paymentDetails").removeClass("hide");
		$(".invoiceDetails").addClass("hide");		
	} else {
		$(".paymentDetails").addClass("hide");		
		$(".invoiceDetails").addClass("hide");				
	}
}

