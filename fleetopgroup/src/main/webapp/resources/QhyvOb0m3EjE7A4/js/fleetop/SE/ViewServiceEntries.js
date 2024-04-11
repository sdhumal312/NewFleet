$(document).ready(function() {
	showList(1,1);
});

function showList(status, pagenumber){
	if(status == 1){
		$('#open').addClass('active');
		$('#inProcess').removeClass('active');
		$('#completed').removeClass('active');
	} else if(status == 2){
		$('#inProcess').addClass('active');
		$('#open').removeClass('active');
		$('#completed').removeClass('active');
	} else {
		$('#completed').addClass('active');
		$('#inProcess').removeClass('active');
		$('#open').removeClass('active');
	}
	
	var jsonObject					= new Object();
	jsonObject["status"]			= status;
	jsonObject["pagenumber"]		= pagenumber;
	
	showLayer();
	$.ajax({
		url: "getServiceEntriesList",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setServiceEntriesList(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setServiceEntriesList(data) {
	$("#count").text(data.totalserviceentriescount);
	$("#VendorPaymentTable").empty();
	
	
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
	var th12	= $('<th>');
	var th13	= $('<th>');
	var thInvoiceDate = $('<th class="fit ar">');
	var thPaidDate = $('<th class="fit ar">');

	tr1.append(th1.append("SE-No"));
	tr1.append(th2.append("Vehicle"));
	tr1.append(th4.append("Vendor"));
	tr1.append(th7.append("Invoice Number"));
	if(data.configuration.showInvoiceDateOnListView){
		tr1.append(thInvoiceDate.append("Invoice Date"));
	}
	tr1.append(th8.append("Job Number"));
	if(!data.configuration.removeFeildFromLaborTab)
	tr1.append(th9.append("Cost"));
	
	tr1.append(th10.append("Paid By"));
	if(data.configuration.showPaidDateOnListView){
		tr1.append(thPaidDate.append("Paid Date"));
	}
	if(data.configuration.showInvoiceDateOnListView){
		
	}
	tr1.append(th11.append("Last modified By"));
	tr1.append(th12.append("Document"));
	tr1.append(th13.append("Action"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.ServiceEntries != undefined && data.ServiceEntries.length > 0) {
		
		var ServiceEntries = data.ServiceEntries;
	
		for(var i = 0; i < ServiceEntries.length; i++) {
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
			var td12	= $('<td>');
			var td13	= $('<td>');
			var tdInDate	= $('<td class="fit ar">');
			var tdPaidDate	= $('<td class="fit ar">');
			
			tr1.append(td1.append('<a href="showServiceEntryDetails?serviceEntryId='+ServiceEntries[i].serviceEntries_id+'" target="_blank">SE-'+ServiceEntries[i].serviceEntries_Number+'</a>'));
			
			var curl = "VehicleServiceEntriesDetails.in?vid="+ServiceEntries[i].vid
			tr1.append(td2.append('<a target="_blank" href="' + curl + '">'+ServiceEntries[i].vehicle_registration+'</a><br>'));
			
			tr1.append(td4.append(ServiceEntries[i].vendor_name));
			tr1.append(td7.append(ServiceEntries[i].invoiceNumber));
			if(data.configuration.showInvoiceDateOnListView){
				tr1.append(tdInDate.append(ServiceEntries[i].invoiceDate));
			}
			tr1.append(td8.append(ServiceEntries[i].jobNumber));
			if(!data.configuration.removeFeildFromLaborTab)
			tr1.append(td9.append((ServiceEntries[i].totalserviceROUND_cost).toFixed(2)));
			
			tr1.append(td10.append(ServiceEntries[i].service_paidby));	
			if(data.configuration.showPaidDateOnListView){
				tr1.append(tdPaidDate.append(ServiceEntries[i].service_paiddate));
			}
			tr1.append(td11.append(ServiceEntries[i].lastModifiedBy));
			
			
			if(ServiceEntries[i].service_document == true){
				tr1.append(td12.append('<a href="download/serviceDocument/'+ServiceEntries[i].service_document_id+'.in"> <span class="fa fa-download"> Doc</span> </a>'));
			} else {
				tr1.append(td12.append(" "));
			}
			
			if(ServiceEntries[i].serviceEntries_statusId != 3){
				var curl = "serviceEntriesEditAjax.in?SEID="+ServiceEntries[i].serviceEntries_id
				tr1.append(td13.append(
				'<div class="btn-group">'
				+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
				+'<ul class="dropdown-menu pull-right">'
				+'<li><a href="'+curl+'"><i class="fa fa-edit"></i> Edit</a></li>'
				+'<li><a href="#" class="confirmation" onclick="deleteServiceEntry('+ServiceEntries[i].serviceEntries_id+')"><span class="fa fa-trash"></span> Delete</a></li>'
				+'</ul>'
				+'</div>'
				));
			} else {
				tr1.append(td13.append(
						'<div class="btn-group">'
						+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
						+'<ul class="dropdown-menu pull-right">'
						+'<li><span class="label label-warning"><i class="fa fa-dot-circle-o"></i>SERVICE COMPLETED</span></li>'
						+'</ul>'
						+'</div>'
						));
			}
			

			tbody.append(tr1);
		}
		
		$("#allCount").html(data.allCount);
		$("#inProcessCount").html(data.inProcessCount);
		$("#completeCount").html(data.completeCount);
		
	}else{
		showMessage('info','No record found !')
	}
	
	$("#VendorPaymentTable").append(thead);
	$("#VendorPaymentTable").append(tbody);
	
	$("#navigationBar").empty();

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;&lt;</a></li>');		
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="showList('+data.SelectStatus+', 1)">&lt;&lt;</a></li>');		
	}

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="showList('+data.SelectStatus+', '+(data.currentIndex - 1)+')">&lt;</a></li>');
	}
	
	for (i = data.beginIndex; i <= data.endIndex; i++) {
	    if(i == data.currentIndex) {
	    	$("#navigationBar").append('<li class="active"><a href="#" onclick="showList('+data.SelectStatus+', '+i+')">'+i+'</a></li>');	    	
	    } else {
	    	$("#navigationBar").append('<li><a href="#" onclick="showList('+data.SelectStatus+', '+i+')">'+i+'</a></li>');	    	
	    }
	} 
	
	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="showList('+data.SelectStatus+', '+(data.currentIndex + 1)+')">&gt;</a></li>');			
		}
	}

	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="showList('+data.SelectStatus+', '+(data.deploymentLog.totalPages)+')">&gt;&gt;</a></li>');			
		}
	}
}

function deleteServiceEntry(serviceEntries_id){
	
	var jsonObject						= new Object();
	jsonObject["serviceEntries_id"]		= serviceEntries_id;
	
	showLayer();
	$.ajax({
		url: "deleteServiceEntryDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			if(data.serviceEntryCannotBeDeleted != undefined && data.serviceEntryCannotBeDeleted == true){
				hideLayer();
				showMessage('info', 'Cannot Delete Service Entry Please Remove Task Details!');
			}else if(data.accidentEntryApproved != undefined && data.accidentEntryApproved){
				hideLayer();
				showMessage('info', 'Cannot Delete Service Entry As Vehicle Accident Quotation is Approved !');
			}else{
				hideLayer();
				window.location.replace("viewServiceEntries");
			}
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function serachServiceEntryByNumber(){
	
	var jsonObject					= new Object();
	jsonObject["serviceNumber"]		= $("#searchByNumber").val();
	
	if( $("#searchByNumber").val() == ""){
		$('#searchByNumber').focus();
		showMessage('errors', 'Please Enter Valid Service Number !');
		return false;
	}
	
	showLayer();
	$.ajax({
		url: "searchServiceEntriesByNumber",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			console.log('data : ', data);
			
			if(data.serviceEntriesFound != undefined && data.serviceEntriesFound == true){
				
				if(data.serviceEntries.serviceEntries_statusId != 3){
					hideLayer();
					window.location.replace("showServiceEntryDetails.in?serviceEntryId="+data.serviceEntryId+"");
				} else {
					hideLayer();
					window.location.replace("showCompleteServiceEntryDetails.in?serviceEntryId="+data.serviceEntryId+"");
				}
				
			}
			
			if(data.serviceEntriesNotFound != undefined && data.serviceEntriesNotFound == true){
				hideLayer();
				showMessage('info', 'Please Enter valid Service Entry Number!');
			}
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

$(function() {
	function a(a, b) {
		$("#ReportDailydate").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
	}
	a(moment().subtract(1, "days"), moment()), $("#ReportDailydate").daterangepicker( {
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

function searchServiceEntriesDateWise(){
	
	var jsonObject					= new Object();
	jsonObject["dateRange"]			= $("#ReportDailydate").val();
	
	if( $("#ReportDailydate").val() == ""){
		$('#ReportDailydate').focus();
		showMessage('errors', 'Please Enter Valid Date !');
		return false;
	}
	
	showLayer();
	$.ajax({
		url: "searchServiceEntriesDateWise",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			console.log('dataDate : ', data);
			setServiceEntriesDateWise(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setServiceEntriesDateWise(data){
	
	$('#searchServiceEntriesByDate').modal('hide');
	$("#totalSE").hide();
	$("#filter").hide();
	$("#tabsHeading").hide();
	$("#tabsData").hide();
	$(".filterByDateData").removeClass('hide');
	
$("#VendorPaymentTable1").empty();
	
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
	var th12	= $('<th>');
	var th13	= $('<th>');

	tr1.append(th1.append("SE-No"));
	tr1.append(th2.append("Vehicle"));
	tr1.append(th4.append("Vendor"));
	tr1.append(th7.append("Invoice Number"));
	tr1.append(th8.append("Job Number"));
	tr1.append(th9.append("Cost"));
	tr1.append(th10.append("Paid By"));
	tr1.append(th11.append("Last modified By"));
	tr1.append(th12.append("Document"));
	tr1.append(th13.append("Action"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.ServiceEntries != undefined && data.ServiceEntries.length > 0) {
		
		var ServiceEntries = data.ServiceEntries;
	
		for(var i = 0; i < ServiceEntries.length; i++) {
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
			var td12	= $('<td>');
			var td13	= $('<td>');
			
			tr1.append(td1.append('<a href="showServiceEntryDetails?serviceEntryId='+ServiceEntries[i].serviceEntries_id+'" target="_blank">SE-'+ServiceEntries[i].serviceEntries_Number+'</a>'));
			
			var curl = "VehicleServiceEntriesDetails.in?vid="+ServiceEntries[i].vid
			tr1.append(td2.append('<a target="_blank" href="' + curl + '">'+ServiceEntries[i].vehicle_registration+'</a><br>'));
			
			tr1.append(td4.append(ServiceEntries[i].vendor_name));
			tr1.append(td7.append(ServiceEntries[i].invoiceNumber));
			tr1.append(td8.append(ServiceEntries[i].jobNumber));
			tr1.append(td9.append((ServiceEntries[i].totalserviceROUND_cost).toFixed(2)));	
			tr1.append(td10.append(ServiceEntries[i].service_paidby));	
			tr1.append(td11.append(ServiceEntries[i].lastModifiedBy));
			
			if(ServiceEntries[i].service_document == true){
				tr1.append(td12.append('<a href="/download/serviceDocument/'+ServiceEntries[i].service_document_id+'.in"> <span class="fa fa-download"> Doc</span> </a>'));
			} else {
				tr1.append(td12.append(" "));
			}
				
			
			if(ServiceEntries[i].serviceEntries_statusId != 3){
				var curl = "serviceEntriesEditAjax.in?SEID="+ServiceEntries[i].serviceEntries_id
				tr1.append(td13.append(
				'<div class="btn-group">'
				+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
				+'<ul class="dropdown-menu pull-right">'
				+'<li><a href="'+curl+'"><i class="fa fa-edit"></i> Edit</a></li>'
				+'<li><a href="#" class="confirmation" onclick="deleteServiceEntry('+ServiceEntries[i].serviceEntries_id+')"><span class="fa fa-trash"></span> Delete</a></li>'
				+'</ul>'
				+'</div>'
				));
			} else {
				tr1.append(td13.append(
						'<div class="btn-group">'
						+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
						+'<ul class="dropdown-menu pull-right">'
						+'<li><span class="label label-warning"><i class="fa fa-dot-circle-o"></i>SERVICE COMPLETED</span></li>'
						+'</ul>'
						+'</div>'
						));
			}

			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#VendorPaymentTable1").append(thead);
	$("#VendorPaymentTable1").append(tbody);
	
}

function searchSEByDifferentFilter(){
	
	var jsonObject					= new Object();
	jsonObject["filter"]			= $("#searchByDifferentFilter").val();
	
	if( $("#searchByDifferentFilter").val() == ""){
		$('#searchByDifferentFilter').focus();
		showMessage('errors', 'Please Enter Valid Details !');
		return false;
	}
	
	showLayer();
	$.ajax({
		url: "searchServiceEntriesByDifferentFilters",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			console.log('dataDate : ', data);
			setServiceEntriesByDifferentFilters(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setServiceEntriesByDifferentFilters(data){
	
	$("#totalSE").hide();
	$("#filter").hide();
	$("#tabsHeading").hide();
	$("#tabsData").hide();
	$(".filterByDateData").addClass('hide');
	$(".filterByDifferentData").removeClass('hide');
	
	$("#VendorPaymentTable2").empty();
	
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
	var th12	= $('<th>');
	var th13	= $('<th>');

	tr1.append(th1.append("SE-No"));
	tr1.append(th2.append("Vehicle"));
	tr1.append(th4.append("Vendor"));
	tr1.append(th7.append("Invoice Number"));
	tr1.append(th8.append("Job Number"));
	tr1.append(th9.append("Cost"));
	tr1.append(th10.append("Paid By"));
	tr1.append(th11.append("Last modified By"));
	tr1.append(th12.append("Document"));
	tr1.append(th13.append("Action"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.ServiceEntries != undefined && data.ServiceEntries.length > 0) {
		
		var ServiceEntries = data.ServiceEntries;
	
		for(var i = 0; i < ServiceEntries.length; i++) {
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
			var td12	= $('<td>');
			var td13	= $('<td>');
			
			tr1.append(td1.append('<a href="showServiceEntryDetails?serviceEntryId='+ServiceEntries[i].serviceEntries_id+'" target="_blank">SE-'+ServiceEntries[i].serviceEntries_Number+'</a>'));
			
			var curl = "VehicleServiceEntriesDetails.in?vid="+ServiceEntries[i].vid
			tr1.append(td2.append('<a target="_blank" href="' + curl + '">'+ServiceEntries[i].vehicle_registration+'</a><br>'));
			
			tr1.append(td4.append(ServiceEntries[i].vendor_name));
			tr1.append(td7.append(ServiceEntries[i].invoiceNumber));
			tr1.append(td8.append(ServiceEntries[i].jobNumber));
			tr1.append(td9.append((ServiceEntries[i].totalserviceROUND_cost).toFixed(2)));	
			tr1.append(td10.append(ServiceEntries[i].service_paidby));	
			tr1.append(td11.append(ServiceEntries[i].lastModifiedBy));
			
			if(ServiceEntries[i].service_document == true){
				tr1.append(td12.append('<a href="/download/serviceDocument/'+ServiceEntries[i].service_document_id+'.in"> <span class="fa fa-download"> Doc</span> </a>'));
			} else {
				tr1.append(td12.append(" "));
			}
				
			
			if(ServiceEntries[i].serviceEntries_statusId != 3){
				var curl = "serviceEntriesEditAjax.in?SEID="+ServiceEntries[i].serviceEntries_id
				tr1.append(td13.append(
				'<div class="btn-group">'
				+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
				+'<ul class="dropdown-menu pull-right">'
				+'<li><a href="'+curl+'"><i class="fa fa-edit"></i> Edit</a></li>'
				+'<li><a href="#" class="confirmation" onclick="deleteServiceEntry('+ServiceEntries[i].serviceEntries_id+')"><span class="fa fa-trash"></span> Delete</a></li>'
				+'</ul>'
				+'</div>'
				));
			} else {
				tr1.append(td13.append(
						'<div class="btn-group">'
						+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
						+'<ul class="dropdown-menu pull-right">'
						+'<li><span class="label label-warning"><i class="fa fa-dot-circle-o"></i>SERVICE COMPLETED</span></li>'
						+'</ul>'
						+'</div>'
						));
			}

			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#VendorPaymentTable2").append(thead);
	$("#VendorPaymentTable2").append(tbody);
	
}