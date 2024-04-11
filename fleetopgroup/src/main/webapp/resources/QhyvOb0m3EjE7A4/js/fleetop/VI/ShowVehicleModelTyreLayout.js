$(document).ready(function() {
	getVehicleModelTyreLayout();
	
});

function getVehicleModelTyreLayout(){
	var jsonObject				= new Object();
	jsonObject["vehicleModelId"]		= $('#vehicleModelId').val();
	jsonObject["companyId"]						= $('#companyId').val();
	
	showLayer();
	$.ajax({
		url: "getVehicleModelTyreLayout",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setVehicleModelTyreLayout(data);
			setVehicleModelTyreLayoutPosition(data);
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
		var axleSize 			= vehicleModelTyreLayout.numberOfAxle;
		var isSpareTyrePresent 	= vehicleModelTyreLayout.spareTyrePresent;
		var LO					= "LO-";
		var RO					= "RO-";
		var LI					= "LI-";
		var RI					= "RI-";
		var SP					= "SP-0";
		if(axleSize > 0){
			for(var i = 1; i <= axleSize; i++ ){
				var columnArray = new Array();
				
				var tr = '<tr class="fit" id="axleRow'+(i)+'">';
				if(vehicleModelTyreLayout.dualTyrePresentAxle.includes(i)){
					tr += '<td class="tyreSize"> <div class="column" onclick="showTyreDetails(\''+LO+''+i+'\');"><img src="resources/QhyvOb0m3EjE7A4/images/tyreUpperView.png" class="img-rounded" alt="Tyre Photo" width="45" height="90" /></div><div class="column" onclick="showTyreDetails(\''+LI+''+i+'\');" style="padding-right: 33px;"  id="dualLeft'+i+'" ><img src="resources/QhyvOb0m3EjE7A4/images/tyreUpperView.png" class="img-rounded" alt="Tyre Photo" width="45" height="90" /></div></td>';
				}else{
					tr += '<td class="tyreSize"> <div class="column" onclick="showTyreDetails(\''+LO+''+i+'\');"><img src="resources/QhyvOb0m3EjE7A4/images/tyreUpperView.png" class="img-rounded" alt="Tyre Photo" width="45" height="90" /></div></td>';
				}
				
				tr +='<td class="steelSize2" id="steel'+i+'" style="padding-right: 20px;"> <div class="Steel"></div> <div class="SteelDown"></div> </td>';
				
				if(vehicleModelTyreLayout.dualTyrePresentAxle.includes(i)){
					tr += '<td class="tyreSize"> <div class="column" onclick="showTyreDetails(\''+RI+''+i+'\');"><img src="resources/QhyvOb0m3EjE7A4/images/tyreUpperView.png" class="img-rounded" alt="Tyre Photo" width="45" height="90" /></div><div class="column" onclick="showTyreDetails(\''+RO+''+i+'\');"  style="padding-right: 33px;"  id="dualLeft'+i+'" ><img src="resources/QhyvOb0m3EjE7A4/images/tyreUpperView.png" class="img-rounded" alt="Tyre Photo" width="45" height="90" /></div></td>';
				}else{
					tr += '<td class="tyreSize"> <div class="column" onclick="showTyreDetails(\''+RO+''+i+'\');"><img src="resources/QhyvOb0m3EjE7A4/images/tyreUpperView.png" class="img-rounded" alt="Tyre Photo" width="45" height="90" /></div></td>';
				}
				
				tr +='<td class="tyreSize"></td> </tr><br><br>';
				
				columnArray.push(tr);
				
				columnArray.push('<tr class="fit" id="axle'+(i)+'"></tr>');
				$('#layoutTable').append(columnArray.join(' '));
			}
			
			$("#axle1").append('<td class="tyreSize"></td>'
					+'<td class="steelSize">'
					+'<div id="spearTyre" style="display: none;" onclick="showTyreDetails(\''+SP+'\');" ><img src="resources/QhyvOb0m3EjE7A4/images/TyreWheel.png" class="img-rounded" alt="Tyre Photo" width="100" height="100" /></div>'
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
	var dualTyrePresentAxleArr = new Array();
	if(vehicleModelTyreLayout.dualTyrePresentAxle != undefined){
		dualTyrePresentAxleArr = vehicleModelTyreLayout.dualTyrePresentAxle.split(",");
	}
	if(dualTyrePresentAxleArr != null ){
		for(var i = 0; i < dualTyrePresentAxleArr.length; i++){
			console.log(">",dualTyrePresentAxleArr[i])
			$("#steel"+dualTyrePresentAxleArr[i]+"").addClass('steel');
		}
	}
}

function showTyreDetails(position){

	var jsonObject								= new Object();
	jsonObject["position"]						= position;
	jsonObject["vehicleModelTyreLayoutId"]		= $('#vehicleModelTyreLayoutId').val();
	jsonObject["companyId"]						= $('#companyId').val();
	
	showLayer();
	$.ajax({
		url: "getVehicleModelTyreLayoutPositionByPosition",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setVehicleModelTyreLayoutPositionDetails(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});

}

function setVehicleModelTyreLayoutPositionDetails(data){
	var vehicleModelTyreLayoutPosition = data.vehicleModelTyreLayoutPosition;
	console.log("vehicleModelTyreLayoutPosition",vehicleModelTyreLayoutPosition)
	if(vehicleModelTyreLayoutPosition != undefined){
		$("#tyrePosition").html(vehicleModelTyreLayoutPosition.position);
		$("#tyreModelName").html(vehicleModelTyreLayoutPosition.tyreModel);
		$("#tyreModelType").html(vehicleModelTyreLayoutPosition.tyreModelType);
		$("#tyreModelSize").html(vehicleModelTyreLayoutPosition.tyreModelSize);
		$("#gauageMeasurementLine").html(vehicleModelTyreLayoutPosition.gauageMeasurementLine);
		$("#tyreGuage").html(vehicleModelTyreLayoutPosition.tyreGauge);
		$("#tyreTubeTypeName").html(vehicleModelTyreLayoutPosition.tyreTubeType);
		$("#ply").html(vehicleModelTyreLayoutPosition.ply);
		$("#psi").html(vehicleModelTyreLayoutPosition.psi);
		
		$("#tyrePositionDetails").show();
		$("#tyreLayoutDetails").hide();
		
	}
}



function deleteVehicleModelTyreLayout(){

if(confirm('Are You Sure! Do You Want To Delete The Layout')){
	var jsonObject								= new Object();
	jsonObject["vehicleModelId"]				= $('#vehicleModelId').val();
	jsonObject["vehicleModelTyreLayoutId"]		= $('#vehicleModelTyreLayoutId').val();
	jsonObject["companyId"]						= $('#companyId').val();
	
	showLayer();
	$.ajax({
		url: "deleteVehicleModelTyreLayout",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			console.log("data",data)
			
			if(data.tyreExist != undefined && data.tyreExist == true){
				showMessage('info','Tyre Is Already Assign Hence Can Not Delete Layout ')
				hideLayer();
				return false;
			}else{
				window.location.replace("vehicleModel");
			}
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}


}