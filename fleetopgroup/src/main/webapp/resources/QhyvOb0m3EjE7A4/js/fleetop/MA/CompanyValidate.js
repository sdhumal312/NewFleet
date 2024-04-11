$(document).ready(function() {
    function a(a) {
        for (var k = document.getElementsByTagName("textarea"), l = 0; l < k.length; l++) com_satheesh.EVENTS.addEventHandler(k[l], "focus", b, !1), com_satheesh.EVENTS.addEventHandler(k[l], "blur", c, !1);
        k = document.getElementsByTagName("input");
        for (var l = 0; l < k.length; l++) a.indexOf(-1 != k[l].getAttribute("type")) && (com_satheesh.EVENTS.addEventHandler(k[l], "focus", b, !1), com_satheesh.EVENTS.addEventHandler(k[l], "blur", c, !1));
        com_satheesh.EVENTS.addEventHandler(document.getElementById("formCreateCompany"), "submit", i, !1), com_satheesh.EVENTS.addEventHandler(document.getElementById("formEditCreateCompany"), "submit", j, !1), document.getElementsByTagName("input")[0].focus(), com_satheesh.EVENTS.addEventHandler(document.forms[0].companyName, "blur", d, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].companyType, "blur", e, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].fileselect, "blur", f, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].companyEmail, "blur", g, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].companyMobile, "blur", h, !1)
    }

    function b(a) {
        var b = com_satheesh.EVENTS.getEventTarget(a);
        null != b && (b.style.backgroundColor = k)
    }

    function c(a) {
        var b = com_satheesh.EVENTS.getEventTarget(a);
        null != b && (b.style.backgroundColor = "")
    }

    function d() {
        var a = document.getElementById("companyName"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpcompanyName");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("companyNameIcon").className = "fa fa-check form-text-feedback", document.getElementById("companyNameErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("companyNameIcon").className = "fa fa-remove form-text-feedback", document.getElementById("companyNameErrorMsg").innerHTML = "Please enter company name"), b
    }
    
    function dC() {
        var a = document.getElementById("companyCode"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpcompanyCode");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("companyCodeIcon").className = "fa fa-check form-text-feedback", document.getElementById("companyCodeErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("companyCodeIcon").className = "fa fa-remove form-text-feedback", document.getElementById("companyCodeErrorMsg").innerHTML = "Please enter company code"), b
    }

    function e() {
        var a = document.getElementById("companyType"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpcompanyType");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("companyTypeIcon").className = "fa fa-check form-text-feedback", document.getElementById("companyTypeErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("companyTypeIcon").className = "fa fa-remove form-text-feedback", document.getElementById("companyTypeErrorMsg").innerHTML = "Please enter company type"), b
    }

    function f() {
        var a = document.getElementById("fileselect"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpcompanyLogo");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("companyLogoIcon").className = "fa fa-check form-text-feedback", document.getElementById("companyLogoErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("companyLogoIcon").className = "fa fa-remove form-text-feedback", document.getElementById("companyLogoErrorMsg").innerHTML = "Please select company Logo"), b
    }

    function g() {
        var a = document.getElementById("companyEmail"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpcompanyEmail");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("companyEmailIcon").className = "fa fa-check  form-text-feedback", document.getElementById("companyEmailErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("companyEmailIcon").className = "fa fa-remove form-text-feedback", document.getElementById("companyEmailErrorMsg").innerHTML = "Please enter company email")), b
    }

    function h() {
        var a = document.getElementById("companyMobile"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpcompanyMobile");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("companyMobileIcon").className = "fa fa-check  form-text-feedback", document.getElementById("companyMobileErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("companyMobileIcon").className = "fa fa-remove form-text-feedback", document.getElementById("companyMobileErrorMsg").innerHTML = "Please enter company mobile no")), b
    }

    function i(a) {
        var b = d();
        b = dC(), b &= e(), b &= f(), b &= g(), (b &= h()) || com_satheesh.EVENTS.preventDefault(a)
    }

    function j(a) {
        var b = d();
        b = dC(), b &= e(), b &= g(), (b &= h()) || com_satheesh.EVENTS.preventDefault(a)
    }
    var k = "#FFC";
    com_satheesh.EVENTS.addEventHandler(window, "load", function() {
        a("text")
    }, !1)
});