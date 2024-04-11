$(document).ready(function() {
	getPartSubCategoryDetails();
});

function getPartSubCategoryDetails() {
	
	showLayer();
	var jsonObject					= new Object();

	$.ajax({
		url: "getPartSubCategoryList",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			setPartSubCategoryList(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}


function setPartSubCategoryList(data) {
	$("#partSubCategoryTable").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th>');
	var th5		= $('<th>');

	tr1.append(th1.append("No"));
	tr1.append(th2.append("Name"));
	tr1.append(th3.append("Category"));
	tr1.append(th4.append("Description"));
	tr1.append(th5.append("Action"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.partSubCategoryList != undefined && data.partSubCategoryList.length > 0) {
		
		var partSubCategoryList = data.partSubCategoryList;
	
		for(var i = 0; i < partSubCategoryList.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td>');
			var td5		= $('<td>');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append(partSubCategoryList[i].subCategoryName));
			tr1.append(td3.append(partSubCategoryList[i].partCategoryName));
			tr1.append(td4.append(partSubCategoryList[i].description));
					
			
			tr1.append(td5.append(
			'<div class="btn-group">'
			+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
			+'<ul class="dropdown-menu pull-right">'
			+'<li><a href="#" class="confirmation" onclick="getPartSubCategoryById('+partSubCategoryList[i].subCategoryId+')"><i class="fa fa-edit"></i> Edit</a></li>'
			+'<li><a href="#" class="confirmation" onclick="deletePartSubCategoryById('+partSubCategoryList[i].subCategoryId+')"><span class="fa fa-trash"></span> Delete</a></li>'
			+'</ul>'
			+'</div>'
			));

			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#partSubCategoryTable").append(thead);
	$("#partSubCategoryTable").append(tbody);

}

function savePartSubCategories(){
	showLayer();
	var jsonObject					= new Object();

	jsonObject["partCategory"]						= $('#partCategory').val();
	jsonObject["partSubCategory"]					= $('#partSubCategory').val();
	jsonObject["description"]						= $('#description').val();
	
	if($('#partCategory').val() == ''){
		$('#partCategory').focus();
		showMessage('errors', 'Please Select Category Name !');
		hideLayer();
		return false;
	}else if($('#partSubCategory').val().trim() == ''){
			$('#partSubCategory').focus();
			showMessage('errors', 'Please Enter Part Sub Category Name !');
			hideLayer();
			return false;
	}
	
	$.ajax({
		url: "savePartSubCategories",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			 $('#partCategory').val('');
			 $('#partSubCategory').val('');
			 $('#description').val('');
			 $('#addManufacturer').modal('hide');
			 
			if(data.alreadyExist != undefined && data.alreadyExist == true){
				showMessage('info', 'Already Exist!');
				hideLayer();
				return false;
			}
			
			setPartSubCategoryList(data);
			hideLayer();
			showMessage('success', 'Part Sub Category Saved Successfully!');
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function getPartSubCategoryById(subCategoryId){
	console.log("subCategoryId---",subCategoryId)
	showLayer();
	var jsonObject						= new Object();

	jsonObject["subCategoryId"]		= subCategoryId;
	
	$.ajax({
		url: "getPartSubCategoryById.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			$('#editSubCategoryId').val(subCategoryId);
			$('#editPartCategory').val(data.partSubCategory.categoryId);
			$('#editPartSubCategory').val(data.partSubCategory.subCategoryName);
			$('#editDescription').val(data.partSubCategory.description);
			$('#editSubCategory').modal('show');
			hideLayer();
		},
		error: function (e) {
			console.log("Error : " , e);
			hideLayer();
		}
	});

}

function updatePartSubCategory(){

	showLayer();
	var jsonObject						= new Object();

	jsonObject["editSubCategoryId"]		= $('#editSubCategoryId').val();
	jsonObject["editPartSubCategory"]	= $('#editPartSubCategory').val();
	jsonObject["editDescription"]		= $('#editDescription').val();
	jsonObject["editPartCategory"]		= $('#editPartCategory').val();

	if($('#editPartCategory').val() == null){
		$('#editPartCategory').focus();
		showMessage('errors', 'Please Select Category Name !');
		hideLayer();
		return false;
	}else if($('#editPartSubCategory').val().trim() == null || $('#editPartSubCategory').val().trim() == ''){
			$('#editPartSubCategory').focus();
			showMessage('errors', 'Please Enter Part Sub Category Name !');
			hideLayer();
			return false;
	}
	
	$.ajax({
		url: "editPartSubCategory.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			$('#editSubCategory').modal('hide');
			if(data.alreadyExist != undefined && data.alreadyExist == true){
				showMessage('info', 'Already Exist!');
				hideLayer();
				return false;
			}else{
				showMessage('success', 'Part Sub Category Details Updated Successfully!');
				location.reload();
			}
			
		
			hideLayer();
		},
		error: function (e) {
			console.log("Error : " , e);
			hideLayer();
		}
	});

}


function deletePartSubCategoryById(subCategoryId) {
	
	if (confirm('are you sure to delete ?')) {
		showLayer();
		var jsonObject					= new Object();

		jsonObject["subCategoryId"]	= subCategoryId;
		
		$.ajax({
			url: "deletePartSubCategoryById.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				if(data.deleted != undefined && (data.deleted == 'true' || data.deleted == true )){
					showMessage('success', 'Data Deleted Sucessfully!');
				}
				
				getPartSubCategoryDetails();
				hideLayer();
			},
			error: function (e) {
				console.log("Error : " , e);
				hideLayer();
			}
		});
	} 
	
}
