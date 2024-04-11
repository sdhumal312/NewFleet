var fromServiceReminder = false;
var SRArray = [];
var ids ="";
$(document).ready(function() {
	if($('#showCurrentDateAtWOStartDate').val() == 'true' || $('#showCurrentDateAtWOStartDate').val() == true){
		$('#woStartDate').val(formatOnlyDate(new Date()));
	}
	$('#issueAssignee').hide()

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
                            text: a.driver_empnumber + " - " + a.driver_firstname+" "+a.driver_Lastname+" - "+a.driver_fathername + " - " + a.driver_mobnumber,
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
    }),$("#reason").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getReasonTypeWorkOrder.in",
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
                            text: a.Reason_Type,
                            slug: a.slug,
                            id: a.Reason_id
                        }
                    })
                }
            }
        }
    }),$("#to").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        multiple:!0,
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
    }), $("#subLocationId").select2({
        minimumInputLength: 0,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getPartLocationsByMainLocationId.in?Action=FuncionarioSelect2",
            dataType: "json",
            type: "POST",
            contentType: "application/json",
            quietMillis: 50,
            data: function(a) {
                return {
                    term: a,
                    locationType: subPartLocationType,
                    mainLocationId:  $('#location').val()
                }
            },
            results: function(a) {
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
    }),$("#workLocationId").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getAllSubLocations.in?Action=FuncionarisoSelect2",
            dataType: "json",
            type: "POST",
            contentType: "application/json",
            quietMillis: 50,
            data: function(a) {
                return {
                    term: a,
                }
            },
            results: function(a) {
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
    });
    
}), 

$(document).ready(function() {
	
	 if($("#issueVid").val() > 0 && $("#issueId").val() > 0){
			$("#WOVehicle").hide();
			$("#issueVehicle").show();
			$('#select3').val($("#issueVid").val());
			
			
			vehicleOnChange1();
			vehicleOnChange2();
			vehicleOnChange3();
			
			getAssignToFromIssue();
			if($("#showPendingIssueWhileCreatingWO").val() == true || $("#showPendingIssueWhileCreatingWO").val() == 'true'){
					checkIssueDetail();
					$("#issueDiv").show();
			}else{
				$("#issueDiv").hide();
			}
			
	 }
	
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

function getJob_subtypetask(a) {
    $.getJSON("getJobSubTypeList.in", {
        ajax: "true"
    }, function(b) {
        for (var c = "", d = b.length, e = 0; d > e; e++) c += '<option value="' + b[e].Job_Type + "-" + b[e].Job_SubType + '">' + b[e].Job_Type + "-" + b[e].Job_SubType + "</option>";
        c += "</option>", $("#" + a).html(c)
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
}), 

$("#select3").on("change", function() {

	var showVehicleHealthStatus =  $("#showVehicleHealthStatus").val();
	if(showVehicleHealthStatus == true || showVehicleHealthStatus == 'true'){
		if($('#select3').select2('data') != null){
			var vehicleNumber =  $('#select3').select2('data').text;
			var vehicleStr = vehicleNumber.replace(/-/g, "");
			getVehicleStatusByVehicleNumber(vehicleStr);
		}else{
			$("#healthStatusId").addClass('hide');
			$("#healthStatusName").addClass('hide');
		}
	}

	vehicleOnChange1();
	vehicleOnChange2();
	vehicleOnChange3();
	if($("#showPendingIssueWhileCreatingWO").val() == true || $("#showPendingIssueWhileCreatingWO").val() == 'true'){
		checkIssueDetail();
		if($(this).val() == '' || $(this).val() == null){
			$('#issue').select2('val','');
			$('#issue').select2().trigger('change');
		}
	}
})

function vehicleOnChange1(){
	
        var a = "",
            b = $("#select3").val();
        $("#vehicle_Meter option").each(function() {
            b == $(this).val() && (a = $(this).text())
        }), document.getElementById("vehicle_Odometer").placeholder = a, document.getElementById("vehicle_Odometer_old").value = a, $("#hidden").hide()
    }

$(document).ready(function() {
    $(".select2").select2(), $("#tagPicker").select2({
        closeOnSelect: !1
    })
}); 

$(document).ready(function() {
    var a = 25,
        b = $(".input_fields_wrap"),
        c = $(".add_field_button"),
        d = 1;
    $(c).click(function(c) {
    	 var count =	Number($('#count').val());
    	 $('#count').val(count + 1);
   		c.preventDefault(), a > d && (d++, $(b).append('<div>    <input type="hidden" id="newJobSubId' +d+'" name="newJobSubId"> <div class="row1" id="programDetails' + d +'">  </div><div class="row1" id="showCategory"><label class = "L-size control-label">Category Type <abbr title="required">*</abbr></label><div class="I-size"  id="CategoryType' + d + '" ><div class="col-md-8" ><select class ="form-text" name="categoryId" id="partCategory' + d +'" style="width: 100%;" required="required" placeholder="select Category"></select></div></div></div>  <div class="row1"><label class="L-size control-label" for="priority">JobType </label><div class="I-size"><div class="col-md-8"> <input type="hidden" id="from' + d + '" name="job_typetaskId" style="width: 100%;" required="required" placeholder="Please Enter 3 or more Job Type Name" /></div></div></div><div class="row1"><label class="L-size control-label">Service Sub Jobs <abbr title="required">*</abbr></label><div class="I-size"  id="JoBSelect' + d + '" ><div class="col-md-8" ><input type="hidden"  name="job_subtypetask"  id="to' + d + '" style="width: 100%;" value="0" required="required"placeholder="Please Enter 3 or more Job Sub Type Name"/></div><div class="col-md-1"><a class=" btn btn-link " onclick="visibility( \'JoBEnter' + d + "', 'JoBSelect" + d + '\');valueSet(SubReTypeROTName'+ d +',SubReTypeRotNum'+d+' ,to' + d + ');"> <i class="fa fa-plus"> New</i></a></div></div><div id="JoBEnter' + d + '" class="contact_Hide"><div class="I-size"><div class="col-md-8"><input type="text" class="form-text" id="SubReTypeROTName'+ d +'" \tname="Job_ROT" maxlength="150" placeholder="Enter ROT Name" value="0" onpaste="return false" onkeypress="return /[a-z]/i.test(event.key)"; /><br> <input type="text" class="form-text" \tid="SubReTypeRotNum'+d+'" name="Job_ROT_number" value="0" maxlength="30" \tplaceholder="Enter ROT Number"  onpaste="return false" onkeypress="return isNumberKey(event)" /></div><div class="col-md-1"><a class=" btn btn-link col-sm-offset-1" \tonclick="visibility( \'JoBEnter' + d + "', 'JoBSelect" + d + '\');setValue(SubReTypeROTName'+ d +',SubReTypeRotNum'+d+')"> <i\tclass="fa fa-minus"> Select</i></a></div></div></div></div><div class="jobTypeRemarkCol row1 form-group"><label class="L-size control-label" for="to">Job Type Remark<abbr title="required">*</abbr></label><div class="I-size"><div class="col-md-8"><textarea class="form-text" id="JobTypeRemark'+ d +'" name="JobTypeRemark" maxlength="150" placeholder="Enter Remark"></textarea></div></div></div><div class="row1" id="showReasonType"><label class="L-size control-label" for="priority"> Reason Type</label><div class="I-size"><div class="col-md-8"> <input type="hidden" id="reason' + d + '" name="reasontypeId" style="width: 100%;" required="required" placeholder="Please Enter 3 or more Reason Type Name" /></div></div></div><a href="#" class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font></div>'), $(document).ready(function() {
           
            if($("#showPartCategoriesDropdown").val() == "false" || $("#showPartCategoriesDropdown").val() == false ){
				$("#showCategory").remove();
			}
			
			if($("#showReasonForRepairTypeBox").val() == "false" || $("#showReasonForRepairTypeBox").val() == false ){
				$("#showReasonType").remove();
			}
            
            $('#category'+d).val('0');
				$.getJSON("getPartCategoriesList", function(a) {
					
					var b = '<option value="">Please Select</option>';
				    for (var s = 0; s < a.length; s++) {
				        b += '<option value="' + a[s].pcid + '">' + a[s].pcName + '</option>';
				    }
					 $("#partCategory"+d ).html(b);
                }),
            
            
            
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
            }),
			$("#from" + d).click(function() {
			    $("#jobId").val($("#from" + d).val());
			    validateJobAndSubTypeForMultiple(d);
			});	
			
			$("#to" + d).select2({
                minimumInputLength: 3,
                minimumResultsForSearch: 10,
                multiple : !0,
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
            }),
            $("#to" + d).click(function() {
			    $("#jobsubId").val($("#to" + d).val());
			    validateJobAndSubTypeForMultiple(d);
			});

            $("#reason"+d).select2({
			minimumInputLength: 2,
		        minimumResultsForSearch: 10,
		        ajax: {
		            url: "getReasonTypeWorkOrder.in",
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
		                            text: a.Reason_Type,
		                            slug: a.slug,
		                            id: a.Reason_id
		                        }
		                    })
		                }
		            }
		        }
		    }),
             $("#from" + d).change(function() {
                 $('#to' + d).val('0');
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
                    if( a != null && a != undefined && a.length == 1 ){
                 	   $("#to" + d).select2('data',{
                 		   id : a[0].Job_Subid,
                 		   text : a[0].Job_ROT + "-" + a[0].Job_ROT_number
                 	   })
                 	   $("#jobsubId").val(a[0].Job_Subid);
                    }
                    $('#programDetails'+d).empty();
                    validateJobAndSubTypeForSR($("#from"+d).val(),$("#to"+d).val(), d);
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
            }),
            
            $("#JoBEnter" + d).hide()
        }))
        
        if($("#showJobTypeRemarkCol").val() == false || $("#showJobTypeRemarkCol").val() == 'false') {
        	$(".jobTypeRemarkCol").addClass("hide");        	
        }
    }), 
    	$(b).on("click", ".remove_field", function(a) {
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
}); 



$(document).ready(function() {
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
        for (var b = '<option value="">Please Select</option>', c = a.length, d = 0; c > d; d++) b += '<option value="' + a[d].Job_Type + '">' + a[d].Job_Type + "</option>";
        b += "</option>", $("#jobType").html(b)
    })
}), 


$(document).ready(function() {
    $("#from").change(function() {
   	 $('#to').val('0');
        $.getJSON("getJobSubTypeChangeWorkOrder.in", {
            JobType: $(this).val(),
            ajax: "true"
            	
        }, function(a) {
            for (var b = a.length - 1, c = "", d = a.length, e = 0; d > e; e++) b != e ? (c += '{"id":"' + a[e].Job_Subid + '","text":"' + a[e].Job_ROT + "-" + a[e].Job_ROT_number + '" }', c += ",") : c += '{"id":"' + a[e].Job_Subid + '","text":"' + a[e].Job_ROT + "-" + a[e].Job_ROT_number + '" }';
            var f = "[" + c + "]",
                g = JSON.parse(f);
            $("#to").select2({
                allowClear: !0,
                multiple:!0,
                data: g
            });
            if( a != null && a != undefined && a.length == 1 ){
          	   $("#to").select2('data',{
          		   id : a[0].Job_Subid,
          		   text : a[0].Job_ROT + "-" + a[0].Job_ROT_number
          	   })
             }
             $('#programDetails').empty();
             validateJobAndSubTypeForSR($("#from").val(),$("#to").val(),"");
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
}), 

$(document).ready(function() {
    $("#JoBEnter").hide()
    $("#workLocationNew").hide()
}), 

$(document).ready(function() {
    $("#subscribe").select2({
        minimumInputLength: 0,
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


function validateWorkOrderDetails(){
	
	if(Number($('#select3').val()) <= 0){
		$("#select3").select2('focus');
		showMessage('errors', 'Please Select Vehicle !');
		return false;
	}
	if($('#assigneeIndetifier').val() == 'true'){
		if(Number($('#subscribe').val()) <= 0){
			$("#subscribe").select2('focus');
			showMessage('errors', 'Please Select Assigned To !');
			return false;
		}
	}else{
		if(Number($('#subscribe1').val()) <= 0){
			$("#subscribe1").select2('focus');
			showMessage('errors', 'Please Select Assigned To !');
			return false;
		}	
	}
	
	if($("#woStartDate").val() == "" || $("#woStartDate").val() == undefined ){
		$("#woStartDate").focus();
		showMessage('errors','Please Select Start Date');
		return false;
	}
	
	if($("#startTimeForGroup").val() == true){
		if($("#woStartTime").val() == "" || $("#woStartTime").val() == undefined ){
			$("#woStartTime").focus();
			showMessage('errors','Please Select Start Time !');
			return false;
		}
	}
	
	if($("#woEndDate").val() == "" || $("#woEndDate").val() == undefined ){
		$("#woEndDate").focus();
		showMessage('errors','Please Select Due Date');
		return false;
	}
	
	if($("#startTimeForGroup").val() == true){
		if($("#woEndTime").val() == "" || $("#woEndTime").val() == undefined ){
			$("#woEndTime").focus();
			showMessage('errors','Please Select Due Time !');
			return false;
		}
	}
	
	if(Number($('#location').val()) <= 0 && $("#new_workorders_location").val() == ""){
		$("#location").select2('focus');
		showMessage('errors', 'Please Select Work Location !');
		return false;
	}
	
	var validateSubLocation = false;
	if($("#showSubLocation").val() == true || $("#showSubLocation").val() == "true"){
		var mainLocationIds = $("#mainLocationIds").val().split(',');
    	for(var i = 0; i < mainLocationIds.length; i++) {
    		if($("#location").val() == mainLocationIds[i]){
    			validateSubLocation = true
    		}
    	}
    	if((validateSubLocation == true ) && ($('#subLocationId').val() == undefined || $('#subLocationId').val() == "" )){
    		showMessage('info','Please Select Sub Location');
    		return false;
    	}
		
	 }
	
	if($("#vehicle_Odometer").val() == "" || $("#vehicle_Odometer").val() == undefined ){
		$("#vehicle_Odometer").focus();
		showMessage('errors','Please Enter Odometer');
		return false;
	}
	
	if($("#tallyConfig").val() == true || $("#tallyConfig").val() == 'true' ){
		if($("#tallyCompanyId").val() == "" || $("#tallyCompanyId").val() == undefined ){
			showMessage('errors','Please Select Tally Company Master');
			return false;
		}
	}else{
		$("#tallyCompanyId").val(0);
	}
	
	return true;
}


function validateOdometer(){ 
	
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
	
	if($('#ServiceReminder').val() != undefined && $('#ServiceReminder').val().trim() != ''){
		return true;
	}
	
	if(($('#showPendingIssueWhileCreatingWO').val() == 'true' || $('#showPendingIssueWhileCreatingWO').val() == true) && $('#issue').val() != null && $('#issue').val() != 'null'){
		return true;
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
	//return false;
	
	if(count == 1){
		var to = Number($('#to').val());
		var SubReType = $('#SubReTypeROTName').val();
		 var SubReTypeRotNum =$('#SubReTypeRotNum').val();
		if((to == undefined || to == 0 ) && (SubReType == undefined || SubReType.trim() == '' || SubReType == '0' ) ){
			showMessage('errors', 'Please enter Job Sub Type !');
			return false;
		}
//		if((to == undefined || to == 0) && (SubReTypeRotNum == undefined || SubReTypeRotNum.trim() == '' || SubReTypeRotNum == '0') ){
//			showMessage('errors', 'Please enter Job Sub Type !');
//			return false;
//		}
	}else{
		for(var i = 2; i <= count; i++ ){
			
			var to1 = Number($('#to').val());
			var to = Number($('#to'+i).val()); 						
			var SubReType1 = $('#SubReTypeROTName').val(); 
			//var SubReTypeRotNum1 =$('#SubReTypeRotNum').val();
			var SubReType = $('#SubReTypeROTName'+i).val();  
			//var SubReTypeRotNum =$('#SubReTypeRotNum'+i).val();
				if((to == undefined || to == 0) && (SubReType == undefined || SubReType.trim() == '' || SubReType == '0' )){  
				showMessage('errors', 'Please enter Job Sub Type !');
				return false;
			}else if((to1 == undefined || to1 == 0) && (SubReType1 == undefined || SubReType1.trim() == '' || SubReType1 == '0' )){ 
				showMessage('errors', 'Please enter Job Sub Type !');
				return false;
			}
//			else if((to == undefined || to == 0) && (SubReTypeRotNum == undefined || SubReTypeRotNum.trim() == '' || SubReTypeRotNum == '0' )){  
//				showMessage('errors', 'Please enter Job Sub Type !');
//				return false;
//			}
//			else if((to1 == undefined || to1 == 0) && (SubReTypeRotNum1 == undefined || SubReTypeRotNum1.trim() == '' || SubReTypeRotNum1 == '0' )){
//				showMessage('errors', 'Please enter Job Sub Type !');
//				return false;
//			}
			
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

function validateWOSave(){

	if(!validateWorkOrderDetails()){
		return false;
	}
	
	if(!validateOdometer()){
		return false;
	}
	
	if(!validateJobType()){
		return false;
	}
	
	if(!validateDate()){
		return false;
	}
	
	if(!validateIssueDetailsOrServiceProgram()){
		return false;
	}
	
 	return true;
}

function validateIssueDetailsOrServiceProgram() {
	if($("#validatIssueDetailsOrServiceProgramm").val() == true || $("#validatIssueDetailsOrServiceProgramm").val() == 'true'  ){
	
	if($('#issue').val() == null && $("#ServiceReminder").val() == "") {
		showMessage('info', 'Please, enter either Issue Details or Service Program !');
		return false;
	}
	}
	return true;
}

function createWorkOrder(){
	
	$('#saveWO').hide();
	
	if(!validateWOSave()){
		$('#saveWO').show();
		return false;
	}
	
	if($('#issueId').val() == 0 &&($('#issue').val() == null || $('#issue').val() == "") &&($("#showPendingIssueWhileCreatingWO").val() == true || $("#showPendingIssueWhileCreatingWO").val() == 'true')){
		if($("#validateIssue").val() == true || $("#validateIssue").val() == 'true'){
			var result = confirm('There Are Some Issues Created On The Vehicle, Do You Want To Continue Without Selecting Issue ');
			if(!result){
				$('#saveWO').show();
				$('#issue').focus();
				return false;
			}
		}
	} 
	
	var jsonObject							= new Object();
	jsonObject["validateDoublePost"] 	 =  true;
	jsonObject["unique-one-time-token"]  =  $('#accessToken').val();
	jsonObject["vid"] 						=  $('#select3').val(); 	
	jsonObject["driverId"] 	  				=  $('#SelectDriverName').val();
	jsonObject["serviceReminderId"] 	  	=  $('#ServiceReminder').val();
	jsonObject["driverNumber"] 	  			=  $('#driverNumber').val();
	if($('#assigneeIndetifier').val()=='true'){
		jsonObject["assignedTo"] 	  			=  $('#subscribe').val();
	}else{
		jsonObject["assignedTo"] 	  			=  $('#subscribe1').val();
	}
	
	jsonObject["woStartDate"] 	  			=  $('#woStartDate').val();
	jsonObject["woStartTime"] 	  			=  $('#woStartTime').val();
	jsonObject["woEndDate"] 	  			=  $('#woEndDate').val();
	jsonObject["woEndTime"] 	  			=  $('#woEndTime').val();
	jsonObject["outWorkStation"] 	  		=  $('#outWorkStation').val();
	jsonObject["gpsWorkLocation"] 	  		=  $('#gpsWorkLocation').val();
	jsonObject["location"] 	  				=  $('#location').val();
	jsonObject["new_workorders_location"] 	=  $('#new_workorders_location').val();
	jsonObject["workorders_route"] 			=  $('#workorders_route').val();
	jsonObject["vehicle_Odometer_Old"] 		=  $('#vehicle_Odometer_old').val();
	jsonObject["vehicle_Odometer"] 			=  $('#vehicle_Odometer').val();
	jsonObject["gpsOdometer"] 				=  $('#gpsOdometer').val();
	jsonObject["workorders_diesel"] 		=  $('#workorders_diesel').val();
	jsonObject["indentno"] 					=  $('#indentno').val();
	jsonObject["priority"] 					=  $('#priority').val();
	jsonObject["initial_note"] 				=  $('#initial_note').val();
	jsonObject["tallyCompanyId"] 			=  $('#tallyCompanyId').val();
	if($('#issueId').val() > 0 ){
		jsonObject["issueId"]					=  $('#issueId').val(); // from issue module 
	}else{
		var issue = $('#issue').val();
		if(issue != null)
			issue=issue.toString();
		
		jsonObject["issueId"]					=  issue;// from WO module 
	}
	jsonObject["subLocationId"]				=  $('#subLocationId').val();
	jsonObject["accidentId"]				=  $('#accidentId').val();
	jsonObject["workLocationId"]			=  $('#workLocationId').val();
	
	var jobType				 = new Array();  
	var serviceSubJobs		 = new Array();  
	var remark				 = new Array();  
	var service_ROT			 = new Array(); 
	var service_ROT_number	 = new Array();
	var part_category        = new Array();
	var reason_category      = new Array();
	
	$("select[name=categoryId]").each(function(){
		if($(this).val() !=null && $(this).val() != '' && $(this).val().length > 0){
			part_category.push($(this).val());
		} 
	});
	
	$("input[name=job_typetaskId]").each(function(){
		if($(this).val() != null && $(this).val() != '' && $(this).val().length > 0){
			jobType.push($(this).val());
		}
	});
	
	$("input[name=newJobSubId]").each(function(){
		if($(this).val() != null && $(this).val() != '' && $(this).val().length > 0){
			serviceSubJobs.push($(this).val());
		}
	});
	
	$("textarea[name=JobTypeRemark]").each(function(){
		remark.push($(this).val().trim());
	});
	
	$("input[name=Job_ROT]").each(function(){
		if($(this).val() != null && $(this).val() != '' && $(this).val().length > 0){
			service_ROT.push($(this).val());
		}
	});
	
	$("input[name=Job_ROT_number]").each(function(){
		if($(this).val() != null && $(this).val() != '' && $(this).val().length > 0){
			service_ROT_number.push($(this).val());
		}
	});
	
	$("input[name=reasontypeId]").each(function(){
		if($(this).val() != null && $(this).val() != '' && $(this).val().length > 0){
			reason_category.push($(this).val());
		}
	});
	
	
	if( !fromServiceReminder && $('#showServiceProgramINWO').val() == 'true' || $('#showServiceProgramINWO').val() == true){
		var serviceIds = '';
		$("input[name=selectedSchedule]").each(function(){
			if($('#'+this.id+'').prop('checked')){
				serviceIds += this.id+',';
			}
		});
		jsonObject["serviceReminderId"]	=  serviceIds;
	}

	serviceSubJobs.push(ids);

	var array	 = new Array();
	
	for(var i =0 ; i< jobType.length; i++){

		var workOrderDetails					= new Object();
		workOrderDetails.jobType				= jobType[i];
		workOrderDetails.jobSubType				= serviceSubJobs[i];
		workOrderDetails.remark					= remark[i];
		workOrderDetails.wo_ROT		   			= service_ROT[i];
		workOrderDetails.wo_ROT_number			= service_ROT_number[i];
		workOrderDetails.part_category                 = part_category[i];
		workOrderDetails.reason_category        = reason_category[i];
		
		array.push(workOrderDetails);
	}
	
	if(!confirm('Do you want to create Work Order !')) {
		$('#saveWO').show();
		return;
	}
	
	jsonObject.workOrderDetails = JSON.stringify(array);
	showLayer();
	
	$.ajax({
	url: "saveWorkOrderDetails",
	type: "POST",
	dataType: 'json',
	data: jsonObject,
	success: function (data) {
		
			if(data.emptyTask != undefined && data.emptyTask){
				hideLayer();
				$('#accessToken').val(data.accessToken);
				$('#saveWO').show();
				showMessage('errors', 'Please select atleast one proper Job Type and Sub Type !');
				return false;
			}
			if(data.hasError != undefined && data.hasError){
				hideLayer();
				$('#accessToken').val(data.accessToken);
				showMessage('errors', 'Some Error Occurred!');
				return false;
			}
			
			if(data.saveWorkOrder != undefined && data.saveWorkOrder == true){
				hideLayer();
				window.location.replace("showWorkOrder.in?woId="+data.workOrderId+"");
			}
			
			if(data.inTripRoute != undefined && data.inTripRoute == true){
				hideLayer();
				$('#saveWO').show();
				$('#accessToken').val(data.accessToken);
				showMessage('errors', 'vehicle in Triproute Status Hence Cannot Create WorkOrder !')
			}
			
			if(data.inSold != undefined && data.inSold == true){
				hideLayer();
				$('#saveWO').show();
				$('#accessToken').val(data.accessToken);
				showMessage('errors', 'vehicle in Sold Status Hence Cannot Create WorkOrder !')
			}
			
			if(data.inActive != undefined && data.inActive == true){
				hideLayer();
				$('#saveWO').show();
				$('#accessToken').val(data.accessToken);
				showMessage('errors', 'vehicle in InActive Status Hence Cannot Create WorkOrder !')
			}
			
			if(data.inSurrender != undefined && data.inSurrender == true){
				hideLayer();
				$('#accessToken').val(data.accessToken);
				showMessage('errors', 'vehicle in Surrender Status Hence Cannot Create WorkOrder !')
			}
			if(data.inWorkShop != undefined && data.inWorkShop == true){
				hideLayer();
				$('#accessToken').val(data.accessToken);
				$('#saveWO').show();
				showMessage('errors', 'vehicle in Workshop Status Hence Cannot Create WorkOrder !')
			}
			if(data.inAccident != undefined && data.inAccident == true){
				hideLayer();
				$('#accessToken').val(data.accessToken);
				$('#saveWO').show();
				showMessage('errors', 'vehicle in Accident Status Hence Cannot Create WorkOrder !')
			}
		},
		error: function (e) {
			alert("failure")
			showMessage('errors', 'Some error occured!')
			hideLayer();
		}
	});  
}


function woValidate(){
	if(Number($('#to').val()) <= 0){
		showMessage('errors','Please Select Job Subtype !');
		return false;
	}	
}

$(document).ready(function() {
    var currentDate 			= $('#serverDate').val();
    var previousDate 			= $('#backDateString').val();
    if(previousDate != undefined){
    	var previousDateForBackDate =   previousDate.split("-")[0] + '-' +  previousDate.split("-")[1] + '-' +  previousDate.split("-")[2];
    	var currentDateStr =   currentDate.split("-")[0] + '-' +  currentDate.split("-")[1] + '-' +  currentDate.split("-")[2];
    	$("#StartDate1").datepicker({
    		defaultDate: new Date(),
    		autoclose: !0,
    		todayHighlight: !0,
    		format: "dd-mm-yyyy",
    		setDate: "0",
    		endDate: currentDateStr,
    		startDate:previousDateForBackDate,
    		maxDate: currentDateStr
    	}),
    	$("#LeaveDate1").datepicker({
    		defaultDate: new Date(),
    		autoclose: !0,
    		todayHighlight: !0,
    		format: "dd-mm-yyyy",
    		setDate: "0",
    		startDate:previousDateForBackDate
    	});
    }
});

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

function visibility(a, b) {
    var c = document.getElementById(a), 
        d = document.getElementById(b); 
    var sub =c.attributes.id.nodeValue;
    if(sub == "JoBEnter"){
    	valueSet(SubReTypeROTName, SubReTypeRotNum, to);
    }
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

function setServiceReminderDetails(Service){
	
	if(Service != undefined && Service != null && Service != ''){
		fromServiceReminder = true;
		var serviceReminder = JSON.parse(Service);
		//select3
		$('#select3').select2('data', {
			id : serviceReminder.vid,
			text : serviceReminder.vehicle_registration
		});
		$("#select3").select2("readonly", true);
		
		$('#from').select2('data', {
			id : serviceReminder.serviceTypeId,
			text : serviceReminder.service_type
		});
		$("#from").select2("readonly", true);
		$('#to').select2('data', {
			id : serviceReminder.serviceSubTypeId,
			text : serviceReminder.service_subtype
		});
		$("#to").select2("readonly", true);
		$('#new').hide();
		$("#ServiceReminder").select2("readonly", true);
		
		var text = serviceReminder.serviceProgram + '  '+serviceReminder.service_type+'-'+serviceReminder.service_subtype;
		
		$('#ServiceReminder').select2('data', {
			id : serviceReminder.service_id,
			text : text
		});
		
		vehicleOnChange1();
		vehicleOnChange2();
		validateJobAndSubTypeForSR($("#from").val(),$("#to").val(),"");
	}

}var mainPartLocationType = 1;
var subPartLocationType  = 2;

function showSubLocationDropDown(){
	var showSubLocationForMainLocation = false;
    if($("#showSubLocation").val() == true || $("#showSubLocation").val() == "true"){
    	var mainLocationIds = $("#mainLocationIds").val().split(',');
    	for(var i = 0; i < mainLocationIds.length; i++) {
    		if($("#location").val() == mainLocationIds[i]){
    			showSubLocationForMainLocation = true
    		}
    	}
    }
    
    if(showSubLocationForMainLocation == true){
    	$("#subLocation").show();
    	$("#validateSubLocation").val(true);
    }else{
    	$("#subLocationId").select2("val", "");
    	$("#subLocation").hide();
    	$("#validateSubLocation").val(false);
    }
}

function validateDate(){

	if($("#startTimeForGroup").val() == true || $("#startTimeForGroup").val() == 'true'  )	{
		
		if($('#woEndTime').val() == ""){
			$('#woEndTime').val($('#woStartTime').val())
		}
		var startDateTime    	= $('#woStartDate').val().split("-").reverse().join("-")+' '+$('#woStartTime').val();
		var endDateTime    		= $('#woEndDate').val().split("-").reverse().join("-")+' '+$('#woEndTime').val();
	}else{
		var startDateTime    	= $('#woStartDate').val().split("-").reverse().join("-")+' 00:00:00';
		var endDateTime    		= $('#woEndDate').val().split("-").reverse().join("-")+' 00:00:00';
	}
	
	var isafter = moment(startDateTime).isAfter(endDateTime);
	
	if(isafter){
		$('#woEndTime').val('');
		$('#woEndDate').val('');
		showMessage('info', 'Due Date Can Not Be Greater Than Start Date');
		hideLayer();
		return false;
	}
	return true;
}
function setAccidentDetails(detailsDto){
	if(!jQuery.isEmptyObject(detailsDto)){
		var accidentDetailsDto = JSON.parse(detailsDto);
		if(accidentDetailsDto != undefined){
			$('#accidentId').val(accidentDetailsDto.accidentDetailsId);
			$('#select3').select2('data', {
	    		id : accidentDetailsDto.vid,
	    		text : accidentDetailsDto.vehicleNumber
	    	});
			$('#select3').select2('readonly', true);
			
			$('#SelectDriverName').select2('data', {
	    		id : accidentDetailsDto.driverId,
	    		text : accidentDetailsDto.driverName
	    	});
			$('#SelectDriverName').select2('readonly', true);
			$('#vehicle_Odometer').val(accidentDetailsDto.vehicleOdometer);
			$('#vehicle_Odometer_Old').val(accidentDetailsDto.vehicleOdometer);
			
			vehicleOnChange1();
			vehicleOnChange2();
			vehicleOnChange3();
			
		}
	}
}
function valueSet(rotName ,rotNumber ,to){
	$(rotName).val("");
	$(rotNumber).val("");

	$(to).val([]).change();
	$(to).val('0');
}
function setValue(rotName,rotNumber){
	if($(rotName).val().trim() == '' && $(rotNumber).val() == ''){
	$(rotName).val("0");
	$(rotNumber).val("0");
	}
}

function getIssueDetails(vid){
	var issueId = Number($('#issueId').val()) // this issue id come from issue module  and "$("#issue")" this is from work order module
	$("#issue").val('');
	if(issueId == 0){
	
		$("#issueDiv").show();
		
		$("#issue").select2({
			ajax : {
				url:"getVehicleWiseIssueDropdown.in?Action=FuncionarioSelect2", 
				dataType:"json", 
				type:"GET", 
				contentType:"application/json", 
				data:function() {
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
	jsonObject["vid"] 	 		=  $('#select3').val();
	jsonObject["companyId"] 	=  $('#companyId').val();
	
	if($("#issueVid").val() > 0 && $("#issueId").val() > 0){
		 jsonObject["issueId"] 	 	=  $('#issueId').val();
	 }else{
		 jsonObject["issueId"] 	 	=  $('#issue').val();
	 }
	var validationFlag			= true;
	var issueDetails;
	$.ajax({
		url: "getVehicleWiseIssueDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.issueDetails != undefined){
				var option  = '';
				for(var i = 0; i < data.issueDetails.length; i++){
					option	+= '<option value="'+data.issueDetails[i].issues_ID+'">I-'+data.issueDetails[i].issues_NUMBER+' - '+data.issueDetails[i].issues_SUMMARY+'</option>';
				}
				$('#issue').html(option);
				$("#validateIssue").val(true);
				$('#issue').val(data.issueDetails[0].issues_ID);
				$('#issue').select2().trigger('change');
				
				setIssueData(data);
				
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

function populateEndDateOnIssue(issueTypeId){
	var todayDate = new Date(), weekDate = new Date();
	
	if(issueTypeId == 4){
		weekDate.setTime(todayDate.getTime()+(2*24*3600000));
	}else if(issueTypeId == 5){
		weekDate.setTime(todayDate.getTime()+(1*24*3600000));
	}else{
		weekDate.setTime(todayDate.getTime()+(3*24*3600000));
	}
	
	return formatOnlyDate(weekDate);
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
					$("#healthStatusName").html( '<a class="label label-success" >'+data.healthStatus+'</a>');
				}else if(data.healthStatus.toUpperCase() == "MINOR"){
					$("#healthStatusName").html( '<a class="label label-warning" >'+data.healthStatus+'</a>');
				}else if(data.healthStatus.toUpperCase() == "MAJOR"){
					$("#healthStatusName").html( '<a class="label label-danger" >'+data.healthStatus+'</a>');
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


function getAssignToFromIssue(){
		if($('#issue').val() != '' && $('#issue').val() != null ){
			if($('#showIssueInWO').val() == 'true' || $('#showIssueInWO').val() == true){
				$('#taskFieldSet').hide();
			}
			if($("#showPendingIssueWhileCreatingWO").val() == true || $("#showPendingIssueWhileCreatingWO").val() == 'true'){
			$('#driverDiv').hide();
			$('#anyAssignee').hide();
			$('#issueAssignee').show()
			$('#assigneeIndetifier').val("false")
			$('#SelectDriverName').select2('val',"");
			}
		}else{
			$('#driverDiv').show();
			$('#taskFieldSet').show();
			$('#anyAssignee').show();
			$('#issueAssignee').hide()
			$('#assigneeIndetifier').val("true")
		}

	
	var jsonObject				= new Object();
	jsonObject["vid"] 	 		=  $('#select3').val();
	 if($("#issueVid").val() > 0 && $("#issueId").val() > 0){
		 jsonObject["issueId"] 	 	=  $('#issueId').val();
	 }else{
		 var issue = $('#issue').val();
		 if(issue != null)
			 issue=issue.toString();

		 jsonObject["issueId"]		=  issue;// from WO module 
	 }
	jsonObject["companyId"] 	=  $('#companyId').val();
	var validationFlag			= true;
	var issueDetails;
	$.ajax({
		url: "getVehicleWiseIssueDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setIssueData(data);
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			hideLayer();
		}
	});
}
function setIssueData(data){
	if(data.issueDetails != undefined){
				$('#priority').select2('data', {
					id : data.issueDetails[0].issues_LABELS_ID,
					text : data.issueDetails[0].issues_LABELS
				});
	
				if($("#showPendingIssueWhileCreatingWO").val() == true || $("#showPendingIssueWhileCreatingWO").val() == 'true'){
					if(data.subsciberList != undefined){
						var option  = '';
						for(var i = 0; i < data.subsciberList.length; i++){
							option	+= '<option value="'+data.subsciberList[i].user_id+'">'+data.subsciberList[i].firstName+' - '+data.subsciberList[i].lastName+'</option>';
						}
						$('select[name=issueAssign]').html(option);
						if(data.subsciberList.length > 0){
							$('select[name=issueAssign]').select2('data',{
								id : data.subsciberList[0].user_id,
								text : data.subsciberList[0].firstName+' - '+data.subsciberList[0].lastName
							})
						}
					}}
				$('#woEndDate').val(populateEndDateOnIssue(data.issueDetails[0].issues_LABELS_ID));
				
	}
}
/*$(document).ready(function(){
	
	validateJobAndSubTypeForSR1($("#from").val(),$("#to").val());
	
	$("#from").change(function(){
		validateJobAndSubTypeForSR1($("#from").val(),$("#to").val());
	});
	$("#to").change(function(){
		console.log("inside change")
		validateJobAndSubTypeForSR1($("#from").val(),$("#to").val());
	});
});*/

/*function validateJobAndSubTypeForSR1(jobId, jobsubId){	
		
		if($("#select3").val() >0 && $("#select3").val() != undefined && jobsubId != ""  && jobId>0){
		
		var jsonObject =  new Object();
		
		jsonObject['vid']  			= $("#select3").val();
		jsonObject['jobType']       = jobId;
		jsonObject['jobsubType']    = jobsubId;
		jsonObject['companyId']		= $("#companyId").val();
		
		$.ajax({
			url       : "validateJobTypeAndsubType",
			type      : "POST",
			dataType  : 'json',
			data 	  : jsonObject,
			success   : function(data){
				console.log("data 1 - "+ JSON.stringify(data));
				if(data.Warning != null || data.Warning != undefined){
					showMessage('warning', data.Warning);
				    $("#jobsubId").val("");
				}
			},
			error     : function(e){
			    hideLayer();
			}
			
		});
	}
}*/





function validateJobAndSubTypeForMultiple(d){
	validateJobAndSubTypeForSR($("#jobId").val(), $("#jobsubId").val(),d);
}
$(document).ready(function(){
	
	validateJobAndSubTypeForSR($("#from").val(),$("#to").val(),"");
	
	$("#from").change(function(){
		validateJobAndSubTypeForSR($("#from").val(),$("#to").val(),"");
	});
	$("#to").change(function(){
		validateJobAndSubTypeForSR($("#from").val(),$("#to").val(),"");
	});
});

function validateJobAndSubTypeForSR(jobId, jobsubId, d){	
		
		if($("#select3").val() >0 && $("#select3").val() != undefined && jobsubId != ""  && jobId>0){
		
		var jsonObject =  new Object();
		
		var d = d;
		jsonObject['vid']  			= $("#select3").val();
		jsonObject['jobType']       = jobId;
		jsonObject['jobsubType']    = jobsubId;
		jsonObject['companyId']		= $("#companyId").val();
		
		$.ajax({
			url       : "validateJobTypeAndsubType",
			type      : "POST",
			dataType  : 'json',
			data 	  : jsonObject,
			success   : function(data){
				if(data.Warning != null || data.Warning != undefined){
					showMessage('warning', data.Warning);
				    $("#jobsubId").val("");
				}
				if(d != "")
					setServiceProgramDetailsForMultiple(data,d);
				else
				    setServiceProgramDetails(data);
			},
			error     : function(e){
			    hideLayer();
			}
			
		});
	}
}



function setServiceProgramDetails(data) {
    var list = data.serviceReminderDtoList;
    $('#programDetails').empty();

  	var ids = "";	
	var jobSubIds = [];
  
    if (data.serviceReminderDtoList !== undefined) {
		   
	   for (var i = 0; i < list.length; i++) {
		    var ServiceList = list[i];
		    var div = $("<div>");
		    var anchor = $("<a>")
				.attr("href", "ShowService.in?service_id="+ServiceList.service_id)
				.attr("target", "_blank") // Set the URL to link to
				.text(" SR-" +ServiceList.service_Number);
		      
		    var p1 = $("<p>").text("Service Program - " + ServiceList.serviceProgram);
		    p1.append(anchor);
		    div.append(p1);
		    
		    if (ids ==""){
			   ids += ServiceList.serviceIdsWithSubIds;
		} else {
			   ids += ","+ServiceList.serviceIdsWithSubIds;
		}
		
		$('#programDetails').append(div);
		jobSubIds.push(Number(ServiceList.serviceSubTypeId));
	  }
      	 
    }

  	var subIds = $("#to").val().split(",");	
	  for(var i =0; i<subIds.length; i++){  
		 if(subIds[i] !=0){
		  if(!jobSubIds.includes(Number(subIds[i]))) {
			  if(ids === ""){
			  	ids += subIds[i]+"_";
			  	 }else {
			  	ids += ","+subIds[i]+"_";
			  }
		     }
		 }
	   }	
	$("#newJobSubId").val(ids);
    
}

function setServiceProgramDetailsForMultiple(data, d) {
	var list = data.serviceReminderDtoList;
	$('#programDetails'+d).empty();
  
        var ids ="";	
	    var jobSubIds = [];
	    
	if (data.serviceReminderDtoList !== undefined) {
	    
	    for (var i = 0; i < list.length; i++) {
            var ServiceList = list[i];
	        var div = $("<div>");
	        var anchor = $("<a>")
		        .attr("href", "ShowService.in?service_id="+ServiceList.service_id)
			    .attr("target", "_blank") // Set the URL to link to
			    .text(" SR-" +ServiceList.service_Number);
	      
	        var p1 = $("<p>").text("Service Program - " + ServiceList.serviceProgram);
	     	p1.append(anchor);
	     	div.append(p1);
	     	
	          if(ids ==""){
			     ids += ServiceList.serviceIdsWithSubIds;
			 } else {
				 ids += ","+ServiceList.serviceIdsWithSubIds;
			 }
	     	  jobSubIds.push(Number(ServiceList.serviceSubTypeId));
	       
	       	  $('#programDetails'+d).append(div);
		 }
		    		    
	}
		  
	var subIds = $("#to"+d).val().split(",");

	for(var i =0; i<subIds.length; i++){
			  if(subIds[i] !=0){
				  if(!jobSubIds.includes(Number(subIds[i]))) {
					  if(ids === ""){
						  	ids += subIds[i]+"_";
					  	}else{
						  	ids += ","+subIds[i]+"_";
					  }
				  }
			  }
		 }	
		 
	     $('#newJobSubId'+d).val(ids);
}
