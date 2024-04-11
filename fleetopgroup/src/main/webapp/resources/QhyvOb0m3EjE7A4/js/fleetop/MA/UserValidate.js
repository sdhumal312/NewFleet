function IsFirstName(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        o = n > 31 && 33 > n || n > 44 && 48 > n || n >= 48 && 57 >= n || n >= 65 && 90 >= n || n >= 97 && 122 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorFirstName").innerHTML = "Special Characters not allowed", document.getElementById("errorFirstName").style.display = o ? "none" : "inline", o
}

function IsLastName(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        o = n > 31 && 33 > n || n >= 48 && 57 >= n || n >= 65 && 90 >= n || n >= 97 && 122 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorLastName").innerHTML = "Special Characters not allowed", document.getElementById("errorLastName").style.display = o ? "none" : "inline", o
}

function IsEmail(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        o = n > 39 && 42 > n || n >= 45 && 57 >= n || n >= 64 && 90 >= n || n >= 97 && 122 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorEmail").innerHTML = "Special Characters not allowed", document.getElementById("errorEmail").style.display = o ? "none" : "inline", o
}

function IsDesignation(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        o = n > 31 && 33 > n || n >= 48 && 57 >= n || n >= 65 && 90 >= n || n >= 97 && 122 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorDesignation").innerHTML = "Special Characters not allowed", document.getElementById("errorDesignation").style.display = o ? "none" : "inline", o
}

function IsPersonalEmail(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        o = n > 31 && 33 > n || n > 39 && 42 > n || n >= 45 && 57 >= n || n >= 64 && 90 >= n || n >= 97 && 122 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorPersonalEmail").innerHTML = "Special Characters not allowed", document.getElementById("errorPersonalEmail").style.display = o ? "none" : "inline", o
}

function IsHomeNo(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        o = n >= 48 && 57 >= n || n > 42 && 44 > n || n > 44 && 46 > n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorHomeNo").innerHTML = "Alphabets & Special Characters not allowed", document.getElementById("errorHomeNo").style.display = o ? "none" : "inline", o
}

function IsMobileNo(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        o = n >= 48 && 57 >= n || n > 42 && 44 > n || n > 44 && 46 > n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorMobileNo").innerHTML = "Alphabets & Special Characters not allowed", document.getElementById("errorMobileNo").style.display = o ? "none" : "inline", o
}

function IsWorkNo(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        o = n >= 48 && 57 >= n || n > 42 && 44 > n || n > 44 && 46 > n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorWorkNo").innerHTML = "Alphabets & Special Characters not allowed", document.getElementById("errorWorkNo").style.display = o ? "none" : "inline", o
}

function IsBranchAddress(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        o = n > 31 && 33 > n || n > 39 && 42 > n || n >= 45 && 57 >= n || n >= 65 && 90 >= n || n >= 97 && 122 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorBranchAddress").innerHTML = "Special Characters not allowed", document.getElementById("errorBranchAddress").style.display = o ? "none" : "inline", o
}

function IsBranchPin(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        o = n >= 48 && 57 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorBranchPin").innerHTML = "Alphabet Characters not allowed", document.getElementById("errorBranchPin").style.display = o ? "none" : "inline", o
}

function IsEmergencyName(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        o = n > 31 && 33 > n || n >= 45 && 57 >= n || n >= 65 && 90 >= n || n >= 97 && 122 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorEmergencyName").innerHTML = "Special Characters not allowed", document.getElementById("errorEmergencyName").style.display = o ? "none" : "inline", o
}

function IsEmergencyPhone(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        o = n >= 48 && 57 >= n || n > 42 && 44 > n || n > 44 && 46 > n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorEmergencyPhone").innerHTML = "Alphabets & Special Characters not allowed", document.getElementById("errorEmergencyPhone").style.display = o ? "none" : "inline", o
}

function IsEmployeeID(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        o = n > 31 && 33 > n || n >= 45 && 57 >= n || n >= 65 && 90 >= n || n >= 97 && 122 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorEmployeeID").innerHTML = "Special Characters not allowed", document.getElementById("errorEmployeeID").style.display = o ? "none" : "inline", o
}

function IsESINO(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        o = n > 31 && 33 > n || n >= 45 && 57 >= n || n >= 65 && 90 >= n || n >= 97 && 122 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorESINO").innerHTML = "Special Characters not allowed", document.getElementById("errorESINO").style.display = o ? "none" : "inline", o
}

function IsPFNo(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        o = n > 31 && 33 > n || n >= 45 && 57 >= n || n >= 65 && 90 >= n || n >= 97 && 122 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorPFNo").innerHTML = "Special Characters not allowed", document.getElementById("errorPFNo").style.display = o ? "none" : "inline", o
}

function IsInsuranceNO(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        o = n > 31 && 33 > n || n >= 45 && 57 >= n || n >= 65 && 90 >= n || n >= 97 && 122 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorInsuranceNO").innerHTML = "Special Characters not allowed", document.getElementById("errorInsuranceNO").style.display = o ? "none" : "inline", o
}
function IsRfidNO(e) {
	var n = 0 == e.keyCode ? e.charCode : e.keyCode,
			o = n > 31 && 33 > n || n >= 45 && 57 >= n || n >= 65 && 90 >= n || n >= 97 && 122 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
			return document.getElementById("errorRfidCardNo").innerHTML = "Special Characters not allowed", document.getElementById("errorRfidCardNo").style.display = o ? "none" : "inline", o
}

var specialKeys = new Array;
specialKeys.push(8), specialKeys.push(9), specialKeys.push(46), specialKeys.push(36), specialKeys.push(35), specialKeys.push(37), specialKeys.push(39), $(document).ready(function() {
    $("#company_id").change(function() {
        $.getJSON("getDepartmentList.in", {
            companyID: $(this).val(),
            ajax: "true"
        }, function(e) {
            for (var n = '<option value="" selected="selected">Please Select</option>', o = e.length, r = 0; o > r; r++) n += '<option value="' + e[r].depart_id + '">' + e[r].depart_name + " - " + e[r].depart_code + "</option>";
            n += "</option>", $("#DepartmentList").html(n)
        })
    }), $("#company_id").change(function() {
        $.getJSON("getBranchList.in", {
            companyID: $(this).val(),
            ajax: "true"
        }, function(e) {
            for (var n = '<option value="" selected="selected">Please Select</option>', o = e.length, r = 0; o > r; r++) n += '<option value="' + e[r].branch_id + '">' + e[r].branch_name + " - " + e[r].branch_city + "</option>";
            n += "</option>", $("#BranchList").html(n)
        })
    }), $("#DepartmentList, #BranchList").select2()
});

$(document).ready(function() {
    function a(a) {
        for (var r = document.getElementsByTagName("textarea"), s = 0; s < r.length; s++) com_satheesh.EVENTS.addEventHandler(r[s], "focus", b, !1), com_satheesh.EVENTS.addEventHandler(r[s], "blur", c, !1);
        r = document.getElementsByTagName("input");
        for (var s = 0; s < r.length; s++) a.indexOf(-1 != r[s].getAttribute("type")) && (com_satheesh.EVENTS.addEventHandler(r[s], "focus", b, !1), com_satheesh.EVENTS.addEventHandler(r[s], "blur", c, !1));
        com_satheesh.EVENTS.addEventHandler(document.getElementById("formUser"), "submit", p, !1), com_satheesh.EVENTS.addEventHandler(document.getElementById("formEditUser"), "submit", q, !1), document.getElementsByTagName("input")[0].focus(), com_satheesh.EVENTS.addEventHandler(document.forms[0].firstName, "blur", d, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].lastName, "blur", e, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].userEmail, "blur", f, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].password, "blur", g, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].matchPassword, "blur", h, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].userRole, "blur", i, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].company_id, "blur", j, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].DepartmentList, "blur", k, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].BranchList, "blur", l, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].userDOB, "blur", m, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].userpersonalEmail, "blur", n, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].userMobile, "blur", o, !1)
    }

    function b(a) {
        var b = com_satheesh.EVENTS.getEventTarget(a);
        null != b && (b.style.backgroundColor = r)
    }

    function c(a) {
        var b = com_satheesh.EVENTS.getEventTarget(a);
        null != b && (b.style.backgroundColor = "")
    }

    function d() {
        var a = document.getElementById("firstName"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpfirstName");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("firstNameIcon").className = "fa fa-check form-text-feedback", document.getElementById("firstNameErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("firstNameIcon").className = "fa fa-remove form-text-feedback", document.getElementById("firstNameErrorMsg").innerHTML = "Please enter user first name"), b
    }

    function e() {
        var a = document.getElementById("lastName"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grplastName");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("lastNameIcon").className = "fa fa-check form-text-feedback", document.getElementById("lastNameErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("lastNameIcon").className = "fa fa-remove form-text-feedback", document.getElementById("lastNameErrorMsg").innerHTML = "Please enter user last name"), b
    }

    function f() {
        var a = document.getElementById("userEmail"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpuserEmail");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("userEmailIcon").className = "fa fa-check form-text-feedback", document.getElementById("userEmailErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("userEmailIcon").className = "fa fa-remove form-text-feedback", document.getElementById("userEmailErrorMsg").innerHTML = "Please enter user company email id"), b
    }

    function g() {
        var a = document.getElementById("password"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpuserPassword");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("userPasswordIcon").className = "fa fa-check  form-text-feedback", document.getElementById("userPasswordErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("userPasswordIcon").className = "fa fa-remove form-text-feedback", document.getElementById("userPasswordErrorMsg").innerHTML = "Please enter user password")), b
    }

    function h() {
        var a = document.getElementById("matchPassword"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpusermatchPassword");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("usermatchPasswordIcon").className = "fa fa-check  form-text-feedback", document.getElementById("usermatchPasswordErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("usermatchPasswordIcon").className = "fa fa-remove form-text-feedback", document.getElementById("usermatchPasswordErrorMsg").innerHTML = "Please enter match password")), b
    }

    function i() {
        var a = document.getElementById("userRole"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpuserRole");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("userRoleIcon").className = "fa fa-check  form-text-feedback", document.getElementById("userRoleErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("userRoleIcon").className = "fa fa-remove form-text-feedback", document.getElementById("userRoleErrorMsg").innerHTML = "Please select user role name")), b
    }


    function k() {
        var a = document.getElementById("DepartmentList"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpuserDepartment");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("userDepartmentIcon").className = "fa fa-check  form-text-feedback", document.getElementById("userDepartmentErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("userDepartmentIcon").className = "fa fa-remove form-text-feedback", document.getElementById("userDepartmentErrorMsg").innerHTML = "Please select user department name")), b
    }

    function l() {
        var a = document.getElementById("BranchList"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpuserBranch");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("userBranchIcon").className = "fa fa-check  form-text-feedback", document.getElementById("userBranchErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("userBranchIcon").className = "fa fa-remove form-text-feedback", document.getElementById("userBranchErrorMsg").innerHTML = "Please select user branch name")), b
    }

    function m() {
        var a = document.getElementById("userDOB"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpuserDOB");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("userDOBIcon").className = "fa fa-check  form-text-feedback", document.getElementById("userDOBErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("userDOBIcon").className = "fa fa-remove form-text-feedback", document.getElementById("userDOBErrorMsg").innerHTML = "Please select user date of birth")), b
    }

    function n() {
        var a = document.getElementById("userpersonalEmail"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpuserpersonalEmail");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("userpersonalEmailIcon").className = "fa fa-check  form-text-feedback", document.getElementById("userpersonalEmailErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("userpersonalEmailIcon").className = "fa fa-remove form-text-feedback", document.getElementById("userpersonalEmailErrorMsg").innerHTML = "Please enter user email")), b
    }

    function o() {
        var a = document.getElementById("userMobile"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpuserMobile");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("userMobileIcon").className = "fa fa-check  form-text-feedback", document.getElementById("userMobileErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("userMobileIcon").className = "fa fa-remove form-text-feedback", document.getElementById("userMobileErrorMsg").innerHTML = "Please enter user mobile no")), b
    }
   
    function p(a) {
        var b = d();
        b &= e(), b &= f(), b &= g(), b &= h(), b &= i(),  b &= k(), b &= l(), b &= m(), b &= n(), (b &= o())  || com_satheesh.EVENTS.preventDefault(a)
    }

    function q(a) {
        var b = d();
        b &= e(), b &= f(), b &= i(),  b &= k(), b &= l(), b &= m(), b &= n(), (b &= o())  || com_satheesh.EVENTS.preventDefault(a)
    }
   
    var r = "#FFC";
    com_satheesh.EVENTS.addEventHandler(window, "load", function() {
        a("text")
    }, !1)
});