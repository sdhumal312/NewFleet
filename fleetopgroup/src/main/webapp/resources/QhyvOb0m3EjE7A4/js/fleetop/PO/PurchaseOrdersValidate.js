var	PAYMENT_MODE_PAID				= 1;
var	PAYMENT_MODE_NOT_PAID			= 2;
var	PAYMENT_MODE_APPROVED			= 3;
var	PAYMENT_MODE_PARTIALLY_PAID		= 4;
var	PAYMENT_MODE_NEGOTIABLE_PAID	= 5;
var	PAYMENT_MODE_CREATE_APPROVAL	= 6;

var e = 10;
t = $(".input_fields_wrap1"),
n = $(".add_field_button1"),
a = 1;

$(document).ready(function() {
    $(n).click(function() {
    	moreVehicle();
    }), $(t).on("click", ".remove_field", function(e) {
    	removeExpense();
    	})
    if($("#retectedPurchaseOrderPart").val() == true || $("#retectedPurchaseOrderPart").val() == 'true'){
    	$("#rejectedPart").show()
    }else{
    	$("#rejectedPart").hide()
    }	
    if($("#transferPurchaseOrderPart").val() == true || $("#transferPurchaseOrderPart").val() == 'true'){
    	$("#transferPart").show()
    }else{
    	$("#transferPart").hide()
    }
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
    
});

function removeExpense(){
	$(".remove_field").each(function(i){
		if(a - 2 == i)
		$(this).parent("div").remove(), a--;
	});
}

function toggle2(e, n) {
    var t = document.getElementById(e),
        r = document.getElementById(n);
    "block" == t.style.display ? (t.style.display = "none", r.innerHTML = "Add Part") : (t.style.display = "block", r.innerHTML = "Cancel Part")
}

function toggle3(e, n) {
    var t = document.getElementById(e),
        r = document.getElementById(n);
    "block" == t.style.display ? (t.style.display = "none", r.innerHTML = "Add Upholstery") : (t.style.display = "block", r.innerHTML = "Cancel Upholstery")
}

function toggle2Labor(e, n) {
    var t = document.getElementById(e),
        r = document.getElementById(n);
    "block" == t.style.display ? (t.style.display = "none", r.innerHTML = "Add Labor") : (t.style.display = "block", r.innerHTML = "Cancel Labor")
}

function toggle2Task(e, n) {
    var t = document.getElementById(e),
        r = document.getElementById(n);
    "block" == t.style.display ? (t.style.display = "none", r.innerHTML = "Add Task") : (t.style.display = "block", r.innerHTML = "Cancel Task")
}

function toggle4(e, n) {
	var t = document.getElementById(e),
        r = document.getElementById(n);
    "block" == t.style.display ? (t.style.display = "none", r.innerHTML = "Add Urea") : (t.style.display = "block", r.innerHTML = "Cancel Urea")
}

function sumthere(e, n, t, r, a) {
    var i = document.getElementById(e).value,
        o = document.getElementById(n).value,
        l = document.getElementById(t).value,
        c = document.getElementById(r).value,
        s = parseFloat(i) * parseFloat(o),
        d = s * l / 100,
        u = s - d,
        e = u * c / 100,
        y = u + e;
    isNaN(y) || (document.getElementById(a).value = y.toFixed(2))
}

function isNumberKeyQut(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        t = n >= 48 && 57 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorPin").innerHTML = "Alphabetical Characters not allowed", document.getElementById("errorPin").style.display = t ? "none" : "inline", t
}

function isNumberKeyEach(e, n) {
    var t = e.which ? e.which : event.keyCode;
    if (t > 31 && (48 > t || t > 57) && 46 != t && 8 != charcode) return !1;
    var r = $(n).val().length,
        a = $(n).val().indexOf(".");
    if (a > 0 && 46 == t) return !1;
    if (a > 0) {
        var i = r + 1 - a;
        if (i > 3) return !1
    }
    return !0
}

function isNumberKeyDis(e, n) {
    var t = e.which ? e.which : event.keyCode;
    if (t > 31 && (48 > t || t > 57) && 46 != t && 8 != charcode) return !1;
    var r = $(n).val().length,
        a = $(n).val().indexOf(".");
    if (a > 0 && 46 == t) return !1;
    if (a > 0) {
        var i = r + 1 - a;
        if (i > 3) return !1
    }
    return !0
}

function isNumberKeyTax(e, n) {
    var t = e.which ? e.which : event.keyCode;
    if (t > 31 && (48 > t || t > 57) && 46 != t && 8 != charcode) return !1;
    var r = $(n).val().length,
        a = $(n).val().indexOf(".");
    if (a > 0 && 46 == t) return !1;
    if (a > 0) {
        var i = r + 1 - a;
        if (i > 3) return !1
    }
    return !0
}

function IsNumericAmount(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        t = n >= 48 && 57 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorAmount").innerHTML = "Alphabets & Special Characters not allowed", document.getElementById("errorAmount").style.display = t ? "none" : "inline", t
}

function IsOrderdby(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        t = n > 31 && 33 > n || n > 44 && 48 > n || n >= 48 && 57 >= n || n >= 65 && 90 >= n || n >= 97 && 122 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorOrderdby").innerHTML = "Special Characters not allowed", document.getElementById("errorOrderdby").style.display = t ? "none" : "inline", t
}

function toggle2Tax(e, n) {
    var t = document.getElementById(e),
        r = document.getElementById(n);
    "block" == t.style.display ? (t.style.display = "none", r.innerHTML = '<i class="fa fa-inr">') : (t.style.display = "block", r.innerHTML = "Cancel")
}

function toggle2Freight(e, n) {
    var t = document.getElementById(e),
        r = document.getElementById(n);
    "block" == t.style.display ? (t.style.display = "none", r.innerHTML = '<i class="fa fa-inr">') : (t.style.display = "block", r.innerHTML = "Cancel")
}

function toggleReceived_quantity(e, n) {
    var t = document.getElementById(e),
        r = document.getElementById(n);
    "block" == t.style.display ? (t.style.display = "none", r.innerHTML = '<i class="fa fa-cube">') : (t.style.display = "block", r.innerHTML = "Cancel")
}

function getInventoryList(e) {
    $("#" + e).select2({
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
                            slug: e.slug,
                            id: e.partid
                        }
                        
                        
                    })
                   
                }
            }
        }
    })
}

function getClothInventoryList(e) {
	if($('#previousDate').val() == true || $('#previousDate').val() == 'true'){
	$("#lastPOpartDetails").html("");
	}
	
    $("#" + e).select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getClothTypesList.in",
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
                        	
                            text: e.clothTypeName,
                            slug: e.slug,
                            id: e.clothTypesId
                        }
                        
                        
                    })
                   
                }
            }
        }
    })
}

var specialKeys = new Array;
specialKeys.push(8), specialKeys.push(9), specialKeys.push(46), specialKeys.push(36), specialKeys.push(35), specialKeys.push(37), specialKeys.push(39), $(document).ready(function() {
    $(".select2").select2(), $("#tagPicker").select2({
        closeOnSelect: !1
    }), $("#select3").on("change", function() {
        var e = "",
            n = $(this).val();
        $("#vehicle_Meter option").each(function() {
            n == $(this).val() && (e = $(this).text())
        }), document.getElementById("vehicle_Odometer").placeholder = e, document.getElementById("vehicle_Odometer_old").value = e, $("#hidden").hide()
    })
}), $(document).ready(function() {
	getVendorTypeId();// used to get vendorTypeid for searching vendor with their respective type .
   
	$("#partlocation").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getPurchasePartLocation.in",
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
                            text: e.partlocation_name,
                            slug: e.slug,
                            id: e.partlocation_id
                        }
                    })
                }
            }
        }
    }), $("#workorder_id").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getWorkorderID.in",
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
                            text: "WO-" + e.workorders_Number,
                            slug: e.slug,
                            id: e.workorders_id
                        }
                    })
                }
            }
        }
    }),$("#vid").select2( {
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
    }),$.getJSON("subCompaniesAutoComplete.in", function(e) {
		subCompanyList	= e;//To get All Company Name 
		$("#subCompanyId").empty();
		if($("#editSubCompanyId").val() != undefined){
			var subCompanyId = $("#editSubCompanyId").val()
			var subCompanyName = $("#editSubCompanyName").val()
			$("#subCompanyId").append($("<option>").text(subCompanyName).attr("value",subCompanyId));
			$("#subCompanyId").select2("val",$("#editSubCompanyId").val());
		}
		$("#subCompanyId").select2();
		for(var k = 0; k <subCompanyList.length; k++){
			$("#subCompanyId").append($("<option>").text("Please Select SubCompany").attr("value",0));
			$("#subCompanyId").append($("<option>").text(subCompanyList[k].subCompanyName).attr("value", subCompanyList[k].subCompanyId));
		}

	});	
});

$(document).ready(function() {
        $(this).val();
        $.getJSON("getCompanyAddress.in", {
            companyname: $('#purchaseorder_buyer').val(),
            ajax: "true"
        }, function(e) {
            var n = "" + e.company_address + ", " + e.company_city + " , " + e.company_state + " , " + e.company_country + ". PIN- " + e.company_pincode;
            document.getElementById("purchaseorder_buyeraddress").value = n
        })
});

$(document).ready(function() {
    function f(a) {
        for (var b = document.getElementsByTagName("textarea"), c = 0; c < b.length; c++) com_satheesh.EVENTS.addEventHandler(b[c], "focus", g, !1), com_satheesh.EVENTS.addEventHandler(b[c], "blur", h, !1);
        b = document.getElementsByTagName("input");
        for (var c = 0; c < b.length; c++) a.indexOf(-1 != b[c].getAttribute("type")) && (com_satheesh.EVENTS.addEventHandler(b[c], "focus", g, !1), com_satheesh.EVENTS.addEventHandler(b[c], "blur", h, !1));
        com_satheesh.EVENTS.addEventHandler(document.getElementById("formPurchaseOrder"), "submit", t, !1), com_satheesh.EVENTS.addEventHandler(document.getElementById("formEditPurchaseOrder"), "submit", u, !1), com_satheesh.EVENTS.addEventHandler(document.getElementById("formPOAddParts"), "submit", v, !1), document.getElementsByTagName("input")[0].focus(), com_satheesh.EVENTS.addEventHandler(document.forms[0].poOpendate, "blur", i, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].poClosedate, "blur", j, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].selectVendor, "blur", k, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].purchaseorder_buyer, "blur", l, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].purchaseorder_buyeraddress, "blur", m, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].partlocation, "blur", n, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].inventory_name, "blur", o, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].quantity, "blur", p, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].parteachcost, "blur", q, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].discount, "blur", r, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].tax, "blur", s, !1)
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
        var a = document.getElementById("poOpendate"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grppoOpendate");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("poOpendateIcon").className = "fa fa-check form-text-feedback", document.getElementById("poOpendateErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("poOpendateIcon").className = "fa fa-remove form-text-feedback", document.getElementById("poOpendateErrorMsg").innerHTML = "Please select opened date"), b
    }

    function j() {
        var a = document.getElementById("poClosedate"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grppoClosedate");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("poClosedateIcon").className = "fa fa-check form-text-feedback", document.getElementById("poClosedateErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("poClosedateIcon").className = "fa fa-remove form-text-feedback", document.getElementById("poClosedateErrorMsg").innerHTML = "Please select required date"), b
    }

    function k() {
        var a = document.getElementById("selectVendor"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpselectVendor");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("selectVendorIcon").className = "fa fa-check form-text-feedback", document.getElementById("selectVendorErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("selectVendorIcon").className = "fa fa-remove form-text-feedback", document.getElementById("selectVendorErrorMsg").innerHTML = "Please select vendor name"), b
    }

    function l() {
        var a = document.getElementById("purchaseorder_buyer"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grppoBuyer");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("poBuyerIcon").className = "fa fa-check  form-text-feedback", document.getElementById("poBuyerErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("poBuyerIcon").className = "fa fa-remove form-text-feedback", document.getElementById("poBuyerErrorMsg").innerHTML = "Please select buyer name")), b
    }

    function m() {
        var a = document.getElementById("purchaseorder_buyeraddress"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grppoBuyerAddress");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("poBuyerAddressIcon").className = "fa fa-check  form-text-feedback", document.getElementById("poBuyerAddressErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("poBuyerAddressIcon").className = "fa fa-remove form-text-feedback", document.getElementById("poBuyerAddressErrorMsg").innerHTML = "Please enter buyer address")), b
    }

    function n() {
        var a = document.getElementById("partlocation"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grppoShip");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("poShipIcon").className = "fa fa-check  form-text-feedback", document.getElementById("poShipErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("poShipIcon").className = "fa fa-remove form-text-feedback", document.getElementById("poShipErrorMsg").innerHTML = "Please select ship address")), b
    }

    function o() {
        var a = document.getElementById("inventory_name"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grppoParts");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("PurchaseOrders_idIcon").className = "fa fa-check  form-text-feedback", document.getElementById("PurchaseOrders_idErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("PurchaseOrders_idIcon").className = "fa fa-remove form-text-feedback", document.getElementById("PurchaseOrders_idErrorMsg").innerHTML = "Please select po parts")), b
    }

    function p() {
        var a = document.getElementById("quantity"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grppoParts");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("quantityIcon").className = "fa fa-check  form-text-feedback", document.getElementById("quantityErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("quantityIcon").className = "fa fa-remove form-text-feedback", document.getElementById("quantityErrorMsg").innerHTML = "Please enter po quantity")), b
    }

    function q() {
        var a = document.getElementById("parteachcost"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grppoParts");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("parteachcostIcon").className = "fa fa-check  form-text-feedback", document.getElementById("parteachcostErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("parteachcostIcon").className = "fa fa-remove form-text-feedback", document.getElementById("parteachcostErrorMsg").innerHTML = "Please enter unit cost")), b
    }

    function r() {
        var a = document.getElementById("discount"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grppoParts");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("discountIcon").className = "fa fa-check  form-text-feedback", document.getElementById("discountErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("discountIcon").className = "fa fa-remove form-text-feedback", document.getElementById("discountErrorMsg").innerHTML = "Please enter discount")), b
    }

    function s() {
        var a = document.getElementById("tax"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grppoParts");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("taxIcon").className = "fa fa-check  form-text-feedback", document.getElementById("taxErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("taxIcon").className = "fa fa-remove form-text-feedback", document.getElementById("taxErrorMsg").innerHTML = "Please enter tax")), b
    }

    function t(a) {
        var b = i();
        b &= j(), b &= k(), b &= l(), b &= m(), (b &= n()) || com_satheesh.EVENTS.preventDefault(a)
    }

    function u(a) {
        var b = i();
        b &= j(), (b &= m()) || com_satheesh.EVENTS.preventDefault(a)
    }

    function v(a) {
        var b = o();
        b &= p(), b &= q(), b &= r(), (b &= s()) || com_satheesh.EVENTS.preventDefault(a)
    }
    var b = "#FFC";
    com_satheesh.EVENTS.addEventHandler(window, "load", function() {
        f("text")
    }, !1)
});

function validatePurchase(){
	showLayer();
	$('#savePurchase').hide();
	var partlocationId = Number($('#partlocationId').val());
	if(partlocationId <= 0){
		$('#savePurchase').show();
		 $("#partlocationId").select2('focus');
		showMessage('errors', 'Please Select Ship To!');
		hideLayer();
		$('#savePurchase').show();
		return false;
	}
	
	if(($("#VendorInputFieldInPOAdd").val() == true || $("#VendorInputFieldInPOAdd").val() == 'true' ) && Number($('#selectVendor').val()) <= 0){
		$('#savePurchase').show();
		showMessage('errors','Please Select Vendor !');
		hideLayer();
		$('#savePurchase').show();
		return false;
	}
	
	if($("#showTallyCompany").val() == true || $("#showTallyCompany").val() == 'true' ){
		if($("#tallyCompanyId").val() == "" || $("#tallyCompanyId").val() == undefined || Number($('#tallyCompanyId').val()) <= 0){
			$("#tallyCompanyId").select2('focus');
			showMessage('errors','Please Select Tally Company !');
			hideLayer();
			$('#savePurchase').show();
			return false;
		}
	}
	
	if($('#poClosedate').val().trim() == '' ){
		showMessage('errors','Please Select required date !');
		hideLayer();
		$('#savePurchase').show();
		return false;	
	}
	
	if (validateBankPaymentDetails && !validateBankPayment($('#bankPaymentTypeId').val())) {
		return false;
	}
	
	return true;
}



$("#purchaseTypeId").change(function(){
	getVendorTypeId()
}); 

function getVendorTypeId(){
	var jsonObject		= new Object();
	var purchaseTypeId 	= $("#purchaseTypeId").val();
	var purchaseType	= "part";
	
	switch (purchaseTypeId) {
	  case "1":
		  purchaseType = "part";
	    break;
	  case "2":
		  purchaseType = "tyre";
	    break;
	  case "3":
		  purchaseType = "battery";
	    break;
	  case "4":
		  purchaseType = "upholstery";
	    break;
	  case "5":
		  purchaseType = "urea";
	    break;  
	    
	  default :
	  	purchaseType = "part";
	   break;
	   
	  return   purchaseType;
	 
	}
	
	jsonObject["purchaseType"]				= purchaseType;

	$.ajax({
		url: "vendorTypeByName",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setVendorList(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setVendorList(data){
	var list = data.htData.vendorTypeList;
	var jsonObject		= new Object();
	var vendorTypeId 	= "";
	
	for(var i=0; i<list.length; i++){
		vendorTypeId = vendorTypeId+","+list[i].vendor_Typeid;
	}
	 vendorTypeId = vendorTypeId.replace(/^,/, '');
	 
	 if(($("#allVendorTypeAutoComplete").val() != undefined) && ($("#allVendorTypeAutoComplete").val() == true || $("#allVendorTypeAutoComplete").val() == 'true')){
		
		 $("#selectVendor").select2({
		        minimumInputLength: 3,
		        minimumResultsForSearch: 10,
		        ajax: {
		            url: "getAllTypeOfVendorAutoComplete.in",
		            dataType: "json",
		            type: "POST",
		            contentType: "application/json",
		            quietMillis: 50,
		            data: function(e) {
		                return {
		                    term: e,
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
	 }else{
		 $("#selectVendor").select2({
		        minimumInputLength: 3,
		        minimumResultsForSearch: 10,
		        ajax: {
		            url: "getVendorListByVendorType.in",
		            dataType: "json",
		            type: "POST",
		            contentType: "application/json",
		            quietMillis: 50,
		            data: function(e) {
		                return {
		                    term: e,
		                    text: vendorTypeId
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
	 } 
	 
	 if($("#editVendorInPOConfig").val() == true || $("#editVendorInPOConfig").val() == 'true'){
		 $('#selectVendor').select2('data', {
				id : $("#poVendorId").val(),
				text : $("#poVendorName").val()
			});
	 }
	
}

function addVendorInPO(){
	
	$('#vendorDetails').modal('show');
	$("#allTypeOfVendorId").select2("val", "");
	
	$("#allTypeOfVendorId").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getAllTypeOfVendorAutoComplete.in",
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
    });
	
}

$(document).ready(
		function($) {
			$('button[id=saveVendor]').click(function(e) {
				console.log("save")
				//showLayer();
				
				if($('#allTypeOfVendorId').val() == undefined || $('#allTypeOfVendorId').val() == ""){
					showMessage('info','Please Enter Vendor Name');
					hideLayer();
					return false;
				}
				
				var jsonObject							= new Object();
				jsonObject["vendorId"] 					=  $('#allTypeOfVendorId').val();
				jsonObject["purchaseOrderId"] 			=  $('#purchaseOrderId').val();
				
				$.ajax({
					url: "saveVendorInPO",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						showMessage('success','Updated Vendor Successfully');
						location.reload();
						
					},
					error: function (e) {
						hideLayer();
						showMessage('errors', 'Some Error Occurred!');
					}
				})
			})
		});

function addTallyCompanyInPO(){
	$('#tallyCompanyDetails').modal('show');
}

$(document).ready(
	function($) {
		$('button[id=saveTally]').click(function(e) {
			console.log("save")
			//showLayer();
			
			if($('#tallyCompanyId').val() == undefined || $('#tallyCompanyId').val() == ""){
				showMessage('info','Please Enter Tally Company Name');
				hideLayer();
				return false;
			}
			
			var jsonObject							= new Object();
			jsonObject["tallyCompanyId"] 			=  $('#tallyCompanyId').val();
			jsonObject["purchaseOrderId"] 			=  $('#purchaseOrderId').val();
			
			$.ajax({
				url: "saveTallyCompanyIdInPO",
				type: "POST",
				dataType: 'json',
				data: jsonObject,
				success: function (data) {
					
					if(data.invalidId != undefined && data.invalidId == true){
						hideLayer();
						showMessage('errors', 'Please Enter Valid Tally Company!');
					}
					
					if(data.tallyUpdated != undefined && data.tallyUpdated == true){
						showMessage('success','Tally Company Updated Successfully');
						location.reload();
					}	
					
				},
				error: function (e) {
					hideLayer();
					showMessage('errors', 'Some Error Occurred!');
				}
			})
		})
});

function validateSentPart(){
	
	if($("#VendorInputFieldInPOAddPart").val() == true || $("#VendorInputFieldInPOAddPart").val() == 'true'){
		if($("#updatedVendorId").val() == undefined || $("#updatedVendorId").val() == "" || $("#updatedVendorId").val() == 0){
			showMessage('info','Please Select Vendor First');
			return false;
		}
	}
	
	if($("advanceAmount").val() > $("#finalTotalCost").val()){
		showMessage('info','Advance Amount Can Not Be Greater Than Total Cost');
		return false;
	}
	
	if($("#showTallyCompany").val() == true || $("#showTallyCompany").val() == 'true'){
		if($("#updatedTallyCompanyId").val() == undefined || $("#updatedTallyCompanyId").val() == "" || $("#updatedTallyCompanyId").val() == 0){
			showMessage('info','Please Select Tally Company First');
			return false;
		}
	}
	if(($("#pendingApproval").val() == true || $("#pendingApproval").val() == 'true' ) && ($("#makeApprovalApproval").val() == true || $("#makeApprovalApproval").val() == 'true' ) && ($("#approvedApproval").val() != true || $("#approvedApproval").val() != 'true' )){
		showMessage('info','Approval is Pending ');
		return false;
	}
	if(($("#makeApprovalApproval").val() == true || $("#makeApprovalApproval").val() == 'true' ) && ($("#approvedApproval").val() == undefined )){
		showMessage('info','No Approval Found ');
		return false;
	}
	
	var confirmMsg = confirm('Are You Sure ? Sent PurchaseOrder')
	if(confirmMsg ==  true){
		
		return true;
	}else{
		return false;
	}
		
}

function showAddVehicle(totalQuantity,purchaseorderToPartId){
	$('#totalQty').val(totalQuantity);
	$('#purchaseorderToPartId').val(purchaseorderToPartId);
	
	var jsonObject							= new Object();
	jsonObject["purchaseorderToPartId"] 	=  purchaseorderToPartId;
	jsonObject["purchaseOrderId"] 			=  $('#purchaseOrderId').val();
	
	$.ajax({
		url: "getAllPurchasePartForVehicle",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setVehicleDetails(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	})
	
}
function setVehicleDetails(data){
	var remainingPartQuantity 	= 0;
	var addedPartQuantity 		= 0;
	$("#vehicleTable").empty();
	
	var vehicleDetails = data.purchasePartForVehicleList;
	
	if(vehicleDetails != undefined || vehicleDetails != null){
		for(var index = 0 ; index < vehicleDetails.length; index++){
			
			var columnArray = new Array();
			columnArray.push("<td class='fit'>"+(index+1)+"</td>");
			columnArray.push("<td class='fit'ar> <h4> "+ vehicleDetails[index].vehicleRegistration  +"</td>");
			columnArray.push("<td class='fit ar'>" + vehicleDetails[index].partQuantity +"</td>");
			columnArray.push("<td class='fit ar' style='vertical-align: middle;' ><a href='#' class='confirmation' onclick='deleteVehicleDetails("+vehicleDetails[index].purchasePartForVehicleId+");'><span class='fa fa-trash'></span> Delete</a></td>");
			
			$('#vehicleTable').append("<tr id='purchasePartForVehicleId"+vehicleDetails[index].purchasePartForVehicleId+"' >" + columnArray.join(' ') + "</tr>");
			addedPartQuantity += vehicleDetails[index].partQuantity;
		}
		columnArray = [];
		
		}else{
			showMessage('info','No Record Found!')
		}
	
	remainingPartQuantity = Number($('#totalQty').val())-Number(addedPartQuantity)
	$("#remainingQty").val(remainingPartQuantity);
	$('#vehicleDetails').modal('show');
	
}

function deleteVehicleDetails(purchasePartForVehicleId){
	
	var jsonObject									= new Object();
	jsonObject["purchasePartForVehicleId"] 			=  purchasePartForVehicleId;
	
	$.ajax({
		url: "deletePurchasePartForVehicle",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			showMessage('success','Deleted Successfully');
			location.reload();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	})
}

function validatePartQuantity(a){
	if(a == undefined){
		a="";
	}
	var remainingQuantity 	= Number($("#remainingQty").val());
	var partQuantity 		= new Array();
	var totalPartQuantity 	= 0 ;
	
	$("input[name=partQuantity]").each(function(){
		partQuantity.push($(this).val());
	});
	for(var i = 0; i < partQuantity.length; i++){
		totalPartQuantity += Number(partQuantity[i]); 
	}
	
	if(totalPartQuantity > remainingQuantity){
		showMessage('info','Please Enter Valid Part Quantity');
		$('#partQty'+a).val(0)
		return false;
	}
		
}

function saveVehiclePart(){
	showLayer();
	
	var jsonObject			= new Object();
	var array	 			= new Array();
	var vehicle 			= new Array();
	var partQuantity 		= new Array();
	
	jsonObject["validateDoublePost"] 	 	=  true;
	jsonObject["unique-one-time-token"] 	=  $('#unique-one-time-token').val();
	
	$("input[name=vehicle]").each(function(){
		vehicle.push($(this).val());
	});
	$("input[name=partQuantity]").each(function(){
		partQuantity.push($(this).val());
	});
	
	for(var i =0 ; i< vehicle.length; i++){
		var vehicleDetailsList					= new Object();
		
		if(vehicle[i] == undefined || vehicle[i] == "" ){
			showMessage('info','Please Select Vehicle');
			hideLayer();
			return false;
		}
		
		if(partQuantity[i] == undefined || partQuantity[i] == "" || partQuantity[i] == 0){
			showMessage('info','Please Enter Valid Part Quantity');
			hideLayer();
			return false;
		}
		
		vehicleDetailsList.vid							= vehicle[i];
		vehicleDetailsList.partQuantity					= partQuantity[i];
		vehicleDetailsList.purchaseOrderId				= $('#purchaseOrderId').val();
		vehicleDetailsList.purchaseOrderToPartId		= $('#purchaseorderToPartId').val();
		
		array.push(vehicleDetailsList);
	}
	
	jsonObject.vehicleDetailsFinalList = JSON.stringify(array);
	
	
	$.ajax({
		url: "addPurchasePartForVehicle",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			showMessage('success','Added Successfully');
			location.reload();
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	})

}


function moreVehicle(){
	
	 e > a && (a++, $(t).append('<div><div class="row1"><div class="col-md-5"><label class="L-size control-label" >Vehicle:<abbr title="required">*</abbr></label> <div class="I-size" ><input type="hidden" class="select" style="width: 100%;" name="vehicle" id="vid'+a+'" required="required" ></div></div><div class="col-md-4"><label class="L-size control-label">Part Qty<abbr title="required">*</abbr></label><div class="I-size" ><input type="text" class="form-text"  style="width: 80%;" name="partQuantity" id="partQty'+a+'" onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="validatePartQuantity('+a+');"/></div> </div></div><br/><a href="#"class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i>Remove</a></font></div>'),
	$(document).ready(function() {
		 $("#vid"+a).select2( {
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
		    })
	 }));
}

function showPOVehicleDetails(purchaseOrderToPartId){
	
	var jsonObject							= new Object();
	jsonObject["purchaseorderToPartId"] 	=  purchaseOrderToPartId;
	jsonObject["purchaseOrderId"] 			=  $('#purchaseOrderId').val();
	
	$.ajax({
		url: "getAllPurchasePartForVehicle",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setPOVehicleDetails(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	})
	
}

function setPOVehicleDetails(data){
	$("#vehicleDetailsTable").empty();
	
	var vehicleDetails = data.purchasePartForVehicleList;
	
	if(vehicleDetails != undefined || vehicleDetails != null){
		for(var index = 0 ; index < vehicleDetails.length; index++){
			
			var columnArray = new Array();
			columnArray.push("<td class='fit'>"+(index+1)+"</td>");
			columnArray.push("<td class='fit'ar> <h4> "+ vehicleDetails[index].vehicleRegistration  +"</td>");
			columnArray.push("<td class='fit ar'>" + vehicleDetails[index].partQuantity +"</td>");
			
			$('#vehicleDetailsTable').append("<tr id='purchasePartForVehicleId"+vehicleDetails[index].purchasePartForVehicleId+"' >" + columnArray.join(' ') + "</tr>");
		}
		columnArray = [];
		
		}else{
			showMessage('info','No Record Found!')
		}
	
	$('#vehicleDetails').modal('show');
	
}

function changePOToOrderedStatus(){
	
	if (confirm('Are You Sure You Want To Change Purchase Order Status From Received To Ordered ?')) {
		
		var jsonObject					= new Object();
		jsonObject["purchaseOrderId"]	= $('#purchaseOrderId').val();
		
		showLayer();
		$.ajax({
			url: "updatePartPOStatusFromReceivedToOrdered.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				
				hideLayer();
				
				if(data.partAlreadyUsed != undefined && data.partAlreadyUsed == true){
					showMessage('info', 'Cannot Change PO Status From Received To Ordered Since Part is Already Used !');
				}
				
				if(data.poUpdated != undefined && data.poUpdated == true){
					showMessage('success', 'PO Status Changed Successfully');
					window.location.replace("PurchaseOrders_Parts.in?ID="+$('#purchaseOrderId').val()+"");
				}
				
				if(data.NoAuthen != undefined && data.NoAuthen == true){
					showMessage('info', 'PO Status Cannot Be Changed. Please Grant Permission First.');
					window.location.replace("PurchaseOrders_Parts.in?ID="+$('#purchaseOrderId').val()+"");
				}
			},
			error: function (e) {
				console.log("Error : " , e);
				showMessage('errors', 'Some Error Occurred!');
				hideLayer();
			}
		});
	} 
}

function changeBatteryPOToOrderedStatus(){
	
	if (confirm('Are You Sure You Want To Change Purchase Order Status From Received To Ordered ?')) {
		
		var jsonObject					= new Object();
		jsonObject["purchaseOrderId"]	= $('#purchaseOrderId').val();
		
		showLayer();
		$.ajax({
			url: "updateBatteryPOStatusFromReceivedToOrdered.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				
				hideLayer();
				if(data.batteryAlreadyUsed != undefined && data.batteryAlreadyUsed == true){
					showMessage('info', 'Cannot Change PO Status From Received To Ordered Since Battery is Already Used !');
				}
				
				if(data.poUpdated != undefined && data.poUpdated == true){
					showMessage('success', 'PO Status Changed Successfully');
					window.location.replace("PurchaseOrders_Parts.in?ID="+$('#purchaseOrderId').val()+"");
				}
				
			},
			error: function (e) {
				console.log("Error : " , e);
				showMessage('errors', 'Some Error Occurred!');
				hideLayer();
			}
		});
	} 
}

function changeTyrePOToOrderedStatus(){
	
	if (confirm('Are You Sure You Want To Change Purchase Order Status From Received To Ordered ?')) {
		
		var jsonObject					= new Object();
		jsonObject["purchaseOrderId"]	= $('#purchaseOrderId').val();
		
		showLayer();
		$.ajax({
			url: "updateTyrePOStatusFromReceivedToOrdered.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				
				hideLayer();
				if(data.tyreAlreadyUsed != undefined && data.tyreAlreadyUsed == true){
					showMessage('info', 'Cannot Change PO Status From Received To Ordered Since Tyre is Already Used !');
				}
				
				if(data.poUpdated != undefined && data.poUpdated == true){
					showMessage('success', 'PO Status Changed Successfully');
					window.location.replace("PurchaseOrders_Parts.in?ID="+$('#purchaseOrderId').val()+"");
				}
				
			},
			error: function (e) {
				console.log("Error : " , e);
				showMessage('errors', 'Some Error Occurred!');
				hideLayer();
			}
		});
	} 
}

function changeUpholsteryPOToOrderedStatus(){
	
	if (confirm('Are You Sure You Want To Change Purchase Order Status From Received To Ordered ?')) {
		
		var jsonObject						= new Object();
		jsonObject["purchaseOrderId"]		= $('#purchaseOrderId').val();
		jsonObject["purchaseOrderNumber"]	= $('#purchaseOrderNumber').val();
		
		showLayer();
		$.ajax({
			url: "updateUpholsteryPOStatusFromReceivedToOrdered.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				
				hideLayer();
				if(data.poUpdated != undefined && data.poUpdated == true){
					showMessage('success', 'PO Status Changed Successfully');
					window.location.replace("PurchaseOrders_Parts.in?ID="+$('#purchaseOrderId').val()+"");
				}
				
			},
			error: function (e) {
				console.log("Error : " , e);
				showMessage('errors', 'Some Error Occurred!');
				hideLayer();
			}
		});
	} 
}

function changeUreaPOToOrderedStatus(){
	
	if (confirm('Are You Sure You Want To Change Purchase Order Status From Received To Ordered ?')) {
		
		var jsonObject						= new Object();
		jsonObject["purchaseOrderId"]		= $('#purchaseOrderId').val();
		
		showLayer();
		$.ajax({
			url: "updateUreaPOStatusFromReceivedToOrdered.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				
				hideLayer();
				if(data.poUpdated != undefined && data.poUpdated == true){
					showMessage('success', 'PO Status Changed Successfully');
					window.location.replace("PurchaseOrders_Parts.in?ID="+$('#purchaseOrderId').val()+"");
				}
				
			},
			error: function (e) {
				console.log("Error : " , e);
				showMessage('errors', 'Some Error Occurred!');
				hideLayer();
			}
		});
	} 
}
function validatePartSave(){
	/*$('#savePart').hide();
	showLayer();
	return true;*/
	if($('#quantity').val()==""){
		showMessage('info','Please Enter Quantity');
		$('#quantity').focus();
		return false;
	}
	
	if($('#parteachcost').val()==""){
		showMessage('info','Please Enter EachCost');
		return false;
	}
	
	if($('#discount').val()==""){
		showMessage('info','Please Enter Discount');
		return false;
	}
	
	if($('#tax').val()==""){
		showMessage('info','Please Enter GST');
		return false;
	}
	
	return true;
}
function validatePartReceive(){
	$('#receivePart').hide();
	// showLayer();
	return true;
}
function receivePurchaseOrder(){
	$('#receivePO').hide();
	if(confirm('Are you sure? Received all parts')){
		showLayer();
		if($('#invoiceno').val()== undefined || $('#invoiceno').val()== "" ){
			hideLayer();
			showMessage('info','Please Enter InvoiceNo');
			$('#receivePO').show();
			return false;
		}
		return true;
	}else{
		$('#receivePO').show();
		return false;
	}
}

/**********common function for all type of PO************/
function editPurchaseOrderPartDetails(partId,partName, modelId, modelName, sizeId, sizeName, purchaseorderToPartId,quantity,unitCost,discount,tax,totalCost){
	
	$("#partName").select2({
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
    }),$("#partName").change(function() {
    	$.getJSON("getPurchaseOrderStock.in",{
    		PARTID : $("#partName").val(),
			ajax : "true"
		},
		function(e) {
			var t = "", d = "", n = "", i = "", r = "";
					d = e.inventory_all_id,
					n = e.partnumber,
					i = e.partname,
					r = e.all_quantity;
			if (r != 0
					&& null != r)
				t = '<p style="color: red;"> You have '
						+ r
						+ ' Quantity in stock . <a href="showInventory.in?inventory_all_id='
						+ d
						+ '" target="_blank">View <i class="fa fa-external-link"></i></a></p>'
			else
				t = '<p style="color: blue;">You don\'t have in stock.</P>';
			$(
					"#partStock")
					.html(t)
		})
}), $("#editClothTypesId").select2({
	        minimumInputLength: 2,
	        minimumResultsForSearch: 10,
	        ajax: {
	            url: "getClothTypesList.in",
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
	                        	
	                            text: e.clothTypeName,
	                            slug: e.slug,
	                            id: e.clothTypesId
	                        }
	                        
	                        
	                    })
	                   
	                }
	            }
	        }
	    });
	

	$('#purchaseorderToPartId').val(purchaseorderToPartId);
	$('#editQuantity').val(quantity);    
	$('#editUnitPrice').val(unitCost);   
	$('#editDiscount').val(discount);    
	$('#editGST').val(tax);         
	$('#editTotalCost').val(totalCost);   
	$('#purcahseOrderTotal').val(totalCost);
	
	 $('#oldQuantity').val(quantity);  
	 $('#oldUnitPrice').val(unitCost); 
	 $('#oldGst').val(tax);       
	
	$('#editPartDetailsModal').modal('show');
	setPartDetails(partId,partName, modelId, modelName, sizeId, sizeName)
}

/*common function for all type of PO*/
function updatePurchaseOrderPartDetails(){
	
	if(!validatePurchaseOrderPartDetails($('#purchaseOrderTypeId').val())){
		return false;
	}
	
	
	var jsonObject							= new Object();
	jsonObject["purchaseOrderId"]			= $('#purchaseOrderId').val();
	jsonObject["purchaseorderTypeId"]		= $('#purchaseOrderTypeId').val();
	jsonObject["purchaseorderToPartId"]		= $('#purchaseorderToPartId').val();
	
	jsonObject["partId"]					= $('#partName').val(); //part
	
	jsonObject["tyreManufacturer"]			= $('#editTyreManufacturer').val();//tyre
	jsonObject["tyreModel"]					= $('#editTyreModel').val();
	jsonObject["tyreSize"]					= $('#editTyreSize').val();
	
	jsonObject["batteryManufacturer"]		= $('#editBatteryManufacturer').val();//battery
	jsonObject["batteryModel"]				= $('#editBatteryModel').val();
	jsonObject["batteryCapacity"]			= $('#editBatterySize').val();
	
	jsonObject["clothTypeId"]				= $('#editClothTypesId').val();// upholstery
	
	jsonObject["ureaManufacturerId"]		= $('#editUreaManufacturerId').val(); // Urea
	jsonObject["purcahseOrderTotal"]		= $('#purcahseOrderTotal').val(); // update  purchaseorder total amount only for urea
	
	jsonObject["oldQuantity"]				= $('#oldQuantity').val();
	jsonObject["oldUnitPrice"]				= $('#oldUnitPrice').val();
	jsonObject["oldGst"]					= $('#oldGst').val();
	
	jsonObject["quantity"]					= $('#editQuantity').val();
	jsonObject["notreceived_quantity"]		= $('#editQuantity').val();
	jsonObject["parteachcost"]				= $('#editUnitPrice').val();
	jsonObject["discount"]					= $('#editDiscount').val();
	jsonObject["tax"]						= $('#editGST').val();
	jsonObject["totalcost"]					= $('#editTotalCost').val();
	
	
	showLayer();
	$.ajax({
		url: "updatePurchaseOrderPartDetails.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			location.reload();
		},
		error: function (e) {
			console.log("Error : " , e);
			showMessage('errors', 'Some Error Occurred!');
			hideLayer();
		}
	});
	
}

function validatePurchaseOrderPartDetails(purchaseOrderType){
	switch (Number(purchaseOrderType)) {
	  case 1:
	    if($('#partName').val() == undefined || $('#partName').val() == "" ){
	    	showMessage('info','Please Select Part Name');
	    	hideLayer();
	    	return false;
	    }
	    break;
	  case 2:
		  if($('#editTyreManufacturer').val() == undefined || $('#editTyreManufacturer').val() == "" ){
		    	showMessage('info','Please Select Tyre Manufacturer');
		    	hideLayer();
		    	return false;
		    }
		  if($('#editTyreModel').val() == null || $('#editTyreModel').val() == 0  || $('#editTyreModel').val() == undefined || $('#editTyreModel').val() == "" ){
			  showMessage('info','Please Select Tyre Model');
			  hideLayer();
			  return false;
		  }
		  if($('#editTyreSize').val() == undefined || $('#editTyreSize').val() == "" ){
			  showMessage('info','Please Select Tyre Size');
			  hideLayer();
			  return false;
		  }
	    break;
	  case 3:
		  if($('#editBatteryManufacturer').val() == undefined || $('#editBatteryManufacturer').val() == "" ){
		    	showMessage('info','Please Select Battery Manufacturer');
		    	hideLayer();
		    	return false;
		    }
		  if($('#editBatteryModel').val() == null || $('#editBatteryModel').val() == 0 || $('#editBatteryModel').val() == undefined || $('#editBatteryModel').val() == "" ){
			  showMessage('info','Please Select Battery Model');
			  hideLayer();
			  return false;
		  }
		  if($('#editBatterySize').val() == undefined || $('#editBatterySize').val() == "" ){
			  showMessage('info','Please Select Battery Capacity');
			  hideLayer();
			  return false;
		  }
	    break;
	  case 4:
		  if($('#editClothTypesId').val() == undefined || $('#editClothTypesId').val() == "" ){
		    	showMessage('info','Please Select Upholstery Name');
		    	hideLayer();
		    	return false;
		    }
	    break;
	  case 5:
		  if($('#editUreaManufacturerId').val() == undefined || $('#editUreaManufacturerId').val() == "" ){
		    	showMessage('info','Please Select Manufacturer Name');
		    	hideLayer();
		    	return false;
		    }
			if($("#editUnitPrice").val() > 100){
				 showMessage('info','Unit Price Should Not Be Greater Than 100');
				 $("#editUnitPrice").val(0);
				 return false;
			 }
	    break;
	}
	
	if($('#editQuantity').val()  == "" || $('#editQuantity').val() <= 0 ){
		showMessage('info','Please Enter Quantity');
    	hideLayer();
    	return false;
	}
	if($('#editUnitPrice').val() == "" || $('#editUnitPrice').val() <= 0 ){
		showMessage('info','Please Enter Unit Price');
    	hideLayer();
    	return false;
	}
	if($('#editDiscount').val() == "" || $('#editDiscount').val() < 0 ){
		showMessage('info','Please Enter Discount');
    	hideLayer();
    	return false;
	}
	if($('#editGST').val() == "" || $('#editGST').val() < 0 ){
		showMessage('info','Please Enter GST');
    	hideLayer();
    	return false;
	}
	return true; 
	
}

function setPartDetails(partId,partName, modelId, modelName, sizeId, sizeName){
	switch (Number($('#purchaseOrderTypeId').val())) {
	  case 1:
			$('#partName').select2('data', {
				id : Number(partId),
				text : partName
			});
			$('#partName').val(partId);
	    break;
	  case 2:
		 
		  $('#editTyreManufacturer').select2('data', {
				id : Number(partId),
				text : partName
			});
		  $('#editTyreModel').select2('data', {
				id : modelId,
				text : modelName,
				val : modelId
			});
		  $('#editTyreSize').select2('data', {
			  id : sizeId,
			  text : sizeName
		  });
		  
		  $('#editTyreManufacturer').val(partId);
		  $("#editTyreModel").append($("<option>").text(modelName).attr("value",modelId));
		  $('#editTyreSize').val(sizeId);
		 
	    break;
	  case 3:
		  $('#editBatteryManufacturer').select2('data', {
				id : Number(partId),
				text : partName
			});
		  $('#editBatteryModel').select2('data', {
				id : modelId,
				text : modelName
			});
		  $('#editBatterySize').select2('data', {
			  id : sizeId,
			  text : sizeName
		  });
		  
		  $('#editBatteryManufacturer').val(partId);
		  $("#editBatteryModel").append($("<option>").text(modelName).attr("value",modelId));
		  $('#editBatterySize').val(sizeId);
	    break;
	  case 4:
			$('#editClothTypesId').select2('data', {
				id : Number(partId),
				text : partName
			});
		  $('#editClothTypesId').val(partId);
	    break;
	  case 5:
			$('#editUreaManufacturerId').select2('data', {
				id : Number(partId),
				text : partName
			});
			 $('#editUreaManufacturerId').val(partId);
			 getEditstockQuantity();
	    break;
	}
	
	
	
}

function openApproval(vendorPaymodeId, vendorApprovalId){

	if(vendorPaymodeId == PAYMENT_MODE_CREATE_APPROVAL){
		window.open("AddServiceApproval.in?approvalId="+vendorApprovalId+" ", '_blank');
	}else if(vendorPaymodeId == PAYMENT_MODE_APPROVED){
		window.open("ShowApproval.in?approvalId="+vendorApprovalId+"", '_blank');
	}else if(vendorPaymodeId == PAYMENT_MODE_PAID || vendorPaymodeId == PAYMENT_MODE_PARTIALLY_PAID ||  vendorPaymodeId == PAYMENT_MODE_NEGOTIABLE_PAID){
		window.open("ShowApprovalPayment.in?approvalId="+vendorApprovalId+" ", '_blank');
	}

}

function makeApproval(){
	
	$("#makeApprovalMoadal").modal('show');

	$(".partVendorId").select2("val", "");
	

	$(".partVendorId").select2({
		minimumInputLength: 3,
		minimumResultsForSearch: 10,
		ajax: {
			url: "getAllTypeOfVendorAutoComplete.in",
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
	});
if( $("#updatedVendorId").val() != undefined ){
	$('.partVendorId').select2('data', {
		id : $("#updatedVendorId").val(),
		text : $("#updatedVendorName").val()
	});
}
}
function getLocationWisePartQuantity(partId,purchaseOrderToPartId){
	var jsonObject								= new Object();
	jsonObject["locationId"]					= $("#locationId").val();
	jsonObject["partId"]						= partId;
	jsonObject["companyId"]						= $("#companyId").val();
	jsonObject["purchaseOrderToPartId"]			= purchaseOrderToPartId;
	
	
	showLayer();
	$.ajax({
		url: "getLocationWisePartQuantity.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setLocationWisePartQuantity(data);
			hideLayer();
		},
		error: function (e) {
			console.log("Error : " , e);
			showMessage('errors', 'Some Error Occred!');
			hideLayer();
		}
	});
	
}

function getLocationWiseBatteryQuantity(manufacturerId,typeId,sizeId){
	var jsonObject								= new Object();
	jsonObject["locationId"]					= $("#locationId").val();
	jsonObject["manufacturerId"]				= manufacturerId;
	jsonObject["typeId"]						= typeId;
	jsonObject["sizeId"]						= sizeId;
	jsonObject["companyId"]						= $("#companyId").val();
	
	
	showLayer();
	$.ajax({
		url: "getLocationWiseBatteryQuantity.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setLocationWisePartQuantity(data);
			hideLayer();
		},
		error: function (e) {
			console.log("Error : " , e);
			showMessage('errors', 'Some Error Occred!');
			hideLayer();
		}
	});
	
}

function getLocationWiseTyreQuantity(manufacturerId,typeId,sizeId){
	var jsonObject								= new Object();
	jsonObject["locationId"]					= $("#locationId").val();
	jsonObject["manufacturerId"]				= manufacturerId;
	jsonObject["typeId"]						= typeId;
	jsonObject["sizeId"]						= sizeId;
	jsonObject["companyId"]						= $("#companyId").val();
	
	
	showLayer();
	$.ajax({
		url: "getLocationWiseTyreQuantity.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setLocationWisePartQuantity(data);
			hideLayer();
		},
		error: function (e) {
			console.log("Error : " , e);
			showMessage('errors', 'Some Error Occred!');
			hideLayer();
		}
	});
	
}

function getLocationWiseUpholsteryQuantity(clothTypesId){
	var jsonObject								= new Object();
	jsonObject["locationId"]					= $("#locationId").val();
	jsonObject["clothTypesId"]					= clothTypesId;
	jsonObject["companyId"]						= $("#companyId").val();
	
	
	showLayer();
	$.ajax({
		url: "getLocationWiseUpholsteryQuantity.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setLocationWisePartQuantity(data);
			hideLayer();
		},
		error: function (e) {
			console.log("Error : " , e);
			showMessage('errors', 'Some Error Occred!');
			hideLayer();
		}
	});
	
}

function setLocationWisePartQuantity(data){
	$("#purchaseOrderToPartId").val(data.purchaseOrderToPartId);
	var locationWisePartQuantity = data.locationWisePartQuantity;
	$("#locationPartDetailsHeader").empty();
	$("#locationPartDetailsTable").empty();
	console.log("locationWisePartList",locationWisePartQuantity)
	if(locationWisePartQuantity != undefined && locationWisePartQuantity.length > 0){
		for(var index = 0 ; index < locationWisePartQuantity.length; index++){
			
			var columnArray = new Array();
			var headerArray = new Array();
			if(locationWisePartQuantity[index].locationId == $("#locationId").val()){
				headerArray.push("<th class='fit ar'>Ship To Location :"+locationWisePartQuantity[index].location+"</th>");
			}else{
				headerArray.push("<th class='fit ar'>"+locationWisePartQuantity[index].location+"</th>");
			}
			
			columnArray.push("<td class='fit ar'>"+locationWisePartQuantity[index].quantity+"</td>");
			
			
			$('#locationPartDetailsHeader').append(headerArray.join(' '));
			$('#locationPartDetailsTable').append(columnArray.join(' '));
			
		}
		columnArray = [];
		$("#locationPartDetailsModal").modal('show');
		}else{
			showMessage('info','No Record Found!')
		}
}
	
function createPurchaseOrderPartApproval(){
	
if(confirm('Are you sure! Do you want to complete approval')){	
	
	showLayer();
	var jsonObject									= new Object();
	var array	 									= new Array();
	var purchaseOrderToPartIdArr 					= new Array();
	var partIdArr 									= new Array();
	var approvalQuantityArr 						= new Array();
	var eachCostArr 								= new Array();
	var taxArr 										= new Array();
	var discountArr 								= new Array();
	var approvalTotalCostArr 						= new Array();
	var remarkArr 									= new Array();
	var statusArr 									= new Array();
	var vendorArr 									= new Array();
	
	var tyreManufacturerArr 						= new Array();
	var tyreModelArr 								= new Array();
	var tyreSizeArr 								= new Array();
	
	var batteryManufacturerArr 						= new Array();
	var batteryTypeArr 								= new Array();
	var batterySizeArr 								= new Array();
	
	var clothTypeArr 								= new Array();
	var ureaManufacturerIdArr 						= new Array();
	
	
	$("input[name=approvalPurchaseOrderToPartId]").each(function(){
		purchaseOrderToPartIdArr.push($(this).val());
	});
	$("input[name=approvalPurchaseOrderPartId]").each(function(){
		partIdArr.push($(this).val());
	});
	$("input[name=approvalQuantity]").each(function(){
		approvalQuantityArr.push($(this).val().replace(/"/g, ""));
	});
	$("input[name=approvalTotalCost]").each(function(){
		approvalTotalCostArr.push($(this).val());
	});
	$('input[name*=status]:checked').each(function(){
		statusArr.push($(this).val());
	});
	$("input[name=remark]").each(function(){
		remarkArr.push($(this).val().trim());
	});
	$("input[name=vendorId]").each(function(){
		vendorArr.push($(this).val());
	});
	$("input[name=approvalEachCost]").each(function(){
		eachCostArr.push($(this).val());
	});
	$("input[name=approvalTax]").each(function(){
		taxArr.push($(this).val());
	});
	$("input[name=approvalDiscount]").each(function(){
		discountArr.push($(this).val());
	});
	
	
	$("input[name=approvalTyreManufacturerId]").each(function(){
		tyreManufacturerArr.push($(this).val());
	});
	$("input[name=approvaltyreModalId]").each(function(){
		tyreModelArr.push($(this).val());
	});
	$("input[name=approvalTyreSizeId]").each(function(){
		tyreSizeArr.push($(this).val());
	});
	
	$("input[name=approvalBatteryManufacturerId]").each(function(){
		batteryManufacturerArr.push($(this).val());
	});
	$("input[name=approvalBatteryTypeId]").each(function(){
		batteryTypeArr.push($(this).val());
	});
	$("input[name=approvalBatterySizeId]").each(function(){
		batterySizeArr.push($(this).val());
	});
	
	$("input[name=approvalPurchaseOrderClothTypeId]").each(function(){
		clothTypeArr.push($(this).val());
	});
	$("input[name=approvalUreaManufacturerId]").each(function(){
		ureaManufacturerIdArr.push($(this).val());
	});
	
	for(var i =0 ; i< purchaseOrderToPartIdArr.length; i++){
		var purchaseOrderToPart					= new Object();
		
		console.log("approvalQuantityArr[i]",approvalQuantityArr[i])
		if(approvalQuantityArr[i] == "" || approvalQuantityArr[i] == undefined || approvalQuantityArr[i] <= 0){
			showMessage('info','Please Enter Quantity');
			hideLayer();
			return false;
		}
		if(remarkArr[i] == "" || remarkArr[i] == undefined){
			showMessage('info','Please Enter Remark');
			hideLayer();
			return false;
		}
		
		purchaseOrderToPart.purchaseOrderType				= $("#purchaseOrderTypeId").val();
		purchaseOrderToPart.purchaseOrderToPartId			= purchaseOrderToPartIdArr[i];
		purchaseOrderToPart.partId							= partIdArr[i];
		purchaseOrderToPart.quantity						= approvalQuantityArr[i];
		purchaseOrderToPart.eachCost						= eachCostArr[i];
		purchaseOrderToPart.tax								= taxArr[i];
		purchaseOrderToPart.discount						= discountArr[i];
		purchaseOrderToPart.totalCost						= approvalTotalCostArr[i];
		purchaseOrderToPart.companyId						= $("#companyId").val();
		purchaseOrderToPart.remark							= remarkArr[i];
		purchaseOrderToPart.vendorId						= vendorArr[i];
		purchaseOrderToPart.purchaseOrderId					= $("#purchaseOrderId").val();
		
		purchaseOrderToPart.tyreManufacturerId				= tyreManufacturerArr[i];
		purchaseOrderToPart.tyreModelId						= tyreModelArr[i];
		purchaseOrderToPart.tyreSizeId						= tyreSizeArr[i];
		
		purchaseOrderToPart.batteryManufacturerId				= batteryManufacturerArr[i];
		purchaseOrderToPart.batteryTypeId						= batteryTypeArr[i];
		purchaseOrderToPart.batterySizeId						= batterySizeArr[i];
	
		purchaseOrderToPart.clothId								= clothTypeArr[i];
	
		purchaseOrderToPart.ureaManufacturerId			= ureaManufacturerIdArr[i];
	
		if(vendorArr[i] != null && vendorArr[i] != undefined && vendorArr[i] != "" && vendorArr[i] != $("#updatedVendorId").val()  ){
			purchaseOrderToPart.status							= 5;//Vendor change status
		}else{
			purchaseOrderToPart.status							= statusArr[i]
		}
		
		array.push(purchaseOrderToPart);
		
	}
	jsonObject["companyId"]					= $("#companyId").val();
	jsonObject["purchaseOrderId"]			= $("#purchaseOrderId").val();
	jsonObject.purchaseOrderToPartList 		= JSON.stringify(array);
	
	$.ajax({
		url: "createPurchaseOrderPartApproval",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.sequenceNotFound != undefined && (data.sequenceNotFound == true || data.sequenceNotFound == 'true')){
				showMessage('info','Sequence Not Found');
			}
			location.reload();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occred!');
		}
	});
	
}
}


function createNewPurchaseOrder(purchaseOrdersPartId){
	console.log("dfs",purchaseOrdersPartId)
	if($("#partVendorId"+purchaseOrdersPartId).val() != "" && $("#updatedVendorId").val() != $("#partVendorId"+purchaseOrdersPartId).val()){
		confirm('You are Changing The Vendor, Hence It Will Create Another PO For This Vendor ')
		$("#btnGroup"+purchaseOrdersPartId).hide();
	}else{
		$("#btnGroup"+purchaseOrdersPartId).show();
	}
	
}

function getLastPartCostDetails(partId){
	if ($('#previousDate').val() == true || $('#previousDate').val() == 'true') {
		var arr = Number(partId.split('_')[1]);
		var lastPOpartDetails = "lastPOpartDetails";
		if (!isNaN(arr))
			lastPOpartDetails = "lastPOpartDetails_" + arr;
		if ($("#" + partId + "").val() != "") {
			$.getJSON("getLastPartDetails.in", {
				PARTID: $("#" + partId + "").val(),
				partType: $('#purchaseOrderTypeId').val(),
				ajax: "true"
			}, function(data) {
				for (var index = 0; index < data.length; index++) {
					var e = data[index];
					var t = "", d = "", n = "", i = "", r = "", dis = e.discount, tax = e.tax, total = e.totalcost;
					d = e.parteachcost,
						n = e.purchaseorder_Number,
						i = e.quantity,
						r = e.purchaseorder_id,
						v = e.vendorName;

					if (r != 0 && null != r) {
						t = '<p style="color: blue;">';
						if (v != undefined && v != '')
							t += 'Vendor Name :' + v + ' ,';

						t += ' Previous rate : Each Cost = ' + d + ' <i class="fa fa-inr"></i> , Discount = ' + dis + '% , GST = ' + tax + '%  .'
							+ '  <a href="PurchaseOrders_Parts.in?ID='
							+ r
							+ '" target="_blank"> View <i class="fa fa-external-link"></i> </a></p>';
						$("#" + lastPOpartDetails).html($("#" + lastPOpartDetails).html() + " " + t);
					} else {
						$("#" + lastPOpartDetails).html("");
					}
				}
			})
		} else {
			$("#" + lastPOpartDetails).html("");
		}
	}
	if($('#locationWisePartCount').val() == true || $('#locationWisePartCount').val() == 'true'){
		if($("#"+partId+"").val() != ""){
	$.getJSON("getlocationWisePartCount", {
		PARTID: $("#"+partId+"").val(),
		partType:$('#purchaseOrderTypeId').val(),
		shipLocationId:$('#shipLocationId').val(),
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
		"#last_occurred")
		.html(t)
	})
		}else{
			$(
			"#last_occurred")
			.html("")	
			
		}
	
	}
}


$("#ureaManufacturerId").change(function (){
	if($('#previousRate').val() == true || $('#previousRate').val() == 'true'){
	$.getJSON("getLastUreaDetails.in", {
		manufacturerId: $(this).val(),
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

			$("#lastPOUreaDetails").html(t);

		}else{
			$("#lastPOUreaDetails").html("");
		}
	})
	
	}
	
})

function showSubPartModal(id){

	var jsonObject = new Object();
	jsonObject["partId"] = id;
	$.ajax({
		
		url : "getSubPartList",
		type : "POST",
		dataType : 'json',
		data : jsonObject,
		success : function(data){
			$('#subPartDetails').modal('show');
			setsubPartDetails(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
		
		
		
	})
	
}

function setsubPartDetails(data){
	$('#taskTable').empty();
	var thead = $('<thead>');
	var tr1 = $('<tr>');
	
	var th1 = 	$('<th>');
	var th2 =	$('<th>');
	var th3 =	$('<th>');
	var th4 =	$('<th>');
	var th5 =	$('<th>');
	
	tr1.append(th1.append("NO."));
	tr1.append(th2.append("Part Name"));
	tr1.append(th3.append("Quantity"));
	tr1.append(th4.append(" "));
	thead.append(tr1);
	
	var num = 1;
	var tbody = $('<tbody>');
	if(data.partList != undefined  && data.partList.length > 0) {
		var tasks= data.partList;
	for(var i = 0; i < tasks.length; i++) {
		var tr1 = $('<tr class="ng-scope">');
		var tr2 = $('<tr class="ng-scope">');
		var tr3 = $('<tr class="ng-scope">');
		var tr4 = $('<tr>');
		var td1		= $('<td>');
		var td2		= $('<td>');
		var td3		= $('<td>');
		var td4		= $('<td>');
		var td5		= $('<td>');
		var td6		= $('<td>');
		var td7		= $('<td>');
		
		var serviceLink ='<a href="/showMasterParts.in?partid='+tasks[i].partid+'" target="_blank" > '+tasks[i].partnumber+' ';
		tr1.append(td1.append(serviceLink));
		tr1.append(td2.append(tasks[i].partname))
		tr1.append(td4.append(tasks[i].location_quantity))
		tr1.append(td3.append('<a href="/showInventory.in?inventory_all_id='+tasks[i].inventory_all_id+'" target="_blank" > view <i class="fa fa-external-link"></i> </a> '))
		
		tbody.append(tr1);
		
	}
	}else{
		showMessage('info','No record found !')
	}
	$('#taskTable').append(thead);
	$('#taskTable').append(tbody);
	
	
}
$(document).ready(function(){
	if(($('#fromTransfer').val() == true || $('#fromTransfer').val() == 'true') && ($('#partFound').val()== false || $('#partFound').val()=='false' )){
		$('#quantity').val($('#rQuantity').val());	
		switch (Number($('#purchaseOrderTypeId').val())) {
		  case 1:
			  $('#addParts')[0].click();
				$('#inventory_name').select2('data', {
					id : $('#transactionId').val(),
					text : $('#transactionName').val()
				});
				  $('#inventory_name').select2('readonly',true);
		    break;
		  case 2:
			  $('#addParts')[0].click();
			  setTimeout(() => {
					  $('#tyremodel').select2();
					  $('#tyremodel').select2('data', {
							id : $('#rModelId').val(),
							text : $('#rModelName').val()
						});
					  $('#tyremodel').select2('readonly',true);
					  $('#tyremodel').trigger('change');
			}, 100);
		    break;
		  case 3:
			  $('#addParts')[0].click();
			  setTimeout(()=>{
				  $('#batteryCapacityId').select2('data', {
					  id : $('#rSizeId').val(),
					text : $('#rSizeName').val()
				  });  
				  $('#batteryCapacityId').select2("readonly",true);
			  },100)
			  
		    break;
		  case 4:
			  $('#addParts')[0].click();
				$('#clothTypesId').select2('data', {
					id : $('#transactionId').val(),
					text : $('#transactionName').val()
				});
				  $('#clothTypesId').select2("readonly",true);
		    break;
		  case 5:
			  setTimeout(()=>{
				  $('#showAddUreaModal')[0].click();
					$('#quantity').val($('#rQuantity').val());	
			  },200)
		    break;
		}
	}
});


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

function approveStatusOfPO(){
	
	var jsonObject							= new Object();
	jsonObject["remark"] 					=  $('#approvalRemark').val();
	jsonObject["purchaseOrderId"] 			=  $('#purchaseOrderId').val();
	
	if($('#approvalRemark').val() == null || $('#approvalRemark').val().trim() == ''){
		showMessage('info', 'Please enter remark');
		return false;
	}
	
	$.ajax({
		url: "approvalPO",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.success != undefined && data.success){
				showMessage('sucess', 'Purchase Order Approved Successfully !');
				location.reload();
			}
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	})
	
}

function openApprovePopup(){
	$('#poApprovalModal').modal('show');
}
function SavePoRemark()
{
	
	var jsonObject							= new Object();
	jsonObject["remark"] 					= $("#initial_note").val();
	jsonObject["companyId"]					= $("#companyId").val();
	jsonObject["purchaseOrderId"] 			=  $('#purchaseOrderId').val();
	
	$.ajax({
		url : "savePoRemark",
		type : "POST",
		datatype : 'json',
		data : jsonObject,
		success: function (data)
		{
			if(data.success != undefined && data.success){
				alert("Remark has been saved successfully !!")
				location.reload();
			}
		},
		error: function(e)
		{
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	})
	
}
$(document).ready(function(){
	
	var jsonObject							= new Object();
	jsonObject["remark"] 					= $("#initial_note").val();
	jsonObject["companyId"]					= $("#companyId").val();
	jsonObject["purchaseOrderId"] 			=  $('#purchaseOrderId').val();
	
	$.ajax({
		url : "getPoRemark",
		type : "POST",
		datatype : 'json',
		data : jsonObject,
		success: function (data)
		{
			if(data.success != undefined && data.success){
				$("#initial_note").val(data.remark);
				showMessage('sucess', 'Remark Saved  Successfully !');
				//location.reload();
			}
		},
		error: function(e)
		{
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	})
	
});
