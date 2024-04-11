var totalReceivedQuantity 	= 0;
var totalReturnQuantity	=0;
var totalOrderQuantity = 0;

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
	var ureaPurchaseOrdersDetails = data.ureaPurchaseOrdersDetails ;
	if( ureaPurchaseOrdersDetails != null ){
		for(var index = 0 ; index < ureaPurchaseOrdersDetails.length; index++){
			
			totalOrderQuantity 		+= ureaPurchaseOrdersDetails[index].quantity;
			totalReceivedQuantity 	+= ureaPurchaseOrdersDetails[index].received_quantity;
			
		}
		
		totalReturnQuantity = (totalOrderQuantity -totalReceivedQuantity).toFixed(2);
		
		$('#totalOrderQuantity').text(totalOrderQuantity);
		$('#totalReceivedQuantity').text(totalReceivedQuantity);
		$('#totalReturnQuantity').text(totalReturnQuantity);
		
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
			columnArray.push("<td class='fit ar'><a href='#' class='confirmation' onclick='deleteReturnPurchaseOrdersDetails("+purchaseOrderDebitDetails[index].purchaseorderto_debitnoteid+");'><span class='fa fa-trash'></span> Delete</a></td>");
			
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

function deleteReturnPurchaseOrdersDetails(debitNoteId){

	showLayer();
	var jsonObject							= new Object();
	jsonObject["debitNoteId"]				= debitNoteId;
	jsonObject["purchaseOrderId"]			= $('#purchaseOrderId').val();
	jsonObject["purchaseOrderTypeId"]		= $('#purchaseOrderType').val();
	
	$.ajax({
		url: "deleteReturnPurchaseOrdersDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			window.location.replace("PurchaseOrders_Parts.in?ID="+data.purchaseOrderId+"");
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});

}


			