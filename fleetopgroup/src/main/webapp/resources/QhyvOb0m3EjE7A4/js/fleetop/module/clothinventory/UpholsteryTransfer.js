$(document).ready(function() {
   $("#transferViaId").select2();
   $("#stockTypeId_0").select2();
   $("#fromLocationID").select2( {
        minimumInputLength:2, minimumResultsForSearch:10, ajax: {
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
    }),
    
    $("#toLocationId").select2( {
        minimumInputLength:2, minimumResultsForSearch:10, ajax: {
            url:"getAllPartLocations.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
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
    }),
    
    $("#transferReceivedById").select2({
		minimumInputLength : 3,
		minimumResultsForSearch : 10,
		multiple : 0,
		ajax : {
			url : "getUserEmailId_Subscrible",
			dataType : "json",
			type : "POST",
			contentType : "application/json",
			quietMillis : 50,
			data : function(e) {
				return {
					term : e
				}
			},
			results : function(e) {
				return {
					results : $.map(e, function(e) {
						return {
							text : e.firstName + " " + e.lastName,
							slug : e.slug,
							id : e.user_id
						}
					})
				}
			}
		}
	}),
    
    $("#clothTypeId_0").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        multiple : 0,
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
    }),
    
    $("#clothTypeId_0").change(function() {
    	showLayer();
        0 != $(this).val() && $.getJSON("getLocationClothCountDetails.in", {
        	locationId : $('#fromLocationID').val(),
        	clothTypesId : $("#clothTypeId_0").val(),
            ajax: "true"
        }, function(e) {
        	hideLayer();
        	
        	 if(e.htData.StockNotFound != undefined){
              	showMessage('info', 'Upholstery Not Found at this Location. Please Add Upholstery Inventory For this Location First.');
              } 
        	
            $('#stockQty_0').val(e.htData.detailsDto.newStockQuantity);
        })
        hideLayer();
    }),
    
    $("#stockTypeId_0").change(function() {
    	showLayer();
        0 != $(this).val() && $.getJSON("getLocationClothCountDetails.in", {
        	locationId : $('#fromLocationID').val(),
        	clothTypesId : $("#clothTypeId_0").val(),
            ajax: "true"
        }, function(e) {
        	hideLayer();
        	var typeOfCloth	= $('#stockTypeId_0').val();
        	if(typeOfCloth == 1){
        		$('#stockQty_0').val(e.htData.detailsDto.newStockQuantity);
        	} else {
        		$('#stockQty_0').val(e.htData.detailsDto.usedStockQuantity);	
        	}
            
        })
    })
    
});

$(document).ready(function() {
    var a = 10,
        b = $(".input_fields_wrap"),
        c = $(".add_field_button"),      
        d = 1;
    $(c).click(function(c) {
        c.preventDefault(), a > d && (d++, $(b).append('<div><div class="panel panel-success"><div class="panel-body"><div class="row1"><label class="L-size control-label">Upholstery Name:<abbr title="required">*</abbr></label><div class="I-size"><input type="hidden" id="clothTypeId_' + d + '" onchange="validateInspectionParameter(document.getElementById(&#34;stockTypeId_'+ d +'&#34;));" name="clothType" style="width: 100%;" placeholder="Please Enter Upholstery Name" /></div></div><div class="row1" class="form-group"><label class="L-size control-label">Types of Upholstery <abbr title="required">*</abbr></label><div class="I-size"><select style="width: 100%;" name="stockType" id="stockTypeId_' + d + '" required="required" onchange="validateInspectionParameter(this);"><option value="1">NEW/FRESH</option><option value="2">OLD/USED</option></select></div></div><div class="row1"><label class="L-size control-label">Current Stock Qty:</label><div class="I-size"><input type="text" class="form-text" name="stockQty" id="stockQty_' + d + '" style="width: 15%;" readonly="readonly" /></div></div><div class="row1"><label class="L-size control-label">Transfer Stock Qty:<abbr title="required">*</abbr></label><div class="I-size"><input type="text" class="form-text" name="transferStockQty" id="transferStockQty_' + d + '" style="width: 15%;" onkeyup="validateQty('+d+');" onkeypress="return isNumberKeyWithDecimal(event,this.id)"/></div></div></div></div><a href="#" class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i>Remove</a></font></div>'), 
          $(document).ready(function() {
        	$("#stockTypeId_"+ d).select2();  
            $("#clothTypeId_" + d).select2({
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
            }),
            
            $("#clothTypeId_"+d).change(function() {
            	showLayer();
                0 != $(this).val() && $.getJSON("getLocationClothCountDetails.in", {
                	locationId : $('#fromLocationID').val(),
                	clothTypesId : $("#clothTypeId_"+d).val(),
                    ajax: "true"
                }, function(e) {
                	hideLayer();
                	
                	if(e.htData.StockNotFound != undefined){
                      	showMessage('info', 'Upholstery Not Found at this Location. Please Add Upholstery Inventory For this Location First.');
                      }
                	
                    $('#stockQty_'+d).val(e.htData.detailsDto.newStockQuantity);
                })
                hideLayer();
            }),
            
            $("#stockTypeId_"+d).change(function() {
            	showLayer();
                0 != $(this).val() && $.getJSON("getLocationClothCountDetails.in", {
                	locationId : $('#fromLocationID').val(),
                	clothTypesId : $("#clothTypeId_"+d).val(),
                    ajax: "true"
                }, function(e) {
                	hideLayer();
                	
                	var typeOfCloth	= $('#stockTypeId_'+d).val();
                	if(typeOfCloth == 1){
                		$('#stockQty_'+d).val(e.htData.detailsDto.newStockQuantity);
                	} else {
                		$('#stockQty_'+d).val(e.htData.detailsDto.usedStockQuantity);	
                	}
                    
                })
            })
            
            
        }))
    }), $(b).on("click", ".remove_field", function(a) {
        a.preventDefault(), $(this).parent("div").remove(), d--
    })
});

function validateQty(d){
	
		var currentStockQty		= Number($('#stockQty_'+d).val());
		var transferStckQty		= Number($('#transferStockQty_'+d).val());
	
	if(transferStckQty > currentStockQty){
		$('#transferStockQty_'+d).val(0);
		showMessage('info', 'You can not Add Quantity More Than '+currentStockQty);
		return false;
	}
}


function saveUpholsteryTransfer(){
	
	if(Number($("#fromLocationID").val()) == 0){
		showMessage('errors','Please select Transfer From Location!');
		return false;
	}
	
	if(Number($("#toLocationId").val()) == 0){
		showMessage('errors','Please select Transfer To Location!');
		return false;
	}
	
	if(Number($("#transferReceivedById").val()) == 0){
		showMessage('errors','Please select Transfer Received By!');
		return false;
	}
	
	if(Number($("#fromLocationID").val()) == Number($("#toLocationId").val())){
		showMessage('errors','Transfer From and Transfer To Cannot be same !');
		return false;
	}
	
	/*if(Number($("#clothTypeId_0").val()) == 0){
		showMessage('errors','Please select Upholstery Name!');
		return false;
	}*/
	
	var noQuantity = false;
	$('input[name*=transferStockQty]' ).each(function(){
		var vehicleVal = $("#"+$( this ).attr( "id" )).val();
		if(vehicleVal <= 0){
			 $("#"+$( this ).attr( "id" )).focus();
			 noQuantity	= true;
			showMessage('errors', 'Please Enter Quantity!');
			return false;
		}
	});
	
	var noStockType	= false;
	$('select[name*=stockType]' ).each(function(){
		var vehicleVal = $("#"+$( this ).attr( "id" )).val();
		if(vehicleVal <= 0){
			$("#"+$( this ).attr( "id" )).select2('focus');
			noStockType	= true;
			showMessage('errors', 'Please Select Stock Type!');
			return false;
		}
	});
	
	var noManufacturer	= false;
	$('input[name*=clothType]' ).each(function(){
		var vehicleVal = $("#"+$( this ).attr( "id" )).val();
		if(vehicleVal <= 0){
			 $("#"+$( this ).attr( "id" )).select2('focus');
			 noManufacturer	= true;
			showMessage('errors', 'Please Select Cloth Types!');
			return false;
		}
	});
	
	
	
	
	if(noManufacturer || noQuantity || noStockType ){
		return false;
	}
	
	var jsonObject 	= new Object();
	var clothType	= new Array();
	var	stockType	= new Array();
	var stckQty		= new Array();
	
	jsonObject["fromLocationID"]			= $('#fromLocationID').val();
	jsonObject["toLocationId"]				= $('#toLocationId').val();
	jsonObject["transferReceivedById"]		= $('#transferReceivedById').val();
	jsonObject["transferViaId"]				= $('#transferViaId').val();
	jsonObject["transferReasonId"]			= $('#transferReasonId').val();
	jsonObject["clothTypeId"]				= $('#clothTypeId_0').val();
	jsonObject["stockTypeId"]				= $('#stockTypeId_0').val();
	jsonObject["transferStockQty"]			= $('#transferStockQty_0').val();
	
	
	$("input[name=clothType]").each(function(){
		clothType.push($(this).val().replace(/"/g, ""));
	});
	
	$("select[name=stockType]").each(function(){
		stockType.push($(this).val());
	});
	
	$("input[name=transferStockQty]").each(function(){
		stckQty.push($(this).val());
	});
	
	var array = new Array();
	for (var i=0; i<clothType.length; i++){
		var InventoryUpholsteryTransfer = new Object();
		InventoryUpholsteryTransfer.clothTypeId  = clothType[i];
		InventoryUpholsteryTransfer.stockType	 = stockType[i];
		InventoryUpholsteryTransfer.stckQty		 = stckQty[i];
		
		array.push(InventoryUpholsteryTransfer);
	}
	
	jsonObject.UpholsteryTransfer = JSON.stringify(array);
	
	
	showLayer();
	$.ajax({
        url: "saveUpholsteryTransfer",
        type: "POST",
        dataType: 'json',
        data: jsonObject,
        success: function (data) {
        	window.location.replace("ClothInventory.in?saved=true");
        	showMessage('info', 'Upholstery Transfered Successfully!')
        	hideLayer();
        },
        error: function (e) {
       	 showMessage('errors', 'Some error occured!')
       	 hideLayer();
        }
	});
}
    
function validateInspectionParameter(object){
	var currentId		= object.id.split("_")[1];
	var clothTypeCurr	= $("#clothTypeId_"+currentId).val();
	var stockTypeValNew	= $("#stockTypeId_"+currentId).val();
	
	var flag = true;
	$('input[name*=clothType]').each(function(obj){
		var oldId		= this.id.split("_")[1];
		var stockTypeValOld	= $("#stockTypeId_"+oldId).val();
		
		if(this.value == clothTypeCurr){
			if(stockTypeValNew == stockTypeValOld){
				if(!flag){
					showMessage('info','Already Selected !');
					$("#"+object.id).select2('val', '');
				}
				flag= false;
			}
		}
		
	});
}