$(document).ready(function() {
	showList(0,1,0);
	$('[data-toggle="tooltip"]').tooltip()
});

function showList(status, pagenumber, vehicleStatus){
	
	console.log("status...",status);
	console.log("pagenumber...",pagenumber);
	
	if(status == 0 && vehicleStatus == 0){
		$('#all').addClass('active');
		$('#overDue').removeClass('active');
		$('#dueSoon').removeClass('active');
		$("#allClr").css("background-color",  "#87CEFA");
		$("#dueSoonClr").css("background-color",  "");
		$("#overDueClr").css("background-color",  "");
		$('#dateWiseList').addClass('hide');
		$('#inactiveDueSoon').removeClass('active');
		$('#inactiveOverdue').removeClass('active');
		$("#inactiveOverdueClr").css("background-color",  "");
		$("#inactiveOueSoonClr").css("background-color",  "");
		openList(status, pagenumber,vehicleStatus);
	} else if(status == 7 && vehicleStatus == 1){
		$('#overDue').addClass('active');
		$("#allClr").css("background-color",  "");
		$("#overDueClr").css("background-color",  "#ffcccb");
		$("#dueSoonClr").css("background-color",  "");
		$('#all').removeClass('active');
		$('#dueSoon').removeClass('active');
		$('#dateWiseList').addClass('hide');
		$('#inactiveDueSoon').removeClass('active');
		$('#inactiveOverdue').removeClass('active');
		$("#inactiveOverdueClr").css("background-color",  "");
		$("#inactiveOueSoonClr").css("background-color",  "");
		overDueList(status, pagenumber,vehicleStatus);
	} else if(status == 8 && vehicleStatus == 1){
		$('#dueSoon').addClass('active');
		$("#allClr").css("background-color",  "");
		$("#dueSoonClr").css("background-color",  "#fed8b1");
		$("#overDueClr").css("background-color",  "");
		$('#overDue').removeClass('active');
		$('#all').removeClass('active');
		$('#dateWiseList').addClass('hide');
		$("#inactiveOverdueClr").css("background-color",  "");
		$("#inactiveOueSoonClr").css("background-color",  "");
		dueSoonList(status, pagenumber,vehicleStatus);
	}else if(status == 7 && vehicleStatus == 2){

		$('#inactiveOverdue').addClass('active');
		$('#dueSoon').removeClass('active');
		$("#allClr").css("background-color",  "");
		$("#dueSoonClr").css("background-color",  "");
		$("#overDueClr").css("background-color",  "");
		$('#overDue').removeClass('active');
		$('#all').removeClass('active');
		$('#dateWiseList').addClass('hide');
		$('#inactiveDueSoon').removeClass('active');
		$("#inactiveOverdueClr").css("background-color",  "#fed8b1");
		$("#inactiveOueSoonClr").css("background-color",  "");
		overDueList(status, pagenumber,vehicleStatus);
		
	}else if(status == 8 && vehicleStatus == 2){
		
		$('#inactiveDueSoon').addClass('active');
		$('#dueSoon').removeClass('active');
		$("#allClr").css("background-color",  "");
		$("#dueSoonClr").css("background-color",  "");
		$("#overDueClr").css("background-color",  "");
		$('#overDue').removeClass('active');
		$('#all').removeClass('active');
		$('#dateWiseList').addClass('hide');
		$('#inactiveOverdue').removeClass('active');
		$("#inactiveOverdueClr").css("background-color",  "");
		$("#inactiveOueSoonClr").css("background-color",  "#fed8b1");
		dueSoonList(status, pagenumber,vehicleStatus);
		
	}

}	

function getRRListByDate(date, dateStatus,count, pageNumber){
	
	$('#all').removeClass('active');
	$('#overDue').removeClass('active');
	$('#dueSoon').removeClass('active');
	$("#allClr").css("background-color",  "");
	$("#dueSoonClr").css("background-color",  "");
	$("#overDueClr").css("background-color",  "");
	$('#dateWiseList').removeClass('hide');
	$('#dateStatus').html(dateStatus);
	$('#dateWiseList').addClass('active');
	$('#dateWiseCount').html(count);
	$("#dateWiseListClr").css("background-color",  "#90EE90");
	
	var dateArr						= new Array();
	var jsonObject					= new Object();
	
	dateArr 						= date.split('To');
	
	
	jsonObject["date"]				= date;
	jsonObject["dateStatus"]		= dateStatus;
	jsonObject["count"]				= count;
	jsonObject["startDate"]			= dateArr[0].trim().split("-").reverse().join("-");
	jsonObject["endDate"]			= dateArr[1].trim().split("-").reverse().join("-");
	jsonObject["dateStatus"]		= dateStatus;
	jsonObject["pagenumber"]		= pageNumber;
	
	
	showLayer();
	$.ajax({
		url: "RenewalReminderWS/getRRListByDate.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setRenewalReminderDetailsByDateList(data);
		
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});

}
	
function openList(status, pagenumber,vehicleStatus){	
	
	var jsonObject					= new Object();
	jsonObject["status"]			= status;
	jsonObject["pagenumber"]		= pagenumber;
	jsonObject["vehicleStatus"]		= vehicleStatus;
	console.log("vehicleStatus",vehicleStatus)
	showLayer();
	$.ajax({
		url: "RenewalReminderWS/getRenewalReminderDetailsOpenList.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setRenewalReminderDetailsList(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function overDueList(status, pagenumber,vehicleStatus){	
	
	var jsonObject										= new Object();
	jsonObject["status"]								= status;
	jsonObject["pageNumber"]							= pagenumber;
	jsonObject["vehicleStatus"]							= vehicleStatus;
	jsonObject["inactiveVehicleInRR"]					= $("#inactiveVehicleInRR").val();
	jsonObject["vehicleExcludingSurrenderAndInactive"]	= $("#vehicleExcludingSurrenderAndInactive").val();
	
	showLayer();
	$.ajax({
		url: "RenewalReminderWS/getRenewalReminderDetailsOverdueList.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setRenewalReminderDetailsList(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function dueSoonList(status, pagenumber,vehicleStatus){	
	
	var jsonObject					= new Object();
	jsonObject["status"]			= status;
	jsonObject["pageNumber"]		= pagenumber;
	jsonObject["vehicleStatus"]		= vehicleStatus;
	jsonObject["inactiveVehicleInRR"]					= $("#inactiveVehicleInRR").val();
	jsonObject["vehicleExcludingSurrenderAndInactive"]	= $("#vehicleExcludingSurrenderAndInactive").val();
	showLayer();
	$.ajax({
		url: "RenewalReminderWS/getRenewalReminderDetailsDueSoonList.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setRenewalReminderDetailsList(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setRenewalReminderDetailsList(data) {
	$("#VendorPaymentTable").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th class="fit ar">');
	var th6		= $('<th class="fit ar">');
	var th7		= $('<th class="fit ar">');
	var th8		= $('<th class="fit ar">');
	var th9		= $('<th>');
	var th10	= $('<th>');
	var th11	= $('<th>');

	tr1.append(th1.append("RR-No"));
	tr1.append(th2.append("Vehicle"));
	tr1.append(th3.append("Receipt No/Policy No"));
	tr1.append(th4.append("Renewal Types"));
	tr1.append(th5.append("Validity From"));
	tr1.append(th6.append("Validity To"));
	tr1.append(th7.append("Amount"));
	tr1.append(th8.append("Download"));
	if($('#ignoreRenewal').val() == true || $('#ignoreRenewal').val() == 'true'){
		tr1.append(th11.append("Ignore Reminder"));
	}
	tr1.append(th9.append("Revise"));
	tr1.append(th10.append("Actions"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.RenewalReminder != undefined && data.RenewalReminder.length > 0) {
		
		var RenewalReminder = data.RenewalReminder;
	
		for(var i = 0; i < RenewalReminder.length; i++) {
			
			var labelClass;
			switch(RenewalReminder[i].renewal_staus_id) {
			  case 1:
				  labelClass = "label-danger";
			    break;
			  default:
				  labelClass = "label-success";
			} 
			
			
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
			
			tr1.append(td1.append('<a href="showRenewalReminderDetails?renewalId='+RenewalReminder[i].renewal_id+'" target="_blank">RR-'+RenewalReminder[i].renewal_R_Number+'</a>'));
			
			var curl = "ViewVehicleDocument.in?vehid="+RenewalReminder[i].vid
			tr1.append(td2.append('<a target="_blank" title="Status : '+RenewalReminder[i].vehicleStatus+' , Vehicle Group : '+RenewalReminder[i].vehicleGroup+'" href="' + curl + '">'+RenewalReminder[i].vehicle_registration+'</a><br>'));
			
			tr1.append(td3.append(RenewalReminder[i].renewal_receipt));
			
			if(RenewalReminder[i].renewal_dueRemDate == 'Due Soon'){
				tr1.append(td4.append('<span class="label label-default label-warning">'+RenewalReminder[i].renewal_dueRemDate+'</span>'+RenewalReminder[i].renewal_type+' '+RenewalReminder[i].renewal_subtype+' <span class="label '+labelClass+' ">'+RenewalReminder[i].renewal_status+'</span>'));
			} else if (RenewalReminder[i].renewal_dueRemDate == 'Overdue'){
				tr1.append(td4.append('<span class="label label-default label-danger">'+RenewalReminder[i].renewal_dueRemDate+'</span>'+RenewalReminder[i].renewal_type+' '+RenewalReminder[i].renewal_subtype+' <span  class="label '+labelClass+'">'+RenewalReminder[i].renewal_status+'</span>'));
			} else {
				if(RenewalReminder[i].renewal_dueRemDate != null){
					tr1.append(td4.append('<span class="label label-default label-info">'+RenewalReminder[i].renewal_dueRemDate+'</span>'+RenewalReminder[i].renewal_type+' '+RenewalReminder[i].renewal_subtype+' <span class="label '+labelClass+'">'+RenewalReminder[i].renewal_status+'</span>'));
				} else {
					tr1.append(td4.append(RenewalReminder[i].renewal_type+' '+RenewalReminder[i].renewal_subtype+' <span class="label '+labelClass+' ">'+RenewalReminder[i].renewal_status+'</span>'));
				}
			}
			
			tr1.append(td5.append(RenewalReminder[i].renewal_from));
			tr1.append(td6.append(RenewalReminder[i].renewal_to+'</br><span class="fa fa-calendar-check-o"> '+RenewalReminder[i].renewal_dueDifference+'</span>'));
			
			if(RenewalReminder[i].renewal_Amount != null){
				tr1.append(td7.append((RenewalReminder[i].renewal_Amount).toFixed(2)));
			} else {
				tr1.append(td7.append(('0')));
			}
			
			if(RenewalReminder[i].renewal_document == true && RenewalReminder[i].renewal_document_id > 0){
				tr1.append(td8.append('<a href="download/RenewalReminder/'+RenewalReminder[i].renewal_document_id+'.in"> <span class="fa fa-download"> Doc</span> </a>'));
			} else {
				tr1.append(td8.append("-"));
			}
			
			if($('#ignoreRenewal').val() == true || $('#ignoreRenewal').val() == 'true'){
				if(RenewalReminder[i].ignored){
					tr1.append(td11.append('Ignored'));
				}else{
					if(RenewalReminder[i].allowToIgnored){
						tr1.append(td11.append('<a href="#" onclick="ignoreRenewalReminder('+RenewalReminder[i].renewal_id+')"> <span class="btn btn-warning btn-sm"> Ignore</span> </a>'));
					}else{
						tr1.append(td11.append('Not allowed'));
					}
				}
			}
			if(($('#ignoreRenewal').val() == true || $('#ignoreRenewal').val() == 'true')){
				if(RenewalReminder[i].ignored){
					tr1.append(td9.append(' - '));
				}else{
					tr1.append(td9.append('<a href="renewalReminderAjaxRevise?renewalId='+RenewalReminder[i].renewal_id+'"> <span class="btn btn-info btn-sm"> Revise</span> </a>'));
				}
			}else{
				tr1.append(td9.append('<a href="renewalReminderAjaxRevise?renewalId='+RenewalReminder[i].renewal_id+'"> <span class="btn btn-info btn-sm"> Revise</span> </a>'));
			}
			
			
			var curl = "renewalReminderAjaxEdit?renewalId="+RenewalReminder[i].renewal_id
			if(($("#createApprovalPermission").val() == true || $("#createApprovalPermission").val() == 'true') && (RenewalReminder[i].renewal_staus_id == 1)){
				
				tr1.append(td10.append(
						'<div class="btn-group">'
						+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
						+'<ul class="dropdown-menu pull-right">'
						+'<li><a href="#" class="confirmation" onclick="createApprovalPopup('+RenewalReminder[i].renewal_id+')"><span class="fa fa-pen"></span> Create Approval</a></li>'
						+'<li><a href="'+curl+'"><i class="fa fa-edit"></i> Edit</a></li>'
						+'<li><a href="#" class="confirmation" onclick="deleteRenewal('+RenewalReminder[i].renewal_id+')"><span class="fa fa-trash"></span> Delete</a></li>'
						+'</ul>'
						+'</div>'
				));
			}else{
				var appendData = '<div class="btn-group">'
					+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
					+'<ul class="dropdown-menu pull-right">';
				if(($('#ignoreRenewal').val() == true || $('#ignoreRenewal').val() == 'true')){
					if(!RenewalReminder[i].ignored){
						appendData+= '<li><a href="'+curl+'"><i class="fa fa-edit"></i> Edit</a></li>';
					}
				}else{
					appendData+= '<li><a href="'+curl+'"><i class="fa fa-edit"></i> Edit</a></li>';	
				}
				appendData+='<li><a href="#" class="confirmation" onclick="deleteRenewal('+RenewalReminder[i].renewal_id+')"><span class="fa fa-trash"></span> Delete</a></li>'
					+'</ul>'
					+'</div>';
				
				tr1.append(td10.append(appendData));
			}
			

			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#VendorPaymentTable").append(thead);
	$("#VendorPaymentTable").append(tbody);
	
	$("#navigationBar").empty();

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;&lt;</a></li>');		
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="showList('+data.SelectStatus+', 1,'+data.vehicleStatus+')">&lt;&lt;</a></li>');		
	}

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="showList('+data.SelectStatus+', '+(data.currentIndex - 1)+', '+data.vehicleStatus+')">&lt;</a></li>');
	}
	
	for (i = data.beginIndex; i <= data.endIndex; i++) {
	    if(i == data.currentIndex) {
	    	$("#navigationBar").append('<li class="active"><a href="#" onclick="showList('+data.SelectStatus+', '+i+', '+data.vehicleStatus+')">'+i+'</a></li>');	    	
	    } else {
	    	$("#navigationBar").append('<li><a href="#" onclick="showList('+data.SelectStatus+', '+i+','+data.vehicleStatus+')">'+i+'</a></li>');	    	
	    }
	} 
	
	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="showList('+data.SelectStatus+', '+(data.currentIndex + 1)+', '+data.vehicleStatus+')">&gt;</a></li>');			
		}
	}

	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="showList('+data.SelectStatus+', '+(data.deploymentLog.totalPages)+', '+data.vehicleStatus+')">&gt;&gt;</a></li>');			
		}
	}
}

function deleteRenewal(renewal_id){
	
	if (confirm('Are you sure, you want to Delete this ?')) {
		
		showLayer();
		var jsonObject				= new Object();
		jsonObject["renewal_id"]	= renewal_id;
		
		$.ajax({
			url: "RenewalReminderWS/deleteRenewalReminderById",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				
				if(data.deleteRenewalReminder == true){
					hideLayer();
					showMessage('success', "Renewal Reminder Deleted Successfully !");
					window.location.replace("viewRenewalReminder.in");
				} else {
					hideLayer();
					showMessage('success', "Renewal Reminder Not Deleted !");
				}
				
				hideLayer();
			},
			error: function (e) {
				hideLayer();
				showMessage('errors', 'Some Error Occurred!');
			}
		});
		
	}else {
		location.reload();
	}
	
}

function serachRenewalReminderByNumber(){
	
	var jsonObject					= new Object();
	jsonObject["renewalNumber"]		= $("#searchByNumber").val();
	
	if( $("#searchByNumber").val() == ""){
		$('#searchByNumber').focus();
		showMessage('errors', 'Please Enter Valid Renewal Number !');
		return false;
	}
	
	showLayer();
	$.ajax({
		url: "RenewalReminderWS/serachRenewalReminderByNumber",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			if(data.RenewalReminder != undefined && data.RenewalReminder.renewal_id != null){
				hideLayer();
				window.location.replace("showRenewalReminderDetails.in?renewalId="+data.RenewalReminder.renewal_id+"");
			} else {
				hideLayer();
				showMessage('info', 'Please Enter Valid Renewal Number !');
			}
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

$(document).ready(function () {
    $("#searchByDifferentFilter").on('keypress', function (e) {
        if(e.which == 13) {
        	searchRRByDifferentFilter();
        }
    });
    $("#searchByNumber").on('keypress', function (e) {
    	if(e.which == 13) {
    		serachRenewalReminderByNumber();
    	}
    });

});


function searchRRByDifferentFilter(){
	window.location.replace("renewalReminderSearch.in?id="+$("#searchByDifferentFilter").val()+"");
}


function searchRRDateWise(){
	
	var jsonObject					= new Object();
	jsonObject["dateRange"]			= $("#ApprovalPaidDate").val().split("-").reverse().join("-");
	
	if( $("#ApprovalPaidDate").val() == ""){
		$('#ApprovalPaidDate').focus();
		showMessage('errors', 'Please Enter Valid Date !');
		return false;
	}
	
	showLayer();
	$.ajax({
		url: "RenewalReminderWS/searchRRDateWise",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			console.log('dataDate : ', data);
			setRRDateWise(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setRRDateWise(data) {
	
	$("#tabsHeading").hide();
	$("#tabsData").hide();
	$(".filterByDifferentData").addClass('hide');
	$(".filterByDateData").removeClass('hide');
	$('#searchRRByDate').modal('hide');
	
	$("#VendorPaymentTable2").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th class="fit ar">');
	var th6		= $('<th class="fit ar">');
	var th7		= $('<th class="fit ar">');
	var th8		= $('<th class="fit ar">');
	var th9		= $('<th>');
	var th10	= $('<th>');

	tr1.append(th1.append("RR-No"));
	tr1.append(th2.append("Vehicle"));
	tr1.append(th3.append("Vehicle Group"));
	tr1.append(th4.append("Renewal Types"));
	tr1.append(th5.append("Validity From"));
	tr1.append(th6.append("Validity To"));
	tr1.append(th7.append("Amount"));
	tr1.append(th8.append("Download"));
	tr1.append(th9.append("Revise"));
	tr1.append(th10.append("Actions"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.RenewalReminder != undefined && data.RenewalReminder.length > 0) {
		
		var RenewalReminder = data.RenewalReminder;
	
		for(var i = 0; i < RenewalReminder.length; i++) {
			var labelClass;
			switch(RenewalReminder[i].renewal_staus_id) {
			  case 1:
				  labelClass = "label-danger";
			    break;
			  default:
				  labelClass = "label-success";
			} 
			
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
			
			tr1.append(td1.append('<a href="showRenewalReminderDetails?renewalId='+RenewalReminder[i].renewal_id+'" target="_blank">RR-'+RenewalReminder[i].renewal_R_Number+'</a>'));
			
			var curl = "ViewVehicleDocument.in?vehid="+RenewalReminder[i].vid
			tr1.append(td2.append('<a target="_blank" href="' + curl + '">'+RenewalReminder[i].vehicle_registration+'</a><br>'));
			
			tr1.append(td3.append(RenewalReminder[i].vehicleGroup));
			
			if(RenewalReminder[i].renewal_dueRemDate == 'Due Soon'){
				tr1.append(td4.append('<span class="label label-default label-warning">'+RenewalReminder[i].renewal_dueRemDate+'</span>'+RenewalReminder[i].renewal_type+' '+RenewalReminder[i].renewal_subtype +' <span class="label '+labelClass+' ">'+RenewalReminder[i].renewal_status+'</span>'));
			} else if (RenewalReminder[i].renewal_dueRemDate == 'Overdue'){
				tr1.append(td4.append('<span class="label label-default label-danger">'+RenewalReminder[i].renewal_dueRemDate+'</span>'+RenewalReminder[i].renewal_type+' '+RenewalReminder[i].renewal_subtype +' <span class="label '+labelClass+' ">'+RenewalReminder[i].renewal_status+'</span>'));
			} else {
				if(RenewalReminder[i].renewal_dueRemDate != null){
					tr1.append(td4.append('<span class="label label-default label-info">'+RenewalReminder[i].renewal_dueRemDate+'</span>'+RenewalReminder[i].renewal_type+' '+RenewalReminder[i].renewal_subtype +' <span class="label '+labelClass+' ">'+RenewalReminder[i].renewal_status+'</span>'));
				} else {
					tr1.append(td4.append(RenewalReminder[i].renewal_type+' '+RenewalReminder[i].renewal_subtype +' <span class="label '+labelClass+' ">'+RenewalReminder[i].renewal_status+'</span>'));
				}
			}
			
			tr1.append(td5.append(RenewalReminder[i].renewal_from));
			tr1.append(td6.append(RenewalReminder[i].renewal_to));
			tr1.append(td7.append((RenewalReminder[i].renewal_Amount).toFixed(2)));
			
			if(RenewalReminder[i].renewal_document == true && RenewalReminder[i].renewal_document_id > 0){
				tr1.append(td8.append('<a href="download/RenewalReminder/'+RenewalReminder[i].renewal_document_id+'.in"> <span class="fa fa-download"> Doc</span> </a>'));
			} else {
				tr1.append(td8.append("-"));
			}
			
			tr1.append(td9.append('<a href="renewalReminderAjaxRevise?renewalId='+RenewalReminder[i].renewal_id+'"> <span class="btn btn-info btn-sm"> Revise</span> </a>'));
			
			
			var curl = "renewalReminderAjaxEdit?renewalId="+RenewalReminder[i].renewal_id
			if(($("#createApprovalPermission").val() == true || $("#createApprovalPermission").val() == 'true') && (RenewalReminder[i].renewal_staus_id == 1)){
				tr1.append(td10.append(
				'<div class="btn-group">'
				+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
				+'<ul class="dropdown-menu pull-right">'
				+'<li><a href="#" class="confirmation" onclick="createApprovalPopup('+RenewalReminder[i].renewal_id+')"><span class="fa fa-pen"></span> Create Approval</a></li>'
				+'<li><a href="'+curl+'"><i class="fa fa-edit"></i> Edit</a></li>'
				+'<li><a href="#" class="confirmation" onclick="deleteRenewal('+RenewalReminder[i].renewal_id+')"><span class="fa fa-trash"></span> Delete</a></li>'
				+'</ul>'
				+'</div>'
				));
			}else{
				tr1.append(td10.append(
				'<div class="btn-group">'
				+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
				+'<ul class="dropdown-menu pull-right">'
				+'<li><a href="'+curl+'"><i class="fa fa-edit"></i> Edit</a></li>'
				+'<li><a href="#" class="confirmation" onclick="deleteRenewal('+RenewalReminder[i].renewal_id+')"><span class="fa fa-trash"></span> Delete</a></li>'
				+'</ul>'
				+'</div>'
				));
			}
			

			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#VendorPaymentTable2").append(thead);
	$("#VendorPaymentTable2").append(tbody);
	
}

$(document).ready(function() {
	
	$("#vehicleId").select2( {
        minimumInputLength:2, minimumResultsForSearch:10, ajax: {
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
    });
});

function searchByVehicle(){
	
	var jsonObject	= new Object();
	
	jsonObject["vid"]	= $('#vehicleId').val();
	
	if($('#vehicleId').val().trim() == ''){
		$('#vehicleId').focus();
		showMessage('errors', 'Please Select Vehicle !');
		hideLayer();
		return false;
	}
	
	console.log("jsonObject..",jsonObject);
	
	showLayer();
	$.ajax({
		url: "getVehicleIdFromVehicle.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			if(data.vehicleId != undefined && data.vehicleId != null){
				window.location.replace("ViewVehicleDocument.in?vehid="+data.vehicleId+"");
			}
			hideLayer();
		},
		error: function (e) {
			console.log("Error : " , e);
			hideLayer();
		}
	});

}

$(document).ready(function () {
	
    $("#btnSubmit").on('keypress click', function (e) {
        e.preventDefault(); 	//stop submit the form, we will post it manually.
        importRenewalCSV();
    });

});

function importRenewalCSV(){
	
	if( $('#renewalFile').val().trim() == ''){					
		showMessage('errors', 'Please Upload .CSV File!');
		hideLayer();
		return false;
	}
	
	var jsonObject	= new Object();
	
	var form = $('#fileUploadForm')[0];
    var data = new FormData(form);

    data.append("RenewalData", JSON.stringify(jsonObject));   
	
    showLayer();
	$.ajax({
		type: "POST",
		enctype: 'multipart/form-data',
		url: "RenewalReminderWS/uploadRenewalCSVFile",
		data: data,
		processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
		success: function (data) {
			
			$('#addImport').modal('hide');
			
			if (data.renewalRemindeAlready == true){
				hideLayer();
				showMessage('errors', "Renewal Reminder Already Exists Hence Cannot Create New RR !");
				
			} else if (data.renewalReceiptAlready == true){
				hideLayer();
				showMessage('errors', "Renewal Reminder Compliance Issue Hence Cannot Create New RR !");
				
			} else if (data.importSaveAlreadyError == true){
				hideLayer();
				showMessage('errors', "Renewal Reminder cannot be created. Duplicate Found !");
				
			}  else if (data.importSaveError == true){
				hideLayer();
				showMessage('errors', "Renewal Reminder cannot be created. Imoprt Error  !");
				
			}  else if (data.importSave == true){
				showMessage('success', "Renewal Reminder successfully Created !");
				location.reload();
				hideLayer();
				
			} else {
				hideLayer();
				showMessage('errors', "Renewal Not Created, Please contact on Support !");
			}
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}


function createApprovalPopup(renewalId){
	$("#createApprovalModal").modal('show');
	$("#renewalId").val(renewalId);
}

$("#createApproval").click(function(){
	showLayer();
	var jsonObject					= new Object();
	jsonObject["renewal_id"]		= $('#renewalId').val();
	jsonObject["approvalRemark"]	= $('#approvalRemark').val();
	$.ajax({
		url: "RenewalReminderWS/createRRApproval",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			showMessage('success','Renewal Approved')
			location.reload();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}); 



function setRenewalReminderDetailsByDateList(data) {
	$("#VendorPaymentTable").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th class="fit ar">');
	var th6		= $('<th class="fit ar">');
	var th7		= $('<th class="fit ar">');
	var th8		= $('<th class="fit ar">');
	var th9		= $('<th>');
	var th10	= $('<th>');
	var th11	= $('<th>');
	var th12	= $('<th>');

	tr1.append(th1.append("RR-No"));
	tr1.append(th2.append("Vehicle"));
	tr1.append(th11.append("Vehicle Status"));
	tr1.append(th3.append("Vehicle Group"));
	tr1.append(th4.append("Renewal Types"));
	tr1.append(th5.append("Validity From"));
	tr1.append(th6.append("Validity To"));
	tr1.append(th7.append("Amount"));
	tr1.append(th8.append("Download"));
	if($('#ignoreRenewal').val() == true || $('#ignoreRenewal').val() == 'true'){
		tr1.append(th12.append("Ignore Reminder"));
	}
	tr1.append(th9.append("Revise"));
	tr1.append(th10.append("Actions"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.RenewalReminder != undefined && data.RenewalReminder.length > 0) {
		
		var RenewalReminder = data.RenewalReminder;
		var todayCount = 0 ;
		for(var i = 0; i < RenewalReminder.length; i++) {
			
			var labelClass;
			switch(RenewalReminder[i].renewal_staus_id) {
			  case 1:
				  labelClass = "label-danger";
			    break;
			  default:
				  labelClass = "label-success";
			} 
			
			
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
			var td12	= $('<td class="fit ar">');
			
			tr1.append(td1.append('<a href="showRenewalReminderDetails?renewalId='+RenewalReminder[i].renewal_id+'" target="_blank">RR-'+RenewalReminder[i].renewal_R_Number+'</a>'));
			
			var curl = "ViewVehicleDocument.in?vehid="+RenewalReminder[i].vid
			tr1.append(td2.append('<a target="_blank" href="' + curl + '">'+RenewalReminder[i].vehicle_registration+'</a><br>'));
			tr1.append(td11.append(RenewalReminder[i].vehicleStatus));
			tr1.append(td3.append(RenewalReminder[i].vehicleGroup));
			
			if(RenewalReminder[i].renewal_dueRemDate == 'Due Soon'){
				tr1.append(td4.append('<span class="label label-default label-warning">'+RenewalReminder[i].renewal_dueRemDate+'</span>'+RenewalReminder[i].renewal_type+' '+RenewalReminder[i].renewal_subtype+' <span class="label '+labelClass+' ">'+RenewalReminder[i].renewal_status+'</span>'));
			} else if (RenewalReminder[i].renewal_dueRemDate == 'Overdue'){
				tr1.append(td4.append('<span class="label label-default label-danger">'+RenewalReminder[i].renewal_dueRemDate+'</span>'+RenewalReminder[i].renewal_type+' '+RenewalReminder[i].renewal_subtype+' <span  class="label '+labelClass+'">'+RenewalReminder[i].renewal_status+'</span>'));
			} else {
				if(RenewalReminder[i].renewal_dueRemDate != null){
					tr1.append(td4.append('<span class="label label-default label-info">'+RenewalReminder[i].renewal_dueRemDate+'</span>'+RenewalReminder[i].renewal_type+' '+RenewalReminder[i].renewal_subtype+' <span class="label '+labelClass+'">'+RenewalReminder[i].renewal_status+'</span>'));
				} else {
					tr1.append(td4.append(RenewalReminder[i].renewal_type+' '+RenewalReminder[i].renewal_subtype+' <span class="label '+labelClass+' ">'+RenewalReminder[i].renewal_status+'</span>'));
				}
			}
			
			tr1.append(td5.append(RenewalReminder[i].renewal_from));
			tr1.append(td6.append(RenewalReminder[i].renewal_to+'</br><span class="fa fa-calendar-check-o"> '+RenewalReminder[i].renewal_dueDifference+'</span>'));
			
			if(RenewalReminder[i].renewal_Amount != null){
				tr1.append(td7.append((RenewalReminder[i].renewal_Amount).toFixed(2)));
			} else {
				tr1.append(td7.append(('0')));
			}
			
			if(RenewalReminder[i].renewal_document == true && RenewalReminder[i].renewal_document_id > 0){
				tr1.append(td8.append('<a href="download/RenewalReminder/'+RenewalReminder[i].renewal_document_id+'.in"> <span class="fa fa-download"> Doc</span> </a>'));
			} else {
				tr1.append(td8.append("-"));
			}
			if(($('#ignoreRenewal').val() == true || $('#ignoreRenewal').val() == 'true')){
				if(RenewalReminder[i].ignored){
					tr1.append(td12.append('Ignored'));
				}else{
					if(RenewalReminder[i].allowToIgnored){
						tr1.append(td12.append('<a href="#" onclick="ignoreRenewalReminder('+RenewalReminder[i].renewal_id+')"> <span class="btn btn-warning btn-sm"> Ignore</span> </a>'));
					}else{
						tr1.append(td12.append('Not allowed'));
					}
				}
			}
			
			tr1.append(td9.append('<a href="renewalReminderAjaxRevise?renewalId='+RenewalReminder[i].renewal_id+'"> <span class="btn btn-info btn-sm"> Revise</span> </a>'));
			
			
			var curl = "renewalReminderAjaxEdit?renewalId="+RenewalReminder[i].renewal_id
			if(($("#createApprovalPermission").val() == true || $("#createApprovalPermission").val() == 'true') && (RenewalReminder[i].renewal_staus_id == 1)){
				
				tr1.append(td10.append(
						'<div class="btn-group">'
						+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
						+'<ul class="dropdown-menu pull-right">'
						+'<li><a href="#" class="confirmation" onclick="createApprovalPopup('+RenewalReminder[i].renewal_id+')"><span class="fa fa-pen"></span> Create Approval</a></li>'
						+'<li><a href="'+curl+'"><i class="fa fa-edit"></i> Edit</a></li>'
						+'<li><a href="#" class="confirmation" onclick="deleteRenewal('+RenewalReminder[i].renewal_id+')"><span class="fa fa-trash"></span> Delete</a></li>'
						+'</ul>'
						+'</div>'
				));
			}else{
				tr1.append(td10.append(
						'<div class="btn-group">'
						+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
						+'<ul class="dropdown-menu pull-right">'
						+'<li><a href="'+curl+'"><i class="fa fa-edit"></i> Edit</a></li>'
						+'<li><a href="#" class="confirmation" onclick="deleteRenewal('+RenewalReminder[i].renewal_id+')"><span class="fa fa-trash"></span> Delete</a></li>'
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
	
	$("#navigationBar").empty();

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;&lt;</a></li>');		
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="getRRListByDate(\'' + data.date + '\',\'' + data.dateStatus + '\','+data.count+', 1)">&lt;&lt;</a></li>');		
	}

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="getRRListByDate(\'' + data.date + '\',\'' + data.dateStatus + '\','+data.count+','+(data.currentIndex - 1)+')">&lt;</a></li>');
	}
	
	for (i = data.beginIndex; i <= data.endIndex; i++) {
	    if(i == data.currentIndex) {
	    	$("#navigationBar").append('<li class="active"><a href="#" onclick="getRRListByDate(\'' + data.date + '\',\'' + data.dateStatus + '\','+data.count+', '+i+')">'+i+'</a></li>');	    	
	    } else {
	    	$("#navigationBar").append('<li><a href="#" onclick="getRRListByDate(\'' + data.date + '\',\'' + data.dateStatus + '\','+data.count+', '+i+')">'+i+'</a></li>');	    	
	    }
	} 
	
	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getRRListByDate(\'' + data.date + '\',\'' + data.dateStatus + '\','+data.count+', '+(data.currentIndex + 1)+')">&gt;</a></li>');			
		}
	}

	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getRRListByDate(\'' + data.date + '\',\'' + data.dateStatus + '\','+data.count+', '+(data.deploymentLog.totalPages)+')">&gt;&gt;</a></li>');			
		}
	}
}

function ignoreRenewalReminder(renewalId){

	$('#ignoreWithRemark').modal('show');
	$('#ignoreRenewalReminderId').val(renewalId);

}
function ignoreWithRemark(){
	var remark = $('#igRemark').val();
	if(remark == null || remark.trim() == ''){
		$('#igRemark').focus();
		showMessage('warning','Please enter Remark !');
		return false;
	}
	
	
	var jsonObject = new Object();

	jsonObject['renewalId'] =  $('#ignoreRenewalReminderId').val();
	jsonObject['igRemark'] =  remark;
	$.ajax({
		url : "RenewalReminderWS/ignoreRenewalReminder",
		type : "POST",
		dataType : 'json',
		data : jsonObject ,
		success: function (data) {
			showMessage('success','Renewal set as Ignored ')
			setTimeout(function(){
				location.reload();
			},500)
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	})
	
}