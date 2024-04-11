$(document).ready(function() {
	getPageWiseRefreshmentsDetails(1);
});

function getPageWiseRefreshmentsDetails(pageNumber) {
	
	showLayer();
	var jsonObject					= new Object();
	jsonObject["pageNumber"]			= pageNumber;

	$.ajax({
		url: "getPageWiseRefreshmentsDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setUreaInvoiceList(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}


function setUreaInvoiceList(data) {
	
	$("#VendorPaymentTable").empty();
	$("#searchInv").val('');
	$("#searchBattery").val('');
	$('#searchData').show();
	$('#countDiv').show();
	$('#listTab').show();
	$('#addClothDiv').addClass('hide');
	$("#totalClothInvoice").html(data.invoiceCount);
	$('#countId').html('Total Refreshment Entry');
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
	var th13	= $('<th class="fit ar">');
	var th14	= $('<th>');

	tr1.append(th1.append("No"));
	tr1.append(th2.append("Vehicle"));
	tr1.append(th3.append("TripSheet"));
	tr1.append(th4.append("Part"));
	tr1.append(th5.append("Date"));
	tr1.append(th6.append("Quantity"));
	tr1.append(th7.append("Unit Price"));
	tr1.append(th8.append("Amount"));
	tr1.append(th9.append("Return Qty"));
	tr1.append(th10.append("Return Date"));
	tr1.append(th11.append("Return Amt"));
	tr1.append(th12.append("Consumption"));
	tr1.append(th13.append("Balance"));
	tr1.append(th14.append("Action"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.ureaEntriesList != undefined && data.ureaEntriesList.length > 0) {
		
		var ureaEntriesList = data.ureaEntriesList;
	
		for(var i = 0; i < ureaEntriesList.length; i++) {
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
			var td13	= $('<td class="fit ar">');
			var td14	= $('<td>');
			var consumption = ureaEntriesList[i].quantity - ureaEntriesList[i].returnQuantity;
			tr1.append(td1.append('<a href="#">'+ureaEntriesList[i].refreshmentEntryNumber+'</a>'));
			var curl = "showVehicle?vid="+ureaEntriesList[i].vid
			tr1.append(td2.append('<a target="_blank" href="' + curl + '">'+ureaEntriesList[i].vehicle_registration+'</a><br>'));
			tr1.append(td3.append(ureaEntriesList[i].tripSheetNumber));
			tr1.append(td4.append(ureaEntriesList[i].partname));
			tr1.append(td5.append(ureaEntriesList[i].asignmentDateStr));
			tr1.append(td6.append(ureaEntriesList[i].quantity.toFixed(2)));
			tr1.append(td7.append(ureaEntriesList[i].unitprice.toFixed(2)));
			tr1.append(td8.append((ureaEntriesList[i].totalAmount).toFixed(2)));
			tr1.append(td9.append(ureaEntriesList[i].returnQuantity.toFixed(2)));	
			if(ureaEntriesList[i].returnQuantity > 0){
				tr1.append(td10.append(ureaEntriesList[i].returnDateStr));
			}else{
				tr1.append(td10.append('--'));
			}
				
			tr1.append(td11.append((ureaEntriesList[i].returnAmount).toFixed(2)));
			tr1.append(td12.append(consumption.toFixed(2)));
			tr1.append(td13.append((ureaEntriesList[i].balanceAmount).toFixed(2)));
			
			tr1.append(td14.append(
			'<div class="btn-group">'
			+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
			+'<ul class="dropdown-menu pull-right">'
			+'<li><a href="#" class="confirmation" onclick="deleteRefreshmentEntry('+ureaEntriesList[i].refreshmentEntryId+')"><span class="fa fa-trash"></span> Delete</a></li>'
			+'</ul>'
			+'</div>'
			));

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
		$("#navigationBar").append('<li><a href="#" onclick="getPageWiseRefreshmentsDetails(1)">&lt;&lt;</a></li>');		
	}

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="getPageWiseRefreshmentsDetails('+(data.currentIndex - 1)+')">&lt;</a></li>');
	}
	
	for (i = data.beginIndex; i <= data.endIndex; i++) {
	    if(i == data.currentIndex) {
	    	$("#navigationBar").append('<li class="active"><a href="#" onclick="getPageWiseRefreshmentsDetails('+i+')">'+i+'</a></li>');	    	
	    } else {
	    	$("#navigationBar").append('<li><a href="#" onclick="getPageWiseRefreshmentsDetails('+i+')">'+i+'</a></li>');	    	
	    }
	} 
	
	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getPageWiseRefreshmentsDetails('+(data.currentIndex + 1)+')">&gt;</a></li>');			
		}
	}

	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getPageWiseRefreshmentsDetails('+(data.deploymentLog.totalPages)+')">&gt;&gt;</a></li>');			
		}
	}
}

function locationUreaDetails(location, pageNumber){
	showLayer();
	var jsonObject					= new Object();

	jsonObject["pageNumber"]			= pageNumber;
	jsonObject["locationId"]			= location;

	$.ajax({
		url: "locationStockDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			setUreaDetailsList(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setUreaDetailsList(data) {
	
	$('#statues').val(data.location);
	document.getElementById("All").className = "tab-link";
	document.getElementById('AllStock').className = "tab-link";
	
	if(data.PartLocations != undefined){
		for(var i = 0 ; i < data.PartLocations.length; i++){
			if(data.PartLocations[i].partlocation_name == data.location){
				document.getElementById(data.location).className = "active";
			}else{
				document.getElementById(data.PartLocations[i].partlocation_name).className = "tab-link";	
			}
		}
	}
	
	$("#VendorPaymentTable").empty();
	
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

	tr1.append(th1.append("Sr no."));
	tr1.append(th2.append("Manufacturer Name"));
	tr1.append(th3.append("Stock Liters"));
	
	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.detailsDtos != undefined && data.detailsDtos.length > 0) {
		var detailsDtos = data.detailsDtos;
		
		var locationId	= data.locationId;
		var totalStock	= 0;
		
		
		for(var i = 0; i < detailsDtos.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append('<a href="#"> '+detailsDtos[i].manufacturerName+'</a>'));
			tr1.append(td3.append(detailsDtos[i].quantity - detailsDtos[i].usedQuantity));
			
			totalStock += detailsDtos[i].quantity - detailsDtos[i].usedQuantity;

			tbody.append(tr1);
		}
		if(data.locationId == undefined){
			$('#countId').html('ALL Urea');
		}else{
			$('#countId').html(data.location+' Urea');
		}
		$("#totalClothInvoice").html(totalStock);
		
	}else{
		showMessage('info','No record found !')
	}
	
	$("#VendorPaymentTable").append(thead);
	$("#VendorPaymentTable").append(tbody);
	
	$("#navigationBar").empty();

	

}


function getAllUreaStockDetails(pageNumber) {
	showLayer();
	var jsonObject					= new Object();
	jsonObject["pageNumber"]			= pageNumber;

	$.ajax({
		url: "locationStockDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			setUreaDetailsList(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function deleteRefreshmentEntry(refreshmentEntryId){
	
	  if(confirm("Are You Sure to Delete ?")){
		  	
			var jsonObject			= new Object();
			jsonObject["refreshmentEntryId"] =  refreshmentEntryId;
			
			showLayer();
			$.ajax({
		             url: "deleteRefreshmentEntry",
		             type: "POST",
		             dataType: 'json',
		             data: jsonObject,
		             success: function (data) {
		            	 window.location.replace("RefreshmentEntriesList.in?saved=true");
		            	 showMessage('success', 'Data Deleted Successfully!')
		            	 hideLayer();
		             },
		             error: function (e) {
		            	 showMessage('errors', 'Some error occured!')
		            	 hideLayer();
		             }
			});
	  }
}