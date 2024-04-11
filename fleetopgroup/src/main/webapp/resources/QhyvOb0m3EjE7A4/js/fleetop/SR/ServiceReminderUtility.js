function searchServiceReminderByNumber(){
	showLayer();
	var jsonObject						= new Object();
	jsonObject["companyId"]				= $('#companyId').val();
	jsonObject["userId"]				= $('#userId').val();
	jsonObject["serviceNumber"]			= $('#serviceNumber').val();
	$.ajax({
		url: "searchServiceReminderByNumber",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			if(data.service_id != undefined && data.service_id > 0){
				window.location.replace("ShowService.in?service_id="+data.service_id+"");
			}else{
				showMessage('errors', 'No Record Found !');
			}
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			console.log(e);
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}
function searchServiceByNumberOnEnter(e){
	var code = (e.keyCode ? e.keyCode : e.which);
	if(code == 13) { //Enter keycode
		searchServiceReminderByNumber();
	}
}

function searchServiceReminderByMultiple(){
	showLayer();
	var jsonObject						= new Object();
	jsonObject["companyId"]				= $('#companyId').val();
	jsonObject["userId"]				= $('#userId').val();
	jsonObject["serviceNumber"]			= $('#serviceMultipleSearch').val();

	$.ajax({
		url: "searchServiceReminderByMultiple",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setSearchServiceReminderList(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			console.log(e);
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}
function searchServiceByMultipleOnEnter(e){
	var code = (e.keyCode ? e.keyCode : e.which);
	if(code == 13) {
		searchServiceReminderByMultiple();
	}
}

function setSearchServiceReminderList(data) {
	$('#dataType').text('Searched Service Reminder List');
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

	tr1.append(th1.append("No."));
	tr1.append(th2.append("Vehicle"));
	tr1.append(th3.append("Service Task And Schedule"));
	tr1.append(th4.append("Next Due"));
	tr1.append(th5.append("Subscribers"));
	tr1.append(th6.append("Action"));

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
		
		var srNo = 1;
		var serviceReminderList = data.serviceReminderList;
	
		for(var i = 0; i < serviceReminderList.length; i++) {
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
	}else{
		showMessage('info','No record found !')
	}
	
	$("#VendorPaymentTable").append(thead);
	$("#VendorPaymentTable").append(tbody);
	
	$("#navigationBar").empty();

}

function deleteServiceReminder(service_id){
	
	Swal.fire({
		  title: 'Are you sure to Delete?',
		  text: "You won't be able to revert this!",
		  icon: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  customClass:'swal-wide',
		  confirmButtonText: 'Yes, delete it!'
		}).then((result) => {
		  if (result.value) {
			  showLayer();
				var jsonObject						= new Object();
				jsonObject["companyId"]				= $('#companyId').val();
				jsonObject["userId"]				= $('#userId').val();
				jsonObject["service_id"]			= service_id;
				$.ajax({
					url: "deleteServiceReminderById",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						if(data.delete != undefined && data.delete ){
							hideLayer();
							showAlert('success', 'Data Deleted Sucessfully');
							setTimeout(function(){ 
								window.location.replace("ViewServiceReminderList.in?delete=true");
							}, 2000);
						}else{
							showAlert('error', 'Data Deletion Failed !');
						}
						
					},
					error: function (e) {
						hideLayer();
						console.log(e);
						showMessage('errors', 'Some Error Occurred!');
					}
				});
		  }
		})
	
	/**/
}

function skipRemarkModal(id){
	
	$('#skipSrRemark').modal('show');
	$('#skipSrId').val(id);
	
}
function skipServiceReminder(){
	var remark = $('#skipRemark').val();
	if(remark == null || remark.trim() == ''){
		$('#skipRemark').focus();
		showMessage('warning','Please enter Remark !');
		return false;
	}
	showLayer();
	var jsonObject							= new Object();
	jsonObject["skipSrId"]					= $('#skipSrId').val();
	jsonObject["skipRemark"]				=  remark;
	
	$.ajax({
		url: "skipServiceReminderWithRemark",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.skip == true || data.skip === 'true'){
				hideLayer();
				showMessage('success', 'Reminder Skipped');
				location.reload();
			}
		},
		error: function (e) {
			console.log("Error : " , e);
			showMessage('errors', 'Some Error Occurred!');
			hideLayer();
		}
	});
}

