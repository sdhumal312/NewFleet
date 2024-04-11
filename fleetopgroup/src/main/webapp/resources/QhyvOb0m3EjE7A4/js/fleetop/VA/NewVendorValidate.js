function showLabel() {
    var e = $("#grp_option :selected"),
        n = e.text();
    $("#target").text(n + " Number :")
}

function visibility(e) {
    var n = document.getElementById(e);
    "block" == n.style.display ? n.style.display = "none" : n.style.display = "block"
}

function IsVendorName(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        o = n > 31 && 33 > n || n > 44 && 48 > n || n >= 48 && 57 >= n || n >= 65 && 90 >= n || n >= 97 && 122 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorVendorName").innerHTML = "Special Characters not allowed", document.getElementById("errorVendorName").style.display = o ? "none" : "inline", o
}

function IsVendorPhone(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        o = n >= 48 && 57 >= n || n > 42 && 44 > n || n > 44 && 46 > n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorVendorPhone").innerHTML = "Alphabets & Special Characters not allowed", document.getElementById("errorVendorPhone").style.display = o ? "none" : "inline", o
}

function IsVendorLocation(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        o = n > 31 && 33 > n || n >= 45 && 57 >= n || n >= 65 && 90 >= n || n >= 97 && 122 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorVendorLocation").innerHTML = "Special Characters not allowed", document.getElementById("errorVendorLocation").style.display = o ? "none" : "inline", o
}

function IsVendorWesite(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        o = n > 31 && 33 > n || n >= 45 && 58 >= n || n >= 65 && 90 >= n || n >= 97 && 122 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorVendorWesite").innerHTML = "Special Characters not allowed", document.getElementById("errorVendorWesite").style.display = o ? "none" : "inline", o
}

function IsVendorAddress(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        o = n > 31 && 33 > n || n > 39 && 42 > n || n >= 45 && 57 >= n || n >= 65 && 90 >= n || n >= 97 && 122 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorVendorAddress").innerHTML = "Special Characters not allowed", document.getElementById("errorVendorAddress").style.display = o ? "none" : "inline", o
}

function IsVendorAddress2(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        o = n > 31 && 33 > n || n > 39 && 42 > n || n >= 45 && 57 >= n || n >= 65 && 90 >= n || n >= 97 && 122 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorVendorAddress2").innerHTML = "Special Characters not allowed", document.getElementById("errorVendorAddress2").style.display = o ? "none" : "inline", o
}

function IsVendorCity(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        o = n > 31 && 33 > n || n >= 65 && 90 >= n || n >= 97 && 122 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorVendorCity").innerHTML = "Special Characters & Numbers not allowed", document.getElementById("errorVendorCity").style.display = o ? "none" : "inline", o
}

function IsVendorState(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        o = n > 31 && 33 > n || n >= 65 && 90 >= n || n >= 97 && 122 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorVendorState").innerHTML = "Special Characters & Numbers not allowed", document.getElementById("errorVendorState").style.display = o ? "none" : "inline", o
}

function IsVendorCountry(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        o = n > 31 && 33 > n || n >= 65 && 90 >= n || n >= 97 && 122 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorVendorCountry").innerHTML = "Special Characters & Numbers not allowed", document.getElementById("errorVendorCountry").style.display = o ? "none" : "inline", o
}

function IsVendorPin(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        o = n >= 48 && 57 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorVendorPin").innerHTML = "Alphabet Characters not allowed", document.getElementById("errorVendorPin").style.display = o ? "none" : "inline", o
}

function IsVendorRemark(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        o = n > 31 && 33 > n || n > 39 && 42 > n || n >= 45 && 57 >= n || n >= 65 && 90 >= n || n >= 97 && 122 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorVendorRemark").innerHTML = "Special Characters not allowed", document.getElementById("errorVendorRemark").style.display = o ? "none" : "inline", o
}

function IsVendorPanNO(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
       o = n > 31 && 33 > n || n > 44 && 46 > n || n >= 45 && 57 >= n || n >= 65 && 90 >= n || n >= 97 && 122 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorVendorPanNO").innerHTML = "Special Characters not allowed", document.getElementById("errorVendorPanNO").style.display = o ? "none" : "inline", o
}

function IsVendorTaxNo(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        o = n > 31 && 33 > n || n > 44 && 46 > n || n >= 45 && 57 >= n || n >= 65 && 90 >= n || n >= 97 && 122 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorVendorTaxNo").innerHTML = "Special Characters not allowed", document.getElementById("errorVendorTaxNo").style.display = o ? "none" : "inline", o
}

function IsVendorVatNo(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        o = n > 31 && 33 > n || n > 44 && 46 > n || n >= 45 && 57 >= n || n >= 65 && 90 >= n || n >= 97 && 122 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorVendorVatNo").innerHTML = "Special Characters not allowed", document.getElementById("errorVendorVatNo").style.display = o ? "none" : "inline", o
}

function IsVendorCreditLimit(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        o = n >= 48 && 57 >= n || n > 42 && 44 > n || n > 44 && 46 > n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorVendorCreditLimit").innerHTML = "Alphabets & Special Characters not allowed", document.getElementById("errorVendorCreditLimit").style.display = o ? "none" : "inline", o
}

function IsVendorAdvancePaid(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        o = n >= 48 && 57 >= n || n > 42 && 44 > n || n > 44 && 46 > n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorVendorAdvancePaid").innerHTML = "Alphabets & Special Characters not allowed", document.getElementById("errorVendorAdvancePaid").style.display = o ? "none" : "inline", o
}

function IsVendorConName(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        o = n > 31 && 33 > n || n > 44 && 46 > n || n >= 65 && 90 >= n || n >= 97 && 122 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorVendorConName").innerHTML = "Special Characters not allowed", document.getElementById("errorVendorConName").style.display = o ? "none" : "inline", o
}

function IsVendorConPhone(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        o = n >= 48 && 57 >= n || n > 42 && 44 > n || n > 44 && 46 > n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorVendorConPhone").innerHTML = "Alphabets & Special Characters not allowed", document.getElementById("errorVendorConPhone").style.display = o ? "none" : "inline", o
}

function IsVendorConEmail(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        o = n > 31 && 33 > n || n > 39 && 42 > n || n >= 45 && 57 >= n || n >= 64 && 90 >= n || n >= 97 && 122 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorVendorConEmail").innerHTML = "Special Characters not allowed", document.getElementById("errorVendorConEmail").style.display = o ? "none" : "inline", o
}

function IsVendorConName2(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        o = n > 31 && 33 > n || n > 44 && 46 > n || n >= 65 && 90 >= n || n >= 97 && 122 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorVendorConName2").innerHTML = "Special Characters not allowed", document.getElementById("errorVendorConName2").style.display = o ? "none" : "inline", o
}

function IsVendorConPhone2(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        o = n >= 48 && 57 >= n || n > 42 && 44 > n || n > 44 && 46 > n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorVendorConPhone2").innerHTML = "Alphabets & Special Characters not allowed", document.getElementById("errorVendorConPhone2").style.display = o ? "none" : "inline", o
}

function IsVendorConEmail2(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        o = n > 31 && 33 > n || n > 39 && 42 > n || n >= 45 && 57 >= n || n >= 64 && 90 >= n || n >= 97 && 122 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorVendorConEmail2").innerHTML = "Special Characters not allowed", document.getElementById("errorVendorConEmail2").style.display = o ? "none" : "inline", o
}

function IsVendorBankName(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        o = n > 31 && 33 > n || n >= 48 && 57 >= n || n >= 65 && 90 >= n || n >= 97 && 122 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorVendorBankName").innerHTML = "Special Characters not allowed", document.getElementById("errorVendorBankName").style.display = o ? "none" : "inline", o
}

function IsVendorBankBranch(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        o = n > 31 && 33 > n || n >= 48 && 57 >= n || n >= 65 && 90 >= n || n >= 97 && 122 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorVendorBankBranch").innerHTML = "Special Characters not allowed", document.getElementById("errorVendorBankBranch").style.display = o ? "none" : "inline", o
}

function IsVendorBankAC(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        o = n > 31 && 33 > n || n >= 48 && 57 >= n || n >= 65 && 90 >= n || n >= 97 && 122 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorVendorBankAC").innerHTML = "Special Characters not allowed", document.getElementById("errorVendorBankAC").style.display = o ? "none" : "inline", o
}

function IsVendorBankIF(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        o = n > 31 && 33 > n || n >= 48 && 57 >= n || n >= 65 && 90 >= n || n >= 97 && 122 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorVendorBankIF").innerHTML = "Special Characters not allowed", document.getElementById("errorVendorBankIF").style.display = o ? "none" : "inline", o
}

function OnChangeDueThreshold(e) {
    var n = (document.getElementById("renewal_timethreshold"), e.value);
    0 == n ? renewal_timethreshold.value > 30 ? document.getElementById("errorTime").innerHTML = "Maximum allowed 30 day(s)" : document.getElementById("errorTime").innerHTML = "" : 7 == n ? renewal_timethreshold.value > 4 ? document.getElementById("errorTime").innerHTML = "Maximum allowed 4 Week(s)" : document.getElementById("errorTime").innerHTML = "" : 28 == n && (renewal_timethreshold.value > 1 ? document.getElementById("errorTime").innerHTML = "Maximum allowed 1 Month" : document.getElementById("errorTime").innerHTML = "")
}

function renewalReminder() {
    var e = !0,
        n = document.getElementById("renewal_timethreshold");
    return ValidateDue(n) || (e = !1), e
}

function renewalReminderUpdate() {
    var e = !0,
        n = document.getElementById("renewal_timethreshold");
    return ValidateDue(n) || (alert("hi"), e = !1), e
}

function ValidateDue(e) {
    var n = !0,
        o = document.getElementById("renewal_periedthreshold"),
        r = o.value;
    return 0 == r ? e.value > 30 && (document.getElementById("renewal_timethreshold").style.border = "2px solid #F00", e.style.border = "2px solid #F00", document.getElementById("errorTime").innerHTML = "Maximum allowed 30 day(s)", n = !1) : 7 == r ? e.value > 4 && (document.getElementById("errorTime").innerHTML = "Maximum allowed 4 Week(s)", n = !1) : 28 == r && e.value > 1 && (document.getElementById("errorTime").innerHTML = "Maximum allowed 1 Month", n = !1), n
}
function IsVendorTDS(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode;
	var o = (n >= 48 && 57 >= n) || -1 != specialKeys.indexOf(e.keyCode) && ![35, 36, 37, 38].includes(n);

    return document.getElementById("errorVendorTDS").innerHTML = "Alphabets & Special Characters not allowed", document.getElementById("errorVendorTDS").style.display = o ? "none" : "inline", o
}
$(document).ready(function() {
    $("#grp_option").on("change", function() {
        showLabel()
    }), $("#grp_option").change()
}), $(document).ready(function() {
    $(".select2").select2();
    $("#tagPicker").select2({
        closeOnSelect: !1
    }), $(".select3").select2({
        tags: !0,
        placeholder: "Please Select the value ",
        createTag: function(e) {
            return {
                id: e.term,
                text: e.term,
                isNew: !0
            }
        }
    }).on("select3:select", function(e) {
        e.params.data.isNew && $(this).find('[value="' + e.params.data.id + '"]').replaceWith('<option selected value="' + e.params.data.id + '">' + e.params.data.text + "</option>")
    })
}), $(document).ready(function() {
    $("#contactTwo").hide(), $("#Bank").hide()
});
var specialKeys = new Array;
specialKeys.push(8), specialKeys.push(9), specialKeys.push(46), specialKeys.push(36), specialKeys.push(35), specialKeys.push(37), specialKeys.push(39);

$(document).ready(function() {
    function f(a) {
        for (var b = document.getElementsByTagName("textarea"), c = 0; c < b.length; c++) com_satheesh.EVENTS.addEventHandler(b[c], "focus", g, !1), com_satheesh.EVENTS.addEventHandler(b[c], "blur", h, !1);
        b = document.getElementsByTagName("input");
        for (var c = 0; c < b.length; c++) a.indexOf(-1 != b[c].getAttribute("type")) && (com_satheesh.EVENTS.addEventHandler(b[c], "focus", g, !1), com_satheesh.EVENTS.addEventHandler(b[c], "blur", h, !1));
        com_satheesh.EVENTS.addEventHandler(document.getElementById("formVendor"), "submit", p, !1), com_satheesh.EVENTS.addEventHandler(document.getElementById("formEditVendor"), "submit", q, !1), document.getElementsByTagName("input")[0].focus(), com_satheesh.EVENTS.addEventHandler(document.forms[0].vendorName, "blur", i, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].phoneNumber, "blur", j, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].vendorType, "blur", k, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].vendorLocation, "blur", l, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].contactName, "blur", m, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].contactPhone, "blur", o, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].contactName, "blur", n, !1)
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
        var a = document.getElementById("vendorName"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpvendorName");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("vendorNameIcon").className = "fa fa-check form-text-feedback", document.getElementById("vendorNameErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("vendorNameIcon").className = "fa fa-remove form-text-feedback", document.getElementById("vendorNameErrorMsg").innerHTML = "Please enter vendor name"), b
    }

    function j() {
        var a = document.getElementById("phoneNumber"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpphoneNumber");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("phoneNumberIcon").className = "fa fa-check form-text-feedback", document.getElementById("phoneNumberErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("phoneNumberIcon").className = "fa fa-remove form-text-feedback", document.getElementById("phoneNumberErrorMsg").innerHTML = "Please enter vendor phone number "), b
    }

    function k() {
        var a = document.getElementById("vendorType"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpvendorType");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("vendorTypeIcon").className = "fa fa-check  form-text-feedback", document.getElementById("vendorTypeErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("vendorTypeIcon").className = "fa fa-remove form-text-feedback", document.getElementById("vendorTypeErrorMsg").innerHTML = "Please select vendor type ")), b
    }

    function l() {
        var a = document.getElementById("vendorLocation"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpvendorLocation");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("vendorLocationIcon").className = "fa fa-check  form-text-feedback", document.getElementById("vendorLocationErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("vendorLocationIcon").className = "fa fa-remove form-text-feedback", document.getElementById("vendorLocationErrorMsg").innerHTML = "Please enter vendor location ")), b
    }
	 function r() {
		if($("#PanCardNoMandatory").val() == "true")
		{
        var a = document.getElementById("VenPanNo"),
            b = null != a.value && 0 !=  x.test(a.value),
            c = document.getElementById("VendorPanNo");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("vendorPanNoIcon").className = "fa fa-check  form-text-feedback", document.getElementById("vendorPanNoErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("vendorPanNoIcon").className = "fa fa-remove form-text-feedback", document.getElementById("vendorPanNoErrorMsg").innerHTML = "Please enter your valid Pan Card No ")), b
    	}
    	return true;
    }
    
    function s() {
		console.log("inside Validate Pan no function..")
		if($("#TDSPercent").val() == "true")
		{
        var a = document.getElementById("vendorTDS"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("VendorTDSAmount");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("vendorTDSIcon").className = "fa fa-check  form-text-feedback", document.getElementById("vendorTDSErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("vendorTDSIcon").className = "fa fa-remove form-text-feedback", document.getElementById("vendorTDSErrorMsg").innerHTML = "Please enter TDS %  ")), b
    	}
    	return true;
    }
	
    function m() {
    	if($('#validatePrimaryContactName').val() == 'true' || $('#validatePrimaryContactName').val() == true ){
        var a = document.getElementById("contactName"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpcontactName");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("contactNameIcon").className = "fa fa-check  form-text-feedback", document.getElementById("contactNameErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("contactNameIcon").className = "fa fa-remove form-text-feedback", document.getElementById("contactNameErrorMsg").innerHTML = "Please enter vendor Contact name ")), b
    	}else{
    		return true;
    	}
    }

    function n() {
	    	if($('#validatePrimaryContactEmail').val() == 'true' || $('#validatePrimaryContactEmail').val() == true ){
	    		var a = document.getElementById("contactEmail"),
	    		b = 0 != a.value.length && c.test(a.value),
	    		d = document.getElementById("grpcontactEmail");
	    		return null != d && (b ? (d.className = "form-group has-success has-feedback", document.getElementById("contactEmailIcon").className = "fa fa-check form-text-feedback", document.getElementById("contactEmailErrorMsg").innerHTML = "") : (d.className = "form-group  has-feedback", document.getElementById("contactEmailIcon").className = "fa fa-remove form-text-feedback", document.getElementById("contactEmailErrorMsg").innerHTML = "Please enter your valid email id")), b
	    	}else{
	    		return true;
	    	}
    }

    function o() {
    	if($('#validatePrimaryContactNum').val() == 'true' || $('#validatePrimaryContactNum').val() == true ){
        var a = document.getElementById("contactPhone"),
            b = 0 != a.value.length && d.test(a.value),
            c = document.getElementById("grpcontactPhone");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("contactPhoneIcon").className = "fa fa-check  form-text-feedback", document.getElementById("contactPhoneErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("contactPhoneIcon").className = "fa fa-remove form-text-feedback", document.getElementById("contactPhoneErrorMsg").innerHTML = "Please enter your valid mobile number")), b
    	}else{
    		return true;
    	}
    }

    function p(a) {
        var b = i();
        b &= j(), b &= k(), b &= l(), b &= m(), b &= o(), b &= r(), b &= s(), (b &= n()) || com_satheesh.EVENTS.preventDefault(a)
    }

    function q(a) {
        var b = i();
        b &= j(), b &= k(), b &= l(), b &= m(), b &= o(),b &= r(), b &= s(), (b &= n()) || com_satheesh.EVENTS.preventDefault(a)
    }
    var b = "#FFC",
        c = /^[\w\.=-]+\@[\w\.-]+.[a-z]{2,4}$/,
        d = /([0-9]{10})|(\([0-9]{3}\)\s+[0-9]{3}\-[0-9]{4})/;
        x = /^[A-Z]{5}[0-9]{4}[A-Z]{1}$/;
    com_satheesh.EVENTS.addEventHandler(window, "load", function() {
        f("text")
    }, !1)
});

function showHideOwnPetrolPump(){
	if($('#vendorType').val() == 21){
		$('#ownPetrolPumpId').show();
	}else{
		$('#ownPetrolPumpId').hide();
	}
}

var validateEmail = function(elementValue) {
    var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
    return emailPattern.test(elementValue);
}



$('#contactEmail').keyup(function() {

    var value = $(this).val();
    var valid = validateEmail(value);

    if (!valid) {


        $(this).css('color', 'red');

    } else {


        $(this).css('color', '#000');

    }



});
var validatePanNo = function(elementValue) {
    var emailPattern = /^[A-Z]{5}[0-9]{4}[A-Z]{1}$/;
    return emailPattern.test(elementValue);
}

$('#VenPanNo').keyup(function (){
	
	if($("#PanCardNoMandatory").val() == "true")
	{
	var value = $(this).val();
    var valid = validatePanNo(value);

    if (!valid) {


        $(this).css('color', 'red');

    } else {


        $(this).css('color', 'green');

    }
    }
});


