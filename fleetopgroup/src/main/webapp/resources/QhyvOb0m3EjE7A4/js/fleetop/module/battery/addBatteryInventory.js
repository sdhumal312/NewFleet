$(document).ready(function() {
			$("#location").select2();
			$("#batterryTypeId").select2();
			$("#tagPicker").select2({
				closeOnSelect : !1
  }),$("#manufacurer").select2({
	        minimumInputLength: 2,
	        minimumResultsForSearch: 10,
	        ajax: {
	            url: "getBatteryManufacturer.in?Action=FuncionarioSelect2",
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
	                            id: a.batteryManufacturerId
	                        }
	                    })
	                }
	            }
	        }
	    }),$("#manufacurer").change(function() {
            $.getJSON("getBatteryType.in", {
                ModelType: $(this).val(),
                ajax: "true"
            }, function(a) {
                for (var b = '<option value="0">Please Select</option>', c = a.length, d = 0; c > d; d++) b += '<option value="' + a[d].batteryTypeId + '">' + a[d].batteryType + "</option>";
                b += "</option>", $("#batterryTypeId").html(b)
            })
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
        }), $("#batteryCapacityId").select2({
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
        }),$("#selectVendor").on("change", function() {
            var a = document.getElementById("selectVendor").value;
            if (0 != a) {
                var b = '<option value="1"> CASH</option><option value="2">CREDIT</option><option value="3">NEFT</option><option value="4">RTGS</option><option value="5">IMPS</option><option value="6">DD</option><option value="7">CHEQUE</option>';
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
                url: "getBatteryVendorSearchList.in?Action=FuncionarioSelect2",
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
			var deleted = getUrlParameter('delete');
		if(deleted == true || deleted == 'true'){
			showMessage('info','Data Deleted Successfully !');
		}else if(deleted == false || deleted == 'false'){
			showMessage('errors','Data Deletion Failed !');
		}	
		
		var ScrapSuccess = getUrlParameter('ScrapSuccess');
		if(ScrapSuccess == true || ScrapSuccess == 'true'){
			showMessage('info', 'Battery Scraped Successfully !');
		}
		var valuesRejected = getUrlParameter('valuesRejected');
		var valuesInserted = getUrlParameter('valuesInserted');
		
		if(valuesRejected != undefined && valuesInserted != undefined){
			showMessage('success', 'No of Battery : '+valuesInserted+' inserted successfully. AND '+ valuesRejected+' Rejected !');
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

function showHideAddBattery(){
	if($('#addBatteryDiv').hasClass('hide')){
		$('#addBatteryDiv').removeClass('hide');
	}else{
		$('#addBatteryDiv').addClass('hide');
	}
	$('#searchData').hide();
	$('#batteryCount').hide();
	$('#listTab').hide();
	$("#VendorPaymentTable").empty();
	$("#navigationBar").empty();
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
        c.preventDefault(), a > d && (d++, $(b).append('<div><div class="panel panel-success"><div class="panel-body"><div class="row1"><label class="L-size control-label">Battery Manufacturer :<abbr title="required">*</abbr></label><div class="col-md-3"><input type="hidden" id="manufacurer' + d + '" name="batteryManufacturerId" style="width: 100%;" required="required" placeholder="Please Enter 2 or more Battery Manufacturer Name" /></div><div><label class="L-size control-label" for="issue_vehicle_id">Battery Type :<abbr title="required">*</abbr></label><div class="col-md-3"><select style="width: 100%;" id="batterryTypeId' + d + '" name="batteryTypeId" required="required"></select> <label class="error" id="errorManufacturer" style="display: none"> </label></div></br></div></br></br><div class="row1"><label class="L-size control-label">Battery Capacity :<abbr title="required">*</abbr></label><div class="col-md-3"><input type="hidden" id="batteryCapacityId' + d + '" name="batteryCapacityId" style="width: 100%;" required="required" placeholder="Please Enter 2 or more Battery Capacity Name" /></div><input type="hidden" id="finalDiscountTaxTypId' + d + '" name="finalDiscountTaxTypId"><label class="L-size control-label" for="payMethod">Discount/GSTType :<abbr title="required">*</abbr></label><div class="col-md-3"><div class=""><div class="btn-group"><label id="percentId' + d + '" class="btn btn-default  btn-sm active" onclick="selectDiscountTaxType(1,'+d+');">Percentage</label><label id="amountId' + d + '" class="btn btn-default  btn-sm" onclick="selectDiscountTaxType(2,'+d+');">Amount</label></div></div></div></div><div class="row1"><label class="L-size control-label"></label><div class="col-md-9"><div class="col-md-1"><label class="control-label">Quantity</label></div><div class="col-md-2"><label class="control-label">Unit Cost</label></div><div class="col-md-1"><label class="control-label">Discount</label></div><div class="col-md-1"><label class="control-label">GST</label></div><div class="col-md-2"><label class="control-label">Total</label></div></div></div><div class="row1"><label class="L-size control-label" for="issue_vehicle_id"></label><div class="col-md-9"><div class="col-md-1"><input type="text" class="form-text" name="quantity_many" onpaste="return false" min="0.0" id="quantity' + d + '" maxlength="4" placeholder="ex: 23.78" required="required"data-toggle="tip"data-original-title="enter Part Quantity" onkeypress="return isNumberKey(event,this);" onkeyup="javascript:sumthereTypeWise(\'quantity' + d + "', 'unitprice" + d + "', 'discount" + d + "', 'tax" + d + "', 'tatalcost" + d + "', '" + d + '\' );"\tondrop="return false;"></div><div class="col-md-2"><input type="text" class="form-text" name="unitprice_many" onpaste="return false" id="unitprice' + d + '" maxlength="10" min="0.0"placeholder="Unit Cost" required="required" data-toggle="tip" data-original-title="enter Unit Price" onkeypress="return isNumberKeyWithDecimal(event,this.id)" onkeyup="javascript:sumthereTypeWise(\'quantity' + d + "', 'unitprice" + d + "', 'discount" + d + "', 'tax" + d + "', 'tatalcost" + d + "', '" + d + '\' );"\tondrop="return false;"></div><div class="col-md-1"><input type="text" class="form-text" name="discount_many" onpaste="return false" min="0.0" id="discount' + d + '" maxlength="5" placeholder="Discount" required="required" data-toggle="tip" data-original-title="enter Discount" onkeypress="return isNumberKeyWithDecimal(event,this.id)" onkeyup="javascript:sumthereTypeWise(\'quantity' + d + "', 'unitprice" + d + "', 'discount" + d + "', 'tax" + d + "', 'tatalcost" + d + "', '" + d + '\' );"\tondrop="return false;"></div> <div class="col-md-1"> <input type="text" class="form-text" name="tax_many" onpaste="return false" id="tax' + d + '" maxlength="5" placeholder="GST" required="required"data-toggle="tip" data-original-title="enter GST" onkeypress="return isNumberKeyWithDecimal(event,this.id)" onkeyup="javascript:sumthereTypeWise(\'quantity' + d + "', 'unitprice" + d + "', 'discount" + d + "', 'tax" + d + "', 'tatalcost" + d + "', '" + d + '\' );"ondrop="return false;"></div><div class="col-md-2"><input type="text" class="form-text" maxlength="8" value="0.0" min="0.0" name="tatalcost" id="tatalcost' + d + '" readonly="readonly"data-toggle="tip" data-original-title="Total Cost" onkeypress="return isNumberKey(event,this);" ondrop="return false;"></div></div></div></div></div><a href="#" class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font></div> '), $(document).ready(function() {
            $("#manufacurer" + d).select2({
                minimumInputLength: 2,
                minimumResultsForSearch: 10,
                ajax: {
                    url: "getBatteryManufacturer.in?Action=FuncionarioSelect2",
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
    	                            id: a.batteryManufacturerId
    	                        }
                            })
                        }
                    }
                }
            }),$("#batteryCapacityId" + d).select2({
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
            }),$("#manufacurer" + d).change(function() {
                $.getJSON("getBatteryType.in", {
                    ModelType: $(this).val(),
                    ajax: "true"
                }, function(a) {
                	console.log("getBatteryType...",a);
                	console.log("ddd...",d);
                    for (var b = '<option value="0">Please Select</option>', c = a.length, f = 0; c > f; f++) b += '<option value="' + a[f].batteryTypeId + '">' + a[f].batteryType + "</option>";
                    b += "</option>", $("#batterryTypeId" + d).html(b)
                })
            }), $("#batterryTypeId" + d).select2(),
            
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
				$('#saveBatteryInv').hide();
				
				if(!validateBatteryUpdate()){
				$('#saveBatteryInv').show();
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
				jsonObject["tallyCompanyId"] 					=  $('#tallyCompanyId').val();
				jsonObject["batteryDocument"] 					=  $('#batteryDocument').val();
				jsonObject["subLocationId"] 					=  $('#subLocationId').val();
				jsonObject["validateDoublePost"]				= true;
				jsonObject["unique-one-time-token"]				= $('#accessToken').val();

				$("input[name=batteryManufacturerId]").each(function(){
					batteryMan.push($(this).val().replace(/"/g, ""));
				});
				$("select[name=batteryTypeId]").each(function(){
					batteryType.push($(this).val());
				});
				$("input[name=batteryCapacityId]").each(function(){
					batteryCapacity.push($(this).val().replace(/"/g, ""));
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
				
				var gradTotalAmount	= 0;
				for(var i =0 ; i< batteryMan.length; i++){
					var batteryManDetails	= new Object();
					
					batteryManDetails.batteryManufacturer	= batteryMan[i];
					batteryManDetails.batteryType			= batteryType[i];
					batteryManDetails.quantity				= quantity_many[i];
					batteryManDetails.unitprice				= unitprice_many[i];
					batteryManDetails.discount				= discount_many[i];
					batteryManDetails.tax					= tax_many[i];
					batteryManDetails.tatalcost				= tatalcost[i];
					batteryManDetails.batteryCapacity		= batteryCapacity[i];
					batteryManDetails.disTaxTypeId			= disTaxTypeIdMany[i];
					
					gradTotalAmount += Number(tatalcost[i]);
					array.push(batteryManDetails);
				}
				jsonObject.batteryDetails = JSON.stringify(array);
				
				jsonObject["totalBatteryAmount"] 		= gradTotalAmount;
				
				var form = $('#fileUploadForm')[0];
			    var data = new FormData(form);
			    
				if (allowBankPaymentDetails) {
					prepareObject(jsonObject)
				}
				

			    data.append("batteryInvoiceData", JSON.stringify(jsonObject));
			
			    
			    showLayer();
				$.ajax({
					type: "POST",
					enctype: 'multipart/form-data',
					url: "saveBatteryDetails",
					data: data,
					processData: false, //prevent jQuery from automatically transforming the data into a query string
			        contentType: false,
			        cache: false,
					success: function (data) {
			            	if(data.batteryInvoice != undefined) {
			            		emptyAddBatteryPage(data);			            		
			            	} else if(data.duplicateInvoiceNumber != undefined && data.duplicateInvoiceNumber == true){
			            		showMessage('info',"Invoice Number Already Exist");	
			            		$('#saveBatteryInv').show();
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

function validateBatteryInventory(){
	var noManufacturer	= false;
	
	$('input[name*=batteryManufacturerId]' ).each(function(){
		var vehicleVal = $("#"+$( this ).attr( "id" )).val();
		
		if(vehicleVal <= 0 || vehicleVal == undefined){
			 $("#"+$( this ).attr( "id" )).select2('focus');
			 noManufacturer	= true;
		}
	});
	
	if(noManufacturer) {
		showMessage('errors', 'Please Select Manufacturer!');
		return false;
	}
	
	var noBatteryType = false;
	
	$('select[name*=batteryTypeId]' ).each(function(){
		var vehicleVal = $("#"+$( this ).attr( "id" )).val();
	
		if(vehicleVal <= 0){
			 $("#"+$( this ).attr( "id" )).select2('focus');
			 noBatteryType	= true;
		}
	});
	
	if(noBatteryType) {
		showMessage('errors', 'Please Select Battery Type!');
		return false;
	}
	
	var batteryCapacity = false;
	
	$('input[name*=batteryCapacityId]' ).each(function(){
		var vehicleVal = $("#"+$( this ).attr( "id" )).val();
		if(vehicleVal <= 0){
			 $("#"+$( this ).attr( "id" )).select2('focus');
			 batteryCapacity	= true;
			
		}
	});
	
	if(batteryCapacity) {
		showMessage('errors', 'Please Select Battery Capacity!');
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
			 noDiscount = true;
			 return false;
		}
	});
	 if(noDiscount) {
		showMessage('errors', 'Please Enter Discount!');
		return false;
      }
	
	
	
	var noTax = false;
	
	
	$('input[name*=tax_many]' ).each(function(){
		var vehicleVal = $("#"+$( this ).attr( "id" )).val();
		if(vehicleVal =="" || vehicleVal<= 0){
			 $("#"+$( this ).attr( "id" )).val(0);
			 noTax = true;
			 
		}
	});
	
	if(noTax) {
		showMessage('errors', 'Please Enter Tax!');
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
	    	$('#readOnlyInvoiceAmount').val((invoiceAmount).toFixed(2));
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

function emptyAddBatteryPage(data){
	window.location.replace("showBatteryInvoice?Id="+data.batteryInvoice+"&saved="+true+"");
}
function validateSelection(){
	
	if($('#warehouselocation2').val() <= 0){
		showMessage('errors', 'Please Select WareHouse Location !');
		return false;
	}
	
	return true;
}

function isNumberKey(evt)
{	
   var charCode = (evt.which) ? evt.which : event.keyCode
   if (charCode > 31 && (charCode < 48 || charCode > 57))
      return false;

   return true;
}


































   






