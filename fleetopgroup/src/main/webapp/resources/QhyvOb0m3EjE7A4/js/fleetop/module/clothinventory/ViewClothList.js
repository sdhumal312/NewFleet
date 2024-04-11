$(document).ready(function() {
	getPageWiseClothInvoiceDetails(1);
});

function getPageWiseClothInvoiceDetails(pageNumber) {
	
	
	showLayer();
	var jsonObject					= new Object();
	jsonObject["pageNumber"]		= pageNumber;

	$.ajax({
		url: "getPageWiseClothInvoiceDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			console.log('data : ', data);
			setBatteryInvoiceList(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}


function setBatteryInvoiceList(data) {
	$("#VendorPaymentTable").empty();
	$("#searchInv").val('');
	$("#searchBattery").val('');
	$('#searchData').show();
	$('#countDiv').show();
	$('#listTab').show();
	$('#addClothDiv').addClass('hide');
	$("#totalClothInvoice").html(data.invoiceCount);
	$('#countId').html('Upholstery Count');
	document.getElementById('All').className = "active";
	document.getElementById('laundry').className = "tab-link";
	
	var showSubLocation = $("#showSubLocation").val();
	
	if(data.PartLocations != undefined){
		for(var i = 0 ; i < data.PartLocations.length; i++){document.getElementById(data.PartLocations[i].partlocation_name).className = "tab-link";}
	}
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	
	var th4		= $('<th class="fit ar">');
	
	
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
	tr1.append(th2.append("Vendor Name"));
	tr1.append(th4.append("Location"));
	if(showSubLocation == true || showSubLocation == 'true'){
		tr1.append(th14.append("Sub Location"));
	}
	tr1.append(th7.append("Invoice"));
	tr1.append(th8.append("Invoice Date"));
	tr1.append(th9.append("Amount"));
	tr1.append(th10.append("Quantity"));
	tr1.append(th11.append("Created By"));
	if($("#downloadClothInventoryDocument").val() == true || $("#downloadClothInventoryDocument").val() == 'true'){
		tr1.append(th12.append("Doc"));
	}
	tr1.append(th13.append("Action"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.ClothInvoice != undefined && data.ClothInvoice.length > 0) {
		
		var clothInvoice = data.ClothInvoice;
	
		for(var i = 0; i < clothInvoice.length; i++) {
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
			if(showSubLocation == true || showSubLocation == 'true'){
				var td14	= $('<td class="fit ar">');
			}
			
			tr1.append(td1.append('<a href="showClothInvoice?Id='+clothInvoice[i].clothInvoiceId+'" target="_blank">CI-'+clothInvoice[i].clothInvoiceNumber+'</a>'));
			var curl = "ShowVendor.in?vendorId="+clothInvoice[i].vendorId+"&page="+data.SelectPage
			tr1.append(td2.append('<a target="_blank" href="' + curl + '">'+clothInvoice[i].vendorName+'</a><br>'));
			tr1.append(td4.append(clothInvoice[i].clothLocation));
			if(showSubLocation == true || showSubLocation == 'true'){
				tr1.append(td14.append(clothInvoice[i].subLocation));
			}
			tr1.append(td7.append(clothInvoice[i].invoiceNumber));
			tr1.append(td8.append(clothInvoice[i].invoiceDateStr));
			if(clothInvoice[i].invoiceAmount != undefined && clothInvoice[i].invoiceAmount != null){
				if($('#roundupConfig').val()==true||$('#roundupConfig').val()=='true'){
					tr1.append(td9.append(Math.round((clothInvoice[i].invoiceAmount))));
				}else{
					tr1.append(td9.append(clothInvoice[i].invoiceAmount));
				}
			}
			tr1.append(td10.append(clothInvoice[i].quantity));	
			tr1.append(td11.append(clothInvoice[i].createdBy));	
			
			if($("#downloadClothInventoryDocument").val() == true || $("#downloadClothInventoryDocument").val() == 'true'){
				if(clothInvoice[i].cloth_document == true){
					tr1.append(td12.append('<a href="download/ClothInvoiceDocument/'+clothInvoice[i].cloth_document_id+'" <span class="fa fa-download"> Doc</span> </a>'));
				}else{
					tr1.append(td12.append());
				}
			}
			
			
			var curl = "editClothInvoice.in?Id="+clothInvoice[i].clothInvoiceId
			
			if((clothInvoice[i].paymentTypeId !=2 || clothInvoice[i].vendorPaymentStatus == 2) &&  clothInvoice[i].vendorPaymentStatus != 0){
				tr1.append(td13.append(
				'<div class="btn-group">'
				+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
				+'<ul class="dropdown-menu pull-right">'
				+'<li><a href="'+curl+'"><i class="fa fa-edit"></i> Edit</a></li>'
				+'<li><a href="#" class="confirmation" onclick="deleteclothInvoice('+clothInvoice[i].clothInvoiceId+')"><span class="fa fa-trash"></span> Delete</a></li>'
				+'</ul>'
				+'</div>'
				));
			}else{
				tr1.append(td13.append(
						'<div class="btn-group">'
						+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
						+'<ul class="dropdown-menu pull-right">'
						+'<li><i class="fa fa-money"></i> '+clothInvoice[i].vendorPaymentStatusStr+'</a></li>'
						+'</ul>'
						+'</div>'
						));
			}

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
		$("#navigationBar").append('<li><a href="#" onclick="getPageWiseClothInvoiceDetails(1)">&lt;&lt;</a></li>');		
	}

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="getPageWiseClothInvoiceDetails('+(data.currentIndex - 1)+')">&lt;</a></li>');
	}
	
	for (i = data.beginIndex; i <= data.endIndex; i++) {
	    if(i == data.currentIndex) {
	    	$("#navigationBar").append('<li class="active"><a href="#" onclick="getPageWiseClothInvoiceDetails('+i+')">'+i+'</a></li>');	    	
	    } else {
	    	$("#navigationBar").append('<li><a href="#" onclick="getPageWiseClothInvoiceDetails('+i+')">'+i+'</a></li>');	    	
	    }
	} 
	
	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getPageWiseClothInvoiceDetails('+(data.currentIndex + 1)+')">&gt;</a></li>');			
		}
	}

	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getPageWiseClothInvoiceDetails('+(data.deploymentLog.totalPages)+')">&gt;&gt;</a></li>');			
		}
	}
}

function locationClothDetails(location, pageNumber){
	showLayer();
	var jsonObject					= new Object();

	jsonObject["pageNumber"]			= pageNumber;
	jsonObject["location"]				= location;

	$.ajax({
		url: "locationClothDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			setClothDetailsList(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setClothDetailsList(data) {
	
	data = JSON.parse(JSON.stringify(data).replace(/\:null/gi, "\:\"0\"")); 
	
	$('#countId').html(data.location+' Cloth');
	$("#totalClothInvoice").html(data.clothQuentity);
	
	$('#statues').val(data.location);
	document.getElementById("All").className = "tab-link";
	document.getElementById('laundry').className = "tab-link";
	
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
	tr1.append(th2.append("Upholstery Name"));
	tr1.append(th3.append("Old / Used"));
	tr1.append(th4.append("New / Fresh"));
	tr1.append(th5.append("In Service"));
	tr1.append(th6.append("In Washing"));
	tr1.append(th7.append("In Damage"));
	tr1.append(th8.append("In Lost"));
	tr1.append(th9.append("In Transfer"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.clothList != undefined && data.clothList.length > 0) {
		var clothList = data.clothList;
		
		for(var i = 0; i < clothList.length; i++) {
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
			
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append('<a href="#"> '+clothList[i].clothTypeName+'</a>'));
			tr1.append(td3.append((clothList[i].usedStockQuantity).toFixed(2)));
			tr1.append(td4.append((clothList[i].newStockQuantity).toFixed(2)));
			tr1.append(td5.append('<a href="#" class="confirmation" onclick="inServiceVehicle('+clothList[i].clothTypesId+','+clothList[i].wareHouseLocationId+',1)">'+clothList[i].inServiceQuantity));
			tr1.append(td6.append('<a href="#" class="confirmation" onclick="inWashingDetails('+clothList[i].clothTypesId+','+clothList[i].wareHouseLocationId+',1)">'+clothList[i].inWashingQuantity));
			tr1.append(td7.append('<a href="#" class="confirmation" onclick="inDamageDetails('+clothList[i].clothTypesId+','+clothList[i].wareHouseLocationId+',1)">'+clothList[i].damagedQuantity));
			tr1.append(td8.append('<a href="#" class="confirmation" onclick="inLostDetails('+clothList[i].clothTypesId+','+clothList[i].wareHouseLocationId+',1)">'+clothList[i].losedQuantity));
			tr1.append(td9.append('<a href="#" class="confirmation" onclick="inTransferDetails('+clothList[i].clothTypesId+','+clothList[i].wareHouseLocationId+',1)">'+clothList[i].inTransferQuantity));

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
		$("#navigationBar").append('<li><a href="#" onclick="locationClothDetails('+data.locationId+',1)">&lt;&lt;</a></li>');		
	}

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="locationClothDetails('+data.locationId+','+(data.currentIndex - 1)+')">&lt;</a></li>');
	}
	
	for (i = data.beginIndex; i <= data.endIndex; i++) {
	    if(i == data.currentIndex) {
	    	$("#navigationBar").append('<li class="active"><a href="#" onclick="locationClothDetails('+data.locationId+','+i+')">'+i+'</a></li>');	    	
	    } else {
	    	$("#navigationBar").append('<li><a href="#" onclick="locationClothDetails('+data.locationId+','+i+')">'+i+'</a></li>');	    	
	    }
	} console.log('daDASDADAD : ', data.deploymentLog.totalPages);
	
	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="locationClothDetails('+data.locationId+','+(data.currentIndex + 1)+')">&gt;</a></li>');			
		}
	}

	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="locationClothDetails('+data.locationId+','+(data.deploymentLog.totalPages)+')">&gt;&gt;</a></li>');			
		}
	}
}


function getClothLaundryDetails(pageNumber){
	
	showLayer();
	var jsonObject					= new Object();
	jsonObject["pageNumber"]			= pageNumber;

	$.ajax({
		url: "getClothLaundryDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setLaundryInvoiceList(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setLaundryInvoiceList(data) {
	$("#VendorPaymentTable").empty();
	$("#searchInv").val('');
	$("#searchBattery").val('');
	$('#searchData').show();
	$('#countDiv').show();
	$('#listTab').show();
	$('#addClothDiv').addClass('hide');
	$("#totalClothInvoice").html(data.invoiceCount);
	$('#countId').html('Total Laundry Invoice');
	document.getElementById('laundry').className = "active";
	document.getElementById('All').className = "tab-link";
	if(data.PartLocations != undefined){
		for(var i = 0 ; i < data.PartLocations.length; i++){document.getElementById(data.PartLocations[i].partlocation_name).className = "tab-link";}
	}
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	
	var th4		= $('<th class="fit ar">');
	
	var th7		= $('<th class="fit ar">');
	var th8		= $('<th class="fit ar">');
	var th9		= $('<th class="fit ar">');
	var th10	= $('<th class="fit ar">');
	var th11	= $('<th class="fit ar">');
	var th12	= $('<th class="fit ar">');
	var th13	= $('<th class="fit ar">');
	var th14	= $('<th class="fit ar">');
	var th15	= $('<th>');

	tr1.append(th1.append("No"));
	tr1.append(th2.append("Vendor Name"));
	tr1.append(th4.append("Location"));
	tr1.append(th7.append("Sent Date"));
	tr1.append(th8.append("Received Qty"));
	tr1.append(th9.append("Damage Qty"));
	tr1.append(th10.append("Lost Qty"));
	tr1.append(th11.append("Remaining Qty"));
	tr1.append(th12.append("Amount"));
	tr1.append(th13.append("Quantity"));
	tr1.append(th14.append("Created By"));
	tr1.append(th15.append("Action"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.LaundryInvoice != undefined && data.LaundryInvoice.length > 0) {
		
		var laundryInvoice = data.LaundryInvoice;
	
		for(var i = 0; i < laundryInvoice.length; i++) {
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
			var td14	= $('<td class="fit ar">');
			var td15	= $('<td>');
			
			tr1.append(td1.append('<a href="showLaundryInvoice?Id='+laundryInvoice[i].laundryInvoiceId+'" target="_blank">LI-'+laundryInvoice[i].laundryInvoiceNumber+'</a>'));
			var curl = "ShowVendor.in?vendorId="+laundryInvoice[i].vendorId+"&page="+data.SelectPage
			tr1.append(td2.append('<a target="_blank" href="' + curl + '">'+laundryInvoice[i].vendorName+'</a><br>'));
			tr1.append(td4.append(laundryInvoice[i].locationName));
			tr1.append(td7.append(laundryInvoice[i].sentDateStr));
			tr1.append(td8.append(laundryInvoice[i].receivedQuantity));
			tr1.append(td9.append(laundryInvoice[i].damagedQuantity));
			tr1.append(td10.append(laundryInvoice[i].losedQuantity));
			tr1.append(td11.append(laundryInvoice[i].remainingQuantity));
			tr1.append(td12.append(laundryInvoice[i].totalCost));	
			tr1.append(td13.append(laundryInvoice[i].totalQuantity));	
			tr1.append(td14.append(laundryInvoice[i].createdBy));	
			
			var curl = "editClothLaundryInvoice.in?Id="+laundryInvoice[i].laundryInvoiceId
			tr1.append(td15.append(
			'<div class="btn-group">'
			+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
			+'<ul class="dropdown-menu pull-right">'
			+'<li><a href="'+curl+'"><i class="fa fa-edit"></i> Edit</a></li>'
			+'<li><a href="#" class="confirmation" onclick="deleteClothLaundryInvoiceById('+laundryInvoice[i].laundryInvoiceId+')"><span class="fa fa-trash"></span> Delete</a></li>'
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
		$("#navigationBar").append('<li><a href="#" onclick="getClothLaundryDetails(1)">&lt;&lt;</a></li>');		
	}

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="getClothLaundryDetails('+(data.currentIndex - 1)+')">&lt;</a></li>');
	}
	
	for (i = data.beginIndex; i <= data.endIndex; i++) {
	    if(i == data.currentIndex) {
	    	$("#navigationBar").append('<li class="active"><a href="#" onclick="getClothLaundryDetails('+i+')">'+i+'</a></li>');	    	
	    } else {
	    	$("#navigationBar").append('<li><a href="#" onclick="getClothLaundryDetails('+i+')">'+i+'</a></li>');	    	
	    }
	} 
	
	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getClothLaundryDetails('+(data.currentIndex + 1)+')">&gt;</a></li>');			
		}
	}

	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getClothLaundryDetails('+(data.deploymentLog.totalPages)+')">&gt;&gt;</a></li>');			
		}
	}
}

function inServiceVehicle(clothTypesId,wareHouseLocationId,pageNumber){
	showLayer();
	var jsonObject						= new Object();

	jsonObject["clothTypesId"]			= clothTypesId;
	jsonObject["wareHouseLocationId"]	= wareHouseLocationId;
	jsonObject["pageNumber"]			= pageNumber;
	
	$.ajax({
		url: "getInServiceVehicle",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setInServiceVehicle(data);
			$('#inService').modal('show');
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
}

function setInServiceVehicle(data) {
	
	$("#VendorPaymentTable1").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th class="fit ar">');
	var th6		= $('<th class="fit ar">');
	var th7		= $('<th>');

	tr1.append(th1.append("No"));
	tr1.append(th2.append("Location"));
	tr1.append(th3.append("Upholstery Name"));
	tr1.append(th4.append("Vehicle"));
	tr1.append(th5.append("Quantity"));
	tr1.append(th6.append("Date"));
	tr1.append(th7.append("Assigned/Removed By"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.InServiceVehicleDetailsList != undefined && data.InServiceVehicleDetailsList.length > 0) {
		
		var InServiceVehicleDetailsList = data.InServiceVehicleDetailsList;
	
		for(var i = 0; i < InServiceVehicleDetailsList.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td class="fit ar">');
			var td5		= $('<td class="fit ar">');
			var td6		= $('<td class="fit ar">');
			var td7		= $('<td>');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append(InServiceVehicleDetailsList[i].locationName));
			tr1.append(td3.append(InServiceVehicleDetailsList[i].clothTypesName));
			tr1.append(td4.append(InServiceVehicleDetailsList[i].vehicle_registration));
			tr1.append(td5.append(InServiceVehicleDetailsList[i].quantity));
			tr1.append(td6.append(InServiceVehicleDetailsList[i].lastModifiedOnstr));
			tr1.append(td7.append(InServiceVehicleDetailsList[i].lastModifiedBy));

			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#VendorPaymentTable1").append(thead);
	$("#VendorPaymentTable1").append(tbody);
	
	if(data.InServiceVehicleDetailsList != undefined && data.InServiceVehicleDetailsList.length > 0) {
	$("#navigationBar2").empty();
	
	if(data.currentIndex == 1) {
		$("#navigationBar2").append('<li class="disabled"><a href="#" onclick="">&lt;&lt;</a></li>');		
	} else {
		$("#navigationBar2").append('<li><a href="#" onclick="inServiceVehicle('+data.InServiceVehicleDetailsList[0].clothTypesId+','+data.InServiceVehicleDetailsList[0].locationId+',1)">&lt;&lt;</a></li>');		
	}

	if(data.currentIndex == 1) {
		$("#navigationBar2").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	} else {
		$("#navigationBar2").append('<li><a href="#" onclick="inServiceVehicle('+data.InServiceVehicleDetailsList[0].clothTypesId+','+data.InServiceVehicleDetailsList[0].locationId+','+(data.currentIndex - 1)+')">&lt;</a></li>');
	}
	
	for (i = data.beginIndex; i <= data.endIndex; i++) {
	    if(i == data.currentIndex) {
	    	$("#navigationBar2").append('<li class="active"><a href="#" onclick="inServiceVehicle('+data.InServiceVehicleDetailsList[0].clothTypesId+','+data.InServiceVehicleDetailsList[0].locationId+','+i+')">'+i+'</a></li>');	    	
	    } else {
	    	$("#navigationBar2").append('<li><a href="#" onclick="inServiceVehicle('+data.InServiceVehicleDetailsList[0].clothTypesId+','+data.InServiceVehicleDetailsList[0].locationId+','+i+')">'+i+'</a></li>');	    	
	    }
	} 
	
	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar2").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar2").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
		} else {
			$("#navigationBar2").append('<li><a href="#" onclick="inServiceVehicle('+data.InServiceVehicleDetailsList[0].clothTypesId+','+data.InServiceVehicleDetailsList[0].locationId+','+(data.currentIndex + 1)+')">&gt;</a></li>');			
		}
	}

	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar2").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar2").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
		} else {
			$("#navigationBar2").append('<li><a href="#" onclick="inServiceVehicle('+data.InServiceVehicleDetailsList[0].clothTypesId+','+data.InServiceVehicleDetailsList[0].locationId+','+(data.deploymentLog.totalPages)+')">&gt;&gt;</a></li>');			
		}
	 }
   }
}

function inDamageDetails(clothTypesId,wareHouseLocationId,pageNumber){
	showLayer();
	var jsonObject						= new Object();

	jsonObject["clothTypesId"]			= clothTypesId;
	jsonObject["wareHouseLocationId"]	= wareHouseLocationId;
	jsonObject["pageNumber"]			= pageNumber;
	
	$.ajax({
		url: "getInDamageDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setInDamageDetails(data);
			$('#inDamage').modal('show');
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
}

function setInDamageDetails(data) {
	$("#VendorPaymentTable2").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th class="fit ar">');
	var th6		= $('<th>');

	tr1.append(th1.append("No"));
	tr1.append(th2.append("Location"));
	tr1.append(th3.append("Upholstery Name"));
	tr1.append(th4.append("Damage Qty"));
	tr1.append(th5.append("Date"));
	tr1.append(th6.append("Assigned/Removed By"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.InDamageDetailsList != undefined && data.InDamageDetailsList.length > 0) {
		
		var InDamageDetailsList = data.InDamageDetailsList;
	
		for(var i = 0; i < InDamageDetailsList.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			console.log("hiiii");
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td class="fit ar">');
			var td5		= $('<td class="fit ar">');
			var td6		= $('<td>');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append(InDamageDetailsList[i].locationName));
			tr1.append(td3.append(InDamageDetailsList[i].clothName));
			tr1.append(td4.append(InDamageDetailsList[i].damagedQuantity));
			tr1.append(td5.append(InDamageDetailsList[i].createdDateStr));
			tr1.append(td6.append(InDamageDetailsList[i].receiveByStr));

			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#VendorPaymentTable2").append(thead);
	$("#VendorPaymentTable2").append(tbody);
	
	if(data.InDamageDetailsList != undefined && data.InDamageDetailsList.length > 0) {
	$("#navigationBar3").empty();
	
	if(data.currentIndex == 1) {
		$("#navigationBar3").append('<li class="disabled"><a href="#" onclick="">&lt;&lt;</a></li>');		
	} else {
		$("#navigationBar3").append('<li><a href="#" onclick="inDamageDetails('+data.InDamageDetailsList[0].clothTypesId+','+data.InDamageDetailsList[0].locationId+',1)">&lt;&lt;</a></li>');		
	}

	if(data.currentIndex == 1) {
		$("#navigationBar3").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	} else {
		$("#navigationBar3").append('<li><a href="#" onclick="inDamageDetails('+data.InDamageDetailsList[0].clothTypesId+','+data.InDamageDetailsList[0].locationId+','+(data.currentIndex - 1)+')">&lt;</a></li>');
	}
	
	for (i = data.beginIndex; i <= data.endIndex; i++) {
	    if(i == data.currentIndex) {
	    	$("#navigationBar3").append('<li class="active"><a href="#" onclick="inDamageDetails('+data.InDamageDetailsList[0].clothTypesId+','+data.InDamageDetailsList[0].locationId+','+i+')">'+i+'</a></li>');	    	
	    } else {
	    	$("#navigationBar3").append('<li><a href="#" onclick="inDamageDetails('+data.InDamageDetailsList[0].clothTypesId+','+data.InDamageDetailsList[0].locationId+','+i+')">'+i+'</a></li>');	    	
	    }
	} 
	
	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar3").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar3").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
		} else {
			$("#navigationBar3").append('<li><a href="#" onclick="inDamageDetails('+data.InDamageDetailsList[0].clothTypesId+','+data.InDamageDetailsList[0].locationId+','+(data.currentIndex + 1)+')">&gt;</a></li>');			
		}
	}

	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar3").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar3").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
		} else {
			$("#navigationBar3").append('<li><a href="#" onclick="inDamageDetails('+data.InDamageDetailsList[0].clothTypesId+','+data.InDamageDetailsList[0].locationId+','+(data.deploymentLog.totalPages)+')">&gt;&gt;</a></li>');			
		}
	 }
   }
}

function inLostDetails(clothTypesId,wareHouseLocationId,pageNumber){
	showLayer();
	var jsonObject						= new Object();

	jsonObject["clothTypesId"]			= clothTypesId;
	jsonObject["wareHouseLocationId"]	= wareHouseLocationId;
	jsonObject["pageNumber"]			= pageNumber;
	
	$.ajax({
		url: "getInLostDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setInLostDetails(data);
			$('#inLost').modal('show');
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
}

function setInLostDetails(data) {
	$("#VendorPaymentTable3").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th class="fit ar">');
	var th6		= $('<th>');

	tr1.append(th1.append("No"));
	tr1.append(th2.append("Location"));
	tr1.append(th3.append("Upholstery Name"));
	tr1.append(th4.append("Lost Qty"));
	tr1.append(th5.append("Date"));
	tr1.append(th6.append("Assigned/Removed By"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.InLostDetailsList != undefined && data.InLostDetailsList.length > 0) {
		
		var InLostDetailsList = data.InLostDetailsList;
	
		for(var i = 0; i < InLostDetailsList.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td class="fit ar">');
			var td5		= $('<td class="fit ar">');
			var td6		= $('<td>');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append(InLostDetailsList[i].locationName));
			tr1.append(td3.append(InLostDetailsList[i].clothName));
			tr1.append(td4.append(InLostDetailsList[i].losedQuantity));
			tr1.append(td5.append(InLostDetailsList[i].createdDateStr));
			tr1.append(td6.append(InLostDetailsList[i].receiveByStr));

			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#VendorPaymentTable3").append(thead);
	$("#VendorPaymentTable3").append(tbody);
	
	if(data.InLostDetailsList != undefined && data.InLostDetailsList.length > 0) {
	$("#navigationBar4").empty();
	
	if(data.currentIndex == 1) {
		$("#navigationBar4").append('<li class="disabled"><a href="#" onclick="">&lt;&lt;</a></li>');		
	} else {
		$("#navigationBar4").append('<li><a href="#" onclick="inLostDetails('+data.InLostDetailsList[0].clothTypesId+','+data.InLostDetailsList[0].locationId+',1)">&lt;&lt;</a></li>');		
	}

	if(data.currentIndex == 1) {
		$("#navigationBar4").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	} else {
		$("#navigationBar4").append('<li><a href="#" onclick="inLostDetails('+data.InLostDetailsList[0].clothTypesId+','+data.InLostDetailsList[0].locationId+','+(data.currentIndex - 1)+')">&lt;</a></li>');
	}
	
	for (i = data.beginIndex; i <= data.endIndex; i++) {
	    if(i == data.currentIndex) {
	    	$("#navigationBar4").append('<li class="active"><a href="#" onclick="inLostDetails('+data.InLostDetailsList[0].clothTypesId+','+data.InLostDetailsList[0].locationId+','+i+')">'+i+'</a></li>');	    	
	    } else {
	    	$("#navigationBar4").append('<li><a href="#" onclick="inLostDetails('+data.InLostDetailsList[0].clothTypesId+','+data.InLostDetailsList[0].locationId+','+i+')">'+i+'</a></li>');	    	
	    }
	} 
	
	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar4").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar4").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
		} else {
			$("#navigationBar4").append('<li><a href="#" onclick="inLostDetails('+data.InLostDetailsList[0].clothTypesId+','+data.InLostDetailsList[0].locationId+','+(data.currentIndex + 1)+')">&gt;</a></li>');			
		}
	}

	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar4").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar4").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
		} else {
			$("#navigationBar4").append('<li><a href="#" onclick="inLostDetails('+data.InLostDetailsList[0].clothTypesId+','+data.InLostDetailsList[0].locationId+','+(data.deploymentLog.totalPages)+')">&gt;&gt;</a></li>');			
		}
	 }
   }
}

function inWashingDetails(clothTypesId,wareHouseLocationId,pageNumber){
	showLayer();
	var jsonObject						= new Object();

	jsonObject["clothTypesId"]			= clothTypesId;
	jsonObject["wareHouseLocationId"]	= wareHouseLocationId;
	jsonObject["pageNumber"]			= pageNumber;
	
	$.ajax({
		url: "getInWashingDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setInWashingDetails(data);
			$('#inWashing').modal('show');
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
}

function setInWashingDetails(data) {
	$("#VendorPaymentTable4").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th class="fit ar">');
	var th6		= $('<th>');
	var th7		= $('<th>');

	tr1.append(th1.append("No"));
	tr1.append(th2.append("Location"));
	tr1.append(th3.append("Upholstery Name"));
	tr1.append(th4.append("Date"));
	tr1.append(th5.append("Assigned/Removed By"));
	tr1.append(th6.append("Currently In Washing Qty"));
	tr1.append(th7.append("Initial In Washing Qty"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.InWashingDetailsList != undefined && data.InWashingDetailsList.length > 0) {
		
		var InWashingDetailsList = data.InWashingDetailsList;
	
		for(var i = 0; i < InWashingDetailsList.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			console.log("hiiii");
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td class="fit ar">');
			var td5		= $('<td class="fit ar">');
			var td6		= $('<td>');
			var td7		= $('<td>');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append(InWashingDetailsList[i].locationName));
			tr1.append(td3.append(InWashingDetailsList[i].clothName));
			tr1.append(td4.append(InWashingDetailsList[i].creationDateStr));
			tr1.append(td5.append(InWashingDetailsList[i].lastModifiedBy));
			tr1.append(td6.append(InWashingDetailsList[i].finalReceivedQty));
			tr1.append(td7.append(InWashingDetailsList[i].totalQuantity));

			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#VendorPaymentTable4").append(thead);
	$("#VendorPaymentTable4").append(tbody);
	
	if(data.InWashingDetailsList != undefined && data.InWashingDetailsList.length > 0) {
	$("#navigationBar5").empty();
	
	if(data.currentIndex == 1) {
		$("#navigationBar5").append('<li class="disabled"><a href="#" onclick="">&lt;&lt;</a></li>');		
	} else {
		$("#navigationBar5").append('<li><a href="#" onclick="inWashingDetails('+data.InWashingDetailsList[0].clothTypesId+','+data.InWashingDetailsList[0].locationId+',1)">&lt;&lt;</a></li>');		
	}

	if(data.currentIndex == 1) {
		$("#navigationBar5").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	} else {
		$("#navigationBar5").append('<li><a href="#" onclick="inWashingDetails('+data.InWashingDetailsList[0].clothTypesId+','+data.InWashingDetailsList[0].locationId+','+(data.currentIndex - 1)+')">&lt;</a></li>');
	}
	
	for (i = data.beginIndex; i <= data.endIndex; i++) {
	    if(i == data.currentIndex) {
	    	$("#navigationBar5").append('<li class="active"><a href="#" onclick="inWashingDetails('+data.InWashingDetailsList[0].clothTypesId+','+data.InWashingDetailsList[0].locationId+','+i+')">'+i+'</a></li>');	    	
	    } else {
	    	$("#navigationBar5").append('<li><a href="#" onclick="inWashingDetails('+data.InWashingDetailsList[0].clothTypesId+','+data.InWashingDetailsList[0].locationId+','+i+')">'+i+'</a></li>');	    	
	    }
	} 
	
	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar5").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar5").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
		} else {
			$("#navigationBar5").append('<li><a href="#" onclick="inWashingDetails('+data.InWashingDetailsList[0].clothTypesId+','+data.InWashingDetailsList[0].locationId+','+(data.currentIndex + 1)+')">&gt;</a></li>');			
		}
	}

	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar5").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar5").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
		} else {
			$("#navigationBar5").append('<li><a href="#" onclick="inWashingDetails('+data.InWashingDetailsList[0].clothTypesId+','+data.InWashingDetailsList[0].locationId+','+(data.deploymentLog.totalPages)+')">&gt;&gt;</a></li>');			
		}
	 }
   }
}

function showDateRangePopup(){
	$('#showAssignUpholsteryDate').modal('show');
	
}


$(function() {
	function a(a, b) {
		$("#upholsteryAssignDate").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
	}
	a(moment().subtract(1, "days"), moment()), $("#upholsteryAssignDate").daterangepicker( {
		maxDate: new Date(),
		format : 'DD-MM-YYYY',
		ranges: {
            Today: [moment(), moment()],
            Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")],
            "Last 7 Days": [moment().subtract(6, "days"), moment()],
            "This Month": [moment().startOf("month"), moment().endOf("month")],
            "Last Month": [moment().subtract(1, "months").startOf("month"), moment().subtract(1, "months").endOf("month")]
        }
	}
	, a)
});

function showUpholsteryAssignedVehicles(pageNumber){
	var dateRange 	= ($('#upholsteryAssignDate').val()).split(" to ");
	var startDate	= dateRange[0].split("-").reverse().join("-").trim();
	var endDate		= dateRange[1].split("-").reverse().join("-").trim();
	
	var jsonObject	= new Object();

	jsonObject["startDate"]			= startDate;
	jsonObject["endDate"]			= endDate;
	jsonObject["pageNumber"]		= pageNumber;
	
	if($('#upholsteryAssignDate').val() == "" || $('#upholsteryAssignDate').val() == null || $('#upholsteryAssignDate').val() == undefined){
		showMessage('errors','Please Select Date First');
		return false;
	}
	$.ajax({
		url: "getVehicleAssignUpholstery",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			$('#showAssignUpholsteryDate').modal('hide');
			setUpholsteryAssignedVehicles(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setUpholsteryAssignedVehicles(data){
	console.log("data",data)
	var	vehicleClothInventoryHistoryList = data.vehicleClothInventoryHistoryList;
	$("#setUpholsteryVehicles").empty();
	
	var tr1 	= $('<tr>');
	var thead 	= $('<thead>');
	var tbody 	= $('<tbody>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th>');
	var th4		= $('<th>');
	var th5		= $('<th>');
	var th6		= $('<th>');
	var th7		= $('<th>');
	var th8		= $('<th>');

	tr1.append(th1.append("No"));
	tr1.append(th2.append("Vehicle No"));
	tr1.append(th3.append("Upholstery Name"));
	tr1.append(th4.append("Quantity"));
	tr1.append(th5.append("Stock Type"));
	tr1.append(th6.append("Assigned/Removed"));
	tr1.append(th7.append("Upholstery Assign Date"));
	tr1.append(th8.append("Upholstery Assign By"));

	thead.append(tr1);
	
	if(vehicleClothInventoryHistoryList != undefined && vehicleClothInventoryHistoryList.length > 0) {
		for(var i = 0; i < vehicleClothInventoryHistoryList.length; i++) {
			var tr2 = $('<tr class="ng-scope">');
			
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td>');
			var td4		= $('<td>');
			var td5		= $('<td>');
			var td6		= $('<td>');
			var td7		= $('<td>');
			var td8		= $('<td>');
			
			tr2.append(td1.append(i + 1));
			tr2.append(td2.append(vehicleClothInventoryHistoryList[i].vehicleRegistration));
			tr2.append(td3.append(vehicleClothInventoryHistoryList[i].clothTypeName));
			tr2.append(td4.append(vehicleClothInventoryHistoryList[i].quantity));
			
			if(vehicleClothInventoryHistoryList[i].stockTypeId == 1){
				tr2.append(td5.append('<span class="label label-default label-warning">'+vehicleClothInventoryHistoryList[i].stockTypeName));
			} else {
				tr2.append(td5.append('<span class="label label-default label-success">'+vehicleClothInventoryHistoryList[i].stockTypeName));
			}
			if(vehicleClothInventoryHistoryList[i].asignType != 1){
				tr2.append(td6.append('<span class="label label-default label-warning">'+vehicleClothInventoryHistoryList[i].asignTypeStr));
			} else {
				tr2.append(td6.append('<span class="label label-default label-success">'+vehicleClothInventoryHistoryList[i].asignTypeStr));
			}
			tr2.append(td7.append(vehicleClothInventoryHistoryList[i].createdOnStr));
			tr2.append(td8.append(vehicleClothInventoryHistoryList[i].createdByName));

			tbody.append(tr2);
		}

		$("#navigationBar6").empty();
		
		if(data.currentIndex == 1) {
			$("#navigationBar6").append('<li class="disabled"><a href="#" onclick="">&lt;&lt;</a></li>');		
		} else {
			$("#navigationBar6").append('<li><a href="#" onclick="showUpholsteryAssignedVehicles(1)">&lt;&lt;</a></li>');		
		}

		if(data.currentIndex == 1) {
			$("#navigationBar6").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
		} else {
			$("#navigationBar6").append('<li><a href="#" onclick="showUpholsteryAssignedVehicles('+(data.currentIndex - 1)+')">&lt;</a></li>');
		}
		for (i = data.beginIndex; i <= data.endIndex; i++) {
		    if(i == data.currentIndex) {
		    	$("#navigationBar6").append('<li class="active"><a href="#" onclick="showUpholsteryAssignedVehicles('+i+')">'+i+'</a></li>');	    	
		    } else {
		    	$("#navigationBar6").append('<li><a href="#" onclick="showUpholsteryAssignedVehicles('+i+')">'+i+'</a></li>');	    	
		    }
		} 
		if(data.deploymentLog.totalPages == 1) {
			$("#navigationBar6").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
		} else {
			if(data.currentIndex == data.deploymentLog.totalPages) {
				$("#navigationBar6").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
			} else {
				$("#navigationBar6").append('<li><a href="#" onclick="showUpholsteryAssignedVehicles('+(data.currentIndex + 1)+')">&gt;</a></li>');			
			}
		}

		if(data.deploymentLog.totalPages == 1) {
			$("#navigationBar6").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
		} else {
			if(data.currentIndex == data.deploymentLog.totalPages) {
				$("#navigationBar6").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
			} else {
				$("#navigationBar6").append('<li><a href="#" onclick="showUpholsteryAssignedVehicles('+(data.deploymentLog.totalPages)+')">&gt;&gt;</a></li>');			
			}
		 }
		$("#setUpholsteryVehicles").append(thead);
		$("#setUpholsteryVehicles").append(tbody);
		$('#setUpholsteryAssignVehicles').modal('show');  
	}else{
		showMessage('info','No record found !')
	}
	
	
}

