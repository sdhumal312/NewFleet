$(document).ready(function() {
	getIssueDetails();
	getIssueAnalysisDetails();
	
});

function getIssueDetails(){
	showLayer();
	var jsonObject						= new Object();
	jsonObject["issueId"]				= $('#issueId').val();
	jsonObject["companyId"]				= $('#companyId').val();
	$.ajax({
		url: "getIssueDetailsForAnalysisByIssueId",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			setIssueDetailsForAnalysisByIssueId(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}
function setIssueDetailsForAnalysisByIssueId (data){
	
	if(data.issueDetails != undefined ){
		var issueDetails = data.issueDetails;
		$("#vehicle").html(issueDetails.issues_VEHICLE_REGISTRATION)
		$("#vehicleType").html(issueDetails.vehicleType)
		$("#vehicleModel").html(issueDetails.vehicleModel)
		$("#complaint").html(issueDetails.issues_SUMMARY)
		$("#breakdownType").html(issueDetails.partCategoryName)
		$("#driver").html(issueDetails.issues_DRIVER_NAME)
		$("#route").html(issueDetails.routeName)
		$("#issueDate").html(issueDetails.issues_REPORTED_DATE)
		$("#issueNumber").html(issueDetails.issues_NUMBER)
	}
	
}

function getIssueAnalysisDetails (){
	showLayer();
	var jsonObject						= new Object();
	jsonObject["issueId"]				= $('#issueId').val();
	jsonObject["companyId"]				= $('#companyId').val();
	console.log("jsonObject",jsonObject)
	$.ajax({
		url: "getIssueAnalysisDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			setIssueAnalysisDetails(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}
function setIssueAnalysisDetails (data){
	if(data.issueAnalysisDetails != undefined ){
		var issueAnalysisDetails = data.issueAnalysisDetails;
		$("#reason").html(issueAnalysisDetails.reason)
		$("#tempSolution").html(issueAnalysisDetails.tempSolution)
		$("#fixSolution").html(issueAnalysisDetails.fixSolution)
		if(issueAnalysisDetails.avoidable){
			$('#avoidableLabelId').addClass('focus active')
			$('#mandatoryLabelId').removeClass('focus active')
			$("#avoidableId").prop('checked',true);
			$("#mandatoryId").prop('checked',false);
		}
		if(issueAnalysisDetails.temporary){
			$('#temporaryId').prop('checked',true)
		}
		$("#isAvoidable").html(issueAnalysisDetails.isAvoidable)
		$("#futurePlan").html(issueAnalysisDetails.futurePlan)
		$("#issueAnalysisId").val(issueAnalysisDetails.issueAnalysisId)
		if(issueAnalysisDetails.permanentIssueId != null && issueAnalysisDetails.permanentIssueId > 0 ){
			$('#msgIssue').hide();
		}
	}
	
}

function saveIssueAnalysis(){
	showLayer();
	
	if($('#reason').val().trim() == "" ){
		showMessage('info','Please Enter Reason')
		 hideLayer();
		return false;
	}
	if($('#permanentId').prop('checked') && $('#fixSolution').val().trim() == "" ){
		showMessage('info','Please Enter Permanent Solution')
		hideLayer();
		return false;
	}
	if($('#futurePlan').val().trim() == "" ){
		showMessage('info','Please Enter Future Plan')
		hideLayer();
		return false;
	}
	
	var jsonObject						= new Object();
	jsonObject["issueAnalysisId"]		= $('#issueAnalysisId').val();
	jsonObject["issueId"]				= $('#issueId').val();
	jsonObject["companyId"]				= $('#companyId').val();
	jsonObject["userId"]				= $('#userId').val();
	jsonObject["reason"]				= $('#reason').val().trim();
	if($("#mandatoryId").prop('checked')){
		jsonObject["isAvoidable"]				= false;
	}else{
		jsonObject["isAvoidable"]				= true;
	}
	jsonObject["tempSolution"]				= $('#tempSolution').val().trim();
	jsonObject["fixSolution"]				= $('#fixSolution').val().trim();
	jsonObject["futurePlan"]				= $('#futurePlan').val().trim();
	jsonObject["issueEncId"]				= $('#issueEncId').val();

	if($('#temporaryId').prop('checked')){
		jsonObject["isTemporary"]  = true;
	}else{
		jsonObject["isTemporary"]  = false;
	}
	console.log("jsonObject",jsonObject)
	
	
	$.ajax({
		url: "saveIssueAnalysis",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			 window.location.replace("showIssues.in?Id="+data.issueEncId+"");
			 hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function checkAvoidable(checkAvoidableId){
	
	if(checkAvoidableId == 1){
		setTimeout(function(){ 
		$('#mandatoryLabelId').addClass('focus active')
		$('#avoidableLabelId').removeClass('focus active')
		$("#mandatoryId").prop('checked',true);
		$("#avoidableId").prop('checked',false);
		}, 30);
	}else{
		setTimeout(function(){ 
		$('#avoidableLabelId').addClass('focus active')
		$('#mandatoryLabelId').removeClass('focus active')
		$("#avoidableId").prop('checked',true);
		$("#mandatoryId").prop('checked',false);
		}, 30);
	}
}