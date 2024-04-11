
$(document).ready(
	
		function($) {
			if($('#showTransferedStatus').val() == true || $('#showTransferedStatus').val() == 'true'){
				$('#transferedStatusTr').hide();
				$('#transferedQtyTr').hide();
			}
			showLayer();
			
			var jsonObject			= new Object();
			jsonObject["fuelInvoiceId"] =  Number($('#fuelInvoiceId').val());
			jsonObject["companyId"] 	=  $('#companyId').val();
			jsonObject["userId"]		=  $('#userId').val();
			showLayer();
			$.ajax({
	             url: "getFuelInvoiceDetails",
	             type: "POST",
	             dataType: 'json',
	             data: jsonObject,
	             success: function (data) {
	            	 console.log('data : ', data);
	                 renderData(data);
	            	 hideLayer();
	             },
	             error: function (e) {
	            	 showMessage('errors', 'Some error occured!')
	            	// window.location.replace("FuelInventory.in");
	            	 hideLayer();
	             }
		});
			
});		
function renderData(data){
	if(data.fuelInvoice != undefined && data.fuelInvoice != null){
		setVendor(data);
		setInvoiceData(data.fuelInvoice);
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
	if(data.fuelInvoice != undefined && data.fuelInvoice != null){
		$('#dataTable').show();
		var srNo = 1, total = 0, subTotal = 0;
		
		var fuelInvoice = data.fuelInvoice;
		var fuelPriceWithGst 	= (fuelInvoice.rate+((fuelInvoice.rate*fuelInvoice.gst)/100));
		var fuelFinalPrice 		= (fuelPriceWithGst-((fuelPriceWithGst*fuelInvoice.discount)/100));

		$("#fuel_price").val(fuelFinalPrice);

		var disc; 
		var taxes;

		if(fuelInvoice.discountGstTypeId == 1){
			disc  = fuelInvoice.discount+'%';
			taxes = fuelInvoice.gst+'%';
		} else {
			disc  = fuelInvoice.discount;
			taxes = fuelInvoice.gst;
		}

		total 	 += fuelInvoice.totalAmount;
		subTotal += fuelInvoice.totalAmount;
		var tr =' <tr id="rowColor'+srNo+'">'
		+'<td class="fit" value="'+srNo+'">'+srNo+'</td>'
		+'<td>'+fuelInvoice.quantity+'</td>';
		if($("#showBalanceStockInFuelInvoice").val() == true || $("#showBalanceStockInFuelInvoice").val() == 'true' ){
			tr +=	'<td>'+fuelInvoice.balanceStock+'</td>';
		}
		tr +=	'<td>'+fuelInvoice.rate+'</td>'
		+'<td>'+fuelInvoice.discount+'</td>'
		+'<td>'+fuelInvoice.gst+'</td>'
		+'<td>'+fuelInvoice.totalAmount+'</td>'
		+'</tr>';
		$('#batteryAmountBody').append(tr);


	}
}

function setVendor(data){
	if(data.fuelInvoice.vendorName != null && data.fuelInvoice.vendorId != null && data.fuelInvoice.vendorId > 0){
		var mydiv = document.getElementById("vendorinfo");
		var aTag = document.createElement('a');
		aTag.setAttribute('href','ShowVendor.in?vendorId='+data.fuelInvoice.vendorId+'');
		aTag.setAttribute('target', '_blank')
		aTag.innerHTML = ""+data.fuelInvoice.vendorName+"<br/>"+data.fuelInvoice.vendorLocation+" ";
		mydiv.appendChild(aTag);
	}
}
function setInvoiceData(fuelInvoice){
	$('#modulePaymentTypeId').val(fuelInvoice.paymentTypeId);
	$('#moduleIdentifier').val(4);
	
	$('#fuelInvoiceNumber').html(fuelInvoice.fuelInvoiceNumber);
	$('#invoiceDate').html(fuelInvoice.invoiceDateStr);
	$('#paymentTypeSpan').html(fuelInvoice.paymentType);
	$('#invoiceNumber').html(fuelInvoice.invoiceNumber);
	$('#invoiceAmount').html((fuelInvoice.invoiceAmount).toFixed(2));
	$('#paymentNumber').html(fuelInvoice.paymentNumber);
	$('#tallyCompany').html(fuelInvoice.tallyCompanyName);
	$('#poNumber').html(fuelInvoice.poNumber);
	$('#description').html(fuelInvoice.description);
	$('#subTotal').html((fuelInvoice.invoiceAmount).toFixed(2));
	$('#total').html((fuelInvoice.invoiceAmount).toFixed(2));
	$('#createdOn').html(fuelInvoice.createdOnStr);
	$('#lastUpdated').html(fuelInvoice.lastModifiedOnStr);
	$('#createdBy').html(fuelInvoice.createdBy);
	$('#lastupdatedBy').html(fuelInvoice.lastUpdatedBy);//
	$('#discountType').html(fuelInvoice.discountGstType);
	$('#petrolPumpId').val(fuelInvoice.petrolPumpId);
	$('#quantity').val(fuelInvoice.quantity);
	$('#invoiceQuantity').val(fuelInvoice.quantity);
	$('#balanceStock').val(fuelInvoice.balanceStock);
	if($('#showTransferedStatus').val() == true || $('#showTransferedStatus').val() == 'true' && fuelInvoice.createdAsTransfered){
		$('#transferedStatusTr').show();
		$('#transferedQtyTr').show();
		$('#transferedStatus').html(' <a href="/showFuelInvoice?Id='+fuelInvoice.transferedFromInvoiceId+'" target="_blank">'+'FI-'+fuelInvoice.transferedFromInvoiceNumber+'</a>');
		$('#transferedQty').html(' '+fuelInvoice.transferedQuantity );
	}
}


function deleteFuelInvoice(){
	showLayer();
	
	var jsonObject			= new Object();
	jsonObject["fuelInvoiceId"] =  Number($('#fuelInvoiceId').val());
	jsonObject["companyId"] 	=  $('#companyId').val();
	jsonObject["userId"]		=  $('#userId').val();
	showLayer();
	$.ajax({
         url: "deleteFuelInvoice",
         type: "POST",
         dataType: 'json',
         data: jsonObject,
         success: function (data) {
        	 if(data.fuelEntryExist != undefined && (data.fuelEntryExist == true || data.fuelEntryExist == 'true')){
        		 showMessage('info', 'Fuel Enry Exist !');
        		 hideLayer();
        	 }else{
        		 showMessage('success', 'Data Deleted SuccessFully !');
        		 window.location.replace("FuelInventory.in");
        	 }
         },
         error: function (e) {
        	 showMessage('errors', 'Some error occured!')
        	// window.location.replace("FuelInventory.in");
        	 hideLayer();
         }
});	
}

function getFuelInvoiceHistory(){
	showLayer();
	var jsonObject					= new Object();
	jsonObject["fuelInvoiceId"] =  Number($('#fuelInvoiceId').val());
	jsonObject["companyId"] 	=  $('#companyId').val();
	jsonObject["pageNumber"] 	=  1;
	$.ajax({
		url: "getFuelInvoiceHistoryByInvoiceId",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			setFuelInvoiceHistory(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}	
function setFuelInvoiceHistory(data){
	$("#fuelInvoiceHistoryTable").empty();
	$('#fuelInvoiceHistoryModal').modal('show');
	var fuelInvoiceHistoryList = data.fuelInvoiceHistoryList;
	
	if(fuelInvoiceHistoryList != undefined || fuelInvoiceHistoryList != null){
		for(var index = 0 ; index < fuelInvoiceHistoryList.length; index++){
			var columnArray = new Array();
			columnArray.push("<td class='fit'>"+(index+1)+"</td>");
			columnArray.push("<td class='fit ar'>"+ fuelInvoiceHistoryList[index].balanceStock +"</td>");
			columnArray.push("<td class='fit ar'>"+ fuelInvoiceHistoryList[index].stockType  +"</td>");
			columnArray.push("<td class='fit ar'>"+ fuelInvoiceHistoryList[index].remark  +"</td>");
			
			$('#fuelInvoiceHistoryTable').append("<tr id='fuelInvoiceHistoryId_"+(index+1)+"' >" + columnArray.join(' ') + "</tr>");
			
		}
		columnArray = [];
		$("#fuelInvoiceHistoryId_1").css("background-color", "bisque");
		$("#fuelInvoiceHistoryTable").css("background-color", "skyblue");
		}else{
			showMessage('info','No Record Found!')
		}
	}

function addShortExcessQuantity(){
	$("#stockInvoiceModal").modal('show');
}

function updateFuelInvoiceStock(){
	showLayer();
	var jsonObject							= new Object();
	var stockTypeId							= $("input[name='stockStatusName']:checked").val();
	var updatedStock						= Number($("#shrotExcessQuantity").val());
	var balanceStock						= Number($("#balanceStock").val());
	
	jsonObject["companyId"] 				= $('#companyId').val();
	jsonObject["userId"] 					= $('#userId').val();
	jsonObject["fuelInvoiceId"]				= $("#fuelInvoiceId").val();
	jsonObject["stockTypeId"]				= stockTypeId;
	jsonObject["updatedStock"]				= updatedStock;
	jsonObject["remark"]					= $("#remark").val();
	jsonObject["petrolPumpId"]				= $("#petrolPumpId").val();
	
	if($("#shrotExcessQuantity").val() <= 0){
		showMessage('info','Please Enter Fuel Liter')
		return false;
	}
	
	if(stockTypeId == 1 && (updatedStock > balanceStock )){
		showMessage('info','Short Quantity Can Not Be Greater Than balanceStock '+balanceStock+' ')
		hideLayer();	
		return false;
	}
	
	$.ajax({
		url: "updateShortExcessQuantity",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			location.reload();
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
}

