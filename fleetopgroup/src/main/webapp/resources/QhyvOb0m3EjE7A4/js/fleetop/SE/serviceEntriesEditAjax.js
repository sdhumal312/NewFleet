
$(document).ready(function() {
	if($("#issueId").val() > 0 && $("#issueId").val() > 0){
		console.log("issue id",$("#issueId").val())
		$("#SEVehicle").hide();
		$("#issueVehicle").show();
		$('#vehicle_vid').val($("#Ovid").val());
		vehicleOnChange();
    }
});
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
                            text: a.driver_empnumber + " - " + a.driver_firstname+" "+ a.driver_Lastname +" - "+ a.driver_fathername,
                            slug: a.slug,
                            id: a.driver_id
                        }
                    })
                }
            }
        }
    }), 
    
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
    }),$("#issue").select2({
		ajax : {
			url:"getVehicleWiseIssueDropdown.in?Action=FuncionarioSelect2", 
			dataType:"json", 
			type:"GET", 
			contentType:"application/json", 
			data:function() {
				return {
					term: $("#select3").val()
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
	if($("#editIssueId").val() != undefined || $("#editIssueId").val() != null){// will show only on edit
		$('#issue').select2('data', {
			id : $("#editIssueId").val(),
			text : $("#editIssueNumber").val()+"-"+$("#editIssueSummary").val()
		});
	}
})

function visibility(a, b) {
    var c = document.getElementById(a),
        d = document.getElementById(b);
    "block" == c.style.display ? (c.style.display = "none", d.style.display = "block") : (c.style.display = "block", d.style.display = "none")
}

var specialKeys = new Array;
specialKeys.push(8), specialKeys.push(9), specialKeys.push(46), specialKeys.push(36), specialKeys.push(35), specialKeys.push(37), specialKeys.push(39), $(document).ready(function() {
    $("#vendorEnter").hide(), $("#driverEnter").hide()
})

 $("#vehicle_vid").change(function() {
	 vehicleOnChange();
 });

    function vehicleOnChange() {
        $.getJSON("getVehicleOdoMerete.in", {
            vehicleID: $('#vehicle_vid').val(),
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
            	//$('#grpodoMeter').hide();
            	//$("#vehicle_Odometer").attr('readonly','readonly')

            }
        })
    }

function validateServiceEntry(){
	
	if(Number($('#vehicle_vid').val()) <= 0){
		$("#vehicle_vid").select2('focus');
		showMessage('errors', 'Please Select Vehicle !');
		return false;
	}
	
	if($("#tallyConfig").val() == true || $("#tallyConfig").val() == 'true' ){
		if($("#tallyCompanyId").val() == "" || $("#tallyCompanyId").val() == undefined || Number($('#tallyCompanyId').val()) <= 0){
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
	
	if(Number($('#selectVendor').val()) <= 0){
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
	
	if(($('#validatePaidDateForServEntOnCash').val() == 'true' || $('#validatePaidDateForServEntOnCash').val() == true)  && ($('#serviceEdit_option').val() == 1 || $('#serviceEdit_option').val() == '1')){
		if($('#servicepaiddate').val() <= 0){					
			showMessage('errors', 'Please Select Paid Date!');
			hideLayer();
			return false;
		}
	}
	

	return true;
}


function updateServiceEntry(){
	
	if(!validateServiceEntry()){
		return false;
	}
	
	if(Number($('#accidentId').val()) > 0){
		if($('#oldVehicleId').val() !=  $('#vehicle_vid').val()){
			showMessage('info', 'You cannot change vehicle number as this entry related to vehicle accident !');
			return false;
		}
		if($('#Did').val() !=  $('#SelectDriverName').val()){
			showMessage('info', 'You cannot change Driver as this entry related to vehicle accident !');
			return false;
		}
		
	}
	
	var jsonObject							= new Object();
	
	jsonObject["serviceEntryId"] 	  		=  $('#serviceEntryId').val();
	jsonObject["serviceNumber"] 	  		=  $('#serviceNumber').val();
	jsonObject["vid"] 	  					=  $('#vehicle_vid').val();
	jsonObject["driverId"] 					=  $('#SelectDriverName').val();
	jsonObject["vehicleOdometerId"] 	  	=  $('#vehicle_Odometer').val();    
	jsonObject["gpsOdometerId"] 	  		=  $('#gpsOdometer').val();			
	jsonObject["vendorId"] 	  				=  $('#selectVendor').val(); 		
	jsonObject["enterVendorName"] 	  		=  $('#enterVendorName').val();    
	jsonObject["enterVendorLocation"] 	  	=  $('#enterVendorLocation').val();    
	jsonObject["invoiceNumber"] 	  		=  $('#invoiceNumber').val();
	jsonObject["invoiceDate"] 	  			=  $('#invoicestartDate').val();    	
	jsonObject["tripSheetId"] 	  			=  $('#tripSheetId').val();			
	jsonObject["jobNumber"] 	  			=  $('#workorders_location').val(); 
	jsonObject["modeOfPaymentId"]	  		=  $('#serviceEdit_option').val();		
	jsonObject["service_PayNumber"]	  		=  $('#service_PayNumber').val();				
	jsonObject["paidDate"]	  				=  $('#servicepaiddate').val();	    	
	jsonObject["service_paidbyId"]	  		=  $('#service_paidbyId').val();
	jsonObject["tallyCompanyId"]	  		=  $('#tallyCompanyId').val();
	jsonObject["tallyExpenseId"]	  		=  $('#tallyExpenseId').val();
	jsonObject["workshopInvoiceAmountId"]	=  $('#workshopInvoiceAmountId').val();
	jsonObject["accidentId"]				=  $('#accidentId').val();
	
	
	$.ajax({
		url: "updateServiceEntryDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.duplicateInvoiceNumber != undefined && data.duplicateInvoiceNumber){
				showMessage('info', 'Duplicate Invoice Number Found');
				hideLayer();
			}	
			if(data.validateDuplicateEditActionSE != undefined && data.validateDuplicateEditActionSE){
				showMessage('info', 'Duplicate Invoice Details Entered');
				hideLayer();
			}
			if(data.completed != undefined){
				showMessage('info', 'ServiceEntry Already Completed , Cannot update !');
				hideLayer();
			}else if(data.changeAccidentDetails != undefined && data.changeAccidentDetails){
				showMessage('info', 'You cannot change vehicle number/ Driver as this entry related to vehicle accident !');
				hideLayer();
			}else if(data.serviceEntryUpdated != undefined && data.serviceEntryUpdated == true){
				hideLayer();
				window.location.replace("viewServiceEntries");
				showMessage('success', 'ServiceEntry Updated!')
			}
			
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			hideLayer();
		}
	});
	
}
