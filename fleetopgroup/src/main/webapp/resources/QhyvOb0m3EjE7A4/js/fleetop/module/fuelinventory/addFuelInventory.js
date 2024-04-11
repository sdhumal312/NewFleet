
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


$(document).ready(function() {
    var a = 10,
        b = $(".input_fields_wrap"),
        c = $(".add_field_button"),
        d = 1;
    $(c).click(function(c) {
        c.preventDefault(), a > d && (d++, $(b).append('<div><div class="panel panel-success"><div class="panel-body"><div class="row1"><label class="L-size control-label">Urea Manufacturer :<abbr title="required">*</abbr></label><div class="col-md-3"><input type="hidden" id="ureaManufacturer' + d + '" name="ureaManufacturer" style="width: 100%;" required="required" placeholder="Please Enter 2 or more Manufacturer Name" /></div><input type="hidden" id="finalDiscountTaxTypId' + d + '" name="finalDiscountTaxTypId"><label class="L-size control-label" for="payMethod">Discount/GST Type :<abbr title="required">*</abbr></label><div class="col-md-3"><div class=""><div class="btn-group"><label id="percentId' + d + '" class="btn btn-default  btn-sm " onclick="selectDiscountTaxType(1,'+d+');">Percentage</label><label id="amountId' + d + '" class="btn btn-default  btn-sm" onclick="selectDiscountTaxType(2,'+d+');">Amount</label></div></div></div></div><div class="row1"><label class="L-size control-label"></label><div class="col-md-9"><div class="col-md-1"><label class="control-label">Liters</label></div><div class="col-md-2"><label class="control-label">Unit Cost</label></div><div class="col-md-1"><label class="control-label">Discount</label></div><div class="col-md-1"><label class="control-label">GST</label></div><div class="col-md-2"><label class="control-label">Total</label></div></div></div><div class="row1"><label class="L-size control-label" for="issue_vehicle_id"></label><div class="col-md-9"><div class="col-md-1"><input type="text" class="form-text" name="quantity_many" min="0.0" id="quantity' + d + '" maxlength="4" placeholder="ex: 23.78" required="required"data-toggle="tip"data-original-title="enter Part Quantity" onkeypress="return isNumberKeyQut(event,this);"onkeyup="javascript:sumthereTypeWise(\'quantity' + d + "', 'unitprice" + d + "', 'discount" + d + "', 'tax" + d + "', 'tatalcost" + d + "', '" + d + '\' );"\tondrop="return false;"></div><div class="col-md-2"><input type="text" class="form-text" name="unitprice_many" id="unitprice' + d + '" maxlength="7" min="0.0"placeholder="Unit Cost" required="required" data-toggle="tip" data-original-title="enter Unit Price"onkeypress="return isNumberKey(event,this);" onkeyup="javascript:sumthereTypeWise(\'quantity' + d + "', 'unitprice" + d + "', 'discount" + d + "', 'tax" + d + "', 'tatalcost" + d + "', '" + d + '\' );"\tondrop="return false;"></div><div class="col-md-1"><input type="text" class="form-text" name="discount_many" min="0.0" id="discount' + d + '" maxlength="5" placeholder="Discount" required="required"data-toggle="tip" data-original-title="enter Discount" onkeypress="return isNumberKeyQut(event,this);"onkeyup="javascript:sumthereTypeWise(\'quantity' + d + "', 'unitprice" + d + "', 'discount" + d + "', 'tax" + d + "', 'tatalcost" + d + "', '" + d + '\' );"\tondrop="return false;"></div> <div class="col-md-1"> <input type="text" class="form-text" name="tax_many" id="tax' + d + '" maxlength="5" placeholder="GST" required="required"data-toggle="tip" data-original-title="enter GST" onkeypress="return isNumberKeyQut(event,this);"onkeyup="javascript:sumthereTypeWise(\'quantity' + d + "', 'unitprice" + d + "', 'discount" + d + "', 'tax" + d + "', 'tatalcost" + d + "', '" + d + '\' );"ondrop="return false;"></div><div class="col-md-2"><input type="text" class="form-text" maxlength="8" value="0.0" min="0.0" name="tatalcost" id="tatalcost' + d + '" readonly="readonly"data-toggle="tip" data-original-title="Total Cost" onkeypress="return isNumberKey(event,this);" ondrop="return false;"></div></div></div></div></div><a href="#" class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font></div> '), $(document).ready(function() {
            $("#ureaManufacturer" + d).select2({
                minimumInputLength: 2,
                minimumResultsForSearch: 10,
                ajax: {
                    url: "getUreaManufacturerList.in?Action=FuncionarioSelect2",
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
    	                            text: a.manufacturerName,
    	                            slug: a.slug,
    	                            id: a.ureaManufacturerId
    	                        }
                            })
                        }
                    }
                }
            }),
            
            console.log("final...");
    		if($('#discountTaxTypId').val() == 1){
    			$("#percentId" + d).addClass('active');
    			$("#finalDiscountTaxTypId" + d).val(1);
    			console.log("final1...");
    		} else {
    			$("#amountId" + d).addClass('active');
    			$("#finalDiscountTaxTypId" + d).val(2);
    			console.log("final2...");
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
				$('#saveFuelInv').hide();
				
				if(!validateFuelInventory()){
					$('#saveFuelInv').show();
					return false;
				}
				
				var jsonObject			= new Object();
				var defaultStockType	= 0;
				var remark				= "FUEL INVOICE CREATED";

				jsonObject["petrolPumpId"] 	  					=  $('#petrolPumpId').val();
				jsonObject["quantity"] 							=  $('#quantity').val();
				jsonObject["unitprice"] 						=  $('#unitprice').val();
				jsonObject["discount"] 							=  $('#discount').val();
				jsonObject["tax"] 								=  $('#tax').val();
				jsonObject["tatalcost"] 						=  $('#tatalcost').val();
				jsonObject["discountTaxTypId"] 					=  $('#discountTaxTypId').val();
				jsonObject["vendorId"] 							=  $('#selectVendor').val();
				jsonObject["paymentTypeId"] 					=  $('#renPT_option').val();
				jsonObject["paymentNumber"] 					=  $('#paymentNumber').val();
				jsonObject["poNumber"] 							=  $('#poNumber').val();
				jsonObject["invoiceNumber"] 					=  $('#invoiceNumber').val();
				jsonObject["invoiceDate"] 						=  $('#invoiceDate').val();
				jsonObject["invoiceAmount"] 					=  $('#invoiceAmount').val();
				jsonObject["tallyCompanyId"] 					=  $('#tallyCompanyId').val();
				jsonObject["fuelDocument"] 						=  $('#fuelDocument').val();
				jsonObject["description"] 						=  $('#description').val();
				jsonObject["companyId"] 						=  $('#companyId').val();
				jsonObject["userId"] 							=  $('#userId').val();
				jsonObject["validateDoublePost"]				= true;
				jsonObject["unique-one-time-token"]				= $('#accessToken').val();
				jsonObject["balanceStock"] 						=  $('#quantity').val();
				jsonObject["updatedStock"] 						=  $('#quantity').val();
				jsonObject["remark"]							=  remark;
				jsonObject["stockType"] 						=  defaultStockType;

				var form = $('#fileUploadForm')[0];
			    var data = new FormData(form);
			    
			      
				if (allowBankPaymentDetails) {
					prepareObject(jsonObject)
				}

			    data.append("fuelInvoiceData", JSON.stringify(jsonObject));
				
			    showLayer();
				
				$.ajax({
						type: "POST",
						enctype: 'multipart/form-data',
						url: "saveFuelInventoryDetails",
						data: data,
						processData: false, //prevent jQuery from automatically transforming the data into a query string
				        contentType: false,
				        cache: false,
						success: function (data) {
							console.log('Data ', data);
			            	if(data.sequenceNotFound != undefined && data.sequenceNotFound) {
			            		showMessage('errors', 'Sequence Counter not Found !');				            		
			            	} else if(data.fuelInvoiceId != undefined) {
			            		showMessage('success', 'Data saved successfully !');
			            		location.replace('FuelInventory.in');
			            	} else {
			            		showMessage('errors', 'Some Error Occured !');			            		
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

function validateUreaInventory(){
	var noManufacturer	= false;
	$('input[name*=ureaManufacturer]' ).each(function(){
		var vehicleVal = $("#"+$( this ).attr( "id" )).val();
		if(vehicleVal <= 0){
			 $("#"+$( this ).attr( "id" )).select2('focus');
			 noManufacturer	= true;
			showMessage('errors', 'Please Select Manufacturer!');
			return false;
		}
	});
	
	
	var noQuantity = false;
	
	$('input[name*=quantity_many]' ).each(function(){
		var vehicleVal = $("#"+$( this ).attr( "id" )).val();
		if(vehicleVal <= 0){
			 $("#"+$( this ).attr( "id" )).focus();
			 noQuantity	= true;
			showMessage('errors', 'Please Enter Liters!');
			return false;
		}
	});
	
	var noCost = false;
	$('input[name*=unitprice_many]' ).each(function(){
		var vehicleVal = $("#"+$( this ).attr( "id" )).val();
		if(vehicleVal <= 0){
			 $("#"+$( this ).attr( "id" )).focus();
			 noCost	= true;
			showMessage('errors', 'Please Enter Unit Cost!');
			return false;
		}
	});
	
	var noDiscount = false;
	$('input[name*=discount_many]' ).each(function(){
		var vehicleVal = $("#"+$( this ).attr( "id" )).val();
		if(vehicleVal <= 0){
			 $("#"+$( this ).attr( "id" )).val(0);
		}
	});
	var noTax = false;
	$('input[name*=tax_many]' ).each(function(){
		var vehicleVal = $("#"+$( this ).attr( "id" )).val();
		if(vehicleVal <= 0){
			 $("#"+$( this ).attr( "id" )).val(0);
		}
	});
	
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
	if(noManufacturer || noQuantity || noCost){
		return false;
	}
	return true;
}

/*function sumthere(a, b, c, d, e) {
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
    	console.log('tatalcost '+$(this).val());
    	invoiceAmount += Number($(this).val());
    	$('#invoiceAmount').val((invoiceAmount).toFixed(2));
	});
}*/

function sumthereTypeWise(a, b, c, d, e, multiple) {
	
	var dicTaxId;
	if(multiple == 0){
		 dicTaxId = $('#discountTaxTypId').val();
	} else {
		console.log("multi..");
		 dicTaxId = $("#discountTaxTypId" + multiple).val();
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
		});
		
	}
	
    
}

function emptyAddClothPage(data){
	window.location.replace("UreaInventory.in?saved=true");
}
function validateSelection(){
	
	if($('#warehouselocation2').val() <= 0){
		showMessage('errors', 'Please Select WareHouse Location !');
		return false;
	}
	
	return true;
}
