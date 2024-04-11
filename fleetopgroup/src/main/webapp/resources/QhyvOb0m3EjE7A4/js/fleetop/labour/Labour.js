$(document).ready(function() {
	getAllLabourMaster();
});

function getAllLabourMaster() {
	console.log("dfdskfhfkh")
	showLayer();
	var jsonObject					= new Object();
	jsonObject["companyId"]						= $('#companyId').val();
	$.ajax({
		url: "getAllLabourMaster",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			setLabourMaster(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setLabourMaster(data) {
	$("#labourTable").empty();
	var labourList = data.labourList;
	if(labourList != undefined || labourList != null){
		for(var index = 0 ; index < labourList.length; index++){
			var columnArray = new Array();
			columnArray.push("<td class='fit'>"+(index+1)+"</td>");
			columnArray.push("<td class='fit'>"+ labourList[index].labourName+"</td>");
			columnArray.push("<td class='fit'>"+ labourList[index].description+"</td>");
			columnArray.push("<td class='fit ar' style='vertical-align: middle;' ><a href='#'class='confirmation' onclick='editLabour("+labourList[index].labourId+");'><span class='fa fa-pen'></span> Edit</a>&nbsp;&nbsp;&nbsp<a href='#' style='color:red'  class='confirmation' onclick='deleteLabour("+labourList[index].labourId+");'><span class='fa fa-trash'></span> Delete</a></td>");
			
			$('#labourTable').append("<tr id='penaltyID"+labourList[index].labourId+"' >" + columnArray.join(' ') + "</tr>");
		}
		columnArray = [];
	}
}



function saveLabour(){
	
	showLayer();
	var jsonObject					= new Object();

	jsonObject["labourName"]						= $('#labourName').val();
	jsonObject["description"]						= $('#description').val();
	jsonObject["companyId"]						= $('#companyId').val();
	jsonObject["userId"]						= $('#userId').val();
	
	if($('#labourName').val().trim() == ''){
		$('#labourName').focus();
		showMessage('errors', 'Please Enter Labour Name !');
		hideLayer();
		return false;
	}
	

	$.ajax({
		url: "saveLabour",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			 $('#labourName').val('');
			 $('#description').val('');
			 $('#addLabour').modal('hide');
			 
			if(data.alreadyExist != undefined && data.alreadyExist == true){
				showMessage('info', 'Already Exist!');
				hideLayer();
				return false;
			}else{
				showMessage('success', 'Labour Saved Successfully!');
				window.location.replace("labour");
				hideLayer();
			}
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function editLabour(labourId){
	var jsonObject						= new Object();
	
	showLayer();
	
	jsonObject["labourId"]		= labourId;
	jsonObject["companyId"]		= $('#companyId').val();
	
	$.ajax({
		url: "getLabourMaster.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			$('#editLabourId').val(labourId);
			$('#editLabourName').val(data.labour.labourName);
			$('#editDescription').val(data.labour.description);
			$('#editLabour').modal('show');
			hideLayer();
		},
		error: function (e) {
			console.log("Error : " , e);
			hideLayer();
		}
	});

}

function updateLabour(){
	showLayer();
	var jsonObject							= new Object();

	jsonObject["labourId"]					= $('#editLabourId').val();
	jsonObject["labourName"]				= $('#editLabourName').val();
	jsonObject["description"]				= $('#editDescription').val();
	jsonObject["companyId"]					= $('#companyId').val();
	
	if($('#editLabourName').val().trim() == ''){
		$('#editLabourName').focus();
		showMessage('errors', 'Please Enter Labour Name !');
		hideLayer();
		return false;
	}
	
	$.ajax({
		url: "updateLabour.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.alreadyExist != undefined && data.alreadyExist == true){
				showMessage('info', 'Already Exist!');
				hideLayer();
				return false;
			}else{
				location.reload();
			}
		},
		error: function (e) {
			console.log("Error : " , e);
			hideLayer();
		}
	});

}


function deleteLabour(labourId) {
	
	if (confirm('Aru You Sure, Do You Want to delete Labour?')) {
		showLayer();
		var jsonObject					= new Object();

		jsonObject["labourId"]	= labourId;
		jsonObject["companyId"]					= $('#companyId').val();
		$.ajax({
			url: "deleteLabour.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				showMessage('success', 'Deleted Sucessfully!');
				location.reload();
				
			},
			error: function (e) {
				console.log("Error : " , e);
				hideLayer();
			}
		});
	} 
	
}
