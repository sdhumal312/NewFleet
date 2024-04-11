var totalReceivedQuantity 	= 0;
var TotalReceivedPartCost 	= 0;

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
});

function setPurchaseOrdersPartDetails(data){
	$("#ureaPurchaseOrdersDetails").empty();
	var ureaPurchaseOrdersDetails = data.ureaPurchaseOrdersDetails ;
	
	if( ureaPurchaseOrdersDetails != null ){
		for(var index = 0 ; index < ureaPurchaseOrdersDetails.length; index++){
			if(ureaPurchaseOrdersDetails[index].approvalPartStatusId == 2){
			var columnArray = new Array();
			columnArray.push('<td class="fit"></td>');
			columnArray.push('<td class="fit">'+ ureaPurchaseOrdersDetails[index].purchaseorder_partname +'</td>');
			columnArray.push('<td class="fit">'+ ureaPurchaseOrdersDetails[index].quantity+'</td>');
			columnArray.push('<td class="fit">'+ ureaPurchaseOrdersDetails[index].received_quantity+'</td>');
		
			columnArray.push('<td class="fit"><i class="fa fa-inr"></i> '+ ureaPurchaseOrdersDetails[index].finalReceivedAmount+'</td>');
			
			columnArray.push('<td class="fit"><i class="fa fa-inr">'+ ureaPurchaseOrdersDetails[index].parteachcost +'</td>');
			columnArray.push('<td class="fit">'+ ureaPurchaseOrdersDetails[index].discount +'%</td>');		
			columnArray.push('<td class="fit">'+ ureaPurchaseOrdersDetails[index].tax +'%</td>');
			columnArray.push('<td class="fit"><i class="fa fa-inr">'+ ureaPurchaseOrdersDetails[index].totalcost +'</td>');			
			if($("#showVehicleDetailsInPO").val() == true || $("#showVehicleDetailsInPO").val() == 'true') {
				columnArray.push("<td class='fit ar'><a href='#' class='confirmation ' onclick='showPOVehicleDetails("+ureaPurchaseOrdersDetails[index].partid+");'><span class='fa fa-plus'></span> Show Vehicle</a></td>");
			}
			$('#ureaPurchaseOrdersDetails').append("<tr data-object-id='' class='ng-scope' id=purchaseOrdersToUreaId'"+ureaPurchaseOrdersDetails[index].partid+"' >" + columnArray.join(' ') + "</tr>");
			
			TotalReceivedPartCost += ureaPurchaseOrdersDetails[index].finalReceivedAmount;
		}
		}
		$('#TotalReceivedPartCost').text(TotalReceivedPartCost);
		
		columnArray = [];
	}else{
		var tr		= $('<tr>');
		var td		= $('<td colspan="8" >');
	
		td.append('<h5 align="center">Purchase Order is Empty</h5>');
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

$(document).ready(
		function($) {
			$('button[id=completeUreaPurchaseOrder]').click(function(e) {
				var jsonObject			= new Object();
				jsonObject["purchaseOrderId"]			= $('#purchaseOrderId').val();
				$.ajax({
					url: "completePurchaseOrder",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						window.location.replace("PurchaseOrders_Parts.in?ID="+data.purchaseOrderId+"");
					},
					error: function (e) {
						hideLayer();
						showMessage('errors', 'Some Error Occurred!');
					}
				});
			})
		});

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

			