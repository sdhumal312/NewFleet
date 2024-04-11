$(document).ready(function() {
	getPageWiseUreaInvoiceDetails(1);
});

function getPageWiseUreaInvoiceDetails(pageNumber) {
	
	
	showLayer();
	var jsonObject					= new Object();
	jsonObject["pageNumber"]			= pageNumber;

	$.ajax({
		url: "getPageWiseUreaInvoiceDetails",
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
	$("#totalClothInvoice").html(data.invoiceCount.toFixed(2));
	$('#countId').html('Total Urea Invoice');
	var showSubLocation = $("#showSubLocation").val();
	document.getElementById('All').className = "active";
	document.getElementById('AllStock').className = "tab-link";
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
	if($("#downloadUreaInventoryDocument").val() == true || $("#downloadUreaInventoryDocument").val() == 'true'){
		tr1.append(th12.append("Doc"));
	}
	tr1.append(th13.append("Action"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.UreaInvoice != undefined && data.UreaInvoice.length > 0) {
		
		var ureaInvoice = data.UreaInvoice;
	
		for(var i = 0; i < ureaInvoice.length; i++) {
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
	
			tr1.append(td1.append('<a href="showUreaInvoice?Id='+ureaInvoice[i].ureaInvoiceId+'" target="_blank">UI-'+ureaInvoice[i].ureaInvoiceNumber+'</a>'));
			var curl = "ShowVendor.in?vendorId="+ureaInvoice[i].vendorId+"&page="+data.SelectPage
			tr1.append(td2.append('<a target="_blank" href="' + curl + '">'+ureaInvoice[i].vendorName+'</a><br>'));
			tr1.append(td4.append(ureaInvoice[i].locationName));
			if(showSubLocation == true || showSubLocation == 'true'){
				tr1.append(td14.append(ureaInvoice[i].subLocation));
			}
			tr1.append(td7.append(ureaInvoice[i].invoiceNumber));
			tr1.append(td8.append(ureaInvoice[i].invoiceDateStr));
			if($('#roundupConfig').val()==true ||$('#roundupConfig').val()=='true'){
				tr1.append(td9.append(Math.round(ureaInvoice[i].invoiceAmount)));	
			}else{
				tr1.append(td9.append((ureaInvoice[i].invoiceAmount).toFixed(2)));	
			}
			tr1.append(td10.append(ureaInvoice[i].quantity.toFixed(2)));	
			tr1.append(td11.append(ureaInvoice[i].createdBy));	
			
			if($("#downloadUreaInventoryDocument").val() == true || $("#downloadUreaInventoryDocument").val() == 'true'){
				if(ureaInvoice[i].urea_document == true){
					tr1.append(td12.append('<a href="download/UreaInvoiceDocument/'+ureaInvoice[i].urea_document_id+'" <span class="fa fa-download"> Doc</span> </a>'));
				}else{
					tr1.append(td12.append());
				}
			}
			var curl = "editUreaInvoice.in?Id="+ureaInvoice[i].ureaInvoiceId
			if((ureaInvoice[i].paymentTypeId !=2 || ureaInvoice[i].vendorPaymentStatus == 2) &&  ureaInvoice[i].vendorPaymentStatus != 0){
				tr1.append(td13.append(
				'<div class="btn-group">'
				+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
				+'<ul class="dropdown-menu pull-right">'
				+'<li><a href="'+curl+'"><i class="fa fa-edit"></i> Edit</a></li>'
				+'<li><a href="#" class="confirmation" onclick="deleteUreaInvoice('+ureaInvoice[i].ureaInvoiceId+')"><span class="fa fa-trash"></span> Delete</a></li>'
				+'</ul>'
				+'</div>'
				));
			}else{
				tr1.append(td13.append(
						'<div class="btn-group">'
						+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
						+'<ul class="dropdown-menu pull-right">'
						+'<li><i class="fa fa-money"></i> '+ureaInvoice[i].vendorPaymentStatusStr+'</a></li>'
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
		$("#navigationBar").append('<li><a href="#" onclick="getPageWiseUreaInvoiceDetails(1)">&lt;&lt;</a></li>');		
	}

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="getPageWiseUreaInvoiceDetails('+(data.currentIndex - 1)+')">&lt;</a></li>');
	}
	
	for (i = data.beginIndex; i <= data.endIndex; i++) {
	    if(i == data.currentIndex) {
	    	$("#navigationBar").append('<li class="active"><a href="#" onclick="getPageWiseUreaInvoiceDetails('+i+')">'+i+'</a></li>');	    	
	    } else {
	    	$("#navigationBar").append('<li><a href="#" onclick="getPageWiseUreaInvoiceDetails('+i+')">'+i+'</a></li>');	    	
	    }
	} 
	
	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getPageWiseUreaInvoiceDetails('+(data.currentIndex + 1)+')">&gt;</a></li>');			
		}
	}

	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getPageWiseUreaInvoiceDetails('+(data.deploymentLog.totalPages)+')">&gt;&gt;</a></li>');			
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
	var totalStock	= 0;
	if(data.locationId == undefined){
		$('#countId').html('ALL Urea');
	}else{
		$('#countId').html(data.location+' Urea');
	}
	
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
		
		
		for(var i = 0; i < detailsDtos.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			
			tr1.append(td1.append(i + 1));
			if($("#showSubLocation").val() == true || $("#showSubLocation").val() == 'true'){
				tr1.append(td2.append('<a href="#" id="abc" onclick="showSubLocationUreaDetails('+data.locationId+',\''+(detailsDtos[i].manufacturerName)+'\');">'+detailsDtos[i].manufacturerName+'</a>'));
			}else{
				tr1.append(td2.append(detailsDtos[i].manufacturerName));
			}
			tr1.append(td3.append((detailsDtos[i].stockQuantity).toFixed(2)));
		//	tr1.append(td3.append((detailsDtos[i].quantity - detailsDtos[i].usedQuantity).toFixed(2)));
			totalStock += detailsDtos[i].stockQuantity;
			tbody.append(tr1);
		}
		
		
		$("#totalClothInvoice").html(totalStock.toFixed(2));
		
	}else{
		$("#totalClothInvoice").html(0);
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
/*function deleteUreaInvoice(id){
	console.log("ID",id)
	showLayer();
	var jsonObject					= new Object();
	jsonObject["Id"]				= Number(id);

	$.ajax({
		url: "deleteUreaInvoice",
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
	
}*/

function showSubLocationUreaDetails(locationId,manufacturerName){
	var manufacturerName = manufacturerName;
	showLayer();
	var jsonObject					= new Object();
	jsonObject["mainLocationId"]	= locationId;
	jsonObject["manufacturerName"]	= manufacturerName;
	
	$.ajax({	
		url: "getsubLocationUreaDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			setsubLocationUreaDetails(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setsubLocationUreaDetails (data){
	
	$("#subLocationModelTable").empty();
	$("#showSubLocationUreaDetails").modal('show');
	
	var sublocationUreaDetails = data.sublocationUreaDetails;
	
	if(sublocationUreaDetails != undefined || sublocationUreaDetails != null){
		for(var index = 0 ; index < sublocationUreaDetails.length; index++){
			
			var columnArray = new Array();
			columnArray.push("<td class='fit'>"+(index+1)+"</td>");
			columnArray.push("<td class='fit'ar> <h4> "+ sublocationUreaDetails[index].subLocation  +"</td>");
			columnArray.push("<td class='fit ar'>" + sublocationUreaDetails[index].stockQuantity.toFixed(2) +"</td>");
			
			$('#subLocationModelTable').append("<tr id='penaltyID"+sublocationUreaDetails[index].inventory_id+"' >" + columnArray.join(' ') + "</tr>");
			
		}
		
		$("#mainLocationName").html($("#statues").val());
		$("#ureaManufacturerName").html(data.manufacturerName);
		columnArray = [];
		
		}else{
			showMessage('info','No Record Found!')
		}
	}