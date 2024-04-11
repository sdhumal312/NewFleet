$(document).ready(function() {
			$("#location").select2();
			$("#batterryTypeId").select2();
			$("#tagPicker").select2({
				closeOnSelect : !1
  }),$("#ureaManufacturer").select2({
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
                url: "getUreaVendorSearchList.in?Action=FuncionarioSelect2",
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
			showMessage('success', 'Urea Inventory Saved Successfully !');
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
        c.preventDefault(), a > d && (d++, $(b).append('<div><div class="panel panel-success"><div class="panel-body"><div class="row1"><label class="L-size control-label">Urea Manufacturer :<abbr title="required">*</abbr></label><div class="col-md-3"><input type="hidden" id="ureaManufacturer' + d + '" name="ureaManufacturer" style="width: 100%;" required="required" placeholder="Please Enter 2 or more Manufacturer Name" /></div><input type="hidden" id="finalDiscountTaxTypId' + d + '" name="finalDiscountTaxTypId"><label class="L-size control-label" for="payMethod">Discount/GST Type :<abbr title="required">*</abbr></label><div class="col-md-3"><div class=""><div class="btn-group"><label id="percentId' + d + '" class="btn btn-default  btn-sm " onclick="selectDiscountTaxType(1,'+d+');">Percentage</label><label id="amountId' + d + '" class="btn btn-default  btn-sm" onclick="selectDiscountTaxType(2,'+d+');">Amount</label></div></div></div></div><div class="row1"><label class="L-size control-label"></label><div class="col-md-9"><div class="col-md-1"><label class="control-label">Liters</label></div><div class="col-md-2"><label class="control-label">Unit Cost</label></div><div class="col-md-1"><label class="control-label">Discount</label></div><div class="col-md-1"><label class="control-label">GST</label></div><div class="col-md-2"><label class="control-label">Total</label></div></div></div><div class="row1"><label class="L-size control-label" for="issue_vehicle_id"></label><div class="col-md-9"><div class="col-md-1"><input type="text" class="form-text" name="quantity_many" min="0.0" id="quantity' + d + '" maxlength="4" placeholder="ex: 23.78" required="required"data-toggle="tip"data-original-title="enter Part Quantity" onkeypress="return isNumberKeyWithDecimal(event,this.id)" onkeyup="javascript:sumthereTypeWise(\'quantity' + d + "', 'unitprice" + d + "', 'discount" + d + "', 'tax" + d + "', 'tatalcost" + d + "', '" + d + '\' );"\tondrop="return false;"></div><div class="col-md-2"><input type="text" class="form-text" name="unitprice_many" id="unitprice' + d + '" maxlength="10" min="0.0"placeholder="Unit Cost" required="required" data-toggle="tip" data-original-title="enter Unit Price" onkeypress="return isNumberKeyWithDecimal(event,this.id)" onkeyup="javascript:sumthereTypeWise(\'quantity' + d + "', 'unitprice" + d + "', 'discount" + d + "', 'tax" + d + "', 'tatalcost" + d + "', '" + d + '\' );"\tondrop="return false;"></div><div class="col-md-1"><input type="text" class="form-text" name="discount_many" min="0.0" id="discount' + d + '" maxlength="5" placeholder="Discount" required="required"data-toggle="tip" data-original-title="enter Discount"   onkeypress="return isNumberKeyWithDecimal(event,this.id)" onkeyup="javascript:sumthereTypeWise(\'quantity' + d + "', 'unitprice" + d + "', 'discount" + d + "', 'tax" + d + "', 'tatalcost" + d + "', '" + d + '\' );"\tondrop="return false;"></div> <div class="col-md-1"> <input type="text" class="form-text" name="tax_many" id="tax' + d + '" maxlength="5" placeholder="GST" required="required"data-toggle="tip" data-original-title="enter GST" onkeypress="return isNumberKeyWithDecimal(event,this.id)" onkeyup="javascript:sumthereTypeWise(\'quantity' + d + "', 'unitprice" + d + "', 'discount" + d + "', 'tax" + d + "', 'tatalcost" + d + "', '" + d + '\' );"ondrop="return false;"></div><div class="col-md-2"><input type="text" class="form-text" maxlength="8" value="0.0" min="0.0" name="tatalcost" id="tatalcost' + d + '" readonly="readonly"data-toggle="tip" data-original-title="Total Cost" onkeypress="return isNumberKey(event,this);" ondrop="return false;"></div></div></div></div></div><a href="#" class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font></div> '), $(document).ready(function() {
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
				
				
				if(!validateUreaInventory()){
					$('#saveUreaInv').show();
					return false;
				}
				
				if($('#manufacturerNonMandatory').val() == true || $('#manufacturerNonMandatory').val() == 'true' ){
					if(!validateUreaInventory()){
						$('#saveUreaInv').show();
						return false;
					}
				}
				
				var jsonObject			= new Object();
				var tax_many = new Array();
				var discount_many = new Array();
				var manufacturer = new Array();
				var batteryType = new Array();
				var quantity_many = new Array();
				var unitprice_many = new Array();
				var tatalcost	   = new Array();
				var batteryCapacity = new Array();
				var disTaxTypeIdMany = new Array();
				

				jsonObject["warehouselocation"] 	  			=  $('#warehouselocation').val();
				jsonObject["vendor"] 							=  $('#selectVendor').val();
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
				jsonObject["tallyCompanyId"] 					=  $('#tallyCompanyId').val();
				jsonObject["ureaDocument"] 						=  $('#ureaDocument').val();
				jsonObject["subLocationId"] 					=  $('#subLocationId').val();
				jsonObject["validateDoublePost"]				= true;
				jsonObject["unique-one-time-token"]				= $('#accessToken').val();
				

				$("input[name=ureaManufacturer]").each(function(){
					manufacturer.push($(this).val().replace(/"/g, ""));
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
					tatalcost.push(Number($(this).val()));
				});
				$("input[name=finalDiscountTaxTypId]").each(function(){
					disTaxTypeIdMany.push($(this).val());
				});
				
				var array	 = new Array();
				var totalQuantity = 0;
				var gradTotalAmount	= 0;
				for(var i =0 ; i< manufacturer.length; i++){
					var ureaDetails	= new Object();
					console.log(manufacturer[i] , "***")
					ureaDetails.manufacturer			= manufacturer[i];
					ureaDetails.quantity				= quantity_many[i];
					ureaDetails.unitprice				= unitprice_many[i];
					ureaDetails.discount				= discount_many[i];
					ureaDetails.tax						= tax_many[i];
					ureaDetails.tatalcost				= tatalcost[i];
					ureaDetails.disTaxTypeId			= disTaxTypeIdMany[i];
					
					totalQuantity +=  Number(quantity_many[i]);
					gradTotalAmount += Number(tatalcost[i]);
					
					array.push(ureaDetails);
				}
				jsonObject.ureaDetails = JSON.stringify(array);
				
				jsonObject["totalQuantity"] 				= totalQuantity;
				jsonObject["totalClothAmount"] 					= gradTotalAmount;
				
				var form = $('#fileUploadForm')[0];
			    var data = new FormData(form);
			       
			   if(typeof(allowBankPaymentDetails) !== "undefined" && allowBankPaymentDetails) {
					prepareObject(jsonObject);
				}

			    data.append("ureaInvoiceData", JSON.stringify(jsonObject));
				
			    showLayer();
				
				$.ajax({
						type: "POST",
						enctype: 'multipart/form-data',
						url: "saveUreaInventoryDetails",
						data: data,
						processData: false, //prevent jQuery from automatically transforming the data into a query string
				        contentType: false,
				        cache: false,
						success: function (data) {
			            	if(data.invoiceId != undefined) {
			            		emptyAddClothPage(data);
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

function validateUreaInventory(){
	var noManufacturer	= false;
	
	$('input[name*=ureaManufacturer]' ).each(function(){
		var vehicleVal = $("#"+$( this ).attr( "id" )).val();
		if(vehicleVal <= 0){
			 $("#"+$( this ).attr( "id" )).select2('focus');
			 noManufacturer	= true;
		}
	});
	if(noManufacturer) {
		showMessage('errors', 'Please Select Manufacturer!');
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
		showMessage('errors', 'Please Enter Liters!');
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
		
		
		
	if(noManufacturer || noQuantity || noCost  || noDiscount || noTax){
		return false;
		}
	});
	
	
	if(noTax) {
		showMessage('errors', 'Please Enter tax!');
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
	});
}

function sumthereTypeWise(a, b, c, d, e, multiple) {
	
	var dicTaxId;
	if(multiple == 0){
		 dicTaxId = $('#finalDiscountTaxTypId').val();
	} else {
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
	    	$('#readOnlyinvoiceAmount').val((invoiceAmount).toFixed(2));
	    	
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
