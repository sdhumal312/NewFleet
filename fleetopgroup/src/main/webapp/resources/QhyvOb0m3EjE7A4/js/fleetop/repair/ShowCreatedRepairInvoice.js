  var currentDate = new Date();
	if($("#serverDate").val() != undefined)
	{
		defaultServerDate = $("#serverDate").val();
	}else{
		defaultServerDate = new Date();
	}
	
$(document).ready(function(){
	if($("#repairTypeId").val() == 1){
		$(".dateOfRemoveDiv").hide();
	}
});
$(document).ready(function(){
	$("#partId").select2({
		minimumInputLength: 2,
		minimumResultsForSearch: 10,
		ajax: {
			url: "getRepairablePartListByLocation.in",
			dataType: "json",
			type: "POST",
			contentType: "application/json",
			quietMillis: 50,
			data: function(a) {
				return {
					term: a
				}
			},
			results: function(a) {
				console.log("a",a)
				return {
					results: $.map(a, function(a) {
						return $("#locationName").val() == a.location ? {
							text: a.location + " - " + a.partnumber + " - " + a.partname + " - " + a.partManufacturer + " - " + a.location_quantity,
							slug: a.slug,
							id: a.inventory_location_id,
							warranty : a.isWarrantyApplicable,
							repairable : a.isRepairable,
							partId : a.partid,
							locationId : a.locationId,
							maxQuantity : a.location_quantity,
							inventory_id : a.inventory_id
						} : {
							text: a.location + " - " + a.partnumber + " - " + a.partname + " - " + a.partManufacturer + " - " + a.location_quantity,
							slug: a.slug,
							id: a.inventory_location_id,
							disabled: !0,
							warranty : a.isWarrantyApplicable,
							repairable : a.isRepairable,
							partId : a.partid,
							locationId : a.locationId,
							maxQuantity : a.location_quantity,
							inventory_id : a.inventory_id
						}
					})
				}
			}
		}
})

})


var partType 			= 1;
var labourType 			= 2;
$(document).ready(function() {
	var a = 500,
	b = $(".addMoreLabourDiv"),
	c = $(".addMoreLabourButton"),
	d = 1;
	$(c).click(function(c) {
		c.preventDefault(), a > d && (d++, $(b).append('<div class="row">'
				+'<div class="col col-sm-12 col-md-3 partDiv" style="display:none">'
				+' <label class="has-float-label">'
				+'	 <input type="hidden" name="partId" id="partId'+d+'" onchange="checkAssetNumber('+d+');" class="browser-default" style="line-height: 30px;font-size: 15px;height: 35px;width:100%;">'
				+'	    <span style="color: #2e74e6;font-size: 18px;" >Part Number </span>'
				+'	</label>'
				+'</div>'
				+'<div class="col col-sm-12 col-md-3 tyreDiv" style="display:none">'
				+'   <label class="has-float-label">'
				+'	 <select name="tyreId" id="tyreId'+d+'" onchange="changeTyre(this.id,dismountDate'+d+')" class="browser-default " style="line-height: 30px;font-size: 15px;height: 35px;width:100%;" ></select>'
				+'	    <span style="color: #2e74e6;font-size: 18px;" >Tyre Number </span>'
				+'	</label>'
				+'</div>'
				+'<div class="col col-sm-12 col-md-3 batteryDiv" style="display:none">'
				+'  <label class="has-float-label">'
				+'	 <select  name="batteryId" id="batteryId'+d+'" onchange="changeBattery(this.id,dismountDate'+d+')" class="browser-default " style="line-height: 30px;font-size: 15px;height: 35px;width:100%;" ></select>'
				+'	    <span style="color: #2e74e6;font-size: 18px;" >Battery Number </span>'
				+'	</label>'
				+'</div>'
				+'<div class="col col-sm-12 col-md-3 dateOfRemoveDiv ">'
				+'	 <label class="has-float-label">'
				+'	    <div class="input-group input-append date" id="removeDate'+d+'">'
				+'				<input type="text" class="form-control  browser-default custom-select noBackGround	invoiceDate" name="dismountDate" readonly="readonly"'
				+'					id="dismountDate'+d+'" required="required"'
				+'data-inputmask="\'alias\': \'dd-mm-yyyy\'" data-mask=""  /> '
				+'					<span  class="  input-group-addon add-on btn btn-sm"><em class="fa fa-calendar"></em></span>'
				+'			</div>'
				+'	    <span style="color: #2e74e6;font-size: 18px;">Date Of Remove <abbr title="required">*</abbr></span>'
				+'	  </label>'
				+'</div>'
				/*+'<div class="col col-sm-12 col-md-2">'
				+'	  <label class="has-float-label">'
				+'	   <select id="warrantyStatusId'+d+'" name="warrantyStatusId" class="browser-default custom-select">'
				+'	    	<option value="0">Not Under Warranty</option>'
				+'	    	<option value="1">Under Warranty</option>'
				+'		</select>'
				+'	    <span style="color: #2e74e6;font-size: 18px;">Warranty Status </span>'
				+'	  </label>'
				+' </div>'*/
				+'<div class="col col-sm-12 col-md-5">'
				+'	 <label class="has-float-label">'
				+'	    <input type="text" class="form-control browser-default custom-select noBackGround" id="workDetail" name="workDetail"  >'
				+'	    <span style="color: #2e74e6;font-size: 18px;">Issue Detail </span>'
				+'	  </label>'
				+' </div>'
				+'<a href="#" class="removeLabour col col-sm-1 col-md-1"><font color="FF00000"><i class="fa fa-trash"></i> Remove </a></font>'
				+'</div>'),
				$.getJSON("getMoveToRepairTyre.in?fromLocationId="+$("#fromLocationId").val()+"", function(e) {
					console.log("e",e)
					tyreList	= e;//To get All Company Name 
					$("#tyreId"+d).empty();
					$("#tyreId"+d).append($("<option>").text("Please Select ").attr("value",0));
					$("#tyreId"+d).select2();

					for(var k = 0; k <tyreList.length; k++){
						$("#tyreId"+d).append($("<option>").text(tyreList[k].TYRE_NUMBER).attr("value", tyreList[k].TYRE_ID));
					}

				}),$.getJSON("getMoveToRepairBattery.in?fromLocationId="+$("#fromLocationId").val()+"", function(e) {
					batteryList	= e;//To get All Company Name 
					$("#batteryId"+d).empty();
					$("#batteryId"+d).append($("<option>").text("Please Select").attr("value",0));
					$("#batteryId"+d).select2();

					for(var k = 0; k <batteryList.length; k++){
						$("#batteryId"+d).append($("<option>").text(batteryList[k].batterySerialNumber).attr("value", batteryList[k].batteryId));
					}

				}), $("#removeDate"+d).datepicker({
			        defaultDate: defaultServerDate,
			        autoclose: !0,
			        todayHighlight: true,
			        format: "dd-mm-yyyy",
			        setDate: "0",
			        endDate: defaultServerDate,
			        maxDate: defaultServerDate
			    }),$("#partId"+d).select2({
					minimumInputLength: 2,
					minimumResultsForSearch: 10,
					ajax: {
						url: "getRepairablePartListByLocation.in",
						dataType: "json",
						type: "POST",
						contentType: "application/json",
						quietMillis: 50,
						data: function(a) {
							return {
								term: a
							}
						},
						results: function(a) {
							return {
								results: $.map(a, function(a) {
									return $("#locationName").val() == a.location ? {
										text: a.location + " - " + a.partnumber + " - " + a.partname + " - " + a.partManufacturer + " - " + a.location_quantity,
										slug: a.slug,
										id: a.inventory_location_id,
										warranty : a.isWarrantyApplicable,
										repairable : a.isRepairable,
										partId : a.partid,
										locationId : a.locationId,
										maxQuantity : a.location_quantity,
										inventory_id : a.inventory_id
									} : {
										text: a.location + " - " + a.partnumber + " - " + a.partname + " - " + a.partManufacturer + " - " + a.location_quantity,
										slug: a.slug,
										id: a.inventory_location_id,
										disabled: !0,
										warranty : a.isWarrantyApplicable,
										repairable : a.isRepairable,
										partId : a.partid,
										locationId : a.locationId,
										maxQuantity : a.location_quantity,
										inventory_id : a.inventory_id
									}
								})
							}
						}
					}
				})
			    
		)
			if($("#repairTypeId").val()== 1){
				$(".partDiv").show();
				$(".dateOfRemoveDiv").hide();
			}else if($("#repairTypeId").val() == 2){
				$(".dateOfRemoveDiv").show();
				$(".tyreDiv").show();
			}else{
				$(".dateOfRemoveDiv").show();
				$(".batteryDiv").show();
			}
		
		
	}), $(b).on("click", ".removeLabour", function(a) {
		a.preventDefault(), $(this).parent("div").remove(), d--
	})
});

$(document).ready(function(){
	if($("#repairTypeId").val()== 1){
		$(".partDiv").show();
	}else if($("#repairTypeId").val() == 2){
		$(".tyreDiv").show();
	}else{
		$(".batteryDiv").show();
	}
});



function changeTyre(tyreId,dismountedDate){
	var tyreArr 		= new Array();
	$("select[name=tyreId]").each(function(){
		if($("#"+tyreId).val() == $(this).val()){
			tyreArr.push($(this).val());
		}
	});
	if(tyreArr.length > 1){
		showMessage('info','This Tyre Is Already Selected')
		$("#"+tyreId).select2("val","");
		return false;
	}
	
console.log("tyreId",tyreArr.length)
console.log("tyre val",$("#"+tyreId).val())
	var jsonObject = new Object();
	jsonObject["tyreId"] 					= $("#"+tyreId).val();
	jsonObject["companyId"] 				= $("#companyId").val();
	jsonObject["userId"] 					= $("#userId").val();
	
	$.ajax({
		url: "getDismountTyreDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			console.log("data",data)
			$("#"+dismountedDate.id).val(data.inventoryTyreHistory.tyre_ASSIGN_DATE);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});


}

function changeBattery(batteryId,dismountedDate){
	
	var batteryArr 		= new Array();
	$("select[name=batteryId]").each(function(){
		if($("#"+batteryId).val() == $(this).val()){
			batteryArr.push($(this).val());
		}
	});
	
	if(batteryArr.length > 1){
		showMessage('info','This Battery Is Already Selected')
		$("#"+batteryId).select2("val","");
		return false;
	}
	
	console.log("batteryId",dismountedDate.id)
	console.log("tyre val",$("#"+batteryId).val())
		var jsonObject = new Object();
		jsonObject["batteryId"] 					= $("#"+batteryId).val();
		jsonObject["companyId"] 				= $("#companyId").val();
		jsonObject["userId"] 					= $("#userId").val();
		
		$.ajax({
			url: "getDismountBatteryDetails",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				hideLayer();
				console.log("datafasa",data)
				$("#"+dismountedDate.id).val(data.batteryHistory.batteryAsignDateStr);
			},
			error: function (e) {
				hideLayer();
				showMessage('errors', 'Some Error Occurred!');
			}
		});


	}

function saveRemark(){
	$("#remarkModal").modal('show');
}

$(document).ready(
		function($) {
			$('button[id=submit]').click(function(e) {
				e.preventDefault();
				showLayer();
				
				/*if($('#sentDate').val() == "" || $('#sentDate').val() == undefined){
					showMessage('info','Please Select Sent Date');
					hideLayer();
					return false;
				}*/
				
				if(($('#remark').val() == undefined ||  $('#remark').val().trim() == "") ){
					showMessage('info','Please Enter Remark');
					hideLayer();
					return false;
				}
				
				
				
				var jsonObject			= new Object();
				var repairStockArr	 		= new Array();
				var inventoryLocationIdArr 			= new Array();
				var repairStockNameArr 			= new Array();
				var repairStockDismountDateArr	= new Array();
				var repairStockWarrantyArr 		= new Array();
				var workDetailArr 				= new Array();

				if(Number($("#repairTypeId").val())== 1){
					$("input[name=partId]").each(function(){
						if($(this).val() != "" && $(this).val() != null && $(this).val() != undefined){
							repairStockNameArr.push($("#"+this.id).select2('data').partId);
						}
						inventoryLocationIdArr.push($(this).val());
					});
				}else if(Number($("#repairTypeId").val())== 2){
					$("select[name=tyreId]").each(function(){
						repairStockNameArr.push($(this).val());
						console.log("hg",$(this).val())
					});
				}else{
					$("select[name=batteryId]").each(function(){
						repairStockNameArr.push($(this).val());
					});
				
				}
				$("input[name=dismountDate]").each(function(){
					repairStockDismountDateArr.push($(this).val());
				});
				$("select[name=warrantyStatusId]").each(function(){
					repairStockWarrantyArr.push($(this).val());
				});
				$("input[name=workDetail]").each(function(){
					workDetailArr.push($(this).val().trim());
				});
				
				if(repairStockNameArr.length <= 0){
					showMessage('info','Please Select Stock');
					hideLayer();
					return false;
				}
				for(var i =0 ; i< repairStockNameArr.length; i++){
					var repairStockDetails					= new Object();
						
						if(repairStockNameArr[i] == '' || repairStockNameArr[i] == 0){
							if($("#repairTypeId").val()== 1){
								showMessage('info','Please Select Part Details');
							}else if($("#repairTypeId").val() == 2){
								showMessage('info','Please Select Tyre Details');
							}else{
								showMessage('info','Please Select Battery Details');
							}
							hideLayer();
							return false;
						}
						if(repairStockNameArr[i] == '' || repairStockNameArr[i] == 0){
							showMessage('info','Please Select Stock');
							hideLayer();
							return false;
						}
						
						if($("#repairTypeId").val() != 1 && (repairStockDismountDateArr[i] == '' || repairStockDismountDateArr[i] == 0)){
							showMessage('info','Please Select Dismount Date');
							hideLayer();
							return false;
						}
						if(workDetailArr[i] == '' || workDetailArr[i] == 0){
							showMessage('info','Please Enter WorkDetails');
							hideLayer();
							return false;
						}
						
						repairStockDetails.inventoryLocationId			= inventoryLocationIdArr[i];
						repairStockDetails.repairStockPartId			= repairStockNameArr[i];
						repairStockDetails.partQty						= 1;
						repairStockDetails.dateOfRemoved				= repairStockDismountDateArr[i];
						repairStockDetails.isRepairInWarranty			= repairStockWarrantyArr[i];
						repairStockDetails.workDetails					= workDetailArr[i];
						
						repairStockArr.push(repairStockDetails);
				}

				jsonObject.repairStockDetails 		= JSON.stringify(repairStockArr);
				
				jsonObject["repairWorkshopId"] 			= $("#repairWorkshopId").val();				
				jsonObject["repairTypeId"] 			= $("#repairTypeId").val();				
				jsonObject["repairStockId"] 		= $("#repairStockId").val();				
				jsonObject["sentDate"]				= $('#sentDate').val();
				jsonObject["remark"]				= $('#remark').val();
				jsonObject["companyId"]				= $('#companyId').val();
				jsonObject["userId"]				= $('#userId').val();
				
				console.log("jsonObject",jsonObject)
				
				$.ajax({
					url: "saveRepairToStockDetails",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						hideLayer();
						window.location.replace("showRepairInvoice?repairStockId="+$("#repairStockId").val()+"");
						showMessage('success','Save Successfully')
					},
					error: function (e) {
						hideLayer();
						showMessage('errors', 'Some Error Occurred!');
					}
				});
			});
		});



$.getJSON("getMoveToRepairTyre.in?fromLocationId="+$("#fromLocationId").val()+"", function(e) {
	console.log("e",e)
	tyreList	= e;//To get All Company Name 
	$("#tyreId").empty();
	$("#tyreId").append($("<option>").text("Please Select ").attr("value",0));
	$('#tyreId').select2();

	for(var k = 0; k <tyreList.length; k++){
		$("#tyreId").append($("<option>").text(tyreList[k].TYRE_NUMBER).attr("value", tyreList[k].TYRE_ID));
	}

}),$.getJSON("getMoveToRepairBattery.in?fromLocationId="+$("#fromLocationId").val()+"", function(e) {
	batteryList	= e;//To get All Company Name 
	$("#batteryId").empty();
	$("#batteryId").append($("<option>").text("Please Select").attr("value",0));
	$('#batteryId').select2();
	if(batteryList != null){
		for(var k = 0; k <batteryList.length; k++){
			$("#batteryId").append($("<option>").text(batteryList[k].batterySerialNumber).attr("value", batteryList[k].batteryId));
		}
	}

})