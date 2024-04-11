function validateStatus() {
    var t = !0,
        e = document.getElementById("vStatus").value;
    return validateMandatory(e) && ValidateAlpha(e) ? ValidateLength(e) || (t = !1) : t = !1, t
}

function validateStatusUpdate() {
    var t = !0,
        e = document.getElementById("vStatusUpdate").value;
    return validateMandatory(e) || (t = !1), ValidateAlpha(e) || (t = !1), ValidateLength(e) || (t = !1), t
}

function validateMandatory(t) {
    var e = !0;
    return (null == t || "" == t) && (vStatus.style.border = "2px solid #F00", errorvStatus.innerHTML = "Please Enter the Value", e = !1), e
}

function ValidateAlpha(t) {
    var e = !0;
    return /[^a-zA-Z0-9 -]/.test(t) && (vStatus.style.border = "2px solid #F00", errorvStatus.innerHTML = "Please Enter the Value Alpha Letter", e = !1), e
}

function ValidateLength(t) {
    var e = !0;
    return t.length <= 25 && t.length >= 2 || (vStatus.style.border = "2px solid #F00", errorvStatus.innerHTML = "Please input the Status between 2 and 25 characters", e = !1), e
}

function validateStatusUpdate() {
    var t = !0,
        e = document.getElementById("vStatusUpdate").value;
    return ValidateLengthStatusUpdate(e) && validateMandatoryStatusUpdate(e) ? ValidateAlphaStatusUpdate(e) || (t = !1) : t = !1, t
}

function validateMandatoryStatusUpdate(t) {
    var e = !0;
    return null == t || "" == t ? (document.getElementById("vStatusUpdate").style.border = "2px solid #F00", document.getElementById("errorEditStatus").innerHTML = "Please Enter the Value", e = !1) : (vStatus.style.border = "2px solid #0F0", errorvStatus.innerHTML = ""), e
}

function ValidateAlphaStatusUpdate(t) {
    var e = !0;
    return /[^a-zA-Z0-9 -]/.test(t) && (document.getElementById("vStatusUpdate").style.border = "2px solid #F00", document.getElementById("errorEditStatus").innerHTML = "Please Enter the Value Alpha Letter", e = !1), e
}

function ValidateLengthStatusUpdate(t) {
    var e = !0;
    return t.length <= 25 && t.length >= 2 || (document.getElementById("vStatusUpdate").style.border = "2px solid #F00", document.getElementById("errorEditStatus").innerHTML = "Please input the Status between 2 and 25 characters", e = !1), e
}
$(document).ready(function() {
    $(".select2").select2();
    $(".select3").select2({
        tags: !0,
        placeholder: "Please Select the value ",
        createTag: function(t) {
            return {
                id: t.term,
                text: t.term,
                isNew: !0
            }
        }
    }).on("select3:select", function(t) {
        t.params.data.isNew && $(this).find('[value="' + t.params.data.id + '"]').replaceWith('<option selected value="' + t.params.data.id + '">' + t.params.data.text + "</option>")
    }), $("#tagPicker").select2({
        closeOnSelect: !1
    })
});

$(document).ready(function() {
    function f(a) {
        for (var b = document.getElementsByTagName("textarea"), c = 0; c < b.length; c++) com_satheesh.EVENTS.addEventHandler(b[c], "focus", g, !1), com_satheesh.EVENTS.addEventHandler(b[c], "blur", h, !1);
        b = document.getElementsByTagName("input");
        for (var c = 0; c < b.length; c++) a.indexOf(-1 != b[c].getAttribute("type")) && (com_satheesh.EVENTS.addEventHandler(b[c], "focus", g, !1), com_satheesh.EVENTS.addEventHandler(b[c], "blur", h, !1));
        com_satheesh.EVENTS.addEventHandler(document.getElementById("formMasterPart"), "submit", m, !1), com_satheesh.EVENTS.addEventHandler(document.getElementById("formEditMasterPart"), "submit", n, !1), document.getElementsByTagName("input")[0].focus(), com_satheesh.EVENTS.addEventHandler(document.forms[0].partName, "blur", i, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].partNumber, "blur", j, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].partCategory, "blur", k, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].manufacturer, "blur", l, !1)
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
        var a = document.getElementById("partName"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grppartName");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("partNameIcon").className = "fa fa-check form-text-feedback", document.getElementById("partNameErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("partNameIcon").className = "fa fa-remove form-text-feedback", document.getElementById("partNameErrorMsg").innerHTML = "Please enter part name"), b
    }

    function j() {
    	if(document.getElementById('isRefreshmentYes') == null || !document.getElementById('isRefreshmentYes').checked){
    		var a = document.getElementById("partNumber"),
    		b = null != a.value && 0 != a.value.length,
    		c = document.getElementById("grppartNumber");
    		if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("partNumberIcon").className = "fa fa-check form-text-feedback", document.getElementById("partNumberErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("partNumberIcon").className = "fa fa-remove form-text-feedback", document.getElementById("partNumberErrorMsg").innerHTML = "Please enter part  number"), b
    	}else{
    		return true;
    	}
    }

    function k() {
        var a = document.getElementById("partCategory"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grppartCategory");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("partCategoryIcon").className = "fa fa-check form-text-feedback", document.getElementById("partCategoryErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("partCategoryIcon").className = "fa fa-remove form-text-feedback", document.getElementById("partCategoryErrorMsg").innerHTML = "Please select part category "), b
    }

    function l() {
        var a = document.getElementById("manufacturer"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpmanufacturer");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("manufacturerIcon").className = "fa fa-check  form-text-feedback", document.getElementById("manufacturerErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("manufacturerIcon").className = "fa fa-remove form-text-feedback", document.getElementById("manufacturerErrorMsg").innerHTML = "Please enter manufacturer ")), b
    }

    function m(a) {
        var b = i();
        b &= j(), b &= k(), (b &= l()) || com_satheesh.EVENTS.preventDefault(a)
    }

    function n(a) {
        var b = i();
        b &= j(), b &= k(), (b &= l()) || com_satheesh.EVENTS.preventDefault(a)
    }
    var b = "#FFC";
    com_satheesh.EVENTS.addEventHandler(window, "load", function() {
        f("text")
    }, !1)
});

function onRefreshmentSelect(){
	if(document.getElementById('isRefreshmentYes').checked){
		$('#refreshment').val(1);
		$('#partAbbr').hide();
		$('#partNumber').prop('required',false);
		var theText = 'Refreshment';
		$("#partCategory option").each(function() {
			if($(this).text() == theText) {
				$(this).attr('selected', 'selected');              
			}   
		});
		
		$("#manufacturer option").each(function() {
			if($(this).text() == theText) {
				$(this).attr('selected', 'selected'); 
				
			}   
		});
	}else{
		$('#refreshment').val(0);
		$('#partAbbr').show();
		$('#partNumber').prop('required',true);
	}
}