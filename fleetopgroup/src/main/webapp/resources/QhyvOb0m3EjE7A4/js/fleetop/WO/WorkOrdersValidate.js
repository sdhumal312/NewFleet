function visibility(a, b) {
    var c = document.getElementById(a), 
        d = document.getElementById(b);  
    "block" == c.style.display ? (c.style.display = "none", d.style.display = "block") : (c.style.display = "block", d.style.display = "none")
}

function toggle2(a, b) {
    var c = document.getElementById(a),
        d = document.getElementById(b);
    "block" == c.style.display ? (c.style.display = "none", d.innerHTML = "Add Part") : (c.style.display = "block", d.innerHTML = "Cancel Part")
}

function toggle2Labor(a, b) {
    var c = document.getElementById(a),
        d = document.getElementById(b);
    "block" == c.style.display ? (c.style.display = "none", d.innerHTML = "Add Labor") : (c.style.display = "block", d.innerHTML = "Cancel Labor")
}

function toggle2Task(a, b) {
    var c = document.getElementById(a),
        d = document.getElementById(b);
    "block" == c.style.display ? (c.style.display = "none", d.innerHTML = "Add Task") : (c.style.display = "block", d.innerHTML = "Cancel Task")
}

function sumthere(a, b, c) {
    var d = document.getElementById(a).value,
        e = document.getElementById(b).value,
        f = parseFloat(d) * parseFloat(e);
    isNaN(f) || (document.getElementById(c).value = f.toFixed(2))
}

function toggle2Tax(a, b) {
    var c = document.getElementById(a),
        d = document.getElementById(b);
    "block" == c.style.display ? (c.style.display = "none", d.innerHTML = '<i class="fa fa-inr"></i>') : (c.style.display = "block", d.innerHTML = "Cancel")
}

function sumthere(a, b, c) {
    var d = document.getElementById(a).value,
        e = document.getElementById(b).value,
        f = parseFloat(d) * parseFloat(e);
    isNaN(f) || (document.getElementById(c).value = f.toFixed(2))
}

function sumthere(a, b, c, d, e) {
    var f = document.getElementById(a).value,
        g = document.getElementById(b).value,
        h = document.getElementById(c).value,
        i = document.getElementById(d).value,
        j = parseFloat(f) * parseFloat(g),
        k = j * h / 100,
        l = j - k,
        a = l * i / 100,
        m = l + a;
    isNaN(m) || (document.getElementById(e).value = m.toFixed(2))
}

function isNumberKeyQut(a) {
    var b = 0 == a.keyCode ? a.charCode : a.keyCode,
        c = b >= 46 && 57 >= b || -1 != specialKeys.indexOf(a.keyCode) && a.charCode != a.keyCode;
    return document.getElementById("errorINEACH").innerHTML = "Alphabetical Characters not allowed", document.getElementById("errorINEACH").style.display = c ? "none" : "inline", c
}

function isLabertimeKeyQut(a) { 
    var b = 0 == a.keyCode ? a.charCode : a.keyCode,
        c = b >= 46 && 57 >= b || -1 != specialKeys.indexOf(a.keyCode) && a.charCode != a.keyCode;
    return document.getElementById("errorLABOR").innerHTML = "Alphabetical Characters not allowed", document.getElementById("errorLABOR").style.display = c ? "none" : "inline", c
}

function isLaberCostKeyQut(a) {
    var b = 0 == a.keyCode ? a.charCode : a.keyCode,
        c = b >= 48 && 57 >= b || -1 != specialKeys.indexOf(a.keyCode) && a.charCode != a.keyCode;
    return document.getElementById("errorLABOR").innerHTML = "Alphabetical Characters not allowed", document.getElementById("errorLABOR").style.display = c ? "none" : "inline", c
}

function isLaberDisKeyQut(a) {
    var b = 0 == a.keyCode ? a.charCode : a.keyCode,
        c = b >= 46 && 57 >= b || -1 != specialKeys.indexOf(a.keyCode) && a.charCode != a.keyCode;
    return document.getElementById("errorLABOR").innerHTML = "Alphabetical Characters not allowed", document.getElementById("errorLABOR").style.display = c ? "none" : "inline", c
}

function isLaberTaxKeyQut(a) {
    var b = 0 == a.keyCode ? a.charCode : a.keyCode,
        c = b >= 46 && 57 >= b || -1 != specialKeys.indexOf(a.keyCode) && a.charCode != a.keyCode;
    return document.getElementById("errorLABOR").innerHTML = "Alphabetical Characters not allowed", document.getElementById("errorLABOR").style.display = c ? "none" : "inline", c
}

function getJob_subtypetask(a) {
    $.getJSON("getJobSubTypeList.in", {
        ajax: "true"
    }, function(b) {
        for (var c = "", d = b.length, e = 0; d > e; e++) c += '<option value="' + b[e].Job_Type + "-" + b[e].Job_SubType + '">' + b[e].Job_Type + "-" + b[e].Job_SubType + "</option>";
        c += "</option>", $("#" + a).html(c)
    })
}

function getlastdetails(a, b, c, d) {
    $.getJSON("getlastworkorderservice.in", {
        jobtask: a,
        jobsubtask: b,
        vehicle_id: c,
        workorders_id: document.getElementById("lastworkorder_id").value,
        ajax: "true"
    }, function(a) {
        var b = "",
            c = "",
            e = "",
            f = "",
            g = "",
            h = "",
            i = "";
        c = a.start_date, e = a.vehicle_Odometer, f = a.workorders_id, g = a.priority, h = a.assignee, i = a.workorders_status;
        var j = document.getElementById("lastworkorder_id").value;
        if (f != j && null != f) switch (h) {
            case "WorkOrders":
                b = '<p style="color: blue;"> Last occurred for this vehicle on ' + c + " from " + h + " on Odometer = " + e + ' Km. <a href="showWorkOrder?woId=' + f + '"target="_blank">View <i class="fa fa-external-link"></i></a> <br> <small style="color: red;"> ' + g + " | " + i + " </small></p>";
                break;
            default:
                b = '<p style="color: blue;"> Last occurred for this vehicle on ' + c + " from " + h + "  on Odometer = " + e + ' Km. <a href="ServiceEntriesParts.in?SEID=' + f + '"target="_blank">View <i class="fa fa-external-link"></i></a> <br> <small style="color: red;"> ' + g + " | " + i + " </small></p>"
        } else b = '<p style="color: blue;">Never logged for this vehicle.</P>';
        $("#" + d).html(b)
    })
}

function getInventoryListLoc(a, b) {
    $("#" + a).select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getInventorySearchList.in",
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
                        return b == a.location ? {
                            text: a.location + " - " + a.partnumber + " - " + a.partname + " - " + a.partManufacturer + " - " + a.location_quantity,
                            slug: a.slug,
                            id: a.inventory_location_id
                        } : {
                            text: a.location + " - " + a.partnumber + " - " + a.partname + " - " + a.partManufacturer + " - " + a.location_quantity,
                            slug: a.slug,
                            id: a.inventory_location_id,
                            disabled: !0
                        }
                    })
                }
            }
        }
    })
}

function getInventoryList(a) {
    $("#" + a).select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getInventorySearchList.in",
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
                            text: a.location + " - " + a.partnumber + " - " + a.partname + " - " + a.partManufacturer + " - " + a.location_quantity,
                            slug: a.slug,
                            id: a.inventory_location_id
                        }
                    })
                }
            }
        }
    })
}

function getTechinicionName(a) {
    $("#" + a).select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getTechinicianWorkOrder.in",
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
                            text: a.driver_firstname + " - " + a.driver_Lastname,
                            slug: a.slug,
                            id: a.driver_id
                        }
                    })
                }
            }
        }
    })
}

function getQuantity(a, b, c) {
    $.getJSON("getInventoryQuantityList.in", {
        inventoryID: document.getElementById(a).value,
        ajax: "true"
    }, function(a) {
        var b = "";
        b = a.quantity;
        var d = document.getElementById(c);
        d.setAttribute("max", b), document.getElementById(c).value = ""
    })
}

function getROT_COST_Hour(a, b, c, d) {
    $.getJSON("getJobSubTypeRTOCost.in", {
        JobSUBTypeID: a,
        ajax: "true"
    }, function(a) {
        var e, f, g = 0;
        e = a.Job_ROT_hour, f = a.Job_ROT_amount, g = e * f, document.getElementById("" + b).value = e, document.getElementById("" + c).value = f, document.getElementById("" + d).value = g
    })
}
var specialKeys = new Array;
specialKeys.push(8), specialKeys.push(9), specialKeys.push(46), specialKeys.push(36), specialKeys.push(35), specialKeys.push(37), specialKeys.push(39), $(document).ready(function() {
    $("#WOLocationSelect").select2({
        ajax: {
            url: "getWorkOrderPartlocation.in?Action=FuncionarioSelect2",
            dataType: "json",
            type: "GET",
            contentType: "application/json",
            data: function(a) {
                return {
                    term: a
                }
            },
            results: function(a) {
            	console.log(a);
                return {
                    results: $.map(a, function(a) {
                        return {
                            text: a.partlocation_name,
                            slug: a.slug,
                            id: a.partlocation_id
                        }
                    })
                }
            }
        }
    }), $("#taskfrom").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getJobTypeService.in?Action=FuncionarioSelect2",
            dataType: "json",
            type: "GET",
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
                            text: a.Job_Type,
                            slug: a.slug,
                            id: a.Job_Type
                        }
                    })
                }
            }
        }
    }), $("#taskfrom").change(function() {
        $(this).val(), $.getJSON("getJobSubTypeChangeService.in", {
            JobType: $(this).val(),
            ajax: "true"
        }, function(a) {
            for (var b = '<option value="0">Please Select</option>', c = a.length, d = 0; c > d; d++) b += '<option value="' + a[d].Job_Subid + '">' + a[d].Job_ROT + " - " + a[d].Job_ROT_number + "</option>";
            b += "</option>", $("#subtask_ID").html(b)
        })
    })
}), $(document).ready(function() {
    $(".select2").select2(), $("#tagPicker").select2({
        closeOnSelect: !1
    }), $("#select3").on("change", function() {
        var a = "",
            b = $(this).val();
        $("#vehicle_Meter option").each(function() {
            b == $(this).val() && (a = $(this).text())
        }), document.getElementById("vehicle_Odometer").placeholder = a, document.getElementById("vehicle_Odometer_old").value = a, $("#hidden").hide()
    })
}), $(document).ready(function() {
    var a = 25,
        b = $(".input_fields_wrap"),
        c = $(".add_field_button"),
        d = 1;
    $(c).click(function(c) {
    	 var count =	Number($('#count').val());
    	 $('#count').val(count + 1);
        c.preventDefault(), a > d && (d++, $(b).append('<div><div class="row1"><label class="L-size control-label" for="priority">JobType </label><div class="I-size"><div class="col-md-8"> <input type="hidden" id="from' + d + '" name="job_typetaskId" style="width: 100%;" required="required" placeholder="Please Enter 3 or more Job Type Name" /></div></div></div><div class="row1"><label class="L-size control-label">Service Sub Jobs <abbr title="required">*</abbr></label><div class="I-size"  id="JoBSelect' + d + '" ><div class="col-md-8" ><input type="hidden"  name="job_subtypetask" value="0" id="to' + d + '" style="width: 100%;" required="required"placeholder="Please Enter 3 or more Job Sub Type Name"/></div><div class="col-md-1"><a class=" btn btn-link " onclick="visibility( \'JoBEnter' + d + "', 'JoBSelect" + d + '\');"> <i class="fa fa-plus"> New</i></a></div></div><div id="JoBEnter' + d + '" class="contact_Hide"><div class="I-size"><div class="col-md-8"><input type="text" class="form-text" id="SubReType'+ d +'" \tname="Job_ROT" maxlength="150" placeholder="Enter ROT Name"  onpaste="return false" onkeypress="return /[a-z]/i.test(event.key)"; /><br> <input type="text" class="form-text" \tid="SubReType" name="Job_ROT_number" maxlength="30" \tplaceholder="Enter ROT Number"  onpaste="return false" onkeypress="return isNumberKey(event)" /></div><div class="col-md-1"><a class=" btn btn-link col-sm-offset-1" \tonclick="visibility( \'JoBEnter' + d + "', 'JoBSelect" + d + '\');"> <i\tclass="fa fa-minus"> Select</i></a></div></div></div></div><div class="jobTypeRemarkCol row1 form-group"><label class="L-size control-label" for="to">Job Type Remark<abbr title="required">*</abbr></label><div class="I-size"><div class="col-md-8"><input type="text" class="form-text" id="JobTypeRemark'+ d +'" name="JobTypeRemark" maxlength="150" placeholder="Enter Remark" /></div></div></div><a href="#" class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font></div>'), $(document).ready(function() {
            $("#from" + d).select2({
                minimumInputLength: 2,
                minimumResultsForSearch: 10,
                ajax: {
                    url: "getJobTypeWorkOrder",
                    dataType: "json",
                    type: "GET",
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
                                    text: a.Job_Type,
                                    slug: a.slug,
                                    id: a.Job_id
                                }
                            })
                        }
                    }
                }
            }), $("#to" + d).select2({
                minimumInputLength: 3,
                minimumResultsForSearch: 10,
                ajax: {
                    url: "getJobSubTypeWorkOrder.in",
                    dataType: "json",
                    type: "GET",
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
                                    text: a.Job_ROT + '" - "' + a.Job_ROT_number,
                                    slug: a.slug,
                                    id: a.Job_Subid
                                }
                            })
                        }
                    }
                }
            }), $("#from" + d).change(function() {
                $.getJSON("getJobSubTypeChangeWorkOrder.in", {
                    JobType: $(this).val(),
                    ajax: "true"
                }, function(a) {
                    for (var b = a.length - 1, c = "", e = a.length, f = 0; e > f; f++) b != f ? (c += '{"id":"' + a[f].Job_Subid + '","text":"' + a[f].Job_ROT + "-" + a[f].Job_ROT_number + '" }', c += ",") : c += '{"id":"' + a[f].Job_Subid + '","text":"' + a[f].Job_ROT + "-" + a[f].Job_ROT_number + '" }';
                    var g = "[" + c + "]",
                        h = JSON.parse(g);
                    $("#to" + d).select2({
                        allowClear: !0,
                        data: h
                    })
                })
            }), $("#to" + d).change(function() {
                $.getJSON("getJobSubTypeChangeOnlyID.in", {
                    JobSub_ID: $(this).val(),
                    ajax: "true"
                }, function(a) {
                    $("#from" + d).select2("data", {
                        id: a.Job_TypeId,
                        text: a.Job_Type
                    })
                })
            }), $("#JoBEnter" + d).hide()
        }))
        if($("#showJobTypeRemarkCol").val() == false || $("#showJobTypeRemarkCol").val() == 'false') {
        	$(".jobTypeRemarkCol").addClass("hide");        	
        }
    }), $(b).on("click", ".remove_field", function(a) {
    	  var count =	Number($('#count').val());
    	   $('#count').val(count - 1);
        a.preventDefault(), $(this).parent("div").remove(), d--
    })
}), $(document).ready(function() {
    var a = 2,
        b = $(".input_fields_wrap"),
        c = $(".add_inventory"),
        d = 1;
    $(c).click(function(c) {
        if (c.preventDefault(), a > d) {
            d++;
            var e = "";
            $(b).append('<div class="col-md-8"><select style="width: 100%;" name="job_subtypetask" id="task' + d + '"></select><a href="#" class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font></div>'), $("#inventory_name option").each(function() {
                e += '<option value="' + $(this).text() + '">' + $(this).text() + "</option>"
            }), $("#task" + d).html(e), $("#task" + d).select2()
        }
    }), $(b).on("click", ".remove_field", function(a) {
        a.preventDefault(), $(this).parent("div").remove(), d--
    })
}), $(document).ready(function() {
    $("#select3").change(function() {
        $.getJSON("getVehicleOdoMerete.in", {
            vehicleID: $(this).val(),
            ajax: "true"
        }, function(a) {
            var b = "";
            b = a.vehicle_Odometer, document.getElementById("vehicle_Odometer").placeholder = b, document.getElementById("vehicle_Odometer_old").value = b
            $('#Odometer').val(a.vehicle_Odometer);
            $('#vehicle_ExpectedOdameter').val(a.vehicle_ExpectedOdameter);
            if(a.gpsOdameter != undefined && a.gpsOdameter > 0){
            	$('#vehicle_Odometer').val(a.vehicle_Odometer);
            	$('#gpsOdometer').val(a.gpsOdameter);
            	$('#vehicle_Odometer').val(parseInt(a.gpsOdameter));
            	$('#gpsWorkLocation').val(a.gpsLocation);
            	$('#gpsOdometerRow').show();
            	$('#gpsWorkLocationRow').show();
            	//$('#grpwoOdometer').hide();
            }else{
            	$('#gpsWorkLocationRow').hide();
            	$('#grpwoOdometer').show();
            }
        })
    })
}), $(document).ready(function() {
	$("#ServiceReminder").select2({
		multiple: !0,
		allowClear: !0,
        data : ""
    })
    $("#select3").change(function() {
        $.getJSON("getVehicleServiceReminderList.in", {
            vehicleID: $(this).val(),
            ajax: "true"
        },  function(a) {
        	 for (var b = a.length - 1, c = "", d = a.length, e = 0; d > e; e++) b != e ? (c += '{"id":"' + a[e].service_id + '","text":"' + a[e].service_Number + "-" + a[e].service_type + "-" + a[e].service_subtype + "-" + a[e].time_servicedate + '" }', c += ",") : c += '{"id":"' + a[e].service_id + '","text":"' + a[e].service_type + "-" + a[e].service_subtype + "-" + a[e].time_servicedate + '" }';
             var f = "[" + c + "]",
                 g = JSON.parse(f);
             $("#ServiceReminder").select2({
            	 multiple: !0,
            	 allowClear: !0,
                 data: g
             })
             
             $("#ServiceReminder").change(function(obj){
            	 if(obj.added != undefined){
            		 var addedValue	= obj.added;
            		 var serviceId 	= addedValue.id;
            		 
            		 for(var i=0 ; i<a.length; i++){
            			 if(a[i].service_id == serviceId){
            				 $("#JobType1").val(a[i].service_type);
            				 $("#JobSubType1").val(a[i].service_subtype);
            				 
            				 if(obj.val.length == 1 || $("#job_serviceId").val() == ""){
            					 $("#job_serviceId").val(serviceId);
            					 $("#from").val(a[i].serviceTypeId);
            					 $("#to").val(a[i].serviceSubTypeId);
            					 
            					 var fromDiv = $("#from").prev()[0]; 
            					 $("#"+fromDiv.id+" *").prop("disabled",true);
            					 
            					 var toDiv = $("#to").prev()[0]; 
            					 $("#"+toDiv.id+" *").prop("disabled",true);
            					 
            					 $($("#from").prev().children()[0]).text(a[i].service_type);
            					 $($("#to").prev().children()[0]).text(a[i].service_subtype);
            					// document.getElementById("s2id_to").disabled = true; //s2id_from
            					 //$($("#to").prev().children()[0].prev().prev().children()[0]).attr("readOnly",true);
            				 } else {
            					 
            					 var newJobType 	= $("#grpJobType").clone();
            					 newJobType[0].id 	= 'grpJobType_'+serviceId;
            					 $(newJobType[0]).removeClass("hide");
            					 $("#grpJobType").after(newJobType[0]);
            					 
            					 var newJobSubType 		= $("#grpJobSubType").clone();
            					 newJobSubType[0].id 	= 'grpJobSubType_'+serviceId;
            					 $(newJobSubType[0]).removeClass("hide");
            					 $("#grpJobSubType").after(newJobSubType[0]);
            				 }
            				 
            			 } 
            		 }
            	 } else if(obj.removed != undefined) {

            		 var removeValue	= obj.removed;
            		 var serviceId 		= removeValue.id;
            		 
            		 for(var i=0 ; i<a.length; i++){
            			 if(a[i].service_id == serviceId){
            				 $("#JobType1").val(a[i].service_type);
            				 $("#JobSubType1").val(a[i].service_subtype);
            				 
            				 if($("#job_serviceId").val() == serviceId){
            					 
            					 $("#job_serviceId").val("");
            					 $("#from").val();
            					 $("#to").val();
            					 
            					 var fromDiv = $("#from").prev()[0]; 
            					 $("#"+fromDiv.id+" *").prop("disabled",false);
            					 var toDiv = $("#to").prev()[0]; 
            					 $("#"+toDiv.id+" *").prop("disabled",false);
            					 
            					 $($("#from").prev().children()[0]).text("");
            					 $($("#to").prev().children()[0]).text("");
            				 } else {
            					 
            					 $("#grpJobType_"+serviceId).remove();
            					 $("#grpJobSubType_"+serviceId).remove();
            				 }
            				 
            			 } 
            		 }
            	 }
            			 
             })
        })
       
    })
    
}), $(document).ready(function() {
    $.getJSON("getJobTypeList.in", {
        ajax: "true"
    }, function(a) {
        for (var b = '<option value="">Please Select</option>', c = a.length, d = 0; c > d; d++) b += '<option value="' + a[d].Job_Type + '">' + a[d].Job_Type + "</option>";
        b += "</option>", $("#jobType").html(b)
    })
}), $(document).ready(function() {
	
    $("#select3").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getVehicleSearchWorkOrder.in?Action=FuncionarioSelect2",
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
                            text: a.vehicle_registration,
                            slug: a.slug,
                            id: a.vid
                        }
                    })
                }
            }
        }
    }), $("#selectJobType").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getJobTypeSearchListWorkOrder.in?Action=FuncionarioSelect2",
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
                            text: a.Job_Type,
                            slug: a.slug,
                            id: a.Job_Type
                        }
                    })
                }
            }
        }
    }), $("#SelectDriverName").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getDriverSearchListWorkOrder.in?Action=FuncionarioSelect2",
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
                            text: a.driver_empnumber + " - " + a.driver_firstname + " - " + a.driver_mobnumber,
                            slug: a.slug,
                            id: a.driver_id
                        }
                    })
                }
            }
        }
    }), $("#from").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getJobTypeWorkOrder.in",
            dataType: "json",
            type: "GET",
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
                            text: a.Job_Type,
                            slug: a.slug,
                            id: a.Job_id
                        }
                    })
                }
            }
        }
    }), $("#to").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getJobSubTypeWorkOrder.in",
            dataType: "json",
            type: "GET",
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
                            text: a.Job_ROT + '" - "' + a.Job_ROT_number,
                            slug: a.slug,
                            id: a.Job_Subid
                        }
                    })
                }
            }
        }
    })
}),

$(document).ready(function() {
    $("#from").change(function() {
        $.getJSON("getJobSubTypeChangeWorkOrder.in", {
            JobType: $(this).val(),
            ajax: "true"
        }, function(a) {
            for (var b = a.length - 1, c = "", d = a.length, e = 0; d > e; e++) b != e ? (c += '{"id":"' + a[e].Job_Subid + '","text":"' + a[e].Job_ROT + "-" + a[e].Job_ROT_number + '" }', c += ",") : c += '{"id":"' + a[e].Job_Subid + '","text":"' + a[e].Job_ROT + "-" + a[e].Job_ROT_number + '" }';
            var f = "[" + c + "]",
                g = JSON.parse(f);
            $("#to").select2({
                allowClear: !0,
                data: g
            })
        })
    }), $("#to").change(function() {
        $.getJSON("getJobSubTypeChangeOnlyID.in", {
            JobSub_ID: $(this).val(),
            ajax: "true"
        }, function(a) {
            $("#from").select2("data", {
                id: a.Job_TypeId,
                text: a.Job_Type
            })
        })
    })
}), $(document).ready(function() {
    $("#JoBEnter").hide()
    $("#workLocationNew").hide()
}), $(document).ready(function() {
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

$(document).ready(function() {
	
    function f(a) {
        for (var b = document.getElementsByTagName("textarea"), c = 0; c < b.length; c++) com_satheesh.EVENTS.addEventHandler(b[c], "focus", g, !1), com_satheesh.EVENTS.addEventHandler(b[c], "blur", h, !1);
        b = document.getElementsByTagName("input");
        for (var c = 0; c < b.length; c++) a.indexOf(-1 != b[c].getAttribute("type")) && (com_satheesh.EVENTS.addEventHandler(b[c], "focus", g, !1), com_satheesh.EVENTS.addEventHandler(b[c], "blur", h, !1));
        com_satheesh.EVENTS.addEventHandler(document.getElementById("formWorkOrder"), "submit", p, !1), com_satheesh.EVENTS.addEventHandler(document.getElementById("formEditWorkOrder"), "submit", q, !1), document.getElementsByTagName("input")[0].focus(), com_satheesh.EVENTS.addEventHandler(document.forms[0].select3, "blur", i, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].subscribe, "blur", j, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].woStartDate, "blur", k, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].woEndDate, "blur", l, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].vehicle_Odometer, "blur", m, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].from, "blur", n, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].to, "blur", o, !1)
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
        var a = document.getElementById("select3"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpvehicleName");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("vehicleNameIcon").className = "fa fa-check form-text-feedback", document.getElementById("vehicleNameErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("vehicleNameIcon").className = "fa fa-remove form-text-feedback", document.getElementById("vehicleNameErrorMsg").innerHTML = "Please select vehicle name"), b
    }

    function j() {
        var a = document.getElementById("subscribe"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpwoAssigned");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("woAssignedIcon").className = "fa fa-check form-text-feedback", document.getElementById("woAssignedErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("woAssignedIcon").className = "fa fa-remove form-text-feedback", document.getElementById("woAssignedErrorMsg").innerHTML = "Please select assigned to user"), b
    }

    function k() {
        var a = document.getElementById("woStartDate"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpwoStartDate");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("woStartDateIcon").className = "fa fa-check form-text-feedback", document.getElementById("woStartDateErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("woStartDateIcon").className = "fa fa-remove form-text-feedback", document.getElementById("woStartDateErrorMsg").innerHTML = "Please select start date"), b
    }

    function l() {
        var a = document.getElementById("woEndDate"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpwoEndDate");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("woEndDateIcon").className = "fa fa-check  form-text-feedback", document.getElementById("woEndDateErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("woEndDateIcon").className = "fa fa-remove form-text-feedback", document.getElementById("woEndDateErrorMsg").innerHTML = "Please select end date")), b
    }

    function m() {
        var a = document.getElementById("vehicle_Odometer"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpwoOdometer");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("woOdometerIcon").className = "fa fa-check  form-text-feedback", document.getElementById("woOdometerErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("woOdometerIcon").className = "fa fa-remove form-text-feedback", document.getElementById("woOdometerErrorMsg").innerHTML = "Please enter odometer")), b
    }

    function n() {
        var a = document.getElementById("from"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpwoJobType");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("woJobTypeIcon").className = "fa fa-check  form-text-feedback", document.getElementById("woJobTypeErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("woJobTypeIcon").className = "fa fa-remove form-text-feedback", document.getElementById("woJobTypeErrorMsg").innerHTML = "Please select job type")), b
    }

    function o() {
        var a = document.getElementById("to"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpwoJobSubType");
        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("woJobSubTypeIcon").className = "fa fa-check  form-text-feedback", document.getElementById("woJobSubTypeErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("woJobSubTypeIcon").className = "fa fa-remove form-text-feedback", document.getElementById("woJobSubTypeErrorMsg").innerHTML = "Please select job sub type")), b
    }

    function p(a) {
        var b = i();
        b &= j(), b &= k(), b &= l(), b &= m(), b &= n(), (b &= o()) || com_satheesh.EVENTS.preventDefault(a)
    }

    function q(a) {
        var b = i();
        b &= j(), b &= k(), b &= l(), (b &= m()) || com_satheesh.EVENTS.preventDefault(a)
    }
    
    function r(a) {
        for (var b = document.getElementsByTagName("textarea"), c = 0; c < b.length; c++) com_satheesh.EVENTS.addEventHandler(b[c], "focus", g, !1), com_satheesh.EVENTS.addEventHandler(b[c], "blur", h, !1);
        b = document.getElementsByTagName("input");
        for (var c = 0; c < b.length; c++) a.indexOf(b[c].getAttribute("type") != -1) && (com_satheesh.EVENTS.addEventHandler(b[c], "focus", g, !1), com_satheesh.EVENTS.addEventHandler(b[c], "blur", h, !1));
        com_satheesh.EVENTS.addEventHandler(document.getElementById("formWorkOrder"), "submit", q, !1), document.getElementsByTagName("input")[0].focus(), com_satheesh.EVENTS.addEventHandler(document.forms[0].ServiceReminder, "blur", i, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].from, "blur", j, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].to, "blur", k, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].meter_interval, "blur", l, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].time_interval, "blur", m, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].renewal_timethreshold, "blur", n, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].time_threshold, "blur", o, !1), com_satheesh.EVENTS.addEventHandler(document.forms[0].subscribe, "blur", p, !1)
    }
    
    function s() {
        var a = document.getElementById("ServiceReminder"),
            b = null != a.value && 0 != a.value.length,
            c = document.getElementById("grpvehicleNumber");
        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("vehicleNumberIcon").className = "fa fa-check form-text-feedback", document.getElementById("vehicleNumberErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("vehicleNumberIcon").className = "fa fa-remove form-text-feedback", document.getElementById("vehicleNumberErrorMsg").innerHTML = "Please select vehicle number"), b
    }
    
    var b = "#FFC";
    com_satheesh.EVENTS.addEventHandler(window, "load", function() {
        f("text")
    }, !1)
});

$(document).ready(function() {
	var startTimeForGroup = $('#startTimeForGroup').val();

	if(startTimeForGroup == 'true' || startTimeForGroup == true){
			var today = new Date();
			var dd = today.getDate();
			var mm = today.getMonth()+1; //January is 0!
			var time = today.getHours();
			var minute = today.getMinutes();
			if(today.getMinutes()<10){
				minute = '0'+minute;
			} 
			if(today.getHours()<10){
				time = '0'+time;
			}
			$('#woStartTime').val(time+':'+minute);
			var yyyy = today.getFullYear();
			if(dd<10){
			    dd='0'+dd;
			} 
			if(mm<10){
			    mm='0'+mm;
			} 
			var today = dd+'-'+mm+'-'+yyyy;
			document.getElementById("woStartDate").value = today;
	}
});

function validateOdometer(){ 

	
	if(!validateJobType()){
		return false;
	}
	
	if(!validateTallyCompanyMaster()){
		return false;
	}
	
	document.getElementById("#woStartDate");
	var validateOdometerInWorkOrder = $('#validateOdometerInWorkOrder').val();
	var validateMinOdometerInWorkOrder = $('#validateMinOdometerInWorkOrder').val();
	var expectedOdo		 = Number($('#vehicle_ExpectedOdameter').val()) + Number($('#Odometer').val());
	var Odometer		 = Number($('#Odometer').val());
	var tripOpeningKM    = Number($('#vehicle_Odometer').val());
	var current  		= new Date().getFullYear() + '-' + ('0' + (new Date().getMonth()+1)).slice(-2)+ '-' + ('0' + (new Date().getDate())).slice(-2);
	var reportDate 		= $('#woStartDate').val().split("-").reverse().join("-");
	var date 			= moment(reportDate);
	var now 			= moment(current);
	
	if(validateMinOdometerInWorkOrder == 'true' || validateMinOdometerInWorkOrder == true){
		if($('#woStartDate').val() != undefined && $('#woStartDate').val() != '' && $('#woStartDate').val() != null){
			if (!date.isBefore(now)) {
				if(tripOpeningKM > 0 && tripOpeningKM < Odometer){
					$('#tripOpeningKM').focus();
					showMessage('errors', 'Trip Odometer Should Not Be Less Than '+Odometer);
					
					return false;
				}
			}
		}
	}
	
	
	
	if(validateOdometerInWorkOrder == 'false' || validateOdometerInWorkOrder == false){
		return true;
	}
	if($('#vehicle_ExpectedOdameter').val() == undefined || $('#vehicle_ExpectedOdameter').val() == '' || $('#vehicle_ExpectedOdameter').val() == null){
		return true;
	}
	
	if(tripOpeningKM > 0 && tripOpeningKM > expectedOdo){
		$('#vehicle_Odometer').focus();
		showMessage('errors', 'Trip Odometer cannot be greater than '+expectedOdo );
		return false;
	}
	
	return true;
}

function validateJobType(){
	
	var noJobType	 = false;
	var noSubJobType = false;
	var noRotType	 = false;
	
	$('input[name*=job_typetaskId]').each(function(){
	var jobType = Number($("#"+$( this ).attr( "id" )).val());
	if(jobType == undefined || jobType == 0){
		noJobType	 = false;
		showMessage('errors', 'Please select JobType !');
		return false;
	}
	});

	var count = Number($('#count').val());
	//return false;
	
	if(count == 1){
		var to = Number($('#to').val());
		var SubReType = $('#SubReType').val();
		if((to == undefined || to == 0) && (SubReType == undefined || SubReType.trim() == '' ) ){
			showMessage('errors', 'Please enter Job Sub Type !');
			return false;
		}
	}else{
		for(var i = 2; i <= count; i++ ){
			
			var to1 = Number($('#to').val());
			var to = Number($('#to'+i).val()); 						
			var SubReType1 = $('#SubReType').val();  
			var SubReType = $('#SubReType'+i).val();  
			
			if((to == undefined || to == 0) && (SubReType == undefined || SubReType.trim() == '' )){  
				showMessage('errors', 'Please enter Job Sub Type !');
				return false;
			}else if((to1 == undefined || to1 == 0) && (SubReType1 == undefined || SubReType1.trim() == '' )){ 
				showMessage('errors', 'Please enter Job Sub Type !');
				return false;
			}
			
		}
	}
	
	var remarkAdd = false;
	if($('#showJobTypeRemarkCol').val() == 'true' || $('#showJobTypeRemarkCol').val() == true){
		$('input[name*=JobTypeRemark]' ).each(function(){
			var rem = $("#"+$( this ).attr( "id" )).val();
			if(rem == undefined || rem <= 0){
				$("#"+$( this ).attr( "id" )).focus();
				remarkAdd = true;
				showMessage('errors', 'Please Enter Remark!');
				return false;
			}
		});
	}
	
	if(noJobType || remarkAdd){
		return false;
	}
	
	return true;
}

function validateTallyCompanyMaster(){
	
	if($("#tallyConfig").val() == true || $("#tallyConfig").val() == 'true' ){
		if($("#tallyCompanyId").val() == "" || $("#tallyCompanyId").val() == undefined ){
			console.log("tally ",$("#tallyCompanyId").val())
			showMessage('info','Please Select Tally Company Master');
			return false;
		}
	}else{
		$("#tallyCompanyId").val(0);
	}
	return true;
}



function woValidate(){
	if(Number($('#to').val()) <= 0){
		showMessage('errors','Please Select Job Subtype !');
		return false;
	}	
}

$(document).ready(function() {
    $("#tallyCompanyId").select2({
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
    })
});

$(document).ready(function() {
    var currentDate = new Date();
    var previousDate 			= $('#backDateString').val();
    if(previousDate != undefined){
    	var previousDateForBackDate =   previousDate.split("-")[0] + '-' +  previousDate.split("-")[1] + '-' +  previousDate.split("-")[2];
    	$("#StartDate1").datepicker({
    		defaultDate: new Date(),
    		autoclose: !0,
    		todayHighlight: !0,
    		format: "dd-mm-yyyy",
    		setDate: "0",
    		endDate: "currentDate",
    		startDate:previousDateForBackDate,
    		maxDate: currentDate
    	}),
    	$("#LeaveDate1").datepicker({
    		defaultDate: new Date(),
    		autoclose: !0,
    		todayHighlight: !0,
    		format: "dd-mm-yyyy",
    		setDate: "0",
    		endDate: "currentDate",
    		startDate:previousDateForBackDate,
    		maxDate: currentDate
    	});
    }
});