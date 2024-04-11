$(document).ready(function() {
	getClothTypesDetails();
});

function getClothTypesDetails() {
	
	showLayer();
	var jsonObject					= new Object();

	$.ajax({
		url: "getClothTypesList",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			setClothTypeList(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}


function setClothTypeList(data) {
	$("#VendorPaymentTable").empty();
	$("#searchInv").val('');
	$("#searchBattery").val('');
	$('#searchData').show();
	$('#countDiv').show();
	$('#listTab').show();
	$('#addBatteryDiv').addClass('hide');
	$("#totalClothType").html(data.count);
	
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
	if(data.clothTypeList != undefined && data.clothTypeList.length > 0) {
		
		var clothTypeList = data.clothTypeList;
	
		for(var i = 0; i < clothTypeList.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td>');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append(clothTypeList[i].clothTypeName));
			tr1.append(td3.append(clothTypeList[i].description));
			
			//var curl = "editBatteryInvoice.in?Id="+clothTypeList[i].clothTypesId
			
			tr1.append(td4.append(
			'<div class="btn-group">'
			+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
			+'<ul class="dropdown-menu pull-right">'
			+'<li><a href="#" class="confirmation" onclick="getClothTypesById('+clothTypeList[i].clothTypesId+')"><i class="fa fa-edit"></i> Edit</a></li>'
			+'<li><a href="#" class="confirmation" onclick="deleteClothType('+clothTypeList[i].clothTypesId+')"><span class="fa fa-trash"></span> Delete</a></li>'
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


function updateClothTypes(){

	showLayer();
	var jsonObject						= new Object();

	jsonObject["clothTypesId"]			= $('#clothTypesId').val();
	jsonObject["clothTypeName"]			= $('#editclothTypeName').val();
	jsonObject["description"]			= $('#editdescription').val();
	
	if($('#editclothTypeName').val().trim() == ''){
		$('#editclothTypeName').focus();
		showMessage('errors', 'Please Select Cloth Type Name !');
		hideLayer();
		return false;
	}
	
	$.ajax({
		url: "editClothTypes.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			$('#editClothType').modal('hide');
			if(data.alreadyExist != undefined && data.alreadyExist == true){
				showMessage('info', 'Already Exist!');
				hideLayer();
				return false;
			}
			setClothTypeList(data);
			
			showMessage('success', 'Cloth Types Updated Successfully!');
			hideLayer();
		},
		error: function (e) {
			console.log("Error : " , e);
			hideLayer();
		}
	});

}

function getClothTypesById(clothTypesId){

	showLayer();
	var jsonObject						= new Object();

	jsonObject["clothTypesId"]			= clothTypesId;

	$.ajax({
		url: "getClothTypesById.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			console.log('data , ', data);
			$('#clothTypesId').val(clothTypesId);
			$('#editclothTypeName').val(data.clothTypes.clothTypeName);
			$('#editdescription').val(data.clothTypes.description);
			$('#editClothType').modal('show');
			hideLayer();
		},
		error: function (e) {
			console.log("Error : " , e);
			hideLayer();
		}
	});

}


function deleteClothType(clothTypesId) {
	
	if (confirm('are you sure to delete ?')) {
		showLayer();
		var jsonObject					= new Object();

		jsonObject["clothTypesId"]			= clothTypesId;
		
		$.ajax({
			url: "deleteClothType.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				if(data.deleted != undefined && (data.deleted == 'true' || data.deleted == true )){
					showMessage('success', 'Data Deleted Sucessfully!');
				}
				
				getClothTypesDetails();
				hideLayer();
			},
			error: function (e) {
				console.log("Error : " , e);
				hideLayer();
			}
		});
	} 
	
}



function saveClothTypes(){
	showLayer();
	var jsonObject					= new Object();

	jsonObject["clothTypeName"]					= $('#clothTypeName').val();
	jsonObject["description"]					= $('#description').val();
	
	if($('#clothTypeName').val().trim() == ''){
		$('#clothTypeName').focus();
		showMessage('errors', 'Please Select Cloth Type Name !');
		hideLayer();
		return false;
	}

	$.ajax({
		url: "saveClothTypes",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			console.log('data', data);
			 $('#clothTypeName').val('');
			 $('#description').val('');
			$('#addManufacturer').modal('hide');
			if(data.alreadyExist != undefined && data.alreadyExist == true){
				showMessage('info', 'Already Exist!');
				hideLayer();
				return false;
			}
			setClothTypeList(data);
			
			hideLayer();
			
			showMessage('success', 'New Cloth Types Saved Successfully!');
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}