isFromFuelEdit = true;
var paidBybranch = false;
var elementShown=true;

$(document).ready(function() {
	showLayer();
	var jsonObject					= new Object();
	jsonObject["fuel_id"]		    = $('#fuel_id').val();
	jsonObject["getStockFromInventoryConfig"]		    = $('#getStockFromInventoryConfig').val();
	$.ajax({
		url: "getFuelDetailsById",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			if(data.alreadyExist != undefined && data.alreadyExist == true){
		  		showMessage('info','Fuel Invoice Already Exist')
				window.location.replace('Fuel/1.in');
			}else{
				setFuelEditData(data);
			}
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
});

function   setFuelEditData(data){
	$('#previousFuelAmount').val(data.fuel.fuel_amount);
	$('#fuelNumber').val(data.fuel.fuel_Number);
	$('#FuelSelectVehicle').select2('data', {
		id : data.fuel.vid,
		text : data.fuel.vehicle_registration
	});
	$('#vehicle_group').val(data.fuel.vehicle_group);
	var dateTimeArr  =  data.fuel.fuel_date.split(' ');
	$('#previousFuelDate').val(dateTimeArr[0]);
	$('#fuelDate').val(dateTimeArr[0]);
	if($("#twelveHourClock").val() == "true"){
		var Time = convertTime(data.fuel.fuelTime);
		$('#fuelTime').val(Time);
	}	
	else{
		$('#fuelTime').val(data.fuel.fuelTime);
	}
	$('#oldVendorId').val(data.fuel.vendor_id);
	$('#prefuel_meter_old').val(data.fuel.fuel_meter_old);
	$('#fuel_meter_old').val(data.fuel.fuel_meter_old);
	$('#fuel_meter').val(data.fuel.fuel_meter);
	$('#oldFuelInvoiceId').val(data.fuel.fuelInvoiceId);
	$('#fuelInvoiceId').val(data.fuel.fuelInvoiceId);
	
	$('#fuel_liters').val(data.fuel.fuel_liters);
	$('#fuel_price').val(data.fuel.fuel_price);
	$('#previousFuelPrice').val(data.fuel.fuel_price);
	$('#fuelAmount').val(data.fuel.fuel_amount);
	$('#tripStatusId').val(data.fuel.tripStutesId);
   	$('#tripClosingKM').val(data.fuel.tripClosingKM);
	
	
	if(data.fuel.vendor_id !=  null  && data.fuel.vendor_id >  0){
		$('#selectVendor').select2('data', {
			id : data.fuel.vendor_id,
			text : data.fuel.vendor_name
		});
		
		if(data.fuel.ownPetrolPump == 1){
			$('#ownPetrolPump').val(data.fuel.ownPetrolPump);
			$('#preOwnPetrolPump').val(data.fuel.ownPetrolPump);
			getPetrolPumpFuelStockDetailsInvoice();
		}
		if(data.fuel.autoVendor != undefined && data.fuel.autoVendor){
		$('#ShowOtherPayOption').hide();
		$('#PayModeOption').show();
		}else{
		$('#ShowOtherPayOption').show();
		$('#PayModeOption').hide();
		}
		
		if(data.fuel.paymentTypeId == 2){
			$('#paymentTypeCreditId').attr('checked',  true);
			$('#debitlebel').addClass('btn btn-default btn-off btn-lg');
			$('#creditlebel').addClass('btn btn-default btn-on btn-lg active');
		}else{
			$('#paymentTypeId').attr('checked',  true);
			$('#creditlebel').addClass('btn btn-default btn-on btn-lg');
			$('#debitlebel').addClass('btn btn-default btn-off btn-lg active');
		
		
		}
	}else{
		$('#ShowOtherPayOption').hide();
		$('#PayModeOption').show();
	}
	
	
	
	
	
	$('#fuel_reference').val(data.fuel.fuel_reference);
	$('#fuel_comments').val(data.fuel.fuel_comments);
	
	if(data.fuel.driver_id !=  null  && data.fuel.driver_id >  0){
		$('#DriverFuel').select2('data', {
			id : data.fuel.driver_id,
			text : data.fuel.driver_name+" "+ data.fuel.firstDriverLastName+" - "+ data.fuel.firstDriverFatherName
		});
	}
	if(data.fuel.secDriverID !=  null  && data.fuel.secDriverID >  0){
	$('#Driver2Fuel').select2('data', {
		id : data.fuel.secDriverID,
		text : data.fuel.fuelSecDriverName +" "+ data.fuel.secDriverLastName +" - "+ data.fuel.secDriverFatherName
	});
	}
	if(data.fuel.cleanerID !=  null  && data.fuel.cleanerID >  0){
		$('#VehicleTOCleanerFuel').select2('data', {
			id : data.fuel.cleanerID,
			text : data.fuel.fuelCleanerName
		});
	}
	
	if(data.fuel.routeID !=  null  && data.fuel.routeID >  0){
		$('#FuelRouteList').select2('data', {
			id : data.fuel.routeID,
			text : data.fuel.fuelRouteName
		});
	}
	if(data.fuel.tallyCompanyId !=  null  && data.fuel.tallyCompanyId >  0){
		$('#tallyCompanyId').select2('data', {
			id : data.fuel.tallyCompanyId,
			text : data.fuel.tallyCompanyName
		});
	}
	if(data.fuel.gpsOdometer != null && data.fuel.gpsOdometer  >  0){
		
		$('#gpsOdometer').val(data.fuel.gpsOdometer);
	}
	
	if(data.fuel.fuel_meter_attributes == 1){
		$('#meter_attributes').attr('checked',  true);
	}
	if(data.fuel.fuel_tank == 1){
		$('#fuel_tank_partial').attr('checked',  true);
		$('#fullTankLabel').addClass('btn btn-default btn-off btn-lg');
		$('#partialTankLabel').addClass('btn btn-default btn-on btn-lg active');
	}else{
		$('#fuel_tank').attr('checked',  true);
		$('#fullTankLabel').addClass('btn btn-default btn-on btn-lg active');
		$('#partialTankLabel').addClass('btn btn-default btn-off btn-lg');
	}
	$('#fuel_type').select2('data', {
		id : data.fuel.fuelTypeId,
		text : data.fuel.fuel_type
	});
	
	$('#fuel_type').html('<option value= '+data.fuel.fuelTypeId+'>'+data.fuel.fuel_type+'</option>');
	$('#fuel_meter_old').val(data.fuel.fuel_meter_old);
	$('#fuel_meter').val(data.fuel.fuel_meter);
	
	if(data.fuel.fuel_TripsheetID != null && data.fuel.fuel_TripsheetID > 0){
		$('#tripsheetNumberSelect').html('<option value= '+data.fuel.fuel_TripsheetID+'>'+data.fuel.fuel_TripsheetNumber+'</option>');
	}
	if(data.fuel,vehicle_ExpectedOdameter != null && data.fuel.vehicle_ExpectedOdameter > 0){
		$('#vehicle_ExpectedOdameter').val(data.fuel.vehicle_ExpectedOdameter);
	}else{
		$('#vehicle_ExpectedOdameter').val(2500);
	}
	if(data.fuel.fuel_meter - data.fuel.vehicle_ExpectedOdameter  >  0){
		$('#minOdometer').val(data.fuel.fuel_meter - data.fuel.vehicle_ExpectedOdameter);
		$('#maxOdometer').val(data.fuel.fuel_meter + data.fuel.vehicle_ExpectedOdameter);
	}else{
		$('#minOdometer').val(0);
		$('#maxOdometer').val(data.fuel.fuel_meter + data.fuel.vehicle_ExpectedOdameter);
	}
	
	getGPSAndActiveTripData(true);
	
}
function convertTime(time){
	const parsedTime = time.split(":");
	const hours = parseInt(parsedTime[0], 10);
	const minutes = parsedTime[1];
	let period;
	if (hours >= 12) {
	    period = "PM";
	} else {
	    period = "AM";
	}
	const hours12 = ((hours + 11) % 12 + 1).toString().padStart(2, "0");
	const formattedTime = `${hours12}:${minutes}${period}`;
	return formattedTime;
}

function updateFuelEntry(){
	
	if($('#maxOdometerAsTripCloseKM').val() == "true" || $('#maxOdometerAsTripCloseKM').val() == true){
		if($('#tripStatusId').val() == 3){
			if($('#fuel_meter').val() > $('#tripClosingKM').val()){
				$('#fuel_meter').focus();
				showMessage('errors', 'Odometer must less than or equal to closing Trip KM !' + $('#tripClosingKM').val());
				$("#btnSubmit").show();
				return false;
			}
			
		}	
	}
	

	if(!validateMaxOdameter()){
		return false;
	}
	
	if(!validateFuelEntries()){
		return false;
	}
	
	if(!validateFuelPrice()){
		return false;
	}
	
	if(!validateMaxFuelPrice()){
		return false;
	}
	
	
	if(Number($("#ownPetrolPump").val()) == 1 && Number($("#maxStockQuantity").val()) < Number($("#fuel_liters").val())){
		showMessage('errors','You can not enter fuel greater then '+Number($("#maxStockQuantity").val()).toFixed(2)+' Liter !');
		return false;
	}
	
	var jsonObject					= new Object();
	jsonObject["fuel_id"]		    				= $('#fuel_id').val();
	jsonObject["FuelSelectVehicle"]					= $('#FuelSelectVehicle').val();
	jsonObject["vehicle_group"]						= $('#vehicle_group').val();
	jsonObject["fuel_TripsheetNumber"]				= $('#fuel_TripsheetNumber').val();
	jsonObject["fuelDate"]							= $('#fuelDate').val();
	if($("#twelveHourClock").val() == "true"){
		var Time = ChangeFuelTime();
		jsonObject["fuelTime"] 				=  Time
	}else{
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
	jsonObject["previousFuelAmount"]				= $('#previousFuelAmount').val();
	jsonObject["previousFuelDate"]					= $('#previousFuelDate').val();
	jsonObject["fuelNumber"]						= $('#fuelNumber').val();
	jsonObject["fromFuelEdit"]						= true;
	jsonObject["vendorName"]						= $('#vendorName').val();
	jsonObject["fuelAmount"]						= $('#fuelAmount').val();
	jsonObject["ownPetrolPump"]						= $('#ownPetrolPump').val();
	jsonObject["preOwnPetrolPump"]					= $('#preOwnPetrolPump').val();
	jsonObject["oldVendorId"]						= $('#oldVendorId').val();
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
	jsonObject["oldFuelInvoiceId"]				= $('#oldFuelInvoiceId').val();
	jsonObject["currentFuelInvoiceId"]			= $('#fuelInvoiceId').val();
	jsonObject["oldVendorId"]					= $('#oldVendorId').val();
	
	if($("#paidByOptionInAddFuleEntry").val() == "true" || $("#paidByOptionInAddFuleEntry").val() == true){
		if(elementShown==true && document.querySelector('select[name="paidBy"]') != null && document.querySelector('select[name="paidBy"]').value == '2')
		{
			paidBybranch = true;
		}
		else
		{
			paidBybranch = false;
		}
	}
	jsonObject["paidByBranch"] = paidBybranch;
	
	if (allowBankPaymentDetails) {
		prepareObject(jsonObject)
	}
	
	showLayer();

	$.ajax({
		url: "updateFuelEntries",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if ((data.noStock != undefined && data.noStock) || (data.insufficientStock != undefined && data.insufficientStock)){
				showMessage('info', 'Cannot Save Fuel Due to insufficient Stock !');
				$('#accessToken').val(data.accessToken);
				hideLayer();
				$("#btnSubmit").show();
				return false;
			}else if(data.paymentDone != undefined){
				$('#accessToken').val(data.accessToken);
				showMessage('info','Cannot update Fuel as payment initiated !');
				hideLayer();
			}else if(data.tripAcClosed != undefined){
				$('#accessToken').val(data.accessToken);
				showMessage('info','Cannot update Fuel as TripSheet A/C is closed !');
				hideLayer();
			}else{
				showMessage('success','Fuel  Data Updated Successfully !');
				location.replace('Fuel/1.in');
			}
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});

}

function getPetrolPumpFuelStockDetailsEdit(){
	showLayer();
	var jsonObject								= new Object();
	jsonObject["petrolPumpId"]					= $("#selectVendor").val();
	jsonObject["oldPetrolPumpId"]				= $("#oldVendorId").val()
	jsonObject["companyId"]						= $("#companyId").val();
	jsonObject["getStockFromInventoryConfig"]	= $("#getStockFromInventoryConfig").val();
	jsonObject["isEditFuelEntry"]				= $("#isEditFuelEntry").val();
	jsonObject["fuelInvoiceId"]					= $("#oldFuelInvoiceId").val();
	$.ajax({
		url: "getFuelStockDetailsEdit",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			renderFuelStockDetailEdit(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
}

function renderFuelStockDetailEdit(data){
	if(data.stockDetails == undefined){
		showMessage('errors', 'No stock available at this petrol pump please add stock before continue !');
		$('#stock').text('');
	}else{
		if(($("#getStockFromInventoryConfig").val() == true || $("#getStockFromInventoryConfig").val() == 'true' )){
			$("#fuelInvoiceId").val(data.stockDetails.fuelInvoiceId);
			$('#fuel_price').val((Number(data.stockDetails.rate)).toFixed(2));
			$('#fuel_price').attr('readonly', true);
			$('#stock').text('Fuel Stock : '+data.stockDetails.balanceStock.toFixed(2)+' ltr & Price : '+(data.stockDetails.rate).toFixed(2));
			$("#stock").append('<a herf="#" onclick="addShortExcessQuantity('+data.stockDetails.fuelInvoiceId+')"> Update Stock </a>')
			$('#balanceStock').val(data.stockDetails.balanceStock);
			$('#maxStockQuantity').val(data.stockDetails.balanceStock);
		}
		else{
			$('#stock').text('Fuel Stock : '+data.stockDetails.stockQuantity.toFixed(2)+' ltr & Price : '+(data.stockDetails.avgFuelRate).toFixed(2));
			$('#maxStockQuantity').val(data.stockDetails.stockQuantity);
			$('#avgPrice').val(data.stockDetails.avgFuelRate);
			$('#fuel_price').val((Number(data.stockDetails.avgFuelRate)).toFixed(2));
			$('#fuel_price').attr('readonly', true);
			
		}
	}
	hideLayer();
}

function getPetrolPumpFuelStockDetailsInvoice(){
	showLayer();
	var jsonObject								= new Object();
	jsonObject["petrolPumpId"]					= $("#selectVendor").val();
	jsonObject["oldPetrolPumpId"]				= $("#oldVendorId").val()
	jsonObject["companyId"]						= $("#companyId").val();
	jsonObject["getStockFromInventoryConfig"]	= $("#getStockFromInventoryConfig").val();
	jsonObject["isEditFuelEntry"]				= $("#isEditFuelEntry").val();
	jsonObject["fuelInvoiceId"]					= $("#oldFuelInvoiceId").val();
	$.ajax({
		url: "getFuelStockDetail",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			renderFuelStockDetail(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
}

function renderFuelStockDetail(data){
	if(data.stockDetails == undefined ){
		showMessage('errors', 'No stock available at this petrol pump please add stock before continue !');
		$('#stock').text('');
	}else{
		if($("#getStockFromInventoryConfig").val() == true || $("#getStockFromInventoryConfig").val() == 'true' ){
			if(data.stockDetails.balanceStock == 0){
				showMessage('errors', 'No stock available in invoice !');
				$('#maxStockQuantity').val($('#fuel_liters').val());
				//$("#fuelInvoiceId").val(data.stockDetails.fuelInvoiceId);
			}else{
				var balanceStock = Number($("#fuel_liters").val())+Number(data.stockDetails.balanceStock);
				$("#fuelInvoiceId").val(data.stockDetails.fuelInvoiceId);
				$('#fuel_price').val((Number(data.stockDetails.rate)).toFixed(2));
				$('#fuel_price').attr('readonly', true);
				$('#stock').text('Fuel Stock : '+(balanceStock.toFixed(2))+' ltr & Price : '+(data.stockDetails.rate).toFixed(2));
				$('#balanceStock').val(balanceStock.toFixed(2));
				$('#maxStockQuantity').val(balanceStock.toFixed(2));
			}
		}
		else{
			if(data.stockDetails.stockQuantity == 0){
				showMessage('errors', 'No stock available at this petrol pump please add stock before continue !');
				$('#maxStockQuantity').val($('#fuel_liters').val());
			}else{
				var balanceStock = Number($("#fuel_liters").val())+Number(data.stockDetails.stockQuantity);
			$('#stock').text('Fuel Stock : '+balanceStock.toFixed(2)+' ltr & Price : '+(data.stockDetails.avgFuelRate).toFixed(2));
			$('#maxStockQuantity').val(balanceStock.toFixed(2));
			$('#avgPrice').val(data.stockDetails.avgFuelRate);
			$('#fuel_price').val((Number(data.stockDetails.avgFuelRate)).toFixed(2));
			}
		}
	}
	hideLayer();
}

