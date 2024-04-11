
function transferProcessed(approvalId,approvedQuantity,transferedQuantity,branchId,subRequisitionId,branchName,receiverId,receiverName){
	showLayer();
	$('#tRType').val(0);
	$('#quantityDivT').show();
	$('#battarySelectDiv').hide();
	$('#tyreSelectDiv').hide();
	$('#ureaManDiv').hide();
	var jsonObject = new Object();
	jsonObject['subRequisitionId'] = subRequisitionId;
	jsonObject['companyId'] = $('#companyId').val();
	jsonObject['location'] = branchId;
	$.ajax({
		url : 'getSubRequisitionBySubRequisitionId',
		type : 'POST',
		dataType : 'json',
		data : jsonObject,
		success : function(data){
			let stock =0;
			if(data.stockQty != undefined)
				stock =data.stockQty
			$('#transferBranchQtySamp').text(branchName+" - Stock Quantity : "+stock);
			$('#branchStockTransfer').val(stock);
			$('#transferLocation').val(branchId);
			$('#approvalIdTransfer').val(approvalId);
			$('#totalQuantitySampTrn').text(approvedQuantity);
			$('#alreadtTransferedSampTrn').text(transferedQuantity);
			let pendingTransfer = (Number)(approvedQuantity)-(Number)(transferedQuantity);
			$('#pendingTransferedSampTrn').text(pendingTransfer.toFixed(2));
			if(stock>pendingTransfer)
			$('#transferQty').val(pendingTransfer);
			else
			$('#transferQty').val(stock);
			
			if(data.subRequisition != undefined){
				hideLayer();
				var subRequisition = data.subRequisition;
				if(subRequisition.requisitionType == PART_RERUISITION){
					$('#transferPartId').val(subRequisition.partId);	
					$('#transferDetailsSamp').text("Part Name : "+subRequisition.partName);	
				}else if (subRequisition.requisitionType == TYRE_RERUISITION){
					$('#transferPartId').val(subRequisition.partId);	
					//$('#tManufacturer').val(subRequisition.tyreManufacturer);	
					$('#tModel').val(subRequisition.tyreModel);	
					//$('#tSize').val(subRequisition.tyreSize);
					$('#tRType').val(TYRE_RERUISITION);
					prepareTransfer(TYRE_RERUISITION);
					setTimeout(() => {
						$("#tyreId").select2('val', '');
					}, 100);
					$('#transferDetailsSamp').text("Tyre Model : "+subRequisition.tyreModelName);
				}else if (subRequisition.requisitionType == BATTARY_RERUISITION){
					$('#tSize').val(subRequisition.batteryCapacity);
					$('#tRType').val(BATTARY_RERUISITION);
					$('#transferDetailsSamp').text("Battery Capacity : "+subRequisition.batteryCapacityName);	
					prepareTransfer(BATTARY_RERUISITION);
					
					setTimeout(() => {
						$("#batteryId").select2('val', '');
					}, 100);
				}else if (subRequisition.requisitionType == CLOTH_RERUISITION){
					$('#transferPartId').val(subRequisition.clothId);
					$('#transferDetailsSamp').text("Upholstrory Name : "+subRequisition.clothName);	
				}else if (subRequisition.requisitionType == UREA_RERUISITION){
					//$('#transferPartId').val(subRequisition.ureaId);
					//$('#transferDetailsSamp').text("Urea Name : "+subRequisition.ureaName);
					$('#ureaManDiv').show();
					$('#tRType').val(UREA_RERUISITION);
					$("#tUreaManufacturer").select2({
							minimumInputLength: 0,
					        minimumResultsForSearch: 10,
					        multiple: 0,
					        ajax: {
					            url: "getStockWiseBranchList",
					            dataType: "json",
					            type: "POST",
					            contentType: "application/json",
					            quietMillis: 50,
					            data: function() {
					                return {
					                    subRequisitionId: subRequisitionId,
					                    transferLocation: $('#transferLocation').val(),
					                    getManufacturerWise : true
					                }
					            },
					            results: function(a) {
					            	a =a.locationWisePartQuantity
					                return {
					                    results: $.map(a, function(a) {
					                        return {
					                            text: a.partname+" - Stock-"+a.quantity,
					                            slug: a.quantity,
					                            id: a.partid
					                        }
					                    })
					                }
					            }
					        }
				   });
				}
			}
			hideLayer();
		},
		error : function(){
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');	
		}
		
	})
	$('#transferModal').show();
	
	prepareFields(receiverName,receiverId);
}

function prepareFields(receiverName,receiverId){
	
$("#assignToRec").select2({
    minimumInputLength: 0,
    minimumResultsForSearch: 10,
    multiple: 0,
    ajax: {
        url: "getAllUserListByCompanyId",
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
                        text: a.firstName + " " + a.lastName,
                        slug: a.slug,
                        id: a.user_id
                    }
                })
            }
        }
    }
});
if (Number(receiverId) > 0) {
		$('#assignToRec').select2('data', { id: receiverId, text: receiverName });
		$("#assignToRec").select2("readonly",true); 
} else {
		$('#assignToRec').select2('data', { id: 0, text: '' });
		$("#assignToRec").select2("readonly",false);
}
}

function validateTransferQuantity(){
	let qty =Number($('#transferQty').val());	
	let pendingQuantity =Number($('#pendingTransferedSampTrn').text());
	if(qty > pendingQuantity){
		showMessage('info', 'Quantity Can not be Greater than pending Quantity !!');
			hideLayer();
			return false;
	}
}

function saveTransferQuantity(){
	showLayer();
	let approvedQty =Number($('#totalQuantitySampTrn').text());
	let qty =Number($('#transferQty').val());
	
	if($('#tRType').val() == UREA_RERUISITION){
		if($('#tUreaManufacturer').val() <= 0){
			showMessage('info', 'Please Select Manufacturer to process ');
			hideLayer();
			return false;
		}
	}
	if($('#tRType').val() == BATTARY_RERUISITION){
		var arrayCount = 0;
		if($('#batteryId').val() != '')
			arrayCount = $('#batteryId').val().split(',').length;
		
		qty = arrayCount;
	}
	if($('#tRType').val() == TYRE_RERUISITION){
		var arrayCount = 0;
		if($('#tyreId').val() != '')
			arrayCount = $('#tyreId').val().split(',').length;
		
		qty = arrayCount;
	}
	
	if(qty <= 0){
		showMessage('info', 'Please Enter Quantity to process ');
		hideLayer();
		return false;
	}

	if(qty > approvedQty){
		showMessage('info', 'Transfer Quantity can not be greater than approved Quantity '+approvedQty+'!!! ');
		hideLayer();
		return false;
	}
	if(qty > Number($('#branchStockTransfer').val())){
		showMessage('info', 'Transfer Quantity can not be greater than Branch Stock ');
		hideLayer();
		return false;
	}
	if($('#assignToRec').val() <=0){
		showMessage('info', 'Please Select Receiver to process');
		hideLayer();
		return false;
	}
	
	
	showLayer();
	var jsonObject = new Object();
	jsonObject['approvalId'] 			= $('#approvalIdTransfer').val();
	jsonObject['transferQty'] 			= qty;
	jsonObject['locationId'] 			= $('#transferLocation').val();
	jsonObject['receiver'] 				= $('#assignToRec').val();
	jsonObject['batteryId'] 			= $('#batteryId').val().toString();
	jsonObject['tyreId']	 			= $('#tyreId').val().toString();
	jsonObject['tUreaManufacturer']	 	= $('#tUreaManufacturer').val();
	$.ajax({
		url : 'saveTransferQuantity',
		type : 'POST',
		dataType : 'json',
		data : jsonObject,
		success : function(data){
			hideLayer();
			if(data.qntyValidateFail == true || data.qntyValidateFail === 'true'){
				showMessage('info', 'Transfer Quantity can not be greater than approved Quantity !!! ');
			}else if (data.stockValidateFail == true || data.stockValidateFail === 'true'){
				showMessage('info', 'Branch do not have enough stock !!!');
			}else if(data.invalidQty  != undefined && (data.invalidQty == true || data.invalidQty == 'true')){
				showMessage('info', 'Invalid Stock Quantity ');
			}else if(data.authFail == true || data.authFail == 'true'){
				showMessage('info', 'Unauthorised User !!! ');
			} 
			if(data.completeTransfer== true || data.completeTransfer === 'true'){
				showMessage('Success', 'Approval transfered Successfully ');
				hideLayer();
				setTimeout(() => {
					window.location.reload();
				}, 300);

			}
		},
		error : function(e){
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');	
		}
		
	})
}
function preparePo(vendorTypeId){
	
		var jsonObject		= new Object();
		var purchaseType	= "part";
		
		switch (vendorTypeId) {
		  case 1:
			  purchaseType = "part";
		    break;
		  case 2:
			  purchaseType = "tyre";
		    break;
		  case 3:
			  purchaseType = "battery";
		    break;
		  case 4:
			  purchaseType = "upholstery";
		    break;
		  case 5:
			  purchaseType = "urea";
		    break;  
		}
		jsonObject["purchaseType"]				= purchaseType;
		$.ajax({
			url: "vendorTypeByName",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				prepareSelect2PO(data);
				hideLayer();
			},
			error: function (e) {
				hideLayer();
				showMessage('errors', 'Some Error Occurred!');
			}
		});
}

function prepareSelect2PO(data){
	var list = data.htData.vendorTypeList;
	var vendorTypeId 	= "";
	for(var i=0; i<list.length; i++){
		vendorTypeId = vendorTypeId+","+list[i].vendor_Typeid;
	}
	 vendorTypeId = vendorTypeId.replace(/^,/, '');
	 $("#selectVendor").select2({
	        minimumInputLength: 3,
	        minimumResultsForSearch: 10,
	        ajax: {
	            url: "getVendorListByVendorType.in",
	            dataType: "json",
	            type: "POST",
	            contentType: "application/json",
	            quietMillis: 50,
	            data: function(e) {
	                return {
	                    term: e,
	                    text: vendorTypeId
	                }
	            },
	            results: function(e) {
	                return {
	                    results: $.map(e, function(e) {
	                        return {
	                            text: e.vendorName + " - " + e.vendorLocation,
	                            slug: e.slug,
	                            id: e.vendorId
	                        }
	                    })
	                }
	            }
	        }
	    });
	 $('#terms').select2();
	 $('#shipvia').select2();
	 $('#poModal').show();
	 $("#selectVendor").select2('data',{id:'',text:''});
}

function savePO(){
	showLayer();
	
	if($('#selectVendor').val() <= 0){
		showMessage('info', 'Please Select Vendor to Process');
		return false;
	}
	if($('#terms').val() <= 0){
		showMessage('info', 'Please Select Term to Process');
		return false;
	}
	
	var jsonObject = new Object();
	jsonObject['approvalId'] 	=$('#approvalIdPo').val();
	jsonObject['companyId'] 	=$('#companyId').val();
	jsonObject['selectVendor'] 	=$('#selectVendor').val();
	jsonObject['terms'] 		=$('#terms').val();
	jsonObject['shipvia'] 		=$('#shipvia').val();
	$.ajax({
		url: "createPOFromRequisition",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.success == true || data.success === 'true' ){
				window.location.href="PurchaseOrders_Parts.in?ID="+data.poId+"&subRequisitionId="+data.subRequisitionId+"&fromTransfer="+1+"&approvalQuantity="+$('#poApprovalQty').val(); 
				hideLayer();
			}
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	})
	
}

function receiveTransfer(approvalId,quantity,subRequisitionId,permissionBoolean,partialBoolean,receivedQty,ureaName){
	showLayer();
	$('#totalTransferedQuantitySamp').text(quantity);
	$('#receiveQtyDiv').show();
	$('#receiveTableBox').hide();
	$('#partialTableBox').hide();
	 $('#receiveRemark').val('');
	 $('#RRType').val(0);
	var qty = quantity;
	if(partialBoolean){
		qty = quantity-receivedQty;
		if(	qty <= 0){
			$('#partiallyReceiveDiv').hide();
		}else{
			if(permissionBoolean){
				$('#partiallyReceiveDiv').show();
			}else{
				$('#partiallyReceiveDiv').hide();
			}
		}
		$('#totalreceivedQuantitySamp').text(receivedQty);
		$('#totalPendingQuantitySamp').text(qty.toFixed(2));
		$('#receivedQuantityH5').show();
		$('#pendingQuantityH5').show();
		$('#fromPartial').val(true);
		$('#maxReceiveAllow').val(qty);
	}else{
		if(permissionBoolean){
			$('#partiallyReceiveDiv').show();
		}else{
			$('#partiallyReceiveDiv').hide();
		}
		$('#receivedQuantityH5').hide();
		$('#pendingQuantityH5').hide();
		$('#fromPartial').val(false);
	}
	
	$('#receiveQty').val(qty);
	$('#approvalIdReceive').val(approvalId);
	var jsonObject = new Object();
	jsonObject['subRequisitionId'] 		= subRequisitionId;
	jsonObject['approvalId'] 			= approvalId;
	jsonObject['companyId'] 			= $('#companyId').val();
	jsonObject['dontGetStock'] 			= true;
	jsonObject['partialBoolean'] 		= partialBoolean;
	jsonObject['fromReceive'] 			= true;
	$.ajax({
		url : 'getSubRequisitionBySubRequisitionId',
		type : 'POST',
		dataType : 'json',
		data : jsonObject,
		success : function(data){
			if(data.subRequisition != undefined){
				hideLayer();
				var subRequisition = data.subRequisition;
				if(subRequisition.requisitionType == PART_RERUISITION){
					$('#receiveDetailsSamp').text("Part Name : "+subRequisition.partName);	
				}else if (subRequisition.requisitionType == TYRE_RERUISITION){
					$('#RRType').val(TYRE_RERUISITION);
					setBatteryTyreReceiveList(data);
					$('#receiveDetailsSamp').text("Tyre Model : "+subRequisition.tyreModelName);
				}else if (subRequisition.requisitionType == BATTARY_RERUISITION){
					$('#RRType').val(BATTARY_RERUISITION);
					setBatteryTyreReceiveList(data);
					$('#receiveDetailsSamp').text("Battery Capacity : "+subRequisition.batteryCapacityName);	
				}else if (subRequisition.requisitionType == CLOTH_RERUISITION){
					$('#receiveDetailsSamp').text("Upholstrory Name : "+subRequisition.clothName);	
				}else if (subRequisition.requisitionType == UREA_RERUISITION){
					$('#receiveDetailsSamp').text("Urea : ");
				}
				if(partialBoolean && data.partiallyReceivedList != undefined  && data.partiallyReceivedList.length > 0)
					setPartiallyReceived(data.partiallyReceivedList);
			}
			hideLayer();
		},
		error : function(e){
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');	
		}
	})
	$('#receiveModal').show();	
}


function saveReceiveWithQuantity(){
	showLayer();
	var idsArray = new Array();
	var quantity = $('#receiveQty').val();
	if($('#RRType').val() == BATTARY_RERUISITION || $('#RRType').val() == TYRE_RERUISITION){
		quantity = 0;
		$("input:checkbox[name=ids]:checked").each(function(){
			idsArray.push($(this).val());
		});
		quantity =idsArray.length;
	}
	
	if(!validateReceiveQuantity(quantity)){
		hideLayer();
		return false;
	}
	if(quantity <=0 ){
		showMessage('info','Qunatity should be grated Than 0'); 
		hideLayer();
		return false;
	}
//	if($('#receiveRemark').val() == '' || ($('#receiveRemark').val()).trim() ==''){
//		showMessage('info', 'Please Enter Reamrk To process !! ');
//		hideLayer();
//		return false;
//	}
	var jsonObject = new Object();
	jsonObject['approvalId'] 	= $('#approvalIdReceive').val();
	jsonObject['receiveQty'] 	= quantity;
	jsonObject['receiveRemark'] = $('#receiveRemark').val();
	jsonObject['companyId'] 	= $('#companyId').val();
	jsonObject['fromPartial'] 	= $('#fromPartial').val();
	jsonObject['ids'] 			= idsArray.toString();
	
	
	$.ajax({
		url: "receiveTransfer",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.success == true || data.success === 'true' ){
				showMessage('success', 'Approval Received Successsfully !!! ');
				window.location.reload();
			}else if(data.authFail == true || data.authFail == 'true'){
				showMessage('info', 'Unauthorised User !!! ');
			}else if(data.qtyFail == true || data.qtyFail == 'true'){
				showMessage('info', 'Invalid Quantity !!! ');
			}
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	})	
}
function rejectReceive(approvalId){
	$('#rejectModal').show();
	$('#rejectRemark').val("");
	$('#reqIdForReject').val(approvalId);
	$('#rejectSubReqButtton').hide();
	$('#rejectReceiveButtton').show();
	$('#rejectAppButtton').hide();
}

function rejectApprovalReceive(){
	var jsonObject = new Object();
	showLayer();
	
	if($('#rejectRemark').val() == undefined || ($('#rejectRemark').val()).trim() == ''){
		showMessage('info','Please Enter Remark');
		hideLayer();
		return false;
	}
	jsonObject['approvalId'] =$('#reqIdForReject').val();
	jsonObject['rejectRemark'] =$('#rejectRemark').val();
	jsonObject['companyId'] =$('#companyId').val();
	$.ajax({
		url: "rejectReceive",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			if(data.success == true || data.success === 'true'){
				showMessage('success', ' Receival Rejected Successsfully !!! ');
				window.location.reload();
			}else if(data.authFail == true || data.authFail == 'true'){
				showMessage('info', 'Unauthorised User !!! ');
			}
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	})	
}

function setPartiallyReceived(data){
	$('#partialTable').empty();
	var thead = $('<thead>');
	var tr1 = $('<tr>');
	
	var th1 = 	$('<th>');
	var th2 =	$('<th>');
	var th3 =	$('<th>');
	
	tr1.append(th1.append("NO."));
	tr1.append(th2.append("Received Quantity"));
	tr1.append(th3.append("Remark"));
	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data != undefined  && data.length > 0) {
		var list= data;
	for(var i = 0; i < list.length; i++) {
		var tr1		= $('<tr class="ng-scope">');
		var td1		= $('<td>');
		var td2		= $('<td>');
		var td3		= $('<td>');
		tr1.append(td1.append(i+1));
		tr1.append(td2.append(list[i].receivedQuantity))
		tr1.append(td3.append(list[i].remark))
		tbody.append(tr1);
	}
	}else{
		showMessage('info','No record found !')
	}
	$('#partialTable').append(thead);
	$('#partialTable').append(tbody);
	
	$('#partialTableBox').show();
	
}
function validateReceiveQty(id){
	let quantity = $('#'+id).val();
	let maxReceiveAllow = 0;
	
	if($('#fromPartial').val() =='true'  || $('#fromPartial').val() == true){
		maxReceiveAllow = $('#maxReceiveAllow').val();
	}else{
		maxReceiveAllow = Number($('#totalTransferedQuantitySamp').text());
	}
	if(quantity > maxReceiveAllow){
		showMessage('info','can not enter more than pending quantity ');
		$('#receiveQty').val(maxReceiveAllow);
		return false;
	}
	return true;
}
function markAsComplete(id){
	if(confirm("Remaining of partially Received will be returned, Sure you want to complete this Requisition ?")){
		showLayer();
		var jsonObject = new Object();
		jsonObject['requisitionId'] 	=id;
		jsonObject['companyId'] 		=$('#companyId').val();
		$.ajax({
			url: "markRequisitionAsComplete",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				if(data.success == true || data.success === 'true' ){
					showMessage('success', 'Marked As Complete !!! ');
						setTimeout(()=>(
					window.location.reload(),
					hideLayer()
				),500);
				}else if(data.inCompleteTask == true || data.success === 'true' ){
					showMessage('info', ' Action requisre On All approvals to complete Requisition !!! ');
					hideLayer();
				}
					setTimeout(()=>(
					window.location.reload(),
					hideLayer()
				),500);
			},
			error: function (e) {
				hideLayer();
				showMessage('errors', 'Some Error Occurred!');
			}
		})
	}
}
function prepareTransfer(type){
	$('#quantityDivT').hide();
	if(type == BATTARY_RERUISITION){
	$("#batteryId").select2({
		minimumInputLength : 1, 
		minimumResultsForSearch : 10,
		multiple:!0,
		ajax : {
			url : "getBatteryListTransfer",
			dataType : "json",
			type : "POST",
			contentType : "application/json",
			quietMillis : 50,
			data : function(e) {
				return {
					term : e,
					fromLocation : $("#transferLocation").val(),
					companyId :$('#companyId').val(),
					size :$('#tSize').val()
				}
			},
			results : function(a) {
				if(a.data != undefined)
					a=a.data
				return {
					results : $.map(a,function(a) {
						return {
							text : a.batterySerialNumber+" -"+a.manufacturerName+" -"+a.batteryType,
							slug : a.slug,
							id : a.batteryId
						}
					})
				}
			}
		}
	});
	$('#battarySelectDiv').show();
	}
	
	if(type == TYRE_RERUISITION){
		$("#tyreId").select2({
			minimumInputLength : 1, 
			minimumResultsForSearch : 10,
			multiple:!0,
			ajax : {
				url : "getTyreIDsForTransfer.in",
				dataType : "json",
				type : "POST",
				contentType : "application/json",
				quietMillis : 50,
				data : function(e) {
					return {
						term : e,
						fromLocation : $("#transferLocation").val(),
						companyId :$('#companyId').val(),
//						manufacturer :$('#tManufacturer').val(),
						model :$('#tModel').val(),
//						size :$('#tSize').val()
					}
				},
				results : function(e) {
					return {
						results : $.map(e, function(e) {
							return {
								text : e.TYRE_NUMBER,
								slug : e.slug,
								id : e.TYRE_ID
							}
						})
					}
				}
			}
		});
		$('#tyreSelectDiv').show();
	}
}
function setBatteryTyreReceiveList(data){
	var type = data.type;
		data =data.list
	$('#receiveQtyDiv').hide();
	$('#receiveTable').empty();
	var thead = $('<thead>');
	var tr1 = $('<tr>');
	
	var th1 = 	$('<th>');
	var th2 =	$('<th>');
	var th3 =	$('<th>');
	
	tr1.append(th1.append(" "));
	tr1.append(th2.append("Number"));
	tr1.append(th3.append("select"));
	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data != undefined  && data.length > 0) {
		$('#receiveTableBox').show();
		var list= data;
	for(var i = 0; i < list.length; i++) {
		var tr1		= $('<tr class="ng-scope">');
		var td1		= $('<td>');
		var td2		= $('<td>');
		var td3		= $('<td>');
		tr1.append(td1.append(i+1));
		
		let number = 0;
		let id = 0;
		if(type == BATTARY_RERUISITION){
			number = list[i].batterySerialNumber;
			id = list[i].batteryId;
		}else if (type == TYRE_RERUISITION){
			number = list[i].tyre_NUMBER;
			id = list[i].tyre_ID;
		}
		tr1.append(td2.append(number))
		tr1.append(td3.append('<input type="checkbox" name="ids" id="'+id+'" value="'+id+'" />'))
		tbody.append(tr1);
	}
	$('#receiveTable').append(thead);
	$('#receiveTable').append(tbody);
	}

}

function validateReceiveQuantity(quantity){
	let maxReceiveAllow = 0;
	
	if($('#fromPartial').val() =='true'  || $('#fromPartial').val() == true){
		maxReceiveAllow = $('#maxReceiveAllow').val();
	}else{
		maxReceiveAllow = Number($('#totalTransferedQuantitySamp').text());
	}
	if(quantity > maxReceiveAllow){
		showMessage('info','can not enter more than pending quantity ');
		$('#receiveQty').val(maxReceiveAllow);
		return false;
	}
	return true;
}

function setBranchAvailableQty(){
	var data = $('#tUreaManufacturer').select2('data');
	if(data != undefined && data.id > 0){
		$('#branchStockTransfer').val(data.slug);
	}else{
		$('#branchStockTransfer').val(0);
	}
}

function receiveAllParts(requisitionId) {
	if (confirm("Only Approvals Assigned to you will be Received ")) {
		showLayer();
		var jsonObject = new Object();
		jsonObject['requisitionId'] = requisitionId;
		jsonObject['companyId'] 		=$('#companyId').val();
		$.ajax({
			url: 'receiveAllPartApprovals',
			type: 'POST',
			dataType: 'json',
			data: jsonObject,
			success: function(data) {
				if (data.msg != undefined) {
					showMessage('success', data.msg);
					setTimeout(() => {
						window.location.reload();
					}, 800);
				}else
					hideLayer();
			},
			error: function(e) {
				console.log("error : ", e);
				hideLayer();
				showMessage('errors', 'Some Error Occurred!');
			}
		});
	}
}