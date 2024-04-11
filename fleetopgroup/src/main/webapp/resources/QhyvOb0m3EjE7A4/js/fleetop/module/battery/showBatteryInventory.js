$(document).ready(
		function($) {
			showLayer();
			
			var invoiceId = Number($('#invoiceId').val());
			var jsonObject			= new Object();
			jsonObject["invoiceId"] =  invoiceId;
			showLayer();
			$.ajax({
	             url: "getBatteryInvoiceDetails",
	             type: "POST",
	             dataType: 'json',
	             data: jsonObject,
	             success: function (data) {
	                 renderData(data);
	            	 hideLayer();
	             },
	             error: function (e) {
	            	 showMessage('errors', 'Some error occured!')
	            	 hideLayer();
	             }
		});
});		
function renderData(data){
	
	if(data.batteryInvoice != null){
		setVendor(data);
		setInvoiceData(data.batteryInvoice);
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
	if(data.batteryAmountList != null && data.batteryAmountList.length > 0){
		$('#dataTable').show();
		$('#delete').hide();
		var srNo = 1;
		var totalAmount=0;
		for(var i = 0; i< data.batteryAmountList.length; i++){
			var batteryAmount = data.batteryAmountList[i];
			
			var disc; 
			var taxes;
			
			if(batteryAmount.discountTaxTypeId == 1){
				disc  = batteryAmount.discount+'%';
				taxes = batteryAmount.tax+'%';
			} else {
				disc  = batteryAmount.discount;
				taxes = batteryAmount.tax;
			}
			totalAmount +=batteryAmount.totalAmount;
			var tr =' <tr data-object-id="">'
				+'<td class="fit" value="'+srNo+'">'+srNo+'</td>'
				+'<td id="batteryType'+batteryAmount.batteryAmountId+'">'+batteryAmount.manufacturerName+'  ' +batteryAmount.batteryType+' </td>'
				+'<td>'+batteryAmount.batteryCapacity+'</td>'
				+'<td>'+batteryAmount.batteryQuantity.toFixed(2)+'</td>'
				+'<td>'+batteryAmount.unitCost.toFixed(2)+'</td>'
				+'<td>'+disc+'</td>'
				+'<td>'+taxes+'</td>'
				+'<td><i class="fa fa-inr"></i>'+batteryAmount.totalAmount.toFixed(2)+'</td>'
				  if((data.batteryInvoice.paymentTypeId !=2 || data.batteryInvoice.vendorPaymentStatus == 2) &&  data.batteryInvoice.vendorPaymentStatus != 0) {
					if(batteryAmount.batteryQuantity == batteryAmount.batteryAsignNumber){
						tr += '<td class="fit"><a class="btn btn-primary btn-sm" href="#" onclick="deleteBatteryAmountDetails('+batteryAmount.batteryAmountId+');">Delete</a<td>'
				     }
				}	
				+'</tr>';
			$('#batteryAmountBody').append(tr);
			srNo++;
			
			if(data.batteryList != undefined && data.batteryList != null && data.batteryList.length > 0){
				var battery =' <tr data-object-id="">'
					+'<td colspan="8"><div class="row"><div class="col-md-11"><table class="table"><thead>'
					+'<tr class="breadcrumb"><th class="icon">No</th><th class="icon ar">Battery Serial Number</th><th class="icon ar">Amount</th><th class="fit">Action</th></tr>'
					+'</thead><tbody>';
				var sr = 1;
				for(var j = 0 ; j < data.batteryList.length; j++){
					if(data.batteryList[j].batteryAmountId == batteryAmount.batteryAmountId ){
						battery += '<tr data-object-id="" class="ng-scope">'
							       +'<td class="fit">'+sr+'</td><td class="icon">'+data.batteryList[j].batterySerialNumber+'</td>'
							       +'<td class="icon ar"><i class="fa fa-inr"></i>'+data.batteryList[j].batteryAmount.toFixed(2)+'</td>'
							    
							       if((data.batteryInvoice.paymentTypeId !=2 || data.batteryInvoice.vendorPaymentStatus == 2) &&  data.batteryInvoice.vendorPaymentStatus != 0) {    
								       if(data.batteryList[j].batteryStatusId == 1){
								    		  battery += '<td class="fit"><a class="btn btn-primary btn-sm" href="#" onclick="deleteBatteryDetails('+data.batteryList[j].batteryId+');">Delete</a<td>'
								    	  }else{
								    		  battery += '<td class="fit">'+data.batteryList[j].batteryStatus+'<td>'  
								    	  }
							       }
					}
					sr++;
				}	
				battery += ''
					+'</td>'
					+'</tr>';
				$('#batteryAmountBody').append(battery);
			}
			if(batteryAmount.batteryAsignNumber != 0){
				var tr =' <tr data-object-id="">'
					+'<td colspan = "4"><a href="#" onclick="createPopup('+batteryAmount.batteryAmountId+', '+batteryAmount.batteryAsignNumber+', '+batteryAmount.batteryInvoiceId+');">Add Battery Details</a></td>'
					+'</tr>';
				$('#batteryAmountBody').append(tr);
			}
			
		}
		if($('#rounupconfig').val()==true||$('#rounupconfig').val()=='true'){
		$('#subTotal').html(totalAmount.toFixed(2));
		$('#total').html(totalAmount.toFixed(2));
		$('#invoiceTotal').html(totalAmount.toFixed(2));
		}
	}
}

function setVendor(data){
	if(data.batteryInvoice.vendorName != null && data.batteryInvoice.vendorId != null && data.batteryInvoice.vendorId > 0){
		var mydiv = document.getElementById("vendorinfo");
		var aTag = document.createElement('a');
		aTag.setAttribute('href','ShowVendor.in?vendorId='+data.batteryInvoice.vendorId+'');
		aTag.setAttribute('target', '_blank')
		aTag.innerHTML = ""+data.batteryInvoice.vendorName+"<br/>"+data.batteryInvoice.vendorLocation+" ";
		mydiv.appendChild(aTag);
	}
}
function setInvoiceData(batteryInvoice){
	
	$('#moduleId').val(batteryInvoice.batteryInvoiceId);
	$('#modulePaymentTypeId').val(batteryInvoice.paymentTypeId);
	$('#moduleIdentifier').val(3);
	
	$('#batteryInvoiceNumber').html(batteryInvoice.batteryInvoiceNumber);
	$('#invoiceDate').html(batteryInvoice.invoiceDateStr);
	$('#paymentTypeSpan').html(batteryInvoice.paymentStatus);
	$('#invoiceNumber').html(batteryInvoice.invoiceNumber);
	$('#invoiceAmount').html((batteryInvoice.invoiceAmount).toFixed(2));
	if(batteryInvoice.tallyCompanyName != undefined)
		$('#tallyCompany').html(batteryInvoice.tallyCompanyName);
	$('#paymentNumber').html(batteryInvoice.paymentNumber);
	
	$('#poNumber').html("<a href='PurchaseOrders_Parts.in?ID="+batteryInvoice.purchaseOrderId+"'> "+batteryInvoice.poNumber+" </a>");
	if(batteryInvoice.approvalId > 0){
		$('#approvalNumber').html("<a href='ShowApprovalPayment.in?approvalId="+batteryInvoice.approvalId+"'> "+batteryInvoice.approvalNumber+" </a>");
	}
	$('#description').html(batteryInvoice.description);
	if($('#rounupconfig').val()!= true||$('#rounupconfig').val()!= 'true'){
		$('#subTotal').html((batteryInvoice.invoiceAmount).toFixed(2));
		$('#total').html((batteryInvoice.invoiceAmount).toFixed(2));
	}
	$('#createdOn').html(batteryInvoice.created);
	$('#lastUpdated').html(batteryInvoice.lastModified);
	$('#subLocationId').val(batteryInvoice.subLocationId);
	$('#subLocation').html(batteryInvoice.subLocation);
	$('#wareHouseLocation').html(batteryInvoice.batteryLocation);
	if(batteryInvoice.vendorPaymentStatus == 4 || batteryInvoice.vendorPaymentStatus == 5){
		$('#vendorPaymentStatus').html(batteryInvoice.vendorPaymentStatusStr +" PAID ");
	}else{
		$('#vendorPaymentStatus').html(batteryInvoice.vendorPaymentStatusStr );
	}
	console.log(batteryInvoice.batteryInvoiceId," &&& ",batteryInvoice.paymentTypeId);
	setPaymentDetailsLink(batteryInvoice.batteryInvoiceId,3,batteryInvoice.paymentTypeId);
}
//location.reload();
function createPopup(batteryAmountId, batteryAsignNumber, batteryInvoiceId){
	var batteryType = $('#batteryType'+batteryAmountId).html();
	$('#modelBody').html('');
	for(var i = 0 ; i< batteryAsignNumber; i++){
		var modelBody = "";
		
		modelBody = '<div class="row1">'
					+'<label class="col-md-1 col-sm-1 col-xs-12 control-label">Battery No :</label>'
					+'<div class="col-md-3 col-sm-3 col-xs-12"> <input type="text" class="form-text" name="batterySerialNumber" id="batterySerialNumber'+i+'"></div>'
					+'<label class="L-size control-label"> Battery type :</label>'
					+'<div class="col-md-2 col-sm-2 col-xs-12"><input type="text" value="'+batteryType+'" class="form-text" readonly="readonly" id="quantity'+i+'" required="required">'
					+'</div><br />' ;
		$('#modelBody').append(modelBody);
					
	}
	var hiddenFields	= '<input type="hidden" id="batteryInvoiceId" /><input type="hidden" id="batteryAmountId" />';
	$('#modelBody').append(hiddenFields);
	
	$('#batteryInvoiceId').val(batteryInvoiceId);
	$('#batteryAmountId').val(batteryAmountId);
	$('#saveBatterySerialNumber').modal();
	
}
$(document).ready(
		function($) {
			$('button[id=serialNoSubmit]').click(function(e) {
				e.preventDefault();
				
				var jsonObject			= new Object();
				var batterySerialNumber = new Array();
				
				jsonObject["batteryInvoiceId"] 	  					=  $('#batteryInvoiceId').val();
				jsonObject["batteryAmountId"] 						=  $('#batteryAmountId').val();
				jsonObject["subLocationId"] 						=  $('#subLocationId').val();
				
				var notEmpty	= false;

				$("input[name=batterySerialNumber]").each(function(){
						if($(this).val() != null && $(this).val() != '' && $(this).val().length > 0){
							batterySerialNumber.push($(this).val());
							notEmpty = true;
						}
				});
				
				if(!notEmpty){
					showMessage('info', 'Please enter at least one battery number to be save !');
					return false;
				}
				
				var array	 = new Array();
				for(var i =0 ; i< batterySerialNumber.length; i++){
					var batteryManDetails	= new Object();
					batteryManDetails.batterySerialNumber	= batterySerialNumber[i];
					array.push(batteryManDetails);
				}
				
				jsonObject.batteryDetails = JSON.stringify(array);
				 let map 	= {};
			     let result = false;
			     for(let i = 0; i < batterySerialNumber.length; i++) {
			    	 if(map[batterySerialNumber[i]]) {
			             result = true;
			             break;
			          }
			          map[batterySerialNumber[i]] = true;
			     }
			     if(result) {
			         showMessage('info','Duplicate Serial Number Found');
			         return false;
			      } 
				
				
				showLayer();
				$.ajax({
			             url: "saveBatterySerialNumber",
			             type: "POST",
			             dataType: 'json',
			             data: jsonObject,
			             success: function (data) {
			            	 window.location.replace("showBatteryInvoice?Id="+data.batteryInvoice+"&inserted="+data.inserted+" &duplicate="+data.duplicate+"");
			             },
			             error: function (e) {
			            	 showMessage('errors', 'Some error occured!')
			            	 hideLayer();
			             }
				});
			
			});

	});

	function deleteBatteryDetails(batteryId){
		showLayer();
		var jsonObject			= new Object();
		jsonObject["batteryId"] 	  =  batteryId;
		
		$.ajax({
	             url: "deleteBatteryDetails",
	             type: "POST",
	             dataType: 'json',
	             data: jsonObject,
	             success: function (data) {
	            	 window.location.replace("showBatteryInvoice?Id="+data.batteryInvoice+"&deleted="+true+"");
	             },
	             error: function (e) {
	            	 showMessage('errors', 'Some error occured!')
	            	 hideLayer();
	             }
		});
		
	}
	function deleteBatteryAmountDetails(batteryAmountId){
		showLayer();
		var jsonObject			= new Object();
		jsonObject["batteryAmountId"] 	  =  batteryAmountId;
		
		$.ajax({
	             url: "deleteBatteryAmountDetails",
	             type: "POST",
	             dataType: 'json',
	             data: jsonObject,
	             success: function (data) {
	            	 window.location.replace("showBatteryInvoice?Id="+data.batteryInvoice+"&deletedAmount="+true+"");
	             },
	             error: function (e) {
	            	 showMessage('errors', 'Some error occured!')
	            	 hideLayer();
	             }
		});
	}
	function deleteBatteryInventory(){
		showLayer();
		var jsonObject			= new Object();
		var invoiceId 		 	= Number(getUrlParameter('Id'));
		jsonObject["batteryInvoiceId"] 	  =  invoiceId ;
		
		$.ajax({
	             url: "deleteBatteryInventory",
	             type: "POST",
	             dataType: 'json',
	             data: jsonObject,
	             success: function (data) {
	            	 window.location.replace("BatteryInventory.in?delete="+data.deleted+"");
	             },
	             error: function (e) {
	            	 showMessage('errors', 'Some error occured!')
	            	 hideLayer();
	             }
		});
	
	}