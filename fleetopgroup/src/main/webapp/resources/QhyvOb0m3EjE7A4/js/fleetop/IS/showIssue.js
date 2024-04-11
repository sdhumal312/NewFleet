function changeToBreakDown(){
	  showLayer();
	var jsonObject = new Object();	
	jsonObject["issueId"] = $('#issue_ID').val();	
	jsonObject["breakDownLocation"] = $('#location').val().trim();		
	jsonObject["tripSheetNumber"] = $('#tripNumber').val();		
	jsonObject["vehicleReplaced"] = $('#isVehicleReplaced').prop('checked');		
	if($('#isVehicleReplaced').prop('checked')){
		if($('#replacedVehicle').val() == ""){
			showMessage('info','Please Select Replace Vehicle');
			hideLayer();
			return false;
		}
	}
	
	if($('#brRemark').val().trim() == ""){
		showMessage('info','Please Enter Remark');
		hideLayer();
		return false;
	}
	jsonObject["vid"] = $('#vid').val();	
	jsonObject["replacedVehicle"] = $('#replacedVehicle').val();		
	jsonObject["replaceLocation"] = $('#replacedLocation').val().trim();		
	jsonObject["tripCancelled"] = $('#isTripCancelled').prop('checked');		
	jsonObject["cancelledKM"] = $('#cancelledKM').val();		
	jsonObject["companyId"] = $('#companyId').val();		
	jsonObject["remark"] = $('#brRemark').val().trim();		
	$.ajax({
		url:"changeIssueType",
		type:"POST",
		dataType:'json',
		data :jsonObject ,
		success: function(data){
			hideLayer();
			$('#breakDown').modal("hide");
			if(data.success == true || data.success === 'true' ){
				showMessage('success', 'Issue type Changed successfully !');	
				setTimeout(function(){
					location.reload();
						},500);
				
			}else if(data.replaceVehicleNotActive != undefined && data.replaceVehicleNotActive == true) {
				showMessage('info', 'Replace Vehicle In '+data.replaceVehicleStatus+' Status Hence You Can Not Create Issue');
				hideLayer();
				
			}else if(data.replaceVehicleAndIssueVehicleSame != undefined && data.replaceVehicleAndIssueVehicleSame == true) {
				showMessage('info','Replace Vehicle Can Not Be Same As Issue Vehicle');
				hideLayer();
				
			}else if (data.already === 'true' || data.already== true) {
				showMessage('info', ' can not change into Same issue type !');
			}			
		},
		error: function (e) {
			hideLayer();		
			showMessage('errors', 'Some Error Occurred!');
		}				
	})	
}



function changeIssueType(){	
	$('#breakDown').modal("show");	
}

$(document).ready(function() {
	getIssueAnalysisDetails();
	
	if(Number($("#issuetypeId").val()) == 6 && Number($("#categoryId").val()) == Number($("#tyreCategoryConfigId").val())){
		$('#assignTyre').show();	
	}
	
	$("#confirmedWithDriver").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getDriverSearchAllListFuel.in?Action=FuncionarioSelect2",
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
                        	text: e.driver_empnumber + " - " + e.driver_firstname+" "+e.driver_Lastname +" - "+e.driver_fathername,
                            slug: e.slug,
                            id: e.driver_id
                        }
                    })
                }
            }
        }
    }), $("#confirmedWithAssignee").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getUserEmailId_Assignto",
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
                            text: e.firstName + " " + e.lastName,
                            slug: e.slug,
                            id: e.user_id
                        }
                    })
                }
            }
        }
    });
	
});

function mountTyre(){
	window.location.replace("vehicleTyreAssignmentFromIssue.in?issueId="+$("#showIssueId").val()+"");
}

function getIssueAnalysisDetails(){
	showLayer();
	var jsonObject						= new Object();
	jsonObject["issueId"]				= $('#showIssueId').val();
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
		console.log("setIssueAnalysisDetails",issueAnalysisDetails);
		$("#reason").html(issueAnalysisDetails.reason);
		$("#tempSolution").html(issueAnalysisDetails.tempSolution);
		$("#fixSolution").html(issueAnalysisDetails.fixSolution);
		if(issueAnalysisDetails.avoidable){
			$('#avoidableLabelId').addClass('focus active');
			$('#mandatoryLabelId').removeClass('focus active');
			$("#avoidableId").prop('checked',true);
			$("#mandatoryId").prop('checked',false);
		}
		$("#isAvoidable").html(issueAnalysisDetails.isAvoidable);
		$("#futurePlan").html(issueAnalysisDetails.futurePlan);
		$("#issueAnalysisId").val(issueAnalysisDetails.issueAnalysisId);
		
		
		
		$("#showReason").html(issueAnalysisDetails.reason);
		if(issueAnalysisDetails.avoidable){
			$('#showIsAvoidable').html('YES');
		}else{
			$('#showIsAvoidable').html('NO');
		}
		$("#showTempSolution").html(issueAnalysisDetails.tempSolution);
		$("#showFixSolution").html(issueAnalysisDetails.fixSolution);
		$("#showFuturePlan").html(issueAnalysisDetails.futurePlan);
		
	}
	
}
$(document).ready(function() {

    $("#replacedVehicle").select2( {
		minimumInputLength:2, minimumResultsForSearch:10, ajax: {
			url:"getVehicleSearchServiceEntrie.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
				return {
					term: a
				}
			}
		, results:function(a) {
			return {
				results:$.map(a, function(a) {
					return {
						text: a.vehicle_registration, slug: a.slug, id: a.vid
					}
				})
			}
		}
	}
}
    );
});


function checkVehicleReplace(){
	if($("#isVehicleReplaced").prop('checked')){
		$('#replaceVehicleRow').show();
	}else{
		$('#replaceVehicleRow').hide();
		$('#replacedVehicle').select2("val",'');
		$('#replacedLocation').val('');
		
	}
}
function checkTripCancelled(){
	if($("#isTripCancelled").prop('checked')){
		$('#cancelledKmDiv').show();
	}else{
		$('#cancelledKmDiv').hide();
		$('#cancelledKm').val('');
	}
}

function validateReplaceVehicle(){
	if($('#vid').val() == $('#replacedVehicle').val()){
		$('#replacedVehicle').select2('data', '');
		showMessage('info','Replace Vehicle Can Not Be Same As Issue Vehicle');
		return false;
		
	}
	
	var jsonObject						= new Object();
	jsonObject["vid"]					= $('#replacedVehicle').val();
	jsonObject["companyId"]				= $('#companyId').val();
	
	$.ajax({
             url: "checkVehicleStatusByVid",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 if(data.vStatusId == 2 || data.vStatusId == 3 || data.vStatusId == 4){
            			$('#replacedVehicle').select2('data', '');
            			showMessage('info', 'Replce Vehicle In '+data.vehicle_Status+' Status Hence You Can Not Create Issue');
            			return false;
            		}
            	 
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!');
            	 hideLayer();
             }
	});
}
function saveWOIssueRemark(){
	if($('#woIssueRemark').val() == null || $('#woIssueRemark').val().trim() == ''){
		$('#woIssueRemark').focus();
		showMessage('warning','Please enter Remark !');
		return false;
	}else if(Number($('#confirmedWithDriver').val()) <= 0){
		$('#confirmedWithDriver').select2('focus');
		showMessage('warning','Please Select Driver !');
		return false;
	}else if(Number($('#confirmedWithAssignee').val()) <= 0){
		$('#confirmedWithAssignee').select2('focus');
		showMessage('warning','Please Select Assignee !');
		return false;
	}
	return true;
}
function setDriverName(){
	if(Number($('#driverIdRemark').val())> 0){
	$('#confirmedWithDriver').select2('data',{
		id : $('#driverIdRemark').val(),
		text : $('#driverNameRemark').val()
	})
	}
}

function showAssignTyre(){
	var jsonObject						= new Object();
	jsonObject["issueId"]				= $('#showIssueId').val();
	jsonObject["companyId"]					= $("#companyId").val();

	showLayer();
	$.ajax({
		url: "getIssueAssignTyre",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setIssueAssignTyre(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setIssueAssignTyre (data){
	
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
		
		$("#tyreAssignModal").modal('show');
		columnArray = [];
		}
	}

	
	function dismountTyre(){
		$.getJSON("getVehicleOdoMerete.in", {
			vehicleID: $("#vid").val(),
			ajax: "true"
		}, function(a) {
			console.log("result",a)
			if(a.vehicle_Odometer != undefined && a.vehicle_Odometer > 0){
				$('#dismountOdometer').val(a.vehicle_Odometer);
				$('#showDismountOdometer').val(a.vehicle_Odometer);
			}
		})
		$("#dismountModal").modal('show');
	}
	
	function removeTyre(){
		var jsonObject								= new Object();
		jsonObject["vid"]							= $('#vid').val();
		jsonObject["LP_ID"]							= $('#LP_ID').val();
		jsonObject["tyreId"]						= $('#tyreId').val();
		jsonObject["dismountDate"]					= $('#dismountDate').val();
		jsonObject["dismountOdometer"]				= $('#dismountOdometer').val();
		jsonObject["remark"]						= $('#remark').val();
		jsonObject["companyId"]						= $('#companyId').val();
		jsonObject["tyrePositionId"]				= "SP-0";
		jsonObject["tyreNumber"]					= $('#tyreNumber1').val();
		jsonObject["oldTyreMoveId"]					= $('#oldTyreMoveId').val();
		jsonObject["tyreGauge"]						= $('#tyreGaugeVal').val();
		
		
		if($('#dismountDate').val() == undefined || $('#dismountDate').val() == "" || $('#dismountDate').val() == 0){
			showMessage('info','Please Select Dismount Date')
			return false;
		}
		if($('#dismountOdometer').val() == undefined || $('#dismountOdometer').val() == "" || $('#dismountOdometer').val() == 0){
			showMessage('info','Please Enter Dismount Odometer')
			return false;
		}
		if($('#oldTyreMoveId').val() == undefined || $('#oldTyreMoveId').val() == "" || $('#oldTyreMoveId').val() == 0){
			showMessage('info','Please Enter Old Tyre Move To')
			return false;
		}
		if($('#remark').val() == undefined || $('#remark').val().trim() == ""){
			showMessage('info','Please Select Dismount Odometer')
			return false;
		}
		
		showLayer();
		$.ajax({
			url: "tyreRemoveFromVehicle",
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
	
	
