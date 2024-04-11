var TotalTyreSoldAmount = 0;
var SCRAPED_TO_SOLD = 4;

$(document).ready(function() {
	showLayer();
	var jsonObject					= new Object();
	jsonObject["invoiceId"]		= $('#invoiceId').val();
	$.ajax({
		url: "getTyreSoldDetailsByTyreSoldInvoiceId",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			setsoldTyreDetails(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
});

function setsoldTyreDetails(data){
	
	$("#showTyreTable").empty();
	
	var soldType = data.tyreSoldInvoiceDetails.soldType;
	
	var tyreList = data.tyreSoldDetails;
	
	if(soldType == SCRAPED_TO_SOLD){
		$("#completeThead").removeClass("hide");
		if( tyreList != undefined || tyreList.length > 0  ){
			
			for(var index = 0 ; index < tyreList.length; index++){
				var columnArray = new Array();
				columnArray.push("<td class='fit'>"+(index+1)+"</td>");
				columnArray.push("<td class='fit'ar> <h4> "+ tyreList[index].tyre_NUMBER  +"</td>");
				columnArray.push("<td class='fit ar'><i class='fa fa-inr'></i>" + tyreList[index].tyre_SIZE +"</td>");
				
				$('#showTyreTable').append("<tr id='penaltyID"+tyreList[index].tyre_ID+"' >" + columnArray.join(' ') + "</tr>");
				
			}
			$("#totalTyreSoldAmount").html(data.tyreSoldInvoiceDetails.tyreSoldInvoiceNetAmount);
			
			$("#soldDate").html(data.soldDate);
			$("#tyreState").html(data.tyreStatus);
			$("#statues").html(data.tyreStatus);
			$("#soldInvoiceNo").html(data.soldInvoiceNumber);
			columnArray = [];
		}else{
			showMessage('info','No Record Found!')
		}
		
	}else{
		$("#inProcesstHead").removeClass("hide");
		
		if( tyreList != undefined || tyreList.length > 0  ){
			
			for(var index = 0 ; index < tyreList.length; index++){
				
				var columnArray = new Array();
				columnArray.push("<td class='fit'>"+(index+1)+"</td>");
				columnArray.push("<td class='fit'ar> <h4> "+ tyreList[index].tyre_NUMBER +"-"+ tyreList[index].tyre_SIZE +"</td>");
				columnArray.push("<td class='fit ar'><i class='fa fa-inr'></i>" + tyreList[index].tyre_AMOUNT +"</td>");
				columnArray.push("<td class='fit ar'>" + tyreList[index].discount + "%</td>");
				columnArray.push("<td class='fit ar'>" + tyreList[index].gst + "%</td>");
				columnArray.push("<td class='fit ar'><i class='fa fa-inr'></i>" + tyreList[index].tyreNetAmount + "</td>");
				/*columnArray.push("<td class='fit ar'><a href='#' class='confirmation' onclick='deletePickAndDropLocation("+tyreList[index].pickAndDropLocationId+");'><span class='fa fa-trash'></span> Delete</a></td>");*/
				
				$('#showTyreTable').append("<tr id='penaltyID"+tyreList[index].tyre_ID+"' >" + columnArray.join(' ') + "</tr>");
				
				TotalTyreSoldAmount +=  tyreList[index].tyreNetAmount; 
			}
			$("#totalTyreSoldAmount").html(TotalTyreSoldAmount);
			$("#soldDate").html(data.soldDate);
			$("#tyreState").html(data.tyreStatus);
			$("#statues").html(data.tyreStatus);
			$("#soldInvoiceNo").html(data.soldInvoiceNumber);
			columnArray = [];
		}else{
			showMessage('info','No Record Found!')
		}
	}
	
	var input_Statues = data.tyreStatus;
	switch (input_Statues) {
	case "IN PROCESS":
		 $("#status-open").addClass("status-led-open");
		break;
	case "SOLD":
		 $("#status-in-progress").addClass("status-led-in-progress");
		break;
	}
	

	}

