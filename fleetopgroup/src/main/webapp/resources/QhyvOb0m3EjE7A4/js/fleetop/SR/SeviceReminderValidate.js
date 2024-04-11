function Ismeter_interval(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = t >= 48 && 57 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errormeter_interval").innerHTML = "Alphabets & Special Characters not allowed", document.getElementById("errormeter_interval").style.display = n ? "none" : "inline", n
}

function Istime_interval(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = t >= 48 && 57 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errortime_interval").innerHTML = "Alphabets & Special Characters not allowed", document.getElementById("errortime_interval").style.display = n ? "none" : "inline", n
}

function Ismeter_threshold(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = t >= 48 && 57 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errormeter_threshold").innerHTML = "Alphabets & Special Characters not allowed", document.getElementById("errormeter_threshold").style.display = n ? "none" : "inline", n
}

function Istime_threshold(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = t >= 48 && 57 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errortime_threshold").innerHTML = "Alphabets & Special Characters not allowed", document.getElementById("errortime_threshold").style.display = n ? "none" : "inline", n
}

function Isservice_subscribeduser(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = t > 31 && 33 > t || t > 44 && 48 > t || t >= 48 && 57 >= t || t >= 64 && 90 >= t || t >= 97 && 122 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorservice_subscribeduser").innerHTML = "Special Characters not allowed", document.getElementById("errorservice_subscribeduser").style.display = n ? "none" : "inline", n
}

function OnChangeDueThreshold(e) {
    var t = (document.getElementById("renewal_timethreshold"), e.value);
    0 == t ? renewal_timethreshold.value > 30 ? document.getElementById("errorTime").innerHTML = "Maximum allowed 30 day(s)" : document.getElementById("errorTime").innerHTML = "" : 7 == t ? renewal_timethreshold.value > 4 ? document.getElementById("errorTime").innerHTML = "Maximum allowed 4 Week(s)" : document.getElementById("errorTime").innerHTML = "" : 28 == t && (renewal_timethreshold.value > 1 ? document.getElementById("errorTime").innerHTML = "Maximum allowed 1 Month" : document.getElementById("errorTime").innerHTML = "")
}
var specialKeys = new Array;
specialKeys.push(8), specialKeys.push(9), specialKeys.push(46), specialKeys.push(36), specialKeys.push(35), specialKeys.push(37), specialKeys.push(39), $(document).ready(function() {
    $(".select2").select2(), $("#tagPicker").select2({
        closeOnSelect: !1
    })
}), $(document).ready(function() {
	
    $("#ServiceSelectVehicle").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        multiple: !0,
        ajax: {
            url: "getVehicleListService.in?Action=FuncionarioSelect2",
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
    }), $("#ServiceReportVehicle, #vid").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getVehicleListService.in?Action=FuncionarioSelect2",
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
    }), $("#from").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getJobTypeService.in?Action=FuncionarioSelect2",
            dataType: "json",
            type: "GET",
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
                            text: e.Job_Type,
                            slug: e.slug,
                            id: e.Job_id
                        }
                    })
                }
            }
        }
    })
}), $(document).ready(function() {
    $("#subscribe").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        multiple: !0,
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
}), $(document).ready(function() {
    $("#from").change(function() {
    	getJobSubTypeListById($(this).val(), false);
    })
});
function getJobSubTypeListById(JobTypeId, fromEdit){
	if(!fromEdit){
		$('#to').select2("val", "");
	}
    $.getJSON("getJobSubTypeByTypeId.in", {
        JobType: JobTypeId,
        ajax: "true"
    }, function(e) {
    	var subType = '';
    	var t = '';
    	if(!fromEdit){
    		t = '<option value="0">Please Select</option>';
    	}else{
    		var data = $('#to').select2('data');
    		if(data) {
    			t = '<option value="'+data.id+'">'+data.text+'</option>';
    		}
    	}
    	for(var r = 0; r < e.length; r++){
    		if(e[r].Job_ROT_number != null && e[r].Job_ROT_number != 'null'){
    			subType = e[r].Job_ROT_number + " - " + e[r].Job_ROT;
    		}else{
    			subType = e[r].Job_ROT;
    		}
    		t += '<option value="' + e[r].Job_Subid + '">' + subType + "</option>";
    	}
    	$("#to").html(t);
    })
    return true;
}

$(document).ready(function() {
    function f(a) {
        for (var b = document.getElementsByTagName("textarea"), c = 0; c < b.length; c++) com_satheesh.EVENTS.addEventHandler(b[c], "focus", g, !1), com_satheesh.EVENTS.addEventHandler(b[c], "blur", h, !1);
        b = document.getElementsByTagName("input");
        for (var c = 0; c < b.length; c++) a.indexOf(b[c].getAttribute("type") != -1) && (com_satheesh.EVENTS.addEventHandler(b[c], "focus", g, !1), com_satheesh.EVENTS.addEventHandler(b[c], "blur", h, !1));
        com_satheesh.EVENTS.addEventHandler(document.getElementById("formServiceReminder"), "submit", q, !1), document.getElementsByTagName("input")[0].focus(), com_satheesh.EVENTS.addEventHandler(document.forms[0].ServiceSelectVehicle, "blur", i, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].from, "blur", j, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].to, "blur", k, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].meter_interval, "blur", l, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].time_interval, "blur", m, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].renewal_timethreshold, "blur", n, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].time_threshold, "blur", o, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].subscribe, "blur", p, !1)
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
        var a = document.getElementById("ServiceSelectVehicle"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpvehicleNumber");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("vehicleNumberIcon").className = "fa fa-check form-text-feedback", document.getElementById("vehicleNumberErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("vehicleNumberIcon").className = "fa fa-remove form-text-feedback", document.getElementById("vehicleNumberErrorMsg").innerHTML = "Please select vehicle number"), b
    }

    function j() {
        var a = document.getElementById("from"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grprenewalType");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("renewalTypeIcon").className = "fa fa-check form-text-feedback", document.getElementById("renewalTypeErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("renewalTypeIcon").className = "fa fa-remove form-text-feedback", document.getElementById("renewalTypeErrorMsg").innerHTML = "Please select job Type "), b
    }

    function k() {
        var a = document.getElementById("to"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grprenewalSubType");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("renewalSubTypeIcon").className = "fa fa-check  form-text-feedback", document.getElementById("renewalSubTypeErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("renewalSubTypeIcon").className = "fa fa-remove form-text-feedback", document.getElementById("renewalSubTypeErrorMsg").innerHTML = "Please select job sub type")), b
    }

    function l() {
        var a = document.getElementById("meter_interval"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpmeterInterval");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("meterIntervalIcon").className = "fa fa-check form-text-feedback", document.getElementById("meterIntervalErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("meterIntervalIcon").className = "fa fa-remove form-text-feedback", document.getElementById("meterIntervalErrorMsg").innerHTML = "Please  enter reminder meter interval"), b
    }

    function m() {
        var a = document.getElementById("time_interval"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grptimeInterval");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("timeIntervalIcon").className = "fa fa-check form-text-feedback", document.getElementById("timeIntervalErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("timeIntervalIcon").className = "fa fa-remove form-text-feedback", document.getElementById("timeIntervalErrorMsg").innerHTML = "Please enter reminder time interval"), b
    }

    function n() {
        var a = document.getElementById("renewal_timethreshold"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpmeterThreshold");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("meterThresholdIcon").className = "fa fa-check form-text-feedback", document.getElementById("meterThresholdErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("meterThresholdIcon").className = "fa fa-remove form-text-feedback", document.getElementById("meterThresholdErrorMsg").innerHTML = "Please enter meter due threshold"), b
    }

    function o() {
        var a = document.getElementById("time_threshold"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grptimeThreshold");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("timeThresholdIcon").className = "fa fa-check form-text-feedback", document.getElementById("timeThresholdErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("timeThresholdIcon").className = "fa fa-remove form-text-feedback", document.getElementById("timeThresholdErrorMsg").innerHTML = "Please enter time due threshold "), b
    }

    function p() {
        var a = document.getElementById("subscribe"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpsubscribed");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("subscribedIcon").className = "fa fa-check form-text-feedback", document.getElementById("subscribedErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("subscribedIcon").className = "fa fa-remove form-text-feedback", document.getElementById("subscribedErrorMsg").innerHTML = "Please select subscibed users  "), b
    }

    function q(a) {
        var b = i();
        b &= j(), b &= k(), b &= l(), b &= m(), b &= n(), b &= o(), b &= p(), b || com_satheesh.EVENTS.preventDefault(a)
    }
    var b = "#FFC";
    com_satheesh.EVENTS.addEventHandler(window, "load", function() {
        f("text")
    }, !1)
});

function validateServiceReminder(){
	if(Number($('#ServiceSelectVehicle').val()) <= 0){
		$("#ServiceSelectVehicle").select2('focus');
		showAlert('warning', 'Please Select Vehicle !');
		return false;
	}
	if(Number($('#from').val()) <= 0){
		$("#from").select2('focus');
		showAlert('warning', 'Please Select Service Job Type !');
		return false;
	}
	if(Number($('#to').val()) <= 0){
		$("#to").select2('focus');
		showAlert('warning', 'Please Select Service Job Sub Type !');
		return false;
	}
	if((Number($('#meter_interval').val()) <= 0) && (Number($('#time_interval').val()) <= 0)){
		showAlert('warning', 'You Have To Must Enter Between Meter Interval Or Time Interval Or Both  !');
		return false;
	}
	
	if($('#firstService').is(":checked")){
		if((Number($('#first_meter_interval').val()) <= 0) && (Number($('#first_time_interval').val()) <= 0)){
			showAlert('warning', 'You Have To Must Enter Between First Meter Interval Or First Time Interval Or Both  !');
			return false;
		}
	}
	
	if($('#subscribe').val() == null || $('#subscribe').val().trim() == '' ){
		$("#subscribe").select2('focus');
		showAlert('warning', 'Please Select Subscriber User !');
		return false;
	}
	
	return true;
}