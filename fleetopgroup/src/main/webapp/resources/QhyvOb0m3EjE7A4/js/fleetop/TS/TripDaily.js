var vehicleStatus = 0;
function RouteName(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        r = t > 31 && 33 > t || t > 44 && 48 > t || t >= 48 && 57 >= t || t >= 65 && 90 >= t || t >= 97 && 122 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorRouteName").innerHTML = "Special Characters not allowed", document.getElementById("errorRouteName").style.display = r ? "none" : "inline", r
}

function RouteNo(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        r = t > 31 && 33 > t || t > 44 && 48 > t || t >= 48 && 57 >= t || t >= 65 && 90 >= t || t >= 97 && 122 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorRouteNo").innerHTML = "Special Characters not allowed", document.getElementById("errorRouteNo").style.display = r ? "none" : "inline", r
}

function RouteKM(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        r = t >= 48 && 57 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorRouteKM").innerHTML = "Alphabet Characters not allowed", document.getElementById("errorRouteKM").style.display = r ? "none" : "inline", r
}

function RouteRemarks(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        r = t > 31 && 33 > t || t > 39 && 42 > t || t >= 45 && 57 >= t || t >= 65 && 90 >= t || t >= 97 && 122 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorRouteRemarks").innerHTML = "Special Characters not allowed", document.getElementById("errorRouteRemarks").style.display = r ? "none" : "inline", r
}

function tripOpening(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        r = t >= 48 && 57 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errortripOpening").innerHTML = "Alphabet Characters not allowed", document.getElementById("errortripOpening").style.display = r ? "none" : "inline", r
}

function tripBook(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        r = t > 31 && 33 > t || t > 44 && 48 > t || t >= 48 && 57 >= t || t >= 65 && 90 >= t || t >= 97 && 122 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errortripBook").innerHTML = "Special Characters not allowed", document.getElementById("errortripBook").style.display = r ? "none" : "inline", r
}

function IsAdvanceAmount(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        r = t >= 48 && 57 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorAdvanceAmount").innerHTML = "Alphabet Characters not allowed", document.getElementById("errorAdvanceAmount").style.display = r ? "none" : "inline", r
}

function IsAdvancePlace(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        r = t > 31 && 33 > t || t > 39 && 42 > t || t >= 45 && 57 >= t || t >= 65 && 90 >= t || t >= 97 && 122 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorAdvancePlace").innerHTML = "Special Characters not allowed", document.getElementById("errorAdvancePlace").style.display = r ? "none" : "inline", r
}

function IsAdvancePaidby(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        r = t > 31 && 33 > t || t > 44 && 48 > t || t >= 48 && 57 >= t || t >= 65 && 90 >= t || t >= 97 && 122 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorAdvancePaidby").innerHTML = "Special Characters not allowed", document.getElementById("errorAdvancePaidby").style.display = r ? "none" : "inline", r
}

function IsAdvanceRefence(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        r = t > 31 && 33 > t || t > 44 && 48 > t || t >= 48 && 57 >= t || t >= 65 && 90 >= t || t >= 97 && 122 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorAdvanceRefence").innerHTML = "Special Characters not allowed", document.getElementById("errorAdvanceRefence").style.display = r ? "none" : "inline", r
}

function IsAdvanceRemarks(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        r = t > 31 && 33 > t || t > 39 && 42 > t || t >= 45 && 57 >= t || t >= 65 && 90 >= t || t >= 97 && 122 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorAdvanceRemarks").innerHTML = "Special Characters not allowed", document.getElementById("errorAdvanceRemarks").style.display = r ? "none" : "inline", r
}

function Isclosepaidto(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        r = t > 31 && 33 > t || t > 44 && 48 > t || t >= 48 && 57 >= t || t >= 65 && 90 >= t || t >= 97 && 122 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorClosepaidto").innerHTML = "Special Characters not allowed", document.getElementById("errorClosepaidto").style.display = r ? "none" : "inline", r
}

function IsClosingKM(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        r = t >= 48 && 57 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorClosingKM").innerHTML = "Alphabet Characters not allowed", document.getElementById("errorClosingKM").style.display = r ? "none" : "inline", r
}

function validateDispatchTrip() {
    document.getElementById("tripStutes").value = "DISPATCHED";
    var e = !0,
        t = document.getElementById("TripSelectVehicle").value,
        r = document.getElementById("vehicle_registration").value,
        n = document.getElementById("driverList").value,
        a = document.getElementById("tripFristDriverName").value,
        o = document.getElementById("driverList2").value,
        l = document.getElementById("tripSecDriverName").value,
        d = document.getElementById("TripRouteList").value,
        c = document.getElementById("routeName").value,
        i = document.getElementById("tripOpeningKM").value,
        m = document.getElementById("Cleaner").value;
    return validateMandatoryDispatch(t, r, n, a, o, l, m, document.getElementById("tripCleanerName").value, d, c, i) || (e = !1), "" == a && "" == l && (n == o ? (errorDriver2.innerHTML = "Driver 1 and Driver 2 same.", errorDriver2Name.innerHTML = "Driver 1 and Driver 2 same.", e = !1) : (errorDriver2.innerHTML = "", errorDriver2Name.innerHTML = "")), "" != a && "" != l && (n && 0 != n && o && 0 != o ? n == o && (errorDriver2.innerHTML = "Driver 1 and Driver 2 same.", errorDriver2Name.innerHTML = "Driver 1 and Driver 2 same.", e = !1) : (errorDriver2.innerHTML = "", errorDriver2Name.innerHTML = "")), "" == t && (document.getElementById("TripSelectVehicle").value = 0), "" == m && (document.getElementById("Cleaner").value = 0), "" == n && (document.getElementById("driverList").value = 0), "" == o && (document.getElementById("driverList2").value = 0), "" == d && (document.getElementById("TripRouteList").value = 0), e
}

function validateTrip() {
    document.getElementById("tripStutes").value = "SAVED";
    var e = !0,
        t = document.getElementById("TripSelectVehicle").value,
        r = document.getElementById("TripRouteList").value,
        n = document.getElementById("routeName").value,
        a = document.getElementById("driverList").value,
        o = document.getElementById("driverList2").value,
        r = (document.getElementById("tripFristDriverName").value, document.getElementById("tripSecDriverName").value, document.getElementById("TripRouteList").value),
        l = document.getElementById("Cleaner").value;
    return document.getElementById("tripCleanerName").value, validateMandatory(r, n) || (e = !1), "" == t && (document.getElementById("TripSelectVehicle").value = 0), "" == l && (document.getElementById("Cleaner").value = 0), "" == a && (document.getElementById("driverList").value = 0), "" == o && (document.getElementById("driverList2").value = 0), "" == r && (document.getElementById("TripRouteList").value = 0), e
}

function validateMandatoryDispatch(e, t, r, n, a, o, l, d, c, i, m) {
    var u = !0;
    return 0 != c && null != c || "" != i && null != i ? (errorRoute.innerHTML = "", document.getElementById("routeName").style.border = "", errorRouteName.innerHTML = "") : (errorRoute.innerHTML = "Please Select Route", document.getElementById("routeName").style.border = "2px solid #F00", errorRouteName.innerHTML = "Please Enter Route", u = !1), 0 != e && null != e || "" != t && null != t ? (errorVehicle.innerHTML = "", document.getElementById("vehicle_registration").style.border = "", errorVehicleName.innerHTML = "") : (errorVehicle.innerHTML = "Please Select Vehicle", document.getElementById("vehicle_registration").style.border = "", errorVehicleName.innerHTML = "Please Enter Vehicle Name", u = !1), (0 != r && null != r || "" != n && null != n) && (0 != a && null != a || "" != o && null != o) && (0 != r && null != r || "" != n && null != n ? (errorDriver1.innerHTML = "", document.getElementById("tripFristDriverName").style.border = "", errorDriverName.innerHTML = "") : (errorDriver1.innerHTML = "Please Select Driver", document.getElementById("tripFristDriverName").style.border = "2px solid #F00", errorDriverName.innerHTML = "Please Enter Name", u = !1), 0 != a && null != a || "" != o && null != o ? (errorDriver2.innerHTML = "", document.getElementById("tripSecDriverName").style.border = "", errorDriver2Name.innerHTML = "") : (errorDriver2.innerHTML = "Please Select Driver", document.getElementById("tripSecDriverName").style.border = "2px solid #F00", errorDriver2Name.innerHTML = "Please Enter Name", u = !1)), null == m || "" == m ? (document.getElementById("tripOpeningKM").style.border = "2px solid #F00", errorOpening.innerHTML = "Please Select Vehicle", u = !1) : document.getElementById("tripOpeningKM").style.border = "", u
}

function validateMandatory(e, t) {
    var r = !0;
    return 0 != e && null != e || "" != t && null != t ? (errorRoute.innerHTML = "", document.getElementById("routeName").style.border = "", errorRouteName.innerHTML = "") : (errorRoute.innerHTML = "Please Select Route", document.getElementById("routeName").style.border = "2px solid #F00", errorRouteName.innerHTML = "Please Enter Route", r = !1), r
}

function driverEditSelect() {}

function visibility(e, t) {
    var r = document.getElementById(e),
        n = document.getElementById(t);
    "block" == r.style.display ? (r.style.display = "none", n.style.display = "block") : (r.style.display = "block", n.style.display = "none")
}
var specialKeys = new Array;
specialKeys.push(8), specialKeys.push(9), specialKeys.push(46), specialKeys.push(36), specialKeys.push(35), specialKeys.push(37), specialKeys.push(39), $(document).ready(function() {
    $("#TripSelectVehicle").change(function() {
        0 != $(this).val() && $.getJSON("getTripVehicleOdoMerete.in", {
            FuelvehicleID: $(this).val(),
            ajax: "true"
        }, function(e) {
            var t = "",
                r = "";
            t = e.vehicle_Odometer, r = e.vehicle_Group, document.getElementById("tripOpeningKM").placeholder = t, document.getElementById("tripOpeningKM").value = t, document.getElementById("vehicle_Group").value = r;
            $('#VEHICLE_GROUP_ID').val(e.vehicleGroupId);
            $('#currentOdometer').val(t);
            $('#maxAllowedOdometer').val(e.vehicle_ExpectedOdameter);
            if(vehicleStatus != undefined && vehicleStatus != 0 && vehicleStatus != 1){
            	$("#vehicle_Group").val("");
            }
        })
    }), $("#SelectVehicle").change(function() {
        0 != $(this).val() && $.getJSON("getTripVehicleOdoMerete.in", {
            FuelvehicleID: $(this).val(),
            ajax: "true"
        }, function(e) {
            var t = "",
                r = "";
            t = e.vehicle_Odometer, r = e.vehicle_Group, document.getElementById("tripOpeningKM").placeholder = t, document.getElementById("tripOpeningKM").value = t, document.getElementById("vehicle_Group").value = r
            $('#currentOdometer').val(t);
            $('#maxAllowedOdometer').val(e.vehicle_ExpectedOdameter);
        })
    })
}), $(document).ready(function() {
    $("#TripSelectVehicle" ).select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getVehicleListTEST.in",
            dataType: "json",
            type: "POST",
            contentType: "application/json",
            quietMillis: 50,
            data: function(e) {
            	vehicleStatus = e.vStatusId;
            		$("#TripSelectVehicle").change(function(e){
            			if( e.added != undefined &&  e.added.ststusId != 1){
            				vehicleStatus = e.added.ststusId;
            				showMessage('errors','Vehicle in ' + e.added.vStatus + ' Status you can not create TripCollection');
            				/*var textVal = $("#TripSelectVehicle").prev().children()[0];
            				$($(textVal).children()[0]).text("");*/
            				
            				var textVal =$("#TripSelectVehicle").select2("val", "") ; 
            			}
            			
            		})
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
                            id: e.vid,
                            ststusId : e.vStatusId,
                            vStatus	 : e.vehicle_Status
                        }
                    })
                }
            }
        }
    }), $("#driverList").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getDriver1List.in",
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
                            text: e.driver_empnumber + "-" + e.driver_firstname + " " + e.driver_Lastname,
                            slug: e.slug,
                            id: e.driver_id
                        }
                    })
                }
            }
        }
    }), $("#ConductorList").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getConductorList.in",
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
                            text: e.driver_empnumber + "-" + e.driver_firstname + " " + e.driver_Lastname,
                            slug: e.slug,
                            id: e.driver_id
                        }
                    })
                }
            }
        }
    }), $("#Cleaner").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getDriverCleanerList.in",
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
                            text: e.driver_empnumber + "-" + e.driver_firstname + " " + e.driver_Lastname,
                            slug: e.slug,
                            id: e.driver_id
                        }
                    })
                }
            }
        }
    }), $("#TripRouteList").select2({
        ajax: {
            url: "getTripRouteList.in",
            dataType: "json",
            type: "POST",
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
                            text: e.routeNo + " " + e.routeName,
                            slug: e.slug,
                            id: e.routeID
                        }
                    })
                }
            }
        }
    })
}), $(document).ready(function() {
    function e(e) {
        for (var p = document.getElementsByTagName("textarea"), v = 0; v < p.length; v++) com_satheesh.EVENTS.addEventHandler(p[v], "focus", t, !1), com_satheesh.EVENTS.addEventHandler(p[v], "blur", r, !1);
        for (p = document.getElementsByTagName("input"), v = 0; v < p.length; v++) e.indexOf(-1 != p[v].getAttribute("type")) && (com_satheesh.EVENTS.addEventHandler(p[v], "focus", t, !1), com_satheesh.EVENTS.addEventHandler(p[v], "blur", r, !1));
        com_satheesh.EVENTS.addEventHandler(document.getElementById("formTripCollection"), "submit", y, !1), com_satheesh.EVENTS.addEventHandler(document.getElementById("formEditTripCollection"), "submit", g, !1), document.getElementsByTagName("input")[0].focus(), com_satheesh.EVENTS.addEventHandler(document.forms[0].TripSelectVehicle, "blur", n, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].vehicle_Group, "blur", a, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].driverList, "blur", o, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].ConductorList, "blur", l, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].TripRouteList, "blur", d, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].tripDate, "blur", c, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].tripOpeningKM, "blur", i, !1)
    }

    function t(e) {
        var t = com_satheesh.EVENTS.getEventTarget(e);
        null != t && (t.style.backgroundColor = p)
    }

    function r(e) {
        var t = com_satheesh.EVENTS.getEventTarget(e);
        null != t && (t.style.backgroundColor = "")
    }

    function n() {
        var e = document.getElementById("TripSelectVehicle"),
            t = null != e.value && 0 != e.value.length,
            r = document.getElementById("grpvehicleName");
        if (null != r) return t ? (r.className = "form-group has-success has-feedback", document.getElementById("vehicleNameIcon").className = "fa fa-check form-text-feedback", document.getElementById("vehicleNameErrorMsg").innerHTML = "") : (r.className = "form-group has-error has-feedback", document.getElementById("vehicleNameIcon").className = "fa fa-remove form-text-feedback", document.getElementById("vehicleNameErrorMsg").innerHTML = "Please select vehicle name"), t
    }

    function a() {
        var e = document.getElementById("vehicle_Group"),
            t = null != e.value && 0 != e.value.length,
            r = document.getElementById("grpvehicleGroup");
        if (null != r) return t ? (r.className = "form-group has-success has-feedback", document.getElementById("vehicleGroupIcon").className = "fa fa-check form-text-feedback", document.getElementById("vehicleGroupErrorMsg").innerHTML = "") : (r.className = "form-group has-error has-feedback", document.getElementById("vehicleGroupIcon").className = "fa fa-remove form-text-feedback", document.getElementById("vehicleGroupErrorMsg").innerHTML = "Please select vehicle name"), t
    }

    function o() {
        var e = document.getElementById("driverList"),
            t = null != e.value && 0 != e.value.length,
            r = document.getElementById("grpdriverName");
        if (null != r) return t ? (r.className = "form-group has-success has-feedback", document.getElementById("driverNameIcon").className = "fa fa-check form-text-feedback", document.getElementById("driverNameErrorMsg").innerHTML = "") : (r.className = "form-group has-error has-feedback", document.getElementById("driverNameIcon").className = "fa fa-remove form-text-feedback", document.getElementById("driverNameErrorMsg").innerHTML = "Please select driver name"), t
    }

    function l() {
        var e = document.getElementById("ConductorList"),
            t = null != e.value && 0 != e.value.length,
            r = document.getElementById("grpconductorName");
        return null != r && (t ? (r.className = "form-group has-success has-feedback", document.getElementById("conductorNameIcon").className = "fa fa-check  form-text-feedback", document.getElementById("conductorNameErrorMsg").innerHTML = "") : (r.className = "form-group has-error has-feedback", document.getElementById("conductorNameIcon").className = "fa fa-remove form-text-feedback", document.getElementById("conductorNameErrorMsg").innerHTML = "Please select conductor name")), t
    }

    function d() {
        var e = document.getElementById("TripRouteList"),
            t = null != e.value && 0 != e.value.length,
            r = document.getElementById("grptripRouteName");
        return null != r && (t ? (r.className = "form-group has-success has-feedback", document.getElementById("tripRouteNameIcon").className = "fa fa-check  form-text-feedback", document.getElementById("tripRouteNameErrorMsg").innerHTML = "") : (r.className = "form-group has-error has-feedback", document.getElementById("tripRouteNameIcon").className = "fa fa-remove form-text-feedback", document.getElementById("tripRouteNameErrorMsg").innerHTML = "Please select route service")), t
    }

    function c() {
        var e = document.getElementById("tripDate"),
            t = null != e.value && 0 != e.value.length,
            r = document.getElementById("grptripDate");
        return null != r && (t ? (r.className = "form-group has-success has-feedback", document.getElementById("tripDateIcon").className = "fa fa-check  form-text-feedback", document.getElementById("tripDateErrorMsg").innerHTML = "") : (r.className = "form-group has-error has-feedback", document.getElementById("tripDateIcon").className = "fa fa-remove form-text-feedback", document.getElementById("tripDateErrorMsg").innerHTML = "Please select trip date")), t
    }

    function i() {
        var e = document.getElementById("tripOpeningKM"),
            t = null != e.value && 0 != e.value.length,
            r = document.getElementById("grptripOpeningKM");
        return null != r && (t ? (r.className = "form-group has-success has-feedback", document.getElementById("tripOpeningKMIcon").className = "fa fa-check  form-text-feedback", document.getElementById("tripOpeningKMErrorMsg").innerHTML = "") : (r.className = "form-group has-error has-feedback", document.getElementById("tripOpeningKMIcon").className = "fa fa-remove form-text-feedback", document.getElementById("tripOpeningKMErrorMsg").innerHTML = "Please enter opening km")), t
    }

   
    function y(e) {
        var t = n();
        t &= a(), t &= o(), t &= l(), t &= d(), t &= c(), t &= i() || com_satheesh.EVENTS.preventDefault(e)
    }

    function g(e) {
        var t = n();
        t &= a(), t &= o(), t &= l(), t &= d(), t &= c(), t &= i() || com_satheesh.EVENTS.preventDefault(e)
    }
    var p = "#FFC";
    com_satheesh.EVENTS.addEventHandler(window, "load", function() {
        e("text")
    }, !1)
});

function validateOdometer(){
	if(Number($('#currentOdometer').val()) == 0){
		return true;
	}
	if(Number($('#TripSelectVehicle').val()) <= 0){
		showMessage('errors', 'Please Select Vehicle !');
		return false;
	}
	if(Number($('#driverList').val()) <= 0){
		showMessage('errors', 'Please Select Driver !');
		return false;
	}
	if(Number($('#ConductorList').val()) <= 0){
		showMessage('errors', 'Please Select Conductor !');
		return false;
	}
	
	if(Number($('#TripRouteList').val()) <= 0){
		showMessage('errors', 'Please Select Route !');
		return false;
	}
	if(Number($('#tripOpeningKM').val()) <= 0){
		showMessage('errors', 'Please enter Opening KM  !');
		return false;
	}
	var tripOpeningKM 		= Number($('#tripOpeningKM').val());
	var maxAllowedOdometer  = Number($('#maxAllowedOdometer').val());
	var currentOdometer		= Number($('#currentOdometer').val());
	if(tripOpeningKM < currentOdometer || tripOpeningKM > (maxAllowedOdometer + currentOdometer)){
		showMessage('errors', 'Please Enter Opening KM between '+currentOdometer+" and "+(maxAllowedOdometer + currentOdometer));
		return false;
	}
	return true;
}