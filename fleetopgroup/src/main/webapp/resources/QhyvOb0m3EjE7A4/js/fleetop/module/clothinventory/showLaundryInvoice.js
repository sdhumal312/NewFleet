$(document).ready(
		function($) {
			showLayer();
			
			var invoiceId = Number($('#invoiceId').val());
			var jsonObject			= new Object();
			jsonObject["invoiceId"] =  invoiceId;
			showLayer();
			$.ajax({
	             url: "getLaundryInvoiceDetails",
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
		$("#vid").select2({
	        minimumInputLength: 2,
	        minimumResultsForSearch: 10,
	        ajax: {
	            url: "getVehicleListFuel.in?Action=FuncionarioSelect2",
	            dataType: "json",
	            type: "POST",
	            contentType: "application/json",
	            quietMillis: 50,
	            data: function(e) {
	                return {
	                    term: e
	                }
	            },
	            results: function(e) {
	                return {
	                    results: $.map(e, function(e) {
	                        return {
	                            text: e.vehicle_registration,
	                            slug: e.slug,
	                            id: e.vid
	                        }
	                    })
	                }
	            }
	        }
	    });		
});		
function renderData(data){
	if(data.upholsterySendLaundryInvoiceDto != null){
		setVendor(data);
		setInvoiceData(data.upholsterySendLaundryInvoiceDto);
		setInvoiceAmountData(data);
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
	if(data.sentLaundryClothDetailsList != null && data.sentLaundryClothDetailsList.length > 0){
		$('#dataTable').show();
		$('#delete').hide();
		var srNo = 1, total = 0, subTotal = 0;
		for(var i = 0; i< data.sentLaundryClothDetailsList.length; i++){
			var sentLaundryClothDetailsList = data.sentLaundryClothDetailsList[i];
			
			var inputParameters = sentLaundryClothDetailsList.quantity+', '+sentLaundryClothDetailsList.receivedQuantity+','+sentLaundryClothDetailsList.laundryClothDetailsId+', '+sentLaundryClothDetailsList.laundryInvoiceId+','+sentLaundryClothDetailsList.clothTypesId+','+sentLaundryClothDetailsList.damagedQuantity+','+sentLaundryClothDetailsList.losedQuantity;
			total 	 += sentLaundryClothDetailsList.clothTotal;
			subTotal += sentLaundryClothDetailsList.clothTotal;
			var tr =' <tr data-object-id="">'
				+'<td class="fit" value="'+srNo+'">'+srNo+'</td>'
				+'<td>'+sentLaundryClothDetailsList.clothTypeName+'</td>'
				+'<td><a href="#" onclick="toggleReceived_quantity('+inputParameters+')"><i class="fa fa-cube"></i>'+sentLaundryClothDetailsList.quantity+'</a></td>'
				+'<td><a href="#" onclick="getReceivedClothDetails('+sentLaundryClothDetailsList.laundryClothDetailsId+','+sentLaundryClothDetailsList.laundryInvoiceId+');">'+sentLaundryClothDetailsList.receivedQuantity+'</a></td>'
				+'<td>'+sentLaundryClothDetailsList.damagedQuantity+'</td>'
				+'<td>'+sentLaundryClothDetailsList.losedQuantity+'</td>'
				+'<td>'+sentLaundryClothDetailsList.remainingQuantity+'</td>'
				+'<td>'+sentLaundryClothDetailsList.clothEachCost+'</td>'
				+'<td>'+sentLaundryClothDetailsList.clothDiscount+'</td>'
				+'<td>'+sentLaundryClothDetailsList.clothGst+'</td>'
				+'<td><i class="fa fa-inr"></i>'+sentLaundryClothDetailsList.clothTotal+'</td>'
				if(sentLaundryClothDetailsList.quantity == sentLaundryClothDetailsList.clothTotal){
					tr += '<td class="fit"><a class="btn btn-primary btn-sm" href="#" onclick="deleteClothInventoryDetails('+sentLaundryClothDetailsList.clothInventoryDetailsId+');">Delete</a<td>'
			     }
				tr += '<td class="fit"><a class="btn btn-success btn-sm" href="#" onclick="damageWashingQty('+sentLaundryClothDetailsList.laundryClothDetailsId+');">Receive Damage</a<td>'
				tr += '<td class="fit"><a class="btn btn-primary btn-sm" href="#" onclick="lostWashingQty('+sentLaundryClothDetailsList.laundryClothDetailsId+');">Add Lost</a<td>'
				tr += '<td class="fit"><a class="btn btn-info btn-sm" href="#" onclick="addVehicleLaundry('+sentLaundryClothDetailsList.clothTypesId+', '+sentLaundryClothDetailsList.quantity+', '+sentLaundryClothDetailsList.laundryClothDetailsId+');">Add Vehicle Details</a<td>'
		     
				+'</tr>';
				
			$('#batteryAmountBody').append(tr);
			srNo++;
			
		}
		
		$('#subTotal').html(subTotal.toFixed(2));
		$('#total').html(total.toFixed(2));
	}
}

function setVendor(data){
	if(data.upholsterySendLaundryInvoiceDto.vendorName != null && data.upholsterySendLaundryInvoiceDto.vendorId != null && data.upholsterySendLaundryInvoiceDto.vendorId > 0){
		var mydiv = document.getElementById("vendorinfo");
		var aTag = document.createElement('a');
		aTag.setAttribute('href','ShowVendor.in?vendorId='+data.upholsterySendLaundryInvoiceDto.vendorId+'');
		aTag.setAttribute('target', '_blank')
		aTag.innerHTML = ""+data.upholsterySendLaundryInvoiceDto.vendorName+"<br/>"+data.upholsterySendLaundryInvoiceDto.vendorLocation+" ";
		mydiv.appendChild(aTag);
	}
}
function setInvoiceData(laundryInvoiceDto){
	$('#clothInvoiceNumber').html(laundryInvoiceDto.laundryInvoiceNumber);
	$('#invoiceDate').html(laundryInvoiceDto.sentDateStr);
	$('#paymentType').html(laundryInvoiceDto.paymentType);
	$('#invoiceNumber').html(laundryInvoiceDto.expectedReceiveDateStr);
	$('#invoiceAmount').html(laundryInvoiceDto.totalCost);
	$('#paymentNumber').html(laundryInvoiceDto.paymentNumber);
	$('#allQuantity').html(laundryInvoiceDto.totalQuantity);
	$('#totalRecevedQuantity').html(laundryInvoiceDto.receivedQuantity);
	$('#description').html(laundryInvoiceDto.description);
	$('#subTotal').html(laundryInvoiceDto.invoiceAmount);
	$('#total').html(laundryInvoiceDto.invoiceAmount);
	$('#createdOn').html(laundryInvoiceDto.creationDateStr);
	$('#lastUpdated').html(laundryInvoiceDto.lastModifiedOnStr);
	$('#createdBy').html(laundryInvoiceDto.createdBy);
	$('#lastupdatedBy').html(laundryInvoiceDto.lastModifiedBy);
	$('#locationId').val(laundryInvoiceDto.wareHouseLocationId);
	if(laundryInvoiceDto.tallyCompanyId != null && laundryInvoiceDto.tallyCompanyId > 0){
		$('#tallyCompanyRow').show();
		$('#tallyCompany').html(laundryInvoiceDto.tallyCompanyName);
	}else{
		$('#tallyCompanyRow').hide();
	}
}

function toggleReceived_quantity(quantity, receiveQuantity, laundryClothDetailsId, laundryInvoiceId, clothTypesId, damagedQuantity, losedQuantity){
	$('#selectedQuantity').val(quantity - (receiveQuantity + damagedQuantity + losedQuantity));
	$('#laundryClothDetailsId').val(laundryClothDetailsId);
	$('#laundryInvoiceId').val(laundryInvoiceId);
	$('#totalQuantity').val(quantity);
	$('#receivedQuantity').val(receiveQuantity);
	$('#clothTypesId').val(clothTypesId);
	$('#receiveDmgQuantity').val(damagedQuantity);
	$('#receiveLosedQuantity').val(losedQuantity);
	
	$('#receiveClothFromLaundry').modal('show');
}

function receiveClothFromLaundry(){

	var jsonObject			= new Object();
	jsonObject["laundryClothDetailsId"] 	  =  $('#laundryClothDetailsId').val();
	jsonObject["laundryInvoiceId"] 			  =  $('#laundryInvoiceId').val();
	jsonObject["receiveQuantity"] 			  =  $('#receiveQuantity').val();
	jsonObject["receiveDate"] 			  	  =  $('#receiveDate').val();
	jsonObject["receiveDescription"] 		  =  $('#receiveDescription').val();
	jsonObject["locationId"] 		 		  =  $('#locationId').val();
	jsonObject["clothTypesId"] 		 		  =  $('#clothTypesId').val();
	jsonObject["receiveDmgQuantity"] 		  =  $('#receiveDmgQuantity').val();
	jsonObject["receiveLosedQuantity"] 		  =  $('#receiveLosedQuantity').val();
	jsonObject["selectedQuantity"] 		  	  =  $('#selectedQuantity').val();
	
	
	if(Number($('#selectedQuantity').val()) == 0){
		showMessage('info', 'Quantity Already Received In Full');
		return false;
	} else if (Number($('#receiveQuantity').val()) > Number($('#selectedQuantity').val())) {
		showMessage('info', 'Receive Quantity cannot be greater than '+(Number($('#selectedQuantity').val())).toFixed(2));
		return false;
	}
	
	if($('#receiveQuantity').val() == ''){
		showMessage('info', 'Please Select Receive Quantity !');
		return false;
	}
	
	if(Number($('#receiveQuantity').val()) == 0){
		showMessage('info', 'Receive quantity should be greater than 0 !');
		return false;
	}
	
	if($('#receiveDate').val() == ''){
		showMessage('info', 'Please Select Receive Date !');
		return false;
	}
	
	showLayer();
	$.ajax({
             url: "receiveClothFromLaundry",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 
            	 if(data.QuantityExceeded != undefined && data.QuantityExceeded == true){
     				$('#receiveClothFromLaundry').modal('hide');
     				showMessage('info', 'You can not Receive Quantity More Than Assigned Quantity !');
     				hideLayer();
     				return false;
     			}
            	 
            	 window.location.replace("showLaundryInvoice?Id="+Number($('#invoiceId').val()));
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});

}
function getReceivedClothDetails(laundryClothDetailsId, laundryInvoiceId){
	
	showLayer();
	
	var jsonObject			= new Object();
	jsonObject["laundryClothDetailsId"] 	  =  laundryClothDetailsId;
	jsonObject["laundryInvoiceId"] 			  =  laundryInvoiceId;
	
	$.ajax({
             url: "getReceivedClothDetails",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 renderModelData(data);
            	 hideLayer();

             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});

}
function renderModelData(data){
	if(data.historyList != null && data.historyList.length > 0){
		$('#receiveTable').show();
		$("#tableBody tr").remove();
		var srNo = 1;
		var curl = "";
		var serviceUrl = "";
	    var vehicleUrl = "";
		for(var i = 0; i< data.historyList.length; i++){
			
			var tr =' <tr>'
					+'<td class="fit" value="'+srNo+'">'+srNo+'</td>'
					+'<td>'+data.historyList[i].clothTypesName+'</td>'
					+'<td>'+data.historyList[i].receiveDateStr+'</td>'
					+'<td>'+data.historyList[i].receiveQuantity+'</td>'
					+'<td>'+data.historyList[i].receivedBy+'</td>'
					+'<td>'+data.historyList[i].description+'</td>'
				+'</tr>';
			$('#tableBody').append(tr);
			srNo++;
		}
		$('#receivedHistoryModal').modal('show');
	}else{
		$('#receiveTable').hide();
		showMessage('info', 'No record found !');
	}
}

function damageWashingQty(clothDetailsId) {
	
	showLayer();
	var invoiceId = Number($('#invoiceId').val());
	var jsonObject					= new Object();
	jsonObject["invoiceId"] 		=  invoiceId;
	jsonObject["clothDetailsId"] 	=  clothDetailsId;
	
	showLayer();
	$.ajax({
         url: "getDamageWashingQtyDetails",
         type: "POST",
         dataType: 'json',
         data: jsonObject,
         success: function (damageData) {
        	 
        	 $('#totalQty').val(damageData.sentLaundryClothDetailsList.quantity);
        	 $('#receivedQty').val(damageData.sentLaundryClothDetailsList.receivedQuantity);
        	 $('#damageLosedQuantity').val(damageData.sentLaundryClothDetailsList.losedQuantity);
        	 $('#previousDmgQuantity').val(damageData.sentLaundryClothDetailsList.damagedQuantity);
        	 $('#damagelaundryClothDetailsId').val(clothDetailsId);
        	 $('#damagelaundryInvoiceId').val(damageData.sentLaundryClothDetailsList.laundryInvoiceId);
        	 $('#damagelocationId').val(damageData.upholsterySendLaundryInvoiceDto.wareHouseLocationId);
        	 $('#damageclothTypesId').val(damageData.sentLaundryClothDetailsList.clothTypesId);
        	 
        	 var totalQty 		 = Number($('#totalQty').val());
        	 var previousRecvQty = Number($('#receivedQty').val());
        	 var previousDamgQty = Number($('#previousDmgQuantity').val());
        	 var previousLostQty = Number($('#damageLosedQuantity').val());
        	 var totalValidate	 = previousRecvQty + previousDamgQty + previousLostQty;
        	 
        	 $('#validateDamageQuantity').val(totalQty - totalValidate);
        	 $('#damageClothFromLaundry').modal('show');
        	 hideLayer();
         },
         error: function (e) {
        	 showMessage('errors', 'Some error occured!')
        	 //window.location.replace("ClothInventory.in");
        	 hideLayer();
         }
	});
	
}

function saveDamageWashingQty(){

	var jsonObject			= new Object();
	jsonObject["damagelaundryClothDetailsId"] 	  =  $('#damagelaundryClothDetailsId').val();
	jsonObject["damagelaundryInvoiceId"] 		  =  $('#damagelaundryInvoiceId').val();
	jsonObject["damageQuantity"] 			  	  =  $('#damageQuantity').val();
	jsonObject["damageReceiveDate"] 			  =  $('#damageReceiveDate').val();
	jsonObject["damageDescription"] 		  	  =  $('#damageDescription').val();
	jsonObject["damagelocationId"] 		 		  =  $('#damagelocationId').val();
	jsonObject["damageclothTypesId"] 		 	  =  $('#damageclothTypesId').val();
	jsonObject["validateDamageQuantity"] 		  =  $('#validateDamageQuantity').val();
	jsonObject["receivedQty"] 		  			  =  $('#receivedQty').val();
	jsonObject["damageLosedQuantity"] 		  	  =  $('#damageLosedQuantity').val();
	
	
	if(Number($('#validateDamageQuantity').val()) == 0){
		showMessage('info', 'Quantity Already Received In Full');
		return false;
	} else if(Number($('#damageQuantity').val()) > Number($('#validateDamageQuantity').val())){
		showMessage('info', 'Damage Quantity cannot be greater than '+(Number($('#validateDamageQuantity').val())).toFixed(2));
		return false;
	}

	if($('#damageQuantity').val() == ''){
		showMessage('info', 'Please Select Damage Quantity !');
		return false;
	}
	
	if(Number($('#damageQuantity').val()) == 0){
		showMessage('info', 'Damage quantity should be greater than 0 !');
		return false;
	}
	
	if($('#damageReceiveDate').val() == ''){
		showMessage('info', 'Please Select Receive Date !');
		return false;
	}
	
	showLayer();
	$.ajax({
             url: "saveDamageWashingQty",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 
            	 if(data.QuantityExceeded != undefined && data.QuantityExceeded == true){
      				$('#damageClothFromLaundry').modal('hide');
      				showMessage('info', 'You can not Receive Damage Quantity More Than Assigned Quantity !');
      				hideLayer();
      				return false;
      			}
            	 
            	 window.location.replace("showLaundryInvoice?Id="+Number($('#invoiceId').val()));
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});
	
}

function lostWashingQty(clothDetailsId) {
	
	showLayer();
	var invoiceId = Number($('#invoiceId').val());
	var jsonObject					= new Object();
	jsonObject["invoiceId"] 		=  invoiceId;
	jsonObject["clothDetailsId"] 	=  clothDetailsId;
	
	showLayer();
	$.ajax({
         url: "getDamageWashingQtyDetails",
         type: "POST",
         dataType: 'json',
         data: jsonObject,
         success: function (damageData) {
        	 $('#totQty').val(damageData.sentLaundryClothDetailsList.quantity);
        	 $('#recvQty').val(damageData.sentLaundryClothDetailsList.receivedQuantity);
        	 $('#prevLosedQuantity').val(damageData.sentLaundryClothDetailsList.losedQuantity);
        	 $('#prevDmgQuantity').val(damageData.sentLaundryClothDetailsList.damagedQuantity);
        	 $('#lostlaundryClothDetailsId').val(clothDetailsId);
        	 $('#lostlaundryInvoiceId').val(damageData.sentLaundryClothDetailsList.laundryInvoiceId);
        	 $('#lostlocationId').val(damageData.upholsterySendLaundryInvoiceDto.wareHouseLocationId);
        	 $('#lostclothTypesId').val(damageData.sentLaundryClothDetailsList.clothTypesId);
        	 
        	 var totQty 	 = Number($('#totQty').val());
        	 var prevRecvQty = Number($('#recvQty').val());
        	 var prevDamgQty = Number($('#prevDmgQuantity').val());
        	 var prevLostQty = Number($('#prevLosedQuantity').val());
        	 var totalVal	 = prevRecvQty + prevDamgQty + prevLostQty;
        	 
        	 $('#validateLostQuantity').val(totQty - totalVal);
        	 $('#lostClothFromLaundry').modal('show');
        	 hideLayer();
         },
         error: function (e) {
        	 showMessage('errors', 'Some error occured!')
        	 //window.location.replace("ClothInventory.in");
        	 hideLayer();
         }
});
	
}

function savelostWashingQty(){

	var jsonObject			= new Object();
	jsonObject["lostlaundryClothDetailsId"] 	  =  $('#lostlaundryClothDetailsId').val();
	jsonObject["lostlaundryInvoiceId"] 		  	  =  $('#lostlaundryInvoiceId').val();
	jsonObject["lostQuantity"] 			  	  	  =  $('#lostQuantity').val();
	jsonObject["lostReceiveDate"] 			  	  =  $('#lostReceiveDate').val();
	jsonObject["lostDescription"] 		  	 	  =  $('#lostDescription').val();
	jsonObject["lostlocationId"] 		 		  =  $('#lostlocationId').val();
	jsonObject["lostclothTypesId"] 		 	 	  =  $('#lostclothTypesId').val();
	jsonObject["validateLostQuantity"] 		  	  =  $('#validateLostQuantity').val();
	jsonObject["recvQty"] 		  			 	  =  $('#recvQty').val();
	jsonObject["prevDmgQuantity"] 		  	  	  =  $('#prevDmgQuantity').val();
	
	
	if(Number($('#validateLostQuantity').val()) == 0){
		showMessage('info', 'Quantity Already Received In Full');
		return false;
	}else if(Number($('#lostQuantity').val()) > Number($('#validateLostQuantity').val())){
		showMessage('info', 'Lost Quantity cannot be greater than '+Number($('#validateLostQuantity').val()));
		return false;
	}
	
	if($('#lostQuantity').val() == ''){
		showMessage('info', 'Please Select Lost Quantity !');
		return false;
	}
	
	if(Number($('#lostQuantity').val()) == 0){
		showMessage('info', 'Lost quantity should be greater than 0 !');
		return false;
	}
	
	if($('#lostReceiveDate').val() == ''){
		showMessage('info', 'Please Select Receive Date !');
		return false;
	}
	
	showLayer();
	$.ajax({
             url: "saveLostWashingQty",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 
            	 if(data.QuantityExceeded != undefined && data.QuantityExceeded == true){
       				$('#lostClothFromLaundry').modal('hide');
       				showMessage('info', 'You can not Add Lost Quantity More Than Assigned Quantity !');
       				hideLayer();
       				return false;
       			}
            	 
            	 window.location.replace("showLaundryInvoice?Id="+Number($('#invoiceId').val()));
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});
	
}

function addVehicleLaundry(clothTypesId, quantity, laundryClothDetailsId){
	
	console.log('quantity ', quantity);
	
	$('#clothTypesId').val(clothTypesId);
	$('#maxVehicleQty').val(quantity);
	$('#laundryClothDetailsId').val(laundryClothDetailsId);
	
	var jsonObject					  = new Object();
	
	jsonObject["laundryInvoiceId"] 	  =  $('#invoiceId').val();
	jsonObject["clothTypeId"] 		  =  clothTypesId;
	
	showLayer();
	$.ajax({
             url: "getVehicleLaundryDetailsList",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 setVehicleLaundryDetailsList(data);
            	 hideLayer();
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});
	

	
	$('#addVehicleLaundryModel').modal('show');
}

function setVehicleLaundryDetailsList(data){
	$('#vehicleBody').empty();
	var asignVehicleQty = 0;
	if(data.vehicleLaundryList != undefined && data.vehicleLaundryList.length > 0){
		
		for(var i = 0; i< data.vehicleLaundryList.length; i++){
			var tr = '<tr>'
				+'<td>'+data.vehicleLaundryList[i].vehicleRegistration+'</td>'
				+'<td>'+data.vehicleLaundryList[i].clothTypesStr+'</td>'
				+'<td>'+data.vehicleLaundryList[i].quantity+'</td>'
				+'<td><a href="#" class="btn btn-warning" onclick="removeVehicleLaundry('+data.vehicleLaundryList[i].vehicleLaundryDetailsId+');">Remove</a></td>'
				+'</tr>';
			
			asignVehicleQty += data.vehicleLaundryList[i].quantity;
			
			$('#vehicleBody').append(tr);
		}
		
	}
	$('#asignVehicleQty').val(asignVehicleQty);
}

function removeVehicleLaundry(vehicleLaundryDetailsId){

	var jsonObject					  = new Object();
	jsonObject["vehicleLaundryDetailsId"] 	  = vehicleLaundryDetailsId;
	
	
	showLayer();
	$.ajax({
             url: "removeVehicleLaundry",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 showMessage('success', 'Data Deleted SuccessFully !');
            	 window.location.replace("showLaundryInvoice?Id="+Number($('#invoiceId').val()));
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});
	

}

function saveVehicleLaundryDetails(){
	
	
	if((Number($('#vehicleQuantity').val())+ Number($('#asignVehicleQty').val())) >  Number($('#maxVehicleQty').val())){
		showMessage('info', 'All Asigned quantity cannot be grater than '+Number($('#maxVehicleQty').val()));
		return false;
	}
	
	var jsonObject					  = new Object();
	jsonObject["laundryInvoiceId"] 	  =  $('#invoiceId').val();
	jsonObject["clothTypesId"] 		  =  $('#clothTypesId').val();
	jsonObject["vid"] 			  	  =  $('#vid').val();
	jsonObject["vehicleQuantity"] 	  =  $('#vehicleQuantity').val();
	jsonObject["laundryClothDetailsId"] 	  =  $('#laundryClothDetailsId').val();
	
	
	showLayer();
	$.ajax({
             url: "saveVehicleLaundryDetails",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 console.log('data : ', data);
            	 showMessage('success', 'Data Saved SuccessFully !');
            	 window.location.replace("showLaundryInvoice?Id="+Number($('#invoiceId').val()));
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});
	
}