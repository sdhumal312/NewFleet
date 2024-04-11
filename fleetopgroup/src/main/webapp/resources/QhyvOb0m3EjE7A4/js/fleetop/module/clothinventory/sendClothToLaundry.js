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
	    }),$("#clothTypes").change(function() {
	    	showLayer();
	        0 != $(this).val() && $.getJSON("geLaundryVendorRateAndStockCount.in", {
	        	locationId : $('#warehouselocation').val(),
	        	clothTypesId : $("#clothTypes").val(),
	        	vendorId : $("#selectVendor").val(),
	            ajax: "true"
	        }, function(e) {
	        	hideLayer();
	            $('#stockQuantity').val(e.usedStockQuantity.toFixed(2));
	            $('#unitprice').val(e.clothEachCost);
	            $('#discount').val(e.clothDiscount);
	            $('#tax').val(e.clothGst);
	            if(e.vendorLaundryRateId == 0){
    				showMessage('info',' No Rate Found ');
    			}
	        })
	        hideLayer();
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
                url: "getLaundryVendorSearchList.in?Action=FuncionarioSelect2",
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
		
});

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
        d = 1,
    moreParts = $("#moreParts");
    $(c).click(function(c) {
        c.preventDefault(), a > d && (d++, $(moreParts).append('<div><div class="panel panel-success"><div class="panel-body"><div class="row1"><label class="L-size control-label">Cloth Types :<abbr title="required">*</abbr></label><div class="I-size"><input type="hidden" id="clothTypes' + d + '" name="clothTypes" style="width: 100%;" required="required" placeholder="Please Enter 2 or more Cloth Type Name" /></div></div><div class="row1"><label class="L-size control-label"></label><div class="col-md-9"><div class="col-md-1"><label class="control-label">Stock Qty</label></div><div class="col-md-1"><label class="control-label">Quantity</label></div><div class="col-md-2"><label class="control-label">Unit Cost</label></div><div class="col-md-1"><label class="control-label">Discount</label></div><div class="col-md-1"><label class="control-label">GST</label></div><div class="col-md-2"><label class="control-label">Total</label></div></div></div><div class="row1"><label class="L-size control-label" for="issue_vehicle_id"></label><div class="col-md-9"><div class="col-md-1"><input type="text" readonly="readonly" class="form-text" name="stockQuantity" min="0.0" id="stockQuantity' + d + '" maxlength="4"  \tondrop="return false;"></div><div class="col-md-1"><input type="text" class="form-text" name="quantity_many" min="0.0" id="quantity' + d + '" maxlength="4" placeholder="ex: 23.78" required="required"data-toggle="tip"data-original-title="enter Part Quantity" onkeypress="return isNumberKey(event,this);" onblur="validateQuantity(' + d + ');" onkeyup="validateQuantity(' + d + '); javascript:sumthere(\'quantity' + d + "', 'unitprice" + d + "', 'discount" + d + "', 'tax" + d + "', 'tatalcost" + d + '\' );"\tondrop="return false;"></div><div class="col-md-2"><input type="text" class="form-text" name="unitprice_many" id="unitprice' + d + '" maxlength="7" min="0.0"placeholder="Unit Cost" required="required" data-toggle="tip" data-original-title="enter Unit Price"onkeypress="return isNumberKey(event,this);" onkeyup="javascript:sumthere(\'quantity' + d + "', 'unitprice" + d + "', 'discount" + d + "', 'tax" + d + "', 'tatalcost" + d + '\' );"\tondrop="return false;"></div><div class="col-md-1"><input type="text" class="form-text" name="discount_many" min="0.0" id="discount' + d + '" maxlength="5" placeholder="Discount" required="required"data-toggle="tip" data-original-title="enter Discount" onkeypress="return isNumberKeyQut(event,this);"onkeyup="javascript:sumthere(\'quantity' + d + "', 'unitprice" + d + "', 'discount" + d + "', 'tax" + d + "', 'tatalcost" + d + '\' );"\tondrop="return false;"></div> <div class="col-md-1"> <input type="text" class="form-text" name="tax_many" id="tax' + d + '" maxlength="5" placeholder="GST" required="required"data-toggle="tip" data-original-title="enter GST" onkeypress="return isNumberKeyQut(event,this);"onkeyup="javascript:sumthere(\'quantity' + d + "', 'unitprice" + d + "', 'discount" + d + "', 'tax" + d + "', 'tatalcost" + d + '\' );"ondrop="return false;"></div><div class="col-md-2"><input type="text" class="form-text" maxlength="8" value="0.0" min="0.0" name="tatalcost" id="tatalcost' + d + '" readonly="readonly"data-toggle="tip" data-original-title="Total Cost" onkeypress="return isNumberKey(event,this);" ondrop="return false;"></div></div></div></div></div><a href="#" class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font></div> '), $(document).ready(function() {
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
            }),$("#clothTypes" + d).change(function() {
    	    	showLayer();
    	        0 != $(this).val() && $.getJSON("geLaundryVendorRateAndStockCount.in", {
    	        	locationId : $('#warehouselocation').val(),
    	        	clothTypesId : $("#clothTypes"+d).val(),
    	        	vendorId : $("#selectVendor").val(),
    	            ajax: "true"
    	        }, function(e) {
    	        	hideLayer();
    	        	if(e != undefined && e!= null){
    	        		if(e.usedStockQuantity > 0){
    	        			$('#stockQuantity'+ d).val(e.usedStockQuantity);
    	        			$('#unitprice'+d).val(e.clothEachCost);
    	        			$('#discount'+d).val(e.clothDiscount);
    	        			$('#tax'+d).val(e.clothGst);
    	        			if(e.vendorLaundryRateId == 0){
    	        				showMessage('info',' No Rate Found ');
    	        			}
    	        		}else{
    	        			$('#stockQuantity'+ d).val(e.usedStockQuantity);
    	        			$('#unitprice'+d).val(e.clothEachCost);
    	        			$('#discount'+d).val(e.clothDiscount);
    	        			$('#tax'+d).val(e.clothGst);
    	        			if(e.vendorLaundryRateId == 0){
    	        				showMessage('info',' No Rate Found ');
    	        			}
    	        		}
    	        	}else{
    	        		showMessage('info',' No Record Found ');
    	        	}
    	        })
    	    })
        }))
    }), $(moreParts).on("click", ".remove_field", function(a) {
        a.preventDefault(), $(this).parent("div").remove(), d--
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

function sumthere(a, b, c, d, e) {
    var f = document.getElementById(a).value,//Quantity
        g = document.getElementById(b).value,//Unit Price
        h = document.getElementById(c).value,//discount
        i = document.getElementById(d).value,//tax
        j = parseFloat(f) * parseFloat(g),
        k = j * h / 100,
        l = j - k,
        a = l * i / 100,
        m = l + a;
    isNaN(m) || (document.getElementById(e).value = m.toFixed(2))//TotalCost
    var invoiceAmount = 0;
    $("input[name=tatalcost]").each(function(){
    	invoiceAmount += Number($(this).val());
    	$('#invoiceAmount').val(invoiceAmount);
	});
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

function validateQuantity(d){
	if( d == undefined){
		var sq 		 = Number($("#stockQuantity").val());
		var quantity = Number($("#quantity").val());
	} else {
		var sq 		 = Number($("#stockQuantity"+d).val());
		var quantity = Number($("#quantity"+d).val());
	}
	if(quantity > sq ){
		showMessage('errors', 'Quantity Cannot Be More Than Available Stock Quantity!');
		if( d == undefined){
			$("#quantity").val(0);
		} else{
			$("#quantity"+d).val(0);
		}
	}
 }

function sendClothToLaundry(){
		$('#sentLaundry').hide();
		if(!validateSentLaundry()){
			$('#sentLaundry').show();
			return false;
		}
			
			var jsonObject			= new Object();
			var tax_many 			= new Array();
			var discount_many 		= new Array();
			var batteryMan 			= new Array();
			var batteryType 		= new Array();
			var quantity_many 		= new Array();
			var unitprice_many 		= new Array();
			var tatalcost	   		= new Array();
			var stockQuantity_many  = new Array();
			
		
			jsonObject["warehouselocation"] 	  			=  $('#warehouselocation').val();
			jsonObject["vendorId"] 							=  $('#selectVendor').val();
			jsonObject["paymentType"] 						=  $('#renPT_option').val();
			jsonObject["paymentNumber"] 					=  $('#PAYMENT_NUMBER').val();
			jsonObject["quoteNumber"] 						=  $('#quoteNumber').val();
			jsonObject["manualNumber"] 						=  $('#manualNumber').val();
			jsonObject["description"] 						=  $('#description').val();
			jsonObject["sentDate"] 							=  $('#sentDate').val();
			jsonObject["receiveDate"] 						=  $('#receiveDate').val();
			jsonObject["tallyCompanyId"] 						=  $('#tallyCompanyId').val();
			jsonObject["accessToken"] 						=  $('#accessToken').val();
			jsonObject["unique-one-time-token"] 			=  true;
			
		
			$("input[name=clothTypes]").each(function(){
				batteryMan.push($(this).val().replace(/"/g, ""));
			});
			
			$("input[name=stockQuantity]").each(function(){
				stockQuantity_many.push(Number($(this).val()));
			});
			
			$("input[name=quantity_many]").each(function(){
				quantity_many.push(Number($(this).val()));
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
				tatalcost.push(Number($(this).val()));
			});
			
			var array	 = new Array();
			var totalQuantity = 0;
			var totalInvoiceCost	= 0;
			for(var i =0 ; i< batteryMan.length; i++){
				var clothTypeDetails	= new Object();
				clothTypeDetails.clothTypes				= batteryMan[i];
				clothTypeDetails.batteryType			= batteryType[i];
				clothTypeDetails.stockQuantity			= stockQuantity_many[i];
				clothTypeDetails.quantity				= quantity_many[i];
				clothTypeDetails.unitprice				= unitprice_many[i];
				clothTypeDetails.discount				= discount_many[i];
				clothTypeDetails.tax					= tax_many[i];
				clothTypeDetails.totalcost				= tatalcost[i];
				
				totalQuantity +=  quantity_many[i];
				totalInvoiceCost += tatalcost[i]
				
				array.push(clothTypeDetails);
			}
			jsonObject.clothDetails = JSON.stringify(array);
		
			
			jsonObject["totalQuantity"] 						= totalQuantity;
			jsonObject["totalInvoiceCost"] 						= totalInvoiceCost;
				
			if (allowBankPaymentDetails) {
					prepareObject(jsonObject)
			}
			
			showLayer();
			$.ajax({
		             url: "saveSentClothToLaundry",
		             type: "POST",
		             dataType: 'json',
		             data: jsonObject,
		             success: function (data) {
		            	 
		            	 if(data.QuantityExceeded != undefined && data.QuantityExceeded == true){
		     				showMessage('info', 'You can not Add Quantity More Than Available Stock Quantity !');
		     				hideLayer();
		     				$('#sentLaundry').show();
		     				return false;
		     			}
		            	 
		            	if(data.invoiceId != undefined) {
		            		emptyAddClothPage(data);			            		
		            	} else {
		            		showMessage('errors', data.saveMessage);	
		            		$('#sentLaundry').show();
		            	}
		            	 hideLayer();
		             },
		             error: function (e) {
		            	 showMessage('errors', 'Some error occured!');
		            	 $('#sentLaundry').show();
		            	 hideLayer();
		             }
			});

}
