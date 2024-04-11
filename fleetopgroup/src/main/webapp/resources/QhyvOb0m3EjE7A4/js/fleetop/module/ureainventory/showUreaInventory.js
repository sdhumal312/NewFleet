$(document).ready(
		function($) {
			showLayer();
			
			var invoiceId = Number($('#invoiceId').val());
			var jsonObject			= new Object();
			jsonObject["invoiceId"] =  invoiceId;
			showLayer();
			$.ajax({
	             url: "getUreaInvoiceDetails",
	             type: "POST",
	             dataType: 'json',
	             data: jsonObject,
	             success: function (data) {
	                 renderData(data);
	            	 hideLayer();
	             },
	             error: function (e) {
	            	 showMessage('errors', 'Some error occured!')
	            	 window.location.replace("UreaInventory.in");
	            	 hideLayer();
	             }
		});
			
});		
function renderData(data){
	if(data.ureaInvoiceDto != null){
		setVendor(data);
		setInvoiceData(data.ureaInvoiceDto);
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
		
		
		if(deleted == true || deleted == 'true'){
			showMessage('success','Data Deleted Successfully!');
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
	if(data.ureaInvoiceToDetailsDtos != null && data.ureaInvoiceToDetailsDtos.length > 0){
		$('#dataTable').show();
		var srNo = 1, total = 0, subTotal = 0;
		var ureaInvoiceDto = data.ureaInvoiceDto;
		for(var i = 0; i< data.ureaInvoiceToDetailsDtos.length; i++){
			var ureaInvoiceToDetailsDtos = data.ureaInvoiceToDetailsDtos[i];
			
			var disc; 
			var taxes;
			
			if(ureaInvoiceToDetailsDtos.discountTaxTypeId == 1){
				disc  = ureaInvoiceToDetailsDtos.discount+'%';
				taxes = ureaInvoiceToDetailsDtos.tax+'%';
			} else {
				disc  = ureaInvoiceToDetailsDtos.discount;
				taxes = ureaInvoiceToDetailsDtos.tax;
			}
			
			total 	 += ureaInvoiceToDetailsDtos.total;
			subTotal += ureaInvoiceToDetailsDtos.total;
			var tr =' <tr id="rowColor'+srNo+'">'
				+'<td class="fit" value="'+srNo+'">'+srNo+'</td>'
				+'<td><a target="_blank" id="hyperRow'+srNo+'">'+ureaInvoiceToDetailsDtos.manufacturerName+'</a></td>'
				+'<td>'+ureaInvoiceToDetailsDtos.quantity+'</td>'
				+'<td>'+ureaInvoiceToDetailsDtos.transferQuantity+'</td>'
				+'<td>'+ureaInvoiceToDetailsDtos.stockQuantity+'</td>'
				+'<td>'+ureaInvoiceToDetailsDtos.unitprice+'</td>'
				+'<td>'+disc+'</td>'
				+'<td>'+taxes+'</td>'
				+'<td><i class="fa fa-inr"></i>'+ureaInvoiceToDetailsDtos.total+'</td>'
				if((ureaInvoiceDto.paymentTypeId !=2 || ureaInvoiceDto.vendorPaymentStatus == 2) &&  ureaInvoiceDto.vendorPaymentStatus != 0) {
				if(ureaInvoiceToDetailsDtos.quantity == ureaInvoiceToDetailsDtos.quantity){
					tr += '<td class="fit"><a class="btn btn-primary btn-sm" href="#" onclick="deleteUreaInventoryDetails('+ureaInvoiceToDetailsDtos.ureaInvoiceToDetailsId+');">Delete</a<td>'
			     }
				 }
				+'</tr>';
			$('#batteryAmountBody').append(tr);
				
				if(ureaInvoiceToDetailsDtos.ureaTransferDetailsId != null && ureaInvoiceToDetailsDtos.ureaTransferDetailsId != undefined && ureaInvoiceToDetailsDtos.ureaTransferDetailsId >0) {
					$("#rowColor"+srNo+"").css("background-color","lightblue");
					/*$("#transferId").text(ureaInvoiceToDetailsDtos.ureaTransferId);
					$("#rowColor"+srNo+"").on({
						mouseenter: function () {
							$("#transferUreaModal").modal('show');
						    },
						    mouseleave: function () {
						    	setTimeout(function(){  
						    	$("#transferUreaModal").modal('hide');
						    	}, 3000);
						    	
						    }
					});*/
					
					$("#rowColor"+srNo+"").prop('title','This is Transferd Urea UT-'+ureaInvoiceToDetailsDtos.ureaTransferId+'')
					$("#hyperRow"+srNo+"").attr("href", "showUreaTransferDetails.in?Id="+ureaInvoiceToDetailsDtos.ureaTransferId+" ","_blank")
				}
				
			srNo++;
			
		}
		
		$('#subTotal').html((subTotal).toFixed(2));
		$('#total').html((total).toFixed(2));
		$('#invoiceAmounttotal').text((total).toFixed(2));
	}
}

function setVendor(data){
	if(data.ureaInvoiceDto.vendorName != null && data.ureaInvoiceDto.vendorId != null && data.ureaInvoiceDto.vendorId > 0){
		var mydiv = document.getElementById("vendorinfo");
		var aTag = document.createElement('a');
		aTag.setAttribute('href','ShowVendor.in?vendorId='+data.ureaInvoiceDto.vendorId+'');
		aTag.setAttribute('target', '_blank')
		aTag.innerHTML = ""+data.ureaInvoiceDto.vendorName+"<br/>"+data.ureaInvoiceDto.vendorLocation+" ";
		mydiv.appendChild(aTag);
	}
}
function setInvoiceData(ureaInvoiceDto){
	
	$('#modulePaymentTypeId').val(ureaInvoiceDto.paymentTypeId);
	$('#moduleIdentifier').val(5);
	
	$('#clothInvoiceNumber').html(ureaInvoiceDto.ureaInvoiceNumber);
	$('#invoiceDate').html(ureaInvoiceDto.invoiceDateStr);
	$('#paymentTypeSpan').html(ureaInvoiceDto.paymentType);
	$('#invoiceNumber').html(ureaInvoiceDto.invoiceNumber);
	$('#invoiceAmount').html((ureaInvoiceDto.invoiceAmount).toFixed(2));
	$('#paymentNumber').html(ureaInvoiceDto.paymentNumber);
	$('#tallyCompany').html(ureaInvoiceDto.tallyCompanyName);
	if(ureaInvoiceDto.purchaseOrderId > 0){
		$('#poNumber').html("<a href='PurchaseOrders_Parts.in?ID="+ureaInvoiceDto.purchaseOrderId+"'> PO-"+ureaInvoiceDto.poNumber+" </a>");
		$("#AddMoreParts").hide();
	}
	if(ureaInvoiceDto.approvalId > 0){
		$('#approvalNumber').html("<a href='ShowApprovalPayment.in?approvalId="+ureaInvoiceDto.approvalId+"'> "+ureaInvoiceDto.approvalNumber+" </a>");
	}
	if((ureaInvoiceDto.vendorPaymentStatus == 4 || ureaInvoiceDto.vendorPaymentStatus == 5) && ureaInvoiceDto.vendorPaymentStatus != 0){
		$('#vendorPaymentStatus').html(ureaInvoiceDto.vendorPaymentStatusStr +" ");
	}else{
		$('#vendorPaymentStatus').html(ureaInvoiceDto.vendorPaymentStatusStr );
	}
	$('#description').html(ureaInvoiceDto.description);
	$('#subTotal').html((ureaInvoiceDto.invoiceAmount).toFixed(2));
	$('#total').html((ureaInvoiceDto.invoiceAmount).toFixed(2));
	$('#createdOn').html(ureaInvoiceDto.createdOnStr);
	$('#lastUpdated').html(ureaInvoiceDto.lastModifiedOnStr);
	$('#createdBy').html(ureaInvoiceDto.createdBy);
	$('#lastupdatedBy').html(ureaInvoiceDto.lastUpdatedBy);
	$('#wareHouseLocation').html(ureaInvoiceDto.locationName);
	$('#subLocation').html(ureaInvoiceDto.subLocation);
	//$('#clothTypeId').html(ureaInvoiceDto.clothTypeStr);
}


	$(document).ready(function($) {
			var deleted = getUrlParameter('deleted');
			if(deleted == true || deleted == 'true'){
				showMessage('success', 'Data Deleted Successfully !');
			}
	});
	
	function AddPart(){
		
		$('#configureMorePart').modal('show');
	}	
	
	function validateAddPart(){
		
		var forPartId	= true;
		var quantity	= true;
		var unitprice	= true;
		var discount	= true;
		var tax			= true;
		
		if($('#manufacturerMandatory').val() == true || $('#manufacturerMandatory').val() == 'true' ){
		 $('input[name*=ureaManufacturer]').each(function(i,obj){
			if($(this).val() == undefined || $(this).val() == ""){
				forPartId	= false;
				this.focus();
				return false;
			}
			
		 });
		 
		 if(!forPartId ) {
				showMessage('errors', 'Please Select UreaManufacture!');
				return false;
			}
		}
		
		 $('input[name*=quantity_many]').each(function(obj){
			 if($(this).val() < 0 || $(this).val() == undefined || $(this).val() == ""){
				 quantity = false;
				 this.focus();
				 return false;
			 }
		 });
		 
		 if(!quantity ) {
				showMessage('errors', 'Please Select Quantity!');
				return false;
			}
		 
		 $('input[name*=unitprice_many]').each(function(obj){
			 if($(this).val() < 0 ||$(this).val() == undefined || $(this).val() == ""){
				 unitprice = false;
				 this.focus();
				 return false;
			 }
		 });
		 
		 if(!unitprice) {
				showMessage('errors', 'Please Select UnitPrice!');
				return false;
			}
		 $('input[name*=discount_many]').each(function(obj){
			 if($(this).val() < 0 ||$(this).val() == undefined || $(this).val() == ""){
				 discount = false;
				 this.focus();
				 return false;
			 }
		 });
		 
		 if(!discount) {
				showMessage('errors', 'Please Select Discount!');
				return false;
			}
		 $('input[name*=tax_many]').each(function(obj){
			 if($(this).val() < 0 || $(this).val() == undefined || $(this).val() == ""){
				 tax = false;
				 this.focus();
				 return false;
			 }
		 });
		 
		 if(!tax) {
				showMessage('errors', 'Please Select Tax!');
				return false;
			}
		 
		 
	return true;
	}
	
	