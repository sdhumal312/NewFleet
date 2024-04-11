function IsAlphaNumericFristName(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = r > 31 && 33 > r || r > 44 && 48 > r || r >= 65 && 90 >= r || r >= 97 && 122 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorFristName").innerHTML = "Special Characters & Number not allowed", document.getElementById("errorFristName").style.display = n ? "none" : "inline", n
}

function IsAlphaNumericLastName(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = r > 31 && 33 > r || r > 44 && 48 > r || r >= 65 && 90 >= r || r >= 97 && 122 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorLastName").innerHTML = "Special Characters & Number not allowed", document.getElementById("errorLastName").style.display = n ? "none" : "inline", n
}

function IsAlphaNumericFatherName(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = r > 31 && 33 > r || r > 44 && 48 > r || r >= 65 && 90 >= r || r >= 97 && 122 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorFatherName").innerHTML = "Special Characters & Number not allowed", document.getElementById("errorFatherName").style.display = n ? "none" : "inline", n
}

function IsAlphaNumericQualification(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = r > 31 && 33 > r || r > 42 && 44 > r || r > 44 && 48 > r || r >= 48 && 57 >= r || r >= 65 && 90 >= r || r >= 97 && 122 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorQualification").innerHTML = "Special Characters not allowed", document.getElementById("errorQualification").style.display = n ? "none" : "inline", n
}

function IsAlphaNumericEmail(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = r > 31 && 33 > r || r > 44 && 47 > r || r >= 48 && 57 >= r || r >= 64 && 90 >= r || r > 94 && 96 > r || r >= 97 && 122 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorEmail").innerHTML = "Special Characters not allowed", document.getElementById("errorEmail").style.display = n ? "none" : "inline", n
}

function isNumberKey(e, t) {
    var n = e.which ? e.which : event.keyCode;
    if (n > 31 && (48 > n || n > 57) && 46 != n && 8 != charcode) return !1;
    var r = $(t).val().length,
        o = $(t).val().indexOf(".");
    if (o > 0 && 46 == n) return !1;
    if (o > 0) {
        var i = r + 1 - o;
        if (i > 3) return !1
    }
    return !0
}

function IsNumericMob(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = r >= 48 && 57 >= r || r > 42 && 44 > r || r > 44 && 46 > r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorMob").innerHTML = "Alphabetical Characters not allowed", document.getElementById("errorMob").style.display = n ? "none" : "inline", n
}

function IsNumericHome(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = r >= 48 && 57 >= r || r > 42 && 44 > r || r > 44 && 46 > r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorHome").innerHTML = "Alphabetical Characters not allowed", document.getElementById("errorHome").style.display = n ? "none" : "inline", n
}

function IsNumericWork(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = r >= 48 && 57 >= r || r > 42 && 44 > r || r > 44 && 46 > r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorWork").innerHTML = "Alphabetical Characters not allowed", document.getElementById("errorWork").style.display = n ? "none" : "inline", n
}

function IsAlphaNumericAddress(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = r > 31 && 33 > r || r >= 43 && 57 >= r || r >= 64 && 90 >= r || r > 94 && 96 > r || r >= 97 && 122 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorAddress").innerHTML = "Special Characters not allowed", document.getElementById("errorAddress").style.display = n ? "none" : "inline", n
}

function IsAlphaNumericAddress2(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = r > 31 && 33 > r || r >= 43 && 57 >= r || r >= 64 && 90 >= r || r > 94 && 96 > r || r >= 97 && 122 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorAddress2").innerHTML = "Special Characters not allowed", document.getElementById("errorAddress2").style.display = n ? "none" : "inline", n
}

function IsAlphaNumericCity(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = r > 31 && 33 > r || r >= 65 && 90 >= r || r >= 97 && 122 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorCity").innerHTML = "Special Characters & Number not allowed", document.getElementById("errorCity").style.display = n ? "none" : "inline", n
}

function IsAlphaNumericState(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = r > 31 && 33 > r || r >= 65 && 90 >= r || r >= 97 && 122 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorState").innerHTML = "Special Characters & Number not allowed", document.getElementById("errorState").style.display = n ? "none" : "inline", n
}

function IsNumericPin(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = r >= 48 && 57 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorPin").innerHTML = "Alphabetical Characters not allowed", document.getElementById("errorPin").style.display = n ? "none" : "inline", n
}

function IsAlphaNumericCountry(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = r > 31 && 33 > r || r >= 65 && 90 >= r || r >= 97 && 122 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorCountry").innerHTML = "Special Characters not allowed", document.getElementById("errorCountry").style.display = n ? "none" : "inline", n
}

function IsAlphaNumericEmp(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = r > 31 && 32 > r || r > 44 && 46 > r || r >= 48 && 57 >= r || r >= 65 && 90 >= r || r >= 97 && 122 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorEmp").innerHTML = "Special Characters not allowed", document.getElementById("errorEmp").style.display = n ? "none" : "inline", n
}

function IsInsuranceNumber(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = r > 31 && 33 > r || r > 44 && 46 > r || r >= 48 && 57 >= r || r >= 65 && 90 >= r || r >= 97 && 122 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorInsurance").innerHTML = "Special Characters not allowed", document.getElementById("errorInsurance").style.display = n ? "none" : "inline", n
}

function IsESINumber(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = r > 31 && 33 > r || r > 44 && 46 > r || r >= 48 && 57 >= r || r >= 65 && 90 >= r || r >= 97 && 122 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorESI").innerHTML = "Special Characters not allowed", document.getElementById("errorESI").style.display = n ? "none" : "inline", n
}

function IsPFNumber(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = r > 31 && 33 > r || r > 44 && 46 > r || r >= 48 && 57 >= r || r >= 65 && 90 >= r || r >= 97 && 122 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorPF").innerHTML = "Special Characters not allowed", document.getElementById("errorPF").style.display = n ? "none" : "inline", n
}

function IsAlphaNumericJob(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = r > 31 && 33 > r || r >= 48 && 57 >= r || r >= 65 && 90 >= r || r >= 97 && 122 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorJob").innerHTML = "Special Characters not allowed", document.getElementById("errorJob").style.display = n ? "none" : "inline", n
}

function IsTrainings(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = r > 31 && 33 > r || r > 43 && 45 > r || r >= 48 && 57 >= r || r >= 65 && 90 >= r || r >= 97 && 122 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorTrainings").innerHTML = "Special Characters not allowed", document.getElementById("errorTrainings").style.display = n ? "none" : "inline", n
}

function IsAlphaNumericDl(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = r > 31 && 33 > r || r >= 45 && 57 >= r || r >= 65 && 90 >= r || r >= 97 && 122 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorDl").innerHTML = "Special Characters not allowed", document.getElementById("errorDl").style.display = n ? "none" : "inline", n
}

function IsAlphaNumericBadge(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = r > 31 && 33 > r || r >= 45 && 57 >= r || r >= 65 && 90 >= r || r >= 97 && 122 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorBadge").innerHTML = "Special Characters not allowed", document.getElementById("errorBadge").style.display = n ? "none" : "inline", n
}

function IsAlphaNumericClass(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = r > 31 && 33 > r || r >= 44 && 45 >= r || r >= 65 && 90 >= r || r >= 97 && 122 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorClass").innerHTML = "Special Characters & Number not allowed", document.getElementById("errorClass").style.display = n ? "none" : "inline", n
}

function IsAlphaNumericDlprovince(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = r > 31 && 33 > r || r >= 65 && 90 >= r || r >= 97 && 122 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorDlprovince").innerHTML = "Special Characters & Number not allowed", document.getElementById("errorDlprovince").style.display = n ? "none" : "inline", n
}

function IsAlphaNumericAuthorise(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = r > 31 && 33 > r || r >= 44 && 45 >= r || r >= 65 && 90 >= r || r >= 97 && 122 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorAuthorise").innerHTML = "Special Characters & Number not allowed", document.getElementById("errorAuthorise").style.display = n ? "none" : "inline", n
}

function IsAlphaNumericOrinalRemarls(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = r > 31 && 33 > r || r >= 44 && 57 >= r || r >= 65 && 90 >= r || r >= 97 && 122 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorOrinalRemarls").innerHTML = "Special Characters & Number not allowed", document.getElementById("errorOrinalRemarls").style.display = n ? "none" : "inline", n
}

function IsAlphaNumericRFrist(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = r > 31 && 33 > r || r >= 65 && 90 >= r || r >= 97 && 122 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorRFrist").innerHTML = "Special Characters & Number not allowed", document.getElementById("errorRFrist").style.display = n ? "none" : "inline", n
}

function IsAlphaNumericRLast(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = r > 31 && 33 > r || r >= 65 && 90 >= r || r >= 97 && 122 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorRLast").innerHTML = "Special Characters & Number not allowed", document.getElementById("errorRLast").style.display = n ? "none" : "inline", n
}

function IsNumericContent(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = r >= 48 && 57 >= r || r > 42 && 44 > r || r > 44 && 46 > r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorContent").innerHTML = "Alphabetical Characters not allowed", document.getElementById("errorContent").style.display = n ? "none" : "inline", n
}

function validateDriverSave() {
    var e = !0,
        r = document.getElementById("driver_dateofbirth"),
        n = document.getElementById("driver_email");
    return OnSelectionDate(r) ? "" != n.value && (ValidateEmail(n) || (e = !1)) : e = !1, e
}

function validateDriverUpdate() {
    var e = !0,
        r = document.getElementById("driver_dateofbirth"),
        n = document.getElementById("driver_email");
    return alret(r.value), alret(n.value), OnSelectionDate(r) ? "" != n.value && (ValidateEmail(n) || (e = !1)) : e = !1, e
}

function OnSelectionDate(e) {
    var r = !0,
        n = e.value,
        o = n.split("-"),
        t = o[0],
        d = o[1],
        a = o[2],
        l = 18,
        i = new Date;
    i.setFullYear(a, d - 1, t);
    var y = new Date,
        c = new Date;
    return c.setFullYear(i.getFullYear() + l, d - 1, t), 0 > y - c && (document.getElementById("driver_dateofbirth").style.border = "2px solid #F00", e.style.border = "2px solid #F00", document.getElementById("errorDateofbirth").innerHTML = "Below 18 years not allowed", alert("Below 18 years not allowed"), r = !1), r
}

function ValidateEmail(e) {
    var r = !0,
        n = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    return n.test(e.value) || (document.getElementById("driver_email").style.border = "2px solid #F00", e.style.border = "2px solid #F00", document.getElementById("errorEmailValidate").innerHTML = "You have entered  invalid email address!", e.focus, r = !1), r
}

function validateMandatory(e) {
    var r = !0;
    return (null == e || "" == e) && (vStatus.style.border = "2px solid #F00", errorvStatus.innerHTML = "Please Enter the Value", r = !1), r
}
var specialKeys = new Array;
specialKeys.push(8), specialKeys.push(9), specialKeys.push(46), specialKeys.push(36), specialKeys.push(35), specialKeys.push(37), specialKeys.push(39), $(document).ready(function() {
    $("#datemask").inputmask("dd-mm-yyyy", {
        placeholder: "dd-mm-yyyy"
    }), $("[data-mask]").inputmask()
});

$(document).ready(function() {
    function f(a) {
        for (var b = document.getElementsByTagName("textarea"), c = 0; c < b.length; c++) com_satheesh.EVENTS.addEventHandler(b[c], "focus", g, !1), com_satheesh.EVENTS.addEventHandler(b[c], "blur", h, !1);
        b = document.getElementsByTagName("input");
        for (var c = 0; c < b.length; c++) a.indexOf(b[c].getAttribute("type") != -1) && (com_satheesh.EVENTS.addEventHandler(b[c], "focus", g, !1), com_satheesh.EVENTS.addEventHandler(b[c], "blur", h, !1));
        com_satheesh.EVENTS.addEventHandler(document.getElementById("formDriver"), "submit", p, !1), document.getElementsByTagName("input")[0].focus(), com_satheesh.EVENTS.addEventHandler(document.forms[0].driverfirstname, "blur", i, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].driver_dateofbirth, "blur", j, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].mobNumber, "blur", k, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].empNumber, "blur", l, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].joinDate, "blur", m, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].dlNumber, "blur", n, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].aadharNumber, "blur", o, !1)
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
        var a = document.getElementById("driverfirstname"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpdriverName");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("driverNameIcon").className = "fa fa-check form-text-feedback", document.getElementById("driverNameErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("driverNameIcon").className = "fa fa-remove form-text-feedback", document.getElementById("driverNameErrorMsg").innerHTML = "Please enter driver name"), b
    }

    function j() {
    	
    	var showOriginalDLRemark = $('#showOriginalDLRemark').val();
    	if(showOriginalDLRemark == false || showOriginalDLRemark == 'false'){
    		return true;
    	}
    	
    	 	var a = document.getElementById("driver_dateofbirth"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpbirthDay");
    	 	 
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("birthDayIcon").className = "fa fa-check form-text-feedback", document.getElementById("birthDayErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("birthDayIcon").className = "fa fa-remove form-text-feedback", document.getElementById("birthDayErrorMsg").innerHTML = "Please select driver birth date"), b
    }

    function k() {
        var a = document.getElementById("mobNumber"),
            b = 0 != a.value.length && d.test(a.value),
            c = document.getElementById("grpmobileNumber");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("mobNumberIcon").className = "fa fa-check  form-text-feedback", document.getElementById("mobNumberErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("mobNumberIcon").className = "fa fa-remove form-text-feedback", document.getElementById("mobNumberErrorMsg").innerHTML = "Please enter driver valid mobile number")), b
    }

    function l() {
        var a = document.getElementById("empNumber"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpempNumber");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("empNumberIcon").className = "fa fa-check form-text-feedback", document.getElementById("empNumberErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("empNumberIcon").className = "fa fa-remove form-text-feedback", document.getElementById("empNumberErrorMsg").innerHTML = "Please enter employee number"), b
    }

    function m() {
    	
    	var showOriginalDLRemark = $('#showOriginalDLRemark').val();
    	if(showOriginalDLRemark == false || showOriginalDLRemark == 'false'){
    		return true;
    	}
        var a = document.getElementById("joinDate"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpjoinDate");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("joinDateIcon").className = "fa fa-check form-text-feedback", document.getElementById("joinDateErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("joinDateIcon").className = "fa fa-remove form-text-feedback", document.getElementById("joinDateErrorMsg").innerHTML = "Please select driver start date"), b
    }

    function n() {
    	var showOriginalDLRemark = $('#showOriginalDLRemark').val();
    	if(showOriginalDLRemark == false || showOriginalDLRemark == 'false'){
    		return true;
    	}
        var a = document.getElementById("dlNumber"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpdlNumber");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("dlNumberIcon").className = "fa fa-check form-text-feedback", document.getElementById("dlNumberErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("dlNumberIcon").className = "fa fa-remove form-text-feedback", document.getElementById("dlNumberErrorMsg").innerHTML = "Please enter dl number"), b
    }

    function o() {
    	
    	var showOriginalDLRemark = $('#showOriginalDLRemark').val();
    	if(showOriginalDLRemark == false || showOriginalDLRemark == 'false'){
    		return true;
    	}
        var a = document.getElementById("aadharNumber"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpaadharNumber");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("aadharNumberIcon").className = "fa fa-check form-text-feedback", document.getElementById("aadharNumberErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("aadharNumberIcon").className = "fa fa-remove form-text-feedback", document.getElementById("aadharNumberErrorMsg").innerHTML = "Please enter aadhar number"), b
    }

    function p(a) {
        var b = i();
        b &= j(), b &= k(), b &= l(), b &= m(), b &= n(), b &= o(), b || com_satheesh.EVENTS.preventDefault(a)
    }
    var b = "#FFC",
        d = /^\d{10}$/;
    com_satheesh.EVENTS.addEventHandler(window, "load", function() {
        f("text")
    }, !1)
});


function IsNumericTimeThru(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = t >= 48 && 57 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorTimeThru").innerHTML = "Alpha Characters not allowed", document.getElementById("errorTimeThru").style.display = n ? "none" : "inline", n
}

function validateRenewal(){// This validation for Renewal Document And Renewal Date While Creating Driver.
	if($('#renewalTypeConfigID').val() == 'true'){
		$('.tab-pane').each((i,obj) =>  $(obj).removeClass('active') );
		$('#DlInfo').addClass('active');
		
		/*if($('#DriverReminderdate').val() == "" && $('#renewalDocId').val() == ""){
			return true;
		} else{
			if($('#DriverReminderdate').val() == ""){
				$('#DriverReminderdate').focus();
				showMessage('info','Please Select Date');
				return false;
			}
			if($('#renewalDocId').val() == ""){
				
				$('#renewalDocId').focus();
				showMessage('info','Please Upload Document');
				return false;
			}
		}*/
		
	}
	if($('#showVehicle').val() == 'true' || $('#showVehicle').val() == true){
		if(Number($('#vid').val()) <= 0){
			showMessage('info', 'Please Select Vehicle !');
			$('#vid').focus();
			return false;
		}
	}
	
	return true;
}


$(document).ready(function() {
	$("#SelectVehicle").select2( {
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
    }
    )
});
$(document).ready(function() {// This is configuration part (Fields Visible For Perticular Groups)
var renewalTypeConfigID = $('#renewalTypeConfigID').val();// all These Are Yml Configuration Values (True/False)
var validityConfigID 	= $('#validityConfigID').val();
var thresholdConfigID 	= $('#thresholdConfigID').val();
var docTypeConfigID	 	= $('#docTypeConfigID').val();

if(renewalTypeConfigID =='true' && validityConfigID =='true' && thresholdConfigID =='true' && docTypeConfigID == 'true' ){
	$('#renewalTypeId').removeClass('hide'); 
	$('#validityId').removeClass('hide'); 
	$('#thresholdId').removeClass('hide'); 
	$('#docTypeId').removeClass('hide'); 
}
$("#vid").select2({
    minimumInputLength: 3,
    minimumResultsForSearch: 10,
    ajax: {
        url: "getVehicleSearchServiceEntrie.in?Action=FuncionarioSelect2",
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
                        text: a.vehicle_registration, slug: a.slug, id: a.vid
                        }
                })
            }
        }
    }
});
});
