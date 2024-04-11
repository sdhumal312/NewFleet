$(document).ready(function() {
	getRenewalMandatoryList();
	getRenewalReminderByVehicle();
	getDocumentList();
});

function getRenewalMandatoryList() {
	
	showLayer();
	var jsonObject			= new Object();
	jsonObject["vid"]		= $('#vid').val();
	
	$.ajax({
		url: "RenewalReminderWS/getRenewalMandatoryList",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			if(data.mandatoryList != undefined){
				setRenewalMandatoryList(data);
			}
			
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setRenewalMandatoryList(data) {
	$("#VendorPaymentTable1").empty();
	
	if(data.mandatoryList != undefined && data.mandatoryList.length > 0) {
		
		var mandatoryList = data.mandatoryList;
	
		var thead = $('<thead>');
		var tr1 = $('<tr>');
	
		var th1		= $('<th>');
		var th2		= $('<th>');
		var th3		= $('<th class="fit ar">');
		var th4		= $('<th>');
	
		tr1.append(th1.append("No"));
		tr1.append(th2.append("Renewal Type"));
		tr1.append(th3.append("Renewal SubType"));
		tr1.append(th4.append("Create Renewal"));
	
		thead.append(tr1);
		
		var tbody = $('<tbody>');
	
		for(var i = 0; i < mandatoryList.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td>');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append(mandatoryList[i].renewal_type));
			tr1.append(td3.append(mandatoryList[i].renewal_subtype));
			
			var curl = "renewalReminderAjaxAdd?vid="+$('#vid').val()+"&renewalSubTypeId="+mandatoryList[i].renewal_Subid
			tr1.append(td4.append('<a class="btn btn-btn-info" href="' + curl + '">Create Renewal</a><br>'));
			
			tbody.append(tr1);
		}
	}else{
		showMessage('info','No Mandatory Renewal found !')
	}
	
	$("#VendorPaymentTable1").append(thead);
	$("#VendorPaymentTable1").append(tbody);

}

function getRenewalReminderByVehicle(renewalHistory) {
	
	showLayer();
	var jsonObject					= new Object();
	jsonObject["vid"]				= $('#vid').val();
	jsonObject["renewalHistory"]	= renewalHistory;
	
	$.ajax({
		url: "RenewalReminderWS/getRenewalReminderByVehicle",
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
function renewalHistoryModal(){
	var renewalHistory = true
	getRenewalReminderByVehicle(renewalHistory);
}

function setRenewalReminderByVehicle(data) {
	if(data.renewalHistory == true || data.renewalHistory == 'true'){
		$("#renewalHistoryTable").empty();  
	}else{
		$("#VendorPaymentTable2").empty();
	}
	
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
		var th11	= $('<th>');
		var th12	= $('<th>');
	
		tr1.append(th1.append("No"));
		tr1.append(th2.append("Renewal Number"));
		tr1.append(th11.append("Receipt No/ Policy No"));
		tr1.append(th3.append("Renewal Type"));
		tr1.append(th4.append("Renewal SubType"));
		tr1.append(th5.append("Validity From"));
		tr1.append(th6.append("Validity To"));
		tr1.append(th7.append("Amount"));
		tr1.append(th8.append("Edit"));
		tr1.append(th9.append("Delete"));
		tr1.append(th10.append("Revise"));
		tr1.append(th12.append("Doc"));
	
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
			var td11	= $('<td>');
			var td12        = $('<td>');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append('<a href="showRenewalReminderDetails?renewalId='+renewalList[i].renewal_id+'" target="_blank">RR-'+renewalList[i].renewal_R_Number+'</a>'));
			tr1.append(td11.append(renewalList[i].renewal_receipt));
			tr1.append(td3.append(renewalList[i].renewal_type));
			tr1.append(td4.append(renewalList[i].renewal_subtype));
			tr1.append(td5.append(renewalList[i].renewal_from));
			tr1.append(td6.append(renewalList[i].renewal_to));
			tr1.append(td7.append(renewalList[i].renewal_Amount));
			if(renewalList[i].ignored){
				tr1.append(td8.append('Ignored'));
			}else{
				tr1.append(td8.append('<a href="#" class="confirmation" onclick="editRenewal('+renewalList[i].renewal_id+')"><i class="fa fa-edit"></i> Edit</a>'));
			}	
			tr1.append(td9.append('<a href="#" class="confirmation" onclick="deleteRenewal('+renewalList[i].renewal_id+')"><span class="fa fa-trash"></span> Delete</a>'));
			if(renewalList[i].ignored){
				tr1.append(td10.append('-'));
			}else{
				tr1.append(td10.append('<a href="renewalReminderAjaxRevise?renewalId='+renewalList[i].renewal_id+'"> <span class="fa fa-upload"> Revise</span> </a>'));
			}
			tr1.append(td12.append('<button onclick="getRenewalReminderByFile(\'' + renewalList[i].renewal_id + '\', \'' + renewalList[i].companyId + '\')"><span class="fa fa-upload"></span></button>'));
			
			tbody.append(tr1);
		}
	}else{
		showMessage('info','No Mandatory Renewal found !')
	}
	
	if(data.renewalHistory == true || data.renewalHistory == 'true'){
		$("#renewalHistoryTable").append(thead);
		$("#renewalHistoryTable").append(tbody);
		$('#renewalHistoryModel').modal('show');

	}else{
		$("#VendorPaymentTable2").append(thead);
		$("#VendorPaymentTable2").append(tbody);
		
	}
	

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
					window.location.replace("ViewVehicleDocument?vehid="+$('#vid').val());
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

function getDocumentList() {
	
	showLayer();
	var jsonObject			= new Object();
	jsonObject["vid"]		= $('#vid').val();
	
	$.ajax({
		url: "getDocumentList",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			if(data.vehicledocumentList != null){
				setVehicleDocumentList(data);
			}
			hideLayer();
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setVehicleDocumentList(data) {
	$("#VendorPaymentTable").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th>');
	var th5		= $('<th>');
	var th6		= $('<th>');
	var th7		= $('<th>');

	tr1.append(th1.append("No"));
	tr1.append(th2.append("Document Name"));
	tr1.append(th3.append("Upload Date"));
	tr1.append(th4.append("File Name"));
	tr1.append(th5.append("Download"));
	tr1.append(th6.append("Edit"));
	tr1.append(th7.append("Delete"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.vehicledocumentList != undefined && data.vehicledocumentList.length > 0) {
		
		var vehicledocumentList = data.vehicledocumentList;
	
		for(var i = 0; i < vehicledocumentList.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td>');
			var td5		= $('<td>');
			var td6		= $('<td>');
			var td7		= $('<td>');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append(vehicledocumentList[i].name));
			tr1.append(td3.append(vehicledocumentList[i].uploaddate));
			tr1.append(td4.append(vehicledocumentList[i].filename));
			
			var curl = "downloaddocument/"+vehicledocumentList[i].id
			tr1.append(td5.append('<a class="fa fa-download" href="' + curl + '"></a><br>'));
			
			tr1.append(td6.append('<a href="#" class="confirmation" onclick="editVehicleDocById('+vehicledocumentList[i].id+')"><i class="fa fa-edit"></i> Edit</a>'));
			tr1.append(td7.append('<a href="#" class="confirmation" onclick="deleteVehicleDocById('+vehicledocumentList[i].id+')"><span class="fa fa-trash"></span> Delete</a>'));
			
			tbody.append(tr1);
		}
	}else{
		//showMessage('info','No record found !')
	}
	
	$("#VendorPaymentTable").append(thead);
	$("#VendorPaymentTable").append(tbody);

}

$(document).ready(function () {
	
    $("#btnSubmit").on('keypress click', function (e) {
        e.preventDefault(); 	//stop submit the form, we will post it manually.
        saveVehicleDocument();
    });

});

function saveVehicleDocument(){
	
	var jsonObject								= new Object();
	jsonObject["vid"]							= $('#vid').val();
	jsonObject["docTypeId"]						= $('#docTypeId').val();
	jsonObject["file"]							= $('#file').val();
	jsonObject["validateDoublePost"] 	 		=  true;
	jsonObject["unique-one-time-token"] 	 	=  $('#accessToken').val();
	
	
	if($('#file').val().trim() == ''){
		$('#file').focus();
		showMessage('errors', 'Please Select A File To Upload !');
		hideLayer();
		return false;
	}
	
	var form = $('#fileUploadForm')[0];
    var data = new FormData(form);

    data.append("vehicleDocumentData", JSON.stringify(jsonObject)); 
	
    showLayer();
    $.ajax({
		type: "POST",
		enctype: 'multipart/form-data',
		url: "saveVehicleDocumentDetails",
		data: data,
		processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
		success: function (data) {
			
			$('#vehicleDocuemnt').modal('hide');
			showMessage('success', "Vehicle Document Added Successfully !");
			location.reload();
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function editVehicleDocById(documentId){
	
	var jsonObject				= new Object();
	jsonObject["documentId"]	= documentId;
	
	$.ajax({
		url: "getVehicleDocumentById",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			$('#editDocTypeName').val(data.vehicledocument.name);
			$('#editDocTypeId').val(data.vehicledocument.docTypeId);
			$('#id').val(documentId);
			$('#editVehicleDocuemnt').modal('show');
			
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
}

$(document).ready(function () {
	
    $("#editBtnSubmit").on('keypress click', function (e) {
        e.preventDefault(); 	//stop submit the form, we will post it manually.
        updateVehicleDocument();
    });

});

function updateVehicleDocument(){
	
	var jsonObject					= new Object();
	jsonObject["id"]				= $('#id').val();
	jsonObject["vid"]				= $('#vid').val();
	jsonObject["docTypeId"]			= $('#editDocTypeId').val();
	jsonObject["file"]				= $('#editFile').val();
	
	
	if($('#editFile').val().trim() == ''){
		$('#editFile').focus();
		showMessage('errors', 'Please Select A File To Upload !');
		hideLayer();
		return false;
	}
	
	var form = $('#editFileUploadForm')[0];
    var data = new FormData(form);

    data.append("vehicleDocumentData", JSON.stringify(jsonObject)); 
	
    showLayer();
    $.ajax({
		type: "POST",
		enctype: 'multipart/form-data',
		url: "saveVehicleDocumentDetails",
		data: data,
		processData: false, //prevent jQuery from automatically transforming the data into a query string
        contentType: false,
        cache: false,
		success: function (data) {
			
			$('#editVehicleDocuemnt').modal('hide');
			showMessage('success', "Vehicle Document Updated Successfully !");
			location.reload();
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function deleteVehicleDocById(documentId){
	
	if (confirm('Are you sure, you want to Delete this ?')) {
	
		showLayer();
		var jsonObject				= new Object();
		jsonObject["documentId"]	= documentId;
		
		$.ajax({
			url: "deleteVehicleDocumentById",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				
				showMessage('success', "Vehicle Document Deleted Successfully !");
				location.reload();
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


function getRenewalReminderByFile(renewal_id, companyId) {
 
 	 showLayer(); // Move this function call outside the requestData object
  
	  const url = `RenewalReminderWS/fetch-documents?renewalId=${renewal_id}&companyId=${companyId}`;
	  fetch(url, {
	    method: 'GET',
	    headers: {
	      'Content-Type': 'application/json'
	    }
	  })
	   .then(response => {
	      if (!response.ok) {
	        throw new Error('Network response was not ok');
	      }
	      return response.json();
	    })
      .then(data => {
		 
		var data2 = JSON.stringify(data);
		var data3 = data2.split(",");

	    var baseURL = window.location.protocol + "//" + window.location.hostname;
				if (window.location.port) {
				  baseURL += ":" + window.location.port;
				}
					
		  for(var i=0;i<data3.length;i++){
			  
			  const anchor = document.createElement('a');
			  anchor.href = baseURL+"/download/RenewalReminder/" + data[i] ;
			  anchor.download = data[i];
			  document.body.appendChild(anchor);
			  anchor.click();
			  document.body.removeChild(anchor);
			  
		  }
       hideLayer();
    })
    .catch(error => console.error('Error fetching documents:', error));
}
