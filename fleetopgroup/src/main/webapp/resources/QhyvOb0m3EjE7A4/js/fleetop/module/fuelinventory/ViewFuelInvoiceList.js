$(document).ready(function() {
	getPageWiseFuelInvoiceDetails(1);
});

function getPageWiseFuelInvoiceDetails(pageNumber) {
	
	showLayer();
	var jsonObject					= new Object();
	jsonObject["pageNumber"]			= pageNumber;

	$.ajax({
		url: "getPageWiseFuelInvoiceDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setFuelInvoiceList(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}



function getFuelStockDetails(petrolPumpId, petrolPump){

	
	showLayer();
	var jsonObject					= new Object();
	jsonObject["petrolPumpId"]		= petrolPumpId;
	jsonObject["companyId"]			= $('#companyId').val();

	$.ajax({
		url: "getFuelStockDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.showBalanceStockInvoiceList != undefined && data.showBalanceStockInvoiceList == true || data.showBalanceStockInvoiceList ==='true')
			{
				setBalanceStockInvoiceList(data,petrolPump);
			}else{
				setStockDetailsList(data, petrolPump);
			}
			hideLayer();
		},
		error: function (e) {x
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});

}

function setFuelInvoiceList(data) {
	
	$("#VendorPaymentTable").empty();
	$("#searchInv").val('');
	$("#searchBattery").val('');
	$('#searchData').show();
	$('#countDiv').show();
	$('#listTab').show();
	$('#addClothDiv').addClass('hide');
	$("#totalClothInvoice").html(data.invoiceCount);
	$('#countId').html('Total Fuel Invoice');
	var showSubLocation = $("#showSubLocation").val();
	document.getElementById('All').className = "active";
	//document.getElementById('AllStock').className = "tab-link";
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th class="fit ar">');
	var th6		= $('<th class="fit ar">');
	var th7		= $('<th class="fit ar">');
	var th8		= $('<th class="fit ar">');
	var th9		= $('<th class="fit ar">');
	var th10	= $('<th class="fit ar">');
	var th11	= $('<th class="fit ar">');
	var th12	= $('<th class="fit ar">');
	var th13	= $('<th>');
	if(showSubLocation == true || showSubLocation == 'true'){
		var th14	= $('<th class="fit ar">');
	}
	tr1.append(th1.append("No"));
	tr1.append(th2.append("Petrol Pump"));
	tr1.append(th3.append("Vendor"));
	tr1.append(th4.append("Quantity"));
	if($("#showBalanceStockInFuelInvoice").val()  == true || $("#showBalanceStockInFuelInvoice").val()  == "true"){
		tr1.append(th10.append("Balance Quantity"));
	}
	tr1.append(th5.append("Invoice Date"));
	tr1.append(th6.append("Invoice Number"));
	tr1.append(th7.append("Total Amount"));
	if($("#addFuelInventoryDocument").val() == true || $("#addFuelInventoryDocument").val() == 'true'){
		tr1.append(th8.append("Doc"));
	}
	if($('#showTransferedStatus').val() == true || $('#showTransferedStatus').val() ==='true'){
	tr1.append(th11.append("Transfered Status"));
	}
	tr1.append(th9.append("Action"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.FuelInvoice != undefined && data.FuelInvoice.length > 0) {
		
		var fuelInvoice = data.FuelInvoice;
	
		for(var i = 0; i < fuelInvoice.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td class="fit ar">');
			var td5		= $('<td class="fit ar">');
			var td6		= $('<td class="fit ar">');
			var td7		= $('<td class="fit ar">');
			var td8		= $('<td class="fit ar">');
			var td9		= $('<td class="fit ar">');
			var td10	= $('<td class="fit ar">');
			var td11	= $('<td class="fit ar">');
			var td12	= $('<td class="fit ar">');
			var td13	= $('<td>');
			var td14	= $('<td>');
			
			
			tr1.append(td1.append('<a href="showFuelInvoice?Id='+fuelInvoice[i].fuelInvoiceId+'" target="_blank">FI-'+fuelInvoice[i].fuelInvoiceNumber+'</a>'));
			var curl = "ShowVendor.in?vendorId="+fuelInvoice[i].petrolPumpId;
			var curlVendor = "ShowVendor.in?vendorId="+fuelInvoice[i].vendorId;
			tr1.append(td2.append('<a target="_blank" href="' + curl + '">'+fuelInvoice[i].petrolPumpName+'</a><br>'));
			tr1.append(td3.append('<a target="_blank" href="' + curlVendor + '">'+fuelInvoice[i].vendorName+'</a><br>'));
			tr1.append(td4.append(fuelInvoice[i].quantity.toFixed(2)));
			if($("#showBalanceStockInFuelInvoice").val()  == true || $("#showBalanceStockInFuelInvoice").val()  == "true"){
				tr1.append(td10.append(fuelInvoice[i].balanceStock));
			}
			tr1.append(td5.append(fuelInvoice[i].invoiceDateStr));
			tr1.append(td6.append(fuelInvoice[i].invoiceNumber));
			tr1.append(td7.append((fuelInvoice[i].invoiceAmount).toFixed(2)));	
			
			if($("#addFuelInventoryDocument").val() == true || $("#addFuelInventoryDocument").val() == 'true'){
				if(fuelInvoice[i].documentId != undefined && fuelInvoice[i].documentId > 0){
					tr1.append(td8.append('<a href="download/FuelInvoiceDocument/'+fuelInvoice[i].documentId+'" <span class="fa fa-download"> Doc</span> </a>'));
				}else{
					tr1.append(td8.append());
				}
			}
			
			var curl 	= "editFuelInvoice.in?Id="+fuelInvoice[i].fuelInvoiceId;
			var action 	= '<div class="btn-group">'
				+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
				+'<ul class="dropdown-menu pull-right">';
				if($("#editInvoicePermission").val() == true || $("#editInvoicePermission").val() == 'true'){
					action += '<li><a href="'+curl+'"><i class="fa fa-edit"></i> Edit</a></li>';
				}
				action 	+= '<li><a href="#" class="confirmation" onclick="deleteFuelInvoice('+fuelInvoice[i].fuelInvoiceId+')"><span class="fa fa-trash"></span> Delete</a></li>';
				action	+= '</ul>'
				+'</div>';
				if($('#showTransferedStatus').val() == true || $('#showTransferedStatus').val() ==='true'){
					
				if(fuelInvoice[i].createdAsTransfered == true || fuelInvoice[i].createdAsTransfered === 'true'){
					tr1.append(td11.append('Transfered'));	
				}else{
					tr1.append(td11.append('-'));	
				}
				}
				tr1.append(td9.append(action));

			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#VendorPaymentTable").append(thead);
	$("#VendorPaymentTable").append(tbody);
	
	$("#navigationBar").empty();

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;&lt;</a></li>');		
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="getPageWiseFuelInvoiceDetails(1)">&lt;&lt;</a></li>');		
	}

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="getPageWiseFuelInvoiceDetails('+(data.currentIndex - 1)+')">&lt;</a></li>');
	}
	
	for (i = data.beginIndex; i <= data.endIndex; i++) {
	    if(i == data.currentIndex) {
	    	$("#navigationBar").append('<li class="active"><a href="#" onclick="getPageWiseFuelInvoiceDetails('+i+')">'+i+'</a></li>');	    	
	    } else {
	    	$("#navigationBar").append('<li><a href="#" onclick="getPageWiseFuelInvoiceDetails('+i+')">'+i+'</a></li>');	    	
	    }
	} 
	
	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getPageWiseFuelInvoiceDetails('+(data.currentIndex + 1)+')">&gt;</a></li>');			
		}
	}

	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getPageWiseFuelInvoiceDetails('+(data.deploymentLog.totalPages)+')">&gt;&gt;</a></li>');			
		}
	}
}


function setStockDetailsList(data, petrolPump) {
	$('#dataFuelTable').empty();
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th class="fit ar">');

	tr1.append(th1.append("Sr no."));
	tr1.append(th2.append("Petrol Pump"));
	tr1.append(th3.append("Avg Price"));
	tr1.append(th4.append("Stock Liters"));
	tr1.append(th5.append("Total Cost"));
	
	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.stockDetails != undefined) {
		var stockDetails = data.stockDetails;
		
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td class="fit ar">');
			var td5		= $('<td class="fit ar">');
			
			tr1.append(td1.append(1));
			tr1.append(td2.append(petrolPump));
			tr1.append(td3.append((stockDetails.avgFuelRate).toFixed(2)));
			tr1.append(td4.append((stockDetails.stockQuantity).toFixed(2)));
			tr1.append(td5.append((stockDetails.totalFuelCost).toFixed(2)));
			

			tbody.append(tr1);
			
			$('#dataFuelTable').show();
			
			$("#dataFuelTable").append(thead);
			$("#dataFuelTable").append(tbody);
			$('#fuelStockModel').modal('show');

			
		
	}else{
		showMessage('info','No record found !')
	}

}


function deleteFuelInvoice(invoiceId){
	showLayer();
	
	var jsonObject			= new Object();
	jsonObject["fuelInvoiceId"] =  invoiceId;
	jsonObject["companyId"] 	=  $('#companyId').val();
	jsonObject["userId"]		=  $('#userId').val();
	showLayer();
	$.ajax({
         url: "deleteFuelInvoice",
         type: "POST",
         dataType: 'json',
         data: jsonObject,
         success: function (data) {
        	 if(data.fuelEntryExist != undefined  && data.fuelEntryExist == true){
        		 showMessage('info', 'Fuel Entry Exist !');
        	 }else{
        		 showMessage('success', 'Data Deleted SuccessFully !');
        	 }
        	 window.location.replace("FuelInventory.in");
         },
         error: function (e) {
        	 showMessage('errors', 'Some error occured!')
        	// window.location.replace("FuelInventory.in");
        	 hideLayer();
         }
});	
}



function setBalanceStockInvoiceList(data,petrolPump) {
	
	$('#petrolPumpNameInModal').html(petrolPump);
	var totalCost = 0;
	$('#dataFuelTable').empty();
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th class="fit ar">');
	tr1.append(th1.append("No"));
	tr1.append(th3.append("Invoice Date"));
	tr1.append(th4.append("Invoice Number"));
	tr1.append(th2.append("Balance Quantity"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.FuelInvoice != undefined && data.FuelInvoice.length > 0) {
		
		var fuelInvoice = data.FuelInvoice;
	
		for(var i = 0; i < fuelInvoice.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td class="fit ar">');
			var td5		= $('<td class="fit ar">');
			tr1.append(td1.append('<a href="showFuelInvoice?Id='+fuelInvoice[i].fuelInvoiceId+'" target="_blank">FI-'+fuelInvoice[i].fuelInvoiceNumber+'</a>'));
			tr1.append(td3.append(fuelInvoice[i].invoiceDateStr));
			tr1.append(td4.append(fuelInvoice[i].invoiceNumber));
			tr1.append(td2.append(fuelInvoice[i].balanceStock));
			tbody.append(tr1);
			 totalCost += fuelInvoice[i].balanceStock;
		}
		var columnArray = new Array();
		columnArray.push('<td class="fit ar" colspan="3"><b> Total</b> </td>');
		columnArray.push("<td class='fit ar'><b> "+totalCost.toFixed(2)+" </b></td>");
		$("#dataFuelTable").append(thead);
		$("#dataFuelTable").append(tbody);
		$("#dataFuelTable").append('<tr>'+columnArray.join(' ')+'</tr>')
		
	}else{
		showMessage('info','No record found !')
	}
	$("#dataFuelTable").show();
	
	$('#fuelStockModel').modal('show');
}


