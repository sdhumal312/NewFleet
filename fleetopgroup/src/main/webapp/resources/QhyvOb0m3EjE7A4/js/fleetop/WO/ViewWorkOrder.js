$(document).ready(function() {
	showList(1,1);
});

function showList(status, pagenumber){
	
	var jsonObject					= new Object();
	jsonObject["status"]			= status;
	jsonObject["pagenumber"]		= pagenumber;
	
	if(status == 1){
		$('#open').addClass('active');
		$('#inProcess').removeClass('active');
		$('#onHold').removeClass('active');
		$('#completed').removeClass('active');
	} else if (status == 2){
		$('#inProcess').addClass('active');
		$('#open').removeClass('active');
		$('#onHold').removeClass('active');
		$('#completed').removeClass('active');
	} else if (status == 3){
		$('#onHold').addClass('active');
		$('#open').removeClass('active');
		$('#inProcess').removeClass('active');
		$('#completed').removeClass('active');
	} else {
		$('#completed').addClass('active');
		$('#inProcess').removeClass('active');
		$('#onHold').removeClass('active');
		$('#open').removeClass('active');
	}
	
	showLayer();
	$.ajax({
		url: "getWorkOrderList",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			console.log('WorkOrderList : ', data);
			setWorkOrderList(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function getWOStatus(statusId){
  var status = '';
  if(statusId == 1){
  	status = 'OPEN';
  }else if(statusId == 2){
  	status = 'IN PROCESS';
  }else if(statusId == 3){
  	status = 'ON HOLD';
  }else if(statusId == 4){
  	status = 'COMPLETED';
  }
  
  return status;
}

function setWorkOrderList(data) {
	var showSubLocation = $("#showSubLocation").val();
	$("#woStatus").text(getWOStatus(data.status));
	$("#count").text(data.WorkOrderCount);
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
	var th12	= $('<th class="fit ar">');
	var th13	= $('<th class="fit ar">');

	tr1.append(th1.append("WO-No"));
	tr1.append(th2.append("Start Date"));
	tr1.append(th4.append("Due Date"));
	tr1.append(th7.append("Vehicle"));
	tr1.append(th8.append("Assigned To"));
	tr1.append(th9.append("Location"));
	if($('#showDocumentColumn').val() == true || $('#showDocumentColumn').val() == 'true'){
		tr1.append(th10.append("Document"));
	}
	if($('#showTaskInList').val() == true || $('#showTaskInList').val() == 'true'){
	tr1.append(th12.append("Show Tasks"))
	}
//	if($('#showIssueSummary').val() == true || $('#showIssueSummary').val() == 'true'){
//	tr1.append(th13.append("Issue Summary"))
//	}
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
			var td12	= $('<td class="fit ar">');
			var td13	= $('<td >');
			
			tr1.append(td1.append('<a href="showWorkOrder?woId='+WorkOrder[i].workorders_id+'" target="_blank">WO-'+WorkOrder[i].workorders_Number+'</a>'));
			tr1.append(td2.append(WorkOrder[i].start_date));
			tr1.append(td4.append(WorkOrder[i].due_date));
			
			var curl = 'VehicleWorkOrderDetails/'+WorkOrder[i].vehicle_vid+'/1.in'
			tr1.append(td7.append('<a target="_blank" href="' + curl + '">'+WorkOrder[i].vehicle_registration+'</a><br>'));
			
			tr1.append(td8.append(WorkOrder[i].assignee));
			if((showSubLocation == true || showSubLocation == 'true' ) && (WorkOrder[i].subLocationId > 0)){
				tr1.append(td9.append(WorkOrder[i].workorders_location + "-" + WorkOrder[i].subLocation));
			}else{
				tr1.append(td9.append(WorkOrder[i].workorders_location));
			}
			if($('#showDocumentColumn').val() == true || $('#showDocumentColumn').val() == 'true'){
			if(WorkOrder[i].workorders_document == true){
				tr1.append(td10.append('<a href="download/workorderDocument/'+WorkOrder[i].workorders_id+'.in"> <span class="fa fa-download"> Doc</span> </a>'));
			} else {
				tr1.append(td10.append(" "));
			}
			}
			
			if($('#showTaskInList').val() == true || $('#showTaskInList').val() == 'true'){
				tr1.append(td12.append('<a href="#" onclick=showTasks('+WorkOrder[i].workorders_id+')> View </a>'));
			}
			
			
//			if($('#showIssueSummary').val() == true || $('#showIssueSummary').val() == 'true'){
//				
//				var summary = WorkOrder[i].issueSummary;
//				
//				if(summary != null && summary != ' '){
//					summary = summary.substring(0,20)+'...';
//				}
//				var summaryNullCheck = " ";
//				if(WorkOrder[i].issueSummary == null || WorkOrder[i].issueSummary == 'null'){
//					summaryNullCheck = " ";
//				}else{
//					summaryNullCheck='<a href="#" style="color:black" data-toggle="tooltip" data-placement="top" title="'+WorkOrder[i].issueSummary+'" >'+summary;
//				}
//				tr1.append(td13.append(summaryNullCheck));
//				
//			}
			
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
	
		$('#openWOCount').html(data.openWOCount);
		$('#inProressWOCount').html(data.inProressWOCount);
		$('#onHoldWOCount').html(data.onHoldWOCount);
		$('#completedWOCount').html(data.completedWOCount);
		
	
	$("#VendorPaymentTable").append(thead);
	$("#VendorPaymentTable").append(tbody);
	
	$("#navigationBar").empty();

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;&lt;</a></li>');		
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="showList('+data.SelectStatus+', 1)">&lt;&lt;</a></li>');		
	}

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="showList('+data.SelectStatus+', '+(data.currentIndex - 1)+')">&lt;</a></li>');
	}
	
	for (i = data.beginIndex; i <= data.endIndex; i++) {
	    if(i == data.currentIndex) {
	    	$("#navigationBar").append('<li class="active"><a href="#" onclick="showList('+data.SelectStatus+', '+i+')">'+i+'</a></li>');	    	
	    } else {
	    	$("#navigationBar").append('<li><a href="#" onclick="showList('+data.SelectStatus+', '+i+')">'+i+'</a></li>');	    	
	    }
	} 
	
	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="showList('+data.SelectStatus+', '+(data.currentIndex + 1)+')">&gt;</a></li>');			
		}
	}

	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="showList('+data.SelectStatus+', '+(data.deploymentLog.totalPages)+')">&gt;&gt;</a></li>');			
		}
	}
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
			console.log('data : ', data);
			
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

function searchWOByDifferentFilter(){
	
	var jsonObject					= new Object();
	jsonObject["searchTerm"]		= $("#searchByDifferentFilter").val();
	
	if( $("#searchByDifferentFilter").val() == ""){
		$('#searchByDifferentFilter').focus();
		showMessage('errors', 'Please Enter Valid Details !');
		return false;
	}
	
	showLayer();
	$.ajax({
		url: "searchWoByDifferentParameters",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			console.log('dataDate : ', data);
			setWoByDifferentParameters(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setWoByDifferentParameters(data) {
	
	$("#woDetails").hide();
	$("#tabsHeading").hide();
	$("#tabsData").hide();
	$(".filterByDifferentData").removeClass('hide');
	
	$("#VendorPaymentTable1").empty();
	
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
	if(data.WorkOrderListFound != undefined && data.WorkOrderListFound == true && data.WorkOrder != undefined && data.WorkOrder.length > 0) {
		
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
	
	$("#VendorPaymentTable1").append(thead);
	$("#VendorPaymentTable1").append(tbody);
	
}

function showTasks(workOrderId){
	
	var jsonObject = new Object();
	jsonObject["workOrderId"] = workOrderId;
	$.ajax({
		
		url : "getTaskDetails",
		type : "POST",
		dataType : 'json',
		data : jsonObject,
		success : function(data){
			$('#taskDetails').modal('show');
			setTaskDeatails(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
		
		
		
	})
	
	
}


function setTaskDeatails(data){
	$('#taskTable').empty();
	var thead = $('<thead>');
	var tr1 = $('<tr>');
	
	var th1 = 	$('<th>');
	var th2 =	$('<th>');
	var th3 =	$('<th>');
	var th4 =	$('<th>');
	var th5 =	$('<th>');
	
	tr1.append(th1.append("NO."));
	tr1.append(th2.append("Service Reminder"));
	tr1.append(th4.append("Issue"));
	tr1.append(th5.append("Summary"));
	tr1.append(th3.append("Task"));
	thead.append(tr1);
	
	var num = 1;
	var tbody = $('<tbody>');
	if(data.WorkOrdersTasks != undefined  && data.WorkOrdersTasks.length > 0) {
		var tasks= data.WorkOrdersTasks;
	for(var i = 0; i < tasks.length; i++) {
		var tr1 = $('<tr class="ng-scope">');
		var tr2 = $('<tr class="ng-scope">');
		var tr3 = $('<tr class="ng-scope">');
		var tr4 = $('<tr>');
		var td1		= $('<td>');
		var td2		= $('<td>');
		var td3		= $('<td>');
		var td4		= $('<td>');
		var td5		= $('<td>');
		var td6		= $('<td>');
		var td7		= $('<td>');
		
		tr1.append(td1.append(num));
		var serviceLink =" " ;
		if(tasks[i].service_NumberStr != null){
			serviceLink ='<a href="ShowService.in?service_id='+tasks[i].service_id+'" target="_blank" > '+tasks[i].service_NumberStr+' ';
		}else{
			serviceLink ='-';
		}
		tr1.append(td3.append(serviceLink));
		var issueLink ="-";
		if(tasks[i].issueIds != null && tasks[i].issueIds > 0){
			issueLink='<a href="showIssues.in?Id='+tasks[i].issueIdsEncry+'" target="_Blank">'+'I-'+tasks[i].issueNumber+'</a>';
		}
		tr1.append(td4.append(issueLink));
			var summary = tasks[i].issueSummary;
			
			if(summary != null && summary != ' '){
				summary = summary.substring(0,20)+'...';
			}
			var summaryNullCheck = " ";
			if(tasks[i].issueSummary == null || tasks[i].issueSummary == 'null'){
				summaryNullCheck = " ";
			}else{
				summaryNullCheck='<a href="#" style="color:black" data-toggle="tooltip" data-placement="top" title="'+tasks[i].issueSummary+'" >'+summary;
			}
		
		tr1.append(td5.append(summaryNullCheck));
		tr1.append(td2.append(tasks[i].job_typetask+' - '+tasks[i].job_subtypetask));
		
		
		tbody.append(tr1);
		num++;
		
	}
	}else{
		showMessage('info','No record found !')
	}
	$('#taskTable').append(thead);
	$('#taskTable').append(tbody);
	
	
}

function showPrintIssueModule(){
$('#issue').select2();
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
    });
    $('#issuePrintModal').modal('show');
}

function checkIssueDetail(){
	var jsonObject				= new Object();
	jsonObject["vid"] 	 		=  $('#select3').val();
	jsonObject["companyId"] 	=  $('#companyId').val();

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
				//$('#issue').val(data.issueDetails[0].issues_ID);
			}
		},
		error: function (e) {
			showMessage('errors', 'Some error occured!')
			hideLayer();
		}
	});
}
function printJobCard(){
	if($('#select3').val() <= 0){
		showMessage('info','Select Vehicle to process further');
		return false;
	}
	let issueIds=$('#issue').val();
	if(issueIds == null){
		showMessage('info','Select Issues to process further');
		return false;
	}
	window.open("showMultiIssuePrint?issueIds="+issueIds.toString(),'_blank');
}
function showPrintServiceRemiderModule()
{
    $("#serviceReminderVehicle").select2({
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
    });
    $('#serviceReminderPrintModal').modal('show');
}
$("#serviceReminderVehicle").on('change', function(){
	
	$.getJSON("getVehicleServiceReminderList.in", {
	        vehicleID: $("#serviceReminderVehicle").val(),
	        ajax: "true"
	    },  function(a) {
	    	 for (var b = a.length - 1, c = "", d = a.length, e = 0; d > e; e++) b != e ? (c += '{"id":"' + a[e].service_id + '","date":"' + a[e].servceDate + '","text":"' + a[e].service_NumberStr + '" }', c += ",") : c += '{"id":"' + a[e].service_id + '","text":"'+ a[e].service_NumberStr + '" }';
	         var f = "[" + c + "]",
	             g = JSON.parse(f);
	         $("#ServiceReminderInput").select2({
	        	 multiple: !0,
	        	 allowClear: !0,
	             data: g
	         })
	    })
})
$(document).ready(function() {
	$("#ServiceReminderInput").select2({
		multiple: !0,
		allowClear: !0,
        data : ""
    })
})

function printJobCard2(){
	if($('#serviceReminderVehicle').val() <= 0){
		showMessage('info','Select Vehicle to process further');
		return false;
	}
	let vehicleId=$('#serviceReminderVehicle').val();
	let serviceReminderIds=$('#ServiceReminderInput').val();
	if(serviceReminderIds == null){
		showMessage('info','Select Service Reminder to process further');
		return false;
	}
	window.open("showMultiServiceReminderPrint?serviceProgramIds="+serviceReminderIds.toString() + "&vid="+vehicleId,'_blank');
}


