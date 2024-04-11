$(document).ready(function() {
	$('#serviceTypeIds').select2();
	getServiceProgramDetailsById();
	 $("#from").change(function() {
	    	getJobSubTypeListById($(this).val(), false);
	    })
});

function getJobSubTypeListById(JobTypeId, fromEdit){
	if(!fromEdit){
		$('#to').select2("val", "");
	}
    $.getJSON("getJobSubTypeByTypeId.in", {
        JobType: JobTypeId,
        ajax: "true"
    }, function(e) {
    	var subType = '';
    	var t = '';
    	if(!fromEdit){
    		t = '<option value="0">Please Select</option>';
    	}else{
    		var data = $('#to').select2('data');
    		if(data) {
    			t = '<option value="'+data.id+'">'+data.text+'</option>';
    		}
    	}
    	for(var r = 0; r < e.length; r++){
    		if(e[r].Job_ROT_number != null && e[r].Job_ROT_number != 'null'){
    			subType = e[r].Job_ROT_number + " - " + e[r].Job_ROT;
    		}else{
    			subType = e[r].Job_ROT;
    		}
    		t += '<option value="' + e[r].Job_Subid + '">' + subType + "</option>";
    	}
    	$("#to").html(t);
    })
    return true;
}

function getServiceProgramDetailsById(){
	
	var jsonObject								= new Object();
	jsonObject["vehicleServiceProgramId"]		= $('#vehicleServiceProgramId').val();
	jsonObject["companyId"]						= $('#companyId').val();
	jsonObject["userId"]						= $('#userId').val();
	
	showLayer();
	$.ajax({
		url: "getServiceProgramDetailsById",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			console.log('data ', data);
			setServiceProgramSchedule(data);
			setServiceScheduleList(data);
			setAssignedServiceDetails(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setAssignedServiceDetails(data){
	if(data.asignmentList != undefined && data.asignmentList.length > 0){
		var srNO = 1;
		var tr = '';
		for(var i=0; i<data.asignmentList.length; i++){
			tr = '<tr>'
				+'<td>'+srNO+'</td>'
				+'<td>'+data.asignmentList[i].vehicleType+'</td>'
				+'<td>'+data.asignmentList[i].vehicleModal+'</td>';
				
				if($('#vehicleBranchWiseProgramConfig').val() == 'true' || $('#vehicleBranchWiseProgramConfig').val() == true ){
					if(data.asignmentList[i].branchName != null && data.asignmentList[i].branchName != undefined){
						tr +='<td>'+data.asignmentList[i].branchName+'</td>';
					}else{
						tr +='<td>--</td>';
					}
				}
				if($('#deleteServiceAssignment').val() == 'true' || $('#deleteServiceAssignment').val() == true ){
					tr += '<td><a style="color:red;" onclick="deleteServiceProgramAssignment('+data.asignmentList[i].vehicleTypeId+', '+data.asignmentList[i].vehicleModalId+', '+data.asignmentList[i].serviceProgramId+','+data.asignmentList[i].branchId+');" href="#"><i class="fa fa-trash"></i> Remove</a></td>'
				}
				tr += '</tr>';
				
				$('#aissgnedBody').append(tr);
				
				srNO ++;
		}
	}
}

function setServiceProgramSchedule(data) {
	$("#VendorPaymentTable").empty();
	
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th class="fit ar">');
	var th2		= $('<th class="fit ar">');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th class="fit ar">');

	tr1.append(th1.append("SR No."));
	tr1.append(th2.append("Name"));
	tr1.append(th3.append("Schedule Count"));
	tr1.append(th4.append("No Of Vehicle"));
	tr1.append(th5.append("Action"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.serviceProgram != undefined) {
	$('#programName').html(data.serviceProgram.programName);
	$('#descrption').html(data.serviceProgram.description);
	$('#createdBy').html(data.serviceProgram.createdBy);
	$('#createdOn').html(data.serviceProgram.createdOnStr);
	$('#createdBy1').html(data.serviceProgram.createdBy);
	$('#createdOn1').html(data.serviceProgram.createdOnStr);
	$('#lastupdatedBy').html(data.serviceProgram.lastModifedBy);
	$('#lastUpdated').html(data.serviceProgram.lastModifiedOnOnStr);
	}else{
		showMessage('info','No record found !')
	}
}


function setServiceScheduleList(data){
	var vendorProgram = data.serviceProgram.vendorProgram;
	if(!vendorProgram || Number($('#companyId').val()) == 4){
		$('#servicePopup').show();
	}
	
	if(data.serviceSchedules != null && data.serviceSchedules.length > 0){
		$('#dataTable').show();
		$('#delete').hide();
		var srNo = 1;
		for(var i = 0; i< data.serviceSchedules.length; i++){
			var serviceSchedules = data.serviceSchedules[i];
			var tr =' <tr data-object-id="">'
				+'<td class="fit" value="'+srNo+'">'+srNo+'</td>'
				+'<td>'+serviceSchedules.jobType+' </td>'
				+'<td>'+serviceSchedules.jobSubType+'</td>'
				+'<td>'+serviceSchedules.timeIntervalTypeStr+'</td>'
				+'<td>'+serviceSchedules.timeThresholdStr+'</td>'
				+'<td>'+serviceSchedules.serviceType+'</td>';
			     if(!vendorProgram || Number($('#companyId').val()) == 4){
			         if($('#deleteServiceShedule').val() == 'true' || $('#deleteServiceShedule').val() == true){
				    	 tr+= '<td><a href="#" class="btn btn-danger btn-sm" onclick="deleteServiceSchedule('+serviceSchedules.serviceScheduleId+');">Delete</a></td>';
			         }
			     }else{
			    	 tr+= '<td></td>';
			     }
			     tr+= '</tr>';
			$('#serviceScheduleBody').append(tr);
			srNo++;
		}
	}
}

function deleteServiceSchedule(serviceScheduleId){
	Swal.fire({
		  title: 'Are you sure to Delete?',
		  text: "You won't be able to revert this!",
		  icon: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  customClass:'swal-wide',
		  confirmButtonText: 'Yes, delete it!'
		}).then((result) => {
		  if (result.value) {
			  var jsonObject						= new Object();
			    jsonObject["companyId"]			= $('#companyId').val();
				jsonObject["userId"]			= $('#userId').val();
				jsonObject["serviceScheduleId"]		= serviceScheduleId;
				jsonObject["serviceProgramId"]		= $('#vehicleServiceProgramId').val();
				
				showLayer();
				$.ajax({
					url: "deleteServiceSchedule",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						showMessage('sucess', 'Data Deleted Successfully !');
						location.reload();
					},
					error: function (e) {
						hideLayer();
						showMessage('errors', 'Some Error Occurred!');
					}
				});
		  }
		})
}

function addServiceProgram(){
	$('#addServiceProgram').modal('show');
}
function saveServiceProgram(){

	if($('#programName').val() == null || $('#programName').val().trim() == ''){
		$('#programName').focus();
		showMessage('info', 'Please select Program name !');
		return false;
	}
	
	var jsonObject					= new Object();
	jsonObject["programName"]		= $('#programName').val();
	jsonObject["description"]		= $('#description').val();
	jsonObject["companyId"]			= $('#companyId').val();
	jsonObject["userId"]			= $('#userId').val();
	
	if(document.getElementById('vendorProgramYes') != null && document.getElementById('vendorProgramYes').checked){
		jsonObject["vendorProgram"]		= true;
	}else{
		jsonObject["vendorProgram"]		= false;
	}
	
	showLayer();
	$.ajax({
		url: "saveServiceProgram",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.noProgramName != undefined && data.noProgramName){
				showMessage('errors', 'Please select Program name !');	
				return false;
			}
			
			location.reload();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function openServiceSchedulePopup(){
	$('#serviceSchedulePopUp').modal('show');
}
$(document).ready(function() {
	$("#to").select2();
	
	 $("#from").select2({
	        minimumInputLength: 2,
	        minimumResultsForSearch: 10,
	        ajax: {
	            url: "getJobTypeService.in?Action=FuncionarioSelect2",
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
	                            text: e.Job_Type,
	                            slug: e.slug,
	                            id: e.Job_id
	                        }
	                    })
	                }
	            }
	        }
	    }), $("#from").change(function() {
	        $.getJSON("getJobSubTypeByTypeId.in", {
	            JobType: $(this).val(),
	            ajax: "true"
	        }, function(e) {
	            for (var t = '<option value="0">Please Select</option>', n = e.length, r = 0; n > r; r++) t += '<option value="' + e[r].Job_Subid + '">'  + e[r].Job_ROT + "</option>";
	            t += "</option>", $("#to").html(t)
	        })
	    }),
	
	//new starts
	$.getJSON("getVehicleType.in", function(e) {
        $("#vehicleType").empty(), $("#vehicleType").append($("<option value='0'>").text("Please Select Vehicle Type").attr("value", 0)), $.each(e, function(e, t) {
            $("#vehicleType").append($("<option>").text(t.vtype).attr("value", t.tid))
        });
        
        $("#vehicleType").select2({
        	allowClear: true,
    	});
	}),	
		
	$.getJSON("vehicleModelAutocomplete.in", function(e) {
        $("#vehicleModal").empty(), $("#vehicleModal").append($("<option value='0'>").text("Please Select Vehicle Model").attr("value", 0)), $.each(e, function(e, t) {
            $("#vehicleModal").append($("<option>").text(t.vehicleModelName).attr("value", t.vehicleModelId))
        });
        $("#vehicleModal").select2({
        	allowClear: true,
    	});
	}),	
	// new ends
	
	$("#branchId").select2({
        minimumInputLength: 0,
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
    }),
     $("#subscribe").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        multiple: !0,
        ajax: {
            url: "getUserEmailId_Subscrible",
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
    })
});

function saveServiceProramSchedule(){
	if(Number($('#from').val()) <= 0){
		$("#from").select2('focus');
		showMessage('info', 'Please select Job Type !');
		return false;
	}
	
	if(Number($('#to').val()) <= 0){
		$("#to").select2('focus');
		showMessage('info', 'Please select Job Sub Type !');
		return false;
	}
	
	if((Number($('#meter_interval').val()) <= 0) && (Number($('#time_interval').val()) <= 0)){
		showAlert('warning', 'You Have To Must Enter Between Meter Interval Or Time Interval Or Both  !');
		return false;
	}
	if((Number($('#meter_interval').val()) > 0) &&  Number($('#meter_threshold').val()) >= Number($('#meter_interval').val())){
		showAlert('warning', 'Due Meter Threshold Should Be Lesser Then Meter Interval !');
		return false;
	}
	
	if((Number($('#time_interval').val()) > 0) &&  calculateNoOfDays(Number($('#time_threshold').val()), Number($('#time_thresholdperiod').val())) >= calculateNoOfDays(Number($('#time_interval').val()), Number($('#time_intervalperiod').val()))){
		showAlert('warning', 'Time Threshold Should Be Lesser Then Time Interval !');
		return false;
	}
	
	var jsonObject					= new Object();
	jsonObject["companyId"]					= $('#companyId').val();
	jsonObject["userId"]					= $('#userId').val();
	jsonObject["vehicleServiceProgramId"]	= $('#vehicleServiceProgramId').val();
	jsonObject["jobTypeId"]					= $('#from').val();
	jsonObject["jobSubTypeId"]				= $('#to').val();
	jsonObject["meter_interval"]			= $('#meter_interval').val();
	jsonObject["time_interval"]				= $('#time_interval').val();
	jsonObject["time_intervalperiod"]		= $('#time_intervalperiod').val();
	jsonObject["branchId"]					= $('#branchId').val();
	jsonObject["subscribe"]                         = $('#subscribe').val();
	
	if(Number($('#meter_interval').val()) > 0){
		jsonObject["meter_threshold"]			= $('#meter_threshold').val();
	}else{
		jsonObject["meter_threshold"]			= 0;
	}
	
	if(Number($('#time_interval').val()) > 0){
		jsonObject["time_threshold"]			= $('#time_threshold').val();
		jsonObject["time_thresholdperiod"]		= $('#time_thresholdperiod').val();
	}else{
		jsonObject["time_threshold"]			= 0;
		jsonObject["time_thresholdperiod"]		= 0;
	}
	
	
	jsonObject["serviceTypeIds"]			= $('#serviceTypeIds').val();
	
 	showLayer();
	$.ajax({
		url: "saveServiceProramSchedule",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.alreadyExist != undefined && data.alreadyExist){
				showAlert('warning', 'This Service Schedule Already Exist In This Program');
				hideLayer();
				return false;
			}
			showMessage('success', 'Data Saved Successfully !');
			location.reload();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	}); 

}

function calculateNoOfDays(time, type){
	var noOfDays = 0;
	
	if(type == 2){
		noOfDays = Number(time) * 7;
	}else if(type == 3){
		noOfDays = Number(time) * 30;
	}else if(type == 4){
		noOfDays = Number(time) * 365;
	}else{
		noOfDays = Number(time);
	}
	
	return noOfDays;
}

function openServiceAsignPopup(){
	$('#serviceAsignPopup').modal('show');
}
function saveServiceProramAsign(){

	if(Number($('#vehicleType').val()) <= 0){
		$('#vehicleType').select2('focus');
		showMessage('info', 'Please select vehicle type !');
		return false;
	}
	if(Number($('#vehicleModal').val()) <= 0){
		$('#vehicleModal').select2('focus');
		showMessage('info', 'Please select vehicle modal !');
		return false;
	}
	
	if($("#vehicleBranchWiseProgramConfig").val() != undefined && ($("#vehicleBranchWiseProgramConfig").val()  == true || $("#vehicleBranchWiseProgramConfig").val()  == 'true')){
		if(Number($('#branchId').val()) <= 0){
			$('#branchId').select2('focus');
			showMessage('info', 'Please select Branch !');
			return false;
		}
	}

	var jsonObject					= new Object();
	jsonObject["companyId"]			= $('#companyId').val();
	jsonObject["userId"]			= $('#userId').val();
	jsonObject["vehicleType"]		= $('#vehicleType').val();
	jsonObject["vehicleModal"]		= $('#vehicleModal').val();
	jsonObject["serviceProgramId"]	= $('#vehicleServiceProgramId').val();
	jsonObject["branchId"]			= $('#branchId').val();
	
	var vehicleArray	 			= new Array();
	$('input[name*=selectVehicle]:checked').each(function(){
		vehicleArray.push($(this).val());
	});
	jsonObject["vehicleIds"]			= vehicleArray.toString();
	
	showLayer();
	$.ajax({
		url: "saveServiceProramAsign",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.alreadyAsigned != undefined && data.alreadyAsigned){
				showAlert('warning', 'Service Program already assigned to the selected ');
				hideLayer();
				return false;
			}
			showMessage('success', 'Data Saved Successfully !');
			location.reload();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}
function deleteServiceProgramAssignment(vehicleTypeId, vehicleModalId, serviceProgramId,branchId){

	Swal.fire({
		  title: 'Are you sure to Delete?',
		  text: "You won't be able to revert this!",
		  icon: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  customClass:'swal-wide',
		  confirmButtonText: 'Yes, delete it!'
		}).then((result) => {
		  if (result.value) {
			 var jsonObject					= new Object();
	jsonObject["companyId"]			= $('#companyId').val();
	jsonObject["userId"]			= $('#userId').val();
	jsonObject["vehicleTypeId"]		= vehicleTypeId;
	jsonObject["vehicleModalId"]	= vehicleModalId;
	jsonObject["serviceProgramId"]	= serviceProgramId;
	jsonObject["branchId"]	= branchId;
	jsonObject["vehicleBranchWiseProgramConfig"]	= $("#vehicleBranchWiseProgramConfig").val();
	
	showLayer();
	$.ajax({
		url: "deleteServiceProgramAssignment",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			if(data.deleted != undefined && data.deleted){
				showAlert('success', 'Service Program Assignment has been removed !');
				setTimeout(function(){ location.reload(); }, 2000);
				
			}else{
				showAlert('warning', 'Service Program assignment removal failed ! ');
				
			}
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
		  }
		})

}


function getVehicleListForCreateServiceProgram(){
	var flag = false;
	if(($("#vehicleBranchWiseProgramConfig").val() == true || $("#vehicleBranchWiseProgramConfig").val() == 'true') &&  Number($('#vehicleType').val()) > 0 &&  Number($('#vehicleModal').val()) > 0 && Number($('#branchId').val()) > 0){
		flag = true;
	}else if(($("#vehicleBranchWiseProgramConfig").val() == false || $("#vehicleBranchWiseProgramConfig").val() == 'false') &&  Number($('#vehicleType').val()) > 0 &&  Number($('#vehicleModal').val()) > 0 ){
		flag = true;
	}
	console.log("flag",flag)
	if(flag){
		$("#vehicleTable").addClass('hide');
		var jsonObject						= new Object();
		jsonObject["vehicleTypeId"]			= $('#vehicleType').val();
		jsonObject["vehicleModalId"]		= $('#vehicleModal').val();
		jsonObject["vehicleBranchId"]		= $('#branchId').val();
		jsonObject["vehicleBranchId"]		= $('#branchId').val();
		jsonObject["vehicleBranchWiseProgramConfig"]				= $('#vehicleBranchWiseProgramConfig').val();

		showLayer();
		$.ajax({
			url: "getVehicleListForCreateServiceProgram",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				setTypeModalAndBranchWiseVehicle(data);
				hideLayer();
			},
			error: function (e) {
				hideLayer();
				showMessage('errors', 'Some Error Occurred!');
			}
		});
	}
}

function setTypeModalAndBranchWiseVehicle(data){
	$("#vehicleTableBody").empty();
	console.log("data",data)
	if(data.vehicleList != undefined){
		var vehicleList = data.vehicleList;
		for(var index=0; index<vehicleList.length; index++){

			var columnArray = new Array();
			columnArray.push("<td class='fit'><input type='checkbox' checked name='selectVehicle' id='selectVehicle' value="+vehicleList[index].vid+"></td>");
			columnArray.push("<td class='fit'ar> <h4> "+ vehicleList[index].vehicle_registration  +"</td>");
			
			$('#vehicleTableBody').append("<tr id='penaltyID"+vehicleList[index].vid+"' >" + columnArray.join(' ') + "</tr>");
		
		}
		$("#vehicleTable").removeClass('hide');
	}
}

function Isservice_subscribeduser(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = t > 31 && 33 > t || t > 44 && 48 > t || t >= 48 && 57 >= t || t >= 64 && 90 >= t || t >= 97 && 122 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorservice_subscribeduser").innerHTML = "Special Characters not allowed", document.getElementById("errorservice_subscribeduser").style.display = n ? "none" : "inline", n
}

function validateServiceReminder(){
	if($('#subscribe').val() == null || $('#subscribe').val().trim() == '' ){
		$("#subscribe").select2('focus');
		showAlert('warning', 'Please Select Subscriber User !');
		return false;
	}
}	
