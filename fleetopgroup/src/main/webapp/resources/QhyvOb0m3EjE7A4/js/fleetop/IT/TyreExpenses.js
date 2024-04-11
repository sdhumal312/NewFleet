$(document).ready(function() {
	showLayer();
	var jsonObject					= new Object();
	$.ajax({
		url: "getAllTyreExpenses",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			setAllTyreExpenses(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
});
function setAllTyreExpenses (data){
	$("#tyreExpensesTable").empty();
	$('#searchData').show();
	$('#countDiv').show();
	
	var thead 	= $('<thead>');
	var tbody 	= $('<tbody>');
	var tr1 	= $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th>');

	tr1.append(th1.append("No"));
	tr1.append(th2.append("Name"));
	tr1.append(th3.append("Description"));
	tr1.append(th4.append("Action"));

	thead.append(tr1);
	
	if(data.tyreExpensesList != undefined && data.tyreExpensesList.length > 0) {
		
		var tyreExpensesList = data.tyreExpensesList;
		$('#countId').text(tyreExpensesList.length);
	
		for(var i = 0; i < tyreExpensesList.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td>');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append(tyreExpensesList[i].tyreExpenseName));
			tr1.append(td3.append(tyreExpensesList[i].description));
			tr1.append(td4.append(
			'<div class="btn-group">'
			+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
			+'<ul class="dropdown-menu pull-right">'
			+'<li><a href="#" class="confirmation" onclick="editTyreExpensName('+tyreExpensesList[i].tyreExpenseId+')"><i class="fa fa-edit"></i> Edit</a></li>'
			+'<li><a href="#" class="confirmation" onclick="deleteTyreExpensName('+tyreExpensesList[i].tyreExpenseId+')"><span class="fa fa-trash"></span> Delete</a></li>'
			+'</ul>'
			+'</div>'
			));

			tbody.append(tr1);
			
		}
		
		$("#tyreExpensesTable").append(thead);
		$("#tyreExpensesTable").append(tbody);
	
	}else{
		showMessage('info','No record found !')
		return false;
	}
	
}

function saveTyreExpense(){
	showLayer();
	var jsonObject							= new Object();

	jsonObject["addTyreExpenseName"]		= $('#addTyreExpenseName').val();
	jsonObject["addDescription"]			= $('#addDescription').val();
	
	if($('#addTyreExpenseName').val().trim() == ''){
		$('#addTyreExpenseName').focus();
		showMessage('errors', 'Please Select Tyre Expense Name !');
		hideLayer();
		return false;
	}
	
	$.ajax({
		url: "addTyreExpense",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			$('#addTyreExpense').modal('hide');
			$('#addTyreExpenseName').val('');
			$('#addDescription').val('');
			if(data.alreadyExist != undefined && data.alreadyExist == true){
				showMessage('info', 'Already Exist!');
				hideLayer();
				return false;
			}else{
				showMessage('success', 'New Tyre Expense Saved Successfully!');
				window.location.replace("TyreExpense");
			}
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function editTyreExpensName(tyreExpenseId){
	var jsonObject					= new Object();
	jsonObject["tyreExpenseId"]		= tyreExpenseId;
	
	$.ajax({
		url: "getTyreExpenseByTyreExpenseId",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setTyreExpenseByExpenseId(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function setTyreExpenseByExpenseId(data){
	var tyreExpense 	= data.tyreExpensesDto;
	
	$('#editTyreExpenseName').val(tyreExpense.tyreExpenseName);
	$('#editDescription').val(tyreExpense.description);
	$('#editTyreExpenseId').val(tyreExpense.tyreExpenseId);
	
	$('#editTyreExpense').modal('show');
}
function updateTyreExpense(){
	if($('#editTyreExpenseId').val() != undefined &&  $('#editTyreExpenseId').val() > 0){
		showLayer();
		var jsonObject					= new Object();
		
		jsonObject["editTyreExpenseId"]				= $('#editTyreExpenseId').val();
		jsonObject["editTyreExpenseName"]			= $('#editTyreExpenseName').val();
		jsonObject["editDescription"]				= $('#editDescription').val();
		
		if($('#editTyreExpenseName').val().trim() == ''){
			$('#editTyreExpenseName').focus();
			showMessage('errors', 'Please Select Tyre Expense Name !');
			hideLayer();
			return false;
		}
		
		
		$.ajax({
			url: "updateTyreExpense",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				$('#editTyreExpense').modal('hide');
				$('#editTyreExpenseName').val('');
				$('#editTyreExpenseId').val('');
				$('#editDescription').val('');
				if(data.alreadyExist != undefined && data.alreadyExist == true){
					showMessage('info', 'Already Exist!');
					hideLayer();
					return false;
				}else{
					showMessage('success', 'Update Tyre Expense Successfully!');
					window.location.replace("TyreExpense");
				}
				hideLayer();
			},
			error: function (e) {
				hideLayer();
				showMessage('errors', 'Some Error Occurred!');
			}
		});
	}
}

function deleteTyreExpensName(tyreExpenseId){
	
	if (confirm('Are you sure, you want to Delete this Expense?')) {
	
		if(tyreExpenseId != undefined && tyreExpenseId > 0){
			
			var jsonObject					= new Object();
			
			jsonObject["deleteTyreExpenseId"]					= tyreExpenseId;
			
			$.ajax({
				url: "deleteTyreExpense",
				type: "POST",
				dataType: 'json',
				data: jsonObject,
				success: function (data) {
					showMessage('success', 'Delete Tyre Expense Successfully!');
					hideLayer();
					window.location.replace("TyreExpense");
					return false;
					
				},
				error: function (e) {
					hideLayer();
					showMessage('errors', 'Some Error Occurred!');
				}
			});
		}
	  
	}else {
	window.location.replace("TyreExpense");
	}
}

