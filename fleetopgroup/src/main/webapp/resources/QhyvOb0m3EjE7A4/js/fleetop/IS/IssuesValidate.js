function visibility(e, t) {
    var n = document.getElementById(e),
        r = document.getElementById(t);
    "block" == n.style.display ? (n.style.display = "none", r.style.display = "block") : (n.style.display = "block", r.style.display = "none")
}

function IsDriverName(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = t > 31 && 33 > t || t > 44 && 48 > t || t >= 48 && 57 >= t || t >= 65 && 90 >= t || t >= 97 && 122 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorDriverName").innerHTML = "Special Characters not allowed", document.getElementById("errorDriverName").style.display = n ? "none" : "inline", n
}

function IsNumericOdometer(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = t >= 48 && 57 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorOdometer").innerHTML = "Alphabets & Special Characters not allowed", document.getElementById("errorOdometer").style.display = n ? "none" : "inline", n
}

function IsIssue_Summary(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = t > 31 && 33 > t || t > 44 && 48 > t || t >= 48 && 57 >= t || t >= 65 && 90 >= t || t >= 97 && 122 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorIssue_Summary").innerHTML = "Special Characters not allowed", document.getElementById("errorIssue_Summary").style.display = n ? "none" : "inline", n
}

function IsIssue_Description(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = t > 31 && 33 > t || t > 44 && 48 > t || t >= 48 && 57 >= t || t >= 65 && 90 >= t || t >= 97 && 122 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorIssue_Description").innerHTML = "Special Characters not allowed", document.getElementById("errorIssue_Description").style.display = n ? "none" : "inline", n
}

function IsIssue_Label(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = t > 31 && 33 > t || t > 44 && 48 > t || t >= 48 && 57 >= t || t >= 65 && 90 >= t || t >= 97 && 122 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorIssue_Label").innerHTML = "Special Characters not allowed", document.getElementById("errorIssue_Label").style.display = n ? "none" : "inline", n
}

function IsIssues_Reported_By(e) {
    var t = 0 == e.keyCode ? e.charCode : e.keyCode,
        n = t > 31 && 33 > t || t > 44 && 48 > t || t >= 48 && 57 >= t || t >= 65 && 90 >= t || t >= 97 && 122 >= t || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorIssues_Reported_By").innerHTML = "Special Characters not allowed", document.getElementById("errorIssues_Reported_By").style.display = n ? "none" : "inline", n
}

function isNumberKey(e, t) {
    var n = e.which ? e.which : event.keyCode;
    if (n > 31 && (48 > n || n > 57) && 46 != n && 8 != charcode) return !1;
    var r = $(t).val().length,
        i = $(t).val().indexOf(".");
    if (i > 0 && 46 == n) return !1;
    if (i > 0) {
        var s = r + 1 - i;
        if (s > 3) return !1
    }
    return !0
}
$(document).ready(function() {
    $("#IssuesSelectVehicle").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getIssuesVehicleList.in?Action=FuncionarioSelect2",
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
    }), $("#VehicleTODriverFuel").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getIssuesDriverList.in?Action=FuncionarioSelect2",
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
                            text: e.driver_empnumber + " - " + e.driver_firstname+" "+e.driver_Lastname+" - "+e.driver_fathername,
                            slug: e.slug,
                            id: e.driver_id
                        }
                    })
                }
            }
        }
    }), $("#subscribe").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        multiple: !0,
        ajax: {
            url: "getUserEmailId_Assignto",
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
                            text: e.firstName + " " + e.lastName,
                            slug: e.slug,
                            id: e.user_email
                        }
                    })
                }
            }
        }
    }), $("#IssuesBranch").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getIssuesBranch.in?Action=FuncionarioSelect2",
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
                            text: e.branch_name + " - " + e.branch_code,
                            slug: e.slug,
                            id: e.branch_id
                        }
                    })
                }
            }
        }
    }), $("#IssuesDepartnment").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getIssuesDepartnment.in?Action=FuncionarioSelect2",
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
                            text: e.depart_name + " - " + e.depart_code,
                            slug: e.slug,
                            id: e.depart_id
                        }
                    })
                }
            }
        }
    })
}), $(document).ready(function() {
    $("#driverEnter").hide(), $("#JoBEnter").hide(), $("#Branch_Depart_hide").hide(), $("#to").select2(), $("#tagPicker").select2({
        closeOnSelect: !1
    }), $("#IssuesSelectVehicle").change(function() {
        $.getJSON("getIssuesVehicleOdoMerete.in", {
            FuelvehicleID: $(this).val(),
            ajax: "true"
        }, function(e) {
            var t = "";
            t = e.vehicle_Odometer, document.getElementById("Issues_Odometer").placeholder = t
            $('#vehicle_ExpectedOdameter').val(e.vehicle_ExpectedOdameter);
            $('#vehicle_Odameter').val(e.vehicle_Odometer);
            if(e.gpsOdameter != null && e.gpsOdameter > 0){
            	$('#GPS_ODOMETER').val(e.gpsOdameter);
            	$('#Issues_Odometer').val(e.vehicle_Odometer);
            	$('#gpsOdometerRow').show();
            	//$('#VehicleOdoRow').hide();
            }else{
            	$('#gpsOdometerRow').hide();
            	$('#VehicleOdoRow').show();
            	$('#GPS_ODOMETER').val('');
            }
        })
    }), $("#IssuesType").change(function() {
        var e = $(this).val();
        switch (Number(e)) {
            case 1:
                $("#VehicleID").show(), $("#Vehicle_DriverID").show(), $("#Branch_Depart_hide").hide(), $("#vehicle_group_id").hide(),$("#Customer_ID").hide();
                break;
            case 2:
                $("#VehicleID").hide(), $("#Vehicle_DriverID").show(), $("#Branch_Depart_hide").hide(), $("#vehicle_group_id").hide(),$("#Customer_ID").hide();
                break;
            case 3:
                $("#Vehicle_DriverID").hide(), $("#Branch_Depart_hide").show(), $("#vehicle_group_id").show(),$("#Customer_ID").hide();
                break;
            case 4:
            	$("#Customer_ID").show(), $("#vehicle_group_id").hide(), $("#Branch_Depart_hide").hide(), $("#Vehicle_DriverID").hide();
            	break;
            case 5:
                $("#Vehicle_DriverID").hide(), $("#Branch_Depart_hide").show(), $("#vehicle_group_id").hide(),$("#Customer_ID").hide();
                
               
                
        }
    })
});
var specialKeys = new Array;
specialKeys.push(8), specialKeys.push(9), specialKeys.push(46), specialKeys.push(36), specialKeys.push(35), specialKeys.push(37), specialKeys.push(39), $(document).ready(function() {
    $("#datemask").inputmask("dd-mm-yyyy", {
        placeholder: "dd-mm-yyyy"
    }), $("[data-mask]").inputmask()
});


$(document).ready(function() {
    function f(a) {
        for (var b = document.getElementsByTagName("textarea"), c = 0; c < b.length; c++) com_satheesh.EVENTS.addEventHandler(b[c], "focus", g, !1), com_satheesh.EVENTS.addEventHandler(b[c], "blur", h, !1);
        b = document.getElementsByTagName("input");
        for (var c = 0; c < b.length; c++) a.indexOf(-1 != b[c].getAttribute("type")) && (com_satheesh.EVENTS.addEventHandler(b[c], "focus", g, !1), com_satheesh.EVENTS.addEventHandler(b[c], "blur", h, !1));
        com_satheesh.EVENTS.addEventHandler(document.getElementById("formIssues"), "submit", l, !1), com_satheesh.EVENTS.addEventHandler(document.getElementById("formEditIssues"), "submit", m, !1), document.getElementsByTagName("input")[0].focus(), com_satheesh.EVENTS.addEventHandler(document.forms[0].reportDate, "blur", i, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].issuesSummary, "blur", j, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].subscribe, "blur", k, !1)
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
        var a = document.getElementById("reportDate"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpreportDate");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("reportDateIcon").className = "fa fa-check form-text-feedback", document.getElementById("reportDateErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("reportDateIcon").className = "fa fa-remove form-text-feedback", document.getElementById("reportDateErrorMsg").innerHTML = "Please select reported date"), b
    }

    function j() {
        var a = document.getElementById("issuesSummary"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpissuesSummary");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("issuesSummaryIcon").className = "fa fa-check form-text-feedback", document.getElementById("issuesSummaryErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("issuesSummaryIcon").className = "fa fa-remove form-text-feedback", document.getElementById("issuesSummaryErrorMsg").innerHTML = "Please enter issues summary "), b
    }

    function k() {
        var a = document.getElementById("subscribe"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpissuesAssigned");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("issuesAssignedIcon").className = "fa fa-check  form-text-feedback", document.getElementById("issuesAssignedErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("issuesAssignedIcon").className = "fa fa-remove form-text-feedback", document.getElementById("issuesAssignedErrorMsg").innerHTML = "Please enter assign user ")), b
    }

    function l(a) {
        var b = i();
        b &= j(), (b &= k()) || com_satheesh.EVENTS.preventDefault(a)
    }

    function m(a) {
        var b = i();
        b &= j(), (b &= k()) || com_satheesh.EVENTS.preventDefault(a)
    }
    var b = "#FFC";
    com_satheesh.EVENTS.addEventHandler(window, "load", function() {
        f("text")
    }, !1)
});
function validateMaxOdameter(){
	var IssuesType 				 		= Number($('#IssuesType').val());
	var validateOdometerInIssues 		= $('#validateOdometerInIssues').val();
	var validateMinOdometerInIssues  = $('#validateMinOdometerInIssues').val();
	
	var current  	= new Date().getFullYear() + '-' + ('0' + (new Date().getMonth()+1)).slice(-2)+ '-' + ('0' + (new Date().getDate())).slice(-2);
	var reportDate 	= $('#reportDate').val().split("-").reverse().join("-");
	var date = moment(reportDate);
	var now = moment(current);
	
	var expectedOdameter = Number($('#vehicle_ExpectedOdameter').val());
	var vehicleOdometer  = Number($('#vehicle_Odameter').val());
	var issueOdometer	 = Number($('#Issues_Odometer').val());
	var expectedMaxOdameter =  expectedOdameter + Number($('#vehicle_Odameter').val());
	var vehicleOdometer		=  Number($('#vehicle_Odameter').val());
	
	
	if(validateMinOdometerInIssues == 'true' || validateMinOdometerInIssues == true){
		if($('#reportDate').val() != undefined && $('#reportDate').val() != '' && $('#reportDate').val() != null){
			if (!date.isBefore(now)) {
				if(expectedOdameter != undefined &&  expectedOdameter != null && expectedOdameter > 0){
					if(Number($('#Issues_Odometer').val()) != undefined && Number($('#Issues_Odometer').val()) != null){	
						if(issueOdometer > 0 && issueOdometer < vehicleOdometer ){
							$('#Issues_Odometer').focus();
							showMessage('errors', 'Trip Odometer Should Not Be Less Than '+vehicleOdometer);

							return false;
						}
					}
				}
			}	
		}
	}
	
	if(IssuesType != 1 || validateOdometerInIssues == 'false' || validateOdometerInIssues == false){
		return true
	}
	
	if($('#reportDate').val() != undefined && $('#reportDate').val() != '' && $('#reportDate').val() != null){
		
		if (!date.isBefore(now)) {
			
			if(expectedOdameter != undefined &&  expectedOdameter != null && expectedOdameter > 0){
				
				if(Number($('#Issues_Odometer').val()) != undefined && Number($('#Issues_Odometer').val()) != null){
					if(Number($('#Issues_Odometer').val()) > expectedMaxOdameter){
						showMessage('errors', 'You can not enter Odameter greter than '+expectedMaxOdameter);
						$('#Issues_Odometer').focus();
						return false;
					}
					
					
				}
			}
		} 
		
	}
	
	return true;
}