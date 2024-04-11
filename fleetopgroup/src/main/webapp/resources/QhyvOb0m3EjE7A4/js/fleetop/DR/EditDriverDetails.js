 var driverStatusId = 0;
 var editDriver = 2;


 $(document).ready(function () {
	 if($("#jobTypeWiseAutoGenerateEmpNo").val() == true || $("#jobTypeWiseAutoGenerateEmpNo").val() == 'true' || $("#autoGenerateEmpNo").val() == true || $("#autoGenerateEmpNo").val() == 'true'){
	
			$("#empNumber").attr('readonly',true);
			$("#driJobId").prop("disabled", true);
			
		}
	
    $("#btnSubmit").on('keypress click', function (e) {
        e.preventDefault(); 	//stop submit the form, we will post it manually.
        validateAllField(editDriver);
       
    });

});
 function saveDriveDetails(remarkFlag){
	showLayer();
	
	var array = new Array();
	array.push("Hindi");
	var jobType										= "";
	if(remarkFlag != true){
		if($("#statusRemarkConfiguration").val() != undefined && ($("#statusRemarkConfiguration").val() == true || $("#statusRemarkConfiguration").val() == 'true')){
			if($("#statusId").val() != $("#driverStatusId").val()){
				if($("#driverStatusId").val() == 1){
					$("#changeStatusTo").html('ACTIVE');
				}else if($("#driverStatusId").val() == 2){
					$("#changeStatusTo").html('INACTIVE');
				}else if($("#driverStatusId").val() == 3){
					$("#changeStatusTo").html('TRIPROUTE');
				}else if($("#driverStatusId").val() == 6){
					$("#changeStatusTo").html('SUSPEND');
				}else if ($('#driverStatusId').val() == 7){
					$('#changeStatusTo').html('HOLD');
				}else if ($('#driverStatusId').val()== 8){
					$('#changeStatusTo').html('RESIGN');
					
				}
				
				$("#remarkModal").modal('show');
				hideLayer();
				return false;
			}
		}
	}
	
	
	
	
	
	var jsonObject									= new Object();

	jsonObject["driverId"]						= $('#driverId').val();
	jsonObject["driverFirstName"]				= $('#driverFirstName').val();
	jsonObject["driverLastName"]				= $('#driverLastName').val();
	jsonObject["driverFatherName"]				= $('#driverFatherName').val();
	jsonObject["driverDateOfBirth"]				= $('#driverDateOfBirth').val();
	jsonObject["driverQualification"]			= $('#driverQualification').val();
	jsonObject["driverBloodGroup"]				= $('#driverBloodGroup').val();
	
	if($('#driverLanguage').val() != null){
		jsonObject["driverLanguage"]				=(JSON.stringify($('#driverLanguage').val())).replace(/[\[\]']+/g,'').replace(/\"/g, "");
	}else {
		jsonObject["driverLanguage"]				= (JSON.stringify(array)).replace(/[\[\]']+/g,'').replace(/\"/g, "");
	}
	
	jsonObject["vid"]							= $('#vid').val();
	jsonObject["vGroup"]						= $('#vGroup').val();
	jsonObject["driverStatusId"]				= $('#driverStatusId').val();
	jsonObject["driverSalaryTypeId"]			= $('#driverSalaryTypeId').val();
	jsonObject["driverPerDaySalary"]			= $('#driverPerDaySalary').val();
	jsonObject["driverESIAmount"]				= $('#driverESIAmount').val();
	jsonObject["driverPFAmount"]				= $('#driverPFAmount').val();
	jsonObject["driverEmail"]					= $('#driverEmail').val();
	jsonObject["mobileNo"]						= $('#mobileNo').val();
	jsonObject["driverHomePhoneNo"]				= $('#driverHomePhoneNo').val();
	jsonObject["driverAddress"]					= $('#driverAddress').val();
	jsonObject["driverAddress2"]				= $('#driverAddress2').val();
	jsonObject["countryId"]						= $('#countryId').val();
	jsonObject["stateId"]						= $('#stateId').val();
	jsonObject["cities"]						= $('#cityId').val();
	jsonObject["pinCode"]						= $('#pinCode').val();
	jsonObject["empNumber"]						= $('#empNumber').val();
	jsonObject["insuranceNo"]					= $('#insuranceNo').val();
	jsonObject["ESINumber"]						= $('#ESINumber').val();
	jsonObject["PFNumber"]						= $('#PFNumber').val();
	if($("#driJobId").val() != undefined || $("#driJobId").val() != null){
		jsonObject["driJobId"]						= $("#driJobId").val().trim().split("-")[0];
		
		jobType										= $("#driJobId").val().trim().split("-")[1];
	}
	if($('#driverTraining').val() != null || $('#driverTraining').val() != undefined){
		jsonObject["driverTraining"]				= (JSON.stringify($('#driverTraining').val())).replace(/[\[\]']+/g,'').replace(/\"/g, "");
	}
	jsonObject["joinDate"]						= $('#joinDate').val();
	jsonObject["LeaveDate"]						= $('#LeaveDate').val();
	jsonObject["dlNumber"]						= $('#dlNumber').val();
	jsonObject["badgeNumber"]					= $('#badgeNumber').val();
	jsonObject["DL_Class"]						= $('#DL_Class').val();
	jsonObject["DL_State"]						= $('#DL_State').val();
	jsonObject["driverAuthorized"]				= $('#driverAuthorized').val();
	jsonObject["DL_Original"]					= $('#DL_Original').val();
	jsonObject["bankName"]						= $('#bankName').val();
	jsonObject["bankACNumber"]					= $('#bankACNumber').val();
	jsonObject["bankIFSC"]						= $('#bankIFCSNumber').val();
	jsonObject["aadharNumber"]					= $('#aadharNumber').val();
	jsonObject["panNumber"]						= $('#panNumber').val();
	jsonObject["referenceFirstName"]			= $('#referenceFirstName').val();
	jsonObject["referenceLastName"]				= $('#referenceLastName').val();
	jsonObject["referenceContactNo"]			= $('#referenceContactNo').val();
	jsonObject["driverPhotoId"]					= $('#driverPhotoId').val();
	if($("#statusRemarkConfiguration").val() == undefined && ($("#statusRemarkConfiguration").val() == false || $("#statusRemarkConfiguration").val() == 'false')){
		if(  $('#remark').val().trim() == "" && $('#driverStatusId').val() == 6){
			$("#addRemarkModal").modal('show');
			hideLayer();
			return false;
		}
	}
	jsonObject["remark"]						= $('#remark').val().trim();
	jsonObject["tripsheetId"]					= 0;
	if(jobType=='DRIVER'){
		jsonObject["jobTypeDriver"]					= true;
	}else{
		jsonObject["jobTypeDriver"]					= false;
	}
	
	if(document.getElementById('salariedNo') != null && document.getElementById('salariedNo').checked) {
		jsonObject["salariedId"]					= $('#salariedNo').val();
	}else{
		jsonObject["salariedId"]					= $('#salariedYes').val();
	}
	 
	jsonObject["changeRemark"]						= $('#changeRemark').val();
	jsonObject["currentStatusId"]					= $('#statusId').val();
	jsonObject["changeToStatusId"]					= $('#driverStatusId').val();
	jsonObject["userId"]							= $('#userId').val();
	jsonObject["companyId"]							= $('#companyId').val();
	$.ajax({
		url: "updateDriverDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.dlNumberExist != undefined && data.dlNumberExist == true){
				showMessage('info', 'DL Number Already Exist!');
				hideLayer();
				return false;
			}else if(data.empNumberExist != undefined && data.empNumberExist == true){
				showMessage('info', 'Employee Number Already Exist!');
				hideLayer();
				return false;
			}else if(data.alreadyExist != undefined && data.alreadyExist == true){
				showMessage('info', 'Already Exsit!');
				hideLayer();
				return false;
			}else if(data.driverFirstName != undefined && data.driverFirstName == true){
				showMessage('info', 'Please Enter Driver First Name!');
				hideLayer();
				return false;
			}else if(data.driverDateOfBirth != undefined && data.driverDateOfBirth == true){
				showMessage('info', 'Please Enter Date Of Birth!');
				hideLayer();
				return false;
			}else if(data.vGroup != undefined && data.vGroup == true){
				showMessage('info', 'Please Select Group!');
				hideLayer();
				return false;
			}else if(data.aadhar != undefined && data.aadhar == true){
				showMessage('info', 'Please Enter Aadhar Number!');
				hideLayer();
				return false;
			}else if(data.vehicle != undefined && data.vehicle == true){
				showMessage('info', 'Please Select Vehicle!');
				hideLayer();
				return false;
			}else if(data.driverSalaryTypeId != undefined && data.driverSalaryTypeId == true){
				showMessage('info', 'Please Select Salary Type!');
				hideLayer();
				return false;
			}else if(data.driverPerDaySalary != undefined && data.driverPerDaySalary == true){
				showMessage('info', 'Please Enter Driver Per Day Salary!');
				hideLayer();
				return false;
			}else if(data.mobileNo != undefined && data.mobileNo == true){
				showMessage('info', 'Please Enter Mobile Number!');
				hideLayer();
				return false;
			}else if(data.empNumber != undefined && data.empNumber == true){
				showMessage('info', 'Please Enter Employee Number!');
				hideLayer();
				return false;
			}else if(data.insuranceNo != undefined && data.insuranceNo == true){
				showMessage('info', 'Please Enter Insurance Number!');
				hideLayer();
				return false;
			}else if(data.driJobId != undefined && data.driJobId == true){
				showMessage('info', 'Please Select Driver Job Type!');
				hideLayer();
				return false;
			}else if(data.joinDate != undefined && data.joinDate == true){
				showMessage('info', 'Please Select Date Of Joining!');
				hideLayer();
				return false;
			}else if(data.dlNumber != undefined && data.dlNumber == true){
				showMessage('info', 'Please Enter DL Number');
				hideLayer();
				return false;
			}else{
				window.location.replace("showDriver.in?driver_id="+data.driverId+"");
				showMessage('success', 'New Driver Updated Successfully!');
			}
			//hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

 function saveRemark(){
	 $("#addRemarkModal").modal('hide');
 }
 
 