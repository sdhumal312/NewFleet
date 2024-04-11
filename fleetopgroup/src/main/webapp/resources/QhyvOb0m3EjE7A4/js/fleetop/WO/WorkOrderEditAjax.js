var mainPartLocationType = 1;
var subPartLocationType  = 2;
$(document).ready(function() { 
	$('#issueAssignee').hide();
	fromWOEdit	= true;
		$("#ServiceReminder").select2({
        	 multiple: !0,
        	 allowClear: !0,
             data: ''
         });
     
	if($("#issueId").val() > 0 && $("#issueId").val() > 0){
		$("#WOVehicle").hide();
		$("#issueVehicle").show();
		$('#select3').val($("#Ovid").val());
		vehicleOnChange();
		vehicleOnChange3();
		
    }
});

$(document).ready(function() {
	
	showSubLocationDropDown();
    $("#select3").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getVehicleSearchWorkOrder.in?Action=FuncionarioSelect2",
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
                            text: a.vehicle_registration,
                            slug: a.slug,
                            id: a.vid
                        }
                    })
                }
            }
        }
    }),  
    
    $("#SelectDriverName").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getDriverSearchListWorkOrder.in?Action=FuncionarioSelect2",
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
                            text: a.driver_empnumber + " - " + a.driver_firstname+" "+a.driver_Lastname+" - "+a.driver_fathername + " - " + a.driver_mobnumber,
                            slug: a.slug,
                            id: a.driver_id
                        }
                    })
                }
            }
        }
    }), 
    
    $("#subscribe").select2({
        minimumInputLength: 0,
        minimumResultsForSearch: 10,
        multiple: 0,
        ajax: {
            url: "getUserEmailId_Subscrible",
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
    }),
    
    $("#tallyCompanyId").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getTallyCompanySearchList.in?Action=FuncionarioSelect2",
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
                            text: a.companyName ,
                            slug: a.slug,
                            id: a.tallyCompanyId
                        }
                    })
                }
            }
        }
    }), $("#subLocationId").select2({
        minimumInputLength: 0,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getPartLocationsByMainLocationId.in?Action=FuncionarioSelect2",
            dataType: "json",
            type: "POST",
            contentType: "application/json",
            quietMillis: 50,
            data: function(a) {
                return {
                    term: a,
                    locationType: subPartLocationType,
                    mainLocationId:  $('#location').val()
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
    });
    if($("#editSubLocationId").val() != undefined || $("#editSubLocationId").val() != null){// will show only on edit
		$('#subLocationId').select2('data', {
			id : $("#editSubLocationId").val(),
			text : $("#editSubLocation").val()
		});
	}
    
    $("#issue").select2({
    	multiple : !0,
		ajax : {
			url:"getVehicleWiseIssueDropdown.in?Action=FuncionarioSelect2", 
			dataType:"json", 
			type:"GET", 
			contentType:"application/json", 
			data:function() {
				return {
					term: $('#select3').val()
				}
			}
	, results:function(t) {
		return {
			results:$.map(t, function(t) {
				return {
					
					text: "I-"+t.ISSUES_NUMBER+"-"+t.ISSUES_SUMMARY, slug: t.slug, id: t.ISSUES_ID
				}
			}
			)
		}
	}
		}
	
	});
    if($("#editIssueId").val() != undefined || $("#editIssueId").val() != null){// will show only on edit
		$('#issue').select2('data', {
			id : $("#editIssueId").val(),
			text : $("#editIssueNumber").val()+"-"+$("#editIssueSummary").val()
		});
	}
	 if($('#showServiceProgramINWO').val() == 'false' || $('#showServiceProgramINWO').val() == false){
		 
		setTimeout(function(){ 
			 vehicleOnChange3();
			
			 $('#ServiceReminder').val([$('#serviceReminderIds').val()]).trigger("change");
			 if($('#serviceReminderIds').val() != '')
			 	$('#ServiceReminder').select2('readonly', true);
		}, 500);
	 }
	 
	 $("#workLocationId").select2({
	        minimumInputLength: 2,
	        minimumResultsForSearch: 10,
	        ajax: {
	            url: "getAllSubLocations.in?Action=FuncionarisoSelect2",
	            dataType: "json",
	            type: "POST",
	            contentType: "application/json",
	            quietMillis: 50,
	            data: function(a) {
	                return {
	                    term: a,
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
	    });
	 if($("#editWorkLocationId").val() != undefined || $("#editWorkLocationId").val() != null){// will show only on edit
			$('#workLocationId').select2('data', {
				id : $("#editWorkLocationId").val(),
				text : $("#editWorkLocationName").val()
			});
		}
	
});   

$("#select3").change(function vehicleOnChange() {
	 vehicleOnChange();
	 vehicleOnChange3();
});


$(document).ready(function() {
	var startTimeForGroup = $('#startTimeForGroup').val();

	if(startTimeForGroup == 'true' || startTimeForGroup == true){
			var today = new Date();
			var dd = today.getDate();
			var mm = today.getMonth()+1; //January is 0!
			var time = today.getHours();
			var minute = today.getMinutes();
			if(today.getMinutes()<10){
				minute = '0'+minute;
			} 
			if(today.getHours()<10){
				time = '0'+time;
			}
			$('#woStartTime').val(time+':'+minute);
			var yyyy = today.getFullYear();
			if(dd<10){
			    dd='0'+dd;
			} 
			if(mm<10){
			    mm='0'+mm;
			} 
			var today = dd+'-'+mm+'-'+yyyy;
			document.getElementById("woStartDate").value = today;
	}
});

$(document).ready(function() {
    var currentDate 			= $('#serverDate').val();
    var previousDate 			= $('#backDateString').val();
    if(previousDate != undefined){
    	var previousDateForBackDate =   previousDate.split("-")[0] + '-' +  previousDate.split("-")[1] + '-' +  previousDate.split("-")[2];
    	var currentDateStr =   currentDate.split("-")[0] + '-' +  currentDate.split("-")[1] + '-' +  currentDate.split("-")[2];
    	$("#StartDate1").datepicker({
    		defaultDate: currentDateStr,
    		autoclose: !0,
    		todayHighlight: !0,
    		format: "dd-mm-yyyy",
    		setDate: "0",
    		endDate: currentDateStr,
    		startDate:previousDateForBackDate,
    		maxDate: currentDateStr
    	}),
    	$("#LeaveDate1").datepicker({
    		defaultDate: currentDateStr,
    		autoclose: !0,
    		todayHighlight: !0,
    		format: "dd-mm-yyyy",
    		setDate: "0",
    		endDate: currentDateStr,
    		startDate:previousDateForBackDate,
    		maxDate: currentDateStr
    	});
    }
});

function validateWorkOrderDetails(){
	
	if(Number($('#select3').val()) <= 0){
		$("#select3").select2('focus');
		showMessage('errors', 'Please Select Vehicle !');
		return false;
	}
	
//	if(Number($('#subscribe').val()) <= 0){
//		$("#subscribe").select2('focus');
//		showMessage('errors', 'Please Select Assigned To !');
//		return false;
//	}
	if($('#assigneeIndetifier').val() == 'true'){
		if(Number($('#subscribe').val()) <= 0){
			$("#subscribe").select2('focus');
			showMessage('errors', 'Please Select Assigned To !');
			return false;
		}
	}else{
		if(Number($('#subscribe1').val()) <= 0){
			$("#subscribe1").select2('focus');
			showMessage('errors', 'Please Select Assigned To !');
			return false;
		}	
	}
	if($("#woStartDate").val() == "" || $("#woStartDate").val() == undefined ){
		$("#woStartDate").focus();
		showMessage('errors','Please Select Start Date');
		return false;
	}
	
	if($("#startTimeForGroup").val() == true){
		if($("#woStartTime").val() == "" || $("#woStartTime").val() == undefined ){
			$("#woStartTime").focus();
			showMessage('errors','Please Select Start Time !');
			return false;
		}
	}
	
	if($("#woEndDate").val() == "" || $("#woEndDate").val() == undefined ){
		$("#woEndDate").focus();
		showMessage('errors','Please Select Due Date');
		return false;
	}
	
	if($("#startTimeForGroup").val() == true){
		if($("#woEndTime").val() == "" || $("#woEndTime").val() == undefined ){
			$("#woEndTime").focus();
			showMessage('errors','Please Select Due Time !');
			return false;
		}
	}
	
	if($("#vehicle_Odometer").val() == "" || $("#vehicle_Odometer").val() == undefined ){
		$("#vehicle_Odometer").focus();
		showMessage('errors','Please Enter Odometer');
		return false;
	}
	
	if($("#tallyConfig").val() == true || $("#tallyConfig").val() == 'true' ){
		if($("#tallyCompanyId").val() == "" || $("#tallyCompanyId").val() == undefined ){
			showMessage('errors','Please Select Tally Company Master');
			return false;
		}
	}else{
		$("#tallyCompanyId").val(0);
	}
	
	if($("#validateSubLocation").val() == true || $("#validateSubLocation").val() == 'true'){
		if($("#subLocationId").val() == "" || $("#subLocationId").val() == undefined ){
			showMessage('info','Please Select SubLocation');
			return false;
		}
	}
	
	
	return true;
}


function validateOdometer(){ 
	
	document.getElementById("#woStartDate");
	var validateOdometerInWorkOrder = $('#validateOdometerInWorkOrder').val();
	var validateMinOdometerInWorkOrder = $('#validateMinOdometerInWorkOrder').val();
	var expectedOdo		 = Number($('#vehicle_ExpectedOdameter').val()) + Number($('#Odometer').val());
	var Odometer		 = Number($('#Odometer').val());
	var tripOpeningKM    = Number($('#vehicle_Odometer').val());
	var current  		= new Date().getFullYear() + '-' + ('0' + (new Date().getMonth()+1)).slice(-2)+ '-' + ('0' + (new Date().getDate())).slice(-2);
	var reportDate 		= $('#woStartDate').val().split("-").reverse().join("-");
	var date 			= moment(reportDate);
	var now 			= moment(current);
	
	if(validateMinOdometerInWorkOrder == 'true' || validateMinOdometerInWorkOrder == true){
		if($('#woStartDate').val() != undefined && $('#woStartDate').val() != '' && $('#woStartDate').val() != null){
			if (!date.isBefore(now)) {
				if(tripOpeningKM > 0 && tripOpeningKM < Odometer){
					$('#tripOpeningKM').focus();
					showMessage('errors', 'Trip Odometer Should Not Be Less Than '+Odometer);
					
					return false;
				}
			}
		}
	}
	
	if(validateOdometerInWorkOrder == 'false' || validateOdometerInWorkOrder == false){
		return true;
	}
	if($('#vehicle_ExpectedOdameter').val() == undefined || $('#vehicle_ExpectedOdameter').val() == '' || $('#vehicle_ExpectedOdameter').val() == null){
		return true;
	}
	
	if(tripOpeningKM > 0 && tripOpeningKM > expectedOdo){
		$('#vehicle_Odometer').focus();
		showMessage('errors', 'Trip Odometer cannot be greater than '+expectedOdo );
		return false;
	}
	
	return true;
}

function updateWorkOrder(){
	
	if(!validateWorkOrderDetails()){
		return false;
	}
	
	if(!validateOdometer()){
		return false;
	}
	
	if(!validateDate()){
		return false;
	}
	
	if(Number($('#accidentId').val()) > 0){
		if($('#Ovid').val() != $('#select3').val()){
			showMessage('info', 'You cannot change vehicle number as this entry related to vehicle accident !');
			return false;
		}
		if($('#Odid').val() !=  $('#SelectDriverName').val()){
			showMessage('info', 'You cannot change Driver as this entry related to vehicle accident !');
			return false;
		}
		
	}
	
	var jsonObject							= new Object();
	
	jsonObject["workOrderId"] 				=  $('#woId').val(); 	
	jsonObject["woNumber"] 					=  $('#woNo').val(); 	
	jsonObject["vid"] 						=  $('#select3').val(); 	
	jsonObject["driverId"] 	  				=  $('#SelectDriverName').val();
	jsonObject["driverNumber"] 	  			=  $('#driverNumber').val();
	
	if($('#assigneeIndetifier').val()=='true'){
		jsonObject["assignedTo"] 	  			=  $('#subscribe').val();
	}else{
		jsonObject["assignedTo"] 	  			=  $('#subscribe1').val();
	}
//	jsonObject["assignedTo"] 	  			=  $('#subscribe').val();
	jsonObject["woStartDate"] 	  			=  $('#woStartDate').val();
	jsonObject["woStartTime"] 	  			=  $('#woStartTime').val();
	jsonObject["woEndDate"] 	  			=  $('#woEndDate').val();
	jsonObject["woEndTime"] 	  			=  $('#woEndTime').val();
	jsonObject["outWorkStation"] 	  		=  $('#outWorkStation').val();
	jsonObject["gpsWorkLocation"] 	  		=  $('#gpsWorkLocation').val();
	jsonObject["location"] 	  				=  $('#location').val();
	jsonObject["workorders_route"] 			=  $('#workorders_route').val();
	jsonObject["vehicle_Odometer_Old"] 		=  $('#vehicle_Odometer_old').val();
	jsonObject["vehicle_Odometer"] 			=  $('#vehicle_Odometer').val();
	jsonObject["gpsOdometer"] 				=  $('#gpsOdometer').val();
	jsonObject["workorders_diesel"] 		=  $('#workorders_diesel').val();
	jsonObject["indentno"] 					=  $('#indentno').val();
	jsonObject["priority"] 					=  $('#priority').val();
	jsonObject["initial_note"] 				=  $('#initial_note').val();
	jsonObject["tallyCompanyId"] 			=  $('#tallyCompanyId').val();
	jsonObject["subLocationId"] 			=  $('#subLocationId').val();
	jsonObject["workOrdersStatusId"] 		=  $('#workOrdersStatusId').val();
	jsonObject["accidentId"] 				=  $('#accidentId').val();
	jsonObject["multiIssueInWO"] 			=  $("#multiIssueInWO").val();
	if($("#showPendingIssueWhileCreatingWO").val() == true || $("#showPendingIssueWhileCreatingWO").val() == 'true'){
		var issue = $('#issue').val();
		if(issue != null)
			issue=issue.toString();
		jsonObject["issue"]					=  issue;// from WO module 
	}else{
		jsonObject["issue"] 					=  $('#editIssueId').val();
	}
	jsonObject["workLocationId"]			=  $('#workLocationId').val();
	if($('#showServiceProgramINWO').val() == 'true' || $('#showServiceProgramINWO').val() == true){
		var serviceIds = '';
		$("input[name=selectedSchedule]").each(function(){
			if($('#'+this.id+'').prop('checked')){
				serviceIds += this.id+',';
			}
		});
		jsonObject["serviceReminderId"]	=  serviceIds;
	}
	
	showLayer();
	$.ajax({
	url: "updateWorkOrderDetails",
	type: "POST",
	dataType: 'json',
	data: jsonObject,
	success: function (data) {
		
		if(data.inTripRoute != undefined && data.inTripRoute == true){
			hideLayer();
			showMessage('errors', 'vehicle in Triproute Status Hence Cannot Update WorkOrder !')
		}
		
		if(data.changeAccidentDetails != undefined && data.changeAccidentDetails){
			showMessage('info', 'You cannot change vehicle number/ Driver as this entry related to vehicle accident !');
			hideLayer();
		}
		
		if(data.inSold != undefined && data.inSold == true){
			hideLayer();
			showMessage('errors', 'vehicle in Sold Status Hence Cannot Update WorkOrder !')
		}
		
		if(data.inActive != undefined && data.inActive == true){
			hideLayer();
			showMessage('errors', 'Vehicle in InActive Status Hence Cannot Update WorkOrder !')
		}
		
		if(data.inSurrender != undefined && data.inSurrender == true){
			hideLayer();
			showMessage('errors', 'vehicle in Surrender Status Hence Cannot Update WorkOrder !')
		}
		if(data.inAccident != undefined && data.inAccident == true){
			hideLayer();
			showMessage('errors', 'Vehicle in Accident Status Hence Cannot Update WorkOrder !')
			return false;
		}
		
		if(data.inWorkShop != undefined && data.inWorkShop == true){
			hideLayer();
			showMessage('errors', 'selected Vehicle in Workshop Status Hence Cannot Update WorkOrder !')
		}
		
		if(data.workOrderUpdated != undefined && data.workOrderUpdated == true){
			hideLayer();
			window.location.replace("viewWorkOrder");
			showMessage('success', 'WorkOrder Updated !')
		}
		
		if(data.emptyWO != undefined && data.emptyWO == true){
			hideLayer();
			showMessage('errors', 'WorkOrder cannot be Updated !')
		} 
		
	},
	error: function (e) {
		alert("failure")
		showMessage('errors', 'Some error occured!')
		hideLayer();
	}
	});
	
}

function showSubLocationDropDown(){
	var showSubLocationForMainLocation = false;
    if($("#showSubLocation").val() == true || $("#showSubLocation").val() == "true"){
    	var mainLocationIds = $("#mainLocationIds").val().split(',');
    	for(var i = 0; i < mainLocationIds.length; i++) {
    		if($("#location").val() == mainLocationIds[i]){
    			showSubLocationForMainLocation = true
    		}
    	}
    }
    
    if(showSubLocationForMainLocation == true){
    	$("#subLocation").show();
    	$("#validateSubLocation").val(true);
    }else{
    	$("#subLocationId").select2("val", "");
    	$("#subLocation").hide();
    	$("#validateSubLocation").val(false);
    }
}

function validateDate(){

	if($("#startTimeForGroup").val() == true || $("#startTimeForGroup").val() == 'true'  )	{
		
		if($('#woEndTime').val() == ""){
			$('#woEndTime').val($('#woStartTime').val())
		}
		var startDateTime    	= $('#woStartDate').val().split("-").reverse().join("-")+' '+$('#woStartTime').val();
		var endDateTime    		= $('#woEndDate').val().split("-").reverse().join("-")+' '+$('#woEndTime').val();
	}else{
		var startDateTime    	= $('#woStartDate').val().split("-").reverse().join("-")+' 00:00:00';
		var endDateTime    		= $('#woEndDate').val().split("-").reverse().join("-")+' 00:00:00';
	}
	
	var isafter = moment(startDateTime).isAfter(endDateTime);
	
	if(isafter){
		showMessage('info', 'Due Date Can Not Be Greater Than Start Date');
		$('#woEndTime').val('');
		$('#woEndDate').val('');
		hideLayer();
		return false;
	}
	return true;
}


function setmultipleIssues(data){
	if($("#multiIssueInWO").val() == true || $("#multiIssueInWO").val() == 'true'){
		if(data != null && data != 'null' && data != ""){
			var array = new Array();
			var jsondata = JSON.parse(data);
			for(var i = 0 ;i < jsondata.length;i++){
				var issueList = new Object();
				issueList.id = jsondata[i].issues_ID;
				issueList.text = jsondata[i].issuesNumberStr;
				array.push(issueList);
			}
			$('#issue').data().select2.updateSelection(array);
			
			getAssignToFromIssue();
			
	

		}
	}
}

function getAssignToFromIssue(){
	if($("#multiIssueInWO").val() == true || $("#multiIssueInWO").val() == 'true'){
		if($('#issue').val() != '' && $('#issue').val() != null ){
			$('#driverDiv').hide();
			$('#anyAssignee').hide();
			$('#issueAssignee').show()
			$('#assigneeIndetifier').val("false")
			$('#SelectDriverName').select2('val',"");
			
			$("#WOVehicle").hide();
			$("#issueVehicle").show();
			$('#select3').val($("#Ovid").val());
			vehicleOnChange();
			vehicleOnChange3();
		}else{
			$('#driverDiv').show();
			$('#anyAssignee').show();
			$('#issueAssignee').hide()
			$('#assigneeIndetifier').val("true")
		}
		
	
	var jsonObject				= new Object();
	jsonObject["vid"] 	 		=  $('#select3').val();
	var issue = $('#issue').val();
	if(issue != null)
		issue=issue.toString();

	jsonObject["issueId"]		=  issue;// from WO module 
	jsonObject["companyId"] 	=  $('#companyId').val();
	jsonObject["isEdit"] 	=  1;
	$.ajax({
		url: "getVehicleWiseIssueDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setIssueData(data);
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			hideLayer();
		}
	});
	}
}

function setIssueData(data){

	if($("#showPendingIssueWhileCreatingWO").val() == true || $("#showPendingIssueWhileCreatingWO").val() == 'true'){
		if(data.subsciberList != undefined){
			var option  = '';
			for(var i = 0; i < data.subsciberList.length; i++){
				option	+= '<option value="'+data.subsciberList[i].user_id+'">'+data.subsciberList[i].firstName+' - '+data.subsciberList[i].lastName+'</option>';
			}
			$('select[name=issueAssign]').html(option);
			$('#subscribe1').select2();

			$('#subscribe1').select2('data',{
				id : $('#OassigneeId').val(),
				text : $('#Oassignee').val()
			})
		}}
}

