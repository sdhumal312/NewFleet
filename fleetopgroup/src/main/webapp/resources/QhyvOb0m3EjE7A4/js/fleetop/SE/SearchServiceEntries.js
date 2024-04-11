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
			console.log('data : ', data);
			
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

var data = [];
$(function() {
	function a(a, b) {
		$("#dateRange").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
	}
	a(moment().subtract(1, "days"), moment()), $("#dateRange").daterangepicker( {
		maxDate: new Date(),
		format : 'DD-MM-YYYY',
		ranges: {
            Today: [moment(), moment()],
            Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")],
            "Last 7 Days": [moment().subtract(6, "days"), moment()],
            "This Month": [moment().startOf("month"), moment().endOf("month")],
            "Last Month": [moment().subtract(1, "months").startOf("month"), moment().subtract(1, "months").endOf("month")]
        }
	}
	, a)
}
);

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
    }), $("#selectVendor").select2({
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
                            text: a.driver_empnumber + " - " + a.driver_firstname+" "+a.driver_Lastname+" - "+a.driver_fathername,
                            slug: a.slug,
                            id: a.driver_id
                        }
                    })
                }
            }
        }
    })
})

$(document).ready(
		function($) {
			$('#btn-save').click(function(e) {
				e.preventDefault();

				showLayer();
				var jsonObject			= new Object();

				jsonObject["vehicle_vid"] 			=  $('#vehicle_vid').val();				
				jsonObject["SelectDriverName"] 	  	=  $('#SelectDriverName').val();
				jsonObject["selectVendor"] 			=  $('#selectVendor').val();
				jsonObject["invoiceDate"] 			=  $('#invoiceDate').val();
				jsonObject["renPT_option"] 			=  $('#renPT_option').val();
				jsonObject["dateRange"] 			=  $('#dateRange').val();
				
				if($('#dateRange').val() == undefined || $('#dateRange').val() == null || $('#dateRange').val().trim() == ''){					
					showMessage('errors', 'Please Select Date Range !');
					hideLayer();
					return false;
				}
				
				$.ajax({
					
					url: "searchServiceEntriesList.do",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						setServiceEntriesList(data);
						hideLayer();
					},
					error: function (e) {
						showMessage('errors', 'Some error occured!')
						hideLayer();
					}
				});

			});

});

function setServiceEntriesList(data) {
	
	$("#VendorPaymentTable").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th4		= $('<th class="fit ar">');
	var th7		= $('<th class="fit ar">');
	var th8		= $('<th class="fit ar">');
	var th9		= $('<th class="fit ar">');
	var th10	= $('<th class="fit ar">');
	var th11	= $('<th class="fit ar">');
	var th12	= $('<th>');
	var th13	= $('<th>');

	tr1.append(th1.append("SE-No"));
	tr1.append(th2.append("Vehicle"));
	tr1.append(th4.append("Vendor"));
	tr1.append(th7.append("Invoice Number"));
	tr1.append(th8.append("Job Number"));
	tr1.append(th9.append("Cost"));
	tr1.append(th10.append("Paid By"));
	tr1.append(th11.append("Last modified By"));
	tr1.append(th12.append("Document"));
	tr1.append(th13.append("Action"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.ServiceEntries != undefined && data.ServiceEntries.length > 0) {
		
		var ServiceEntries = data.ServiceEntries;
	
		for(var i = 0; i < ServiceEntries.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td class="fit ar">');
			var td5		= $('<td class="fit ar">');
			var td6		= $('<td class="fit ar">');
			var td7		= $('<td class="fit ar">');
			var td8		= $('<td class="fit ar">');
			var td9		= $('<td class="fit ar">');
			var td10	= $('<td class="fit ar">');
			var td11	= $('<td class="fit ar">');
			var td12	= $('<td>');
			var td13	= $('<td>');
			
			tr1.append(td1.append('<a href="showServiceEntryDetails?serviceEntryId='+ServiceEntries[i].serviceEntries_id+'" target="_blank">SE-'+ServiceEntries[i].serviceEntries_Number+'</a>'));
			
			var curl = "VehicleServiceEntriesDetails.in?vid="+ServiceEntries[i].vid
			tr1.append(td2.append('<a target="_blank" href="' + curl + '">'+ServiceEntries[i].vehicle_registration+'</a><br>'));
			
			tr1.append(td4.append(ServiceEntries[i].vendor_name));
			tr1.append(td7.append(ServiceEntries[i].invoiceNumber));
			tr1.append(td8.append(ServiceEntries[i].jobNumber));
			tr1.append(td9.append((ServiceEntries[i].totalserviceROUND_cost).toFixed(2)));	
			tr1.append(td10.append(ServiceEntries[i].service_paidby));	
			tr1.append(td11.append(ServiceEntries[i].lastModifiedBy));
			
			if(ServiceEntries[i].service_document == true){
				tr1.append(td12.append('<a href="/download/serviceDocument/'+ServiceEntries[i].service_document_id+'.in"> <span class="fa fa-download"> Doc</span> </a>'));
			} else {
				tr1.append(td12.append(" "));
			}
				
			
			if(ServiceEntries[i].serviceEntries_statusId != 3){
				var curl = "serviceEntriesEditAjax.in?SEID="+ServiceEntries[i].serviceEntries_id
				tr1.append(td13.append(
				'<div class="btn-group">'
				+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
				+'<ul class="dropdown-menu pull-right">'
				+'<li><a href="'+curl+'"><i class="fa fa-edit"></i> Edit</a></li>'
				+'<li><a href="#" class="confirmation" onclick="deleteServiceEntry('+ServiceEntries[i].serviceEntries_id+')"><span class="fa fa-trash"></span> Delete</a></li>'
				+'</ul>'
				+'</div>'
				));
			} else {
				tr1.append(td13.append(
						'<div class="btn-group">'
						+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
						+'<ul class="dropdown-menu pull-right">'
						+'<li><span class="label label-warning"><i class="fa fa-dot-circle-o"></i>SERVICE COMPLETED</span></li>'
						+'</ul>'
						+'</div>'
						));
			}

			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#VendorPaymentTable").append(thead);
	$("#VendorPaymentTable").append(tbody);
}

function deleteServiceEntry(serviceEntries_id){
	
	var jsonObject						= new Object();
	jsonObject["serviceEntries_id"]		= serviceEntries_id;
	
	showLayer();
	$.ajax({
		url: "deleteServiceEntryDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			if(data.serviceEntryCannotBeDeleted != undefined && data.serviceEntryCannotBeDeleted == true){
				hideLayer();
				showMessage('info', 'Cannot Delete Service Entry Please Remove Task Details!');
			}else if(data.accidentEntryApproved != undefined && data.accidentEntryApproved){
				hideLayer();
				showMessage('info', 'Cannot Delete Service Entry As Vehicle Accident Quotation is Approved !');
			}else{
				hideLayer();
				window.location.replace("viewServiceEntries");
			}
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}