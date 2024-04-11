function serachServiceEntryByNumber(){
	
	var jsonObject					= new Object();
	jsonObject["serviceNumber"]		= $("#searchByNumber").val();
	
	if( $("#searchByNumber").val() == ""){
		$('#searchByNumber').focus();
		showMessage('errors', 'Please Enter Valid Service Number !');
		return false;
	}
	
	showLayer();
	$.ajax({
		url: "searchServiceEntriesByNumber",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			if(data.serviceEntriesFound != undefined && data.serviceEntriesFound == true){
				
				if(data.serviceEntries.serviceEntries_statusId != 3){
					hideLayer();
					window.location.replace("showServiceEntryDetails.in?serviceEntryId="+data.serviceEntryId+"");
				} else {
					hideLayer();
					window.location.replace("showCompleteServiceEntryDetails.in?serviceEntryId="+data.serviceEntryId+"");
				}
				
			}
			
			if(data.serviceEntriesNotFound != undefined && data.serviceEntriesNotFound == true){
				hideLayer();
				showMessage('info', 'Please Enter valid Service Entry Number!');
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

function toggle2Tax(a, b) {
    var c = document.getElementById(a),
        d = document.getElementById(b);
    "block" == c.style.display ? (c.style.display = "none", d.innerHTML = '<i class="fa fa-inr"></i>') : (c.style.display = "block", d.innerHTML = "Cancel")
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

$(document).ready(function() {
	getServiceEntryDetails();
	
	  var showVehicleHealthStatus = $("#showVehicleHealthStatus").val();
		
	  if(showVehicleHealthStatus == true || showVehicleHealthStatus == 'true'){
		  
		  var vehicleNumber =  $("#vehicleNO").val();
		  console.log("vehicleNumber---",vehicleNumber)
		  var vehicleStr = vehicleNumber.replace(/-/g, "");
		  console.log("showVehicleHealthStatus---",showVehicleHealthStatus)
		  getVehicleHealthStatus(vehicleStr);
	  }
	
});

function getServiceEntryDetails() {
	
	var jsonObject					 = new Object();
	jsonObject["serviceEntryId"]	 = $('#serviceId').val();
	
	showLayer();
	$.ajax({
		url: "getServiceEntryDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			setServiceEntryDetails(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setServiceEntryDetails(data) {
		
	$("#serviceNumber").text(data.ServiceEntries.serviceEntries_Number);
	$("#vehicleNumber").html('<a href="VehicleServiceEntriesDetails?vid='+data.ServiceEntries.vid+'" target="_blank">'+data.ServiceEntries.vehicle_registration+'</a>');
	
	$("#invoiceDate").text(data.ServiceEntries.invoiceDate);
	$("#odometer").text(data.ServiceEntries.vehicle_Odometer);
	$("#jobNumber").text(data.ServiceEntries.jobNumber);
	$("#invoiceNumbers").text(data.ServiceEntries.invoiceNumber);
	$("#invoiceDateTime").text(data.ServiceEntries.invoiceDate);
	$("#driverName").text(data.ServiceEntries.driver_name);
	$("#vendorName").text(data.ServiceEntries.vendor_name);
	$("#vendorLocation").text(data.ServiceEntries.vendor_location);
	$("#paymentMode").text(data.ServiceEntries.service_paymentType);
	$("#receiptNo").text(data.ServiceEntries.service_PayNumber);
	$("#paidDate").text(data.ServiceEntries.service_paiddate);
	$("#paidBy").text(data.ServiceEntries.service_paidby);
	$("#tallyCompany").text(data.ServiceEntries.tallyCompanyName);
	$("#tallyExpenseName").text(data.ServiceEntries.tallyExpenseName);
	$("#tripsheetNumber").text(data.ServiceEntries.tripSheetNumber);
	$("#gpsOdometer").text(data.ServiceEntries.gpsOdometer);
	$("#workshopAmount").text(data.ServiceEntries.workshopInvoiceAmount);
	$("#created").text(data.ServiceEntries.createdBy);
	$("#createdDates").text(data.ServiceEntries.created);
	$("#lastUpdatedBy").text(data.ServiceEntries.lastModifiedBy);
	$("#lastUpdatedDates").text(data.ServiceEntries.lastupdated);
	
	var status=$('#statuesId').val();
	if(status==1){
		$('#status-open').css("color", "blue");
		
	}
	else if(status==2){
		$('#status-in-progress').css("color", "blue");
		
	}
	
	
	//$("#statues").text(data.ServiceEntries.serviceEntries_status);
	//$("#inProgress").html('<a href="ServiceEntries_INPROCESS?serviceEntries_id='+data.ServiceEntries.serviceEntries_id+'"><span id="status-in-progress" class="status-led"><i class="fa fa-circle"></i><span class="status-text">'+In Progress+'</span></span></a>');
	
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

function sumthere(a, b, c, d, e,x,y,z,flag) {
	console.log("inside sumthere")
	
	  if($("#allowGSTbifurcation").val() =="true" || $("#allowGSTbifurcation").val()==true){
			if( parseFloat($("#"+x).val()) >0 && flag=="false"){
				$("#"+y).val(0);
				$("#"+z).val(0);
			}
			if(parseFloat($("#"+y).val()) > 0 && flag=="true"){
				$("#"+x).val(0)
			}
			if(parseFloat($("#"+z).val()) > 0 && flag =="true"){
			    $("#"+x).val(0)
			}
	   }
	
	
    var f = document.getElementById(a).value,
        g = document.getElementById(b).value,
        h = document.getElementById(c).value,
        j = parseFloat(f) * parseFloat(g),
        k = j * h / 100,
        l = j - k;
        
        if($("#allowGSTbifurcation").val() =="true" || $("#allowGSTbifurcation").val()==true){
			var x = document.getElementById(x).value,
 				y = document.getElementById(y).value,	
 				z = document.getElementById(z).value;
			var   tax = parseFloat(x)+ parseFloat(y) +parseFloat(z),
			    a  = l * tax/100;
				m  = l+a;
		}else{
			 var i = document.getElementById(d).value,
			     a = l * i / 100,
        	     m = l + a;
		}
    isNaN(m) || (document.getElementById(e).value = m.toFixed(2))
}


async function savePartDetails(ServiceEntriestaskid){
	var isRequestSubmitted = false;
	$('#savePart').hide();
	
	var jsonObject					 		= new Object();
	jsonObject["serviceEntryId"]	 		= $('#serviceEntries_id').val();
	jsonObject["serviceEntrytaskId"]		= ServiceEntriestaskid;
	jsonObject["partsId"]					= $('#inventory_name'+ServiceEntriestaskid+'').val();
	jsonObject["partsQuantity"]				= $('#quantity'+ServiceEntriestaskid+'').val();
	jsonObject["partsEachCost"]				= $('#parteachcost'+ServiceEntriestaskid+'').val();
	jsonObject["partsDiscount"]				= $('#partdis'+ServiceEntriestaskid+'').val();
	jsonObject["partsTax"]					= $('#parttax'+ServiceEntriestaskid+'').val();
	jsonObject["partsTotalCost"]			= $('#tatalcost'+ServiceEntriestaskid+'').val();
	jsonObject["validateDoublePost"]		= true;
	jsonObject["unique-one-time-token"]		= $('#addPartToken').val();
	jsonObject["partIGST"]					= $("#partIGST"+ServiceEntriestaskid+'').val();
	jsonObject["partCGST"]					= $("#partCGST"+ServiceEntriestaskid+'').val();
	jsonObject["partSGST"]					= $("#partSGST"+ServiceEntriestaskid+'').val();

	if(!validateSavePart(ServiceEntriestaskid)){
		$('#savePart').show();
		return false;
	}
	
	$.ajax({
		url: "saveServiceEntryPartDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		 beforeSend: function(){
			 showLayer();
             isRequestSubmitted = true;
			 if(!isRequestSubmitted){
				 return false;
			 }
             
         },
         complete: function(){
        	 
         },
		success: function (data) {
			if(data.hasError != undefined && data.hasError){
				hideLayer();
				showMessage('errors', 'Some Error Occurred!');
				return false;
			}
			$('#addPartToken').val(data.accessToken);
			if(data.partAdded != undefined && data.partAdded == true){
				window.location.replace("showServiceEntryDetails.in?serviceEntryId="+$('#serviceEntries_id').val()+"");
			}
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
}

function validateSavePart(ServiceEntriestaskid){
	var partCGST = parseFloat($("#partCGST"+ServiceEntriestaskid).val());
	var partSGST = parseFloat($("#partSGST"+ServiceEntriestaskid).val());
	var partIGST = parseFloat($("#partIGST"+ServiceEntriestaskid).val());

	if(Number( $('#inventory_name'+ServiceEntriestaskid+'').val() ) <= 0){
		$('#inventory_name'+ServiceEntriestaskid+'').select2('focus');
		showMessage('errors', 'Please Select Part !');
		return false;
	}
	
	if(Number( $('#quantity'+ServiceEntriestaskid+'').val() ) < 0 || $('#quantity'+ServiceEntriestaskid+'').val() == ""){
		$('#quantity'+ServiceEntriestaskid+'').focus();
		showMessage('errors', 'Please Enter Quantity !');
		return false;
	}
	
	if(Number( $('#parteachcost'+ServiceEntriestaskid+'').val() ) < 0 || $('#parteachcost'+ServiceEntriestaskid+'').val() == ""){
		$('#parteachcost'+ServiceEntriestaskid+'').focus();
		showMessage('errors', 'Please Enter Parteachcost !');
		return false;
	}
	
	if(Number( $('#partdis'+ServiceEntriestaskid+'').val() ) < 0 || $('#partdis'+ServiceEntriestaskid+'').val() == ""){
		$('#partdis'+ServiceEntriestaskid+'').focus();
		showMessage('errors', 'Please Enter Discount !');
		return false;
	}
	
	if($("#allowGSTbifurcation").val() == "false" || $("#allowGSTbifurcation").val() == false){
		if(Number( $('#parttax'+ServiceEntriestaskid+'').val() ) < 0 || $('#parttax'+ServiceEntriestaskid+'').val() == ""){
			$('#parttax'+ServiceEntriestaskid+'').focus();
			showMessage('errors', 'Please Enter Tax !');
			return false;
		}
	}else{
		
		if ((isNaN(partCGST) || partCGST === 0) && (isNaN(partSGST) || partSGST === 0) && (isNaN(partIGST) || partIGST === 0)) {
			 showMessage('info', "please enter IGST OR CGST, SGST");
			 return false;
		}else{
			
			if(partCGST > 0 && (isNaN(partSGST) || partSGST === 0) && (isNaN(partIGST) || partIGST === 0)) {
			    showMessage('info',   "please enter PartSGST");
			    return false;
			}
			if(partSGST > 0 && (isNaN(partCGST) || partCGST === 0) && (isNaN(partIGST) || partIGST === 0)) {
			    showMessage('info',   "please enter PartCGST");
			    return false;
			}
		}
	}
	return true;
}


function sumthereLaber(a, b, c) {
    var d = document.getElementById(a).value,
        e = document.getElementById(b).value,
        f = parseFloat(d) * parseFloat(e);
    isNaN(f) || (document.getElementById(c).value = f.toFixed(2))
}

function sumthereLaber(a, b, c, d, e,x,y,z,flag) {
	
	if($("#allowGSTbifurcation").val() =="true" || $("#allowGSTbifurcation").val()==true){
			if( parseFloat($("#"+x).val()) >0 && flag=="false"){
				$("#"+y).val(0);
				$("#"+z).val(0);
			}
			if(parseFloat($("#"+y).val()) > 0 && flag=="true"){
				$("#"+x).val(0)
			}
			if(parseFloat($("#"+z).val()) > 0 && flag =="true"){
			    $("#"+x).val(0)
			}
	   }
    var f = document.getElementById(a).value,
        g = document.getElementById(b).value,
        h = document.getElementById(c).value,
        j = parseFloat(f) * parseFloat(g),
        k = j * h / 100,
        l = j - k;
        
        if($("#allowGSTbifurcation").val() =="true" || $("#allowGSTbifurcation").val()==true){
			var x = document.getElementById(x).value,
 				y = document.getElementById(y).value,	
 				z = document.getElementById(z).value;
		    var   tax = parseFloat(x)+ parseFloat(y) +parseFloat(z),
			    a  = l * tax/100;
				m  = l+a;
		}else{
			var i = document.getElementById(d).value;
			a = l * i / 100,
        	m = l + a;
		}
    isNaN(m) || (document.getElementById(e).value = m.toFixed(2))
}

function saveLabourDetails(ServiceEntriestaskid){
	$('#saveLabour').hide();
	var jsonObject					 		= new Object();
	jsonObject["serviceEntryId"]	 		= $('#serviceEntries_id').val();
	jsonObject["serviceEntrytaskId"]		= ServiceEntriestaskid;
	jsonObject["labername"]					= $('#labername'+ServiceEntriestaskid+'').val();
	jsonObject["laberhourscost"]			= $('#laberhourscost'+ServiceEntriestaskid+'').val();
	jsonObject["eachlabercost"]				= $('#eachlabercost'+ServiceEntriestaskid+'').val();
	jsonObject["laberdiscount"]				= $('#laberdiscount'+ServiceEntriestaskid+'').val();
	jsonObject["labertax"]					= $('#labertax'+ServiceEntriestaskid+'').val();
	jsonObject["totalLaborcost"]			= $('#totalLaborcost'+ServiceEntriestaskid+'').val();
	jsonObject["validateDoublePost"]		= true;
	jsonObject["unique-one-time-token"]		= $('#addLabourToken').val();
	jsonObject["labourIGST"]					= $("#labourIGST"+ServiceEntriestaskid+'').val();
	jsonObject["labourCGST"]					= $("#labourCGST"+ServiceEntriestaskid+'').val();
	jsonObject["labourSGST"]					= $("#labourSGST"+ServiceEntriestaskid+'').val();
	
	if(!validateLabourSave(ServiceEntriestaskid)){
		$('#saveLabour').show();
		return false;
	}
	
	var isRequestSubmitted = false;
	showLayer();
	$.ajax({
		url: "saveServiceEntryLabourDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		beforeSend: function(){
             isRequestSubmitted = true;
			 if(!isRequestSubmitted){
				 return false;
			 }
             
         },
         complete: function(){
        	 
         },
		success: function (data) {
			if(data.hasError != undefined && data.hasError){
				hideLayer();
				showMessage('errors', 'Some Error Occurred!');
				return false;
			}
			$('#addLabourToken').val(data.accessToken);
			hideLayer();
			
			if(data.labourAdded != undefined && data.labourAdded == true){
				window.location.replace("showServiceEntryDetails.in?serviceEntryId="+$('#serviceEntries_id').val()+"");
			}
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
}

function validateLabourSave(ServiceEntriestaskid){
	
	var labourIGST = parseFloat($("#labourIGST"+ServiceEntriestaskid).val());
	var labourCGST = parseFloat($("#labourCGST"+ServiceEntriestaskid).val());
	var labourSGST = parseFloat($("#labourSGST"+ServiceEntriestaskid).val());
	
	if($('#labername'+ServiceEntriestaskid+'').val() == ""){
		$('#labername'+ServiceEntriestaskid+'').focus();
		showMessage('errors', 'Please Enter Technician Name !');
		return false;
	}
	
	if(Number( $('#laberhourscost'+ServiceEntriestaskid+'').val() ) < 0 || $('#laberhourscost'+ServiceEntriestaskid+'').val() == ""){
		$('#laberhourscost'+ServiceEntriestaskid+'').focus();
		showMessage('errors', 'Please Enter Time !');
		return false;
	}
	
	if(Number( $('#eachlabercost'+ServiceEntriestaskid+'').val() ) < 0 || $('#eachlabercost'+ServiceEntriestaskid+'').val() == ""){
		$('#eachlabercost'+ServiceEntriestaskid+'').focus();
		showMessage('errors', 'Please Enter Cost !');
		return false;
	}
	
	if(Number( $('#laberdiscount'+ServiceEntriestaskid+'').val() ) < 0 || $('#laberdiscount'+ServiceEntriestaskid+'').val() == ""){
		$('#laberdiscount'+ServiceEntriestaskid+'').focus();
		showMessage('errors', 'Please Enter Discount !');
		return false;
	}
	
	
	
	
	if($("#allowGSTbifurcation").val() == "false" || $("#allowGSTbifurcation").val() == false){
		console.log("inside if ")
		if(Number( $('#labertax'+ServiceEntriestaskid+'').val() ) < 0 || $('#labertax'+ServiceEntriestaskid+'').val() == ""){
			$('#labertax'+ServiceEntriestaskid+'').focus();
			showMessage('errors', 'Please Enter Tax !');
			return false;
		}
	}else{
		console.log("inside else ")
        if ((isNaN(labourIGST) || labourIGST === 0) && (isNaN(labourCGST) || labourCGST === 0) && (isNaN(labourSGST) || labourSGST === 0)) {
			 showMessage('info', "please enter IGST OR CGST, SGST");
			 return false;
		}else{
			console.log("inside else.. ")
			if(labourCGST > 0 && (isNaN(labourSGST) || labourSGST === 0) && (isNaN(labourIGST) || labourIGST === 0)) {
				console.log("inside else if ")
			    showMessage('info',   "please enter Labour SGST");
			    return false;
			}
			if(labourSGST > 0 && (isNaN(labourCGST) || labourCGST === 0) && (isNaN(labourIGST) || labourIGST === 0)) {
			    showMessage('info',   "please enter Labour CGST");
			    return false;
			}
		}
		
	}
	
	return true;
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
	
})

function saveTaskDetails(){
	
	var jsonObject					 		= new Object();
	jsonObject["serviceEntryId"]	 		= $('#serviceEntries_id').val();
	jsonObject["vehicle"]					= $('#vehicle').val();
	jsonObject["jobtypeId"]					= $('#from').val();
	jsonObject["jobsubtypeId"]				= $('#to').val();
	jsonObject["taskRemark"]				= $('#taskRemark').val();
	
	console.log("jsonObjectTask...",jsonObject);
	
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
	
	showLayer();
	$.ajax({
		url: "saveServiceEntryNewTaskDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			
			if(data.taskAdded != undefined && data.taskAdded == true){
				window.location.replace("showServiceEntryDetails.in?serviceEntryId="+$('#serviceEntries_id').val()+"");
			}
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
}

function deletePartDetails(serviceEntriesTaskto_partid) {
	
	
	if (confirm('are you sure you want to delete ?')) {
		showLayer();
		
		var jsonObject								= new Object();
		jsonObject["serviceEntriesTaskto_partid"]	= serviceEntriesTaskto_partid;
		
		$.ajax({
			url: "deletePartDetails.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				hideLayer();
				if(data.accidentEntryApproved != undefined && data.accidentEntryApproved){
					hideLayer();
					showMessage('info', 'Cannot Delete Service Entry As Vehicle Accident Quotation is Approved !');
				}
				else if(data.partDeleted != undefined && data.partDeleted == true){
					window.location.replace("showServiceEntryDetails.in?serviceEntryId="+$('#serviceEntries_id').val()+"");
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

function deleteLabourDetails(serviceEntriesto_laberid) {
	
	
	if (confirm('are you sure you want to delete ?')) {
		showLayer();
		
		var jsonObject								= new Object();
		jsonObject["serviceEntriesto_laberid"]		= serviceEntriesto_laberid;
		
		$.ajax({
			url: "deleteLabourDetails.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				hideLayer();
				if(data.accidentEntryApproved != undefined && data.accidentEntryApproved){
					hideLayer();
					showMessage('info', 'Cannot Delete Service Entry As Vehicle Accident Quotation is Approved !');
				}
				else if(data.LabourDetailsDeleted != undefined && data.LabourDetailsDeleted == true){
					window.location.replace("showServiceEntryDetails.in?serviceEntryId="+$('#serviceEntries_id').val()+"");
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

function deleteTaskDetails(servicetaskid) {
	
	
	if (confirm('are you sure you want to delete ?')) {
		showLayer();
		
		var jsonObject					= new Object();
		jsonObject["servicetaskid"]		= servicetaskid;
		
		$.ajax({
			url: "deleteTaskDetails.do",
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
				}else if(data.taskDetailsDeleted != undefined && data.taskDetailsDeleted == true){
					hideLayer();
					window.location.replace("showServiceEntryDetails.in?serviceEntryId="+$('#serviceEntries_id').val()+"");
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
	
}

function changeStatusToInProgress(serviceEntries_id) {
	if (confirm('are you sure you want to change status to In Progress?')) {
		showLayer();
		
		var jsonObject						= new Object();
		jsonObject["serviceEntries_id"]		= serviceEntries_id;
		
		$.ajax({
			url: "changeStatusToInProgress.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				if(data.accidentEntryApproved != undefined){
					hideLayer();
					showMessage('info', data.accidentEntryApproved);
				}else if(data.statusUpdated != undefined && data.statusUpdated == true){
					hideLayer();
					window.location.replace("showServiceEntryDetails.in?serviceEntryId="+$('#serviceEntries_id').val()+"");
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
}

function completeServiceEntry(serviceEntries_id) {
	$('#status-close').hide();
	
	if (confirm('are you sure you want to Complete Service Entry?')) {
		
		 if($('#addSECompletionRemark').val() == 'true' || $('#addSECompletionRemark').val() == true){
		   		$('#completionRemark').modal('show');
		 }
		 else
		 {
			 SavecompleteServiceEntry(serviceEntries_id);
		 }
	}else{
		$('#status-close').show();
	} 
}
function showCompletebutton()
{
	$('#status-close').show();
}
function completeSEWithRemark(serviceEntries_id){
	
	SavecompleteServiceEntry(serviceEntries_id);
}

function SavecompleteServiceEntry(serviceEntries_id)
{
	var jsonObject						= new Object();
	jsonObject["serviceEntries_id"]		= serviceEntries_id;
	jsonObject["validateDoublePost"] 	 	=  true;
	jsonObject["unique-one-time-token"] 	=  $('#completeSEToken').val();
	jsonObject["SEremark"]				    = $('#woRemark').val();
	jsonObject['driverId']					= $('#driver_id').val();
	showLayer();
	$.ajax({
		url: "completeServiceEntry.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.completeStatus != undefined && data.completeStatus == true){
				hideLayer();
				window.location.replace("showCompleteServiceEntryDetails.in?serviceEntryId="+$('#serviceEntries_id').val()+"");
			}else if(data.quotationNotApproved != undefined && data.quotationNotApproved == true){
				$('#completeSEToken').val(data.completeSEToken);
				hideLayer();
				showMessage('info',' Vehicle Accident quotation not approved !');
			}
		},
		error: function (e) {
			$('#completeSEToken').val(data.completeSEToken);
			console.log("Error : " , e);
			showMessage('errors', 'Some Error Occurred!');
			hideLayer();
		}
	});
}

function saveRoundOfDetails(serviceEntries_id) {
		
	var jsonObject						= new Object();
	jsonObject["serviceEntries_id"]		= serviceEntries_id;
	jsonObject["totalRoundCost"]		= $('#totalRoundCost').val();
	
	showLayer();
	$.ajax({
		url: "saveRoundOfDetails.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			if(data.roundOfCompleted != undefined && data.roundOfCompleted == true){
				hideLayer();
				window.location.replace("showServiceEntryDetails.in?serviceEntryId="+$('#serviceEntries_id').val()+"");
			}
			
		},
		error: function (e) {
			console.log("Error : " , e);
			showMessage('errors', 'Some Error Occurred!');
			hideLayer();
		}
	});
	
	
}

function AddInvoiceDeatils(){
	
	var jsonObject			= new Object();
	jsonObject["seId"] 		=  $('#seId').val();
	
	showLayer();
	$.ajax({
		url: "getInvoiceDeatils",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			$('#invoicestartDate').val(data.ServiceEntriesDto.invoiceDate);
			$('#invoiceNumber').val(data.ServiceEntriesDto.invoiceNumber);
			$('#servicepaiddate').val(data.ServiceEntriesDto.service_paiddate);
			
			$('#invoiceDetails').modal('show');
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
}

function saveServEntInvDetails(){
	
	var jsonObject			= new Object();
	jsonObject["invoicestartDate"] 	 		 =  $('#invoicestartDate').val();
	jsonObject["invoiceNumber"] 		  	 =  $('#invoiceNumber').val();
	jsonObject["servicepaiddate"] 		 	 =  $('#servicepaiddate').val();
	jsonObject["seId"] 		 	 			 =  $('#seId').val();
	
	
	if($('#invoicestartDate').val() == ""){
		showMessage('info', 'Please Select Invoice Date !');
		$('#invoicestartDate').focus();
		return false;
	}
	
	if(Number($('#invoiceNumber').val()) <= 0){
		showMessage('info', 'Please Select Invoice Number !');
		$('#invoiceNumber').focus();
		return false;
	}
	
	if($('#servicepaiddate').val() == ""){
		showMessage('info', 'Please Select Paid Date !');
		$('#servicepaiddate').focus();
		return false;
	}
	
	showLayer();
	$.ajax({
             url: "saveServEntInvDetails",
             type: "POST",
             dataType: 'json',
             data: jsonObject,
             success: function (data) {
            	 
            	 console.log("ola....",$('#seId').val());
            	 
            	 window.location.replace("showServiceEntryDetails?serviceEntryId="+$('#seId').val()+"");
            	 showMessage('success', 'Invoice Deatils Updated Successfully !')
            	 hideLayer();
            	 $('#completeInvDeatils').val('true');
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	});
	
}

function valInvDet(){
	showMessage('errors', 'Service Entry cannot be completed. Please update Invoice details first !');
}

$(document).ready(function () {
	
    $("#btnSubmit").on('keypress click', function (e) {
        e.preventDefault(); 	//stop submit the form, we will post it manually.
        uploadServiceEntryDocument();
    });

});

function uploadServiceEntryDocument(){
	var jsonObject						= new Object();
	jsonObject["serviceEntries_id"]		= $('#seId').val();
	
	var form = $('#fileUploadForm')[0];
	var data = new FormData(form);

	data.append("serviceEntryData", JSON.stringify(jsonObject)); 
	
	$.ajax({
		type: "POST",
		enctype: 'multipart/form-data',
		url: "uploadServiceEntryDocument",
		data: data,
		processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
		success: function (data) {
			
			if(data.UploadSuccess != undefined && data.UploadSuccess == true){
				hideLayer();
				window.location.replace("showServiceEntryDetails.in?serviceEntryId="+$('#serviceEntries_id').val()+"");
			}
			
			},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
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

