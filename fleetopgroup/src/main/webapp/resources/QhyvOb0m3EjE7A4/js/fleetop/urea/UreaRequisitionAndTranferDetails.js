$(document).ready(function() {
	getRequisitionAndTransferList(1,1);
});

function getRequisitionAndTransferList(pageNumber,status) {
	$("#all").show();
	$("#your").hide();
	activateTab(status);
	
	showLayer();
	var jsonObject								= new Object();
	jsonObject["pageNumber"]					= pageNumber;
	jsonObject["ureaRequisitionStatusId"]		= status;

	$.ajax({
		url: "getRequisitionAndTransferListByStatus",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setRequisitionAndTransferListByStatus(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setRequisitionAndTransferListByStatus (data){
	$("#totalRequisitionCount").text(data.count);
	$("#reqTable").empty();
	var requisitionList = data.requisitionList;
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th>');
	var th4		= $('<th>');
	var th5		= $('<th>');
	var th6		= $('<th>');
	var th7		= $('<th>');
	var th8		= $('<th>');
	var th9		= $('<th>');

	tr1.append(th1.append("ID"));
	tr1.append(th2.append("Required Location"));
	tr1.append(th3.append("Required Liters"));
	if(data.ureaRequisitionStatusId == "4"){
		tr1.append(th4.append("Received Liters"));
	}else if(data.ureaRequisitionStatusId == "5"){
		tr1.append(th4.append("Rejected Liters"));
	}else if(data.ureaRequisitionStatusId == "7"){
		tr1.append(th4.append("Transfered Liters"));
	}
	tr1.append(th5.append("Required Date"));
	tr1.append(th6.append("Requisition Sender"));
	tr1.append(th7.append("Requisition Receiver"));
	tr1.append(th8.append("Status"));
	if(data.ureaRequisitionStatusId == "1" || data.ureaRequisitionStatusId == "7" ){
		tr1.append(th9.append("Action"));
	}

	thead.append(tr1);
	
	if(requisitionList != undefined || requisitionList != null){
		var tbody = $('<tbody>');
		for(var index = 0 ; index < requisitionList.length; index++){
			
			var columnArray = new Array();
			columnArray.push("<td class='fit'><a href='showUreaRequisitionDetail?Id="+requisitionList[index].ureaRequisitionId+"' target='_blank'> UR-"+requisitionList[index].ureaRequisitionId+"</a></td>");
			columnArray.push("<td class='fit'ar> "+ requisitionList[index].ureaRequiredLocation  +"</td>");
			columnArray.push("<td class='fit ar'>" + requisitionList[index].ureaRequiredQuantity +"</td>");
			if(data.ureaRequisitionStatusId == "4"){
				columnArray.push("<td class='fit ar'>" + requisitionList[index].ureaReceivedQuantity +"</td>");
			}else if(data.ureaRequisitionStatusId == "5"){
				columnArray.push("<td class='fit ar'>" + requisitionList[index].ureaRejectedQuantity +"</td>");
			}else if(data.ureaRequisitionStatusId == "7"){
				columnArray.push("<td class='fit ar'>" + requisitionList[index].ureaTransferQuantity +"</td>");
			}
			columnArray.push("<td class='fit ar'>" + requisitionList[index].ureaRequiredDateStr +"</td>");
			columnArray.push("<td class='fit ar'>" + requisitionList[index].ureaRequisitionSender +"</td>");
			columnArray.push("<td class='fit ar'>" + requisitionList[index].ureaRequisitionReceiver +"</td>");
			columnArray.push("<td class='fit ar'><span class='label label-default label-warning'>" + requisitionList[index].ureaRequisitionStatus +"</span></td>");
			
			if(data.ureaRequisitionStatusId == "1"){
				columnArray.push("<td class='fit ar' style='vertical-align: middle;' ><div class='btn-group'><a class='btn btn-default btn-sm dropdown-toggle'data-toggle='dropdown' href='#'> <span class='fa fa-ellipsis-v'></span></a><ul class='dropdown-menu pull-right'> <li><a href='editUreaRequisition.in?Id="+requisitionList[index].ureaRequisitionId+"'>Edit</a></li><li><a herf='#' onclick='deleteUreaRequisition("+requisitionList[index].ureaRequisitionId+");'>Delete</a></li></ul></div></td>");
			}else if(data.ureaRequisitionStatusId == "7"){
				columnArray.push("<td class='fit ar' style='vertical-align: middle;' ><a href='showUreaRequisitionDetail?Id="+requisitionList[index].ureaRequisitionId+"' target='_blank'><span class='fa fa-edit'></span> Receive</a>&nbsp;&nbsp;&nbsp <a href='showUreaRequisitionDetail?Id="+requisitionList[index].ureaRequisitionId+"' target='_blank'></span> Reject</a></td>");
			}
			
			tbody.append("<tr id='driverBasicDetailsId"+requisitionList[index].ureaRequisitionId+"' >" + columnArray.join(' ') + "</tr>");
		}
		$('#reqTable').append(thead);
		$('#reqTable').append(tbody);
		columnArray = [];
		}else{
			showMessage('info','No Record Found!')
		}
	
	$("#navigationBar").empty();
	

	if(data.yourUreaSendAndReceivedRequisitionStatusId != undefined){
		if(data.currentIndex == 1) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;&lt;</a></li>');		
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getYourRequisitionAndTransferList(1,'+data.ureaRequisitionStatusId+','+data.yourUreaSendAndReceivedRequisitionStatusId+','+data.activeTabStatus+')">&lt;&lt;</a></li>');		
		}
		
		if(data.currentIndex == 1) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getYourRequisitionAndTransferList('+(data.currentIndex - 1)+','+data.ureaRequisitionStatusId+','+data.yourUreaSendAndReceivedRequisitionStatusId+','+data.activeTabStatus+')">&lt;</a></li>');
		}
		
		for (i = data.beginIndex; i <= data.endIndex; i++) {
			if(i == data.currentIndex) {
				$("#navigationBar").append('<li class="active"><a href="#" onclick="getYourRequisitionAndTransferList('+i+','+data.ureaRequisitionStatusId+','+data.yourUreaSendAndReceivedRequisitionStatusId+','+data.activeTabStatus+')">'+i+'</a></li>');	    	
			} else {
				$("#navigationBar").append('<li><a href="#" onclick="getYourRequisitionAndTransferList('+i+','+data.ureaRequisitionStatusId+','+data.yourUreaSendAndReceivedRequisitionStatusId+','+data.activeTabStatus+')">'+i+'</a></li>');	    	
			}
		} 
		
		if(data.deploymentLog.totalPages == 1) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
		} else {
			if(data.currentIndex == data.deploymentLog.totalPages) {
				$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
			} else {
				$("#navigationBar").append('<li><a href="#" onclick="getYourRequisitionAndTransferList('+(data.currentIndex + 1)+','+data.ureaRequisitionStatusId+','+data.yourUreaSendAndReceivedRequisitionStatusId+','+data.activeTabStatus+')">&gt;</a></li>');			
			}
		}
		
		if(data.deploymentLog.totalPages == 1) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
		} else {
			if(data.currentIndex == data.deploymentLog.totalPages) {
				$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
			} else {
				$("#navigationBar").append('<li><a href="#" onclick="getYourRequisitionAndTransferList('+(data.deploymentLog.totalPages)+','+data.ureaRequisitionStatusId+','+data.yourUreaSendAndReceivedRequisitionStatusId+','+data.activeTabStatus+')">&gt;&gt;</a></li>');			
			}
		}
	}else{
		if(data.currentIndex == 1) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;&lt;</a></li>');		
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getRequisitionAndTransferList(1,'+data.ureaRequisitionStatusId+')">&lt;&lt;</a></li>');		
		}
		
		if(data.currentIndex == 1) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getRequisitionAndTransferList('+(data.currentIndex - 1)+','+data.ureaRequisitionStatusId+')">&lt;</a></li>');
		}
		
		for (i = data.beginIndex; i <= data.endIndex; i++) {
			if(i == data.currentIndex) {
				$("#navigationBar").append('<li class="active"><a href="#" onclick="getRequisitionAndTransferList('+i+','+data.ureaRequisitionStatusId+')">'+i+'</a></li>');	    	
			} else {
				$("#navigationBar").append('<li><a href="#" onclick="getRequisitionAndTransferList('+i+','+data.ureaRequisitionStatusId+')">'+i+'</a></li>');	    	
			}
		} 
		
		if(data.deploymentLog.totalPages == 1) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
		} else {
			if(data.currentIndex == data.deploymentLog.totalPages) {
				$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
			} else {
				$("#navigationBar").append('<li><a href="#" onclick="getRequisitionAndTransferList('+(data.currentIndex + 1)+','+data.ureaRequisitionStatusId+')">&gt;</a></li>');			
			}
		}
		
		if(data.deploymentLog.totalPages == 1) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
		} else {
			if(data.currentIndex == data.deploymentLog.totalPages) {
				$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
			} else {
				$("#navigationBar").append('<li><a href="#" onclick="getRequisitionAndTransferList('+(data.deploymentLog.totalPages)+','+data.ureaRequisitionStatusId+')">&gt;&gt;</a></li>');			
			}
		}
		
	}
	
	}

function activateTab(status){
	switch(status){
	case 1: 
		$("#sentRequisition").addClass('active');
		$("#acceptedRequisition").removeClass('active');
		$("#rejectedRequisition").removeClass('active');
		$("#receivedList").removeClass('active');
		$("#rejectedList").removeClass('active');
		$("#transferdList").removeClass('active');
		 break;
		
	case 2:	
		$("#sentRequisition").removeClass('active');
		$("#acceptedRequisition").addClass('active');
		$("#rejectedRequisition").removeClass('active');
		$("#receivedList").removeClass('active');
		$("#rejectedList").removeClass('active');
		$("#transferdList").removeClass('active');
		 break;
	
	case 3:	
		$("#sentRequisition").removeClass('active');
		$("#acceptedRequisition").removeClass('active');
		$("#rejectedRequisition").addClass('active');
		$("#receivedList").removeClass('active');
		$("#rejectedList").removeClass('active');
		$("#transferdList").removeClass('active');
		 break;
	
	case 4:	
		$("#sentRequisition").removeClass('active');
		$("#acceptedRequisition").removeClass('active');
		$("#rejectedRequisition").removeClass('active');
		$("#receivedList").addClass('active');
		$("#rejectedList").removeClass('active');
		$("#transferdList").removeClass('active');
		 break;
	case 5:	
		$("#sentRequisition").removeClass('active');
		$("#acceptedRequisition").removeClass('active');
		$("#rejectedRequisition").removeClass('active');
		$("#receivedList").removeClass('active');
		$("#rejectedList").addClass('active');
		$("#transferdList").removeClass('active');
		break;
	case 7:	
		$("#sentRequisition").removeClass('active');
		$("#acceptedRequisition").removeClass('active');
		$("#rejectedRequisition").removeClass('active');
		$("#receivedList").removeClass('active');
		$("#rejectedList").removeClass('active');
		$("#transferdList").addClass('active');
		break;
	
	default :
		$("#sentRequisition").addClass('active');
		$("#acceptedRequisition").removeClass('active');
		$("#rejectedRequisition").removeClass('active');
		$("#receivedList").removeClass('active');
		$("#rejectedList").removeClass('active');
		$("#transferdList").removeClass('active');
		 break;
	}
}

function getYourRequisitionAndTransferList(pageNumber,status,yourStatus,activeTabStatus){
	$("#all").hide();
	$("#your").show();
	$("#reqTable").empty();
	yourActivateTab(activeTabStatus);
	
	showLayer();
	var jsonObject												= new Object();
	jsonObject["pageNumber"]									= pageNumber;
	jsonObject["ureaRequisitionStatusId"]						= status;
	jsonObject["yourUreaSendAndReceivedRequisitionStatusId"]	= yourStatus;
	jsonObject["activeTabStatus"]								= activeTabStatus;

	$.ajax({
		url: "getYourRequisitionAndTransferListByStatus",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setRequisitionAndTransferListByStatus(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function yourActivateTab(activeTabStatus){
	switch(status){
	case 1: 
		$("#transferedUrea").addClass('active');
		$("#yourSendRequisitionList").removeClass('active');
		$("#yourReceivedRequisitionList").removeClass('active');
		$("#yourRejetedRequisitionList").removeClass('active');
		 break;
		
	case 2:	
		$("#transferedUrea").removeClass('active');
		$("#yourSendRequisitionList").addClass('active');
		$("#yourReceivedRequisitionList").removeClass('active');
		$("#yourRejetedRequisitionList").removeClass('active');
		 break;
	
	case 3:	
		$("#transferedUrea").removeClass('active');
		$("#yourSendRequisitionList").removeClass('active');
		$("#yourReceivedRequisitionList").addClass('active');
		$("#yourRejetedRequisitionList").removeClass('active');
		 break;
	
	case 4:	
		$("#transferedUrea").removeClass('active');
		$("#yourSendRequisitionList").removeClass('active');
		$("#yourReceivedRequisitionList").removeClass('active');
		$("#yourRejetedRequisitionList").addClass('active');
		 break;
	
	
	default :
		$("#transferedUrea").addClass('active');
		$("#yourSendRequisitionList").removeClass('active');
		$("#yourReceivedRequisitionList").removeClass('active');
		$("#yourRejetedRequisitionList").removeClass('active');
		 break;
	}
}
function searchUR(){
	if($("#searchUR").val() != "" && $("#searchUR").val() != undefined){
	window.location.replace("showUreaRequisitionDetail.in?Id="+$("#searchUR").val()+"");
	}
	
}

function deleteUreaRequisition(ureaRequisitionId){
	showLayer();
	var jsonObject												= new Object();
	jsonObject["ureaRequisitionId"]									= ureaRequisitionId;

	$.ajax({
		url: "deleteUreaRequisition",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			showMessage('info','Deleted Suyccessfully')
			location.reload();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}