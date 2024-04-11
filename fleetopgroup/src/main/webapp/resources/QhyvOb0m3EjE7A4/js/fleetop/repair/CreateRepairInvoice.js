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
    }), $("#locationId,#additionalPartLocationId").select2({
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
	jsonObject["additionalPartLocationId"] 	= $("#additionalPartLocationId").val();
	
	
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
	if($("#repairWorkshopId").val() == 1 &&  $("#locationId").val() == "" ){
		$("#vendorDiv").hide();
		$("#locationDiv").show();
		showMessage('info','Please Select Location');
		hideLayer();
		return false;
	}
	if($("#repairWorkshopId").val() == 1 &&  $("#additionalPartLocationId").val() == "" ){
		$("#additionalPartLocationDiv").show();
		showMessage('info','Please Select To Location');
		hideLayer();
		return false;
	}
	if($("#repairWorkshopId").val() == 2 &&  $("#repairTypeId").val() == 1 && $("#locationId").val() == ""){
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
		showMessage('info','Please Enter Sent Date');
		hideLayer();
		return false;
	}
	if($("#repairStockRequiredDate").val() == "" ){
		showMessage('info','Please Enter Required Date');
		hideLayer();
		return false;
	}
	
	var openDate    			= $('#createRepairInvoiceDate').val().split("-").reverse().join("-");	
	var requiredDate    		= $('#repairStockRequiredDate').val().split("-").reverse().join("-");	
	var isBeforeSentDate		= moment(requiredDate).isBefore(openDate);
	if(isBeforeSentDate){
		showMessage('info','Required Date Can Not Before Sent Date');
		hideLayer();
		return false;
	}
	
	if($("#repairWorkshopId").val() == 1 && $("#locationId").val() == $("#additionalPartLocationId").val() ){
		$("#additionalPartLocationDiv").show();
		showMessage('info','From location And Additional Part Location Can Not Be Same');
		hideLayer();
		return false;
	}
	
	
	return true;
}

function validateVendor(){
	if($("#repairWorkshopId").val() == 1){
		$("#vendorDiv").hide();
		$("#locationDiv").show();
		$("#vendorId").select2("val", "");
		$("#additionalPartLocationDiv").show();
	}else if($("#repairWorkshopId").val() == 2 && $("#repairTypeId").val() == 1 ){
		$("#vendorDiv").show();
		$("#locationDiv").show();
		$("#additionalPartLocationDiv").hide()
		$("#vendorId").select2("val", "");
	}else if($("#repairWorkshopId").val() == 2 && $("#repairTypeId").val() != 1 ){
		$("#vendorDiv").show();
		$("#locationDiv").hide();
		$("#additionalPartLocationDiv").hide()
	}
		
}