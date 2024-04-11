$(document).ready(function() {
	showList(1);
});

function showDueSoonServiceList(){

	var jsonObject					= new Object();
	jsonObject["companyId"]			= $('#companyId').val();
	jsonObject["userId"]			= $('#userId').val();
	showLayer();
	$.ajax({
		url: "getDueSoonServiceList",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setDueSoonServiceReminderList(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});

}

function showTodaysOverDueServiceList(pagenumber){

	var jsonObject					= new Object();
	jsonObject["pageNumber"]		= pagenumber;
	jsonObject["companyId"]			= $('#companyId').val();
	jsonObject["userId"]			= $('#userId').val();
	showLayer();
	$.ajax({
		url: "getTodayOverDueServiceList",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setTodaysOverDueServiceReminderList(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});

}

function showUpcomingOverDueServiceList(pagenumber){

	var jsonObject					= new Object();
	jsonObject["pageNumber"]		= pagenumber;
	jsonObject["companyId"]			= $('#companyId').val();
	jsonObject["userId"]			= $('#userId').val();
	showLayer();
	$.ajax({
		url: "getUpcomingOverDueServiceList",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setUpcomingOverDueServiceReminderList(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});

}

function showOverDueServiceList(pagenumber){

	var jsonObject					= new Object();
	jsonObject["pageNumber"]		= pagenumber;
	jsonObject["companyId"]			= $('#companyId').val();
	jsonObject["userId"]			= $('#userId').val();
	showLayer();
	$.ajax({
		url: "getOverDueServiceList",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setOverDueServiceReminderList(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});

}

function showList(pagenumber){
	var jsonObject					= new Object();
	jsonObject["pageNumber"]		= pagenumber;
	jsonObject["companyId"]						= $('#companyId').val();
	jsonObject["userId"]						= $('#userId').val();
	showLayer();
	$.ajax({
		url: "getServiceReminderList",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setServiceReminderList(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setOverDueServiceReminderList(data) {
	$('#dataType').text('OverDue Service Reminder List');
	$("#count").text(data.totalCount);
	$("#VendorPaymentTable").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th class="fit ar">');
	var th2		= $('<th class="fit ar">');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th class="fit ar">');
	var th6		= $('<th class="fit ar">');
	
	if($('#groupListByVehicleNumber').val() == 'true' || $('#groupListByVehicleNumber').val() == true){
		tr1.append(th1.append("Vehicle"));
		tr1.append(th2.append("No Of Service Reminder"));
		tr1.append(th3.append("No Of Service Program"));
	}else{

		tr1.append(th1.append("No."));
		tr1.append(th2.append("Vehicle"));
		tr1.append(th3.append("Service Task And Schedule"));
		tr1.append(th4.append("Next Due"));
		tr1.append(th5.append("Subscribers"));
		tr1.append(th6.append("Action"));
	
	}

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.serviceReminderList != undefined && data.serviceReminderList.length > 0) {
		var deleteServiceReminder = false,editServiceReminder = false,createWoOnService = false;
		
		if($('#createWoOnService').val() != undefined && $('#createWoOnService').val() != null && $('#createWoOnService').val() == 'true'){
			createWoOnService = true;
		}
		if($('#editServiceReminder').val() != undefined && $('#editServiceReminder').val() != null && $('#editServiceReminder').val() == 'true'){
			editServiceReminder = true;
		}
		if($('#deleteServiceReminder').val() != undefined && $('#deleteServiceReminder').val() != null && $('#deleteServiceReminder').val() == 'true'){
			deleteServiceReminder = true;
		}
		
		$('#overeDueCount').text(data.TodayOverDueServiceRemindercount);
		$('#DueSoonCount').text(data.TodayDueServiceRemindercount);
		$('#overDueTodayCount').text(data.TodaysDueServiceRemindercount);
		$('#overUpcomingDueCount').text(data.UpcomingDueServiceRemindercount);
		
		var srNo = 1;
		var serviceReminderList = data.serviceReminderList;
	
		for(var i = 0; i < serviceReminderList.length; i++) {
		
		if($('#groupListByVehicleNumber').val() == 'true' || $('#groupListByVehicleNumber').val() == true){
				var tr1 = $('<tr class="ng-scope">');
			
				var td1		= $('<td class="fit ar">');
				var td2		= $('<td class="fit ar">');
				var td3		= $('<td class="fit ar">');
				var td4		= $('<td class="fit ar">');
				tr1.append(td1.append('<a target="_blank" href="VehicleServiceDetails.in?vid='+serviceReminderList[i].vid+'">'+serviceReminderList[i].vehicle_registration+'</a>'));
				tr1.append(td2.append('<a href="#" onclick = "getOverDueReminderOfVehicle('+serviceReminderList[i].vid+');">'+serviceReminderList[i].service_id+'</a>'));
				tr1.append(td3.append('<a href="#" onclick = "getOverDueReminderOfVehicle('+serviceReminderList[i].vid+');">'+serviceReminderList[i].serviceProgramId+'</a>'));
				
				tbody.append(tr1);
				
			}else{
		
			var createWO = false;
			if(serviceReminderList[i].service_remider_howtimes <= 1){
					createWO = true;
			}
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td class="fit ar">');
			var td2		= $('<td class="fit ar">');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td class="fit ar">');
			var td5		= $('<td class="fit ar">');
			var td6		= $('<td class="fit ar">');
			var nextDue = "";
			if(serviceReminderList[i].diffrent_time_days != '' && serviceReminderList[i].diffrent_meter_oddmeter != ''){
				nextDue = '<i class="fa fa-calendar-check-o"></i> '+serviceReminderList[i].diffrent_time_days+'<br> <i class="fa fa-road"></i> '+serviceReminderList[i].diffrent_meter_oddmeter;
			}else if(serviceReminderList[i].diffrent_time_days != '' && serviceReminderList[i].diffrent_meter_oddmeter == ''){
				nextDue = '<i class="fa fa-calendar-check-o"></i> '+serviceReminderList[i].diffrent_time_days;
			}else if(serviceReminderList[i].diffrent_time_days == '' && serviceReminderList[i].diffrent_meter_oddmeter != ''){
				nextDue = '<i class="fa fa-calendar-check-o"></i> '+serviceReminderList[i].diffrent_meter_oddmeter;
			}
			
			tr1.append(td1.append('<a target="_blank" href="ShowService.in?service_id='+serviceReminderList[i].service_id+'">S-'+serviceReminderList[i].service_Number+'</a>'));
			tr1.append(td2.append('<a target="_blank" href="VehicleServiceDetails.in?vid='+serviceReminderList[i].vid+'">'+serviceReminderList[i].vehicle_registration+'</a><br> '+serviceReminderList[i].vehicle_Group+''));
			tr1.append(td3.append('<b style="font-size:12px;">'+serviceReminderList[i].diffenceThrsholdOdometer+' '+serviceReminderList[i].taskAndSchedule+'</b>'));
			tr1.append(td4.append(nextDue));
			tr1.append(td5.append(serviceReminderList[i].service_subscribeduser_name));
			if(serviceReminderList[i].diffenceThrsholdOdometer != ''){
				createWO = true;
			}
			var action = '<div class="btn-group">'
				+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
				+'<ul class="dropdown-menu pull-right">';
				if(createWoOnService && createWO){
					action+= '<li><a href="createWorkOrder.in?service_id='+serviceReminderList[i].service_id+'"><i class="fa fa-edit"></i> Create WorkOrder</a></li>';
				}else if(createWoOnService && !createWO){
					action+= '<li><span class="label label-warning"><i class="fa fa-dot-circle-o"></i>WorkOrder Created</span></li>';
				}
				
				if(editServiceReminder){
					action+= '<li><a href="editServiceReminderEntry.in?service_id='+serviceReminderList[i].service_id+'"><i class="fa fa-edit"></i> Edit</a></li>';
				}
				if(deleteServiceReminder){
					action+= '<li><a href="#" class="confirmation" onclick="deleteServiceReminder('+serviceReminderList[i].service_id+');"><span class="fa fa-trash"></span> Delete</a></li>';
				}
				action+= '</ul>'
				+'</div>'
				
			tr1.append(td6.append(action));

			tbody.append(tr1);
			srNo ++;
		}
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
		$("#navigationBar").append('<li><a href="#" onclick="showOverDueServiceList(1)">&lt;&lt;</a></li>');		
	}
	
	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="showOverDueServiceList('+(data.currentIndex - 1)+')">&lt;</a></li>');
	}
	if(data.currentIndex == 1){
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	}else{
		$("#navigationBar").append('<li><a href="#" onclick="showTodayOverDueServiceList'+(data.currentIndex - 1)+')">&lt;</a></li>');
	}
	if(data.currentIndex == 1){
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	}else{
		$("#navigationBar").append('<li><a href="#" onclick="showUpcomingOverDueServiceList'+(data.currentIndex - 1)+')">&lt;</a></li>');
	}
	
	for (i = data.beginIndex; i <= data.endIndex; i++) {
	    if(i == data.currentIndex) {
	    	$("#navigationBar").append('<li class="active"><a href="#" onclick="showOverDueServiceList('+i+')">'+i+'</a></li>');	    	
	    } else {
	    	$("#navigationBar").append('<li><a href="#" onclick="showOverDueServiceList('+i+')">'+i+'</a></li>');	    	
	    }
	} 
	
	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="showOverDueServiceList('+(data.currentIndex + 1)+')">&gt;</a></li>');			
		}
	}

	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="showOverDueServiceList('+(data.deploymentLog.totalPages)+')">&gt;&gt;</a></li>');			
		}
	}

}

function setTodaysOverDueServiceReminderList(data) {
	$('#dataType').text('Todays OverDue Service Reminder List');
	$("#count").text(data.totalCount);
	$("#VendorPaymentTable").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th class="fit ar">');
	var th2		= $('<th class="fit ar">');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th class="fit ar">');
	var th6		= $('<th class="fit ar">');
	
	if($('#groupListByVehicleNumber').val() == 'true' || $('#groupListByVehicleNumber').val() == true){
		tr1.append(th1.append("Vehicle"));
		tr1.append(th2.append("No Of Service Reminder"));
		tr1.append(th3.append("No Of Service Program"));
	}else{
		tr1.append(th1.append("No."));
		tr1.append(th2.append("Vehicle"));
		tr1.append(th3.append("Service Task And Schedule"));
		tr1.append(th4.append("Next Due"));
		tr1.append(th5.append("Subscribers"));
		tr1.append(th6.append("Action"));
	}

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.serviceReminderList != undefined && data.serviceReminderList.length > 0) {
		var deleteServiceReminder = false,editServiceReminder = false,createWoOnService = false;
		
		if($('#createWoOnService').val() != undefined && $('#createWoOnService').val() != null && $('#createWoOnService').val() == 'true'){
			createWoOnService = true;
		}
		if($('#editServiceReminder').val() != undefined && $('#editServiceReminder').val() != null && $('#editServiceReminder').val() == 'true'){
			editServiceReminder = true;
		}
		if($('#deleteServiceReminder').val() != undefined && $('#deleteServiceReminder').val() != null && $('#deleteServiceReminder').val() == 'true'){
			deleteServiceReminder = true;
		}
		
		$('#overeDueCount').text(data.TodayOverDueServiceRemindercount);
		$('#DueSoonCount').text(data.TodayDueServiceRemindercount);
		$('#overDueTodayCount').text(data.TodaysDueServiceRemindercount);
		$('#overUpcomingDueCount').text(data.UpcomingDueServiceRemindercount);
		
		var srNo = 1;
		var serviceReminderList = data.serviceReminderList;
	
		for(var i = 0; i < serviceReminderList.length; i++) {
		
		if($('#groupListByVehicleNumber').val() == 'true' || $('#groupListByVehicleNumber').val() == true){
				var tr1 = $('<tr class="ng-scope">');
			
				var td1		= $('<td class="fit ar">');
				var td2		= $('<td class="fit ar">');
				var td3		= $('<td class="fit ar">');
				var td4		= $('<td class="fit ar">');
				tr1.append(td1.append('<a target="_blank" href="VehicleServiceDetails.in?vid='+serviceReminderList[i].vid+'">'+serviceReminderList[i].vehicle_registration+'</a>'));
				tr1.append(td2.append('<a href="#" onclick = "getOverDueReminderOfVehicle('+serviceReminderList[i].vid+');">'+serviceReminderList[i].service_id+'</a>'));
				tr1.append(td3.append('<a href="#" onclick = "getOverDueReminderOfVehicle('+serviceReminderList[i].vid+');">'+serviceReminderList[i].serviceProgramId+'</a>'));
				
				tbody.append(tr1);
				
			}else{
		
			var createWO = false;
			if(serviceReminderList[i].service_remider_howtimes <= 1){
					createWO = true;
			}
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td class="fit ar">');
			var td2		= $('<td class="fit ar">');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td class="fit ar">');
			var td5		= $('<td class="fit ar">');
			var td6		= $('<td class="fit ar">');
			var nextDue = "";
			if(serviceReminderList[i].diffrent_time_days != '' && serviceReminderList[i].diffrent_meter_oddmeter != ''){
				nextDue = '<i class="fa fa-calendar-check-o"></i> '+serviceReminderList[i].diffrent_time_days+'<br> <i class="fa fa-road"></i> '+serviceReminderList[i].diffrent_meter_oddmeter;
			}else if(serviceReminderList[i].diffrent_time_days != '' && serviceReminderList[i].diffrent_meter_oddmeter == ''){
				nextDue = '<i class="fa fa-calendar-check-o"></i> '+serviceReminderList[i].diffrent_time_days;
			}else if(serviceReminderList[i].diffrent_time_days == '' && serviceReminderList[i].diffrent_meter_oddmeter != ''){
				nextDue = '<i class="fa fa-calendar-check-o"></i> '+serviceReminderList[i].diffrent_meter_oddmeter;
			}
			
			tr1.append(td1.append('<a target="_blank" href="ShowService.in?service_id='+serviceReminderList[i].service_id+'">S-'+serviceReminderList[i].service_Number+'</a>'));
			tr1.append(td2.append('<a target="_blank" href="VehicleServiceDetails.in?vid='+serviceReminderList[i].vid+'">'+serviceReminderList[i].vehicle_registration+'</a><br> '+serviceReminderList[i].vehicle_Group+''));
			tr1.append(td3.append('<b style="font-size:12px;">'+serviceReminderList[i].diffenceThrsholdOdometer+' '+serviceReminderList[i].taskAndSchedule+'</b>'));
			tr1.append(td4.append(nextDue));
			tr1.append(td5.append(serviceReminderList[i].service_subscribeduser_name));
			if(serviceReminderList[i].diffenceThrsholdOdometer != ''){
				createWO = true;
			}
			var action = '<div class="btn-group">'
				+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
				+'<ul class="dropdown-menu pull-right">';
				if(createWoOnService && createWO){
					action+= '<li><a href="createWorkOrder.in?service_id='+serviceReminderList[i].service_id+'"><i class="fa fa-edit"></i> Create WorkOrder</a></li>';
				}else if(createWoOnService && !createWO){
					action+= '<li><span class="label label-warning"><i class="fa fa-dot-circle-o"></i>WorkOrder Created</span></li>';
				}
				
				if(editServiceReminder){
					action+= '<li><a href="editServiceReminderEntry.in?service_id='+serviceReminderList[i].service_id+'"><i class="fa fa-edit"></i> Edit</a></li>';
				}
				if(deleteServiceReminder){
					action+= '<li><a href="#" class="confirmation" onclick="deleteServiceReminder('+serviceReminderList[i].service_id+');"><span class="fa fa-trash"></span> Delete</a></li>';
				}
				action+= '</ul>'
				+'</div>'
				
			tr1.append(td6.append(action));

			tbody.append(tr1);
			srNo ++;
		}
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
		$("#navigationBar").append('<li><a href="#" onclick="showOverDueServiceList(1)">&lt;&lt;</a></li>');		
	}
	
	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="showOverDueServiceList('+(data.currentIndex - 1)+')">&lt;</a></li>');
	}
	if(data.currentIndex == 1){
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	}else{
		$("#navigationBar").append('<li><a href="#" onclick="showTodaysOverDueServiceList'+(data.currentIndex - 1)+')">&lt;</a></li>');
	}
	if(data.currentIndex == 1){
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	}else{
		$("#navigationBar").append('<li><a href="#" onclick="showUpcomingOverDueServiceList'+(data.currentIndex - 1)+')">&lt;</a></li>');
	}
	
	for (i = data.beginIndex; i <= data.endIndex; i++) {
	    if(i == data.currentIndex) {
	    	$("#navigationBar").append('<li class="active"><a href="#" onclick="showOverDueServiceList('+i+')">'+i+'</a></li>');	    	
	    } else {
	    	$("#navigationBar").append('<li><a href="#" onclick="showOverDueServiceList('+i+')">'+i+'</a></li>');	    	
	    }
	} 
	
	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="showOverDueServiceList('+(data.currentIndex + 1)+')">&gt;</a></li>');			
		}
	}

	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="showOverDueServiceList('+(data.deploymentLog.totalPages)+')">&gt;&gt;</a></li>');			
		}
	}

}

function setUpcomingOverDueServiceReminderList(data) {
	$('#dataType').text('UpComing OverDue Service Reminder List');
	$("#count").text(data.totalCount);
	$("#VendorPaymentTable").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th class="fit ar">');
	var th2		= $('<th class="fit ar">');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th class="fit ar">');
	var th6		= $('<th class="fit ar">');
	
	if($('#groupListByVehicleNumber').val() == 'true' || $('#groupListByVehicleNumber').val() == true){
		tr1.append(th1.append("Vehicle"));
		tr1.append(th2.append("No Of Service Reminder"));
		tr1.append(th3.append("No Of Service Program"));
	}else{

		tr1.append(th1.append("No."));
		tr1.append(th2.append("Vehicle"));
		tr1.append(th3.append("Service Task And Schedule"));
		tr1.append(th4.append("Next Due"));
		tr1.append(th5.append("Subscribers"));
		tr1.append(th6.append("Action"));
	
	}

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.serviceReminderList != undefined && data.serviceReminderList.length > 0) {
		var deleteServiceReminder = false,editServiceReminder = false,createWoOnService = false;
		
		if($('#createWoOnService').val() != undefined && $('#createWoOnService').val() != null && $('#createWoOnService').val() == 'true'){
			createWoOnService = true;
		}
		if($('#editServiceReminder').val() != undefined && $('#editServiceReminder').val() != null && $('#editServiceReminder').val() == 'true'){
			editServiceReminder = true;
		}
		if($('#deleteServiceReminder').val() != undefined && $('#deleteServiceReminder').val() != null && $('#deleteServiceReminder').val() == 'true'){
			deleteServiceReminder = true;
		}
		
		$('#overeDueCount').text(data.TodayOverDueServiceRemindercount);
		$('#DueSoonCount').text(data.TodayDueServiceRemindercount);
		$('#overDueTodayCount').text(data.TodaysDueServiceRemindercount);
		$('#overUpcomingDueCount').text(data.UpcomingDueServiceRemindercount);
		
		var srNo = 1;
		var serviceReminderList = data.serviceReminderList;
	
		for(var i = 0; i < serviceReminderList.length; i++) {
		
		if($('#groupListByVehicleNumber').val() == 'true' || $('#groupListByVehicleNumber').val() == true){
				var tr1 = $('<tr class="ng-scope">');
			
				var td1		= $('<td class="fit ar">');
				var td2		= $('<td class="fit ar">');
				var td3		= $('<td class="fit ar">');
				var td4		= $('<td class="fit ar">');
				tr1.append(td1.append('<a target="_blank" href="VehicleServiceDetails.in?vid='+serviceReminderList[i].vid+'">'+serviceReminderList[i].vehicle_registration+'</a>'));
				tr1.append(td2.append('<a href="#" onclick = "getOverDueReminderOfVehicle('+serviceReminderList[i].vid+');">'+serviceReminderList[i].service_id+'</a>'));
				tr1.append(td3.append('<a href="#" onclick = "getOverDueReminderOfVehicle('+serviceReminderList[i].vid+');">'+serviceReminderList[i].serviceProgramId+'</a>'));
				
				tbody.append(tr1);
				
			}else{
		
			var createWO = false;
			var createWoOnDueAndOverDue = false;
			if(serviceReminderList[i].service_remider_howtimes <= 1){
					createWO = true;
			}
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td class="fit ar">');
			var td2		= $('<td class="fit ar">');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td class="fit ar">');
			var td5		= $('<td class="fit ar">');
			var td6		= $('<td class="fit ar">');
			var nextDue = "";
			if(serviceReminderList[i].diffrent_time_days != '' && serviceReminderList[i].diffrent_meter_oddmeter != ''){
				nextDue = '<i class="fa fa-calendar-check-o"></i> '+serviceReminderList[i].diffrent_time_days+'<br> <i class="fa fa-road"></i> '+serviceReminderList[i].diffrent_meter_oddmeter;
			}else if(serviceReminderList[i].diffrent_time_days != '' && serviceReminderList[i].diffrent_meter_oddmeter == ''){
				nextDue = '<i class="fa fa-calendar-check-o"></i> '+serviceReminderList[i].diffrent_time_days;
			}else if(serviceReminderList[i].diffrent_time_days == '' && serviceReminderList[i].diffrent_meter_oddmeter != ''){
				nextDue = '<i class="fa fa-calendar-check-o"></i> '+serviceReminderList[i].diffrent_meter_oddmeter;
			}
			
			tr1.append(td1.append('<a target="_blank" href="ShowService.in?service_id='+serviceReminderList[i].service_id+'">S-'+serviceReminderList[i].service_Number+'</a>'));
			tr1.append(td2.append('<a target="_blank" href="VehicleServiceDetails.in?vid='+serviceReminderList[i].vid+'">'+serviceReminderList[i].vehicle_registration+'</a><br> '+serviceReminderList[i].vehicle_Group+''));
			tr1.append(td3.append('<b style="font-size:12px;">'+serviceReminderList[i].diffenceThrsholdOdometer+' '+serviceReminderList[i].taskAndSchedule+'</b>'));
			tr1.append(td4.append(nextDue));
			tr1.append(td5.append(serviceReminderList[i].service_subscribeduser_name));
			if(serviceReminderList[i].diffenceThrsholdOdometer != ''){
				createWO = true;
				createWoOnDueAndOverDue = true;
			}
			var action = '<div class="btn-group">'
				+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
				+'<ul class="dropdown-menu pull-right">';
				if(createWoOnService && createWO && createWoOnDueAndOverDue){
					action+= '<li><a href="createWorkOrder.in?service_id='+serviceReminderList[i].service_id+'"><i class="fa fa-edit"></i> Create WorkOrder</a></li>';
				}else if(createWoOnService && !createWO){
					action+= '<li><span class="label label-warning"><i class="fa fa-dot-circle-o"></i>WorkOrder Created</span></li>';
				}
				
				if(editServiceReminder){
					action+= '<li><a href="editServiceReminderEntry.in?service_id='+serviceReminderList[i].service_id+'"><i class="fa fa-edit"></i> Edit</a></li>';
				}
				if(deleteServiceReminder){
					action+= '<li><a href="#" class="confirmation" onclick="deleteServiceReminder('+serviceReminderList[i].service_id+');"><span class="fa fa-trash"></span> Delete</a></li>';
				}
				action+= '</ul>'
				+'</div>'
				
			tr1.append(td6.append(action));

			tbody.append(tr1);
			srNo ++;
		}
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
		$("#navigationBar").append('<li><a href="#" onclick="showOverDueServiceList(1)">&lt;&lt;</a></li>');		
	}
	
	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="showOverDueServiceList('+(data.currentIndex - 1)+')">&lt;</a></li>');
	}
	if(data.currentIndex == 1){
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	}else{
		$("#navigationBar").append('<li><a href="#" onclick="showTodaysOverDueServiceList'+(data.currentIndex - 1)+')">&lt;</a></li>');
	}
	if(data.currentIndex == 1){
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	}else{
		$("#navigationBar").append('<li><a href="#" onclick="showUpcomingOverDueServiceList'+(data.currentIndex - 1)+')">&lt;</a></li>');
	}
	for (i = data.beginIndex; i <= data.endIndex; i++) {
	    if(i == data.currentIndex) {
	    	$("#navigationBar").append('<li class="active"><a href="#" onclick="showOverDueServiceList('+i+')">'+i+'</a></li>');	    	
	    } else {
	    	$("#navigationBar").append('<li><a href="#" onclick="showOverDueServiceList('+i+')">'+i+'</a></li>');	    	
	    }
	} 
	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="showOverDueServiceList('+(data.currentIndex + 1)+')">&gt;</a></li>');			
		}
	}
	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="showOverDueServiceList('+(data.deploymentLog.totalPages)+')">&gt;&gt;</a></li>');			
		}
	}

}

function setServiceReminderList(data) {
	
	$("#allServiceCount").text(data.totalCount);
	$("#VendorPaymentTable").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th class="fit ar">');
	var th2		= $('<th class="fit ar">');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th class="fit ar">');
	var th6		= $('<th class="fit ar">');
	
	if($('#groupListByVehicleNumber').val() == 'true' || $('#groupListByVehicleNumber').val() == true){
		tr1.append(th1.append("Vehicle"));
		tr1.append(th2.append("No Of Service Reminder"));
		tr1.append(th3.append("No Of Service Program"));
	}else{
		tr1.append(th1.append("No."));
		tr1.append(th2.append("Vehicle"));
		tr1.append(th3.append("Service Task And Schedule"));
		tr1.append(th4.append("Next Due"));
		tr1.append(th5.append("Subscribers"));
		tr1.append(th6.append("Action"));
	}
	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.serviceReminderList != undefined && data.serviceReminderList.length > 0) {
		var deleteServiceReminder = false,editServiceReminder = false,createWoOnService = false, createWoOnDueAndOverDue = false;
		
		if($('#createWoOnService').val() != undefined && $('#createWoOnService').val() != null && $('#createWoOnService').val() == 'true'){
			createWoOnService = true;
		}
		if($('#editServiceReminder').val() != undefined && $('#editServiceReminder').val() != null && $('#editServiceReminder').val() == 'true'){
			editServiceReminder = true;
		}
		if($('#deleteServiceReminder').val() != undefined && $('#deleteServiceReminder').val() != null && $('#deleteServiceReminder').val() == 'true'){
			deleteServiceReminder = true;
		}
		$('#overeDueCount').text(data.TodayOverDueServiceRemindercount);
		$('#DueSoonCount').text(data.TodayDueServiceRemindercount);
		$('#overDueTodayCount').text(data.TodaysDueServiceRemindercount);
		$('#overUpcomingDueCount').text(data.UpcomingDueServiceRemindercount)
		
		
		var srNo = 1;
		var serviceReminderList = data.serviceReminderList;
	
		for(var i = 0; i < serviceReminderList.length; i++) {
		
			if($('#groupListByVehicleNumber').val() == 'true' || $('#groupListByVehicleNumber').val() == true){
				var tr1 = $('<tr class="ng-scope">');
			
				var td1		= $('<td class="fit ar">');
				var td2		= $('<td class="fit ar">');
				var td3		= $('<td class="fit ar">');
				var td4		= $('<td class="fit ar">');
				tr1.append(td1.append('<a target="_blank" href="VehicleServiceDetails.in?vid='+serviceReminderList[i].vid+'">'+serviceReminderList[i].vehicle_registration+'</a>'));
				tr1.append(td2.append('<a href="#" onclick = "getServiceRminderOfVehicle('+serviceReminderList[i].vid+');">'+serviceReminderList[i].service_id+'</a>'));
				tr1.append(td3.append('<a href="#" onclick = "getServiceRminderOfVehicle('+serviceReminderList[i].vid+');">'+serviceReminderList[i].serviceProgramId+'</a>'));
				
				tbody.append(tr1);
				
			}else{
				var createWO = false;
				createWoOnDueAndOverDue = false;
			if(serviceReminderList[i].service_remider_howtimes <= 1){
					createWO = true;
			}
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td class="fit ar">');
			var td2		= $('<td class="fit ar">');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td class="fit ar">');
			var td5		= $('<td class="fit ar">');
			var td6		= $('<td class="fit ar">');
			var nextDue = "";
			if(serviceReminderList[i].diffrent_time_days != '' && serviceReminderList[i].diffrent_meter_oddmeter != ''){
				nextDue = '<i class="fa fa-calendar-check-o"></i> '+serviceReminderList[i].diffrent_time_days+'<br> <i class="fa fa-road"></i> '+serviceReminderList[i].diffrent_meter_oddmeter;
			}else if(serviceReminderList[i].diffrent_time_days != '' && serviceReminderList[i].diffrent_meter_oddmeter == ''){
				nextDue = '<i class="fa fa-calendar-check-o"></i> '+serviceReminderList[i].diffrent_time_days;
			}else if(serviceReminderList[i].diffrent_time_days == '' && serviceReminderList[i].diffrent_meter_oddmeter != ''){
				nextDue = '<i class="fa fa-calendar-check-o"></i> '+serviceReminderList[i].diffrent_meter_oddmeter;
			}
			
			tr1.append(td1.append('<a target="_blank" href="ShowService.in?service_id='+serviceReminderList[i].service_id+'">S-'+serviceReminderList[i].service_Number+'</a>'));
			tr1.append(td2.append('<a target="_blank" href="VehicleServiceDetails.in?vid='+serviceReminderList[i].vid+'">'+serviceReminderList[i].vehicle_registration+'</a><br> '+serviceReminderList[i].vehicle_Group+''));
			tr1.append(td3.append('<b style="font-size:12px;">'+serviceReminderList[i].diffenceThrsholdOdometer+' '+serviceReminderList[i].taskAndSchedule+'</b>'));
			tr1.append(td4.append(nextDue));
			tr1.append(td5.append(serviceReminderList[i].service_subscribeduser_name));
			if(serviceReminderList[i].diffenceThrsholdOdometer != ''){
				createWO = true;
			}
			if(serviceReminderList[i].diffenceThrsholdOdometer != null && serviceReminderList[i].diffenceThrsholdOdometer !== ''){
				createWoOnDueAndOverDue = true;
			}	
			var action = '<div class="btn-group">'
				+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
				+'<ul class="dropdown-menu pull-right">';
				if(createWoOnService && createWO && createWoOnDueAndOverDue){
					action+= '<li><a href="createWorkOrder.in?service_id='+serviceReminderList[i].service_id+'"><i class="fa fa-edit"></i> Create WorkOrder</a></li>';
				}else if(createWoOnService && !createWO){
					action+= '<li><span class="label label-warning"><i class="fa fa-dot-circle-o"></i>WorkOrder Created</span></li>';
				}
				
				if(editServiceReminder){
					action+= '<li><a href="editServiceReminderEntry.in?service_id='+serviceReminderList[i].service_id+'"><i class="fa fa-edit"></i> Edit</a></li>';
				}
				if(deleteServiceReminder){
					action+= '<li><a href="#" class="confirmation" onclick="deleteServiceReminder('+serviceReminderList[i].service_id+');"><span class="fa fa-trash"></span> Delete</a></li>';
				}
				action+= '</ul>'
				+'</div>'
				
			tr1.append(td6.append(action));

			tbody.append(tr1);
			srNo ++;
			}
		
			
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
		$("#navigationBar").append('<li><a href="#" onclick="showList(1)">&lt;&lt;</a></li>');		
	}
	
	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="showList('+(data.currentIndex - 1)+')">&lt;</a></li>');
	}
	
	for (i = data.beginIndex; i <= data.endIndex; i++) {
	    if(i == data.currentIndex) {
	    	$("#navigationBar").append('<li class="active"><a href="#" onclick="showList('+i+')">'+i+'</a></li>');	    	
	    } else {
	    	$("#navigationBar").append('<li><a href="#" onclick="showList('+i+')">'+i+'</a></li>');	    	
	    }
	} 
	
	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="showList('+(data.currentIndex + 1)+')">&gt;</a></li>');			
		}
	}

	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="showList('+(data.deploymentLog.totalPages)+')">&gt;&gt;</a></li>');			
		}
	}
}
function deleteServiceProgram(vehicleServiceProgramId){

	var jsonObject						= new Object();
	jsonObject["vehicleServiceProgramId"]		= vehicleServiceProgramId;
	
	showLayer();
	$.ajax({
		url: "deleteServiceProgram",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.serviceScheduleExit != undefined && data.serviceScheduleExit == true){
				showMessage('info', 'Cannot Delete , Please Remove All Service Schedule First !');
				hideLayer();
			}
			
			if(data.success != undefined && data.success == true){
				showMessage('success', 'Data Deleted Successfully !');
				location.reload();
			}
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});

}

function editServiceProgram(vehicleServiceProgramId){

	var jsonObject						= new Object();
	jsonObject["vehicleServiceProgramId"]		= vehicleServiceProgramId;
	
	showLayer();
	$.ajax({
		url: "getVehicleServiceProgramDetailsById",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			$('#programNameEdit').val(data.serviceProgram.programName);
			$('#descriptionEdit').val(data.serviceProgram.description);
			$('#vehicleServiceProgramId').val(vehicleServiceProgramId);
			
			if(data.serviceProgram.vendorProgram){
				$('#vendorEditYes').addClass('btn btn-default btn-on btn-lg active');
				$('#vendorEditNo').addClass('btn btn-default btn-on btn-lg');
				document.getElementById('vendorProgramYes').checked = true;
				document.getElementById('vendorProgramNo').checked = false;
			}else{
				$('#vendorEditYes').addClass('btn btn-default btn-on btn-lg');
				$('#vendorEditNo').addClass('btn btn-default btn-on btn-lg active');
				document.getElementById('vendorProgramYes').checked = false;
				document.getElementById('vendorProgramNo').checked = true;
			}
			
			
			
			$('#EditServiceProgram').modal('show');
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});


}

function addServiceProgram(){
	$('#addServiceProgram').modal('show');
}
function saveServiceProgram(){

	if($('#programName').val() == null || $('#programName').val().trim() == ''){
		$('#programName').focus();
		showMessage('info', 'Please select Program name !');
		return false;
	}
	
	var jsonObject					= new Object();
	jsonObject["programName"]		= $('#programName').val();
	jsonObject["description"]		= $('#description').val();
	jsonObject["companyId"]			= $('#companyId').val();
	jsonObject["userId"]			= $('#userId').val();
	if(document.getElementById('vendorProgramYes') != null && document.getElementById('vendorProgramYes').checked){
		jsonObject["vendorProgram"]		= true;
	}else{
		jsonObject["vendorProgram"]		= false;
	}
	
	showLayer();
	$.ajax({
		url: "saveServiceProgram",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.noProgramName != undefined && data.noProgramName){
				showMessage('errors', 'Please select Program name !');	
				return false;
			}
			
			location.reload();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});

}
function updateServiceProgram(){

	if($('#programNameEdit').val() == null || $('#programNameEdit').val().trim() == ''){
		$('#programNameEdit').focus();
		showMessage('info', 'Please select Program name !');
		return false;
	}
	
	var jsonObject					= new Object();
	jsonObject["vehicleServiceProgramId"]		= $('#vehicleServiceProgramId').val();
	jsonObject["programName"]		= $('#programNameEdit').val();
	jsonObject["description"]		= $('#descriptionEdit').val();
	jsonObject["companyId"]			= $('#companyId').val();
	jsonObject["userId"]			= $('#userId').val();
	if(document.getElementById('vendorProgramYesEdit') != null && document.getElementById('vendorProgramYesEdit').checked){
		jsonObject["vendorProgram"]		= true;
	}else{
		jsonObject["vendorProgram"]		= false;
	}
	
	showLayer();
	$.ajax({
		url: "updateServiceProgram",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.noProgramName != undefined && data.noProgramName){
				showMessage('errors', 'Please select Program name !');	
				return false;
			}
			
			location.reload();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});

}

function setDueSoonServiceReminderList(data) {

	$('#dataType').text('DueSoon Service Reminder List');
	$("#count").text(data.totalCount);
	$("#VendorPaymentTable").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th class="fit ar">');
	var th2		= $('<th class="fit ar">');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th class="fit ar">');
	var th6		= $('<th class="fit ar">');
	
	if($('#groupListByVehicleNumber').val() == 'true' || $('#groupListByVehicleNumber').val() == true){
		tr1.append(th1.append("Vehicle"));
		tr1.append(th2.append("No Of Service Reminder"));
		tr1.append(th3.append("No Of Service Program"));
	}else{

		tr1.append(th1.append("No."));
		tr1.append(th2.append("Vehicle"));
		tr1.append(th3.append("Service Task And Schedule"));
		tr1.append(th4.append("Next Due"));
		tr1.append(th5.append("Subscribers"));
		tr1.append(th6.append("Action"));
	}

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.serviceReminderList != undefined && data.serviceReminderList.length > 0) {
		var deleteServiceReminder = false,editServiceReminder = false,createWoOnService = false;
		
		if($('#createWoOnService').val() != undefined && $('#createWoOnService').val() != null && $('#createWoOnService').val() == 'true'){
			createWoOnService = true;
		}
		if($('#editServiceReminder').val() != undefined && $('#editServiceReminder').val() != null && $('#editServiceReminder').val() == 'true'){
			editServiceReminder = true;
		}
		if($('#deleteServiceReminder').val() != undefined && $('#deleteServiceReminder').val() != null && $('#deleteServiceReminder').val() == 'true'){
			deleteServiceReminder = true;
		}
		
		$('#overeDueCount').text(data.TodayOverDueServiceRemindercount);
		$('#DueSoonCount').text(data.TodayDueServiceRemindercount);
		$("#overDueTodayCount").text(data.TodaysDueServiceRemindercount);
		$('#overUpcomingDueCount').text(data.UpcomingDueServiceRemindercount);
		
		var srNo = 1;
		var serviceReminderList = data.serviceReminderList;
	
		for(var i = 0; i < serviceReminderList.length; i++) {
		
			if($('#groupListByVehicleNumber').val() == 'true' || $('#groupListByVehicleNumber').val() == true){
				var tr1 = $('<tr class="ng-scope">');
			
				var td1		= $('<td class="fit ar">');
				var td2		= $('<td class="fit ar">');
				var td3		= $('<td class="fit ar">');
				var td4		= $('<td class="fit ar">');
				tr1.append(td1.append('<a target="_blank" href="VehicleServiceDetails.in?vid='+serviceReminderList[i].vid+'">'+serviceReminderList[i].vehicle_registration+'</a>'));
				tr1.append(td2.append('<a href="#" onclick = "getDueSoonReminderOfVehicle('+serviceReminderList[i].vid+');">'+serviceReminderList[i].service_id+'</a>'));
				tr1.append(td3.append('<a href="#" onclick = "getDueSoonReminderOfVehicle('+serviceReminderList[i].vid+');">'+serviceReminderList[i].serviceProgramId+'</a>'));
				
				tbody.append(tr1);
				
			}else{
				var createWO = false;
				var createWoOnDueAndOverDue = false;
				if(serviceReminderList[i].service_remider_howtimes <= 1){
						createWO = true;
				}
				var tr1 = $('<tr class="ng-scope">');
				
				var td1		= $('<td class="fit ar">');
				var td2		= $('<td class="fit ar">');
				var td3		= $('<td class="fit ar">');
				var td4		= $('<td class="fit ar">');
				var td5		= $('<td class="fit ar">');
				var td6		= $('<td class="fit ar">');
				var nextDue = "";
				if(serviceReminderList[i].diffrent_time_days != '' && serviceReminderList[i].diffrent_meter_oddmeter != ''){
					nextDue = '<i class="fa fa-calendar-check-o"></i> '+serviceReminderList[i].diffrent_time_days+'<br> <i class="fa fa-road"></i> '+serviceReminderList[i].diffrent_meter_oddmeter;
				}else if(serviceReminderList[i].diffrent_time_days != '' && serviceReminderList[i].diffrent_meter_oddmeter == ''){
					nextDue = '<i class="fa fa-calendar-check-o"></i> '+serviceReminderList[i].diffrent_time_days;
				}else if(serviceReminderList[i].diffrent_time_days == '' && serviceReminderList[i].diffrent_meter_oddmeter != ''){
					nextDue = '<i class="fa fa-calendar-check-o"></i> '+serviceReminderList[i].diffrent_meter_oddmeter;
				}
				
				tr1.append(td1.append('<a target="_blank" href="ShowService.in?service_id='+serviceReminderList[i].service_id+'">S-'+serviceReminderList[i].service_Number+'</a>'));
				tr1.append(td2.append('<a target="_blank" href="VehicleServiceDetails.in?vid='+serviceReminderList[i].vid+'">'+serviceReminderList[i].vehicle_registration+'</a><br> '+serviceReminderList[i].vehicle_Group+''));
				tr1.append(td3.append('<b style="font-size:12px;">'+serviceReminderList[i].diffenceThrsholdOdometer+' '+serviceReminderList[i].taskAndSchedule+'</b>'));
				tr1.append(td4.append(nextDue));
				tr1.append(td5.append(serviceReminderList[i].service_subscribeduser_name));
				if(serviceReminderList[i].diffenceThrsholdOdometer != ''){
					createWO = true;
					createWoOnDueAndOverDue = true;
				}
				var action = '<div class="btn-group">'
					+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
					+'<ul class="dropdown-menu pull-right">';
					if(createWoOnService && createWO && createWoOnDueAndOverDue){
						action+= '<li><a href="createWorkOrder.in?service_id='+serviceReminderList[i].service_id+'"><i class="fa fa-edit"></i> Create WorkOrder</a></li>';
					}else if(createWoOnService && !createWO){
						action+= '<li><span class="label label-warning"><i class="fa fa-dot-circle-o"></i>WorkOrder Created</span></li>';
					}
					
					if(editServiceReminder){
						action+= '<li><a href="editServiceReminderEntry.in?service_id='+serviceReminderList[i].service_id+'"><i class="fa fa-edit"></i> Edit</a></li>';
					}
					if(deleteServiceReminder){
						action+= '<li><a href="#" class="confirmation" onclick="deleteServiceReminder('+serviceReminderList[i].service_id+');"><span class="fa fa-trash"></span> Delete</a></li>';
					}
					action+= '</ul>'
					+'</div>'
					
				tr1.append(td6.append(action));
	
				tbody.append(tr1);
				srNo ++;
				}
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#VendorPaymentTable").append(thead);
	$("#VendorPaymentTable").append(tbody);
	
	$("#navigationBar").empty();

}

function getServiceRminderOfVehicle(vid){
	var jsonObject					= new Object();
	jsonObject["companyId"]			= $('#companyId').val();
	jsonObject["userId"]			= $('#userId').val();
	jsonObject["vid"]				= vid;
	showLayer();
	$.ajax({
		url: "getServiceReminderOfVehicle",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
		setVehicleServiceReminderList(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function getOverDueReminderOfVehicle(vid){
	var jsonObject					= new Object();
	jsonObject["companyId"]			= $('#companyId').val();
	jsonObject["userId"]			= $('#userId').val();
	jsonObject["vid"]				= vid;
	showLayer();
	$.ajax({
		url: "getOverDueReminderOfVehicle",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
		setVehicleServiceReminderList(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function getDueSoonReminderOfVehicle(vid){
	var jsonObject					= new Object();
	jsonObject["companyId"]			= $('#companyId').val();
	jsonObject["userId"]			= $('#userId').val();
	jsonObject["vid"]				= vid;
	showLayer();
	$.ajax({
		url: "getDueSoonReminderOfVehicle",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
		setVehicleServiceReminderList(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setVehicleServiceReminderList(data){
	$("#vehiclereminderbody").empty();
	
	var tr1 = $('<tr>');

	var th1		= $('<th class="fit ar">');
	var th2		= $('<th class="fit ar">');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th class="fit ar">');
	var th6		= $('<th class="fit ar">');

	
	
	if(data.serviceReminderList != undefined && data.serviceReminderList.length > 0) {
		var deleteServiceReminder = false,editServiceReminder = false,createWoOnService = false,skipServiceReminder=false,createDSEOnService=false,createWoOnDueAndOverDue = false;;
		
		if($('#createWoOnService').val() != undefined && $('#createWoOnService').val() != null && $('#createWoOnService').val() == 'true'){
			createWoOnService = true;
		}
		if($('#editServiceReminder').val() != undefined && $('#editServiceReminder').val() != null && $('#editServiceReminder').val() == 'true'){
			editServiceReminder = true;
		}
		
		if($('#deleteServiceReminder').val() != undefined && $('#deleteServiceReminder').val() != null && $('#deleteServiceReminder').val() == 'true'){
			deleteServiceReminder = true;
		}
		if($('#skipServiceReminder').val() != undefined && $('#skipServiceReminder').val() != null && $('#skipServiceReminder').val() == 'true'){
			skipServiceReminder = true;
		}
		if($('#createDSEOnService').val() != undefined && $('#createDSEOnService').val() != null && $('#createDSEOnService').val() == 'true'){
			createDSEOnService = true;
		}
		
		var srNo = 1;
		
		
		if(data.reminderHM != undefined){
			for (var key in data.reminderHM) {
				var serviceReminderList = data.reminderHM[key];
  				
  				var groupTr = 	$('<tr class="ng-scope">');
  				var groupTd	= $('<td colspan="9" style="font-size:16px;" class="fit ar">');
  				groupTr.append(groupTd.append('<a href="#"> '+key+' <span data-toggle="tip" title="" class="badge bg-yellow" data-original-title=" Count ">'+serviceReminderList.length+'</span></a>'));	
				$('#vehiclereminderbody').append(groupTr);
  				
  				for(var i = 0; i < serviceReminderList.length; i++) {
		
				var createWO = false;
				if(serviceReminderList[i].service_remider_howtimes <= 1){
						createWO = true;
				}
			
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td class="fit ar">');
			var td2		= $('<td style="width:10%;" class="fit ar">');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td class="fit ar">');
			var td5		= $('<td class="fit ar">');
			var td6		= $('<td class="fit ar">');
			var td7		= $('<td class="fit ar">');
			var td8		= $('<td class="fit ar">');
			var td9		= $('<td class="fit ar">');
			var td10	= $('<td class="fit ar">');
			
			
			
			var nextDue = "";
			if(serviceReminderList[i].diffrent_time_days != '' && serviceReminderList[i].diffrent_meter_oddmeter != ''){
				nextDue = '<i class="fa fa-calendar-check-o"></i> '+serviceReminderList[i].diffrent_time_days+'<br> <i class="fa fa-road"></i> '+serviceReminderList[i].diffrent_meter_oddmeter;
			}else if(serviceReminderList[i].diffrent_time_days != '' && serviceReminderList[i].diffrent_meter_oddmeter == ''){
				nextDue = '<i class="fa fa-calendar-check-o"></i> '+serviceReminderList[i].diffrent_time_days;
			}else if(serviceReminderList[i].diffrent_time_days == '' && serviceReminderList[i].diffrent_meter_oddmeter != ''){
				nextDue = '<i class="fa fa-calendar-check-o"></i> '+serviceReminderList[i].diffrent_meter_oddmeter;
			}
			
			tr1.append(td1.append('<a target="_blank" href="ShowService.in?service_id='+serviceReminderList[i].service_id+'">S-'+serviceReminderList[i].service_Number+'</a>'));
			tr1.append(td2.append('<a target="_blank" href="VehicleServiceDetails.in?vid='+serviceReminderList[i].vid+'">'+serviceReminderList[i].vehicle_registration+'</a>'));
			tr1.append(td4.append('<b style="font-size:12px;">'+serviceReminderList[i].diffenceThrsholdOdometer+' '+serviceReminderList[i].taskAndSchedule+'</b>'));
			tr1.append(td5.append(nextDue));
			if(serviceReminderList[i].diffenceThrsholdOdometer != ''){
				createWO = true;
			}
			var action = '<div class="btn-group">'
				+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
				+'<ul class="dropdown-menu pull-right">';
				if(serviceReminderList[i].diffenceThrsholdOdometer != ''){
					createWoOnDueAndOverDue = true;
				}
				if(createWoOnService && createWO && createWoOnDueAndOverDue){
					action+= '<li><a href="createWorkOrder.in?service_id='+serviceReminderList[i].service_id+'"><i class="fa fa-edit"></i> Create WorkOrder</a></li>';
				}
				if(createDSEOnService && createWO && createWoOnDueAndOverDue){
				action+= '<li><a href="createDealerServiceEntries?serviceReminder='+serviceReminderList[i].service_id+'"><i class="fa fa-edit"></i> Create DSE</a></li>';
				}
				if(createWoOnService && !createWO){
					action+= '<li><span class="label label-warning"><i class="fa fa-dot-circle-o"></i>WorkOrder/DSE/Skipped</span></li>';
				}
				if(skipServiceReminder && createWO){
					action+= '<li><a href="#" class="confirmation" onclick="skipRemarkModal('+serviceReminderList[i].service_id+');"><span class="fa fa-fast-forward"></span> Skip </a></li>';
				}
				if(editServiceReminder){
					action+= '<li><a href="editServiceReminderEntry.in?service_id='+serviceReminderList[i].service_id+'"><i class="fa fa-edit"></i> Edit</a></li>';
				}
				if(deleteServiceReminder){
					action+= '<li><a href="#" class="confirmation" onclick="deleteServiceReminder('+serviceReminderList[i].service_id+');"><span class="fa fa-trash"></span> Delete</a></li>';
				}
				action+= '</ul>'
				+'</div>'
				tr1.append(td6.append(serviceReminderList[i].vehicle_currentOdometer));
				tr1.append(td7.append(serviceReminderList[i].meter_threshold));
				tr1.append(td8.append(serviceReminderList[i].meter_servicethreshold_odometer));
				tr1.append(td9.append(serviceReminderList[i].meter_serviceodometer));
				tr1.append(td10.append(action));
				
			
			$('#vehiclereminderbody').append(tr1);
			srNo ++;
			
		}
			}	
			
		}
	

	}else{
		showMessage('info','No record found !')
	}
	
	//$("#vehicleReminderTab").append(tbody);
	
	$("#vehicleReminder").modal('show');
	
}
