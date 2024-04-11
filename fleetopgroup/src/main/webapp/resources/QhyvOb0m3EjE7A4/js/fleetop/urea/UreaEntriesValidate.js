function validateMaxOdameter(){
	
	if($('#meter_attributes').prop('checked')){
	//	 $('#fuel_meter_old').removeAttr('readonly');
		 $('#fuel_meter_old').attr('readonly', false);
		return true;
	}else{
		$('#fuel_meter_old').attr('readonly', true);
	
		var minOdometer 		= Number($('#minOdometer').val());
		var maxOdometer			= Number($('#maxOdometer').val());
		var ureaOdometer  		= Number($('#fuel_meter').val());
		var ureaOldOdometer  	= Number($('#fuel_meter_old').val());
		
		if(ureaOdometer < minOdometer || ureaOdometer > maxOdometer){
			showMessage('errors', 'Please Enter Odometer In Range Of : '+minOdometer+' - '+maxOdometer);
		//	$('#fuel_meter').focus();
			return false;
		}
		
		if(ureaOldOdometer > ureaOdometer){
			showMessage('errors', 'Urea Old Odometer Can Not Be Greater Than Current Odometer');
		//	$('#fuel_meter_old').focus();
			return false;
		}
	}
	return true;
	
}

function validateUreaLiters(){
	var ureaLiters	 = Number($('#ureaLiters').val());
	var maxQuantity  = Number($('#maxQuantity').val());
	if(maxQuantity < ureaLiters){
		showMessage('errors', 'You can not enter Urea  greter than '+maxQuantity+' Liters !');
		$('#ureaLiters').val(0);
		$('#ureaAmount').val(0);
		$('#ureaLiters').focus();
		return false;
	}
} 

function getPreNextUreaEntiresByDate(){
	showLayer();
	var jsonObject						= new Object();
	
	if($('#FuelSelectVehicle').val() != undefined && !isNaN($('#FuelSelectVehicle').val())){
		jsonObject["vid"] 					=  $('#FuelSelectVehicle').val();
	} else {
		jsonObject["vid"] 					=  $('#vid').val();
	}
	
	if(jsonObject.vid == undefined || Number(jsonObject.vid) == 0){
		showMessage('info', 'Please select vehicle first !');
		$('#fuelDate').val('');
		hideLayer();
		return false;
	}
	
	if($('#fuelDate').val() == undefined || $('#fuelDate').val() == ""){
		hideLayer();
		return false;
	}
	
	jsonObject["companyId"] 					=  $('#companyId').val();
	jsonObject["fuelDate"] 						=  $('#fuelDate').val();
	jsonObject["fuelTime"] 						=  $('#fuelTime').val();
	jsonObject["fuel_id"] 						=  $('#fuel_id').val();
	jsonObject["vehicle_ExpectedOdameter"] 		=  $('#vehicle_ExpectedOdameter').val();
	$('#isNextUreaEntry').val(false); 
	jsonObject["ureaEntriesId"] 				=  $('#ureaEntriesInvoiceId').val();
	
	$.ajax({
             url: "getPreNextUreaEntires",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 $('#fuel_meter_old').attr('readonly', true);
            	 if($('#tempFuel_meter').val() != undefined && $('#tempFuel_meter').val() > 0){
        			 $("#fuel_meter").attr("placeholder", Number($('#tempFuel_meter').val()));
        			 $("#fuel_meter_old").attr("placeholder",  Number($('#tempUreaOdometerOld').val()));
        		 }
            	 
            	 if($("#isEdit").val() != undefined &&($("#isEdit").val() == true || $("#isEdit").val() == 'true')){
            		 if(data.nextFuelDeatils != undefined && data.nextFuelDeatils != null){
            			 $('#isNextUreaEntry').val(true); 
            			 $('#nextUreaEntryId').val(data.nextFuelDeatils.ureaEntriesId)
            			 
            			 if(data.previousFuelDeatils != null){
            				 $('#minOdometer').val(data.previousFuelDeatils.ureaOdometer + 1);
            				 $('#maxOdometer').val(data.nextFuelDeatils.ureaOdometer - 1 );
            				 //	 $('#fuel_meter').val(data.previousFuelDeatils.ureaOdometer + 1);
            				 //	 $('#fuel_meter_old').val(data.previousFuelDeatils.ureaOdometer);
            				 $('#fuel_meter').val(data.nextFuelDeatils.lastUreaOdometer );
            				 $('#fuel_meter_old').val(data.previousFuelDeatils.ureaOdometer);
            				 $('#odometerRange').text('Odometer Range Between '+$('#minOdometer').val()+' And '+$('#maxOdometer').val()+'');
            				 hideLayer();
            				 
            			 }else{
            				// $('#fuel_meter_old').removeAttr('readonly');
            				 $('#fuel_meter_old').attr('readonly', false);
            				 $('#maxOdometer').val(data.nextFuelDeatils.ureaOdometer -1);
            				 if(data.nextFuelDeatils.lastUreaOdometer - data.nextFuelDeatils.vehicle_ExpectedOdameter >  0){
            					 $('#minOdometer').val(data.nextFuelDeatils.lastUreaOdometer - data.nextFuelDeatils.vehicle_ExpectedOdameter);
            				 }else{
            					 $('#minOdometer').val(0);
            				 }
            				// $('#fuel_meter').val($('#minOdometer').val());
            				// $('#fuel_meter_old').val($('#minOdometer').val());
            				 $('#odometerRange').text('Odometer Range Between '+$('#minOdometer').val()+' And '+$('#maxOdometer').val()+'');
            				 hideLayer();
            				 
            			 }
            			 
            		 }else if(data.previousFuelDeatils != undefined && data.previousFuelDeatils != null){
            			 
            			 if(data.previousFuelDeatils != null){
            				 
            				 $('#minOdometer').val(data.previousFuelDeatils.ureaOdometer + 1);
            				 $('#maxOdometer').val(data.previousFuelDeatils.ureaOdometer + data.previousFuelDeatils.vehicle_ExpectedOdameter);
            				 $('#fuel_meter').val(data.previousFuelDeatils.ureaOdometer + 1 );
            				 $('#fuel_meter_old').val(data.previousFuelDeatils.ureaOdometer);
            				 
            				 $('#odometerRange').text('Odometer Range Between '+$('#minOdometer').val()+' And '+$('#maxOdometer').val()+'');
            				 
            				 hideLayer();
            				 
            			 }
            			 hideLayer();
            		 } else{
            			 $('#odometerRange').text('Odometer Range Between '+$('#minOdometer').val()+' And '+$('#maxOdometer').val()+'');
            			 
            			// $('#fuel_meter_old').removeAttr('readonly');
            			 $('#fuel_meter_old').attr('readonly', false);
            			 hideLayer();
            		 }
            	 }else{
            		 
            		 if(data.nextFuelDeatils != undefined && data.nextFuelDeatils != null){
            			 $('#isNextUreaEntry').val(true); 
            			 $('#nextUreaEntryId').val(data.nextFuelDeatils.ureaEntriesId)
            			 
            			 if(data.previousFuelDeatils != null){
            				 $('#minOdometer').val(data.previousFuelDeatils.ureaOdometer + 1);
            				 $('#maxOdometer').val(data.nextFuelDeatils.ureaOdometer-1 );
            				 $('#fuel_meter').val(data.nextFuelDeatils.lastUreaOdometer );
            				 $('#fuel_meter_old').val(data.previousFuelDeatils.ureaOdometer);
            				 $('#odometerRange').text('Odometer Range Between '+$('#minOdometer').val()+' And '+$('#maxOdometer').val()+'');
            				 hideLayer();
            				 
            			 }else{
            				 // $('#fuel_meter_old').removeAttr('readonly');
            				 $('#fuel_meter_old').attr('readonly', false);
            				 $('#maxOdometer').val(data.nextFuelDeatils.ureaOdometer -1);
            				 if(data.nextFuelDeatils.lastUreaOdometer - data.nextFuelDeatils.vehicle_ExpectedOdameter >  0){
            					 $('#minOdometer').val(data.nextFuelDeatils.lastUreaOdometer - data.nextFuelDeatils.vehicle_ExpectedOdameter);
            				 }else{
            					 $('#minOdometer').val(0);
            				 }
            				 $('#fuel_meter').val($('#minOdometer').val());
            				 $('#fuel_meter_old').val($('#minOdometer').val());
            				 $('#odometerRange').text('Odometer Range Between '+$('#minOdometer').val()+' And '+$('#maxOdometer').val()+'');
            				 hideLayer();
            				 
            			 }
            			 
            		 }else if(data.previousFuelDeatils != undefined && data.previousFuelDeatils != null){
            			 
            			 if(data.previousFuelDeatils != null){
            				 
            				 console.log("data.previousFuelDeatils :::: ",data.previousFuelDeatils)
            				 $('#minOdometer').val(data.previousFuelDeatils.ureaOdometer + 1);
            				 $('#maxOdometer').val(data.previousFuelDeatils.ureaOdometer + data.previousFuelDeatils.vehicle_ExpectedOdameter);
            				 $('#fuel_meter').val(data.previousFuelDeatils.ureaOdometer + 1 );
            				 $('#fuel_meter_old').val(data.previousFuelDeatils.ureaOdometer);
            				 
            				 $('#odometerRange').text('Odometer Range Between '+$('#minOdometer').val()+' And '+$('#maxOdometer').val()+'');
            				 
            				 hideLayer();
            				 
            			 }
            			 hideLayer();
            		 } else{
            			 $('#odometerRange').text('Odometer Range Between '+$('#minOdometer').val()+' And '+$('#maxOdometer').val()+'');
            			 
            		//	 $('#fuel_meter_old').removeAttr('readonly');
            			 $('#fuel_meter_old').attr('readonly', false);
            			 hideLayer();
            		 }
            	 }
            	 
            	 
            	 $('#odometerRangeDiv').show();

            	 
        	 },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});
	
	
}


