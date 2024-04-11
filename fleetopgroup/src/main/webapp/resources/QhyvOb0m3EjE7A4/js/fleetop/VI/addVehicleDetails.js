var ACTIVE							= 1;
var DEISEL							= 1;
var LITERS							= 1;
var KM								= 1;
var AC								= 1;

var defaultFuelId 					= DEISEL;
var fuelDefaultUnitId 				= LITERS;
var defaultMeterUnitId 				= KM;
var defaultAcTypeId 				= AC;
var defaultVehicleStatus			= ACTIVE;

function showSelectedfuel(){
	$("input[name='vehicleFuelUnitId']:checked").each(function(){
		fuelDefaultUnitId = $(this).val();
	});
}
function showMeterSelected(){
	$("input[name='vehicleMeterUnitId']:checked").each(function(){
		defaultMeterUnitId = $(this).val();
	});
}
function showAcType(){
	$("input[name='acTypeId']:checked").each(function(){
		defaultAcTypeId = $(this).val();
	});
}

$(document).ready(
		function($) {
			$('button[type=submit]').click(function(e) {
				saveVehicleDetails();
			}),$('#MaximumOdometer').keyup(function(e) {
				var maximumOdometer = Number($('#MaximumOdometer').val());
				if(maximumOdometer > Number($('#allowMaximumOdometer').val())){
					showMessage('info', 'Maximum Allow Odometer Can Not be Greater Than '+$('#allowMaximumOdometer').val());
					return false;
				}
			}),$('#ExpectedMileage_from').keyup(function(e) {
				var ExpectedMileage_from = Number($('#ExpectedMileage_from').val());
				if(ExpectedMileage_from > 100){
					$('#ExpectedMileage_from').val(0);
					showMessage('info', 'Expected Mileage_From Can not be greater than 100');
					return false;
				}
			}),$('#ExpectedMileage_from').blur(function(e) {
				var ExpectedMileage_from = Number($('#ExpectedMileage_from').val());
				var ExpectedMileage_to = Number($('#ExpectedMileage_to').val());
				if(ExpectedMileage_to < ExpectedMileage_from ){
					$('#ExpectedMileage_to').val(0);
					showMessage('info', 'Expected Mileage_From Can not be greater Expected Mileage_To');
					return false;
				}
			}),$('#ExpectedMileage_to').keyup(function(e) {
				var ExpectedMileage_to = Number($('#ExpectedMileage_to').val());
				if(ExpectedMileage_to > 100 ){
					$('#ExpectedMileage_to').val(0);
					showMessage('info', 'Expected Mileage_To Can not be greater than 100');
					return false;
				}
			}),$('#ExpectedMileage_to').blur(function(e) {
				var ExpectedMileage_from = Number($('#ExpectedMileage_from').val());
				var ExpectedMileage_to = Number($('#ExpectedMileage_to').val());
				if(ExpectedMileage_to < ExpectedMileage_from ){
					$('#ExpectedMileage_from').val(0);
					showMessage('info', 'Expected Mileage_From Can not be greater Expected Mileage_To');
					return false;
				}
			}), $('#vehicleOilCapacity').keyup(function(e) {
				var vehicleOilCapacity = Number($('#vehicleOilCapacity').val());
				if(vehicleOilCapacity > 100){
					$('#vehicleOilCapacity').val(0);
					showMessage('info', 'Engine Oil Capacity  Can not be greater than 100');
					return false;
				}
			}),$('#vehicleFuelTank').keyup(function(e) {
				var vehicleFuelTank = Number($('#vehicleFuelTank').val());
				if(vehicleFuelTank > 1000){
					$('#vehicleFuelTank').val(0);
					showMessage('info', ''+$("#fuelTankText").val()+' Can not be greater than 1000');
					return false;
				}
			});
			
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

function saveVehicleDetails(){
	showLayer();

	if( $('#vehicleRegNo').val() == "" || $('#vehicleRegNo').val() == null ){
		showMessage('info','Please Enter Vehicle Registration No');
		$('#vehicleRegNo').focus();
		hideLayer();
		return false;
	}
	if($("#validateChasisNo").val() == true || $("#validateChasisNo").val() == 'true' &&  $('#vehicleChasisNo').val() =="" || $('#vehicleChasisNo').val() == null ){
		showMessage('info','Please Select Chasis No');
		$('#vehicleChasisNo').focus();
		hideLayer();
		return false;
	}
	if($("#validateEngineNo").val() == true || $("#validateEngineNo").val() == 'true' &&  $('#vehicleEngineNo').val() =="" || $('#vehicleEngineNo').val() == null ){
		showMessage('info','Please Select Engine NO');
		$('#validateEnginNo').focus();
		hideLayer();
		return false;
	}
	if($("#validateVType").val() == true || $("#validateVType").val() == 'true' &&  $('#VehicleTypeSelect').val() =="" || $('#VehicleTypeSelect').val() == null ){
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
	if( $('#VehicleGroupSelect').val() == "" || $('#VehicleGroupSelect').val() == null ){
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
	
	if($("#validateVFuelType").val() == true || $("#validateVFuelType").val() == 'true' &&  $('#vehicleFuel').val() =="" || $('#vehicleFuel').val() == null ){
		showMessage('info','Please Select Vehicle Fuel Type');
		hideLayer();
		return false;
	}
	if(($("#validateVOdometer").val() == true || $("#validateVOdometer").val() == 'true' )&& ( $('#vehicleOdometer').val() =="" || $('#vehicleOdometer').val() == null) ){
		showMessage('info','Please Enter Vehicle Odometer');
		hideLayer();
		return false;
	}
	if(($("#validateVExpectedOdometer").val() == true || $("#validateVExpectedOdometer").val() == 'true' )&& ( $('#MaximumOdometer').val() =="" || $('#MaximumOdometer').val() == null) ){
		showMessage('info','Please Enter Maximum Allow Odometer');
		hideLayer();
		return false;
	}
	if(($("#validateVExpectedMileage").val() == true || $("#validateVExpectedMileage").val() == 'true' )&& (( $('#ExpectedMileage_from').val() =="" || $('#ExpectedMileage_from').val() == null ) || ( $('#ExpectedMileage_to').val() =="" || $('#ExpectedMileage_to').val() == null )) ){
		showMessage('info','Please Enter Expected Mileage');
		hideLayer();
		return false;
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

	jsonObject["vehicleRegistrationNumber"]			= $('#vehicleRegNo').val().toUpperCase();
	jsonObject["vehicleChasisNumber"]				= $('#vehicleChasisNo').val();
	jsonObject["vehicleEngineNumber"]				= $('#vehicleEngineNo').val();
	jsonObject["VehicleTypeId"]						= $('#VehicleTypeSelect').val();
	jsonObject["vehicleYear"]						= $('#vehicle_year').val();
	jsonObject["vehicleMaker"]						= $('#vehicleMaker').val();
	jsonObject["vehicleModel"]						= $('#vehicleModel').val();
	jsonObject["registrationState"]					= $('#registrationState').val();
	jsonObject["vehicleRegisterDate"]				= $('#vehicle_RegisterDate').val();
	jsonObject["vehicle_Registeredupto"]			= $('#vehicle_Registeredupto').val();
	jsonObject["serviceProgramId"]					= $('#serviceProgramId').val();
	jsonObject["vehicleTollId"]					= $('#vehicleTollId').val();
	
	if($("#vehicleStatus").val() == "" || $("#vehicleStatus").val() == undefined){
		jsonObject["vehicleStatusId"]				= defaultVehicleStatus;
	}else{
		jsonObject["vehicleStatusId"]				= $("#vehicleStatus").val();
	}
	
	jsonObject["vehicleGroupId"]					= $('#VehicleGroupSelect').val();
	jsonObject["vehicleRouteId"]					= $('#VehicleRouteSelect').val();
	jsonObject["ownership"]							= $('#ownership').val();
	jsonObject["vehicleLocation"]					= branchName;
	jsonObject["vehicleColor"]						= $('#vehicle_Color').val();
	jsonObject["vehicleClass"]						= $('#vehicle_Class').val();
	jsonObject["vehicleBody"]						= $('#vehicle_body').val();
	
	jsonObject["acTypeId"]							= defaultAcTypeId;
	
	jsonObject["vehicleCylinders"]					= $('#vehicle_cylinders').val();
	jsonObject["vehicleCubicCapacity"]				= $('#vehicle_cubicCapacity').val();
	jsonObject["vehiclePower"]						= $('#vehicle_Power').val();
	jsonObject["vehicleWheelBase"]					= $('#vehicle_wheelBase').val();
	jsonObject["vehicleSeatCapacity"]				= $('#vehicle_seatCapacity').val();
	jsonObject["vehicleUnladenWeight"]				= $('#vehicle_unladenWeight').val();
	jsonObject["vehicleLadenWeight"]				= $('#vehicle_ladenWeight').val();
	if($("#vehicleFuel").val() != null){
		jsonObject["vehicleFuel"]					= ($("#vehicleFuel").val()).toString();
	}else{
		jsonObject["vehicleFuel"]					= defaultFuelId;
	}
	jsonObject["vehicleFuelTank"]					= $('#vehicleFuelTank').val();
	jsonObject["vehicleOilCapacity"]				= $('#vehicleOilCapacity').val();
	
	jsonObject["vehicleMeterUnitId"]				= defaultMeterUnitId;
	
	jsonObject["vehicleOdometer"]					= $('#vehicleOdometer').val();
	
	jsonObject["vehicleFuelUnitId"]					= fuelDefaultUnitId
	
	jsonObject["photoId"]							= $('#photoId').val(); 
	jsonObject["expectedMileageFrom"]				= $('#ExpectedMileage_from').val();
	jsonObject["expectedMileageTo"]					= $('#ExpectedMileage_to').val();
	jsonObject["maximumOdometer"]					= $('#MaximumOdometer').val();
	jsonObject["vehicleGPSId"]						= $('#vehicleGPSId').val();
	jsonObject["gpsVendorId"]						= $('#gpsVendorId').val();
        if($('#ledgerName').val() !=="" && $('#ledgerName').val() !==null && $('#ledgerName').val() !== undefined){
			jsonObject["ledgerName"] = $('#ledgerName').val();
		}else{
		      if($("#allowVehicleNameInLedger").val() == true || $("#allowVehicleNameInLedger").val() == 'true'){
				jsonObject["ledgerName"] = $('#vehicleRegNo').val().toUpperCase();	
		}
	}
	jsonObject["mobileNumber"]						= $('#mobileNumber').val();
	jsonObject["branchId"]							= branchId;
	jsonObject["vehiclebodyMaker"]					= $('#bodyMakerSelect').val();
	jsonObject["subCompanyId"] 						= $("#subCompany").val();

	  $.ajax({
		url: "saveVehicleDetails",
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
				hideLayer();
				return false;
			}else if(data.VGroupNotFound != undefined && data.VGroupNotFound == true){
				showMessage('info', 'Please Select Vehicle Group!');
				hideLayer();
				return false;
			}else if(data.VehicleStatusNotFound != undefined && data.VehicleStatusNotFound == true){
				showMessage('info', 'Please Select Vehicle Status!');
				hideLayer();
				return false;
			}else if(data.VehicleFuelTypeNotFound != undefined && data.VehicleFuelTypeNotFound == true){
				showMessage('info', 'Please Select Vehicle Fuel Type!');
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
				showMessage('info', 'Maximum Allow Odometer Can Not be Greater Than'+data.maxAllowedOdo);
				hideLayer();
				return false;
			}else{
				showMessage('success', 'New Vehicle Created Successfully!');
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

function vehicleNumberValidation(){

//	var pattern = /^[A-Z]{2}([ \-])[0-9]{2}[ ,][A-Z0-9]{1,2}[A-Z]\1[0-9]{3,4}$/ ; //MH-01-BB-2020
//	var pattern = /^[A-Z]{2}[ -][0-9]{1,2}(?: [A-Z])?(?: [A-Z]*)? [0-9]{4}$/; // MH-01-2020
//	var pattern = /^[A-Z]{2}([ \-])[0-9]{2}[ ,][A-Z0-9]{1,2}[A-Z]\1[0-9]{3,4}$/ ; //MH-01-BB-2020
	var pattern = /^[A-Z]{2}([ \-])[0-9]{2}[ ,][A-Z]{1,2}\1[0-9]{3,4}$/ ; //MH-01-B-2020
	var vehicle = ($("#vehicleRegNo").val().split('-').join(' ').toUpperCase());
		
		if(!vehicle.match(pattern)){
			showMessage('info','Please Enter Valid Vehicle Number');
			$("#vehicleRegNo").val('')
			return false;
		}
		
	}






