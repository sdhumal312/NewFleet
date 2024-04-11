
$(document).ready(function() {
    $("#SelectVehicle").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getVehicleListFuel.in?Action=FuncionarioSelect2",
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
                            text: e.vehicle_registration,
                            slug: e.slug,
                            id: e.vid
                        }
                    })
                }
            }
        }
    })
});

function visibility(e) {
    var t = document.getElementById(e);
    "block" == t.style.display ? t.style.display = "none" : t.style.display = "block"
}

function IsInvoicenumber(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = t > 31 && 33 > t || t > 44 && 48 > t || t >= 48 && 57 >= t || t >= 65 && 90 >= t || t >= 97 && 122 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorInvoicenumber").innerHTML = "Special Characters not allowed", document.getElementById("errorInvoicenumber").style.display = n ? "none" : "inline", n
}

function visibility(e, t) {
    var n = document.getElementById(e),
        a = document.getElementById(t);
    "block" == n.style.display ? (n.style.display = "none", a.style.display = "block") : (n.style.display = "block", a.style.display = "none")
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
    }),$("#searchpart_show").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "../../getSearchMasterPart.in?Action=FuncionarioSelect2",
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
    })
}), $(document).ready(function() {
    var e = $(".input_fields_wrap"),
        t = $(".add_field_button"),
        n = 1;
    $(t).click(function(t) {
        t.preventDefault(), 10 > n && (n++, $(e).append('<div><div class="panel panel-success"><div class="panel-body"><div class="row1"><label class="L-size control-label">Search Parts Number :<abbr title="required">*</abbr></label><div class="I-size"><input type="hidden" id="searchpart' + n + '" name="partid_many" style="width: 100%;" required="required" placeholder="Please Enter 2 or more Part Name or Part Number" /></div></div><div class="row1"><label class="L-size control-label" for="issue_vehicle_id">Quantity:</label><div class="I-size"><input type="text" class="form-text" name="quantity_many" min="0.0" id="quantity' + n + '" maxlength="4" placeholder="ex: 23.78" required="required"data-toggle="tip"data-original-title="enter Part Quantity" onkeypress="return isNumberKey(event,this);"\tondrop="return false;"></div></div></div></div><a href="#" class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font></div> '), $(document).ready(function() {
            $("#searchpart" + n).select2({
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
            })
        }))
    }), $(e).on("click", ".remove_field", function(e) {
        e.preventDefault(), $(this).parent("div").remove(), n--
    })
}),$(document).ready(function() {
    var e = $(".input_fields_wrap_show"),
    t = $(".add_field_button_show"),
    n = 1;
$(t).click(function(t) {
    t.preventDefault(), 10 > n && (n++, $(e).append('<div><div class="panel panel-success"><div class="panel-body"><div class="row1"><label class="L-size control-label">Search Parts Number :<abbr title="required">*</abbr></label><div class="I-size"><input type="hidden" id="searchpart_show' + n + '" name="partid_many" style="width: 100%;" required="required" placeholder="Please Enter 2 or more Part Name or Part Number" /></div></div><div class="row1"><label class="L-size control-label" for="issue_vehicle_id">Quantity:</label><div class="I-size"><input type="text" class="form-text" name="quantity_many" min="0.0" id="quantity' + n + '" maxlength="4" placeholder="ex: 23.78" required="required"data-toggle="tip"data-original-title="enter Part Quantity" onkeypress="return isNumberKey(event,this);"\tondrop="return false;"></div></div></div></div><a href="#" class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font></div> '), $(document).ready(function() {
        $("#searchpart_show" + n).select2({
            minimumInputLength: 2,
            minimumResultsForSearch: 10,
            ajax: {
                url: "../../getSearchMasterPart.in?Action=FuncionarioSelect2",
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
        })
    }))
}), $(e).on("click", ".remove_field", function(e) {
    e.preventDefault(), $(this).parent("div").remove(), n--
})
}), $(document).ready(function() {
    $("#subscribeID").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        multiple: 0,
        ajax: {
            url: "getUserEmailId_Subscrible",
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
                            text: e.firstName + " " + e.lastName,
                            slug: e.slug,
                            id: e.user_id
                        }
                    })
                }
            }
        }
    })
}), (specialKeys = new Array).push(8), specialKeys.push(9), specialKeys.push(46), specialKeys.push(36), specialKeys.push(35), specialKeys.push(37), specialKeys.push(39), $(document).ready(function() {
    function e(e) {
        for (var c = document.getElementsByTagName("textarea"), u = 0; u < c.length; u++) com_satheesh.EVENTS.addEventHandler(c[u], "focus", t, !1), com_satheesh.EVENTS.addEventHandler(c[u], "blur", n, !1);
        c = document.getElementsByTagName("input");
        for (u = 0; u < c.length; u++) e.indexOf(-1 != c[u].getAttribute("type")) && (com_satheesh.EVENTS.addEventHandler(c[u], "focus", t, !1), com_satheesh.EVENTS.addEventHandler(c[u], "blur", n, !1));
        com_satheesh.EVENTS.addEventHandler(document.getElementById("formPartInventory"), "submit", l, !1), com_satheesh.EVENTS.addEventHandler(document.getElementById("formEditPartInventory"), "submit", i, !1), document.getElementsByTagName("input")[0].focus(), com_satheesh.EVENTS.addEventHandler(document.forms[0].searchpart, "blur", a, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].subscribe, "blur", r, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].quantity, "blur", s, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].invoiceDate, "blur", o, !1)
    }

    function t(e) {
        var t = com_satheesh.EVENTS.getEventTarget(e);
        null != t && (t.style.backgroundColor = c)
    }

    function n(e) {
        var t = com_satheesh.EVENTS.getEventTarget(e);
        null != t && (t.style.backgroundColor = "")
    }

    function a() {
        var e = document.getElementById("searchpart"),
            t = null != e.value && 0 != e.value.length,
            n = document.getElementById("grpinventoryPart");
        if (null != n) return t ? (n.className = "form-group has-success has-feedback", document.getElementById("inventoryPartIcon").className = "fa fa-check form-text-feedback", document.getElementById("inventoryPartErrorMsg").innerHTML = "") : (n.className = "form-group has-error has-feedback", document.getElementById("inventoryPartIcon").className = "fa fa-remove form-text-feedback", document.getElementById("inventoryPartErrorMsg").innerHTML = "Please enter search part name & number"), t
    }

    function r() {
        var e = document.getElementById("subscribeID"),
            t = null != e.value && 0 != e.value.length,
            n = document.getElementById("grpwoAssigned");
        if (null != n) return t ? (n.className = "form-group has-success has-feedback", document.getElementById("woAssignedIcon").className = "fa fa-check form-text-feedback", document.getElementById("woAssignedErrorMsg").innerHTML = "") : (n.className = "form-group has-error has-feedback", document.getElementById("woAssignedIcon").className = "fa fa-remove form-text-feedback", document.getElementById("woAssignedErrorMsg").innerHTML = "Please enter Requisition Person name "), t
    }

    function s() {
        var e = document.getElementById("quantity"),
            t = null != e.value && 0 != e.value.length,
            n = document.getElementById("grpquantity");
        return null != n && (t ? (n.className = "form-group has-success has-feedback", document.getElementById("quantityIcon").className = "fa fa-check  form-text-feedback", document.getElementById("quantityErrorMsg").innerHTML = "") : (n.className = "form-group has-error has-feedback", document.getElementById("quantityIcon").className = "fa fa-remove form-text-feedback", document.getElementById("quantityErrorMsg").innerHTML = "Please enter quantity ")), t
    }

    function o() {
        var e = document.getElementById("invoiceDate"),
            t = null != e.value && 0 != e.value.length,
            n = document.getElementById("grpinvoiceDate");
        return null != n && (t ? (n.className = "form-group has-success has-feedback", document.getElementById("invoiceDateIcon").className = "fa fa-check  form-text-feedback", document.getElementById("invoiceDateErrorMsg").innerHTML = "") : (n.className = "form-group has-error has-feedback", document.getElementById("invoiceDateIcon").className = "fa fa-remove form-text-feedback", document.getElementById("invoiceDateErrorMsg").innerHTML = "Please select Invoice Date")), t
    }

    function l(e) {
        var t = a();
        t &= r(), t &= s(), (t &= o()) || com_satheesh.EVENTS.preventDefault(e)
    }

    function i(e) {
        var t = r();
        t &= s(), (t &= o()) || com_satheesh.EVENTS.preventDefault(e)
    }
    var c = "#FFC";
    com_satheesh.EVENTS.addEventHandler(window, "load", function() {
        e("text")
    }, !1)
});


function confirmComplete() {
	if($('#isPartPendingQtyZero').val() == true || $('#isPartPendingQtyZero').val() == 'true'){
		var answer=confirm("There are few pending quantity to transfer ,  Do you want to continue ?");
		if (answer==true)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}