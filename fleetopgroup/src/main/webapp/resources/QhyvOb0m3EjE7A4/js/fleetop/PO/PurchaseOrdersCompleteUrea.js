var totalReceivedQuantity 	= 0;
var totalReturnQuantity	=0;
var totalOrderQuantity = 0;
var TotalReceivedPartCost =0;
$(document).ready(function() {
	showLayer();
	var jsonObject							= new Object();
	jsonObject["purchaseOrderId"]			= $('#purchaseOrderId').val();
	jsonObject["purchaseOrderTypeId"]		= $('#purchaseOrderType').val();
	
	$.ajax({
		url: "getPurchaseOrdersPartDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setPurchaseOrdersPartDetails(data);
			if( $('#makeApproval').val() == true || $('#makeApproval').val() == 'true'){
				setPurchaseOrdersRejectPartDetails(data);
				setPurchaseOrdersBranchTransferPartDetails(data);
			}
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
	$.ajax({
		url: "getPurchaseOrdersDebitNoteDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setPurchaseOrdersDebitNoteDetails(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
});

function setPurchaseOrdersPartDetails(data){
	$("#ureaPurchaseOrdersDetails").empty();
	var ureaPurchaseOrdersDetails = data.ureaPurchaseOrdersDetails ;
	if( ureaPurchaseOrdersDetails != null ){
		for(var index = 0 ; index < ureaPurchaseOrdersDetails.length; index++){
			if(ureaPurchaseOrdersDetails[index].approvalPartStatusId == 2){
			var columnArray = new Array();
			columnArray.push('<td class="fit">'+(index+1)+'</td>');
			columnArray.push('<td class="fit">'+ ureaPurchaseOrdersDetails[index].purchaseorder_partname +'</td>');
			columnArray.push('<td class="fit">'+ ureaPurchaseOrdersDetails[index].quantity+'</td>');
			columnArray.push('<td class="fit">'+ ureaPurchaseOrdersDetails[index].received_quantity+'</td>');
			
			
			columnArray.push('<td class="fit"> <i class="fa fa-inr"></i>'+ ureaPurchaseOrdersDetails[index].finalReceivedAmount+'</td>');
			
			columnArray.push('<td class="fit"><i class="fa fa-inr">'+ ureaPurchaseOrdersDetails[index].parteachcost +'</td>');
			columnArray.push('<td class="fit">'+ ureaPurchaseOrdersDetails[index].discount +'%</td>');		
			columnArray.push('<td class="fit">'+ ureaPurchaseOrdersDetails[index].tax +'%</td>');
			columnArray.push('<td class="fit"><i class="fa fa-inr">'+ ureaPurchaseOrdersDetails[index].totalcost +'</td>');			
			if($("#showVehicleDetailsInPO").val() == true || $("#showVehicleDetailsInPO").val() == 'true') {
				columnArray.push("<td class='fit ar'><a href='#' class='confirmation ' onclick='showPOVehicleDetails("+ureaPurchaseOrdersDetails[index].partid+");'><span class='fa fa-plus'></span> Show Vehicle</a></td>");
			}
			$('#ureaPurchaseOrdersDetails').append("<tr data-object-id='' class='ng-scope' id=purchaseOrdersToUreaId'"+ureaPurchaseOrdersDetails[index].partid+"' >" + columnArray.join(' ') + "</tr>");
			
			totalOrderQuantity += ureaPurchaseOrdersDetails[index].quantity;
			totalReceivedQuantity += ureaPurchaseOrdersDetails[index].received_quantity;
			TotalReceivedPartCost += ureaPurchaseOrdersDetails[index].finalReceivedAmount;
		}
		}
		
		totalReturnQuantity = (totalOrderQuantity -totalReceivedQuantity).toFixed(2);
		
		$('#totalOrderQuantity').text(totalOrderQuantity);
		$('#totalReceivedQuantity').text(totalReceivedQuantity);
		$('#totalReturnQuantity').text(totalReturnQuantity);
		$('#TotalReceivedPartCost').text(TotalReceivedPartCost);
		
		columnArray = [];
	}else{
		var tr		= $('<tr>');
		var td		= $('<td colspan="8" >');
	
		td.append('<h5 align="center">Purchase Order is Empty </h5>');
		tr.append(td);
		
		$('#ureaPurchaseOrdersDetails').append(tr);
	}
	
}
function setPurchaseOrdersRejectPartDetails(data){
	$("#ureaPurchaseRejectDetails").empty();
	var ureaDetails = data.ureaPurchaseOrdersDetails;
	
	if(ureaDetails != undefined || ureaDetails != null ){
	
		for(var index = 0 ; index < ureaDetails.length; index++){
			if(ureaDetails[index].approvalPartStatusId == 3){
				var columnArray = new Array();
				$("#rejectedApproval").val(true);
				
				
				columnArray.push('<td class="fit"></td>');
				columnArray.push('<td class="fit">'+ ureaDetails[index].purchaseorder_partname +'</td>');
				columnArray.push('<td class="fit">'+ ureaDetails[index].quantity+'</td>');
				columnArray.push('<td class="fit"><i class="fa fa-inr">'+ ureaDetails[index].parteachcost +'</td>');
				columnArray.push('<td class="fit">'+ ureaDetails[index].discount +'%</td>');		
				columnArray.push('<td class="fit">'+ ureaDetails[index].tax +'%</td>');
				columnArray.push('<td class="fit"><i class="fa fa-inr">'+ ureaDetails[index].totalcost +'</td>');			
				
				
				$('#ureaPurchaseRejectDetails').append("<tr data-object-id='' class='ng-scope' id=purchaseOrdersToUreaId'"+ureaDetails[index].partid+"' >" + columnArray.join(' ') + "</tr>");
			}
		
		}
		
		columnArray = [];
	}
	
}

function setPurchaseOrdersBranchTransferPartDetails(data){
	$("#ureaPurchaseTransferDetails").empty();
	var ureaDetails = data.ureaPurchaseOrdersDetails;
	
	if(ureaDetails != undefined || ureaDetails != null ){
	
		for(var index = 0 ; index < ureaDetails.length; index++){
			if(ureaDetails[index].approvalPartStatusId == 4){
				$("#transferApproval").val(true);
				var columnArray = new Array();
				
				
				columnArray.push('<td class="fit"></td>');
				columnArray.push('<td class="fit">'+ ureaDetails[index].purchaseorder_partname +'</td>');
				columnArray.push('<td class="fit">'+ ureaDetails[index].quantity+'</td>');
				columnArray.push('<td class="fit"><i class="fa fa-inr">'+ ureaDetails[index].parteachcost +'</td>');
				columnArray.push('<td class="fit">'+ ureaDetails[index].discount +'%</td>');		
				columnArray.push('<td class="fit">'+ ureaDetails[index].tax +'%</td>');
				columnArray.push('<td class="fit"><i class="fa fa-inr">'+ ureaDetails[index].totalcost +'</td>');			
				
				$('#ureaPurchaseTransferDetails').append("<tr data-object-id='' class='ng-scope' id=purchaseOrdersToUreaId'"+ureaDetails[index].partid+"' >" + columnArray.join(' ') + "</tr>");
			}
		
		} 
		
		columnArray = [];
	}
	
}


function setPurchaseOrdersDebitNoteDetails(data){
	$("#ureaPurchaseOrdersReturnDetails").empty();
	var purchaseOrderDebitDetails = data.purchaseOrderDebitDetails ;
	if( purchaseOrderDebitDetails != null ){
		for(var index = 0 ; index < purchaseOrderDebitDetails.length; index++){
			var columnArray = new Array();
			columnArray.push('<td class="fit">'+(index+1)+'</td>');
			columnArray.push('<td class="fit">'+ purchaseOrderDebitDetails[index].purchaseorder_partname +'</td>');
			columnArray.push('<td class="fit">'+ purchaseOrderDebitDetails[index].received_quantity_remark +'</td>');
			columnArray.push('<td class="fit">'+ purchaseOrderDebitDetails[index].notreceived_quantity+'</td>');
			columnArray.push('<td class="fit"><i class="fa fa-inr">'+ purchaseOrderDebitDetails[index].parteachcost +'</td>');
			columnArray.push('<td class="fit">'+ purchaseOrderDebitDetails[index].discount +'%</td>');		
			columnArray.push('<td class="fit">'+ purchaseOrderDebitDetails[index].tax +'%</td>');
			columnArray.push('<td class="fit"><i class="fa fa-inr">'+ purchaseOrderDebitDetails[index].total_return_cost +'</td>');			
			
			$('#ureaPurchaseOrdersReturnDetails').append("<tr data-object-id='' class='ng-scope' id=purchaseorderto_debitnoteid'"+purchaseOrderDebitDetails[index].purchaseorderto_debitnoteid+"' >" + columnArray.join(' ') + "</tr>");
		}
			
		columnArray = [];
	}else{
		var tr		= $('<tr>');
		var td		= $('<td colspan="8" >');
	
		td.append('<h5 align="center">Purchase Order Debit Note is Empty</h5>');
		tr.append(td);
		
		$('#ureaPurchaseOrdersReturnDetails').append(tr);
	}
	
}

setTimeout(function(){ 
	if($("#rejectedApproval").val() == true || $("#rejectedApproval").val() == 'true'){
		$("#rejectedPart").show()
	}else{
		$("#rejectedPart").hide()
	}	
	if($("#transferApproval").val() == true || $("#transferApproval").val() == 'true'){
		$("#transferPart").show()
	}else{
		$("#transferPart").hide()
	}
}, 300);
			