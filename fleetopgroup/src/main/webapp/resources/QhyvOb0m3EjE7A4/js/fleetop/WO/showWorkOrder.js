$(document).ready(
		function($) {
$("#confirmedWithDriver").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getDriverSearchAllListFuel.in?Action=FuncionarioSelect2",
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
                        	text: e.driver_empnumber + " - " + e.driver_firstname+" "+e.driver_Lastname +" - "+e.driver_fathername,
                            slug: e.slug,
                            id: e.driver_id
                        }
                    })
                }
            }
        }
    }), $("#confirmedWithAssignee").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
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
    })
	
	$('#confirmedWithDriver').select2('data', {
    		id : $('#driverId').val(),
    		text : $('#driverName').val()
    	});
    	
    $('#confirmedWithAssignee').select2('data', {
    		id : $('#assigneeId').val(),
    		text : $('#assigneeName').val()
    	});	
	
});	
var dataTableInitialized = false;
function serachWoByNumber(){
	var jsonObject					= new Object();
	jsonObject["woNumber"]			= $("#searchByNumber").val();
	
	if( $("#searchByNumber").val() == ""){
		$('#searchByNumber').focus();
		showMessage('errors', 'Please Enter Valid Work Order Number !');
		return false;
	}
	
	showLayer();
	$.ajax({
		url: "searchWoByNumber",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.WorkOrderFound != undefined && data.WorkOrderFound == true){
				hideLayer();
				window.location.replace("showWorkOrder?woId="+data.workOrderId+"");
			}
			
			if(data.WorkOrderNotFound != undefined && data.WorkOrderNotFound == true){
				hideLayer();
				showMessage('info', 'Please Enter valid Work Order Number!');
			}
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

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
    "block" == c.style.display ? (c.style.display = "none", d.innerHTML = "Add Task",$('#issueId').val('0'),$('#issueMsg').html(' ')) : (c.style.display = "block", d.innerHTML = "Cancel Task")
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
        if($("#getPerDayWorkingHourForDriver").val() > 0){
			var v = $("#perHourDriverSalary").val();
			var f = document.getElementById(a).value;
			console.log(" f == " +f);
			console.log("v   == " + v);
			m = v * f ;
			isNaN(m) || (document.getElementById(e).value = m.toFixed(2))
		}else{
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
}

function getInventoryListLoc(a, b,c,d) {
	if(($("#showSubLocation").val() == true || $("#showSubLocation").val() == 'true') && ($("#subLocationId").val() > 0)){
		$("#" + a).select2({
			minimumInputLength: 2,
			minimumResultsForSearch: 10,
			ajax: {
				url: "getInventorySearchListByMainAndSubLocation.in",
				dataType: "json",
				type: "POST",
				contentType: "application/json",
				quietMillis: 50,
				data: function(a) {
					return {
						term: a,
						subLocationId: c,
						mainLocationId: d
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
	}else{
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
								id: a.inventory_location_id,
								warranty : a.isWarrantyApplicable,
								repairable : a.isRepairable,
								partId : a.partid,
								locationId : a.locationId,
								maxQuantity : a.location_quantity
							} : {
								text: a.location + " - " + a.partnumber + " - " + a.partname + " - " + a.partManufacturer + " - " + a.location_quantity,
								slug: a.slug,
								id: a.inventory_location_id,
								disabled: !0,
								warranty : a.isWarrantyApplicable,
								repairable : a.isRepairable,
								partId : a.partid,
								locationId : a.locationId,
								maxQuantity : a.location_quantity
							}
						})
					}
				}
			}
		})
		
	}
	
}




function testChecked(){
$('#warrantyData').dataTable({searching: false, paging: false, info: false});
	$("input[name=warrantyParts]").each(function(){
		if($('#'+this.id+'').prop('checked')){ 
				console.log('selected ...... ');
	    }
	});
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
        },
        createSearchChoice:function(term, results) {
        	 if($('#autoLabourAdd').val()==true || $('#autoLabourAdd').val()=='true'){
        	if ($(results).filter( function() {
                return term.localeCompare(this.text)===0; 
            }).length===0) {
                return {id:term, text:term + ' [New]'};
            }
        	   }
        },
     
        
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
    }), $(b).on("click", ".remove_field", function(a) {
        a.preventDefault(), $(this).parent("div").remove(), d--
    })
})

function savePartDetails(workordertaskid){
	if(!quantityValidation(workordertaskid)){
		return false;
	}
	if(Number($('#quantity'+workordertaskid+'').val()) <= 0){
		showMessage('errors', 'Please Enter  Part Quantity !');
		return false;
	}
	$('#savePart').hide();
	var jsonObject					 		= new Object();
	jsonObject["workOrderId"]	 			= $('#lastworkorder_id').val();
	jsonObject["woTaskId"]					= workordertaskid;
	jsonObject["partId"]					= $('#inventory_name'+workordertaskid+'').val();
	jsonObject["quantity"]					= $('#quantity'+workordertaskid+'').val();
	jsonObject["oldpart"]					= $('#oldpart'+workordertaskid+'').val();
	jsonObject["last_occurred"]		 		= $('#last_occurred'+workordertaskid+'').val();
	jsonObject["showSubLocation"]		 	= $('#showSubLocation').val();
	jsonObject["subLocationId"]		 		= $('#subLocationId').val();
	jsonObject["validateDoublePost"]		= true;
	jsonObject["unique-one-time-token"]		= $('#addPartToken').val();
	jsonObject["inventoryId"]				= $('#inventoryId'+workordertaskid+'').val();	
	jsonObject["invoiceWisePartListConfig"] = $("#invoiceWisePartListConfig").val();
	var partData = $('#inventory_name'+workordertaskid+'').select2('data');
	if(partData != undefined && partData != null){
		jsonObject["masterPartId"] 				= partData.partId;
	}
	
	
	if(Number( $('#inventory_name'+workordertaskid+'').val() ) <= 0){
		$('#inventory_name'+workordertaskid+'').select2('focus');
		showMessage('errors', 'Please Select Part !');
		$('#savePart').show();
		return false;
	}
	
	if(Number( $('#quantity'+workordertaskid+'').val() ) < 0 || $('#quantity'+workordertaskid+'').val() == ""){
		$('#quantity'+workordertaskid+'').focus();
		showMessage('errors', 'Please Enter Quantity !');
		$('#savePart').show();
		return false;
	}
	var isRequestSubmitted = false;
	showLayer();
	$.ajax({
		url: "savePartForWorkOderTask",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		beforeSend: function(){
			$('#savePart').hide();
            isRequestSubmitted = true;
			 if(!isRequestSubmitted){
				 return false;
			 }
            
        },
		success: function (data) {
			if(data.hasError != undefined && data.hasError){
				hideLayer();
				showMessage('errors', 'Some Error Occurred!');
				return false;
			}
			$('#addPartToken').val(data.accessToken);
			hideLayer();
			
			if(data.partAdded != undefined && data.partAdded == true){
				window.location.replace("showWorkOrder.in?woId="+$('#lastworkorder_id').val()+"");
			}else if(data.NoInventory != undefined && data.NoInventory == true){
				$('#savePart').hide();
				showMessage('errors', 'Part Not Added, Please Add Parts To Inventory First !');
			}else if(data.NoAuthen != undefined && data.NoAuthen == true){
			     $('#savePart').hide();
				showMessage('errors', 'Please get Permission to add Parts in Work Order !');
			}else if(data.quantityZero != undefined && data.quantityZero){
			     $('#savePart').hide();
				showMessage('errors', 'Please Select Part Quantity !');
			}
		},
		error: function (e) {
			hideLayer();
			$('#savePart').hide();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
}

function saveLabourDetails(workordertaskid){
	$('#saveLabour').hide();
	
	var jsonObject					 		= new Object();
	jsonObject["workOrderId"]	 			= $('#workorders_id').val();
	jsonObject["woTaskId"]					= workordertaskid;
	jsonObject["laberid"]					= $('#labername'+workordertaskid+'').val();
	jsonObject["laberhourscost"]			= $('#laberhourscost'+workordertaskid+'').val();
	jsonObject["eachlabercost"]				= $('#eachlabercost'+workordertaskid+'').val();
	jsonObject["laberdiscount"]				= $('#laberdiscount'+workordertaskid+'').val();
	jsonObject["labertax"]					= $('#labertax'+workordertaskid+'').val();
	jsonObject["totalLaborcost"]			= $('#totalLaborcost'+workordertaskid+'').val();
	jsonObject["validateDoublePost"]		= true;
	jsonObject["unique-one-time-token"]		= $('#addLabourToken').val();
	
	
	
	if(!validateLabourSave(workordertaskid)){
		$('#saveLabour').show();
		return false;
	}
	
	var isRequestSubmitted = false;
	showLayer();
	$.ajax({
		url: "saveLabourForWorkOderTask",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		beforeSend: function(){
			$('#saveLabour').hide();
            isRequestSubmitted = true;
			 if(!isRequestSubmitted){
				 return false;
			 }
            
        },
		success: function (data) {
			if(data.hasError != undefined && data.hasError){
				hideLayer();
				showMessage('errors', 'Some Error Occured!');
				return false;
			}
			$('#addLabourToken').val(data.accessToken);
			
			hideLayer();
			
			if(data.labourAdded != undefined && data.labourAdded == true){
				window.location.replace("showWorkOrder.in?woId="+$('#workorders_id').val()+"");
			}
			
			if(data.NoAuthen != undefined && data.NoAuthen == true){
				showMessage('info', 'Please get Permission to add Labour Details in Work Order !');
			}
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
}

function validateLabourSave(workordertaskid){

	if($('#labername'+workordertaskid+'').val() == ""){
		$('#labername'+workordertaskid+'').focus();
		showMessage('errors', 'Please Enter Technician Name !');
		return false;
	}
	
	if(Number( $('#laberhourscost'+workordertaskid+'').val() ) < 0 || $('#laberhourscost'+workordertaskid+'').val() == ""){
		$('#laberhourscost'+workordertaskid+'').focus();
		showMessage('errors', 'Please Enter Time !');
		return false;
	}
	
	if(Number( $('#eachlabercost'+workordertaskid+'').val() ) < 0 || $('#eachlabercost'+workordertaskid+'').val() == ""){
		$('#eachlabercost'+workordertaskid+'').focus();
		showMessage('errors', 'Please Enter Cost !');
		return false;
	}
	
	if(Number( $('#laberdiscount'+workordertaskid+'').val() ) < 0 || $('#laberdiscount'+workordertaskid+'').val() == ""){
		$('#laberdiscount'+workordertaskid+'').focus();
		showMessage('errors', 'Please Enter Discount !');
		return false;
	}
	
	if(Number( $('#labertax'+workordertaskid+'').val() ) < 0 || $('#labertax'+workordertaskid+'').val() == ""){
		$('#labertax'+workordertaskid+'').focus();
		showMessage('errors', 'Please Enter Tax !');
		return false;
	}
	return true;
}

function markAsComplete(workordertaskid){
	
	var jsonObject					 		= new Object();
	jsonObject["woTaskId"]					= workordertaskid;
	
	
	showLayer();
	$.ajax({
		url: "saveMarkAsComplete",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			
			if(data.markAsCompleted != undefined && data.markAsCompleted == true){
				window.location.replace("showWorkOrder.in?woId="+$('#woId').val()+"");
				showMessage('success', 'Mark As Completed');
			}
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
}

$(document).ready(function() {
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
    }), 
    
    $("#to").select2({
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
	
  var showVehicleHealthStatus = $("#showVehicleHealthStatus").val();
	
  if(showVehicleHealthStatus == true || showVehicleHealthStatus == 'true'){
	  
	  var vehicleNumber =  $("#vehicleNumber").val();
	  var vehicleStr = vehicleNumber.replace(/-/g, "");
	  console.log("showVehicleHealthStatus---",showVehicleHealthStatus)
	  getVehicleHealthStatus(vehicleStr);
  }
    
})

function saveTaskDetails(){
	
	var jsonObject					 		= new Object();
	jsonObject["workOrderId"]	 			= $('#workorders_id').val();
	jsonObject["vid"]						= $('#vid').val();
	jsonObject["jobtypeId"]					= $('#from').val();
	jsonObject["jobsubtypeId"]				= $('#to').val();
	jsonObject["taskRemark"]				= $('#taskRemark').val();
	jsonObject["issueId"]					= $('#issueId').val();
	jsonObject["unique-one-time-token"]		= $('#accessIssueTaskToken').val();
	if($('#categoryId').val()>0){
		jsonObject["categoryId"]                = $('#categoryId').val();
	}
	if(Number( $('#from').val() ) < 0 || $('#from').val() == ""){
		$('#from').select2('focus');
		showMessage('errors', 'Please Enter Job Type !');
		return false;
	}
	
	if(Number( $('#to').val() ) < 0 || $('#to').val() == ""){
		$('#to').select2('focus');
		showMessage('errors', 'Please Enter Job Sub Type !');
		return false;
	}
	
	if(($('#showPartCategoriesList').val() == 'true' || $('#showPartCategoriesList').val() == true) && Number($('#categoryId').val()) <= 0) {
		showMessage('errors', 'Please Select Category !');
		return false;
	}
	
	showLayer();
	$.ajax({
		url: "saveWorkOderTasks",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			$('#accessIssueTaskToken').val(data.accessToken);
			if(data.taskAdded != undefined && data.taskAdded == true){
				window.location.replace("showWorkOrder.in?woId="+$('#workorders_id').val()+"");
			}
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
}

function deletePartDetails(workordertaskto_partid,subLocationId) {
	
	if (confirm('are you sure you want to delete ?')) {
		showLayer();
		
		var jsonObject								= new Object();
		jsonObject["workordertaskto_partid"]		= workordertaskto_partid;
		jsonObject["subLocationId"]		= subLocationId;
		
		$.ajax({
			url: "deletePartOfWorkOrdertask.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				
				if(data.accidentEntryApproved != undefined && data.accidentEntryApproved){
					hideLayer();
					showMessage('info', 'Cannot Delete Service Entry As Vehicle Accident Quotation is Approved !');
				}else if(data.partDeleted != undefined && data.partDeleted == true){
					hideLayer();
					window.location.replace("showWorkOrder.in?woId="+$('#woId').val()+"");
				}else if(data.NoAuthen != undefined && data.NoAuthen == true){
					hideLayer();
					showMessage('info', 'Please get Permission to delete Part in Work Order !');
				}
				
			},
			error: function (e) {
				console.log("Error : " , e);
				showMessage('errors', 'Some Error Occurred!');
				hideLayer();
			}
		});
	} 
	
}

function deleteLabourDetails(workordertaskto_laberid) {
	
	if (confirm('are you sure you want to delete ?')) {
		showLayer();
		
		var jsonObject								= new Object();
		jsonObject["workordertaskto_laberid"]		= workordertaskto_laberid;
		
		$.ajax({
			url: "deleteLabourOfWorkOrdertask.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				if(data.accidentEntryApproved != undefined && data.accidentEntryApproved){
					hideLayer();
					showMessage('info', 'Cannot Delete Service Entry As Vehicle Accident Quotation is Approved !');
				}else if(data.LabourDetailsDeleted != undefined && data.LabourDetailsDeleted == true){
					hideLayer();
					window.location.replace("showWorkOrder.in?woId="+$('#woId').val()+"");
				}
			},
			error: function (e) {
				console.log("Error : " , e);
				showMessage('errors', 'Some Error Occurred!');
				hideLayer();
			}
		});
	} 
	
}

function deleteTaskDetails(workordertaskid) {
	
	if (confirm('are you sure you want to delete ?')) {
		showLayer();
		
		var jsonObject					= new Object();
		jsonObject["workordertaskid"]	= workordertaskid;
		
		$.ajax({
			url: "deleteWorkOrdertask.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				
				if(data.accidentEntryApproved != undefined && data.accidentEntryApproved){
					hideLayer();
					showMessage('info', 'Cannot Delete Service Entry As Vehicle Accident Quotation is Approved !');
				}else if(data.deletePartFirst != undefined && data.deletePartFirst == true){
					hideLayer();
					showMessage('info', 'Cannot Delete Task, Please Delete Parts which are added to the Task.');
				}else if(data.deleteLobourFirst != undefined && data.deleteLobourFirst == true){
					hideLayer();
					showMessage('info', 'Cannot Delete Task, Please Delete Labour Details which are added to the Task.');
				}
				
				if(data.taskDetailsDeleted != undefined && data.taskDetailsDeleted == true){
					hideLayer();
					window.location.replace("showWorkOrder.in?woId="+$('#woId').val()+"");
				}
				
			},
			error: function (e) {
				console.log("Error : " , e);
				showMessage('errors', 'Some Error Occurred!');
				hideLayer();
			}
		});
	} 
	
}

function editTaskRemark(workordertaskid) {
	if (confirm("Are you sure?") == true) {
		showLayer();
		var jsonObject					= new Object();

		jsonObject["workordertaskid"]	= workordertaskid;
		jsonObject["remark"]			= $("#taskRemark_"+workordertaskid).val().trim();

		console.log("jsonObject : " , jsonObject);
		$.ajax({
			url: "workOrderWS/updateTaskRemarkById.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				if(data.taskRemark != undefined) {
					window.location.replace("showWorkOrder.in?woId="+$('#woId').val()+"");
				}
			},
			error: function (e) {
				console.log("Error : " , e);
			}
		});
		setTimeout(function(){ hideLayer(); }, 500);
	}
}

function updateWoGstCost(){
	
	var jsonObject					 		= new Object();
	jsonObject["workOrderId"]				= $('#woId').val();
	jsonObject["gstCost"]					= $('#gstCost').val();
	
	showLayer();
	$.ajax({
		url: "updateWoGstCost",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			if(data.gstCostUpdated != undefined && data.gstCostUpdated == true){
				hideLayer();
				window.location.replace("showWorkOrder.in?woId="+$('#woId').val()+"");
				showMessage('success', 'GST Cost Updated');
			}
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
}

function changeStatusToHold(workOrderId) {
	
	
	if (confirm('are you sure you want to change status to In Hold?')) {
		
		if($('#addHoldRemark').val() == true || $('#addHoldRemark').val() === 'true' ){
			
		$('#holdRemark').modal('show');
		}else{
			saveChangeStatusToHold();
		}
		
	} 
	
}

function HoldWOWithRemark(){
	if($('#woHoRemark').val() == null || $('#woHoRemark').val().trim() == ''){
		$('#woHoRemark').focus();
		showMessage('warning','Please enter Remark !');
		return false;
	}else{
		saveChangeStatusToHold();
	}
}


function saveChangeStatusToHold(){
	
	
		showLayer();
		
		var jsonObject						= new Object();
		jsonObject["workOrderId"]			= $('#workOrderId').val();
		jsonObject["woRemark"]				= $('#woHoRemark').val();
		
		$.ajax({
			url: "changeWorkorderStatusToHold.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				
				if(data.statusChangedToHold != undefined && data.statusChangedToHold == true){
					hideLayer();
					window.location.replace("showWorkOrder.in?woId="+$('#woId').val()+"");
				}
				
				if(data.NoAuthen != undefined && data.NoAuthen == true){
					showMessage('info', 'Please get Permission to change Work Order Status to Hold !');
				}
				
			},
			error: function (e) {
				console.log("Error : " , e);
				showMessage('errors', 'Some Error Occurred!');
				hideLayer();
			}
		});
	
	
}

function changeStatusToInProgress(workOrderId) {
	
	if (confirm('are you sure you want to change status to In Progress?')) {
		
		if($('#addInprocessRemark').val() == true || $('#addInprocessRemark').val() === 'true' ){
			
		$('#inProcessRemark').modal('show');
		}else{
			saveStatusToInProgress();
		}
		
	} 
	
}

function inProcessWOWithRemark(){
	if($('#woInProRemark').val() == null || $('#woInProRemark').val().trim() == ''){
		$('#woInProRemark').focus();
		showMessage('warning','Please enter Remark !');
		return false;
	}else{
		saveStatusToInProgress();
	}
}


function saveStatusToInProgress(){
	
	
		showLayer();
		
		var jsonObject						= new Object();
		jsonObject["workOrderId"]			= $('#workOrderId').val();
		jsonObject["woRemark"]				= $('#woInProRemark').val();
		jsonObject["inPorocess"]			= true ;
		
		$.ajax({
			url: "changeWorkorderStatusToInProgress.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				
				if(data.accidentEntryApproved != undefined ){
					hideLayer();
					showMessage('info', data.accidentEntryApproved);
				}else if(data.statusChangedToInProgress != undefined && data.statusChangedToInProgress == true){
					hideLayer();
					window.location.replace("showWorkOrder.in?woId="+$('#woId').val()+"");
				}
				
				if(data.NoAuthen != undefined && data.NoAuthen == true){
					showMessage('info', 'Please get Permission to change Work Order Status to In Progress !');
				}
				
			},
			error: function (e) {
				console.log("Error : " , e);
				showMessage('errors', 'Some Error Occurred!');
				hideLayer();
			}
		});
	
	
	
	
}

function changeStatusToComplete(workOrderId) {
	
	if (confirm('are you sure you want to change status to Complete?')) {
		if($('#taskToPartDocument').val() == true || $('#taskToPartDocument').val() == 'true'){
			
			if($('#photoPendingForAnyPart').val() == true || $('#photoPendingForAnyPart').val() == 'true'){
				showMessage('errors', 'Please Upload Photo for all parts added in this Workorder !');
				return false;
			}
	   }
	   
	   if($('#allPartAssigned').val() == false || $('#allPartAssigned').val() == 'false'){
				showMessage('errors', 'Please Add Part Serial Numbers for all Warranty Parts !');
				return false;
		}
	   
	   
	   if($('#addWOCompletionRemark').val() == 'true' || $('#addWOCompletionRemark').val() == true){
	   		$('#completionRemark').modal('show');
	   		//completeWOWithRemark()
	   		
	   }else{
	   		saveWorkOrderStatusToComplete();
	   }
	}
	
}

function completeWOWithRemark(){

	if($('#showAllTaskCompleteOption').val() == 'true' || $('#showAllTaskCompleteOption').val() == true){
		if($('#allTaskCOmpleted').val() == 'false' || $('#allTaskCOmpleted').val() == false){
				showMessage('errors', 'Please Complete All Task First !');
				return false;
		}
	}
//	if($('#remarkForIssueMandatory').val() == true || $('#remarkForIssueMandatory').val() == 'true'){
//		if($('#issueRemarkStatus').val() == true || $('#issueRemarkStatus').val() == 'true'){
//			showMessage('errors', 'Please add remark to all issues !');
//			return false;
//		}
//	}
	if($('#taskForIssueMandatory').val() == true || $('#taskForIssueMandatory').val() == 'true'){
		if($('#issueTaskStatus').val() == true || $('#issueTaskStatus').val() == 'true'){
			showMessage('errors', 'Please add task to all issues !');
			return false;
		}
	}
	
		saveWorkOrderStatusToComplete();
}

function saveWorkOrderStatusToComplete(){

	var jsonObject					= new Object();
		jsonObject["workOrderId"]	= $('#workOrderId').val();
		jsonObject["woRemark"]		= $('#woRemark').val();
		jsonObject["driverId"]		= $('#confirmedWithDriver').val();
		jsonObject["assignee"]		= $('#confirmedWithAssignee').val();
		
		showLayer();
		$.ajax({
			url: "changeWorkorderStatusToComplete.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				
				if(data.quotationNotApproved != undefined && data.quotationNotApproved){
					hideLayer();
					showMessage('errors', 'Cannot Close, Vehicle Accident quotation not approved !');
				}else if(data.statusChangedToComplete != undefined && data.statusChangedToComplete == true){
					hideLayer();
					window.location.replace("showWorkOrder.in?woId="+$('#woId').val()+"");
				}
				else if(data.DocMissing != undefined && data.DocMissing ){
					hideLayer();
					showMessage('errors', 'Please Upload Photo for all parts added in this Workorder !');
				}else{
					hideLayer();
				}
			},
			error: function (e) {
				console.log("Error : " , e);
				showMessage('errors', 'Some Error Occurred!');
				hideLayer();
			}
		});

}

$(document).ready(function () {
	
    $("#btnSubmit").on('keypress click', function (e) {
        e.preventDefault(); 	//stop submit the form, we will post it manually.
        uploadWorkOrderDocument();
    });

});

function uploadWorkOrderDocument(){
	var jsonObject						= new Object();
	jsonObject["workOrderId"]			= $('#woId').val();
	
	var form = $('#fileUploadForm')[0];
	var data = new FormData(form);

	data.append("WorkOrderData", JSON.stringify(jsonObject)); 
	
	$.ajax({
		type: "POST",
		enctype: 'multipart/form-data',
		url: "uploadWorkOrderDocument",
		data: data,
		processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
		success: function (data) {
			
			$('#addworkorderDocument').modal('hide');
			showMessage('success', 'Image uploaded !');
			
			if(data.UploadSuccess != undefined && data.UploadSuccess == true){
				window.location.replace("showWorkOrder.in?woId="+$('#woId').val()+"");
			}
			
			},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function getInvoiceWisePartList(taskId,mainLocationId,subLocationId){
	$("#inventory_name"+taskId+" ").select2( {
		minimumInputLength:2,minimumResultsForSearch:10, 
		ajax: {
            url:"getInvoiceWisePartList.in", 
            dataType:"json", 
            type:"POST", 
            contentType:"application/json", 
            quietMillis:50, 
            data:function(a) {
                return {
                	term: a,
					mainLocationId: mainLocationId,
					subLocationId: subLocationId
                }
            },results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                        	text: a.partnumber + " - " + a.partname + " - " + a.make +  " - " + a.inventory_id + " - " + a.quantity,// do not change the sequence
        					slug: a.slug,
        					id: a.inventory_location_id
                        }
                    })
                }
            }
        }
    })
}
function quantityValidation(taskId){
	dataArr = new Array()
	
	var data 		= $("#inventory_name"+taskId+" ").select2('data').text;
	var dataqty 	= $("#inventory_name"+taskId+" ").select2('data');
	dataArr		 	= data.split("-");
	
	var quantity 		= dataqty.maxQuantity;
	var inventoryId 	= dataArr[dataArr.length-2];
	var partQuantity 	= Number(quantity);
	var currentQuantity = Number($("#quantity"+taskId+" ").val());
	
	
	$("#inventoryId"+taskId+" ").val(inventoryId.trim());
	
		if(currentQuantity > partQuantity ){
			$("#quantity"+taskId+" ").val(0);
			showMessage('info','Quantity Can Not Be Greater Than Total Part Quantity '+partQuantity+' ')
			return false;
		}
	
	return true;
}


function showModaluploadWOTaskToPart(id){
	$('#woTastToPartDoc').modal('show');
	$('#workOrderTaskToPartId').val(id);
	
}

function uploadFileWoTaskToPart(){
	showLayer();
	var jsonObject = new Object();
	jsonObject["workordertaskto_partid"]= $('#workOrderTaskToPartId').val();
	jsonObject["description"] =$('#description').val();
	
	
	var doc= document.getElementById('workOTaskToPartDocument');
	
	var data = new FormData();
	
	data.append("partForWO",JSON.stringify(jsonObject));
	
	for(var i=0;i<doc.files.length;i++){
		
		data.append("file",doc.files[i]);
	}
	
	
	$.ajax({
		type: "POST",
		enctype: 'multipart/form-data',
		url: "uploadFilePartForWorkOderTask",
		data: data,
		contentType: false,
		processData: false,
		success :function(data){
			
			$('#woTastToPartDoc').modal('hide');
			hideLayer();
			showMessage('info','File Uploaded Succesfully !!! ');
			window.location.replace("showWorkOrder.in?woId="+$('#workorders_id').val()+"");
		
		},
		error :function(e){
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
		})
}

function getWOTaskToPartDoc(id){
	
	showLayer();
	var jsonObject = new Object();
	jsonObject["workordertaskto_partid"] = id;


	$.ajax({
		url : "getDocumentWOTaskToPartList",
		type : "POST",
		dataType : 'json',
		data :jsonObject,
		success :function(data){
			hideLayer();
			taskToPartDocument(data,id);
		},
		error : function(e){
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}


	});

}
function taskToPartDocument(data,id){
	
	if(data.documents != undefined && data.documents.length > 0){
		$('#wotaskTopartId').val(id);
		var documentList = data.documents;
		$('#Popup').modal('show');
		$('#documentTable').empty();
		var thead 	=$('<thead>');
		var tr1 	= $('<tr>');
		
		var th1		= $('<th class="fit ar">');
		var th2     =$('<th class="fit ar">');
		var th3     =$('<th class="fit ar">');
		var th4     =$('<th class="fit ar">');
		
		tr1.append(th1.append("Document name"));
		tr1.append(th2.append("Description"))
		tr1.append(th3.append("Document"));
		tr1.append(th4.append("Action"));
		
		thead.append(tr1);
		var tbody =$('<tbody>');
		
		for(var i=0;i<documentList.length;i++){
			
			var atag=' <a target="_blank" data-toggle="tip" data-original-title="Click Download" href="download/WorkOTaskToPart/'+documentList[i]._id +' "> <i class="fa fa-download"></i> Download  </a>';
			var adelete=' <a  data-toggle="tip" data-original-title="Delete File" onclick ="deleteWorkOTaskToPartDoc('+documentList[i]._id +') "> <i class="fa fa-trash"></i> Remove </a>';
			var tr1 =$('<tr>');
			var td1 =$('<td class="fit ar">');
			var td2 =$('<td class="fit ar">');
			var td3 =$('<td class="fit ar">');
			var td4 =$('<td class="fit ar">');
			tr1.append(td1.append(documentList[i].documentFilename));
			tr1.append(td4.append(documentList[i].description));
			tr1.append(td2.append(atag));
			tr1.append(td3.append(adelete));
			
			tbody.append(tr1);
			
		}
		$("#documentTable").append(thead);
		$("#documentTable").append(tbody);
		$("#documentTable").show();
	}else{
		
		hideLayer();
		
		$('#Popup').modal('hide');
		window.location.replace("showWorkOrder.in?woId="+$('#workorders_id').val()+"");
	}

	
}

function deleteWorkOTaskToPartDoc(id){
	showLayer();
	
	var jsonObject = new Object();
	jsonObject["docId"] = id;
	jsonObject["workordertaskto_partid"] = $('#wotaskTopartId').val();
	
	$.ajax({
		url : "deleteWorkOTaskToPartDocument",
		type : "POST",
		dataType : 'json',
		data :jsonObject,
		success :function(data){
			
			hideLayer();
			showMessage('info','File Deleted Succesfully !!! ');
			getWOTaskToPartDoc($('#wotaskTopartId').val());
		},
	
	
	error : function(e){
		hideLayer();
		showMessage('errors', 'Some Error Occurred!');
	}
	});
	
}

function getCheckedQuantity(maxQuantity, evt){
 if($('#'+evt.id+'').prop('checked')){
 	var checkedCount	= 0;
 	$("input[name=partWarrantyDetailsId]").each(function(){
		if($('#'+this.id+'').prop('checked')){
			checkedCount ++;
		}
	});
	
	if(Number(maxQuantity) < checkedCount){
		$('#'+evt.id+'').prop('checked', false);
		showMessage('info','You can select only '+maxQuantity+' parts !');
	}
 }
}
function getVehicleHealthStatus(vehicleStr){
	
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
				console.log("data--00",data)
				if(data.healthStatus != undefined && data.healthStatus != null){
					
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

function markAllTaskCompletd(){
 		var jsonObject						= new Object();
		jsonObject["workOrderId"]			= $('#woId').val();
		
		showLayer();
		
		$.ajax({
			url: "markAllTaskCompleted",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				if(data.saved != undefined && data.saved){
					showMessage('success', 'All Task Marked Completed Successfully !');
					location.replace("showWorkOrder.in?woId="+$('#woId').val());
				}				
			},
			error: function (e) {
				showMessage('errors', 'Some error occurred!')
				hideLayer();
			}
		});
}

function setIssueId(issueId,issueNumber){
	$('#issueId').val(issueId);
	$('#issueMsg').html('For issue Number : I-'+issueNumber);
	
	getCategoryId(issueId);
}
function getCategoryId(issueId) {
	var jsonObject = new Object();
	jsonObject["issueId"]  = issueId;
	
	$.ajax({
		url : "getCategoryByIssueId",
		type : "POST",
		dataType : 'json',
		data :jsonObject,
		success :function(data){
			if(data.IssueCategory != undefined){
				//$("#categoryId").val(data.IssueCategory.categoryId);
				//$("#categoryName").val(data.IssueCategory.pcName)
				$('#categoryId').val(data.IssueCategory.categoryId);
				$('#categoryId').attr("readonly", true); 
			}
		},
		error : function(e){
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
	
}
function issueRemark(issueId,userId,data){
	$('#issueRemark').modal('show');
	$('#issueIdRemark').val(issueId);
	if(userId != null && userId > 0){
	$('#confirmedWithAssignee').select2('data',{
		id : userId,
		text : data
	})
	}
}
//function saveWOIssueRemark(){
//	if($('#woIssueRemark').val() == null || $('#woIssueRemark').val().trim() == ''){
//		$('#woIssueRemark').focus();
//		showMessage('warning','Please enter Remark !');
//		return false;
//	}else if(Number($('#confirmedWithDriver').val()) <= 0){
//		$('#confirmedWithDriver').select2('focus');
//		showMessage('warning','Please Select Driver !');
//		return false;
//	}else if(Number($('#confirmedWithAssignee').val()) <= 0){
//		$('#confirmedWithAssignee').select2('focus');
//		showMessage('warning','Please Select Assignee !');
//		return false;
//	}
//	
//	var jsonObject				= new Object();
//	jsonObject["workOrderId"]	= $('#workOrderId').val();
//	jsonObject["woIssueRemark"]	= $('#woIssueRemark').val();
//	jsonObject["driverId"]		= $('#confirmedWithDriver').val();
//	jsonObject["assignee"]		= $('#confirmedWithAssignee').val();
//	jsonObject["issueIdRemark"]	= $('#issueIdRemark').val();
//	
//	showLayer();
//	$.ajax({
//		url: "saveWorkorderIssueRemark",
//		type: "POST",
//		dataType: 'json',
//		data: jsonObject,
//		success: function (data) {
//			if(data.saved == true || data.saved == 'true'){
//				showMessage('success', 'Reamrk Saved .');
//				hideLayer();
//				location.reload();
//			}else if(data.notSaved == true || data.notSaved == 'true'){
//				hideLayer();
//				showMessage('errors', 'Can not enter empty remark !');
//			}
//		},
//		error: function (e) {
//			console.log("Error : " , e);
//			showMessage('errors', 'Some Error Occurred!');
//			hideLayer();
//		}
//	});
//	
//}

$("#from").change(function() {
   $.getJSON("getJobSubTypeChangeWorkOrder.in", {
       JobType: $(this).val(),
       ajax: "true"
   }, function(a) {
       for (var b = a.length - 1, c = "", e = a.length, f = 0; e > f; f++) b != f ? (c += '{"id":"' + a[f].Job_Subid + '","text":"' + a[f].Job_ROT + "-" + a[f].Job_ROT_number + '" }', c += ",") : c += '{"id":"' + a[f].Job_Subid + '","text":"' + a[f].Job_ROT + "-" + a[f].Job_ROT_number + '" }';
       var g = "[" + c + "]",
           h = JSON.parse(g);
       $("#to").select2({
           allowClear: !0,
           multiple : !0,
           data: h
       })
       if( a != null && a != undefined && a.length == 1 ){
    	   $('#to').select2('data',{
    		   id : a[0].Job_Subid,
    		   text : a[0].Job_ROT + "-" + a[0].Job_ROT_number
    	   })
       }
   })
})

function savePartWithWarrantyDetails(){
	
	var jsonObject = new Object();
	
	jsonObject["vid"] 			= $('#vid').val();
	jsonObject["partId"] 		= $('#warrantyPartId').val();
	jsonObject["workorders_id"] = $('#workorders_id').val();
	jsonObject["taskId"] 		= $('#warrantyPartTaskId').val();
	
	var prePartWarrantyDetailsIdArr 	= new Array();
	var partWarrantyDetailsIdArr 		= new Array();
	var partWarrantyArr					= new Array();
	
	
	$("input[name=prePartWarrantyDetailsId]").each(function(){
		if($(this).val() != undefined && $(this).val() != ""){
			prePartWarrantyDetailsIdArr.push($(this).val());
		}
	});
	
	$("input[name=partWarrantyDetailsId]").each(function(){
		var ids = this.id;
		if($('#'+ids+'').prop('checked')){
			partWarrantyDetailsIdArr.push(this.id);
		}
	});
	
	
	for(var i =0 ; i< partWarrantyDetailsIdArr.length; i++){
			var partWarrantyDetails					= new Object();
			partWarrantyDetails.prePartWarrantyDetailsId		= prePartWarrantyDetailsIdArr[i];
			partWarrantyDetails.partWarrantyDetailsId			= partWarrantyDetailsIdArr[i];
			
			partWarrantyArr.push(partWarrantyDetails);
	}
	jsonObject.partWarrantyDetails 				= JSON.stringify(partWarrantyArr);
	
	showLayer();
	
	$.ajax({
		url : "savePartWithWarrantyDetails",
		type : "POST",
		dataType : 'json',
		data :jsonObject,
		success :function(data){
			if(data.validationFailed != undefined && data.validationFailed){
				showMessage('errors', 'Warranty part not added  !');
			}else if(data.success != undefined && data.success){
				showMessage('success', 'Warranty part added Successfully !');
			}
			location.replace("showWorkOrder.in?woId="+$('#workorders_id').val());
		},
	
	error : function(e){
		hideLayer();
		showMessage('errors', 'Some Error Occurred!');
	}
	});
}
function mountTyre(workorderTaskId,jobtypeId,jobSubtypeId){
	
	if(($("#tyreAssginFromWOConfig").val() == true  || $("#tyreAssginFromWOConfig").val() == 'true') && jobtypeId == $("#tyreAssignmentJobType").val()  && jobSubtypeId == $("#tyreAssignmentSubJobType").val() ){
		window.location.replace("vehicleTyreAssignmentFromWO.in?workorderTaskId="+workorderTaskId+"");
	}
}

function handleInputChange(inputElement) {
    var inputId = inputElement.id;
     
     getTecnicianPerDaySalary(inputId);
    
}
function  getTecnicianPerDaySalary(inputId){
	
	var jsonObject = new Object();
	
	jsonObject["vid"] = $("#"+inputId).val();
	
	$.ajax({
		url : "getTecnicianPerDaySalaryDetails",
		type : "POST",
		dataType : 'json',
		data :jsonObject,
		success :function(data){
			
			var perHourDriverSalary = data.perHourTecnicianSalary;
			  
			  $("#perHourDriverSalary").val(perHourDriverSalary);
		},
	error : function(e){
		hideLayer();
		showMessage('errors', 'Some Error Occurred!');
	}
	});
}
