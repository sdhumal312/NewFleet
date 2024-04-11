
document.title = $("#vehicleRegistration").val()+' Tyre Layout ';
$(document).ready(function() {
	getVehicleModelTyreLayout();
});

function getVehicleModelTyreLayout(){
	var jsonObject						= new Object();
	jsonObject["vehicleModelId"]		= $('#vehicleModelId').val();
	jsonObject["companyId"]				= $('#companyId').val();
	
	showLayer();
	$.ajax({
		url: "getVehicleModelTyreLayout",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setTimeout(function(){
				console.log("data.vehicleModelTyreLayout",data.vehicleModelTyreLayout)
				if(data.vehicleModelTyreLayout.frontTyreModel != null && data.vehicleModelTyreLayout.frontTyreModel != undefined){
					setVehicleModelTyreLayout(data);
					setVehicleModelTyreLayoutPosition(data);
					getAssignTyreToPosition();
				}else{
					showMessage('info','Please Create Layout');
					$("#createLayout").removeClass('hide');
					return false;
				}
			}, 500);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setVehicleModelTyreLayout(data){
	var vehicleModelTyreLayout = data.vehicleModelTyreLayout;
	
	if(vehicleModelTyreLayout != undefined){
		$("#numberOfAxle").html("Axle "+vehicleModelTyreLayout.numberOfAxle);
		$("#vehicleModel").html(vehicleModelTyreLayout.vehicleModel);
		$("#frontTyreModel").html(vehicleModelTyreLayout.frontTyreModel);
		$("#frontTyreSize").html(vehicleModelTyreLayout.frontTyreSize);
		$("#rearTyreModel").html(vehicleModelTyreLayout.rearTyreModel);
		$("#rearTyreSize").html(vehicleModelTyreLayout.rearTyreSize);
		$("#spareTyreModel").html(vehicleModelTyreLayout.spareTyreModel);
		$("#spareTyreSize").html(vehicleModelTyreLayout.spareTyreSize);
		$("#vehicleModelTyreLayoutId").val(vehicleModelTyreLayout.vehicleModelTyreLayoutId);
	
	}else{
		showMessage('info','Please Create Layout')
		window.location.replace("vehicleModelTyreLayout.in?id="+$('#vehicleModelId').val()+"");
	}
}
function setVehicleModelTyreLayoutPosition(data){
	var vehicleModelTyreLayout = data.vehicleModelTyreLayout;
	$("#layoutTable").empty();
	if(vehicleModelTyreLayout != undefined){
		var imageBlurSrc 		= "resources/QhyvOb0m3EjE7A4/images/tyreUpperViewBlur.jpg";
		var spareImageBlurSrc 	= "resources/QhyvOb0m3EjE7A4/images/TyreWheelBlur.png";
		var axleSize 			= vehicleModelTyreLayout.numberOfAxle;
		var isSpareTyrePresent 	= vehicleModelTyreLayout.spareTyrePresent;

		var LO					= "LO-";
		var RO					= "RO-";
		var LI					= "LI-";
		var RI					= "RI-";
		var SP					= "SP-0";
		
		var REAR_LEFT_OUT		= "Rear LO-";
		var REAR_RIGHT_OUT		= "Rear RO-";
		var REAR_LEFT_IN		= "Rear LI-";
		var REAR_RIGHT_IN		= "Rear RI-";
		var BUS_SPARE			= "Bus Spare";
		var FL					= "Front Left";
		var FR					= "Front Right";
		
		if(axleSize > 0){
			for(var i = 1; i <= axleSize; i++ ){
				var columnArray = new Array();
				var tr = '<tr class="fit" id="axleRow'+(i)+'">';
				
				if(i == 1){
					console.log("abccc")
					if(vehicleModelTyreLayout.dualTyrePresentAxle.includes(i)){
						console.log("duealll")
						tr += '<td class="tyreSize"> <div class="column" onclick="showTyreDetails(\'' +LO +i+ "','" +FL+ '\' );"><img id="'+LO+''+i+'" src="'+imageBlurSrc+'" class="img-rounded" alt="Tyre Photo" width="45" height="90" title="Position: '+FL+'"/></div><div class="column" onclick="showTyreDetails(\'' +LI +i+ "','" +FL+ '\' );" style="padding-right: 33px;"  id="dualLeft'+i+'" ><img id="'+LI+''+i+'" src="'+imageBlurSrc+'" class="img-rounded" alt="Tyre Photo" width="45" height="90" title="Position: '+FL+'"/></div></td>';
					}else{
						console.log("no duealll")
						tr += '<td class="tyreSize"> <div class="column" onclick="showTyreDetails(\'' +LO +i+ "','" +FL+ '\' );"><img id="'+LO+''+i+'" src="'+imageBlurSrc+'" class="img-rounded" alt="Tyre Photo" width="45" height="90" title="Position: '+FL+'" /></div></td>';
					}
					
					tr +='<td class="steelSize2" id="steel'+i+'" style="padding-right: 20px;"> <div class="Steel"></div> <div class="SteelDown"></div> </td>';
					
					if(vehicleModelTyreLayout.dualTyrePresentAxle.includes(i)){
						tr += '<td class="tyreSize"> <div class="column" onclick="showTyreDetails(\'' +RI +i+ "','" +FR+ '\' );"><img id="'+RI+''+i+'" src="'+imageBlurSrc+'" class="img-rounded" alt="Tyre Photo" width="45" height="90" title="Position: '+FR+'" /></div><div class="column" onclick="showTyreDetails(\'' +RO +i+ "','" +FR+ '\' );"  style="padding-right: 33px;"  id="dualLeft'+i+'" ><img id="'+RO+''+i+'" src="'+imageBlurSrc+'" class="img-rounded" alt="Tyre Photo" width="45" height="90" title="Position: '+FR+'"  /></div></td>';
					}else{
						tr += '<td class="tyreSize"> <div class="column" onclick="showTyreDetails(\'' +RO +i+ "','" +FR+ '\' );"><img id="'+RO+''+i+'" src="'+imageBlurSrc+'" class="img-rounded" alt="Tyre Photo" width="45" height="90" title="Position: '+FR+'"  /></div></td>';
					}
				}else{
					console.log("222222222")
					if(vehicleModelTyreLayout.dualTyrePresentAxle.includes(i)){
						tr += '<td class="tyreSize"> <div class="column" onclick="showTyreDetails(\'' +LO +i+ "','" +REAR_LEFT_OUT+i+ '\' );"><img id="'+LO+''+i+'" src="'+imageBlurSrc+'" class="img-rounded" alt="Tyre Photo" width="45" height="90" title="Position: '+REAR_LEFT_OUT+''+i+'"/></div><div class="column" onclick="showTyreDetails(\'' +LI +i+ "','" +REAR_LEFT_IN+i+ '\');" style="padding-right: 33px;"  id="dualLeft'+i+'" ><img id="'+LI+''+i+'" src="'+imageBlurSrc+'" class="img-rounded" alt="Tyre Photo" width="45" height="90" title="Position: '+REAR_LEFT_IN+''+i+'"/></div></td>';
					}else{
						tr += '<td class="tyreSize"> <div class="column" onclick="showTyreDetails(\'' +LO +i+ "','" +REAR_LEFT_OUT+i+ '\');"><img id="'+LO+''+i+'" src="'+imageBlurSrc+'" class="img-rounded" alt="Tyre Photo" width="45" height="90" title="Position: '+REAR_LEFT_OUT+''+i+'" /></div></td>';
					}
					
					tr +='<td class="steelSize2" id="steel'+i+'" style="padding-right: 20px;"> <div class="Steel"></div> <div class="SteelDown"></div> </td>';
					
					if(vehicleModelTyreLayout.dualTyrePresentAxle.includes(i)){
						tr += '<td class="tyreSize"> <div class="column" onclick="showTyreDetails(\'' +RI +i+ "','" +REAR_RIGHT_IN+i+ '\');"><img id="'+RI+''+i+'" src="'+imageBlurSrc+'" class="img-rounded" alt="Tyre Photo" width="45" height="90" title="Position: '+REAR_RIGHT_IN+''+i+'" /></div><div class="column" onclick="showTyreDetails(\'' +RO +i+ "','" +REAR_RIGHT_OUT+i+ '\');"  style="padding-right: 33px;"  id="dualLeft'+i+'" ><img id="'+RO+''+i+'" src="'+imageBlurSrc+'" class="img-rounded" alt="Tyre Photo" width="45" height="90" title="Position: '+REAR_RIGHT_OUT+''+i+'"  /></div></td>';
					}else{
						tr += '<td class="tyreSize"> <div class="column" onclick="showTyreDetails(\'' +RO +i+ "','" +REAR_RIGHT_OUT+i+ '\');"><img id="'+RO+''+i+'" src="'+imageBlurSrc+'" class="img-rounded" alt="Tyre Photo" width="45" height="90" title="Position: '+REAR_RIGHT_OUT+''+i+'"  /></div></td>';
					}
				}
				
				tr +='<td class="tyreSize"></td> </tr><br><br>';
				
				columnArray.push(tr);
				
				columnArray.push('<tr class="fit" id="axle'+(i)+'"></tr>');
				$('#layoutTable').append(columnArray.join(' '));
			}
			
			$("#axle1").append('<td class="tyreSize"></td>'
					+'<td class="steelSize">'
					+'<div id="spearTyre" style="display: none;" onclick="showTyreDetails(\'' +SP+ "','" +BUS_SPARE+ '\');" ><img id="SP-0" src="'+spareImageBlurSrc+'" class="img-rounded" alt="Tyre Photo" width="100" height="100" title="Position: '+BUS_SPARE+'" /></div>'
					+'</td>'
					+'<td class="tyreSize"></td>'
					+'<td class="steelSize2">'
					+'</td>'
					+'<td class="tyreSize"></td>'
					+'<td class="steelSize"></td>'
					+'<td class="tyreSize"></td>');
			
		}
		
		if(isSpareTyrePresent){
			$("#spearTyre").show();
		}
		
	}
	if(vehicleModelTyreLayout.dualTyrePresentAxle != undefined){
		var dualTyrePresentAxleArr = new Array();
		dualTyrePresentAxleArr = vehicleModelTyreLayout.dualTyrePresentAxle.split(",");
		if(dualTyrePresentAxleArr != null ){
			for(var i = 0; i < dualTyrePresentAxleArr.length; i++){
				$("#steel"+dualTyrePresentAxleArr[i]+"").addClass('steel');
			}
		}
	}
}


function getAssignTyreToPosition(){
	var jsonObject						= new Object();
	jsonObject["vid"]		= $('#vid').val();
	jsonObject["companyId"]				= $('#companyId').val();
	
	showLayer();
	$.ajax({
		url: "getVehicleTyreLayoutPosition",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setAssignTyreToPosition(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setAssignTyreToPosition(data){
	var vehicleTyreLayoutPositionList = data.vehicleTyreLayoutPositionList;
	var imageSrc 				= "resources/QhyvOb0m3EjE7A4/images/tyreUpperView.png";
	var spareImageSrc 			= "resources/QhyvOb0m3EjE7A4/images/TyreWheel.png";
	var spearePostion 			= "SP-0";
	
	if(vehicleTyreLayoutPositionList != undefined){
		console.log("vehicleTyreLayoutPositionList.............",vehicleTyreLayoutPositionList)
		for(var i = 0; i< vehicleTyreLayoutPositionList.length; i++){
			if(vehicleTyreLayoutPositionList[i].tyre_ID != undefined){
				console.log("tyre id",vehicleTyreLayoutPositionList[i].tyre_ID, " position>>",vehicleTyreLayoutPositionList[i].position  )
				if(vehicleTyreLayoutPositionList[i].position == spearePostion){
					$("#SP-0").attr("src",spareImageSrc); 	
					$("#SP-0").attr('title', 'Position: SP-0 Tyre No: '+vehicleTyreLayoutPositionList[i].tyre_SERIAL_NO+'');
				}else{
					var axel = (vehicleTyreLayoutPositionList[i].position).split("-");
					$("#"+vehicleTyreLayoutPositionList[i].position+"").attr("src",imageSrc);
					if(axel[1] == 1){
						if(axel[0] == 'LO'){
							$("#"+vehicleTyreLayoutPositionList[i].position+"").attr('title', 'Position: Front Left Tyre No: '+vehicleTyreLayoutPositionList[i].tyre_SERIAL_NO+'');
						}else{
							$("#"+vehicleTyreLayoutPositionList[i].position+"").attr('title', 'Position: Front Right Tyre No: '+vehicleTyreLayoutPositionList[i].tyre_SERIAL_NO+'');
						}
						
					}else{
						$("#"+vehicleTyreLayoutPositionList[i].position+"").attr('title', 'Position: Rear '+vehicleTyreLayoutPositionList[i].position+' Tyre No: '+vehicleTyreLayoutPositionList[i].tyre_SERIAL_NO+'');
						
					}
				}
			}
		}
		
	}
}


function showTyreDetails(position,postionStr){

	console.log("postionStr",postionStr)
	var jsonObject								= new Object();
	jsonObject["position"]						= position;
	jsonObject["vid"]							= $('#vid').val();
	jsonObject["companyId"]						= $('#companyId').val();
	console.log("jsonObject",jsonObject);
	showLayer();
	$.ajax({
		url: "getTyreDetailsOfPosition",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setVehicleModelTyreLayoutPositionDetails(data,postionStr);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});

}

function setVehicleModelTyreLayoutPositionDetails(data,postionStr){
	var vehicleTyreLayoutPosition = data.vehicleTyreLayoutPosition;
	if(data.position )
	$("#tyrePosition").html(postionStr);
	$("#LP_ID").val(vehicleTyreLayoutPosition.lp_ID);
	if(vehicleTyreLayoutPosition.tyre_ID != undefined && vehicleTyreLayoutPosition.tyre_ID > 0){
		$("#tyreNumber").html(vehicleTyreLayoutPosition.tyre_SERIAL_NO);
		$("#tyreNumber").attr("href", "showTyreInfo?Id="+vehicleTyreLayoutPosition.tyre_ID+" ")
		$("#assignDate").html(vehicleTyreLayoutPosition.mountDateStr);
		$("#tyreModelName").html(vehicleTyreLayoutPosition.tyreModel);
		$("#tyreModelType").html(vehicleTyreLayoutPosition.tyreModelType);
		$("#tyreModelSize").html(vehicleTyreLayoutPosition.tyreModelSize);
		$("#gauageMeasurementLine").html(vehicleTyreLayoutPosition.gauageMeasurementLine);
		$("#tyreGuage").html(vehicleTyreLayoutPosition.tyreGauge);
		$("#tyreGaugeVal").val(vehicleTyreLayoutPosition.tyreGauge);
		$("#tyreTubeTypeName").html(vehicleTyreLayoutPosition.tyreTubeType);
		$("#ply").html(vehicleTyreLayoutPosition.ply);
		$("#psi").html(vehicleTyreLayoutPosition.psi);
		
		$("#tyreNumber1").val(vehicleTyreLayoutPosition.tyre_SERIAL_NO);
		$("#tyrePositionId").val(vehicleTyreLayoutPosition.position);
		$("#tyreId").val(vehicleTyreLayoutPosition.tyre_ID);
		$(".mountInfo").show();
	}else{
		$(".mountInfo").hide();
	}
	
	$("#tyrePositionDetails").show();
	$("#tyreLayoutDetails").hide();
		
	if(vehicleTyreLayoutPosition.tyre_SERIAL_NO != undefined){
		$("#dismountId").show();
		$("#mountId").hide();
	}else{
		$("#mountId").show();
		$("#dismountId").hide();
	}
}



function deleteVehicleModelTyreLayout(){

if(confirm('Are You Sure! Do You Want To Delete The Layout')){
	var jsonObject								= new Object();
	jsonObject["vehicleModelTyreLayoutId"]		= $('#vehicleModelTyreLayoutId').val();
	jsonObject["companyId"]						= $('#companyId').val();
	
	showLayer();
	$.ajax({
		url: "deleteVehicleModelTyreLayout",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			window.location.replace("vehicleModel");
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}


}

function dismountTyre(){
	$.getJSON("getVehicleOdoMerete.in", {
		vehicleID: $("#vid").val(),
		ajax: "true"
	}, function(a) {
		console.log("result",a)
		if(a.vehicle_Odometer != undefined && a.vehicle_Odometer > 0){
			$('#dismountOdometer').val(a.vehicle_Odometer);
			$('#showDismountOdometer').val(a.vehicle_Odometer);
		}
		
	})

	
	$("#dismountModal").modal('show');
}

$(document).ready(function() {
	 $("#startDate").datepicker({
        autoclose: !0,
        todayHighlight: !0,
        format: "dd-mm-yyyy"
    })
});

function removeTyre(){
	var jsonObject								= new Object();
	jsonObject["vid"]							= $('#vid').val();
	jsonObject["LP_ID"]							= $('#LP_ID').val();
	jsonObject["tyreId"]						= $('#tyreId').val();
	jsonObject["dismountDate"]					= $('#dismountDate').val();
	jsonObject["dismountOdometer"]				= $('#dismountOdometer').val();
	jsonObject["remark"]						= $('#remark').val();
	jsonObject["companyId"]						= $('#companyId').val();
	jsonObject["tyrePositionId"]				= $('#tyrePositionId').val();
	jsonObject["tyreNumber"]					= $('#tyreNumber1').val();
	jsonObject["oldTyreMoveId"]					= $('#oldTyreMoveId').val();
	jsonObject["tyreGauge"]						= $('#tyreGaugeVal').val();
	
		
	var isBeforeMountDate	= moment($('#dismountDate').val().split("-").reverse().join("-")+" 00:00:00").isBefore($("#assignDate").html().split("-").reverse().join("-")+" 00:00:00");
	if(isBeforeMountDate){
		showMessage('info','Dismount Date Can Not Before Than Mount Date')
		return false;
	}
	
	if(Number($('#dismountDate').val())  < Number($("#showDismountOdometer")) ){
		showMessage('info','Dismount Odometer Can Not be Less Than Mount Odometer')
		return false;
	}
	
	if($('#dismountDate').val() == undefined || $('#dismountDate').val() == "" || $('#dismountDate').val() == 0){
		showMessage('info','Please Select Dismount Date')
		return false;
	}
	if($('#dismountOdometer').val() == undefined || $('#dismountOdometer').val() == "" || $('#dismountOdometer').val() == 0){
		showMessage('info','Please Enter Dismount Odometer')
		return false;
	}
	if($('#oldTyreMoveId').val() == undefined || $('#oldTyreMoveId').val() == "" || $('#oldTyreMoveId').val() == 0){
		showMessage('info','Please Enter Old Tyre Move To')
		return false;
	}
	if($('#remark').val() == undefined || $('#remark').val().trim() == ""){
		showMessage('info','Please Enter Remark')
		return false;
	}
	
	showLayer();
	$.ajax({
		url: "tyreRemoveFromVehicle",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			location.reload();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function mountTyre(){
	window.location.replace("vehicleTyreAssignment.in?data="+$("#vid").val()+","+$("#LP_ID").val()+","+$("#tyrePosition").html()+" ");
}

function getDateWiseOdometer(){
	var vid = $("#vid").val();
	var date = $("#dismountDate").val();
	$.getJSON("getDateWiseVehicleOdometer.in", {
		vid: vid,
		date: date,
		ajax: "true"
	}, function(a) {
		console.log("result...............",a)
		if(a.vehicle_Odometer != undefined && a.vehicle_Odometer != ""){
			$('#dismountOdometer').val(a.vehicle_Odometer);
		}else{
			$('#dismountOdometer').val(0);
		}
	})
}