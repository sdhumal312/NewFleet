$(document).ready(function() {
	getUserNotificationList(1);
});

function getReadNotificationList(pageNumber){

	$('#partModal2').modal('hide');
	showLayer();
	var jsonObject					= new Object();
	jsonObject["pageNumber"]			= pageNumber;

	$.ajax({
		url: "getReadNotificationList",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setReadNotifications(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});

}

function getUserNotificationList(pageNumber) {
	$('#partModal').modal('hide');
	showLayer();
	var jsonObject					= new Object();
	jsonObject["pageNumber"]			= pageNumber;
	$.ajax({
		url: "getUnreadNotificationList",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setUnreadNotifications(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}


function setUnreadNotifications(data) {
	$("#VendorPaymentTable").empty();
	$("#searchInv").val('');
	$("#searchBattery").val('');
	$('#countDiv').show();
	$('#listTab').show();
	$('#addClothDiv').addClass('hide');
	$("#totalClothInvoice").html(data.invoiceCount);
	$('#countId').html('Total Unread Notification');
	document.getElementById('All').className = "active";
	document.getElementById('AllStock').className = "tab-link";
	document.getElementById('AllSent').className = "tab-link";
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th>');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th>');
	var th6		= $('<th>');

	tr1.append(th1.append("No"));
	tr1.append(th2.append("Type"));
	tr1.append(th3.append("Number"));
	tr1.append(th4.append("Send By"));
	tr1.append(th5.append("Msg"));
	//tr1.append(th6.append("Action"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.notificationList != undefined && data.notificationList.length > 0) {
		
		var notificationList = data.notificationList;
		var srNo = 1;
		for(var i = 0; i < notificationList.length; i++) {
			
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td class="fit ar">');
			var td5		= $('<td class="fit ar">');
			var td6		= $('<td class="fit ar">');
			var alertmsg = window.btoa(notificationList[i].alertMsg);
			tr1.append(td1.append(srNo));
			tr1.append(td2.append(notificationList[i].txnTypeName));
			if(notificationList[i].txnTypeId == 1){
				tr1.append(td3.append('<a href="4/1/showInventoryReq.in?ID='+notificationList[i].transactionId+'" target="_blank">IR-'+notificationList[i].partRequisitionNumber+'</a>'));
			}else if(notificationList[i].txnTypeId == 3){ //requisition notification
				tr1.append(td3.append('<a href="showRequisition?requisitionId='+notificationList[i].transactionId+'" target="_blank">R-'+notificationList[i].requisitionNumber+'</a>'));
			}else{
				tr1.append(td3.append('------'));
			}
			tr1.append(td4.append(notificationList[i].sendBy));
			tr1.append(td5.append('<input type="hidden" value="'+alertmsg+'" id="msg_'+notificationList[i].userAlertNotificationsId+'"><a class="btn btn-success" href="#" onclick="markAsRead('+notificationList[i].userAlertNotificationsId+')";>Show Msg</a>'));
			//tr1.append(td6.append('<a class="btn btn-success" href="#" onclick="markAsRead('+notificationList[i].userAlertNotificationsId+')";>Mark As Read</a>'));	
			
			srNo ++;
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
		$("#navigationBar").append('<li><a href="#" onclick="getUserNotificationList(1)">&lt;&lt;</a></li>');		
	}

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="getUserNotificationList('+(data.currentIndex - 1)+')">&lt;</a></li>');
	}
	
	for (i = data.beginIndex; i <= data.endIndex; i++) {
	    if(i == data.currentIndex) {
	    	$("#navigationBar").append('<li class="active"><a href="#" onclick="getUserNotificationList('+i+')">'+i+'</a></li>');	    	
	    } else {
	    	$("#navigationBar").append('<li><a href="#" onclick="getUserNotificationList('+i+')">'+i+'</a></li>');	    	
	    }
	} 
	
	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getUserNotificationList('+(data.currentIndex + 1)+')">&gt;</a></li>');			
		}
	}

	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getUserNotificationList('+(data.deploymentLog.totalPages)+')">&gt;&gt;</a></li>');			
		}
	}
}


function markAsRead(userAlertNotificationsId){
	showLayer();
	var jsonObject					= new Object();
	jsonObject["userAlertNotificationsId"]			= userAlertNotificationsId;
	$.ajax({
		url: "markNotificationAsRead",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	var alertmsg = window.atob($('#msg_'+userAlertNotificationsId+'').val());
	$('#modalBody').html(alertmsg);
	$('#partModal').modal('show');
}


function setReadNotifications(data) {
	$("#VendorPaymentTable").empty();
	$("#searchInv").val('');
	$("#searchBattery").val('');
	$('#searchData').show();
	$('#countDiv').show();
	$('#listTab').show();
	$('#addClothDiv').addClass('hide');
	$("#totalClothInvoice").html(data.invoiceCount);
	$('#countId').html('Total Read Notification');
	document.getElementById('All').className = "tab-link";
	document.getElementById('AllSent').className = "tab-link";
	document.getElementById('AllStock').className = "active";
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th>');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th>');
	var th6		= $('<th>');

	tr1.append(th1.append("No"));
	tr1.append(th2.append("Type"));
	tr1.append(th3.append("Number"));
	tr1.append(th4.append("Send By"));
	tr1.append(th5.append("Msg"));
	//tr1.append(th6.append("Action"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.notificationList != undefined && data.notificationList.length > 0) {
		
		var notificationList = data.notificationList;
		var srNo = 1;
		for(var i = 0; i < notificationList.length; i++) {
			
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td class="fit ar">');
			var td5		= $('<td class="fit ar">');
			var td6		= $('<td class="fit ar">');
			var alertmsg = window.btoa(notificationList[i].alertMsg);
			tr1.append(td1.append(srNo));
			tr1.append(td2.append(notificationList[i].txnTypeName));
			if(notificationList[i].txnTypeId == 1){
				tr1.append(td3.append('<a href="4/1/showInventoryReq.in?ID='+notificationList[i].transactionId+'" target="_blank">IR-'+notificationList[i].partRequisitionNumber+'</a>'));
			}else if(notificationList[i].txnTypeId == 3){ //requisition notification
				tr1.append(td3.append('<a href="showRequisition?requisitionId='+notificationList[i].transactionId+'" target="_blank">R-'+notificationList[i].requisitionNumber+'</a>'));
			}else{
				tr1.append(td3.append('------'));
			}
			tr1.append(td4.append(notificationList[i].sendBy));
			tr1.append(td5.append('<input type="hidden" value="'+alertmsg+'" id="msg_'+notificationList[i].userAlertNotificationsId+'"><a class="btn btn-success" href="#" onclick="showReadMsg('+notificationList[i].userAlertNotificationsId+')";>Show Msg</a>'));
			//tr1.append(td6.append('<a class="btn btn-success" href="#" onclick="markAsRead('+notificationList[i].userAlertNotificationsId+')";>Mark As Read</a>'));	
			
			srNo ++;
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
		$("#navigationBar").append('<li><a href="#" onclick="getReadNotificationList(1)">&lt;&lt;</a></li>');		
	}

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="getReadNotificationList('+(data.currentIndex - 1)+')">&lt;</a></li>');
	}
	
	for (i = data.beginIndex; i <= data.endIndex; i++) {
	    if(i == data.currentIndex) {
	    	$("#navigationBar").append('<li class="active"><a href="#" onclick="getReadNotificationList('+i+')">'+i+'</a></li>');	    	
	    } else {
	    	$("#navigationBar").append('<li><a href="#" onclick="getReadNotificationList('+i+')">'+i+'</a></li>');	    	
	    }
	} 
	
	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getReadNotificationList('+(data.currentIndex + 1)+')">&gt;</a></li>');			
		}
	}

	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getReadNotificationList('+(data.deploymentLog.totalPages)+')">&gt;&gt;</a></li>');			
		}
	}
}

function showReadMsg(userAlertNotificationsId){
	var alertmsg = window.atob($('#msg_'+userAlertNotificationsId+'').val());
	$('#modalBody2').html(alertmsg);
	$('#partModal2').modal('show');
}

function getSentNotificationList(pageNumber){

	$('#partModal2').modal('hide');
	showLayer();
	var jsonObject					= new Object();
	jsonObject["pageNumber"]			= pageNumber;

	$.ajax({
		url: "getSentNotificationList",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setSentNotifications(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});

}


function setSentNotifications(data) {
	$("#VendorPaymentTable").empty();
	$("#searchInv").val('');
	$("#searchBattery").val('');
	$('#searchData').show();
	$('#countDiv').show();
	$('#listTab').show();
	$('#addClothDiv').addClass('hide');
	$("#totalClothInvoice").html(data.invoiceCount);
	$('#countId').html('Total Sent Notification');
	document.getElementById('All').className = "tab-link";
	document.getElementById('AllSent').className = "active";
	document.getElementById('AllStock').className = "tab-link";
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th>');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th>');
	var th6		= $('<th>');

	tr1.append(th1.append("No"));
	tr1.append(th2.append("Type"));
	tr1.append(th3.append("Number"));
	tr1.append(th4.append("Send TO"));
	tr1.append(th5.append("Status"));
	tr1.append(th6.append("Msg"));
	//tr1.append(th6.append("Action"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.notificationList != undefined && data.notificationList.length > 0) {
		
		var notificationList = data.notificationList;
		var srNo = 1;
		for(var i = 0; i < notificationList.length; i++) {
			
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td class="fit ar">');
			var td5		= $('<td class="fit ar">');
			var td6		= $('<td class="fit ar">');
			var alertmsg = window.btoa(notificationList[i].alertMsg);
			tr1.append(td1.append(srNo));
			tr1.append(td2.append(notificationList[i].txnTypeName));
			if(notificationList[i].txnTypeId == 1){
				tr1.append(td3.append('<a href="4/1/showInventoryReq.in?ID='+notificationList[i].transactionId+'" target="_blank">IR-'+notificationList[i].partRequisitionNumber+'</a>'));
			}else if(notificationList[i].txnTypeId == 3){ //requisition notification
				tr1.append(td3.append('<a href="showRequisition?requisitionId='+notificationList[i].transactionId+'" target="_blank">R-'+notificationList[i].requisitionNumber+'</a>'));
			}else{
			   tr1.append(td3.append('------'));
			}
			tr1.append(td4.append(notificationList[i].sendBy));
			tr1.append(td5.append(notificationList[i].statusStr));
			tr1.append(td6.append('<input type="hidden" value="'+alertmsg+'" id="msg_'+notificationList[i].userAlertNotificationsId+'"><a class="btn btn-success" href="#" onclick="showReadMsg('+notificationList[i].userAlertNotificationsId+')";>Show Msg</a>'));
			//tr1.append(td6.append('<a class="btn btn-success" href="#" onclick="markAsRead('+notificationList[i].userAlertNotificationsId+')";>Mark As Read</a>'));	
			
			srNo ++;
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
		$("#navigationBar").append('<li><a href="#" onclick="getSentNotificationList(1)">&lt;&lt;</a></li>');		
	}

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="getSentNotificationList('+(data.currentIndex - 1)+')">&lt;</a></li>');
	}
	
	for (i = data.beginIndex; i <= data.endIndex; i++) {
	    if(i == data.currentIndex) {
	    	$("#navigationBar").append('<li class="active"><a href="#" onclick="getSentNotificationList('+i+')">'+i+'</a></li>');	    	
	    } else {
	    	$("#navigationBar").append('<li><a href="#" onclick="getSentNotificationList('+i+')">'+i+'</a></li>');	    	
	    }
	} 
	
	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getSentNotificationList('+(data.currentIndex + 1)+')">&gt;</a></li>');			
		}
	}

	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getSentNotificationList('+(data.deploymentLog.totalPages)+')">&gt;&gt;</a></li>');			
		}
	}
}