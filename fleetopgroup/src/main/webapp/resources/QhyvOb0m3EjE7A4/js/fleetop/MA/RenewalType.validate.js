function validateReType() {
    var e = !0,
        t = document.getElementById("ReType").value;
    return validateMandatory(t) && ValidateLength(t) ? ValidateAlpha(t) || (e = !1) : (e = !1), e;
}
function validateMandatory(e) {
    var t = !0;
    return (null == e || "" == e) && ((document.getElementById("ReType").style.border = "2px solid #F00"), (document.getElementById("errorReType").innerHTML = "Please Enter the Value"), (t = !1)), t;
}
function ValidateAlpha(e) {
    var t = !0;
    return /[^a-zA-Z0-9 -]/.test(e) && ((document.getElementById("ReType").style.border = "2px solid #F00"), (document.getElementById("errorReType").innerHTML = "Please Enter the Value Alpha Letter"), (t = !1)), t;
}
function ValidateLength(e) {
    var t = !0;
    return (e.length <= 25 && e.length >= 2) || ((document.getElementById("ReType").style.border = "2px solid #F00"), (document.getElementById("errorReType").innerHTML = "Please input the ReType between 2 and 25 characters"), (t = !1)), t;
}
function validateReTypeUpdate() {
    var e = !0,
        t = document.getElementById("ReTypeUpdate").value;
    return validateMandatoryReTypeUpdate(t) && ValidateAlphaReTypeUpdate(t) ? ValidateLengthReTypeUpdate(t) || (e = !1) : (e = !1), e;
}
function validateMandatoryReTypeUpdate(e) {
    var t = !0;
    return (null == e || "" == e) && ((document.getElementById("ReTypeUpdate").style.border = "2px solid #F00"), (document.getElementById("errorEditReType").innerHTML = "Please Enter the Value"), (t = !1)), t;
}
function ValidateAlphaReTypeUpdate(e) {
    var t = !0;
    return /[^a-zA-Z0-9 -]/.test(e) && ((document.getElementById("ReTypeUpdate").style.border = "2px solid #F00"), (document.getElementById("errorEditReType").innerHTML = "Please Enter the Value Alpha Letter"), (t = !1)), t;
}
function ValidateLengthReTypeUpdate(e) {
    var t = !0;
    return (
        (e.length <= 25 && e.length >= 2) ||
            ((document.getElementById("ReTypeUpdate").style.border = "2px solid #F00"), (document.getElementById("errorEditReType").innerHTML = "Please input the ReType between 2 and 25 characters"), (t = !1)),
        t
    );
}
$(document).ready(function() {
	$("#tallyExpenseId").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getSearchExpenseName.in?Action=FuncionarioSelect2",
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
                            text: a.expenseName ,
                            slug: a.slug,
                            id: a.expenseID
                        }
                    })
                }
            }
        }
    })
})