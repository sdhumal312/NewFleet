$(document).ready(function(){
	hideAllDiv('');
	prepareSelect2('');
});


$(document).ready(function() {
	var a = 500,
	b = $(".addMorePartDiv"),
	c = $(".addMorePartButton"),
	d = 1,
	showUpholstery= $('#showUpholstery').val() === 'true' ? '<option value="4">Upholstery</option>':'';
	
	$(c).click(function(c) { 
		c.preventDefault(), a > d && (d++, $(b).append('<div> <div class="box">'
					 +' <div class="box-body">'
					 +' <div class="row">'
					 +' <div class="col col-sm-1 col-md-3">'
					 +' <label class="has-float-label">'
					 +' <select id="requisitionType_'+d+'" style="line-height: 30px;font-size: 15px;height: 35px;" name="requisitionType" onchange="hideDiv(this.id)" >'
					 +'		<option value="0">Select Type</option>'
					 +'		<option value="1">Part</option>'
					 +'		<option value="2">Tyre</option>'
					 +'		<option value="3">Battery</option>'
					 +' '+showUpholstery+''
					 +'		<option value="5">Urea</option>'
					 +'	</select>'
					 +'  <span style="color: #2e74e6;font-size: 16px;">Requisition type <abbr title="required">*</abbr></span>'
					 +'  </label>'
					 +' </div>'
					 +' <div id="partDiv_'+d+'" class="col col-sm-1 col-md-3">'
					 +'	 <label class="has-float-label">'
					 +'	 <input type="hidden" name="partId" id="partId_'+d+'" onchange="setUOM(this.id);validateDublicate(this)" class="browser-default partId" style="line-height: 30px;font-size: 15px;height: 35px;">'
					 +'    <span style="color: #2e74e6;font-size: 18px;" >Part Name </span>'
					 +'  </label>'
					 +'	</div>'
					 +'	'+($('#showPartUOM').val() === 'true' ? '<div id="partUOMDiv_'+d+'" class="col col-sm-1 col-md-3">  <label class="has-float-label">  <input type="text" class="form-control browser-default noBackGround" name="partUom" id="partUom_'+d+'" readonly="readonly">  <span style="color: #2e74e6;font-size: 18px;" >UOM</span> </label> </div>':'')+''
					 +' <div id="tyreModelDiv_'+d+'" class="col col-sm-1 col-md-3">'
					 +' <label class="has-float-label">'
					 +' <select style="line-height: 30px;font-size: 15px;height: 35px;" id="tyremodel_'+d+'" name="TYRE_MODEL_ID" required="required"  onchange="validateSelectDublicate(this);" > </select>'
					 +'	    <span style="color: #2e74e6;font-size: 18px;" >Tyre Model </span>'
					 +'	  </label>'
					 +' </div>'
//					 +' <div id="tyreManuDiv_'+d+'" class="col col-sm-1 col-md-3">'
//					 +' <label class="has-float-label">'
//					 +' <input type="hidden" id="manufacurer_'+d+'" style="line-height: 30px;font-size: 15px;height: 35px;" name="TYRE_MANUFACTURER_ID" style="width: 100%;" required="required" placeholder="Please Enter 2 or more Tyre Manufacturer Name" />'
//					 +'   <span style="color: #2e74e6;font-size: 18px;" >Tyre Manufacturer </span>'
//					 +'	  </label>'
//					 +' </div>'
//					 +' <div id="tyreSizeDiv_'+d+'" class="col col-sm-1 col-md-3">'
//					 +'	 <label class="has-float-label">'
//					 +' <input type="hidden" id="tyreSize_'+d+'" name="TYRE_SIZE_ID" style="line-height: 30px;font-size: 15px;height: 35px;" required="required" placeholder="Please select Tyre Size" />'
//					 +'	 <span style="color: #2e74e6;font-size: 18px;" >Tyre Size </span>'
//					 +'  </label>'
//					 +' </div>'
//					 +' <div id="batteryManuDiv_'+d+'" class="col col-sm-1 col-md-3">'
//					 +' <label class="has-float-label">'
//					 +' <input type="hidden" id="batteryManufacturer_'+d+'" name="batteryManufacturerId" style="line-height: 30px;font-size: 15px;height: 35px;" placeholder="Please Enter 2 or more Battery Manufacturer Name" />'
//					 +' <span style="color: #2e74e6;font-size: 18px;" >Battery Manufacturer </span>'
//					 +'	</label>'
//					 +'	</div>'
//					 +' <div id="batteryTypeDiv_'+d+'" class="col col-sm-1 col-md-3">'
//					 +' <label class="has-float-label">'
//					 +'	<select style="line-height: 30px;font-size: 15px;height: 35px;" id="batterryTypeId_'+d+'"  name="batteryTypeId"></select>'
//					 +'  <span style="color: #2e74e6;font-size: 18px;" >Battery Model </span>'
//					 +'  </label>'
//					 +'	</div>'
					 +' <div id="batteryCapaDiv_'+d+'" class="col col-sm-1 col-md-3">'
					 +' <label class="has-float-label">'
					 +' <input type="hidden" id="batteryCapacityId_'+d+'" name="batteryCapacityId" style="line-height: 30px;font-size: 15px;height: 35px;" placeholder="Please select Battery Capacity" onchange="validateDublicate(this);"/>'
					 +' <span style="color: #2e74e6;font-size: 18px;" >Battery Capacity</span>'
					 +' </label>'
					 +'	</div>'
					 +'	<div id="upholsteryDiv_'+d+'"class="col col-sm-1 col-md-3">'
					 +'	<label class="has-float-label">'
					 +'	<input type="hidden" id="clothTypes_'+d+'" name="clothTypes" style="line-height: 30px;font-size: 15px;height: 35px;" placeholder="Please Enter 2 or more Cloth Types Name" onchange="validateDublicate(this);" />'
					 +'	 <span style="color: #2e74e6;font-size: 18px;" >Upholstery : </span>'
					 +'  </label>'
					 +' </div>'
//					 +'	<div id="ureaDiv_'+d+'" class="col col-sm-1 col-md-3">'
//					 +' <label class="has-float-label"> '
//					 +'	<input type="hidden" id="ureaManufacturer_'+d+'" name="ureaManufacturer" style="line-height: 30px;font-size: 15px;height: 35px;" placeholder="Please Enter 2 or more Manufacturer Name" />'
//					 +'   <span style="color: #2e74e6;font-size: 18px;" >Urea Manufacturer : </span>'
//					 +'  </label>'
//					 +' </div>'
					 +' <div class="col col-sm-1 col-md-3">'
					 +' <label class="has-float-label">'
					 +'  <input type="text" class="form-control browser-default custom-select noBackGround" name="Qty" id="Qty_'+d+'" onkeypress="return isNumberKeyWithDecimal(event,this.id);"> '
					 +'  <span style="color: #2e74e6;font-size: 18px;" >Qty</span>'
					 +'  </label>'
					 +'	</div>'
					 +' </div>'
					 +'<a href="#" class="removePart col col-sm-1 col-md-3"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font>'
					 +'	</div>'
					 +'	</div>'
					 +' </div>'),
				prepareSelect2('_'+d),
				hideAllDiv('_'+d))
	}),
	$(b).on("click", ".removePart", function(a) {
		a.preventDefault(), $(this).parent("div").remove(), d--
	})
});


function prepareSelect2(d){
	if(d == undefined || d == ''){
		d='';
	}
	$('#location'+d).select2();
	$('#requisitionType'+d).select2();
	 $("#batterryTypeId"+d).select2({
		 
	 });
	
    $("#partId"+d).select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getPartList.in",
            dataType: "json",
            type: "POST",
            contentType: "application/json",
            quietMillis: 50,
            data: function(e) {
            	
                return {
                    term: e
                }
            },
            results: function(e) {
            	
                return {
                	
                    results: $.map(e, function(e) {
                    	
                    	$('#quantity').val(e.reorderQuantity);
                    	
                        return {
                        	
                            text: e.partnumber + " - " + e.partname + " - " + e.make,
                            slug: e.unittype,
                            id: e.partid
                        }
                        
                        
                    })
                   
                }
            }
        }
    });
	$("#assignToId"+d).select2({
        minimumInputLength: 0,
        minimumResultsForSearch: 10,
        multiple: 0,
        ajax: {
            url: "getAllUserListByCompanyId",
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
                        return {
                            text: a.firstName + " " + a.lastName,
                            slug: a.slug,
                            id: a.user_id
                        }
                    })
                }
            }
        }
    });
	 $("#manufacurer"+d).select2({
	        minimumInputLength: 2,
	        minimumResultsForSearch: 10,
	        ajax: {
	            url: "getSearchTyreModel.in?Action=FuncionarioSelect2",
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
	                        return {
	                            text: a.TYRE_MODEL,
	                            slug: a.slug,
	                            id: a.TYRE_MT_ID
	                        }
	                    })
	                }
	            }
	        }
	    }) ;
	 $("#batteryCapacityId"+d).select2({
         minimumInputLength: 2,
         minimumResultsForSearch: 10,
         ajax: {
             url: "getSearchBatteryCapacity.in?Action=FuncionarioSelect2",
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
                         return {
                             text: a.batteryCapacity,
                             slug: a.slug,
                             id: a.batteryCapacityId
                         }
                     })
                 }
             }
         }
     });
	 $("#clothTypes"+d).select2({
	        minimumInputLength: 2,
	        minimumResultsForSearch: 10,
	        ajax: {
	            url: "getClothTypesList.in?Action=FuncionarioSelect2",
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
	                        return {
	                            text: a.clothTypeName,
	                            slug: a.slug,
	                            id: a.clothTypesId
	                        }
	                    })
	                }
	            }
	        }
	    });
		
	 $.getJSON("searchAllTyreModel.in", function(e) {
 		companyList	= e;//To get All Company Name 
 		$("#tyremodel"+d).empty();

 		$("#tyremodel"+d).append($("<option>").text('Select Model').attr("value", 0));
 		for(var k = 0; k <companyList.length; k++){
 			$("#tyremodel"+d).append($("<option>").text(companyList[k].TYRE_MODEL_SUBTYPE).attr("value", companyList[k].TYRE_MST_ID));
 		}

 		$("#tyremodel"+d ).select2({
 			placeholder : 'Select Model',
 			allowClear : false
 		});
 	});
//	 $("#ureaManufacturer"+d).select2({
//	        minimumInputLength: 2,
//	        minimumResultsForSearch: 10,
//	        ajax: {
//	            url: "getUreaManufacturerList.in?Action=FuncionarioSelect2",
//	            dataType: "json",
//	            type: "POST",
//	            contentType: "application/json",
//	            quietMillis: 50,
//	            data: function(a) {
//	                return {
//	                    term: a
//	                }
//	            },
//	            results: function(a) {
//	                return {
//	                    results: $.map(a, function(a) {
//	                        return {
//	                            text: a.manufacturerName,
//	                            slug: a.slug,
//	                            id: a.ureaManufacturerId
//	                        }
//	                    })
//	                }
//	            }
//	        }
//	    });
//	 $("#batteryManufacturer"+d).select2({
//	        minimumInputLength: 2,
//	        minimumResultsForSearch: 10,
//	        ajax: {
//	            url: "getBatteryManufacturer.in?Action=FuncionarioSelect2",
//	            dataType: "json",
//	            type: "POST",
//	            contentType: "application/json",
//	            quietMillis: 50,
//	            data: function(a) {
//	                return {
//	                    term: a
//	                }
//	            },
//	            results: function(a) {
//	                return {
//	                    results: $.map(a, function(a) {
//	                    	
//	                        return {
//	                            text: a.manufacturerName,
//	                            id: a.batteryManufacturerId
//	                        }
//	                    })
//	                }
//	            }
//	        }
//	    }),$("#batteryManufacturer"+d).change(function() {
//	    	if($(this).val() != '' && $(this).val() >0){
//	    		 $.getJSON("getBatteryType.in", {
//	                 ModelType: $(this).val(),
//	                 ajax: "true"
//	             }, function(a) {
//	                 for (var b = '<option value="0">Please Select</option>', c = a.length, e = 0; c > e; e++) b += '<option value="' + a[e].batteryTypeId + '">' + a[e].batteryType + "</option>";
//	                 b += "</option>", $("#batterryTypeId"+d).html(b)
//	             })
//	    	}else{
//	    		$('#batterryTypeId'+d).empty();
//	    		$('#batterryTypeId'+d).select2();
//	    	}
//     });
	 
//	    , $("#tyreSize"+d).select2({
//	        minimumInputLength: 2,
//	        minimumResultsForSearch: 10,
//	        ajax: {
//	            url: "getSearchTyreSize.in?Action=FuncionarioSelect2",
//	            dataType: "json",
//	            type: "POST",
//	            contentType: "application/json",
//	            quietMillis: 50,
//	            data: function(a) {
//	                return {
//	                    term: a
//	                }
//	            },
//	            results: function(a) {
//	                return {
//	                    results: $.map(a, function(a) {
//	                        return {
//	                            text: a.TYRE_SIZE,
//	                            slug: a.slug,
//	                            id: a.TS_ID
//	                        }
//	                    })
//	                }
//	            }
//	        }
//	    })
	
	

	
	
}



//function tyreModelChange(d){
//	if(d != undefined &&  d != ""){
//		d= "_"+d;
//	}else{
//		d= "";
//	}
//	var tyreModel= $('#tyremodel'+d).val();
//	if(tyreModel != '' && tyreModel > 0){
//		
//	
//	var jsonObject					= new Object();
//	jsonObject["TYRE_MST_ID"] 		= tyreModel;
//	jsonObject["companyId"] 		= $('#companyId').val();
//
//	$.ajax({
//		url: "getVehicleTyreModelSubTypeDetails",
//		type: "POST",
//		dataType: 'json',
//		data: jsonObject,
//		success: function (data) {
//			if(data.tyreModelDetails != undefined){
//				var tyreModelDetails = data.tyreModelDetails;
//				
//				$('#manufacurer'+d).select2('data', {
//					id : tyreModelDetails.tyre_MT_ID,
//					text : tyreModelDetails.tyre_MODEL
//				});
//				$('#manufacurer'+d).select2('readonly',true);
//				$('#tyreSize'+d).select2('data', {
//					id : tyreModelDetails.tyreModelSizeId,
//					text : tyreModelDetails.tyreModelSize
//				});
//				$('#tyreSize'+d).select2('readonly',true);
//			}
//		},
//		error: function (e) {
//			showMessage('errors', 'Some error occured!')
//			hideLayer();
//		}
//	});
//	}else{
//		$('#manufacurer'+d).select2("val","");
//		$('#tyreSize'+d).select2("val","");
//	}
//}

function hideDiv(requisitionType){
	let rerSplit = requisitionType.split('_');
	let d ="";

	if(rerSplit.length > 1){
		d ='_'+rerSplit[1];
	}
	var showDiv = $('#'+requisitionType).val();
	hideAllDiv(d);
	if(showDiv == 1){
		$('#partDiv'+d).show();
		$('#partUOMDiv'+d).show();
	}else if(showDiv == 2){
		$('#tyreModelDiv'+d).show();
		if($("#showManufacturerAndSize").val() == 'true'){
		$('#tyreManuDiv'+d).show();
		$('#tyreSizeDiv'+d).show();
		}
	}else if (showDiv == 3){
		$('#batteryManuDiv'+d).show();
		$('#batteryTypeDiv'+d).show();
		$('#batteryCapaDiv'+d).show();
	}else if (showDiv == 4){
		$('#upholsteryDiv'+d).show();
	}else if (showDiv == 5){
		$('#ureaDiv'+d).show();
	}
}
function hideAllDiv(d){
	if(d == undefined || d == ''){
		d ='';
	}
	$('#partDiv'+d).hide();
	$('#partUOMDiv'+d).hide();
	
	$('#tyreModelDiv'+d).hide();
	$('#tyreManuDiv'+d).hide();
	$('#tyreSizeDiv'+d).hide();
	
	$('#batteryManuDiv'+d).hide();
	$('#batteryTypeDiv'+d).hide();
	$('#batteryCapaDiv'+d).hide();
	
	$('#upholsteryDiv'+d).hide();
	$('#ureaDiv'+d).hide();
}

function validateMultiType(id,val){
	var returnVar = true;
	var splitId = id.split('_');
	let finalId ='';
	if(splitId.length > 1 && splitId[1] != undefined){
		finalId ='_'+splitId[1];
	}
	if(val == 1){
		if($('#partId'+finalId).val() == '' || $('#partId'+finalId).val() <= 0 ){
			$('#partId'+finalId).addClass('showError');
			$('#s2id_partId'+finalId).addClass('showError');
			returnVar=false;
		}
	}else if(val == 2){
		if($('#tyremodel'+finalId).val() == '' || $('#tyremodel'+finalId).val() <= 0 ){
			$('#tyremodel'+finalId).addClass('showError');
			$('#s2id_tyremodel'+finalId).addClass('showError');
			returnVar=false;
		}else if ($('#manufacurer'+finalId).val() == '' || $('#manufacurer'+finalId).val() <= 0){
			$('#manufacurer'+finalId).addClass('showError');
			$('#s2id_manufacurer'+finalId).addClass('showError');
			returnVar=false;
		}else if ($('#tyreSize'+finalId).val() == '' || $('#tyreSize'+finalId).val() <= 0){
			$('#tyreSize'+finalId).addClass('showError');
			$('#s2id_tyreSize'+finalId).addClass('showError');
			returnVar=false;
		}
	}else if(val == 3){
		if($('#batteryManufacturer'+finalId).val() == '' || $('#batteryManufacturer'+finalId).val() <= 0 ){
			$('#batteryManufacturer'+finalId).addClass('showError');
			$('#s2id_batteryManufacturer'+finalId).addClass('showError');
			returnVar=false;
		}
		if($('#batterryTypeId'+finalId).val() == '' || $('#batterryTypeId'+finalId).val() <= 0 ){
			$('#batterryTypeId'+finalId).addClass('showError');
			$('#s2id_batterryTypeId'+finalId).addClass('showError');
			returnVar=false;
		}
		if($('#batteryCapacityId'+finalId).val() == '' || $('#batteryCapacityId'+finalId).val() <= 0 ){
			$('#batteryCapacityId'+finalId).addClass('showError');
			$('#s2id_batteryCapacityId'+finalId).addClass('showError');
			returnVar=false;
		}
	}else if(val == 4){
		if($('#clothTypes'+finalId).val() == '' || $('#clothTypes'+finalId).val() <= 0 ){
			$('#clothTypes'+finalId).addClass('showError');
			$('#s2id_clothTypes'+finalId).addClass('showError');
			returnVar=false;
		} 
	}else if (val == 5){
		if($('#ureaManufacturer'+finalId).val() == '' || $('#ureaManufacturer'+finalId).val() <= 0 ){
			$('#ureaManufacturer'+finalId).addClass('showError');
			$('#s2id_ureaManufacturer'+finalId).addClass('showError');
			returnVar=false;
		}
	}
if ($('#Qty'+finalId).val() == '' || $('#Qty'+finalId).val() <= 0){
		$('#Qty'+finalId).addClass('showError');
		returnVar=false;
	}
	return returnVar;
}
function prepareObject(id,val){
	var splitId = id.split('_');
	let finalId ='';
	var object = new Object();
	if(splitId.length > 1 && splitId[1] != undefined){
		finalId ='_'+splitId[1];
	}
	
	object['reqType'] = val;
	if(val == 1){
		object['transactionId'] = $('#partId'+finalId).val();
	}else if(val == 2){
		object['tyremodel'] =$('#tyremodel'+finalId).val();
		object['manufacurer'] =$('#manufacurer'+finalId).val();
		object['tyreSize'] =$('#tyreSize'+finalId).val();
	}else if(val == 3){
		object['batteryManufacturer'] =$('#batteryManufacturer'+finalId).val();
		object['batterryTypeId'] =$('#batterryTypeId'+finalId).val();
		object['batteryCapacityId'] =$('#batteryCapacityId'+finalId).val();
	}else if(val == 4){
		object['transactionId'] =$('#clothTypes'+finalId).val();
	}else if (val == 5){
		object['transactionId'] =$('#ureaManufacturer'+finalId).val();
	}
	object['Qty'] =$('#Qty'+finalId).val();
	return object;
}
function setUOM(id){
	var splitId = id.split('_');
	let finalId ='';
	if(splitId.length > 1 && splitId[1] != undefined){
		finalId ='_'+splitId[1];
	}
	if($('#'+id).val() >0){
		var data =$('#'+id).select2('data');
		$('#partUom'+finalId).val(data.slug);
	}else{
		$('#partUom'+finalId).val(' ');
	}

}

