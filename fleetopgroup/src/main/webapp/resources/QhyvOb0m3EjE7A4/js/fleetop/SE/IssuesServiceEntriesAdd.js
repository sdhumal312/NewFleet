function visibility(a, b) {
    var c = document.getElementById(a),
        d = document.getElementById(b);
    "block" == c.style.display ? (c.style.display = "none", d.style.display = "block") : (c.style.display = "block", d.style.display = "none")
}

function showoption() {
    var a = $("#renPT_option :selected"),
        b = a.text();
    "CASH" == b ? $("#target1").text(b + " Receipt NO : ") : $("#target1").text(b + " Transaction NO : ")
}

$(document).ready(function() {
	/*$("#vehicle_vid").select2( {
        minimumInputLength:2,
        minimumResultsForSearch:10, 
        ajax: {
            url:"getVehicleSearchServiceEntrie.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.vehicle_registration, slug: a.slug, id: a.vid
                        }
                    }
                    )
                }
            }
        }
    }),*/
    $("#from").select2({
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
    });
    $("#vehicle_vid").change(function() {
        
    }),
    
    $("#ServiceReminder").select2({
		multiple: !0,
		allowClear: !0,
        data : ""
    }),
    
    $(document).ready(function() {
    		
    	$.getJSON("getVehicleOdoMerete.in", {
            vehicleID: $("#vehicle_vid").val(),
            ajax: "true"
        }, function(a) {
            var b = "";
            b = a.vehicle_Odometer, document.getElementById("vehicle_Odometer").placeholder = b;
            if(document.getElementById("vehicle_Odometer_old") != null){
            	document.getElementById("vehicle_Odometer_old").value = b;
            }
            if(a.gpsOdameter != undefined && a.gpsOdameter > 0){
            	$('#grpgpsOdometer').show();
            	$('#gpsOdometer').val(a.gpsOdameter);
            	$('#vehicle_Odometer').val(parseInt(a.gpsOdameter));
            	$('#grpodoMeter').hide();
            	$("#vehicle_Odometer").attr('readonly','readonly')

            }
        })
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
            		console.log("serviceId>>>>>",serviceId) 
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
            			 
             })
        })
    }),
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
    }),
    $("#SelectDriverName").select2({
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
                            text: a.driver_empnumber + " - " + a.driver_firstname,
                            slug: a.slug,
                            id: a.driver_id
                        }
                    })
                }
            }
        }
    })
    
});

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
});






//latest
var specialKeys = new Array;
specialKeys.push(8), specialKeys.push(9), specialKeys.push(46), specialKeys.push(36), specialKeys.push(35), specialKeys.push(37), specialKeys.push(39), $(document).ready(function() {
    $("#vendorEnter").hide(), $("#driverEnter").hide()
}), $(document).ready(function() {
    $("#selectVendor").on("change", function() {
        var a = document.getElementById("selectVendor").value;
        if (0 != a) {
            var b = '<option value="1"> CASH</option><option value="2">CREDIT</option><option value="3">NEFT</option><option value="4">RTGS</option><option value="5">IMPS</option><option value="6">DD</option><option value="7">CHEQUE</option><option value="10">ON ACCOUNT</option>';
            $("#renPT_option").html(b)
        } else {
            var b = '<option value="1">CASH</option>';
            $("#renPT_option").html(b)
        }
    }), $("#selectVendor").change(), $("#renPT_option").on("change", function() {
        showoption()
    }), $("#renPT_option").change()
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
        c.preventDefault(), a >= d && (d++, $(b).append('<div><div class="row1"><label class="L-size control-label" for="priority">JobType </label><div class="I-size"><div class="col-md-8"> <input type="hidden" id="from' + d + '" name="service_typetask" style="width: 100%;" required="required" placeholder="Please Enter 3 or more Job Type Name" /></div></div></div><div class="row1"><label class="L-size control-label">Service Sub Jobs <abbr title="required">*</abbr></label><div class="I-size" id="JoBSelect' + d + '"><div class="col-md-8"><input type="hidden"  value="0" name="service_subtypetask" id="to' + d + '" style="width: 100%;" required="required"placeholder="Please Enter 3 or more Job Sub Type Name" /></div><div class="col-md-1"><a class=" btn btn-link " onclick="visibility( \'JoBEnter' + d + "', 'JoBSelect" + d + '\');"> <i class="fa fa-plus"> New</i></a></div></div><div id="JoBEnter' + d + '" class="contact_Hide"><div class="I-size"><div class="col-md-8"><input type="text" class="form-text"  name="service_ROT" id="SubReType'+ d +'" maxlength="150" placeholder="Enter ROT Name" /><br> <input type="text" class="form-text" \t name="service_ROT_number" id="SubReType" maxlength="30" \tplaceholder="Enter ROT Number" /></div><div class="col-md-1"><a class=" btn btn-link col-sm-offset-1" \tonclick="visibility( \'JoBEnter' + d + "', 'JoBSelect" + d + '\');"> <i\tclass="fa fa-minus"> Select</i></a></div></div></div></div><div class="jobTypeRemarkCol row1 form-group"><label class="L-size control-label" for="to">Remark: </label><div class="I-size"><div class="col-md-8"><input type="text" class="form-text" id="taskRemark" name="taskRemark" maxlength="250" placeholder="Enter Remark" /></div></div></div><a href="#" class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font></div>'), $(document).ready(function() {
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
})


//validation devy

function validateServiceEntry(){
	//alert("validateServiceEntry")
	
	if(Number($('#vehicle_vid').val()) <= 0){
		$("#vehicle_vid").select2('focus');
		showMessage('errors', 'Please Select Vehicle !');
		hideLayer();
		return false;
	}
	
	if(Number($('#vehicle_Odometer').val()) < 0 || $('#vehicle_Odometer').val() == ""){
		$("#vehicle_Odometer").focus();
		showMessage('errors', 'Please Enter Vehicle Odometer !');
		hideLayer();
		return false;
	}
	
	if(Number($('#selectVendor').val()) <= 0 && ($('#enterVendorName').val() == null || $('#enterVendorName').val().trim() == '')){
		$("#selectVendor").select2('focus');
		showMessage('errors', 'Please Select Service Vendor !');
		hideLayer();
		return false;
	}
	
	if(Number($('#invoiceNumber').val()) <= 0){			
		$("#invoiceNumber").focus();
		showMessage('errors', 'Please Enter Invoice Number!');
		hideLayer();
		return false;
	}
	
	if($('#invoicestartDate').val() <= 0){		
		showMessage('errors', 'Please Select Invoice Date!');
		hideLayer();
		return false;
	}
	
	if($('#servicepaiddate').val() <= 0){					
		showMessage('errors', 'Please Select Paid Date!');
		hideLayer();
		return false;
	}
	
	/*var jobTypes	= false;
	$('input[name*=service_typetask]' ).each(function(){
		var jobTypesInfo = $("#"+$( this ).attr( "id" )).val();
		if(jobTypesInfo <= 0){
			 $("#"+$( this ).attr( "id" )).select2('focus');
			 jobTypes	= true;
			showMessage('errors', 'Please Select Job Types!');
			return false;
		}
	});
	
	var jobSubTypes	= false;
	$('input[name*=service_subtypetask]' ).each(function(){
		var jobTypesInfo = $("#"+$( this ).attr( "id" )).val();
		if(jobTypesInfo <= 0){
			 $("#"+$( this ).attr( "id" )).select2('focus');
			 jobSubTypes	= true;
			showMessage('errors', 'Please Select Job Sub Types!');
			return false;
		}
	});*/
	
	/*var rotName	= false;
	$('input[name*=service_ROT]' ).each(function(){
		var rotNameInfo = $("#"+$( this ).attr( "id" )).val();
		if(rotNameInfo <= 0){
			 $("#"+$( this ).attr( "id" )).focus();
			 rotName	= true;
			showMessage('errors', 'Please Select Rot Name!');
			return false;
		}
	});
	
	var rotNumber	= false;
	$('input[name*=service_ROT_number]' ).each(function(){
		var rotNumberInfo = $("#"+$( this ).attr( "id" )).val();
		if(rotNumberInfo <= 0){
			 $("#"+$( this ).attr( "id" )).focus();
			 rotNumber	= true;
			showMessage('errors', 'Please Select Rot Number!');
			return false;
		}
	});*/
	
	/*if(jobTypes || jobSubTypes || rotName || rotNumber){
		return false;
	}*/
	
	//return true;
	//latest experiment start
	var jsonObject			= new Object();
	
	var jobType				 = new Array();  //id=from
	var serviceSubJobs		 = new Array();  //id=to
	var remark				 = new Array();  //id=taskRemark
	
	var service_ROT			 = new Array(); 
	var service_ROT_number	 = new Array(); 
	var serviceReminder		 = new Array();
	
	
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
	
	$("input[name=taskRemark]").each(function(){
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
	//look below
	$("input[name=service_id]").each(function(){
		if($(this).val() != null && $(this).val() != '' && $(this).val().length > 0){
			serviceReminder.push($(this).val());
		}
	});
	
	var array	 = new Array();
	console.log(" jobType.length ", jobType.length)
	console.log(" developer ", service_ROT[i])
	for(var i =0 ; i< jobType.length; i++){
		var serviceEntriesDetails	= new Object();
		serviceEntriesDetails.jobType				= jobType[i];
		serviceEntriesDetails.serviceSubJobs		= serviceSubJobs[i];
		serviceEntriesDetails.remark				= remark[i];
			
		serviceEntriesDetails.service_ROT		    = service_ROT[i];
		serviceEntriesDetails.service_ROT_number	= service_ROT_number[i];
		serviceEntriesDetails.serviceReminder	    = serviceReminder[i];
		
		array.push(serviceEntriesDetails);
	}
	
	
	
	jsonObject.serviceEntriesDetails = JSON.stringify(array);
	
	
	console.log("jsonObject.serviceEntriesDetails",jsonObject.serviceEntriesDetails)
	
	jsonObject["driverId"] 					=  $('#SelectDriverName').val(); 	//Perfect
	jsonObject["vehicleOdometerId"] 	  	=  $('#vehicle_Odometer').val();    //Perfect
	jsonObject["vendorId"] 	  				=  $('#selectVendor').val(); 		//Perfect
	jsonObject["newVendorId"] 	  			=  $('#enterVendorName').val(); 	//Perfect
	jsonObject["newVendorLocationId"] 	  	=  $('#enterVendorLocation').val(); //Perfect
	jsonObject["invoiceNumberId"] 	  		=  $('#invoiceNumber').val();	    //Perfect
	jsonObject["invoiceDateId"] 	  		=  $('#invoicestartDate').val();    //Perfect	
	jsonObject["jobNumberId"] 	  			=  $('#workorders_location').val(); //Perfect
	jsonObject["modeOfPaymentId"]	  		=  $('#renPT_option').val();		//Perfect
	
	jsonObject["cashReceiptNoId"]	  		=  $('#target1').val();	 //Check 
	
	jsonObject["paidDateId"]	  			=  $('#servicepaiddate').val();	    //Perfect	
	jsonObject["service_paidbyId"]	  		=  $('#service_paidbyId').val();  	//Perfect
	jsonObject["gpsOdometerId"] 	  		=  $('#gpsOdometer').val();			//Perfect


	


	
	
	
	jsonObject["vid"] 	  					=  $('#vehicle_vid').val(); //Problem
	jsonObject["serviceReminder"] 	  	=  $('#ServiceReminder').val();  //Can Be Issue

	jsonObject["workshopInvoiceAmountId"]	=  $('#workshopInvoiceAmountId').val();
	jsonObject["jobTypeId"]	  				=  $('#from').val();		//check
	jsonObject["serviceSubJobsId"]	  		=  $('#to').val();			//check		
	jsonObject["rotNameId"]	  				=  $('#SubReType').val();	//check		
	jsonObject["rotNumberId"]	  			=  $('#SubReTypeNum').val();	//check	
	jsonObject["taskRemark"]	  			=  $('#taskRemark').val();	//check imp	
	jsonObject["job_serviceId"]	  			=  $('#job_serviceId').val();	//check imp  //hidden
	
	jsonObject["ISSUES_ID"]	  				=  $('#ISSUES_ID').val();
	jsonObject["ISSUES_STATUS_ID"]	  		=  $('#ISSUES_STATUS_ID').val();
	
	
	console.log("jsonObject imp:",jsonObject)
	
	$.ajax({
		url: "createIssuesServiceEntries",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			window.location.replace("issuesOpen.in");
			
				if(data.already == true){
					showMessage('info', 'ServiceEntry Already Exists ')
				}
				else{
					showMessage('info', 'Service Entry Create Successfully!')
					
				}
				
		
			
			hideLayer();
		},
		error: function (e) {
			alert("failure")
			showMessage('errors', 'Some error occured!')
			hideLayer();
		}
	});
	
	
	//latest experiment stop
	
	return true;
}


function validateVehicle(){

 	showLayer();
    0 !=  $('#vehicle_vid').val() && $.getJSON("getTripVehicleOdoMerete.in", {
    	 FuelvehicleID:  $('#vehicle_vid').val(),
        ajax: "true"
    }, function(e) {
    	hideLayer();
    	if(e.vStatusId != 1 && e.vStatusId != 5){
    		showMessage('errors','You Can not create Service Entries , Vehicle Status is : '+e.vehicle_Status);
    		$("#selectVendor").select2("val", "");
    		return false;
    	}
    })
    
}