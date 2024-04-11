$(document).ready(function() {
	getPageWiseCommentTypeDetails(1);
});

function getPageWiseCommentTypeDetails(pageNumber) {

	showLayer();
	var jsonObject					= new Object();
	jsonObject["pageNumber"]		= pageNumber;

	$.ajax({
		url: "getPageWiseCommentTypeDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setCommentTypeList(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}


function setCommentTypeList(data) {

	$("#commentTypeDetailsTable").empty();

	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th>');

	tr1.append(th1.append("No"));
	tr1.append(th2.append("Comment Type"));
	tr1.append(th3.append("Description"));
	tr1.append(th4.append("Action"));

	thead.append(tr1);

	var tbody = $('<tbody>');
	if(data.CommentType != undefined && data.CommentType.length > 0) {

		var CommentTypeList = data.CommentType;

		for(var i = 0; i < CommentTypeList.length; i++) {
			var tr1 = $('<tr class="ng-scope">');

			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td>');

			tr1.append(td1.append(i + 1));
			tr1.append(td2.append(CommentTypeList[i].commentTypeName));
			tr1.append(td3.append(CommentTypeList[i].description));

			tr1.append(td4.append(
					'<div class="btn-group">'
					+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
					+'<ul class="dropdown-menu pull-right">'
					+'<li><a href="#" class="confirmation" onclick="getCommentTypeById('+CommentTypeList[i].commentTypeId+')"><i class="fa fa-edit"></i> Edit</a></li>'
					+'<li><a href="#" class="confirmation" onclick="deleteCommentTypeById('+CommentTypeList[i].commentTypeId+')"><span class="fa fa-trash"></span> Delete</a></li>'
					+'</ul>'
					+'</div>'
			));

			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}

	$("#commentTypeDetailsTable").append(thead);
	$("#commentTypeDetailsTable").append(tbody);


	$("#navigationBar").empty();

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;&lt;</a></li>');		
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="getPageWiseCommentTypeDetails(1)">&lt;&lt;</a></li>');		
	}

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="getPageWiseCommentTypeDetails('+(data.currentIndex - 1)+')">&lt;</a></li>');
	}

	for (i = data.beginIndex; i <= data.endIndex; i++) {
		if(i == data.currentIndex) {
			$("#navigationBar").append('<li class="active"><a href="#" onclick="getPageWiseCommentTypeDetails('+i+')">'+i+'</a></li>');	    	
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getPageWiseCommentTypeDetails('+i+')">'+i+'</a></li>');	    	
		}
	} 

	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getPageWiseCommentTypeDetails('+(data.currentIndex + 1)+')">&gt;</a></li>');			
		}
	}

	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getPageWiseCommentTypeDetails('+(data.deploymentLog.totalPages)+')">&gt;&gt;</a></li>');			
		}
	}
}

function saveCommentType(){
	showLayer();
	var jsonObject			= new Object();

	jsonObject["commentTypeName"]						= $('#commentTypeName').val();
	jsonObject["description"]							= $('#description').val();
	jsonObject["validateDoublePost"] 					=  true;
	jsonObject["unique-one-time-token"] 				=  $('#accessToken').val();

	if($('#commentTypeName').val().trim() == '' || $('#commentTypeName').val().trim() == null){
		$('#commentTypeName').focus();
		showMessage('errors', 'Please Enter Comment Type Name !');
		hideLayer();
		return false;
	}

	$.ajax({
		url: "saveCommentType",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			if(data.commentTypeName != undefined && (data.commentTypeName == true || data.commentTypeName == 'true')){
				 $('#accessToken').val(data.accessToken);
				showMessage('errors', 'Please Enter Comment Type Name !');
				hideLayer();
				return false;
			}
			
			$('#commentTypeName').val('');
			$('#description').val('');
			$('#addManufacturer').modal('hide');
			
			if(data.alreadyExist != undefined && data.alreadyExist == true){
				showMessage('info', 'Already Exist!');
				hideLayer();
				return false;
			}else{
				hideLayer();
				showMessage('success', 'Comment Type Saved Successfully!');
				return location.reload();
			}
			
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function getCommentTypeById(commentTypeId){
	showLayer();
	var jsonObject						= new Object();

	jsonObject["commentTypeId"]		= commentTypeId;

	$.ajax({
		url: "getCommentTypeById.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {

			$('#editCommentTypeId').val(commentTypeId);
			$('#editCommentTypeName').val(data.commentType.commentTypeName);
			$('#editDescription').val(data.commentType.description);
			$('#editCommentType').modal('show');
			hideLayer();
		},
		error: function (e) {
			console.log("Error : " , e);
			hideLayer();
		}
	});

}

function updateCommentType(){

	showLayer();
	var jsonObject						= new Object();

	jsonObject["editCommentTypeId"]			= $('#editCommentTypeId').val();
	jsonObject["editCommentTypeName"]		= $('#editCommentTypeName').val();
	jsonObject["editDescription"]			= $('#editDescription').val();

	if($('#editCommentTypeName').val().trim() == '' || $('#editCommentTypeName').val().trim() == null){
		$('#editCommentTypeName').focus();
		showMessage('errors', 'Please Enter Comment Type Name !');
		hideLayer();
		return false;
	}

	$.ajax({
		url: "editCommentType.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			if(data.editCommentTypeName != undefined && (data.editCommentTypeName == true || data.editCommentTypeName == 'true')){
				showMessage('errors', 'Please Enter Comment Type Name !');
				hideLayer();
				return false;
			}
			
			$('#editCommentType').modal('hide');
			
			if(data.alreadyExist != undefined && data.alreadyExist == true){
				showMessage('info', 'Already Exist!');
				hideLayer();
				return false;
			}else{
				showMessage('success', 'Comment Type Details Updated Successfully!');
				location.reload();
			}
		},
		error: function (e) {
			console.log("Error : " , e);
			hideLayer();
		}
	});

}


function deleteCommentTypeById(commentTypeId) {

	if (confirm('are you sure to delete ?')) {
		showLayer();
		var jsonObject					= new Object();

		jsonObject["commentTypeId"]	= commentTypeId;

		$.ajax({
			url: "deleteCommentTypeById.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				if(data.deleted != undefined && (data.deleted == 'true' || data.deleted == true)){
					showMessage('success', 'Data Deleted Sucessfully!');
				}
				getPageWiseCommentTypeDetails(1);
				hideLayer();
			},
			error: function (e) {
				console.log("Error : " , e);
				hideLayer();
			}
		});
	} 

}
