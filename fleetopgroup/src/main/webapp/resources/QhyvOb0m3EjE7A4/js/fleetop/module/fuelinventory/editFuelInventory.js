$(document).ready(function() {
	showLayer();
	var jsonObject								= new Object();
	jsonObject["fuelInvoiceId"]					= $('#fuelInvoiceId').val();
	jsonObject["companyId"]						= $('#companyId').val();
	
	$.ajax({
		url: "getFuelInvoiceDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			setFuelInvoiceDetails(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
});
var fuelInvoiceDetails;
function setFuelInvoiceDetails(data){
	 fuelInvoiceDetails = data.fuelInvoice;
	if(fuelInvoiceDetails != undefined ){
		$('#petrolPumpId').val(fuelInvoiceDetails.petrolPumpId);
		$('#petrolPumpName').val(fuelInvoiceDetails.petrolPumpName);
		if(fuelInvoiceDetails.discountGstTypeId == 1){
			$('#amountId').removeClass('btn-default');
			$('#amountId').removeClass('btn-success');
			$('#percentId').addClass('btn-success');
		}else{
			$('#percentId').removeClass('btn-success');
			$('#percentId').removeClass('btn-default');
			$('#amountId').addClass('btn-success');
		}
		$('#quantity').val(fuelInvoiceDetails.quantity);
		$('#unitprice').val(fuelInvoiceDetails.rate);
		$('#discount').val(fuelInvoiceDetails.discount);
		$('#tax').val(fuelInvoiceDetails.gst);
		$('#tatalcost').val(fuelInvoiceDetails.totalAmount);
		$('#selectVendor').val(fuelInvoiceDetails.vendorId);
		$('#selectVendorName').val(fuelInvoiceDetails.vendorName);
		$('#paymentType').val(fuelInvoiceDetails.paymentType);
		$('#paymentNumber').val(fuelInvoiceDetails.paymentNumber);
		$('#poNumber').val(fuelInvoiceDetails.poNumber);
		$('#invoiceNumber').val(fuelInvoiceDetails.invoiceNumber);
		$('#invoiceDate').val(fuelInvoiceDetails.invoiceDateStr);
		$('#invoiceAmount').val(fuelInvoiceDetails.invoiceAmount);
		$('#tallyCompanyId').val(fuelInvoiceDetails.tallyCompanyId);
		$('#tallyCompanyName').val(fuelInvoiceDetails.tallyCompanyName);
		$('#fuelDocument').val(fuelInvoiceDetails.documentId);
		$('#description').val(fuelInvoiceDetails.description);
		
		
		
		if($('#petrolPumpId').val() != undefined){
			$('#petrolPumpId').select2('data', {
				id : $('#petrolPumpId').val(),
				text : $('#petrolPumpName').val()
			});
		}	
	
		if($('#selectVendor').val() != undefined){
			$('#selectVendor').select2('data', {
				id : $('#selectVendor').val(),
				text : $('#selectVendorName').val()
			});
		}// 1st vendor slection
		vendorOnchange();	// 2nd onchane vendor
		$('#renPT_option').val(fuelInvoiceDetails.paymentTypeId);// 3rd paymet type selection
	}
	
}

$(document).ready(
		function($) {
			$('button[id=update]').click(function(e) {
				
				console.log("fuelInvoiceDetails",fuelInvoiceDetails);
				
				e.preventDefault();
				/*$('#saveFuelInv').hide();*/
				
				if(!validateFuelInventory()){
					$('#saveFuelInv').show();
					return false;
				}
				
				var jsonObject			= new Object();
				var defaultStockType	= 0;
				var remark				= "Edit Fuel Invoice";

			//	jsonObject["oldFuelInvoiceDetails"] 	  		=  fuelInvoiceDetails;
				jsonObject["fuelInvoiceId"] 	  				=  $('#fuelInvoiceId').val();
				jsonObject["petrolPumpId"] 	  					=  $('#petrolPumpId').val();
				jsonObject["quantity"] 							=  $('#quantity').val();
				jsonObject["unitprice"] 						=  $('#unitprice').val();
				jsonObject["discount"] 							=  $('#discount').val();
				jsonObject["tax"] 								=  $('#tax').val();
				jsonObject["tatalcost"] 						=  $('#tatalcost').val();
				jsonObject["discountTaxTypId"] 					=  $('#discountTaxTypId').val();
				jsonObject["vendorId"] 							=  $('#selectVendor').val();
				jsonObject["paymentTypeId"] 					=  $('#renPT_option').val();
				jsonObject["paymentNumber"] 					=  $('#paymentNumber').val();
				jsonObject["poNumber"] 							=  $('#poNumber').val();
				jsonObject["invoiceNumber"] 					=  $('#invoiceNumber').val();
				jsonObject["invoiceDate"] 						=  $('#invoiceDate').val();
				jsonObject["invoiceAmount"] 					=  $('#invoiceAmount').val();
				jsonObject["tallyCompanyId"] 					=  $('#tallyCompanyId').val();
				jsonObject["fuelDocument"] 						=  $('#fuelDocument').val();
				jsonObject["description"] 						=  $('#description').val();
				jsonObject["companyId"] 						=  $('#companyId').val();
				jsonObject["userId"] 							=  $('#userId').val();
				jsonObject["validateDoublePost"]				= true;
				jsonObject["unique-one-time-token"]				= $('#accessToken').val();
				jsonObject["balanceStock"] 						=  $('#quantity').val();
				jsonObject["updatedStock"] 						=  $('#quantity').val();
				jsonObject["remark"]							=  remark;
				jsonObject["stockType"] 						=  defaultStockType;
				jsonObject["fuelInvoiceEdit"] 					=  true;
				
				jsonObject["preQuantity"] 						=  fuelInvoiceDetails.quantity;
				jsonObject["preTotalAmount"] 					=  fuelInvoiceDetails.totalAmount	;
				jsonObject["preBalanceStock"] 					=  fuelInvoiceDetails.balanceStock;
				jsonObject["prePetrolPumpId"] 					=  fuelInvoiceDetails.petrolPumpId;
				jsonObject["preFuelInvoiceNumber"] 				=  fuelInvoiceDetails.fuelInvoiceNumber;
				jsonObject["transferedFromInvoiceId"] 			= fuelInvoiceDetails.transferedFromInvoiceId;
				

				
				var form = $('#fileUploadForm')[0];
			    var data = new FormData(form);
			    
			    if (allowBankPaymentDetails) {
					prepareObject(jsonObject);
				}
			    data.append("fuelInvoiceData", JSON.stringify(jsonObject));
				
			    showLayer();
				
				$.ajax({
						type: "POST",
						enctype: 'multipart/form-data',
						url: "updateFuelInventoryDetails",
						data: data,
						processData: false, //prevent jQuery from automatically transforming the data into a query string
				        contentType: false,
				        cache: false,
						success: function (data) {
			            	showMessage('info', 'updated successfully!');	
			            	window.location.replace("FuelInventory");
			             },
			             error: function (e) {
			            	 showMessage('errors', 'Some error occured!')
			            	 hideLayer();
			             }
				});
			
			});

		});