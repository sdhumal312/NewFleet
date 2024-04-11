$(document).ready(function() {
	getRenewalReminderByVehicle();
});

function getRenewalReminderByVehicle() {
	
	showLayer();
	var jsonObject			= new Object();
	jsonObject["vid"]		= $('#vid').val();
	
	$.ajax({
		url: "RenewalReminderWS/getVehicleWiseRenewalReminder",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			if(data.renewalList != null){
				setRenewalReminderByVehicle(data);
			}
			hideLayer();
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setRenewalReminderByVehicle(data) {
	$("#VendorPaymentTable").empty();
	
	if(data.renewalList != undefined && data.renewalList.length > 0) {
		
		var renewalList = data.renewalList;
	
		var thead = $('<thead>');
		var tr1 = $('<tr>');
	
		var th1		= $('<th>');
		var th2		= $('<th>');
		var th3		= $('<th class="fit ar">');
		var th4		= $('<th>');
		var th5		= $('<th>');
		var th6		= $('<th>');
		var th7		= $('<th>');
		var th8		= $('<th>');
		var th9		= $('<th>');
		var th10	= $('<th>');
	
		tr1.append(th1.append("No"));
		tr1.append(th2.append("Renewal Number"));
		tr1.append(th3.append("Renewal Type"));
		tr1.append(th4.append("Renewal SubType"));
		tr1.append(th5.append("Validity From"));
		tr1.append(th6.append("Validity To"));
		tr1.append(th7.append("Amount"));
		tr1.append(th8.append("Edit"));
		tr1.append(th9.append("Delete"));
		tr1.append(th10.append("Revise"));
	
		thead.append(tr1);
		
		var tbody = $('<tbody>');
	
		for(var i = 0; i < renewalList.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td>');
			var td5		= $('<td>');
			var td6		= $('<td>');
			var td7		= $('<td>');
			var td8		= $('<td>');
			var td9		= $('<td>');
			var td10	= $('<td>');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append('<a href="showRenewalReminderDetails?renewalId='+renewalList[i].renewal_id+'" target="_blank">RR-'+renewalList[i].renewal_R_Number+'</a>'));
			tr1.append(td3.append(renewalList[i].renewal_type));
			tr1.append(td4.append(renewalList[i].renewal_subtype));
			tr1.append(td5.append(renewalList[i].renewal_from));
			tr1.append(td6.append(renewalList[i].renewal_to));
			tr1.append(td7.append(renewalList[i].renewal_Amount));
			tr1.append(td8.append('<a href="#" class="confirmation" onclick="editRenewal('+renewalList[i].renewal_id+')"><i class="fa fa-edit"></i> Edit</a>'));
			tr1.append(td9.append('<a href="#" class="confirmation" onclick="deleteRenewal('+renewalList[i].renewal_id+')"><span class="fa fa-trash"></span> Delete</a>'));
			tr1.append(td10.append('<a href="renewalReminderAjaxRevise?renewalId='+renewalList[i].renewal_id+'"> <span class="fa fa-upload"> Revise</span> </a>'));
			
			tbody.append(tr1);
		}
	}else{
		showMessage('info','No Mandatory Renewal found !')
	}
	
	$("#VendorPaymentTable").append(thead);
	$("#VendorPaymentTable").append(tbody);

}

function editRenewal(renewal_id){
	console.log("renewal_id..",renewal_id);
	window.location.replace("renewalReminderAjaxEdit?renewalId="+renewal_id);
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
					window.location.replace("ViewVehicleWiseRenewalReminder?vid="+$('#vid').val());
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