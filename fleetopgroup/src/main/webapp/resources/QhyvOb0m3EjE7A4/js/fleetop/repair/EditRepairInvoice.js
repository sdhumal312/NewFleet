$(document).ready(function() {

	var jsonObject					= new Object();

	jsonObject["repairStockId"]		= $("#repairStockId").val();
	jsonObject["companyId"]			= $("#companyId").val();

	showLayer();
	$.ajax({
		url: "getRepairInvoice",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setRepairInvoice(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
})
function setRepairInvoice(data){
	var repairInvoice = data.repairStockDto;
	
	$("#repairTypeId").val(repairInvoice.repairTypeId);
//	$("#transactionNumber").val(repairInvoice.repairType);
	$("#repairWorkshopId").val(repairInvoice.repairWorkshopId);
//	$("#transactionNumber").val(repairInvoice.repairWorkshop);
//	$("#transactionNumber").val(repairInvoice.locationId);
//	$("#transactionNumber").val(repairInvoice.location);
	$('#locationId').select2('data', {
		id : repairInvoice.locationId,
		text : repairInvoice.location
	});
//	$("#transactionNumber").val(repairInvoice.vendorId);
//	$("#transactionNumber").val(repairInvoice.vendorName);
	$('#vendorId').select2('data', {
		id : repairInvoice.vendorId,
		text : repairInvoice.vendorName
	});
	$("#createRepairInvoiceDate").val(repairInvoice.openDateStr);
	$("#repairStockRequiredDate").val(repairInvoice.requiredDateStr);
	$("#referenceNumber").val(repairInvoice.refNumber);
	$("#description").val(repairInvoice.description);
}

$(document).ready(function() {
	$("#vendorId").select2( {
        minimumInputLength:3, minimumResultsForSearch:10, ajax: {
            url:"getVendorSearchListInventory.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.vendorName+" - "+a.vendorLocation, slug: a.slug, id: a.vendorId
                        }
                    }
                    )
                }
            }
        }
    }), $("#locationId").select2({
        ajax: {
            url: "getWorkOrderPartlocation.in?Action=FuncionarioSelect2",
            dataType: "json",
            type: "GET",
            contentType: "application/json",
            data: function(a) {
                return {
                    term: a
                }
            },
            results: function(a) {
                return {
                    results: $.map(a, function(a) {
                        return {
                            text: a.partlocation_name,
                            slug: a.slug,
                            id: a.partlocation_id
                        }
                    })
                }
            }
        }
    })
});
      
function saveRepairStockInvoice(){
	if(!validateRepairStock()){
		return false;
	}
	var jsonObject			= new Object();
	
	jsonObject["repairStockId"] 			= $("#repairStockId").val();
	jsonObject["repairTypeId"] 				= $("#repairTypeId").val();
	jsonObject["repairWorkshopId"] 			= $("#repairWorkshopId").val();
	jsonObject["vendorId"] 					= $("#vendorId").val();
	jsonObject["createRepairInvoiceDate"] 	= $("#createRepairInvoiceDate").val();
	jsonObject["repairStockRequiredDate"] 	= $("#repairStockRequiredDate").val();
	jsonObject["referenceNumber"] 			= $("#referenceNumber").val();
	jsonObject["description"] 				= $("#description").val();
	jsonObject["companyId"] 				= $("#companyId").val();
	jsonObject["userId"] 					= $("#userId").val();
	jsonObject["locationId"] 				= $("#locationId").val();
	
	
	$.ajax({
		url: "saveRepairStockInvoice",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			console.log("data",data)
			window.location.replace("showRepairInvoice?repairStockId="+data.repairStock.repairStockId+"");
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function validateRepairStock(){
	if($("#repairTypeId").val() == "" ){
		showMessage('info','Please Enter Repair Type');
		hideLayer();
		return false;
	}
	if($("#repairWorkshopId").val() == "" ){
		showMessage('info','Please Enter Repair Workshop');
		hideLayer();
		return false;
	}
	if($("#repairWorkshopId").val() == 1 &&  $("#locationId").val() == ""){
		$("#vendorDiv").hide();
		$("#locationDiv").show();
		showMessage('info','Please Select Location');
		hideLayer();
		return false;
	}
	if($("#repairWorkshopId").val() == 2 &&  $("#vendorId").val() == ""){
		$("#vendorDiv").show();
		$("#locationDiv").hide();
		showMessage('info','Please Select Vendor');
		hideLayer();
		return false;
	}
	if($("#createRepairInvoiceDate").val() == "" ){
		showMessage('info','Please Enter Open Date');
		hideLayer();
		return false;
	}
	if($("#repairStockRequiredDate").val() == "" ){
		showMessage('info','Please Enter Required Date');
		hideLayer();
		return false;
	}
	
	return true;
}

function validateVendor(){
	if($("#repairWorkshopId").val() == 2 &&  $("#vendorId").val() == ""){
		$("#vendorDiv").show();
		$("#locationDiv").hide();
	}else{
		$("#vendorDiv").hide();
		$("#locationDiv").show();
		$("#vendorId").select2("val", "");

	}
}