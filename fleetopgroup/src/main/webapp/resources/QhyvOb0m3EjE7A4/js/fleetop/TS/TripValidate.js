var maximumOdometer = 0; 
function TotalPoint(a, b, c) {
    var d = document.getElementById(a).value,
        e = document.getElementById(b).value,
        f = parseFloat(d) + parseFloat(e);
    isNaN(f) || (document.getElementById(c).value = f.toFixed(2))
}

function RouteName(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        t = r > 31 && 33 > r || r > 44 && 48 > r || r >= 48 && 57 >= r || r >= 65 && 90 >= r || r >= 97 && 122 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorRouteName").innerHTML = "Special Characters not allowed", document.getElementById("errorRouteName").style.display = t ? "none" : "inline", t
}

function RouteNo(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        t = r > 31 && 33 > r || r > 44 && 48 > r || r >= 48 && 57 >= r || r >= 65 && 90 >= r || r >= 97 && 122 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorRouteNo").innerHTML = "Special Characters not allowed", document.getElementById("errorRouteNo").style.display = t ? "none" : "inline", t
}

function RouteKM(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        t = r >= 48 && 57 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorRouteKM").innerHTML = "Alphabet Characters not allowed", document.getElementById("errorRouteKM").style.display = t ? "none" : "inline", t
}

function RouteRemarks(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        t = r > 31 && 33 > r || r > 39 && 42 > r || r >= 45 && 57 >= r || r >= 65 && 90 >= r || r >= 97 && 122 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorRouteRemarks").innerHTML = "Special Characters not allowed", document.getElementById("errorRouteRemarks").style.display = t ? "none" : "inline", t
}

function tripOpening(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        t = r >= 48 && 57 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errortripOpening").innerHTML = "Alphabet Characters not allowed", document.getElementById("errortripOpening").style.display = t ? "none" : "inline", t
}

function tripBook(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        t = r > 31 && 33 > r || r > 44 && 48 > r || r >= 48 && 57 >= r || r >= 65 && 90 >= r || r >= 97 && 122 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errortripBook").innerHTML = "Special Characters not allowed", document.getElementById("errortripBook").style.display = t ? "none" : "inline", t
}

function IsAdvanceAmount(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        t = r >= 48 && 57 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorAdvanceAmount").innerHTML = "Alphabet Characters not allowed", document.getElementById("errorAdvanceAmount").style.display = t ? "none" : "inline", t
}

function IsAdvancePlace(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        t = r > 31 && 33 > r || r > 39 && 42 > r || r >= 45 && 57 >= r || r >= 65 && 90 >= r || r >= 97 && 122 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorAdvancePlace").innerHTML = "Special Characters not allowed", document.getElementById("errorAdvancePlace").style.display = t ? "none" : "inline", t
}

function IsAdvancePaidby(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        t = r > 31 && 33 > r || r > 44 && 48 > r || r >= 48 && 57 >= r || r >= 65 && 90 >= r || r >= 97 && 122 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorAdvancePaidby").innerHTML = "Special Characters not allowed", document.getElementById("errorAdvancePaidby").style.display = t ? "none" : "inline", t
}

function IsAdvanceRefence(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        t = r > 31 && 33 > r || r > 44 && 48 > r || r >= 48 && 57 >= r || r >= 65 && 90 >= r || r >= 97 && 122 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorAdvanceRefence").innerHTML = "Special Characters not allowed", document.getElementById("errorAdvanceRefence").style.display = t ? "none" : "inline", t
}

function IsAdvanceRemarks(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        t = r > 31 && 33 > r || r > 39 && 42 > r || r >= 45 && 57 >= r || r >= 65 && 90 >= r || r >= 97 && 122 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorAdvanceRemarks").innerHTML = "Special Characters not allowed", document.getElementById("errorAdvanceRemarks").style.display = t ? "none" : "inline", t
}

function Isclosepaidto(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        t = r > 31 && 33 > r || r > 44 && 48 > r || r >= 48 && 57 >= r || r >= 65 && 90 >= r || r >= 97 && 122 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorClosepaidto").innerHTML = "Special Characters not allowed", document.getElementById("errorClosepaidto").style.display = t ? "none" : "inline", t
}

function IsClosingKM(e) {
    var r = 0 == e.keyCode ? e.charCode : e.keyCode,
        t = r >= 48 && 57 >= r || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorClosingKM").innerHTML = "Alphabet Characters not allowed", document.getElementById("errorClosingKM").style.display = t ? "none" : "inline", t
}

function validateDispatchTrip() {
	
	if(Number($('#TripSelectVehicle').val()) <= 0){
		showMessage('info','Please Select Vehicle!');
		return false;
	}
	
	if(Number($('#driverList').val()) <= 0){
		showMessage('info','Please Select Driver 1!');
		return false;
	}
	
	if($('#showAlwaysDispatchTime').val() == 'true' || $('#showAlwaysDispatchTime').val() == true){
		if($('#dispatchTime').val() == null || $('#dispatchTime').val().trim() == ''){
			showMessage('info','Please Enter Dispatch Time !');
			$('#dispatchTime').focus();
			return false;
		}
	}
	
	if($('#tripOpenCloseFuelRequired').val() == 'true' || $('#tripOpenCloseFuelRequired').val() == true){
		if(Number($('#tripStartDiesel').val()) <= 0){
			$('#tripStartDiesel').focus();
			showMessage('info','Please Enter Last Diesel !');
			return false;
		}
	}
	
	if(Number($('#loadListId').val()) <= 0){
		showMessage('info','Please Select Load Type !');
		return false;
	}
	
	if(validateOdometer()){
		 document.getElementById("tripStutes").value = 2;
		    var e = !0,
		        r = document.getElementById("TripSelectVehicle").value,
		        t = document.getElementById("TripSelectVehicle").value,
		        n = document.getElementById("driverList").value,
		        o = document.getElementById("tripFristDriverName").value,
		        d = document.getElementById("driverList2").value,
		        l = document.getElementById("tripSecDriverName").value,
		        i = document.getElementById("TripRouteList").value,
		        a = document.getElementById("routeName").value,
		        c = document.getElementById("tripOpeningKM").value,
		        u = document.getElementById("Cleaner").value,
		        m = document.getElementById("tripCleanerName").value;
		    return validateMandatoryDispatch(r, t, n, o, d, l, u, m, i, a, c) || (e = !1), "" == o && "" == l && (n == d ? (errorDriver2.innerHTML = "Driver 1 and Driver 2 same.", errorDriver2Name.innerHTML = "Driver 1 and Driver 2 same.", e = !1) : (errorDriver2.innerHTML = "", errorDriver2Name.innerHTML = "")), "" != o && "" != l && (n && 0 != n && d && 0 != d ? n == d && (errorDriver2.innerHTML = "Driver 1 and Driver 2 same.", errorDriver2Name.innerHTML = "Driver 1 and Driver 2 same.", e = !1) : (errorDriver2.innerHTML = "", errorDriver2Name.innerHTML = "")), "" == r && (document.getElementById("TripSelectVehicle").value = 0), "" == u && (document.getElementById("Cleaner").value = 0), "" == n && (document.getElementById("driverList").value = 0), "" == d && (document.getElementById("driverList2").value = 0), "" == i && (document.getElementById("TripRouteList").value = 0), e

	}else{
		return false;
	}
   }

function validateTrip() {
	if(Number($('#driverList').val()) <= 0){
	showMessage('info','Please Select Driver 1!');
	return false;
	}	
	
		if(validateOdometer()){
		 document.getElementById("tripStutes").value = 1;
		    var e = !0,
		        r = document.getElementById("TripSelectVehicle").value,
		        t = document.getElementById("TripRouteList").value,
		        n = document.getElementById("routeName").value,
		        o = document.getElementById("driverList").value,
		        d = document.getElementById("driverList2").value,
		        t = (document.getElementById("tripFristDriverName").value, document.getElementById("tripSecDriverName").value, document.getElementById("TripRouteList").value),
		        l = document.getElementById("Cleaner").value;
		    return document.getElementById("tripCleanerName").value, validateMandatory(t, n) || (e = !1), "" == r && (document.getElementById("TripSelectVehicle").value = 0), "" == l && (document.getElementById("Cleaner").value = 0), "" == o && (document.getElementById("driverList").value = 0), "" == d && (document.getElementById("driverList2").value = 0), "" == t && (document.getElementById("TripRouteList").value = 0), e

	}else{
		return false;
	}
   }

function validateMandatoryDispatch(e, r, t, n, o, d, l, i, a, c, u) {
    var m = !0;
    return 0 != a && null != a || "" != c && null != c ? (errorRoute.innerHTML = "", document.getElementById("routeName").style.border = "", errorRouteName.innerHTML = "") : (errorRoute.innerHTML = "Please Select Route", document.getElementById("routeName").style.border = "2px solid #F00", errorRouteName.innerHTML = "Please Enter Route", m = !1), 0 != e && null != e || "" != r && null != r ? (errorVehicle.innerHTML = "", document.getElementById("vehicle_registration").style.border = "", errorVehicle.innerHTML = "") : (errorVehicle.innerHTML = "Please Select Vehicle", document.getElementById("vehicle_registration").style.border = "", errorVehicle.innerHTML = "Please Enter Vehicle Name", m = !1), (0 != t && null != t || "" != n && null != n) && (0 != o && null != o || "" != d && null != d) && (0 != t && null != t || "" != n && null != n ? (errorDriver1.innerHTML = "", document.getElementById("tripFristDriverName").style.border = "", errorDriverName.innerHTML = "") : (errorDriver1.innerHTML = "Please Select Driver", document.getElementById("tripFristDriverName").style.border = "2px solid #F00", errorDriverName.innerHTML = "Please Enter Name", m = !1), 0 != o && null != o || "" != d && null != d ? (errorDriver2.innerHTML = "", document.getElementById("tripSecDriverName").style.border = "", errorDriver2Name.innerHTML = "") : (errorDriver2.innerHTML = "Please Select Driver", document.getElementById("tripSecDriverName").style.border = "2px solid #F00", errorDriver2Name.innerHTML = "Please Enter Name", m = !1)), null == u || "" == u ? (document.getElementById("tripOpeningKM").style.border = "2px solid #F00", errorOpening.innerHTML = "Please Select Vehicle", m = !1) : document.getElementById("tripOpeningKM").style.border = "", m
}

function validateMandatory(e, r) {
    var t = !0;
    return 0 != e && null != e || "" != r && null != r ? (errorRoute.innerHTML = "", document.getElementById("routeName").style.border = "", errorRouteName.innerHTML = "") : (errorRoute.innerHTML = "Please Select Route", document.getElementById("routeName").style.border = "2px solid #F00", errorRouteName.innerHTML = "Please Enter Route", t = !1), t
}

function driverEditSelect() {}

function visibility(e, r) {
    var t = document.getElementById(e),
        n = document.getElementById(r);
    "block" == t.style.display ? (t.style.display = "none", n.style.display = "block") : (t.style.display = "block", n.style.display = "none")
}
function visibilityRoute(e, r) {
    var t = document.getElementById(e),
        n = document.getElementById(r);
    	
    	if("block" == t.style.display){
    		$('#isNewRoute').val(0);
    	}else{
    		$('#isNewRoute').val(1);
    	}
    "block" == t.style.display ? (t.style.display = "none", n.style.display = "block") : (t.style.display = "block", n.style.display = "none")
}
function visibilityvehicle(e, r) {
	$("#TripSelectVehicle").select2("val", "");
    var t 			 = document.getElementById(e),
        n 			 = document.getElementById(r),
        vehicleGroup = document.getElementById("vehicle_group_id");
    if(n.style.display == "block"){
    	 vehicleGroupVal = $('#vehicle_Group').val();
    }
    "block" == t.style.display ? (t.style.display = "none", n.style.display = "block", vehicleGroup.style.display = "none") : (t.style.display = "block", n.style.display = "none", vehicleGroup.style.display = "block")
   
    if(t.style.display == 'block'){
    	$('#vehicle_Group').val($("#vehicleGroupId option:selected").text().trim());
    }else{
    	$('#vehicle_Group').val('');
    }
}

function visibilityEdit(e, r) {
    var t = document.getElementById(e),
    n = document.getElementById(r);
    vehicleGroup = document.getElementById("vehicle_group_id");
    
    "block" == t.style.display ? (t.style.display = "none", n.style.display = "block", vehicleGroup.style.display = "none") : (t.style.display = "block", n.style.display = "none", vehicleGroup.style.display = "block")
    if(t.style.display == 'block'){
    	$('#VGroupTab').hide();
    }else{
    	$('#vehicle_Group').val($('#prevehicle_group').val());
    	$('#VGroupTab').show();
    }
}
var specialKeys = new Array;
specialKeys.push(8), specialKeys.push(9), specialKeys.push(46), specialKeys.push(36), specialKeys.push(35), specialKeys.push(37), specialKeys.push(39)
, /*$(document).ready(function() {
    $("#TripSelectVehicle").change(function() {
    	
    	onVehicleSelect($(this).val());
    	
        0 != $(this).val() && $.getJSON("getTripVehicleOdoMerete.in", {
            FuelvehicleID: $(this).val(),
            ajax: "true"
        }, function(e) {
        	if(e.serviceOverDue){
        		showMessage('errors','there is mandetory service reminder is overdue, you can not create tripsheet Service Reminders is : '+e.serviceNumbers);
        		$("#TripSelectVehicle").select2("val", "");
        		return false;
        	}
            var r = "",
                t = "",
                n = "";
            maximumOdometer	= e.vehicle_Odometer;
            maximumOdometer	= e.vehicleExpectedKm;
            r = e.vehicle_Odometer, n = e.vehicle_Group, t = e.vehicle_RouteName, document.getElementById("tripOpeningKM").placeholder = r, document.getElementById("tripOpeningKM").value = r, document.getElementById("vehicle_Group").value = n;
            //undefined != t && $("#routeSelect").hide(), $("#routeEnter").show(), document.getElementById("routeName").value = t, $('#routeID').val(e.routeID);
            $('#vehicle_ExpectedOdameter').val(e.vehicle_ExpectedOdameter);
            $('#vehicle_Odometer').val(e.vehicle_Odometer);
            if(t != undefined){
            	$('#TripRouteList').select2('data', {
    				id : e.routeID,
    				text : t
    			});
            	
            	
			        $.getJSON("getTripRouteSubListById.in", {
			            vehicleGroup: e.routeID, ajax: "true"
			        }
			        , function(a) {
			            for(var b='', c=a.length, d=0;
			            c>d;
			            d++)b+='<option value="'+a[d].routeID+'">'+a[d].routeNo + " " + a[d].routeName+"</option>";
			            b+="</option>", $("#TripRouteSubList").html(b)
			        }
			        )
			   
            	
            }
        })
    }), $("#SelectVehicle").change(function() {
    	showLayer();
        0 != $(this).val() && $.getJSON("getTripVehicleOdoMerete.in", {
            FuelvehicleID: $(this).val(),
            ajax: "true"
        }, function(e) {
        	hideLayer();
        	if(e.serviceOverDue){
        		showMessage('errors','There is mandetory service reminder is overdue, you can not create tripsheet Service Reminders is : '+e.serviceNumbers);
        		$("#TripSelectVehicle").select2("val", "");
        		return false;
        	}
        	
        	if(e.vStatusId != 1 && e.vStatusId != 5){
        		showMessage('errors','You Can not create TripSheet , Vehicle Status is : '+e.vehicle_Status);
        		$("#TripSelectVehicle").select2("val", "");
        		return false;
        	}
        	if(e.driverFirstName != undefined && e.driverFirstName != null){
        		$('#driverList').select2('data', {
        			id : e.partlocation_id,
        			text : e.driverEmpName+" "+e.driverFirstName+"_"+e.driverLastName
        		});
        	}else{
        		$("#driverList").select2("val", "");

        	}
            var r = "",
                t = "",
                n = "";
            if(e.gpsOdameter != undefined && e.gpsOdameter != null && e.isOdometerReading){
            	$('#tripGpsOpeningKM').val(e.gpsOdameter);
            }
            if(e.tripEndDiesel != undefined && e.tripEndDiesel > 0){
            	$('#tripStartDiesel').val(e.tripEndDiesel);
            }
            r = e.vehicle_Odometer, n = e.vehicle_Group, t = e.vehicle_RouteName, document.getElementById("tripOpeningKM").placeholder = r, document.getElementById("tripOpeningKM").value = r, document.getElementById("vehicle_Group").value = n;
            if(t != undefined){
            	$("#routeSelect").hide();
            	$("#routeEnter").show();
            	document.getElementById("routeName").value = t;
            	$('#TripRouteList').val(e.routeID);
            }
        })
    })
}),*/ $(document).ready(function() {
    $("#TripVehicle").hide(), $("#contactTwo").hide(), $("#driver2enter").hide(), $("#cleanerEnter").hide(), $("#routeEnter").hide(), $("#Bank").hide(), $("#advance").hide()
});

function loadDispatch() {
    $.getJSON("GetTSVehicleValidate.in", {
        VID: $("#TripSelectVehicle").val(),
        ajax: "true"
    }, function(a) {
        var b = "",
            c = "";
        c = a.vehicle_registration, b = 0 != c && null != c ? '<p style="color: red;">You Should be close first Driver Account in those Tripsheet = ' + c + " <br> You can save TripSheet. You can't Dispatch. </p>" : '<p style="color: blue;">You can Save and Dispatch.</P>', $("#last_occurred").html(b)
    })
}
	$(document).ready(function() {
		$("#TripSelectVehicle").change(function() {
			if(previousTrip != undefined){
				previousTrip	= null;
			}
			if($("#tripEdit").val()  == false || $("#tripEdit").val()  == 'false'){
				$("#dispatchTime").val('');
			}
			
			if($("#TripSelectVehicle").val() != null && $("#TripSelectVehicle").val() > 0){
				getLastTripSheetDetailsToCopy(getCurrentDate(),getCurrentTime(), false);
			}else{
				resetTripSheetFeilds();
			}
			
			if($('#preVid').val() != undefined && (Number($('#preVid').val()) != Number($('#TripSelectVehicle').val()))){
				validateEditDetails();
			}
		})
	});

$(document).ready(function() {
    function a(a) {
        for (var o = document.getElementsByTagName("textarea"), p = 0; p < o.length; p++) com_satheesh.EVENTS.addEventHandler(o[p], "focus", b, !1), com_satheesh.EVENTS.addEventHandler(o[p], "blur", c, !1);
        o = document.getElementsByTagName("input");
        for (var p = 0; p < o.length; p++) a.indexOf(-1 != o[p].getAttribute("type")) && (com_satheesh.EVENTS.addEventHandler(o[p], "focus", b, !1), com_satheesh.EVENTS.addEventHandler(o[p], "blur", c, !1));
        com_satheesh.EVENTS.addEventHandler(document.getElementById("formTripRoute"), "submit", m, !1), com_satheesh.EVENTS.addEventHandler(document.getElementById("formEditTripRoute"), "submit", n, !1), document.getElementsByTagName("input")[0].focus(), com_satheesh.EVENTS.addEventHandler(document.forms[0].routeName, "blur", d, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].jobRotName, "blur", e, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].routePoint, "blur", f, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].routeHour, "blur", g, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].routeLiter, "blur", h, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].routeKM, "blur", i, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].Expense, "blur", j, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].routeAmount, "blur", k, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].routeReference, "blur", l, !1)
    }

    function b(a) {
        var b = com_satheesh.EVENTS.getEventTarget(a);
        null != b && (b.style.backgroundColor = o)
    }

    function c(a) {
        var b = com_satheesh.EVENTS.getEventTarget(a);
        null != b && (b.style.backgroundColor = "")
    }

    function d() {
        var a = document.getElementById("routeName"),
            b = 0 != a.value && 0 != a.value.length,
            c = document.getElementById("grprouteName");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("routeNameIcon").className = "fa fa-check form-text-feedback", document.getElementById("routeNameErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("routeNameIcon").className = "fa fa-remove form-text-feedback", document.getElementById("routeNameErrorMsg").innerHTML = "Please enter route name"), b
    }
    function vehicleGroup() {
        var a = document.getElementById("vehiclegroupid"),
            b = 0 != a.value,
            c = document.getElementById("grpVehicleGroup");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("vehicleGroupIcon").className = "fa fa-check form-text-feedback", document.getElementById("vehicleGroupErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("vehicleGroupIcon").className = "fa fa-remove form-text-feedback", document.getElementById("vehicleGroupErrorMsg").innerHTML = "Please Select Vehicle Group"), b
    }

    function e() {
        var a = document.getElementById("routeNumber"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grprouteNumber");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("routeNumberIcon").className = "fa fa-check form-text-feedback", document.getElementById("routeNumberErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("routeNumberIcon").className = "fa fa-remove form-text-feedback", document.getElementById("routeNumberErrorMsg").innerHTML = "Please enter route number"), b
    }

    function f() {
        var a = document.getElementById("routePoint"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grproutePoint");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("routePointIcon").className = "fa fa-check form-text-feedback", document.getElementById("routePointErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("routePointIcon").className = "fa fa-remove form-text-feedback", document.getElementById("routePointErrorMsg").innerHTML = "Please enter route point"), b
    }

    function g() {
        var a = document.getElementById("routeHour"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grprouteHour");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("routeHourIcon").className = "fa fa-check  form-text-feedback", document.getElementById("routeHourErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("routeHourIcon").className = "fa fa-remove form-text-feedback", document.getElementById("routeHourErrorMsg").innerHTML = "Please enter route total hour")), b
    }

    function h() {
        var a = document.getElementById("routeLiter"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grprouteLiter");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("routeLiterIcon").className = "fa fa-check  form-text-feedback", document.getElementById("routeLiterErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("routeLiterIcon").className = "fa fa-remove form-text-feedback", document.getElementById("routeLiterErrorMsg").innerHTML = "Please enter route total liter")), b
    }

    function i() {
        var a = document.getElementById("routeKM"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grprouteKM");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("routeKMIcon").className = "fa fa-check  form-text-feedback", document.getElementById("routeKMErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("routeKMIcon").className = "fa fa-remove form-text-feedback", document.getElementById("routeKMErrorMsg").innerHTML = "Please enter route total km")), b
    }

    function j() {
        var a = document.getElementById("Expense"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grprouteExpense");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("routeExpenseIcon").className = "fa fa-check  form-text-feedback", document.getElementById("routeExpenseErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("routeExpenseIcon").className = "fa fa-remove form-text-feedback", document.getElementById("routeExpenseErrorMsg").innerHTML = "Please select expense")), b
    }

    function k() {
        var a = document.getElementById("routeAmount"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grprouteExpense");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("routeAmountIcon").className = "fa fa-check  form-text-feedback", document.getElementById("routeAmountErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("routeAmountIcon").className = "fa fa-remove form-text-feedback", document.getElementById("routeAmountErrorMsg").innerHTML = "Please enter amount")), b
    }

    function l() {
        var a = document.getElementById("routeReference"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grprouteExpense");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("routeReferenceIcon").className = "fa fa-check  form-text-feedback", document.getElementById("routeReferenceErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("routeReferenceIcon").className = "fa fa-remove form-text-feedback", document.getElementById("routeReferenceErrorMsg").innerHTML = "Please enter reference")), b
    }

    function m(a) {
        var b = d();
        b &= e(), b &= f(), b &= g(), b &= h(), b &= i(), b &= j(), b &= k(), (b &= l()),b &= vehicleGroup() || com_satheesh.EVENTS.preventDefault(a)
    }

    function n(a) {
        var b = d();
        b &= e(), b &= f(), b &= g(), b &= h(), (b &= i()) || com_satheesh.EVENTS.preventDefault(a)
    }
    
    function o() {
        var a = document.getElementById("Extra"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grprouteExpense");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("routeExpenseIcon").className = "fa fa-check  form-text-feedback", document.getElementById("routeExpenseErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("routeExpenseIcon").className = "fa fa-remove form-text-feedback", document.getElementById("routeExpenseErrorMsg").innerHTML = "Please select Extra Option")), b
    }
    var o = "#FFC";
    com_satheesh.EVENTS.addEventHandler(window, "load", function() {
        a("text")
    }, !1)
});

function validatePassengerDetails(){
	var tripliter	 	= $('#tripliter').val();
	var tripPass 		= $('#tripPass').val();
	var tripSingl	 	= $('#tripSingl').val();
	var tripAmount	 	= $('#tripAmount').val();
	var tripOverTime 	= $('#tripOverTime').val();
	var noOfRoundTrip 	= Number($('#noOfRoundTrip').val());
	
	if(noOfRoundTrip <= 0){
		showMessage('errors', 'Please Enter No of Round Trip!');
		return false;
	}
	
	if(tripliter == ""){
		showMessage('errors', 'Please Enter Total Passenger!');
		return false;
	}
	if(tripPass == ""){
		showMessage('errors', 'Please Enter Pass Details!');
		return false;
	}
	if(tripSingl == ""){
		showMessage('errors', 'Please Enter RFID Pass Details!');
		return false;
	}
	if(tripAmount == ""){
		showMessage('errors', 'Please Enter RFID Amount Details!');
		return false;
	}
	if(tripOverTime == ""){
		showMessage('errors', 'Please Enter Over Time Details!');
		return false;
	}
}

function setPaidByFeild(){
	var closeTripStatusId = Number($('#closeTripStatusId').val());
	if(closeTripStatusId == 1){
		$('#paidByDriver').show();
		$('#paidByOffice').hide();
	}
	if(closeTripStatusId == 2){
		$('#paidByDriver').hide();
		$('#paidByOffice').show();
	}
}


function GetTSVehicleValidate(vehicleId){
	
    $.getJSON("GetTSVehicleValidate.in", {
        VID: vehicleId,
        ajax: "true"
    }, function(a) {
    	if(a.vehicle_registration != undefined){
    		$("#TripSelectVehicle").select2("val", "");
    		$("#driverList").select2("val", "");
    		$("#TripRouteList").select2("val", "");
    		$("#tripOpeningKM").val(0);
    		$("#vehicle_Group").val('');
    		$('#last_occurred').show();
    		var b = "",
    		c = "";
    		c = a.vehicle_registration, b = 0 != c && null != c ? '<p style="color: red;">You Should be close first Driver Account in those Tripsheet = ' + c + " <br> You can save TripSheet. You can't Dispatch. </p>" : '<p style="color: blue;">You can Save and Dispatch.</P>', $("#last_occurred").html(b)
    	}else{
    		$('#last_occurred').hide();
    	}
    })

}

function onVehicleSelect(vehicleId){
	//showMessage('info','Please Check Opening Kilometer ');//checkmod
	
	$('#showDriverDetailsFromItsGatewayApi').addClass('hide');
	showLayer();
    0 != vehicleId && $.getJSON("getTripVehicleOdoMerete.in", {
        FuelvehicleID: vehicleId,
        ajax: "true"
    }, function(e) {
    	hideLayer();
    	if(e.serviceOverDue){
    		showMessage('errors','there is mandetory service reminder is overdue, you can not create tripsheet Service Reminders is : '+e.serviceNumbers);
    		$("#TripSelectVehicle").select2("val", "");
    		return false;
    	}
    	if(e.vStatusId != 1 && e.vStatusId != 5){
    		showMessage('errors','You Can not create TripSheet , Vehicle Status is : '+e.vehicle_Status);
    		$("#TripSelectVehicle").select2("val", "");
    		return false;
    	}
    	if(e.driverFirstName != undefined && e.driverFirstName != null){
    		$('#driverList').select2('data', {
    			id : e.partlocation_id,
    			text : e.driverEmpName+" "+e.driverFirstName+"_"+e.driverLastName
    		});
    	}else{
    		$("#driverList").select2("val", "");

    	}
    	 if(e.tripEndDiesel != undefined && e.tripEndDiesel > 0){
         	$('#tripStartDiesel').val(e.tripEndDiesel);
         }
        var r = "",
            t = "",
            n = "";
        if(e.isOdometerReading){
        	$('#gpsKMRow').show();
        }
        if(e.gpsWorking == false){
        	$('#manualKM').show();
        	$('#gpsKMRow').hide().prop('required',false);
        	if(e.isOdometerReading){
        		$('#errorOpening').html('GPS NOT WORKING/ATTACHED !');
        	}
        }else{
        	if(e.vehicle_Location != undefined){
        		$('#gpsLocation').val(e.vehicle_Location);
            	$('#gpsLocationRow').show();
        	}
        }
        $('#tripGpsOpeningKM').val(e.gpsOdameter);
       
        maximumOdometer	= e.vehicle_Odometer;
       /* maximumOdometer	= e.vehicleExpectedKm;*/
        r = e.vehicle_Odometer, n = e.vehicle_Group, t = e.vehicle_RouteName, document.getElementById("tripOpeningKM").placeholder = r, document.getElementById("tripOpeningKM").value = r, document.getElementById("vehicle_Group").value = n;
        //undefined != t && $("#routeSelect").hide(), $("#routeEnter").show(), document.getElementById("routeName").value = t, $('#routeID').val(e.routeID);
        $('#vehicle_ExpectedOdameter').val(e.vehicle_ExpectedOdameter);
        $('#vehicle_Odometer').val(e.vehicle_Odometer);
        if(t != undefined){
        	$('#TripRouteList').select2('data', {
				id : e.routeID,
				text : t
			});
        	
        	
		        $.getJSON("getTripRouteSubListById.in", {
		            vehicleGroup: e.routeID, ajax: "true"
		        }
		        , function(a) {
		            for(var b='', c=a.length, d=0;
		            c>d;
		            d++)b+='<option value="'+a[d].routeID+'">'+a[d].routeNo + " " + a[d].routeName+"</option>";
		            b+="</option>", $("#TripRouteSubList").html(b)
		        }
		        )
		   
        	
        }
    })

}


function validateTimeIncome(){
	var noIncomeName	= false;
	var noAmount	= false;
	
	$('select[name*=incomeName]').each(function(){
		var incomeVal = $("#"+$( this ).attr( "id" )).val();
		if(incomeVal <= 0){
			 //$("#"+$( this ).attr( "id" )).select2('focus');
			noIncomeName	= true;
			showMessage('errors', 'Please Select Time-Income !');
			return false;
		}
	
	});
	
	$('input[name*=incomeAmount]').each(function(){
		var amount = $("#"+$( this ).attr( "id" )).val();
		if(amount <= 0){
			// $("#"+$( this ).attr( "id" )).focus();
			noAmount	= true;
			showMessage('errors', 'Please Enter Amount !');
			return false;
		}
	
	});
	
	if(noAmount || noIncomeName){
		return false;
	}
	
	return true;
}

if($('#tripStatusId').val() == 1){
	$('#TripSelectVehicle' ).prop("readonly", true);
}

function validateOdometerOnKeyUp(){
	var odometer = Number($('#tripOpeningKM').val());
	if(odometer > Number($('#maxAllowed').val())){
		showMessage('errors', 'Please enter odometer between '+$('#minAllowed').val()+" - "+$('#maxAllowed').val());
		return false;
	}
	if(odometer < Number($('#minAllowed').val())){
		showMessage('errors', 'Please enter odometer between '+$('#minAllowed').val()+" - "+$('#maxAllowed').val());
		return false;
	}
	return true;
}

function setCloseOdometerRange(){
	var maxAllowed = Number($('#maxAllowedKM').val()) + Number($('#allowedKMForExtendedTrip').val());
	var minAllowed = Number($('#tripOpeningKM').val()) + 1;
	if(!document.getElementById('isExtendedYes').checked){
		$('#odometerRange').html('Odometer Range : '+minAllowed+' - '+maxAllowed);
		$('#minAllowedKM').val(minAllowed);
		$('#maxAllowedKM').val(maxAllowed);
		
	}else{
		$('#minAllowedKM').val($('#oldminAllowedKM').val());
		$('#maxAllowedKM').val($('#oldmaxAllowedKM').val());
		$('#odometerRange').html('Odometer Range : '+$('#oldminAllowedKM').val()+' - '+$('#oldmaxAllowedKM').val());
	}
}
function setPaidByPayToFeilds(){
	var expenseForReverseBalance = Number($('#expenseForReverseBalance').val());
	var incomeForReverseBalance = Number($('#incomeForReverseBalance').val());
	var advanceForReverseBalance = Number($('#advanceForReverseBalance').val());
	
	showLayer();
	if($('#removeAdvanceFromDriverBalance').val() == 'true' || $('#removeAdvanceFromDriverBalance').val() == true){
		$('#balanceFormula').html(' Income - Expense = Balance ');
		$('#balanceFormulaVal').html(''+$('#totalIncome').val()+' - '+$('#totalExpense').val()+' = '+$('#balanceAmount').val()+' ');
	}else if($('#reverseDriverBalance').val() == 'true' || $('#reverseDriverBalance').val() == true){
		$('#balanceFormula').html('Expense - (Income + Advance) = Balance ');
		
		if($('#roundFigureAmount').val() == 'true' || $('#roundFigureAmount').val() == true){
			balance = Number((Math.round(expenseForReverseBalance))-((Math.round(incomeForReverseBalance))+(Math.round(advanceForReverseBalance))));
		}else{
			 balance = Number((expenseForReverseBalance)-((incomeForReverseBalance)+(advanceForReverseBalance)));
		}
		
		$('#balanceAmount').val(balance);
		
		if($('#roundFigureAmount').val() == 'true' || $('#roundFigureAmount').val() == true){
			$('#balanceFormulaVal').html(' '+Math.round(expenseForReverseBalance)+' - ('+Math.round(incomeForReverseBalance)+' + '+Math.round(advanceForReverseBalance)+') ='+Math.round($('#balanceAmount').val())+'');
		}else{
			$('#balanceFormulaVal').html(' '+$('#expenseForReverseBalance').val()+' - ('+$('#incomeForReverseBalance').val()+' + '+$('#advanceForReverseBalance').val()+') ='+$('#balanceAmount').val()+'');
		}
	}else{
		$('#balanceFormula').html(' (Income + Advance)- Expense = Balance ');
		$('#balanceFormulaVal').html('('+$('#totalIncome').val()+' + '+$('#totalAdvance').val()+')- '+$('#totalExpense').val()+' = '+$('#balanceAmount').val()+' ');
	}
	
	if($('#roundFigureAmount').val() == 'true' || $('#roundFigureAmount').val() == true){
		$('#balanceAmount').val(Math.round($('#balanceAmount').val()));
	}
	$('#closeTripStatusId').attr('readonly', true);
	$('#paidByOfficeSelect').attr('readonly', true);
	$('#closeTripNameByIdselect').attr('readonly', true);
	$('#closeTripAmount').attr('readonly', true);
	$('#closeTripAmount').val($('#balanceAmount').val());
	var lossInTrip = $('#lossInTrip').val();
	if(lossInTrip == 'false' || lossInTrip == false){
		$('#paidByDriver').hide();
		$('#paidByOffice').show();
		$('#closeTripStatusId').val(2);
	}else{
		$('#paidByDriver').show();
		$('#paidByOffice').hide();
		$('#closeTripStatusId').val(1);
	}
	hideLayer();
}

function tripExpenseValidate(){
	incluDriverBalance();
	if($("#expenseName").val() == undefined || ($("#expenseName").val()).trim() == "" ){
		showMessage('info','Please Enter Expense Name')
		return false;
	}
}

function incluDriverBalance()
{
	 if ($("#driverbalance").prop("checked")) {
      $("#driverbalance").val("true");
    } else {
      $("#driverbalance").val("false");
    }
}

function validateTripExapense(){
	incluDriverBalance();
	
	if($("#pcName").val() == undefined || ($("#pcName").val()).trim() == "" ){
		showMessage('info','Please Enter Expense Name')
		return false;
	}
}

function validateTripIncome(){
	
	if($("#incomeName").val() == undefined || ($("#incomeName").val()).trim() == "" ){
		showMessage('info','Please Enter Expense Name')
		return false;
	}
}

function validateCashBookEdit(){
	
	if($("#pcName").val() == undefined || ($("#pcName").val()).trim() == "" ){
		showMessage('info','Please Enter Expense Name')
		return false;
	}
}

function meterNotWorkingOnChange(value,id){
if($("#"+value.id).is(':checked')){
	$("#"+id.id).hide();
}else{
	$("#"+id.id).show();
}
}

