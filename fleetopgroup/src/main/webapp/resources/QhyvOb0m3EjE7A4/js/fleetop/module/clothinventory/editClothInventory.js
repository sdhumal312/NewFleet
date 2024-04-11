$(document).ready(function() {
	showSubLocationDropDown();
$("#selectVendor").on("change", function() {
            var a = document.getElementById("selectVendor").value;
            if (0 != a) {
                var b = '<option value="1"> CASH</option><option value="2">CREDIT</option><option value="3">NEFT</option><option value="4">RTGS</option><option value="5">IMPS</option>';
                $("#renPT_option").html(b)
            } else {
                var b = '<option value="1">CASH</option>';
                $("#renPT_option").html(b)
            }
        }), $("#selectVendor").change(), $("#renPT_option").on("change", function() {
          //  showoption()
        }), $("#renPT_option").change(),$("#selectVendor").select2({
            minimumInputLength: 3,
            minimumResultsForSearch: 10,
            ajax: {
                url: "getClothVendorSearchList.in?Action=FuncionarioSelect2",
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
                                text: a.vendorName + " - " + a.vendorLocation,
                                slug: a.slug,
                                id: a.vendorId
                            }
                        })
                    }
                }
            }
        }),$("#tallyCompanyId").select2({
            minimumInputLength: 3,
            minimumResultsForSearch: 10,
            ajax: {
                url: "getTallyCompanySearchList.in?Action=FuncionarioSelect2",
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
                                text: a.companyName ,
                                slug: a.slug,
                                id: a.tallyCompanyId
                            }
                        })
                    }
                }
            }
        }),$("#tallyCompanyId").select2({
            minimumInputLength: 3,
            minimumResultsForSearch: 10,
            ajax: {
                url: "getTallyCompanySearchList.in?Action=FuncionarioSelect2",
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
                                text: a.companyName ,
                                slug: a.slug,
                                id: a.tallyCompanyId
                            }
                        })
                    }
                }
            }
        });
});

function IsInvoicenumber(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = t > 31 && 33 > t || t > 44 && 48 > t || t >= 48 && 57 >= t || t >= 65 && 90 >= t || t >= 97 && 122 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorInvoicenumber").innerHTML = "Special Characters not allowed", document.getElementById("errorInvoicenumber").style.display = n ? "none" : "inline", n
}

function IsVendorRemark(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        r = n > 31 && 33 > n || n > 39 && 42 > n || n >= 45 && 57 >= n || n >= 65 && 90 >= n || n >= 97 && 122 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorVendorRemark").innerHTML = "Special Characters not allowed", document.getElementById("errorVendorRemark").style.display = r ? "none" : "inline", r
}
