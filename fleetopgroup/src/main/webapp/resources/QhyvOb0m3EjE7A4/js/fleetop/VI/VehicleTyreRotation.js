$(document).ready(function() {
	var a = 500,
	b = $(".addMoreRotationTyreDiv"),
	c = $(".addMoreRotationTyre"),
	d = 1;
	$(c).click(function(c) {
		c.preventDefault(), a > d && (d++, $(b).append('<div class="box"><div class="box-body child"><div class="col col-sm-1 col-md-3">'
				+'<label class="has-float-label"> '
				+'<select id="rotateFromPositionId'+d+'" name="rotateFromPositionId" class="browser-default custom-select rotateFromPositionId" onchange="changeFromPosition(this.id,'+d+');"></select> '
				+'<span style="color: #2e74e6; font-size: 18px;">Rotate From <abbr title="required">*</abbr> </span>'
				+'</label>'
				+'</div>'
				+'<div class="col col-sm-1 col-md-2">'
				+'<label class="has-float-label">' 
				+'<input type="text" id="rotateFromTyreId'+d+'" name="rotateFromTyreId" class="form-control browser-default rotateFromTyreId"  readonly="readonly">'
				+'<span style="color: #2e74e6; font-size: 18px;">Rotate From Tyre <abbr title="required">*</abbr> </span>'
				+'</label>'
				+'</div>'
				+'<div class="col col-sm-1 col-md-3">'
				+'<label class="has-float-label"> '
				+'<select id="rotateToPositionId'+d+'" name="rotateToPositionId" class="browser-default custom-select rotateToPositionId" onchange="changeToPosition(this.id,'+d+');"></select> '
				+'<span style="color: #2e74e6; font-size: 18px;">Rotate To <abbr title="required">*</abbr> </span>'
				+'</label>'
				+'</div>'
				+'<div class="col col-sm-1 col-md-2">'
				+'<label class="has-float-label">' 
				+'<input type="text" id="rotateToTyreId'+d+'" name="rotateToTyreId" class="form-control browser-default rotateToTyreId" readonly="readonly">'
				+'<span style="color: #2e74e6; font-size: 18px;">Rotate To Tyre <abbr title="required">*</abbr> </span>'
				+'</label>'
				+'</div>'
				+' <button type="button" style="width: 50px;" class="btn btn-danger removeRotation col col-sm-1 col-sm-1"><span class="fa fa-trash"></span></button>'
			//	+'<a href="#" class="removeRotation col col-sm-1 col-md-2"><font color="FF00000"><i class="fa fa-trash"></i> Remove </a></font>'
				+'</div></div>'))
			getVehicleRotateTyreLayoutPosition($("#rotateVid").val(),d);
		
	}), $(b).on("click", ".removeRotation", function(a) {
		a.preventDefault(), $(this).parent("div").remove(), d--
	})
});

$("#rotateVid").change(function() {
	vehicleOnChange($("#rotateVid").val());
	getVehicleRotateTyreLayoutPosition($("#rotateVid").val());
	
});

function getVehicleRotateTyreLayoutPosition(vid,d){
	var jsonObject						= new Object();
	jsonObject["vid"] 	 				=  vid;
	jsonObject["companyId"] 			=  $('#companyId').val();
	console.log("jsonObject",jsonObject)
	$.ajax({
		url: "getVehicleTyreLayoutPosition",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data != undefined){
				setVehicleRotateTyreLayoutPosition(data);
			}else{
				showMessage('info','Please Create Vehicle Layout For Vehicle Model')
				
			}
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			hideLayer();
		}
	});
}

function setVehicleRotateTyreLayoutPosition(data){
	$("#assignTyreDetailsTable").empty();
	var assignTyreCount		= 0;
	var modelId 			= 0;// this will use if mount form vehicle show page
	var lpid 				= 0;// this will use if mount form vehicle show page
	var index				= 0;
	
	var vehicleTyreLayoutPositionList  = data.vehicleTyreLayoutPositionList;
	
	if(vehicleTyreLayoutPositionList != undefined){
		$('.rotateToPositionId').empty();
		$('.rotateFromPositionId').empty();
		
		for(var i = 0; i< vehicleTyreLayoutPositionList.length; i++){
			if(vehicleTyreLayoutPositionList[i].axle == 1){
				if(vehicleTyreLayoutPositionList[i].position.split("-")[0]  == "LO"){
					$('.rotateToPositionId').append($("<option></option>").attr("value", vehicleTyreLayoutPositionList[i].position+"_"+vehicleTyreLayoutPositionList[i].tyreModelId+"_"+vehicleTyreLayoutPositionList[i].lp_ID+"_"+vehicleTyreLayoutPositionList[i].tyre_ID ).text("Front left"));
					$('.rotateFromPositionId').append($("<option></option>").attr("value", vehicleTyreLayoutPositionList[i].position+"_"+vehicleTyreLayoutPositionList[i].tyreModelId+"_"+vehicleTyreLayoutPositionList[i].lp_ID+"_"+vehicleTyreLayoutPositionList[i].tyre_ID ).text("Front left"));
				}else{
					$('.rotateToPositionId').append($("<option></option>").attr("value", vehicleTyreLayoutPositionList[i].position+"_"+vehicleTyreLayoutPositionList[i].tyreModelId+"_"+vehicleTyreLayoutPositionList[i].lp_ID+"_"+vehicleTyreLayoutPositionList[i].tyre_ID ).text("Front right"));
					$('.rotateFromPositionId').append($("<option></option>").attr("value", vehicleTyreLayoutPositionList[i].position+"_"+vehicleTyreLayoutPositionList[i].tyreModelId+"_"+vehicleTyreLayoutPositionList[i].lp_ID+"_"+vehicleTyreLayoutPositionList[i].tyre_ID ).text("Front right"));
				}
			
			}else if(vehicleTyreLayoutPositionList[i].axle == 0){
				$('.rotateToPositionId').append($("<option></option>").attr("value", vehicleTyreLayoutPositionList[i].position+"_"+vehicleTyreLayoutPositionList[i].tyreModelId+"_"+vehicleTyreLayoutPositionList[i].lp_ID+"_"+vehicleTyreLayoutPositionList[i].tyre_ID ).text("Bus Spare"));
				$('.rotateFromPositionId').append($("<option></option>").attr("value", vehicleTyreLayoutPositionList[i].position+"_"+vehicleTyreLayoutPositionList[i].tyreModelId+"_"+vehicleTyreLayoutPositionList[i].lp_ID+"_"+vehicleTyreLayoutPositionList[i].tyre_ID ).text("Bus Spare"));
			}else{
				if(vehicleTyreLayoutPositionList[i].position.split("-")[0]  == "LO"){
					$('.rotateToPositionId').append($("<option></option>").attr("value", vehicleTyreLayoutPositionList[i].position+"_"+vehicleTyreLayoutPositionList[i].tyreModelId+"_"+vehicleTyreLayoutPositionList[i].lp_ID+"_"+vehicleTyreLayoutPositionList[i].tyre_ID ).text("Rear "+vehicleTyreLayoutPositionList[i].position));
					$('.rotateFromPositionId').append($("<option></option>").attr("value", vehicleTyreLayoutPositionList[i].position+"_"+vehicleTyreLayoutPositionList[i].tyreModelId+"_"+vehicleTyreLayoutPositionList[i].lp_ID+"_"+vehicleTyreLayoutPositionList[i].tyre_ID ).text("Rear "+vehicleTyreLayoutPositionList[i].position));
				}else{
					$('.rotateToPositionId').append($("<option></option>").attr("value", vehicleTyreLayoutPositionList[i].position+"_"+vehicleTyreLayoutPositionList[i].tyreModelId+"_"+vehicleTyreLayoutPositionList[i].lp_ID+"_"+vehicleTyreLayoutPositionList[i].tyre_ID ).text("Rear "+vehicleTyreLayoutPositionList[i].position));
					$('.rotateFromPositionId').append($("<option></option>").attr("value", vehicleTyreLayoutPositionList[i].position+"_"+vehicleTyreLayoutPositionList[i].tyreModelId+"_"+vehicleTyreLayoutPositionList[i].lp_ID+"_"+vehicleTyreLayoutPositionList[i].tyre_ID ).text("Rear "+vehicleTyreLayoutPositionList[i].position));
				}
				
			}
		}
		
		$('.rotateToTyreId').val(vehicleTyreLayoutPositionList[0].tyre_SERIAL_NO);//rotaion
		$('.rotateFromTyreId').val(vehicleTyreLayoutPositionList[0].tyre_SERIAL_NO);//rotaion
		
		$(".layoutPositionTyreModel").val(vehicleTyreLayoutPositionList[0].tyreModel); // use to find tyre by model id
		//	$(".tyreModelId").val(vehicleTyreLayoutPositionList[0].tyreModelId); // use to find tyre by model id
			$(".LP_ID").val(vehicleTyreLayoutPositionList[0].lp_ID); 
			getAssignedTyreByPosition(vehicleTyreLayoutPositionList[0].position,d);
		
	}else{
		$('.tyrePositionId').empty();
		$("#newTyreId").select2("val", "");
	}
}

/*$("#rotateFromPositionId").change(function() {
	$("#rotateFromTyreId").select2("val", "");
	
});*/

function changeFromPosition(positionId, d){
	getRotateFromAssignedTyre($("#"+positionId+"").val().split("_")[0],d);
}

function getRotateFromAssignedTyre(tyrePosition,d){
	if(d == "" || d == undefined){
		d= "";
	}
	$("#rotateFromTyreId"+d).select2("val", "");
	$('#tyreModelId'+d).val($("#rotateFromPositionId"+d).val().split("_")[1]);
	$('#LP_ID'+d).val($("#rotateFromPositionId"+d).val().split("_")[2]);
				
	if($('#rotateVid').val() != "" && tyrePosition != ""){

		var jsonObject				= new Object();
		jsonObject["vid"] 	 		= $('#rotateVid').val();
		jsonObject["position"] 		= tyrePosition;
		jsonObject["companyId"] 	= $('#companyId').val();

		$.ajax({
			url: "getTyreAssignToVehicleDetails",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				if(data.vehicleTyreLayoutPosition.tyre_ID != undefined){
					$("#rotateFromTyreId"+d).val(data.vehicleTyreLayoutPosition.tyre_SERIAL_NO);
				}else{
					$("#rotateFromTyreId"+d).val("");
				}
				
				
			},
			error: function (e) {
				showMessage('errors', 'Some error occured!')
				hideLayer();
			}
		});
	}

}


/*$("#rotateToPositionId").change(function() {
	
	getRotateToAssignedTyre($("#rotateToPositionId").val().split("_")[0]);
	
});*/

function changeToPosition(positionId, d){
	getRotateToAssignedTyre($("#"+positionId+"").val().split("_")[0],d);
}
			
function getRotateToAssignedTyre(tyrePosition,d){
	if(d == "" || d == undefined){
		d= "";
	}
	$("#rotateToTyreId"+d).select2("val", "");
	$('#tyreModelId'+d).val($("#rotateToPositionId"+d).val().split("_")[1]);
	$('#LP_ID'+d).val($("#rotateToPositionId"+d).val().split("_")[2]);
				
	if($('#rotateVid').val() != "" && tyrePosition != ""){

		var jsonObject				= new Object();
		jsonObject["vid"] 	 		= $('#rotateVid').val();
		jsonObject["position"] 		= tyrePosition;
		jsonObject["companyId"] 	= $('#companyId').val();

		$.ajax({
			url: "getTyreAssignToVehicleDetails",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				console.log("aaaaaaa")
				if(data.vehicleTyreLayoutPosition.tyre_ID != undefined){
					$("#rotateToTyreId"+d).val(data.vehicleTyreLayoutPosition.tyre_SERIAL_NO);
				}else{
					$("#rotateToTyreId"+d).val("");
				}
				
				
			},
			error: function (e) {
				showMessage('errors', 'Some error occured!')
				hideLayer();
			}
		});
	}

}



$(document).ready(
		function($) {
			$('button[id=rotate]').click(function(e) {
				e.preventDefault();
				$("#rotateDiv").hide();
				showLayer();
				
				if($("#rotationDate").val() == "" || $("#rotationDate").val() == undefined ){
					showMessage('info','Please Select Rotation Date');
					$("#rotateDiv").show();
					hideLayer();
					return false;
				}
				if(Number($("#rotateVid").val()) == 0 || $("#rotateVid").val() == "" || $("#rotateVid").val() == undefined ){
					showMessage('info','Please Select Vehicle')
					$("#rotateDiv").show();
					hideLayer();
					return false;
				}
				
				
				var jsonObject					= new Object();
				var positionArr	 				= new Array();
				var tyreRotaionArr	 			= new Array();
				var rotateFromPositionArr		= new Array();
				var rotateToPositionArr	 		= new Array();
				var rotateFromLP_IDArr			= new Array();
				var rotateToLP_IDArr	 		= new Array();
				var rotateFromTyreIdArr			= new Array();
				var rotateToTyreIdArr	 		= new Array();
				var rotateFromTyreNumberArr		= new Array();
				var rotateToTyreNumberArr	 	= new Array();
				
				
				$("select[name=rotateFromPositionId]").each(function(){
					positionArr.push($(this).val().split("_")[0]); // all positon (from and to) for checking duplicate
					rotateFromPositionArr.push($(this).val().split("_")[0]);
					rotateFromLP_IDArr.push($(this).val().split("_")[2]);
					rotateFromTyreIdArr.push($(this).val().split("_")[3]);
				});
				$("select[name=rotateToPositionId]").each(function(){
					positionArr.push($(this).val().split("_")[0]);// all positon (from and to) for checking duplicate
					rotateToPositionArr.push($(this).val().split("_")[0]);
					rotateToLP_IDArr.push($(this).val().split("_")[2]);
					rotateToTyreIdArr.push($(this).val().split("_")[3]);
				});
				
				
				console.log("positionArr",positionArr.length)
				
				jsonObject["assignDate"] 				= $('#rotationDate').val();
				jsonObject["vid"] 	 					= $('#rotateVid').val();
				jsonObject["vehicleReg"] 				= $('#rotateVid').select2('data').text;
				jsonObject["vehicleOdometer"] 	 		= $('#rotateOdometer').val();
				jsonObject["remark"] 					= $('#rotationRemark').val();
				jsonObject["companyId"] 				= $('#companyId').val();
				jsonObject["assignDiffTyreModelConfig"] 				= $("#assignDiffTyreModelConfig").val();
				
				
				/*jsonObject["rotateFromPositionId"] 		= $("#rotateFromPositionId").val().split("_")[0];
				jsonObject["rotateToPositionId"] 		= $("#rotateToPositionId").val().split("_")[0];//rotateToPositionId
				jsonObject["tyreFromId"] 				= $("#rotateFromPositionId").val().split("_")[3];
				jsonObject["tyreToId"] 					= $("#rotateToPositionId").val().split("_")[3];
				jsonObject["tyreModelId"] 				= $("#rotateFromPositionId").val().split("_")[1];
				jsonObject["tyreFromLP_ID"] 			= $("#rotateFromPositionId").val().split("_")[2];
				jsonObject["tyreToLP_ID"] 				= $("#rotateToPositionId").val().split("_")[2];
				jsonObject["tyreFromNumber"] 			= $("#rotateFromPositionId").val().split("_")[3];
				jsonObject["tyreToNumber"] 				= $("#rotateToPositionId").val().split("_")[3];*/
				
				
				for(var i = 0 ; i< positionArr.length; i++){ // positionArr[i] contains both from and to positions for validation
					console.log("positionArr[i]",positionArr[i])
					if(positionArr[i] == 0 || positionArr[i] == "" || positionArr[i] == undefined){
						showMessage('info','Please Select Vehicle Tyre Position');
						$("#rotateDiv").show();
						hideLayer();
						return false;
					}
				}
				
				if((new Set(positionArr)).size !== positionArr.length){// check any of the position is duplicate
					 showMessage('info','Postion Can Not Be Same');
					 $("#rotateDiv").show();
						hideLayer();
					return false;
				}
				
				for(var i = 0 ; i< rotateFromPositionArr.length; i++){ // actual for loop
					var tyreRotationDetails					= new Object();
					if(rotateFromTyreIdArr[i] == "null"  || rotateFromTyreIdArr[i] == null){
						showMessage('info','Rotate From Tyre Can Not Be Blank ')
						$("#rotateDiv").show();
						hideLayer();
						return false;
					}
					if(rotateToTyreIdArr[i] == "null"  || rotateToTyreIdArr[i] == null){
						showMessage('info','Rotate To Tyre Can Not Be Blank ')
						$("#rotateDiv").show();
						hideLayer();
						return false;
					}
					
					
					tyreRotationDetails.rotateFromPositionId			= rotateFromPositionArr[i];
					tyreRotationDetails.rotateToPositionId				= rotateToPositionArr[i];
					tyreRotationDetails.tyreFromLP_ID					= rotateFromLP_IDArr[i];
					tyreRotationDetails.tyreToLP_ID						= rotateToLP_IDArr[i];
					tyreRotationDetails.tyreFromId						= rotateFromTyreIdArr[i];
					tyreRotationDetails.tyreToId						= rotateToTyreIdArr[i];
					tyreRotationDetails.tyreFromNumber					= rotateFromTyreNumberArr[i];
					tyreRotationDetails.tyreToNumber					= rotateToTyreNumberArr[i];
					
					tyreRotaionArr.push(tyreRotationDetails);
				}
				
				jsonObject.tyreRotationDetails 				= JSON.stringify(tyreRotaionArr);
				
				console.log("jsonObject",jsonObject)
			
				$.ajax({
					url: "tyreRotation",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						 if(data.tyreModelNotSame != undefined && data.tyreModelNotSame == true && $('#assignDiffTyreModelConfig').val() == false) {
							showMessage('info','Tyre Model Is Different Kindly Select Other Tyre Position')
							$("#rotateDiv").show();
							hideLayer();
						}else if(data.differentSize != undefined && (data.differentSize == true || data.differentSize == 'true')){
							showMessage('info','Tyre Size Is Different Please Change Tyre Position ')
							$("#assignedDiv").show();
						//	$('#accessToken').val(data.accessToken); 		
							hideLayer();
						}  else if(data.tyreNotAssign != undefined && data.tyreNotAssign == true){
							showMessage('info','Tyre Not Found On From Position')
							$("#rotateDiv").show();
							hideLayer();
						}else{
							window.location.replace("showVehicleTyreAssignedDetails?id="+$('#rotateVid').val()+"");
						}
					},
					error: function (e) {
						showMessage('errors', 'Some error occured!')
						hideLayer();
					}
				});
			
				
			})
		});
