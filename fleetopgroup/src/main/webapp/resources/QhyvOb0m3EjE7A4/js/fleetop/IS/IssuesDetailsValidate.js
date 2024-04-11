$(document).ready(function() {
	$('.clockpicker').clockpicker({
		placement: 'bottom',
		align: 'right',
		autoclose: true
	});
			
	var today = new Date();
	var time = today.getHours();
	var minute = today.getMinutes();
	var isEdit = $('#isEdit').val();
			
	if(today.getMinutes()<10){
		minute = '0'+minute;
	} 
	
	if(today.getHours()<10){
		time = '0'+time;
	}
	if( $('#editIssuesstartTime').val() != "" || $('#editIssuesstartTime').val() != null ){
		$('#issueStartTime').val($('#editIssuesstartTime').val());
	}else{
		$('#issueStartTime').val(time+':'+minute);
	}
			
	
	
	if(!isEdit){
		var serverDateStr	= $('#serverDateStr').val();
		var serverTimeStr	= $('#serverTimeStr').val();
		$("#reportdDate").val(serverDateStr);
		$('#issueStartTime').val(serverTimeStr);
	}
	
});

$(document).ready(function() {
    $("#IssuesSelectVehicle").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getIssuesVehicleList.in?Action=FuncionarioSelect2",
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
                            text: e.vehicle_registration,
                            slug: e.slug,
                            id: e.vid
                        }
                    })
                }
            }
        }
    }),$("#replacedVehicle").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getIssuesVehicleList.in?Action=FuncionarioSelect2",
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
                            text: e.vehicle_registration,
                            slug: e.slug,
                            id: e.vid
                        }
                    })
                }
            }
        }
    }),$("#FuelRouteList").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getTripRouteSerachList.in?Action=FuncionarioSelect2",
            dataType: "json",
            type: "GET",
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
                            text: e.routeName,
                            slug: e.slug,
                            id: e.routeID
                        }
                    })
                }
            }
        }
    });
    
    if($('#FuelRouteList').val() != undefined &&  Number($('#routeID').val()) > 0){
		$('#FuelRouteList').select2('data', {
			id : $('#routeID').val(),
			text : $('#routeName').val()
		});
	}
    
    if($("#vid").val() != undefined || $("#vid").val() != null){// will show only on edit
		$('#IssuesSelectVehicle').select2('data', {
			id : $("#vid").val(),
			text : $("#vidReg").val()
		});
	}
    
    $("#driverId").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getIssuesDriverList.in?Action=FuncionarioSelect2",
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
                            text: e.driver_empnumber + " - " + e.driver_firstname+" "+e.driver_Lastname+" - "+e.driver_fathername,
                            slug: e.slug,
                            id: e.driver_id
                        }
                    })
                }
            }
        }
    });
    if($("#editDriverId").val() != undefined || $("#editDriverId").val() != null){// will show only on edit
		$('#driverId').select2('data', {
			id : $("#editDriverId").val(),
			text : $("#editDriverName").val()
		});
	}
	
    $("#subscribe").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        multiple: !0,
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
                            id  : e.user_email+"_"+e.user_id
                        }
                    })
                }
            }
        }
    });
    if($("#editSubscribeId").val() != undefined || $("#editSubscribeId").val() != null){// will show only on edit
		$('#subscribe').select2('data', {
			id : $("#editSubscribeId").val(),
			text : $("#editSubscribeName").val()
		});
	}
    
    $("#issuesBranch").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getIssuesBranch.in?Action=FuncionarioSelect2",
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
                            text: e.branch_name + " - " + e.branch_code,
                            slug: e.slug,
                            id: e.branch_id
                        }
                    })
                }
            }
        }
    });
    if($("#editBranchId").val() != undefined || $("#editBranchId").val() != null){// will show only on edit
		$('#issuesBranch').select2('data', {
			id : $("#editBranchId").val(),
			text : $("#editBranchName").val()
		});
	}
    
    $("#issuesDepartnment").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getIssuesDepartnment.in?Action=FuncionarioSelect2",
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
                            text: e.depart_name + " - " + e.depart_code,
                            slug: e.slug,
                            id: e.depart_id
                        }
                    })
                }
            }
        }
    });
    if($("#editDeptId").val() != undefined || $("#editDeptId").val() != null){// will show only on edit
		$('#issuesDepartnment').select2('data', {
			id : $("#editDeptId").val(),
			text : $("#editDeptName").val()
		});
	}
})

/*function register(event) {
	event.preventDefault();
	$(".alert").html("").hide();
	$(".error-list").html("");
	if ($("#to").val() == "") {
		$("#emptyto").show().html('Please specify at least one recipient.');
		return;
	}
	var formData = $('form').serialize();
	var issuestype = $("#IssuesType").val();
	$.post("<c:url value="/saveVehicleIssues"/>", formData, function(data) {
			if (data.message == "success" && data.error != null) {
				window.location.href = '<c:url value="/showIssues?Id=' + data.error + '"></c:url>';
			} else {
				window.location.href = '<c:url value="/issues/1.in"></c:url>';

			}
		})
	.fail(function(data) {
		if (data.responseJSON.error.indexOf("MailError") > -1) {
			$("#globalError").show().html('Mail id is empty');
		} else if (data.responseJSON.error == "MailSentExist") {
			$("#emailError").show().html( data.responseJSON.message);
		} else {
			var errors = $.parseJSON(data.responseJSON.message);
			$.each(errors, function(index, item) {
				$("#" + item.field + "Error").show().html(item.defaultMessage);
			});
			errors = $.parseJSON(data.responseJSON.error);
			
			$.each(errors, function(index, item) {
				$("#globalError").show().append( item.defaultMessage + "<br>");
			});
		}
	});
}
function closeArert() {
	$("#success").css("display", "none");
}*/

function validateIssue(){
	var issueType = $("#issueType").val();
	
	switch (Number(issueType)){

	case 1:
		if($("#IssuesSelectVehicle").val() == "" || $("#IssuesSelectVehicle").val() == null){
			showMessage('info','Please Select Vehicle');
			hideLayer();
			return false;
		}
		if($('#partCategory').is(":visible")) {
			if($("#partCategory").val() == "" || $("#partCategory").val() == null){
				showMessage('info','Please Select Category');
				hideLayer();
				return false;
			}
		}
		
		if($('#validateOdometer').val() == true || $('#validateOdometer').val() === 'true' ){
			if(!validateOdometer()){
				hideLayer();
				return false;
			}
		}
		validateMaxOdameter(); 
		break;
	case 2:
		if($("#driverId").val() == "" || $("#driverId").val() == null){
			showMessage('info','Please Select Driver');
			hideLayer();
			return false;
		}
		break;
	case 3:
		if($("#issuesBranch").val() == "" || $("#issuesBranch").val() == null){
			showMessage('info','Please Select Branch');
			hideLayer();
			return false;
		}
		
		if($("#issuesDepartnment").val() == "" || $("#issuesDepartnment").val() == null){
			showMessage('info','Please Select Department');
			hideLayer();
			return false;
		}
		
		break;
	case 4:
		if($("#customerName").val() == "" || $("#customerName").val() == null){
			showMessage('info','Please Enter Cumstomer Name');
			hideLayer();
			return false;
		}
		break;
	case 5:
		if($("#issuesBranch").val() == "" || $("#issuesBranch").val() == null){
			showMessage('info','Please Select Branch');
			hideLayer();
			return false;
		}
		if($("#issuesDepartnment").val() == "" || $("#issuesDepartnment").val() == null){
			showMessage('info','Please Select Department');
			hideLayer();
			return false;
		}
		break;	
	}
	
	if($("#reportdDate").val() == "" || $("#reportdDate").val() == null){
		showMessage('info','Please Select Issue Reported Date');
		hideLayer();
		return false;
	}
	if($("#issueStartTime").val() == "" || $("#issueStartTime").val() == null){
		showMessage('info','Please Select Issue Reported Time');
		hideLayer();
		return false;
	}
	
	if($("#issuesSummary").val() == "" || $("#issuesSummary").val() == null){
		showMessage('info','Please Enter Issue Summary');
		hideLayer();
		return false;
	}
	if($("#subscribe").val() == "" || $("#subscribe").val() == null){
		showMessage('info','Please Enter Whome You Assign Issue');
		hideLayer();
		return false;
	}
	
	if(Number($('#issueType').val()) == 6){
		if($('#vehicleReplaced').prop('checked')){
			if(Number($('#replacedVehicle').val()) <= 0){
					$('#replacedVehicle').select2('focus');
					showMessage('info','Please Select Replaced With Vehicle !');
					hideLayer();
					return false;
			}
		}
	}
	
	/*if(== true){
		return true;
	}*/
	
	return true;
	
}

$(document).ready(function() {
	$('#driverFieldMendotry').addClass('hide');
	$("#IssuesSelectVehicle").change(function() {
		if($("#IssuesSelectVehicle").val() != "" ){
			getlastOpenIssue();
		}
	});
	$("#driverId").change(function() {
		 if($("#issueType").val() == 2 && $("#driverId").val() != ""){
			 getlastOpenIssue();
		 }
	 });
});


//function vehicleChange(){
//	$.getJSON("getIssuesVehicleOdoMerete.in", {
//        FuelvehicleID: $('#IssuesSelectVehicle').val(),
//        ajax: "true"
//    }, function(e) {
//        var t = "";
//        t = e.vehicle_Odometer, document.getElementById("Issues_Odometer").placeholder = t
//        $('#vehicle_ExpectedOdameter').val(e.vehicle_ExpectedOdameter);
//        $('#vehicle_Odameter').val(e.vehicle_Odometer);
//      
//        if($("#editIssue").val() == undefined ){
//        	$('#Issues_Odometer').val(e.vehicle_Odometer);
//        }
//        /*if(e.gpsOdameter != null && e.gpsOdameter > 0){
//        	$('#GPS_ODOMETER').val(e.gpsOdameter);
//        	$('#Issues_Odometer').val(e.vehicle_Odometer);
//        	$('#gpsOdometerRow').show();
//        	//$('#VehicleOdoRow').hide();
//        }else{
//        	$('#gpsOdometerRow').hide();
//        	$('#VehicleOdoRow').show();
//        	$('#GPS_ODOMETER').val('');
//        }*/
//    })
//}

function vehicleChange() {
	$.getJSON("getVehicleOdoMerete.in", {
        vehicleID: $("#IssuesSelectVehicle").val(),
        ajax: "true"
    }, function(a) {
        var b = "";
        b = a.vehicle_Odometer, document.getElementById("Issues_Odometer").placeholder = b;
        $('#vehicle_ExpectedOdameter').val(a.vehicle_ExpectedOdameter);
        $('#vehicle_Odameter').val(a.vehicle_Odometer);
        if(a.gpsOdameter != undefined && a.gpsOdameter > 0){
        	$('#vehicle_Odometer').val(parseInt(a.gpsOdameter));
        }
        if($("#editIssue").val() == undefined ){
        	$('#Issues_Odometer').val(a.vehicle_Odometer);
        }
    });
	backdateOdometerValidation()
	
}
function validateOdometer(){ 
	var maxOdometer= Number($("#backDateMaxOdo").val());
	var minOdometer =Number($("#backDateMinOdo").val());
	
	var Odometer		 = Number($('#vehicle_Odameter').val());
	var tripOpeningKM    = Number($('#Issues_Odometer').val());
	
	if (tripOpeningKM == 0){
		$('#vehicle_Odometer').focus();
		showMessage('errors', ' Odometer should be greater than 0 ' );
		return false;
	}
	if(tripOpeningKM < minOdometer){
		$('#vehicle_Odometer').focus();
		showMessage('errors', ' Odometer should be greater than '+minOdometer );
		return false;
	}
	
	if($('#vehicle_ExpectedOdameter').val() == undefined || $('#vehicle_ExpectedOdameter').val() == '' || $('#vehicle_ExpectedOdameter').val() == null){
		return true;
	}
	
	if(tripOpeningKM > maxOdometer){
		$('#vehicle_Odometer').focus();
		showMessage('errors', ' Odometer cannot be greater than '+maxOdometer );
		return false;
		
	}
	return true;
}


$(document).ready(function() {
 $("#IssuesSelectVehicle").change(function() {
	 
	 vehicleChange();
  
}),$("#issueType").change(function(){
	changeIssuesType();
	});
});



function changeIssuesType(){
	var issueType = $("#issueType").val();
	
	switch (Number(issueType)){
	case 1:
        $("#vehicleDiv").show(), $("#vGroupDiv").hide(), $("#driverDiv").show(),$("#odometerDiv").show(),$("#customerDiv").hide(), $("#branchDiv").hide(),$("#departmentDiv").hide(),$("#gpsOdometerRow").hide();
        $("#customerName").val(''),$("#issuesBranch").select2("val", "");$("#issuesDepartnment").select2("val", "");$('#driverFieldMendotry').addClass('hide');$("#partCategoryDiv").show();
        break;
    case 2:
        $("#vehicleDiv").hide(), $("#vGroupDiv").hide(), $("#driverDiv").show(),$("#odometerDiv").hide(),$("#customerDiv").hide(), $("#branchDiv").hide(),$("#departmentDiv").hide(),$("#gpsOdometerRow").hide();
        $("#IssuesSelectVehicle").select2("val", "");$("#Issues_Odometer").val(''),$("#customerName").val(''),$("#issuesBranch").select2("val", "");$("#issuesDepartnment").select2("val", "");$('#driverFieldMendotry').removeClass('hide');
        $("#partCategoryDiv").hide();
        break;
    case 3:
    	$("#vehicleDiv").hide(), $("#vGroupDiv").show(), $("#driverDiv").hide(),$("#odometerDiv").hide(),$("#customerDiv").hide(), $("#branchDiv").show(),$("#departmentDiv").show(),$("#gpsOdometerRow").hide();
    	$("#IssuesSelectVehicle").select2("val", "");$("#driverId").select2("val", ""); $("#Issues_Odometer").val(''),$("#customerName").val('');$("#partCategoryDiv").hide();
        break;
    case 4:
    	$("#vehicleDiv").hide(), $("#vGroupDiv").hide(), $("#driverDiv").hide(),$("#odometerDiv").hide(),$("#customerDiv").show(), $("#branchDiv").hide(),$("#departmentDiv").hide(),$("#gpsOdometerRow").hide();
    	$("#IssuesSelectVehicle").select2("val", ""); $("#driverId").select2("val", ""); $("#Issues_Odometer").val(''); $("#issuesBranch").select2("val", "");$("#issuesDepartnment").select2("val", "");
    	$("#partCategoryDiv").hide();
    	break;
    case 5:
    	$("#vehicleDiv").hide(), $("#vGroupDiv").hide(), $("#driverDiv").hide(),$("#odometerDiv").hide(),$("#customerDiv").hide(), $("#branchDiv").show(),$("#departmentDiv").show(),$("#gpsOdometerRow").hide();
    	$("#IssuesSelectVehicle").select2("val", ""); $("#driverId").select2("val", "");$("#Issues_Odometer").val('');$("#Issues_Odometer").val('');$("#partCategoryDiv").hide();
    	break;
	}
	
}

function validateMaxOdameter(){
	if($('#validateOdometer').val() == false || $('#validateOdometer').val() == 'false' ){
		
	var IssuesType 				 		= Number($('#IssuesType').val());
	var validateOdometerInIssues 		= $('#validateOdometerInIssues').val();
	var validateMinOdometerInIssues  	= $('#validateMinOdometerInIssues').val();
	
	var current  	= new Date().getFullYear() + '-' + ('0' + (new Date().getMonth()+1)).slice(-2)+ '-' + ('0' + (new Date().getDate())).slice(-2);
	var reportDate 	= $('#reportdDate').val().split("-").reverse().join("-");
	var date 		= moment(reportDate);
	var now 		= moment(current);
	
	var expectedOdometer 		= Number($('#vehicle_ExpectedOdameter').val());
	var vehicleOdometer  		= Number($('#vehicle_Odameter').val());
	var issueOdometer	 		= Number($('#Issues_Odometer').val());
	var expectedMaxOdometer 	= Number(expectedOdometer + vehicleOdometer);
	var minOdometer  			= Number($('#odometerWhileCreatingIssue').val());
	var maxOdometer    			= Number($("#issueMaxOdomter").val());
	if(maxOdometer > 0)
	 $('#odometerRange').text('Odometer Range Between '+minOdometer+' And '+maxOdometer+'');
	
	if(validateMinOdometerInIssues == 'true' || validateMinOdometerInIssues == true){
		
		if (!date.isBefore(now)) {
			if(issueOdometer == ""){
				$('#Issues_Odometer').val(vehicleOdometer);
			}
			if((issueOdometer > 0) && (issueOdometer < minOdometer) ){
				showMessage('info', 'Odometer Can Not Be Less Than Vehicle Odometer '+minOdometer);
			//	$('#Issues_Odometer').val(vehicleOdometer);
				hideLayer();
				return false;
			}
			if((issueOdometer > 0) && (issueOdometer > maxOdometer) ){
				showMessage('info', 'Odometer Can Not Be Greated Than Expected Odometer '+maxOdometer);
			//	$('#Issues_Odometer').val(vehicleOdometer);
				hideLayer();
				return false;
			}
		}	
	}
	
	if(IssuesType != 1 || validateOdometerInIssues == 'false' || validateOdometerInIssues == false){
		return true;
	}
	
	return true;
	}
}

function getVehicleGpsDetails(){
	var allowGPSIntegration	= $('#allowGPSIntegration').val();
	if($("#issueType").val() == 1){
		if($("#IssuesSelectVehicle").val() == "" || $("#IssuesSelectVehicle").val() == null){
			showMessage('info','Please Select Vehicle First');
			return false;
		}
		
		if(allowGPSIntegration == 'true' || allowGPSIntegration == true){
			var jsonObject			= new Object();
			
			if($('#reportdDate').val() != '' && $('#issueStartTime').val() != ''){
				jsonObject["vehicleId"] 					=  $('#IssuesSelectVehicle').val();
				jsonObject["companyId"] 					=  $('#companyId').val();
				jsonObject["dispatchedByTime"] 				=  $('#reportdDate').val();
				jsonObject["dispatchTime"] 					=  $('#issueStartTime').val();
				jsonObject["fromFuel"] 						=  true;
				$.ajax({
			             url: "getVehicleGPSDataAtTime",
			             type: "POST",
			             dataType: 'json',
			             data: jsonObject,
			             success: function (data) {
			            	 
			            	 if(data.VEHICLE_TOTAL_KM != null){
			            		 $('#GPS_ODOMETER').val(data.VEHICLE_TOTAL_KM);
			            		 $('#gpsOdometerRow').show();
			            	 }else{
			            		 $('#gpsOdometerRow').hide();
			            		 $('#GPS_ODOMETER').val('');
			            	 }
			             },
			             error: function (e) {
			            	 showMessage('errors', 'Some error occured!');
			            	 hideLayer();
			             }
				});
			}
			
		} 
	}	
	backdateOdometerValidation();
}

function getlastOpenIssue(){
	var jsonObject				= new Object();
	jsonObject["issueType"]						= $('#issueType').val();
	jsonObject["vid"]							= $('#IssuesSelectVehicle').val();
	jsonObject["driverId"]						= $('#driverId').val();
	jsonObject["issuesBranch"]					= $('#issuesBranch').val();
	jsonObject["issuesDepartnment"]				= $('#issuesDepartnment').val();
	jsonObject["customerName"]					= $('#customerName').val();
	
	$.ajax({
             url: "getlastOpenIssue",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 
            	 if(data.issue != undefined || data.issue != null){
            		 showMessage('info','You Already Have An Issue I-'+data.issue.issues_NUMBER+' On Same '+data.issueType+' ')
            	 }
            	 
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!');
            	 hideLayer();
             }
	});
}

function showHideBreakDownFeilds(){
	if($('#issueType').val() == 6){
		$('#issueLabel').val(5);
		$('.breakdown').show();
	}else{
		$('#issueLabel').val(1);
		$('.breakdown').hide();
	}
}

function showHideVehicleReplaceFeilds(){
	
	if($('#vehicleReplaced').prop('checked')){
		$('.replace').show();
	}else{
		$('.replace').hide();
	}
}
function showHideTripCancelFeilds(){
	if($('#tripCancelled').prop('checked')){
		$('.Cancelled').show();
	}else{
		$('.Cancelled').hide();
	}
}

function validateReplaceVehicle(){
	if($('#IssuesSelectVehicle').val() == $('#replacedVehicle').val()){
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
            	 if(data.vehicle != undefined){
            		var vehicle =  data.vehicle ;
            		 if(vehicle.vStatusId == 2 || vehicle.vStatusId == 3 || vehicle.vStatusId == 4){
            			 $('#replacedVehicle').select2('data', '');
            			 showMessage('info', 'Replce Vehicle In '+vehicle.vehicle_Status+' Status Hence You Can Not Create Issue');
            			 return false;
            		 }
            	 }
            	 
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!');
            	 hideLayer();
             }
	});
	
	
}

function validateMultiIssue(){
	
	var nocategoryId = false; 
	
	$('input[name=categoryId]').each(function(){
		console.log("categoryId ",$(this).val())
		if($(this).val()<=0){
			showMessage("errors","Plaese Select category !!");
			nocategoryId = true;
		}
	})
	
	var noissuesSummary = false;
	$('input[name=issuesSummary]').each(function(){
		var val=$(this).val();
		if(val.trim() == ''){
			showMessage("errors","Please Enter summary !!")
			noissuesSummary= true;
		}
	})
	var noissueLabel_ID = false;
	
 $('select[name=issueLabel_ID]').each(function(){
	 if($(this).val() <=0){
		 showMessage("errors","Please select Issue Label !!!")
		 noissueLabel_ID= true;
	 }
	 
 })
 if(nocategoryId || noissuesSummary || noissueLabel_ID){
	 return false;
 }
	return true;
	
}

function backdateOdometerValidation(){
	
	var jsonObject = new Object();
	if(($('#validateOdometer').val() == true || $('#validateOdometer').val() == 'true') && $('#IssuesSelectVehicle').val() >0 && $('#reportdDate').val() != ""){
		
	jsonObject['vid'] 							=$('#IssuesSelectVehicle').val();
	jsonObject['reportdDate'] 					=$('#reportdDate').val();
	jsonObject['issueStartTime'] 				=$('#issueStartTime').val();
	jsonObject['fromEdit'] 						=$("#fromEdit").val();
	jsonObject['id'] 							=$("#id").val();
	
	$.ajax({
		url : "backdateOdometerValidation",
		type : "POST",
		dataType : "json",
		data :jsonObject ,
		success : function(data){
			if(data.minOdometer > 0 && data.maxOdometer >0){
				$("#backDateMinOdo").val(data.minOdometer);
				$("#backDateMaxOdo").val(data.maxOdometer);
				if($("#editIssue").val() == undefined ){
				$('#Issues_Odometer').val('');
				}
				}else if(data.minOdometer == 0 && data.maxOdometer == 0){
					$("#backDateMinOdo").val($('#vehicle_Odameter').val());
					$("#backDateMaxOdo").val(Number($('#vehicle_ExpectedOdameter').val()) + Number($('#vehicle_Odameter').val()));
					document.getElementById("Issues_Odometer").placeholder = ''+$('#vehicle_Odameter').val()+' ';
				}else{
					$("#backDateMinOdo").val(0);
					$("#backDateMaxOdo").val(Number($('#vehicle_ExpectedOdameter').val()) + Number($('#vehicle_Odameter').val()));
				}
			document.getElementById("Issues_Odometer").placeholder = ' Enter between '+$("#backDateMinOdo").val()+' to '+$("#backDateMaxOdo").val()+' ';
		}
	})
	}	
}
