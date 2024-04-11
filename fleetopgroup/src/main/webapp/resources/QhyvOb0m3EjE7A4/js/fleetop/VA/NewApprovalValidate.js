function showoption() {
    var e = $("#Approval_option :selected").text();
    "Cash" == e ? $("#targetApproval").text(e + " Receipt NO") : "Money Orders" == e ? $("#targetApproval").text(e + " Transaction NO") : "Cheque" == e ? $("#targetApproval").text(e + " NO") : "Bank Draft" == e ? $("#targetApproval").text(e + " Receipt NO") : "Online" == e && $("#targetApproval").text(e + " Transaction NO")
}

function dateRangeVenderApproval() {
    $("#dateRangeApproval :selected"), $.fn.datepicker.defaults.format = "yyyy-mm-dd";
    var e = document.getElementById("dateRangeApproval"),
        t = e[e.selectedIndex].value;
    "0" == t ? ($("#FuelTo").datepicker("setDate", "+d"), $("#FuelFrom").datepicker("setDate", "+d"), $("#customVenderApprovalList").hide()) : "1" == t ? ($("#FuelTo").datepicker("setDate", "-0d"), $("#FuelFrom").datepicker("setDate", "-0d"), $("#customVenderApprovalList").hide()) : "2" == t ? ($("#FuelTo").datepicker("setDate", "+2d"), $("#FuelFrom").datepicker("setDate", "+2d"), $("#customVenderApprovalList").hide()) : "3" == t ? ($("#FuelTo").datepicker("setDate", "-2d"), $("#FuelFrom").datepicker("setDate", "-2d"), $("#customVenderApprovalList").hide()) : "4" == t ? ($("#FuelTo").datepicker("setDate", "+7d"), $("#FuelFrom").datepicker("setDate", "+d"), $("#customVenderApprovalList").hide()) : "5" == t ? ($("#FuelTo").datepicker("setDate", "+d"), $("#FuelFrom").datepicker("setDate", "-30d"), $("#customVenderApprovalList").hide()) : "6" == t ? ($("#FuelTo").datepicker("setDate", "+d"), $("#FuelFrom").datepicker("setDate", "-7d"), $("#customVenderApprovalList").hide()) : "7" == t ? ($("#FuelTo").datepicker("setDate", "+d"), $("#FuelFrom").datepicker("setDate", "-15d"), $("#customVenderApprovalList").hide()) : "8" == t && ($("#customVenderApprovalList").show(), $("#FuelTo").datepicker({
        autoclose: !0,
        todayHighlight: !0
    }), $("#FuelFrom").datepicker({
        autoclose: !0,
        todayHighlight: !0
    }))
}
$(document).ready(function() {
    $("#datemask").inputmask("dd-mm-yyyy", {
        placeholder: "dd-mm-yyyy"
    }), $("[data-mask]").inputmask()
}), $(document).ready(function() {
    $("#Approval_option").on("change", function() {
        showoption()
    }), $("#Approval_option").change()
}), $(document).ready(function() {
    $("#dateRangeApproval").on("change", function() {
        dateRangeVenderApproval()
    }), $("#dateRangeApproval").change()
}), $(function() {
    function e(e, t) {
        $("#reportrange").val(e.format("YYYY-MM-DD") + " to " + t.format("YYYY-MM-DD"))
    }
    e(moment().subtract(1, "days"), moment()), $("#reportrange").daterangepicker({
        ranges: {
            Today: [moment(), moment()],
            Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")],
            "Last 7 Days": [moment().subtract(6, "days"), moment()]
        }
    }, e)
}), $(function() {
    function e(e, t) {
        $("#reportrangeService").val(e.format("YYYY-MM-DD") + " to " + t.format("YYYY-MM-DD"))
    }
    e(moment().subtract(1, "days"), moment()), $("#reportrangeService").daterangepicker({
        ranges: {
            Today: [moment(), moment()],
            Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")],
            "Last 7 Days": [moment().subtract(6, "days"), moment()]
        }
    }, e)
}), $(document).ready(function() {
	$("#SelectVehicleGroup").select2({
        ajax: {
            url: "../getVehicleGroup.in?Action=FuncionarioSelect2",
            dataType: "json",
            type: "GET",
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
                            text: e.vGroup,
                            slug: e.slug,
                            id: e.gid
                        }
                    })
                }
            }
        }
    }), 
    
    $("#vehicleGroupIdStr").select2({
        ajax: {
            url: "../getVehicleGroup.in?Action=FuncionarioSelect2",
            dataType: "json",
            type: "GET",
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
                            text: e.vGroup,
                            slug: e.slug,
                            id: e.gid
                        }
                    })
                }
            }
        }
    }),
    
    $("#SelectVehicleGroupService").select2({
        ajax: {
            url: "getVehicleGroup.in?Action=FuncionarioSelect2",
            dataType: "json",
            type: "GET",
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
                            text: e.vGroup,
                            slug: e.slug,
                            id: e.gid
                        }
                    })
                }
            }
        }
    }),$("#SelectVehicleGroup2").select2({
        ajax: {
            url: "../getVehicleGroup.in?Action=FuncionarioSelect2",
            dataType: "json",
            type: "GET",
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
                            text: e.vGroup,
                            slug: e.slug,
                            id: e.gid
                        }
                    })
                }
            }
        }
    }), $("#warehouselocation").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getSearchPartLocations.in?Action=FuncionarioSelect2",
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
                            id: e.partlocation_name
                        }
                    })
                }
            }
        }
    })
});
	
function validateFuel(){
	
		if($('#SelectVehicleGroup').val() == ""){
			showMessage('errors','Please Select VehicleGroup!')
			return false;
		}
		
	return true;
	
}
	


