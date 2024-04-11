$(document).ready(function() {
	showList(1);
});

function showList(pagenumber){
	
	var jsonObject					= new Object();
	jsonObject["pagenumber"]		= pagenumber;
	jsonObject["companyId"]						= $('#companyId').val();
	jsonObject["userId"]						= $('#userId').val();
	showLayer();
	$.ajax({
		url: "getServiceProgramList",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setServiceProgramList(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setServiceProgramList(data) {
	$("#count").text(data.totalCount);
	$("#allServiceCount").text(data.totalSRCount);
	$('#overeDueCount').text(data.TodayOverDueServiceRemindercount);
	$('#DueSoonCount').text(data.TodayDueServiceRemindercount);
	
	$("#VendorPaymentTable").empty();
	
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th class="fit ar">');
	var th2		= $('<th class="fit ar">');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th class="fit ar">');
	var th6		= $('<th class="fit ar">');

//	tr1.append(th1.append("SR No."));
	tr1.append(th2.append("Name"));
	tr1.append(th3.append("Schedule Count"));
	tr1.append(th6.append("Service Reminder Count"));
	tr1.append(th4.append("Description"));
	tr1.append(th5.append("Action"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.serviceProgram != undefined && data.serviceProgram.length > 0) {
//		var srNo = 1;
		var serviceProgram = data.serviceProgram;
	
		for(var i = 0; i < serviceProgram.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
//			var td1		= $('<td class="fit ar">');
			var td2		= $('<td class="fit ar">');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td class="fit ar">');
			var td5		= $('<td class="fit ar">');
			var td6		= $('<td class="fit ar">');
			
//			tr1.append(td1.append(srNo));
			tr1.append(td2.append('<a href="viewServiceProgram?Id='+serviceProgram[i].vehicleServiceProgramId+'">'+serviceProgram[i].programName+'</a> '));
			var schCount = 0;
			if(data.scheduleCountHM != undefined && data.scheduleCountHM[serviceProgram[i].vehicleServiceProgramId] != undefined ){
				schCount = data.scheduleCountHM[serviceProgram[i].vehicleServiceProgramId];
			}
			if(schCount > 0){
				tr1.append(td3.append( '<a href="#" onclick="getServiceSheduleList('+serviceProgram[i].vehicleServiceProgramId+')">'+schCount+'</a> '));
			}else{
				tr1.append(td3.append( '<a href="#">'+schCount+'</a> '));
			}
			if(schCount > 0){
				tr1.append(td6.append( '<a href="#" onclick="getServiceSheduleList('+serviceProgram[i].vehicleServiceProgramId+')">'+serviceProgram[i].serviceReminderCount+'</a> '));
			}else{
				tr1.append(td6.append( '<a href="#">'+serviceProgram[i].serviceReminderCount+'</a> '));
			}
			
			tr1.append(td4.append(serviceProgram[i].description));
			if(!serviceProgram[i].vendorProgram || Number($('#companyId').val()) == 4){
				tr1.append(td5.append(
						'<div class="btn-group">'
						+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
						+'<ul class="dropdown-menu pull-right">'
						+'<li><a href="viewServiceProgram?Id='+serviceProgram[i].vehicleServiceProgramId+'"><i class="fa fa-plus"></i> View Service Schedule</a></li>'
						+'<li><a href="#" onclick="editServiceProgram('+serviceProgram[i].vehicleServiceProgramId+');"><i class="fa fa-edit"></i> Edit</a></li>'
						+'<li><a href="#" class="confirmation" onclick="deleteServiceProgram('+serviceProgram[i].vehicleServiceProgramId+')"><span class="fa fa-trash"></span> Delete</a></li>'
						+'</ul>'
						+'</div>'
				));
			}else{
				tr1.append(td5.append(
						'<div class="btn-group">'
						+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
						+'<ul class="dropdown-menu pull-right">'
						+'<li><span onclick="showVendorProgramAlert();" class="label label-warning"><i class="fa fa-dot-circle-o"></i>Vendor Service Program</span></li>'
						+'</ul>'
						+'</div>'
				));
			
			}

			tbody.append(tr1);
//			srNo ++;
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
function showVendorProgramAlert(){
	showAlert('info','This is a vendor service program , you can not make any changes to it');
}
function deleteServiceProgram(vehicleServiceProgramId){
	Swal.fire({
		  title: 'Are you sure to Delete?',
		  text: "You won't be able to revert this , This Delete will have no effect on previously created service reminders on this program !",
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
				jsonObject["vehicleServiceProgramId"]		= vehicleServiceProgramId;
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
		})
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
				if(document.getElementById('vendorProgramYes') != null)
				  document.getElementById('vendorProgramYes').checked = false;
				if(document.getElementById('vendorProgramNo') != null)
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
	

	Swal.fire({
		  title: 'Are you sure to Update?',
		  text: "",
		  icon: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  customClass:'swal-wide',
		  confirmButtonText: 'Yes, Update it!'
		}).then((result) => {
		  if (result.value) {
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
		})
}

function getServiceSheduleList(vehicleServiceProgramId){
	var jsonObject						= new Object();
	jsonObject["vehicleServiceProgramId"]		= vehicleServiceProgramId;
	
	showLayer();
	$.ajax({
		url: "getVehicleSheduleProgramListById",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.serviceSchedules != undefined && data.serviceSchedules != null){
				setServiceSheduleList(data);
			}
			

			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setServiceSheduleList(data){
	if(data.serviceSchedules != null && data.serviceSchedules.length > 0){
		$('#serviceScheduleList').modal('show');
		$('#dataTable').show();
		$("#serviceScheduleListBody").empty();
		var srNo = 1;
		var totalSr	= 0;
		for(var i = 0; i< data.serviceSchedules.length; i++){
			var serviceSchedules = data.serviceSchedules[i];
			
			totalSr	+= serviceSchedules.serviceReminderCount;
			
			var tr =' <tr data-object-id="">'
				+'<td class="fit" value="'+srNo+'">'+srNo+'</td>'
				+'<td>'+serviceSchedules.jobType+' </td>'
				+'<td>'+serviceSchedules.jobSubType+'</td>'
				+'<td>'+serviceSchedules.timeIntervalTypeStr+'</td>'
				+'<td>'+serviceSchedules.timeThresholdStr+'</td>'
				+'<td>'+serviceSchedules.serviceType+'</td>'
				+'<td style="text-align:right;">'+ '<a href="#" onclick="getServiceReminderList('+serviceSchedules.vehicleServiceProgramId+','+serviceSchedules.serviceScheduleId+')">'+serviceSchedules.serviceReminderCount+'</a> '  +'</td>';
			     tr+= '</tr>';
			$('#serviceScheduleListBody').append(tr);
			srNo++;
		}
		var totalRow = '<tr data-object-id=""><td colspan="7" style="text-align:right;">'+totalSr+' </td></tr>';
		$('#serviceScheduleListBody').append(totalRow);
	}
	
}
function getServiceReminderList(vehicleServiceProgramId, serviceScheduleId){
	
	var jsonObject						= new Object();
	jsonObject["vehicleServiceProgramId"]		= vehicleServiceProgramId;
	jsonObject["serviceScheduleId"]				= serviceScheduleId;
	
	showLayer();
	$.ajax({
		url: "getServiceReminderByserviceScheduleId",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
				$('#exampleModalLong').modal('show');
				$('#sRdataTable').show();
			
			if(data.serviceReminderDto != undefined && data.serviceReminderDto != null){
				setServiceReminderList(data);
			}
			

			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
}

function setServiceReminderList(data){
	if(data.serviceReminderDto != null && data.serviceReminderDto.length > 0){
		$('#exampleModalLong').modal('show');
		$('#sRdataTable').show();
		$("#serviceReminderListBody").empty();
		var srNo = 1;
		for(var i = 0; i< data.serviceReminderDto.length; i++){
			var serviceSchedules = data.serviceReminderDto[i];
			
			var nextDue = "";
			if(serviceSchedules.diffrent_time_days != '' && serviceSchedules.diffrent_meter_oddmeter != ''){
				nextDue = '<i class="fa fa-calendar-check-o"></i> '+serviceSchedules.diffrent_time_days+'<br> <i class="fa fa-road"></i> '+serviceSchedules.diffrent_meter_oddmeter;
			}else if(serviceSchedules.diffrent_time_days != '' && serviceSchedules.diffrent_meter_oddmeter == ''){
				nextDue = '<i class="fa fa-calendar-check-o"></i> '+serviceSchedules.diffrent_time_days;
			}else if(serviceSchedules.diffrent_time_days == '' && serviceSchedules.diffrent_meter_oddmeter != ''){
				nextDue = '<i class="fa fa-calendar-check-o"></i> '+serviceSchedules.diffrent_meter_oddmeter;
			}
			
			var tr =' <tr data-object-id="">'
				+'<td>'+srNo+' </td>'
				+'<td class="fit" value="'+srNo+'">'+'<a target="_blank" href="ShowService.in?service_id='+serviceSchedules.service_id+'">S-'+serviceSchedules.service_Number+'</a>'+'</td>'
				//+'<td>'+'<b style="font-size:12px;">'+serviceSchedules.service_type+'</b>'+' </td>'
				//+'<td>'+'<b style="font-size:12px;">'+serviceSchedules.service_subtype+'</b>'+' </td>'
				+'<td>'+'<b style="font-size:12px;">'+serviceSchedules.diffenceThrsholdOdometer+' '+serviceSchedules.taskAndSchedule+'</b>'+' </td>'
				+'<td>'+'<b style="font-size:12px;">'+nextDue+' </td>'
				+'<td>'+'<a target="_blank" href="VehicleServiceDetails.in?vid='+serviceSchedules.vid+'">'+serviceSchedules.vehicle_registration+'</a>'+'</td>';
			     tr+= '</tr>';
			$('#serviceReminderListBody').append(tr);
			srNo++;
		}
	}
	
	
}

function showDueSoonServiceList(){
	var jsonObject						= new Object();
	
	showLayer();
	$.ajax({
		url: "getDueSoonServiceListGroupBySProgram",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			console.log('data : ', data);
			renderReportData(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
}


function renderReportData(resultData) {

	if(resultData.serviceReminderList != undefined && resultData.serviceReminderList.length > 0){
		var columnConfiguration ;
		var tableProperties;
		$('#ResultContent').show();
		$('#myGrid').empty();
		
		if(resultData.tableConfig != undefined) {
			var ColumnConfig = resultData.tableConfig.columnConfiguration;
			var columnKeys	= _.keys(ColumnConfig);
			var bcolConfig		= new Object();
			
			for (var i = 0; i < columnKeys.length; i++) {
				var bObj	= ColumnConfig[columnKeys[i]];
				
				if (bObj.show != undefined && bObj.show == true) {
					bcolConfig[columnKeys[i]] = bObj;
				}
			}
		
			columnConfiguration	= _.values(bcolConfig);
			tableProperties	=  resultData.tableConfig.tableProperties;
			
		}
		
		setSlickData(resultData.serviceReminderList, columnConfiguration, tableProperties);
		
		$('#gridContainer').show();
		$('#exampleModalLong1').modal('show');
		$('#printBtn').removeClass('hide');
	}else{
		//$('#gridContainer').hide();
		$('#ResultContent').hide();
	    $('#printBtn').addClass('hide');
	    showMessage('info', 'No record found !');
	}

}
