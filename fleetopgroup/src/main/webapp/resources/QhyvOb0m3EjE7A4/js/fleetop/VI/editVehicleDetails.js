var fuelUnitId 		= 1;
var meterUnitId 	= 1;
var acTypeId 		= 1;

/*$(document).ready(
	function($) {
		$('button[type=submit]').click(function(e) {
			editVehicleDetails();
		});
	});*/

$(document).ready(function() {
	$('#serviceProgramId').select2();
	$("#VehicleTypeSelect").select2( {
        ajax: {
            url:"getVehicleType.in?Action=FuncionarioSelect2", dataType:"json", type:"GET", contentType:"application/json", data:function(t) {
                return {
                    term: t
                }
            }
            , results:function(t) {
                return {
                    results:$.map(t, function(t) {
                        return {
                            text: t.vtype, slug: t.slug, id: t.tid
                        }
                    }
                    )
                }
            }
        }
    }
    ), $("#VehicleGroupSelect").select2( {
        ajax: {
            url:"getVehicleGroup.in?Action=FuncionarioSelect2", dataType:"json", type:"GET", contentType:"application/json", data:function(t) {
                return {
                    term: t
                }
            }
            , results:function(t) {
                return {
                    results:$.map(t, function(t) {
                        return {
                            text: t.vGroup, slug: t.slug, id: t.gid
                        }
                    }
                    )
                }
            }
        }
    }
    ), $("#VehicleRouteSelect").select2( {
        ajax: {
            url:"getVehicleTripRoute.in?Action=FuncionarioSelect2", dataType:"json", type:"GET", contentType:"application/json", data:function(t) {
                return {
                    term: t
                }
            }
            , results:function(t) {
                return {
                    results:$.map(t, function(t) {
                        return {
                            text: t.routeName, slug: t.slug, id: t.routeID
                        }
                    }
                    )
                }
            }
        }
    }
    ),$.getJSON("vehicleStatusAutocomplete.in", function(e) {
		statusList	= e;
		$("#vehicleStatus").empty();
		$("#vehicleStatus").append($("<option>").text($("#editVehicleStatusName").val()).attr("value",$("#editVehicleStatusId").val().split(",")));
		$('#vehicleStatus').select2();
		for(var k = 0; k <statusList.length; k++){
			$("#vehicleStatus").append($("<option>").text(statusList[k].vStatus).attr("value", statusList[k].sid));
		}

	}),$("#vehicle_maker").select2({
		ajax : {
            url:"vehicleManufacturerAutocomplete.in?Action=FuncionarioSelect2", dataType:"json", type:"GET", contentType:"application/json", data:function(t) {
                return {
                    term: t
                }
            }
            , results:function(t) {
                return {
                    results:$.map(t, function(t) {
                        return {
                            text: t.vehicleManufacturerName, slug: t.slug, id: t.vehicleManufacturerId
                        }
                    }
                    )
                }
            }
        }
	
	}),$("#vehicle_Model").select2({
		ajax : {
            url:"vehicleModelAutocomplete.in?Action=FuncionarioSelect2", dataType:"json", type:"GET", contentType:"application/json", data:function(t) {
                return {
                    term: t,
                    manufacturer : $('#vehicle_maker').val()
                }
            }
            , results:function(t) {
                return {
                    results:$.map(t, function(t) {
                        return {
                            text: t.vehicleModelName, slug: t.slug, id: t.vehicleModelId
                        }
                    }
                    )
                }
            }
        }
	
	}), $("#VehicleLocation").select2({
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
    if($("#editBranchId").val() != undefined || $("#editBranchId").val() != null){// will show only on edit
		$('#VehicleLocation').select2('data', {
			id : $("#editBranchId").val(),
			text : $("#editVehicleLocation").val()
		});
	};
	
	  $("#bodyMakerSelect").select2({
        ajax: {
            url:"getVehicleBodyMaker.in?Action=FuncionarioSelect2", dataType:"json", type:"GET", contentType:"application/json", data:function(t) {
                return {
                    search: t
                }
            }
            , results:function(t) {
                return {
                    results:$.map(t, function(t) {
                        return {
                            text: t.vehicleBodyMakerName, id: t.vehicleBodyMakerId
                        }
                    }
                    )
                }
            }
        }
    }
    )
	
	$("#subCompany").select2({
	        minimumInputLength: 2,
	        minimumResultsForSearch: 10,
	        ajax: {
	            url: "/getSubCompany",
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
	                            text: e.subCompanyName,
	                            slug: e.slug,
	                            id: e.subCompanyId
	                        }
	                    })
	                }
	            }
	        }
	    })
	
});


$(document).ready(
		function($) {
			$('#MaximumOdometer').keyup(function(e) {
				var maximumOdometer = Number($('#MaximumOdometer').val());
				if(maximumOdometer > Number($('#allowMaximumOdometer').val())){
					showMessage('info', 'Maximum Allow Odometer Can Not be Greater Than '+$('#allowMaximumOdometer').val());
					return false;
				}
				
			}),$('#vehicle_ExpectedMileage').keyup(function(e) {
				var vehicle_ExpectedMileage = Number($('#vehicle_ExpectedMileage').val());
				if(vehicle_ExpectedMileage > 100){
					$('#vehicle_ExpectedMileage').val(0);
					showMessage('info', 'Expected Mileage_From Can not be greater than 100');
					return false;
				}
			}),$('#vehicle_ExpectedMileage').blur(function(e) {
				var vehicle_ExpectedMileage = Number($('#vehicle_ExpectedMileage').val());
				var vehicle_ExpectedMileageTo = Number($('#vehicle_ExpectedMileageTo').val());
				
				if(vehicle_ExpectedMileageTo < vehicle_ExpectedMileage ){
					$('#vehicle_ExpectedMileageTo').val(0);
					showMessage('info', 'Expected Mileage_From Can not be greater Expected Mileage_To');
					return false;
				}
			}),$('#vehicle_ExpectedMileageTo').keyup(function(e) {
				var vehicle_ExpectedMileageTo = Number($('#vehicle_ExpectedMileageTo').val());
				if(vehicle_ExpectedMileageTo > 100 ){
					$('#vehicle_ExpectedMileageTo').val(0);
					showMessage('info', 'Expected Mileage_To Can not be greater than 100');
					return false;
				}
			}),$('#vehicle_ExpectedMileageTo').blur(function(e) {
				var vehicle_ExpectedMileage = Number($('#vehicle_ExpectedMileage').val());
				var vehicle_ExpectedMileageTo = Number($('#vehicle_ExpectedMileageTo').val());
				
				if(vehicle_ExpectedMileageTo < vehicle_ExpectedMileage ){
					$('#vehicle_ExpectedMileage').val(0);
					showMessage('info', 'Expected Mileage_From Can not be greater Expected Mileage_To');
					return false;
				}
			}) ,$('#vehicle_Oil').keyup(function(e) {
				var vehicle_Oil = Number($('#vehicle_Oil').val());
				if(vehicle_Oil > 100){
					$('#vehicle_Oil').val(0);
					showMessage('info', 'Engine Oil Capacity  Can not be greater than 100');
					return false;
				}
			}),$('#vehicle_FuelTank1').keyup(function(e) {
				var vehicleFuelTank = Number($('#vehicle_FuelTank1').val());
				if(vehicleFuelTank > 1000){
					$('#vehicle_FuelTank1').val(0);
					showMessage('info', ''+$("#fuelTankText").val()+' Can not be greater than 1000');
					return false;
				}
			});
		}); 

function showSelectedfuel(){
	$("input[name='vehicleFuelUnitId']:checked").each(function(){
		fuelUnitId = $(this).val();
	});
}
function showMeterSelected(){
	$("input[name='vehicleMeterUnitId']:checked").each(function(){
		meterUnitId = $(this).val();
	});
}
function showAcType(){
	$("input[name='acTypeId']:checked").each(function(){
		acTypeId = $(this).val();
	});
}

function editVehicleDetails(remarkFlag){
	showLayer();
	console.log("inside editVehicleDetails ") ;
	if( $('#vehicle_registrationNumber').val() == "" || $('#vehicle_registrationNumber').val() == null ){
		showMessage('info','Please Enter Vehicle Registration No');
		$('#vehicle_registrationNumber').focus();
		hideLayer();
		return false;
	}
	if($("#validateChasisNo").val() == true || $("#validateChasisNo").val() == 'true' &&  $('#vehicle_chasisNumber').val() =="" || $('#vehicle_chasisNumber').val() == null ){
		showMessage('info','Please Select Chasis No');
		$('#vehicleChasisNo').focus();
		hideLayer();
		return false;
	}
	if($("#validateEngineNo").val() == true || $("#validateEngineNo").val() == 'true' &&  $('#vehicle_engineNumber').val() =="" || $('#vehicle_engineNumber').val() == null ){
		showMessage('info','Please Select Engine NO');
		$('#validateEnginNo').focus();
		hideLayer();
		return false;
	}
	if(($("#validateVType").val() == true || $("#validateVType").val() == 'true') &&  ($('#VehicleTypeSelect').val()== undefined || $('#VehicleTypeSelect').val() =="" || $('#VehicleTypeSelect').val() =="0" || $('#VehicleTypeSelect').val() == null) ){
		showMessage('info','Please Select Vehicle Type');
		$('#VehicleTypeSelect').focus();
		hideLayer();
		return false;
	}
	if($("#validateVStatus").val() == true || $("#validateVStatus").val() == 'true' &&  $('#vehicleStatus').val() =="" || $('#vehicleStatus').val() == null ){
		showMessage('info','Please Select Vehicle Status');
		$('#VehicleStatusSelect').focus();
		hideLayer();
		return false;
	}
	if( $('#VehicleGroupSelect').val() =="" || $('#VehicleGroupSelect').val() == null ){
		showMessage('info','Please Select Vehicle Group');
		$('#VehicleGroupSelect').focus();
		hideLayer();
		return false;
	}
	
	if($("#validateVehicleModal").val() == true || $("#validateVehicleModal").val() == 'true'){
		if(Number($('#vehicleModel').val()) <= 0){
			showMessage('info','Please Select Vehicle Modal');
			$('#vehicleModel').select2('focus');
			hideLayer();
			return false;
		}
	}
	
	if($("#validateVFuelType").val() == true || $("#validateVFuelType").val() == 'true' &&  $('#fuelId').val() =="" || $('#fuelId').val() == null ){
		showMessage('info','Please Select Vehicle Fuel Type');
	//	$('#VehicleStatusSelect').focus();
		hideLayer();
		return false;
	}if(($("#validateVOdometer").val() == true || $("#validateVOdometer").val() == 'true' )&& ( $('#vehicleOdometer').val() =="" || $('#vehicleOdometer').val() == null) ){
		showMessage('info','Please Enter Vehicle Odometer');
		hideLayer();
		return false;
	}
	if(($("#validateVExpectedOdometer").val() == true || $("#validateVExpectedOdometer").val() == 'true' )&& ( $('#MaximumOdometer').val() =="" || $('#MaximumOdometer').val() == null) ){
		showMessage('info','Please Enter Maximum Allow Odometer');
		hideLayer();
		return false;
	}
	if(($("#validateVExpectedMileage").val() == true || $("#validateVExpectedMileage").val() == 'true' )&& (( $('#vehicle_ExpectedMileage').val() =="" || $('#vehicle_ExpectedMileage').val() == null ) || ( $('#vehicle_ExpectedMileageTo').val() =="" || $('#vehicle_ExpectedMileageTo').val() == null )) ){
		showMessage('info','Please Enter Expected Mileage');
		hideLayer();
		return false;
	}
	
	if(Number($('#VehicleLocation').val()) != Number($('#oldBranchId').val())){
		alert('You have changed vehicle location , Service Program will be change accordingly ! ');
	}
	
		if(remarkFlag != true){
		if($("#statusRemarkConfiguration").val() != undefined && ($("#statusRemarkConfiguration").val() == true || $("#statusRemarkConfiguration").val() == 'true')){
			if($("#editVehicleStatusId").val() != $("#vehicleStatus").val() && $("#changeToStatusId").val() != $("#vehicleStatus").val() ){
				$("#soldDiv1").hide();
				$("#soldDiv2").hide();
				
				if($("#vehicleStatus").val() == 1){
					$("#changeStatusTo").html('ACTIVE');
				}else if($("#vehicleStatus").val() == 2){
					$("#changeStatusTo").html('INACTIVE');
				}else if($("#vehicleStatus").val() == 3){
					$("#changeStatusTo").html('SURRENDER');
				}else if($("#vehicleStatus").val() == 4){
					$("#changeStatusTo").html('SOLD');
					$("#soldDiv1").show();
					$("#soldDiv2").show();
				}else if($("#vehicleStatus").val() == 5){
					$("#changeStatusTo").html('TRIPROUTE');
				}else if($("#vehicleStatus").val() == 6){
					$("#changeStatusTo").html('WORKSHOP');
				}else if($("#vehicleStatus").val() == 7){
					$("#changeStatusTo").html('SEIZED');
				}else if($("#vehicleStatus").val() == 8){
					$("#changeStatusTo").html('ACCIDENT');
				}
				$("#remarkModal").modal('show');
				hideLayer();
				return false;
				
			}
		}
	}
		if(remarkFlag == true){
			if($("#statusRemarkConfiguration").val() != undefined && ($("#statusRemarkConfiguration").val() == true || $("#statusRemarkConfiguration").val() == 'true')){
					if($('#remark').val().trim() == ''){
					showMessage('info','Please Enter Remark to process');
					hideLayer();
					return false;	
					}
					
					if($("#vehicleStatus").val() == 4){
					if($('#StartDate').val() ==''){
						showMessage('info','Please Select Date To process ');
						hideLayer();
						return false;
					}
					if($('#partyName').val().trim() == ''){
						showMessage('info','Please Enter Party Name ');
						hideLayer();
						return false;
					}
					if($('#mobileNo').val().trim() == ''){
						showMessage('info','Please Enter Party Name ');
						hideLayer();
						return false;
					}
//					if(Number($('#soldAmount').val()) <= 0){
//						showMessage('info','Please amount to process ');
//						hideLayer();
//						return false;
//					}
				}
			}
		}
	
	
	
	var jsonObject									= new Object();

	var branchId = 0;
	var branchName = "";
	
	if(Number($('#VehicleLocation').val()) > 0){
		branchId 	= $('#VehicleLocation').val();
		branchName 	= $('#VehicleLocation').select2('data').text;
	}else{
		branchName 	= $('#VehicleLocation').val();
	}

	jsonObject["vid"]								= $('#vid').val();
	jsonObject["vehicleRegistrationNumber"]			= $('#vehicle_registrationNumber').val();
	jsonObject["vehicleChasisNumber"]				= $('#vehicle_chasisNumber').val();
	jsonObject["vehicleEngineNumber"]				= $('#vehicle_engineNumber').val();
	jsonObject["VehicleTypeId"]						= $('#VehicleTypeSelect').val();
	jsonObject["vehicleYear"]						= $('#vehicle_year').val();
	jsonObject["vehicleMaker"]						= $('#vehicle_maker').val();
	jsonObject["vehicleModel"]						= $('#vehicle_Model').val();
	jsonObject["registrationState"]					= $('#registrationState').val();
	jsonObject["vehicleRegisterDate"]				= $('#vehicle_RegisterDate').val();
	jsonObject["vehicle_Registeredupto"]			= $('#vehicle_Registeredupto').val();
	if($('#vehicleStatus').val() == "" || $('#vehicleStatus').val() == undefined){
		jsonObject["vehicleStatusId"]					= $('#editVehicleStatusId').val();
	}else{
		jsonObject["vehicleStatusId"]					= $('#vehicleStatus').val();
	}
	jsonObject["vehicleGroupId"]					= $('#VehicleGroupSelect').val();
	jsonObject["vehicleRouteId"]					= $('#VehicleRouteSelect').val();
	jsonObject["ownership"]							= $('#ownership').val();
	jsonObject["vehicleLocation"]					= branchName;
	jsonObject["vehicleColor"]						= $('#vehicle_Color').val();
	jsonObject["vehicleClass"]						= $('#vehicle_Class').val();
	jsonObject["vehicleBody"]						= $('#vehicle_body').val();
	jsonObject["acTypeId"]							= acTypeId
	jsonObject["vehicleCylinders"]					= $('#vehicle_cylinders').val();
	jsonObject["vehicleCubicCapacity"]				= $('#vehicle_cubicCapacity').val();
	jsonObject["vehiclePower"]						= $('#vehicle_Power').val();
	jsonObject["vehicleWheelBase"]					= $('#vehicle_wheelBase').val();
	jsonObject["vehicleSeatCapacity"]				= $('#vehicle_seatCapacity').val();
	jsonObject["vehicleUnladenWeight"]				= $('#vehicle_unladenWeight').val();
	jsonObject["vehicleLadenWeight"]				= $('#vehicle_ladenWeight').val();
	jsonObject["vehicleTollId"]						= $('#vehicleTollId').val();
	
	if($("#fuelId").val() != null ){
		jsonObject["vehicleFuel"]					= ($("#fuelId").val()).toString();
	}else{
		jsonObject["vehicleFuel"]					= ($("#editFuleId").val()).toString();
	}
	jsonObject["vehicleFuelTank"]					= $('#vehicle_FuelTank1').val();
	jsonObject["vehicleOilCapacity"]				= $('#vehicle_Oil').val();
	jsonObject["vehicleMeterUnitId"]				= meterUnitId;
	jsonObject["vehicleOdometer"]					= $('#vehicleOdometer').val();
	jsonObject["vehicleFuelUnitId"]					= fuelUnitId
	jsonObject["photoId"]							= $('#photoId').val(); 
	jsonObject["expectedMileageFrom"]				= $('#vehicle_ExpectedMileage').val();
	jsonObject["expectedMileageTo"]					= $('#vehicle_ExpectedMileageTo').val();
	jsonObject["maximumOdometer"]					= $('#MaximumOdometer').val();
	jsonObject["vehicleGPSId"]						= $('#vehicleGPSId').val();
	jsonObject["gpsVendorId"]						= $('#gpsVendorId').val();
	jsonObject["ledgerName"]						= $('#ledgerName').val();
	jsonObject["mobileNumber"]						= $('#mobileNumber').val();
	jsonObject["serviceProgramId"]					= $('#serviceProgramId').val();
	jsonObject["branchId"]							= branchId;
	
	jsonObject["changeRemark"]							= $('#remark').val();
	jsonObject["currentStatusId"]						= $('#editVehicleStatusId').val();
	jsonObject["changeToStatusId"]						= $('#vehicleStatus').val();
	jsonObject["userId"]								= $('#userId').val();
	jsonObject["companyId"]								= $('#companyId').val();
	jsonObject["soldDate"]								= $('#StartDate').val() ;
	jsonObject["partyName"]								= $('#partyName').val() ;
	jsonObject["mobileNo"]								= $('#mobileNo').val() ;
	jsonObject["soldAmount"]							= $('#soldAmount').val() ;
	
	jsonObject["vehiclebodyMaker"]						= $('#bodyMakerSelect').val();
	jsonObject["subCompanyId"] 						    = $("#subCompany").val();
	$.ajax({
		url: "updateVehicleDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.VehicleRegExist != undefined && data.VehicleRegExist == true){
				showMessage('info', 'Vehicle Number Already Exist!');
				$('#vehicleRegNo').val(''); 
				hideLayer();
				return false;
			}else if(data.VehicleChasisExist != undefined && data.VehicleChasisExist == true){
				showMessage('info', 'Vehicle Chasis Number Already Exist!');
				hideLayer();
				return false;
			}else if(data.VehicleEngineExist != undefined && data.VehicleEngineExist == true){
				showMessage('info', 'Vehicle Engine Number Already Exist!');
				hideLayer();
				return false;
			}else if(data.alreadyExist != undefined && data.alreadyExist == true){
				showMessage('info', 'Already Exsit!');
				hideLayer();
				return false;
			}else if(data.vehicleNumberNotFound != undefined && data.vehicleNumberNotFound == true){
				showMessage('info', 'Please Enter Vehicle Registration Number!');
				hideLayer();
				return false;
			}else if(data.ChasisNoNotFound != undefined && data.ChasisNoNotFound == true){
				showMessage('info', 'Please Enter Chasis Number!');
				$('#vehicleChasisNo').val('');
				hideLayer();
				return false;
			}else if(data.EngineNoNotFound != undefined && data.EngineNoNotFound == true){
				showMessage('info', 'Please Enter Engine Number!');
				$('#validateEnginNo').val('');
				hideLayer();
				return false;
			}else if(data.VehicleTypeNoNotFound != undefined && data.VehicleTypeNoNotFound == true){
				showMessage('info', 'Please Select Vehicle Type!');
				//$('#validateEnginNo').val('');
				hideLayer();
				return false;
			}else if(data.VehicleStatusNotFound != undefined && data.VehicleStatusNotFound == true){
				showMessage('info', 'Please Select Vehicle Status!');
				//$('#validateEnginNo').val('');
				hideLayer();
				return false;
			}else if(data.VGroupNotFound != undefined && data.VGroupNotFound == true){
				showMessage('info', 'Please Select Vehicle Group!');
				hideLayer();
				return false;
			}else if(data.VehicleFuelTypeNotFound != undefined && data.VehicleFuelTypeNotFound == true){
				showMessage('info', 'Please Select Vehicle Fuel Type!');
				//$('#validateEnginNo').val('');
				hideLayer();
				return false;
			}else if(data.VehicleOdometerNotFound != undefined && data.VehicleOdometerNotFound == true){
				showMessage('info', 'Please Enter Vehicle Odometer!');
				hideLayer();
				return false;
			}else if(data.VehicleExpectedOdometerNotFound != undefined && data.VehicleExpectedOdometerNotFound == true){
				showMessage('info', 'Please Enter Maximum Allow Odometer!');
				hideLayer();
				return false;
			}else if(data.VehicleExpectedMileageNotFound != undefined && data.VehicleExpectedMileageNotFound == true){
				showMessage('info', 'Please Enter Expected Mileage!');
				hideLayer();
				return false;
			}else if(data.aboveMaxOdometer != undefined && data.aboveMaxOdometer == true){
				showMessage('info', 'Maximum Allow Odometer Can Not be Greater Than '+data.maxAllowedOdo);
				hideLayer();
				return false;
			}else if(data.duplicateCount != undefined && data.duplicateCount > 0){
				showMessage('info', 'No Service Reminder Saved, Already Exits  ! ');
				hideLayer();
			}else{
				showMessage('success', 'Vehicle Updated Successfully!');
				window.location.replace("showVehicle?vid="+data.vid+"");
			}
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

$(".activeClassification").click(function(){
	console.log("Hello")
	$("#classInformation").addClass("active");
	$("#basicInformation").removeClass("active");
	$("#ownInformation").removeClass("active");
	$("#settingInformation").removeClass("active");
	
	}); 
	
$(".activeVehicleInfo").click(function(){
	console.log("hi")
	$("#basicInformation").addClass("active");
	$("#classInformation").removeClass("active");
	$("#ownInformation").removeClass("active");
	$("#settingInformation").removeClass("active");
	
	});
	
$(".activeOwnership").click(function(){
	$("#ownInformation").addClass("active");
	$("#basicInformation").removeClass("active");
	$("#classInformation").removeClass("active");
	$("#settingInformation").removeClass("active");
	
});
		
$(".activeSetting").click(function(){
	$("#basicInformation").removeClass("active");
	$("#classInformation").removeClass("active");
	$("#ownInformation").removeClass("active");
	$("#settingInformation").addClass("active");

});
