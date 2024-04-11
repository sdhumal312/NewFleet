var ALL 							= 0; 
var OPEN_ALL	 					= 1; 
var WORK_PENDING 					= 2; // on hold
var DSE_PAYMENT_PENDING 			= 3; 
var DSE_ACCOUNT_CLOSE 				= 4; 

var OPEN_INVOICE_PENDING 			= 1; 
var OPEN_INVOICE_RECEIVED 			= 2; 

$(document).ready(function() {
	showList(1,1,0);
});

function showList(status, pagenumber,invoiceStatus){
	
	switch (status) {
	case ALL:
		$('#all').addClass('active');
		$('#allOpen').removeClass('active');
		$('#invoicePending').removeClass('active');
		$('#invoiceReceived').removeClass('active');
		$('#hold').removeClass('active');
		$('#paymentPending').removeClass('active');
		$('#accountClose').removeClass('active');
		
		break;
	case OPEN_ALL:
		if(invoiceStatus == OPEN_INVOICE_PENDING )	{
			$('#all').removeClass('active');
			$('#allOpen').removeClass('active');
			$('#invoicePending').addClass('active');
			$('#invoiceReceived').removeClass('active');
			$('#hold').removeClass('active');
			$('#paymentPending').removeClass('active');
			$('#accountClose').removeClass('active');
		}else if(invoiceStatus == OPEN_INVOICE_RECEIVED ){
			$('#all').removeClass('active');
			$('#allOpen').removeClass('active');
			$('#invoicePending').removeClass('active');
			$('#invoiceReceived').addClass('active');
			$('#hold').removeClass('active');
			$('#paymentPending').removeClass('active');
			$('#accountClose').removeClass('active');
		}else{
			$('#all').removeClass('active');
			$('#allOpen').addClass('active');
			$('#invoicePending').removeClass('active');
			$('#invoiceReceived').removeClass('active');
			$('#hold').removeClass('active');
			$('#paymentPending').removeClass('active');
			$('#accountClose').removeClass('active');
		}
		
		break;
	case WORK_PENDING:
		$('#all').removeClass('active');
		$('#allOpen').removeClass('active');
		$('#invoicePending').removeClass('active');
		$('#invoiceReceived').removeClass('active');
		$('#hold').addClass('active');
		$('#paymentPending').removeClass('active');
		$('#accountClose').removeClass('active');
		
		break;
	case DSE_PAYMENT_PENDING:
		$('#all').removeClass('active');
		$('#allOpen').removeClass('active');
		$('#invoicePending').removeClass('active');
		$('#invoiceReceived').removeClass('active');
		$('#hold').removeClass('active');
		$('#paymentPending').addClass('active');
		$('#accountClose').removeClass('active');
		
		break;
	case DSE_ACCOUNT_CLOSE:
		$('#all').removeClass('active');
		$('#allOpen').removeClass('active');
		$('#invoicePending').removeClass('active');
		$('#invoiceReceived').removeClass('active');
		$('#hold').removeClass('active');
		$('#paymentPending').removeClass('active');
		$('#accountClose').addClass('active');
		
		break;

	default:
		$('#all').addClass('active');
		$('#allOpen').removeClass('active');
		$('#invoicePending').removeClass('active');
		$('#invoiceReceived').removeClass('active');
		$('#hold').removeClass('active');
		$('#paymentPending').removeClass('active');
		$('#accountClose').removeClass('active');
		break;
	}
	
	var jsonObject					= new Object();
	
	console.log("pagenumber",pagenumber)
	jsonObject["status"]			= status;
	jsonObject["pagenumber"]		= pagenumber;
	jsonObject["invoiceStatus"]		= invoiceStatus;
	jsonObject["companyId"]		= $("#companyId").val();
	
	showLayer();
	$.ajax({
		url: "getDealerServiceEntriesList",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setDealerServiceEntriesList(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setDealerServiceEntriesList(data){
	
	$("#dealerServiceEntriesTable").empty();
	$('#searchData').show();
	$('#countDiv').show();
	var dealerServiceEntriesList = data.dealerServiceEntriesList;
	var configuration			= data.configuration;
	
	if(dealerServiceEntriesList != undefined && dealerServiceEntriesList != null){
		for(var index = 0 ; index < dealerServiceEntriesList.length; index++){
			
			var columnArray = new Array();
			columnArray.push("<td class='fit'><a href='showDealerServiceEntries?dealerServiceEntriesId="+dealerServiceEntriesList[index].dealerServiceEntriesId+"' target='_blank'>"+dealerServiceEntriesList[index].dealerServiceEntriesNumberStr+"</a></td>");
			columnArray.push("<td class='fit ar'>  "+ dealerServiceEntriesList[index].vehicleNumber  +"</td>");
			columnArray.push("<td class='fit ar'> "+ dealerServiceEntriesList[index].vendorName  +"</td>");
			columnArray.push("<td class='fit ar'>  "+ dealerServiceEntriesList[index].invoiceNumber  +"</td>");
			columnArray.push("<td class='fit ar'>"+ dealerServiceEntriesList[index].invoiceDateStr  +"</td>");
			columnArray.push("<td class='fit ar'>  "+ dealerServiceEntriesList[index].jobNumber  +"</td>");
			
			if(dealerServiceEntriesList[index].totalInvoiceCost != undefined)
				columnArray.push("<td class='fit ar'>  "+ addCommas((dealerServiceEntriesList[index].totalInvoiceCost).toFixed(2))  +"</td>");
			
			if(configuration.showLastModifiedBy)
				columnArray.push("<td class='fit ar'> "+ dealerServiceEntriesList[index].lastModifiedBy  +"</td>");
			
			if(configuration.showRemark)
				columnArray.push('<td class="fit ar dseRemark"> <a href="#" data-toggle="tooltip" data-placement="top" title="'+dealerServiceEntriesList[index].remark+'">'+ dealerServiceEntriesList[index].remark +'</a></td>');
			
			
			if(configuration.showAction) {
				if(dealerServiceEntriesList[index].statusId == 1)
					columnArray.push("<td class='fit ar' style='vertical-align: middle;' ><a  href='editDealerServiceEntries?dealerServiceEntriesId="+dealerServiceEntriesList[index].dealerServiceEntriesId+"'><span class='fa fa-edit'></span> Edit</a>&nbsp;&nbsp;&nbsp<a href='#' style='color:red'  class='confirmation' onclick='deleteDealerServiceEntries("+dealerServiceEntriesList[index].dealerServiceEntriesId+");'><span class='fa fa-trash'></span> Delete</a></td>");
				else
					columnArray.push("<td class='fit ar'> "+ dealerServiceEntriesList[index].status  +"</td>");
			}
			
			$('#dealerServiceEntriesTable').append("<tr id='penaltyID"+dealerServiceEntriesList[index].vehicleModelId+"' >" + columnArray.join(' ') + "</tr>");
		}
		
		$('.dseRemark').css({"white-space" : "nowrap", "overflow" : "hidden", "text-overflow" : "ellipsis", "max-width" : "100px"});
		
		
		columnArray = [];
		}else{
			showMessage('info','No Record Found!')
		}
	$("#allCount").html(data.allCount);
	$("#allOpenCount").html(data.allOpenCount);
	$("#invoicePendingCount").html(data.invoicePendingCount);
	$("#invoiceReceivedCount").html(data.invoiceReceivedCount);
	$("#holdCount").html(data.holdCount);
	$("#paymentPendingCount").html(data.paymentPendingCount);
	$("#paymentReceivedCount").html(data.paymentReceivedCount);
	
	$("#navigationBar").empty();

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;&lt;</a></li>');		
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="showList('+data.statusId+',1,'+(data.invoiceStatus)+')">&lt;&lt;</a></li>');		
	}

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="showList('+data.statusId+','+(data.currentIndex - 1)+','+(data.invoiceStatus)+')">&lt;</a></li>');
	}
	
	for (i = data.beginIndex; i <= data.endIndex; i++) {
	    if(i == data.currentIndex) {
	    	$("#navigationBar").append('<li class="active"><a href="#" onclick="showList('+data.statusId+','+i+','+(data.invoiceStatus)+')">'+i+'</a></li>');	    	
	    } else {
	    	$("#navigationBar").append('<li><a href="#" onclick="showList('+data.statusId+','+i+','+(data.invoiceStatus)+')">'+i+'</a></li>');	    	
	    }
	} 
	
	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="showList('+data.statusId+','+(data.currentIndex + 1)+','+(data.invoiceStatus)+')">&gt;</a></li>');			
		}
	}

	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="showList('+data.statusId+','+(data.deploymentLog.totalPages)+','+(data.invoiceStatus)+')">&gt;&gt;</a></li>');			
		}
	}
	}

function deleteDealerServiceEntries(DealerServiceEntriesId){
	if(confirm('Are You Sure, Do You Want To Delete Dealer Service Entries! ')){
	var jsonObject								= new Object();
	jsonObject["dealerServiceEntriesId"]		= DealerServiceEntriesId;
	jsonObject["companyId"]						= $("#companyId").val();
	jsonObject["userId"]						= $("#userId").val();
	
	showLayer();
	$.ajax({
		url: "deleteDealerServiceEntries",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.labourDetailsFound != undefined && data.labourDetailsFound == true){
				showMessage('info', 'Can not Delete Dealer Service Entry Please Remove Labour Details!');
				hideLayer();
			}else if(data.partDetailsFound != undefined && data.partDetailsFound){
				showMessage('info', 'Can not Delete Dealer Service Entry Please Remove Part Details!');
				hideLayer();
			}else{
				location.reload();
			}
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	}
}

function addCommas(x) {
	return x.toString().split('.')[0].length > 3 ? x.toString().substring(0,x.toString().split('.')[0].length-3).replace(/\B(?=(\d{2})+(?!\d))/g, ",") + "," + x.toString().substring(x.toString().split('.')[0].length-3): x.toString();
}







