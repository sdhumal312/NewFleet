var totalReceivedQuantity 	= 0;

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
var partArray			= new Array();

function setPurchaseOrdersPartDetails(data){
	$("#ureaPurchaseOrdersDetails").empty();
	var ureaPurchaseOrdersDetails = data.ureaPurchaseOrdersDetails ;
	if( ureaPurchaseOrdersDetails != null ){
		for(var index = 0 ; index < ureaPurchaseOrdersDetails.length; index++){
			if(ureaPurchaseOrdersDetails[index].approvalPartStatusId == 2){
			var columnArray = new Array();
			var ureaDetails	= new Object();
			columnArray.push('<td class="fit">'+ ureaPurchaseOrdersDetails[index].purchaseorder_partname +'</td>');
			columnArray.push('<td class="fit">'+ ureaPurchaseOrdersDetails[index].quantity+'</td>');
			if(ureaPurchaseOrdersDetails[index].received_quantity == 0){
				columnArray.push('<td class="fit"><i class="fa fa-cube" onclick="showReceiveUreaModal('+ureaPurchaseOrdersDetails[index].partid+','+ureaPurchaseOrdersDetails[index].quantity+','+ureaPurchaseOrdersDetails[index].received_quantity+','+ureaPurchaseOrdersDetails[index].ureaInvoiceToDetailsId+');" style="color: red"></i>'+ ureaPurchaseOrdersDetails[index].received_quantity+'</td>');
			}else{
				columnArray.push('<td class="fit"><i class="fa fa-cube" onclick="showReceiveUreaModal('+ureaPurchaseOrdersDetails[index].partid+','+ureaPurchaseOrdersDetails[index].quantity+','+ureaPurchaseOrdersDetails[index].received_quantity+','+ureaPurchaseOrdersDetails[index].ureaInvoiceToDetailsId+');" style="color: blue"></i>'+ ureaPurchaseOrdersDetails[index].received_quantity+'</td>');
			}
			columnArray.push('<td class="fit"><i class="fa fa-inr">'+ ureaPurchaseOrdersDetails[index].parteachcost +'</td>');
			columnArray.push('<td class="fit">'+ ureaPurchaseOrdersDetails[index].discount +'%</td>');		
			columnArray.push('<td class="fit">'+ ureaPurchaseOrdersDetails[index].tax +'%</td>');
			columnArray.push('<td class="fit"><i class="fa fa-inr">'+ ureaPurchaseOrdersDetails[index].totalcost +'</td>');			
			if($("#showVehicleDetailsInPO").val() == true || $("#showVehicleDetailsInPO").val() == 'true'){
				columnArray.push("<td class='fit ar'><a href='#' class='confirmation ' onclick='showPOVehicleDetails("+ureaPurchaseOrdersDetails[index].partid+");'><span class='fa fa-plus'></span> Show Vehicle</a></td>");
			}
			$('#ureaPurchaseOrdersDetails').append("<tr data-object-id='' class='ng-scope' id=purchaseOrdersToUreaId'"+ureaPurchaseOrdersDetails[index].partid+"' >" + columnArray.join(' ') + "</tr>");

			/*this will be use while receiving PurchaseOrderPart*/
			ureaDetails.manufacturer			= ureaPurchaseOrdersDetails[index].ureaManufacturerId;
			ureaDetails.quantity				= ureaPurchaseOrdersDetails[index].received_quantity;
			ureaDetails.unitprice				= ureaPurchaseOrdersDetails[index].parteachcost;
			ureaDetails.discount				= ureaPurchaseOrdersDetails[index].discount;
			ureaDetails.tax						= ureaPurchaseOrdersDetails[index].tax;
			ureaDetails.tatalcost				= ureaPurchaseOrdersDetails[index].totalcost;
	//		ureaDetails.ureaInvoiceToDetailsId	= ureaPurchaseOrdersDetails[index].ureaInvoiceToDetailsId;
			ureaDetails.noReceivedQuantity		= ureaPurchaseOrdersDetails[index].notreceived_quantity;
			ureaDetails.receivedQuantityRemark	= ureaPurchaseOrdersDetails[index].received_quantity_remark;
			ureaDetails.purchaseOrdersToUreaId	= ureaPurchaseOrdersDetails[index].partid;
			ureaDetails.purchaseOrderId			= $('#purchaseOrderId').val();
			totalReceivedQuantity += ureaPurchaseOrdersDetails[index].received_quantity;
			//totalQuantity +=  Number(quantity_many[i]);
			partArray.push(ureaDetails);
		}
			
		}
			$("#receivedToPurchase").removeClass('hide');
			
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

function showReceiveUreaModal(partId,orderedQuantity,receivedQuantity,ureaInvoiceToDetailsId){
	$("#ureaManufacturerId").val(partId);
	$("#orderedQuantity").val(orderedQuantity);
	$("#receivedQuantity").val(receivedQuantity);
	$("#ureaInvoiceToDetailsId").val(ureaInvoiceToDetailsId);
	$("#receiveUrea").modal("show");
	$("#receivedQuantity").keyup(function(){
		 if($("#receivedQuantity").val()> orderedQuantity){
			 showMessage('info','You Can Not Received Urea More Than '+orderedQuantity+'');
			 $("#receivedQuantity").val(receivedQuantity);
			 return false;
		 }
	});
}


$(document).ready(
		function($) {
			$('button[id=receivedUreaQuantityToPurchaseOrder]').click(function(e) {
				var jsonObject			= new Object();
				jsonObject["purchaseOrderId"]					= $('#purchaseOrderId').val();
				jsonObject["purchaseOrderType"]					= $('#purchaseOrderType').val();
				jsonObject["partId"]							= $('#ureaManufacturerId').val();
				jsonObject["orderedQuantity"]					= $('#orderedQuantity').val();
				jsonObject["receivedQuantity"]					= $('#receivedQuantity').val();
				jsonObject["receivedQuantityRemark"]			= $('#receivedQuantityRemark').val();
				
				$.ajax({
					url: "receivedQuantityToPurchaseOrder",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						if(data.alreadyReceived == true){
							showMessage('info','This PO Already In Received State Hence You Can Not Received Urea Again');
						}
						location.reload();
					},
					error: function (e) {
						hideLayer();
						showMessage('errors', 'Some Error Occurred!');
					}
				});
				
			})
		});

function reEnterPurchaseOrder(){
	var jsonObject			= new Object();
	
	jsonObject["purchaseOrderType"]				= $('#purchaseOrderType').val();
	jsonObject["purchaseOrderId"]				= $('#purchaseOrderId').val();
	jsonObject["purchaseOrderAdvanceCost"]		= $('#purchaseOrderAdvanceCost').val();
	jsonObject["purchaseOrderBalanceCost"]		= $('#purchaseOrderBalanceCost').val();
	
	
	$.ajax({
		url: "reEnterPurchaseOrder",
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

$(document).ready(
		function($) {
			$('button[id=receivedUreaToPurchaseOrder]').click(function(e) {
				
				var jsonObject			= new Object();
				
				jsonObject["purchaseOrderType"]				= $('#purchaseOrderType').val();
				jsonObject["purchaseOrderId"]				= $('#purchaseOrderId').val();
				jsonObject["purchaseOrderAdvanceCost"]		= $('#purchaseOrderAdvanceCost').val();
				jsonObject["purchaseOrderRemark"]			= $('#purchaseOrderRemark').val();//initial_note
				jsonObject["warehouselocation"]				= $('#shipToLocation').val();
				jsonObject["poNumber"]						= $('#poNumber').val();
				jsonObject["invoiceNumber"]					= $('#invoiceNumber').val();
				jsonObject["invoiceAmount"]					= $('#invoiceAmount').val();
				jsonObject["totalClothAmount"]				= $('#totalClothAmount').val();// actually this is total urea cost
				jsonObject["description"]					= $('#initial_note').val();
				jsonObject["totalQuantity"]					= totalReceivedQuantity;
				jsonObject["vendor"]						= $('#vendor').val();
				jsonObject["invoiceDate"]					= $('#invoiceDate').val();
				
				jsonObject.ureaDetails = JSON.stringify(partArray);
				
				if($('#invoiceNumber').val() == "" ){
					showMessage('info','Please Enter The Invoice Number');
					return false;
				}
				if($('#invoiceDate').val() == "" ){
					showMessage('info','Please Select The Invoice Date');
					return false;
				}
				
				if (confirm("Are you sure? Receive PurchaseOrder ")) {

					$.ajax({
						url: "receivedPartFromPurchaseOrder",
						type: "POST",
						dataType: 'json',
						data: jsonObject,
						success: function (data) {
							if(data.alreadyReceived == true){
								showMessage('info','This PO Already In Received State Hence You Can Not Received Urea Again');
							}
							hideLayer();
							window.location.replace("PurchaseOrders_Parts.in?ID="+data.purchaseOrderId+"");
						},
						error: function (e) {
							hideLayer();
							showMessage('errors', 'Some Error Occurred!');
						}
					});
				} else {
					location.reload();
				  }
				
			});
		});	
			