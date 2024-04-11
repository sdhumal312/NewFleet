var mainPartLocationType = 1;
var subPartLocationType  = 2;
function toggle2(a, b) {
    var c = document.getElementById(a),
        d = document.getElementById(b);
    "block" == c.style.display ? (c.style.display = "none", d.innerHTML = "Add Tyre Details") : (c.style.display = "block", d.innerHTML = "Cancel Tyre Entries")
}

function getMultiTyreInput(a, b, c, showTyreUsesColumn, showTyreStatusAtAdd, STATUS_OF_TYRE) {
    for (var d = "", e = b, f = 1; e >= f; f++) d += '<div class="row"><div class="col-md-1"><label class="control-label">' + f + '</label></div><div class="col-md-3"><input type="text" class="form-text" name="TyreSerialno"></div><div class="col-md-2"><input type="text" class="form-text"value="' + c + '" required="required"readonly="readonly"></div><div class="col-md-1"><input type="number" class="form-text showUses" value="0" name="TYRE_USEAGE"></div><div class="col-md-1"><input type="number" placeholder = "Ex : 1" class="form-text noOfRetread" value="1" name="TYRE_RETREAD_COUNT"></div>'
    +'</div><br>';
    d += "", $("#" + a).html(d);
    if(showTyreUsesColumn == 'false' || showTyreUsesColumn == false){
    	$('.showUses').hide();
    }
    if((showTyreStatusAtAdd == 'false' || showTyreStatusAtAdd == false) || STATUS_OF_TYRE != 1){
    	$('.noOfRetread').hide();
    }
}

function edit_TyreSerialInput(a, b, c) {
    document.getElementById("tyreId").value = a, document.getElementById("tyreInvoiceId").value = b, document.getElementById("TyreSerialName").value = c, $("#editTyreSerialNumber").modal()
}

function visibility(a) {
    var b = document.getElementById(a);
    "block" == b.style.display ? b.style.display = "none" : b.style.display = "block"
}

function IsManufacturer(a) {
    var b = 0 == a.keyCode ? a.charCode : a.keyCode,
        c = b > 31 && 33 > b || b > 44 && 48 > b || b >= 48 && 57 >= b || b >= 65 && 90 >= b || b >= 97 && 122 >= b || -1 != specialKeys.indexOf(a.keyCode) && a.charCode != a.keyCode;
    return document.getElementById("errorManufacturer").innerHTML = "Special Characters not allowed", document.getElementById("errorManufacturer").style.display = c ? "none" : "inline", c
}

function IsPonumber(a) {
    var b = 0 == a.keyCode ? a.charCode : a.keyCode,
        c = b > 31 && 33 > b || b > 44 && 48 > b || b >= 48 && 57 >= b || b >= 65 && 90 >= b || b >= 97 && 122 >= b || -1 != specialKeys.indexOf(a.keyCode) && a.charCode != a.keyCode;
    return document.getElementById("errorPonumber").innerHTML = "Special Characters not allowed", document.getElementById("errorPonumber").style.display = c ? "none" : "inline", c
}

function IsInvoicenumber(a) {
    var b = 0 == a.keyCode ? a.charCode : a.keyCode,
        c = b > 31 && 33 > b || b > 44 && 48 > b || b >= 48 && 57 >= b || b >= 65 && 90 >= b || b >= 97 && 122 >= b || -1 != specialKeys.indexOf(a.keyCode) && a.charCode != a.keyCode;
    return document.getElementById("errorInvoicenumber").innerHTML = "Special Characters not allowed", document.getElementById("errorInvoicenumber").style.display = c ? "none" : "inline", c
}

function IsAisle(a) {
    var b = 0 == a.keyCode ? a.charCode : a.keyCode,
        c = b > 31 && 33 > b || b > 44 && 48 > b || b >= 48 && 57 >= b || b >= 65 && 90 >= b || b >= 97 && 122 >= b || -1 != specialKeys.indexOf(a.keyCode) && a.charCode != a.keyCode;
    return document.getElementById("errorAisle").innerHTML = "Special Characters not allowed", document.getElementById("errorAisle").style.display = c ? "none" : "inline", c
}

function IsRow(a) {
    var b = 0 == a.keyCode ? a.charCode : a.keyCode,
        c = b > 31 && 33 > b || b > 44 && 48 > b || b >= 48 && 57 >= b || b >= 65 && 90 >= b || b >= 97 && 122 >= b || -1 != specialKeys.indexOf(a.keyCode) && a.charCode != a.keyCode;
    return document.getElementById("errorRow").innerHTML = "Special Characters not allowed", document.getElementById("errorRow").style.display = c ? "none" : "inline", c
}

function IsBin(a) {
    var b = 0 == a.keyCode ? a.charCode : a.keyCode,
        c = b > 31 && 33 > b || b > 44 && 48 > b || b >= 48 && 57 >= b || b >= 65 && 90 >= b || b >= 97 && 122 >= b || -1 != specialKeys.indexOf(a.keyCode) && a.charCode != a.keyCode;
    return document.getElementById("errorBin").innerHTML = "Special Characters not allowed", document.getElementById("errorBin").style.display = c ? "none" : "inline", c
}

function visibility(a, b) {
    var c = document.getElementById(a),
        d = document.getElementById(b);
    "block" == c.style.display ? (c.style.display = "none", d.style.display = "block") : (c.style.display = "block", d.style.display = "none")
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

function isNumberKeyQut(a) {
    var b = 0 == a.keyCode ? a.charCode : a.keyCode,
        c = 46 == b || b >= 48 && 57 >= b || -1 != specialKeys.indexOf(a.keyCode) && a.charCode != a.keyCode;
    return document.getElementById("errorINEACH").innerHTML = "Alphabetical Characters not allowed", document.getElementById("errorINEACH").style.display = c ? "none" : "inline", c
}

function showoption() {
    var a = $("#renPT_option :selected"),
        b = a.text();
    "Cash" == b ? $("#target1").text(b + " Receipt NO") : "Cheque" == b ? $("#target1").text(b + " NO") : "CREDIT" == b ? $("#target1").text(b + " Transaction NO") : $("#target1").text(b + " Transaction NO")
}
$(document).ready(function() {
    $("#selectVendor").on("change", function() {
        var a = document.getElementById("selectVendor").value;
        if (0 != a) {
            var b = '<option value="1"> CASH</option><option value="2">CREDIT</option><option value="3">NEFT</option><option value="4">RTGS</option><option value="5">IMPS</option><option value="7">CHEQUE</option><option value="10">ON ACCOUNT</option>';
            $("#renPT_option").html(b)
        } else {
            var b = '<option value="1">CASH</option>';
            $("#renPT_option").html(b)
        }
    }), $("#selectVendor").change(), $("#renPT_option").on("change", function() {
        showoption()
    }), $("#renPT_option").change()
});
var specialKeys = new Array;
specialKeys.push(8), specialKeys.push(9), specialKeys.push(46), specialKeys.push(36), specialKeys.push(35), specialKeys.push(37), specialKeys.push(39), $(function() {
    $("#quantity").keyup(function() {
        if ($(this).val($(this).val().replace(/[^0-9\.]/g, "")), -1 != $(this).val().indexOf(".") && $(this).val().split(".")[1].length > 2) {
            if (isNaN(parseFloat(this.value))) return;
            this.value = parseFloat(this.value).toFixed(2)
        }
        return this
    })
}), $(function() {
    $("#unitprice").keyup(function() {
        if ($(this).val($(this).val().replace(/[^0-9\.]/g, "")), -1 != $(this).val().indexOf(".") && $(this).val().split(".")[1].length > 2) {
            if (isNaN(parseFloat(this.value))) return;
            this.value = parseFloat(this.value).toFixed(2)
        }
        return this
    })
}), $(function() {
    $("#lastprice").keyup(function() {
        if ($(this).val($(this).val().replace(/[^0-9\.]/g, "")), -1 != $(this).val().indexOf(".") && $(this).val().split(".")[1].length > 2) {
            if (isNaN(parseFloat(this.value))) return;
            this.value = parseFloat(this.value).toFixed(2)
        }
        return this
    })
}), $(document).ready(function() {
    $("#manufacurer,#editTyreManufacturer").select2({
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
    }), $("#tyreSize,#editTyreSize").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getSearchTyreSize.in?Action=FuncionarioSelect2",
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
                            text: a.TYRE_SIZE,
                            slug: a.slug,
                            id: a.TS_ID
                        }
                    })
                }
            }
        }
    }), $("#warehouselocation").select2({
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
    if($("#vehicleModelTyreLayoutConfig").val() != undefined && ($("#vehicleModelTyreLayoutConfig").val() == true || $("#vehicleModelTyreLayoutConfig").val() == 'true')){
    	 $.getJSON("searchAllTyreModel.in", function(e) {
	    		companyList	= e;//To get All Company Name 
	    		$("#tyremodel").empty();
	    		$("#tyremodel" ).select2();

	    		for(var k = 0; k <companyList.length; k++){
	    			$("#tyremodel").append($("<option>").text(companyList[k].TYRE_MODEL_SUBTYPE).attr("value", companyList[k].TYRE_MST_ID));
	    		}

	    	})
	 }
    if($("#editMainLocationId").val() != undefined || $("#editMainLocationId").val() != null){// will show only on edit
		$('#warehouselocation').select2('data', {
			id : $("#editMainLocationId").val(),
			text : $("#editMainLocation").val()
		});
	}
   
    $("#subLocationId").select2({
        minimumInputLength: 0,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getPartLocationsByMainLocationId.in?Action=FuncionarioSelect2",
            dataType: "json",
            type: "POST",
            contentType: "application/json",
            quietMillis: 50,
            data: function(a) {
                return {
                    term: a,
                    locationType: subPartLocationType,
                    mainLocationId:  $('#warehouselocation').val()
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
    if($("#editSubLocationId").val() != undefined || $("#editSubLocationId").val() != null){// will show only on edit
		$('#subLocationId').select2('data', {
			id : $("#editSubLocationId").val(),
			text : $("#editSubLocation").val()
		});
	}
    
    $(document).ready(function() {
    	  $("#manufacurer,#editTyreManufacturer").change(function() {
	            $.getJSON("getTyreModelSubType.in", {
	                ModelType: $(this).val(),
	                ajax: "true"
	            }, function(a) {
	                for (var b = '<option value="0">Please Select</option>', c = a.length, d = 0; c > d; d++) b += '<option value="' + a[d].TYRE_MST_ID + '">' + a[d].TYRE_MODEL_SUBTYPE + "</option>";
	                b += "</option>", $("#tyremodel,#editTyreModel").html(b)
	            })
	        }),
      $("#tallyCompanyId").select2({
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
    
    if($('#discountTaxTypId').val() == 1){
		$('#percentId').addClass('active');
		$('#finalDiscountTaxTypId').val(1);
		console.log("1...");
	} else {
		$('#amountId').addClass('active');
		$('#finalDiscountTaxTypId').val(2);
		console.log("2...");
	}
    
}), $(document).ready(function() {
    if($("#vehicleModelTyreLayoutConfig").val() != undefined && ($("#vehicleModelTyreLayoutConfig").val() == true || $("#vehicleModelTyreLayoutConfig").val() == 'true')){
    	var a = 10,
        b = $(".input_fields_wrap"),
        c = $(".add_field_button"),
        d = 1;
	    $(c).click(function(c) {
	    	console.log("11111111",d)
	       c.preventDefault(), a > d && (d++, $(b).append('<div>'
	    		   +'<div class="panel panel-success">'
	    		   		+'<div class="panel-body">'
	    		   			+'<div class="row1">'
	    		   				+'<div>'
	    		   					+'<label class="L-size control-label" for="issue_vehicle_id">Tyre Model :<abbr title="required">*</abbr></label>'
	    		   					+'<div class="col-md-3"><select style="width: 100%;" id="tyremodel' + d + '"  onchange="tyreModelChange('+d+');" name="TYRE_MODEL_ID" required="required"></select>' 
	    		   					+'</div>'
	    		   				+'</div>'
	    		   				+'<div>'
	    		   					+'<label class="L-size control-label">Tyre Manufacturer :<abbr title="required">*</abbr></label>'
	    		   					+'<div class="col-md-3"><input type="hidden"  id="manufacurer' + d + '" class="tyreManufacturer" name="TYRE_MANUFACTURER_ID" style="width: 100%;" required="required" placeholder="Please Enter 2 or more Tyre Manufacturer Name" readonly="readonly" />'
	    		   					+'</div>'
	    		   				+'</div>'
	    		   			+'</div><div class="row1"><label class="L-size control-label">Tyre Size :<abbr title="required">*</abbr></label><div class="col-md-3"><input type="hidden" id="tyreSize' + d + '" name="TYRE_SIZE_ID" class="tyreSize" style="width: 100%;" required="required" placeholder="Please Enter 2 or more Tyre Size Name"  readonly="readonly" /></div><input type="hidden" id="finalDiscountTaxTypId' + d + '" name="finalDiscountTaxTypId"><label class="L-size control-label" for="payMethod">Discount/GST Type :<abbr title="required">*</abbr></label><div class="col-md-3"><div class=""><div class="btn-group"><label id="percentId' + d + '" class="btn btn-default  btn-sm " onclick="selectDiscountTaxType(1,'+d+');">Percentage</label><label id="amountId' + d + '" class="btn btn-default  btn-sm" onclick="selectDiscountTaxType(2,'+d+');">Amount</label></div></div></div></div><div class="row1"><label class="L-size control-label"></label><div class="col-md-9"><div class="col-md-1"><label class="control-label">Quantity</label></div><div class="col-md-2"><label class="control-label">Unit Cost</label></div><div class="col-md-1"><label class="control-label">Discount</label></div><div class="col-md-1"><label class="control-label">GST</label></div><div class="col-md-2"><label class="control-label">Total</label></div></div></div><div class="row1"><label class="L-size control-label" for="issue_vehicle_id">:</label><div class="col-md-9"><div class="col-md-1"><input type="text" class="form-text" name="quantity_many" onpaste="return false"  min="0.0" id="quantity' + d + '" maxlength="8" placeholder="ex: 23.78" required="required"data-toggle="tip"data-original-title="enter Part Quantity" onkeypress="return isNumberKey(event);" onkeyup="javascript:sumthereTypeWise(\'quantity' + d + "', 'unitprice" + d + "', 'discount" + d + "', 'tax" + d + "', 'tatalcost" + d + "', '" + d + '\' );"\tondrop="return false;"></div><div class="col-md-2"><input type="text" class="form-text" name="unitprice_many" onpaste="return false"  id="unitprice' + d + '" maxlength="10" min="0.0"placeholder="Unit Cost" required="required" data-toggle="tip" data-original-title="enter Unit Price" onkeypress="return isNumberKeyWithDecimal(event,this.id)" onkeyup="javascript:sumthereTypeWise(\'quantity' + d + "', 'unitprice" + d + "', 'discount" + d + "', 'tax" + d + "', 'tatalcost" + d + "', '" + d + '\' );"\tondrop="return false;"></div><div class="col-md-1"><input type="text" class="form-text" name="discount_many" min="0.0" onpaste="return false"  id="discount' + d + '" maxlength="5" placeholder="Discount" value="0" data-toggle="tip" data-original-title="enter Discount" onkeypress="return isNumberKeyWithDecimal(event,this.id)" onkeyup="javascript:sumthereTypeWise(\'quantity' + d + "', 'unitprice" + d + "', 'discount" + d + "', 'tax" + d + "', 'tatalcost" + d + "', '" + d + '\' );"\tondrop="return false;"></div> <div class="col-md-1"> <input type="text" class="form-text" name="tax_many"  onpaste="return false"  id="tax' + d + '" maxlength="5" placeholder="GST" value="0" data-toggle="tip" data-original-title="enter GST" onkeypress="return isNumberKeyWithDecimal(event,this.id)" onkeyup="javascript:sumthereTypeWise(\'quantity' + d + "', 'unitprice" + d + "', 'discount" + d + "', 'tax" + d + "', 'tatalcost" + d + "', '" + d + '\' );"ondrop="return false;"></div><div class="col-md-2"><input type="text" class="form-text" maxlength="8" value="0.0" min="0.0" name="tatalcost" id="tatalcost' + d + '" readonly="readonly"data-toggle="tip" data-original-title="Total Cost" onkeypress="return isNumberKey(event,this);" ondrop="return false;"></div></div></div></div></div><a href="#" class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font></div> '), $(document).ready(function() {
	        	
	    	   $("#manufacurer" + d).select2({
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
		        }), $("#tyreSize" + d).select2({
		            minimumInputLength: 2,
		            minimumResultsForSearch: 10,
		            ajax: {
		                url: "getSearchTyreSize.in?Action=FuncionarioSelect2",
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
		                                text: a.TYRE_SIZE,
		                                slug: a.slug,
		                                id: a.TS_ID
		                            }
		                        })
		                    }
		                }
		            }
		        }), 
		        $.getJSON("searchAllTyreModel.in", function(e) {
		    		companyList	= e;//To get All Company Name 
		    		$("#tyremodel" + d).empty();
		    		$("#tyremodel" + d).select2();

		    		for(var k = 0; k <companyList.length; k++){
		    			$("#tyremodel" + d).append($("<option>").text(companyList[k].TYRE_MODEL_SUBTYPE).attr("value", companyList[k].TYRE_MST_ID));
		    		}

		    	}),

		        
		        /*
		        $("#tyremodel" + d).select2({
	                minimumInputLength:0, minimumResultsForSearch:10, ajax: {
	                    url:"getSearchTyreSubModel.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
	                        return {
	                            term: a
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
	            }),*/  $("#unitprice" + d).keyup(function() {
	                if ($(this).val($(this).val().replace(/[^0-9\.]/g, "")), -1 != $(this).val().indexOf(".") && $(this).val().split(".")[1].length > 2) {
	                    if (isNaN(parseFloat(this.value))) return;
	                    this.value = parseFloat(this.value).toFixed(2)
	                }
	                return this
	            })
	        	console.log("whyyy",d)
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

		}else { 
			
			var a = 10,
		    b = $(".input_fields_wrap"),
		    c = $(".add_field_button"),
		    d = 1;
		$(c).click(function(c) {
			console.log("222222")
		    c.preventDefault(), a > d && (d++, $(b).append('<div><div class="panel panel-success"><div class="panel-body"><div class="row1"><label class="L-size control-label">Tyre Manufacturer :<abbr title="required">*</abbr></label><div class="col-md-3"><input type="hidden" id="manufacurer' + d + '" class="tyreManufacturer" name="TYRE_MANUFACTURER_ID" style="width: 100%;" required="required" placeholder="Please Enter 2 or more Tyre Manufacturer Name" /></div><div><label class="L-size control-label" for="issue_vehicle_id">Tyre Model :<abbr title="required">*</abbr></label><div class="col-md-3"><select style="width: 100%;" id="tyremodel' + d + '" name="TYRE_MODEL_ID" required="required"></select> <label class="error" id="errorManufacturer" style="display: none"> </label></div></div></div><div class="row1"><label class="L-size control-label">Tyre Size :<abbr title="required">*</abbr></label><div class="col-md-3"><input type="hidden" id="tyreSize' + d + '" name="TYRE_SIZE_ID" class="tyreSize" style="width: 100%;" required="required" placeholder="Please Enter 2 or more Tyre Size Name" /></div><input type="hidden" id="finalDiscountTaxTypId' + d + '" name="finalDiscountTaxTypId"><label class="L-size control-label" for="payMethod">Discount/GST Type :<abbr title="required">*</abbr></label><div class="col-md-3"><div class=""><div class="btn-group"><label id="percentId' + d + '" class="btn btn-default  btn-sm " onclick="selectDiscountTaxType(1,'+d+');">Percentage</label><label id="amountId' + d + '" class="btn btn-default  btn-sm" onclick="selectDiscountTaxType(2,'+d+');">Amount</label></div></div></div></div><div class="row1"><label class="L-size control-label"></label><div class="col-md-9"><div class="col-md-1"><label class="control-label">Quantity</label></div><div class="col-md-2"><label class="control-label">Unit Cost</label></div><div class="col-md-1"><label class="control-label">Discount</label></div><div class="col-md-1"><label class="control-label">GST</label></div><div class="col-md-2"><label class="control-label">Total</label></div></div></div><div class="row1"><label class="L-size control-label" for="issue_vehicle_id">:</label><div class="col-md-9"><div class="col-md-1"><input type="text" class="form-text" name="quantity_many" onpaste="return false"  min="0.0" id="quantity' + d + '" maxlength="8" placeholder="ex: 23.78" required="required"data-toggle="tip"data-original-title="enter Part Quantity" onkeypress="return isNumberKey(event);" onkeyup="javascript:sumthereTypeWise(\'quantity' + d + "', 'unitprice" + d + "', 'discount" + d + "', 'tax" + d + "', 'tatalcost" + d + "', '" + d + '\' );"\tondrop="return false;"></div><div class="col-md-2"><input type="text" class="form-text" name="unitprice_many" onpaste="return false"  id="unitprice' + d + '" maxlength="10" min="0.0"placeholder="Unit Cost" required="required" data-toggle="tip" data-original-title="enter Unit Price" onkeypress="return isNumberKeyWithDecimal(event,this.id)" onkeyup="javascript:sumthereTypeWise(\'quantity' + d + "', 'unitprice" + d + "', 'discount" + d + "', 'tax" + d + "', 'tatalcost" + d + "', '" + d + '\' );"\tondrop="return false;"></div><div class="col-md-1"><input type="text" class="form-text" name="discount_many" min="0.0" onpaste="return false"  id="discount' + d + '" maxlength="5" placeholder="Discount" value="0" data-toggle="tip" data-original-title="enter Discount" onkeypress="return isNumberKeyWithDecimal(event,this.id)" onkeyup="javascript:sumthereTypeWise(\'quantity' + d + "', 'unitprice" + d + "', 'discount" + d + "', 'tax" + d + "', 'tatalcost" + d + "', '" + d + '\' );"\tondrop="return false;"></div> <div class="col-md-1"> <input type="text" class="form-text" name="tax_many"  onpaste="return false"  id="tax' + d + '" maxlength="5" placeholder="GST" value="0" data-toggle="tip" data-original-title="enter GST" onkeypress="return isNumberKeyWithDecimal(event,this.id)" onkeyup="javascript:sumthereTypeWise(\'quantity' + d + "', 'unitprice" + d + "', 'discount" + d + "', 'tax" + d + "', 'tatalcost" + d + "', '" + d + '\' );"ondrop="return false;"></div><div class="col-md-2"><input type="text" class="form-text" maxlength="8" value="0.0" min="0.0" name="tatalcost" id="tatalcost' + d + '" readonly="readonly"data-toggle="tip" data-original-title="Total Cost" onkeypress="return isNumberKey(event,this);" ondrop="return false;"></div></div></div></div></div><a href="#" class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font></div> '), $(document).ready(function() {
		        $("#manufacurer" + d).select2({
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
		        }), $("#tyreSize" + d).select2({
		            minimumInputLength: 2,
		            minimumResultsForSearch: 10,
		            ajax: {
		                url: "getSearchTyreSize.in?Action=FuncionarioSelect2",
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
		                                text: a.TYRE_SIZE,
		                                slug: a.slug,
		                                id: a.TS_ID
		                            }
		                        })
		                    }
		                }
		            }
		        }), $("#manufacurer" + d).change(function() {
		            $.getJSON("getTyreModelSubType.in", {
		                ModelType: $(this).val(),
		                ajax: "true"
		            }, function(a) {
		                for (var b = '<option value="0">Please Select</option>', c = a.length, e = 0; c > e; e++) b += '<option value="' + a[e].TYRE_MST_ID + '">' + a[e].TYRE_MODEL_SUBTYPE + "</option>";
		                b += "</option>", $("#tyremodel" + d).html(b)
		            })
		        }), $("#tyremodel" + d).select2(),
		        
		        console.log("final...");
		        $("#unitprice" + d).keyup(function() {
		            if ($(this).val($(this).val().replace(/[^0-9\.]/g, "")), -1 != $(this).val().indexOf(".") && $(this).val().split(".")[1].length > 2) {
		                if (isNaN(parseFloat(this.value))) return;
		                this.value = parseFloat(this.value).toFixed(2)
		            }
		            return this
		        });
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
	} 
    
}), $(document).ready(function() {
    $("#vendorEnter").hide()
}), $(document).ready(function() {
    $("#selectVendor").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getTyreVendorSearchList.in?Action=FuncionarioSelect2",
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
    })
});
var specialKeys = new Array;
specialKeys.push(8), specialKeys.push(9), specialKeys.push(46), specialKeys.push(36), specialKeys.push(35), specialKeys.push(37), specialKeys.push(39);

$(document).ready(function() {
    function f(a) {
        for (var b = document.getElementsByTagName("textarea"), c = 0; c < b.length; c++) com_satheesh.EVENTS.addEventHandler(b[c], "focus", g, !1), com_satheesh.EVENTS.addEventHandler(b[c], "blur", h, !1);
        b = document.getElementsByTagName("input");
        for (var c = 0; c < b.length; c++) a.indexOf(-1 != b[c].getAttribute("type")) && (com_satheesh.EVENTS.addEventHandler(b[c], "focus", g, !1), com_satheesh.EVENTS.addEventHandler(b[c], "blur", h, !1));
        com_satheesh.EVENTS.addEventHandler(document.getElementById("formTyreInventory"), "submit", r, !1), com_satheesh.EVENTS.addEventHandler(document.getElementById("formEditTyreInventory"), "submit", s, !1), document.getElementsByTagName("input")[0].focus(), com_satheesh.EVENTS.addEventHandler(document.forms[0].manufacurer, "blur", k, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].tyremodel, "blur", i, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].tyreSize, "blur", j, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].quantity, "blur", l, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].unitprice, "blur", m, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].discount, "blur", n, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].tax, "blur", o, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].warehouselocation, "blur", p, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].invoiceDate, "blur", q, !1)
    }

    function g(a) {
        var c = com_satheesh.EVENTS.getEventTarget(a);
        null != c && (c.style.backgroundColor = b)
    }

    function h(a) {
        var b = com_satheesh.EVENTS.getEventTarget(a);
        null != b && (b.style.backgroundColor = "")
    }

    function i() {
        var a = document.getElementById("tyremodel"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grptyreModel");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("tyreModelIcon").className = "fa fa-check form-text-feedback", document.getElementById("tyreModelErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("tyreModelIcon").className = "fa fa-remove form-text-feedback", document.getElementById("tyreModelErrorMsg").innerHTML = "Please select tyre model"), b
    }

    function j() {
        var a = document.getElementById("tyreSize"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grptyreSize");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("tyreSizeIcon").className = "fa fa-check form-text-feedback", document.getElementById("tyreSizeErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("tyreSizeIcon").className = "fa fa-remove form-text-feedback", document.getElementById("tyreSizeErrorMsg").innerHTML = "Please select tyre size"), b
    }

    function k() {
        var a = document.getElementById("manufacurer"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpmanufacturer");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("manufacturerIcon").className = "fa fa-check form-text-feedback", document.getElementById("manufacturerErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("manufacturerIcon").className = "fa fa-remove form-text-feedback", document.getElementById("manufacturerErrorMsg").innerHTML = "Please enter tyre manufacturer name "), b
    }

    function l() {
        var a = document.getElementById("quantity"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpquantity");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("quantityIcon").className = "fa fa-check  form-text-feedback", document.getElementById("quantityErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("quantityIcon").className = "fa fa-remove form-text-feedback", document.getElementById("quantityErrorMsg").innerHTML = "Please enter quantity ")), b
    }

    function m() {
        var a = document.getElementById("unitprice"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpquantity");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("unitpriceIcon").className = "fa fa-check  form-text-feedback", document.getElementById("unitpriceErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("unitpriceIcon").className = "fa fa-remove form-text-feedback", document.getElementById("unitpriceErrorMsg").innerHTML = "Please enter unit price  ")), b
    }

    function n() {
        var a = document.getElementById("discount"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpquantity");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("discountIcon").className = "fa fa-check  form-text-feedback", document.getElementById("discountErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("discountIcon").className = "fa fa-remove form-text-feedback", document.getElementById("discountErrorMsg").innerHTML = "Please enter discount ")), b
    }

    function o() {
        var a = document.getElementById("tax"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpquantity");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("taxIcon").className = "fa fa-check  form-text-feedback", document.getElementById("taxErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("taxIcon").className = "fa fa-remove form-text-feedback", document.getElementById("taxErrorMsg").innerHTML = "Please enter tax ")), b
    }

    function p() {
        var a = document.getElementById("warehouselocation"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grppartLocation");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("partLocationIcon").className = "fa fa-check  form-text-feedback", document.getElementById("partLocationErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("partLocationIcon").className = "fa fa-remove form-text-feedback", document.getElementById("partLocationErrorMsg").innerHTML = "Please select Warehouse location ")), b
    }

    function q() {
        var a = document.getElementById("invoiceDate"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpinvoiceDate");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("invoiceDateIcon").className = "fa fa-check  form-text-feedback", document.getElementById("invoiceDateErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("invoiceDateIcon").className = "fa fa-remove form-text-feedback", document.getElementById("invoiceDateErrorMsg").innerHTML = "Please select Invoice Date")), b
    }

    function r(a) {
        var b = i();
        b &= k(), b &= j(), b &= l(), b &= m(), b &= n(), b &= o(), b &= p(), (b &= q()) || com_satheesh.EVENTS.preventDefault(a)
    }

    function s(a) {
        q() || com_satheesh.EVENTS.preventDefault(a)
    }
    var b = "#FFC";
    com_satheesh.EVENTS.addEventHandler(window, "load", function() {
        f("text")
    }, !1)
});

function isNumberKey(evt)
{	
   var charCode = (evt.which) ? evt.which : event.keyCode
   if (charCode > 31 && (charCode < 48 || charCode > 57))
      return false;

   return true;
}

function isNumberKeyWithDecimal(evt,id)
{ 
	try{

        var charCode = (evt.which) ? evt.which : event.keyCode;
        var txt=document.getElementById(id).value;
        if(charCode==46){
            
            if(!(txt.indexOf(".") > -1)){
	
                return true;
            }
        }
        if (charCode > 31 && (charCode < 48 || charCode > 57) )
            return false;

        if(txt.indexOf(".")>-1 && (txt.split('.')[1].length > 1))		{
            event.preventDefault();
        }
        return true;
	}catch(w){
		alert(w);
	}
}

function validateTyreInventorySave(){
	$('#saveTyreInventory').hide();
	var gst = false;
	$('input[name*=tax_many]' ).each(function(){
		var tyreGst = $("#"+$( this ).attr( "id" )).val();  
		if(tyreGst=="" || tyreGst<0){									
			 $("#"+$( this ).attr( "id" )).focus();
			 gst	= true;
			showMessage('errors', 'Please Enter Gst !');
			return false;
		}
	});
	
	var discount = false;
	$('input[name*=discount_many]' ).each(function(){
		var tyreDiscount = $("#"+$( this ).attr( "id" )).val();  
		if(tyreDiscount=="" || tyreDiscount<0){									
			 $("#"+$( this ).attr( "id" )).focus();
			 discount	= true;
			showMessage('errors', 'Please Enter Discount !');
			return false;
		}
	});
	
	
	var unitCost = false;
	$('input[name*=unitprice_many]' ).each(function(){
		var tyreUnitCost = $("#"+$( this ).attr( "id" )).val();  
		if(tyreUnitCost <= 0){									
			 $("#"+$( this ).attr( "id" )).focus();
			 unitCost	= true;
			showMessage('errors', 'Please Enter Unit Cost !');
			return false;
		}
	});
	
	var qty	= false;
	$('input[name*=quantity_many]' ).each(function(){
		var tyreQty = $("#"+$( this ).attr( "id" )).val();  
		if(tyreQty <= 0){									
			 $("#"+$( this ).attr( "id" )).focus();
			 qty	= true;
			showMessage('errors', 'Please Enter Quantity !');
			return false;
		}
	});
	
	
	var size	= false;
	$('input[name*=TYRE_SIZE_ID]' ).each(function(){
		var tyreSize = $("#"+$( this ).attr( "id" )).val();  
		if(tyreSize <= 0){									
			 $("#"+$( this ).attr( "id" )).select2('focus');
			 size	= true;
			showMessage('errors', 'Please Enter Tyre size !');
			return false;
		}
	});
	
	var model	= false; 
	$('select[name*=TYRE_MODEL_ID]' ).each(function(){ 
		var tyreModel = $("#"+$( this ).attr( "id" )).val();
		if(tyreModel <= 0){					
			 $("#"+$( this ).attr( "id" )).select2('focus');
			 model	= true;
			showMessage('errors', 'Please Enter Tyre Model!');
			return false;
		}
	});
	
	
	
	var manufacurer = false;
	$('input[name*=TYRE_MANUFACTURER_ID]' ).each(function(){
		var tyreManuFacturer = $("#"+$( this ).attr( "id" )).val();
		if(tyreManuFacturer <= 0){
			 $("#"+$( this ).attr( "id" )).select2('focus');
			 manufacurer	= true;
			showMessage('errors', 'Please Enter Tyre Manufacturer!');
			return false;
		}
	});
	
	if(gst || discount || unitCost || qty || manufacurer || model || size ){
		return false;
	}
	
	if(Number($('#selectVendor').val()) <= 0){
		showMessage('info','Please Enter Tyre Vendor !');
		return false;
	}	
	
	if($('#tallyIntegrationRequired').val() == 'true' || $('#tallyIntegrationRequired').val() == true){
		if(Number($('#tallyCompanyId').val()) <= 0){
			showMessage('info','Please Select Tally Company !');
			$('#saveTyreInventory').show();
			return false;
		}	
	}
	if($('#validateSubLocation').val() == 'true' || $('#validateSubLocation').val() == true){
		if(Number($('#subLocationId').val()) <= 0){
			showMessage('info','Please Select Sub Location !');
			return false;
		}	
	}
	
	if($("#roundupConfig").val() == true || $("#roundupConfig").val()=='true'){
		if($('#roundupTotal').val() == ""||$('#roundupTotal').val()== null || $('#roundupTotal').val()<=0 ){
			showMessage('info', 'Please enter valid roundup amount !');
			hideLayer();
			return false;
		}
	}
	
		if(validateBankPaymentDetails && !validateBankPayment($('#bankPaymentTypeId').val())){
		$('#saveTyreInventory').show();
		return false;	
	}
	
	$('#saveTyre').hide();
	
	return true;
}
function validateTyreInvoiceEdit(){
	if($("#roundupConfig").val() == true || $("#roundupConfig").val()=='true'){
	if($('#roundupTotal').val()==""||$('#roundupTotal')==null){
		showMessage('info','Please Enter round up amount!');
		return false;
	}
	else if(!($('#roundupTotal').val()>0)){
		showMessage('info', "Round up amount should be greater than '0'");
		hideLayer();
		return false;
		}
	
	}
	if($("#renPT_option1").val() == 2 ){
	 	if($("#selectVendor").val() == "" || Number($("#selectVendor").val()) ==  0){
	 		showMessage('info','Please Select Vendor');
	 		return false;
	 	}
	}
	if($('#tallyIntegrationRequired').val() == 'true' || $('#tallyIntegrationRequired').val() == true){
		if(Number($('#tallyCompanyId').val()) <= 0){
			showMessage('info','Please Select Tally Company !');
			return false;
		}	
	}
	if($('#validateSubLocation').val() == 'true' || $('#validateSubLocation').val() == true){
		if(Number($('#subLocationId').val()) <= 0){
			showMessage('info','Please Select Sub Location !');
			return false;
		}	
	}
	
	if(validateBankPaymentDetails && !validateBankPayment($('#bankPaymentTypeId').val())){
		return false;	
	}
	
	return true;
}

function showSubLocationDropDown(){
    var showSubLocationForMainLocation = false;
    if($("#showSubLocation").val() == true || $("#showSubLocation").val() == "true"){
    	var mainLocationIds = $("#mainLocationIds").val().split(',');

    	for(var i = 0; i < mainLocationIds.length; i++) {
    		if($("#warehouselocation").val() == mainLocationIds[i]){
    			showSubLocationForMainLocation = true
    		}
    	}
    }
    
    if(showSubLocationForMainLocation == true){
    	$("#subLocation").show();
    	$("#validateSubLocation").val(true);
    	
    }else{
    	$("#subLocationId").select2("val", "");
    	$("#subLocation").hide();
    	$("#validateSubLocation").val(false);
    }
    
    setTimeout(function(){
    var  invoiceAmount = 0;
    $("input[name=tatalcost]").each(function(){
    	invoiceAmount += Number($(this).val());
	});
    $('#invoiceAmount').val((invoiceAmount).toFixed(2));
    
}, 300);
	
}

function selectDiscountTaxType(typeId, d){
	
	console.log("initialtypeId...",typeId);
	console.log("multiple...",d);
	
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

function getLastTyreCostDetails(tyreSize){
	
	if($('#previousPOpartRate').val() == true || $('#previousPOpartRate').val() == 'true'){
		if($("#"+tyreSize+"").val() != "" && $("#manufacurer").val() != "" &&  $("#tyremodel").val() != "" ){
	$.getJSON("getLastTyreDetails.in", {
		PARTID: $("#"+tyreSize+"").val(),
		manufacturer: $("#manufacurer").val(),
		tyremodel: $("#tyremodel").val(),
		
		ajax: "true"
	}, function(e) {
		var t = "", d = "", n = "", i = "", r = "",dis=e.discount,tax=e.tax,total=e.totalcost;
		d = e.parteachcost,
		n = e.purchaseorder_Number,
		i = e.quantity,
		r = e.purchaseorder_id;

		if (r != 0 && null != r){
			t = '<p style="color: blue;"> Previous rate : Each Cost = '+d+' <i class="fa fa-inr"></i> , Discount = '+dis+'% , GST = '+tax+'%  .'
			+ '  <a href="/PurchaseOrders_Parts.in?ID='
			+ r
			+ '" target="_blank"> View <i class="fa fa-external-link"></i> </a></p>';

			$("#lastPOtyreDetails").html(t);

		}else{
			$("#lastPOtyreDetails").html("");
		}
	})
		}else{
			$("#lastPOtyreDetails").html("");
		}
	}
	if($('#locationWisePartCount').val() == true || $('#locationWisePartCount').val() == 'true'){
	if($("#"+tyreSize+"").val() != "" && $("#manufacurer").val() != "" &&  $("#tyremodel").val() != "" ){
	$.getJSON("getlocationWiseTyreCount", {
		capacity: $("#"+tyreSize+"").val(),
		manufacturer: $("#manufacurer").val(),
		model: $("#tyremodel").val(),
		location:$('#shipLocationId').val(),
		ajax: "true"
	}, function(e) {

		var t = "", d = "", r = "";
		d = e.otherLocationCount,
		r = e.locationWiseCount;
		if (r != 0
				&& null != r || d != 0)
			t = '<p style="color: red;"> '+$('#shipLocation').val()+' Stock Details :'
			+ r
			+ ' , Other Locations Stock Details : '
			+ d
			//	+ '" target="_blank">View <i class="fa fa-external-link"></i></a></p>'
			else
				t = '<p style="color: blue;">You don\'t have in stock.</P>';
		$(
		"#Tyre_occurred")
		.html(t)
	})
	
	}else{
		$('#Tyre_occurred').html("");
	}
	}

}

function getLastBatteryCostDetails(batteryCapacity){

	if($('#previousPOpartRate').val() == true || $('#previousPOpartRate').val() == 'true'){

		if($("#"+batteryCapacity+"").val() != "" && $("#batteryManufacurer").val() != "" && $("#batterryTypeId").val() != "" ){
			$.getJSON("getLastBatteryDetails.in", {
				PARTID: $("#"+batteryCapacity+"").val(),
				manufacturer: $("#batteryManufacurer").val(),
				batterrymodel: $("#batterryTypeId").val(),

				ajax: "true"
			}, function(e) {
				var t = "", d = "", n = "", i = "", r = "",dis=e.discount,tax=e.tax,total=e.totalcost;
				d = e.parteachcost,
				n = e.purchaseorder_Number,
				i = e.quantity,
				r = e.purchaseorder_id;

				if (r != 0 && null != r){
					t = '<p style="color: blue;"> Previous rate : Each Cost = '+d+' <i class="fa fa-inr"></i> , Discount = '+dis+'% , GST = '+tax+'%  .'
					+ '  <a href="/PurchaseOrders_Parts.in?ID='
					+ r
					+ '" target="_blank"> View <i class="fa fa-external-link"></i> </a></p>';

					$("#lastPOBatteryDetails").html(t);

				}else{
					$("#lastPOBatteryDetails").html("");
				}
			})

		}else{
			$("#lastPOBatteryDetails").html("");
		}
	}
	if($('#locationWisePartCount').val() == true || $('#locationWisePartCount').val() == 'true'){

		if($("#"+batteryCapacity+"").val() != "" && $("#batteryManufacurer").val() != "" && $("#batterryTypeId").val() != "" ){
			$.getJSON("getlocationWiseBatteryCount", {
				capacity: $("#"+batteryCapacity+"").val(),
				manufacturer: $("#batteryManufacurer").val(),
				model: $("#batterryTypeId").val(),
				location:$('#shipLocationId').val(),

				ajax: "true"
			}, function(e) {

				var t = "", d = "", r = "";
				d = e.otherLocationCount,
				r = e.locationWiseCount;
				if (r != 0
						&& null != r || d != 0)
					t = '<p style="color: red;"> '+$('#shipLocation').val()+' Stock Details :'
					+ r
					+ ' , Other Locations Stock Details : '
					+ d
					//	+ '" target="_blank">View <i class="fa fa-external-link"></i></a></p>'
					else
						t = '<p style="color: blue;">You don\'t have in stock.</P>';
				$(
				"#Tyre_occurred")
				.html(t)
			})
		}
	}
	
}

$('#batteryManufacurer').change(function(){
	$('#batterryTypeId').select2("val","");	
//	$('#batteryCapacityId').select2("val","");	
	if($('#previousPOpartRate').val() == true || $('#previousPOpartRate').val() == 'true'){
	$("#lastPOBatteryDetails").html("");}
	if($('#locationWisePartCount').val() == true || $('#locationWisePartCount').val() == 'true'){
		
	}
})
$('#manufacurer').change(function(){
	$('#tyremodel').select2("val","");	
	$('#tyreSize').select2("val","");	
	if($('#previousPOpartRate').val() == true || $('#previousPOpartRate').val() == 'true'){
		$("#lastPOtyreDetails").html("");}
if($('#locationWisePartCount').val() == true || $('#locationWisePartCount').val() == 'true'){
	$("#Tyre_occurred").html("");
	}
})

$('#batterryTypeId').change(function(){
//	$('#batteryCapacityId').select2("val","");
	if($('#previousPOpartRate').val() == true || $('#previousPOpartRate').val() == 'true'){
		$("#lastPOBatteryDetails").html("");}
if($('#locationWisePartCount').val() == true || $('#locationWisePartCount').val() == 'true'){
	$("#Tyre_occurred").html("");
	}
})
$('#tyremodel').change(function(){
	$('#tyreSize').select2("val","");
	if($('#previousPOpartRate').val() == true || $('#previousPOpartRate').val() == 'true'){
		$("#lastPOtyreDetails").html("");}
if($('#locationWisePartCount').val() == true || $('#locationWisePartCount').val() == 'true'){
	$("#Tyre_occurred").html("");
	}
})


$(document).ready(function(){
	
	if($("#vehicleModelTyreLayoutConfig").val() != undefined && ($("#vehicleModelTyreLayoutConfig").val() == true || $("#vehicleModelTyreLayoutConfig").val() == 'true')){
	//	$('#manufacurer').select2('readonly',true);
	//	$('#tyreSize').select2('readonly',true);
		
		$('#manufacurer').select2('readonly',true);
		$('#tyreSize').select2('readonly',true);
		
	}
})

function tyreModelChange(d){
	if(d == undefined ||  d == ""){
		d= "";
	}
	var jsonObject					= new Object();
	jsonObject["TYRE_MST_ID"] 		= $('#tyremodel'+d).val();
	jsonObject["companyId"] 		= $('#companyId').val();

	$.ajax({
		url: "getVehicleTyreModelSubTypeDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			console.log("data.....",data)
			if(data.tyreModelDetails != undefined){
				var tyreModelDetails = data.tyreModelDetails;
				
				$('#manufacurer'+d).select2('data', {
					id : tyreModelDetails.tyre_MT_ID,
					text : tyreModelDetails.tyre_MODEL
				});
				$('#tyreSize'+d).select2('data', {
					id : tyreModelDetails.tyreModelSizeId,
					text : tyreModelDetails.tyreModelSize
				});
			}
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			hideLayer();
		}
	});
}