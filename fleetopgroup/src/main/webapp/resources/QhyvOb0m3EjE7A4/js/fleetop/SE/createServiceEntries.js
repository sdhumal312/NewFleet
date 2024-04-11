function getJob_subtypetask(a) {
    $.getJSON("getJobSubTypeList.in", {
        ajax: "true"
    }, function(b) {
        for (var c = "", d = b.length, e = 0; d > e; e++) c += '<option value="' + b[e].Job_Type + "-" + b[e].Job_SubType + '">' + b[e].Job_Type + "-" + b[e].Job_SubType + "</option>";
        c += "</option>", $("#" + a).html(c)
    })
}

function getInventoryList(a) {
    $("#" + a).select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getPartList.in",
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
                            text: a.partnumber + " - " + a.partname + " - " + a.make,
                            slug: a.slug,
                            id: a.partid
                        }
                    })
                }
            }
        }
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
    $("#vendorEnter").hide(), $("#driverEnter").hide()
}), 

$(document).ready(function() {
	
    $("#selectVendor").on("change", function() {
        var a = document.getElementById("selectVendor").value;
        if (0 != a) {
            var b = '<option value="1"> CASH</option><option value="2">CREDIT</option><option value="3">NEFT</option><option value="4">RTGS</option><option value="5">IMPS</option><option value="6">DD</option><option value="7">CHEQUE</option><option value="10">ON ACCOUNT</option>';
            $("#renPT_option").html(b)
        } else {
            var b = '<option value="1">CASH</option>';
            $("#renPT_option").html(b)
        }
    }), 
    
    $("#selectVendor").change(), $("#renPT_option").on("change", function() {
        showoption()
    }), 
    
    $("#renPT_option").change()
}), 

$(document).ready(function() {
    $(".select2").select2(), $("#tagPicker").select2({
        closeOnSelect: !1
    }), $("#select3").on("change", function() {
        var a = "",
            b = $(this).val();
        $("#vehicle_Meter option").each(function() {
            b == $(this).val() && (a = $(this).text())
        }), document.getElementById("vehicle_Odometer").placeholder = a, document.getElementById("vehicle_Odometer_old").value = a, $("#hidden").hide()
    })
}), 

$(document).ready(function() {
    var a = 25,
        b = $(".input_fields_wrap"),
        c = $(".add_field_button"),
        d = 1;
    $(c).click(function(c) {
    	var count =	Number($('#count').val());
   	 	$('#count').val(count + 1);
        c.preventDefault(), a >= d && (d++, $(b).append('<div><div class="row1"><label class="L-size control-label" for="priority">JobType </label><div class="I-size"><div class="col-md-8"> <input type="hidden" id="from' + d + '" name="service_typetask" style="width: 100%;" required="required" placeholder="Please Enter 3 or more Job Type Name" /></div></div></div><div class="row1"><label class="L-size control-label">Service Sub Jobs <abbr title="required">*</abbr></label><div class="I-size" id="JoBSelect' + d + '"><div class="col-md-8"><input type="hidden"   name="service_subtypetask" id="to' + d + '" style="width: 100%;" value="0" required="required"placeholder="Please Enter 3 or more Job Sub Type Name" /></div><div class="col-md-1"><a class=" btn btn-link " onclick="visibility( \'JoBEnter' + d + "', 'JoBSelect" + d + '\');setValue(SubReType'+ d +',SubReTypeNum'+ d +',to' + d + ')"> <i class="fa fa-plus"> New</i></a></div></div><div id="JoBEnter' + d + '" class="contact_Hide"><div class="I-size"><div class="col-md-8"><input type="text" class="form-text"  name="service_ROT" id="SubReType'+ d +'" maxlength="150" value="0" placeholder="Enter ROT Name" /><br> <input type="text" class="form-text" \t name="service_ROT_number" id="SubReTypeNum'+ d +'" value="0" maxlength="30" \tplaceholder="Enter ROT Number" /></div><div class="col-md-1"><a class=" btn btn-link col-sm-offset-1" \tonclick="visibility( \'JoBEnter' + d + "', 'JoBSelect" + d + '\');setBackValue(SubReType'+ d +',SubReTypeNum'+ d +');"> <i\tclass="fa fa-minus"> Select</i></a></div></div></div></div><div class="jobTypeRemarkCol row1 form-group"><label class="L-size control-label" for="to">Remark: </label><div class="I-size"><div class="col-md-8"><input type="text" class="form-text" id="taskRemark" name="taskRemark" maxlength="250" placeholder="Enter Remark" /></div></div></div><a href="#" class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font></div>'), $(document).ready(function() {
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
                multiple: !0,
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
                        multiple : !0,
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
    }), $(b).on("click", ".remove_field", function(a) {
    	 var count =	Number($('#count').val());
 	   $('#count').val(count - 1);
        a.preventDefault(), $(this).parent("div").remove(), d--
    })
}), 

$(document).ready(function() {
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
    }), 
    
    $(b).on("click", ".remove_field", function(a) {
        a.preventDefault(), $(this).parent("div").remove(), d--
    })
}), 

$("#vehicle_vid").change(function() {
	vehicleOnChange1();
	vehicleOnChange2();
	if($("#showPendingIssueWhileCreatingSE").val() == true || $("#showPendingIssueWhileCreatingSE").val() == 'true'){
		getIssueDetails($("#vehicle_vid").val());
	}
});
$("#vehicle_vid").on("change", function() {
	
var showVehicleHealthStatus =  $("#showVehicleHealthStatus").val();
	
	if(showVehicleHealthStatus == true || showVehicleHealthStatus == 'true'){
		if($('#vehicle_vid').select2('data') != null){
			var vehicleNumber =  $('#vehicle_vid').select2('data').text;
			var vehicleStr = vehicleNumber.replace(/-/g, "");
			getVehicleStatusByVehicleNumber(vehicleStr);
		}else{
			$("#healthStatusId").addClass('hide');
			$("#healthStatusName").addClass('hide');
		}
	}
	
});

function vehicleOnChange1() {
    $.getJSON("getVehicleOdoMerete.in", {
        vehicleID: $("#vehicle_vid").val(),
        ajax: "true"
    }, function(a) {
    	console.log("a..",a);
        var b = "";
        b = a.vehicle_Odometer, document.getElementById("vehicle_Odometer").placeholder = b;
        if(document.getElementById("vehicle_Odometer_old") != null){
        	document.getElementById("vehicle_Odometer_old").value = b;
        }
        if(a.gpsOdameter != undefined && a.gpsOdameter > 0){
        	$('#grpgpsOdometer').show();
        	$('#gpsOdometer').val(a.gpsOdameter);
        	$('#vehicle_Odometer').val(parseInt(a.gpsOdameter));
        	//$('#grpodoMeter').hide();
        	//$("#vehicle_Odometer").attr('readonly','readonly')

        }
    })
}

function vehicleOnChange2() {
    $.getJSON("getVehicleServiceReminderList.in", {
        vehicleID: $("#vehicle_vid").val(),
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
        			 
         });
         $('#ServiceReminder').select2('data', {
    			id: g[0].id,
    			text: g[0].text,
    			data: g
        	 
         });
        
    })
   
}

	

$(document).ready(function() {
	 
	if($("#issueVid").val() > 0 ){
			$("#SEVehicle").hide();
			$("#issueVehicle").show();
			$('#vehicle_vid').val($("#issueVid").val());
			
			vehicleOnChange1();
			vehicleOnChange2();
	}
	
	$("#ServiceReminder").select2({
		multiple: !0,
		allowClear: !0,
        data : ""
    })
}),
	
$(document).ready(function() {
    $.getJSON("getJobTypeList.in", {
        ajax: "true"
    }, function(a) {
        for (var b = '<option value="">Please Select</option>', c = a.length, d = 0; c > d; d++) b += '<option value="' + a[d].Job_id + '">' + a[d].Job_Type + "</option>";
        b += "</option>", $("#jobType").html(b)
    })
}), 

$(document).ready(function() {
    $("#vehicle_vid").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getVehicleSearchServiceEntrie.in?Action=FuncionarioSelect2",
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
    }); 
    $("#selectVendor").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getVendorSearchListPart.in?Action=FuncionarioSelect2",
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
    }), $("#SelectDriverName").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getDriverSearchAllListFuel.in?Action=FuncionarioSelect2",
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
                            text: a.driver_empnumber + " - " + a.driver_firstname +" "+a.driver_Lastname+" - "+a.driver_fathername,
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
        multiple: !0,
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
    }),$("#tallyExpenseId").select2({
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
    });
    //creating Service entry from service reminder 
    if($("#fromSR").val() == true || $("#fromSR").val() == 'true'){
		
		$('#from').select2('data', {
			id : $("#jobTypeId").val(),
			text : $("#jobType").val()
		});
		$('#to').select2('data', {
			id : $("#subJobTypeId").val(),
			text : $("#subJobType").val()
		});
		$("#addMoreJob").hide();
		$("#newJob").hide();
		$("#from").select2('readonly', true);
		$("#to").select2('readonly', true);
		$("#ServiceReminder").attr('readonly', true);
		
	}
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
                multiple: !0,
                data: g
            })
        })
    }),
    
    $("#to").change(function() {
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
    
}), 

$(document).ready(function() {
    $("#JoBEnter").hide()
});



function validateServiceEntry(){
	
	var noJobType	 = false;
	var noSubJobType = false;
	var noRotType	 = false;
	
	if(Number($('#vehicle_vid').val()) <= 0){
		$("#vehicle_vid").select2('focus');
		showMessage('errors', 'Please Select Vehicle !');
		return false;
	}
	
	if($("#tallyConfig").val() == true || $("#tallyConfig").val() == 'true' ){
		if($("#tallyCompanyId").val() == "" || $("#tallyCompanyId").val() == undefined || Number($('#tallyCompanyId').val()) <= 0){
			$("#tallyCompanyId").select2('focus');
			showMessage('info','Please Select Tally Company !');
			return false;
		}
		if($("#tallyExpenseId").val() == "" || $("#tallyExpenseId").val() == undefined || Number($('#tallyExpenseId').val()) <= 0){
			$("#tallyExpenseId").select2('focus');
			showMessage('info','Please Select Tally Expense Head !');
			return false;
		}
	}else{
		$("#tallyCompanyId").val(0);
	}
	
	
	if(Number($('#vehicle_Odometer').val()) < 0 || $('#vehicle_Odometer').val() == ""){
		$("#vehicle_Odometer").focus();
		showMessage('errors', 'Please Enter Vehicle Odometer !');
		return false;
	}
	
	if(Number($('#selectVendor').val()) <= 0 && ($('#enterVendorName').val() == null || $('#enterVendorName').val().trim() == '')){
		$("#selectVendor").select2('focus');
		showMessage('errors', 'Please Select Service Vendor !');
		return false;
	}
	
	if($('#validateInvNumForServEnt').val() == 'true' || $('#validateInvNumForServEnt').val() == true){
		if($('#invoiceNumber').val() <= 0){					
			showMessage('errors', 'Please Enter Invoice Number!');
			hideLayer();
			return false;
		}
	}
	
	if($('#validateInvDateForServEnt').val() == 'true' || $('#validateInvDateForServEnt').val() == true){
		if($('#invoicestartDate').val() <= 0){					
			showMessage('errors', 'Please Select Invoice Date!');
			hideLayer();
			return false;
		}
	}
	
	if($('#validatePaidDateForServEnt').val() == 'true' || $('#validatePaidDateForServEnt').val() == true){
			if($('#servicepaiddate').val() <= 0){					
				showMessage('errors', 'Please Select Paid Date!');
				hideLayer();
				return false;
			}
	}
	
	if(($('#validatePaidDateForServEntOnCash').val() == 'true' || $('#validatePaidDateForServEntOnCash').val() == true)  && ($('#renPT_option').val() == 1 || $('#renPT_option').val() == '1')){
		if($('#servicepaiddate').val() <= 0){					
			showMessage('errors', 'Please Select Paid Date!');
			hideLayer();
			return false;
		}
	}
	
	
	$('input[name*=job_typetaskId]').each(function(){
	var jobType = Number($("#"+$( this ).attr( "id" )).val());
	if(jobType == undefined || jobType == 0){
		noJobType	 = false;
		showMessage('errors', 'Please select JobType !');
		return false;
	}
	});

	var count = Number($('#count').val());
	if(count == 1){
		var to = Number($('#to').val());
		var SubReType = $('#SubReType').val();
		if((to == undefined || to == 0) && (SubReType == undefined || SubReType.trim() == '' || SubReType == '0' ) ){
			$("#to").select2('focus');
			showMessage('errors', 'Please enter Job Sub Type !');
			return false;
		}
	}else{
		for(var i = 2; i <= count; i++ ){

			var to1 = Number($('#to').val());
			var to = Number($('#to'+i).val());
			var SubReType1 = $('#SubReType').val();
			var SubReType = $('#SubReType'+i).val();
			if((to == undefined || to == 0) && (SubReType == undefined || SubReType.trim() == '' || SubReType == '0' ) ){
				showMessage('errors', 'Please enter Job Sub Type !');
				return false;
			}
			else if((to1 == undefined || to1 == 0) && (SubReType1 == undefined || SubReType1.trim() == '' || SubReType1 == '0' ) ){
				showMessage('errors', 'Please enter Job Sub Type !');
				return false;
			}
		
		}
	}
	
	if(noJobType){
		return false;
	}
	
	return true;
}


function createServiceEntry(){
	$('#saveSe').hide();
	 
	if(!validateServiceEntry()){
		$('#saveSe').show();
		return false;
	}
	
	if($('#issueId').val() == 0 && $('#issue').val() == "" && ($("#showPendingIssueWhileCreatingSE").val() == true || $("#showPendingIssueWhileCreatingSE").val() == 'true')){
		if($("#validateIssue").val() == true || $("#validateIssue").val() == 'true' ){
			var result = confirm('There Are Some Issues Created On The Vehicle, Do You Want To Continue Without Selecting Issue ');
			if(!result){
				$('#saveSe').show();
				$('#issue').focus();
				return false;
			}
		}
	}
	
	var jsonObject							= new Object();
	
	jsonObject["vid"] 	  					=  $('#vehicle_vid').val();
	jsonObject["driverId"] 					=  $('#SelectDriverName').val();
	if($('#fromSR').val() == true ||  $('#fromSR').val() == 'true'){
		jsonObject["serviceReminderId"] 	  	=  $('#serviceReminderId').val();		
	}else{
		jsonObject["serviceReminderId"] 	  	=  $('#ServiceReminder').val();		
	}
	jsonObject["vehicleOdometerId"] 	  	=  $('#vehicle_Odometer').val();    
	jsonObject["gpsOdometerId"] 	  		=  $('#gpsOdometer').val();			
	jsonObject["vendorId"] 	  				=  $('#selectVendor').val(); 		
	jsonObject["enterVendorName"] 	  		=  $('#enterVendorName').val();     
	jsonObject["enterVendorLocation"] 	  	=  $('#enterVendorLocation').val();     
	jsonObject["invoiceNumber"] 	  		=  $('#invoiceNumber').val().trim();
	jsonObject["invoiceDate"] 	  			=  $('#invoicestartDate').val();    	
	jsonObject["tripSheetId"] 	  			=  $('#tripSheetId').val();			
	jsonObject["jobNumber"] 	  			=  $('#workorders_location').val(); 
	jsonObject["modeOfPaymentId"]	  		=  $('#renPT_option').val();		
	jsonObject["payNumber"]	  				=  $('#payNumber').val();				
	jsonObject["paidDate"]	  				=  $('#servicepaiddate').val();	    	
	jsonObject["service_paidbyId"]	  		=  $('#service_paidbyId').val();
	jsonObject["tallyCompanyId"]	  		=  $('#tallyCompanyId').val();
	jsonObject["tallyExpenseId"]	  		=  $('#tallyExpenseId').val();
	jsonObject["workshopInvoiceAmountId"]	=  $('#workshopInvoiceAmountId').val();
	jsonObject["serviceEntryId"] 	  	=  $('#serviceEntryId').val();
	if($('#issueId').val() > 0 ){
		jsonObject["issueId"]				=  $('#issueId').val(); // from issue module 
	}else{
		jsonObject["issueId"]				=  $('#issue').val();// from SE module 
	}
	jsonObject["unique-one-time-token"]		=  $('#accessToken').val();
	jsonObject["validateDoublePost"]		=  true;
	jsonObject["fromSR"]					=  $('#fromSR').val();
	jsonObject["accidentId"]				=  $('#accidentId').val();
	
	
	var jobType				 = new Array();  
	var serviceSubJobs		 = new Array();  
	var remark				 = new Array();  
	var service_ROT			 = new Array(); 
	var service_ROT_number	 = new Array(); 
	
	
	$("input[name=service_typetask]").each(function(){
		if($(this).val() != null && $(this).val() != '' && $(this).val().length > 0){
			jobType.push($(this).val());
		}
	});
	
	$("input[name=service_subtypetask]").each(function(){
		if($(this).val() != null && $(this).val() != '' && $(this).val().length > 0){
			serviceSubJobs.push($(this).val());
		}
	});
	
	$("textarea[name=taskRemark]").each(function(){
		if($(this).val() != null && $(this).val() != '' && $(this).val().length > 0){
			remark.push($(this).val());
		}
	});
	
	
	$("input[name=service_ROT]").each(function(){
		if($(this).val() != null && $(this).val() != '' && $(this).val().length > 0){
			service_ROT.push($(this).val());
		}
	});
	
	$("input[name=service_ROT_number]").each(function(){
		if($(this).val() != null && $(this).val() != '' && $(this).val().length > 0){
			service_ROT_number.push($(this).val());
		}
	});
	
	
	var array	 = new Array();
	for(var i =0 ; i< jobType.length; i++){
		var serviceEntriesDetails	= new Object();
		serviceEntriesDetails.jobType				= jobType[i];
		serviceEntriesDetails.serviceSubJobs		= serviceSubJobs[i];
		serviceEntriesDetails.remark				= remark[i];
		serviceEntriesDetails.service_ROT		    = service_ROT[i];
		serviceEntriesDetails.service_ROT_number	= service_ROT_number[i];
		
		array.push(serviceEntriesDetails);
	}
	
	
	jsonObject.serviceEntriesTaskDetails = JSON.stringify(array);
    showLayer();	
	$.ajax({
		url: "saveServiceEntriesDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			if(data.duplicateInvoiceNumber != undefined && data.duplicateInvoiceNumber){
				showMessage('info', 'Duplicate Invoice Number Found');
				$('#saveSe').show();
				hideLayer();
			}
			if(data.validateDuplicateSE != undefined && data.validateDuplicateSE){
				showMessage('info', 'Duplicate Details Entered ');
				$('#saveSe').show();
				hideLayer();
			}
			if(data.hasError != undefined && data.hasError){
				showMessage('errors', 'Some error occured!');
				$('#accessToken').val(data.accessToken);
				$('#saveSe').show();
				hideLayer();
			}
			
			if(data.sequenceNotFound != undefined && data.sequenceNotFound == true){
				showMessage('errors', 'Sequence Counter Missing. Please call System Addministration');
				$('#saveSe').show();
				hideLayer();
			}
			
			if(data.serviceEntryCreated != undefined && data.serviceEntryCreated == true){
				window.location.replace("showServiceEntryDetails.in?serviceEntryId="+data.serviceEntryId+"");
			}
			
			if(data.inSold != undefined && data.inSold == true){
				hideLayer();
				showMessage('errors', 'Vehicle is in Sold Status Hence Cannot Create Service Entry !')
			}
			
			/*if(data.inWorkShop != undefined && data.inWorkShop == true){
				hideLayer();
				showMessage('errors', 'Vehicle is in WorkShop Status Hence Cannot Create Service Entry !')
			}
			*/
			if(data.inActive != undefined && data.inActive == true){
				hideLayer();
				showMessage('errors', 'Vehicle is in InActive Status Hence Cannot Create Service Entry !')
			}
			
			if(data.inSurrender != undefined && data.inSurrender == true){
				hideLayer();
				showMessage('errors', 'Vehicle is in Surrender Status Hence Cannot Create Service Entry !')
			}
			
		},
		error: function (e) {
			//alert("failure")
			showMessage('errors', 'Some error occured!')
			hideLayer();
		}
	});
	
}

	
function getVehicleTripSheetDetails(){
	if($('#showTripSheet').val() == 'true' || $('#showTripSheet').val() == true){
		var jsonObject			= new Object();
		
		if($('#vehicle_vid').val() == undefined || Number($('#vehicle_vid').val()) <= 0){
			showMessage('info', 'Please select vehicle first !');
			return false;
		}
		if($('#invoicestartDate').val() == undefined || $('#invoicestartDate').val() == null){
			showMessage('info', 'Please select invoice date !');
			return false;
		}
		
		jsonObject["vid"] 	 		 		=  $('#vehicle_vid').val();
		jsonObject["invoicestartDate"] 	 	=  $('#invoicestartDate').val();
		//
		showLayer();
		$.ajax({
			url: "getVehicleTripSheetDetails",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				if(data.tripSheet != undefined && data.tripSheet.length > 0){
					showMessage('success', 'TripSheet Found !');
					 var options = "<option value='0'>Select TripSheet</option>";
					for(var i =0; i < data.tripSheet.length; i++){
						options += '<option value="'+data.tripSheet[i].tripSheetID+'"> TS- '+data.tripSheet[i].tripSheetNumber+' :'+data.tripSheet[i].tripOpenDate+' to '+data.tripSheet[i].closetripDate+'</option>';
					}
					$("#tripSheetId").html(options);
				}
				hideLayer();
			},
			error: function (e) {
				showMessage('errors', 'Some error occured!')
				hideLayer();
			}
		});
	}
	
}


//***************** Old Code below 

function visibility(a, b) {
    var c = document.getElementById(a),
        d = document.getElementById(b);
    var sub= c.attributes.id.nodeValue;
    if(sub == "JoBEnter"){
     setValue(SubReType,SubReTypeNum,to);
    }
    "block" == c.style.display ? (c.style.display = "none", d.style.display = "block") : (c.style.display = "block", d.style.display = "none")
}

function showoption() {
    var a = $("#renPT_option :selected"),
        b = a.text();
    "CASH" == b ? $("#target1").text(b + " Receipt NO : ") : $("#target1").text(b + " Transaction NO : ")
}

function toggle2(a, b) {
    var c = document.getElementById(a),
        d = document.getElementById(b);
    "block" == c.style.display ? (c.style.display = "none", d.innerHTML = "Add Part") : (c.style.display = "block", d.innerHTML = "Cancel Part")
}

function toggle2Labor(a, b) {
    var c = document.getElementById(a),
        d = document.getElementById(b);
    "block" == c.style.display ? (c.style.display = "none", d.innerHTML = "Add Labour") : (c.style.display = "block", d.innerHTML = "Cancel Labour")
}

function toggle2Task(a, b) {
    var c = document.getElementById(a),
        d = document.getElementById(b);
    "block" == c.style.display ? (c.style.display = "none", d.innerHTML = "Add Task") : (c.style.display = "block", d.innerHTML = "Cancel Task")
}

function isNumberKeyEach(a, b) {
    var c = a.which ? a.which : event.keyCode;
    if (c > 31 && (48 > c || c > 57) && 46 != c && 8 != charcode) return !1;
    var d = $(b).val().length,
        e = $(b).val().indexOf(".");
    if (e > 0 && 46 == c) return !1;
    if (e > 0) {
        var f = d + 1 - e;
        if (f > 3) return !1
    }
    return !0
}

function isNumberKeyDis(a, b) {
    var c = a.which ? a.which : event.keyCode;
    if (c > 31 && (48 > c || c > 57) && 46 != c && 8 != charcode) return !1;
    var d = $(b).val().length,
        e = $(b).val().indexOf(".");
    if (e > 0 && 46 == c) return !1;
    if (e > 0) {
        var f = d + 1 - e;
        if (f > 3) return !1
    }
    return !0
}

function isNumberKeyTax(a, b) {
    var c = a.which ? a.which : event.keyCode;
    if (c > 31 && (48 > c || c > 57) && 46 != c && 8 != charcode) return !1;
    var d = $(b).val().length,
        e = $(b).val().indexOf(".");
    if (e > 0 && 46 == c) return !1;
    if (e > 0) {
        var f = d + 1 - e;
        if (f > 3) return !1
    }
    return !0
}

function getlastdetails(a, b, c, d) {
    
	$.getJSON("getlastServiceEntries.in", {
        jobtask: a,
        jobsubtask: b,
        vehicle_id: c,
        serviceEntries_id: document.getElementById("serviceEntries_id").value,
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
        var j = document.getElementById("serviceEntries_id").value;
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

function getlastdetailswithpart(a, b, c, d,j) {
	
	 $.getJSON("getMasterPartShow.in", {
            PartID: $('#inventory_name'+j).val(),
            ajax: "true"
        }, function(e) {
           
            $('#parteachcost' + j).val(e.unitCost);
            $('#partdis' + j).val(e.discount);
            $('#parttax' + j).val(e.tax);
        })
	
    
	$.getJSON("getlastServiceEntries.in", {
        jobtask: a,
        jobsubtask: b,
        vehicle_id: c,
        serviceEntries_id: document.getElementById("serviceEntries_id").value,
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
        var j = document.getElementById("serviceEntries_id").value;
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

function toggle2Tax(a, b) {
    var c = document.getElementById(a),
        d = document.getElementById(b);
    "block" == c.style.display ? (c.style.display = "none", d.innerHTML = '<i class="fa fa-inr"></i>') : (c.style.display = "block", d.innerHTML = "Cancel")
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

function isNumberKeyEach(a) {
    var b = 0 == a.keyCode ? a.charCode : a.keyCode,
        c = 46 == b || b >= 48 && 57 >= b || -1 != specialKeys.indexOf(a.keyCode) && a.charCode != a.keyCode;
    return document.getElementById("errorINEACH").innerHTML = "Alphabetical Characters not allowed", document.getElementById("errorINEACH").style.display = c ? "none" : "inline", c
}

function isNumberKeyDis(a) {
    var b = 0 == a.keyCode ? a.charCode : a.keyCode,
        c = 46 == b || b >= 48 && 57 >= b || -1 != specialKeys.indexOf(a.keyCode) && a.charCode != a.keyCode;
    return document.getElementById("errorINEACH").innerHTML = "Alphabetical Characters not allowed", document.getElementById("errorINEACH").style.display = c ? "none" : "inline", c
}

function isNumberKeyTax(a) {
    var b = 0 == a.keyCode ? a.charCode : a.keyCode,
        c = 46 == b || b >= 48 && 57 >= b || -1 != specialKeys.indexOf(a.keyCode) && a.charCode != a.keyCode;
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

function sumthereLaber(a, b, c) {
    var d = document.getElementById(a).value,
        e = document.getElementById(b).value,
        f = parseFloat(d) * parseFloat(e);
    isNaN(f) || (document.getElementById(c).value = f.toFixed(2))
}

function sumthereLaber(a, b, c, d, e) {
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
function setAccidentDetails(detailsDto){
	if(!jQuery.isEmptyObject(detailsDto)){
		var accidentDetailsDto = JSON.parse(detailsDto);
		if(accidentDetailsDto != undefined){
			$('#accidentId').val(accidentDetailsDto.accidentDetailsId);
			$('#vehicle_vid').select2('data', {
	    		id : accidentDetailsDto.vid,
	    		text : accidentDetailsDto.vehicleNumber
	    	});
			$('#vehicle_vid').select2('readonly', true);
			
			$('#SelectDriverName').select2('data', {
	    		id : accidentDetailsDto.driverId,
	    		text : accidentDetailsDto.driverName
	    	});
			$('#SelectDriverName').select2('readonly', true);
			$('#vehicle_Odometer').val(accidentDetailsDto.vehicleOdometer);
		}
	}
}

function setValue(SubReType,SubReTypeNum,to){
	
	$(SubReType).val("");
	$(SubReTypeNum).val("");
	$(to).val([]).change();
	$(to).val("0");
}
function setBackValue(SubReType,SubReTypeNum){
	if($(SubReType).val() == '' && $(SubReTypeNum).val() == ''){
		$(SubReType).val("0");
		$(SubReTypeNum).val("0");
	}
}

function getIssueDetails(vid){
	var issueId = Number($('#issueId').val()) // this issue id come from issue module  and "$("#issue")" this is from work order module
	console.log("dsfsfs",issueId)
	$("#issue").val('');
	if(issueId == 0){
	
		$("#issueDiv").removeClass('hide');
		$("#issue").select2({
			ajax : {
				url:"getVehicleWiseIssueDropdown.in?Action=FuncionarioSelect2", 
				dataType:"json", 
				type:"GET", 
				contentType:"application/json", 
				data:function() {
					console.log("vid",vid)
					return {
						term: vid
					}
				}
		, results:function(t) {
			return {
				results:$.map(t, function(t) {
					return {
						
						text: "I-"+t.ISSUES_NUMBER+"-"+t.ISSUES_SUMMARY, slug: t.slug, id: t.ISSUES_ID
					}
				}
				)
			}
		}
			}
		
		});
		
		
		checkIssueDetail()
		
	}
	
}

function checkIssueDetail(){
	var jsonObject				= new Object();
	jsonObject["vid"] 	 		=  $("#vehicle_vid").val();
	jsonObject["companyId"] 	=  $('#companyId').val();
	var validationFlag			= true;
	var issueDetails;
	$.ajax({
		url: "getVehicleWiseIssueDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.issueDetails != undefined){
				$("#validateIssue").val(true);
			}else{
				$("#validateIssue").val(false);
			}
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			hideLayer();
		}
	});
	
	
	
}

function getVehicleStatusByVehicleNumber(vehicleStr){

	var jsonObject						= new Object();
	jsonObject["vehicleNumber"] 	= vehicleStr;

	var validationFlag			= true;
	var issueDetails;
	$.ajax({
		url: "getIntangleByVehicleNumber",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.healthStatus != undefined && data.healthStatus != null){
				
				$("#healthStatusId").removeClass('hide');
				$("#healthStatusName").removeClass('hide');

				$("#healthStatusId").html("Health Status : ");

				if(data.healthStatus.toUpperCase() == "GOOD"){
					$("#healthStatusName").html( '<a class="label  label-success" >'+data.healthStatus+'</a>');
				}else if(data.healthStatus.toUpperCase() == "MINOR"){
					$("#healthStatusName").html( '<a class="label  label-warning" >'+data.healthStatus+'</a>');
				}else if(data.healthStatus.toUpperCase() == "MAJOR"){
					$("#healthStatusName").html( '<a class="label  label-danger" >'+data.healthStatus+'</a>');
				}

			}else{
				$("#healthStatusId").addClass('hide');
				$("#healthStatusName").addClass('hide');
			}
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			hideLayer();
		}
	});

}
