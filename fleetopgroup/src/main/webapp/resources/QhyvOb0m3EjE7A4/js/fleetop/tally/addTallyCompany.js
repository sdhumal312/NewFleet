$(document).ready(function() {
	getTallyCompanyDetails();
});

function getTallyCompanyDetails() {
	
	showLayer();
	var jsonObject					= new Object();

	$.ajax({
		url: "getTallyCompanyList",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			setTallyCompanyList(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}


function setTallyCompanyList(data) {
	$("#VendorPaymentTable").empty();
	$("#totalTallyCompany").html(data.count);
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th>');

	tr1.append(th1.append("No"));
	tr1.append(th2.append("Name"));
	tr1.append(th3.append("Description"));
	tr1.append(th4.append("Action"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.tallyComapnyList != undefined && data.tallyComapnyList.length > 0) {
		
		var tallyComapnyList = data.tallyComapnyList;
	
		for(var i = 0; i < tallyComapnyList.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td>');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append(tallyComapnyList[i].companyName));
			tr1.append(td3.append(tallyComapnyList[i].description));
					
			
			tr1.append(td4.append(
			'<div class="btn-group">'
			+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
			+'<ul class="dropdown-menu pull-right">'
			+'<li><a href="#" class="confirmation" onclick="getTallyCompanyById('+tallyComapnyList[i].tallyCompanyId+')"><i class="fa fa-edit"></i> Edit</a></li>'
			+'<li><a href="#" class="confirmation" onclick="deleteTallyCompany('+tallyComapnyList[i].tallyCompanyId+')"><span class="fa fa-trash"></span> Delete</a></li>'
			+'</ul>'
			+'</div>'
			));

			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#VendorPaymentTable").append(thead);
	$("#VendorPaymentTable").append(tbody);

}

function saveTallyCompany(){
	showLayer();
	var jsonObject					= new Object();

	jsonObject["tallyCompanyName"]					= $('#tallyCompanyName').val();
	jsonObject["description"]						= $('#description').val();
	
	if($('#tallyCompanyName').val().trim() == ''){
		$('#tallyCompanyName').focus();
		showMessage('errors', 'Please Select Company Name !');
		hideLayer();
		return false;
	}

	$.ajax({
		url: "saveTallyCompany",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			 $('#tallyCompanyName').val('');
			 $('#description').val('');
			 $('#addManufacturer').modal('hide');
			 
			if(data.alreadyExist != undefined && data.alreadyExist == true){
				showMessage('info', 'Already Exist!');
				hideLayer();
				return false;
			}
			
			setTallyCompanyList(data);
			hideLayer();
			showMessage('success', 'Tally Company Saved Successfully!');
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function getTallyCompanyById(tallyCompanyId){
	
	showLayer();
	var jsonObject						= new Object();

	jsonObject["tallyCompanyId"]		= tallyCompanyId;
	
	console.log("olaaaaaa.....",jsonObject)
	
	$.ajax({
		url: "getTallyCompanyListById.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			
			$('#editTallyCompanyId').val(tallyCompanyId);
			$('#editTallyCompany').val(data.TallyCompany.companyName);
			$('#editDescription').val(data.TallyCompany.description);
			$('#editTallyComp').modal('show');
			hideLayer();
		},
		error: function (e) {
			console.log("Error : " , e);
			hideLayer();
		}
	});

}

function updateTallyCompany(){

	showLayer();
	var jsonObject						= new Object();

	jsonObject["editTallyCompanyId"]		= $('#editTallyCompanyId').val();
	jsonObject["editTallyCompany"]			= $('#editTallyCompany').val();
	jsonObject["editDescription"]			= $('#editDescription').val();
	
	if($('#editTallyCompany').val().trim() == ''){
		$('#editTallyCompany').focus();
		showMessage('errors', 'Please Select Company Name !');
		hideLayer();
		return false;
	}
	
	$.ajax({
		url: "editTallyCompany.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			$('#editTallyComp').modal('hide');
			if(data.alreadyExist != undefined && data.alreadyExist == true){
				showMessage('info', 'Already Exist!');
				hideLayer();
				return false;
			}
			
			getTallyCompanyDetails();
			showMessage('success', 'Company Details Updated Successfully!');
			hideLayer();
		},
		error: function (e) {
			console.log("Error : " , e);
			hideLayer();
		}
	});

}


function deleteTallyCompany(tallyCompanyId) {
	
	if (confirm('are you sure to delete ?')) {
		showLayer();
		var jsonObject					= new Object();

		jsonObject["tallyCompanyId"]	= tallyCompanyId;
		
		$.ajax({
			url: "deleteTallyCompany.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				if(data.deleted != undefined && (data.deleted == 'true' || data.deleted == true )){
					showMessage('success', 'Data Deleted Sucessfully!');
				}
				
				getTallyCompanyDetails();
				hideLayer();
			},
			error: function (e) {
				console.log("Error : " , e);
				hideLayer();
			}
		});
	} 
	
}
