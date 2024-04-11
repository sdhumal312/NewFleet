$(document).ready(function() {
	showLayer();
	var jsonObject						= new Object();
	jsonObject["purchaseOrderId"]		= $('#purchaseOrderId').val();
	jsonObject["purchaseOrderTypeId"]	= $('#purchaseOrderTypeId').val();
	$.ajax({
		url: "getPurchaseOrdersPartDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setPurchaseOrdersPartDetails(data);
			setPurchaseOrdersPartForApproval(data);
			setPurchaseOrdersRejectPartDetails(data);
			setPurchaseOrdersBranchTransferPartDetails(data);
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
	var ureaDetails = data.ureaPurchaseOrdersDetails;
	
	if(ureaDetails != undefined || ureaDetails != null ){
	
		for(var index = 0 ; index < ureaDetails.length; index++){
			if(ureaDetails[index].approvalPartStatusId == 1){
				$("#pendingApproval").val(true);
			}
			if(ureaDetails[index].approvalPartStatusId == 2){
				$("#approvedApproval").val(true);
			}
			if(ureaDetails[index].approvalPartStatusId == 1 || ureaDetails[index].approvalPartStatusId == 2){
				if(ureaDetails[index].partid == null) {
					showMessage('info','No Record Found')
					var tr1		= $('<tr>');
					var td1		= $('<td colspan="8" >');
			
					td1.append('<h5 align="center">Purchase Order is Empty</h5>');
					tr1.append(td1);
				
					$('#ureaPurchaseOrdersDetails').append(tr1);
				      return false;
				    }
					
				var columnArray = new Array();
				
				
				columnArray.push('<td class="fit"></td>');
				columnArray.push('<td class="fit">'+ ureaDetails[index].purchaseorder_partname +'</td>');
				columnArray.push('<td class="fit">'+ ureaDetails[index].quantity+'</td>');
				columnArray.push('<td class="fit"><i class="fa fa-inr">'+ ureaDetails[index].parteachcost +'</td>');
				columnArray.push('<td class="fit">'+ ureaDetails[index].discount +'%</td>');		
				columnArray.push('<td class="fit">'+ ureaDetails[index].tax +'%</td>');
				columnArray.push('<td class="fit"><i class="fa fa-inr">'+ ureaDetails[index].totalcost +'</td>');			
				if(($("#makeApproval").val() == true || $("#makeApproval").val() == 'true')  ){
					columnArray.push('<td class="fit">'+ ureaDetails[index].approvalPartStatus +'</td>');	
				}
				if(($("#addVehicleDetailsInPOAddPart").val() == true || $("#addVehicleDetailsInPOAddPart").val() == 'true') && ($("#editPartDetails").val() == true || $("#editPartDetails").val() == 'true') ){
					columnArray.push("<td class='fit ar'><a href='#' class='confirmation' onclick='deleteUreaPurchaseOrdersDetails("+ureaDetails[index].quantity+","+ureaDetails[index].parteachcost+","+ureaDetails[index].tax+","+ureaDetails[index].partid+","+ ureaDetails[index].totalcost +");'><span class='fa fa-trash'></span> Delete</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' class='confirmation addVehicle' onclick='showAddVehicle("+ureaDetails[index].quantity+","+ ureaDetails[index].partid +");'><span class='fa fa-plus'></span> Add Vehicle</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' class='confirmation' onclick='editPurchaseOrderPartDetails("+ureaDetails[index].ureaManufacturerId+",\"" + ureaDetails[index].purchaseorder_partname + "\","+ureaDetails[index].batteryPartNumber+","+ureaDetails[index].batteryPartNumber+","+ureaDetails[index].batteryPartNumber+","+ureaDetails[index].batteryPartNumber+","+ureaDetails[index].partid+","+ ureaDetails[index].quantity +","+ureaDetails[index].parteachcost+","+ureaDetails[index].discount+","+ureaDetails[index].tax+","+ureaDetails[index].totalcost+");'><span class='fa fa-edit'></span> Edit</a></td>"); // +ureaDetails[index].batteryPartNumber+ <<< not in use because of common function
				}else if($("#addVehicleDetailsInPOAddPart").val() == true || $("#addVehicleDetailsInPOAddPart").val() == 'true'){
					columnArray.push("<td class='fit ar'><a href='#' class='confirmation' onclick='deleteUreaPurchaseOrdersDetails("+ureaDetails[index].quantity+","+ureaDetails[index].parteachcost+","+ureaDetails[index].tax+","+ureaDetails[index].partid+","+ ureaDetails[index].totalcost +");'><span class='fa fa-trash'></span> Delete</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' class='confirmation addVehicle' onclick='showAddVehicle("+ureaDetails[index].quantity+","+ ureaDetails[index].partid +");'><span class='fa fa-plus'></span> Add Vehicle</a></td>");
				}
				else if((($("#editPartDetails").val() == true || $("#editPartDetails").val() == 'true') && ureaDetails[index].approvalPartStatusId == 1 ) ){
					columnArray.push("<td class='fit ar'><a href='#' class='confirmation' onclick='deleteUreaPurchaseOrdersDetails("+ureaDetails[index].quantity+","+ureaDetails[index].parteachcost+","+ureaDetails[index].tax+","+ureaDetails[index].partid+","+ ureaDetails[index].totalcost +");'><span class='fa fa-trash'></span> Delete</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' class='confirmation' onclick='editPurchaseOrderPartDetails("+ureaDetails[index].ureaManufacturerId+",\"" + ureaDetails[index].purchaseorder_partname + "\","+ureaDetails[index].batteryPartNumber+","+ureaDetails[index].batteryPartNumber+","+ureaDetails[index].batteryPartNumber+","+ureaDetails[index].batteryPartNumber+","+ureaDetails[index].partid+","+ ureaDetails[index].quantity +","+ureaDetails[index].parteachcost+","+ureaDetails[index].discount+","+ureaDetails[index].tax+","+ureaDetails[index].totalcost+");'><span class='fa fa-edit'></span> Edit</a></td>");
				}else{
					columnArray.push("<td class='fit ar'><a href='#' class='confirmation' onclick='deleteUreaPurchaseOrdersDetails("+ureaDetails[index].quantity+","+ureaDetails[index].parteachcost+","+ureaDetails[index].tax+","+ureaDetails[index].partid+","+ ureaDetails[index].totalcost +");'><span class='fa fa-trash'></span> Delete</a></td>");
				}
				
				$('#ureaPurchaseOrdersDetails').append("<tr data-object-id='' class='ng-scope' id=purchaseOrdersToUreaId'"+ureaDetails[index].partid+"' >" + columnArray.join(' ') + "</tr>");
			}
		
		}
		$("#sentToPurchase").removeClass('hide');
		
		columnArray = [];
	}else{
		var tr1		= $('<tr>');
		var td1		= $('<td colspan="8" >');
	
		td1.append('<h5 align="center">Purchase Order is Empty</h5>');
		tr1.append(td1);
		
		$('#ureaPurchaseOrdersDetails').append(tr1);
	}
	
}

function setPurchaseOrdersRejectPartDetails(data){
	$("#ureaPurchaseRejectDetails").empty();
	var ureaDetails = data.ureaPurchaseOrdersDetails;
	
	if(ureaDetails != undefined || ureaDetails != null ){
	
		for(var index = 0 ; index < ureaDetails.length; index++){
			if(ureaDetails[index].approvalPartStatusId == 3){
				$("#rejectedApproval").val(true);
				var columnArray = new Array();
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

$(document).ready( function($) {
	
	$("#ureaManufacturerId").change(function(){
		getstockQuantity();
    })
    $("#editUreaManufacturerId").change(function(){
    	getEditstockQuantity();
    })
    
	$("#showAddUreaModal").click(function(){
		
		$("#ureaManufacturerId").select2("val", "");
		
		$('#quantity').val('');    
		$('#unitprice').val('');   
		$('#discount').val(0);    
		$('#tax').val(0);         
		$('#totalCost').val(0);   
		$("#stockId, #editStockId").val(0);
		$("#otherLocationstock, #editOtherLocationStockInfo").val(0);
		if($('#previousRate').val() == true || $('#previousRate').val() == 'true'){
		$("#lastPOUreaDetails").html("");
		}
		
		
		$("#addUrea").modal("show");
	}); 
	
	$("#unitprice").keyup(function(){
		if($("#unitprice").val() > 100){
			 showMessage('info','Unit Price Should Not Be Greater Than 100');
			 $("#unitprice").val(0);
			 return false;
		 }
    });
	
	$("#purchaseOrderAdvanceCost").keyup(function(){
	var purchaseAdvanceCost = Number($("#purchaseOrderAdvanceCost").val());
	var subTotalPOCost	= Number($("#subTotalPOCost").val());
		if( purchaseAdvanceCost> subTotalPOCost){
			 showMessage('info','Advance Amount Can Not Be Greater Than Total Added Part Amount ');
			 $("#purchaseOrderAdvanceCost").val(0);
			 return false;
		 }
    })
    
    
	
	$("#ureaManufacturerId, #editUreaManufacturerId").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getUreaManufacturerList.in?Action=FuncionarioSelect2",
            dataType: "json",
            type: "POST",
            contentType: "application/json",
            quietMillis: 50,
            data: function(a) {
                return {
                    term: a
                }
            },
            results: function(a) {
                return {
                    results: $.map(a, function(a) {
                        return {
                            text: a.manufacturerName,
                            slug: a.slug,
                            id: a.ureaManufacturerId
                        }
                    })
                }
            }
        }
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


$(document).ready(
		function($) {
			$('button[id=saveUreaToPurchaseOrder]').click(function(e) {
				console.log("sdfkushfisuhfisuhfishh")
				var jsonObject			= new Object();
				
				jsonObject["purchaseOrderId"]				= $('#purchaseOrderId').val();
				jsonObject["ureaManufacturerId"]			= $('#ureaManufacturerId').val();
				jsonObject["quantity"]						= $('#quantity').val();
				jsonObject["NotReceivedQuantity"]			= $('#quantity').val();
				jsonObject["unitprice"]						= $('#unitprice').val();
				jsonObject["discount"]						= $('#discount').val();
				jsonObject["gst"]							= $('#tax').val();
				jsonObject["totalCost"]						= $('#totalCost').val();
				jsonObject["purchaseOrderTypeId"]			= $('#purchaseOrderTypeId').val();
				jsonObject["ureaInvoiceToDetailsId"]		= $('#ureaInvoiceToDetailsId').val();
				
				if($('#ureaManufacturerId').val() == "" || $('#ureaManufacturerId').val() == undefined || $('#ureaManufacturerId').val() == null){
					showMessage('info','Please Select Urea Manufacturer');
					return false;
				}
				if($('#quantity').val() == 0 ||$('#quantity').val() == undefined || $('#quantity').val() == null){
					showMessage('info','Please Enter liter');
					return false;
				}
				if($('#unitprice').val() == 0 || $('#unitprice').val() == undefined || $('#unitprice').val() == null){
					showMessage('info','Please Enter unitprice');
					return false;
				}
				
					$.ajax({
						url: "addPurchaseOrderPart",
						type: "POST",
						dataType: 'json',
						data: jsonObject,
						success: function (data) {
							hideLayer();
							location.reload();
							
						},
						error: function (e) {
							hideLayer();
							showMessage('errors', 'Some Error Occurred!');
						}
					});
				
			})
		});
		
function getstockQuantity(a){
	var jsonObject			= new Object();
	
	
	$("#StockName").removeClass('stock-color-red');
	$("#otherStockName").removeClass('stock-color-red');
	
	$("#ureaManufacturerId").val();
	$("#shipLocationId").val();
	
	jsonObject["ureaManufacturerId"]	= $("#ureaManufacturerId").val();
	jsonObject["warehouselocation"]		= $("#shipLocationId").val();
	
	$.ajax({
		url: "getUreastockQuantity",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			var stock = data.stockDetails.quantity;
			if(stock == null ){
				$("#stockId").val(0);
				$("#approvalStockId").val(0);
				$("#stockName").addClass('stock-color-red');
				
			}else{
				$("#stockName").addClass('stock-color-blue');
				$("#stockId").val(stock);
				$("#approvalStockId").val(stock);
			}
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	$.ajax({
		url: "getOtherUreastockQuantity",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			var otherStock = data.stockDetails.quantity;
			if(otherStock == undefined ){
				$("#otherLocationstock").val(0);
				$("#approvalOtherLocationstock").val(0);
				$("#otherStockName").addClass('stock-color-red');
			}else{
				$("#otherStockName").addClass('stock-color-blue');
				$("#otherLocationstock").val(otherStock);
				$("#approvalOtherLocationstock").val(otherStock);
			}
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
}

function deleteUreaPurchaseOrdersDetails(quantity,eachCost,tax,purchaseOrdersToUreaId,partTotalCost){
	var jsonObject			= new Object();
	
	jsonObject["purchaseOrdersToUreaId"]				= purchaseOrdersToUreaId;
	jsonObject["purchaseOrderId"]						= $('#purchaseOrderId').val();
	jsonObject["purchaseOrderTypeId"]					= $('#purchaseOrderTypeId').val();
	jsonObject["partTotalCost"]							= partTotalCost;
	jsonObject["quantity"]								= quantity;
	jsonObject["eachCost"]								= eachCost;
	jsonObject["tax"]									= tax;
	
	$.ajax({
		url: "deletePurchaseOrderPart",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			showMessage('success', 'Delete Urea Successfully!');
			location.reload();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
}

$(document).ready(
		function($) {
			$('button[id=sentToPurchaseOrder]').click(function(e) {
			
				var jsonObject			= new Object();
				
				jsonObject["purchaseOrderId"]				= $('#purchaseOrderId').val();
				jsonObject["purchaseOrderAdvanceCost"]		= $('#purchaseOrderAdvanceCost').val();
				jsonObject["purchaseOrderRemark"]			= $('#purchaseOrderRemark').val();
				
				if($("#VendorInputFieldInPOAddPart").val() == true || $("#VendorInputFieldInPOAddPart").val() == 'true'){
					if(!validateSentPart()){
						return false;
					}
				}
				if($("#pendingApproval").val() == true || $("#pendingApproval").val() == 'true'){
					showMessage('info','Approval Is Pending')
					return false;
				}
				if($("#approvedApproval").val() == false || $("#approvedApproval").val() == 'false'){
					showMessage('info','No Approval Found')
					return false;
				}
				
				
				if (confirm("Are you sure? Save PurchaseOrder ")) {
					$.ajax({
						url: "sentToPurchaseOrder",
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
				} else {
					location.reload();
				  }
				
				
				
			});
		});	
			
function getEditstockQuantity(a){
	var jsonObject			= new Object();

	$("#editStockName").removeClass('stock-color-red');
	$("#EditOtherStockName").removeClass('stock-color-red');
	
	jsonObject["ureaManufacturerId"]	= $("#editUreaManufacturerId").val();
	jsonObject["warehouselocation"]		= $("#shipLocationId").val();
	
	$.ajax({
		url: "getUreastockQuantity",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			var stock = data.stockDetails.quantity;
			if(stock == null ){
				$("#editStockId").val(0);
				$("#editStockName").addClass('stock-color-red');
				
			}else{
				$("#editStockName").addClass('stock-color-blue');
				$("#editStockId").val(stock.toFixed(2));
			}
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	$.ajax({
		url: "getOtherUreastockQuantity",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			var otherStock = data.stockDetails.quantity;
			if(otherStock == undefined ){
				$("#editOtherLocationStockInfo").val(0);
				$("#EditOtherStockName").addClass('stock-color-red');
			}else{
				$("#EditOtherStockName").addClass('stock-color-blue');
				$("#editOtherLocationStockInfo").val(otherStock.toFixed(2));
			}
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
	
}

function setPurchaseOrdersPartForApproval(data){
	$("#makeApprovalTable").empty();
	var ureaDetails = data.ureaPurchaseOrdersDetails;
	
	if(ureaDetails != undefined || ureaDetails != null ){
	
		for(var index = 0 ; index < ureaDetails.length; index++){
			if(ureaDetails[index].approvalPartStatusId == 1){
				if(ureaDetails[index].partid == null) {
					showMessage('info','No Record Found')
					var tr1		= $('<tr>');
					var td1		= $('<td colspan="8" >');
			
					td1.append('<h5 align="center">Purchase Order is Empty</h5>');
					tr1.append(td1);
				
					$('#ureaPurchaseOrdersDetails').append(tr1);
				      return false;
				    }
					
				var columnArray = new Array();
				
				columnArray.push('<td class="fit"><a href="#" onclick="getLocationstockQuantity('+ureaDetails[index].ureaManufacturerId+');">'+ ureaDetails[index].purchaseorder_partname +'</a></td>');
				columnArray.push('<td class="fit"><a href="#" data-toggle="tooltip" title="Each: '+ ureaDetails[index].parteachcost +' Tax: '+ ureaDetails[index].tax +' Discount: '+ ureaDetails[index].discount +'">'+ ureaDetails[index].quantity+'</a></td>');
				columnArray.push('<td class="fit">'
						+'<input type="hidden" name="approvalPurchaseOrderToPartId" id="purchaseOrderToPartId'+ureaDetails[index].partid+'" value="'+ ureaDetails[index].partid +'" > '
						+'<input type="hidden" name="approvalUreaManufacturerId" id="approvalUreaManufacturerId'+ureaDetails[index].partid+'" value="'+ ureaDetails[index].ureaManufacturerId +'" > '
						+'<input type="hidden" name="approvalEachCost" id="approvalPartEachCost'+ureaDetails[index].partid+'" value="'+ ureaDetails[index].parteachcost +'" > '
						+'<input type="hidden" name="approvalDiscount" id="approvalPartDiscount'+ureaDetails[index].partid+'" value="'+ ureaDetails[index].discount +'"> '
						+'<input type="hidden" name="approvalTax" id="approvalPartTax'+ureaDetails[index].partid+'" value="'+ ureaDetails[index].tax +'"> '
						+'<input type="text" id="approvalPartQuantity'+ureaDetails[index].partid+'" class="form-text" style="width: 90%;" name="approvalQuantity"'
						//+'value='+ ureaDetails[index].quantity+' 	onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="javascript:sumthere("approvalPartQuantity'+ureaDetails[index].partid+'", "approvalPartEachCost'+ureaDetails[index].partid+'", "approvalPartDiscount'+ureaDetails[index].partid+'", "approvalPartTax'+ureaDetails[index].partid+'", "approvalPartCost'+ureaDetails[index].partid+'");"></td>');
				+'value='+ ureaDetails[index].quantity+' 	onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="javascript:sumthere(\'approvalPartQuantity'+ ureaDetails[index].partid +'\',\'approvalPartEachCost'+ ureaDetails[index].partid +'\',\'approvalPartDiscount'+ ureaDetails[index].partid +'\',\'approvalPartTax'+ ureaDetails[index].partid +'\',\'approvalPartCost'+ ureaDetails[index].partid +'\');"></td>');
				columnArray.push('<td class="fit"><input type="text" id="approvalPartCost'+ureaDetails[index].partid+'" class="form-text" style="width: 90%;" name="approvalTotalCost"'
						+'	value="'+ureaDetails[index].totalcost +'" readonly="readonly"></td>');	
				columnArray.push('<td class="fit" style="width: 12%;"><input type="hidden" id="partVendorId'+ureaDetails[index].partid+'" name="vendorId" class="partVendorId"  onchange="createNewPurchaseOrder('+ureaDetails[index].partid+');" style="width: 100%;" required="required" placeholder="Vendor Name" /></td>');
						
				columnArray.push('<td class="fit"><div class="btn-group" id="btnGroup'+ureaDetails[index].partid+'" data-toggle="buttons">'
						+'<label class="btn btn-default btn-on btn-mg active" id="approveStatusId'+ureaDetails[index].partid+'" style="width: 20%;"> '
						+'<input type="radio" value="2"   name="status'+ureaDetails[index].partid+'"  checked="checked">Approve'
						+'</label> '
						+'<label class="btn btn-default btn-off btn-mg" id="rejectStatusId'+ureaDetails[index].partid+'" style="width: 15%;" >'
						+'<input type="radio" value="3"  name="status'+ureaDetails[index].partid+'"  > Reject'
						+'</label>'
						+'<label class="btn btn-default btn-off btn-mg" id="transferStatusId'+ureaDetails[index].partid+'" style="width: 35%;" >'
						+'	<input type="radio" value="4"  name="status'+ureaDetails[index].partid+'"  > Branch Transfer'
						+'</label>'
						+'</div></td>');			
				columnArray.push('<td class="fit"><input type="text" class="form-text" id="remark" name="remark"></td>');
				
				
				$('#makeApprovalTable').append("<tr data-object-id='' class='ng-scope' id=purchaseOrdersToUreaId'"+ureaDetails[index].partid+"' >" + columnArray.join(' ') + "</tr>");
			}
		
		}
		
		columnArray = [];
	}else{
		if(($("#makeApproval").val() == true || $("#makeApproval").val() == 'true')  ){
			showMessage('info','No Pending Approval Found')
		}
	}
	
}

function getLocationstockQuantity(ureaManufacturerId){
	var jsonObject			= new Object();
	
	$("#StockName").removeClass('stock-color-red');
	$("#otherStockName").removeClass('stock-color-red');
	
	jsonObject["ureaManufacturerId"]	= ureaManufacturerId;
	jsonObject["warehouselocation"]		= $("#shipLocationId").val();
	
	$.ajax({
		url: "getUreastockQuantity",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			var stock = data.stockDetails.quantity;
			if(stock == null ){
				$("#stockId").val(0);
				$("#approvalStockId").val(0);
				$("#stockName").addClass('stock-color-red');
				
			}else{
				$("#stockName").addClass('stock-color-blue');
				$("#stockId").val(stock);
				$("#approvalStockId").val(stock);
			}
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occred!');
		}
	});
	$.ajax({
		url: "getOtherUreastockQuantity",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			var otherStock = data.stockDetails.quantity;
			if(otherStock == undefined ){
				$("#otherLocationstock").val(0);
				$("#approvalOtherLocationstock").val(0);
				$("#otherStockName").addClass('stock-color-red');
			}else{
				$("#otherStockName").addClass('stock-color-blue');
				$("#otherLocationstock").val(otherStock);
				$("#approvalOtherLocationstock").val(otherStock);
			}
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occred!');
		}
	});
		$("#locationPartDetailsModal").modal('show');
	
}

