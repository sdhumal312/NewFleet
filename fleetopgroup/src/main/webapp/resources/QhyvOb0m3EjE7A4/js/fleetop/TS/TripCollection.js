function RouteName(a) {
    var b = 0 == a.keyCode ? a.charCode : a.keyCode,
        c = b > 31 && 33 > b || b > 44 && 48 > b || b >= 48 && 57 >= b || b >= 65 && 90 >= b || b >= 97 && 122 >= b || -1 != specialKeys.indexOf(a.keyCode) && a.charCode != a.keyCode;
    return document.getElementById("errorRouteName").innerHTML = "Special Characters not allowed", document.getElementById("errorRouteName").style.display = c ? "none" : "inline", c
}

function RouteNo(a) {
    var b = 0 == a.keyCode ? a.charCode : a.keyCode,
        c = b > 31 && 33 > b || b > 44 && 48 > b || b >= 48 && 57 >= b || b >= 65 && 90 >= b || b >= 97 && 122 >= b || -1 != specialKeys.indexOf(a.keyCode) && a.charCode != a.keyCode;
    return document.getElementById("errorRouteNo").innerHTML = "Special Characters not allowed", document.getElementById("errorRouteNo").style.display = c ? "none" : "inline", c
}

function RouteKM(a) {
    var b = 0 == a.keyCode ? a.charCode : a.keyCode,
        c = b >= 48 && 57 >= b || -1 != specialKeys.indexOf(a.keyCode) && a.charCode != a.keyCode;
    return document.getElementById("errorRouteKM").innerHTML = "Alphabet Characters not allowed", document.getElementById("errorRouteKM").style.display = c ? "none" : "inline", c
}

function RouteRemarks(a) {
    var b = 0 == a.keyCode ? a.charCode : a.keyCode,
        c = b > 31 && 33 > b || b > 39 && 42 > b || b >= 45 && 57 >= b || b >= 65 && 90 >= b || b >= 97 && 122 >= b || -1 != specialKeys.indexOf(a.keyCode) && a.charCode != a.keyCode;
    return document.getElementById("errorRouteRemarks").innerHTML = "Special Characters not allowed", document.getElementById("errorRouteRemarks").style.display = c ? "none" : "inline", c
}

function tripOpening(a) {
    var b = 0 == a.keyCode ? a.charCode : a.keyCode,
        c = b >= 48 && 57 >= b || -1 != specialKeys.indexOf(a.keyCode) && a.charCode != a.keyCode;
    return document.getElementById("errortripOpening").innerHTML = "Alphabet Characters not allowed", document.getElementById("errortripOpening").style.display = c ? "none" : "inline", c
}

function tripBook(a) {
    var b = 0 == a.keyCode ? a.charCode : a.keyCode,
        c = b > 31 && 33 > b || b > 44 && 48 > b || b >= 48 && 57 >= b || b >= 65 && 90 >= b || b >= 97 && 122 >= b || -1 != specialKeys.indexOf(a.keyCode) && a.charCode != a.keyCode;
    return document.getElementById("errortripBook").innerHTML = "Special Characters not allowed", document.getElementById("errortripBook").style.display = c ? "none" : "inline", c
}

function IsAdvanceAmount(a) {
    var b = 0 == a.keyCode ? a.charCode : a.keyCode,
        c = b >= 48 && 57 >= b || -1 != specialKeys.indexOf(a.keyCode) && a.charCode != a.keyCode;
    return document.getElementById("errorAdvanceAmount").innerHTML = "Alphabet Characters not allowed", document.getElementById("errorAdvanceAmount").style.display = c ? "none" : "inline", c
}

function IsAdvancePlace(a) {
    var b = 0 == a.keyCode ? a.charCode : a.keyCode,
        c = b > 31 && 33 > b || b > 39 && 42 > b || b >= 45 && 57 >= b || b >= 65 && 90 >= b || b >= 97 && 122 >= b || -1 != specialKeys.indexOf(a.keyCode) && a.charCode != a.keyCode;
    return document.getElementById("errorAdvancePlace").innerHTML = "Special Characters not allowed", document.getElementById("errorAdvancePlace").style.display = c ? "none" : "inline", c
}

function IsAdvancePaidby(a) {
    var b = 0 == a.keyCode ? a.charCode : a.keyCode,
        c = b > 31 && 33 > b || b > 44 && 48 > b || b >= 48 && 57 >= b || b >= 65 && 90 >= b || b >= 97 && 122 >= b || -1 != specialKeys.indexOf(a.keyCode) && a.charCode != a.keyCode;
    return document.getElementById("errorAdvancePaidby").innerHTML = "Special Characters not allowed", document.getElementById("errorAdvancePaidby").style.display = c ? "none" : "inline", c
}

function IsAdvanceRefence(a) {
    var b = 0 == a.keyCode ? a.charCode : a.keyCode,
        c = b > 31 && 33 > b || b > 44 && 48 > b || b >= 48 && 57 >= b || b >= 65 && 90 >= b || b >= 97 && 122 >= b || -1 != specialKeys.indexOf(a.keyCode) && a.charCode != a.keyCode;
    return document.getElementById("errorAdvanceRefence").innerHTML = "Special Characters not allowed", document.getElementById("errorAdvanceRefence").style.display = c ? "none" : "inline", c
}

function IsAdvanceRemarks(a) {
    var b = 0 == a.keyCode ? a.charCode : a.keyCode,
        c = b > 31 && 33 > b || b > 39 && 42 > b || b >= 45 && 57 >= b || b >= 65 && 90 >= b || b >= 97 && 122 >= b || -1 != specialKeys.indexOf(a.keyCode) && a.charCode != a.keyCode;
    return document.getElementById("errorAdvanceRemarks").innerHTML = "Special Characters not allowed", document.getElementById("errorAdvanceRemarks").style.display = c ? "none" : "inline", c
}

function Isclosepaidto(a) {
    var b = 0 == a.keyCode ? a.charCode : a.keyCode,
        c = b > 31 && 33 > b || b > 44 && 48 > b || b >= 48 && 57 >= b || b >= 65 && 90 >= b || b >= 97 && 122 >= b || -1 != specialKeys.indexOf(a.keyCode) && a.charCode != a.keyCode;
    return document.getElementById("errorClosepaidto").innerHTML = "Special Characters not allowed", document.getElementById("errorClosepaidto").style.display = c ? "none" : "inline", c
}

function IsClosingKM(a) {
    var b = 0 == a.keyCode ? a.charCode : a.keyCode,
        c = b >= 48 && 57 >= b || -1 != specialKeys.indexOf(a.keyCode) && a.charCode != a.keyCode;
    return document.getElementById("errorClosingKM").innerHTML = "Alphabet Characters not allowed", document.getElementById("errorClosingKM").style.display = c ? "none" : "inline", c
}

function validateDispatchTrip() {
    document.getElementById("tripStutes").value = "DISPATCHED";
    var a = !0,
        b = document.getElementById("TripSelectVehicle").value,
        c = document.getElementById("vehicle_registration").value,
        d = document.getElementById("driverList").value,
        e = document.getElementById("tripFristDriverName").value,
        f = document.getElementById("driverList2").value,
        g = document.getElementById("tripSecDriverName").value,
        h = document.getElementById("TripRouteList").value,
        i = document.getElementById("routeName").value,
        j = document.getElementById("tripOpeningKM").value,
        k = document.getElementById("Cleaner").value,
        l = document.getElementById("tripCleanerName").value;
    return validateMandatoryDispatch(b, c, d, e, f, g, k, l, h, i, j) || (a = !1), "" == e && "" == g && (d == f ? (errorDriver2.innerHTML = "Driver 1 and Driver 2 same.", errorDriver2Name.innerHTML = "Driver 1 and Driver 2 same.", a = !1) : (errorDriver2.innerHTML = "", errorDriver2Name.innerHTML = "")), "" != e && "" != g && (d && 0 != d && f && 0 != f ? d == f && (errorDriver2.innerHTML = "Driver 1 and Driver 2 same.", errorDriver2Name.innerHTML = "Driver 1 and Driver 2 same.", a = !1) : (errorDriver2.innerHTML = "", errorDriver2Name.innerHTML = "")), "" == b && (document.getElementById("TripSelectVehicle").value = 0), "" == k && (document.getElementById("Cleaner").value = 0), "" == d && (document.getElementById("driverList").value = 0), "" == f && (document.getElementById("driverList2").value = 0), "" == h && (document.getElementById("TripRouteList").value = 0), a
}

function validateTrip() {
    document.getElementById("tripStutes").value = "SAVED";
    var a = !0,
        b = document.getElementById("TripSelectVehicle").value,
        c = document.getElementById("TripRouteList").value,
        d = document.getElementById("routeName").value,
        e = document.getElementById("driverList").value,
        f = document.getElementById("driverList2").value,
        c = (document.getElementById("tripFristDriverName").value, document.getElementById("tripSecDriverName").value, document.getElementById("TripRouteList").value),
        g = document.getElementById("Cleaner").value;
    return document.getElementById("tripCleanerName").value, validateMandatory(c, d) || (a = !1), "" == b && (document.getElementById("TripSelectVehicle").value = 0), "" == g && (document.getElementById("Cleaner").value = 0), "" == e && (document.getElementById("driverList").value = 0), "" == f && (document.getElementById("driverList2").value = 0), "" == c && (document.getElementById("TripRouteList").value = 0), a
}

function validateMandatoryDispatch(a, b, c, d, e, f, g, h, i, j, k) {
    var l = !0;
    return 0 != i && null != i || "" != j && null != j ? (errorRoute.innerHTML = "", document.getElementById("routeName").style.border = "", errorRouteName.innerHTML = "") : (errorRoute.innerHTML = "Please Select Route", document.getElementById("routeName").style.border = "2px solid #F00", errorRouteName.innerHTML = "Please Enter Route", l = !1), 0 != a && null != a || "" != b && null != b ? (errorVehicle.innerHTML = "", document.getElementById("vehicle_registration").style.border = "", errorVehicleName.innerHTML = "") : (errorVehicle.innerHTML = "Please Select Vehicle", document.getElementById("vehicle_registration").style.border = "", errorVehicleName.innerHTML = "Please Enter Vehicle Name", l = !1), (0 != c && null != c || "" != d && null != d) && (0 != e && null != e || "" != f && null != f) && (0 != c && null != c || "" != d && null != d ? (errorDriver1.innerHTML = "", document.getElementById("tripFristDriverName").style.border = "", errorDriverName.innerHTML = "") : (errorDriver1.innerHTML = "Please Select Driver", document.getElementById("tripFristDriverName").style.border = "2px solid #F00", errorDriverName.innerHTML = "Please Enter Name", l = !1), 0 != e && null != e || "" != f && null != f ? (errorDriver2.innerHTML = "", document.getElementById("tripSecDriverName").style.border = "", errorDriver2Name.innerHTML = "") : (errorDriver2.innerHTML = "Please Select Driver", document.getElementById("tripSecDriverName").style.border = "2px solid #F00", errorDriver2Name.innerHTML = "Please Enter Name", l = !1)), null == k || "" == k ? (document.getElementById("tripOpeningKM").style.border = "2px solid #F00", errorOpening.innerHTML = "Please Select Vehicle", l = !1) : document.getElementById("tripOpeningKM").style.border = "", l
}

function validateMandatory(a, b) {
    var c = !0;
    return 0 != a && null != a || "" != b && null != b ? (errorRoute.innerHTML = "", document.getElementById("routeName").style.border = "", errorRouteName.innerHTML = "") : (errorRoute.innerHTML = "Please Select Route", document.getElementById("routeName").style.border = "2px solid #F00", errorRouteName.innerHTML = "Please Enter Route", c = !1), c
}

function driverEditSelect() {}

function visibility(a, b) {
    var c = document.getElementById(a),
        d = document.getElementById(b);
    "block" == c.style.display ? (c.style.display = "none", d.style.display = "block") : (c.style.display = "block", d.style.display = "none")
}
var specialKeys = new Array;
specialKeys.push(8), specialKeys.push(9), specialKeys.push(46), specialKeys.push(36), specialKeys.push(35), specialKeys.push(37), specialKeys.push(39), $(document).ready(function() {
    $("#TripSelectVehicle").change(function() {
        0 != $(this).val() && $.getJSON("getTripVehicleOdoMerete.in", {
            FuelvehicleID: $(this).val(),
            ajax: "true"
        }, function(a) {
            var b = "",
                c = "";
            b = a.vehicle_Odometer, c = a.vehicle_Group, document.getElementById("tripOpeningKM").placeholder = b, document.getElementById("tripOpeningKM").value = b, document.getElementById("vehicle_Group").value = c;
            $('#VEHICLE_GROUP_ID').val(a.vehicleGroupId);
        })
    }), $("#SelectVehicle").change(function() {
        0 != $(this).val() && $.getJSON("getTripVehicleOdoMerete.in", {
            FuelvehicleID: $(this).val(),
            ajax: "true"
        }, function(a) {
            var b = "",
                c = "";
            b = a.vehicle_Odometer, c = a.vehicle_Group, document.getElementById("tripOpeningKM").placeholder = b, document.getElementById("tripOpeningKM").value = b, document.getElementById("vehicle_Group").value = c
        })
    })
}), $(document).ready(function() {
    $("#TripSelectVehicle").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getVehicleListTEST.in",
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
                            text: a.vehicle_registration,
                            slug: a.slug,
                            id: a.vid
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
            data: function(a) {
                return {
                    term: a
                }
            },
            results: function(a) {
                return {
                    results: $.map(a, function(a) {
                        return {
                            text: a.driver_empnumber + "-" + a.driver_firstname + " " + a.driver_Lastname,
                            slug: a.slug,
                            id: a.driver_id
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
            data: function(a) {
                return {
                    term: a
                }
            },
            results: function(a) {
                return {
                    results: $.map(a, function(a) {
                        return {
                            text: a.driver_empnumber + "-" + a.driver_firstname + " " + a.driver_Lastname,
                            slug: a.slug,
                            id: a.driver_id
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
            data: function(a) {
                return {
                    term: a
                }
            },
            results: function(a) {
                return {
                    results: $.map(a, function(a) {
                        return {
                            text: a.driver_empnumber + "-" + a.driver_firstname + " " + a.driver_Lastname,
                            slug: a.slug,
                            id: a.driver_id
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
            data: function(a) {
                return {
                    term: a
                }
            },
            results: function(a) {
                return {
                    results: $.map(a, function(a) {
                        return {
                            text: a.routeNo + " " + a.routeName,
                            slug: a.slug,
                            id: a.routeID
                        }
                    })
                }
            }
        }
    })
});

$(document).ready(function() {
    function f(a) {
        for (var b = document.getElementsByTagName("textarea"), c = 0; c < b.length; c++) com_satheesh.EVENTS.addEventHandler(b[c], "focus", g, !1), com_satheesh.EVENTS.addEventHandler(b[c], "blur", h, !1);
        b = document.getElementsByTagName("input");
        for (var c = 0; c < b.length; c++) a.indexOf(-1 != b[c].getAttribute("type")) && (com_satheesh.EVENTS.addEventHandler(b[c], "focus", g, !1), com_satheesh.EVENTS.addEventHandler(b[c], "blur", h, !1));
        com_satheesh.EVENTS.addEventHandler(document.getElementById("formTripCollection"), "submit", q, !1), com_satheesh.EVENTS.addEventHandler(document.getElementById("formEditTripCollection"), "submit", r, !1), document.getElementsByTagName("input")[0].focus(), com_satheesh.EVENTS.addEventHandler(document.forms[0].TripSelectVehicle, "blur", i, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].vehicle_Group, "blur", j, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].driverList, "blur", k, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].ConductorList, "blur", l, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].TripRouteList, "blur", m, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].tripDate, "blur", n, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].tripliter, "blur", o, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].tripSingl, "blur", p, !1)
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
        var a = document.getElementById("TripSelectVehicle"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpvehicleName");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("vehicleNameIcon").className = "fa fa-check form-text-feedback", document.getElementById("vehicleNameErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("vehicleNameIcon").className = "fa fa-remove form-text-feedback", document.getElementById("vehicleNameErrorMsg").innerHTML = "Please select vehicle name"), b
    }

    function j() {
        var a = document.getElementById("vehicle_Group"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpvehicleGroup");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("vehicleGroupIcon").className = "fa fa-check form-text-feedback", document.getElementById("vehicleGroupErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("vehicleGroupIcon").className = "fa fa-remove form-text-feedback", document.getElementById("vehicleGroupErrorMsg").innerHTML = "Please select vehicle name"), b
    }

    function k() {
        var a = document.getElementById("driverList"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpdriverName");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("driverNameIcon").className = "fa fa-check form-text-feedback", document.getElementById("driverNameErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("driverNameIcon").className = "fa fa-remove form-text-feedback", document.getElementById("driverNameErrorMsg").innerHTML = "Please select driver name"), b
    }

    function l() {
        var a = document.getElementById("ConductorList"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpconductorName");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("conductorNameIcon").className = "fa fa-check  form-text-feedback", document.getElementById("conductorNameErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("conductorNameIcon").className = "fa fa-remove form-text-feedback", document.getElementById("conductorNameErrorMsg").innerHTML = "Please select conductor name")), b
    }

    function m() {
        var a = document.getElementById("TripRouteList"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grptripRouteName");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("tripRouteNameIcon").className = "fa fa-check  form-text-feedback", document.getElementById("tripRouteNameErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("tripRouteNameIcon").className = "fa fa-remove form-text-feedback", document.getElementById("tripRouteNameErrorMsg").innerHTML = "Please select route service")), b
    }

    function n() {
        var a = document.getElementById("tripDate"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grptripDate");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("tripDateIcon").className = "fa fa-check  form-text-feedback", document.getElementById("tripDateErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("tripDateIcon").className = "fa fa-remove form-text-feedback", document.getElementById("tripDateErrorMsg").innerHTML = "Please select trip date")), b
    }

    function o() {
        var a = document.getElementById("tripliter"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grptripliter");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("tripliterIcon").className = "fa fa-check  form-text-feedback", document.getElementById("tripliterErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("tripliterIcon").className = "fa fa-remove form-text-feedback", document.getElementById("tripliterErrorMsg").innerHTML = "Please enter diesel liter")), b
    }

    function p() {
        var a = document.getElementById("tripSingl"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grptripSingl");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("tripSinglIcon").className = "fa fa-check  form-text-feedback", document.getElementById("tripSinglErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("tripSinglIcon").className = "fa fa-remove form-text-feedback", document.getElementById("tripSinglErrorMsg").innerHTML = "Please enter running singl")), b
    }

    function q(a) {
        var b = i();
        b &= j(), b &= k(), b &= l(), b &= m(), b &= n(), b &= o(), (b &= p()) || com_satheesh.EVENTS.preventDefault(a)
    }

    function r(a) {
        var b = i();
        b &= j(), b &= k(), b &= l(), b &= m(), b &= n(), b &= o(), (b &= p()) || com_satheesh.EVENTS.preventDefault(a)
    }
    var b = "#FFC";
    com_satheesh.EVENTS.addEventHandler(window, "load", function() {
        f("text")
    }, !1)
});