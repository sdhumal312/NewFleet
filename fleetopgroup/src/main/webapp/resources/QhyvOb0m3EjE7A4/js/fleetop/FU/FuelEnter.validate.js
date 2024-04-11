var defaultFuelPrice = $("#defaultFuelPrice").val();
var paidBybranch = false;
var elementShown=true;

function IsNumericOdometer(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = t >= 48 && 57 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorOdometer").innerHTML = "Alphabets & Special Characters not allowed", document.getElementById("errorOdometer").style.display = n ? "none" : "inline", n
}

function isNumberKey(e, t) {
    var n = e.which ? e.which : event.keyCode;
    if (n > 31 && (48 > n || n > 57) && 46 != n && 8 != charcode) return !1;
    var r = $(t).val().length,
        a = $(t).val().indexOf(".");
    return !(a > 0 && 46 == n) && !(a > 0 && r + 1 - a > 3)
}

function IsVendorName(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = t > 31 && 33 > t || t > 44 && 48 > t || t >= 48 && 57 >= t || t >= 65 && 90 >= t || t >= 97 && 122 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorVendorName").innerHTML = "Special Characters not allowed", document.getElementById("errorVendorName").style.display = n ? "none" : "inline", n
}

function IsVendorLocation(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = t > 31 && 33 > t || t > 44 && 48 > t || t >= 48 && 57 >= t || t >= 65 && 90 >= t || t >= 97 && 122 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorVendorLocation").innerHTML = "Special Characters not allowed", document.getElementById("errorVendorLocation").style.display = n ? "none" : "inline", n
}

function IsReference(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = t > 31 && 33 > t || t > 44 && 48 > t || t >= 48 && 57 >= t || t >= 65 && 90 >= t || t >= 97 && 122 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorReference").innerHTML = "Special Characters not allowed", document.getElementById("errorReference").style.display = n ? "none" : "inline", n
}

function IsComment(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = t > 31 && 33 > t || t > 44 && 48 > t || t >= 65 && 90 >= t || t >= 97 && 122 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorComment").innerHTML = "Special Characters not allowed", document.getElementById("errorComment").style.display = n ? "none" : "inline", n
}

function FuelValidate() {
    var e = !0,
        t = document.getElementById("fuel_meter");
    return ValidateMeter(t) || (e = !1), e
}


function validateMandatory(e, t) {
    var n = !0;
    return 0 != e && null != e || "" != t && null != t ? (errorVendorSelect.innerHTML = "", document.getElementById("enterVendorName").style.border = "", document.getElementById("enterVendorLocation").style.border = "") : (errorVendorSelect.innerHTML = "Please Select Vendor or Enter", document.getElementById("enterVendorName").style.border = "2px solid #F00", document.getElementById("enterVendorLocation").style.border = "2px solid #F00", n = !1), n
}
var specialKeys = new Array;
specialKeys.push(8), specialKeys.push(9), specialKeys.push(46), specialKeys.push(36), specialKeys.push(35), specialKeys.push(37), specialKeys.push(39), $(function() {
    $("#fuel_liters").keyup(function() {
        if ($(this).val($(this).val().replace(/[^0-9\.]/g, "")), -1 != $(this).val().indexOf(".") && $(this).val().split(".")[1].length > 2) {
            if (isNaN(parseFloat(this.value))) return;
            this.value = parseFloat(this.value).toFixed(2)
        }
        return this
    })
}),$(document).ready(function() {
	if($('#lastFuelAsOpen').val() == 'true' || $('#lastFuelAsOpen').val() == true){
		$('#openKmRow').show();
	}
    $("#driverEnter").hide(), $("#JoBEnter").hide(), $("#Branch_Depart_hide").hide(), $("#to").select2(), $("#tagPicker").select2({
        closeOnSelect: !1
    }),$("#FuelSelectVehicle, #TRIPFUELADD").change(function(){
    	setVehicleOnChangeDate($(this).val());
    	 if($('#showTripSheetDropDown').val() == 'true' || $('#showTripSheetDropDown').val() == true)
    	    	setTripSheetData();
    	 
    	 $('#showBackDateMessage').addClass('hide');
		 $('#backDateRange').hide();
		 if($("#showCurrnetDateAndTime").val() == true || $("#showCurrnetDateAndTime").val() == 'true' ){
			 var currentdate = new Date(); 
			 $('#fuelDate').val(currentdate.getDate()+"-" + (currentdate.getMonth()+1)+"-"+currentdate.getFullYear());
			 if(Number(currentdate.getMinutes()) <  10){
				$('#fuelTime').val(currentdate.getHours() + ":0" + currentdate.getMinutes());
			}else if(Number(currentdate.getHours()) <  10){
				$('#fuelTime').val("0"+currentdate.getHours() + ":" + currentdate.getMinutes());
			} else{
				$('#fuelTime').val(currentdate.getHours() + ":" + currentdate.getMinutes());
			}
			 getGPSAndActiveTripData(false);
		 }else{
			 $('#fuelDate').val('');
			 $('#fuelTime').val('');
		 }
		
		 if($("#setDefaultDriverForVehicle").val() == true || $("#setDefaultDriverForVehicle").val() == 'true'){
			 setDriverName();
		 }
    		
    }),$("#fuel_liters").change(function(){
    	setFuelTankSuggestion($(this).val());
    })
});
$(function() {
    $("#fuel_price").keyup(function() {
        if (this.value = this.value.replace(/[^0-9\.]/g, ""), -1 != $(this).val().indexOf(".") && $(this).val().split(".")[1].length > 2) {
            if (isNaN(parseFloat(this.value))) return;
            
            this.value = parseFloat(this.value).toFixed(2)
        }
        if(!validateFuelPrice()){
    		return false;
    	}
        return this
    })
}), $(document).ready(function() {
    $("#datemask").inputmask("dd-mm-yyyy", {
        placeholder: "dd-mm-yyyy"
    }), $("[data-mask]").inputmask()
}), $(document).ready(function() {
    $("#FuelSelectVehicle").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getVehicleListFuel.in?Action=FuncionarioSelect2",
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
    }), $("#selectVendor").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getVendorSearchListFuel.in?Action=FuncionarioSelect2",
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
                            text: e.vendorName + " - " + e.vendorLocation,
                            slug: e.slug,
                            id: e.vendorId,
                            ownPetrolPump : e.ownPetrolPump
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
    }),$("#selectVendor").change(function(){
    	$('#ownPetrolPump').val(0);
		$('#stock').text('');
		$('#fuel_price').val('');
		$('#fuel_price').attr('readonly', false);
		$('#fuelAmount').val('');
		$('#fuel_liters').val('');
		$("#fuelInvoiceId").val('');
    	var vendorData = $("#selectVendor").select2('data');
    	if(vendorData != undefined && vendorData.ownPetrolPump != undefined && vendorData.ownPetrolPump == 1){
    		$('#ownPetrolPump').val(vendorData.ownPetrolPump);
    		if(($('#getStockFromInventoryConfig').val() == true || $('#getStockFromInventoryConfig').val() =='true')){
    			if(($("#isEditFuelEntry").val() == undefined)){
    				$('#priceAndAmount').hide();
        			getPetrolPumpFuelStockDetails();
    			}else{
    				getPetrolPumpFuelStockDetailsEdit();
    			}
    		}else{
    			getPetrolPumpFuelStockDetails();
    		}
    		
    		
    		/*if(($('#getStockFromInventoryConfig').val() == true || $('#getStockFromInventoryConfig').val() =='true') && ($("#isEditFuelEntry").val() == undefined)){
    			$('#priceAndAmount').hide();
    			console.log('I am here ');
    			getPetrolPumpFuelStockDetails();
    		}if(($('#getStockFromInventoryConfig').val() == true || $('#getStockFromInventoryConfig').val() =='true') && ($("#isEditFuelEntry").val() == true || $("#isEditFuelEntry").val() == 'true' )){
    			getPetrolPumpFuelStockDetailsEdit();
    		}*/
    	}else{
    		if($('#getStockFromInventoryConfig').val() == true || $('#getStockFromInventoryConfig').val() =='true'){
    			$('#priceAndAmount').show();
    		}
    	}
    	
    }), $("#VehicleTODriverFuel").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getDriverSearchListFuel.in?Action=FuncionarioSelect2",
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
                            text: e.driver_empnumber + " - " + e.driver_firstname,
                            slug: e.slug,
                            id: e.driver_id
                        }
                    })
                }
            }
        }
    }),$("#VehicleTODriver2Fuel").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getDriverSearchListFuel.in?Action=FuncionarioSelect2",
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
                            text: e.driver_empnumber + " - " + e.driver_firstname,
                            slug: e.slug,
                            id: e.driver_id
                        }
                    })
                }
            }
        }
    }),$("#TripRouteList").select2({
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
    }),$("#VehicleTOCleanerFuel").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "SearchOnlyAllCleanerNAME.in?Action=FuncionarioSelect2",
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
                            text: e.driver_empnumber + " - " + e.driver_firstname,
                            slug: e.slug,
                            id: e.driver_id
                        }
                    })
                }
            }
        }
    }),
    
    $("#selectBranch").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getBranchList",
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
                            text: e.branch_name,
                            slug: e.slug,
                            id: e.branch_id
                        }
                    })
                }
            }
        }
    })
});

function validateMaxOdameter(){
	
	if(document.getElementById('meter_attributes').checked){
		return true;
	}
	
	var validateOdometerInFuel			= $('#validateOdometerInFuel').val();
	var validateFuel  					= false;
	if(validateOdometerInFuel == 'true' || validateOdometerInFuel == true){
		validateFuel = true;
	}
	var minOdometer 					=  Number($('#minOdometer').val());
	var maxOdometer						=  Number($('#maxOdometer').val());
	var fuelMeter						=  Number($('#fuel_meter').val());
	var doNotValidateBackDateEntries    =  $('#doNotValidateBackDateEntries').val();
	if(fuelMeter <= 0){
		$('#fuel_meter').focus();
		showMessage('errors', 'Odometer cannot be zero !');
		return false;
	}
	
	var current  	= new Date().getFullYear() + '-' + ('0' + (new Date().getMonth()+1)).slice(-2)+ '-' + ('0' + (new Date().getDate())).slice(-2);
	var fuelDate 	= $('#fuelDate').val().split("-").reverse().join("-");
	var date = moment(fuelDate);
	var now = moment(current);
	if(date.isBefore(now) && (doNotValidateBackDateEntries == 'true' || doNotValidateBackDateEntries == true)) {
		validateFuel =  false;
	}
	
	if($('#allowManualOdometerEntry').val() == 'true' || $('#allowManualOdometerEntry').val() == true){
		validateFuel =  false;
		if(fuelMeter <= Number($('#fuel_meter_old').val())){
			$('#fuel_meter').focus();
			showMessage('errors', 'Old Odometer will be less then current odometer !');
			return false;
		}
		
		if((fuelMeter - Number($('#fuel_meter_old').val())) > Number($('#vehicle_ExpectedOdameter').val())){
			$('#fuel_meter').focus();
			showMessage('errors', 'Differen between Old Odometer and Current Odometer cannot be greater then '+$('#vehicle_ExpectedOdameter').val());
			return false;
		}
	}
	
	if(validateFuel){
		if(fuelMeter < minOdometer || fuelMeter > maxOdometer){
			$('#fuel_meter').focus();
			showMessage('errors', 'Please Enter Odometer in Range Of : '+minOdometer+' - '+maxOdometer);
			return false;
		}
	}
		
	return true;
}

function applyGpsSettings(gpsConfiguration){
	var jsonObject = JSON.parse(gpsConfiguration);
	if(jsonObject.allowGPSIntegration){
		$('#gpsOdometerRow').show();
	}
}


$(document).ready(function () {
	$("#showBranch").hide();
	
	if($("#paidByOptionInAddFuleEntry").val() == true || $("#paidByOptionInAddFuleEntry").val()== "true"){
		$('#selectBranch').select2('data', {
				id : $("#userBranchId").val(),
				text : $("#userBranch").val()
		});
		$("#showBranch").show();
		$("#paidByBranch").prop("selected", true);
	}
    $("#selectVendor").on("change", function() {
        var a = document.getElementById("selectVendor").value;
        if (0 != a) {
            var b = '<option value="1"> CASH</option><option value="2">CREDIT</option><option value="11">UPI</option><option value="3">NEFT</option><option value="4">RTGS</option><option value="5">IMPS</option><option value="12">CARD</option><option value="7">CHEQUE</option>';
            $("#renPT_option").html(b)
        } else {
            var b = '<option value="1">CASH</option>';
            $("#renPT_option").html(b)
        }
    });

	 $("#renPT_option").change(function (){
	
		 var selectedValue = $(this).val(); 
		
		 if(selectedValue > 1)
		 {
			 $('#paidByOption').hide();
			 elementShown = false;
		 }
		 else
		 {
			 $('#paidByOption').show();
			 elementShown = true;
		 }
	});
	
    $("#btnSubmit").on('keypress click', function (e) {
        e.preventDefault(); 	//stop submit the form, we will post it manually.
        
        saveFuelEntry();
    });

});

function validateBranch()
{
	if(document.querySelector('select[name="paidBy"]').value == '2' && $("#selectBranch").val()==0)
	{
		showMessage('errors', 'select Branch name !');
		return false;
	}
	
	return true;
}

function saveFuelEntry(){
	 $("#btnSubmit").hide();
	 
	if($('#maxOdometerAsTripCloseKM').val() == "true" || $('#maxOdometerAsTripCloseKM').val() == true){
		if($('#tripStatusId').val() == 3){
	
			if(Number($('#fuel_meter').val()) > Number($('#tripCloseKM').val())){
				
				$('#fuel_meter').focus();
				showMessage('errors', 'Odometer must less than or equal to closing Trip KM !' +Number($('#tripCloseKM').val()));
				$("#btnSubmit").show();
				return false;
			}
			
		}	
	}
	
	if(!validateMaxOdameter()){
		$("#btnSubmit").show();
		return false;
	}
	
	if(!validateFuelEntries()){
		$("#btnSubmit").show();
		return false;
	}
	
	if(!validateFuelPrice()){
		$("#btnSubmit").show();
		return false;
	}
	
	
	if(!validateMaxFuelPrice()){
		$("#btnSubmit").show();
		return false;
	}
	
	
	//if($('#companyId').val()== 46)
	if($("#paidByOptionInAddFuleEntry").val() == "true" || $("#paidByOptionInAddFuleEntry").val() == true)
	{
		if(!validateBranch())
		{
			$("#btnSubmit").show();
			return false;
		}
	}
	
	
	
	showLayer();
	var jsonObject					= new Object();

	jsonObject["validateDoublePost"] 	 =  true;
	jsonObject["unique-one-time-token"]  =  $('#accessToken').val();
	jsonObject["FuelSelectVehicle"]					= $('#FuelSelectVehicle').val();
	jsonObject["vehicle_group"]						= $('#vehicle_group').val();
	jsonObject["fuel_TripsheetNumber"]				= $('#fuel_TripsheetNumber').val();
	jsonObject["fuelDate"]							= $('#fuelDate').val();
	
	if($("#twelveHourClock").val() == "true")
	{
		var Time = ChangeFuelTime();
		jsonObject["fuelTime"] 				=  Time
	}
	else
	{
		jsonObject["fuelTime"]							= $('#fuelTime').val();
	}
	jsonObject["fuel_meter_old"]					= $('#fuel_meter_old').val();
	jsonObject["fuel_meter"]						= $('#fuel_meter').val();
	jsonObject["gpsOdometer"]						= $('#gpsOdometer').val();
	jsonObject["fuel_type"]							= $('#fuel_type').val();
	jsonObject["selectVendor"]						= $('#selectVendor').val();
	jsonObject["fuel_liters"]						= $('#fuel_liters').val();
	jsonObject["fuel_price"]						= $('#fuel_price').val();
	jsonObject["fuel_reference"]					= $('#fuel_reference').val();
	jsonObject["VehicleTODriverFuel"]				= $('#DriverFuel').val();
	jsonObject["VehicleTODriver2Fuel"]				= $('#Driver2Fuel').val();
	jsonObject["VehicleTOCleanerFuel"]				= $('#VehicleTOCleanerFuel').val();
	jsonObject["TripRouteList"]						= $('#FuelRouteList').val();
	jsonObject["fuel_personal"]						= $('#fuel_personal').val();
	jsonObject["renewalFile"]						= $('#renewalFile').val();
	jsonObject["fuel_comments"]						= $('#fuel_comments').val();
	jsonObject["tripSheetId"]						= $('#tripSheetId').val();
	jsonObject["companyId"]							= $('#companyId').val();
	jsonObject["userId"]							= $('#userId').val();
	jsonObject["tripsheetNumberSelect"]				= $('#tripsheetNumberSelect').val();
	jsonObject["tallyCompanyId"]					= $('#tallyCompanyId').val();
	jsonObject["creatingBackDateFuel"]				= $('#creatingBackDateFuel').val();
	jsonObject["nextFuelIdOfBackDate"]				= $('#nextFuelIdOfBackDate').val();
	jsonObject["fuelAmount"]						= $('#fuelAmount').val();
	jsonObject["ownPetrolPump"]						= $('#ownPetrolPump').val();
	jsonObject["fuelInvoiceId"]						= $('#fuelInvoiceId').val();
	jsonObject["currentFuelInvoiceId"]				= $('#fuelInvoiceId').val();
	jsonObject["paidByBranchId"] 					= $('#selectBranch').val();
	if($("#DriverFuel").val() == ""){
		jsonObject["paidById"]				    	= 0;
	}
	else{
		jsonObject["paidById"]				    	= $("#DriverFuel").val();
	}
	
	if(Number($('#tripSheetId').val()) > 0){
		jsonObject["tripSheetId"]					= Number($('#tripSheetId').val());
	}else if($('#tripsheetNumberSelect').val() != undefined && Number($('#tripsheetNumberSelect').val()) > 0){
		jsonObject["tripSheetId"]					= Number($('#tripsheetNumberSelect').val());
	}
	
	
	if($("#paymentOption").val() == "true")
	{
		jsonObject["paymentTypeId"]					= $('#renPT_option').val();
	}
	else
	{
		if(document.getElementById('paymentTypeCreditId').checked) {
			jsonObject["paymentTypeId"]					= $('#paymentTypeCreditId').val();
		}
		else{
			jsonObject["paymentTypeId"]					= $('#paymentTypeId').val();
		}
	}	
		
	if(document.getElementById('fuel_tank_partial').checked) {
		jsonObject["fuel_tank"]					= $('#fuel_tank_partial').val();
	}else{
		jsonObject["fuel_tank"]					= $('#fuel_tank').val();
	}	
	
	if(document.getElementById('meter_attributes').checked) {
		jsonObject["fuel_meter_attributes"]				= $('#meter_attributes').val();
	}else{
		jsonObject["fuel_meter_attributes"]				= $('#fuel_meter_attributes').val();
	}
	jsonObject["getStockFromInventoryConfig"]				= $('#getStockFromInventoryConfig').val();
	
	if($("#addFuelDocument").val()   == true || $("#addFuelDocument").val() == 'true'){
		if($("#renewalFile").val() == undefined  || $("#renewalFile").val() == ""){
			showMessage('info','Please Upload Fuel Document');
			$("#btnSubmit").show();
			hideLayer();
			return false;
		}
	}
	if($("#paidByOptionInAddFuleEntry").val() == "true" || $("#paidByOptionInAddFuleEntry").val() == true){
		if(elementShown==true && document.querySelector('select[name="paidBy"]').value != null && document.querySelector('select[name="paidBy"]').value == '2')
		{
			paidBybranch = true;
		}
		else
		{
			paidBybranch = false;
		}
	}	
	jsonObject["paidByBranch"] = paidBybranch;
	  
	
	var form = $('#fileUploadForm')[0];
    var data = new FormData(form);

	if (allowBankPaymentDetails) {
		prepareObject(jsonObject)
	}
	
    data.append("FuelData", JSON.stringify(jsonObject));
// $("#btnSubmit").prop("disabled", true);
	
	
    
	$.ajax({
		type: "POST",
		enctype: 'multipart/form-data',
		url: "saveFuelEntryDetails",
		data: data,
		processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
		success: function (data) {
			if(data.alreadyExist != undefined && data.alreadyExist == true){
				showMessage('info', 'Already Exist!');
				$('#accessToken').val(data.accessToken);
				hideLayer();
				$("#btnSubmit").show();
				return false;
			}else if(data.noStock != undefined && data.noStock){
				showMessage('info', 'Cannot Save Fuel Due to insufficient Stock !');
				$('#accessToken').val(data.accessToken);
				hideLayer();
				$("#btnSubmit").show();
				return false;
			}else if(data.insufficientStock != undefined && data.insufficientStock){
				showMessage('info', 'Cannot Save Fuel Due to insufficient Stock !');
				$('#accessToken').val(data.accessToken);
				hideLayer();
				$("#btnSubmit").show();
				return false;
			} else if (data.refAlreadyExist != undefined && data.refAlreadyExist == true){
				showMessage('info', 'Fuel Reference Number Already Exist from same vendor please see FT-'+data.fuelNumber);
				$('#accessToken').val(data.accessToken);
				hideLayer();
				$("#btnSubmit").show();
				return false;
			}else if (data.contactAdmin != undefined && data.contactAdmin == true){
				$('#accessToken').val(data.accessToken);
				showMessage('info', 'Sequence Not Found. Please contact Admin !');
				hideLayer();
				$("#btnSubmit").show();
				return false;
			} else if (data.odoMeter != undefined){
				$('#accessToken').val(data.accessToken);
				showMessage('info', data.odoMeter);
				hideLayer();
				$("#btnSubmit").show();
				return false;
			} else if (data.vehicleStatus != undefined){
				$('#accessToken').val(data.accessToken);
				showMessage('info', data.vehicleStatus);
				hideLayer();
				$("#btnSubmit").show();
				return false;
			}else if (data.SuccessId == undefined || data.SuccessId == null || data.SuccessId <= 0){
				showMessage('errors',' Fuel Entry Operation Failed , please contact to system administrator !');
				hideLayer();
				$("#btnSubmit").show();
				return false;
			}  else {
				showMessage('success', 'Record saved successfully !');
				$('#showData').removeClass('hide');
				$('#fuelID').attr('href','showFuel.in?FID='+data.SuccessId);
				$('#fuelID').attr('target','_blank');
				$('#fuelID').html(data.SuccessNum);
				$('#showBackDateMessage').addClass('hide');
				$('#backDateRange').hide();
				//showMessage('success', 'Fuel Entry FT - '+data.SuccessNum+' Saved Successfully!');
				$("#btnSubmit").show();
				$('#back-to-top').trigger('click');
				hideLayer();
			}
			
			var tripSheetID 	= getUrlParameter('tripSheetID');
			if(tripSheetID  != undefined && tripSheetID > 0){
				showLayer();
				location.reload();
			}
			
			if(data.ResetAllFuelEntries != undefined && data.ResetAllFuelEntries == true){
				
				//location.reload();
				 $('#FuelSelectVehicle').select2("val", "");
				 $('#vehicle_group').val('');
				 $('#fuelDate').val('');
				 $('#fuel_meter').val('');
				 $('#gpsOdometer').val('');
				 $('#fuelTime').val(''); 
				 $('#fuel_type').select2("val", "");
				 $('#selectVendor').select2("val", "");
				 $('#fuel_liters').val('');
				 $('#fuel_price').val('');     
				 $('#fuel_reference').val('');
				 $('#DriverFuel').select2("val", "");
				 $('#Driver2Fuel').select2("val", "");
				 $('#VehicleTOCleanerFuel').select2("val", "");
				 $('#FuelRouteList').select2("val", "");
				 $('#renewalFile').val('');
				 $('#fuel_comments').val('');
				 $('#fuel_TripsheetNumber').val('');
				 $('#fuel_meter_old').val('');
				 $('#tripsheetNumberSelect').val('');
				 $('#partialFuelSuggestion').hide();
				 $('#fullFuelSuggestion').hide();
				 $('#fuelAmount').val('');
				 $('#stock').text('');
				 $('#maxStockQuantity').val(0);
				 $('#avgPrice').val(0);
				 $('#fuel_price').val('');
				 // For BackDate
				 $('#nextFuelIdOfBackDate').val(0);
				 $('#nextFuelMeterOfBackDate').val(0);
				 $('#actualOdameterReading').val(0);
				 $('#creatingBackDateFuel').val(false);
				 $('#showBackDateMessage').addClass('hide');
				 document.getElementById('meter_attributes').checked = false;

			} else {
				
				 $('#tripsheetNumberSelect').val('');
				 $('#partialFuelSuggestion').hide();
				 $('#fullFuelSuggestion').hide();
				 $('#fuelAmount').val('');
				 $('#stock').text('');
				 $('#maxStockQuantity').val(0);
				 $('#avgPrice').val(0);
				 $('#fuel_price').val('');
				 
				 if(data.ResetFuelDate != undefined && data.ResetFuelDate == true){
					 $('#fuelDate').val('');
					 $('#fuelTime').val('');
				 }
				 
				 if(data.ResetFuelOdometer != undefined && data.ResetFuelOdometer == true){
					 $('#fuel_meter').val('');
				 }
				
				 if(data.ResetFuelType != undefined && data.ResetFuelType == true){
					 $('#fuel_type').select2("val", "");
				 }
				 
				 if(data.ResetFuelVendor != undefined && data.ResetFuelVendor == true){
					 $('#selectVendor').select2("val", "");
				 }
				 
				 if(data.ResetFuelLiter != undefined && data.ResetFuelLiter == true){
					 $('#fuel_liters').val('');
				 }
				 
				 if(data.ResetFuelPrice != undefined && data.ResetFuelPrice == true){
					 $('#fuel_price').val('');
				 }
				 
				 if(data.ResetFuelReference != undefined && data.ResetFuelReference == true){
					 $('#fuel_reference').val('');
				 }
				 
				 if(data.ResetFuelDriver != undefined && data.ResetFuelDriver == true){
					 $('#DriverFuel').select2("val", "");
				 }
				 
				 if(data.ResetFuelDriverTwo != undefined && data.ResetFuelDriverTwo == true){
					 $('#Driver2Fuel').select2("val", "");
				 }
				 
				 if(data.ResetFuelCleaner != undefined && data.ResetFuelCleaner == true){
					 $('#VehicleTOCleanerFuel').select2("val", "");
				 }
				 
				 if(data.ResetFuelRoute != undefined && data.ResetFuelRoute == true){
					 $('#TripRouteList').select2("val", "");
				 }
				 
				 if(data.ResetFuelImage != undefined && data.ResetFuelImage == true){
					 $('#renewalFile').val('');
				 }
				 
				 if(data.ResetFuelComment != undefined && data.ResetFuelComment == true){
					 $('#fuel_comments').val('');
				 }
				 
				// For BackDate
				 $('#nextFuelIdOfBackDate').val(0);
				 $('#nextFuelMeterOfBackDate').val(0);
				 $('#actualOdameterReading').val(0);
				 $('#creatingBackDateFuel').val(false);
				 $('#showBackDateMessage').addClass('hide');
				 
				 if(data.ResetFuelVehicle != undefined && data.ResetFuelVehicle == true){
					 $('#FuelSelectVehicle').select2("val", "");
					 $('#vehicle_group').val('');
					 $('#fuel_TripsheetNumber').val('');
				 }else{
					 	setVehicleOnChangeDate($('#FuelSelectVehicle').val());
				 }
			}
			document.getElementById('meter_attributes').checked = false;
			$('#fuel_meter_old').attr('readonly', true);
			
		},
		error: function (e) {
			hideLayer();
			$("#btnSubmit").show();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function validateFuelEntries(){
	
	if(Number($("#FuelSelectVehicle").val()) == 0){
		$("#FuelSelectVehicle").select2('focus');
		showMessage('errors','Please select Vehicle !');
		return false;
	}
	if($('#showTripSheetDropDown').val() == 'true' || $('#showTripSheetDropDown').val() == true){
		if($('#validateTripSheetNumber').val() == 'true' || $('#validateTripSheetNumber').val() == true){
			if(Number($("#tripsheetNumberSelect").val()) == 0){
				$("#tripsheetNumberSelect").focus();
				showMessage('errors','Please select TripSheet !');
				return false;
			}
		}
	}
	
	if(($("#pcName").val() == "") || (Number($("#pcName").val()) < 0)){
		$("#pcName").focus();
		showMessage('errors','Please select TripSheet Id !');
		return false;
	}
	
	if($("#fuelDate").val() == ""){
		$("#fuelDate").focus();
		showMessage('errors','Please select Date !');
		return false;
	}
	if($("#fuelTime").val()  != null && $("#fuelTime").val().trim() == ''){
		if($("#allowFuelEntryTime").val() == true || $("#allowFuelEntryTime").val() == 'true'){
			$("#fuelTime").focus();
			showMessage('errors','Please select Time !');
			return false;
		}
	}
	
	if($("#fuel_meter").val() == ""){
		$("#fuel_meter").focus();
		showMessage('errors','Please select Odometer !');
		return false;
	}
	
	if($("#fuel_meter_old").val() == null || $("#fuel_meter_old").val() == "" || Number($("#fuel_meter_old").val()) <= 0 ){
		$("#fuel_meter_old").focus();
		showMessage('errors','Please Enter Old Fuel Odometer !');
		return false;
	}
	
	if(Number($("#fuel_meter").val()) < Number($("#fuel_meter_old").val()) && !document.getElementById('meter_attributes').checked){
		$("#fuel_meter").focus();
		showMessage('errors','Old Odometer cannot be greater then current odometer !');
		return false;
	}
	
	if($('#validateVehicleKMPL').val() == 'true' || $('#validateVehicleKMPL').val() == true){
		if( Number($("#expectedMileageFrom").val()) == 0 && Number($("#expectedMileageFrom").val()) == 0){
			$('#addKMPL').modal('show');
			return false;
		}
	}
	
	if($("#fuel_type").val() == null){
		$("#fuel_type").select2('focus');
		showMessage('errors','Please select Fuel Type !');
		return false;
	}
	
	if(Number($("#selectVendor").val()) == 0){
		$("#selectVendor").select2('focus');
		showMessage('errors','Please select Vendor !');
		return false;
	}
	
	if(Number($("#fuel_liters").val()) <= 0){
		$("#fuel_liters").focus();
		showMessage('errors','Please enter Fuel Liter !');
		return false;
	}
	if(Number($("#ownPetrolPump").val()) == 1 && Number($("#maxStockQuantity").val()) <= 0){
		showMessage('errors','You Have No Stock For Fuel Entry !');
		return false;
	}
	
//	if(Number($("#ownPetrolPump").val()) == 1 && Number($("#maxStockQuantity").val()) < Number($("#fuel_liters").val())){
//		
//		//showMessage('errors','You can not enter fuel greater then '+Number($("#maxStockQuantity").val()).toFixed(2)+' Liter !');
//		//return false;
//	}
	
	if($('#allowToAddFuelAmount').val() == 'true' || $('#allowToAddFuelAmount').val() == true){
		if(Number($("#fuelAmount").val()) <= 0){
			$("#fuelAmount").focus();
			showMessage('errors','Please enter Fuel Amount !');
			return false;
		}
	}
	
	if($('#ownPetrolPump').val() == undefined || $('#ownPetrolPump').val() === '0' || $("#getStockFromInventoryConfig").val() == false || $("#getStockFromInventoryConfig").val() === 'false')
	{
		if($("#fuel_price").val() == ""){
			$("#fuel_price").focus();
			showMessage('errors','Please select Price !');
			return false;
		}
	}
	
	if($("#fuel_reference").val() == ""){
		var hasHide 	    	  = $("#grpfuelReference").hasClass("hide");
		var validateFuelReference = $('#validateReference').val();
		if(!hasHide && (validateFuelReference == 'true' || validateFuelReference == true)){
			$("#fuel_reference").focus();
			showMessage('errors','Please select Fuel Reference !');
			return false;
		}
	}
	
	if($("#tallyCompanyId").val() != undefined && $("#tallyCompanyId").val() != null){
		
		if(Number($("#tallyCompanyId").val()) <= 0){
			//$("#tallyCompanyId").focus();
			showMessage('errors','Please select Tally Company !');
			return false;
		}
	}
	
	if($("#validateDriver1").val() == 'true' || $("#validateDriver1").val() == true){
		var hasDriver = $("#DriverFuel").val();
		if(Number(hasDriver) <= 0){
			$("#DriverFuel").focus();
			showMessage('errors','Please select Driver !');
			return false;
		}
	}
	
	return true;
}

function resetAllFeilds(){
	location.reload();
}

function setVehicleOnChangeDate(vid){
	showLayer();
	$.getJSON("getFuelVehicleOdoMerete.in", {
		FuelvehicleID: vid,
		ajax: "true"
	}, function(e) {
		var n = "",
        t = "",
        l = "";
    n = e.vehicle_Odometer, l = e.vehicle_Group, document.getElementById("fuel_meter").placeholder = n, document.getElementById("vehicle_group").value = l;
    $('#vehicleGroupId').val(e.vehicleGroupId);
    $('#vehicle_Odometer').val(e.vehicle_Odometer);
    $('#fuelTankCapacity').val(e.vehicle_FuelTank1);
    
    if(e.vehicle_ExpectedOdameter != undefined && e.vehicle_ExpectedOdameter > 0){
    	$('#maxOdometer').val(e.vehicle_Odometer + e.vehicle_ExpectedOdameter);
    	$('#vehicle_ExpectedOdameter').val(e.vehicle_ExpectedOdameter);
    }else{
    	$('#maxOdometer').val(e.vehicle_Odometer + 2500);
    	$('#vehicle_ExpectedOdameter').val(2500);
    }
    
    if(e.gpsOdameter != undefined && e.gpsOdameter > 0){
		$('#gpsOdometer').val(e.gpsOdameter);
	}
	if(e.vehicle_Odometer != undefined && e.vehicle_Odometer > 0){	
		$('#fuel_meter').val(e.vehicle_Odometer);
		$('#actualOdameterReading').val(e.vehicle_Odometer);
	}
	if(e.lastFuelOdometer != undefined && e.lastFuelOdometer > 0){	
		$('#fuel_meter_old').val(e.lastFuelOdometer);
		$('#minOdometer').val(e.lastFuelOdometer);
	}else{
		$('#openKmRow').show();
		$('#fuel_meter_old').attr("readonly", false); 
		$('#fuel_meter_old').val(e.vehicle_Odometer);
		$('#minOdometer').val(0);
	}
    if(!showReferenceCol) {
    	$('#fuel_reference').val(e.vehicle_Odometer);
    }
    
    var kmplFrom = e.vehicle_ExpectedMileage;
    var kmplTo = e.vehicle_ExpectedMileage_to;
    if(kmplFrom == 0 && kmplTo == 0){
    	$('#vehicleKmplId').val(vid);
    	$('#vehicleKmplName').val(e.vehicle_registration);
    	$('#validateVehicleKMPL').val(true);
    	//$('#kmplDetails').show();
    	$('#addKMPL').modal('show');
    }
    
   /****set fuel type start*/ 
    $("#fuel_type").select2("val", "");
    
    var fuelTypeName 	= new Array;
    var fuelTypeId 		= new Array;
   
    fuelTypeName 		= e.vehicle_Fuel.split(",");
    fuelTypeId 			= e.vehicleFuelId.split(",");
    
    
    for (var i = fuelTypeId.length, o = 0; i > o; o++) t += '<option value="' + fuelTypeId[o] + '" >' + fuelTypeName[o] + "</option>";
    t += "</option>", $("#fuel_type").html(t)
    
    var FuelContain = fuelTypeName.includes("DIESEL");
   
    if(FuelContain){
    	$('#fuel_type').select2('data', {
    		id : 1,
    		text : 'DIESEL'
    	});
    }
   
    /****set fuel type End*/   
	hideLayer();

	})
    
}

var dropDownLength = 0;

function setTripSheetData(){
	
	$.getJSON("getRecentTripListByVid.in", {
		vid: $('#FuelSelectVehicle').val(),
		ajax: "true"
	}, function(a) {
		dropDownLength = a.length;
        for(var b='<option value="0">Select TripSheet</option>', c=a.length, d=0;
        c>d;
        d++)b+='<option value="'+a[d].tripSheetID+'"> TS- '+a[d].tripSheetNumber+' :'+a[d].tripOpenDate+' to '+a[d].closetripDate+"</option>";
        b+="</option>", $("#tripsheetNumberSelect").html(b)
	})
}

function validateFuelPrice(){

	if($('#ownPetrolPump').val() == undefined || $('#ownPetrolPump').val() === '0' || $("#getStockFromInventoryConfig").val() == false || $("#getStockFromInventoryConfig").val() === 'false'){
		if($('#fuel_price').val() <= 0){
			$("#fuel_price").val('');
			showMessage('info','Fuel Price Should Not Be 0')
			return false;
		}else{
			return true;
		}

	}
	return true;
}

function setFuelTankSuggestion(currentLiter){
	var fuelTankCapacity = $('#fuelTankCapacity').val();
	var previousFuelEntryCapacity = $('#previousFuelEntryCapacity').val();
	
	if(fuelTankCapacity != 0){
		
		var exception 		 = 0;
		var compareCapacity	 = 0;
		
		exception = (5 * fuelTankCapacity)/100;
		compareCapacity = fuelTankCapacity - exception;
		
		if(currentLiter < compareCapacity){
			$('#partialFuelSuggestion').show();
			$('#fullFuelSuggestion').hide();
		}else {
			$('#fullFuelSuggestion').show();
			$('#partialFuelSuggestion').hide();
		}
		
	}
}

function changeOpenOdometer(){
	if(document.getElementById('meter_attributes').checked){
		$('#fuel_meter_old').attr('readonly', false);
		$('#prefuel_meter_old').val($('#fuel_meter_old').val());
		$('#fuel_meter_old').val($('#vehicle_Odometer').val());
	}else{

		$('#fuel_meter_old').attr('readonly', true);
		$('#fuel_meter_old').val($('#prefuel_meter_old').val());
	
	}
}
function calculateFuelAmount(){
		validateMaxFuelPrice();
		var fuelQty = 0;
		var fuelRate = 0;
		var fuelAmount = 0;
		
		fuelQty 	= Number($('#fuel_liters').val());
		fuelRate 	= Number($('#fuel_price').val());
		fuelAmount 	= fuelRate * fuelQty;
		if($("#roundFigureAmount").val() == true || $("#roundFigureAmount").val() == 'true' ){
			$("#fuelAmount").val(Math.round(fuelAmount));
		}else{
			$('#fuelAmount').val(fuelAmount.toFixed(2));
		}
		}
function calculateFuelRate(){
	var fuelQty 			=  Number($('#fuel_liters').val());
	var maxFuelCapacity 	=  Number($('#maxFuelCapacity').val());
	
	
	
	if(fuelQty > maxFuelCapacity){
		showMessage('info','Fuel Liter Can Not Be Greater Than '+maxFuelCapacity+' ')
		$('#fuel_liters').val(0)
		hideLayer();
		return false;
	}
		var fuelAmount = 0;
			if(Number($('#ownPetrolPump').val()) == 0){
				fuelAmount 	= Number($('#fuelAmount').val());
				if(fuelAmount > 0 && fuelQty > 0){
					$('#fuel_price').val((fuelAmount/fuelQty).toFixed(2));
				}
				if($("#roundFigureAmount").val() == true || $("#roundFigureAmount").val() == 'true' ){
					$("#fuelAmount").val(Math.round(fuelAmount));
				}
			}else{
//				if($("#getStockFromInventoryConfig").val() == false || $("#getStockFromInventoryConfig").val() == 'false'){
//					//$('#fuel_price').val($('#avgPrice').val());
//				}
				fuelAmount = fuelQty * Number($('#fuel_price').val());
				$('#fuelAmount').val(fuelAmount.toFixed(2));
			}
}
function getPetrolPumpFuelStockDetails(){
	showLayer();
	var jsonObject								= new Object();
	jsonObject["petrolPumpId"]					= $("#selectVendor").val();
	jsonObject["oldPetrolPumpId"]				= $("#oldVendorId").val()
	jsonObject["companyId"]						= $("#companyId").val();
	jsonObject["getStockFromInventoryConfig"]	= $("#getStockFromInventoryConfig").val();
	jsonObject["isEditFuelEntry"]				= $("#isEditFuelEntry").val();
	jsonObject["fuelInvoiceId"]					= $("#oldFuelInvoiceId").val();
	$.ajax({
		url: "getFuelBalanceStockDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			renderFuelStockDetails(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
}

function renderFuelStockDetails(data){
		if(($("#getStockFromInventoryConfig").val() == true || $("#getStockFromInventoryConfig").val() == 'true' )){
			if(data.stockDetails == undefined || data.stockDetails <= 0){
				showMessage('errors', 'No stock available at this petrol pump please add stock before continue !');
				$('#stock').text('');
			}else{
				$('#stock').text('Fuel Stock : '+data.stockDetails.toFixed(2));
				$('#fuel_price').attr('readonly', true);
				$('#maxStockQuantity').val(data.stockDetails);
			}
		}
		else{
			if(data.stockDetails == undefined || data.stockDetails.stockQuantity == 0){
				showMessage('errors', 'No stock available at this petrol pump please add stock before continue !');
				$('#stock').text('');
			}else{
				$('#stock').text('Fuel Stock : '+data.stockDetails.stockQuantity.toFixed(2)+' ltr & Price : '+(data.stockDetails.avgFuelRate).toFixed(2));
				$('#maxStockQuantity').val(data.stockDetails.stockQuantity);
				$('#avgPrice').val(data.stockDetails.avgFuelRate.toFixed(2));
				$('#fuel_price').val((Number(data.stockDetails.avgFuelRate)).toFixed(2));
				$('#fuel_price').attr('readonly', true);
			}
	}
	hideLayer();
}

function setDriverName(){
	showLayer();
	var jsonObject					= new Object();
	
	jsonObject["vid"]				= $('#FuelSelectVehicle').val();
	
	$.ajax({
		url: "getDefaultDriverForVehicle",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data != null && data != undefined){
				
			var driverDetails =	data.defaultDriverForVehicle;
				if(driverDetails != undefined){
					$('#defaultDriverIdForVehicle').val(driverDetails.driver_id);
					$('#defaultDriverNameForVehicle').val(driverDetails.driver_empnumber+"-"+driverDetails.driver_firstname+" "+driverDetails.driver_fathername+""+driverDetails.driver_Lastname)
				}
			}
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
	setTimeout(function(){
		$('#DriverFuel').select2('data', {
			id : $('#defaultDriverIdForVehicle').val(),
			text : $('#defaultDriverNameForVehicle').val()
		});
	}, 500);
	
}

function validateMaxFuelLiters(){
	if(Number($('#fuel_liters').val()) > Number($('#maxFuelLiters').val()) ){
		showMessage('errors', 'Please check Fuel Liter , You have entered above the allowed limit !');
		return false;
	}
	return true;
}

function validateMaxFuelPrice(){
	if(Number($('#fuel_price').val()) > Number($('#maxFuelPrice').val()) ){
		
		if(Number($('#previousFuelAmount').val()) > 0){
			$('#fuel_price').val(Number($('#previousFuelPrice').val()));
		}else
			$('#fuel_price').val('');
		
		$('#fuel_price').focus();
		showMessage('errors', 'Please check Fuel Price , You have entered above the allowed limit !');
		return false;
	}
	return true;
}

function addShortExcessQuantity(invoiceId){
	$("#selectVendor").select2("val", "");
	window.open('showFuelInvoice?Id='+invoiceId+'', '_blank');
//	$("#stockInvoiceModal").modal('show');
}

function updateFuelInvoiceStock(){
	showLayer();
	var jsonObject							= new Object();
	var stockTypeId							= $("input[name='stockStatusName']:checked").val();
	var updatedStock						= Number($("#shrotExcessQuantity").val());
	var balanceStock						= Number($("#balanceStock").val());
	
	jsonObject["companyId"] 				= $('#companyId').val();
	jsonObject["userId"] 					= $('#userId').val();
	jsonObject["fuelInvoiceId"]				= $("#fuelInvoiceId").val();
	jsonObject["stockTypeId"]				= stockTypeId;
	jsonObject["updatedStock"]				= updatedStock;
	jsonObject["remark"]					= $("#remark").val();
	jsonObject["petrolPumpId"]				=  $("#selectVendor").val();
	
	if($("#shrotExcessQuantity").val() <= 0){
		showMessage('info','Please Enter Fuel Liter')
		return false;
	}
	
	if(stockTypeId == 1 && (updatedStock > balanceStock )){
		showMessage('info','Short Quantity Can Not Be Greater Than balanceStock '+balanceStock+' ')
		hideLayer();	
		return false;
	}
	
	$.ajax({
		url: "updateShortExcessQuantity",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			location.reload();
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
}



