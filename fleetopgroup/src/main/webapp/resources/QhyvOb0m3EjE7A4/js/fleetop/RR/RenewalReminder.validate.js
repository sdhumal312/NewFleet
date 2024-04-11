function IsAlphaNumeric(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        t = n > 31 && 33 > n || n > 44 && 48 > n || n >= 48 && 57 >= n || n >= 65 && 90 >= n || n >= 97 && 122 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("error").innerHTML = "Special Characters not allowed", document.getElementById("error").style.display = t ? "none" : "inline", t
}

function IsNumeric(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        t = n >= 48 && 57 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorAmount").innerHTML = "Alphabets & Special Characters not allowed", document.getElementById("errorAmount").style.display = t ? "none" : "inline", t
}

function IsAlphaNumericPaynumber(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        t = n > 31 && 33 > n || n > 44 && 48 > n || n >= 48 && 57 >= n || n >= 65 && 90 >= n || n >= 97 && 122 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorPaynumber").innerHTML = "Special Characters not allowed", document.getElementById("errorPaynumber").style.display = t ? "none" : "inline", t
}

function IsAlphaNumericPaidby(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        t = n > 31 && 33 > n || n > 44 && 48 > n || n >= 65 && 90 >= n || n >= 97 && 122 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorPaidby").innerHTML = "Special Characters not allowed", document.getElementById("errorPaidby").style.display = t ? "none" : "inline", t
}

function IsAlphaNumericAuthor(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        t = n > 31 && 33 > n || n > 44 && 48 > n || n >= 48 && 57 >= n || n >= 65 && 90 >= n || n >= 97 && 122 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorAuthor").innerHTML = "Special Characters not allowed", document.getElementById("errorAuthor").style.display = t ? "none" : "inline", t
}

function IsAlphaNumericNumber(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        t = n > 31 && 33 > n || n > 44 && 48 > n || n >= 48 && 57 >= n || n >= 65 && 90 >= n || n >= 97 && 122 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorNumber").innerHTML = "Special Characters not allowed", document.getElementById("errorNumber").style.display = t ? "none" : "inline", t
}

function IsNumericTimeThru(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        t = n >= 48 && 57 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorTimeThru").innerHTML = "Alphabet Characters not allowed", document.getElementById("errorTimeThru").style.display = t ? "none" : "inline", t
}

function OnChangeDueThreshold(e) {
    var n = (document.getElementById("renewal_timethreshold"), e.value);
    0 == n ? renewal_timethreshold.value > 30 ? document.getElementById("errorTime").innerHTML = "Maximum allowed 30 day(s)" : document.getElementById("errorTime").innerHTML = "" : 7 == n ? renewal_timethreshold.value > 4 ? document.getElementById("errorTime").innerHTML = "Maximum allowed 4 Week(s)" : document.getElementById("errorTime").innerHTML = "" : 28 == n && (renewal_timethreshold.value > 1 ? document.getElementById("errorTime").innerHTML = "Maximum allowed 1 Month" : document.getElementById("errorTime").innerHTML = "")
}

function renewalReminder() {
    var e = !0;
    return ValidateDue(document.getElementById("renewal_timethreshold")) || (e = !1), e
}

function renewalReminderUpdate() {
    var e = !0;
    return ValidateDue(document.getElementById("renewal_timethreshold")) || (alert("hi"), e = !1), e
}

function ValidateDue(e) {
    var n = !0,
        t = document.getElementById("renewal_periedthreshold").value;
    return 0 == t ? e.value > 30 && (document.getElementById("renewal_timethreshold").style.border = "2px solid #F00", e.style.border = "2px solid #F00", document.getElementById("errorTime").innerHTML = "Maximum allowed 30 day(s)", n = !1) : 7 == t ? e.value > 4 && (document.getElementById("errorTime").innerHTML = "Maximum allowed 4 Week(s)", n = !1) : 28 == t && e.value > 1 && (document.getElementById("errorTime").innerHTML = "Maximum allowed 1 Month", n = !1), n
}
var specialKeys = new Array;
specialKeys.push(8), specialKeys.push(9), specialKeys.push(46), specialKeys.push(36), specialKeys.push(35), specialKeys.push(37), specialKeys.push(39), $(document).ready(function() {
    $("#datemask").inputmask("dd-mm-yyyy", {
        placeholder: "dd-mm-yyyy"
    }), $("[data-mask]").inputmask()
}), $(document).ready(function() {
    $(".select2").select2(), $("#tagPicker").select2({
        closeOnSelect: !1
    })
}), $(document).ready(function() {
    $("#RenewalSelectVehicle").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getVehicleSearchServiceEntrie.in?Action=FuncionarioSelect2",
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
    }),$("#vendorId").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getOtherVendorSearchList.in?Action=FuncionarioSelect2",
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
                            text: e.vendorName+' : '+e.vendorLocation,
                            slug: e.slug,
                            id: e.vendorId
                        }
                    })
                }
            }
        }
    }), $("#from").select2({
        ajax: {
            url: "getRenewalType.in?Action=FuncionarioSelect2",
            dataType: "json",
            type: "GET",
            contentType: "application/json",
            data: function(e) {
                return {
                    term: e
                }
            },
            results: function(e) {
                return {
                    results: $.map(e, function(e) {
                        return {
                            text: e.renewal_Type,
                            slug: e.slug,
                            id: e.renewal_id
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
    })
}), $(document).ready(function() {
    $("#from").change(function() {
    	getRenewalSubTypeListById($(this).val(), false, 0);
    })
});

function getRenewalSubTypeListById(renewalTypeId, fromEdit, renewalSubTypeId){
	
	if(!fromEdit){
		$('#to').select2("val", "");
	}
    $.getJSON("getRenewalSubTypeById.in", {
        RenewalType: renewalTypeId,
        ajax: "true"
    }, function(e) {
    	
    	var t = '';
    	if(!fromEdit){
    		t = '<option value="0">Please Select</option>';
    	}else{
    		var data = $('#to').select2('data');
    		if(data) {
    			t = '<option value="'+data.id+'">'+data.text+'</option>';
    		}
    	}
    	
    	for (var n = 0, t = e.length, r = 0; t > r; r++) n += '<option value="' + e[r].renewal_Subid + '">' + e[r].renewal_SubType + "</option>";
        n += "</option>", $("#to").html(n)
        
        if(renewalSubTypeId > 0){
        	$('#to').val(renewalSubTypeId).trigger("change");
        }
        
        $('#to').select2();
    })
    
    return true;
}
	
	$(document).ready(function() {
    function e(e) {
        for (var h = document.getElementsByTagName("textarea"), g = 0; g < h.length; g++) com_satheesh.EVENTS.addEventHandler(h[g], "focus", n, !1), com_satheesh.EVENTS.addEventHandler(h[g], "blur", t, !1);
        for (h = document.getElementsByTagName("input"), g = 0; g < h.length; g++) e.indexOf(-1 != h[g].getAttribute("type")) && (com_satheesh.EVENTS.addEventHandler(h[g], "focus", n, !1), com_satheesh.EVENTS.addEventHandler(h[g], "blur", t, !1));
        com_satheesh.EVENTS.addEventHandler(document.getElementById("formRenewal"), "submit", i, !1), com_satheesh.EVENTS.addEventHandler(document.getElementById("formApprovalRenewalAdd"), "submit", y, !1), com_satheesh.EVENTS.addEventHandler(document.getElementById("formApprovalRenewalUpload"), "submit", f, !1), document.getElementsByTagName("input")[0].focus(), com_satheesh.EVENTS.addEventHandler(document.forms[0].RenewalSelectVehicle, "blur", r, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].from, "blur", a, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].to, "blur", l, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].reservation, "blur", o, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].renewal_timethreshold, "blur", d, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].renewalReceipt, "blur", m, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].renewalAmount, "blur", c, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].renewalFile, "blur", s, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].renewalpaidDate, "blur", u, !1)
    }

    function n(e) {
        var n = com_satheesh.EVENTS.getEventTarget(e);
        null != n && (n.style.backgroundColor = h)
    }

    function t(e) {
        var n = com_satheesh.EVENTS.getEventTarget(e);
        null != n && (n.style.backgroundColor = "")
    }

    function r() {
        var e = document.getElementById("RenewalSelectVehicle"),
            n = null != e.value && 0 != e.value.length,
            t = document.getElementById("grpvehicleNumber");
        if (null != t) return n ? (t.className = "form-group has-success has-feedback", document.getElementById("vehicleNumberIcon").className = "fa fa-check form-text-feedback", document.getElementById("vehicleNumberErrorMsg").innerHTML = "") : (t.className = "form-group has-error has-feedback", document.getElementById("vehicleNumberIcon").className = "fa fa-remove form-text-feedback", document.getElementById("vehicleNumberErrorMsg").innerHTML = "Please select vehicle number"), n
    }

    function a() {
        var e = document.getElementById("from"),
            n = null != e.value && 0 != e.value.length,
            t = document.getElementById("grprenewalType");
        if (null != t) return n ? (t.className = "form-group has-success has-feedback", document.getElementById("renewalTypeIcon").className = "fa fa-check form-text-feedback", document.getElementById("renewalTypeErrorMsg").innerHTML = "") : (t.className = "form-group has-error has-feedback", document.getElementById("renewalTypeIcon").className = "fa fa-remove form-text-feedback", document.getElementById("renewalTypeErrorMsg").innerHTML = "Please select renewal Type "), n
    }

    function l() {
        var e = document.getElementById("to"),
            n = null != e.value && 0 != e.value.length,
            t = document.getElementById("grprenewalSubType");

		if (e.value == 0){
        	n = false;
        }

        return null != t && (n ? (t.className = "form-group has-success has-feedback", document.getElementById("renewalSubTypeIcon").className = "fa fa-check  form-text-feedback", document.getElementById("renewalSubTypeErrorMsg").innerHTML = "") : (t.className = "form-group has-error has-feedback", document.getElementById("renewalSubTypeIcon").className = "fa fa-remove form-text-feedback", document.getElementById("renewalSubTypeErrorMsg").innerHTML = "Please select renewal sub type")), n
    }

    function o() {
        var e = document.getElementById("reservation"),
            n = null != e.value && 0 != e.value.length,
            t = document.getElementById("grprenewalDate");
        if (null != t) return n ? (t.className = "form-group has-success has-feedback", document.getElementById("renewalDateIcon").className = "fa fa-check form-text-feedback", document.getElementById("renewalDateErrorMsg").innerHTML = "") : (t.className = "form-group has-error has-feedback", document.getElementById("renewalDateIcon").className = "fa fa-remove form-text-feedback", document.getElementById("renewalDateErrorMsg").innerHTML = "Please  select renewal from & to date"), n
    }

    function d() {
        var e = document.getElementById("renewal_timethreshold"),
            n = null != e.value && 0 != e.value.length,
            t = document.getElementById("grprenewalTime");
        if (null != t) return n ? (t.className = "form-group has-success has-feedback", document.getElementById("renewalTimeIcon").className = "fa fa-check form-text-feedback", document.getElementById("renewalTimeErrorMsg").innerHTML = "") : (t.className = "form-group has-error has-feedback", document.getElementById("renewalTimeIcon").className = "fa fa-remove form-text-feedback", document.getElementById("renewalTimeErrorMsg").innerHTML = "Please enter due threshold time"), n
    }

    function m() {
        var e = document.getElementById("renewalReceipt"),
            n = null != e.value && 0 != e.value.length,
            t = document.getElementById("grprenewalReceipt");
        if (null != t) return n ? (t.className = "form-group has-success has-feedback", document.getElementById("renewalReceiptIcon").className = "fa fa-check form-text-feedback", document.getElementById("renewalReceiptErrorMsg").innerHTML = "") : (t.className = "form-group has-error has-feedback", document.getElementById("renewalReceiptIcon").className = "fa fa-remove form-text-feedback", document.getElementById("renewalReceiptErrorMsg").innerHTML = "Please enter Receipt Or Challan number"), n
    }

    function c() {
        var e = document.getElementById("renewalAmount"),
            n = null != e.value && 0 != e.value.length,
            t = document.getElementById("grprenewalAmount");
        if (null != t) return n ? (t.className = "form-group has-success has-feedback", document.getElementById("renewalAmountIcon").className = "fa fa-check form-text-feedback", document.getElementById("renewalAmountErrorMsg").innerHTML = "") : (t.className = "form-group has-error has-feedback", document.getElementById("renewalAmountIcon").className = "fa fa-remove form-text-feedback", document.getElementById("renewalAmountErrorMsg").innerHTML = "Please enter renewal amount "), n
    }

    function s() {
    	var saveRenewalWithoutFile = $('#saveRenewalWithoutFile').val();
    	if(saveRenewalWithoutFile == 'true' || saveRenewalWithoutFile == true)
    		return true;
    	
        var e = document.getElementById("renewalFile"),
            n = null != e.value && 0 != e.value.length,
            t = document.getElementById("grprenewalFile");
        if (null != t) return n ? (t.className = "form-group has-success has-feedback", document.getElementById("renewalFileIcon").className = "fa fa-check form-text-feedback", document.getElementById("renewalFileErrorMsg").innerHTML = "") : (t.className = "form-group has-error has-feedback", document.getElementById("renewalFileIcon").className = "fa fa-remove form-text-feedback", document.getElementById("renewalFileErrorMsg").innerHTML = "Please upload renewal file image jpg or pdf "), n
    }

    function u() {
        var e = document.getElementById("renewalpaidDate"),
            n = null != e.value && 0 != e.value.length,
            t = document.getElementById("grppaidDate");
        if (null != t) return n ? (t.className = "form-group has-success has-feedback", document.getElementById("paidDateIcon").className = "fa fa-check form-text-feedback", document.getElementById("paidDateErrorMsg").innerHTML = "") : (t.className = "form-group has-error has-feedback", document.getElementById("paidDateIcon").className = "fa fa-remove form-text-feedback", document.getElementById("paidDateErrorMsg").innerHTML = "Please select renewal paid date "), n
    }

    function i(e) {
        var n = r();
        n &= a(), n &= l(), n &= o(), n &= d(), n &= m(), n &= c(), n &= s(), (n &= u()) || com_satheesh.EVENTS.preventDefault(e)
    }

    function y(e) {
        var n = r();
        n &= a(), n &= l(), n &= o(), n &= d(), (n &= c()) || com_satheesh.EVENTS.preventDefault(e)
    }

    function f(e) {
        var n = o();
        n &= m(), n &= c(), (n &= s()) || com_satheesh.EVENTS.preventDefault(e)
    }
    var h = "#FFC";
    com_satheesh.EVENTS.addEventHandler(window, "load", function() {
        e("text")
    }, !1)
});

function validateRenewalSave(){
	// 2 = credit
	if($('#showVendorCol').val() == 'true' || $('#showVendorCol').val() == true){
		if(Number($('#renPT_option').val()) == 2 && Number($('#vendorId').val()) <= 0){
			showMessage('info', 'Please select vendor !');
			return false;
		}
	}
	if($('#tallyIntegrationRequired').val() == 'true' || $('#tallyIntegrationRequired').val() == true){
		if(Number($('#tallyCompanyId').val()) <= 0){
			$("#tallyCompanyId").select2('focus');
			showMessage('info', 'Please Tally Company !');
			return false;
		}
		if($('#renewalpaidDate').val() == undefined || $('#renewalpaidDate').val() == null || $('#renewalpaidDate').val().trim() == ''){
			$("#renewalpaidDate").select2('focus');
			showMessage('info', 'Please Select Paid Date !');
			return false;
		}
	}
	return true;
}

function dateRangeValidation(){
	var thresholdperiod= 0;
		var dateRange =$('#reservation').val();
		var threshold=$('#renewal_timethreshold').val();
		var period=$('#renewal_periedthreshold').val();
		if(period == '0' || period == 0){
			 thresholdperiod=threshold ;
		}else{
			 thresholdperiod=threshold * period;
		}
		
		var datew=dateRange.split(" to ");
		var to = dateFormat(datew[1]);
		var from =dateFormat(datew[0]);
		var diffdays= days_between(from,to);
		
		if(thresholdperiod > diffdays){
			showMessage('info',"Due Threshold can't be more than validity period");
			return false;
		}
	return true;
		}




function days_between(date1, date2) {
    const ONE_DAY = 1000 * 60 * 60 * 24;
    const differenceMs = Math.abs(date1 - date2);
    return Math.round(differenceMs / ONE_DAY);
}

function dateFormat(dateStr) {
	  var parts = dateStr.split("-")
	  return new Date(parts[2], parts[1] - 1, parts[0])
	}
