var fromWOEdit	= true;
$(document).ready(function() {
	var jsonObject					= new Object();

	jsonObject["dealerServiceEntriesId"]	= $("#dealerServiceEntriesId").val();
	jsonObject["companyId"]					= $("#companyId").val();

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
	var dealerServiceEntries = data.dealerServiceEntries;
	
	$('#vehicleId').select2('data', {
		id : dealerServiceEntries.vid,
		text : dealerServiceEntries.vehicleNumber
	});
	$("#vehicleOdometer").val(dealerServiceEntries.vehicleOdometer);
	$("#editOdometer").val(dealerServiceEntries.vehicleOdometer);
	
	$('#vendorId').select2('data', {
		id : dealerServiceEntries.vendorId,
		text : dealerServiceEntries.vendorName
	});
	$("#vendorId").change();
	$('#driverId').select2('data', {
		id : dealerServiceEntries.driverId,
		text : dealerServiceEntries.driverFullName
	});
	$('#assignToId').select2('data', {
		id : dealerServiceEntries.assignToId,
		text : dealerServiceEntries.assignTo
	});
	$("#serviceReminderId").select2({
   	 multiple: !0,
   	 allowClear: !0,
        data: ''
    })
	
	$("#invoicestartDate").val(dealerServiceEntries.invoiceDateStr);
	
	$("#dealerServiceEntriesNumber").html(dealerServiceEntries.dealerServiceEntriesNumberStr);
	$("#invoiceNumber").val(dealerServiceEntries.invoiceNumber);
	$("#jobNumber").val(dealerServiceEntries.jobNumber);
	$("#transactionNumber").val(dealerServiceEntries.payNumber);
	$("#paymentTypeId").append($("<option selected='selected'>").text(dealerServiceEntries.paymentType).attr("value",dealerServiceEntries.paymentTypeId));
	$("#serviceReminderIds").val(dealerServiceEntries.serviceReminderIds);
	
	getOdometer();
	backdateOdometerValidation();
	
	
	if(data.issueNumb != undefined && data.issueNumb != ""){
		//$("#issueId").val(dealerServiceEntries.issueId);
		$("#issueVehicleNumber").val(dealerServiceEntries.vehicleNumber);
	//	$("#issueDetail").html(dealerServiceEntries.issueNumberStr +","+ dealerServiceEntries.issueSummary);
		$("#issueDetail").val(data.issueNumb);
		$('#issueVidDiv').show();
		$('#vidDiv').hide();
		$('#editIssueDiv').show();
	}else{
		$('#vidDiv').show();
		$('#issueVidDiv').hide();
		$('#editIssueDiv').hide();
	}
	
	if(data.dealerServiceEntriesPart != undefined && data.dealerServiceEntriesPart[0].partDiscountTaxTypeId == 1){
		$('#partPercentLabelId').addClass('focus active')
		$('#partAmountLabelId').removeClass('focus active')
		$("#partAmountId").prop('checked',false);
		$("#partPercentId").prop('checked',true);
		
	}else{
		$('#partPercentLabelId').removeClass('focus active')
		$('#partAmountLabelId').addClass('focus active')
		$("#partAmountId").prop('checked',true);
		$("#partPercentId").prop('checked',false);
	}
	
	if(data.dealerServiceEntriesLabour != undefined && data.dealerServiceEntriesLabour[0].labourDiscountTaxTypeId == 1){
		$('#labourPercentLabelId').addClass('focus active')
		$('#labourAmountLabelId').removeClass('focus active')
		$("#labourPercentId").prop('checked',true);
		$("#labourAmountId").prop('checked',false);
		
	}else{
		$('#labourPercentLabelId').removeClass('focus active')
		$('#labourAmountLabelId').addClass('focus active')
		$("#labourPercentId").prop('checked',false);
		$("#labourAmountId").prop('checked',true);
	}
	
	if(data.dealerServiceEntriesPart != undefined){
		$('#finalPartDiscountTaxTypId').val(data.dealerServiceEntriesPart[0].partDiscountTaxTypeId);
	}else{
		$('#finalPartDiscountTaxTypId').val(1);
	}
	if(data.dealerServiceEntriesLabour != undefined){
		$('#finalLabourDiscountTaxTypId').val(data.dealerServiceEntriesLabour[0].labourDiscountTaxTypeId);
	}else{
		$('#finalLabourDiscountTaxTypId').val(1);
	}
	if(dealerServiceEntries.meterNotWorkingId == true){
		$("#meterNotWorkingId").prop('checked',true);
	}else{
		$("#meterNotWorkingId").prop('checked',false);
	}
	
	setDsePartDetails(data.dealerServiceEntriesPart);
	setDseLabourDetails(data.dealerServiceEntriesLabour);
	setServiceProgramList(data.serviceProgramList);
	
	
}

function getOdometer() {
	$.getJSON("getVehicleOdoMerete.in", {
        vehicleID: $("#vehicleId").val(),
        ajax: "true"
    }, function(a) {
        var b = "";
        b = a.vehicle_Odometer, document.getElementById("vehicleOdometer").placeholder = b;
        $('#vehicle_ExpectedOdameter').val(a.vehicle_ExpectedOdameter);
        $('#vehicle_Odameter').val(a.vehicle_Odometer);
    })
}

function setDsePartDetails(dealerServiceEntriesPart){
	if(dealerServiceEntriesPart != undefined || dealerServiceEntriesPart != null){
		if(dealerServiceEntriesPart[0].partDiscountTaxTypeId == 2){
			$('.partPercent').removeClass('fa-percent');
			$('.partPercent').addClass('fa-inr');
		}else{
			$('.partPercent').addClass('fa-percent');
			$('.partPercent').removeClass('fa-inr');
		}
		for(var i = 0 ; i < dealerServiceEntriesPart.length; i++){
			$("#partId"+dealerServiceEntriesPart[i].dealerServiceEntriesPartId+"").select2({
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
			});
			
			$("#partId"+dealerServiceEntriesPart[i].dealerServiceEntriesPartId+"").select2('data', {
				id : dealerServiceEntriesPart[i].partId,
				text :dealerServiceEntriesPart[i].partName,
				isWarrantyApplicable: dealerServiceEntriesPart[i].warrantyApplicable,
				warrantyInMonths: dealerServiceEntriesPart[i].warrantyInMonths
			});
			
			if(dealerServiceEntriesPart[i].warrantyApplicable == true){
				$("#warrantyStatus"+dealerServiceEntriesPart[i].dealerServiceEntriesPartId+"").html('Part In Warranty')
			}
		}
	}
}
function setDseLabourDetails(dealerServiceEntriesLabour){
	if(dealerServiceEntriesLabour != undefined || dealerServiceEntriesLabour != null){
		if(dealerServiceEntriesLabour[0].labourDiscountTaxTypeId == 2){
			$('.labourPercent').removeClass('fa-percent');
			$('.labourPercent').addClass('fa-inr');
		}else{
			$('.labourPercent').addClass('fa-percent');
			$('.labourPercent').removeClass('fa-inr');
		}
		for(var i = 0 ; i < dealerServiceEntriesLabour.length; i++){
			
			$("#labourName"+dealerServiceEntriesLabour[i].dealerServiceEntriesLabourId+"").select2({
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
				}
			});
			
			$("#labourName"+dealerServiceEntriesLabour[i].dealerServiceEntriesLabourId+"").select2('data', {
				id : dealerServiceEntriesLabour[i].labourId,
				text :dealerServiceEntriesLabour[i].labourName
			});
		}
	}
}


$(document).ready(
		function($) {
			$('button[id=submit]').click(function(e) {
				e.preventDefault();
				showLayer();
				if($('#vehicleId').val() == "" || $('#vehicleId').val() == undefined){
					showMessage('info','Please Select Vehicle');
					hideLayer();
					return false;
				}
				if($('#vendorId').val() == "" || $('#vendorId').val() == undefined){
					showMessage('info','Please Select Vendor');
					hideLayer();
					return false;
				}
				if(($('#invoicestartDate').val() == undefined ||  $('#invoicestartDate').val() == "") ){
					showMessage('info','Please Select Invoice Date');
					hideLayer();
					return false;
				}
				
				if($('#validateOdometer').val() == true ||$('#validateOdometer').val() === 'true'){
					if(!validateOdometer()){
						hideLayer();
						return false;
					}
				}
				
				var isVendorIdNan			= isNaN(Number($('#vendorId').val()))
				
				if(isVendorIdNan == true){
					$("#modalVendorName").val($('#vendorId').val());
					hideLayer();
					$("#vendorModal").modal('show');
					return false;
				}
				if (validateBankPaymentDetails && !validateBankPayment($('#bankPaymentTypeId').val())) {
					return false;
				}
				
				var jsonObject			= new Object();
				
				var labourArray	 		= new Array();
				var partArray	 		= new Array();

				var dealerServiceEntriesLabourIdArr 			= new Array();
				var labourNameArr 								= new Array();
				var labourHourArr 								= new Array();
				var labourRateArr 								= new Array();
				var labourDisArr 								= new Array();
				var labourTaxArr 								= new Array();
				var labourTotalCostArr 							= new Array();

				var dealerServiceEntriesPartIdArr 				= new Array();
				var selectedPartIdArr 							= new Array();
				var partNameArr 								= new Array();
				var partQtyArr 									= new Array();
				var partRateArr 								= new Array();
				var partDisArr 									= new Array();
				var partTaxArr 									= new Array();
				var partTotalCostArr 							= new Array();
				
				var partNumberArr 			= new Array();
				var partWarrantyArr 		= new Array();
				var partWarrantyMonthArr 	= new Array();
				
				$("input[name=dealerServiceEntriesLabourId]").each(function(){
					dealerServiceEntriesLabourIdArr.push($(this).val().replace(/"/g, ""));
				});
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
				});


				$("input[name=DealerServiceEntriesPartId]").each(function(){
					dealerServiceEntriesPartIdArr.push($(this).val());
				});
				$("input[name=partId]").each(function(){
					selectedPartIdArr.push(this.id)
					partNameArr.push($(this).val());
					if($(this).val() != ""){
						partWarrantyArr.push($('#'+this.id).select2('data').isWarrantyApplicable);
						partWarrantyMonthArr.push($('#'+this.id).select2('data').warrantyInMonths);
					}
				});
				$("input[name=partQty]").each(function(){
					partQtyArr.push($(this).val().replace(/"/g, ""));
				});
				$("input[name=partEachCost]").each(function(){
					partRateArr.push($(this).val());
				});
				$("input[name=partDiscount]").each(function(){
					partDisArr.push($(this).val());
				});
				$("input[name=partTax]").each(function(){
					partTaxArr.push($(this).val());
				});
				$("input[name=partTotalCost]").each(function(){
					partTotalCostArr.push($(this).val());
			//		totalPartCost += Number($(this).val());
				});
				
				for(var i =0 ; i< labourNameArr.length; i++){
					var labourDetails					= new Object();
					if(labourNameArr[i] != ""){
						if(labourNameArr[i] > 0 && (labourTotalCostArr[i] == '' || labourTotalCostArr[i] == 0)){
							showMessage('info','Please Enter Labour Details');
							hideLayer();
							return false;
						}
						labourDetails.dealerServiceEntriesLabourId				= dealerServiceEntriesLabourIdArr[i];
						labourDetails.labourId									= labourNameArr[i];
						labourDetails.labourWorkingHours						= labourHourArr[i];
						labourDetails.labourPerHourCost							= labourRateArr[i];
						labourDetails.labourTax									= labourTaxArr[i];
						labourDetails.labourDiscount							= labourDisArr[i];
						labourDetails.totalLabourCost							= labourTotalCostArr[i];
						
						labourArray.push(labourDetails);
					}
				}

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
							$("#selectedPartId").val(selectedPartIdArr[i]); // this is the dynamic id of part autocomplete
							$("#modalPartName").val(partNameArr[i]); // value display on modal
							$("#partNumberModal").modal('show');
							return false;
							
						}
						if(partNameArr[i] > 0 && (partTotalCostArr[i] == '' || partTotalCostArr[i] == 0)){
							showMessage('info','Please Enter Part Details');
							hideLayer();
							return false;
						}
						partDetails.dealerServiceEntriesPartId					= dealerServiceEntriesPartIdArr[i];
						partDetails.partQty										= partQtyArr[i];
						partDetails.partCost									= partRateArr[i];
						partDetails.partTax										= partTaxArr[i];
						partDetails.partDiscount								= partDisArr[i];
						partDetails.totalPartCost								= partTotalCostArr[i];
						partDetails.isWarrantyApplicable						= partWarrantyArr[i];
						partDetails.warrantyInMonths							= partWarrantyMonthArr[i];
	
						partArray.push(partDetails);
					}
				}
				jsonObject.labourDetails 				= JSON.stringify(labourArray);
				jsonObject.partDetails 					= JSON.stringify(partArray);
				
				jsonObject["dealerServiceEntriesId"]	= $('#dealerServiceEntriesId').val();
				jsonObject["vid"]						= $('#vehicleId').val();
				jsonObject["vehicleOdometer"]			= $('#vehicleOdometer').val();
				jsonObject["invoiceNumber"]				= $('#invoiceNumber').val();
				jsonObject["jobNumber"]					= $('#jobNumber').val();
				jsonObject["vendorId"]					= $('#vendorId').val();
				jsonObject["paymentTypeId"]				= $('#paymentTypeId').val();
				jsonObject["invoiceDate"]				= $('#invoicestartDate').val();
				jsonObject["transactionNumber"]			= $('#transactionNumber').val();
				jsonObject["companyId"]					= $('#companyId').val();
				jsonObject["userId"]					= $('#userId').val();
				jsonObject["driverId"]					= $('#driverId').val();
				jsonObject["partDiscountTaxTypeId"]			= $('#finalPartDiscountTaxTypId').val();
				jsonObject["labourDiscountTaxTypeId"]		= $('#finalLabourDiscountTaxTypId').val();
				jsonObject["assignToId"]					= $('#assignToId').val();
				jsonObject["meterNotWorkingId"]					= $('#meterNotWorkingId').val();
				
				var serviceIds = '';
				if($('#showServiceProgram').val() == 'true'){
				$("input[name=selectedSchedule]").each(function(){
					if($('#'+this.id+'').prop('checked')){
						serviceIds += this.id+',';
					}
				});
				}
				if($('#showServRemindWhileCreating').val() == 'true' && $('#serviceReminderId').val() != ''){
					serviceIds=$('#serviceReminderId').val().toString();
				}
				
				jsonObject["serviceReminderId"]	=  serviceIds;
				
				var form = $('#fileUploadForm')[0];
				var data = new FormData(form);
				
				if (allowBankPaymentDetails) {
					prepareObject(jsonObject);
				}

				data.append("dealerServiceEntryData", JSON.stringify(jsonObject)); 
				
				$.ajax({
					type: "POST",
					enctype: 'multipart/form-data',
					url: "updateDealerServiceEntries",
					data: data,
					processData: false, //prevent jQuery from automatically transforming the data into a query string
			        contentType: false,
			        cache: false,
					success: function (data) {
						if(data.sequenceCounterNotFound != undefined && (data.sequenceCounterNotFound == true || data.sequenceCounterNotFound == 'true' )){
							showMessage('info','Sequence Not Found Please Contact To System Administrator');
							hideLayer
						}else if(data.alreadyExist != undefined && (data.alreadyExist == true || data.alreadyExist == 'true' )){
							showMessage('info','Already Exist');
							hideLayer
						}else if(data.inSold != undefined && (data.inSold == true || data.inSold == 'true' )){
							showMessage('info','Vehicle Is In Sold Status');
							hideLayer
						} else if(data.inActive != undefined && (data.inActive == true || data.inActive == 'true' )){
							showMessage('info','Vehicle Is In In-Active Status');
							hideLayer
						}else if(data.inSurrender != undefined && (data.inSurrender == true || data.inSurrender == 'true' )){
							showMessage('info','Vehicle Is In Surrneder Status');
							hideLayer
						}else{
							showMessage('success','Save Successfully'); 
							window.location.replace("showDealerServiceEntries?dealerServiceEntriesId="+ $('#dealerServiceEntriesId').val()+"");
						}
						hideLayer();
					},
					error: function (e) {
						hideLayer();
						showMessage('errors', 'Some Error Occurred!');
					}
				});
				
				
			});
		});

function checkWarrantyPart(dsePartId){

	if($("#partId"+dsePartId+"").val() != "" && $("#partId"+dsePartId+"").select2('data').isWarrantyApplicable == true){
		$("#warrantyStatus"+dsePartId+"").html('Part In Warranty')
	}else{
		$("#warrantyStatus"+dsePartId+"").html('')
	}


}