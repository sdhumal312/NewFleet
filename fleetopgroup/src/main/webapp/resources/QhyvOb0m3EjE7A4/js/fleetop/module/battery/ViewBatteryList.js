$(document).ready(function() {
	getPageWiseBatteryInvoiceDetails(1);
});

function getPageWiseBatteryInvoiceDetails(pageNumber) {
	
	
	showLayer();
	var jsonObject					= new Object();
	jsonObject["pageNumber"]			= pageNumber;

	$.ajax({
		url: "getPageWiseBatteryInvoiceDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			setBatteryInvoiceList(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}


function setBatteryInvoiceList(data) {
	var availableCount	= data.batteryAvailableCount;
	var serviceCount	= data.batteryServiceCount;
	var scrapedCount	= data.batteryScrapedCount;
	
	$("#allAvailableBatteryCount").html(availableCount);
	$("#batteryInServiceCount").html(serviceCount);
	$("#scrapedBatteryCount").html(scrapedCount);
	
	$("#VendorPaymentTable").empty();
	$("#searchInv").val('');
	$("#searchBattery").val('');
	$('#searchData').show();
	$('#batteryCount').show();
	$('#countDiv').show();
	$('#listTab').show();
	$('#addBatteryDiv').addClass('hide');
	$("#totalBattryInvoice").html(data.BatteryInvoiceCount);
	document.getElementById('All').className = "active";
	
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
	if(showSubLocation == true || showSubLocation == 'true'){
		var th13	= $('<th class="fit ar">');
	}

	tr1.append(th1.append("No"));
	tr1.append(th2.append("Vendor Name"));
	
	tr1.append(th4.append("Location"));
	if(showSubLocation == true || showSubLocation == 'true'){
		tr1.append(th13.append("Sub Location"));
	}

	tr1.append(th7.append("Invoice"));
	tr1.append(th8.append("Invoice Date"));
	tr1.append(th9.append("Amount"));
	tr1.append(th10.append("Created By"));
	if($("#downloadBatteryInventoryDocument").val() == true || $("#downloadBatteryInventoryDocument").val() == 'true' ){
		tr1.append(th11.append("Doc"));
	}	
	
	tr1.append(th12.append("Action"));
	

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.BatteryInvoice != undefined && data.BatteryInvoice.length > 0) {
		
		var batteryInvoice = data.BatteryInvoice;
	
		for(var i = 0; i < batteryInvoice.length; i++) {
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
			if(showSubLocation == true || showSubLocation == 'true'){
				var td13	= $('<td class="fit ar">');
			}
		
			tr1.append(td1.append('<a href="showBatteryInvoice?Id='+batteryInvoice[i].batteryInvoiceId+'" target="_blank">BI-'+batteryInvoice[i].batteryInvoiceNumber+'</a>'));
			var curl = "ShowVendor.in?vendorId="+batteryInvoice[i].vendorId+"&page="+data.SelectPage
			tr1.append(td2.append('<a target="_blank" href="' + curl + '">'+batteryInvoice[i].vendorName+'</a><br>'));
			//tr1.append(td2.append(batteryInvoice[i].vendorName));
			//tr1.append(td3.append(batteryInvoice[i].batteryCapacity));
			tr1.append(td4.append(batteryInvoice[i].batteryLocation));
			if(showSubLocation == true || showSubLocation == 'true'){
				tr1.append(td13.append(batteryInvoice[i].subLocation));
			}
			//tr1.append(td5.append(batteryInvoice[i].batteryInvoice));
			//tr1.append(td6.append(batteryInvoice[i].batteryCapacity));
			tr1.append(td7.append(batteryInvoice[i].invoiceNumber));
			tr1.append(td8.append(batteryInvoice[i].invoiceDateStr));
			if($('#roundupConfig').val()==true||$('#roundupConfig').val()=='true'){
				
				tr1.append(td9.append(Math.round(batteryInvoice[i].invoiceAmount)));	
			}else{
				
				tr1.append(td9.append((batteryInvoice[i].invoiceAmount).toFixed(2)));
			}
			
			
			//tr1.append(td6.append(batteryInvoice[i].invoiceAmount));
			tr1.append(td10.append( batteryInvoice[i].firstName));
			if($("#downloadBatteryInventoryDocument").val() == true || $("#downloadBatteryInventoryDocument").val() == 'true'){
				if(batteryInvoice[i].battery_document == true){
					tr1.append(td11.append('<a href="download/BatteryInvoiceDocument/'+batteryInvoice[i].battery_document_id+'" <span class="fa fa-download"> Doc</span> </a>'));
				}else{
					tr1.append(td11.append());
				}
			}
			
			var curl = "editBatteryInvoice.in?Id="+batteryInvoice[i].batteryInvoiceId
		
			console.log("batteryInvoice[i].vendorPaymentStatus",batteryInvoice[i].paymentTypeId)
			 if((batteryInvoice[i].paymentTypeId !=2 || batteryInvoice[i].vendorPaymentStatus == 2) &&  batteryInvoice[i].vendorPaymentStatus != 0){
				tr1.append(td12.append(
						'<div class="btn-group">'
						+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
						+'<ul class="dropdown-menu pull-right">'
						+'<li><a href="'+curl+'"><i class="fa fa-edit"></i> Edit</a></li>'
						+'<li><a href="#" class="confirmation" onclick="deleteBatteryInvoice('+batteryInvoice[i].batteryInvoiceId+')"><span class="fa fa-trash"></span> Delete</a></li>'
						+'</ul>'
						+'</div>'
						));
					}else{
						tr1.append(td12.append(
								'<div class="btn-group">'
								+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
								+'<ul class="dropdown-menu pull-right">'
								+'<li><i class="fa fa-money"></i> '+batteryInvoice[i].vendorPaymentStatusStr+'</a></li>'
								+'</ul>'
								+'</div>'
								));
					}
			
			
			
			
			/*if(batteryInvoice[i].vendorPaymentStatus == 2){
				if(batteryInvoice[i].vendorPaymentDate != null){
				tr1.append(td12.append(
				'<div class="btn-group">'
				+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
				+'<ul class="dropdown-menu pull-right">'
				+'<li><a href="#" class="confirmation" onclick="deleteBatteryInvoice('+batteryInvoice[i].batteryInvoiceId+')"><span class="fa fa-trash"></span> Delete</a></li>'
				+'</ul>'
				+'</div>'
				));
				}
				else{
					tr1.append(td12.append(
					'<div class="btn-group">'
					+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
					+'<ul class="dropdown-menu pull-right">'
					+'<li><a href="'+curl+'"><i class="fa fa-edit"></i> Edit</a></li>'
					+'<li><a href="#" class="confirmation" onclick="deleteBatteryInvoice('+batteryInvoice[i].batteryInvoiceId+')"><span class="fa fa-trash"></span> Delete</a></li>'
					+'</ul>'
					+'</div>'
					));
				}
			}else if(batteryInvoice[i].vendorPaymentStatus == 4 || batteryInvoice[i].vendorPaymentStatus == 5){
				tr1.append(td12.append(
						'<div class="btn-group">'
						+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
						+'<ul class="dropdown-menu pull-right">'
						+'<li><i class="fa fa-money"></i> '+batteryInvoice[i].vendorPaymentStatusStr + ' PAID </a></li>'
						+'</ul>'
						+'</div>'
						));
			}else{
				tr1.append(td12.append(
						'<div class="btn-group">'
						+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
						+'<ul class="dropdown-menu pull-right">'
						+'<li><i class="fa fa-money"></i> '+batteryInvoice[i].vendorPaymentStatusStr+'</a></li>'
						+'</ul>'
						+'</div>'
						));
			}*/
			
			
			
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
		$("#navigationBar").append('<li><a href="#" onclick="getPageWiseBatteryInvoiceDetails(1)">&lt;&lt;</a></li>');		
	}

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="getPageWiseBatteryInvoiceDetails('+(data.currentIndex - 1)+')">&lt;</a></li>');
	}
	
	for (i = data.beginIndex; i <= data.endIndex; i++) {
	    if(i == data.currentIndex) {
	    	$("#navigationBar").append('<li class="active"><a href="#" onclick="getPageWiseBatteryInvoiceDetails('+i+')">'+i+'</a></li>');	    	
	    } else {
	    	$("#navigationBar").append('<li><a href="#" onclick="getPageWiseBatteryInvoiceDetails('+i+')">'+i+'</a></li>');	    	
	    }
	} 
	
	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getPageWiseBatteryInvoiceDetails('+(data.currentIndex + 1)+')">&gt;</a></li>');			
		}
	}

	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getPageWiseBatteryInvoiceDetails('+(data.deploymentLog.totalPages)+')">&gt;&gt;</a></li>');			
		}
	}
}

function deleteBatteryInvoice(batteryInvoiceId) {
	showLayer();
	var jsonObject					= new Object();

	jsonObject["batteryInvoiceId"]			= batteryInvoiceId;

	$.ajax({
		url: "deleteBatteryInventory.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.deleted != undefined && (data.deleted == 'true' || data.deleted == true )){
				showMessage('success', 'Data Deleted Sucessfully!');
			}
			if(data.deleted != undefined && (data.deleted == 'false' || data.deleted == false )){
				showMessage('success', 'Please first Battary Amount & Battery Details first!');
			}
			getPageWiseBatteryInvoiceDetails(1);
			hideLayer();
		},
		error: function (e) {
			console.log("Error : " , e);
			hideLayer();
		}
	});
}
var locationIds = 0 ;
var batteryStatus;
function locationBatteryDetails(location, pageNumber){
	showLayer();
	var jsonObject					= new Object();
	locationIds						= location;
	jsonObject["pageNumber"]			= pageNumber;
	jsonObject["location"]				= location;

	$.ajax({
		url: "locationBatteryDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			setBatteryDetailsList(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setBatteryDetailsList(data) {
	
	var locationWiseBatteryAvailableCount = data.locationWiseBatteryAvailableCount;
	var locationWisebatteryServiceCount = data.locationWisebatteryServiceCount;
	var locationWisebatteryScrapedCount = data.locationWisebatteryScrapedCount;
	var mainLocationIds	 				= $("#mainLocationIds").val().split(",");
	var subLocationForMainLocation		= false;
	
	$("#allAvailableBatteryCount").html(locationWiseBatteryAvailableCount);
	$("#batteryInServiceCount").html(locationWisebatteryServiceCount);
	$("#scrapedBatteryCount").html(locationWisebatteryScrapedCount);
	
	$('#countId').html(data.location+' Battery');
	$("#totalBattryInvoice").html(data.batteryQuentity);
	
	$('#statues').val(data.location);
	document.getElementById("All").className = "tab-link";
	
	if(data.PartLocations != undefined){
		for(var i = 0 ; i < data.PartLocations.length; i++){
			if(data.PartLocations[i].partlocation_name == data.location){
				document.getElementById(data.location).className = "active";
			}else{
				document.getElementById(data.PartLocations[i].partlocation_name).className = "tab-link";	
			}
			if(data.locationId == mainLocationIds[i]) { 
				subLocationForMainLocation = true
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
	if(subLocationForMainLocation == true){
		var th10	= $('<th class="fit ar">');
	}

	tr1.append(th1.append("Battery no."));
	tr1.append(th2.append("Manufacturer"));
	tr1.append(th3.append("Model"));
	tr1.append(th7.append("Capacity"));
	tr1.append(th4.append("Location"));
	if(subLocationForMainLocation == true){
		tr1.append(th10.append("Sub Location"));
	}
	tr1.append(th8.append("Vehicle"));
	tr1.append(th5.append("Status"));
	tr1.append(th6.append("Usage(In Days)"));
	tr1.append(th9.append("Usage(In Odometer)"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.battery != undefined && data.battery.length > 0) {
		var battery = data.battery;
		
		for(var i = 0; i < battery.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td class="fit ar">');
			var td5		= $('<td class="fit ar">');
			var td7		= $('<td class="fit ar">');
			var td8		= $('<td class="fit ar">');
			var td9		= $('<td class="fit ar">');
			if(subLocationForMainLocation == true){
				var td10	= $('<td class="fit ar">');
			}
			
			if(battery[i].batteryStatusId == 1){
				var status		= '<span class="label label-pill label-success">'+battery[i].batteryStatus+'</span>';
			}else if(battery[i].batteryStatusId == 3){
				var status		= '<span class="label label-pill label-danger">'+battery[i].batteryStatus+'</span>';
			}else{
				var status		= '<span class="label label-pill label-warning">'+battery[i].batteryStatus+'</span>';
			}
			var td6		= $('<td class="fit ar">');
			
			if(battery[i].batteryUsesStatusId == 1){
				var span = '<span class="label label-pill label-success">'+battery[i].batteryUsesStatus+'</span>';
			}else{
				var span = '<span class="label label-pill label-warning">'+battery[i].batteryUsesStatus+'</span>';
			}
			
			tr1.append(td1.append(''+span+'<a href="showBatteryInformation?Id='+battery[i].batteryId+'" target="_blank"> '+battery[i].batterySerialNumber+'</a>'));
			tr1.append(td2.append('<a target="_blank" href="#">'+battery[i].manufacturerName+'</a><br>'));
			tr1.append(td3.append(battery[i].batteryType));
			tr1.append(td7.append(battery[i].batteryCapacity));
			tr1.append(td4.append(battery[i].locationName));
			if(subLocationForMainLocation == true){
				tr1.append(td10.append(battery[i].subLocation));
			}
			tr1.append(td8.append(battery[i].vehicle_registration));
			tr1.append(td5.append(status));
			tr1.append(td6.append(battery[i].usesNoOfTime));
			tr1.append(td9.append(battery[i].batteryUsesOdometer));

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
		$("#navigationBar").append('<li><a href="#" onclick="locationBatteryDetails('+data.locationId+',1)">&lt;&lt;</a></li>');		
	}

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="locationBatteryDetails('+data.locationId+','+(data.currentIndex - 1)+')">&lt;</a></li>');
	}
	
	for (i = data.beginIndex; i <= data.endIndex; i++) {
	    if(i == data.currentIndex) {
	    	$("#navigationBar").append('<li class="active"><a href="#" onclick="locationBatteryDetails('+data.locationId+','+i+')">'+i+'</a></li>');	    	
	    } else {
	    	$("#navigationBar").append('<li><a href="#" onclick="locationBatteryDetails('+data.locationId+','+i+')">'+i+'</a></li>');	    	
	    }
	} 
	
	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="locationBatteryDetails('+data.locationId+','+(data.currentIndex + 1)+')">&gt;</a></li>');			
		}
	}

	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="locationBatteryDetails('+data.locationId+','+(data.deploymentLog.totalPages)+')">&gt;&gt;</a></li>');			
		}
	}
}
function invoiceSearch(pageNumber){
	showLayer();
	var jsonObject					= new Object();

	jsonObject["pageNumber"]			= pageNumber;
	jsonObject["term"]					= $('#searchInv').val();

	$.ajax({
		url: "searchBatteryInvoice",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			setBatteryInvoiceList(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}
function invoiceSearchOnEnter(e){
	var code = (e.keyCode ? e.keyCode : e.which);
	if(code == 13) { //Enter keycode
		invoiceSearch(1);
	}
}
function batterySearchOnEnter(e){
	var code = (e.keyCode ? e.keyCode : e.which);
	if(code == 13) { //Enter keycode
		searchBatteryDetails(1);
	}
}
function searchBatteryDetails(pageNumber){
	showLayer();
	var jsonObject					= new Object();

	jsonObject["pageNumber"]			= pageNumber;
	jsonObject["term"]					= $('#searchBattery').val();

	$.ajax({
		url: "searchBatteryDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			setBatteryDetailsList(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}
function getBatteryList(pageNo,status){
	var jsonObject						= new Object();
	batteryStatus						= status;
	jsonObject["locationId"]			= locationIds;
	jsonObject["status"]				= batteryStatus;
	jsonObject["pageNo"]				= pageNo;

	
	$.ajax({
		url: "getBatteryCountList",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			setBatteryCountList(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setBatteryCountList(data){
	$("#list1").empty();
	if(data.batteryCountList != undefined && data.batteryCountList.length > 0) {
		var battery = data.batteryCountList;
		
		var thead 	= $('<thead>');
		var tr1 	= $('<tr>');
		var tbody 	= $('<tbody>');
		
		var th1		= $('<th>');
		var th2		= $('<th>');
		var th3		= $('<th class="fit ar">');
		var th4		= $('<th class="fit ar">');
		var th5		= $('<th class="fit ar">');
		var th6		= $('<th>');
		
		
		tr1.append(th1.append("Battery No"));
		tr1.append(th2.append("Manufacturer"));
		tr1.append(th3.append("Model"));
		tr1.append(th4.append("Capacity"));
		tr1.append(th5.append("Location"));
	/*	tr1.append(th6.append("Vehicle"));*/
		tr1.append(th6.append("Status"));

		thead.append(tr1);
		if(batteryStatus == 1){
			$("#headerData").html("<h3> AVAILABLE BATTERY </h3>");
		}else if(batteryStatus == 3){
			$("#headerData").html("<h3> SCRAPED BATTERY</h3>");	
		}else{
			$("#headerData").html("<h3> BATTERY IN-SERVICE </h3>");
		}
		for(var i = 0; i < battery.length; i++) {
			var tr1 = $('<tr class="ng-scope" >');
			
			var td1		= $('<td style="padding: 2px;"  >');
			var td2		= $('<td style="padding: 2px;" >');
			var td3		= $('<td style="padding: 2px;" >');
			var td4		= $('<td style="padding: 2px;" >');
			var td5		= $('<td style="padding: 2px;" >');
			var td6		= $('<td style="padding: 2px;" >');
			
			if(battery[i].batteryStatusId == 1){
				var status		= '<span class="label label-pill label-success">'+battery[i].batteryStatus+'</span>';
			}else if(battery[i].batteryStatusId == 3){
				var status		= '<span class="label label-pill label-danger">'+battery[i].batteryStatus+'</span>';
			}else{
				var status		= '<span class="label label-pill label-warning">'+battery[i].batteryStatus+'</span>';
			}
			
			
			tr1.append(td1.append('<a href="showBatteryInformation?Id='+battery[i].batteryId+'" target="_blank"> '+battery[i].batterySerialNumber+'</a>'));
			tr1.append(td2.append('<a target="_blank" href="#">'+battery[i].manufacturerName+'</a><br>'));
			tr1.append(td3.append(battery[i].batteryType));
			tr1.append(td4.append(battery[i].batteryCapacity));
			tr1.append(td5.append(battery[i].locationName));
			/*tr1.append(td6.append(battery[i].vehicle_registration));*/
			tr1.append(td6.append(status));
			tbody.append(tr1);
		}
		$('#batteryModelList').modal('show');
	}else{
		$('#batteryModelList').modal('hide');
		showMessage('info','No record found !')
	}
	
	
	$("#navigationBar6").empty();
	if(data.currentIndex == 1) {
		$("#navigationBar6").append('<li class="disabled"><a href="#" onclick="">&lt;&lt;</a></li>');		
	} else {
		$("#navigationBar6").append('<li><a href="#" onclick="getBatteryList(1,'+batteryStatus+')">&lt;&lt;</a></li>');		
	}

	if(data.currentIndex == 1) {
		$("#navigationBar6").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	} else {
		$("#navigationBar6").append('<li><a href="#" onclick="getBatteryList('+(data.currentIndex - 1)+','+batteryStatus+')">&lt;</a></li>');
	}
	for (i = data.beginIndex; i <= data.endIndex; i++) {
	    if(i == data.currentIndex) {
	    	$("#navigationBar6").append('<li class="active"><a href="#" onclick="getBatteryList('+i+','+batteryStatus+')">'+i+'</a></li>');	    	
	    } else {
	    	$("#navigationBar6").append('<li><a href="#" onclick="getBatteryList('+i+','+batteryStatus+')">'+i+'</a></li>');	    	
	    }
	} 
	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar6").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar6").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
		} else {
			$("#navigationBar6").append('<li><a href="#" onclick="getBatteryList('+(data.currentIndex + 1)+','+batteryStatus+')">&gt;</a></li>');			
		}
	}

	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar6").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar6").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
		} else {
			$("#navigationBar6").append('<li><a href="#" onclick="getBatteryList('+(data.deploymentLog.totalPages)+','+batteryStatus+')">&gt;&gt;</a></li>');			
		}
	 }
	
	$("#list1").append(thead);
	$("#list1").append(tbody);
	
	
}

