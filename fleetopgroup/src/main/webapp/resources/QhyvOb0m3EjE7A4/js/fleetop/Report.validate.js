var mainPartLocationType = 1;
var subPartLocationType  = 2;
function IsAlphaNumeric(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = r > 31 && 33 > r || r > 44 && 48 > r || r >= 48 && 57 >= r || r >= 65 && 90 >= r || r >= 97 && 122 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("error").innerHTML = "Special Characters not allowed", document.getElementById("error").style.display = n ? "none" : "inline", n
}

function IsNumeric(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = r >= 48 && 57 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorAmount").innerHTML = "Alphabets & Special Characters not allowed", document.getElementById("errorAmount").style.display = n ? "none" : "inline", n
}

function IsAlphaNumericPaynumber(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = r > 31 && 33 > r || r > 44 && 48 > r || r >= 48 && 57 >= r || r >= 65 && 90 >= r || r >= 97 && 122 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorPaynumber").innerHTML = "Special Characters not allowed", document.getElementById("errorPaynumber").style.display = n ? "none" : "inline", n
}

function IsAlphaNumericPaidby(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = r > 31 && 33 > r || r > 44 && 48 > r || r >= 65 && 90 >= r || r >= 97 && 122 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorPaidby").innerHTML = "Special Characters not allowed", document.getElementById("errorPaidby").style.display = n ? "none" : "inline", n
}

function IsAlphaNumericAuthor(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = r > 31 && 33 > r || r > 44 && 48 > r || r >= 48 && 57 >= r || r >= 65 && 90 >= r || r >= 97 && 122 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorAuthor").innerHTML = "Special Characters not allowed", document.getElementById("errorAuthor").style.display = n ? "none" : "inline", n
}

function IsAlphaNumericNumber(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = r > 31 && 33 > r || r > 44 && 48 > r || r >= 48 && 57 >= r || r >= 65 && 90 >= r || r >= 97 && 122 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorNumber").innerHTML = "Special Characters not allowed", document.getElementById("errorNumber").style.display = n ? "none" : "inline", n
}

function IsNumericTimeThru(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = r >= 48 && 57 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorTimeThru").innerHTML = "Alphabet Characters not allowed", document.getElementById("errorTimeThru").style.display = n ? "none" : "inline", n
}

function OnChangeDueThreshold(e) {
    var r = (document.getElementById("renewal_timethreshold"), e.value);
    0 == r ? renewal_timethreshold.value > 30 ? document.getElementById("errorTime").innerHTML = "Maximum allowed 30 day(s)" : document.getElementById("errorTime").innerHTML = "" : 7 == r ? renewal_timethreshold.value > 4 ? document.getElementById("errorTime").innerHTML = "Maximum allowed 4 Week(s)" : document.getElementById("errorTime").innerHTML = "" : 28 == r && (renewal_timethreshold.value > 1 ? document.getElementById("errorTime").innerHTML = "Maximum allowed 1 Month" : document.getElementById("errorTime").innerHTML = "")
}

function renewalReminder() {
    var e = !0,
        r = document.getElementById("renewal_timethreshold");
    return ValidateDue(r) || (e = !1), e
}

function renewalReminderUpdate() {
    var e = !0,
        r = document.getElementById("renewal_timethreshold");
    return ValidateDue(r) || (alert("hi"), e = !1), e
}

function ValidateDue(e) {
    var r = !0,
        n = document.getElementById("renewal_periedthreshold"),
        t = n.value;
    return 0 == t ? e.value > 30 && (document.getElementById("renewal_timethreshold").style.border = "2px solid #F00", e.style.border = "2px solid #F00", document.getElementById("errorTime").innerHTML = "Maximum allowed 30 day(s)", r = !1) : 7 == t ? e.value > 4 && (document.getElementById("errorTime").innerHTML = "Maximum allowed 4 Week(s)", r = !1) : 28 == t && e.value > 1 && (document.getElementById("errorTime").innerHTML = "Maximum allowed 1 Month", r = !1), r
}
var specialKeys = new Array;
specialKeys.push(8), specialKeys.push(9), specialKeys.push(46), specialKeys.push(36), specialKeys.push(35), specialKeys.push(37), specialKeys.push(39),
$(document).ready(function() {
    $("#datemask").inputmask("dd-mm-yyyy", {
        placeholder: "dd-mm-yyyy"
    }), $("[data-mask]").inputmask()
});

function showSubLocationDropDown(){
	$("#subLocationId").select2("val", "");
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
    }else{
    	$("#subLocationId").val('')
    	$("#subLocation").hide();
    }
	
}
$(document).ready(function() {
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
});