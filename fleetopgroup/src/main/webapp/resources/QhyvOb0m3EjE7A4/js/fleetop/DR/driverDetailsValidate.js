var jobTypeDriver	= false;

function ValidateEmail(e) {
    var r = !0,
        n = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    return n.test(e.value) || (document.getElementById("driver_email").style.border = "2px solid #F00", e.style.border = "2px solid #F00", document.getElementById("errorEmailValidate").innerHTML = "You have entered  invalid email address!", e.focus, r = !1), r
}

$(document).ready(function() {
	
	dlNumberValidation();
	
	$("#dlNumberShow").removeClass('hide');
	$(".select2").select2();
	$(function() {
		$('#DriverReminderdate').daterangepicker();
	}),$("#SelectVehicle").select2( {
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
                    }
                    )
                }
            }
        }
    }
    )
});

function dlNumberValidation(){
	if($("#driJobId").val() != undefined || $("#driJobId").val() != null){
		var jobIdType 	= $("#driJobId").val().trim().split("-");
		var jobType		= jobIdType[1].toUpperCase();
		
		if((jobType == "DRIVER") && ( $("#allowDLNumber").val() == true || $("#allowDLNumber").val() == 'true') ){
			jobTypeDriver = true;
			$("#dlAsterix").removeClass('hide');
			}else{
				jobTypeDriver = false;
				$("#dlAsterix").addClass('hide');
			}
		}
	
	if($("#isEditDriver").val() == undefined || $("#isEditDriver").val() == false || $("#isEditDriver").val() =='flase'){
		autoEmpNumber();
	} 
}	
$(document).ready(function() {// This is configuration part (Fields Visible For Perticular Groups)
	var renewalTypeConfigID = $('#renewalTypeConfigID').val();// all These Are Yml Configuration Values (True/False)
	var validityConfigID 	= $('#validityConfigID').val();
	var thresholdConfigID 	= $('#thresholdConfigID').val();
	var docTypeConfigID	 	= $('#docTypeConfigID').val();

	if(renewalTypeConfigID =='true' && validityConfigID =='true' && thresholdConfigID =='true' && docTypeConfigID == 'true' ){
		$('#renewalTypeId').removeClass('hide'); 
		$('#validityId').removeClass('hide'); 
		$('#thresholdId').removeClass('hide'); 
		$('#docTypeId').removeClass('hide'); 
	}
	 if($("#driverTrain").val() == "" || $("#driverTrain").val() == null){
		 $("#driverTraining").select2("val", "");
	}
	
	$("#vid").select2({
	    minimumInputLength: 3,
	    minimumResultsForSearch: 10,
	    ajax: {
	        url: "getVehicleSearchServiceEntrie.in?Action=FuncionarioSelect2",
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
	                        text: a.vehicle_registration, slug: a.slug, id: a.vid
	                        }
	                })
	            }
	        }
	    }
	})
	if($("#vehicleId").val() != undefined || $("#vehicleId").val() != null){// will show only on edit
		$('#vid').select2('data', {
			id : $("#vehicleId").val(),
			text : $("#vehicleNumber").val()
		});
	}
	
	
});


function validateAllField(driverOperationFlag){ // driverOperationFlag this is the common var for (Save/Update)
	/*AGE VALIDATION START*/
	var DOB = $("#driverDateOfBirth").val().split("-");

	var day 	= DOB[0];
	var month 	= DOB[1];
	var year 	= DOB[2];
	var age 	= 18;

	var today 		= new Date();
	var currDay 	= today.getDate();
	var currMonth 	= today.getMonth();
	var currYear 	= today.getFullYear();

	var setDate 	= new Date(currYear-age, currMonth , currDay);
	var inputDate 	= new  Date(year, month , day);

	if (inputDate >= setDate) {
		showMessage('info','Age Is Below 18 ');
		hideLayer();
		if(driverOperationFlag == 1){
			//	$("#driverDateOfBirth").val('');
		}else{
			$("#driverDateOfBirth").val($('#editDob').val());
		}
		return false;
		
	} 

/*AGE VALIDATION END*/
if( $('#driverFirstName').val() == "" || $('#driverFirstName').val() == null ){
	showMessage('info','Please Enter Driver First Name');
	$('#driverFirstName').focus();
	hideLayer();
	return false;
}
if($("#validateDateOfBirth").val() == true || $("#validateDateOfBirth").val() == 'true' &&  $('#driverDateOfBirth').val() =="" || $('#driverDateOfBirth').val() == null ){
	showMessage('info','Please Select Date Of Birth');
	$('#driverDateOfBirth').focus();
	hideLayer();
	return false;
}
if($("#validateMobileNo").val() == true || $("#validateMobileNo").val() == 'true' &&  $('#mobileNo').val() =="" || $('#mobileNo').val() == null ){
	showMessage('info','Please Enter Mobile Number');
	$('#mobileNo').focus();
	hideLayer();
	return false;
}
if($("#validateJobTitle").val() == true || $("#validateJobTitle").val() == 'true' &&  $('#driJobId').val() =="" || $('#driJobId').val() == null ){
	showMessage('info','Please Select Job Title');
	$('#driJobId').focus();
	hideLayer();
	return false;
}

var validate = jobTypeDriver && ($("#validateDLNumber").val() == true || $("#validateDLNumber").val() == 'true') &&  ($('#dlNumber').val() == null || $('#dlNumber').val().trim() == "" );
if(validate){
	showMessage('info','Please Enter DL Number');
	$('#dlNumber').focus();
	hideLayer();
	return false;
}
/*if((jobTypeDriver) &&($('#dlNumber').val() ==""  || $('#dlNumber').val() == null)){
	showMessage('info','Please Enter DL Number');
	$('#dlNumber').focus();
	hideLayer();
	return false;
}*/
if($("#validateEmployeeNumber").val() == true || $("#validateEmployeeNumber").val() == 'true' &&  $('#empNumber').val() =="" || $('#empNumber').val() == null ){
	showMessage('info','Please Enter Employee Number');
	$('#empNumber').focus();
	hideLayer();
	return false;
}

if(($("#validateAadhar").val() == true || $("#validateAadhar").val() == 'true') &&  ($('#aadharNumber').val() =="" || $('#aadharNumber').val() == null )){
	showMessage('info','Please Enter Aadhar Number');
	$('#aadharNumber').focus();
	hideLayer();
	return false;
}else if(($("#validateAadhar").val() == true || $("#validateAadhar").val() == 'true') &&  ($('#aadharNumber').val() != "" || $('#aadharNumber').val() != null )){
	if($('#aadharNumber').val().length < 12){
		showMessage('info','Please Enter Valid Aadhar Number');
		$('#aadharNumber').focus();
		hideLayer();
		return false;
	}
}

if(($("#validateVehicle").val() == true || $("#validateVehicle").val() == 'true' )&&  ($('#vid').val() =="" || $('#vid').val() == null )){
	showMessage('info','Please Select Vehicle');
	$('#vid').focus();
	hideLayer();
	return false;
}
if($("#validateStartDate").val() == true || $("#validateStartDate").val() == 'true' &&  $('#joinDate').val() =="" || $('#joinDate').val() == null ){
	showMessage('info','Please Enter Driver Start Date');
	$('#StartDate').focus();
	hideLayer();
	return false;
}

if($("#validateGroup").val() == true || $("#validateGroup").val() == 'true' &&  $('#vGroup').val() =="" || $('#vGroup').val() == null ){
	showMessage('info','Please Select Group Service');
	$('#v').focus();
	hideLayer();
	return false;
}
if($("#validateSalaryType").val() == true || $("#validateSalaryType").val() == 'true' &&  $('#driverSalaryTypeId').val() =="" || $('#driverSalaryTypeId').val() == null ){
	showMessage('info','Please Select Salary Type');
	$('#driverSalaryTypeId').focus();
	hideLayer();
	return false;
}
if($("#validatePerDaySalary").val() == true || $("#validatePerDaySalary").val() == 'true' &&  $('#driverPerDaySalary').val() =="" || $('#driverPerDaySalary').val() == null ){
	showMessage('info','Please Enter Driver Per Day Salary');
	$('#driverPerDaySalary').focus();
	hideLayer();
	return false;
}

if($("#validateInsuranceNumber").val() == true || $("#validateInsuranceNumber").val() == 'true' &&  $('#insuranceNo').val() =="" || $('#insuranceNo').val() == null ){
	showMessage('info','Please Enter Insurance Number');
	$('#mobileNo').focus();
	hideLayer();
	return false;
}
if($("#driverStatusId").val() == 6  && $("#validateVehicle").val() == false && ( $('#vid').val() != undefined  || $('#vid').val() != "")){
	showMessage('info','Please Remove The Vehicle First Then You Can Change The Status To Suspend');
	$('#driverStatusId').focus();
	hideLayer();
	return false;
}

if(driverOperationFlag == 1){

	if(($("#validateRenewalType").val() == true || $("#validateRenewalType").val() == 'true') &&  ($('#driverRenewalTypeId').val() =="" || $('#driverRenewalTypeId').val() == null )){
		showMessage('info','Please Select Renewal Type');
		$('#driverRenewalTypeId').focus();
		hideLayer();
		return false;
	}
	if(($("#showRenewalValidity").val() == true || $("#showRenewalValidity").val() == 'true' )&& ( $('#DriverReminderdate').val() =="" || $('#DriverReminderdate').val() == null )){
		showMessage('info','Please Enter Renewal Validity');
		$('#DriverReminderdate').focus();
		hideLayer();
		return false;
	}
	if(($("#validateDueThreshold").val() == true || $("#validateDueThreshold").val() == 'true' )&& ( $('#driverDueThreshold').val() =="" || $('#driverDueThreshold').val() == null && $('#driverDueThresholdPeriod').val() =="" || $('#driverDueThresholdPeriod').val() == null )){
		showMessage('info','Please Enter Renewal Threshold');
		$('#driverDueThreshold').focus();
		hideLayer();
		return false;
	}
	if(($("#validateRenewalDoc").val() == true || $("#validateRenewalDoc").val() == 'true' )&&  ($('#renewalDocId').val() =="" || $('#renewalDocId').val() == null )){
		showMessage('info','Please Select Renewal Document');
		$('#renewalDocId').focus();
		hideLayer();
		return false;
	}
}	


saveDriveDetails();// after validate this will Save/Update Driver 
}