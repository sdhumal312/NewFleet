$(document).ready(function() {
	getRenewalReminderHistoryByVehicle();
});

function getRenewalReminderHistoryByVehicle() {
	
	showLayer();
	var jsonObject			= new Object();
	jsonObject["vid"]		= $('#vid').val();
	
	$.ajax({
		url: "RenewalReminderWS/getRenewalReminderHistoryByVehicle",
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
	
		tr1.append(th1.append("Renewal Number"));
		tr1.append(th2.append("Vehicle Number"));
		tr1.append(th3.append("Renewal Type"));
		tr1.append(th4.append("Renewal SubType"));
		tr1.append(th5.append("Validity From"));
		tr1.append(th6.append("Validity To"));
		tr1.append(th7.append("Amount"));
		tr1.append(th8.append("Download"));
		tr1.append(th9.append("Delete"));
	
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
			
			tr1.append(td1.append(renewalList[i].renewalhis_id));
			tr1.append(td2.append('<a href="ViewVehicleDocument.in?vehid='+renewalList[i].vid+'" target="_blank">RR-'+renewalList[i].vehicle_registration+'</a>'));
			tr1.append(td3.append(renewalList[i].renewalhis_type));
			tr1.append(td4.append(renewalList[i].renewalhis_subtype));
			tr1.append(td5.append(renewalList[i].renewalhis_from));
			tr1.append(td6.append(renewalList[i].renewalhis_to));
			tr1.append(td7.append(renewalList[i].renewalhis_Amount));
			tr1.append(td8.append('<a href="download/RenewalReminderHis/'+renewalList[i].renewal_document_id+'.in"> <span class="fa fa-download"> Doc</span> </a>'));
			tr1.append(td9.append('<a href="renewalReminderAjaxRevise?renewalId='+renewalList[i].renewalhis_id+'"> <span class="fa fa-upload"> Revise</span> </a>'));
			
			tbody.append(tr1);
		}
	}else{
		showMessage('info','No Renewal History found !')
	}
	
	$("#VendorPaymentTable").append(thead);
	$("#VendorPaymentTable").append(tbody);

}