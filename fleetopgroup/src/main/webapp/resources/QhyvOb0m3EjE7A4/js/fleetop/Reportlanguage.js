function dateRange() {
    $("#dateRange :selected"), $.fn.datepicker.defaults.format = "yyyy-mm-dd";
    var e = document.getElementById("dateRange"),
        t = e[e.selectedIndex].value;
    "0" == t ? ($("#ReportTo").datepicker("setDate", "+d"), $("#ReportFrom").datepicker("setDate", "+d"), $("#customRangeReminder").hide()) : "1" == t ? ($("#ReportTo").datepicker("setDate", "-1d"), $("#ReportFrom").datepicker("setDate", "-1d"), $("#customRangeReminder").hide()) : "2" == t ? ($("#ReportTo").datepicker("setDate", "+1d"), $("#ReportFrom").datepicker("setDate", "+1d"), $("#customRangeReminder").hide()) : "3" == t ? ($("#ReportTo").datepicker("setDate", "+2d"), $("#ReportFrom").datepicker("setDate", "+2d"), $("#customRangeReminder").hide()) : "4" == t ? ($("#ReportTo").datepicker("setDate", "+7d"), $("#ReportFrom").datepicker("setDate", "+d"), $("#customRangeReminder").hide()) : "5" == t ? ($("#ReportTo").datepicker("setDate", "+15d"), $("#ReportFrom").datepicker("setDate", "+d"), $("#customRangeReminder").hide()) : "6" == t ? ($("#ReportTo").datepicker("setDate", "+d"), $("#ReportFrom").datepicker("setDate", "-7d"), $("#customRangeReminder").hide()) : "7" == t ? ($("#ReportTo").datepicker("setDate", "+d"), $("#ReportFrom").datepicker("setDate", "-15d"), $("#customRangeReminder").hide()) : "8" == t && ($("#customRangeReminder").show(), $("#ReportTo").datepicker({
        autoclose: !0,
        todayHighlight: !0
    }), $("#ReportFrom").datepicker({
        autoclose: !0,
        todayHighlight: !0
    }))
}

function dateRangeFuel() {
    $("#dateRangeFuel :selected"), $.fn.datepicker.defaults.format = "yyyy-mm-dd";
    var e = document.getElementById("dateRangeFuel"),
        t = e[e.selectedIndex].value;
    "0" == t ? ($("#FuelTo").datepicker("setDate", "+d"), $("#FuelFrom").datepicker("setDate", "+d"), $("#customRangeFuel").hide()) : "1" == t ? ($("#FuelTo").datepicker("setDate", "-1d"), $("#FuelFrom").datepicker("setDate", "-1d"), $("#customRangeFuel").hide()) : "2" == t ? ($("#FuelTo").datepicker("setDate", "+1d"), $("#FuelFrom").datepicker("setDate", "+1d"), $("#customRangeFuel").hide()) : "3" == t ? ($("#FuelTo").datepicker("setDate", "+2d"), $("#FuelFrom").datepicker("setDate", "+2d"), $("#customRangeFuel").hide()) : "4" == t ? ($("#FuelTo").datepicker("setDate", "+7d"), $("#FuelFrom").datepicker("setDate", "+d"), $("#customRangeFuel").hide()) : "5" == t ? ($("#FuelTo").datepicker("setDate", "+15d"), $("#FuelFrom").datepicker("setDate", "+d"), $("#customRangeFuel").hide()) : "6" == t ? ($("#FuelTo").datepicker("setDate", "+d"), $("#FuelFrom").datepicker("setDate", "-7d"), $("#customRangeFuel").hide()) : "7" == t ? ($("#FuelTo").datepicker("setDate", "+d"), $("#FuelFrom").datepicker("setDate", "-15d"), $("#customRangeFuel").hide()) : "8" == t && ($("#customRangeFuel").show(), $("#FuelTo").datepicker({
        autoclose: !0,
        todayHighlight: !0
    }), $("#FuelFrom").datepicker({
        autoclose: !0,
        todayHighlight: !0
    }))
}
$(document).ready(function() {
    $(".select2").select2(), $("#tagPicker").select2({
        closeOnSelect: !1
    }), $("#datemask").inputmask("dd-mm-yyyy", {
        placeholder: "dd-mm-yyyy"
    }), $("[data-mask]").inputmask()
}), $(document).ready(function() {
    $("#customRangeReminder").hide(), $("#dateRange").on("change", function() {
        dateRange()
    }), $("#dateRange").change()
}), $(document).ready(function() {
    $("#customRangeFuel").hide(), $("#dateRangeFuel").on("change", function() {
        dateRangeFuel()
    }), $("#dateRangeFuel").change()
}), $(document).ready(function() {
    $("#RenewalSelectVehicle").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getVehicleListFuel.in?Action=FuncionarioSelect2",
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
                            text: e.vehicle_registration,
                            slug: e.slug,
                            id: e.vid
                        }
                    })
                }
            }
        }
    }), $("#RenewalTypeSelect").select2({
        ajax: {
            url: "getReportRenewalType.in?Action=FuncionarioSelect2",
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
                            text: e.renewal_Type,
                            slug: e.slug,
                            id: e.renewal_id
                        }
                    })
                }
            }
        }
    }), $("#RenewalSubTypeSelect").select2({
        ajax: {
            url: "getReportRenewalSubType.in?Action=FuncionarioSelect2",
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
                            text: e.renewal_SubType,
                            slug: e.slug,
                            id: e.renewal_Subid
                        }
                    })
                }
            }
        }
    }), $("#MasterpartSelect").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getPartList.in",
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
                        	text: e.partnumber + " - " + e.partname + " - " + e.make,
                            slug: e.slug,
                            id: e.partid
                        }
                    })
                }
            }
        }
    })
}), $(document).ready(function() {
    $("#FuelSelectVehicle").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getVehicleListFuel.in?Action=FuncionarioSelect2",
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
                            text: e.vehicle_registration,
                            slug: e.slug,
                            id: e.vid
                        }
                    })
                }
            }
        }
    }), $("#selectVendor").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getVendorSearchListInventory.in?Action=FuncionarioSelect2",
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
                            text: e.vendorName + " - " + e.vendorLocation,
                            slug: e.slug,
                            id: e.vendorId
                        }
                    })
                }
            }
        }
    }), $("#selectVendor2").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getVendorSearchListInventory.in?Action=FuncionarioSelect2",
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
                            text: e.vendorName + " - " + e.vendorLocation,
                            slug: e.slug,
                            id: e.vendorId
                        }
                    })
                }
            }
        }
    }), $("#VehicleTODriverFuel").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getDriverSearchListFuel.in?Action=FuncionarioSelect2",
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
                            text: e.driver_empnumber + " - " + e.driver_firstname +" "+e.driver_Lastname+" - "+e.driver_fathername,
                            slug: e.slug,
                            id: e.driver_id
                        }
                    })
                }
            }
        }
    }), $("#Reportpartlocation").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getPurchasePartLocation.in?Action=FuncionarioSelect2",
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
                            text: e.partlocation_name,
                            slug: e.slug,
                            id: e.partlocation_id
                        }
                    })
                }
            }
        }
    }), $("#searchpart").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getSearchMasterPart.in?Action=FuncionarioSelect2",
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
                            text: e.partnumber + " - " + e.partname + " - " + e.category + " - " + e.make,
                            slug: e.slug,
                            id: e.partid
                        }
                    })
                }
            }
        }
    })
});
$(document).ready(function() {
    $("#RenewalTypeSelect").change(function() {
        $.getJSON("getRenewalSubTypeChange.in", {
            RenewalType: $(this).val(),
            ajax: "true"
        }, function(e) {
            for (var n = '<option value="0">Please Select</option>', t = e.length, r = 0; t > r; r++) n += '<option value="' + e[r].renewal_Subid + '">' + e[r].renewal_SubType + "</option>";
            n += "</option>", $("#to").html(n)
        })
    })
});$(document).ready(function() {
    $("#subscribe").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        multiple: 0,
        ajax: {
            url: "getUserEmailId_Subscrible",
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
                            text: a.firstName + " " + a.lastName,
                            slug: a.slug,
                            id: a.user_id
                        }
                    })
                }
            }
        }
    })
});