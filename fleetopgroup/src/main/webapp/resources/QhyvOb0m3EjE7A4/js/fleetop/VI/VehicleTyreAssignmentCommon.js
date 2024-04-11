$(document).ready(function() {
	$("#vid,#rotateVid,#assignFromVid,#layoutVid").select2( {
		minimumInputLength:2, minimumResultsForSearch:10, ajax: {
			url:"getActiveVehicleDropdown.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
				return {
					term: a
				}
			}
		, results:function(a) {
			return {
				results:$.map(a, function(a) {
					return {
						text: a.vehicle_registration, slug: a.slug, id: a.vid, vehicleModelId: a.vehicleModelId
					}
				})
			}
		}
	}
});
	
if($("#showPosition").val() != "" && $("#showPosition").val() != undefined && $("#mountVehicleId").val() != "" && $("#mountVehicleId").val() != undefined  )	{
	$('#tyrePositionId').attr("disabled", true);
}
if($("#mountVehicleId").val() != "" && $("#mountVehicleId").val() != undefined ){
	$('#operationId').attr("disabled", true);
	$("#moreAssign").hide();
	
	$('#vid').select2('data', {
		id :$("#mountVehicleId").val(),
		text : $("#vehicleReg").val(),
		vehicleModelId: $("#showVehicleModelId").val()
	});
	
	vehicleOnChange($("#vid").val());
	getVehicleTyreLayoutPosition($("#vid").val());
	
}
var currentDate = new Date();

$("#tyreAsignDate1, #tyreRotateDate1").datepicker({
    defaultDate: currentDate,
    autoclose: !0,
    todayHighlight: true,
    format: "dd-mm-yyyy",
    setDate: "0",
    endDate: currentDate,
    maxDate: currentDate
}), $("#newTyreId").select2({
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
					tyreModelId : $("#tyreModelId").val(),
					tyreStatusId : $("#assignFromId").val(),
					locationId : $("#locationId").val()
				}
			},
			results : function(e) {
				if($("#locationId").val() == ""){
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
	}),$("#locationId").select2( {
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
    }),$("#tyreModelId").select2({
        minimumInputLength:0, minimumResultsForSearch:10, ajax: {
            url:"getSearchTyreSubModelByTyreSize.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a,
                    tyreSizeConfig: $("#validateTyreSizeConfig").val(), 
                    tyreSize: $("#tyreSizeId").val() 
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
});


function vehicleOnChange(vid){
	$.getJSON("getVehicleOdoMerete.in", {
		vehicleID: vid,
		ajax: "true"
	}, function(a) {
		if(a.vehicle_Odometer != undefined && a.vehicle_Odometer != ""){
			if($("#operationId").val() == 1){
				$('#vehicleOdometer').val(a.vehicle_Odometer);
			}else if($("#operationId").val() == 2){
				$('#rotateOdometer').val(a.vehicle_Odometer);
			}
		}else{
			if($("#operationId").val() == 1){
				$('#vehicleOdometer').val(0);
			}else if($("#operationId").val() == 2){
				$('#rotateOdometer').val(0);
			}
		}
		
	})

}

function getVehicleTyreLayoutPosition(vid,d){
		var jsonObject						= new Object();
		jsonObject["vid"] 	 				=  vid;
		jsonObject["companyId"] 			=  $('#companyId').val();
		$.ajax({
			url: "getVehicleTyreLayoutPosition",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				if(data != undefined){
					setVehicleTyreLayoutPosition(data,d);
					setAllAssignedTyre(data);
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

	function setVehicleTyreLayoutPosition(data,d){
		var modelId 			= 0;// this will use if mount form vehicle show page
		var lpid 				= 0;// this will use if mount form vehicle show page
		if(d == "" || d == undefined){
			d= "";
		}
		$("#newTyreId"+d).select2("val", "");
		
		var vehicleTyreLayoutPositionList  = data.vehicleTyreLayoutPositionList;
		
		if(vehicleTyreLayoutPositionList != undefined){
			$('#tyrePositionId'+d).empty();
			
			if($("#showPosition").val() != "" && $("#showPosition").val() != undefined ){
				getAssignedTyreByPosition($("#showPosition").val());
				getTyreDetailsByPosition($("#showPosition").val());// to set model and lpid and position 
			}else{
				for(var i = 0; i< vehicleTyreLayoutPositionList.length; i++){
					if(vehicleTyreLayoutPositionList[i].axle == 1){
						if(vehicleTyreLayoutPositionList[i].position.split("-")[0]  == "LO"){
							$('#tyrePositionId'+d).append($("<option></option>").attr("value", vehicleTyreLayoutPositionList[i].position+"_"+vehicleTyreLayoutPositionList[i].tyreModelId+"_"+vehicleTyreLayoutPositionList[i].lp_ID).text("Front left"));
						}else{
							$('#tyrePositionId'+d).append($("<option></option>").attr("value", vehicleTyreLayoutPositionList[i].position+"_"+vehicleTyreLayoutPositionList[i].tyreModelId+"_"+vehicleTyreLayoutPositionList[i].lp_ID).text("Front right"));
						}
							
					}else if(vehicleTyreLayoutPositionList[i].axle == 0){
						$('#tyrePositionId'+d).append($("<option></option>").attr("value", vehicleTyreLayoutPositionList[i].position+"_"+vehicleTyreLayoutPositionList[i].tyreModelId+"_"+vehicleTyreLayoutPositionList[i].lp_ID).text("Bus Spare"));
					}else{
						$('#tyrePositionId'+d).append($("<option></option>").attr("value", vehicleTyreLayoutPositionList[i].position+"_"+vehicleTyreLayoutPositionList[i].tyreModelId+"_"+vehicleTyreLayoutPositionList[i].lp_ID).text("Rear "+vehicleTyreLayoutPositionList[i].position));
					}
				}	
				
				if( $("#showPosition").val() != "" && vehicleTyreLayoutPositionList[i].position == $("#showPosition").val()){
					 modelId = vehicleTyreLayoutPositionList[i].tyreModelId;
					 lpid = vehicleTyreLayoutPositionList[i].lp_ID;
				 }
				
				getAssignedTyreByPosition(vehicleTyreLayoutPositionList[0].position,d);// check tyre is already assign to the positon
				
				$("#layoutPositionTyreModel"+d).val(vehicleTyreLayoutPositionList[0].tyreModel); // use to find tyre by model id
				$("#LP_ID"+d).val(vehicleTyreLayoutPositionList[0].lp_ID); // while saving this will use for updation  of particular vehicletyreposition
				
				
			    $('#tyreModelId'+d).select2('data', {
					id : vehicleTyreLayoutPositionList[0].tyreModelId,
					text : vehicleTyreLayoutPositionList[0].tyreModel
				});
				$('#tyreSizeId'+d).val(vehicleTyreLayoutPositionList[0].tyre_SIZE_ID);
			}
			
			
		}else{
			$('.tyrePositionId').empty();
			$("#newTyreId").select2("val", "");
		}
	}
	
	
	function getTyreDetailsByPosition(tyrePosition){

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
					$("#layoutPositionTyreModel").val(data.vehicleTyreLayoutPosition.tyreModel);
					$('#tyreModelId').select2('data', {
						id : data.vehicleTyreLayoutPosition.tyreModelId,
						text : data.vehicleTyreLayoutPosition.tyreModel
					});
					console.log("fdfdfdfd",data.vehicleTyreLayoutPosition.tyre_SIZE_ID)
					$('#LP_ID').val(data.vehicleTyreLayoutPosition.lp_ID)
					
					$('#tyrePositionId').append($("<option></option>").attr("value", $("#showPosition").val()+"_"+data.vehicleTyreLayoutPosition.tyreModelId+"_"+data.vehicleTyreLayoutPosition.lp_ID).text($("#positionStr").val()));
					$('#tyreSizeId').val(data.vehicleTyreLayoutPosition.tyre_SIZE_ID);
				},
				
				error: function (e) {
					showMessage('errors', 'Some error occured!')
					hideLayer();
				}
			});
		}
		

	}
	
	function setAllAssignedTyre(data){
		$("#assignTyreDetailsTable").empty();
		var vehicleTyreLayoutPositionList  	= data.vehicleTyreLayoutPositionList;
		var index							= 0;
		var assignTyreCount					= 0;
		
		if(vehicleTyreLayoutPositionList != undefined){
			for(var i = 0; i< vehicleTyreLayoutPositionList.length; i++){
				 // Already Assign Tyre list
				var columnArray = new Array();
				if(vehicleTyreLayoutPositionList[i].tyre_ID != undefined && vehicleTyreLayoutPositionList[i].tyre_ID > 0){
				
					columnArray.push("<td class='fit'>"+(index+1)+"</td>");
					columnArray.push("<td class='fit'ar>"+ vehicleTyreLayoutPositionList[i].position  +"</td>");
					columnArray.push("<td class='fit'ar><a href='showTyreInfo?Id="+vehicleTyreLayoutPositionList[i].tyre_ID+"'  target='_blank'>"+ vehicleTyreLayoutPositionList[i].tyre_SERIAL_NO  +"<a></td>");
					
					$('#assignTyreDetailsTable').append("<tr>" + columnArray.join(' ') + "</tr>");
					assignTyreCount += 1;
					index++;
					
					}
				}
				$("#assignTyreCount").html(assignTyreCount);
			}
		}
	
	
	function getAssignedTyreByPosition(tyrePosition,d){
		if(d == "" || d == undefined){
			d= "";
		}
		$("#oldTyreDiv"+d).hide();
		$("#oldTyreMoveDiv"+d).hide();
		$("#oldTyreId"+d).val(0);
		$("#oldTyre"+d).val("");
		//$('#LP_ID'+d).val($("#tyrePositionId"+d+"").val().split("_")[2]);
		
		
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
						$("#oldTyre"+d).val(data.vehicleTyreLayoutPosition.tyre_SERIAL_NO);
						if($("#assignFromId").val() == 3){
							$("#oldTyreMoveId"+d).append('<option value="6">Move To Spare</option>');
						}
						
					}else{
						$("#oldTyreMoveId"+d+" option[value='6']").remove();
						$("#oldTyreDiv"+d).hide();
						$("#oldTyreMoveDiv"+d).hide();
						$("#oldTyreId"+d).val(0);
						$("#oldTyre"+d).val("");
					}
				
				},
				error: function (e) {
					showMessage('errors', 'Some error occured!')
					hideLayer();
				}
			});
		}
		
		$("#newTyreId"+d).select2({
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
    	})
	}
	
	$("#operationId").change(function() {
		if($("#operationId").val() == 2){
			$("#assignmentDiv").hide();
			$("#assignedDiv").hide();
			$(".rotationDiv").show();
			$(".addMoreRotationTyreDiv").show();
			$("#rotateDiv").show();	
			$("#availableTyre").hide();
			
		}else{
			resetFields();
			$("#assignmentDiv").show();
			$("#assignedDiv").show();
			$(".rotationDiv").hide();
			$(".addMoreRotationTyreDiv").hide();
			$("#rotateDiv").hide();
			$("#availableTyre").show();
		}
		$(".child").parent().remove();
		d = 1;
	});
	
	function showVehicleTyreLayout(){
		if($("#layoutVid").val() != ""){
			window.open("showVehicleTyreAssignedDetails.in?id="+$("#layoutVid").val()+" ", '_blank');
		}
		$("#layoutVid").select2("val", "");
	}
	
	function getDateWiseOdometer(){
		var vid = 0;
		var date = "" ;
		if($("#operationId").val() == 1){
			vid = $("#vid").val();
			date = $("#assignDate").val();
		}else if($("#operationId").val() == 2){
			vid = $("#rotateVid").val();
			date = $("#rotationDate").val();
		}
		$.getJSON("getDateWiseVehicleOdometer.in", {
			vid: vid,
			date: date,
			ajax: "true"
		}, function(a) {
			if(a.vehicle_Odometer != undefined && a.vehicle_Odometer != ""){
				if($("#operationId").val() == 1){
					$('#vehicleOdometer').val(a.vehicle_Odometer);
				}else if($("#operationId").val() == 2){
					$('#rotateOdometer').val(a.vehicle_Odometer);
				}
			}else{
				if($("#operationId").val() == 1){
					$('#vehicleOdometer').val(0);
				}else if($("#operationId").val() == 2){
					$('#rotateOdometer').val(0);
				}
			}
		})
	}
	
	function resetFields(){
		
		$("#assignDate").val('');
		$("#vid").select2("val", "");
		$("#layoutVid").select2("val", "");
		$("#vehicleOdometer").val('');
		$("#remark").val('');
		$("#assignFromId").val(0);
		$("#tyrePositionId").val('');
		$("#layoutPositionTyreModel").val('');
		$("#assignFromVidDiv").hide();
		$("#locationId").select2("val", "");
		$('#newTyreId').select2('readonly',false);
		$("#newTyreId").select2("val", "");
		$("#tyreModelId").select2("val", "");
		$("#tyreGuage").val(0);
		$("#oldTyreDiv").hide();
		$("#oldTyreMoveDiv").hide();
		$("#alignmentId").val(0);
		$("#kinpinId").val(0);
		
		$(".child").parent().remove();
		d = 1;
	}
	
	function tyreModelChange(d){
		if(d == "" || d == undefined){
			d= "";
		}
		$("#newTyreId"+d).select2("val", "");
		
	}
