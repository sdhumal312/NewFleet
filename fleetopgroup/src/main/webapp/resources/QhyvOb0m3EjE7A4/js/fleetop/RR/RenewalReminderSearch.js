

$(document).ready(function(){
	searchRRByDifferentFilter()
});

function searchRRByDifferentFilter(){
	
	var jsonObject					= new Object();
	jsonObject["filter"]			= $("#searchByDifferentFilter").val();
	
	if( $("#searchByDifferentFilter").val() == ""){
		$('#searchByDifferentFilter').focus();
		showMessage('errors', 'Please Enter Valid Details !');
		return false;
	}
	
	showLayer();
	$.ajax({
		url: "RenewalReminderWS/searchRRByDifferentFilter",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			if(data.RenewalReminder != undefined && data.RenewalReminder.length > 0) {
				setRRByDifferentFilters(data);
				hideLayer();
			} else {
				hideLayer();
				showMessage('info', 'Please Enter Valid Renewal Details !');
			}
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}




function setRRByDifferentFilters(data) {
	
	$("#renewalSearchTable").empty();
	
	
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
	var th9		= $('<th class="fit ar">');


	tr1.append(th1.append("RR-No"));
	tr1.append(th2.append("Vehicle"));
	tr1.append(th3.append("Receipt No/ Policy No"));
	tr1.append(th4.append("Renewal Types"));
	tr1.append(th5.append("Validity From"));
	tr1.append(th6.append("Validity To"));
	tr1.append(th7.append("Amount"));
	tr1.append(th8.append("Revise"));
	tr1.append(th9.append("Actions"));

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
			
			tr1.append(td1.append('<a href="showRenewalReminderDetails?renewalId='+RenewalReminder[i].renewal_id+'" target="_blank">RR-'+RenewalReminder[i].renewal_R_Number+'</a>'));
			
			var curl = "ViewVehicleDocument.in?vehid="+RenewalReminder[i].vid
			tr1.append(td2.append('<a target="_blank" href="' + curl + '">'+RenewalReminder[i].vehicle_registration+'</a><br>'));
			tr1.append(td3.append(RenewalReminder[i].renewal_receipt));
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
			tr1.append(td8.append('<a href="renewalReminderAjaxRevise?renewalId='+RenewalReminder[i].renewal_id+'"> <span class="btn btn-info btn-sm"> Revise</span> </a>'));
			
			
			var curl = "renewalReminderAjaxEdit?renewalId="+RenewalReminder[i].renewal_id
			if(($("#createApprovalPermission").val() == true || $("#createApprovalPermission").val() == 'true') && (RenewalReminder[i].renewal_staus_id == 1)){
				tr1.append(td9.append(
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
				tr1.append(td9.append(
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
	
	$("#renewalSearchTable").append(thead);
	$("#renewalSearchTable").append(tbody);
	
	$("#renewalSearchTable").DataTable({
		sScrollX:"100%",bScrollcollapse:!0,dom:"Blfrtip",buttons:["excel","print"],order:[[0,"desc"]]
	});
	
}