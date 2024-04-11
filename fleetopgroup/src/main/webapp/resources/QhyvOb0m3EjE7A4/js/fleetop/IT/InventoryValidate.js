var mainPartLocationType = 1;
var subPartLocationType  = 2;
function visibility(e) {
    var t = document.getElementById(e);
    "block" == t.style.display ? t.style.display = "none" : t.style.display = "block"
}

function IsManufacturer(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = t > 31 && 33 > t || t > 44 && 48 > t || t >= 48 && 57 >= t || t >= 65 && 90 >= t || t >= 97 && 122 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorManufacturer").innerHTML = "Special Characters not allowed", document.getElementById("errorManufacturer").style.display = n ? "none" : "inline", n
}

function IsPonumber(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = t > 31 && 33 > t || t > 44 && 48 > t || t >= 48 && 57 >= t || t >= 65 && 90 >= t || t >= 97 && 122 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorPonumber").innerHTML = "Special Characters not allowed", document.getElementById("errorPonumber").style.display = n ? "none" : "inline", n
}

function IsInvoicenumber(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = t > 31 && 33 > t || t > 44 && 48 > t || t >= 48 && 57 >= t || t >= 65 && 90 >= t || t >= 97 && 122 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorInvoicenumber").innerHTML = "Special Characters not allowed", document.getElementById("errorInvoicenumber").style.display = n ? "none" : "inline", n
}

function IsAisle(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = t > 31 && 33 > t || t > 44 && 48 > t || t >= 48 && 57 >= t || t >= 65 && 90 >= t || t >= 97 && 122 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorAisle").innerHTML = "Special Characters not allowed", document.getElementById("errorAisle").style.display = n ? "none" : "inline", n
}

function IsRow(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = t > 31 && 33 > t || t > 44 && 48 > t || t >= 48 && 57 >= t || t >= 65 && 90 >= t || t >= 97 && 122 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorRow").innerHTML = "Special Characters not allowed", document.getElementById("errorRow").style.display = n ? "none" : "inline", n
}

function IsBin(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = t > 31 && 33 > t || t > 44 && 48 > t || t >= 48 && 57 >= t || t >= 65 && 90 >= t || t >= 97 && 122 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorBin").innerHTML = "Special Characters not allowed", document.getElementById("errorBin").style.display = n ? "none" : "inline", n
}

function visibility(e, t) {
    var n = document.getElementById(e),
        a = document.getElementById(t);
    "block" == n.style.display ? (n.style.display = "none", a.style.display = "block") : (n.style.display = "block", a.style.display = "none")
}

function sumthere(e, t, n, a, r) {
    var i = document.getElementById(e).value,
        l = document.getElementById(t).value,
        s = document.getElementById(n).value,
        o = document.getElementById(a).value,
        d = parseFloat(i) * parseFloat(l),
        u = d * s / 100,
        c = d - u,
        e = c * o / 100,
        y = c + e;
    isNaN(y) || (document.getElementById(r).value = y.toFixed(2))
    
    calculateInvoice();
    //$("#invoiceAmount").val(c);
}

function calculateInvoice(){
	 var invoiceAmountt = 0;
	 $("input[name=tatalcost]").each(function(){
	    	invoiceAmountt += Number($(this).val());
		});
	 $('#invoiceAmount').val((invoiceAmountt).toFixed(2));
	 $('#previousInvoice').val((invoiceAmountt).toFixed(2));
	 addLabourToInvoice();
}

function sumthereTypeWise(a, b, c, d, e, multiple) {
	
	var dicTaxId;
	var labourCharge = 0;
	
	if(Number($('#labourCharge').val()) > 0){
		labourCharge = Number($('#labourCharge').val());
	}
	
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
	    	$('#invoiceAmount').val(Math.round(invoiceAmount + labourCharge));
	    	$('#previousInvoice').val(Math.round(invoiceAmount + labourCharge));
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
	    	$('#invoiceAmount').val(Math.round(invoiceAmount + labourCharge));
	    	$('#previousInvoice').val(Math.round(invoiceAmount + labourCharge));
		});
	}
    
}

function addLabourToInvoice(){
	if($("#roundupConfig").val() == true || $("#roundupConfig").val()=='true'){
		$('#roundupTotal').val((Number($('#labourCharge').val()) +Number($('#previousInvoice').val())).toFixed(2));
	}else {
		$('#invoiceAmount').val((Number($('#labourCharge').val()) + Number($('#previousInvoice').val())).toFixed(2));
	}
}

function isNumberKeyQut(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = 46 == t || t >= 48 && 57 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorINEACH").innerHTML = "Alphabetical Characters not allowed", document.getElementById("errorINEACH").style.display = n ? "none" : "inline", n
}
var specialKeys = new Array;
specialKeys.push(8), specialKeys.push(9), specialKeys.push(46), specialKeys.push(36), specialKeys.push(35), specialKeys.push(37), specialKeys.push(39), $(function() {
    $("#quantity,#roundupTotal").keyup(function() {
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
    $("#searchpart").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getSearchMasterPart.in?Action=FuncionarioSelect2",
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
                        return {
                            text: e.partnumber + " - " + e.partname + " - " + e.category + " - " + e.make,
                            slug: e.slug,
                            id: e.partid
                        }
                    })
                }
            }
        }
    }), $(document).ready(function() {
        $("#searchpart").change(function() {
            $.getJSON("getMasterPartShow.in", {
                PartID: $(this).val(),
                ajax: "true"
            }, function(e) {
                var t, n = "";
                $('#unitprice').val(e.unitCost);
                $('#discount').val(e.discount);
                $('#tax').val(e.tax);
                t = e.make, document.getElementById("manufacturer").value = t, document.getElementById("manufacturer").readOnly = !1, 0 < t.length && (document.getElementById("manufacturer").readOnly = !0), $("#hidden").hide(), n = e.part_photoid, $("#part_photoid").val(n)
            })
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
    var e = $("#NoOfPartsAllowedToAdd").val(), addMorePartsAtBottom = $("#addMorePartsAtBottom").val(),
    t = $(".input_fields_wrap"),
        n = $(".add_field_button"),
        a = 1;
    if(addMorePartsAtBottom == true || addMorePartsAtBottom == 'true'){
    	t = $("#moreParts");
    }  
   
    $(n).click(function(n) {
        n.preventDefault(), e > a && (a++, $(t).append('<div><div class="panel panel-success"><div class="panel-body"><div class="row1"><label class="L-size control-label">Search Parts Number :<abbr title="required">*</abbr></label><div class="I-size"><input type="hidden" id="searchpart' + a + '" name="partid_many" style="width: 100%;" required="required" placeholder="Please Enter 2 or more Part Name or Part Number" /></div></div></br><div class="row1"><label class="L-size control-label" for="issue_vehicle_id">Manufacturer :<abbr title="required">*</abbr></label><div class="col-md-3"><input type="text" class="form-text" name="make_many" id="manufacturer' + a + '" required="required" maxlength="50" onkeypress="return IsManufacturer(event);"ondrop="return false;"> <label class="error" id="errorManufacturer" style="display: none"> </label></div><input type="hidden" id="finalDiscountTaxTypId' + a + '" name="finalDiscountTaxTypId"><label class="L-size control-label" for="payMethod">Discount/GST Type :<abbr title="required">*</abbr></label><div class="col-md-3"><div class=""><div class="btn-group"><button id="percentId' + a + '" class="btn btn-default  btn-sm active" onclick="selectDiscountTaxType(1,'+a+');">Percentage</button><button id="amountId' + a + '" class="btn btn-default  btn-sm" onclick="selectDiscountTaxType(2,'+a+');">Amount</button></div></div></div></div><div class="row1"><label class="L-size control-label" for="issue_vehicle_id">Quantity:</label><div class="col-md-9"><div class="col-md-1"><input type="text" class="form-text" name="quantity_many" min="0.0" id="quantity' + a + '" maxlength="8" placeholder="ex: 23.78" required="required"data-toggle="tip"data-original-title="enter Part Quantity" onkeypress="isNumberKeyWithDecimal(event,this.id);"onkeyup="javascript:sumthereTypeWise(\'quantity' + a + "', 'unitprice" + a + "', 'discount" + a + "', 'tax" + a + "', 'tatalcost" + a + "', '" + a + '\' );"	ondrop="return false;"></div><div class="col-md-2"><input type="text" class="form-text" name="unitprice_many" id="unitprice' + a + '" maxlength="10" min="0.0"placeholder="Unit Cost" required="required" data-toggle="tip" data-original-title="enter Unit Price" onkeypress="return isNumberKeyWithDecimal(event,this.id)" onkeyup="javascript:sumthereTypeWise(\'quantity' + a + "', 'unitprice" + a + "', 'discount" + a + "', 'tax" + a + "', 'tatalcost" + a + "', '" + a + '\' );"	ondrop="return false;"></div><div class="col-md-1"><input type="text" class="form-text" name="discount_many" min="0.0" id="discount' + a + '" maxlength="5" placeholder="Discount" required="required"data-toggle="tip" data-original-title="enter Discount" onkeypress="return isNumberKeyWithDecimal(event,this.id);"onkeyup="javascript:sumthereTypeWise(\'quantity' + a + "', 'unitprice" + a + "', 'discount" + a + "', 'tax" + a + "', 'tatalcost" + a + "', '" + a + '\' );"	ondrop="return false;"></div> <div class="col-md-1"> <input type="text" class="form-text" name="tax_many" id="tax' + a + '" maxlength="5" placeholder="GST" required="required"data-toggle="tip" data-original-title="enter GST" onkeypress="return isNumberKeyWithDecimal(event,this.id);"onkeyup="javascript:sumthereTypeWise(\'quantity' + a + "', 'unitprice" + a + "', 'discount" + a + "', 'tax" + a + "', 'tatalcost" + a + "', '" + a + '\' );"ondrop="return false;"></div><div class="col-md-2"><input type="text" class="form-text" maxlength="8" value="0.0" min="0.0" id="tatalcost' + a + '" name="tatalcost" readonly="readonly"data-toggle="tip" data-original-title="Total Cost" onkeypress="return isNumberKey(event,this);" ondrop="return false;"></div></div></div></div></div><a href="#" class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font></div> '), $(document).ready(function() {
            $("#searchpart" + a).select2({
                minimumInputLength: 2,
                minimumResultsForSearch: 10,
                ajax: {
                    url: "getSearchMasterPart.in?Action=FuncionarioSelect2",
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
                                return {
                                	 
                                    text: e.partnumber + " - " + e.partname + " - " + e.category + " - " + e.make,
                                    slug: e.slug,
                                    id: e.partid
                                }
                            })
                        }
                    }
                }
            }), $("#searchpart" + a).change(function() {
                $.getJSON("getMasterPartShow.in", {
                    PartID: $(this).val(),
                    ajax: "true"
                }, function(e) {
                    var t, n = "";
                    $('#unitprice' + a).val(e.unitCost);
                    $('#discount' + a).val(e.discount);
                    $('#tax' + a).val(e.tax);
                    t = e.make, document.getElementById("manufacturer" + a).value = t, document.getElementById("manufacturer" + a).readOnly = !1, 0 < t.length && (document.getElementById("manufacturer" + a).readOnly = !0), $("#hidden").hide(), n = e.part_photoid, document.getElementById("part_photoid" + a).value = n
                })
            }),
            $("#unitprice" + a).keyup(function() {
                if ($(this).val($(this).val().replace(/[^0-9\.]/g, "")), -1 != $(this).val().indexOf(".") && $(this).val().split(".")[1].length > 2) {
                    if (isNaN(parseFloat(this.value))) return;
                    this.value = parseFloat(this.value).toFixed(2)
                }
                return this
            })
            
            console.log("final...");
    		if($('#discountTaxTypId').val() == 1){
    			$("#percentId" + a).addClass('active');
    			$("#finalDiscountTaxTypId" + a).val(1);
    			console.log("final1...");
    		} else {
    			$("#amountId" + a).addClass('active');
    			$("#finalDiscountTaxTypId" + a).val(2);
    			console.log("final2...");
    		}
    		
        }))
    }), $(t).on("click", ".remove_field", function(e) {
        e.preventDefault();
        $(this).parent("div").remove(); a--;
        calculateInvoice();
    })
}), $(document).ready(function() {
    $("#vendorEnter").hide()
}), $(document).ready(function() {
    $("#selectVendor").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getVendorSearchListInventory.in?Action=FuncionarioSelect2",
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
                        return {
                            text: e.vendorName + " - " + e.vendorLocation,
                            slug: e.slug,
                            id: e.vendorId
                        }
                    })
                }
            }
        }, createSearchChoice:function(term, results) {
        	if($('#allowNewVendor').val() == 'true' || $('#allowNewVendor').val() == true){
        		if ($(results).filter( function() {
        			return term.localeCompare(this.text)===0; 
        		}).length===0) {
        			return {id:term, text:term + ' [New]'};
        		}
        	}
        },
    }),$("#subLocationId").select2({
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
                    mainLocationId:  $('#location').val()
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
var specialKeys = new Array;
specialKeys.push(8), specialKeys.push(9), specialKeys.push(46), specialKeys.push(36), specialKeys.push(35), specialKeys.push(37), specialKeys.push(39);

$(document).ready(function() {
    function f(a) {
        for (var b = document.getElementsByTagName("textarea"), c = 0; c < b.length; c++) com_satheesh.EVENTS.addEventHandler(b[c], "focus", g, !1), com_satheesh.EVENTS.addEventHandler(b[c], "blur", h, !1);
        b = document.getElementsByTagName("input");
        for (var c = 0; c < b.length; c++) a.indexOf(-1 != b[c].getAttribute("type")) && (com_satheesh.EVENTS.addEventHandler(b[c], "focus", g, !1), com_satheesh.EVENTS.addEventHandler(b[c], "blur", h, !1));
        com_satheesh.EVENTS.addEventHandler(document.getElementById("formPartInventory"), "submit", q, !1), com_satheesh.EVENTS.addEventHandler(document.getElementById("formEditPartInventory"), "submit", r, !1), document.getElementsByTagName("input")[0].focus(), com_satheesh.EVENTS.addEventHandler(document.forms[0].searchpart, "blur", i, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].manufacturer, "blur", j, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].quantity, "blur", k, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].unitprice, "blur", l, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].discount, "blur", m, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].tax, "blur", n, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].location, "blur", o, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].invoiceDate, "blur", p, !1)
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
        var a = document.getElementById("searchpart"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpinventoryPart");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("inventoryPartIcon").className = "fa fa-check form-text-feedback", document.getElementById("inventoryPartErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("inventoryPartIcon").className = "fa fa-remove form-text-feedback", document.getElementById("inventoryPartErrorMsg").innerHTML = "Please enter search part name & number"), b
    }

    function j() {
        var a = document.getElementById("manufacturer"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpmanufacturer");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("manufacturerIcon").className = "fa fa-check form-text-feedback", document.getElementById("manufacturerErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("manufacturerIcon").className = "fa fa-remove form-text-feedback", document.getElementById("manufacturerErrorMsg").innerHTML = "Please enter vendor manufacturer name "), b
    }

    function k() {
        var a = document.getElementById("quantity"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpquantity");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("quantityIcon").className = "fa fa-check  form-text-feedback", document.getElementById("quantityErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("quantityIcon").className = "fa fa-remove form-text-feedback", document.getElementById("quantityErrorMsg").innerHTML = "Please enter quantity ")), b
    }

    function l() {
        var a = document.getElementById("unitprice"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpquantity");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("unitpriceIcon").className = "fa fa-check  form-text-feedback", document.getElementById("unitpriceErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("unitpriceIcon").className = "fa fa-remove form-text-feedback", document.getElementById("unitpriceErrorMsg").innerHTML = "Please enter unit price ")), b
    }

    function m() {
        var a = document.getElementById("discount"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpquantity");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("discountIcon").className = "fa fa-check  form-text-feedback", document.getElementById("discountErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("discountIcon").className = "fa fa-remove form-text-feedback", document.getElementById("discountErrorMsg").innerHTML = "Please enter discount ")), b
    }

    function n() {
        var a = document.getElementById("tax"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpquantity");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("taxIcon").className = "fa fa-check  form-text-feedback", document.getElementById("taxErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("taxIcon").className = "fa fa-remove form-text-feedback", document.getElementById("taxErrorMsg").innerHTML = "Please enter tax ")), b
    }

    function o() {
        var a = document.getElementById("location"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grppartLocation");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("partLocationIcon").className = "fa fa-check  form-text-feedback", document.getElementById("partLocationErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("partLocationIcon").className = "fa fa-remove form-text-feedback", document.getElementById("partLocationErrorMsg").innerHTML = "Please select Wherehouse location ")), b
    }

    function p() {
        var a = document.getElementById("invoiceDate"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpinvoiceDate");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("invoiceDateIcon").className = "fa fa-check  form-text-feedback", document.getElementById("invoiceDateErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("invoiceDateIcon").className = "fa fa-remove form-text-feedback", document.getElementById("invoiceDateErrorMsg").innerHTML = "Please select Invoice Date")), b
    }

    function q(a) {
        var b = i();
        b &= j(), b &= k(), b &= l(), b &= m(), b &= n(), b &= o(), (b &= p()) || com_satheesh.EVENTS.preventDefault(a)
    }

    function r(a) {
        var b = j();
        b &= k(), b &= l(), b &= m(), b &= n(), b &= o(), (b &= p()) || com_satheesh.EVENTS.preventDefault(a)
    }
    var b = "#FFC";
    com_satheesh.EVENTS.addEventHandler(window, "load", function() {
        f("text")
    }, !1)

    $("#selectVendor").on("change", function() {
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
    }), $("#selectVendor").change()

});

function showoption() {
    var a = $("#renPT_option :selected"),
        b = a.text();
    "Cash" == b ? $("#target1").text(b + " Receipt NO") : "Cheque" == b ? $("#target1").text(b + " NO") : "CREDIT" == b ? $("#target1").text(b + " Transaction NO") : $("#target1").text(b + " Transaction NO")
}

function validateInventorySave(){
	$('#saveInventory').hide();
	
	if($('#validateSubLocation').val() == 'true' || $('#validateSubLocation').val() == true){
		if(Number($('#subLocationId').val()) <= 0){
			$('#saveInventory').show();
			showMessage('info','Please Select Sub Location !');
			return false;
		}	
	}

	if($("#renPT_option").val() == 2 ){
	 	if($("#selectVendor").val() == "" || Number($("#selectVendor").val()) ==  0){
	 		$('#saveInventory').show();
	 		showMessage('info','Please Select Vendor');
	 		return false;
	 	}
	}
	
	if($('#tallyIntegrationRequired').val() == 'true' || $('#tallyIntegrationRequired').val() == true){
		if(Number($('#tallyCompanyId').val()) <= 0){
			$('#saveInventory').show();
			showMessage('info', 'Please select tally company name !');
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
		$('#saveInventory').show();
		return false;	
	}
	showLayer();
	return true;
}


function showSubLocationDropDown(){
    var showSubLocationForMainLocation = false;
    if($("#showSubLocation").val() == true || $("#showSubLocation").val() == "true"){
    	var mainLocationIds = $("#mainLocationIds").val().split(',');

    	for(var i = 0; i < mainLocationIds.length; i++) {
    		if($("#location").val() == mainLocationIds[i]){
    			showSubLocationForMainLocation = true
    		}
    		
    	}
    }
    
    if(showSubLocationForMainLocation == true){
    	$("#subLocation").show();
    	$("#validateSubLocation").val(true);
    }else{
    	$("#subLocationId").val('')
    	$("#subLocation").hide();
    	$("#validateSubLocation").val(false);
    }
    
    if($("#roundupConfig").val() == true || $("#roundupConfig").val()=='true'){
	    setTimeout(function(){
	    	  var  invoiceAmount = 0;
	    	    $("input[name=tatalcost]").each(function(){
	    	    	invoiceAmount += Number($(this).val());
	    		});
	    	    $('#invoiceAmount').val((invoiceAmount).toFixed(2));
	    	
	    }, 300);
    }
}

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