$(document).ready(function () {
	showVehicleAccidentDetails();
});
function showVehicleAccidentDetails(){
	var jsonObject					= new Object();
	jsonObject["accidentId"]		= $("#accidentId").val();
	jsonObject["multipleQuotation"]		= $("#multipleQuotation").val();
	
	showLayer();
	$.ajax({
		url: "showVehicleAccidentDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setVehicleAccidentDetails(data);
			setStartDateTimeForAccidentMoudle(data.accidentDetails, data.serveyorDetails);
			hideLayer();
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setVehicleAccidentDetails(data){
	
	if(data.accidentDetails != undefined){
		if(data.incidentDocCount != undefined){
			$('#incidentDocCount').html(data.incidentDocCount);
		}
		if(data.advanceCount != undefined){
			$('#advanceCount').html(data.advanceCount);
		}
		if(data.expenseCount != undefined){
			$('#expenseCount').html(data.expenseCount);
		}
		
		
		$('#vid').val(data.accidentDetails.vid);
		$('#driverId').val(data.accidentDetails.driverId);
		$('#status').val(data.accidentDetails.status);
		$('#accidentDate').val(data.accidentDetails.accidentDate);
		
		
		$('#accidentNumber').html('<a href="#">AD-'+data.accidentDetails.accidentDetailsNumber+' </a>');
		$('#accidentStatus').html('<span class="label label-default label-primary">'+data.accidentDetails.statuStr+'</span>');
		/*$('#vehicleNumber').html(data.accidentDetails.vehicleNumber);*/
		$("#vehicleNumber").html("<a href='showVehicle?vid="+data.accidentDetails.vid+"' target='_blank'>"+data.accidentDetails.vehicleNumber+"</a>");
		$('#accidentDateTime').html(data.accidentDetails.accidentDateTimeStr);
		/*$('#driver').html(data.accidentDetails.driverName);*/
		$("#driver").html("<a href='showDriver.in?driver_id="+data.accidentDetails.driverId+"' target='_blank'>"+data.accidentDetails.driverName+"</a>");
		if(data.accidentDetails.tripSheetId != undefined && data.accidentDetails.tripSheetId > 0){
			$('#tripSheetNumber').html('<a target="_blank" href="showTripSheet.in?tripSheetID='+data.accidentDetails.tripSheetId+'">TS-'+data.accidentDetails.tripSheetNumber+'</a>');
		}
		
		$('#route').html(data.accidentDetails.route);
		$('#description').html(data.accidentDetails.description);
		
		$('#firNumber').html(data.accidentDetails.firNumber);
		$('#polishStation').html(data.accidentDetails.firPoliceStation);
		$('#firBy').html(data.accidentDetails.firBy);
		$('#firRemark').html(data.accidentDetails.firRemark);
		if(data.accidentDetails.paymentDate != undefined){
			$('#pDate').html(data.accidentDetails.paymentDateStr);
			$('#pAmount').html(data.accidentDetails.paymentAmount);
			$('#pRemark').html(data.accidentDetails.paymentRemark);
			$('#qRemark').html(data.accidentDetails.queryRemark);
		}
		if(data.accidentDetails.serviceId != undefined && data.accidentDetails.serviceId > 0){
			$('#serviceId').val(data.accidentDetails.serviceId);
			$('#createSE').hide();
			$('#createWO').hide();
		}
		
		$('#otherVehicle').html(data.accidentDetails.accidentWithVehicle);
		$('#accidentWithDriver').html(data.accidentDetails.accidentWithDriver);
		$('#accidentWithDriverrMobile').html(data.accidentDetails.accidentWithDriverrMobile);
		$('#accidentWithOwner').html(data.accidentDetails.accidentWithOwner);
		$('#polishStation').html(data.accidentDetails.firPoliceStation);
		
		$('#createdBy').html(data.accidentDetails.createdBy);
		$('#createdOnStr').html(data.accidentDetails.createdOnStr);
		$('#lastUpdatedBy').html(data.accidentDetails.lastUpdatedBy);
		$('#lastUpdatedOnStr').html(data.accidentDetails.lastUpdatedOnStr);
		
		if(data.accidentDetails.paymentDate != undefined){
			$('#paymentDateStr').val(data.accidentDetails.paymentDateStr);
			$('#paymentAmount').val(data.accidentDetails.paymentAmount);
			$('#paymentRemark').val(data.accidentDetails.paymentRemark);
			$('#queryRemark').val(data.accidentDetails.queryRemark);
		}
		
		if(data.serveyorDetails != undefined){
			$('#sDetailsDiv').show();
			$('#sName').html(data.serveyorDetails.serveyorName);
			$('#sMobile').html(data.serveyorDetails.serveyorMobile);
			$('#sCompany').html(data.serveyorDetails.serveyorCompany);
			$('#sRemark').html(data.serveyorDetails.remark);
			if($('#accidentAdditionalFields').val() == 'true'){
			if(data.serveyInformDate != undefined){
				var setTime =data.serveyInformDate;
				if(data.serveyInformTime != undefined)
					setTime+=" "+data.serveyInformTime;
				$('#sInformDate').html(setTime);
			}
			}
			
			if(data.serveyorDetails.completionDate != undefined){
				$('#sCompletionDate').html(formatOnlyDate(data.serveyorDetails.completionDate));
				$('#sCompletionRemark').html(data.serveyorDetails.completionRemark);}
			if(data.serveyorDetails.finalDamageServeyDate != undefined){
				$('#finalSCompletionDate').html(formatOnlyDate(data.serveyorDetails.finalDamageServeyDate));
				$('#finalSCompletionRemark').html(data.serveyorDetails.finalDamageServeyRemark);
			}
			
			if(data.serveyorDetails.quotationApprovalDate != undefined){
				$('#sApprovalDate').html(formatOnlyDate(data.serveyorDetails.quotationApprovalDate));
				$('#sApprovalRemark').html(data.serveyorDetails.quotationApprovalRemark);
			}
			
			if(data.serveyorDetails.finalInspectionDate != undefined){
				$('#sInspectionDate').html(formatOnlyDate(data.serveyorDetails.finalInspectionDate));
				$('#sInspectionRemark').html(data.serveyorDetails.finalInspectionRemark);
			}
			
			$('#serveyorName').val(data.serveyorDetails.serveyorName);
			$('#serveyorMobile').val(data.serveyorDetails.serveyorMobile);
			$('#serveyorCompany').val(data.serveyorDetails.serveyorCompany);
			$('#serveyorDetailsId').val(data.serveyorDetails.serveyorDetailsId);
			$('#remark').val(data.serveyorDetails.remark);
			$('#completionDate').val(formatOnlyDate(data.serveyorDetails.completionDate));
			$('#completionRemark').val(data.serveyorDetails.completionRemark);
			
			if($('#accidentAdditionalFields').val() == 'true'){
				if(data.serveyInformDate != undefined)
				$('#surveyorDate').val(data.serveyInformDate);
				if(data.serveyInformTime != undefined)
				$('#surveyorTime').val(data.serveyInformTime);
				
			}
			if(data.serveyorDetails.completionDate != undefined){
				$('#completionDate').val(formatOnlyDate(data.serveyorDetails.completionDate));
				$('#completionRemark').val(data.serveyorDetails.completionRemark);
			}
			if(data.serveyorDetails.finalDamageServeyDate != undefined){
				$('#finalSDate').val(formatOnlyDate(data.serveyorDetails.finalDamageServeyDate));
				$('#finalSRemark').val(data.serveyorDetails.finalDamageServeyRemark);
				
				$('#finalServeyorName').val(data.serveyorDetails.finalServeyorName);
				$('#finalServeyorMobile').val(data.serveyorDetails.finalServeyorMobile);
				$('#finalServeyorEmail').val(data.serveyorDetails.finalServeyorEmail);
			//	$('#finalServeyorDept').val(data.serveyorDetails.finalServeyorDept);
				$('#finalServeyorClaimNum').val(data.serveyorDetails.finalServeyorClaimNum);
				
				$('#sFinalSuveyorName').html(data.serveyorDetails.finalServeyorName);
				$('#sFinalSuveyorMobile').html(data.serveyorDetails.finalServeyorMobile);
				$('#sFinalSuveyorEmail').html(data.serveyorDetails.finalServeyorEmail);
				$('#sFinalSuveyorClaimNum').html(data.serveyorDetails.finalServeyorClaimNum);
				
			}
			if(data.serveyorDetails.quotationApprovalDate != undefined && data.serveyorDetails.quotationApprovalDate != null){
				
				$('#approvalDate').val(formatOnlyDate(data.serveyorDetails.quotationApprovalDate));
				$('#approvalRemark').val(data.serveyorDetails.quotationApprovalRemark);
			}
			if(data.serveyorDetails.finalInspectionDate != undefined){
				$('#inspectionDate').val(formatOnlyDate(data.serveyorDetails.finalInspectionDate));
				$('#salvageAmountKey').html(data.serveyorDetails.salvageAmount);
				$('#salvageAmount').val(data.serveyorDetails.salvageAmount);
				$('#inspectionRemark').val(data.serveyorDetails.finalInspectionRemark);
			}
			
			if(data.serveyorDetails.keepOpenRemark != undefined){
				$('#keepOpenRemark').val(data.serveyorDetails.keepOpenRemark);
				$('#sKeepOpenRemark').html(data.serveyorDetails.keepOpenRemark);
				
			}
			if(data.serveyorDetails.keepOpenDate != undefined){
				$('#keepOpenDate').val(formatOnlyDate(data.serveyorDetails.keepOpenDate));
				$('#sKeepOpenDate').html(formatOnlyDate(data.serveyorDetails.keepOpenDate));
				$("#keepOpenFlag").val(true);
				$('#step10').css('color', 'green');
		        $('#link10').css('color', 'green');
		        $('#stepCheck10').show();
			}
			if(data.serveyorDetails.insuranceSubmitDate != undefined){
				$("#beforeApprovalFlag").val(true);
				$('#step11').css('color', 'green');
		        $('#link11').css('color', 'green');
		        $('#stepCheck11').show();
				$('#sInsuranceSubmitDate').html(formatOnlyDate(data.serveyorDetails.insuranceSubmitDate));
				$('#insuranceSubmitDate').val(formatOnlyDate(data.serveyorDetails.insuranceSubmitDate));
			}
			if(data.serveyorDetails.callFinalSurveyorDate != undefined){
				$('#sCallFinalSurveyorDate').html(formatOnlyDate(data.serveyorDetails.callFinalSurveyorDate));
				$('#callFinalSurveyorDate').val(formatOnlyDate(data.serveyorDetails.callFinalSurveyorDate));
			}
		}
		
		$("#createDSE").attr("href", "createDealerServiceEntries?accident="+$("#accidentId").val());
		$("#createSE").attr("href", "createServiceEntries?accident="+$("#accidentId").val());
		$("#createWO").attr("href", "createWorkOrder?accident="+$("#accidentId").val());
		if(data.accidentDetails.serviceId != undefined && data.accidentDetails.serviceId > 0){
			if(data.accidentDetails.serviceType != undefined && data.accidentDetails.serviceType == 1){
				$('#serviceNumber').html('<a target="_blank" href="showServiceEntryDetails?serviceEntryId='+data.accidentDetails.serviceId+'">'+data.accidentDetails.serviceNumber+'</a>');
			}else if(data.accidentDetails.serviceType != undefined && data.accidentDetails.serviceType == 2){
				$('#serviceNumber').html('<a target="_blank" href="showWorkOrder?woId='+data.accidentDetails.serviceId+'">'+data.accidentDetails.serviceNumber+'</a>');
			}
			$("#serviceStatusId").val(data.accidentDetails.serviceStatusId);
		}
		if($('#multipleQuotation').val() == true || $('#multipleQuotation').val() == 'true'){
		setServiceDetails(data.serviceDetails);
		}
		$('#approxDamageAmount').html(data.accidentDetails.approxDamageAmount);
		$('#damageAmountStatus').html(data.accidentDetails.damageAmountStatus);
		$('#damageAmount').html(data.accidentDetails.damageAmount);
		$('#isClaimStatusId').val(data.accidentDetails.claim);
		$('#isClaim').html(data.accidentDetails.claimStatus);
		$('#claimRemark').html(data.accidentDetails.claimRemark);
		
		if(!data.accidentDetails.claim  && ($("#accidentClaimConfig").val() == true || $("#accidentClaimConfig").val() == "true" )){
			$("#allLink").hide();
		}
		
		if(($("#isClaimStatusId").val() == true || $("#isClaimStatusId").val() == "true" ) && ($("#accidentClaimConfig").val() == true || $("#accidentClaimConfig").val() == "true" )){
			$("#claimDiv").show();
			$("#spotSurveyDiv").show();
			$("#spotSurveyComDiv").show();
			$("#keepOpenDiv").show();
			$("#beforeEsimateDiv").show();
		}else{
			$("#claimDiv").hide();
			$("#spotSurveyDiv").hide();
			$("#spotSurveyComDiv").hide();
			$("#keepOpenDiv").hide();
			$("#beforeEsimateDiv").hide();
		}
		//
		
		if(data.accidentDetails.status > 0){
			updateStepStatus(data.accidentDetails.status);
		}
		getAccidentTypeDetails();
		getSpotSurveyorDetails();
		getSpotSurveyorCompletionDetails();

	}else{
		showMessage('error', 'No record found !');
	}
}

function getRenewalAndVehicleDocuments(){
	$('#tableBody').empty();
	getRenewalReminderByVehicle();
	getDocumentList();
}


function getRenewalReminderByVehicle(renewalHistory) {
	
	showLayer();
	var jsonObject					= new Object();
	jsonObject["vid"]				= $('#vid').val();
	jsonObject["renewalHistory"]	= false;
	
	$.ajax({
		url: "RenewalReminderWS/getRenewalReminderByVehicle",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.renewalList != null && data.renewalList.length > 0){
				setRenewalDocumentList(data.renewalList);
			}else{
				showMessage('info', 'No Renewal doc found !');
				$("#dataTable1").hide();
				$("#dataTableHeader1").hide();
			}
			hideLayer();
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function getDocumentList() {
	
	showLayer();
	var jsonObject			= new Object();
	jsonObject["vid"]		= $('#vid').val();
	
	$.ajax({
		url: "getDocumentList",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (response) {
			if(response.vehicledocumentList != null && response.vehicledocumentList.length > 0){
				setVehicleDocumentList(response.vehicledocumentList);
			}else{
				showMessage('info', 'No Vehicle document found !');
				$("#dataTable").hide();
				$("#dataTableHeader").hide();
			}
			hideLayer();
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}
function setVehicleDocumentList(vehicledocumentList){
	for(var i=0; i<vehicledocumentList.length; i++){
		var tr = '<tr>'
			+'<td class="fit">'+vehicledocumentList[i].name+'</td>'
			+'<td class="fit"><a class="fa fa-download" href="downloaddocument/'+vehicledocumentList[i].id+'" target="_blank"></a></td>'
			+'</tr>';
		$('#tableBody').append(tr);
	}
	$('#vehicledocumentmodal').modal('show');
}

function setRenewalDocumentList(renewalList){
	for(var i=0; i<renewalList.length; i++){
		var tr = '<tr>'
			+'<td class="fit">'+renewalList[i].renewal_subtype+'</td>'
			+'<td class="fit"> <a target="_blank" href="showRenewalReminderDetails.in?renewalId='+renewalList[i].renewal_id+'">R-'+renewalList[i].renewal_R_Number+'</a></td>'
			+'<td class="fit"> '+renewalList[i].renewal_to+'</td>';
			if(renewalList[i].renewal_document == true){
				tr +=	'<td class="fit"><a class="fa fa-download" href="/download/RenewalReminder/'+renewalList[i].renewal_document_id+'" target="_blank"</a></td>';
			}else{
				tr += '<td class="fit">--</td>';
				
			}
		tr += '</tr>';
		$('#tableBody1').append(tr);
	}
	$('#vehicledocumentmodal').modal('show');
}

function getDriverDocuments(){
	showLayer();
	var jsonObject			= new Object();
	jsonObject["driverId"]		= $('#driverId').val();
	
	$.ajax({
		url: "getDriverDocuments",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (response) {
			if(response.documentList != null && response.documentList.length > 0){
				setDriverDocumentList(response.documentList);
			}else{
				showMessage('info', 'No Driver document found !');
			}
			hideLayer();
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}
function setDriverDocumentList(documentList){
	$('#driverTableBody').empty();
	for(var i=0; i<documentList.length; i++){
		var tr = '<tr>'
			+'<td class="fit">'+documentList[i].driver_documentname+'</td>'
			+'<td class="fit"><a class="fa fa-download" href="download/driverDocument/'+documentList[i].driver_documentid+'" target="_blank"></a></td>'
			+'</tr>';
		$('#driverTableBody').append(tr);
	}
	$('#driverdocumentmodal').modal('show');
}
function openUpdateIncedentDocumentPopUp(){
	var jsonObject = new Object();
	jsonObject["accidentId"]		= $('#accidentId').val();
	$.ajax({
		url: "getIncidentDocumentList",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (response) {
			$('#renewalFile').val('');
			if(response.documentList != undefined && response.documentList.length > 0){
				$('#docTableBody').empty();
				for(var i=0; i<response.documentList.length; i++){
					var tr = '<tr>';
						if($('#accidentClaimConfig').val() == true || $('#accidentClaimConfig').val() == 'true'){
							if( response.documentList[i].documentStatusId == 1){
								tr +='<td class="fit">Accident Photos</td>';
							}else if(response.documentList[i].documentStatusId == 2){
								tr +='<td class="fit">Accident Type</td>';
							}else if(response.documentList[i].documentStatusId == 3){
								tr +='<td class="fit">Spot Survey Completion</td>';
							}else if(response.documentList[i].documentStatusId == 4){
								tr +='<td class="fit">Final Survey</td>';
							}else if(response.documentList[i].documentStatusId == 5){
								tr +='<td class="fit">Preliminary Estimate</td>';
							}else if(response.documentList[i].documentStatusId == 6){
								tr +='<td class="fit">Kept Open Photos</td>';
							}else if(response.documentList[i].documentStatusId == 7){
								tr +='<td class="fit">Supplementary Estimate</td>';
							}else if(response.documentList[i].documentStatusId == 8){
								tr +='<td class="fit">Update Final Survey Photo</td>';
							}
							else{
								tr +='<td class="fit">--</td>';
							}
						}
						
						tr += '<td class="fit">'+response.documentList[i].documentType+'</td>'
						+'<td class="fit">'+response.documentList[i].fileName+'</td>'
						+'<td class="fit">'+formatDate(new Date(response.documentList[i].created))+'</td>'
						+'<td class="fit"><a class="fa fa-download" href="download/VehicleAccidentDocument/'+response.documentList[i]._id+'" target="_blank"></a></td>'
						+'<td class="fit"><a class="fa fa-remove" onclick="removeIncidentDoc('+response.documentList[i]._id+');" href="#">Remove</a></td>'
						+'</tr>';
					$('#docTableBody').append(tr);
				}
			}
		},
		error: function (e) {
			hideLayer();
			$("#btnSubmit").show();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
	$('#uploadIncidentFile').modal('show');
}
function saveIncidentDocument(){
	if($('#accidentClaimConfig').val() == true || $('#accidentClaimConfig').val() == 'true'){
		if($('#documentTypeId').val()  == null || $('#documentTypeId').val() == undefined || $('#documentTypeId').val() == ''){
			showMessage('errors', 'Please Enter Document Type !');
					return false;
			}		
	}else{
		if($('#documentType').val().trim() == ''){
			showMessage('errors', 'Please Enter Document Type !');
			return false;
		}

	}
	if($('#renewalFile').val().trim() == ''){
		showMessage('errors', 'Please Select a Document !');
		return false;
	}
	if(Number($('#status').val()) == 9){
		showMessage('info', 'Cannot Update As Status is payment done !');
		return false;
	}
	if(($('#accidentClaimConfig').val() == true || $('#accidentClaimConfig').val() == 'true') && ( $('#documentStatusId').val() == 0 ||  $('#documentStatusId').val() == "")){
		showMessage('info', 'Please Select Document Step !');
		return false;
	}
	
	var jsonObject = new Object();
	jsonObject["accidentId"]		= $('#accidentId').val();
	jsonObject["documentStatusId"]		= $('#documentStatusId').val();
	if($('#accidentClaimConfig').val() == true || $('#accidentClaimConfig').val() == 'true'){
		jsonObject["documentTypeId"]	= $('#documentTypeId').val();
		jsonObject["documentType"]		= $('#documentTypeId').select2('data').text;
	}else{
		jsonObject["documentType"]		= $('#documentType').val();
	}
	var form = $('#fileUploadForm')[0];
    var data = new FormData(form);

    data.append("FuelData", JSON.stringify(jsonObject));
    
    showLayer();
    
	$.ajax({
		type: "POST",
		enctype: 'multipart/form-data',
		url: "saveIncidentDocument",
		data: data,
		processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
		success: function (data) {
			$('#renewalFile').val('');
			$('#uploadIncidentFile').modal('hide');		
			showMessage('success', 'Data saved successfully !');
			location.reload();
		},
		error: function (e) {
			hideLayer();
			$("#btnSubmit").show();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function openUpdateServeryorPopup(){
	showLayer();
	if(($('#accidentTypeFlag').val() == false || $('#accidentTypeFlag').val() == 'false')&& ($('#accidentClaimConfig').val() == true || $('#accidentClaimConfig').val() == 'true')){
		showMessage('info', 'Please Update Accident Type Details First !');
		hideLayer();
		return false;
	}

	if($('#accidentDocValidate').val() == true || $('#accidentDocValidate').val() == 'true'){
	validateDocUploaded(1);  //1 for incedent created 
	setTimeout(() => {
		let docFound = $('#docExist').val();
		 if(docFound == false || docFound == 'false'){  
			$('#documentStatusId').val(1);
			hideLayer();
			return false;
		}
		$('#serveryourDetails').modal('show');
		hideLayer();
	}, 600);
	}else{
		$('#serveryourDetails').modal('show');
		hideLayer();
	}
}
function saveServeyorDetails(){
	
	if(!validateServeyerDetails()){
		return false;
	}
	if(Number($('#status').val()) == 9){
		showMessage('info', 'Cannot Update As Status is payment done !');
		return false;
	}
	
	var jsonObject					= new Object();
	jsonObject["userId"]			= $("#userId").val();
	jsonObject["companyId"]			= $("#companyId").val();
	jsonObject["accidentId"]		= $("#accidentId").val();
	jsonObject["serveyorName"]		= $("#serveyorName").val();
	jsonObject["serveyorMobile"]	= $("#serveyorMobile").val();
	jsonObject["serveyorCompany"]	= $("#serveyorCompany").val();
	jsonObject["serveyorDetailsId"]	= $("#serveyorDetailsId").val();
	
	jsonObject["spotSurveyorDate"] = $("#surveyorDate").val();
	jsonObject["spotSurveyorTime"] = $("#surveyorTime").val();
	jsonObject["remark"]			= $("#remark").val();
	
	showLayer();
	$.ajax({
		url: "saveServeyorDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.saveSuccess != undefined && data.saveSuccess){
				showMessage('success', 'Data Saved Successfully !');
			}
			location.reload();
			hideLayer();
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}
function openServeyCompletePopup(){
	if(Number($('#status').val()) < 2){
		showMessage('info', 'Please Update Serveyor Details First !');
		return false;
	}
	$('#serveyCompletePopUp').modal('show');
}
function saveServeyCompletionDetails(){
	
	if(!validateServeyCompletionDetails()){
		return false;
	}
	if(Number($('#status').val()) == 9){
		showMessage('info', 'Cannot Update As Status is payment done !');
		return false;
	}
	
	var jsonObject					= new Object();
	jsonObject["serveyorDetailsId"]	= $("#serveyorDetailsId").val();
	jsonObject["userId"]			= $("#userId").val();
	jsonObject["companyId"]			= $("#companyId").val();
	jsonObject["accidentId"]		= $("#accidentId").val();
	jsonObject["completionDate"]	= $("#completionDate").val();
	jsonObject["completionRemark"]	= $("#completionRemark").val();
	
	showLayer();
	$.ajax({
		url: "saveServeyCompletionDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.saveSuccess != undefined && data.saveSuccess){
				showMessage('success', 'Data Saved Successfully !');
			}
			location.reload();
			hideLayer();
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}
function openFinalDamageServeyPopup(){
	showLayer();
	if(Number($('#status').val()) < 4){
		showMessage('info', 'Please Create Quotation Details First !');
		hideLayer();
		return false;
	}
	if($('#accidentDocValidate').val() == true || $('#accidentDocValidate').val() == 'true'){
		 validateDocUploaded(5);
		 setTimeout(() => {
			 let docFound = $('#docExist').val();
			 if(docFound == false || docFound == 'false'){ 
				 $('#documentStatusId').val(5);
				 hideLayer();
				 return false;
			 }else{
				 $('#finalServeyDamage').modal('show');
				 hideLayer();
			 }
		 }, 600);
		 }else{
			 $('#finalServeyDamage').modal('show');
			 hideLayer();
		 }
}

 function openCreateQuotation(){
		showLayer();
	if(Number($('#status').val()) < 3){
		showMessage('info', 'Please Update Servey Completion Details First !');
		hideLayer();
		return false;
	}
	
	if($('#accidentDocValidate').val() == true || $('#accidentDocValidate').val() == 'true'){
	 validateDocUploaded(3);
	 setTimeout(() => {
		 let docFound = $('#docExist').val();
		 if(docFound == false || docFound == 'false'){ 
			 $('#documentStatusId').val(3);
				hideLayer();
			 return false;
		 }else{
			 $('#createQuotationModal').modal('show');
				hideLayer();
		 }
	 }, 600);
	 }else{
		 $('#createQuotationModal').modal('show');
			hideLayer();
	 }
	
	if($('#status').val() >= 7 && $('#status').val() <= 9){
		$('#createSE').hide();
		$('#createWO').hide();
		$('#createDSE').hide();
	}
}
function saveFinalServeyForDamage(){
	if(!validateFinalServeyForDamage()){
		return false;
	}
	if(Number($('#status').val()) == 9){
		showMessage('info', 'Cannot Update As Status is payment done !');
		return false;
	}
	var jsonObject					= new Object();
	jsonObject["serveyorDetailsId"]	= $("#serveyorDetailsId").val();
	jsonObject["userId"]			= $("#userId").val();
	jsonObject["companyId"]			= $("#companyId").val();
	jsonObject["accidentId"]		= $("#accidentId").val();
	jsonObject["finalSDate"]		= $("#finalSDate").val();
	jsonObject["finalSRemark"]		= $("#finalSRemark").val();
	
	jsonObject["finalServeyorName"]			= $("#finalServeyorName").val();
	jsonObject["finalServeyorMobile"]		= $("#finalServeyorMobile").val();
	jsonObject["finalServeyorEmail"]		= $("#finalServeyorEmail").val();
	jsonObject["finalServeyorDept"]			= $("#finalServeyorDept").val();
	jsonObject["finalServeyorClaimNum"]		= $("#finalServeyorClaimNum").val();
	//jsonObject["salvageAmount"]				= $("#salvageAmount").val();
	
	showLayer();
	$.ajax({
		url: "saveFinalServeyForDamage",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.saveSuccess != undefined && data.saveSuccess){
				showMessage('success', 'Data Saved Successfully !');
			}
			location.reload();
			hideLayer();
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});

}
function approveQuotationPopup(){
	showLayer();
	if(($('#beforeApprovalFlag').val() == false || $('#beforeApprovalFlag').val() == 'false')&& ($('#accidentClaimConfig').val() == true || $('#accidentClaimConfig').val() == 'true')){
		showMessage('info', 'Please Enter Before Approval Details !');
		hideLayer();
		return false;
	}else{
		if(Number($('#status').val()) < 5 ){
			showMessage('info', 'Please Final Servey Details First !');
			hideLayer();
			return false;
		}
	}
	if($('#accidentDocValidate').val() == true || $('#accidentDocValidate').val() == 'true'){
	 validateDocUploaded(7);
	 setTimeout(() => {
		 let docFound = $('#docExist').val();
		 if(docFound == false || docFound == 'false'){ 
			 $('#documentStatusId').val(7);
				hideLayer();
			 return false;
		 }else{
			 $('#approveQuotation').modal('show');
				hideLayer();
		 }
	 }, 600);
	 }else{
		 $('#approveQuotation').modal('show');
			hideLayer();
	 }
	
	
}
function saveQuotationApprovalDetails(){
	if(!validateQuotationApprovalDetails()){
		return false;
	}
	if(Number($('#status').val()) == 9){
		showMessage('info', 'Cannot Update As Status is payment done !');
		return false;
	}
	var jsonObject					= new Object();
	jsonObject["serveyorDetailsId"]	= $("#serveyorDetailsId").val();
	jsonObject["userId"]			= $("#userId").val();
	jsonObject["companyId"]			= $("#companyId").val();
	jsonObject["accidentId"]		= $("#accidentId").val();
	jsonObject["approvalDate"]		= $("#approvalDate").val();
	jsonObject["approvalRemark"]	= $("#approvalRemark").val();
	
	showLayer();
	$.ajax({
		url: "saveQuotationApprovalDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.saveSuccess != undefined && data.saveSuccess){
				showMessage('success', 'Data Saved Successfully !');
			}
			location.reload();
			hideLayer();
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}
function addAdvancepopup(){

	var jsonObject = new Object();
	jsonObject["accidentId"]		= $('#accidentId').val();
	$.ajax({
		url: "getAdvanceListByAccidentId",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (response) {
			if(response.advanceList != undefined && response.advanceList.length > 0){
				$('#advanceTableBody').empty();
				var advanceTotal	= 0;
				for(var i=0; i<response.advanceList.length; i++){
					advanceTotal += response.advanceList[i].advanceAmount;
					var tr = '<tr>'
						+'<td class="fit">'+formatOnlyDate(new Date(response.advanceList[i].advanceDate))+'</td>'
						+'<td class="fit">'+response.advanceList[i].advanceAmount+'</td>'
						+'<td class="fit">'+response.advanceList[i].remark+'</td>'
						+'<td class="fit"><a class="fa fa-remove" href="#" onclick="removeAdvanceDetails('+response.advanceList[i].vehicleAccidentAdvanceId+');" >Remove</a></td>'
						+'</tr>';
					$('#advanceTableBody').append(tr);
				}
				var trTotal = '<tr>'
					+'<td class="fit"><b>Total</b></td>'
					+'<td class="fit"><b>'+advanceTotal+'</b></td>'
					+'<td class="fit" colspan="2"></td>'
					+'</tr>';
				
				$('#advanceTableBody').append(trTotal);
			}
		},
		error: function (e) {
			hideLayer();
			$("#btnSubmit").show();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
	$('#advancePopup').modal('show');
	
	
}
function saveAdvanceDetails(){
	if(!validateAdvanceDetails()){
		return false;
	}
	if(Number($('#status').val()) == 9){
		showMessage('info', 'Cannot Update As Status is payment done !');
		return false;
	}
	var jsonObject					= new Object();
	jsonObject["serveyorDetailsId"]	= $("#serveyorDetailsId").val();
	jsonObject["userId"]			= $("#userId").val();
	jsonObject["companyId"]			= $("#companyId").val();
	jsonObject["accidentId"]		= $("#accidentId").val();
	jsonObject["advanceDate"]		= $("#advanceDate").val();
	jsonObject["advanceAmount"]		= $("#advanceAmount").val();
	jsonObject["advanceRemark"]		= $("#advanceRemark").val();
	
	showLayer();
	$.ajax({
		url: "saveAdvanceDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.saveSuccess != undefined && data.saveSuccess){
				showMessage('success', 'Data Saved Successfully !');
			}
			location.reload();
			hideLayer();
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});

}

function addExpensepopup(){
	
	var jsonObject = new Object();
	jsonObject["accidentId"]	= $('#accidentId').val();
	jsonObject["companyId"]		= $('#companyId').val();
	
	$.ajax({
		url: "getExpenseListByAccidentId",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (response) {
			if(response.expenseList != undefined && response.expenseList.length > 0){
				$('#expenseTableBody').empty();
				var expenseTotal	= 0;
				for(var i=0; i<response.expenseList.length; i++){
					expenseTotal += response.expenseList[i].expenseAmount;
					var tr = '<tr>'
						+'<td class="fit">'+response.expenseList[i].expenseType+'</td>'
						+'<td class="fit">'+formatOnlyDate(new Date(response.expenseList[i].expenseDate))+'</td>'
						+'<td class="fit">'+response.expenseList[i].expenseAmount+'</td>'
						+'<td class="fit">'+response.expenseList[i].remark+'</td>';
						if(response.documentMap != undefined && response.documentMap[response.expenseList[i].vehicleExpensesId] != undefined){
							tr += '<td class="fit"><a target="_blank" class="fa fa-download" href="downloadDoc/37/'+response.documentMap[response.expenseList[i].vehicleExpensesId]._id+'"></a></td>';
						}else{
							tr += '<td class="fit"><a class="fa fa-download" href="#"></a></td>';
						}
						tr += '<td class="fit"><a class="fa fa-remove" href="#" onclick="removeExpense('+response.expenseList[i].vehicleExpensesId+');">Remove</a></td>'
						+ '</tr>';
					$('#expenseTableBody').append(tr);
				}
				var trTotal = '<tr>'
					+'<td class="fit" colspan="2"><b>Total</b></td>'
					+'<td class="fit"><b>'+expenseTotal+'</b></td>'
					+'<td class="fit" colspan="3"></td>'
					+'</tr>';
				
				$('#expenseTableBody').append(trTotal);
			}
		},
		error: function (e) {
			hideLayer();
			$("#btnSubmit").show();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
	$('#expensePopup').modal('show');
}

function saveExpenseDetails(){
	if(!validateExpenseDetails()){
		return false;
	}
	if(Number($('#status').val()) == 9){
		showMessage('info', 'Cannot Update As Status is payment done !');
		return false;
	}
	var jsonObject					= new Object();
	jsonObject["serveyorDetailsId"]	= $("#serveyorDetailsId").val();
	jsonObject["userId"]			= $("#userId").val();
	jsonObject["companyId"]			= $("#companyId").val();
	jsonObject["accidentId"]		= $("#accidentId").val();
	jsonObject["expenseDate"]		= $("#expenseDate").val();
	jsonObject["expenseAmount"]		= $("#expenseAmount").val();
	jsonObject["expenseRemark"]		= $("#expenseRemark").val();
	jsonObject["expenseType"]		= $("#expenseType").val();
	jsonObject["vid"]				= $('#vid').val();
	
	var form = $('#expenseFileForm')[0];
    var data = new FormData(form);
    data.append("ExpenseData", JSON.stringify(jsonObject));   
    
	showLayer();
	$.ajax({
		type: "POST",
		enctype: 'multipart/form-data',
		url: "saveExpenseDetails",
		data: data,
		processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
		success: function (response) {
			if(response.saveSuccess != undefined && response.saveSuccess){
				showMessage('success', 'Data Saved Successfully !');
			}
			location.reload();
			hideLayer();
			
		},
		error: function (e) {
			hideLayer();
			$("#btnSubmit").show();
			showMessage('errors', 'Some Error Occurred!');
		}
	});

}
function addfinalServeyDetails(){
	if(Number($('#status').val()) < 7){
		showMessage('info', 'Please Complete Service First !');
		return false;
	}
	
	if($('#accidentDocValidate').val() == true || $('#accidentDocValidate').val() == 'true'){
		 validateDocUploaded(8);
		 setTimeout(() => {
			 let docFound = $('#docExist').val();
			 if(docFound == false || docFound == 'false'){ 
				 $('#documentStatusId').val(8);
					hideLayer();
				 return false;
			 }else{
				 $('#finalServeyDetails').modal('show');
					hideLayer();
			 }
		 }, 600);
		 }else{
			 $('#finalServeyDetails').modal('show');
				hideLayer();
		 }	
}
function saveFinalInspectionDetails(){
	if(!validateFinalInspectionDetails()){
		return false;
	}
	if(Number($('#status').val()) == 9){
		showMessage('info', 'Cannot Update As Status is payment done !');
		return false;
	}
	var jsonObject					= new Object();
	jsonObject["serveyorDetailsId"]	= $("#serveyorDetailsId").val();
	jsonObject["userId"]			= $("#userId").val();
	jsonObject["companyId"]			= $("#companyId").val();
	jsonObject["accidentId"]		= $("#accidentId").val();
	jsonObject["inspectionDate"]	= $("#inspectionDate").val();
	jsonObject["inspectionRemark"]	= $("#inspectionRemark").val();
	jsonObject["salvageAmount"]		= $("#salvageAmount").val();
	
	showLayer();
	$.ajax({
		url: "saveFinalInspectionDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.saveSuccess != undefined && data.saveSuccess){
				showMessage('success', 'Data Saved Successfully !');
			}
			location.reload();
			hideLayer();
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}
function openPaymentDetailsPopup(){
	if(Number($('#status').val()) < 8){
		showMessage('info', 'Please Update Final Inspection Details first !');
		return false;
	}
	$('#paymentDetails').modal('show');
}
function savePaymentDetails(){
	if(!validatePaymentDetails()){
		return false;
	}
	var jsonObject					= new Object();
	jsonObject["serveyorDetailsId"]	= $("#serveyorDetailsId").val();
	jsonObject["userId"]			= $("#userId").val();
	jsonObject["companyId"]			= $("#companyId").val();
	jsonObject["accidentId"]		= $("#accidentId").val();
	jsonObject["paymentRemark"]		= $("#paymentRemark").val();
	jsonObject["paymentDate"]		= $("#paymentDateStr").val();
	jsonObject["paymentAmount"]		= $("#paymentAmount").val();
	jsonObject["queryRemark"]		= $("#queryRemark").val();
	
	showLayer();
	$.ajax({
		url: "savePaymentDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.saveSuccess != undefined && data.saveSuccess){
				showMessage('success', 'Data Saved Successfully !');
			}
			location.reload();
			hideLayer();
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});

}
function removeIncidentDoc(documentId){
	if(Number($('#status').val()) == 9){
		showMessage('info', 'Cannot remove As Status is payment done !');
		return false;
	}
	var jsonObject					= new Object();
	jsonObject["userId"]			= $("#userId").val();
	jsonObject["companyId"]			= $("#companyId").val();
	jsonObject["documentId"]		= documentId;
	
	showLayer();
	$.ajax({
		url: "removeIncidentDoc",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.saveSuccess != undefined && data.saveSuccess){
				showMessage('success', 'Data Deleted Successfully !');
			}
			location.reload();
			hideLayer();
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}
function removeAdvanceDetails(vehicleAccidentAdvanceId){
	if(Number($('#status').val()) == 9){
		showMessage('info', 'Cannot Remove As Status is payment done !');
		return false;
	}
	var jsonObject					= new Object();
	jsonObject["userId"]			= $("#userId").val();
	jsonObject["companyId"]			= $("#companyId").val();
	jsonObject["advanceId"]		= vehicleAccidentAdvanceId;
	
	showLayer();
	$.ajax({
		url: "removeAdvanceDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.saveSuccess != undefined && data.saveSuccess){
				showMessage('success', 'Data Deleted Successfully !');
			}
			location.reload();
			hideLayer();
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});

}
function removeExpense(vehicleExpensesId){
	if(Number($('#status').val()) == 9){
		showMessage('info', 'Cannot remove As Status is payment done !');
		return false;
	}
	var jsonObject					= new Object();
	jsonObject["userId"]			= $("#userId").val();
	jsonObject["companyId"]			= $("#companyId").val();
	jsonObject["vehicleExpensesId"]	= vehicleExpensesId;
	
	showLayer();
	$.ajax({
		url: "removeAccidentExpense",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.saveSuccess != undefined && data.saveSuccess){
				showMessage('success', 'Data Deleted Successfully !');
			}
			location.reload();
			hideLayer();
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}
function checkForOldServeyDetails(evt){
	isMobileNum(evt);
	var jsonObject					= new Object();
	jsonObject["userId"]			= $("#userId").val();
	jsonObject["companyId"]			= $("#companyId").val();
	jsonObject["mobileNumber"]		= $("#serveyorMobile").val();
	
	showLayer();
	$.ajax({
		url: "checkForOldServeyDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			$('#previousCount').html('');
			if(data.serveyDetails != undefined){
				$('#previousServey').show();
				var previousAccident = '';
				for(var i=0; i<data.serveyDetails.length; i++){
					if(data.serveyDetails[i].encriptedId != $("#accidentId").val()){
						previousAccident += '<a target="_blank" href="showVehicleAccidentDetails?id='+data.serveyDetails[i].encriptedId+'">'+data.serveyDetails[i].accidentNumber+'</a> , ';
					}
				}
				$('#previousCount').html(previousAccident);
				showMessage('success', 'Servey by same serveyor found !');
			}
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});

}
function updateStepStatus(status){
	for(var i=0; i<=status; i++){
		if( i == 10 || i == 11 ) {
			continue;
		}else if(i == 6 && ($("#sApprovalRemark").html() != "")){
			$('#step'+i+'').css('color', 'green');
			$('#link'+i+'').css('color', 'green');
			$('#stepCheck'+i+'').show();
		}
		else if(i == 7 && $("#serviceStatusId").val() == 4){
			$('#step'+i+'').css('color', 'green');
			$('#link'+i+'').css('color', 'green');
			$('#stepCheck'+i+'').show();
		}
		else if(i == 8 && ($("#inspectionDate").val() != "")){
			$('#step'+i+'').css('color', 'green');
			$('#link'+i+'').css('color', 'green');
			$('#stepCheck'+i+'').show();
		}
		else if(i == 9 && ($("#paymentDateStr").val() != "")){
			$('#step'+i+'').css('color', 'green');
			$('#link'+i+'').css('color', 'green');
			$('#stepCheck'+i+'').show();
		}else if(i < 6){
			$('#step'+i+'').css('color', 'green');
			$('#link'+i+'').css('color', 'green');
			$('#stepCheck'+i+'').show();
		}
	}
}
function setStartDateTimeForAccidentMoudle(accidentDetails, serveyorDetails){
	var defaultServerDate =  $('#accidentDate').val();
	
	if(serveyorDetails != undefined){
		var accidentDate = $('#accidentDate').val();
		
		$("#completionDate").datepicker({
		       autoclose: !0,
		       format: "dd-mm-yyyy",
			   startDate:accidentDate
		})
		if(serveyorDetails.completionDate != undefined){
			$("#finalSDate").datepicker({
			       autoclose: !0,
			       format: "dd-mm-yyyy",
				   startDate: formatOnlyDate(serveyorDetails.completionDate)
			})
		}else{
			$("#completionDate").val('');
		}
		if(serveyorDetails.finalDamageServeyDate != undefined){
			$("#approvalDate").datepicker({
			       autoclose: !0,
			       format: "dd-mm-yyyy",
				   startDate: formatOnlyDate(serveyorDetails.finalDamageServeyDate)
			})
		}else{
			$("#finalSDate").val('');
		}
		if(serveyorDetails.quotationApprovalDate != undefined){
			$("#inspectionDate").datepicker({
			       autoclose: !0,
			       format: "dd-mm-yyyy",
				   startDate: formatOnlyDate(serveyorDetails.quotationApprovalDate)
			})
		}else{
			$("#approvalDate").val('');
		}
		
		if(serveyorDetails.finalInspectionDate != undefined){
			$("#paymentDateStr").datepicker({
			       autoclose: !0,
			       format: "dd-mm-yyyy",
				   startDate: formatOnlyDate(serveyorDetails.finalInspectionDate)
			})
		}else{
			$("#inspectionDate").val('');
		}
		
	}
	
}

function getAccidentTypeDetails(){
	
	var jsonObject = new Object();
	
	jsonObject["accidentId"]		= $('#dAccidentId').val();
	jsonObject["companyId"]			= $('#companyId').val();
	$.ajax({
		url: "getAccidentTypeDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (response) {
			setAccidentTypeDetails(response);
		},
		error: function (e) {
			hideLayer();
			$("#btnSubmit").show();
			showMessage('errors', 'Some Error Occurred!');
		}
	});

}

function setAccidentTypeDetails(data){
	var vehicleAccidentTypeDetails = data.accidentTypeDetails 
	var vehicleAccidentPersonDetails = data.accidentPersonDetailsList 
	$('#vehicleAccidentPersonTable').empty();
	if(vehicleAccidentTypeDetails != undefined){
		$('#vehicleAccidentTypeDetailsId').val(vehicleAccidentTypeDetails.vehicleAccidentTypeDetailsId)
		$('#accidentTypeId').val(vehicleAccidentTypeDetails.vehicleAccidentTypeId)
		$('#natureOfOwnDamage').val(vehicleAccidentTypeDetails.natureOfOwnDamage)
		$('#approxOwnDamgeCost').val(vehicleAccidentTypeDetails.approxOwnDamgeCost)
		$('#natureOfTPDamage').val(vehicleAccidentTypeDetails.natureOfTPDamage)
		$('#approxTPDamgeCost').val(vehicleAccidentTypeDetails.approxTPDamgeCost)
		$('#showAccidentType').html(vehicleAccidentTypeDetails.vehicleAccidentTypeName)
		$('#showNatureOfOwnDamage').html(vehicleAccidentTypeDetails.natureOfOwnDamage)
		$('#showApproxOwnDamgeCost').html(vehicleAccidentTypeDetails.approxOwnDamgeCost)
		$('#showNatureOfTPDamage').html(vehicleAccidentTypeDetails.natureOfTPDamage)
		$('#showApproxTPDamgeCost').html(vehicleAccidentTypeDetails.approxTPDamgeCost)
		if(vehicleAccidentPersonDetails != undefined ){
			$('#showNumberOfAccidentPerson').html(vehicleAccidentPersonDetails.length+" Person")
		}
		$('#accidentTypeFlag').val(true);
		changeAccidentType();
		$('#accidentTypeDetailsDiv').show();
		$('#step1_1').css('color', 'green');
        $('#link1_1').css('color', 'green');
        $('#stepCheck1_1').show();
	}
	if(vehicleAccidentPersonDetails != undefined){
		for(var index = 0 ; index < vehicleAccidentPersonDetails.length; index++){
			var columnArray = new Array();
			if( vehicleAccidentPersonDetails[index].vehicleAccidentPersonTypeId == 1 ){
				columnArray.push("<td class='fit'ar> <div class='btn-group'><label id='ownTypeId"+vehicleAccidentPersonDetails[index].vehicleAccidentPersonDetailsId+"' class='btn btn-default  btn-sm active' onclick='selectTypeId(1, "+vehicleAccidentPersonDetails[index].vehicleAccidentPersonDetailsId+");'> Own </label>  <label id='TPTypeId"+vehicleAccidentPersonDetails[index].vehicleAccidentPersonDetailsId+"' class='btn btn-default  btn-sm' onclick='selectTypeId(2, "+vehicleAccidentPersonDetails[index].vehicleAccidentPersonDetailsId+");'> Other </label> </div></td>");
			}else{
				columnArray.push("<td class='fit'ar> <div class='btn-group'><label id='ownTypeId"+vehicleAccidentPersonDetails[index].vehicleAccidentPersonDetailsId+"' class='btn btn-default  btn-sm' onclick='selectTypeId(1,"+vehicleAccidentPersonDetails[index].vehicleAccidentPersonDetailsId+");'> Own </label>  <label id='TPTypeId"+vehicleAccidentPersonDetails[index].vehicleAccidentPersonDetailsId+"' class='btn btn-default  btn-sm active' onclick='selectTypeId(2, "+vehicleAccidentPersonDetails[index].vehicleAccidentPersonDetailsId+");'> Other </label> </div></td>");
			}
			columnArray.push("<td class='fit'ar><input type='hidden' id='accidentPersonId"+vehicleAccidentPersonDetails[index].vehicleAccidentPersonDetailsId+"' name='accidentPersonId' value="+vehicleAccidentPersonDetails[index].vehicleAccidentPersonDetailsId+" >  <input type='hidden' id='accidentPersonTypeId"+vehicleAccidentPersonDetails[index].vehicleAccidentPersonDetailsId+"' name='accidentPersonTypeId'  value="+vehicleAccidentPersonDetails[index].vehicleAccidentPersonTypeId+">  <input type='hidden' id='accidentPersonStatusId"+vehicleAccidentPersonDetails[index].vehicleAccidentPersonDetailsId+"' name='accidentPersonStatusId' value="+vehicleAccidentPersonDetails[index].vehicleAccidentPersonStatusId+"> <input type='text' name='accidentPersonName' value="+ vehicleAccidentPersonDetails[index].name  +"> </td>");
			columnArray.push("<td class='fit'ar> <input type='number' id='age"+vehicleAccidentPersonDetails[index].vehicleAccidentPersonDetailsId+"' name='accidentPersonAge' value="+ vehicleAccidentPersonDetails[index].age  +" onkeypress='return isNumberKeyWithDecimal(event,this.id);'> </td>");
			if( vehicleAccidentPersonDetails[index].vehicleAccidentPersonStatusId == 1 ){
				columnArray.push("<td class='fit'ar> <div class='btn-group'><label id='injuredId"+vehicleAccidentPersonDetails[index].vehicleAccidentPersonDetailsId+"' class='btn btn-default  btn-sm active' onclick='selectStatusId(1, "+vehicleAccidentPersonDetails[index].vehicleAccidentPersonDetailsId+");'> Injured </label>  <label id='deadId"+vehicleAccidentPersonDetails[index].vehicleAccidentPersonDetailsId+"' class='btn btn-default  btn-sm' onclick='selectStatusId(2, "+vehicleAccidentPersonDetails[index].vehicleAccidentPersonDetailsId+");'> Dead </label> </div></td>");
			}else{
				columnArray.push("<td class='fit'ar> <div class='btn-group'><label id='injuredId"+vehicleAccidentPersonDetails[index].vehicleAccidentPersonDetailsId+"' class='btn btn-default  btn-sm ' onclick='selectStatusId(1, "+vehicleAccidentPersonDetails[index].vehicleAccidentPersonDetailsId+");'> Injured </label>  <label id='deadId"+vehicleAccidentPersonDetails[index].vehicleAccidentPersonDetailsId+"' class='btn btn-default  btn-sm active' onclick='selectStatusId(2, "+vehicleAccidentPersonDetails[index].vehicleAccidentPersonDetailsId+");'> Dead </label> </div></td>");
			}
			columnArray.push("<td class='fit ar'><input type='text' name='accidentDescription' value="+ vehicleAccidentPersonDetails[index].description  +"> </td>");
			columnArray.push("<td class='fit ar'><a href='#' onclick='removeAccidentPersonDetails("+vehicleAccidentPersonDetails[index].vehicleAccidentPersonDetailsId+");' ><font color='FF00000'><i class='fa fa-trash'></i> Remove</a></td>");
			
			$('#vehicleAccidentPersonTable').append("<tr>" + columnArray.join(' ') + "</tr>");
			
		}
		
		columnArray = [];
		
	}else{
		var columnArray = new Array();
		columnArray.push("<td class='fit'ar> <div class='btn-group'> <input type='hidden' id='accidentPersonId' name='accidentPersonId' ><input type='hidden' id='accidentPersonTypeId' name='accidentPersonTypeId'> <input type='hidden' id='accidentPersonStatusId' name='accidentPersonStatusId' ><label id='ownTypeId' class='btn btn-default  btn-sm active' onclick='selectTypeId(1, 0);'> Own </label>  <label id='TPTypeId' class='btn btn-default  btn-sm' onclick='selectTypeId(2, 0);'> Other </label> </div></td>");
		columnArray.push("<td class='fit'ar> <input type='text' class='form-text' id='name' name='accidentPersonName'> </td>");
		columnArray.push("<td class='fit'ar> <input type='number' class='form-text' id='age' name='accidentPersonAge' onkeypress='return isNumberKeyWithDecimal(event,this.id);'> </td>");
		columnArray.push("<td class='fit'ar> <div class='btn-group'> <label id='injuredId' class='btn btn-default  btn-sm active' onclick='selectStatusId(1, 0);'> Injured </label>  <label id='deadId' class='btn btn-default  btn-sm' onclick='selectStatusId(2, 0);'> Dead </label> </div></td>");
		columnArray.push("<td class='fit ar'><input type='text'class='form-text' name='accidentDescription'> </td>");
		
		$('#vehicleAccidentPersonTable').append("<tr id='vehicleAccidentPersonTableId'>" + columnArray.join(' ') + "</tr>");
		
	}
	
	
	
}
function claimProcess(){
		changeAccidentType();
	$('#accidentTypeDetailsModal').modal('show');
}


$(document).ready(function() {
	var a = 50,
	b = $("#vehicleAccidentPersonTable"),
	c = $(".addMoreLabourButton"),
	d = 1;
	$(c).click(function(c) {
		c.preventDefault(), a > d && (d++, $(b).append('<tr id="tr'+d+'"> '
				+"<td class='fit'ar><input type='hidden' id='accidentPersonId"+d+"' name='accidentPersonId' > <input type='hidden' id='accidentPersonTypeId"+d+"' name='accidentPersonTypeId'> <input type='hidden' id='accidentPersonStatusId"+d+"' name='accidentPersonStatusId' > <div class='btn-group'> <label id='ownTypeId"+d+"' class='btn btn-default  btn-sm active' onclick='selectTypeId(1,"+d+");'> Own </label>  <label id='TPTypeId"+d+"' class='btn btn-default  btn-sm' onclick='selectTypeId(2, "+d+");'> Other </label> </div></td>" 
				+"<td class='fit'ar> <input type='text' class='form-text' id='name"+d+"' name='accidentPersonName'> </td>"
				+"<td class='fit'ar> <input type='number' class='form-text' id='age"+d+"' name='accidentPersonAge' onkeypress='return isNumberKeyWithDecimal(event,this.id);'> </td>"
				+"<td class='fit'ar> <div class='btn-group'> <label id='injuredId"+d+"' class='btn btn-default  btn-sm active' onclick='selectStatusId(1,"+d+");'> Injured </label>  <label id='deadId"+d+"' class='btn btn-default  btn-sm' onclick='selectStatusId(2, "+d+");'> Dead </label> </div></td>"
				+"<td class='fit ar'><input type='text'class='form-text' name='accidentDescription'> </td>"
				+"<td class='fit ar'><a href='#' class='removeLabour col col-sm-1 col-md-1'><font color='FF00000'><i class='fa fa-trash'></i> Remove </a> </td>"
				+'</tr>'
		))}), $(b).on("click", ".removeLabour", function(a) {
			a.preventDefault(), $("#tr"+d).remove(), d--
		})
	});

function resetAccidentTypeDetails(){
	$("#natureOfOwnDamage").val('');
	$("#approxOwnDamgeCost").val('');
	$("#natureOfTPDamage").val('');
	$("#approxTPDamgeCost").val('');
	$('#vehicleAccidentPersonTable').empty();
}
function changeAccidentType(){
	//resetAccidentTypeDetails();
	switch (Number($("#accidentTypeId").val())) {
	case 1:
		$("#TPIRow").hide();
		$("#TPDRow").hide();
		$("#ODRow").show();
		break;
	case 2:
		$("#TPIRow").show();
		$("#TPDRow").hide();
		$("#ODRow").hide();
		break;
	case 3:
		$("#TPIRow").hide();
		$("#TPDRow").show();
		$("#ODRow").hide();
		break;
	case 4:
		$("#TPIRow").show();
		$("#TPDRow").hide();
		$("#ODRow").show();
		break;
	case 5:
		$("#TPIRow").hide();
		$("#TPDRow").show();
		$("#ODRow").show();
		break;
	case 6:
		$("#TPIRow").show();
		$("#TPDRow").show();
		$("#ODRow").show();
		break;

	default:
		$("#TPIRow").hide();
		$("#TPDRow").hide();
		break;
	}
	
//	if($("#accidentTypeId").val() == 1)
}
function selectTypeId(typeId, d){
	
	
	if(d == 0){
		
		if(typeId == 1){																	
			$('#ownTypeId').addClass('active');
			$('#TPTypeId').removeClass('active');
			$('#accidentPersonTypeId').val(1);
		} else {
			$('#ownTypeId').removeClass('active');
			$('#TPTypeId').addClass('active');
			$('#accidentPersonTypeId').val(2);
		}
		
	} else {
		if(typeId == 1){
			$("#ownTypeId"+d).addClass('active');
			$("#TPTypeId"+d).removeClass('active');
			$("#accidentPersonTypeId"+d).val(1);
		} else {
			$("#ownTypeId"+d).removeClass('active');
			$("#TPTypeId"+d).addClass('active');
			$("#accidentPersonTypeId"+d).val(2);
		//	accidentPersonTypeId2 
		}
		
	}
	
}

function selectStatusId(statusId, d){
	if(d == 0){
		
		if(statusId == 1){
			$('#injuredId').addClass('active');
			$('#deadId').removeClass('active');
			$('#accidentPersonStatusId').val(1);
		} else {
			$('#injuredId').removeClass('active');
			$('#deadId').addClass('active');
			$('#accidentPersonStatusId').val(2);
		}
		
	} else {
		
		if(statusId == 1){
			$("#injuredId"+d).addClass('active');
			$("#deadId"+d).removeClass('active');
			$("#accidentPersonStatusId"+d).val(1);
		} else {
			$("#injuredId"+d).removeClass('active');
			$("#deadId"+d).addClass('active');
			$("#accidentPersonStatusId"+d).val(2);
		}
		
	}
	
}


function saveUpdateAccidentTypeDetails(){
	showLayer();
	var jsonObject					= new Object();
	var accidentPersonArray	 		= new Array();
	
	var accidentPersonIdArr 			= new Array();
	var accidentPersonTypeArr 			= new Array();
	var accidentPersonNameArr 			= new Array();
	var accidentPersonAgeArr 			= new Array();
	var accidentPersonStatusArr 		= new Array();
	var accidentPersonDescriptionArr 	= new Array();
	
	jsonObject["accidentId"]						= $('#dAccidentId').val();
	jsonObject["vehicleAccidentTypeId"]				= $('#accidentTypeId').val();
	jsonObject["vehicleAccidentTypeDetailsId"]		= $('#vehicleAccidentTypeDetailsId').val();
	jsonObject["approxOwnDamgeCost"]				= $('#approxOwnDamgeCost').val();
	jsonObject["natureOfOwnDamage"]					= $('#natureOfOwnDamage').val();
	jsonObject["approxTPDamgeCost"]					= $('#approxTPDamgeCost').val();
	jsonObject["natureOfTPDamage"]					= $('#natureOfTPDamage').val();
	jsonObject["userId"]							= $('#userId').val();
	jsonObject["companyId"]							= $('#companyId').val();
	
	
	$("input[name=accidentPersonId]").each(function(){
		accidentPersonIdArr.push($(this).val());
	});
	$("input[name=accidentPersonTypeId]").each(function(){
		accidentPersonTypeArr.push($(this).val());
	});
	$("input[name=accidentPersonName]").each(function(){
		accidentPersonNameArr.push($(this).val().replace(/"/g, ""));
	});
	$("input[name=accidentPersonAge]").each(function(){
		accidentPersonAgeArr.push($(this).val());
	});
	$("input[name=accidentPersonStatusId]").each(function(){
		accidentPersonStatusArr.push($(this).val());
	});
	$("input[name=accidentDescription]").each(function(){
		accidentPersonDescriptionArr.push($(this).val());
	});
	
	if($('#accidentTypeId').val() != 2 && $('#accidentTypeId').val() != 3){
		if($('#approxOwnDamgeCost').val() == undefined || $('#approxOwnDamgeCost').val() == ""){
			showMessage('info','Please Enter Apporx OWn Damage Cost')
			hideLayer();
			return false;
		}
		if($('#natureOfOwnDamage').val() == undefined || $('#natureOfOwnDamage').val() == ""){
			showMessage('info','Please Enter Nature Of Own Damage')
			hideLayer();
			return false;
		}
	}
	
	if($('#accidentTypeId').val() != 1 && $('#accidentTypeId').val() != 2 && $('#accidentTypeId').val() != 4){
		if($('#approxTPDamgeCost').val() == undefined || $('#approxTPDamgeCost').val() == ""){
			showMessage('info','Please Enter Apporx TP Damage Cost')
			hideLayer();
			return false;
		}
		if($('#natureOfTPDamage').val() == undefined || $('#natureOfTPDamage').val() == ""){
			showMessage('info','Please Enter Nature Of TP Damage')
			hideLayer();
			return false;
		}
	
	}
	if($('#accidentTypeId').val() == 2 || $('#accidentTypeId').val() == 4 || $('#accidentTypeId').val() == 6){
		if(Number(accidentPersonNameArr.length) == 0){
			showMessage('info','Please Enter Accident Person Details');
			hideLayer();
			return false;
		}
	}
	
	for(var i =0 ; i< accidentPersonNameArr.length; i++){
		
		var accidentPersonDetails					= new Object();
		if($('#accidentTypeId').val() == 2 || $('#accidentTypeId').val() == 4 || $('#accidentTypeId').val() == 6){
			if(accidentPersonNameArr[i] == undefined || accidentPersonNameArr[i] == ""){
				showMessage('info','Please Enter Accident Person Name')
				hideLayer();
				return false;
			}
			if(accidentPersonAgeArr[i] == undefined || accidentPersonAgeArr[i] == ""){
				showMessage('info','Please Enter Accident Age')
				hideLayer();
				return false;
			}
		}
		
		if(accidentPersonNameArr[i].trim() != "" ){
			accidentPersonDetails.vehicleAccidentPersonDetailsId			= accidentPersonIdArr[i];
			accidentPersonDetails.vehicleAccidentPersonTypeId				= accidentPersonTypeArr[i];
			accidentPersonDetails.name										= accidentPersonNameArr[i];
			accidentPersonDetails.age										= accidentPersonAgeArr[i];
			accidentPersonDetails.vehicleAccidentPersonStatusId				= accidentPersonStatusArr[i];
			accidentPersonDetails.description								= accidentPersonDescriptionArr[i];
			
			accidentPersonArray.push(accidentPersonDetails); 
		}
	}
	
	jsonObject.accidentPersonDetails 				= JSON.stringify(accidentPersonArray);
	
	$.ajax({
		url: "saveUpdateAccidentTypeDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (response) {
			if(response.personExist != undefined && response.personExist == true){
				showMessage('info','Please Remove Person Details First')
				hideLayer();
				setTimeout(location.reload(), 2000);
				return false;
			}else{
				showMessage('info','Data Save Successfully')
				location.reload();
			}
		},
		error: function (e) {
			hideLayer();
			$("#btnSubmit").show();
			showMessage('errors', 'Some Error Occurred!');
		}
	});

}
function removeAccidentPersonDetails(vehicleAccidentPersonDetailsId){
	showLayer();
	var jsonObject							= new Object();
	jsonObject["accidentPersonDetailsId"]	= vehicleAccidentPersonDetailsId;
	jsonObject["companyId"]					= $('#companyId').val();
	$.ajax({
		url: "removeAccidentPersonDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (response) {
			showMessage('info','Data Remove Successfully')
			location.reload();
		},
		error: function (e) {
			hideLayer();
			$("#btnSubmit").show();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}
	
$.getJSON("getVehicleAccidentDocumentTypeDropdown.in", function(e) {
	companyList	= e;//To get All Company Name 
	$("#documentTypeId").empty();
	$('#documentTypeId').select2();
	for(var k = 0; k <companyList.length; k++){
		$("#documentTypeId").append($("<option>").text(companyList[k].vPhoType).attr("value", companyList[k].ptid));
	}

});	

function getSpotSurveyorDetails(){
	
	var jsonObject							= new Object();
	jsonObject["accidentId"]				= $('#dAccidentId').val();
	jsonObject["companyId"]					= $('#companyId').val();
	
	$.ajax({
		url: "getSpotSurveyorDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setSpotSurveyorDetails(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function openSpotSurveyorPopup(){
	if(($('#accidentTypeFlag').val() == false || $('#accidentTypeFlag').val() == 'false')&& ($('#accidentClaimConfig').val() == true || $('#accidentClaimConfig').val() == 'true')){
		showMessage('info', 'Please Update Accident Type Details First !');
		return false;
	}else{
		$('#spotSurveryourDetails').modal('show');
	}
}

function setSpotSurveyorDetails(data){
	
	var spotSurveyorDetails = data.spotSurveyorDetails; 
	if(spotSurveyorDetails != undefined){
		$('#spotSurveyorDetailsId').val(spotSurveyorDetails.spotSurveyorDetailsId)
		$('#spotSurveyorName').val(spotSurveyorDetails.spotSurveyorName)
		$('#spotSurveyorMobile').val(spotSurveyorDetails.spotSurveyorMobile)
		$('#spotSurveyorCompany').val(spotSurveyorDetails.spotSurveyorCompany)
		$('#spotSurveyorDate').val(spotSurveyorDetails.spotSurveyorDateStr)
		$('#spotSurveyorTime').val(spotSurveyorDetails.spotSurveyorTimeStr)
		$('#spotSurveyorRemark').val(spotSurveyorDetails.spotSurveyorRemark)
		$('#showSpotSurveyorName').html(spotSurveyorDetails.spotSurveyorName)
		$('#showSpotSurveyorMobile').html(spotSurveyorDetails.spotSurveyorMobile)
		$('#showSpotSurveyorCompany').html(spotSurveyorDetails.spotSurveyorCompany)
		$('#showSpotSurveyorDate').html(spotSurveyorDetails.spotSurveyorDateStr)
		$('#showSpotSurveyorTime').html(spotSurveyorDetails.spotSurveyorTimeStr)
		$('#showSpotSurveyorRemark').html(spotSurveyorDetails.spotSurveyorRemark)
		
		$('#spotSurveyorCompletionDateOnSave').val(spotSurveyorDetails.spotSurveyorCompletionDateStr)
		$('#spotSurveyorCompletionTimeOnSave').val(spotSurveyorDetails.spotSurveyorCompletionTimeStr)
		$('#SpotSurveyorCompletionRemarkOnSave').val(spotSurveyorDetails.spotSurveyorCompletionRemark)
		$("#spotSurveyFlag").val(true);
		$('#step1_2').css('color', 'green');
        $('#link1_2').css('color', 'green');
        $('#stepCheck1_2').show();
	}
	
}



function saveSpotSurveyorDetails(){
	
	if(!validateSpotSurveyorDetails()){
		return false;
	}
	showLayer();
	var jsonObject					= new Object();
	jsonObject["userId"]			= $("#userId").val();
	jsonObject["companyId"]			= $("#companyId").val();
	jsonObject["accidentId"]		= $("#dAccidentId").val();
	
	jsonObject["spotSurveyorName"]		= $("#spotSurveyorName").val();
	jsonObject["spotSurveyorMobile"]	= $("#spotSurveyorMobile").val();
	jsonObject["spotSurveyorCompany"]	= $("#spotSurveyorCompany").val();
	jsonObject["spotSurveyorDate"]		= $("#spotSurveyorDate").val();
	jsonObject["spotSurveyorTime"]		= $("#spotSurveyorTime").val();
	jsonObject["spotSurveyorRemark"]	= $("#spotSurveyorRemark").val();
	jsonObject["spotSurveyorDetailsId"]	= $("#spotSurveyorDetailsId").val();

	jsonObject["spotSurveyorCompletionDate"]	= $("#spotSurveyorCompletionDateOnSave").val();
	jsonObject["spotSurveyorCompletionTime"]	= $("#spotSurveyorCompletionTimeOnSave").val();
	jsonObject["SpotSurveyorCompletionRemark"]	= $("#SpotSurveyorCompletionRemarkOnSave").val();
	
	showLayer();
	$.ajax({
		url: "saveUpdateSpotSurveyorDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.saveSuccess != undefined && data.saveSuccess){
				showMessage('success', 'Data Saved Successfully !');
			}
			location.reload();
			hideLayer();
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});

}

function getSpotSurveyorCompletionDetails(){

	var jsonObject							= new Object();
	jsonObject["accidentId"]					= $('#dAccidentId').val();
	jsonObject["companyId"]					= $('#companyId').val();
	
	$.ajax({
		url: "getSpotSurveyorCompletionDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setSpotSurveyorCompletionPopup(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});

}

function openSpotSurveyorCompletionPopup(){
	if(($('#spotSurveyFlag').val() == false || $('#spotSurveyFlag').val() == 'false')&& ($('#accidentClaimConfig').val() == true || $('#accidentClaimConfig').val() == 'true')){
		showMessage('info', 'Please Update Spot Survey Details First !');
		return false;
	}
	$('#spotSurveryourCompletionDetails').modal('show');
}

function setSpotSurveyorCompletionPopup(data){
	var spotSurveyorDetails = data.spotSurveyorDetails; 
	if(spotSurveyorDetails != undefined){
		$('#spotSurveyorDetailsId').val(spotSurveyorDetails.spotSurveyorDetailsId)
		
		$('#spotSurveyorNameOnUpdate').val(spotSurveyorDetails.spotSurveyorName)
		$('#spotSurveyorMobileOnUpdate').val(spotSurveyorDetails.spotSurveyorMobile)
		$('#spotSurveyorCompanyOnUpdate').val(spotSurveyorDetails.spotSurveyorCompany)
		
		$('#spotSurveyorDateOnUpdate').val(spotSurveyorDetails.spotSurveyorDateStr)
		$('#spotSurveyorTimeOnUpdate').val(spotSurveyorDetails.spotSurveyorTimeStr)
		$('#spotSurveyorRemarkOnUpdate').val(spotSurveyorDetails.spotSurveyorRemark)
		
		
		$('#showSpotSurveyorNameOnUpdate').html(spotSurveyorDetails.spotSurveyorName)
		$('#showSpotSurveyorMobileOnUpdate').html(spotSurveyorDetails.spotSurveyorMobile)
		$('#showspotSurveyorCompanyOnUpdate').html(spotSurveyorDetails.spotSurveyorCompany)
		
		
		$('#spotSurveyorCompletionDate').val(spotSurveyorDetails.spotSurveyorCompletionDateStr)
		$('#spotSurveyorCompletionTime').val(spotSurveyorDetails.spotSurveyorCompletionTimeStr)
		$('#spotSurveyorCompletionRemark').val(spotSurveyorDetails.spotSurveyorCompletionRemark)
		$('#showSpotSUrveyorCompletionDate').html(spotSurveyorDetails.spotSurveyorCompletionDateStr)
		$('#showSpotSUrveyorCompletionTime').html(spotSurveyorDetails.spotSurveyorCompletionTimeStr)
		$('#showSpotSUrveyorCompletionRemark').html(spotSurveyorDetails.spotSurveyorCompletionRemark)
		if(spotSurveyorDetails.spotSurveyorCompletionDateStr != undefined){
		$("#spotSurveyCompletionFlag").val(true);
		$('#step1_3').css('color', 'green');
        $('#link1_3').css('color', 'green');
        $('#stepCheck1_3').show();
		}
	}
}


function updateSpotSurveyorCompletionDetails(){

	if($("#spotSurveyorCompletionDate").val() == "" || $("#spotSurveyorCompletionDate").val()  == undefined){
		showMessage('info', 'Please Enter Completion Date!');
		hideLayer();
		return false
	}
	if($("#spotSurveyorCompletionTime").val() == ""  || $("#spotSurveyorCompletionTime").val()  == undefined){
		showMessage('info', 'Please Enter Completion Time!');
		hideLayer();
		return false
	}
	if($("#spotSurveyorCompletionRemark").val()  == undefined || $("#spotSurveyorCompletionRemark").val().trim() == "" ) {
		showMessage('info', 'Please Enter Completion Remark!');
		hideLayer();
		return false
	}
	
	var jsonObject					= new Object();
	jsonObject["userId"]			= $("#userId").val();
	jsonObject["companyId"]			= $("#companyId").val();
	jsonObject["accidentId"]		= $("#dAccidentId").val();
	
	jsonObject["spotSurveyorName"]		= $("#spotSurveyorNameOnUpdate").val();
	jsonObject["spotSurveyorMobile"]	= $("#spotSurveyorMobileOnUpdate").val();
	jsonObject["spotSurveyorCompany"]	= $("#spotSurveyorCompanyOnUpdate").val();
	jsonObject["spotSurveyorDate"]		= $("#spotSurveyorDateOnUpdate").val();
	jsonObject["spotSurveyorTime"]		= $("#spotSurveyorTimeOnUpdate").val();
	jsonObject["spotSurveyorRemark"]	= $("#spotSurveyorRemarkOnUpdate").val();
	
	jsonObject["spotSurveyorCompletionDate"]		= $("#spotSurveyorCompletionDate").val();
	jsonObject["spotSurveyorCompletionTime"]		= $("#spotSurveyorCompletionTime").val();
	jsonObject["spotSurveyorCompletionRemark"]		= $("#spotSurveyorCompletionRemark").val().trim();
	jsonObject["spotSurveyorDetailsId"]				= $("#spotSurveyorDetailsId").val();
	
	showLayer();
	$.ajax({
		url: "saveUpdateSpotSurveyorDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.saveSuccess != undefined && data.saveSuccess){
				showMessage('success', 'Data Saved Successfully !');
			}
			location.reload();
			hideLayer();
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});

}

function saveKeepOpenDetails(){
	
	if($("#keepOpenDate").val() == "" || $("#keepOpenDate").val()  == undefined){
		showMessage('info', 'Please Enter Keep Open Date!');
		hideLayer();
		return false
	}
	if($("#keepOpenRemark").val() == ""  || $("#keepOpenRemark").val()  == undefined){
		showMessage('info', 'Please Enter Keep Open Remark!');
		hideLayer();
		return false
	}

	var jsonObject							= new Object();
	jsonObject["serveyorDetailsId"]			= $('#serveyorDetailsId').val();
	jsonObject["userId"]					= $('#userId').val();
	jsonObject["keepOpenDate"]				= $('#keepOpenDate').val();
	jsonObject["keepOpenRemark"]			= $('#keepOpenRemark').val();
	showLayer();
	$.ajax({
		url: "saveKeepOpenDetails",
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

function keepOpenPopup(){
	showLayer();
	if(Number($('#status').val()) < 5 ){
		showMessage('info', 'Please Complete Final Survey done !');
		hideLayer();
		return false;
	}
	
	if($('#accidentDocValidate').val() == true || $('#accidentDocValidate').val() == 'true'){
	 validateDocUploaded(4);
	 setTimeout(() => {
		 let docFound = $('#docExist').val();
		 if(docFound == false || docFound == 'false'){ 
			 $('#documentStatusId').val(4);
				hideLayer();
			 return false;
		 }else{
			 $('#keepOpenModal').modal('show');
				hideLayer();
		 }
	 }, 600);
	 }else{
		 $('#keepOpenModal').modal('show'); 
			hideLayer();
	 }
	
}

function saveBeforeEstimate(){
	
	if($("#insuranceSubmitDate").val() == "" || $("#insuranceSubmitDate").val()  == undefined){
		showMessage('info', 'Please Select Insurance Submited Date!');
		hideLayer();
		return false
	}
	if($("#callFinalSurveyorDate").val() == ""  || $("#callFinalSurveyorDate").val()  == undefined){
		showMessage('info', 'Please Select Call Final Surveyor Date!');
		hideLayer();
		return false
	}
	
	var jsonObject							= new Object();
	jsonObject["serveyorDetailsId"]			= $('#serveyorDetailsId').val();
	jsonObject["userId"]					= $('#userId').val();
	jsonObject["insuranceSubmitDate"]		= $('#insuranceSubmitDate').val();
	jsonObject["callFinalSurveyorDate"]		= $('#callFinalSurveyorDate').val();
	showLayer();
	$.ajax({
		url: "saveBeforeEstimate",
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

function beforeEstimatePopup(){
	showLayer();
	if(Number($('#status').val()) < 5){
		showMessage('info', 'Please Complete the Keep Open  !');
		hideLayer();
		return false;
	}
	if($('#accidentDocValidate').val() == true || $('#accidentDocValidate').val() == 'true'){
	 validateDocUploaded(6);
	 setTimeout(() => {
		 let docFound = $('#docExist').val();
		 if(docFound == false || docFound == 'false'){ 
			 $('#documentStatusId').val(6);
			 hideLayer();
			 return false;
		 }else{
				$('#beforeEstimateModal').modal('show');
				hideLayer();
		 }
	 }, 600);
	 }else{
			$('#beforeEstimateModal').modal('show');
			hideLayer();
	 }
}

 function validateDocUploaded(documentStatusId){
	 $('#docExist').val(true);
	var jsonObject = new Object();
	jsonObject["accidentId"]			= $('#dAccidentId').val();
	jsonObject["documentStatusId"]		= documentStatusId;
	jsonObject["companyId"]				= $('#companyId').val();
	$.ajax({
		url: "validateDocUploaded",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.docExist == false){
				 $('#docExist').val(false);
				showMessage('info', 'Please Upload Doc first to process !! ')
				$('#uploadIncidentFile').modal('show');
			}
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}
 function setServiceDetails(data){
		let SERVICE_TYPE_SE		= 1;
		let SERVICE_TYPE_WO		= 2;
		let SERVICE_TYPE_DSE	= 3;
	 var list = data;
	 var dseLink ="";
	 var woLink ="";
	 var seLink	="";
	 var finalLink	="";
	 if(list != undefined && list.length > 0){
		 for (var i = 0; i < list.length; i++) {
			 if(list[i].serviceType == SERVICE_TYPE_SE){
				 seLink +='<a target="_blank" href="showServiceEntryDetails?serviceEntryId='+list[i].serviceId+'">SE-'+list[i].serviceNum+'</a> ,'; 
			 }else if(list[i].serviceType == SERVICE_TYPE_WO){
				 woLink += '<a target="_blank" href="showWorkOrder?woId='+list[i].serviceId+'">WO-'+list[i].serviceNum+'</a> ,';	 
			 }else if (list[i].serviceType == SERVICE_TYPE_DSE){
				 dseLink += '<a target="_blank" href="showDealerServiceEntries?dealerServiceEntriesId='+list[i].serviceId+'">DSE-'+list[i].serviceNum+'</a> ,';	 
			 }
		}
		 finalLink += dseLink; 
		 finalLink += seLink; 
		 finalLink += woLink; 
		 finalLink += $('#serviceNumber').text();
		 $('#serviceNumber').text('');
		 $('#serviceNumber').html(finalLink);
	 }
	 var status = $('#status').val();
	 if( status >= 7 && status <= 9){
		 $("#serviceStatusId").val(4);
	 }else{
		 $("#serviceStatusId").val(0);
	 }
 }