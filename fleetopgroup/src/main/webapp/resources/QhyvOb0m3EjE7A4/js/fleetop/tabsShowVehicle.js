$(document).ready(function(){$("ul.tabs li").click(function(){var t=$(this).attr("data-tab");$("ul.tabs li").removeClass("current"),$(".tab-content2").removeClass("current"),$(this).addClass("current"),$("#"+t).addClass("current")})});

function email(){
	$('#configureEmail').modal('show');
	
	var jsonObject		= new Object();

	$.ajax({
		url: "CompanyRestControllerWS/get_All_Company_Email.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			checkemail(data);
			hideLayer();

		},
		error: function (e) {
			console.log("Error",e);
		}
	});
	
}

function checkemail(data) {
	var email			= null;
	var input			= null;
	var emailConfigId	= 0;
	if(data.Email != undefined) {
		email	= data.Email;
		for(var i = 0; i < email.length; i++ ) {
			emailConfigId	= email[i].configurationId;
			if (input == null){
				input	 = email[i].emailIds;
			} else {
				input	 = input +","+email[i].emailIds;
			}
		}
		$('#emailId').val(input);
		//Id used for update work
		$('#configId').val(emailConfigId);
	}
}

function addEmail() {
	if(validateEmail()) {
		
		showLayer();
		
		var configId 	= $("#configId").val()
		
		
		var jsonObject					= new Object();

		jsonObject["emailId"]			= $("#emailId").val();
		jsonObject["configurationId"]	= configId;

		
		if(configId > 0){
			updateEmail(jsonObject);
		} else {
			saveEmail(jsonObject);
		}
		setTimeout(function(){ hideLayer();location.reload(); }, 500);
	}
}

function validateEmail() {
	if($("#emailId").val() <= 0) {
		showMessage('info','Select E-mail Id!');
		return false;
	}
	return true;
}


function updateEmail(jsonObject){
	$.ajax({
		url: "CompanyRestControllerWS/updateCompany_EmailId.do",	
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			checkemail(data);
			hideLayer();

		},
		error: function (e) {
			console.log("Error",e);
		}
	});
}


function saveEmail(jsonObject){
	$.ajax({
		url: "CompanyRestControllerWS/saveCompanyEmailId.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			showMessage("success", "Saved Successfully")
		},
		error: function (e) {
			console.log("Error",e);
		}
	});
}



function getDriverMonthlySalaryInfo(){
	
	var jsonObject		= new Object();
	var vehicleId       = $("#vid").val();
	var driverMonthlySalary			= null;
	jsonObject["vehicleId"]	= vehicleId;
	
	$.ajax({
		url: "getDriverMonthlySalary.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			if(data.DriverMonthlySalary != undefined) {
				driverMonthlySalary	= data.DriverMonthlySalary;
				$('#amountForDriver').val(driverMonthlySalary.driverMonthlySalary);
			}	
			
			$('#addManufacturer').modal('show');
		},
		error: function (e) {
			console.log("Error",e);
		}
	});
	
}

function getDriverMonthlyBhattaInfo(){
	
	var jsonObject		= new Object();
	var vehicleId       = $("#vid").val();
	var driverMonthlyBhatta			= null;
	jsonObject["vehicleId"]	= vehicleId;
	
	$.ajax({
		url: "getDriverMonthlySalary.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			if(data.DriverMonthlySalary != undefined) {
				driverMonthlyBhatta	= data.DriverMonthlySalary;
				$('#bhattaForDriver').val(driverMonthlyBhatta.driverMonthlyBhatta);
			}	
			
			$('#modalDriverBhatta').modal('show');
		},
		error: function (e) {
			console.log("Error",e);
		}
	});
	
}


function addDriverMonthlySalary(){
	
	showLayer();
	var jsonObject	= new Object();
	var vehicleId   = $("#vid").val();
	
	jsonObject["vehicleId"]			= vehicleId;
	jsonObject["amountForDriver"]	= $('#amountForDriver').val();
	
	if($('#amountForDriver').val().trim() == ''){
		$('#amountForDriver').focus();
		showMessage('errors', 'Please Enter Amount !');
		hideLayer();
		return false;
	}
	
	$.ajax({
		url: "saveDriverMonthlySalary.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			showMessage("success", "Salary Saved Successfully");
			hideLayer();
			$('#addManufacturer').modal('hide');
			setTimeout(function(){ hideLayer();location.reload(); }, 100);
		},
		error: function (e) {
			console.log("Error",e);
			hideLayer();
		}
	});
	
}


function addDriverMonthlyBhatta(){
	
	showLayer();
	var jsonObject	= new Object();
	var vehicleId   = $("#vid").val();
	
	jsonObject["vehicleId"]			= vehicleId;
	jsonObject["bhattaForDriver"]	= $('#bhattaForDriver').val();
	
	if($('#bhattaForDriver').val().trim() == ''){
		$('#bhattaForDriver').focus();
		showMessage('errors', 'Please Enter Amount !');
		hideLayer();
		return false;
	}
	
	$.ajax({
		url: "saveDriverMonthlyBhatta.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			showMessage("success", "Driver Bhatta Saved Successfully");
			hideLayer();
			$('#modalDriverBhatta').modal('hide');
			setTimeout(function(){ hideLayer();location.reload(); }, 100);
		},
		error: function (e) {
			console.log("Error",e);
			hideLayer();
		}
	});
	
}


$(document).ready(function() {
	if($('#showBusIdDetails').val() != undefined && ($('#showBusIdDetails').val() == 'true' || $('#showBusIdDetails').val() == true)){
		getBusDetails();
	}
});

function getBusDetails() {
	if($('#vid').val() != undefined && $('#vid').val() != null){
		showLayer();
		var jsonObject					= new Object();
		jsonObject["vid"] 				= $('#vid').val();
		
		$.ajax({
			url: "getBusDetailsForFalconITSApi",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				
				if(data.busDetailsFound != undefined && data.busDetailsFound == true
						&& data.busDetails != undefined && data.busDetails != null){
					
					$('#busId').val(data.busDetails.busId);
					$('#deviceId').val(data.busDetails.deviceId);
				}
				
				hideLayer();
			},
			error: function (e) {
				hideLayer();
			}
		});
	}
}

function saveBusDetails(){
	showLayer();
	var jsonObject					= new Object();
	
	jsonObject["vid"] 					= $('#vid').val();
	jsonObject["busId"]					= $('#busId').val();
	jsonObject["deviceId"]				= $('#deviceId').val();
	
	if($('#busId').val() == ''){
		$('#busId').focus();
		showMessage('errors', 'Please Select Bus Id greater than 0 !');
		hideLayer();
		return false;
	}
	
	if($('#deviceId').val() == ''){
		$('#deviceId').focus();
		showMessage('errors', 'Please Select Device Id greater than 0 !');
		hideLayer();
		return false;
	}
	
	$.ajax({
		url: "saveBusDetailsForFalconITSApi",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			console.log('data', data);
			$('#addBusDetails').modal('hide');
			 
			if(data.busDetailsAdded != undefined && data.busDetailsAdded == true){
				hideLayer();
				showMessage('success', 'Bus Details Saved Successfully!');
				
			} else if (data.busDetailsAlreadyAdded != undefined && data.busDetailsAlreadyAdded == true){
				hideLayer();
				showMessage('info', 'Bus Details Already Added!');
				
			} else if (data.busDetailsUpdated != undefined && data.busDetailsUpdated == true){
				hideLayer();
				showMessage('success', 'Bus Details Updated Successfully!');
				
			} else if (data.dupliacteBusDetailsFound != undefined && data.dupliacteBusDetailsFound == true){
				hideLayer();
				
				if(data.dupliacteBusIdFound != undefined && data.dupliacteBusIdFound != null){
					showMessage('errors', 'Duplicate Bus Id Details Found. Same Bus Id is present in vehicle'+data.dupliacteBusIdFound);
				}
				
				if(data.dupliacteDeviceIdFound != undefined && data.dupliacteDeviceIdFound != null){
					showMessage('errors', 'Duplicate Device Id Details Found. Same Device Id is present in vehicle'+data.dupliacteDeviceIdFound);
				}
				
			} else {
				hideLayer();
				showMessage('errors', 'Some Error Occurred!');
			}
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}
function linkVehicleToll(){

	
	showLayer();
	var jsonObject					= new Object();
	
	jsonObject["vid"] 							= $('#vid').val();
	jsonObject["vehicleTollDetailsId"]			= $('#vehicleTollDetailsId').val();
	
	
	$.ajax({
		url: "saveVehicleTollDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			$('#LinkICICITOll').modal('hide');
			 
			if (data.customerIdNotFound != undefined && data.customerIdNotFound == true){
				hideLayer();
				showMessage('info', 'Customer Id Not Found IN Request!');
			}
			showMessage('success', 'Data Saved Successfully !');
			location.reload();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});

}

function linkVehicleGPSAC(){
	showLayer();
	var jsonObject					= new Object();
	
	jsonObject["vid"] 							= $('#vid').val();
	jsonObject["vehicleGPSCredentialId"]		= $('#vehicleGPSCredentialId').val();
	
	$.ajax({
		url: "saveVehicleGPSAccount",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			$('#LinkGPSAccount').modal('hide');
			 
			if (data.customerIdNotFound != undefined && data.customerIdNotFound == true){
				hideLayer();
				showMessage('info', 'Customer Id Not Found IN Request!');
			}
			showMessage('success', 'Data Saved Successfully !');
			location.reload();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

	$("#showSubCompany").click(function(){
		showLayer();
		var jsonObject					= new Object();
		$.ajax({
			url: "getAllSubCompanies",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				hideLayer();
				setAllSubCompanies(data);
			},
			error: function (e) {
				hideLayer();
				showMessage('errors', 'Some Error Occurred!');
			}
		});
	});
function setAllSubCompanies (data){
	
	$("#subCompanyTable").empty();
	
	var subCompanyList = data.subCompanyList;
	
	if(subCompanyList != undefined || subCompanyList != null){
		for(var index = 0 ; index < subCompanyList.length; index++){
			
			var columnArray = new Array();
			columnArray.push("<td class='fit'>"+(index+1)+"</td>");
			columnArray.push("<td class='fit'ar> <h4> "+ subCompanyList[index].subCompanyName  +"</td>");
			columnArray.push("<td class='fit ar'>" + subCompanyList[index].subCompanyWebsite +"</td>");
			columnArray.push("<td class='fit ar'>" + subCompanyList[index].subCompanyMobileNumber +"</td>");
			columnArray.push("<td class='fit ar'>" + subCompanyList[index].subCompanyTaxNo +"</td>");
			columnArray.push("<td class='fit ar' style='vertical-align: middle;' ><a href='editSubCompanyDetails.in?subCompanyId="+subCompanyList[index].subCompanyId+"' class='confirmation'><span class='fa fa-edit'></span> Edit</a>&nbsp;&nbsp;&nbsp<a href='#' onclick='deleteSubCompany("+subCompanyList[index].subCompanyId+")' class='confirmation'><span class='fa fa-trash'></span> Delete</a></td>");
			
			$('#subCompanyTable').append("<tr id='penaltyID"+subCompanyList[index].subCompanyId+"' >" + columnArray.join(' ') + "</tr>");
			
		}
		
		columnArray = [];
		}else{
			showMessage('info','No Record Found!')
		}
	}

function deleteSubCompany(subCompanyId){
	var jsonObject					= new Object();
	jsonObject["subCompanyId"]					= subCompanyId;
	$.ajax({
		url: "deleteSubCompanyDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			showMessage('info','SubCompany Deleted Successfully')
			location.reload();
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}


function getStatusChangeHistory(id,type){
	var jsonObject					= new Object();
	jsonObject["id"]				= id;
	jsonObject["typeId"]			= type;
	$.ajax({
		url: "/getStatusChangeRemarkList",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			showStatusChangeDetails(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
}

function showStatusChangeDetails(data){
	$('#taskTable').empty();
	var thead = $('<thead>');
	var tr1 = $('<tr>');
	var th1 = 	$('<th>');
	var th2 =	$('<th>');
	var th3 =	$('<th>');
	var th4 =	$('<th>');
	var th5 =	$('<th>');
	var th6 =	$('<th>');
	tr1.append(th1.append("NO."));
	tr1.append(th2.append("Changed from"));
	tr1.append(th4.append("Changed To"));
	tr1.append(th5.append("updated by"));
	tr1.append(th3.append("updated On"));
	tr1.append(th6.append("Remark"));
	thead.append(tr1);
	var num = 1;
	var tbody = $('<tbody>');
	if(data.statusChangeRemarkList != undefined  && data.statusChangeRemarkList.length > 0) {
		var tasks= data.statusChangeRemarkList;
	for(var i = 0; i < tasks.length; i++) {
		var tr1 = $('<tr class="ng-scope">');
		var tr4 = $('<tr>');
		var td1		= $('<td>');
		var td2		= $('<td>');
		var td3		= $('<td>');
		var td4		= $('<td>');
		var td5		= $('<td>');
		var td6		= $('<td>');
		
		tr1.append(td1.append(num));
		tr1.append(td3.append(tasks[i].currentStatus));
		
		if(data.typeId == 2 && tasks[i].changeToStatusId == 4){
			var link = '<a data-toggle="tooltip" data-placement="top" title="Click for Sold Details" href="javascript:void(0)" onclick="showSoldDetails(\''+tasks[i].partyName+'\',\''+tasks[i].mobileNumber+'\',\''+tasks[i].soldOnstr+'\',\''+tasks[i].soldAmount+'\')">'+tasks[i].changeToStatus+'</a>';
			tr1.append(td4.append(link));	
		}else{
			tr1.append(td4.append(tasks[i].changeToStatus));
		}
		tr1.append(td5.append(tasks[i].createdBy));
		tr1.append(td2.append(tasks[i].creationDate));
		tr1.append(td6.append(tasks[i].statusChangeRemark));
		tbody.append(tr1);
		num++;
		}
	}else{
		showMessage('info','No record found !')
	}
	$('#taskTable').append(thead);
	$('#taskTable').append(tbody);
	$('#statusChangeDetails').modal('show');
}


function showSoldDetails(partyName,mobileNumber,soldOnstr,amount){
	$('#soldTable').empty();
	var tr1 = $('<tr class="ng-scope">');
	var td1		= $('<td>');
	var td2		= $('<td>');
	var td3		= $('<td>');
	var td4		= $('<td>');
	var tbody = $('<tbody>');
	if(partyName != 'null'){
	tr1.append(td1.append('<samp style="font-weight:bolder; color:blue">Party Name:</samp> '+partyName))
	tr1.append(td2.append('<samp style="font-weight:bolder; color:blue"> mobile No: </samp>'+ mobileNumber))
	tr1.append(td3.append('<samp style="font-weight:bolder; color:blue">Sold On: </samp>'+soldOnstr))
	tr1.append(td4.append('<samp style="font-weight:bolder; color:blue"> Amount: </samp>'+amount))
	}else{
		tr1.append(td1.append('<samp style="font-weight:bolder; color:red">No Sold Details Found !</samp> '))
	}
	tbody.append(tr1);
	$('#soldTable').append(tbody);
}

function getExpenseDetails(id,type){
	showLayer();
	var jsonObject					= new Object();
	jsonObject["vid"]				= id;
	jsonObject["typeId"]			= type;
	jsonObject["companyId"]			= $('#companyId').val();
	$.ajax({
		url: "/getVehicleExpenseDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setExpenseDetails(data,type);
			hideLayer();
		},
		error: function () {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setExpenseDetails(data,type){
	$('#showDetailsTable').empty();
	var thead = $('<thead>');
	var tr1 =tHeadRR(type);
	thead.append(tr1);
	var tbody = $('<tbody>');
	if(data.list != undefined  && data.list.length > 0) {
		var list= data.list;
		for(var i = 0; i < list.length; i++) {
			var tr1="";
			if(type ==1)
			 tr1=tbodyRR(list[i]);
			else if(type ==2)
			 tr1=tbodyFE(list[i]);
			else if(type ==3)
			 tr1=tbodyWO(list[i]);	
			 else if(type == 4)
			 tr1=tbodyDSE(list[i])
			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	$('#showDetailsTable').append(thead);
	$('#showDetailsTable').append(tbody);
	$('#showDetails').modal('show');
}

function tHeadRR(type){
	var tr1 = $('<tr>');
	var th1 = 	$('<th>');
	var th2 =	$('<th>');
	var th5 =	$('<th>');
	tr1.append(th1.append("NO."));
		if(type ==1)
			tr1.append(th2.append("Receipt No/Policy No"));
		else if (type == 2 )
			tr1.append(th2.append("Date"));
		else if(type == 4)
				tr1.append(th2.append("Invoice Date"));
	tr1.append(th5.append("Amount"));
	return tr1;
	
}

function tbodyRR(task){
	var tr1 = $('<tr class="ng-scope">');
	var td1		= $('<td>');
	var td4		= $('<td>');
	var td5		= $('<td>');
	var link ='<a href="/showRenewalReminderDetails?renewalId='+task.renewal_id+'">R-'+task.renewal_R_Number+'</a>';
	tr1.append(td1.append(link));
	tr1.append(td4.append(task.renewal_receipt));
	tr1.append(td5.append((task.renewal_Amount).toFixed(2)));
	return tr1;
}

function tbodyFE(task){
	var tr1 = $('<tr class="ng-scope">');
	var td1		= $('<td>');
	var td4		= $('<td>');
	var td5		= $('<td>');
	var link ='<a href="/showFuel.in?FID='+task.fuel_id+'">FE-'+task.fuel_Number+'</a>';
	tr1.append(td1.append(link));
	tr1.append(td4.append(task.fuel_D_date));
	tr1.append(td5.append((task.fuel_amount).toFixed(2)));
	return tr1;
}
function tbodyWO(task){
	var tr1 = $('<tr class="ng-scope">');
	var td1		= $('<td>');
	var td5		= $('<td>');
	var link ='<a href="/showWorkOrder?woId='+task.workorders_id+'">WO-'+task.workorders_Number+'</a>';
	tr1.append(td1.append(link));
	tr1.append(td5.append((task.totalworkorder_cost).toFixed(2)));
	return tr1;
}


function tbodyDSE(task){
	var tr1 = $('<tr class="ng-scope">');
	var td1		= $('<td>');
	var td4		= $('<td>');
	var td5		= $('<td>');
	var link =task.dealerServiceEntriesNumberStr;
	tr1.append(td1.append(link));
	tr1.append(td4.append(task.invoiceDateStr));
	tr1.append(td5.append(task.totalInvoiceCost));
	return tr1;
}

