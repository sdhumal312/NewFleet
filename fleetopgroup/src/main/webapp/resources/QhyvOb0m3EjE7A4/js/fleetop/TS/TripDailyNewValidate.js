$(document).ready(function(){$("#TripSheetTable").DataTable({sScrollX:"100%",bScrollcollapse:!0,dom:"Blfrtip",buttons:["excel","print"],order:[[0,"desc"]]})});
$(document).ready(function() {
    function e(e) {
        for (var c = document.getElementsByTagName("textarea"), l = 0; l < c.length; l++) com_satheesh.EVENTS.addEventHandler(c[l], "focus", t, !1), com_satheesh.EVENTS.addEventHandler(c[l], "blur", a, !1);
        c = document.getElementsByTagName("input");
        for (l = 0; l < c.length; l++) e.indexOf(-1 != c[l].getAttribute("type")) && (com_satheesh.EVENTS.addEventHandler(c[l], "focus", t, !1), com_satheesh.EVENTS.addEventHandler(c[l], "blur", a, !1));
        com_satheesh.EVENTS.addEventHandler(document.getElementById("formCloseTrip"), "submit", r, !1), document.getElementsByTagName("input")[0].focus(), com_satheesh.EVENTS.addEventHandler(document.forms[0].VehicleGroupSelect, "blur", n, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].ReportDailydate, "blur", o, !1)
    }

    function t(e) {
        var t = com_satheesh.EVENTS.getEventTarget(e);
        null != t && (t.style.backgroundColor = c)
    }

    function a(e) {
        var t = com_satheesh.EVENTS.getEventTarget(e);
        null != t && (t.style.backgroundColor = "")
    }

    function n() {
        var e = document.getElementById("VehicleGroupSelect"),
            t = null != e.value && 0 != e.value.length,
            a = document.getElementById("grpVehicleGroupSelect");
        if (null != a) return t ? (a.className = "form-group has-success has-feedback", document.getElementById("VehicleGroupSelectIcon").className = "fa fa-check form-text-feedback", document.getElementById("VehicleGroupSelectErrorMsg").innerHTML = "") : (a.className = "form-group has-error has-feedback", document.getElementById("VehicleGroupSelectIcon").className = "fa fa-remove form-text-feedback", document.getElementById("VehicleGroupSelectErrorMsg").innerHTML = "Please select Close Group name"), t
    }

    function o() {
        var e = document.getElementById("ReportDailydate"),
            t = null != e.value && 0 != e.value.length,
            a = document.getElementById("grpReportDailydate");
        if (null != a) return t ? (a.className = "form-group has-success has-feedback", document.getElementById("ReportDailydateIcon").className = "fa fa-check form-text-feedback", document.getElementById("ReportDailydateErrorMsg").innerHTML = "") : (a.className = "form-group has-error has-feedback", document.getElementById("ReportDailydateIcon").className = "fa fa-remove form-text-feedback", document.getElementById("ReportDailydateErrorMsg").innerHTML = "Please select date "), t
    }

    function r(e) {
        var t = n();
        (t &= o()) || com_satheesh.EVENTS.preventDefault(e)
    }
    var c = "#FFC";
    com_satheesh.EVENTS.addEventHandler(window, "load", function() {
        e("text")
    }, !1)
});
