//var showGpsData = false;
var isFromFuelEdit = false;
function clearTime(){
	if($("#allowFuelEntryTime").val() == true || $("#allowFuelEntryTime").val() == 'true' ){
		$('#fuelTime').val('');
	}else{
		$('#fuelTime').val("00:00");
		getGPSAndActiveTripData(false);
	}
}

function getGPSAndActiveTripData(isCalledOnLoad){
	
	 if($('#FuelSelectVehicle').val() == '') {
			$('#fuelTime').val('');
			$('#fuelDate').val('') ;
			showMessage('errors', 'Please Select Vehicle First.');
		}else if($('#fuelDate').val() == '') {
			showMessage('errors', 'Please Select Date First.');
		}else{
			getActiveTripsheet(isCalledOnLoad);
			//getVehicleGPSDataAtTime();
		}
	
}

function getActiveTripsheet(isCalledOnLoad){
	
	showLayer();
	var jsonObject						= new Object();
	
	if($('#FuelSelectVehicle').val() != undefined){
		jsonObject["vid"] 					=  $('#FuelSelectVehicle').val();
	} else {
		jsonObject["vid"] 					=  $('#vid').val();
	}
	jsonObject["companyId"] 			=  $('#companyId').val();
	jsonObject["fuelDate"] 				=  $('#fuelDate').val();
	
	if($("#twelveHourClock").val() == "true")
	{
		var Time = ChangeFuelTime();
		jsonObject["fuelTime"] 				=  Time
	}
	else
	{	
		jsonObject["fuelTime"] 				=  $('#fuelTime').val();
	}
	
	jsonObject["fuel_id"] 				=  $('#fuel_id').val();
	jsonObject["vehicle_ExpectedOdameter"] 		=  $('#vehicle_ExpectedOdameter').val();
	jsonObject["bindMinMaxOdometerOnTripSheet"]   =  $('#bindMinMaxOdometerOnTripSheet').val();
	
	
	$.ajax({
             url: "getActiveTripsheetDataAtTime",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 if($('#allowManualOdometerEntry').val() =='false' || $('#allowManualOdometerEntry').val() == false){
            		 $('#fuel_meter_old').attr('readonly', true);
            		 if(data.nextFuelDeatils != undefined && data.nextFuelDeatils != null){
            			 if(data.previousFuelDeatils != null){
            				 $('#previousFuelEntryCapacity').val(data.previousFuelDeatils.fuel_liters);
            				 $('#nextFuelIdOfBackDate').val(data.nextFuelDeatils.fuel_id);
            				 if(!isCalledOnLoad){
            					 $('#fuel_meter_old').val(data.previousFuelDeatils.lastFuelOdometer);
            					 $('#fuel_meter').val(data.nextFuelDeatils.fuelMeter);
            				 }
            				 $('#nextFuelMeterOfBackDate').val(data.nextFuelDeatils.fuelMeter);  // This field created only for Validation
            				 $('#creatingBackDateFuel').val(true);
            				 if(!isCalledOnLoad){
            					 $('#buttonMessageBackDate').html('You are Creating Back Date Fuel Entry. '
            							 +' Previous Fuel Entry Is <a href="showFuel?FID='+data.previousFuelDeatils.fuel_id+'" target="_blank"> FT - '+data.previousFuelDeatils.fuel_Number+' </a> '
            							 +' And Next Fuel Entry Is <a href="showFuel?FID='+data.nextFuelDeatils.fuel_id+'" target="_blank"> FT-'+data.nextFuelDeatils.fuel_Number+' </a>. '
            							 +'</br>Odameter Deatils Of Next Fuel Entry <a href="showFuel?FID='+data.nextFuelDeatils.fuel_id+'" target="_blank"> FT -'+data.nextFuelDeatils.fuel_Number+' </a> '  
            							 +'Will Be Updated Automatically With Respect To This Entry.');
            					 $('#showBackDateMessage').removeClass('hide');
            				 }
            				 
            				 $('#odometerRangeBackDate').html('Odometer Range : <span style="color:black;">'+(data.previousFuelDeatils.lastFuelOdometer + 1)  +' - '+(data.nextFuelDeatils.fuelMeter - 1)+'</span> with respect to fuel entries <a target="_blank" href="showFuel?FID='+data.previousFuelDeatils.fuel_id+'">FT - '+data.previousFuelDeatils.fuel_Number+'</a> and  <a target="_blank" href="showFuel?FID='+data.nextFuelDeatils.fuel_id+'">FT - '+data.nextFuelDeatils.fuel_Number+'</a>');
            				 $('#minOdometer').val(data.previousFuelDeatils.lastFuelOdometer + 1);
            				 $('#maxOdometer').val(data.nextFuelDeatils.fuelMeter - 1);
            				 $('#backDateRange').show();
            				 
            			 }else{
            				 $('#firstFuelEntry').val(true);
            				 $('#fuel_meter_old').attr("readonly", false); 
            				 $('#nextFuelIdOfBackDate').val(data.nextFuelDeatils.fuel_id);
            				 if(!isCalledOnLoad){
            					 $('#fuel_meter_old').val(data.nextFuelDeatils.fuelMeter);
            					 $('#fuel_meter').val(data.nextFuelDeatils.fuelMeter);
            				 }
            				 $('#nextFuelMeterOfBackDate').val(data.nextFuelDeatils.fuelMeter);  // This field created only for Validation
            				 $('#creatingBackDateFuel').val(true);
            				 if(!isCalledOnLoad){
            					 $('#buttonMessageBackDate').html('You are Creating Back Date Fuel Entry. '
            							 +' There is No Previous Entry '
            							 +' And Next Fuel Entry Is <a href="showFuel?FID='+data.nextFuelDeatils.fuel_id+'" target="_blank"> FT-'+data.nextFuelDeatils.fuel_Number+' </a>. '
            							 +'</br>Odameter Deatils Of Next Fuel Entry <a href="showFuel?FID='+data.nextFuelDeatils.fuel_id+'" target="_blank"> FT -'+data.nextFuelDeatils.fuel_Number+' </a> '  
            							 +'Will Be Updated Automatically With Respect To This Entry.');
            					 $('#showBackDateMessage').removeClass('hide');
            				 }
            				 $('#odometerRangeBackDate').html('Please Add Odometer below <span style="color:black">'+data.nextFuelDeatils.fuelMeter+'</span> Range with respect to fuel entry <a target="_blank" href="showFuel?FID='+data.nextFuelDeatils.fuel_id+'">FT - '+data.nextFuelDeatils.fuel_Number+'</a>');
            				 if(data.nextFuelDeatils.lastFuelOdometer - data.nextFuelDeatils.vehicle_ExpectedOdameter >  0){
            					 $('#minOdometer').val(data.nextFuelDeatils.lastFuelOdometer - data.nextFuelDeatils.vehicle_ExpectedOdameter);
            				 }else{
            					 $('#minOdometer').val(0);
            				 }
            				 $('#maxOdometer').val(data.nextFuelDeatils.fuelMeter -1);
            				 $('#backDateRange').show();
            			 }
            			 
            		 } else {
            			 if(data.previousFuelDeatils != null){
            				 if(!isCalledOnLoad){
            					 $('#fuel_meter_old').val(data.previousFuelDeatils.lastFuelOdometer);
            				 }
            				 $('#backDateRange').show();
            				 $('#odometerRangeBackDate').html('Please Enter Odometer Above <span style="color:black">'+data.previousFuelDeatils.lastFuelOdometer+'</span> with respect to fuel entry <a target="_blank" href="showFuel?FID='+data.previousFuelDeatils.fuel_id+'">FT - '+data.previousFuelDeatils.fuel_Number+'</a>');
            				 $('#minOdometer').val(data.previousFuelDeatils.lastFuelOdometer + 1);
            				 $('#maxOdometer').val(data.previousFuelDeatils.lastFuelOdometer + data.previousFuelDeatils.vehicle_ExpectedOdameter);
            			 }
            			 if($('#actualOdameterReading').val() != undefined && Number($('#actualOdameterReading').val()) > 0){
            				 if(!isCalledOnLoad){
            					 $('#fuel_meter').val($('#actualOdameterReading').val());  // This will reset back to current odometer when user changes time from backdate to non backdate
            				 }
            			 }
            			 $('#showBackDateMessage').addClass('hide');
            			 $('#creatingBackDateFuel').val(false);
            			 $('#nextFuelIdOfBackDate').val(0);
            			 $('#nextFuelMeterOfBackDate').val(0);
            			 $('#nextFuelEntryFound').val(false)
            			 
            		 }
            	 }
            	 if( Number($('#tripSheetId').val()) <= 0 || (isFromFuelEdit != undefined && isFromFuelEdit)){
            		 if(data.tripActive != undefined && data.tripActive != null){
            			 var activeTripsheet = "TS- "+ data.tripActive.tripSheetNumber +":" +data.tripActive.tripOpenDate+" to "+data.tripActive.closetripDate
            			 var UtID 			 = data.tripActive.tripSheetID;
            			 showMessage('success', 'Tripsheet TS - '+data.tripActive.tripSheetNumber+' Found.');
            			 
            			 if(dropDownLength == 0){
            				 $('#tripsheetNumberSelect')
            				 .append($("<option></option>")
            						 .attr("value",UtID)
            						 .text(activeTripsheet));
            			 }
            			 
            			 $("#tripsheetNumberSelect").val(UtID);
            			 
            			 var DID = data.tripActive.tripFristDriverID;
            			 var DText = data.tripActive.tripFristDriverName;
            			 var DFather = data.tripActive.tripFristDriverFatherName;
            			 var DLast = data.tripActive.tripFristDriverLastName;
            			 $('#DriverFuel').select2('data', {
            				 id : DID,
            				 text : DText+" "+DFather+" "+DLast
            			 });
            			 
            			 var RID = data.tripActive.routeID;
            			 var RText = data.tripActive.routeName;
            			 $('#FuelRouteList').select2('data', {
            				 id : RID,
            				 text : RText
            			 });
            			 
            		 } else {
            			 $("#tripsheetNumberSelect").val(0);
            			 if(!isCalledOnLoad){
            				 showMessage('info', 'No Active Tripsheet Found. Please Create Tripsheet.');
            			 }
            		 }
            	 }
            	 
            	 
            	 hideLayer();
            	 
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!');
            	 hideLayer();
             }
	});
	
	getVehicleGPSDataAtTime();
}

function getVehicleGPSDataAtTime(){
	
	var allowGPSIntegration	= $('#allowGPSIntegration').val();
	if(allowGPSIntegration == 'true' || allowGPSIntegration == true){
		//showGpsData = true;
		if($('#fuelDate').val() != '' && $('#fuelTime').val()){
			//showLayer();
			var jsonObject			= new Object();
			if($('#FuelSelectVehicle').val() != undefined && $('#FuelSelectVehicle').val() != ''){
				jsonObject["vehicleId"] 					=  $('#FuelSelectVehicle').val();
			}else{
				jsonObject["vehicleId"] 					=  $('#vid').val();
			}
			jsonObject["companyId"] 					=  $('#companyId').val();
			jsonObject["dispatchedByTime"] 				=  $('#fuelDate').val();
			
			if($("#twelveHourClock").val() == "true")
			{
				var Time = ChangeFuelTime();
				jsonObject["dispatchTime"] 				=  Time
			}
			else
			{
				jsonObject["dispatchTime"] 					=  $('#fuelTime').val();
			}
			jsonObject["fromFuel"] 						=  true;
			$.ajax({
		             url: "getVehicleGPSDataAtTime",
		             type: "POST",
		             dataType: 'json',
		             data: jsonObject,
		             success: function (data) {
		            	 if(data.isOdometerReading){
		            		 $('#gpsOdometer').val(data.VEHICLE_TOTAL_KM);
		            		 $('#fuel_meter').val('');
		            	     $('#fuel_meter').attr("placeholder", data.VEHICLE_TOTAL_KM);
		            		 $('#gpsWorking').val('true');
		            		 $('#gpsOdometerRow').show();
		            	 }else{
		            		 $('#grpfuelOdometer').show();
		            		 $('#gpsOdometerRow').hide();
		            		 $('#gpsOdometer').val('');
		            	 }
		            	 
		            	 //hideLayer();
		               
		             },
		             error: function (e) {
		            	 showMessage('errors', 'Some error occured!');
		            	 hideLayer();
		             }
			});
		}
		
	} 
}

function ChangeFuelTime()
{
	var Time1 = $('#fuelTime').val();
	var NewTime;
	if(Time1.includes('PM'))
	{
		let time12Hour = Time1.substring(0,5);
		let [hours, minutes] = time12Hour.split(':');
		if (hours !== '12') {
		  hours = String(Number(hours) + 12);
		}
		let time24Hour = hours.padStart(2, '0') + ':' + minutes;
		NewTime = time24Hour;
		return NewTime;
	}
	else
	{
		let time12Hour = Time1.substring(0,5);
		NewTime  = time12Hour
		return NewTime;
	}
}
