
$(document).ready(function() {
	$("#TripSelectVehicle_ID").select2({
		minimumInputLength : 2,
		minimumResultsForSearch : 10,
		ajax : {
			url : "getVehicleSearchServiceEntrie.in?Action=FuncionarioSelect2",
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
							text : e.vehicle_registration,
							slug : e.slug,
							id : e.vid
						}
					})
				}
			}
		}
	}),$("#tallyCompanyId").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getTallyCompanySearchList.in?Action=FuncionarioSelect2",
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
                            text: a.companyName ,
                            slug: a.slug,
                            id: a.tallyCompanyId
                        }
                    })
                }
            }
        }
    })
});

$(document).ready(function() {
			$("#location").select2();
			$("#batterryTypeId").select2();
			$("#tagPicker").select2({
				closeOnSelect : !1
  }),$("#clothTypes").select2({
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
	    }), $("#warehouselocation , #warehouselocation2").select2({
            minimumInputLength: 2,
            minimumResultsForSearch: 10,
            ajax: {
                url: "getSearchPartLocations.in?Action=FuncionarioSelect2",
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
                                text: a.partlocation_name,
                                slug: a.slug,
                                id: a.partlocation_id
                            }
                        })
                    }
                }
            }
        }),$("#selectVendor").on("change", function() {
            var a = document.getElementById("selectVendor").value;
            if (0 != a) {
                var b = '<option value="1"> CASH</option><option value="2">CREDIT</option><option value="3">NEFT</option><option value="4">RTGS</option><option value="5">IMPS</option>';
                $("#renPT_option").html(b)
            } else {
                var b = '<option value="1">CASH</option>';
                $("#renPT_option").html(b)
            }
        }), $("#selectVendor").change(), $("#renPT_option").on("change", function() {
            showoption()
        }), $("#renPT_option").change(),$("#selectVendor").select2({
            minimumInputLength: 3,
            minimumResultsForSearch: 10,
            ajax: {
                url: "getClothVendorSearchList.in?Action=FuncionarioSelect2",
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
                                text: a.vendorName + " - " + a.vendorLocation,
                                slug: a.slug,
                                id: a.vendorId
                            }
                        })
                    }
                }
            }
        });
			var saved 			= getUrlParameter('saved');
			var deleted			= getUrlParameter('deleted');
			var noRecordFound 	= getUrlParameter('noRecordFound');
		if(saved == true || saved == 'true'){
			showMessage('success', 'Cloth Details Saved Successfully !');
		}
		if(deleted == true || deleted == 'true'){
			showMessage('success', 'Data Deleted Successfully !');
		}
		if(noRecordFound == true || noRecordFound == 'true'){
			showMessage('info', 'No Record Found !');
		}
		
		if($('#discountTaxTypId').val() == 1){
			$('#percentId').addClass('active');
			$('#finalDiscountTaxTypId').val(1);
		} else {
			$('#amountId').addClass('active');
			$('#finalDiscountTaxTypId').val(2);
		}
});

function selectDiscountTaxType(typeId, d){
	
	if(d == 0){
		
		if(typeId == 1){
			$('#percentId').addClass('active');
			$('#amountId').removeClass('active');
			$('#finalDiscountTaxTypId').val(1);
		} else {
			$('#amountId').addClass('active');
			$('#percentId').removeClass('active');
			$('#finalDiscountTaxTypId').val(2);
		}
		
	} else {
		
		if(typeId == 1){
			$("#percentId" + d).addClass('active');
			$("#amountId" + d).removeClass('active');
			$("#finalDiscountTaxTypId" + d).val(1);
		} else {
			$("#amountId" + d).addClass('active');
			$("#percentId" + d).removeClass('active');
			$("#finalDiscountTaxTypId" + d).val(2);
		}
		
	}
	
}

function showHideAddCloth(){
	if($('#addClothDiv').hasClass('hide')){
		$('#addClothDiv').removeClass('hide');
		$('#searchData').hide();
		$('#listTab').hide();
		$("#VendorPaymentTable").empty();
		$("#navigationBar").hide();
	}else{
		$('#addClothDiv').addClass('hide');
		$('#searchData').show();
		$('#listTab').show();
		$("#navigationBar").show();
		getPageWiseClothInvoiceDetails(1);
	}
}

function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
};

function showoption() {
    var a = $("#renPT_option :selected"),
        b = a.text();
    "Cash" == b ? $("#target1").text(b + " Receipt NO") : "Cheque" == b ? $("#target1").text(b + " NO") : "CREDIT" == b ? $("#target1").text(b + " Transaction NO") : $("#target1").text(b + " Transaction NO")
}
$(document).ready(function() {
    var a = 10,
        b = $(".input_fields_wrap"),
        c = $(".add_field_button"),
        d = 1;
    $(c).click(function(c) {
        c.preventDefault(), a > d && (d++, $(b).append('<div><div class="panel panel-success"><div class="panel-body"><div class="row1"><label class="L-size control-label">Upholstery Types :<abbr title="required">*</abbr></label><div class="col-md-3"><input type="hidden" id="clothTypes' + d + '" name="clothTypes" style="width: 100%;" required="required" placeholder="Please Enter 2 or more Cloth Type Name" /></div><input type="hidden" id="finalDiscountTaxTypId' + d + '" name="finalDiscountTaxTypId"><label class="L-size control-label" for="payMethod">Discount/GST Type :<abbr title="required">*</abbr></label><div class="col-md-3"><div class=""><div class="btn-group"><label id="percentId' + d + '" class="btn btn-default  btn-sm active" onclick="selectDiscountTaxType(1,'+d+');">Percentage</label><label id="amountId' + d + '" class="btn btn-default  btn-sm" onclick="selectDiscountTaxType(2,'+d+');">Amount</label></div></div></div></div><div class="row1"><label class="L-size control-label"></label><div class="col-md-9"><div class="col-md-1"><label class="control-label">Quantity</label></div><div class="col-md-2"><label class="control-label">Unit Cost</label></div><div class="col-md-1"><label class="control-label">Discount</label></div><div class="col-md-1"><label class="control-label">GST</label></div><div class="col-md-2"><label class="control-label">Total</label></div></div></div><div class="row1"><label class="L-size control-label" for="issue_vehicle_id"></label><div class="col-md-9"><div class="col-md-1"><input type="text" class="form-text" name="quantity_many" min="0.0" id="quantity' + d + '" maxlength="4" placeholder="ex: 23.78" required="required"data-toggle="tip"data-original-title="enter Part Quantity" onkeypress="return isNumberKey(event,this);"onkeyup="javascript:sumthereTypeWise(\'quantity' + d + "', 'unitprice" + d + "', 'discount" + d + "', 'tax" + d + "', 'tatalcost" + d + "', '" + d + '\' );"\t ondrop="return false;"></div><div class="col-md-2"><input type="text" class="form-text" name="unitprice_many" id="unitprice' + d + '" maxlength="10" min="0.0"placeholder="Unit Cost" required="required" data-toggle="tip" data-original-title="enter Unit Price" onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="javascript:sumthereTypeWise(\'quantity' + d + "', 'unitprice" + d + "', 'discount" + d + "', 'tax" + d + "', 'tatalcost" + d + "', '" + d + '\' );"\tondrop="return false;"></div><div class="col-md-1"><input type="text" class="form-text" name="discount_many" min="0.0" id="discount' + d + '" maxlength="5" placeholder="Discount" required="required"data-toggle="tip" data-original-title="enter Discount" onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="javascript:sumthereTypeWise(\'quantity' + d + "', 'unitprice" + d + "', 'discount" + d + "', 'tax" + d + "', 'tatalcost" + d + "', '" + d + '\' );"\tondrop="return false;"></div> <div class="col-md-1"> <input type="text" class="form-text" name="tax_many" id="tax' + d + '" maxlength="5" placeholder="GST" required="required"data-toggle="tip" data-original-title="enter GST" onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="javascript:sumthereTypeWise(\'quantity' + d + "', 'unitprice" + d + "', 'discount" + d + "', 'tax" + d + "', 'tatalcost" + d + "', '" + d + '\' );"ondrop="return false;"></div><div class="col-md-2"><input type="text" class="form-text" maxlength="8" value="0.0" min="0.0" name="tatalcost" id="tatalcost' + d + '" readonly="readonly"data-toggle="tip" data-original-title="Total Cost" onkeypress="return isNumberKey(event,this);" ondrop="return false;"></div></div></div></div></div><a href="#" class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font></div> '), $(document).ready(function() {
            $("#clothTypes" + d).select2({
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
            
            $("#unitprice" + d).keyup(function() {
                if ($(this).val($(this).val().replace(/[^0-9\.]/g, "")), -1 != $(this).val().indexOf(".") && $(this).val().split(".")[1].length > 2) {
                    if (isNaN(parseFloat(this.value))) return;
                    this.value = parseFloat(this.value).toFixed(2)
                }
                return this
            })
            
    		if($('#discountTaxTypId').val() == 1){
    			$("#percentId" + d).addClass('active');
    			$("#finalDiscountTaxTypId" + d).val(1);
    		} else {
    			$("#amountId" + d).addClass('active');
    			$("#finalDiscountTaxTypId" + d).val(2);
    		}
        }))
    }), $(b).on("click", ".remove_field", function(a) {
        a.preventDefault(), $(this).parent("div").remove(), d--
    })
});
$(document).ready(
		function($) {
			$('button[id=submit]').click(function(e) {
				e.preventDefault();
				$('#saveUpholstery').hide();
				
				if(!validateCloth()){
					$('#saveUpholstery').show();
					return false;
				}
				
				if(!validateUpholstery()){
					$('#saveUpholstery').show();
					return false;
				}
				
				var jsonObject			= new Object();
				var tax_many = new Array();
				var discount_many = new Array();
				var batteryMan = new Array();
				var batteryType = new Array();
				var quantity_many = new Array();
				var unitprice_many = new Array();
				var tatalcost	   = new Array();
				var batteryCapacity = new Array();
				var disTaxTypeIdMany = new Array();

				jsonObject["warehouselocation"] 	  			=  $('#warehouselocation').val();
				jsonObject["paymentType"] 						=  $('#renPT_option').val();
				jsonObject["paymentNumber"] 					=  $('#PAYMENT_NUMBER').val();
				jsonObject["poNumber"] 							=  $('#PO_NUMBER').val();
				jsonObject["invoiceNumber"] 					=  $('#INVOICE_NUMBER').val();
				jsonObject["invoiceDate"] 						=  $('#invoiceDate').val();
				if($('#roundupConfig').val()==true || $('#roundupConfig').val()=='true'){
					jsonObject["invoiceAmount"] 					=  $('#roundupTotal').val(); 
				}else{
					jsonObject["invoiceAmount"] 					=  $('#invoiceAmount').val();
				}
				jsonObject["description"] 						=  $('#DESCRIPTION').val();
				jsonObject["vendor"] 							=  $('#selectVendor').val();
				
				jsonObject["typeOfCloth"] 						=  $('#typeOfCloth').val();
				jsonObject["tallyCompanyId"] 					=  $('#tallyCompanyId').val();
				jsonObject["clothDocument"] 					=  $('#clothDocument').val();
				jsonObject["subLocationId"] 					=  $('#subLocationId').val();
				jsonObject["validateDoublePost"]				= true;
				jsonObject["unique-one-time-token"]				= $('#accessToken').val();

				$("input[name=clothTypes]").each(function(){
					batteryMan.push($(this).val().replace(/"/g, ""));
				});
				
				$("input[name=quantity_many]").each(function(){
					quantity_many.push($(this).val());
				});
				$("input[name=unitprice_many]").each(function(){
					unitprice_many.push($(this).val());
				});
				$("input[name=discount_many]").each(function(){
					discount_many.push($(this).val());
				});

				$("input[name=tax_many]").each(function(){
					tax_many.push($(this).val());
				});
				$("input[name=tatalcost]").each(function(){
					tatalcost.push($(this).val());
				});
				$("input[name=finalDiscountTaxTypId]").each(function(){
					disTaxTypeIdMany.push($(this).val());
				});
				
				var array	 = new Array();
				var totalQuantity = 0;
				var totalcost=0;
				for(var i =0 ; i< batteryMan.length; i++){
					var clothTypeDetails	= new Object();
					clothTypeDetails.clothTypes				= batteryMan[i];
					clothTypeDetails.batteryType			= batteryType[i];
					clothTypeDetails.quantity				= quantity_many[i];
					clothTypeDetails.unitprice				= unitprice_many[i];
					clothTypeDetails.discount				= discount_many[i];
					clothTypeDetails.tax					= tax_many[i];
					clothTypeDetails.tatalcost				= tatalcost[i];
					clothTypeDetails.disTaxTypeId			= disTaxTypeIdMany[i];
					
					totalQuantity +=  Number(quantity_many[i]);
					
					totalcost+=Number(tatalcost[i]);
					array.push(clothTypeDetails);
				}
				jsonObject.clothDetails = JSON.stringify(array);
				
				jsonObject["totalQuantity"] 						= totalQuantity;
				
				jsonObject["totalClothAmount"] 					=  totalcost;
				console.log("jsonObject...",jsonObject);
				
				var form = $('#fileUploadForm')[0];
			    var data = new FormData(form);
			    
			    if(typeof(allowBankPaymentDetails) !== "undefined" && allowBankPaymentDetails) {
					prepareObject(jsonObject);
				}

			    data.append("clothInvoiceData", JSON.stringify(jsonObject));
				
				
				showLayer();
				$.ajax({
						type: "POST",
						enctype: 'multipart/form-data',
						url: "saveClothInventoryDetails",
						data: data,
						processData: false, //prevent jQuery from automatically transforming the data into a query string
				        contentType: false,
				        cache: false,
						success: function (data) {
			            	if(data.invoiceId != undefined) {
			            		emptyAddClothPage(data);			            		
			            	} else if(data.duplicateInvoiceNumber != undefined && data.duplicateInvoiceNumber == true){
			            		showMessage('info','Invoice Number Already Exits');	
			            		$('#saveUpholstery').show();
			            	}else {
			            		showMessage('errors', data.saveMessage);			            		
			            	}
			            	 hideLayer();
			             },
			             error: function (e) {
			            	 showMessage('errors', 'Some error occured!')
			            	 hideLayer();
			             }
				});
			
			});

		});

function validateUpholstery(){
	
	var noCloth	= false;
	
	
	$('input[name*=clothTypes]' ).each(function(){
		var vehicleVal = $("#"+$( this ).attr( "id" )).val();
		if(vehicleVal <= 0 || vehicleVal == undefined){
			 $("#"+$( this ).attr( "id" )).select2('focus');
			 noCloth	= true;
			
		}
	});
	
	if(noCloth) {
		showMessage('errors', 'Please Select Cloth Types!');
			return false;
      }
	
	
	var noQuantity = false;
	
	$('input[name*=quantity_many]' ).each(function(){
		var vehicleVal = $("#"+$( this ).attr( "id" )).val();
		if(vehicleVal <= 0){
			 $("#"+$( this ).attr( "id" )).focus();
			 noQuantity	= true;
		}
	});
	
	
	if(noQuantity) {
		showMessage('errors', 'Please Enter Quantity!');
			return false;
      }
      
	var noCost = false;
	
	$('input[name*=unitprice_many]' ).each(function(){
		var vehicleVal = $("#"+$( this ).attr( "id" )).val();
		if(vehicleVal <= 0){
			 $("#"+$( this ).attr( "id" )).focus();
			 noCost	= true;
			
		}
	});
	
	if(noCost) {
		showMessage('errors', 'Please Enter Unit Cost!');
			return false;
      }
	
	var noDiscount = false;
	
	$('input[name*=discount_many]' ).each(function(){
		var vehicleVal = $("#"+$( this ).attr( "id" )).val();
		if(vehicleVal =="" || vehicleVal <= 0){
			 $("#"+$( this ).attr( "id" )).val(0);
		}
		
	});
	
	if(noDiscount) {
		showMessage('errors', 'Please Enter Discount!');
			return false;
      }
	
	var noTax = false;
	$('input[name*=tax_many]' ).each(function(){
		var vehicleVal = $("#"+$( this ).attr( "id" )).val();
		if(vehicleVal <= 0){
			 $("#"+$( this ).attr( "id" )).val(0);
		}
		
	});
	
	
	if(noTax) {
		showMessage('errors', 'Please Enter tax!');
			return false;
      }
	if(noCloth || noQuantity || noCost || noDiscount || noTax ){
		return false;
		}
	
	if(Number($('#warehouselocation').val() <= 0)){
		 $("#warehouselocation").select2('focus');
		showMessage('errors', 'Please Select WareHouse Location!');
		return false;
	}
	if(Number($('#selectVendor').val() <= 0)){
		 $("#selectVendor").select2('focus');
		showMessage('errors', 'Please Select Vendor!');
		return false;
	}
	if($('#tallyIntegrationRequired').val() == 'true' || $('#tallyIntegrationRequired').val() == true){
		if(Number($('#tallyCompanyId').val() <= 0)){
			$("#tallyCompanyId").select2('focus');
			showMessage('errors', 'Please Select Tally Company!');
			return false;
		}
	
	}
	
	return true;
}

function sumthere(a, b, c, d, e) {
    var f = document.getElementById(a).value,
        g = document.getElementById(b).value,
        h = document.getElementById(c).value,
        i = document.getElementById(d).value,
        j = parseFloat(f) * parseFloat(g),
        k = j * h / 100,
        l = j - k,
        a = l * i / 100,
        m = l + a;
    isNaN(m) || (document.getElementById(e).value = m.toFixed(2))
    var invoiceAmount = 0;
    $("input[name=tatalcost]").each(function(){
    	invoiceAmount += Number($(this).val());
    	$('#invoiceAmount').val((invoiceAmount).toFixed(2));
    	$('#viewonlyInvoiceamount').val((invoiceAmount).toFixed(2));
	});
}

function sumthereTypeWise(a, b, c, d, e, multiple) {
	
	console.log("multi..",multiple);
	
	var dicTaxId;
	if(multiple == 0){
		console.log("og..");
		 dicTaxId = $('#finalDiscountTaxTypId').val();
	} else {
		console.log("multi..");
		 dicTaxId = $("#finalDiscountTaxTypId" + multiple).val();
	}
	
	if(dicTaxId == 1){
		var f = document.getElementById(a).value,
	        g = document.getElementById(b).value,
	        h = document.getElementById(c).value,
	        i = document.getElementById(d).value,
	        j = parseFloat(f) * parseFloat(g),
	        k = j * h / 100,
	        l = j - k,
	        a = l * i / 100,
	        m = l + a;
	    isNaN(m) || (document.getElementById(e).value = m.toFixed(2))
	    var invoiceAmount = 0;
	    $("input[name=tatalcost]").each(function(){
	    	invoiceAmount += Number($(this).val());
	    	$('#invoiceAmount').val((invoiceAmount).toFixed(2));
	    	$('#viewonlyInvoiceamount').val((invoiceAmount).toFixed(2));
		});
		
	} else {
		var f = document.getElementById(a).value,
	        g = document.getElementById(b).value,
	        h = document.getElementById(c).value,
	        i = document.getElementById(d).value,
	        j = parseFloat(f) * parseFloat(g),
	        k = Number(j) - Number(h),
	        m = Number(k) + Number(i);
	    isNaN(m) || (document.getElementById(e).value = m.toFixed(2))
	    
	    var invoiceAmount = 0;
	    $("input[name=tatalcost]").each(function(){
	    	invoiceAmount += Number($(this).val());
	    	$('#invoiceAmount').val((invoiceAmount).toFixed(2));
	    	$('#viewonlyInvoiceamount').val((invoiceAmount).toFixed(2));
		});
		
	}
    
}

function emptyAddClothPage(data){
	window.location.replace("ClothInventory.in?saved=true");
}
function validateSelection(){
	
	if($('#warehouselocation2').val() <= 0){
		showMessage('errors', 'Please Select WareHouse Location !');
		return false;
	}
	
	return true;
}

$(document).ready(function() {
	$("#damageClothTypes").select2({
    minimumInputLength: 2,
    minimumResultsForSearch: 10,
    ajax: {
        url: "getClothTypesListByClothTypesId.in?Action=FuncionarioSelect2",
        dataType: "json",
        type: "POST",
        contentType: "application/json",
        quietMillis: 50,
        data: function(a) {
        	if(Number($('#damagelocationId').val()) <= 0){
        		showMessage('info', 'Please Select Location First !');
        		$('#damagelocationId').select2('focus');
        		return false;
        	}	
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
}),$("#damageClothTypes").change(function() {
	showLayer();
    0 != $(this).val() && $.getJSON("getLocationClothCountDetails.in", {
    	locationId : $('#damagelocationId').val(),
    	clothTypesId : $("#damageClothTypes").val(),
        ajax: "true"
    }, function(e) {
    	hideLayer();
    	
    	 if(e.htData.StockNotFound != undefined){
          	showMessage('info', 'Upholstery Not Found at this Location. Please Add Upholstery Inventory For this Location First.');
          } 
    	
        $('#damageUsedQuantity').val(e.htData.detailsDto.usedStockQuantity);
        $('#damageNewQuantity').val(e.htData.detailsDto.newStockQuantity);
        $('#damageStockQuantity').val(e.htData.detailsDto.newStockQuantity);
        $('#previousDmgQuantity').val(e.htData.detailsDto.damagedQuantity);
        $('#previousLostQuantity').val(e.htData.detailsDto.losedQuantity);
        
    })
    hideLayer();
}),$("#damageTypeOfCloth").change(function() {
	showLayer();
    0 != $(this).val() && $.getJSON("getLocationClothCountDetails.in", {
    	locationId : $('#damagelocationId').val(),
    	clothTypesId : $("#damageClothTypes").val(),
        ajax: "true"
    }, function(e) {
    	hideLayer();
    	var typeOfCloth	= $('#damageTypeOfCloth').val();
    	if(typeOfCloth == 1){
    		$('#damageStockQuantity').val(e.htData.detailsDto.newStockQuantity);
    	} else {
    		$('#damageStockQuantity').val(e.htData.detailsDto.usedStockQuantity);	
    	}
        
    })
    hideLayer();
}),$("#damagelocationId").select2({
    minimumInputLength: 2,
    minimumResultsForSearch: 10,
    ajax: {
        url: "getSearchPartLocations.in?Action=FuncionarioSelect2",
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
                        text: a.partlocation_name,
                        slug: a.slug,
                        id: a.partlocation_id
                    }
                })
            }
        }
    }
});
	var saved 			= getUrlParameter('saved');
	var deleted			= getUrlParameter('deleted');
	var noRecordFound 	= getUrlParameter('noRecordFound');
if(saved == true || saved == 'true'){
	showMessage('success', 'Data Saved Successfully !');
}
if(deleted == true || deleted == 'true'){
	showMessage('success', 'Data Deleted Successfully !');
}
if(noRecordFound == true || noRecordFound == 'true'){
	showMessage('info', 'No Record Found !');
}

});

function damageQty(){
	$('#addDamageDetails').modal('show');
}

function validateQty(){
	
	var damageStockQuantity	= Number($('#damageStockQuantity').val());
	var damageQuantity		= Number($('#damageQuantity').val());
	
	if(damageQuantity > damageStockQuantity){
		$('#damageQuantity').val(0);
		showMessage('info', 'You can not Add Quantity More Than '+damageStockQuantity);
		return false;
	}
	
	return true;
}


function saveClothDamageDetails(){

	var jsonObject			= new Object();
	jsonObject["damagelocationId"] 	 		 =  $('#damagelocationId').val();
	jsonObject["damageClothTypes"] 		  	 =  $('#damageClothTypes').val();
	jsonObject["damageTypeOfCloth"] 		 =  $('#damageTypeOfCloth').val();
	jsonObject["damageQuantity"] 			 =  $('#damageQuantity').val();
	jsonObject["damageRemark"] 			 	 =  $('#damageRemark').val();
	jsonObject["damageStockQuantity"] 		 =  $('#damageStockQuantity').val();
	
	
	if(Number($('#damagelocationId').val()) <= 0){
		showMessage('info', 'Please Select Location First !');
		$('#damagelocationId').select2('focus');
		return false;
	}
	
	if(Number($('#damageClothTypes').val()) <= 0){
		showMessage('info', 'Please Select Upholstery Name First !');
		$('#damageClothTypes').select2('focus');
		return false;
	}	
	
	if(Number($('#damageTypeOfCloth').val()) <= 0){
		$("#damageTypeOfCloth").select2('focus');
		showMessage('info', 'Please Select Type of Upholstery First !');
		return false;
	}
	
	if(Number($('#damageQuantity').val()) <= 0){
		$("#damageQuantity").select2('focus');
		showMessage('info', 'Please Select Damage Quantity First !');
		return false;
	}
	
	if(!validateQty()){
		return false;
	}
	
	showLayer();
	$.ajax({
             url: "saveClothDamageDetails",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 
            	 if(data.QuantityExceeded != undefined && data.QuantityExceeded == true){
     				$('#addDamageDetails').modal('hide');
     				showMessage('info', 'You Can Not Add Quantity More Than Available Stock Quantity !');
     				hideLayer();
     				return false;
     			}
            	 
            	 window.location.replace("ClothInventory.in?saved=true");
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});
	
}


$(document).ready(function() {
	$("#lostClothTypes").select2({
    minimumInputLength: 2,
    minimumResultsForSearch: 10,
    ajax: {
        url: "getClothTypesListByClothTypesId.in?Action=FuncionarioSelect2",
        dataType: "json",
        type: "POST",
        contentType: "application/json",
        quietMillis: 50,
        data: function(a) {
        	if(Number($('#lostlocationId').val()) <= 0){
        		showMessage('info', 'Please Select Location First !');
        		$('#lostlocationId').select2('focus');
        		return false;
        	}	
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
}),$("#lostClothTypes").change(function() {
	showLayer();
    0 != $(this).val() && $.getJSON("getLocationClothCountDetails.in", {
    	locationId : $('#lostlocationId').val(),
    	clothTypesId : $("#lostClothTypes").val(),
        ajax: "true"
    }, function(e) {
    	hideLayer();
    	
    	if(e.htData.StockNotFound != undefined){
          	showMessage('info', 'Upholstery Not Found at this Location. Please Add Upholstery Inventory For this Location First.');
          } 
    	
        $('#lostUsedQuantity').val(e.htData.detailsDto.usedStockQuantity);
        $('#lostNewQuantity').val(e.htData.detailsDto.newStockQuantity);
        $('#availableStockQuantity').val(e.htData.detailsDto.newStockQuantity);
        $('#prevDmgQuantity').val(e.htData.detailsDto.damagedQuantity);
        $('#prevLostQuantity').val(e.htData.detailsDto.losedQuantity);
        
    })
    hideLayer();
}),$("#lostTypeOfCloth").change(function() {
	showLayer();
    0 != $(this).val() && $.getJSON("getLocationClothCountDetails.in", {
    	locationId : $('#lostlocationId').val(),
    	clothTypesId : $("#lostClothTypes").val(),
        ajax: "true"
    }, function(e) {
    	hideLayer();
    	var typeOfCloth	= $('#lostTypeOfCloth').val();
    	if(typeOfCloth == 1){
    		$('#availableStockQuantity').val(e.htData.detailsDto.newStockQuantity);
    	} else {
    		$('#availableStockQuantity').val(e.htData.detailsDto.usedStockQuantity);	
    	}
        
    })
}),$("#lostlocationId").select2({
    minimumInputLength: 2,
    minimumResultsForSearch: 10,
    ajax: {
        url: "getSearchPartLocations.in?Action=FuncionarioSelect2",
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
                        text: a.partlocation_name,
                        slug: a.slug,
                        id: a.partlocation_id
                    }
                })
            }
        }
    }
},
$("#ReportSelectVehicle").select2( {
    minimumInputLength:2, minimumResultsForSearch:10, ajax: {
        url:"getVehicleSearchServiceEntrie.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
            return {
                term: a
            }
        }
        , results:function(a) {
            return {
                results:$.map(a, function(a) {
                    return {
                        text: a.vehicle_registration, slug: a.slug, id: a.vid
                    }
                }
                )
            }
        }
    }
}
),
$("#driverAllList").select2( {
    minimumInputLength:3, minimumResultsForSearch:10, ajax: {
        url:"getDriverALLListOfCompany.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
            return {
                term: a
            }
        }
        , results:function(a) {
            return {
                results:$.map(a, function(a) {
                    return {
                        text: a.driver_empnumber+" "+a.driver_firstname+" "+a.driver_Lastname+" - "+a.driver_fathername, slug: a.slug, id: a.driver_id
                    }
                }
                )
            }
        }
    }
}
)
);
	var saved 			= getUrlParameter('saved');
	var deleted			= getUrlParameter('deleted');
	var noRecordFound 	= getUrlParameter('noRecordFound');
if(saved == true || saved == 'true'){
	showMessage('success', 'Data Saved Successfully !');
}
if(deleted == true || deleted == 'true'){
	showMessage('success', 'Data Deleted Successfully !');
}
if(noRecordFound == true || noRecordFound == 'true'){
	showMessage('info', 'No Record Found !');
}

});

function lostQty(){
	$('#addLostDetails').modal('show');
}

function valQty(){
	
	var availableStockQuantity	= Number($('#availableStockQuantity').val());
	var lostQuantity			= Number($('#lostQuantity').val());
	
	if(lostQuantity > availableStockQuantity){
		$('#lostQuantity').val(0);
		showMessage('info', 'You can not Add Quantity More Than '+availableStockQuantity);
		return false;
	}
	
	return true;
}

function saveClothLostDetails(){

	var jsonObject			= new Object();
	jsonObject["lostlocationId"] 	 		 =  $('#lostlocationId').val();
	jsonObject["lostClothTypes"] 		  	 =  $('#lostClothTypes').val();
	jsonObject["lostTypeOfCloth"] 			 =  $('#lostTypeOfCloth').val();
	jsonObject["lostQuantity"] 				 =  $('#lostQuantity').val();
	jsonObject["lostRemark"] 				 =  $('#lostRemark').val();
	jsonObject["availableStockQuantity"] 	 =  $('#availableStockQuantity').val();
	jsonObject["vehicleId"] 				 =  $('#ReportSelectVehicle').val();
	jsonObject["driverConductorId"] 	 	 =  $('#driverAllList').val();
	
	
	if(Number($('#lostlocationId').val()) <= 0){
		showMessage('info', 'Please Select Location First !');
		$('#lostlocationId').select2('focus');
		return false;
	}
	
	if(Number($('#lostClothTypes').val()) <= 0){
		showMessage('info', 'Please Select Upholstery Name First !');
		$('#lostClothTypes').select2('focus');
		return false;
	}	
	
	if(Number($('#lostTypeOfCloth').val()) <= 0){
		$("#lostTypeOfCloth").select2('focus');
		showMessage('info', 'Please Select Type of Upholstery First !');
		return false;
	}
	
    if( $('#driverAllList').val() == "0" || $('#driverAllList').val() == null ){
		showMessage('info','Please Enter Driver/Conductor Name');
		return false;
	}
	
	
	
	if(Number($('#lostQuantity').val()) <= 0){
		$("#lostQuantity").select2('focus');
		showMessage('info', 'Please Select Lost Quantity First !');
		return false;
	}
	
	if(!valQty()){
		return false;
	}
	
	showLayer();
	$.ajax({
             url: "saveClothLostDetails",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 
            	 if(data.QuantityExceeded != undefined && data.QuantityExceeded == true){
      				$('#addLostDetails').modal('hide');
      				showMessage('info', 'You Can Not Add Quantity More Than Available Stock Quantity !');
      				hideLayer();
      				return false;
      			}
            	 
            	 window.location.replace("ClothInventory.in?saved=true");
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});
	
}
function forwardToUpholsteryAsignPage(){
	showLayer();
	window.location.replace("VehiclePartInventoryDetails?Id="+$('#TripSelectVehicle_ID').val());
}

