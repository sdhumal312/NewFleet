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
    $("#RenewalSelectVehicle").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getVehicleListFuel.in?Action=FuncionarioSelect2",
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
    }), 
    
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
})

$(document).ready(
		function($) {
			$('#btn-save').click(function(e) {
				e.preventDefault();
				
				var jsonObject			= new Object();

				jsonObject["vid"] 					=  $('#RenewalSelectVehicle').val();				
				jsonObject["assign"] 	  			=  $('#subscribe').val();
				jsonObject["location"] 				=  $('#location').val();
				jsonObject["priority"] 				=  $('#priority').val();
				jsonObject["dateRange"] 			=  $('#dateRange').val();
				
				
				if($('#dateRange').val() == undefined || $('#dateRange').val() == null || $('#dateRange').val().trim() == ''){					
					showMessage('errors', 'Please Select Date Range !');
					hideLayer();
					return false;
				}
				
				
				showLayer();
				$.ajax({
					
					url: "getWorkOrderByData.do",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						
						setWorkOrderByData(data);
						hideLayer();
					},
					error: function (e) {
						showMessage('errors', 'Some error occured!')
						hideLayer();
					}
				});

			});

});

function setWorkOrderByData(data) {
	
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

	tr1.append(th1.append("WO-No"));
	tr1.append(th2.append("Start Date"));
	tr1.append(th4.append("Due Date"));
	tr1.append(th7.append("Vehicle"));
	tr1.append(th8.append("Assigned To"));
	tr1.append(th9.append("Location"));
	tr1.append(th10.append("Document"));
	tr1.append(th11.append("Action"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.WorkOrder != undefined && data.WorkOrder.length > 0) {
		
		var WorkOrder = data.WorkOrder;
	
		for(var i = 0; i < WorkOrder.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td4		= $('<td class="fit ar">');
			var td7		= $('<td class="fit ar">');
			var td8		= $('<td class="fit ar">');
			var td9		= $('<td class="fit ar">');
			var td10	= $('<td class="fit ar">');
			var td11	= $('<td class="fit ar">');
			
			tr1.append(td1.append('<a href="showWorkOrder?woId='+WorkOrder[i].workorders_id+'" target="_blank">WO-'+WorkOrder[i].workorders_Number+'</a>'));
			tr1.append(td2.append(WorkOrder[i].start_date));
			tr1.append(td4.append(WorkOrder[i].due_date));
			
			var curl = 'VehicleWorkOrderDetails/'+WorkOrder[i].vehicle_vid+'/1.in'
			tr1.append(td7.append('<a target="_blank" href="' + curl + '">'+WorkOrder[i].vehicle_registration+'</a><br>'));
			
			tr1.append(td8.append(WorkOrder[i].assignee));
			tr1.append(td9.append(WorkOrder[i].workorders_location));	
			
			if(WorkOrder[i].workorders_document == true){
				tr1.append(td10.append('<a href="download/workorderDocument/'+WorkOrder[i].workorders_id+'.in"> <span class="fa fa-download"> Doc</span> </a>'));
			} else {
				tr1.append(td10.append(" "));
			}
			
			if(WorkOrder[i].workorders_status == 'OPEN'){
				var curl = "workOrderEdit.in?woId="+WorkOrder[i].workorders_id
				tr1.append(td11.append(
						'<div class="btn-group">'
						+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
						+'<ul class="dropdown-menu pull-right">'
						+'<li><a href="'+curl+'"><i class="fa fa-edit"></i> Edit</a></li>'
						+'<li><a href="#" class="confirmation" onclick="deleteWorkOrder('+WorkOrder[i].workorders_id+', '+WorkOrder[i].vehicle_vid+')"><span class="fa fa-trash"></span> Delete</a></li>'
						+'</ul>'
						+'</div>'
						));
			} else {
				tr1.append(td11.append(
						'<div class="btn-group">'
						+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
						+'<ul class="dropdown-menu pull-right">'
						+'<li><a href="#" class="confirmation" onclick="deleteWorkOrder('+WorkOrder[i].workorders_id+', '+WorkOrder[i].vehicle_vid+')"><span class="fa fa-trash"></span> Delete</a></li>'
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

function deleteWorkOrder(workOrderId, vid){
	
	var jsonObject					= new Object();
	jsonObject["workOrderId"]		= workOrderId;
	jsonObject["vid"]				= vid;
	
	showLayer();
	$.ajax({
		url: "deleteWorkOrderDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			if(data.woCannotBeDeleted != undefined && data.woCannotBeDeleted == true){
				hideLayer();
				showMessage('info', 'Cannot Delete Work Order Please Remove Task Details!');
			}else if(data.woNotFound != undefined){
				hideLayer();
				showMessage('info', 'WorkOrder not found to delete!');
			}
			
			if(data.woDeleted != undefined && data.woDeleted == true){
				hideLayer();
				window.location.replace("viewWorkOrder");
			}
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

