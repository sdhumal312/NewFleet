$(document).ready(function() {
	getPageWiseTyreSoldInvoiceDetails(1);
});

function getPageWiseTyreSoldInvoiceDetails(pageNumber) {
	
	showLayer();
	var jsonObject					= new Object();
	jsonObject["pageNumber"]		= pageNumber;

	$.ajax({
		url: "getPageWiseTyreSoldInvoiceDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setPageWiseTyreSoldInvoiceDetails(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}


function setPageWiseTyreSoldInvoiceDetails(data) {
	$("#tyreSoldInvoiceTable").empty();
	
	var tyreSoldInvoice;
	if(data.tyreSoldInvoiceList != undefined){
		tyreSoldInvoice = data.tyreSoldInvoiceList;
		for(var index = 0 ; index < tyreSoldInvoice.length; index++){
	//<a href='#' class='confirmation' onclick='editPickAndDropLocation("+tyreSoldInvoice[index].tyreSoldInvoiceId+");'><span class='fa fa-edit'></span> Edit</a>&nbsp;&nbsp;&nbsp
		var columnArray = new Array();
		columnArray.push("<td class='fit ar' style='vertical-align: middle;'><a href='addTyreSoldDetails?ID="+tyreSoldInvoice[index].tyreSoldInvoiceId+"'>"+tyreSoldInvoice[index].tyreSoldInvoiceNumber+"</a></td>");
		columnArray.push("<td class='fit ar' style='vertical-align: middle;'>" + tyreSoldInvoice[index].tyreSoldInvoiceDateStr +"</td>");
		columnArray.push("<td class='fit ar' style='vertical-align: middle;'>" + tyreSoldInvoice[index].createdBy + "</td>");
		if(tyreSoldInvoice[index].soldTypeStr == "SCRAPED_TO_SOLD"){
			columnArray.push("<td class=' fit ar ' style='vertical-align: middle;'><span class='label label-pill label-danger'>" + tyreSoldInvoice[index].soldTypeStr + "</span></td>");
		}else{
			columnArray.push("<td class=' fit ar ' style='vertical-align: middle;'><span class='label label-pill label-success'>" + tyreSoldInvoice[index].soldTypeStr + "</span></td>");
		}
		columnArray.push("<td class='fit ar' style='vertical-align: middle;' ><a href='#' class='confirmation' onclick='deleteTyreSoldInvoice("+tyreSoldInvoice[index].tyreSoldInvoiceId+");'><span class='fa fa-trash'></span> Delete</a></td>");
		$('#tyreSoldInvoiceTable').append("<tr id='penaltyID"+tyreSoldInvoice[index].tyreSoldInvoiceId+"' >" + columnArray.join(' ') + "</tr>");
		}
		columnArray = [];
	}else{
		showMessage('info','No Record Found!')
	}
	if(tyreSoldInvoice != undefined){
		
		$("#tyreSoldInvoiceCount").html(tyreSoldInvoice.length);
	}
	
	
	$("#navigationBar").empty();

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;&lt;</a></li>');		
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="getPageWiseTyreSoldInvoiceDetails(1)">&lt;&lt;</a></li>');		
	}

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="getPageWiseTyreSoldInvoiceDetails('+(data.currentIndex - 1)+')">&lt;</a></li>');
	}
	
	for (i = data.beginIndex; i <= data.endIndex; i++) {
	    if(i == data.currentIndex) {
	    	$("#navigationBar").append('<li class="active"><a href="#" onclick="getPageWiseTyreSoldInvoiceDetails('+i+')">'+i+'</a></li>');	    	
	    } else {
	    	$("#navigationBar").append('<li><a href="#" onclick="getPageWiseTyreSoldInvoiceDetails('+i+')">'+i+'</a></li>');	    	
	    }
	} 
	
	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getPageWiseTyreSoldInvoiceDetails('+(data.currentIndex + 1)+')">&gt;</a></li>');			
		}
	}

	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getPageWiseTyreSoldInvoiceDetails('+(data.deploymentLog.totalPages)+')">&gt;&gt;</a></li>');			
		}
	}
}

function deleteTyreSoldInvoice(invoiceId){
	
	showLayer();
	var jsonObject					= new Object();
	jsonObject["invoiceId"]		= invoiceId;

	$.ajax({
		url: "deleteTyreSoldInvoiceDetails",
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