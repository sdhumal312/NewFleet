function totalCost(e, t, n) {
			var o = document.getElementById(e).value, i = document
					.getElementById(t).value, r = parseFloat(o) * parseFloat(i);
			isNaN(r) || (document.getElementById(n).value = r.toFixed(2))
		}

function isNumberKey(e, t) {
			var n = e.which ? e.which : event.keyCode;
			if (n > 31 && (48 > n || n > 57) && 46 != n && 8 != charcode)
				return !1;
			var r = $(t).val().length, o = $(t).val().indexOf(".");
			if (o > 0 && 46 == n)
				return !1;
			if (o > 0) {
				var i = r + 1 - o;
				if (i > 3)
					return !1
			}
			return !0
		}

$(document).ready(function() {
    function f(a) {
        for (var b = document.getElementsByTagName("textarea"), c = 0; c < b.length; c++) 
        	com_satheesh.EVENTS.addEventHandler(b[c], "focus", g, !1), 
        	com_satheesh.EVENTS.addEventHandler(b[c], "blur", h, !1);
        b = document.getElementsByTagName("input");
        for (var c = 0; c < b.length; c++) a.indexOf(-1 != b[c].getAttribute("type")) && (com_satheesh.EVENTS.addEventHandler(b[c], "focus", g, !1), 
        		com_satheesh.EVENTS.addEventHandler(b[c], "blur", h, !1));
        com_satheesh.EVENTS.addEventHandler(document.getElementById("formCloseTripClose"), "submit", p, !1), 
        com_satheesh.EVENTS.addEventHandler(document.getElementById("formEditCloseTripClose"), "submit", q, !1), 
        document.getElementsByTagName("input")[0].focus(), 
        com_satheesh.EVENTS.addEventHandler(document.forms[0].closeDate, "blur", i, !1), 
        com_satheesh.EVENTS.addEventHandler(document.forms[0].totalCollection, "blur", j, !1), 
        com_satheesh.EVENTS.addEventHandler(document.forms[0].perSingl, "blur", k, !1), 
        com_satheesh.EVENTS.addEventHandler(document.forms[0].salarycost, "blur", l, !1), 
        com_satheesh.EVENTS.addEventHandler(document.forms[0].rollNumber, "blur", m, !1), 
        com_satheesh.EVENTS.addEventHandler(document.forms[0].rollPrice, "blur", n, !1), 
        com_satheesh.EVENTS.addEventHandler(document.forms[0].machanicCost, "blur", o, !1),
        com_satheesh.EVENTS.addEventHandler(document.forms[0].insurenceCost, "blur", r, !1),
        com_satheesh.EVENTS.addEventHandler(document.forms[0].bonusCost, "blur", s, !1)
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
        var a = document.getElementById("closeDate"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpcloseDate");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("closeDateIcon").className = "fa fa-check form-text-feedback", document.getElementById("closeDateErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("closeDateIcon").className = "fa fa-remove form-text-feedback", document.getElementById("closeDateErrorMsg").innerHTML = "Please select close date"), b
    }

    function j() {
        var a = document.getElementById("totalCollection"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grptotalCollection");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("totalCollectionIcon").className = "fa fa-check form-text-feedback", document.getElementById("totalCollectionErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("totalCollectionIcon").className = "fa fa-remove form-text-feedback", document.getElementById("totalCollectionErrorMsg").innerHTML = "Please enter total collection"), b
    }

    function k() {
        var a = document.getElementById("perSingl"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpperSingl");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("perSinglIcon").className = "fa fa-check form-text-feedback", document.getElementById("perSinglErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("perSinglIcon").className = "fa fa-remove form-text-feedback", document.getElementById("perSinglErrorMsg").innerHTML = "Please enter per singl"), b
    }

    function l() {
        var a = document.getElementById("salarycost"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpsalarycost");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("salarycostIcon").className = "fa fa-check  form-text-feedback", document.getElementById("salarycostErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("salarycostIcon").className = "fa fa-remove form-text-feedback", document.getElementById("salarycostErrorMsg").innerHTML = "Please enter salary cost")), b
    }

    function m() {
        var a = document.getElementById("rollNumber"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grprollNumber");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("rollNumberIcon").className = "fa fa-check  form-text-feedback", document.getElementById("rollNumberErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("rollNumberIcon").className = "fa fa-remove form-text-feedback", document.getElementById("rollNumberErrorMsg").innerHTML = "Please enter ticket roll")), b
    }

    function n() {
        var a = document.getElementById("rollPrice"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grprollNumber");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("rollPriceIcon").className = "fa fa-check  form-text-feedback", document.getElementById("rollPriceErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("rollPriceIcon").className = "fa fa-remove form-text-feedback", document.getElementById("rollPriceErrorMsg").innerHTML = "Please enter roll price")), b
    }

    function o() {
        var a = document.getElementById("machanicCost"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpmachanicCost");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("machanicCostIcon").className = "fa fa-check  form-text-feedback", document.getElementById("machanicCostErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("machanicCostIcon").className = "fa fa-remove form-text-feedback", document.getElementById("machanicCostErrorMsg").innerHTML = "Please enter machanic cost")), b
    }
    
    function r() {
        var a = document.getElementById("insurenceCost"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpinsurenceCost");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("insurenceCostIcon").className = "fa fa-check  form-text-feedback", document.getElementById("insurenceCostErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("insurenceCostIcon").className = "fa fa-remove form-text-feedback", document.getElementById("insurenceCostErrorMsg").innerHTML = "Please enter insurence cost")), b
    }
    
    function s() {
        var a = document.getElementById("bonusCost"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpbonusCost");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("bonusCostIcon").className = "fa fa-check  form-text-feedback", document.getElementById("bonusCostErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("bonusCostIcon").className = "fa fa-remove form-text-feedback", document.getElementById("bonusCostErrorMsg").innerHTML = "Please enter bonus cost")), b
    }

    function p(a) {
        var b = i();
        b &= j(), b &= k(), b &= l(), b &= m(), b &= n(), b &= o(), b &= r(), (b &= s()) || com_satheesh.EVENTS.preventDefault(a)
    }

    function q(a) {
        var b = i();
        b &= j(), b &= k(), b &= l(), b &= m(), b &= n(), b &= o(), b &= r(), (b &= s()) || com_satheesh.EVENTS.preventDefault(a)
    }
    var b = "#FFC";
    com_satheesh.EVENTS.addEventHandler(window, "load", function() {
        f("text")
    }, !1)
});