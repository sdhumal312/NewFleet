var a = 500,
	b = $(".addMoreAssignTyreDiv"),
	c = $(".addMoreAssignTyre"),
	d = 1;
$(document).ready(function() {
	$(c).click(function(c) {
		if(Number($("#assignFromId").val()) == 1 || Number($("#assignFromId").val()) == 5){
		c.preventDefault(), a > d && (d++, $(b).append('<div class="box" id="tyreDiv'+d+'"><div class="box-body child"><div class="col col-sm-1 col-md-2">'
				+'<label class="has-float-label"> '
				+'<select id="tyrePositionId'+d+'"  name="tyrePositionName" class="browser-default custom-select tyrePositionId" onchange="changePosition(this.id,'+d+');"> </select> '
				+'<span style="color: #2e74e6; font-size: 18px;">Tyre Position <abbr title="required">*</abbr>'
				+'</span>'
				+'</label>'
				+'<span  class="text-danger errTyrePositionId" id="errTyrePositionId'+d+'" ></span>'
				+'</div>'
				+'<div class="col col-sm-1 col-md-2">'
				+'<label class="has-float-label"> '
				+'<input type="hidden" id="tyreModelId'+d+'" name="tyreModelId" class="form-control browser-default tyreModelId" onchange="tyreModelChange('+d+');"> '
				+'<span style="color: #2e74e6; font-size: 18px;">Tyre Model <abbr title="required">*</abbr>'
				+'</span>'
				+'</label>'
				+'</div>'
				+'<div class="col col-sm-1 col-md-2">'
				+'<input type="hidden" id="LP_ID'+d+'" name="LP_ID" class="LP_ID">'
				+'<label class="has-float-label"> <input type="hidden" id="locationId'+d+'" name="locationName" onchange="resetTyre('+d+');" class="browser-default"> '
				+'<span style="color: #2e74e6; font-size: 18px;">Location '
				+'<abbr title="required">*</abbr></span>'
				+'</label>'
				+'</div>'
				+'<div class="col col-sm-1 col-md-2">'
				+'<label class="has-float-label">'
				+'<input type="hidden" class="tyreSizeId" id="tyreSizeId'+d+'" name="tyreSizeId" >' 
				+'<input type="hidden" class="newTyreId" id="newTyreId'+d+'" name="tyreName" onclick="getTyre();" onchange ="onTyreChange(this.value,'+d+');"class="browser-default">' 
				+'<span style="color: #2e74e6; font-size: 18px;">Tyre '
				+'<abbr title="required">*</abbr></span>'
				+'</label>'
				+'<span  class="text-danger errNewTyreId" id="errNewTyreId'+d+'"></span>'
				+'</div>'
				+'<div class="col col-sm-1 col-md-2">'
				+'<label class="has-float-label"> '
				+'<input type="text" class="form-control browser-default custom-select" id="tyreGuage'+d+'" name="tyreGuage" ' 
				+'	onkeypress="return isNumberKeyWithDecimal(event,this.id);">'
				+'	<span style="color: #2e74e6; font-size: 18px;">Tyre Gauge <abbr title="required">*</abbr>'
				+'</span>'
				+'</label>'
				+'</div>'
				+'<div id="oldTyreDiv'+d+'" class="col col-sm-1 col-md-2 oldTyreDiv" style="display: none">'
				+'<label class="has-float-label"> '
				+'<input type="hidden" id="oldTyreId'+d+'" class="oldTyreId" name="oldTyreName"> '
				+'<input type="hidden" id="oldTyreLP_ID'+d+'" name="oldTyreLP_ID" class="oldTyreId">' 
				+'<input type="text" class="form-control browser-default custom-select oldTyre" id="oldTyre'+d+'" name="oldTyre" '
				+'	onkeypress="return isNumberKeyWithDecimal(event,this.id);" readonly="readonly"> '
				+'	<span style="color: #2e74e6; font-size: 18px;">Old Tyre</span>'
				+'</label>'
				+'</div>'		
				+'<div id="oldTyreMoveDiv'+d+'" class="col col-sm-1 col-md-2 oldTyreMoveDiv" style="display: none">'
				+'<label class="has-float-label"> '
				+'<select id="oldTyreMoveId'+d+'" name="oldTyreMoveName" class="browser-default custom-select oldTyreMoveName">'
				+'<option value="0">Please Select Old Tyre Move To</option>'
				+'<option value="1">Remould</option>'
				+'<option value="2">Repair</option>'
				+'<option value="3">Blast</option>'
				+'<option value="4">Scrap</option>'
				+'<option value="5">Workshop</option>'
				+'</select> '
				+'<span style="color: #2e74e6; font-size: 18px;">Old Tyre Move <abbr title="required">*</abbr> </span>'
				+'</label>'
				+'<span  class="text-danger errOldyreMoveId" id="errOldyreMoveId'+d+'"></span>'
				+'</div>'		
				+'<div class="col col-sm-1 col-md-2">'
				+'<label class="has-float-label"> '
				+'<select id="alignmentId'+d+'"  name="alignmentName" class="browser-default custom-select">'
				+'<option value="0">Please Select Alignment</option>'
				+'<option value="1">Done Automatic</option>'
				+'<option value="2">Done Manual</option>'
				+'<option value="2">Not Done</option>'
				+'</select> '
				+'<span style="color: #2e74e6; font-size: 18px;">Alignment <abbr title="required">*</abbr> </span>'
				+'</label>'
				+'<span  class="text-danger errAlignmentId" id="errAlignmentId'+d+'"></span>'
				+'</div>'
				+'<div class="col col-sm-1 col-md-2">'
				+'<label class="has-float-label"> '
				+'<select id="kinpinId'+d+'" name="kinpinName" class="browser-default custom-select">'
				+'<option value="0">Please Select Kinpin</option>'
				+'<option value="1">Working</option>'
				+'<option value="2">Not Working</option>'
				+'</select> '
				+'<span style="color: #2e74e6; font-size: 18px;">Kinpin <abbr title="required">*</abbr></span>'
				+'</label>'
				+'<span  class="text-danger errKinpinId" id="errKinpinId'+d+'"></span>'
				+'</div>'
				+' <button type="button" style="width: 50px;" class="removeAssign btn btn-danger col col-sm-1 col-sm-1"><span class="fa fa-trash"></span></button>'
				+'</div></div>'),
				$("#locationId"+d).select2({
			        minimumInputLength:0, minimumResultsForSearch:10, ajax: {
			            url:"getSearchPartLocations.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
			                return {
			                    term: a
			                }
			            }
			            , results:function(a) {
			                return {
			                    results:$.map(a, function(a) {
			                        return {
			                            text: a.partlocation_name, slug: a.slug, id: a.partlocation_id
			                        }
			                    }
			                    )
			                }
			            }
			        }
			    }),$("#newTyreId"+d).select2({
					minimumInputLength : 0, 
					minimumResultsForSearch : 10,
					ajax : {
						url : "getTyreByTyreModel.in",
						dataType : "json",
						type : "POST",
						contentType : "application/json",
						quietMillis : 50,
						data : function(e) {
							return {
								term : e,
								tyreModelId : $("#tyreModelId"+d).val(),// multiple cha logic baki aahe ligaych
								tyreStatusId : $("#assignFromId").val(),
								locationId : $("#locationId"+d).val()
							}
						},
						results : function(e) {
							if(Number($("#assignFromId").val()) == 0 || $("#assignFromId").val() == "" || $("#assignFromId").val() == undefined ){
								showMessage('info','Please Select Assign From');
								return false;
							}
							if($("#locationId"+d).val() == ""){
								showMessage('info','Please Select Location');
								return false;
							}
							
							if(e != undefined)
							return {
								results : $.map(e, function(e) {
									return {
										text : e.TYRE_NUMBER,
										slug : e.slug,
										id : e.TYRE_ID
									}
								})
							}
						}
					}
				}),$("#tyreModelId"+d).select2({
			        minimumInputLength:0, minimumResultsForSearch:10, ajax: {
			            url:"getSearchTyreSubModelByTyreSize.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
			                return {
			                	  term: a,
			                      tyreSizeConfig: $("#validateTyreSizeConfig").val(), 
			                      tyreSize: $("#tyreSizeId"+d).val() 
			                }
			            }
			            , results:function(a) {
			                return {
			                    results:$.map(a, function(a) {
			                        return {
			                        	text: a.TYRE_MODEL_SUBTYPE, slug: a.slug, id: a.TYRE_MST_ID
			                        }
			                    }
			                    )
			                }
			            }
			        }
			    })
			    
				)
				if($("#assignDiffTyreModelConfig").val() == true || $("#assignDiffTyreModelConfig").val() == 'true'){
					$('#tyreModelId'+d).select2('readonly',false);
				}else{
					$('#tyreModelId'+d).select2('readonly',true);
				}
				getVehicleTyreLayoutPosition($("#vid").val(),d);
				
		}else{
			showMessage('info','Please Select Assign From As New Or Workshop')
		}
	}), $(b).on("click", ".removeAssign", function(a) {
		a.preventDefault(), $(this).parent("div").remove(), d--
	})
});

$("#vid").change(function() {
	$(".newTyreId").select2("val", "");
	$(".child").parent().remove();
	d = 1;
	vehicleOnChange($("#vid").val());
	getVehicleTyreLayoutPosition($("#vid").val());
});

function changePosition(positionId, d){
	getChangePositionAssignTyre($("#"+positionId+"").val().split("_")[0],d);
	getChangePositionDetails($("#"+positionId+"").val().split("_")[0],d);
}

function getChangePositionAssignTyre(tyrePosition,d){
	if(d == "" || d == undefined){
		d= "";
	}
	if(Number($("#assignFromId").val()) == 1 || Number($("#assignFromId").val()) == 5){
		$("#newTyreId"+d).select2("val", "");
	}
	$("#oldTyreDiv"+d).hide();
	$("#oldTyreMoveDiv"+d).hide();
	$("#oldTyreId"+d).val(0);
	$("#oldTyreLP_ID"+d).val(0);
	$("#oldTyre"+d).val("");
	$("#oldTyreMoveId"+d+" option[value='6']").remove();
	$('#tyreModelId'+d).val($("#tyrePositionId"+d+"").val().split("_")[1]);
	$('#LP_ID'+d).val($("#tyrePositionId"+d+"").val().split("_")[2]);
	
	if($('#vid').val() != "" && tyrePosition != ""){

		var jsonObject				= new Object();
		jsonObject["vid"] 	 		= $('#vid').val();
		jsonObject["position"] 		= tyrePosition;
		jsonObject["companyId"] 	= $('#companyId').val();

		$.ajax({
			url: "getTyreAssignToVehicleDetails",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				
				if(data.vehicleTyreLayoutPosition.tyre_ID != undefined){
					$("#oldTyreDiv"+d).show();
					$("#oldTyreMoveDiv"+d).show();
					$("#oldTyreId"+d).val(data.vehicleTyreLayoutPosition.tyre_ID);
					$("#oldTyreLP_ID"+d).val(data.vehicleTyreLayoutPosition.LP_ID);
					$("#oldTyre"+d).val(data.vehicleTyreLayoutPosition.tyre_SERIAL_NO);
					if(Number($("#assignFromId").val()) == 3){
					$("#oldTyreMoveId"+d).append('<option value="6">Move To Spare</option>');
					}
				}else{
					$("#oldTyreDiv"+d).hide();
					$("#oldTyreMoveDiv"+d).hide();
					$("#oldTyreId"+d).val(0);
					$("#oldTyreLP_ID"+d).val(0);
					$("#oldTyre"+d).val("");
					$("#oldTyreMoveId"+d+" option[value='6']").remove();
				}
			},
			
			error: function (e) {
				showMessage('errors', 'Some error occured!')
				hideLayer();
			}
		});
	}
	
}

function getChangePositionDetails(tyrePosition,d){
	if(d == "" || d == undefined){
		d= "";
	}
	$("#layoutPositionTyreModel"+d).val("");
	$('#tyreModelId'+d).val($("#tyrePositionId"+d+"").val().split("_")[1]);
	
	$('#LP_ID'+d).val($("#tyrePositionId"+d+"").val().split("_")[2]);	
	if($('#vid').val() != "" && tyrePosition != ""){

		var jsonObject				= new Object();
		jsonObject["vid"] 	 		= $('#vid').val();
		jsonObject["position"] 		= tyrePosition;
		jsonObject["companyId"] 	= $('#companyId').val();

		$.ajax({
			url: "getTyreDetailsOfPosition",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				$("#layoutPositionTyreModel"+d).val(data.vehicleTyreLayoutPosition.tyreModel);
				$('#tyreModelId'+d).select2('data', {
					id : data.vehicleTyreLayoutPosition.tyreModelId,
					text : data.vehicleTyreLayoutPosition.tyreModel
				});
				$('#tyreSizeId'+d).val(data.vehicleTyreLayoutPosition.tyre_SIZE_ID);
			},
			
			error: function (e) {
				showMessage('errors', 'Some error occured!')
				hideLayer();
			}
		});
	}
	
}

$("#assignFromId").change(function() {
	$(".newTyreId").select2("val", "");
	if($("#assignFromId").val() == 4){
		$("#assignFromVidDiv").show();
		$("#locationDiv").hide();
		$("#tyreModelDiv").hide();
		$('#newTyreId').select2('readonly',true);
		$(".child").parent().remove();
		d = 1;
		
	}else if($("#assignFromId").val() == 3){
		$("#assignFromVidDiv").hide();
		$("#locationDiv").hide();
		$("#tyreModelDiv").hide();
		if( $('#vid').val() == ""){
			showMessage('info','Please Select Vehicle')
			return false;
		}
	
		$('#newTyreId').select2('readonly',true);
		$(".child").parent().remove();
		d = 1;
		
		checkSpareTyreInSameVehicle();
	}else{
		$("#assignFromVidDiv").hide();
		$("#locationDiv").show();
		$('#newTyreId').select2('readonly',false);
		$("#tyreModelDiv").show();
	}
});

function checkSpareTyreInSameVehicle(){
		$("#newTyreId").select2("val", "");
		
		var jsonObject				= new Object();
		jsonObject["vid"] 	 		= $('#vid').val();
		jsonObject["position"] 		= "SP-0";
		jsonObject["companyId"] 	= $('#companyId').val();
		$("#oldTyreMoveId option[value='6']").remove();
		$.ajax({
			url: "getTyreAssignToVehicleDetails",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				if(data.vehicleTyreLayoutPosition.tyre_ID != undefined && data.vehicleTyreLayoutPosition.tyre_ID > 0 && data.vehicleTyreLayoutPosition.tyre_ID != null){
					$('#newTyreId').select2('data', {
						id : data.vehicleTyreLayoutPosition.tyre_ID,
						text : data.vehicleTyreLayoutPosition.tyre_SERIAL_NO
					});
					if($("#oldTyre").val() != ""){
						$("#oldTyreMoveId").append('<option value="6" >Move To Spare</option>');
						$("#oldTyreMoveId").val(6);
					}else{
						$("#oldTyreMoveId option[value='6']").remove();
						$("#oldTyreMoveId").val(0);
					}
				
				}else{
					showMessage('info','Vehicle Does Not Have Own Spare Tyre')
				}
				
			},
			error: function (e) {
				showMessage('errors', 'Some error occured!')
				hideLayer();
			}
		});
	}




$("#assignFromVid").change(function() {
	checkSpareTyreInOtherVehicle()
});


function checkSpareTyreInOtherVehicle(){
	$("#newTyreId").select2("val", "");
	var vehicleId = 0;
		if($('#assignFromVid').val() == $('#vid').val()){
			showMessage('info','Assign From Vehicle And Assign To Vehicle Is Same Kindly Select Assign From Own Vehicle')
			$('#assignFromId').val(3);
			$("#assignFromVidDiv").hide();
			vehicleId = $('#vid').val();
			
		}else{
			vehicleId = $('#assignFromVid').val();
		}

		var jsonObject				= new Object();
		jsonObject["vid"] 	 		= vehicleId;
		jsonObject["position"] 		= "SP-0";
		jsonObject["companyId"] 	= $('#companyId').val();

		$.ajax({
			url: "getTyreAssignToVehicleDetails",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				if(data.vehicleTyreLayoutPosition.tyre_ID != undefined && data.vehicleTyreLayoutPosition.tyre_ID > 0 && data.vehicleTyreLayoutPosition.tyreModelId != $("#tyreModelId").val() && $('#assignDiffTyreModelConfig').val() == false){
					showMessage('info','Tyre Model Is Different For The Position Kindly Select Another Vehicle OR Position')
					$("#assignFromVid").select2("val", "");
					return false;
				}else if(data.vehicleTyreLayoutPosition.tyre_ID != undefined && data.vehicleTyreLayoutPosition.tyre_ID > 0 ){
					$('#newTyreId').select2('data', {
						id : data.vehicleTyreLayoutPosition.tyre_ID,
						text : data.vehicleTyreLayoutPosition.tyre_SERIAL_NO
					});
					$("#newTyreId").prop('readonly',true);
					$('#otherVehicleLP_ID').val(data.vehicleTyreLayoutPosition.lp_ID);
				}else {
					showMessage('info','Vehicle Does Not Have Spare Tyre Kindly Select Another Vehicle ')
					$("#assignFromVid").select2("val", "");
					return false;
				}
				
			},
			error: function (e) {
				showMessage('errors', 'Some error occured!')
				hideLayer();
			}
		});
	}

function resetValidateField(){
	$("#errAssignDate").html('');
	$("#errAssignVehicle").html('');
	$("#errAssignOdo").html('');
	$("#errAssignFrom").html('');
	$("#errAssignFromVehicle").html('');
}
function checkAllField(){
	if($("#assignDate").val() == "" || $("#assignDate").val() == undefined ){
		$("#errAssignDate").html('Please Select Assign Date');
	}
	if(Number($("#vid").val()) == 0 || $("#vid").val() == "" || $("#vid").val() == undefined ){
		$("#errAssignVehicle").html('Please Select Vehicle');
	}
	if(Number($("#vehicleOdometer").val()) == 0 || $("#vehicleOdometer").val() == "" || $("#vehicleOdometer").val() == undefined ){
		$("#errAssignOdo").html('Please Select Vehicle Odometer');
	}
	if(Number($("#assignFromId").val()) == 0 || $("#assignFromId").val() == "" || $("#assignFromId").val() == undefined ){
		$("#errAssignFrom").html('Please Select Assign From');
	}
	if($("#assignFromId").val() == 4 ){
		if( Number($("#assignFromVid").val()) == 0 || $("#assignFromVid").val() == "" || $("#assignFromVid").val() == undefined){
			$("#errAssignFromVehicle").html('Please Select Assign From Vehicle');
		}
	}
}
function resetMultipleValidateField(){
	$(".errTyrePositionId").html('');
	$(".errKinpinId").html('');
	$(".errAlignmentId").html('');
	$(".errOldyreMoveId").html('');
	$(".errNewTyreId").html('');
}

function multipleValidateField(){
	
	var positionArr	 		= new Array();
	
	$("select[name=tyrePositionName]").each(function(){
		positionArr.push($(this).val());
	});
	
	
	for(var i = 0 ; i< positionArr.length; i++){
		var count = i;
		if(i == 0){
			if(	$("#tyrePositionId").val() == 0 || $("#tyrePositionId").val() == "" || $("#tyrePositionId").val() == undefined){
				$("#errTyrePositionId").html('Please Select Tyre Position');
			}
			if(	$("#tyreModelId").val() == 0 || $("#tyreModelId").val() == "" || $("#tyreModelId").val() == undefined){
				$("#errTyreModelId").html('Please Select Tyre Model');
			}
			if(	$("#newTyreId").val() == 0 || $("#newTyreId").val() == "" || $("#newTyreId").val() == undefined){
				$("#errNewTyreId").html('Please Select Tyre');
			}
			if(	$("#oldTyreMoveId").val() == 0 || $("#oldTyreMoveId").val() == "" || $("#oldTyreMoveId").val() == undefined){
				$("#errOldyreMoveId").html('Please Select Old Tyre Move To');
			}
			if(	$("#alignmentId").val() == 0 || $("#alignmentId").val() == "" || $("#alignmentId").val() == undefined){
				$("#errAlignmentId").html('Please Select Alignment');
			}
			
			if($("#tyrePositionId").val() == "" && $("#tyrePositionId").val() == undefined){
				if(((($("#tyrePositionId").val().split("_")[0]).split("-")[1]) == "1") && ($("#kinpinId").val() == 0 )){
					$("#errKinpinId").html('Please Select Kinpin');
				}
			}
				
			/*if(	$("#tyrePositionId").val() == 0 || $("#tyrePositionId").val() == "" || $("#tyrePositionId").val() == undefined && ($("#tyrePositionId").val().split("_")[0]).split("-")[1] == 1){
				
				if(	$("#kinpinId").val() == 0 || $("#kinpinId").val() == "" || $("#kinpinId").val() == undefined){
					$("#errKinpinId").html('Please Select Kinpin');
				}
			}*/
		}else{
			if(	$("#tyrePositionId"+(count+1)).val() == 0 || $("#tyrePositionId"+(count+1)).val() == "" || $("#tyrePositionId"+(count+1)).val() == undefined){
				$("#errTyrePositionId"+(count+1)).html('Please Select Tyre Position');
			}
			if(	$("#tyreModelId"+(count+1)).val() == 0 || $("#tyreModelId"+(count+1)).val() == "" || $("#tyreModelId"+(count+1)).val() == undefined){
				$("#errTyreModelId"+(count+1)).html('Please Select Tyre Model');
			}
			if(	$("#newTyreId"+(count+1)).val() == 0 || $("#newTyreId"+(count+1)).val() == "" || $("#newTyreId"+(count+1)).val() == undefined){
				$("#errNewTyreId"+(count+1)).html('Please Select Tyre');
			}
			if(	$("#oldTyreMoveId"+(count+1)).val() == 0 || $("#oldTyreMoveId"+(count+1)).val() == "" || $("#oldTyreMoveId"+(count+1)).val() == undefined){
				$("#errOldyreMoveId"+(count+1)).html('Please Select Old Tyre Move To');
			}
			if(	$("#alignmentId"+(count+1)).val() == 0 || $("#alignmentId"+(count+1)).val() == "" || $("#alignmentId"+(count+1)).val() == undefined){
				$("#errAlignmentId"+(count+1)).html('Please Select Alignment');
			}
			
			if($("#tyrePositionId"+(count+1)).val() == "" && $("#tyrePositionId"+(count+1)).val() == undefined){
				if(($("#tyrePositionId"+(count+1)).val().split("_")[0]).split("-")[1] == 1 && ($("#kinpinId"+(count+1)).val() == 0 || $("#kinpinId"+(count+1)).val() == "" || $("#kinpinId"+(count+1)).val() == undefined)){
					$("#errKinpinId"+(count+1)).html('Please Select Kinpin');
				}
			}
			
			
			console.log("dfsdf",	$("#tyrePositionId"+(count+1)).val())
			/*if(	$("#tyrePositionId"+(count+1)).val() == 0 || $("#tyrePositionId"+(count+1)).val() == "" || $("#tyrePositionId"+(count+1)).val() == undefined && ($("#tyrePositionId"+(count+1)).val().split("_")[0]).split("-")[1] == 1) {
				if(	$("#kinpinId"+(count+1)).val() == 0 || $("#kinpinId"+(count+1)).val() == "" || $("#kinpinId"+(count+1)).val() == undefined){
					$("#errKinpinId"+(count+1)).html('Please Select Kinpin');
				}
			}*/
			
		}
		
	}
}


$(document).ready(
		function($) {
			$('button[id=submit]').click(function(e) {
				$("#assignedDiv").hide();
				e.preventDefault();
				showLayer();
				resetValidateField();
				checkAllField();
				
				if($("#assignDate").val() == "" || $("#assignDate").val() == undefined ){
					showMessage('info','Please Select Assign Date');
					$("#assignedDiv").show();
					hideLayer();
					return false;
				}
				if(Number($("#vid").val()) == 0 || $("#vid").val() == "" || $("#vid").val() == undefined ){
					showMessage('info','Please Select Vehicle');
					$("#assignedDiv").show();
					hideLayer();
					return false;
				}
				if(Number($("#vehicleOdometer").val()) == 0 || $("#vehicleOdometer").val() == "" || $("#vehicleOdometer").val() == undefined ){
					showMessage('info','Please Select Vehicle Odometer');
					$("#assignedDiv").show();
					hideLayer();
					return false;
				}
				if(Number($("#assignFromId").val()) == 0 || $("#assignFromId").val() == "" || $("#assignFromId").val() == undefined ){
					showMessage('info','Please Select Assign From');
					$("#assignedDiv").show();
					hideLayer();
					return false;
				}
				if($("#assignFromId").val() == 4 ){
					if( Number($("#assignFromVid").val()) == 0 || $("#assignFromVid").val() == "" || $("#assignFromVid").val() == undefined){
						showMessage('info','Please Select Assign From Vehicle');
						$("#assignedDiv").show();
						hideLayer();
						return false;
					}
				}
				
				var jsonObject			= new Object();
				var tyreArr	 			= new Array();
				var tyreNumberArr		= new Array();
				var positionArr	 		= new Array();
				var locationArr	 		= new Array();
				var tyreIdArr	 		= new Array();
				var LP_IDArr	 		= new Array();
				var tyreModelIdArr	 	= new Array();
				var oldTyreArr	 		= new Array();
				var oldTyreNumberArr	= new Array();
				var oldTyreMoveArr	 	= new Array();
				var oldTyreLP_IDArr	 		= new Array();
				var guageArr	 		= new Array();
				var alignmentArr	 	= new Array();
				var kinpinArr	 		= new Array();
				
				$("select[name=tyrePositionName]").each(function(){
					positionArr.push($(this).val());
				});
				$("input[name=locationName]").each(function(){
					locationArr.push($(this).val());
				});
				$("input[name=tyreName]").each(function(){
					tyreIdArr.push($(this).val());
					if($(this).val() != "" && $(this).val() != null && $(this).val() != undefined){
						tyreNumberArr.push($('#'+this.id).select2('data').text);
					}
				});
				$("input[name=LP_ID]").each(function(){
					LP_IDArr.push($(this).val());
				});
				$("input[name=tyreModelId]").each(function(){
					tyreModelIdArr.push($(this).val());
				});
				$("input[name=tyreGuage]").each(function(){
					guageArr.push($(this).val().replace(/"/g, ""));
				});
				$("input[name=oldTyreName]").each(function(){
					oldTyreArr.push($(this).val());
				});
				$("input[name=oldTyreLP_ID]").each(function(){
					oldTyreLP_IDArr.push($(this).val());
				});
				$("input[name=oldTyre]").each(function(){
					oldTyreNumberArr.push($(this).val());
				});
				$("select[name=oldTyreMoveName]").each(function(){
					oldTyreMoveArr.push($(this).val());
				});
				$("select[name=alignmentName]").each(function(){
					alignmentArr.push($(this).val());
				});
				$("select[name=kinpinName]").each(function(){
					kinpinArr.push($(this).val());
				});
				resetMultipleValidateField();
				multipleValidateField(positionArr);
				
				if((new Set(positionArr)).size !== positionArr.length){// check any of the position is duplicate
					 showMessage('info','Postion Can Not Be Same');
					 $("#assignedDiv").show();
						hideLayer();
					return false;
				}
				if((new Set(tyreIdArr)).size !== tyreIdArr.length){
					 showMessage('info','Tyre Can Not Be Duplicate');
					 $("#assignedDiv").show();
						hideLayer();
					return false;
				}
				
				for(var i = 0 ; i< positionArr.length; i++){
					var tyreDetails					= new Object();
					
					if(positionArr[i] == 0 || positionArr[i] == "" || positionArr[i] == undefined){
						showMessage('info','Please Select Vehicle Tyre Position');
						$("#assignedDiv").show();
						hideLayer();
						return false;
					}
					
					
					if(tyreIdArr[i] == 0 || tyreIdArr[i] == "" || tyreIdArr[i] == undefined){
						showMessage('info','Please Select Tyre Number');
						$("#assignedDiv").show();
						hideLayer();
						return false;
					}
					
					if(tyreModelIdArr[i] == 0 || tyreModelIdArr[i] == "" || tyreModelIdArr[i] == undefined){
						showMessage('info','Please Select Tyre Model');
						$("#assignedDiv").show();
						hideLayer();
						return false;
					}
					
					if(oldTyreArr[i] != 0 && oldTyreArr[i] != "" ){
						if(oldTyreMoveArr[i] == 0 || oldTyreMoveArr[i] == "" || oldTyreMoveArr[i] == undefined){
							showMessage('info','Please Select Old tyre Move To');
							$("#assignedDiv").show();
							hideLayer();
							return false;
						}
					}
					if(alignmentArr[i] == 0 || alignmentArr[i] == "" || alignmentArr[i] == undefined){
						showMessage('info','Please Select Alignment');
						$("#assignedDiv").show();
						hideLayer();
						return false;
					}
					if(((positionArr[i].split("_")[0]).split("-")[1]) == 1 &&  (kinpinArr[i] == 0 || kinpinArr[i] == "" || kinpinArr[i] == undefined)){
						showMessage('info','Please Select Kinpin');
						$("#assignedDiv").show();
						hideLayer();
						return false;
					}
					
					tyreDetails.tyrePositionId			= positionArr[i].split("_")[0];
					tyreDetails.newTyreId				= tyreIdArr[i];
					tyreDetails.newTyreNumber			= tyreNumberArr[i];
					tyreDetails.LP_ID					= positionArr[i].split("_")[2];
					tyreDetails.tyreModelId				= tyreModelIdArr[i];
					tyreDetails.oldTyreId				= oldTyreArr[i];
					tyreDetails.oldTyreLP_ID			= oldTyreLP_IDArr[i];
					tyreDetails.oldTyreNumber			= oldTyreNumberArr[i];
					tyreDetails.oldTyreMoveId			= oldTyreMoveArr[i];
					tyreDetails.tyreGauge				= guageArr[i];
					tyreDetails.alignmentId				= alignmentArr[i];
					tyreDetails.kinpinId				= kinpinArr[i];
					
					tyreArr.push(tyreDetails);
					
				}
				
				jsonObject["recentNoOfAssignTyre"] 			= positionArr.length;
				jsonObject["assignDate"] 			= $('#assignDate').val();
				jsonObject["vid"] 	 				= $('#vid').val();
				jsonObject["vehicleOdometer"] 	 	= $('#vehicleOdometer').val();
			//	jsonObject["tyreTypeId"] 			= $('#tyreTypeId').val();
				jsonObject["assignFromId"] 			= $('#assignFromId').val();
				jsonObject["remark"] 				= $('#remark').val();
				jsonObject["vehicleReg"] 			= $('#vid').select2('data').text;
				jsonObject["companyId"] 			= $('#companyId').val();
				jsonObject["userId"] 				= $('#userId').val();
				jsonObject["otherVehicleLP_ID"] 	= $('#otherVehicleLP_ID').val();// not applicable for multiple
				jsonObject["assignFromVid"] 		= $('#assignFromVid').val();// not applicable for multiple
			
				jsonObject["transactionId"] 		= $('#transactionId').val();// not applicable for multiple
				jsonObject["transactionSubId"] 		= $('#transactionSubId').val();// not applicable for multiple
				jsonObject["transactionTypeId"] 	= $('#transactionTypeId').val();// not applicable for multiple
				jsonObject["assignDiffTyreModelConfig"] 	= $('#assignDiffTyreModelConfig').val();// not applicable for multiple
				/*jsonObject["validateDoublePost"] 	 =  true;
				jsonObject["unique-one-time-token"]  =  $('#accessToken').val();*/
				
				jsonObject.tyreDetails 				= JSON.stringify(tyreArr);

				console.log("jsonObject",jsonObject)
				
				$.ajax({
					url: "tyreAssignToVehicle",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						 if(data.tyreModelNotSame != undefined && (data.tyreModelNotSame == true || data.tyreModelNotSame == 'true')){
							showMessage('info','Tyre Model Is Different Please Change Tyre Position ')
							$("#assignedDiv").show();
						//	$('#accessToken').val(data.accessToken); 		
							hideLayer();
						}else if(data.differentSize != undefined && (data.differentSize == true || data.differentSize == 'true')){
							showMessage('info','Tyre Size Is Different Please Change Tyre Position ')
							$("#assignedDiv").show();
						//	$('#accessToken').val(data.accessToken); 		
							hideLayer();
						}  
							else{
							showMessage('info','Tyre Assign Successfully')
							if($("#mountVehicleId").val() != "" && $("#mountVehicleId").val() != undefined && $('#transactionTypeId').val() == ""){
								location.replace("showVehicleTyreAssignedDetails?id="+data.vid+"");
							}else if(Number($('#transactionTypeId').val()) == 1 ){
								location.replace("showIssues?Id="+$('#encIssueId').val()+"");
							}else if(Number($('#transactionTypeId').val()) == 2 ){
								location.replace("showWorkOrder?woId="+$('#transactionId').val()+"");
							}else if(Number($('#transactionTypeId').val()) == 3 ){
								location.replace("showDealerServiceEntries?dealerServiceEntriesId="+$('#transactionId').val()+"");
							}else{
								
								resetFields();
						
								$('#recentAssignVehicleId').removeClass('hide')
								$("#recentAssignVehicleNumber").html(data.vehicleReg);
								$("#recentNoOfAssignTyre").html(data.recentNoOfAssignTyre);
								$("#recentAssignVehicleId").attr("href", "showVehicleTyreAssignedDetails.in?id="+data.vid+"")
						
								hideLayer();
								$("#assignedDiv").show();
								$('#back-to-top').trigger('click');
							}
						
						}
					},
					error: function (e) {
						showMessage('errors', 'Some error occured!')
						hideLayer();
					}
				});
			
				
			})
		});
			

function showAvailableTyre(){
	if(Number($("#assignFromId").val()) == 0 || $("#assignFromId").val() == "" || $("#assignFromId").val() == undefined ){
		showMessage('info','Please Select Assign From')
		return false;
	}
	if(Number($("#vid").val()) == 0 || $("#vid").val() == "" || $("#vid").val() == undefined ){
		showMessage('info','Please Select Vehicle')
		return false;
	}
	
		var jsonObject						= new Object();
		
		jsonObject["vehicleModelId"] 	 	=  $('#vid').select2('data').vehicleModelId;
		jsonObject["assignFromId"] 			=  $("#assignFromId").val();
		jsonObject["companyId"] 			=  $("#companyId").val();
		
		$.ajax({
			url: "getAvailabeTyreForAssignment",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				if(data != undefined){
					setAvailableTyre(data);
				}
			},
			error: function (e) {
				showMessage('errors', 'Some error occured!',e)
				hideLayer();
			}
		});

}

function setAvailableTyre(data){
	$("#availableTyreTable").empty();
	
	var availableTyreList = data.availableTyreList;
	
	if(availableTyreList != undefined || availableTyreList != null){
		for(var index = 0 ; index < availableTyreList.length; index++){
			
			var columnArray = new Array();
			columnArray.push("<td class='fit'>"+(index+1)+"</td>");
			columnArray.push("<td class='fit'><a href='showTyreInfo?Id="+availableTyreList[index].tyre_ID+"'  target='_blank'>"+ availableTyreList[index].tyre_NUMBER  +"<a></td>");
			columnArray.push("<td class='fit'>"+ availableTyreList[index].tyre_MODEL  +"</td>");
			columnArray.push("<td class='fit'>"+ availableTyreList[index].warehouse_LOCATION  +"</td>");
			
			$('#availableTyreTable').append("<tr >" + columnArray.join(' ') + "</tr>");
			
		}
		$("#tyreModelName").html(availableTyreList[0].tyre_MODEL);
		
		$('#availableTyreModal').show();
		columnArray = [];
		}else{
			showMessage('info','No Record Found!')
		}
	
}

function closeModal(){
	$('#availableTyreModal').hide();
}


$("#assignTyreCount").mouseenter(function(){
	if($("#vid").val() == "" || $("#vid").val() == undefined ){
		showMessage('info','Please Select Vehicle');
		return false;
	}
	setTimeout(function(){ 
		$("#assignTyreModal").modal('show');	
	}, 500);
	
  });
  /*$("#assignTyreCount").mouseleave(function(){
	  setTimeout(function(){ 
			$("#assignTyreModal").modal('hide');	
		}, 500);
  });*/


$("#newTyreId").change(function() {
	onTyreChange($('#newTyreId').val());
});
function onTyreChange(newTyreId,d) {
	if(d == undefined ||  d == ""){
		d= "";
	}
	console.log("newTyreId",newTyreId)
	if(newTyreId != undefined && newTyreId != ""){
		if(d == ""){
			$("#tyreDiv"+d).css({"border-style": "double", "border-color": "black"});
		}else{
			$("#tyreDiv"+d).css({"border-style": "double", "border-color": "green"});		
			
		}
	}else{
		$("#tyreDiv"+d).css({"border-style": "none", "border-color": "none"});	
	}
	
	$("#tyreGuage"+d).val(0);	
	if($('#vid').val() != "" && Number($('#assignFromId').val()) == 1){

		var jsonObject				= new Object();
		jsonObject["tyreId"] 	 	= $('#newTyreId'+d).val();
		jsonObject["companyId"] 	= $('#companyId').val();

		$.ajax({
			url: "getTyreGuageByTyreId",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				if(data.inventoryTyre.tyreGuage != undefined){
					$("#tyreGuage"+d).val(data.inventoryTyre.tyreGuage);
				}else{
					$("#tyreGuage"+d).val(0);
				}
			},
			error: function (e) {
				showMessage('errors', 'Some error occured!')
				hideLayer();
			}
		});
	}

}

function resetTyre(d){
	if(d == undefined ||  d == ""){
		d= "";
	}
	
	$("#newTyreId"+d).select2("val", "");
}

