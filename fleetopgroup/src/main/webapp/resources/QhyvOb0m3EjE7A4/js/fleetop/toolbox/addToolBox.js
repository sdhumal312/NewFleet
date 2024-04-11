$(document).ready(function() {
	getToolBoxDetails();
});


function getToolBoxDetails() {
	
	showLayer();
	var jsonObject					= new Object();

	$.ajax({
		url: "getToolBoxList",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			setToolBoxList(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}


function setToolBoxList(data) {
	$("#VendorPaymentTable").empty();
	$("#totalToolBox").html(data.count);
	
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
	if(data.toolBoxList != undefined && data.toolBoxList.length > 0) {
		
		var toolBoxList = data.toolBoxList;
		for(var i = 0; i < toolBoxList.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td>');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append(toolBoxList[i].toolBoxName));
			tr1.append(td3.append(toolBoxList[i].description));
					
			
			tr1.append(td4.append(
			'<div class="btn-group">'
			+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
			+'<ul class="dropdown-menu pull-right">'
			+'<li><a href="#" class="confirmation" onclick="getToolBoxById('+toolBoxList[i].toolBoxId+')"><i class="fa fa-edit"></i> Edit</a></li>'
			+'<li><a href="#" class="confirmation" onclick="deleteTallyCompany('+toolBoxList[i].toolBoxId+')"><span class="fa fa-trash"></span> Delete</a></li>'
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

function saveToolBox(){
	
	showLayer();
	var jsonObject					= new Object();

	jsonObject["toolBoxName"]						= $('#toolBoxName').val();
	jsonObject["description"]						= $('#description').val();
	
	if($('#toolBoxName').val().trim() == ''){
		$('#toolBoxName').focus();
		showMessage('errors', 'Please Select ToolBox Name !');
		hideLayer();
		return false;
	}
	

	$.ajax({
		url: "saveToolBox",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			 $('#toolBoxName').val('');
			 $('#description').val('');
			 $('#addManufacturer').modal('hide');
			 
			if(data.alreadyExist != undefined && data.alreadyExist == true){
				showMessage('info', 'Already Exist!');
				hideLayer();
				return false;
			}else{
				showMessage('success', 'ToolBox Saved Successfully!');
				window.location.replace("addToolBox");
				hideLayer();
			}
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function getToolBoxById(toolBoxId){
	var jsonObject						= new Object();
	
	showLayer();
	
	jsonObject["toolBoxId"]		= toolBoxId;
	
	$.ajax({
		url: "getToolBoxById.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			$('#editToolBoxId').val(toolBoxId);
			$('#editToolBoxName').val(data.ToolBox.toolBoxName);
			$('#editDescription').val(data.ToolBox.description);
			$('#editToolBox').modal('show');
			hideLayer();
		},
		error: function (e) {
			console.log("Error : " , e);
			hideLayer();
		}
	});

}

function updateToolBox(){
	showLayer();
	var jsonObject							= new Object();

	jsonObject["editToolBoxId"]				= $('#editToolBoxId').val();
	jsonObject["editToolBoxName"]			= $('#editToolBoxName').val();
	jsonObject["editDescription"]			= $('#editDescription').val();
	
	if($('#editToolBoxName').val().trim() == ''){
		$('#editToolBoxName').focus();
		showMessage('errors', 'Please Select ToolBox Name !');
		hideLayer();
		return false;
	}
	
	$.ajax({
		url: "editToolBox.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.alreadyExist != undefined && data.alreadyExist == true){
				showMessage('info', 'Already Exist!');
				hideLayer();
				return false;
			}else{
				getToolBoxDetails();
				showMessage('success', 'ToolBox Details Updated Successfully!');
				window.location.replace("addToolBox");
				hideLayer();
			}
		},
		error: function (e) {
			console.log("Error : " , e);
			hideLayer();
		}
	});

}


function deleteTallyCompany(toolBoxId) {
	
	if (confirm('Aru You Sure, Do You Want to delete ToolBox?')) {
		showLayer();
		var jsonObject					= new Object();

		jsonObject["toolBoxId"]	= toolBoxId;
		
		$.ajax({
			url: "deleteToolBox.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
			if(data.deleted != undefined && (data.deleted == 'true' || data.deleted == true )){
				showMessage('success', 'Deleted Sucessfully!');
				window.location.replace("addToolBox");
				hideLayer();
			}
				
			},
			error: function (e) {
				console.log("Error : " , e);
				hideLayer();
			}
		});
	} 
	
}
