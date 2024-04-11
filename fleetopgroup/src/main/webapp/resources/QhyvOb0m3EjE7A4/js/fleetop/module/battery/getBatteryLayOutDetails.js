$(document).ready(
		function($) {
			showLayer();
				
				var jsonObject			= new Object();
				jsonObject["vid"] 	  	=  $('#vid').val();
				
				$.ajax({
			             url: "getBatteryLayoutDetails",
			             type: "POST",
			             dataType: 'json',
			             data: jsonObject,
			             success: function (data) {
			            	 setData(data);
			            	 hideLayer();
			             },
			             error: function (e) {
			            	 showMessage('errors', 'Some error occured!')
			            	 hideLayer();
			             }
				});
		});

function setData(data){
	if(data.batteryLayout != undefined && data.batteryLayout != null && data.batteryLayout.length > 0){
		 setVehicleData(data.vehicle);
		 setBatteryLayOut(data.batteryLayout);
		 addClickEvents(data.batteryLayout);
	}else{
		if(data.vid != undefined){
			window.location.replace("VehicleBatteryLayout?Id="+data.vid);
		}else{
			window.location.replace("NotFound.in");
		}
	}
}


function isNumberKey(e, t) {
	console.log(e);
	console.log(t)
    var n = e.which ? e.which : event.keyCode;
    if (n > 31 && (48 > n || n > 57) && 46 != n && 8 != charcode) return !1;
    var i = $(t).val().length,
        a = $(t).val().indexOf(".");
    return !(a > 0 && 46 == n) && !(a > 0 && i + 1 - a > 3)
}



function addClickEvents(batteryLayout){
	var vehicleId	= batteryLayout[0].vid;
	var batteryMounted = false;
	for(var i = 0 ; i < batteryLayout.length; i++){
		if(batteryLayout[i].batteryAsigned){
			$('#mountBattery' + batteryLayout[i].layoutPosition).html('DisMount');
			$('#battery' + batteryLayout[i].layoutPosition).css('background-color', '#555');
			$('#mountBattery' + batteryLayout[i].layoutPosition).click( disMountBattery(batteryLayout[i]));
			$('#batteryDetails'+  batteryLayout[i].layoutPosition).append('<a href="#" onclick="getBatteryAsignDetails('+batteryLayout[i].layoutId+', '+batteryLayout[i].batteryId+')">Get Details</a>');
			$('#batteryDetails'+  batteryLayout[i].layoutPosition).show();
			batteryMounted = true;
		}else{
			$('#mountBattery' + + batteryLayout[i].layoutPosition).html('Mount');
			$('#mountBattery' + batteryLayout[i].layoutPosition).click( mountBattery( batteryLayout[i]));
		}
	}
	$('#deleteLayout').append('<a class="btn btn-warning btn-sm" onclick="return deleteVehicleBatteryLayout('+vehicleId+', '+batteryMounted+');" href="#">Delete Batery Layout</a>');
	$('#deleteDiv').append('<a class="btn btn-link btn-sm" href="showVehicle.in?vid='+vehicleId+'">Cancel</a>');
}
function deleteVehicleBatteryLayout(vehicleId, batteryMounted){
	//deleteLayout RemoveAssignTyre
	if(batteryMounted){
		showMessage('info', 'Please Dismount All Battery First !');
		return false;
	}
	showLayer();
	var jsonObject			= new Object();
	
	jsonObject["vid"] 	  			=  vehicleId;
	
	$.ajax({
             url: "deleteVehicleBatteryLayout",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	window.location.replace("VehicleBatteryLayout?Id="+data.vid+"&deleteSuccess="+true);
            	 setBatteryAsignData(data);
            	 hideLayer();
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});
	

}

function getBatteryAsignDetails(layoutId, batteryId){
	showLayer();
	var jsonObject			= new Object();
	
	jsonObject["batteryId"] 	  			=  batteryId;
	jsonObject["layoutId"] 	  				=  layoutId;
	
	$.ajax({
             url: "getBatteryAsignmentDetails",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 setBatteryAsignData(data);
            	 hideLayer();
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});
	
}

function setBatteryAsignData(data){
	var position = "Battery - "+data.battery.layoutPosition;
	var TyreDetails = '<table class="table table-striped">'
			+ '											<tbody>' + '												<tr class="row">'
			+ '													<th class="key">Serial No:</th>'
			+ '													<td class="value"><a target="_blank" href="showBatteryInformation?Id='+data.battery.batteryId+'">'+data.battery.batterySerialNumber+'</a></td>'
			+ '												</tr>' 
			+ '												<tr class="row">'
			+ '													<th class="key">Position:</th>'
			+ '													<td class="value">'
			+ position
			+ '</td>'
			+ '												</tr>'
			+ '												<tr class="row">'
			+ '													<th class="key">Battery Capacity:</th>'
			+ '													<td class="value">'
			+ data.battery.batteryCapacity
			+ '</td>'
			+ '												</tr>'
			+ '												<tr class="row">'
			+ '													<th class="key">Brand:</th>'
			+ '													<td class="value"><a href="#">'+data.battery.manufacturerName+'</a></td>'
			+ '												</tr>'
			+ '												<tr class="row">'
			+ '													<th class="key">Model:</th>'
			+ '													<td class="value">'+data.battery.batteryType+'</td>'
			+ '												</tr>'
			+ '												<tr class="row">'
			+ '													<th class="key">Price:</th>'
			+ '													<td class="value">'+data.battery.batteryAmount+'</td>'
			+ '												</tr>'
			+ '												<tr class="row">'
			+ '													<th class="key">Mount Date:</th>'
			+ '													<td class="value">'+data.battery.asignedDate+'</td>'
			+ '												</tr>'
			+ '												<tr class="row">'
			+ '													<th class="key">Mounted Odometer:</th>'
			+ '													<td class="value">'+data.battery.openOdometer+'</td>'
			+ '												</tr>'
			+ '												<tr class="row">'
			+ '													<th class="key">Run KMs:</th>'
			+ '													<td class="value">'+data.battery.batteryUsesOdometer+'</td>'
			+ '												</tr>'
			+ '												<tr class="row">'
			+ '													<th class="key">Used Days:</th>'
			+ '													<td class="value">'+data.battery.usesNoOfTime+'</td>'
			+ '												</tr>'
			+ '											</tbody>'
			+ '										</table>';

	$("#BatteryMountSHoW").html(TyreDetails);
}

function mountBattery(battery){
	  return function(){
	    mountBatteryDetails(battery.layoutId, battery.layoutPosition, battery.vid, battery);
	    selectBatteryAjax();
	  }
}

function disMountBattery(battery){
	  return function(){
		  dismountBatteryDetails(battery.layoutId, battery.layoutPosition, battery.vid, battery);
	  }
}

function setVehicleData(vehicle){
	$('#vehicle').append('<a target="_blank" href="showVehicle.in?vid='+vehicle.vid+'">'+vehicle.vehicle_registration+'</a>');
	$('#status').html(vehicle.vehicle_Status);
	$('#CurrentOdometer').val(vehicle.vehicle_Odometer);
	$('#Odometer').html(vehicle.vehicle_Odometer);
	$('#Type').html(vehicle.vehicle_Type);
	$('#Location').html(vehicle.vehicle_Location);
	$('#Group').html(vehicle.vehicle_Group);
	$('#Route').html(vehicle.vehicle_RouteName);
}
function setBatteryLayOut(batteryLayout){
	
	var selected = batteryLayout[0].noOfBattery;
		 if(selected == 1){
	        	$('#Axle1').show();
	        }else if(selected == 2){
	        	$('#Axle1').show();
	        	$('#Axle2').show();
	        }else if(selected == 3){
	        	$('#Axle1').show();
	        	$('#Axle2').show();
	        	$('#Axle3').show();
	        	
	        }else if(selected == 4){
	        	$('#Axle1').show();
	        	$('#Axle2').show();
	        	$('#Axle3').show();
	        	$('#Axle4').show();
	        	
	        }else if(selected == 5){
	        	$('#Axle1').show();
	        	$('#Axle2').show();
	        	$('#Axle3').show();
	        	$('#Axle4').show();
	        	$('#Axle5').show();
	        	
	        	
	        }else if(selected == 6){
	        	$('#Axle1').show();
	        	$('#Axle2').show();
	        	$('#Axle3').show();
	        	$('#Axle4').show();
	        	$('#Axle5').show();
	        	$('#Axle6').show();
	        	
	        }else if(selected == 7){
	        	$('#Axle1').show();
	        	$('#Axle2').show();
	        	$('#Axle3').show();
	        	$('#Axle4').show();
	        	$('#Axle5').show();
	        	$('#Axle6').show();
	        	$('#Axle7').show();
	        }else if(selected == 8){
	        	$('#Axle1').show();
	        	$('#Axle2').show();
	        	$('#Axle3').show();
	        	$('#Axle4').show();
	        	$('#Axle5').show();
	        	$('#Axle6').show();
	        	$('#Axle7').show();
	        	$('#Axle8').show();
	        }else{
	        	$('#Axle1').hide();
	        	$('#Axle2').hide();
	        	$('#Axle3').hide();
	        	$('#Axle4').hide();
	        	$('#Axle5').hide();
	        	$('#Axle6').hide();
	        	$('#Axle7').hide();
	        	$('#Axle8').hide();
	        }
}
function mountBatteryDetails(layoutId, layoutPosition, vid, battery) {
	var position = "battery - "+layoutPosition;

	var MountTyre = '									<div class="form-horizontal ">'
			+ '													<fieldset>'
			+ '														<legend id="Identification">Battery Mount</legend>'
			+ '														<div class="row1">'
			+ '															<label class="L-size control-label"><span>Position'
			+ '																	:</span><abbr title="required">*</abbr></label>'
			+ '															<div class="I-size">'
			+ '																<input name="layoutPosition" type="text" class="form-text"'+
	'																	readonly="readonly" value="'+position+'"'+
	'																	required="required" />'
			+ '															</div>'
			+ '														</div>'
			+ '														<div class="row1">'
			+ '															<label class="L-size control-label"><span>Battery'
			+ '																	Capacity :</span><abbr title="required">*</abbr></label>'
			+ '															<div class="I-size">'
			+ '																<input  id="batteryCapacity" name="batteryCapacity" type="text" class="form-text"'+
			'																	readonly="readonly" value="'+battery.batteryCapacity+'"'+
			'																	required="required" /> <input id="batteryCapacityId" value = "'+battery.batteryCapacityId+'" type="hidden"/>'	
			+ '															</div>'
			+ '														</div>'
			+ '														<div class="row1">'
			+ '															<label class="L-size control-label"><span>Select'
			+ '																	Battery :</span><abbr title="required">*</abbr></label>'
			+ '															<div class="I-size">'
			+ '																<input type="hidden" id="selectBattery" name="selectBattery"'
			+ '																	style="width: 100%;"'
			+ '																	placeholder="Please Enter 2 or more Battery No" />'
			+ '															</div>'
			+ '														</div>'
			+ '														<div class="row1">'
			+ '															<label class="L-size control-label"><span>Odometer'
			+ '																	:</span><abbr title="required">*</abbr></label>'
			+ '															<div class="I-size">'
			+ '																<input id="vehicle_Odo" onkeypress="return isNumberKey(event,this);" name="TYRE_ODOMETER" type="number"  class="form-text"'+
	'																	value="'+battery.vehicle_Odometer+'" required="required"  />'
			+ '															</div>'
			+ '														</div>'
			+ '														<div class="row1">'
			+ '															<label class="L-size control-label">Mount Date <abbr'+
	'																title="required">*</abbr>'
			+ '															</label>'
			+ '															<div class="I-size">'
			+ '																<div class="input-group input-append date"'+
	'																	id="TyreMount">'
			+ '																	<input type="text" id="mountDate"  class="form-text"'+
	'																		name="MOUNTED_DATE"  readonly="readonly" required="required"'+
	'																		data-inputmask="\'alias\': \'dd-mm-yyyy\'" data-mask="" />'
			+ '																	<span class="input-group-addon add-on"> <span'+
	'																		class="fa fa-calendar"></span>'
			+ '																	</span>'
			+ '																</div>'
			+ '															</div>'
			+ '														</div>'
			+ '                                                   <div class="row1">'
			+ '															<label class="L-size control-label"><span>Mounted Remark'
			+ '																	:</span><abbr title="required">*</abbr></label>'
			+ '															<div class="I-size">'
			+ '																<input id="remark" name="remark" type="text" class="form-text"'+ 
	'																	required="required" />'
			+ '															</div>'
			+ '														</div>'
			+ '														<div class="row1">'
			+ '															<div class="L-size"></div>'
			+ '															<div class="I-size">'
			+ '																<div class="col-sm-offset-4 I-size">'
			+ '																	<input onclick="return mountBatteryDetailstoVehicle('+battery.layoutId+','+layoutPosition+');" class="btn btn-success" type="submit"'+
	'																		value="Assign  Battery"> <a'+
	'																		class="btn btn-default" href="#">Cancel</a>'
			+ '																</div>'
			+ '															</div>'
			+ '														</div>'
			+ '													<\/fieldset>'
			+ '												</div>'
			+ '												<script type="text/javascript">'
			+ '														$(document).ready(function(){$("#datemask").inputmask("dd-mm-yyyy",{placeholder:"dd-mm-yyyy"}),$("[data-mask]").inputmask()},$("#TyreMount").datepicker({autoclose:!0,todayHighlight:!0,format:"dd-mm-yyyy"}));'
			+ '            <\/script>';

	$("#BatteryMountSHoW").html(MountTyre);
}

	function selectBatteryAjax(){
			$("#selectBattery").select2({
	        minimumInputLength: 2,
	        minimumResultsForSearch: 10,
	        ajax: {
	            url: "getBatteryForMount.in?Action=FuncionarioSelect2",
	            dataType: "json",
	            type: "POST",
	            contentType: "application/json",
	            quietMillis: 50,
	            data: function(a) {
	                return {
	                    term: a,
	                    capacityId : $('#batteryCapacityId').val()
	                }
	            },
	            results: function(a) {
	                return {
	                    results: $.map(a, function(a) {
	                        return {
	                            text: a.batterySerialNumber,
	                            slug: a.slug,
	                            id: a.batteryId
	                        }
	                    })
	                }
	            }
	        }
	    })
	}		
function mountBatteryDetailstoVehicle(layoutId, layoutPosition){
	
	if(Number($('#selectBattery').val()) <= 0){
		showMessage('info','Please select Battery !');
		$("#selectBattery").select2('focus');
		return false;
	}
	if($('#mountDate').val().trim() == ''){
		showMessage('info','Please select Mount Date !');
		$("#mountDate").focus();
		return false;
	}
	if($('#remark').val().trim() == ''){
		showMessage('info','Please select Mount Remark !');
		$("#remark").focus();
		return false;
	}
		
	showLayer();
		var jsonObject			= new Object();
		
		jsonObject["batteryId"] 	  			=  $('#selectBattery').val();
		jsonObject["layoutId"] 	  				=  layoutId;
		jsonObject["mountDate"] 	  			=  $('#mountDate').val(); 
		jsonObject["remark"] 	  				=  $('#remark').val(); 
		jsonObject["vid"] 	  					=  $('#vid').val(); 
		jsonObject["vehicle_Odometer"] 	  		=  $('#vehicle_Odo').val(); 
		jsonObject["layoutPosition"] 	  		=  layoutPosition;
		
		$.ajax({
	             url: "mountBatteryDetailstoVehicle",
	             type: "POST",
	             dataType: 'json',
	             data: jsonObject,
	             success: function (data) {
	            	 window.location.replace("VehicleBatteryDetails?Id="+data.vid+"&MountSuccess="+true);
	            	 hideLayer();
	             },
	             error: function (e) {
	            	 showMessage('errors', 'Some error occured!')
	            	 hideLayer();
	             }
		});

}

function dismountBatteryDetails(layoutId, layoutPosition, vid, battery){
	var position = "battery - "+layoutPosition;
	var batteryMoveTo = "";
if($("#oldBatteryMoveConfig").val() == true || $("#oldBatteryMoveConfig").val() == 'true'){
	 batteryMoveTo =+ '<div class="row1">'
	+ '	<label class="L-size control-label">Battery Move To <abbr title="required">*</abbr>'
	+ '	</label>'
	+ '	<div class="I-size">'
	+ '<select id="oldBatteryMoveId" name="oldBatteryMoveId "class="form-text">'
	+ '<option value="0">Please Select Old Tyre Move To</option>'
	+ '<option value="1">Repair</option>'
	+ '<option value="2">Scrap</option>'
	+ '<option value="3">WorkShop</option>'
	+ '</select>'
	+ '	</div>'
	+ ' </div>';
	
}
	
	var MountTyre = '									<div class="form-horizontal ">'
			+ '													<fieldset>'
			+ '														<legend id="Identification">Battery Mount</legend>'
			+ '														<div class="row1">'
			+ '															<label class="L-size control-label"><span>Position'
			+ '																	:</span><abbr title="required">*</abbr></label>'
			+ '															<div class="I-size">'
			+ '																<input name="layoutPosition" type="text" class="form-text"'+
	'																	readonly="readonly" value="'+position+'"'+
	'																	required="required" />'
			+ '															</div>'
			+ '														</div>'
			+ '														<div class="row1">'
			+ '															<label class="L-size control-label"><span>Battery'
			+ '																	Capacity :</span><abbr title="required">*</abbr></label>'
			+ '															<div class="I-size">'
			+ '																<input  id="batteryCapacity" name="batteryCapacity" type="text" class="form-text"'+
			'																	readonly="readonly" value="'+battery.batteryCapacity+'"'+
			'																	required="required" />'	
			+ '															</div>'
			+ '														</div>'
			+ '														<div class="row1">'
			+ '															<label class="L-size control-label"><span>Select'
			+ '																	Battery :</span><abbr title="required">*</abbr></label>'
			+ '															<div class="I-size">'
			+ '																<input type="text" class="form-text" value="'+battery.batterySerialNumber+'" readonly="readonly" id="dismountBattery" name="dismountBattery"'
			+ '																	style="width: 100%;"'
			+ '																	placeholder="Please Enter 2 or more Battery No" /> <input type="hidden" id="disMountBatteryId" value="'+battery.batteryId+'" />'
			+ '															</div>'
			+ '														</div>'
			+ '														<div class="row1">'
			+ '															<label class="L-size control-label"><span>Dismount Odometer'
			+ '																	:</span><abbr title="required">*</abbr></label>'
			+ '															<div class="I-size">'
			+ '																<input id="vehicle_Odo" onkeypress="return isNumberKey(event,this);" name="TYRE_ODOMETER" type="number" class="form-text"'+
	'																	value="'+battery.vehicle_Odometer+'" required="required" />'
			+ '															</div>'
			+ '														</div>'
			+' '+batteryMoveTo+' '
			+ '														<div class="row1">'
			+ '															<label class="L-size control-label">DisMount Date <abbr'+
	'																title="required">*</abbr>'
			+ '															</label>'
			+ '															<div class="I-size">'
			+ '																<div class="input-group input-append date"'+
	'																	id="TyreMount">'
			+ '																	<input type="text" id="mountDate" class="form-text" readonly="readonly"'+
	'																		name="MOUNTED_DATE" required="required"'+
	'																		data-inputmask="\'alias\': \'dd-mm-yyyy\'" data-mask="" />'
			+ '																	<span class="input-group-addon add-on"> <span'+
	'																		class="fa fa-calendar"></span>'
			+ '																	</span>'
			+ '																</div>'
			+ '															</div>'
			+ '														</div>'
			+ '                                                   <div class="row1">'
			+ '															<label class="L-size control-label"><span>DisMounted Remark'
			+ '																	:</span><abbr title="required">*</abbr></label>'
			+ '															<div class="I-size">'
			+ '																<input id="remark" name="remark" type="text" class="form-text"'+ 
	'																	required="required" />'
			+ '															</div>'
			+ '														</div>'
			+ '														<div class="row1">'
			+ '															<div class="L-size"></div>'
			+ '															<div class="I-size">'
			+ '																<div class="col-sm-offset-4 I-size">'
			+ '																	<input onclick="return disMountBatteryDetailstoVehicle('+battery.layoutId+','+layoutPosition+');" class="btn btn-success" type="submit"'+
	'																		value="Dismount  Battery"> <a'+
	'																		class="btn btn-default" href="#">Cancel</a>'
			+ '																</div>'
			+ '															</div>'
			+ '														</div>'
			+ '													<\/fieldset>'
			+ '												</div>'
			+ '												<script type="text/javascript">'
			+ '														$(document).ready(function(){$("#datemask").inputmask("dd-mm-yyyy",{placeholder:"dd-mm-yyyy"}),$("[data-mask]").inputmask()},$("#TyreMount").datepicker({autoclose:!0,todayHighlight:!0,format:"dd-mm-yyyy"}));'
			+ '            <\/script>';

	$("#BatteryMountSHoW").html(MountTyre);

}

function disMountBatteryDetailstoVehicle(layoutId,layoutPosition){
	
		var jsonObject			= new Object();
		
		if($('#mountDate').val().trim() == ''){
			showMessage('info', 'Please Select Dismount Date !');
			$('#mountDate').focus();
			return false;
		}
		if($('#remark').val().trim() == ''){
			showMessage('info', 'Please enter dismount comment !');
			$('#remark').focus();
			return false;
		}
		showLayer();
		
		jsonObject["batteryId"] 	  			=  $('#disMountBatteryId').val();
		jsonObject["layoutId"] 	  				=  layoutId;
		jsonObject["mountDate"] 	  			=  $('#mountDate').val(); 
		jsonObject["remark"] 	  				=  $('#remark').val(); 
		jsonObject["vid"] 	  					=  $('#vid').val(); 
		jsonObject["vehicle_Odometer"] 	  		=  $('#vehicle_Odo').val(); 
		jsonObject["layoutPosition"] 	  		=  layoutPosition; 
		jsonObject["oldBatteryMoveId"] 	  		=   $('#oldBatteryMoveId').val();  
		jsonObject["oldBatteryMoveConfig"] 	  	=   $('#oldBatteryMoveConfig').val();  
		
		$.ajax({
	             url: "disMountBatteryDetailstoVehicle",
	             type: "POST",
	             dataType: 'json',
	             data: jsonObject,
	             success: function (data) {
	            	 window.location.replace("VehicleBatteryDetails?Id="+data.vid+"&DismountSuccess="+true);
	            	 hideLayer();
	             },
	             error: function (e) {
	            	 showMessage('errors', 'Some error occured!')
	            	 hideLayer();
	             }
		});
}