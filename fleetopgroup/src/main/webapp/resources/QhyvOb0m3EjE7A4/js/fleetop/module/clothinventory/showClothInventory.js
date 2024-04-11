$(document).ready(
		function($) {
			showLayer();
			
			var invoiceId = Number($('#invoiceId').val());
			var jsonObject			= new Object();
			jsonObject["invoiceId"] =  invoiceId;
			showLayer();
			$.ajax({
	             url: "getClothInvoiceDetails",
	             type: "POST",
	             dataType: 'json',
	             data: jsonObject,
	             success: function (data) {
	                 renderData(data);
	            	 hideLayer();
	             },
	             error: function (e) {
	            	 showMessage('errors', 'Some error occured!')
	            	 window.location.replace("ClothInventory.in");
	            	 hideLayer();
	             }
		});
			
});		
function renderData(data){
	if(data.clothInvoiceDto != null){
		setVendor(data);
		setInvoiceData(data.clothInvoiceDto);
		setInvoiceAmountData(data);
		var Id 		  = getUrlParameter('Id');
		var saved     = getUrlParameter('saved');
		var inserted  = Number(getUrlParameter('inserted'));
		var duplicate = Number(getUrlParameter('duplicate'));
		var deleted   = getUrlParameter('deleted');
		var deletedAmount = getUrlParameter('deletedAmount');
		var UpdateSuccess = getUrlParameter('UpdateSuccess');
		
		if(saved != undefined && saved != null && (saved == 'true' || saved == true)){
			showMessage('success','Data saved successfully!');
		}
		
		if(duplicate > 0){
			showMessage('success',''+inserted+' Battery Details saved successfully! '+duplicate+' entries was duplicate!');
		}else if(duplicate == 0){
			showMessage('success','All Battery Details saved successfully!');
		}
		
		if(deleted == true || deleted == 'true'){
			showMessage('success','Battery Details Deleted Successfully!');
		}
		if(deletedAmount == true || deletedAmount == 'true'){
			showMessage('success','Battery Amount Details Deleted Successfully!');
		}
		
		if(UpdateSuccess == true || UpdateSuccess == 'true'){
			showMessage('success','Battery Invoice Details Updated successfully!');
		}
		
	}else{
		showMessage('info', 'no data found!');
	}
}
function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
}

function setInvoiceAmountData(data){
	if(data.clothInventoryDetailsDtos != null && data.clothInventoryDetailsDtos.length > 0){
		$('#dataTable').show();
		$('#delete').hide();
		var srNo = 1, total = 0, subTotal = 0;
		for(var i = 0; i< data.clothInventoryDetailsDtos.length; i++){
			var clothInventoryDetailsDtos = data.clothInventoryDetailsDtos[i];
			
			var disc; 
			var taxes;
			
			if(clothInventoryDetailsDtos.discountTaxTypeId == 1){
				disc  = clothInventoryDetailsDtos.discount+'%';
				taxes = clothInventoryDetailsDtos.tax+'%';
			} else {
				disc  = clothInventoryDetailsDtos.discount;
				taxes = clothInventoryDetailsDtos.tax;
			}
			
			total 	 += clothInventoryDetailsDtos.total;
			subTotal += clothInventoryDetailsDtos.total;
			var tr =' <tr data-object-id="">'
				+'<td class="fit" value="'+srNo+'">'+srNo+'</td>'
				+'<td>'+clothInventoryDetailsDtos.clothTypesName+'</td>'
				+'<td>'+clothInventoryDetailsDtos.quantity+'</td>'
				+'<td>'+clothInventoryDetailsDtos.unitprice+'</td>'
				+'<td>'+disc+'</td>'
				+'<td>'+taxes+'</td>'
				+'<td><i class="fa fa-inr"></i>'+clothInventoryDetailsDtos.total+'</td>'
				
				if((data.clothInvoiceDto.paymentTypeId !=2 || data.clothInvoiceDto.vendorPaymentStatus == 2) &&  data.clothInvoiceDto.vendorPaymentStatus != 0){
					if(clothInventoryDetailsDtos.quantity == clothInventoryDetailsDtos.quantity){
						tr += '<td class="fit"><a class="btn btn-primary btn-sm" href="#" onclick="deleteClothInventoryDetails('+clothInventoryDetailsDtos.clothInventoryDetailsId+');">Delete</a<td>'
					}
				}
				+'</tr>';
			$('#batteryAmountBody').append(tr);
			srNo++;
			
		}
		
		$('#subTotal').html((subTotal).toFixed(2));
		$('#total').html((total).toFixed(2));
		$('#totalinvoiceamount').html((total).toFixed(2));
	}
}

function setVendor(data){
	if(data.clothInvoiceDto.vendorName != null && data.clothInvoiceDto.vendorId != null && data.clothInvoiceDto.vendorId > 0){
		var mydiv = document.getElementById("vendorinfo");
		var aTag = document.createElement('a');
		aTag.setAttribute('href','ShowVendor.in?vendorId='+data.clothInvoiceDto.vendorId+'');
		aTag.setAttribute('target', '_blank')
		aTag.innerHTML = ""+data.clothInvoiceDto.vendorName+"<br/>"+data.clothInvoiceDto.vendorLocation+" ";
		mydiv.appendChild(aTag);
	}
}
function setInvoiceData(clothInvoiceDto){

	$('#modulePaymentTypeId').val(clothInvoiceDto.paymentTypeId);
	$('#moduleIdentifier').val(6);
	
	$('#clothInvoiceNumber').html(clothInvoiceDto.clothInvoiceNumber);
	$('#invoiceDate').html(clothInvoiceDto.invoiceDateStr);
	$('#paymentTypeSpan').html(clothInvoiceDto.paymentType);
	
	$('#invoiceNumber').html(clothInvoiceDto.invoiceNumber);
	$('#tallyCompany').html(clothInvoiceDto.tallyCompanyName);
	$('#invoiceAmount').html((clothInvoiceDto.invoiceAmount).toFixed(2));
	$('#paymentNumber').html(clothInvoiceDto.paymentNumber);
	$('#poNumber').html("<a href='PurchaseOrders_Parts.in?ID="+clothInvoiceDto.purchaseOrderId+"'> "+clothInvoiceDto.poNumber+" </a>");
	if(clothInvoiceDto.approvalId > 0){
		$('#approvalNumber').html("<a href='ShowApprovalPayment.in?approvalId="+clothInvoiceDto.approvalId+"'> "+clothInvoiceDto.approvalNumber+" </a>");
	}
	$('#description').html(clothInvoiceDto.description);
	$('#subTotal').html((clothInvoiceDto.invoiceAmount).toFixed(2));
	$('#total').html((clothInvoiceDto.invoiceAmount).toFixed(2));
	$('#createdOn').html(clothInvoiceDto.createdOnStr);
	$('#lastUpdated').html(clothInvoiceDto.lastModifiedOnStr);
	$('#createdBy').html(clothInvoiceDto.createdBy);
	$('#lastupdatedBy').html(clothInvoiceDto.lastUpdatedBy);
	$('#clothTypeId').html(clothInvoiceDto.clothTypeStr);
	$('#wareHouseLocation').html(clothInvoiceDto.clothLocation);
	$('#subLocation').html(clothInvoiceDto.subLocation);
	if(clothInvoiceDto.vendorPaymentStatus == 4 || clothInvoiceDto.vendorPaymentStatus == 5){
		$('#vendorPaymentStatus').html(clothInvoiceDto.vendorPaymentStatusStr + " PAID" );
	}else{
		$('#vendorPaymentStatus').html(clothInvoiceDto.vendorPaymentStatusStr);
	}
}


	$(document).ready(function($) {
			var deleted = getUrlParameter('deleted');
			if(deleted == true || deleted == 'true'){
				showMessage('success', 'Data Deleted Successfully !');
			}
	});

	
	function deleteClothInventoryDetails(clothInventoryDetailsId){
		showLayer();
		var jsonObject			= new Object();
		jsonObject["clothInventoryDetailsId"] 	  =  clothInventoryDetailsId;
		jsonObject["invoiceId"] 	  =  Number($('#invoiceId').val());
		
		$.ajax({
	             url: "deleteClothInventoryDetails",
	             type: "POST",
	             dataType: 'json',
	             data: jsonObject,
	             success: function (data) {
	            	 window.location.replace("showClothInvoice?Id="+Number($('#invoiceId').val())+"&delete="+true+"");
	             },
	             error: function (e) {
	            	 showMessage('errors', 'Some error occured!')
	            	 hideLayer();
	             }
		});
	}
