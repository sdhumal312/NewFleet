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
    var invoiceAmountt = 0;
    
    $("input[name=tatalcost]").each(function(){
    	
    	invoiceAmountt += Number($(this).val());
    	$('#invoiceAmount').val(invoiceAmountt);
    	
	});
    
}

function isNumberKeyQut(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = 46 == t || t >= 48 && 57 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorINEACH").innerHTML = "Alphabetical Characters not allowed", document.getElementById("errorINEACH").style.display = n ? "none" : "inline", n
}
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
                t = e.make, document.getElementById("manufacturer").value = t, document.getElementById("manufacturer").readOnly = !1, 0 < t.length && (document.getElementById("manufacturer").readOnly = !0), $("#hidden").hide(), n = e.part_photoid, document.getElementById("part_photoid").value = n
            })
        })
    })
}), $(document).ready(function() {
    var e = $("#NoOfPartsAllowedToAdd").val(), addMorePartsAtBottom = $("#addMorePartsAtBottom").val(),
    t = $(".input_fields_wrap"),
        n = $(".add_field_button"),
        a = 1;
    if(addMorePartsAtBottom == true || addMorePartsAtBottom == 'true'){
    	t = $("#moreParts");
    }  
   
    $(n).click(function(n) {
        n.preventDefault(), e > a && (a++, $(t).append('<div><div class="panel panel-success"><div class="panel-body"><div class="row1"><label class="L-size control-label">Search Parts Number :<abbr title="required">*</abbr></label><div class="I-size"><input type="hidden" id="searchpart' + a + '" name="partid_many" style="width: 100%;" required="required" placeholder="Please Enter 2 or more Part Name or Part Number" /></div></div><div class="row1"><label class="L-size control-label" for="issue_vehicle_id">Manufacturer :<abbr title="required">*</abbr></label><div class="I-size"><input type="text" class="form-text" name="make_many" id="manufacturer' + a + '" required="required" maxlength="50" onkeypress="return IsManufacturer(event);"ondrop="return false;"> <label class="error" id="errorManufacturer" style="display: none"> </label></div></div><div class="row1"><label class="L-size control-label" for="issue_vehicle_id">Quantity:</label><div class="col-md-9"><div class="col-md-1"><input type="text" class="form-text" name="quantity_many" min="0.0" id="quantity' + a + '" maxlength="4" placeholder="ex: 23.78" required="required"data-toggle="tip"data-original-title="enter Part Quantity" onkeypress="return isNumberKey(event,this);"onkeyup="javascript:sumthere(\'quantity' + a + "', 'unitprice" + a + "', 'discount" + a + "', 'tax" + a + "', 'tatalcost" + a + '\' );"	ondrop="return false;"></div><div class="col-md-2"><input type="text" class="form-text" name="unitprice_many" id="unitprice' + a + '" maxlength="7" min="0.0"placeholder="Unit Cost" required="required" data-toggle="tip" data-original-title="enter Unit Price"onkeypress="return isNumberKey(event,this);" onkeyup="javascript:sumthere(\'quantity' + a + "', 'unitprice" + a + "', 'discount" + a + "', 'tax" + a + "', 'tatalcost" + a + '\' );"	ondrop="return false;"></div><div class="col-md-1"><input type="text" class="form-text" name="discount_many" min="0.0" id="discount' + a + '" maxlength="5" placeholder="Discount" required="required"data-toggle="tip" data-original-title="enter Discount" onkeypress="return isNumberKeyQut(event,this);"onkeyup="javascript:sumthere(\'quantity' + a + "', 'unitprice" + a + "', 'discount" + a + "', 'tax" + a + "', 'tatalcost" + a + '\' );"	ondrop="return false;"></div> <div class="col-md-1"> <input type="text" class="form-text" name="tax_many" id="tax' + a + '" maxlength="5" placeholder="GST" required="required"data-toggle="tip" data-original-title="enter GST" onkeypress="return isNumberKeyQut(event,this);"onkeyup="javascript:sumthere(\'quantity' + a + "', 'unitprice" + a + "', 'discount" + a + "', 'tax" + a + "', 'tatalcost" + a + '\' );"ondrop="return false;"></div><div class="col-md-2"><input type="text" class="form-text" maxlength="8" value="0.0" min="0.0" id="tatalcost' + a + '" name="tatalcost" readonly="readonly"data-toggle="tip" data-original-title="Total Cost" onkeypress="return isNumberKey(event,this);" ondrop="return false;"></div></div></div></div></div><a href="#" class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font></div> '), $(document).ready(function() {
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
            })
        }))
    }), $(t).on("click", ".remove_field", function(e) {
        e.preventDefault(), $(this).parent("div").remove(), a--
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
});

function AddPart(){
	
	$('#configureMorePart').modal('show');
}

function validateAddPart(){
	
	var forPartId	= true;
	var quantity	= true;
	var unitprice	= true;
	var discount	= true;
	var tax			= true;
	
	 $('input[name*=partid_many]').each(function(i,obj){
		if($(this).val() == undefined || $(this).val() == ""){
			forPartId	= false;
			this.focus();
			return false;
		}
		
	 });
	 
	 if(!forPartId ) {
			showMessage('errors', 'Please Select Part!');
			return false;
		}
	
	 $('input[name*=quantity_many]').each(function(obj){
		 if($(this).val() == undefined || $(this).val() == ""){
			 quantity = false;
			 this.focus();
			 return false;
		 }
	 });
	 
	 if(!quantity ) {
			showMessage('errors', 'Please Select Quantity!');
			return false;
		}
	 
	 $('input[name*=unitprice_many]').each(function(obj){
		 if($(this).val() == undefined || $(this).val() == ""){
			 unitprice = false;
			 this.focus();
			 return false;
		 }
	 });
	 
	 if(!unitprice) {
			showMessage('errors', 'Please Select UnitPrice!');
			return false;
		}
	 $('input[name*=discount_many]').each(function(obj){
		 if($(this).val() == undefined || $(this).val() == ""){
			 discount = false;
			 this.focus();
			 return false;
		 }
	 });
	 
	 if(!discount) {
			showMessage('errors', 'Please Select Discount!');
			return false;
		}
	 $('input[name*=tax_many]').each(function(obj){
		 if($(this).val() == undefined || $(this).val() == ""){
			 tax = false;
			 this.focus();
			 return false;
		 }
	 });
	 
	 if(!tax) {
			showMessage('errors', 'Please Select Tax!');
			return false;
		}
	 
	 
return true;
}

function addPartWarrantyDetails(inventoryId, serialNoAddedForParts, quantity, partId, locationId,repairId){

	if(serialNoAddedForParts == undefined || serialNoAddedForParts == null){
		serialNoAddedForParts = 0;
	}
	
	serialNoAddedForParts	= Number(serialNoAddedForParts);
	
	var jsonObject			= new Object();
	var noOfInputs = quantity - serialNoAddedForParts;
	
	jsonObject["inventoryId"]			= inventoryId;
	jsonObject["repairId"]				= repairId;
	
	$('#inventoryId').val(inventoryId);
	$('#partId').val(partId);
	$('#locationId').val(locationId);

	$.ajax({
		url: "getPartWarrantyDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
		$('#tableBody').empty();
				var srNo = 1;
			if(data.partWarranty != undefined && data.partWarranty.length > 0){
				var trTitle	= '<tr><td colspan="2" style="color:blue;"><b> Already added Serial Numbers</b></td></tr>'
				$('#tableBody').append(trTitle);
				for(var i = 0; i < data.partWarranty.length ; i++){
					var tr = '<tr><td>'+srNo+'</td><td class="form-text">'+data.partWarranty[i].partSerialNumber+'</td></tr>';
					srNo++;
					$('#tableBody').append(tr);
				}
			}
			
			if(noOfInputs > 0){
				$('#serialSave').show();
				var trDivider	= '<tr><td style="color:blue;" colspan="2"><b>Serial Numbers To Be added</b></td></tr>';
				$('#tableBody').append(trDivider);
				
				for(var i = 0; i < noOfInputs ; i++){
					var tr = '<tr><td>'+srNo+'</td><td><input type="text" name="partSerialNumber" class="form-text"/></td></tr>';
					$('#tableBody').append(tr);
					srNo++;
				}
			}else{
				$('#serialSave').hide();
			}
			
			
			
			$('#addPartSerialModal').modal('show');
		},
		error: function (e) {
			hideLayer();
			$('#savePartRow').show();
			showMessage('errors', 'Some Error Occurred!');
		}
	});

}

function saveWarrantySerialNumber(){

	var jsonObject				= new Object();
	var serialNumberArr 		= new Array();
	var array 					= new Array();
	
	jsonObject["inventoryId"] 	  	=  $('#inventoryId').val();
	jsonObject["partId"] 	  		=  $('#partId').val();
	jsonObject["locationId"] 	  	=  $('#locationId').val();
	jsonObject["invoiceDate"] 	  	=  $('#invoiceDate').val();
	
			$("input[name=partSerialNumber]").each(function(){
				if($(this).val() != null && $(this).val().trim() != '')
					serialNumberArr.push($(this).val());
			});
			if(serialNumberArr.length <= 0){
				showMessage('info', 'Please enter at least one serial number to save !');
				return false;
			}

			for(var i =0 ; i< serialNumberArr.length; i++){
					var serialDetails	= new Object();
					
					serialDetails.partSerialNumber	= serialNumberArr[i];
					
					array.push(serialDetails);
			}
		jsonObject.serialDetails = JSON.stringify(array);
		showLayer();
		
	 	$.ajax({
		url: "saveWarrantySerialNumber",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			location.reload();
		},
		error: function (e) {
			hideLayer();
			$('#savePartRow').show();
			showMessage('errors', 'Some Error Occurred!');
		}
	}); 
}
