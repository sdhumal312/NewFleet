var validateLabourDetails 	= true;
var validatePartDetails 	= true;
$(document).ready(function() {
	$(".collapse:not(#collapse2)").collapse("show");
	var jsonObject					= new Object();

	jsonObject["dealerServiceEntriesId"]	= $("#dealerServiceEntriesId").val();
	jsonObject["companyId"]					= $("#companyId").val();
	jsonObject["tyreAssginFromDSEConfig"]	= $("#tyreAssginFromDSEConfig").val();

	showLayer();
	$.ajax({
		url: "getDealerServiceEntriesWithPartAndLabourDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setDealerServiceEntries(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
});

function setDealerServiceEntries(data){
	
	var dealerServiceEntries 			= data.dealerServiceEntries;
	var dealerServiceEntriesPart 		= data.dealerServiceEntriesPart;
	var dealerServiceEntriesLabour 		= data.dealerServiceEntriesLabour;
	var dealerServiceEntriesTyre 		= data.vehicleTyreLayoutPositionList;
	
	if(dealerServiceEntriesPart == undefined && (dealerServiceEntries.statusId == 1 || dealerServiceEntries.statusId == 2)){
		$("#addPartButtonOnTop").removeClass('hide');
	}
	if(dealerServiceEntriesLabour == undefined && (dealerServiceEntries.statusId == 1 || dealerServiceEntries.statusId == 2)){
		$("#addLabourButtonOnTop").removeClass('hide');
	}
	if(dealerServiceEntriesTyre == undefined && (dealerServiceEntries.statusId == 1 || dealerServiceEntries.statusId == 2)){
		$("#tyreAssignButtonOnTop").removeClass('hide');
	}
	
	if(dealerServiceEntries.statusId == 3 || dealerServiceEntries.statusId == 4){
		$("#completeDSEId").hide();
		$("#addLabourButton").hide();
		$("#addPartButton").hide();
		$("#addPartButtonM").hide();
		//$("#partAction").hide();
		$("#labourAction").hide();
		$("#partNotApplicableId").hide();
		$("#partApplicableLabel").hide();
		$("#labourNotCheck").hide();
		$("#addExtraIssueButton").hide();
		$("#onHold").hide();
		$("#inProcess").hide();
		$("#editButton").hide();
		
	}else if(dealerServiceEntries.statusId == 1 ){
		$("#labourHeader").hide();
		$("#partHeader").hide();
		$("#partApplicableStatusRow").hide();
		$("#labourApplicableStatusRow").hide();
		$("#inProcess").hide();
		$("#reOpenDse").hide();
		
	}else if(dealerServiceEntries.statusId == 2 ){
		$("#labourHeader").hide();
		$("#partHeader").hide();
		$("#partApplicableStatusRow").hide();
		$("#labourApplicableStatusRow").hide();
		$("#onHold").hide();
		$("#reOpenDse").hide();
	}
	if(dealerServiceEntries.dealerServiceDocumentId  != undefined  && dealerServiceEntries.dealerServiceDocumentId > 0){
		$("#uploadDocumentButton").hide();
	}else{
		$("#downloadDocumentButton").hide();
	}
	
	setDealerServiceEntriesBasicDetails(data);
	setDealerServiceEntriesPart(data);
	setDealerServiceEntriesLabour(data);
	setDealerServiceEntriesTyre(data);
	getScheduleDataView();
	getRemarks();
	getDseExtraIssue();

}

function setDealerServiceEntriesBasicDetails(data){
	var dealerServiceEntries 			= data.dealerServiceEntries;
	
	$("#dealerSENumber").html(dealerServiceEntries.dealerServiceEntriesNumberStr);
	$("#vendorName").html("<a href='ShowVendor?vendorId="+dealerServiceEntries.vendorId+"' target='_blank'>"+dealerServiceEntries.vendorName+"</a>");
	
	var vendorAddress = dealerServiceEntries.vendorAddress;
	if(vendorAddress != "null" && vendorAddress != null ){
		vendorAddress = vendorAddress.substring(0, 50) + "</br>" + vendorAddress.substring(50,100) + "</br>" + vendorAddress.substring(100);
		$("#vendorAddress").html(vendorAddress);
	}else{
		$("#vendorAddress").html('');
		
	}
	
	
	if(dealerServiceEntries.paidDateStr != undefined && dealerServiceEntries.paidDateStr != ""){
		$("#paidDate").html("<a href='#' >"+dealerServiceEntries.paidDateStr+"</a>");
		$("#paymentTypeSpan").html("<a href='#' >"+dealerServiceEntries.paymentType+" - PAID"+"</a>");
		$("#paidDateRow").show();
	}else{
		$("#paymentTypeSpan").html("<a href='#' >"+dealerServiceEntries.paymentType+" - NOT PAID"+"</a>");
		$("#paidDateRow").hide();
	}
	
	$("#invoiceNumber").html("<a href='#' >"+dealerServiceEntries.invoiceNumber+"</a>");
	$("#invoiceDate").html("<a href='#' >"+dealerServiceEntries.invoiceDateStr+"</a>");
	if(dealerServiceEntries.totalInvoiceCost != null ){
		$("#invoiceAmount").html("<a href='#' >"+(dealerServiceEntries.totalInvoiceCost).toFixed(2)+"</a>");
		$("#totalDealerServiceCost").html(addCommas((dealerServiceEntries.totalInvoiceCost).toFixed(2)));
		$("#invoiceCost").val((dealerServiceEntries.totalInvoiceCost).toFixed(2));
	}
	
	if(dealerServiceEntries.statusId == 1){
		if(dealerServiceEntries.invoiceNumber != ""  && dealerServiceEntries.invoiceDateStr != "" ){
			$("#status").html("<a href='#' >"+dealerServiceEntries.status+"-INVOICE RECEIVED</a>");
		}else{
			$("#status").html("<a href='#' >"+dealerServiceEntries.status+"-INVOICE PENDING</a>");
		}
	}else{
		$("#status").html("<a href='#' >"+dealerServiceEntries.status+"</a>");
	}
	
	$("#vehicleNumber").html("<a href='showVehicle?vid="+dealerServiceEntries.vid+"' target='_blank'>"+dealerServiceEntries.vehicleNumber+"</a>");
	$("#vehicleOdometer").html("<a href='#' target='_blank'>"+dealerServiceEntries.vehicleOdometer+"</a>"); 
	$("#vehicleChasisNumber").html("<a href='#' >"+dealerServiceEntries.vehicleChasisNumber+"</a>");
	$("#vehicleEngineNumber").html("<a href='#' >"+dealerServiceEntries.vehicleEngineNumber+"</a>");
	if(dealerServiceEntries.driverId != undefined){
		$("#driverFullName").html("<a href='showDriver?driver_id="+dealerServiceEntries.driverId+"' target='_blank'>"+dealerServiceEntries.driverFullName+"</a>");
		$('#showDriverId').val(dealerServiceEntries.driverId);
		$('#showDriverName').val(dealerServiceEntries.driverFullName);
	}
	$("#issueNumber").html(data.issueNumbers);
	/*if(dealerServiceEntries.issueId > 0){
		$("#issueRow").show();
		$("#issueNumber").html("<a href='showIssues?Id="+dealerServiceEntries.issueEncryptId+"' target='_blank'>"+dealerServiceEntries.issueNumberStr+"</a>");
		$("#issueId").val(dealerServiceEntries.issueId);
	}else{
		$("#issueRow").hide();
	}*/
	$("#dsePartStatus").html(dealerServiceEntries.dsePartStatus);
	$("#dseLabourStatus").html(dealerServiceEntries.labourStatus);
	
	$("#showAssignToId").val(dealerServiceEntries.assignToId);
	$("#showAssignTo").val(dealerServiceEntries.assignTo);
	$("#assignTo").html("<a href='#' >"+dealerServiceEntries.assignTo+"</a>");
	
	$("#createdBy").html("<a href='#' >"+dealerServiceEntries.createdBy+"</a>");
	$("#createdDate").html("<a href='#' >"+dealerServiceEntries.creationDate+"</a>");
	$("#lastUpdatedBy").html("<a href='#' >"+dealerServiceEntries.lastModifiedBy+"</a>");
	$("#lastUpdatedDate").html("<a href='#' >"+dealerServiceEntries.lastUpdatedDate+"</a>");
	
	$('#modulePaymentTypeId').val(dealerServiceEntries.paymentTypeId);
	$('#moduleIdentifier').val(8);
	
	$("#statusId").val(dealerServiceEntries.statusId);
	$("#showVendorId").val(dealerServiceEntries.vendorId);
	$("#paymentTypeId").val(dealerServiceEntries.paymentTypeId);
	$("#modalInvoiceNumber").val(dealerServiceEntries.invoiceNumber);
	$("#modalInvoiceDate").val(dealerServiceEntries.invoiceDateStr);
	$("#invoiceNumberVal").val(dealerServiceEntries.invoiceNumber);
	$("#invoiceDateVal").val(dealerServiceEntries.invoiceDateStr);
	$("#remarkVal").val(dealerServiceEntries.remark);
	$("#vid").val(dealerServiceEntries.vid);
	$("#vehicleOdometerVal").val(dealerServiceEntries.vehicleOdometer); 
	$("#dealerServiceDocumentId").val(dealerServiceEntries.dealerServiceDocumentId);
	$("#serviceReminderIds").val(dealerServiceEntries.serviceReminderIds);
	$("#meterNotWorking").text(dealerServiceEntries.meterNotWorking);
	
}

function setDealerServiceEntriesPart(data){
	var dealerServiceEntries 		= data.dealerServiceEntries;
	var dealerServiceEntriesPart 	= data.dealerServiceEntriesPart;
	$("#dealerServiceEntriesPartTable").empty();
	
	var partSubTotalCost 	= 0;
	var partDiscountCost 	= 0;
	var partTaxCost 		= 0;
	var partTotalCost 		= 0;
	
	if(dealerServiceEntriesPart != undefined || dealerServiceEntriesPart != null){
		$("#partApplicableId").prop('checked',true);
		$("#finalPartDiscountTaxTypId").val(dealerServiceEntriesPart[0].partDiscountTaxTypeId);
		$("#partDiscountTaxButton").addClass('hide');
		if($("#finalPartDiscountTaxTypId").val() == 2){
			$('.partPercent').addClass('fa-inr');
			$('.partPercent').removeClass('fa-percent');
		}else{
			$('.partPercent').removeClass('fa-inr');
			$('.partPercent').addClass('fa-percent');
		}
		for(var index = 0 ; index < dealerServiceEntriesPart.length; index++){
			
			var columnArray = new Array();
			columnArray.push("<td class='fit' style='font-size: 15px;'>"+(index+1)+"</td>");
			columnArray.push("<td class='fit' style='text-align: left; font-size: 15px;'><a style='color:black' title= 'Part Last Occurred ON "+dealerServiceEntriesPart[index].invoiceDateStr+" Odometer: "+dealerServiceEntriesPart[index].vehicleOdometer+"   IN DSE-"+dealerServiceEntriesPart[index].lastOccurredDseNumber +"'>"+ dealerServiceEntriesPart[index].partNumber  +"-  "+ dealerServiceEntriesPart[index].partName  +"</a></td>");
			columnArray.push("<td class='fit' style='text-align: right; font-size: 15px;'>"+ dealerServiceEntriesPart[index].partQuantity.toFixed(2)  +"</td>");
			columnArray.push("<td class='fit' style='text-align: right; font-size: 15px;'>"+ dealerServiceEntriesPart[index].partEchCost.toFixed(2)  +"</td>");
			if(dealerServiceEntriesPart[index].partDiscountTaxTypeId == 1){
				columnArray.push("<td class='fit' style='text-align: right; font-size: 15px;'><a style='color:black' title= 'Discount Amount: "+dealerServiceEntriesPart[index].partDiscountCost+" '>"+ dealerServiceEntriesPart[index].partDiscount  +"%</td>");
				columnArray.push("<td class='fit' style='text-align: right; font-size: 15px;'><a style='color:black' title= 'Tax Amount: "+dealerServiceEntriesPart[index].partTaxCost+" '>"+ dealerServiceEntriesPart[index].partTax  +" %</td>");
			}else{
				columnArray.push("<td class='fit' style='text-align: right; font-size: 15px;'>₹"+ dealerServiceEntriesPart[index].partDiscount  +"</td>");
				columnArray.push("<td class='fit' style='text-align: right; font-size: 15px;'>₹"+ dealerServiceEntriesPart[index].partTax  +" </td>");
			}
			columnArray.push("<td class='fit' style='text-align: right; font-size: 15px;'>"+ addCommas((dealerServiceEntriesPart[index].partTotalCost).toFixed(2))  +"</td>");
			
			if((dealerServiceEntries.statusId == 1 || dealerServiceEntries.statusId == 2 )){
				if($("#editDealerServicePart").val() == true || $("#editDealerServicePart").val()  == 'true'){
					if(dealerServiceEntriesPart[index].warrantyApplicable == true ){
						columnArray.push("<td class='fit' style='vertical-align: middle; ' ><button type='button' class='btn btn-info btn-sm' data-toggle='modal' data-target='#editPartDetails' data-whatever='@mdo'  onclick='editDealerServiceEntriesPart("+dealerServiceEntriesPart[index].dealerServiceEntriesPartId+","+dealerServiceEntriesPart[index].partId+",\""+dealerServiceEntriesPart[index].partName+"\","+dealerServiceEntriesPart[index].partQuantity+","+dealerServiceEntriesPart[index].partEchCost+","+dealerServiceEntriesPart[index].partDiscount+","+dealerServiceEntriesPart[index].partTax+","+dealerServiceEntriesPart[index].partTotalCost+","+dealerServiceEntriesPart[index].warrantyApplicable+","+dealerServiceEntriesPart[index].warrantyInMonths+");'><em class='fa fa-pencil'></em></button>&nbsp;&nbsp;<button type='button' class='btn btn-danger btn-sm'  onclick='deleteDealerServiceEntriesPart("+dealerServiceEntriesPart[index].dealerServiceEntriesPartId+","+dealerServiceEntriesPart[index].warrantyApplicable+");'> <em class='fa fa-trash '></em></button> &nbsp;&nbsp; <button type='button' class='btn btn-warning btn-sm'  onclick='showWarrantyPartDetailsByDseId("+dealerServiceEntriesPart[index].partId+",\""+dealerServiceEntriesPart[index].partNumber+"\",\""+dealerServiceEntriesPart[index].partName+"\","+dealerServiceEntriesPart[index].dealerServiceEntriesPartId+");'><em class='fa fa-certificate'></em></button></td>");
					}else{
						columnArray.push("<td class='fit' style='vertical-align: middle; ' ><button type='button' class='btn btn-info btn-sm' data-toggle='modal' data-target='#editPartDetails' data-whatever='@mdo'  onclick='editDealerServiceEntriesPart("+dealerServiceEntriesPart[index].dealerServiceEntriesPartId+","+dealerServiceEntriesPart[index].partId+",\""+dealerServiceEntriesPart[index].partName+"\","+dealerServiceEntriesPart[index].partQuantity+","+dealerServiceEntriesPart[index].partEchCost+","+dealerServiceEntriesPart[index].partDiscount+","+dealerServiceEntriesPart[index].partTax+","+dealerServiceEntriesPart[index].partTotalCost+","+dealerServiceEntriesPart[index].warrantyApplicable+","+dealerServiceEntriesPart[index].warrantyInMonths+");'><em class='fa fa-pencil'></em></button>&nbsp;&nbsp;<button type='button' class='btn btn-danger btn-sm'  onclick='deleteDealerServiceEntriesPart("+dealerServiceEntriesPart[index].dealerServiceEntriesPartId+","+dealerServiceEntriesPart[index].warrantyApplicable+");'> <em class='fa fa-trash '></em></button>&nbsp;&nbsp;<label style='margin-right: 23%;'></label></td>");
					}
				}else{
					if(dealerServiceEntriesPart[index].warrantyApplicable == true ){
						columnArray.push("<td class='fit' style='vertical-align: middle; ' ><button type='button' class='btn btn-danger btn-sm'  onclick='deleteDealerServiceEntriesPart("+dealerServiceEntriesPart[index].dealerServiceEntriesPartId+","+dealerServiceEntriesPart[index].warrantyApplicable+");'><em class='fa fa-trash '></em></button>&nbsp;&nbsp; <button type='button' class='btn btn-warning btn-sm'  onclick='showWarrantyPartDetailsByDseId("+dealerServiceEntriesPart[index].partId+",\""+dealerServiceEntriesPart[index].partNumber+"\",\""+dealerServiceEntriesPart[index].partName+"\","+dealerServiceEntriesPart[index].dealerServiceEntriesPartId+");'><em class='fa fa-certificate'></em></button></td>");
					}else{
						columnArray.push("<td class='fit' style='vertical-align: middle; ' ><button type='button' class='btn btn-danger btn-sm'  onclick='deleteDealerServiceEntriesPart("+dealerServiceEntriesPart[index].dealerServiceEntriesPartId+","+dealerServiceEntriesPart[index].warrantyApplicable+");'><em class='fa fa-trash '></em></button>&nbsp;&nbsp;<label style='margin-right: 23%;'></label></td>");
					}
				}
			}else{
				if(dealerServiceEntriesPart[index].warrantyApplicable == true ){
					columnArray.push("<td class='fit' style='vertical-align: middle; ' ><button type='button' class='btn btn-warning btn-sm'  onclick='showWarrantyPartDetailsByDseId("+dealerServiceEntriesPart[index].partId+",\""+dealerServiceEntriesPart[index].partNumber+"\",\""+dealerServiceEntriesPart[index].partName+"\","+dealerServiceEntriesPart[index].dealerServiceEntriesPartId+");'><em class='fa fa-certificate'></em></button></td>");
				}
			}

			partSubTotalCost	+= (Number(dealerServiceEntriesPart[index].partQuantity)*Number(dealerServiceEntriesPart[index].partEchCost) );
			partDiscountCost	+= Number(dealerServiceEntriesPart[index].partDiscountCost);
			partTaxCost			+= Number(dealerServiceEntriesPart[index].partTaxCost);
			partTotalCost 		+= Number(dealerServiceEntriesPart[index].partTotalCost);
			
			$('#dealerServiceEntriesPartTable').append("<tr id='penaltyID"+dealerServiceEntriesPart[index].DealerServiceEntriesPartId+"' >" + columnArray.join(' ') + "</tr>");
			
		}
		if(dealerServiceEntriesPart[0].partDiscountTaxTypeId == 2){
			$('.partPercent').addClass('fa-inr');
			$('.partPercent').removeClass('fa-percent');
		}else{
			$('.partPercent').removeClass('fa-inr');
			$('.partPercent').addClass('fa-percent');
		}
		var columnArray1 = new Array();
		columnArray1.push("<td class='fit' colspan='6' style='text-align: left; font-size: 18px;'>  Total Part Cost</td>");
		columnArray1.push("<td class='fit' colspan='1' style='text-align: right; font-size: 18px;'>"+ addCommas(partTotalCost.toFixed(2)) +"</td>");
		columnArray1.push("<td class='fit'></td>");
		$('#dealerServiceEntriesPartTable').append("<tr style='background: lightseagreen;color: white;'>" + columnArray1.join(' ') + "</tr>");
		
		columnArray = [];
	}else{
		$("#finalPartDiscountTaxTypId").val(1);
		$("#partDiscountTaxButton").removeClass('hide');
		validatePartDetails = false;
		$("#partTable").hide();
	}
	$("#partSubCost").html(addCommas(partSubTotalCost.toFixed(2)));
	$("#partDiscountAmount").html(addCommas(partDiscountCost.toFixed(2)));
	$("#partTaxableAmount").html(addCommas(partTaxCost.toFixed(2)));
	$("#totalPartCost").html(addCommas(partTotalCost.toFixed(2)));
}

function getLastPartOccurred(e,lastPartOccurred){
	if(e.value != "" && e.value != 0){
		var jsonObject				= new Object();
		var isPartIdNan				= isNaN(Number(e.value))
		
		if(isPartIdNan){
			$("#selectedPartId").val(e.id);
			$("#modalPartName").val(e.value);
			if(($("#editPartDetails").data('bs.modal') || {})._isShown){
				$("#editPartDetails").modal('hide');
				$("#partNumberModal").modal('show');
			}else{
				$("#partNumberModal").modal('show');
			}
		}else{
			jsonObject["partId"]						= e.value;
			jsonObject["companyId"]						= $("#companyId").val();
			jsonObject["userId"]						= $("#userId").val();
			jsonObject["vid"]							= $('#vid').val();
			
			showLayer();
			$.ajax({
				url: "getLastOccurredDsePartDetails",
				type: "POST",
				dataType: 'json',
				data: jsonObject,
				success: function (data) {
					var lastOccuredPartDetails = data.lastOccuredPartDetails;
					if(lastOccuredPartDetails.dealerServiceEntriesId != undefined){
						$("#"+lastPartOccurred.id+"").show();
						$("#"+lastPartOccurred.id+"").html("<a style='color:red' href='showDealerServiceEntries?dealerServiceEntriesId="+lastOccuredPartDetails.dealerServiceEntriesId+"' target='_blank'> Part Last Occurred ON "+lastOccuredPartDetails.invoiceDateStr+" Odometer: "+lastOccuredPartDetails.vehicleOdometer+" IN DSE-"+lastOccuredPartDetails.dealerServiceEntriesNumber+"</a>");
					}else{
						$("#"+lastPartOccurred.id+"").hide();
					}
					hideLayer();
				},
				error: function (e) {
					hideLayer();
					showMessage('errors', 'Some Error Occurred!');
				}
			});
		}
	}else{
		$("#"+lastPartOccurred.id+"").hide();
	}
}

function setDealerServiceEntriesLabour(data){
	var dealerServiceEntries 		= data.dealerServiceEntries;	
	var dealerServiceEntriesLabour 	= data.dealerServiceEntriesLabour;
	
	$("#dealerServiceEntriesLabourTable").empty();
	
	var labourSubTotalCost 		= 0;
	var labourDiscountCost 		= 0;
	var labourTaxCost 			= 0;
	var labourTotalCost 		= 0;
	
	if(dealerServiceEntriesLabour != undefined || dealerServiceEntriesLabour != null){
		$("#finalLabourDiscountTaxTypId").val(dealerServiceEntriesLabour[0].labourDiscountTaxTypeId);
		$("#labourDiscountTaxButton").addClass('hide');
//		if($("#finalLabourDiscountTaxTypId").val() == 2){
//			$('.labourPercent').hide();
//		}else{
//			$('.labourPercent').show();
//		}
		if($('#finalLabourDiscountTaxTypId').val() == 2){
			$('.labourPercent').removeClass('fa-percent');
			$('.labourPercent').addClass('fa-inr');
		}else{
			$('.labourPercent').addClass('fa-percent');
			$('.labourPercent').removeClass('fa-inr');
		}
		for(var index = 0 ; index < dealerServiceEntriesLabour.length; index++){
			var columnArray = new Array();
			columnArray.push("<td class='fit' style='font-size: 15px;'>"+(index+1)+"</td>");
			columnArray.push("<td class='fit' style='text-align: left; font-size: 15px;'>"+ dealerServiceEntriesLabour[index].labourName  +"</td>");
			columnArray.push("<td class='fit' style='text-align: right; font-size: 15px;'>"+ dealerServiceEntriesLabour[index].labourWorkingHours.toFixed(2)  +"</td>");
			columnArray.push("<td class='fit' style='text-align: right; font-size: 15px;'>"+ dealerServiceEntriesLabour[index].labourPerHourCost.toFixed(2)  +"</td>");
			if(dealerServiceEntriesLabour[index].labourDiscountTaxTypeId == 1){
				columnArray.push("<td class='fit' style='text-align: right; font-size: 15px;'><a style='color:black' title= 'Discount Amount: "+dealerServiceEntriesLabour[index].labourDiscountCost+" '>"+ dealerServiceEntriesLabour[index].labourDiscount  +"%</td>");
				columnArray.push("<td class='fit' style='text-align: right; font-size: 15px;'><a style='color:black' title= 'Tax Amount: "+dealerServiceEntriesLabour[index].labourTaxCost+" '>"+ dealerServiceEntriesLabour[index].labourTax  +"%</td>");
			}else{
				columnArray.push("<td class='fit' style='text-align: right; font-size: 15px;'>₹"+ dealerServiceEntriesLabour[index].labourDiscount  +"</td>");
				columnArray.push("<td class='fit' style='text-align: right; font-size: 15px;'>₹"+ dealerServiceEntriesLabour[index].labourTax  +"</td>");
			}
			columnArray.push("<td class='fit' style='text-align: right; font-size: 15px;'>"+ addCommas((dealerServiceEntriesLabour[index].labourTotalCost).toFixed(2)) +"</td>");
			if(dealerServiceEntries.statusId == 1 || dealerServiceEntries.statusId == 2){
				if($("#editDealerServiceLabour").val() == true || $("#editDealerServiceLabour").val()  == 'true'){
					columnArray.push("<td class='fit' style='vertical-align: middle; ' ><button type='button' class='btn btn-info btn-sm'  onclick='editDealerServiceEntriesLabour("+dealerServiceEntriesLabour[index].dealerServiceEntriesLabourId+","+dealerServiceEntriesLabour[index].labourId+",\""+dealerServiceEntriesLabour[index].labourName+"\","+dealerServiceEntriesLabour[index].labourWorkingHours+","+dealerServiceEntriesLabour[index].labourPerHourCost+","+dealerServiceEntriesLabour[index].labourDiscount+","+dealerServiceEntriesLabour[index].labourTax+","+dealerServiceEntriesLabour[index].labourTotalCost+");'><em class='fa fa-pencil'></em></button>&nbsp;&nbsp;<button type='button' class='btn btn-danger btn-sm'  onclick='deleteDealerServiceEntriesLabour("+dealerServiceEntriesLabour[index].dealerServiceEntriesLabourId+");'><em class='fa fa-trash '></em></button></td>");
				}else{
					columnArray.push("<td class='fit' style='vertical-align: middle; ' ><button type='button' class='btn btn-danger btn-sm'  onclick='deleteDealerServiceEntriesLabour("+dealerServiceEntriesLabour[index].dealerServiceEntriesLabourId+");'><em class='fa fa-trash '></em></button></td>");
				}
			}
			
			labourSubTotalCost		+= (Number(dealerServiceEntriesLabour[index].labourWorkingHours)*Number(dealerServiceEntriesLabour[index].labourPerHourCost) );
			labourDiscountCost		+= Number(dealerServiceEntriesLabour[index].labourDiscountCost);
			labourTaxCost			+= Number(dealerServiceEntriesLabour[index].labourTaxCost);
			labourTotalCost 		+= Number(dealerServiceEntriesLabour[index].labourTotalCost);
		
			$('#dealerServiceEntriesLabourTable').append("<tr id='penaltyID"+dealerServiceEntriesLabour[index].DealerServiceEntriesLabourId+"' >" + columnArray.join(' ') + "</tr>");

		}
		if(dealerServiceEntriesLabour[0].labourDiscountTaxTypeId == 1){
			$(".labourPercentSign").show();
		}else{
			$(".labourPercentSign").hide();
		}
		var columnArray1 = new Array();
		columnArray1.push("<td class='fit' colspan='6' style='text-align: left; font-size: 18px;'> Total Labour Cost </td>");
		columnArray1.push("<td class='fit' colspan='1' style='text-align: right; font-size: 18px; ' >"+ addCommas(labourTotalCost.toFixed(2)) +"</td>");
		if(dealerServiceEntries.statusId == 1 || dealerServiceEntries.statusId == 2){
			columnArray1.push("<td class='fit'></td>");
		}
		$('#dealerServiceEntriesLabourTable').append("<tr style='background: salmon;color: white;'> " + columnArray1.join(' ') + "</tr>");

		columnArray = [];
	}else{
		$("#finalLabourDiscountTaxTypId").val(1);
		$("#labourDiscountTaxButton").removeClass('hide');
		validateLabourDetails = false;
		$("#labourTable").hide();
	}
	
	$("#labourSubCost").html(addCommas(labourSubTotalCost.toFixed(2)));
	$("#labourDiscountAmount").html(addCommas(labourDiscountCost.toFixed(2)));
	$("#labourTaxableAmount").html(addCommas(labourTaxCost.toFixed(2)));
	$("#totalLabourCost").html(addCommas(labourTotalCost.toFixed(2)));
}

function addLabourDetails(){
	
	var jsonObject					= new Object();
	var labourArray	 			= new Array();
	
	var labourNameArr 			= new Array();
	var labourHourArr 			= new Array();
	var labourRateArr 			= new Array();
	var labourDisArr 			= new Array();
	var labourTaxArr 			= new Array();
	var labourTotalCostArr 		= new Array();
	
	
	$("input[name=labourName]").each(function(){
		labourNameArr.push($(this).val().replace(/"/g, ""));
	});
	$("input[name=labourWorkingHours]").each(function(){
		labourHourArr.push($(this).val());
	});
	$("input[name=labourPerHourCost]").each(function(){
		labourRateArr.push($(this).val());
	});
	$("input[name=labourDiscount]").each(function(){
		labourDisArr.push($(this).val());
	});
	$("input[name=labourTax]").each(function(){
		labourTaxArr.push($(this).val());
	});
	$("input[name=totalLabourCost]").each(function(){
		labourTotalCostArr.push($(this).val());
		totalLabourCost += Number($(this).val());
	});
	
	
	for(var i =0 ; i< labourNameArr.length; i++){
		var labourDetails					= new Object();
		if(labourNameArr[i] != ""){
			if(labourNameArr[i] > 0 && (labourTotalCostArr[i] == '' || labourTotalCostArr[i] == 0)){
				showMessage('info','Please Enter Labour Details');
				hideLayer();
				return false;
			}
			
			var isLabourNan			= isNaN(labourNameArr[i])
			
			
			if(isLabourNan){
				labourDetails.labourName				= labourNameArr[i];
			}else{
				labourDetails.labourId					= labourNameArr[i];
			}
			labourDetails.labourWorkingHours		= labourHourArr[i];
			labourDetails.labourPerHourCost			= labourRateArr[i];
			if($('#finalLabourDiscountTaxTypId').val() == 1){
				if(labourTaxArr[i] > 100 || labourDisArr[i] > 100){
					showMessage('info','Please Enter Valid Discount/Tax');
					hideLayer();
					return false;
				}
			}
			labourDetails.labourTax					= labourTaxArr[i];
			labourDetails.labourDiscount			= labourDisArr[i];
			labourDetails.totalLabourCost			= labourTotalCostArr[i];
			
			labourArray.push(labourDetails);
		}
	}
	
	
	
//	if($('#labourName').val() == "" || $('#labourName').val() == undefined){
//		showMessage('info','Please Enter labour Name');
//		hideLayer();
//		return false;
//	}
//	
//	if($('#labourWorkingHours').val() == "" || $('#labourWorkingHours').val() == 0){
//		showMessage('info','Please Enter Working Hours');
//		hideLayer();
//		return false;
//	}
//	
//	if($('#labourPerHourCost').val() == "" || $('#labourPerHourCost').val() == 0){
//		showMessage('info','Please Enter Cost');
//		hideLayer();
//		return false;
//	}
//	
//	if($('#labourTotalCost').val() == "" || $('#labourTotalCost').val() == 0){
//		showMessage('info','Please Labour Details');
//		hideLayer();
//		return false;
//	}
//	
//	if($('#finalLabourDiscountTaxTypId').val() == 1){
//		if($("#labourTax").val() > 100 || $("#labourDiscount").val() > 100){
//			showMessage('info','Please Enter Valid Discount/Tax');
//			hideLayer();
//			return false;
//		}
//	}
	
	jsonObject["dealerServiceEntriesId"]		= $("#dealerServiceEntriesId").val();
	
	
//	var isLabourNan			= isNaN($("#labourName").val())
	
	
//	if(isLabourNan){
//		jsonObject["labourName"]					= $("#labourName").val();
//	}else{
//		jsonObject["labourId"]						= $("#labourName").val();
//	}
	
//	jsonObject["labourWorkingHours"]			= $("#labourWorkingHours").val();
//	jsonObject["labourPerHourCost"]				= $("#labourPerHourCost").val();
//	jsonObject["labourDiscount"]				= $("#labourDiscount").val();
//	jsonObject["labourTax"]						= $("#labourTax").val();
//	jsonObject["totalLabourCost"]				= $("#labourTotalCost").val();
	jsonObject["companyId"]						= $("#companyId").val();
	jsonObject["userId"]						= $("#userId").val();
	jsonObject["labourDiscountTaxTypeId"]		= $("#finalLabourDiscountTaxTypId").val();
	
	
	jsonObject.labourDetails 				= JSON.stringify(labourArray);
	
	showLayer();
	$.ajax({
		url: "addDealerServiceEntriesLabourDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			location.reload();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
}
function addPartDetails(){

		var jsonObject					= new Object();
		var partArray  			 = new Array();
		var partIdArr  			 = new Array();
		var partNameArr 		 = new Array();
		var partQntyArr 		 = new Array();
		var partEachCostArr  	 = new Array();
		var partDiscountArr  	 = new Array();
		var partGSTArr  	     = new Array();
		var partTotalCostArr  	 = new Array();
		var partNumberArr  	 	= new Array();
		var partWarrantyArr  	 = new Array();
		var partWarrantyMonthArr  	 = new Array();
		
//		
//		if($('#partId').val() == "" || $('#partId').val() == undefined){
//			showMessage('info','Please Select Part');
//			hideLayer();
//			return false;
//		}
//		
//		if($('#partQuantity').val() == "" || $('#partQuantity').val() == 0){
//			showMessage('info','Please Enter Quantity');
//			hideLayer();
//			return false;
//		}
//		
//		if($('#partEchCost').val() == "" || $('#partEchCost').val() == 0){
//			showMessage('info','Please Enter Cost');
//			hideLayer();
//			return false;
//		}
//		
//		if($('#partTotalCost').val() == "" || $('#partTotalCost').val() == 0){
//			showMessage('info','Please Enter Part Details');
//			hideLayer();
//			return false;
//		}
//		
//		if($('#finalPartDiscountTaxTypId').val() == 1){
//			if($("#partTax").val() > 100 || $("#partDiscount").val() > 100){
//				showMessage('info','Please Enter Valid Tax');
//				hideLayer();
//				return false;
//			}
//		}
//		
//		if(!isNaN($('#partId').val()) && $('#partId').val() > 0){
//			jsonObject["partId"]		= $("#partId").val();
//		}else{
//			showMessage('info','You have Not Entred Part Number For '+$('#partId').val()+'');
//			hideLayer();
//			$("#selectedPartId").val("partId");
//			$("#modalPartName").val($('#partId').val());
//			$("#partNumberModal").modal('show');
//			return false;
//			jsonObject["partName"]		= $("#partId").val();
//		}
		jsonObject["dealerServiceEntriesId"]		= $("#dealerServiceEntriesId").val();
//		jsonObject["partQty"]						= $("#partQuantity").val();
//		jsonObject["partCost"]						= $("#partEchCost").val();
//		jsonObject["partDiscount"]					= $("#partDiscount").val();
//		jsonObject["partTax"]						= $("#partTax").val();
//		jsonObject["totalPartCost"]					= $("#partTotalCost").val();
		jsonObject["companyId"]						= $("#companyId").val();
		jsonObject["userId"]						= $("#userId").val();
		jsonObject["vid"]							= $('#vid').val();
		jsonObject["partDiscountTaxTypeId"]			= $("#finalPartDiscountTaxTypId").val();
		jsonObject["invoiceDate"]					= $("#invoiceDateVal").val();
//		jsonObject["isWarrantyApplicable"]			= $("#partId").select2('data').isWarrantyApplicable;
//		jsonObject["warrantyInMonths"]				= $("#partId").select2('data').warrantyInMonths;
		
		
		$("input[name=partId]").each(function(){
			partIdArr.push(this.id)
			partNameArr.push($(this).val());
			if($(this).val() != "" && $(this).val() != null && $(this).val() != undefined){
				partNumberArr.push($('#'+this.id).select2('data').partNumber);
				partWarrantyArr.push($('#'+this.id).select2('data').isWarrantyApplicable);
				partWarrantyMonthArr.push($('#'+this.id).select2('data').warrantyInMonths);
			}
		});
		
		$('input[name=partQty]').each(function(){
			partQntyArr.push($(this).val());
		});
		$('input[name=partEachCost]').each(function(){
			partEachCostArr.push($(this).val());
		});
		$('input[name=partDiscount]').each(function(){
			partDiscountArr.push($(this).val());
		});
		$('input[name=partTax]').each(function(){
			partGSTArr.push($(this).val());
		});
		$('input[name=partTotalCost]').each(function(){
			partTotalCostArr.push($(this).val());
		});
		
		for(var i =0 ; i< partNameArr.length; i++){
			var partDetails					= new Object();
			var partId 						= 0;
			var partName 					= "";
			if(partNameArr[i] != ""){	
				//If part is new then have to add part number (logic in else statement)
				if(Number(partNameArr[i]) > 0){
					partDetails.partId					= partNameArr[i];
				}else{
					showMessage('info','You have Not Entred Part Number For '+partNameArr[i]+'');
					hideLayer();
					$("#selectedPartId").val(partIdArr[i]); // this is the dynamic id of part autocomplete
					$("#modalPartName").val(partNameArr[i]); // value display on modal
					$("#partNumberModal").modal('show');
					return false;
					
				}
				if(partNameArr[i] > 0 && (partTotalCostArr[i] == '' || partTotalCostArr[i] == 0)){
					showMessage('info','Please Enter Part Details');
					hideLayer();
					return false;
				}
				partDetails.partNumber				= partNumberArr[i];
				partDetails.isWarrantyApplicable	= partWarrantyArr[i];
				
				partDetails.warrantyInMonths		= partWarrantyMonthArr[i];
				partDetails.partQty					= partQntyArr[i];
				partDetails.partCost				= partEachCostArr[i];
				
				if($('#finalPartDiscountTaxTypId').val() == 1){
					if(partGSTArr[i] > 100 || partDiscountArr[i] > 100){
						showMessage('info','Please Enter Valid Discount/Tax');
						hideLayer();
						return false;
					}
				}
				partDetails.partTax					= partGSTArr[i];
				partDetails.partDiscount			= partDiscountArr[i];
				partDetails.totalPartCost			= partTotalCostArr[i];

				partArray.push(partDetails);
			}
		}
		jsonObject.partDetails 					= JSON.stringify(partArray);
		
		showLayer();
		$.ajax({
			url: "addDealerServiceEntriesPartDetails",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				location.reload();
			},
			error: function (e) {
				hideLayer();
				showMessage('errors', 'Some Error Occurred!');
			}
		});

}

function deleteDealerServiceEntriesPart(dealerServiceEntriesPartId, partWarrantyStatus){
	var confirmMessage = 'Are You Sure, Do You Want To Delete The Part Details!';
	if(partWarrantyStatus == true){
		confirmMessage = 'Are You Sure, Do You Want To Delete The Part Details It Will Delete The Warrnty Details!';
	}
	if(confirm(confirmMessage)){

		var jsonObject					= new Object();

		jsonObject["dealerServiceEntriesId"]		= $("#dealerServiceEntriesId").val();
		jsonObject["dealerServiceEntriesPartId"]	= dealerServiceEntriesPartId;
		jsonObject["companyId"]						= $("#companyId").val();
		jsonObject["userId"]						= $("#userId").val();

		showLayer();
		$.ajax({
			url: "deleteDealerServiceEntriesPart",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				location.reload();
			},
			error: function (e) {
				hideLayer();
				showMessage('errors', 'Some Error Occurred!');
			}
		});

	}else{
		location.reload();
	}
}	
	function deleteDealerServiceEntriesLabour(dealerServiceEntriesLabourId){
		if(confirm('Are You Sure, Do You Want To Delete The Labour Details!')){

			var jsonObject					= new Object();

			jsonObject["dealerServiceEntriesId"]		= $("#dealerServiceEntriesId").val();
			jsonObject["dealerServiceEntriesLabourId"]	= dealerServiceEntriesLabourId;
			jsonObject["companyId"]						= $("#companyId").val();
			jsonObject["userId"]						= $("#userId").val();

			showLayer();
			$.ajax({
				url: "deleteDealerServiceEntriesLabour",
				type: "POST",
				dataType: 'json',
				data: jsonObject,
				success: function (data) {
					location.reload();
				},
				error: function (e) {
					hideLayer();
					showMessage('errors', 'Some Error Occurred!');
				}
			});

		}else{
			location.reload();
		}
}
	


function completeDSE() {
	if(!validateLabourDetails && !$("#labourNotApplicableId").prop('checked')){
		showMessage('info','Please Enter Labour Details')
		hideLayer();
		return false;
	}else if(validateLabourDetails && $("#labourNotApplicableId").prop('checked')){
		$("#labourNotApplicableId").prop('checked',false);
		showMessage('info','Labour Details Found');
		hideLayer();
		return false;
	}
	if(!validatePartDetails && !$("#partNotApplicableId").prop('checked')){
		showMessage('info','Please Enter Part Details')
		hideLayer();
		return false;
	}else if(validatePartDetails && $("#partNotApplicableId").prop('checked')){
		$("#partNotApplicableId").prop('checked',false);
		showMessage('info','Part Details Found');
		hideLayer();
		return false;
	}
	if($("#validatePartWarranty").val() == true || $("#validatePartWarranty").val() == 'true' ){
		showMessage('info','Please Fill Part Warranty Details');
		hideLayer();
		return false;
	}
	$('#modalDriverId').select2('data', {
		id : $('#showDriverId').val(),
		text : $('#showDriverName').val()
	});
	
	$('#modalAssignToId').select2('data', {
		id : $('#showAssignToId').val(),
		text : $('#showAssignTo').val()
	});
	
	if($("#invoiceNumberVal").val() != ""  && $("#invoiceDateVal").val() != "" ){
		if(confirm('Are You Sure, Do You Want To Complete Dealer Service Entry!')){
			$("#remarkModal").modal('show');
		}else{
			location.reload();
		}
	}else{
		$("#invoiceModal").modal('show');
	}
	
}

function saveComplete(){
	var jsonObject					= new Object();
	
	if($("#completeRemarkId").val().trim() == ""){
		showMessage('info','Please Enter Remark');
		hideLayer();
		return false;
		
	}
	
	if($("#modalDriverId").val() == "" || $("#modalDriverId").val() == 0){
		showMessage('info','Please Select Driver');
		hideLayer();
		return false;
		
	}
	
	if($("#modalAssignToId").val() == ""){
		showMessage('info','Please Select Assign To');
		hideLayer();
		return false;
		
	}
	
	jsonObject["dealerServiceEntriesId"]		= $("#dealerServiceEntriesId").val();
	jsonObject["companyId"]						= $("#companyId").val();
	jsonObject["userId"]						= $("#userId").val();
	jsonObject["paymentTypeId"]					= $("#paymentTypeId").val();
	jsonObject["invoiceCost"]					= Number($("#invoiceCost").val());
	jsonObject["vendorId"]						= $("#showVendorId").val();
	jsonObject["dealerServiceEntriesNumber"] 	= $("#dealerSENumber").html();
	jsonObject["invoiceDate"]					= $("#invoiceDateVal").val();
	jsonObject["invoiceNumber"]					= $("#invoiceNumberVal").val();
	jsonObject["vid"]							= $("#vid").val();
	jsonObject["vehicleOdometer"]				= $("#vehicleOdometerVal").val();
	jsonObject["serviceReminderIds"]			= $("#serviceReminderIds").val();
	jsonObject["issueId"]						= $("#issueId").val();
	jsonObject["partNotApplicableId"]			= $("#partNotApplicableId").prop('checked');
	jsonObject["laboutNotApplicableId"]			= $("#labourNotApplicableId").prop('checked');
	jsonObject["remark"]						= $("#completeRemarkId").val().trim();
	jsonObject["driverId"]						= $("#modalDriverId").val();
	jsonObject["assignToId"]					= $("#modalAssignToId").val();
	
	showLayer();
	$.ajax({
		url: "completeDealerServiceEntries",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.partDeatilsNotFound != undefined ){
				showMessage('info','Please Add Part Details');
				hideLayer();
			}else if(data.labourDeatilsNotFound != undefined ){
				showMessage('info','Please Add Labour Details');
				hideLayer();
			}else if(data.unAssigned != undefined ){
				showMessage('info','Please Enter Warranty Details');
				hideLayer();
			}else if(data.quotationNotApproved != undefined ){
				showMessage('info','Cannot Close, Vehicle Accident quotation not approved !');
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

	
function downloadDocument(){
	 window.location.replace("download/serviceDocument/"+$("#dealerServiceDocumentId").val()+"");
}
function uploadDocument(){
	 $("#addDealerServiceDocument").modal('show');
}

$(document).ready(function () {
    $("#btnSubmitDoc").on('keypress click', function (e) {
        e.preventDefault(); 	
        uploadDealerServiceEntryDocument();
    });

});

function uploadDealerServiceEntryDocument(){
	var jsonObject								= new Object();
	jsonObject["dealerServiceEntriesId"]		= $('#dealerServiceEntriesId').val();
	
	var form = $('#fileUploadForm')[0];
	var data = new FormData(form);

	data.append("dealerServiceEntryData", JSON.stringify(jsonObject)); 
	
	$.ajax({
		type: "POST",
		enctype: 'multipart/form-data',
		url: "uploadDealerServiceEntryDocument",
		data: data,
		processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
		success: function (data) {
			
			if(data.UploadSuccess != undefined && data.UploadSuccess == true){
				hideLayer();
				window.location.replace("showDealerServiceEntries?dealerServiceEntriesId="+$('#dealerServiceEntriesId').val()+"");
			}
			
			},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function saveInvoiceDetails(){
	var jsonObject					= new Object();
	
	if($('#modalInvoiceDate').val() == "" || $('#modalInvoiceDate').val() == undefined){
		showMessage('info','Please Select Invoice Date');
		hideLayer();
		return false;
	}
	if($('#modalInvoiceNumber').val().trim() == "" || $('#modalInvoiceNumber').val().trim() == undefined){
		showMessage('info','Please Enter Invoice Number');
		hideLayer();
		return false;
	}

	jsonObject["dealerServiceEntriesId"]	= $("#dealerServiceEntriesId").val();
	jsonObject["vid"]						= $("#vid").val();
	jsonObject["vendorId"]					= $("#showVendorId").val();
	jsonObject["invoiceDate"]				= $("#modalInvoiceDate").val();
	jsonObject["invoiceNumber"]				= $("#modalInvoiceNumber").val();
	jsonObject["companyId"]					= $("#companyId").val();
	showLayer();
	$.ajax({
		url: "saveInvoiceDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.alreadyExist != undefined && data.alreadyExist == true){
				showMessage('info', 'AlreadyExist');
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

$(document).ready(function() {
	$.getJSON("desExtraIssueDropdown.in", function(e) {
		desIssueList	= e;//To get All Company Name 
		$("#dealerServiceExtraIssueId").empty();
		$("#dealerServiceExtraIssueId").append($("<option>").text("Recent Dse Issue").attr("value",0));
		$('#dealerServiceExtraIssueId').select2();
	
		for(var k = 0; k <desIssueList.length; k++){
			$("#dealerServiceExtraIssueId").append($("<option>").text(desIssueList[k].description).attr("value", desIssueList[k].dealerServiceExtraIssueId));
		}
	
	});	
});	

$('#dealerServiceExtraIssueId').on('change', function() {
	if($("#dealerServiceExtraIssueId").val() > 0 ){
		$('#description').val('')
		var extraIssue  = $("#dealerServiceExtraIssueId option:selected").text();
		$('#description').val(extraIssue);
	}
});

function saveDseExtraIssue(){

	var jsonObject					= new Object();
	
	if($('#description').val().trim() == "" || $('#description').val().trim() == undefined){
		showMessage('info','Please Enter DSE Extra Issue');
		hideLayer();
		return false;
	}

	jsonObject["description"]				= $("#description").val().trim();
	jsonObject["userId"]					= $("#userId").val();
	jsonObject["dealerServiceEntriesId"]	= $("#dealerServiceEntriesId").val();
	jsonObject["companyId"]					= $("#companyId").val();
	showLayer();
	$.ajax({
		url: "addDealerServiceExtraIssue",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.alreadyExist != undefined && data.alreadyExist == true){
				showMessage('info','Already Exist')
			}else{
				location.reload();
			}
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});

	
}

function changeDseStatus(){
	var toStatus	= "";
	if($("#statusId").val() == 1){
		toStatus = "On Hold"
	}else{
		toStatus = "In Process"
	}
	if(confirm('Are You Sure, Do You Want Change Status To '+toStatus+'!')){
		$("#changeDseStatusRemarkModal").modal('show');
	}else{
		location.reload();
	}
}

function saveChangeStatus(){
	var jsonObject					= new Object();
	showLayer();
	
	var changedStatusId;
	if($("#statusId").val() == 1){
		changedStatusId = 2;
	}else{
		changedStatusId = 1;
	}
	
	if($("#changeStatusRemarkId").val().trim() == "" ){
		showMessage('info','Please Enter Remark');
		hideLayer();
		return false;
	}
	jsonObject["statusId"]					= changedStatusId;
	jsonObject["dealerServiceEntriesId"]	= $("#dealerServiceEntriesId").val();
	jsonObject["userId"]					= $("#userId").val();
	jsonObject["companyId"]					= $("#companyId").val();
	jsonObject["remark"]					= $("#changeStatusRemarkId").val().trim();
	
	$.ajax({
		url: "changeDseStatus",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			location.reload();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});

}

function reopenDSE(){
	if(confirm('Are You Sure, Do You Want To Complete Dealer Service Entry!')){
		$("#reopnRemarkModal").modal('show');
	}else{
		location.reload();
	}
}
function saveReopenDSE(){
	var jsonObject					= new Object();
	if($("#statusId").val() == 3 || $("#statusId").val() == 4 ){
		jsonObject["dealerServiceEntriesId"]		= $("#dealerServiceEntriesId").val();
		jsonObject["companyId"]						= $("#companyId").val();
		jsonObject["userId"]						= $("#userId").val();
		jsonObject["remark"]						= $("#reOpenRemarkId").val().trim();
		showLayer();
		$.ajax({
			url: "reopenDealerService",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				if(data.accountClose != undefined && (data.accountClose == true || data.accountClose == "true")){
					showMessage('info','Account is Closed, Hence You Can Not Reopen Dealer Service Entry');
					hideLayer();
				}else if(data.accidentEntryApproved != undefined){
					hideLayer();
					showMessage('info', data.accidentEntryApproved);
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

function editDealerServiceEntriesPart(dealerServiceEntriesPartId,partId,partName,qty,rate,dis,tax,total,isWarrantyApplicable,warrantyInMonth){
	
	$("#editDealerServiceEntriesPartId").val(dealerServiceEntriesPartId);
	$('#editPartId').select2('data', {
		id : partId,
		text : partName,
		isWarrantyApplicable: isWarrantyApplicable,
		warrantyInMonths: warrantyInMonth
	});
	$("#editPartQuantity").val(qty);
	$("#editPartEachCost").val(rate);
	$("#editPartDiscount").val(dis);
	$("#editPartTax").val(tax);
	$("#editPartTotalCost").val(total);
	/*if(isWarrantyApplicable == true){
		$('#editPartId').select2('readonly', true); 
	}else{
		$('#editPartId').select2('readonly', false);
	}*/
	//$("#editPartDetails").modal('show');
	
}

function updatePartDetails(){
	
	if($('#editPartEachCost').val() == "" || $('#editPartEachCost').val() == 0){
		showMessage('info','Please Enter Cost');
		hideLayer();
		return false;
	}
	
	if($('#editPartTotalCost').val() == "" || $('#editPartTotalCost').val() == 0){
		showMessage('info','Please Enter Part Details');
		hideLayer();
		return false;
	}
	
	if($('#finalPartDiscountTaxTypId').val() == 1){
		if($("#editPartTax").val() > 100 || $("#editPartDiscount").val() > 100){
			showMessage('info','Please Enter Valid Discount/Tax');
			hideLayer();
			return false;
		}
	}


	
	var jsonObject									= new Object();
	jsonObject["dealerServiceEntriesId"]			= $("#dealerServiceEntriesId").val();
	jsonObject["dealerServiceEntriesPartId"]		= $("#editDealerServiceEntriesPartId").val();
	jsonObject["partId"]							= $("#editPartId").val();
	jsonObject["partQty"]							= $("#editPartQuantity").val();
	jsonObject["partCost"]							= $("#editPartEachCost").val();
	jsonObject["partDiscount"]						= $("#editPartDiscount").val();
	jsonObject["partTax"]							= $("#editPartTax").val();
	jsonObject["partTotalCost"]						= $("#editPartTotalCost").val();
	jsonObject["partDiscountTaxTypeId"]				= $("#finalPartDiscountTaxTypId").val();
	jsonObject["companyId"]							= $("#companyId").val();
	jsonObject["userId"]							= $("#userId").val();
	jsonObject["isWarrantyApplicable"]				= $("#editPartId").select2('data').isWarrantyApplicable;
	jsonObject["invoiceDate"]						= $("#invoiceDateVal").val();
	jsonObject["vid"]								= $("#vid").val();
	
	showLayer();
	$.ajax({
		url: "updateDealerServicePart",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			location.reload();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
}

function editDealerServiceEntriesLabour(dealerServiceEntriesLabourId,laburId,labourName,hrs,rate,dis,tax,total){
	
	$("#editDealerServiceEntriesLabourId").val(dealerServiceEntriesLabourId);
	$('#editLabourId').select2('data', {
		id : laburId,
		text : labourName
	});
	$("#editLabourWorkingHours").val(hrs);
	$("#editLabourPerHourCost").val(rate);
	$("#editLabourDiscount").val(dis);
	$("#editLabourTax").val(tax);
	$("#editLabourTotalCost").val(total);
	$("#editLabourDetails").modal('show');
	
}

function updateLabourDetails(){
	
	if($('#editLabourPerHourCost').val() == "" || $('#editLabourPerHourCost').val() == 0){
		showMessage('info','Please Enter Cost');
		hideLayer();
		return false;
	}
	
	if($('#editLabourTotalCost').val() == "" || $('#editLabourTotalCost').val() == 0){
		showMessage('info','Please Labour Details');
		hideLayer();
		return false;
	}	
	
	if($('#finalLabourDiscountTaxTypId').val() == 1){
		if($("#editLabourTax").val() > 100 || $("#editLabourDiscount").val() > 100){
			showMessage('info','Please Enter Valid Discount/Tax');
			hideLayer();
			return false;
		}
	}
	
	var jsonObject									= new Object();
	var jsonArray 		= new Array();
	jsonObject["dealerServiceEntriesId"]			= $("#dealerServiceEntriesId").val();
	jsonObject["dealerServiceEntriesLabourId"]		= $("#editDealerServiceEntriesLabourId").val();
	var isLabourNan			= isNaN( $("#editLabourId").val())
	if(isLabourNan){
		jsonObject["labourName"]							= $("#editLabourId").val();
	}else{
		jsonObject["labourId"]							= $("#editLabourId").val();
	}
	jsonObject["labourWorkingHours"]				= $("#editLabourWorkingHours").val();
	jsonObject["labourPerHourCost"]					= $("#editLabourPerHourCost").val();
	jsonObject["labourDiscount"]					= $("#editLabourDiscount").val();
	jsonObject["labourTax"]							= $("#editLabourTax").val();
	jsonObject["labourDiscountTaxTypeId"]			= $("#finalLabourDiscountTaxTypId").val();
	jsonObject["companyId"]							= $("#companyId").val();
	jsonObject["userId"]							= $("#userId").val();
		
	jsonArray.push(jsonObject);
	jsonObject.labourDetails 						= JSON.stringify(jsonArray);
	
	showLayer();
	$.ajax({
		url: "updateDealerServiceLabour",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			location.reload();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
}

function showWarrantyPartDetailsByDseId(partId,partName,partNumber,dealerServiceEntiresPartId){
	$("#currentPartId").val(partId);
	var invoiceDate = $("#invoiceDateVal").val().trim();
	var jsonObject								= new Object();
	jsonObject["vid"]							= $('#vid').val();
	jsonObject["partId"]						= partId;
	jsonObject["companyId"]						= $("#companyId").val();
	jsonObject["userId"]						= $("#userId").val();
	jsonObject["serviceId"]						= $("#dealerServiceEntriesId").val();
	jsonObject["invoiceDate"]					= (invoiceDate.split("-")).reverse().join("-").trim();
	jsonObject["companyId"]						= $("#companyId").val();
	jsonObject["dealerServiceEntiresPartId"]	= dealerServiceEntiresPartId;
	jsonObject["partWarrantyStatusId"]			= 1;// unassign
	jsonObject["warrantyPartName"]				= partNumber+"-"+partName;
	
	showLayer();
	$.ajax({
		url: "getWarrantyPartDetailsByDseId",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data != undefined){
				setWarrantyPartDetailsByDseId(data);
			}
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});

}

function setWarrantyPartDetailsByDseId(data){
	
	$("#underWarrantyPartTable").empty();
	$("#assignPartOfDseTable").empty();
	
	var partWarrantyDetailsList 		= data.partWarrantyDetailsList;
	var assignedPartWarrantyDetailsList = data.assignedPartWarrantyDetailsList;
	$("#warrantyPartName").html(data.warrantyPartName);
	
	
	if(partWarrantyDetailsList != undefined || partWarrantyDetailsList != null){
		$("#validatePartWarranty").val(true);
		for(var index = 0 ; index < partWarrantyDetailsList.length; index++){
			
			var columnArray = new Array();
			columnArray.push("<td class='fit ar'><input type='hidden' name='partWarrantyDetailsId' value="+partWarrantyDetailsList[index].partWarrantyDetailsId+">"+(index+1)+"</td>");
			columnArray.push("<td class='fit ar'><input type='text' class='form-control' id='serialNumber"+partWarrantyDetailsList[index].partWarrantyDetailsId+"' name='serialNumber'></td>");
			columnArray.push("<td class='fit ar'><input type='hidden' style='width:100%' class='form-control' id='prePartWarrantyDetailsId"+partWarrantyDetailsList[index].partWarrantyDetailsId+"' name='prePartWarrantyDetailsId' ></td>");
			columnArray.push("<td class='fit ar'><input type='checkbox' id='oldPartReceived"+partWarrantyDetailsList[index].partWarrantyDetailsId+"' name='oldPartReceived' ></td>");
			
			$('#underWarrantyPartTable').append("<tr id='penaltyID"+partWarrantyDetailsList[index].partWarrantyDetailsId+"' >" + columnArray.join(' ') + "</tr>");
			
			var jsonObject					= new Object();
			jsonObject["partId"]			= data.partId;
			jsonObject["vid"]				= data.vid;
			jsonObject["serviceId"]			= data.serviceId;
			jsonObject["invoiceDate"]		= data.invoiceDate;
			jsonObject["companyId"]			= $("#companyId").val();
			jsonObject["partWarrantyStatusId"]			= 1; // unassign
			
			$("#prePartWarrantyDetailsId"+partWarrantyDetailsList[index].partWarrantyDetailsId+"").select2( {
				minimumInputLength:0, minimumResultsForSearch:10, ajax: {
					url:"underWarrantyPartDetailsList.in", 
					dataType:"json", 
					type:"POST", 
					contentType:"application/json", 
					quietMillis:50, 
					data:function(a) {
						return {
							term: a,
							partId: data.partId,
							vid: data.vid,
							serviceId: data.serviceId,
							invoiceDate: data.invoiceDate,
							companyId: $("#companyId").val(),
							partWarrantyStatusId: 2
							
						}
					}
				, results:function(a) {
					return {
						results:$.map(a, function(a) {
							return {
								text: a.partSerialNumber +"-"+ a.serviceNumber, 
								slug: a.slug, 
								id: a.partWarrantyDetailsId,
								serviceId: a.serviceId
							}
						})
					}
				}
			}
			})
			
			
		}
		columnArray = [];
		}else{
			var warrantyColumnArray = new Array();
			warrantyColumnArray.push("<td class='fit ar' colspan='6'> NO RECORD</td>");
			$('#underWarrantyPartTable').append("<tr>" + warrantyColumnArray.join(' ') + "</tr>");
			warrantyColumnArray = [];
		}
	
	if(assignedPartWarrantyDetailsList != undefined || assignedPartWarrantyDetailsList != null){
		
		$("#validatePartWarranty").val(false);
		for(var index = 0 ; index < assignedPartWarrantyDetailsList.length; index++){

			var columnArray = new Array();
			columnArray.push("<td class='fit ar'>"+(index+1)+"</td>");
			columnArray.push("<td class='fit ar'>"+assignedPartWarrantyDetailsList[index].partSerialNumber+"</td>");
			columnArray.push("<td class='fit ar'>"+assignedPartWarrantyDetailsList[index].replacePartSerialNumber+"</td>");
			columnArray.push("<td class='fit ar'>"+assignedPartWarrantyDetailsList[index].replaceInServiceNumber+"</td>");
			columnArray.push("<td class='fit ar'>"+assignedPartWarrantyDetailsList[index].isOldPartReceivedStr+"</td>");
			columnArray.push("<td class='fit' style='vertical-align: middle; ' ><button type='button' class='btn btn-danger btn-sm deleteAssignPart' onclick='removeAssignPartWarrantyDetails("+assignedPartWarrantyDetailsList[index].partWarrantyDetailsId+");' ><em class='fa fa-trash'></em></button></td>");
			$('#assignPartOfDseTable').append("<tr id='penaltyID"+assignedPartWarrantyDetailsList[index].partWarrantyDetailsId+"' >" + columnArray.join(' ') + "</tr>");

		}
		columnArray = [];

	}else{
		var assignColumnArray = new Array();
		assignColumnArray.push("<td class='fit ar' colspan='6'> NO RECORD</td>");
		$('#assignPartOfDseTable').append("<tr>" + assignColumnArray.join(' ') + "</tr>");
		assignColumnArray = [];
	}
	
	
	if((assignedPartWarrantyDetailsList == undefined ) && ( partWarrantyDetailsList == undefined )){
		showMessage('info','No Record Found')
		hideLayer();
	}else{
		$('#partWarrantyModal').modal('show');
		if($("#statusId").val() == 3 || $("#statusId").val() == 4){
			$("#notAssignPartDiv").hide();
			$(".deleteAssignPart").hide();
			$("#allAssignPartButton").hide();
			$("#updatePartWarrantyStatus").hide();
		}
		hideLayer();
	}
	

}

function updatePartWarrantyStatus(){
	
	var partWarrantyArr 				= new Array();
	var serialNumberArr 				= new Array();
	var prePartWarrantyDetailsIdArr 	= new Array();
	var preServiceIdArr 				= new Array();
	var oldPartReceivedArr				= new Array();
	var partWarrantyDetailsIdArr 		= new Array();
	var prePartWarrantyStatusIdArr 		= new Array();
	var jsonObject						= new Object();
	
	$("input[name=serialNumber]").each(function(){
		serialNumberArr.push($(this).val().trim());
	});
	
	$("input[name=prePartWarrantyDetailsId]").each(function(){
		if($(this).val() != undefined && $(this).val() != ""){
			prePartWarrantyDetailsIdArr.push($(this).val());
			prePartWarrantyStatusIdArr.push(3);
			preServiceIdArr.push($('#'+this.id).select2('data').serviceId);
		}else{
			prePartWarrantyDetailsIdArr.push(0);
			prePartWarrantyStatusIdArr.push(2);
			preServiceIdArr.push(0);
		}
	});
	$('input[name*=oldPartReceived]:checked').each(function(){
		oldPartReceivedArr.push($('#'+this.id).prop('checked'));
	});
	$("input[name=partWarrantyDetailsId]").each(function(){
		partWarrantyDetailsIdArr.push($(this).val());
	});
	
	
	for(var i =0 ; i< serialNumberArr.length; i++){
		var partWarrantyDetails					= new Object();
		if(partWarrantyDetailsIdArr[i] != ""){
			if(serialNumberArr[i] == ''){
				continue;
			}
			
			partWarrantyDetails.serialNumber					= serialNumberArr[i].trim();
			partWarrantyDetails.prePartWarrantyDetailsId		= prePartWarrantyDetailsIdArr[i];
			partWarrantyDetails.prePartWarrantyStatusId			= prePartWarrantyStatusIdArr[i];
			partWarrantyDetails.preServiceId					= preServiceIdArr[i];
			partWarrantyDetails.isOldPartReceived				= oldPartReceivedArr[i];
			partWarrantyDetails.partWarrantyDetailsId			= partWarrantyDetailsIdArr[i];
			partWarrantyDetails.partWarrantyStatusId			= 2; //current status is assign 
			partWarrantyDetails.dseStatusId						= $("#statusId").val()
			
			partWarrantyArr.push(partWarrantyDetails);
		}
	}
	jsonObject["vid"]							= $("#vid").val();
	jsonObject["partId"]						= $("#currentPartId").val();
	jsonObject["companyId"]						= $("#companyId").val();
	jsonObject.partWarrantyDetails 				= JSON.stringify(partWarrantyArr);
	
	
	showLayer();
	$.ajax({
		url: "updatePartWarrantyStatus",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.validateSerialNumber == true){
				showMessage('info',''+data.validateMessage+' already exist')
				hideLayer();
				$('#partWarrantyModal').modal('hide');
				
			}else if(data.dseCompleted == true){
				showMessage('info','Dealer Service Entries Completed, Hence You Can Not Update Warranty Details')
				hideLayer();
				$('#partWarrantyModal').modal('hide');
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
function showAllWarrantyPart(partId,partName){

	var invoiceDate = $("#invoiceDateVal").val().trim();
	var jsonObject								= new Object();
	jsonObject["vid"]							= $('#vid').val();
	jsonObject["partId"]						=  $("#currentPartId").val();
	jsonObject["companyId"]						= $("#companyId").val();
	jsonObject["userId"]						= $("#userId").val();
	jsonObject["serviceId"]						= $("#dealerServiceEntriesId").val();
	jsonObject["invoiceDate"]					= (invoiceDate.split("-")).reverse().join("-").trim();
	jsonObject["companyId"]						= $("#companyId").val();
	jsonObject["partWarrantyStatusId"]			= 2;// assign
	jsonObject["warrantyPartName"]				= $("#"+partName).html();// unassign
	
	showLayer();
	$.ajax({
		url: "showAllWarrantyPart",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data != undefined){
				setAllWarrantyPart(data);
			}
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});


}

function setAllWarrantyPart(data){
	
	$("#allPartWarrantyTable").empty();
	var partWarrantyDetailsList = data.partWarrantyDetailsList;
	
	if(partWarrantyDetailsList != undefined || partWarrantyDetailsList != null){
		$("#allWarrantyPartName").html(data.warrantyPartName);
		for(var index = 0 ; index < partWarrantyDetailsList.length; index++){
			
			var columnArray = new Array();
			columnArray.push("<td class='fit'>"+(index+1)+"</td>");
			columnArray.push("<td class='fit ar'> "+ partWarrantyDetailsList[index].partSerialNumber  +"</td>");
			columnArray.push("<td class='fit ar'>" + partWarrantyDetailsList[index].serviceNumber +"</td>");
			
			$('#allPartWarrantyTable').append("<tr id='penaltyID"+partWarrantyDetailsList[index].partWarrantyDetailsId+"' >" + columnArray.join(' ') + "</tr>");
			
		}
		$('#allPartWarrantyModal').modal('show');
		hideLayer();
		columnArray = [];
		}else{
			showMessage('info','No Record Found!')
			hideLayer();
		}

}

function removeAssignPartWarrantyDetails(partWarrantyDetailsId){

	var jsonObject								= new Object();
	jsonObject["partWarrantyDetailsId"]			= partWarrantyDetailsId;
	jsonObject["companyId"]						= $("#companyId").val();
	
	showLayer();
	$.ajax({
		url: "removeAssignPartWarrantyDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			location.reload();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});

}

function getRemarks(){

	var jsonObject								= new Object();
	jsonObject["dealerServiceEntriesId"]		= $("#dealerServiceEntriesId").val();
	jsonObject["companyId"]						= $("#companyId").val();
	
	showLayer();
	$.ajax({
		url: "getDseRemarks",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setRemarks(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});


}

function setRemarks(data){
	var remarkList	= data.remarkList;
	$("#remarkTimeLine").empty();
	if(remarkList != undefined || remarkList != null){
		for(var index = 0 ; index < remarkList.length; index++){
			
			var columnArray = new Array();
			
			var assignee 	= "";
			var driver 	  	= "";
			
			

			if(remarkList[index].assignee != undefined && remarkList[index].assignee > 0){
				assignee =' <span class="bg-orange"> Confirmed With Assignee : '+remarkList[index].assigneeName+' </span>';
			}
			if(remarkList[index].driverId != undefined && remarkList[index].driverId > 0){
				driver =' <span class="bg-orange"> Confirmed With Driver : '+remarkList[index].driverName+' </span>';
			}
			
			
			
			columnArray.push('<li class="time-label">'
					+' <span style="background-color: orange;">'+remarkList[index].remarkType+' </span>'
					+' <span class="bg-red"> '+remarkList[index].creationDate+' </span>'
					+''+assignee+''
					+''+driver+''
					+'</li>'
					+'<li><i class="fa fa-comments bg-yellow"></i>'
					+'<div class="timeline-item">'
					+'<div class="timeline-body">'
					+''+remarkList[index].remark+''
					+'</div>'
					+'</div>'
					+'</li>');
				//console.log("columnArray"+columnArray);
			$('#remarkTimeLine').append(columnArray.join(' '));
		}
		columnArray = [];
	}
	hideLayer();
}

function getDseExtraIssue(){

		var jsonObject								= new Object();
		jsonObject["dealerServiceEntriesId"]		= $("#dealerServiceEntriesId").val();
		jsonObject["companyId"]						= $("#companyId").val();
		
		showLayer();
		$.ajax({
			url: "getDseExtraIssue",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				setDseExtraIssue(data);
			},
			error: function (e) {
				hideLayer();
				showMessage('errors', 'Some Error Occurred!');
			}
		});


	}

	function setDseExtraIssue(data){
		var extraIssueList	= data.extraIssueList;
		$("#extraIssueTimeLine").empty();
		if(extraIssueList != undefined || extraIssueList != null){
			for(var index = 0 ; index < extraIssueList.length; index++){
				var columnArray = new Array();
				columnArray.push('<li class="time-label">'
						+' <span class="bg-red"> '+extraIssueList[index].creationDate+' </span>'
						+'</li>'
						+'<li><i class="fa fa-comments bg-blue"></i>'
						+'<div class="timeline-item">'
						+'<div class="timeline-body">'
						+''+extraIssueList[index].description+''
						+'</div>'
						+'</div>'
						+'</li>');

				$('#extraIssueTimeLine').append(columnArray.join(' '));
			}
			columnArray = [];
			
			
			var dseExtraIssue = extraIssueList[0].description;
			dseExtraIssue = dseExtraIssue.substring(0, 100) + "</br></br></br>" + dseExtraIssue.substring(100,250) + "</br></br></br>" + dseExtraIssue.substring(250);
			$("#dseExtraIssue").html(dseExtraIssue);
		}
		hideLayer();
	}
	

	function getScheduleDataView(){

		var jsonObject						= new Object();
		jsonObject["serviceProgramId"]	= $('#serviceReminderId').val();
		jsonObject["serviceReminderIds"]	= $('#serviceReminderIds').val();
		jsonObject["companyId"]				= $('#companyId').val();
		
		$.ajax({
			url: "getServiceReminderByServiceId",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				setServiceScheduleDataView(data);
			},
			error: function (e) {
				hideLayer();
				showMessage('errors', 'Some Error Occurred!');
			}
		});
	}

	function setServiceScheduleDataView(data){
		if(data != null && data.serviceSchedules != undefined && data.serviceSchedules.length > 0){
			$('#serviceSchedules').show();
			var table = "";
			for (var key of Object.keys(data.scheduleHashMap)) {
				var serviceSchedulesList	= data.scheduleHashMap[key];
			/*	if(!(key in programROwMap)){
					programROwMap[key] = key;
					table += '<tr id="'+key+'">'
					+'<td colspan="4"><a style="font-size:16px;" href="#">'+key+'</a></td>'
					+'</tr>';
				}*/
				for(var i= 0; i<serviceSchedulesList.length; i++){
					if(!(serviceSchedulesList[i].service_id in selectedServiceProgram)){
						selectedServiceProgram[serviceSchedulesList[i].service_id] = serviceSchedulesList[i].service_id;
						table += '<tr id="row_'+serviceSchedulesList[i].service_id+'">'
						+'<td><a target="_blank" href="ShowService.in?service_id='+serviceSchedulesList[i].service_id+' " >S-'+serviceSchedulesList[i].service_Number+'</a></td>'
						+'<td>'+serviceSchedulesList[i].taskAndSchedule+'</td>'
						+'<td>'+serviceSchedulesList[i].nextDue+'</td>'
						+'</tr>';
					}
				}
			}

			$('#serviceSchedulesTable').append(table);   

			for (var key of Object.keys(selectedServiceProgram)) {
				var keyFound = false;
				for(var j= 0; j<data.serviceSchedules.length; j++){
					if((data.serviceSchedules[j].service_id == key)){
						keyFound = true;
					}
				}
				if(!keyFound){
					$('#row_'+key+'').remove();
					delete selectedServiceProgram[key];
				}
			}

			for (var key of Object.keys(programROwMap)) {
				if(!(key in data.scheduleHashMap)){
					$('#'+key+'').remove();
					delete programROwMap[key];
				}
			}

		}else{
			$('#serviceSchedulesTable').empty(); 
			selectedServiceProgram = new Map();
			programROwMap	= new Map();
			$('#serviceSchedules').hide();
		}
	}
	
	
	$(document).ready(function() {
		var a = 500,
		partType =1,
		b = $("#moreParts"),
		c = $(".add_field_button"),
		d = 1;
		$(c).click(function(c) {
			c.preventDefault(), a > d && (d++, $(b).append('<br> '
					+'<div>'
					+'<div class="row">'
					+'<label> <span '
					+'	style="color: #2e74e6; font-size: 14px; wdith: 20%;">Part'
					+'	Name</span>'
					+' </label> '
					+'<input type="hidden" name="partId" id="partId'+d+'" class="select2 form-text"  onchange="getLastOccurredDsePartDetails(this,lastPartOccurred'+d+',lastPartCost'+d+',lastPartDis'+d+',lastPartTax'+d+',false,partEachCost'+d+',partDiscount'+d+',partTax'+d+');">'
					+'	<samp id="lastPartOccurred'+d+'" > </samp>'
					+'</div> <br>'
					+'<div class="row"> <div class="col col-sm-1 col-md-2">'
					+'<label class="has-float-label">'
					+'<input type="text" class="form-control browser-default custom-select noBackGround" name="partQty" id="partQty'+d+'"   onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="javascript:sumthere(\'partQty' + d + "', 'partEachCost" + d + "', 'partDiscount" + d + "','partTax" + d + "', 'partTotalCost" + d +"','"+partType+ '\' );">'
					+'<span style="color: #2e74e6;font-size: 18px;">Qty</span>'
					+'</label>'
					+'</div>'
					+'<div class="col col-sm-1 col-md-2">'
					+'<label class="has-float-label">'
					+'<input type="text" class="form-control browser-default custom-select noBackGround" name="partEachCost" id="partEachCost'+d+'"  onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="javascript:sumthere(\'partQty' + d + "', 'partEachCost" + d + "', 'partDiscount" + d + "','partTax" + d + "', 'partTotalCost" + d + "','"+partType+'\' );">'
					+'<span style="color: #2e74e6;font-size: 18px;">Cost</span>'
					+'</label>'
					+'<samp id="lastPartCost'+d+'"> </samp>'
					+'</div>'
					+'<div class="col col-sm-1 col-md-2"> '
					+'<div class="input-group"> '
					+'<input type="text" class="form-control allPartDiscount" placeholder="Discount" '
					+'	onpaste="return false" name="partDiscount" id="partDiscount'+d+'" maxlength="5" '
					+'		onkeypress="return isNumberKeyWithDecimal(event,this.id);" '
					+'			onkeyup="javascript:sumthere(\'partQty' + d + "', 'partEachCost" + d + "', 'partDiscount" + d + "','partTax" + d + "', 'partTotalCost" + d + "','"+partType+'\' );" '
					+'		min="0.0"> <span class="input-group-addon"><em class="fa fa-percent partPercent"></em></span> '
					+'	 <samp id="lastPartDis'+d+'"> </samp> '
					+'</div> '
					+'</div> '
					
					+'<div class="col col-sm-1 col-md-2">'
					+'<div class="input-group">'
					+'<input type="text" class="form-control allPartTax " placeholder="GST"'
					+'onpaste="return false" name="partTax" id="partTax'+d+'" maxlength="5" '
					+'onkeypress="return isNumberKeyWithDecimal(event,this.id);" '
					+'	onkeyup="javascript:sumthere(\'partQty' + d + "', 'partEachCost" + d + "', 'partDiscount" + d + "','partTax" + d + "', 'partTotalCost" + d + "','"+partType+'\' );" '
					+'	min="0.0"> <span class="input-group-addon"><em class="fa fa-percent partPercent"></em></span>'
					+' <samp id="lastPartTax'+d+'"> </samp>'
					+'</div>'
					+'</div>'
					+'<div class="col col-sm-1 col-md-2">'
					+'<label class="has-float-label">'
					+'<input type="text" class="form-control browser-default custom-select noBackGround allPartTotalCost" name="partTotalCost" id="partTotalCost'+d+'"  readonly="readonly"  onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="javascript:sumthere(\'partQty' + d + "', 'partEachCost" + d + "', 'partDiscount" + d + "','partTax" + d + "', 'partTotalCost" + d + "','"+partType+'\' );">'
					+'<span style="color: #2e74e6;font-size: 18px;" >Total Cost</span>'
					+'</label>'
					+'</div>'
					+'</div>'
					+'<a href="#" class="removePart"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font><br></div>'), 
					$("#partId"+d).select2({
						minimumInputLength: 3,
						minimumResultsForSearch: 10,
						ajax: {
							url: "searchAllMasterParts.in?Action=FuncionarioSelect2",
							dataType: "json",
							type: "POST",
							contentType: "application/json",
							quietMillis: 50,
							data: function(e) {
								return {
									term: e
								}
							},
							results: function(e) {
								return {
									results: $.map(e, function(e) {
										return {
											text: e.partnumber + " - " + e.partname + " - " + e.category + " - " + e.make,
											slug: e.slug,
											id: e.partid,
											partNumber: e.partnumber,
											isWarrantyApplicable: e.isWarrantyApplicable,
											warrantyInMonths: e.warrantyInMonths
										}
									})
								}
							}
						},
						//Allow manually entered text in drop down.
						createSearchChoice:function(term, results) {
							if ($(results).filter( function() {
								return term.localeCompare(this.text)===0; 
							}).length===0) {
								return {id:term, text:term + ' [New]'};
							}
						},
					})
			)
			if($('#finalPartDiscountTaxTypId').val() == 2){
				$('.partPercent').removeClass('fa-percent');
				$('.partPercent').addClass('fa-inr');
			}else{
				$('.partPercent').addClass('fa-percent');
				$('.partPercent').removeClass('fa-inr');
			}
		}), $(b).on("click", ".removePart", function(a) {
			a.preventDefault(), $(this).parent("div").remove(), d--
		})
	});
	
	
	$(document).ready(function() {
		var a = 500,
		labourType 		= 2,
		b = $(".addMoreLabourDiv"),
		c = $(".addMoreLabourButton"),
		d = 1;
		$(c).click(function(c) {
			c.preventDefault(), a > d && (d++, $(b).append('<br> <div>'
//					+'<label class="has-float-label">'
//					+'<input type="text" class="browser-default"  style="line-height: 30px;font-size: 15px;height: 35px;" name="labourName"  id="labourName'+d+'"  >'
//					+'<span style="color: #2e74e6;font-size: 18px;">Labour Type</span>'
//					+'</label>'
//					+'</div>'
					
					+'<div class="row">'
					+'<label> <span'
					+'	style="color: #2e74e6; font-size: 14px; wdith: 20%;">Labour'
					+'		Type</span>'
					+'</label> <input type="hidden"  class="browser-default" style="line-height: 30px;font-size: 15px;height: 35px;" '
					+'name="labourName" id="labourName'+d+'" class="form-control">'
					+'</div> <br>'
					
					+'<div class="row">'
					+'<div class="col col-sm-1 col-md-2">'
					+'<label class="has-float-label">'
					+'<input type="text" class="form-control browser-default custom-select noBackGround" placeholder="Hr/Km"  name="labourWorkingHours"  id="labourWorkingHours'+d+'"  onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="javascript:sumthere(\'labourWorkingHours' + d + "', 'labourPerHourCost" + d + "', 'labourDiscount" + d + "','labourTax" + d + "', 'totalLabourCost" + d + "','"+labourType+'\' );">'
					+'<span style="color: #2e74e6;font-size: 18px;">Hours/Km</span>'
					+'</label>'
					+'</div>'
					+'<div class="col col-sm-1 col-md-2">'
					+'<label class="has-float-label">'
					+'<input type="text" class="form-control browser-default custom-select noBackGround" placeholder="Hours/Km Cost" name="labourPerHourCost"  id="labourPerHourCost'+d+'"  onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="javascript:sumthere(\'labourWorkingHours' + d + "', 'labourPerHourCost" + d + "', 'labourDiscount" + d + "','labourTax" + d + "', 'totalLabourCost" + d + "','"+labourType+'\' );">'
					+'<span style="color: #2e74e6;font-size: 18px;">Rate</span>'
					+'</label>'
					+'</div>'
					+'<div class="col col-sm-1 col-md-2">'
					+'<label class="has-float-label">'
					+'<input type="text" class="form-control browser-default  noBackGround allLabourDiscount" placeholder="Discount" id="labourDiscount'+d+'" name="labourDiscount"   onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="validateLabourTaxDiscount(this.id); javascript:sumthere(\'labourWorkingHours' + d + "', 'labourPerHourCost" + d + "', 'labourDiscount" + d + "','labourTax" + d + "', 'totalLabourCost" + d +"','"+labourType+ '\' ); ">'
					+'<span style="color: #2e74e6;font-size: 18px;" >Dis <i class="fa fa-percent labourPercent"></i></span>'
					+'</label>'
					+'</div>'
					+'<div class="col col-sm-1 col-md-2">'
					+'<label class="has-float-label">'
					+'<input type="text" class="form-control browser-default  noBackGround allLabourTax " placeholder="GST"  id="labourTax'+d+'" name="labourTax"  onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="validateLabourTaxDiscount(this.id); javascript:sumthere(\'labourWorkingHours' + d + "', 'labourPerHourCost" + d + "', 'labourDiscount" + d + "','labourTax" + d + "', 'totalLabourCost" + d +"','"+labourType+ '\' ); ">'
					+'<span style="color: #2e74e6;font-size: 18px;" >GST <i class="fa fa-percent labourPercent"></i></span>'
					+'</label>'
					+'</div>'
					+'<div class="col col-sm-1 col-md-2">'
					+'<label class="has-float-label">'
					+'<input type="text" class="form-control browser-default custom-select noBackGround allLabourTotalCost" name="totalLabourCost"  id="totalLabourCost'+d+'"  readonly="readonly" onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="javascript:sumthere(\'labourWorkingHours' + d + "', 'labourPerHourCost" + d + "', 'labourDiscount" + d + "','labourTax" + d + "', 'totalLabourCost" + d +"','"+labourType+ '\' );">'
					+'<span style="color: #2e74e6;font-size: 18px;" >Total Cost</span>'
					+'</label>'
					+'</div>'
					+'</div>'
					+'<a href="#" class="removeLabour"><font color="FF00000"><i class="fa fa-trash"></i> Remove </a></font><br>'
					+'</div>'),
					$("#labourName"+d).select2({
						minimumInputLength: 3,
						minimumResultsForSearch: 10,
						ajax: {
							url: "labourAutoComplete.in?Action=FuncionarioSelect2",
							dataType: "json",
							type: "POST",
							contentType: "application/json",
							quietMillis: 50,
							data: function(e) {
								return {
									term: e
								}
							},
							results: function(e) {
								return {
									results: $.map(e, function(e) {
										return {
											text: e.labourName,
											slug: e.slug,
											id: e.labourId
										}
									})
								}
							}
						},createSearchChoice:function(term, results) {
						if ($(results).filter( function() {
							return term.localeCompare(this.text)===0; 
						}).length===0) {
							return {id:term, text:term + ' [New]'};
						}
					}
					
					})
			)
			if($('#finalLabourDiscountTaxTypId').val() == 2){
				$('.labourPercent').removeClass('fa-percent');
				$('.labourPercent').addClass('fa-inr');
			}else{
				$('.labourPercent').addClass('fa-percent');
				$('.labourPercent').removeClass('fa-inr');
			}
		}), $(b).on("click", ".removeLabour", function(a) {
			a.preventDefault(), $(this).parent("div").remove(), d--
		})
	});
	
	function mountTyre(){
		window.location.replace("vehicleTyreAssignmentFromDSE.in?dseId="+$("#dealerServiceEntriesId").val()+"");
	}
	
	function setDealerServiceEntriesTyre (data){
		
		$("#tyreAssignTable").empty();
		
		var vehicleTyreLayoutPositionList = data.vehicleTyreLayoutPositionList;
		if(vehicleTyreLayoutPositionList != undefined || vehicleTyreLayoutPositionList != null){
			for(var index = 0 ; index < vehicleTyreLayoutPositionList.length; index++){
				
				var columnArray = new Array();
				columnArray.push("<td class='fit'>"+(index+1)+"</td>");
				columnArray.push("<td class='fit'ar> <h4> "+ vehicleTyreLayoutPositionList[index].position  +"</td>");
				columnArray.push("<td class='fit'ar> <h4> "+ vehicleTyreLayoutPositionList[index].tyre_SERIAL_NO  +"</td>");
				
				
				
				$('#tyreAssignTable').append("<tr  >" + columnArray.join(' ') + "</tr>");
				
			}
			
			$("#tyreAssignDiv").show();
			columnArray = [];
			}
		}
